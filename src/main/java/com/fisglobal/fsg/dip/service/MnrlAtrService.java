package com.fisglobal.fsg.dip.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.MNRLDecryptionUtils;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.request.entity.MNRLAtrBody;
import com.fisglobal.fsg.dip.request.entity.MNRLAtrPayload;
import com.fisglobal.fsg.dip.request.entity.MNRLAtrRequest;
import com.fisglobal.fsg.dip.request.entity.MNRLHeaders;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrData_VO;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrEncrypt_Vo;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrUpdate_Vo;
import com.fisglobal.fsg.dip.response.entity.MnrlAtrResponse;

@Service
public class MnrlAtrService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlAtrService.class);

	

	@Inject
	private MNRLDecryptionUtils utils;

	@Inject
	private MnrlAuthService mnrlAuthService;

	@Inject
	private MnrlService mnrlService;

	

	@Inject
	private ServiceHandlerCbs serviceHandler;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	@Qualifier("MNRL_ATR_REQ")
	private MessageSource mnrlReq;

	public MnrlAtrResponse processAtrData(MnrlCbsData_DAO mnrlCbsEntity) throws KeyManagementException,
			ClientProtocolException, JSONException, NoSuchAlgorithmException, KeyStoreException, IOException {
		List<MnrlAtrData_VO> atrdatalist = new ArrayList<>();
		MnrlAtrResponse atrResponse =null;
		MnrlAtrData_VO atrdata = new MnrlAtrData_VO();
		String actionTaken = mnrlReq.getMessage(mnrlCbsEntity.getMnrlReason() + ".actiontaken", null, "1", Locale.US);
		String investigationDetails = mnrlReq.getMessage(mnrlCbsEntity.getMnrlReason() + ".investigation", null,
				"Digital Block done", Locale.US);
		String grievance = mnrlReq.getMessage(mnrlCbsEntity.getMnrlReason() + ".grievance", null, "No", Locale.US);

		String mobile = commonMethodUtils.getMobileNumberWithoutCC(mnrlCbsEntity.getMobileNo());

		atrdata.setMobile_no(mobile);
		atrdata.setAction_taken(actionTaken);
		atrdata.setInvestigation_details(investigationDetails);

		atrdata.setDate_of_action(commonMethodUtils.toChangeDateFormat("yyyy-MM-dd HH:mm:ss", new Date()));

		atrdata.setDisconnectionreason_id(mnrlCbsEntity.getMnrlReason());
		atrdata.setGrievance_received(grievance);
		atrdatalist.add(atrdata);

		MnrlAtrUpdate_Vo atrVo = new MnrlAtrUpdate_Vo();
		atrVo.setBank_id(MnrlLoadData.tokenReqDetails.getBankId());
		atrVo.setAtr_data(atrdatalist);
		LOGGER.info("Plain List size of ATR {}", atrdatalist.size());

		System.out.println("Atr Request" + atrVo.getBank_id());
		MnrlAtrEncrypt_Vo atrEnc = new MnrlAtrEncrypt_Vo();

		String atrRequest = MnrlLoadData.gson.toJson(atrVo);
		String token = mnrlAuthService.getMnrlAuthtoken(mnrlService.getMnrlAuth());
		if(StringUtils.isBlank(token)) {
			LOGGER.info("Token is null");
			atrResponse =new MnrlAtrResponse();
			return atrResponse;
		}
		LOGGER.info("Atr plain data [{}]", atrRequest);
		atrEnc = utils.encRequestData("IzcaIJ9xdn5Rvgifnvdpuw==", atrRequest,
				MnrlLoadData.propdetails.getAppProperty().getMnrlPubKeyPath(), token);
		// System.out.println(gson.toJson(atrEnc));
		String encAtrData = MnrlLoadData.gson.toJson(atrEnc);

		LOGGER.info("Atr plain data [{}]", encAtrData);
		
		String response = sendRequest(encAtrData);
		try {
			atrResponse= MnrlLoadData.gson.fromJson(response, MnrlAtrResponse.class);
		}catch(Exception e) {
			LOGGER.error("Exception in ATR Response",e);
			atrResponse=null;
		}
		return atrResponse;
	}

	public String sendRequest(String request) throws KeyManagementException, ClientProtocolException, JSONException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
		LOGGER.debug("MNRL ATR request [{}]", request);

		

		String response = serviceHandler.sendRequest(request,  MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlAtrUrl(),MnrlLoadData.propdetails.getAppProperty().getMnrlAtrApiHeader());
		LOGGER.info("Atr Response [{}]", response);
		return response;
	}

	public MnrlAtrEncrypt_Vo setMnrlAtrRequest(String encryptedKey, String encryptedData, String token) {

		MNRLAtrPayload atrPayload = new MNRLAtrPayload();
		atrPayload.setEncryptedKey(encryptedKey);
		atrPayload.setEncryptedData(encryptedData);

		MNRLAtrBody atrBody = new MNRLAtrBody();
		atrBody.setPayload(atrPayload);

		MNRLHeaders headers = new MNRLHeaders();
		headers.setAuthorization(token);

		MNRLAtrRequest atrRequest = new MNRLAtrRequest();
		atrRequest.setBody(atrBody);
		atrRequest.setHeaders(headers);

		MnrlAtrEncrypt_Vo atrEncryptReq = new MnrlAtrEncrypt_Vo();
		atrEncryptReq.setMNRLATRUpdate_Request(atrRequest);

		return atrEncryptReq;
	}

}

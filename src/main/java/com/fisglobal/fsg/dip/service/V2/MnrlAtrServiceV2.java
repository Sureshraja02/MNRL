package com.fisglobal.fsg.dip.service.V2;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.convertor.MNRLDecryptionUtils;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLATRUpdateRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLHeadersV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAtrBodyV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAtrPayloadV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAtrRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAtrResponseV2;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrData_VO;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrUpdate_Vo;

@Service
public class MnrlAtrServiceV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlAtrServiceV2.class);

	@Inject
	private MNRLDecryptionUtils utils;

	@Inject
	private MnrlAuthServiceV2 mnrlAuthService;

	@Inject
	private MnrlServiceV2 mnrlService;

	@Inject
	private CryptoApp cryptoApp;

	@Inject
	private MnrlCbsDataRepo mnrlCbsDataRepo;

	@Inject
	private ServiceHandlerCbs serviceHandler;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	@Qualifier("MNRL_ATR_REQ")
	private MessageSource mnrlReq;

	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;

	public void processAtrData(List<MnrlAtrData_VO> atrdatalist,List<MnrlCbsData_DAO> cbslist) throws Exception {

		MnrlAtrResponseV2 atrResponse = null;

		/*
		 * Map<String, String> atrMobile = new HashMap<String, String>();
		 * 
		 * for (int i = 0; i < atrdatalist.size(); i++) {
		 * atrMobile.put(atrdatalist.get(i).getMobile_no(),
		 * atrdatalist.get(i).getMobile_no()); }
		 */

		MnrlAtrUpdate_Vo atrVo = new MnrlAtrUpdate_Vo();
		atrVo.setBank_id(MnrlLoadData.tokenReqDetails.getBankId());
		atrVo.setAtr_data(atrdatalist);
		LOGGER.info("Plain List size of ATR {} CBS{}", atrdatalist.size(),cbslist.size());

		System.out.println("Atr Request" + atrVo.getBank_id());

		String atrRequest = MnrlLoadData.gson.toJson(atrVo);
		String token = mnrlAuthService.getMnrlAuthtoken(MnrlLoadData.tokenReqDetails.getEmail(),
				MnrlLoadData.tokenReqDetails.getSecureterm());
		if (StringUtils.isBlank(token)) {
			LOGGER.info("Token is null");
			atrResponse = new MnrlAtrResponseV2();
			updateAtrResponse(atrResponse, cbslist);
		}
		if (StringUtils.isNotBlank(token)) {
			LOGGER.info("Atr plain data [{}]", atrRequest);

			String encAtrData = cryptoApp.encryptMnrlData(atrRequest, "ATRV2", token);

			LOGGER.info("Atr plain data [{}]", encAtrData);

			String response = sendRequest(encAtrData);

			try {

				atrResponse = MnrlLoadData.gson.fromJson(response, MnrlAtrResponseV2.class);
				updateAtrResponse(atrResponse, cbslist);
			} catch (Exception e) {
				LOGGER.error("Exception in ATR Response", e);
				atrResponse = null;
				updateAtrResponse(atrResponse, cbslist);
			}
			// return atrResponse;

		}

	}

	public String sendRequest(String request) throws KeyManagementException, ClientProtocolException, JSONException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
		LOGGER.debug("MNRL ATR request [{}]", request);

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlAtrUrl(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlAtrApiHeader());
		LOGGER.info("Atr Response [{}]", response);
		return response;
	}

	public String setMnrlAtrRequest(String encryptedKey, String encryptedData, String authTag, String nonce,
			String token) {

		MnrlAtrPayloadV2 atrPayload = new MnrlAtrPayloadV2();
		atrPayload.setEncryptedKey(encryptedKey);
		atrPayload.setEncryptedData(encryptedData);
		atrPayload.setAuthTag(authTag);
		atrPayload.setNonce(nonce);

		MnrlAtrBodyV2 atrBody = new MnrlAtrBodyV2();
		atrBody.setPayload(atrPayload);

		MNRLHeadersV2 headers = new MNRLHeadersV2();
		headers.setAuthorization(token);

		MNRLATRUpdateRequestV2 atrRequest = new MNRLATRUpdateRequestV2();
		atrRequest.setBody(atrBody);
		atrRequest.setHeaders(headers);

		MnrlAtrRequestV2 atrEncryptReq = new MnrlAtrRequestV2();
		atrEncryptReq.setMNRLATRUpdateRequest(atrRequest);

		String request = MnrlLoadData.gson.toJson(atrEncryptReq);

		LOGGER.debug("ATR V2 Request [{}]", request);
		return request;
	}

	/*
	 * public void updateAtrResponse(MnrlAtrResponseV2 atrResponse, Map<String,
	 * String> atr) { atr.forEach((key, value) -> { LOGGER.info("Mobile No [{}]",
	 * key); MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo
	 * .getAtrUploadData(commonMethodUtils.getMobileNumberWithCC(key),null); if
	 * (!(Optional.ofNullable(atrResponse.getErrorResponse()).isPresent())) { //
	 * LOGGER.info("ATR Retry");
	 * 
	 * mnrlCbsEntity.setAtrUploadFlag("R"); // mnrlCbsEntity.setActionFlag("77"); //
	 * mnrlCbsDataRepo.save(mnrlCbsEntity); }
	 * 
	 * Optional.ofNullable(atrResponse.getErrorResponse()).ifPresent(a -> {
	 * Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> { //
	 * MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo.getAtrUploadData(key); if
	 * (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText()) &&
	 * a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
	 * LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
	 * mnrlCbsEntity.setAtrUploadFlag("R"); // mnrlCbsEntity.setActionFlag("77"); }
	 * else { if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData()))
	 * { mnrlCbsEntity.setAtrUploadFlag("E");
	 * mnrlCbsEntity.setMnrlAtrResponse(a.getAdditionalinfo().getExcepMetaData());
	 * mnrlCbsEntity.setActionFlag("12"); LOGGER.info("ATR EMetadata [{}]",
	 * a.getAdditionalinfo().getExcepMetaData()); } else if
	 * (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
	 * mnrlCbsEntity.setAtrUploadFlag("E");
	 * mnrlCbsEntity.setMnrlAtrResponse(a.getAdditionalinfo().getExcepText());
	 * mnrlCbsEntity.setActionFlag("12"); LOGGER.info("ATR EText [{}]",
	 * a.getAdditionalinfo().getExcepText()); } else if
	 * (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
	 * mnrlCbsEntity.setAtrUploadFlag("E");
	 * mnrlCbsEntity.setMnrlAtrResponse(a.getAdditionalinfo().getExcepCode());
	 * mnrlCbsEntity.setActionFlag("12"); LOGGER.info("ATR Ecode [{}]",
	 * a.getAdditionalinfo().getExcepCode()); } } //
	 * mnrlCbsDataRepo.save(mnrlCbsEntity); }); });
	 * 
	 * Optional.ofNullable(atrResponse.getMNRLATRUpdateResponse()).ifPresent(m -> {
	 * Optional.ofNullable(m.getBody()).ifPresent(n -> {
	 * Optional.ofNullable(n.getPayload()).ifPresent(o -> {
	 * 
	 * if (StringUtils.isNotBlank(n.getPayload().getResponseCode()) &&
	 * n.getPayload().getResponseCode().equals("200")) { LOGGER.info("Success [{}]",
	 * key); // MnrlCbsData_DAO mnrlCbsEntity =
	 * mnrlCbsDataRepo.getAtrUploadData(key); mnrlCbsEntity.setAtrUploadFlag("C");
	 * mnrlCbsEntity.setActionFlag("1");
	 * mnrlCbsEntity.setMnrlAtrResponse(n.getPayload().getMessage()); //
	 * mnrlCbsDataRepo.save(mnrlCbsEntity); } else if
	 * (StringUtils.isNotBlank(n.getPayload().getResponseCode()) &&
	 * n.getPayload().getErrorIn() != null) { if (n.getPayload().getErrorIn().size()
	 * != 0) { Map<String, String> errorInMap = n.getPayload().getErrorIn(); if
	 * (errorInMap.containsKey(key)) { LOGGER.info("matched [{}],[{}]",
	 * errorInMap.get(key), key);
	 * 
	 * mnrlCbsEntity.setAtrUploadFlag("E");
	 * mnrlCbsEntity.setMnrlAtrResponse(errorInMap.get(key));
	 * 
	 * mnrlCbsEntity.setActionFlag("8");
	 * 
	 * } else { LOGGER.info("not matched [{}]", key);
	 * 
	 * mnrlCbsEntity.setAtrUploadFlag("C");
	 * mnrlCbsEntity.setMnrlAtrResponse("Success");
	 * mnrlCbsEntity.setActionFlag("1");
	 * 
	 * } } } else {
	 * 
	 * mnrlCbsEntity.setAtrUploadFlag("E");
	 * mnrlCbsEntity.setMnrlAtrResponse(n.getPayload().getMessage());
	 * mnrlCbsEntity.setActionFlag("8");
	 * 
	 * }
	 * 
	 * }); }); }); LOGGER.info("atrUploadFlag after response [{}]",
	 * mnrlCbsEntity.getAtrUploadFlag()); mnrlCbsDataRepo.save(mnrlCbsEntity); }); }
	 */
	
	public void updateAtrResponse(MnrlAtrResponseV2 atrResponse,List<MnrlCbsData_DAO> cbslist) {
		LocalDateTime date=LocalDateTime.now();
		for(int i=0;i<cbslist.size();i++) {
			String key=commonMethodUtils.getMobileNumberWithoutCC(cbslist.get(i).getMobileNo());
			LOGGER.info("Mobile No [{}]", key);
			MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo
					.getAtrUploadData(commonMethodUtils.getMobileNumberWithCC(key),cbslist.get(i).getUuid());
			mnrlCbsEntity.setAtrDateTime(date);
			if (!(Optional.ofNullable(atrResponse.getMNRLATRUpdateResponse()).isPresent()
					||Optional.ofNullable(atrResponse.getErrorResponse()).isPresent())) {
				// LOGGER.info("ATR Retry");

				mnrlCbsEntity.setAtrUploadFlag("R");
				// mnrlCbsEntity.setActionFlag("77");
				// mnrlCbsDataRepo.save(mnrlCbsEntity);
			}

			Optional.ofNullable(atrResponse.getErrorResponse()).ifPresent(a -> {
				Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
					// MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo.getAtrUploadData(key);
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
							&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
						LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
						mnrlCbsEntity.setAtrUploadFlag("R");
						// mnrlCbsEntity.setActionFlag("77");
					} else {
						if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
							mnrlCbsEntity.setAtrUploadFlag("E");
							mnrlCbsEntity.setMnrlAtrResponse(a.getAdditionalinfo().getExcepMetaData());
							mnrlCbsEntity.setActionFlag("12");
							LOGGER.info("ATR EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
							mnrlCbsEntity.setAtrUploadFlag("E");
							mnrlCbsEntity.setMnrlAtrResponse(a.getAdditionalinfo().getExcepText());
							mnrlCbsEntity.setActionFlag("12");
							LOGGER.info("ATR EText [{}]", a.getAdditionalinfo().getExcepText());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
							mnrlCbsEntity.setAtrUploadFlag("E");
							mnrlCbsEntity.setMnrlAtrResponse(a.getAdditionalinfo().getExcepCode());
							mnrlCbsEntity.setActionFlag("12");
							LOGGER.info("ATR Ecode [{}]", a.getAdditionalinfo().getExcepCode());
						}
					}
					// mnrlCbsDataRepo.save(mnrlCbsEntity);
				});
			});

			Optional.ofNullable(atrResponse.getMNRLATRUpdateResponse()).ifPresent(m -> {
				Optional.ofNullable(m.getBody()).ifPresent(n -> {
					Optional.ofNullable(n.getPayload()).ifPresent(o -> {

						if (StringUtils.isNotBlank(n.getPayload().getResponseCode())
								&& n.getPayload().getResponseCode().equals("200")) {
							LOGGER.info("Success [{}]", key);
							// MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo.getAtrUploadData(key);
							mnrlCbsEntity.setAtrUploadFlag("C");
							mnrlCbsEntity.setActionFlag("1");
							mnrlCbsEntity.setMnrlAtrResponse(n.getPayload().getMessage());
							// mnrlCbsDataRepo.save(mnrlCbsEntity);
						} else if (StringUtils.isNotBlank(n.getPayload().getResponseCode())
								&& n.getPayload().getErrorIn() != null) {
							if (n.getPayload().getErrorIn().size() != 0) {
								Map<String, String> errorInMap = n.getPayload().getErrorIn();
								if (errorInMap.containsKey(key)) {
									LOGGER.info("matched [{}],[{}]", errorInMap.get(key), key);

									mnrlCbsEntity.setAtrUploadFlag("E");
									mnrlCbsEntity.setMnrlAtrResponse(errorInMap.get(key));

									mnrlCbsEntity.setActionFlag("8");

								} else {
									LOGGER.info("not matched [{}]", key);

									mnrlCbsEntity.setAtrUploadFlag("C");
									mnrlCbsEntity.setMnrlAtrResponse("Success");
									mnrlCbsEntity.setActionFlag("1");

								}
							}
						} else {

							mnrlCbsEntity.setAtrUploadFlag("E");
							mnrlCbsEntity.setMnrlAtrResponse(n.getPayload().getMessage());
							mnrlCbsEntity.setActionFlag("8");

						}

					});
				});
			});
			LOGGER.info("atrUploadFlag after response [{}]", mnrlCbsEntity.getAtrUploadFlag());
			mnrlCbsDataRepo.save(mnrlCbsEntity);
		}
	}

	
}

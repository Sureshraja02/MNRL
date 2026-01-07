package com.fisglobal.fsg.dip.fri.service;

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

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;
import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.repo.FriCbsDataRepo;
import com.fisglobal.fsg.dip.fri.entity.FRIAtrBody;
import com.fisglobal.fsg.dip.fri.entity.FRIAtrPayload;
import com.fisglobal.fsg.dip.fri.entity.FRIAtrRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIAtrResponse;
import com.fisglobal.fsg.dip.fri.entity.FRIAtrUpdateRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIHeaders;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAtrResponseV2;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrData_VO;
import com.fisglobal.fsg.dip.request.entity.MnrlAtrUpdate_Vo;
import com.fisglobal.fsg.dip.response.entity.MnrlAtrResponse;
import com.fisglobal.fsg.dip.service.MnrlService;

@Service
public class FriAtrService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriAtrService.class);

	@Inject
	@Qualifier("FRI_ATR_REQ")
	private MessageSource friReq;

	@Inject
	private FriAuthService friAuthService;

	@Inject
	private MnrlService mnrlService;

	@Inject
	private ServiceHandlerCbs serviceHandler;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	private CryptoApp cryptoApp;

	@Inject
	private FriCbsDataRepo cbsDataRepo;

	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;

	public void processAtrData(List<MnrlAtrData_VO> atrdatalist,List<FriCbsData_DAO> cbsdatlist) throws Exception {
		// List<MnrlAtrData_VO> atrdatalist = new ArrayList<>();
		FRIAtrResponse atrResponse = null;
		/*
		 * Map<String, String> atrMobile = new HashMap<String, String>();
		 * 
		 * for (int i = 0; i < atrdatalist.size(); i++) {
		 * atrMobile.put(atrdatalist.get(i).getMobile_no(),
		 * atrdatalist.get(i).getMobile_no()); }
		 */

		MnrlAtrUpdate_Vo atrVo = new MnrlAtrUpdate_Vo();
		atrVo.setClient_id(MnrlLoadData.friTokenRequestDetails.getBankId());
		atrVo.setAtr_data(atrdatalist);
		LOGGER.info("Plain List size of ATR {}", atrdatalist.size());

		System.out.println("Atr Request" + atrVo.getBank_id());

		String atrRequest = MnrlLoadData.gson.toJson(atrVo);
		String token = friAuthService.getFriAuthtoken(MnrlLoadData.friTokenRequestDetails.getEmail(),
				MnrlLoadData.friTokenRequestDetails.getSecureterm());
		if (StringUtils.isBlank(token)) {
			LOGGER.info("Token is null");
			atrResponse = new FRIAtrResponse();
			updateAtrResponse(atrResponse, cbsdatlist);
		}
		if (StringUtils.isNotBlank(token)) {
			LOGGER.info("Atr plain data [{}]", atrRequest);
			String atrEncryptedReq = cryptoApp.encryptData(atrRequest);
			// System.out.println(gson.toJson(atrEnc));
			String encAtrData = MnrlLoadData.gson.toJson(setFriAtrUpdateRequest(token, atrEncryptedReq));

			LOGGER.debug("Atr plain data [{}]", encAtrData);

			String response = sendRequest(encAtrData);
			try {
				atrResponse = MnrlLoadData.gson.fromJson(response, FRIAtrResponse.class);
				updateAtrResponse(atrResponse, cbsdatlist);
			} catch (Exception e) {
				LOGGER.error("Exception in FRI ATR", e);
				atrResponse = null;
				updateAtrResponse(atrResponse, cbsdatlist);
			}
		}

	}

	public FRIAtrRequest setFriAtrUpdateRequest(String token, String atrEncData) {
		FRIAtrUpdateRequest fRIAtrUpdateRequest = new FRIAtrUpdateRequest();
		FRIAtrBody body = new FRIAtrBody();
		FRIAtrPayload payload = new FRIAtrPayload();
		payload = MnrlLoadData.gson.fromJson(atrEncData, FRIAtrPayload.class);
		body.setPayload(payload);
		FRIHeaders headers = new FRIHeaders();
		headers.setAuthorization(token);

		fRIAtrUpdateRequest.setBody(body);
		fRIAtrUpdateRequest.setHeaders(headers);

		FRIAtrRequest atrRequest = new FRIAtrRequest();
		atrRequest.setFRIAtrUpdateRequest(fRIAtrUpdateRequest);

		return atrRequest;

	}

	public String sendRequest(String request) throws KeyManagementException, ClientProtocolException, JSONException,
			NoSuchAlgorithmException, KeyStoreException, IOException {
		LOGGER.debug("MNRL ATR request [{}]", request);

		// int headerLength = header.length;
		// header[headerLength - 1] = new BasicHeader("Authorization", token);

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getFriAtrUrl(),
				MnrlLoadData.propdetails.getAppProperty().getFriAtrApiHeader());
		LOGGER.info("Atr Response [{}]", response);
		return response;
	}

	/*
	 * public void updateAtrResponse(FRIAtrResponse atrResponse, Map<String, String>
	 * atr) { atr.forEach((key, value) -> { LOGGER.info("Mobile No [{}]", key);
	 * FriCbsData_DAO friCbsEntity =
	 * cbsDataRepo.getAtrUploadData(commonMethodUtils.getMobileNumberWithCC(key),
	 * null); if
	 * (!(Optional.ofNullable(atrResponse.getErrorResponse()).isPresent())) { //
	 * LOGGER.info("ATR Retry");
	 * 
	 * friCbsEntity.setAtrUploadFlag("R"); // mnrlCbsEntity.setActionFlag("77"); //
	 * mnrlCbsDataRepo.save(mnrlCbsEntity); }
	 * 
	 * Optional.ofNullable(atrResponse.getErrorResponse()).ifPresent(a -> {
	 * Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> { //
	 * MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo.getAtrUploadData(key); if
	 * (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText()) &&
	 * a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
	 * LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
	 * friCbsEntity.setAtrUploadFlag("R"); // mnrlCbsEntity.setActionFlag("77"); }
	 * else { if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData()))
	 * { friCbsEntity.setAtrUploadFlag("E");
	 * friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepMetaData());
	 * friCbsEntity.setActionFlag("12"); LOGGER.info("ATR EMetadata [{}]",
	 * a.getAdditionalinfo().getExcepMetaData()); } else if
	 * (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
	 * friCbsEntity.setAtrUploadFlag("E");
	 * friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepText());
	 * friCbsEntity.setActionFlag("12"); LOGGER.info("ATR EText [{}]",
	 * a.getAdditionalinfo().getExcepText()); } else if
	 * (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
	 * friCbsEntity.setAtrUploadFlag("E");
	 * friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepCode());
	 * friCbsEntity.setActionFlag("12"); LOGGER.info("ATR Ecode [{}]",
	 * a.getAdditionalinfo().getExcepCode()); } } //
	 * mnrlCbsDataRepo.save(mnrlCbsEntity); }); });
	 * 
	 * Optional.ofNullable(atrResponse.getResponseCode()).ifPresent(m -> {
	 * 
	 * 
	 * if (StringUtils.isNotBlank(atrResponse.getResponseCode()) &&
	 * atrResponse.getResponseCode().equals("200")) { LOGGER.info("Success [{}]",
	 * key); // MnrlCbsData_DAO mnrlCbsEntity =
	 * mnrlCbsDataRepo.getAtrUploadData(key); friCbsEntity.setAtrUploadFlag("C");
	 * friCbsEntity.setActionFlag("1");
	 * friCbsEntity.setFriAtrResponse(atrResponse.getMessage()); //
	 * mnrlCbsDataRepo.save(mnrlCbsEntity); } else if
	 * (StringUtils.isNotBlank(atrResponse.getResponseCode()) &&
	 * atrResponse.getErrorIn() != null) { if (atrResponse.getErrorIn().size() != 0)
	 * { Map<String, String> errorInMap = atrResponse.getErrorIn(); if
	 * (errorInMap.containsKey(key)) { LOGGER.info("matched [{}],[{}]",
	 * errorInMap.get(key), key);
	 * 
	 * friCbsEntity.setAtrUploadFlag("E");
	 * friCbsEntity.setFriAtrResponse(errorInMap.get(key));
	 * friCbsEntity.setActionFlag("8");
	 * 
	 * } else { LOGGER.info("not matched [{}]", key);
	 * 
	 * friCbsEntity.setAtrUploadFlag("C");
	 * friCbsEntity.setFriAtrResponse("Success"); friCbsEntity.setActionFlag("1");
	 * 
	 * } } } else {
	 * 
	 * friCbsEntity.setAtrUploadFlag("E");
	 * friCbsEntity.setFriAtrResponse(atrResponse.getMessage());
	 * friCbsEntity.setActionFlag("8");
	 * 
	 * }
	 * 
	 * }); LOGGER.info("atrUploadFlag after response [{}]",
	 * friCbsEntity.getAtrUploadFlag()); cbsDataRepo.save(friCbsEntity); }); }
	 */
	
	public void updateAtrResponse(FRIAtrResponse atrResponse, List<FriCbsData_DAO> cbsdatalist) {
		LocalDateTime date=LocalDateTime.now();
		for(int i=0;i<cbsdatalist.size();i++) {
			String key=commonMethodUtils.getMobileNumberWithoutCC(cbsdatalist.get(i).getMobileNo());
			LOGGER.info("Mobile No [{}]", key);
			FriCbsData_DAO friCbsEntity = cbsDataRepo.getAtrUploadData(commonMethodUtils.getMobileNumberWithCC(key),cbsdatalist.get(i).getUuid());
			friCbsEntity.setAtrDateTime(date);
			if (!(Optional.ofNullable(atrResponse.getErrorResponse()).isPresent())) {
				// LOGGER.info("ATR Retry");

				friCbsEntity.setAtrUploadFlag("R");
				// mnrlCbsEntity.setActionFlag("77");
				// mnrlCbsDataRepo.save(mnrlCbsEntity);
			}

			Optional.ofNullable(atrResponse.getErrorResponse()).ifPresent(a -> {
				Optional.ofNullable(a.getAdditionalinfo()).ifPresent(b -> {
					// MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo.getAtrUploadData(key);
					if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())
							&& a.getAdditionalinfo().getExcepText().equals(invalidHeader)) {
						LOGGER.info("Error [{}]", a.getAdditionalinfo().getExcepText());
						friCbsEntity.setAtrUploadFlag("R");
						// mnrlCbsEntity.setActionFlag("77");
					} else {
						if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepMetaData())) {
							friCbsEntity.setAtrUploadFlag("E");
							friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepMetaData());
							friCbsEntity.setActionFlag("12");
							LOGGER.info("ATR EMetadata [{}]", a.getAdditionalinfo().getExcepMetaData());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepText())) {
							friCbsEntity.setAtrUploadFlag("E");
							friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepText());
							friCbsEntity.setActionFlag("12");
							LOGGER.info("ATR EText [{}]", a.getAdditionalinfo().getExcepText());
						} else if (StringUtils.isNotBlank(a.getAdditionalinfo().getExcepCode())) {
							friCbsEntity.setAtrUploadFlag("E");
							friCbsEntity.setFriAtrResponse(a.getAdditionalinfo().getExcepCode());
							friCbsEntity.setActionFlag("12");
							LOGGER.info("ATR Ecode [{}]", a.getAdditionalinfo().getExcepCode());
						}
					}
					// mnrlCbsDataRepo.save(mnrlCbsEntity);
				});
			});

			Optional.ofNullable(atrResponse.getResponseCode()).ifPresent(m -> {
				

						if (StringUtils.isNotBlank(atrResponse.getResponseCode())
								&& atrResponse.getResponseCode().equals("200")) {
							LOGGER.info("Success [{}]", key);
							// MnrlCbsData_DAO mnrlCbsEntity = mnrlCbsDataRepo.getAtrUploadData(key);
							friCbsEntity.setAtrUploadFlag("C");
							friCbsEntity.setActionFlag("1");
							friCbsEntity.setFriAtrResponse(atrResponse.getMessage());
							// mnrlCbsDataRepo.save(mnrlCbsEntity);
						} else if (StringUtils.isNotBlank(atrResponse.getResponseCode())
								&& atrResponse.getErrorIn() != null) {
							if (atrResponse.getErrorIn().size() != 0) {
								Map<String, String> errorInMap = atrResponse.getErrorIn();
								if (errorInMap.containsKey(key)) {
									LOGGER.info("matched [{}],[{}]", errorInMap.get(key), key);

									friCbsEntity.setAtrUploadFlag("E");
									friCbsEntity.setFriAtrResponse(errorInMap.get(key));
									friCbsEntity.setActionFlag("8");

								} else {
									LOGGER.info("not matched [{}]", key);

									friCbsEntity.setAtrUploadFlag("C");
									friCbsEntity.setFriAtrResponse("Success");
									friCbsEntity.setActionFlag("1");

								}
							}
						} else {

							friCbsEntity.setAtrUploadFlag("E");
							friCbsEntity.setFriAtrResponse(atrResponse.getMessage());
							friCbsEntity.setActionFlag("8");

						}

					});
			LOGGER.info("atrUploadFlag after response [{}]", friCbsEntity.getAtrUploadFlag());
			cbsDataRepo.save(friCbsEntity);
		}
	}

}

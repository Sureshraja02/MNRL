package com.fisglobal.fsg.dip.service.V2;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAuthBodyV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAuthPayloadV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAuthRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAuthRequest_VO;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlAuthResponseV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlTokenRequestV2;
import com.fisglobal.fsg.dip.response.entity.MnrlAuthResponse;
import com.fisglobal.fsg.dip.service.MnrlService;

@Service
public class MnrlAuthServiceV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlAuthServiceV2.class);

	@Inject
	ServiceHandlerEnq serviceHandler;

	@Inject
	private MnrlService mrnlService;

	@Inject
	private CryptoApp cryptoApp;

	// ObjectMapper objectMapper = new ObjectMapper();

	public String getMnrlAuthtoken(String email, String keyword) throws Exception {

		MnrlAuthResponseV2 mnrlAuthResponse = new MnrlAuthResponseV2();

		String request = setMnrlRequest(email, keyword);

		LOGGER.debug("MNRL auth request [{}][{}][{}]", request,email,keyword);

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlAuthURL(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlAuthAPIHeader());
		LOGGER.info("Response [{}]", response);
		if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			mnrlAuthResponse = MnrlLoadData.gson.fromJson(response, MnrlAuthResponseV2.class);
			LOGGER.info("MNRL auth token  [{}]", mnrlAuthResponse.getMNRLTokenResponse().getBody().getPayload().getToken());
			return mnrlAuthResponse.getMNRLTokenResponse().getBody().getPayload().getToken();
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}

	}

	public String setMnrlRequest(String email, String keyword) throws Exception {

		MnrlAuthRequest_VO mnrlAuthRequest_VO = new MnrlAuthRequest_VO();

		mnrlAuthRequest_VO.setEmail(email);
		mnrlAuthRequest_VO.setPassword(mrnlService.decodePassword(keyword));
		
		

		String payload = MnrlLoadData.gson.toJson(mnrlAuthRequest_VO);
		
		LOGGER.info("plain auth data [{}]", payload);

		String authEncryptedReq = cryptoApp.encryptMnrlData(payload,"AUTHV2",null);

		LOGGER.info("Encrypted auth data [{}]", authEncryptedReq);

		return authEncryptedReq;

	}

	public String setMnrlAuthRequestV2(String encryptedKey, String encryptedData, String authTag, String nonce)
			throws Exception {

		MnrlAuthRequestV2 mnrlAuthRequestV2=new MnrlAuthRequestV2();
		MnrlTokenRequestV2 mnrlTokenRequestV2=new MnrlTokenRequestV2();
		MnrlAuthBodyV2 body=new MnrlAuthBodyV2();
		MnrlAuthPayloadV2 payload=new MnrlAuthPayloadV2();
		payload.setAuthTag(authTag);
		payload.setEncryptedData(encryptedData);
		payload.setEncryptedKey(encryptedKey);
		payload.setNonce(nonce);
		
		body.setPayload(payload);
		
		mnrlTokenRequestV2.setBody(body);
		
		mnrlAuthRequestV2.setMNRLTokenRequest(mnrlTokenRequestV2);
		
		

		String request = MnrlLoadData.gson.toJson(mnrlAuthRequestV2);

		

		LOGGER.info("Encrypted auth data [{}]", request);

		return request;

	}
}

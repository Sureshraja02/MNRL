package com.fisglobal.fsg.dip.fri.service;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.convertor.CryptoApp;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.fri.entity.FRIAuthResponse;
import com.fisglobal.fsg.dip.fri.entity.FriAuthRequest_VO;
import com.fisglobal.fsg.dip.service.MnrlService;

@Service
public class FriAuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriAuthService.class);

	@Inject
	ServiceHandlerEnq serviceHandler;

	@Inject
	private MnrlService mrnlService;

	@Inject
	private CryptoApp cryptoApp;

	public String getFriAuthtoken(String email, String keyword) throws Exception {

		FRIAuthResponse friAuthResponse = new FRIAuthResponse();

		// FriAuthRequest authrequest=setFRIRequest(email,keyword);

		String request = setFRIRequest(email, keyword);

		LOGGER.debug("FRI auth request [{}]", request);

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getFriauthURL(),
				MnrlLoadData.propdetails.getAppProperty().getFriauthHeader());
		LOGGER.info("Response [{}]", response);
		if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			friAuthResponse = MnrlLoadData.gson.fromJson(response, FRIAuthResponse.class);
			LOGGER.info("FRI auth token  [{}]", friAuthResponse.getToken());
			return friAuthResponse.getToken();
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}

	}

	public String setFRIRequest(String email, String keyword) throws Exception {

		FriAuthRequest_VO friAuthRequest_VO = new FriAuthRequest_VO();

		friAuthRequest_VO.setEmail(email);
		friAuthRequest_VO.setPassword(mrnlService.decodePassword(keyword));

		String payload = MnrlLoadData.gson.toJson(friAuthRequest_VO);

		String authEncryptedReq = cryptoApp.encryptData(payload);

		LOGGER.info("Encrypted auth data [{}]", authEncryptedReq);

		return authEncryptedReq;

	}

}

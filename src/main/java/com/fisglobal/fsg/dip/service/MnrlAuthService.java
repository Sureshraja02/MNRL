package com.fisglobal.fsg.dip.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.request.entity.MnrlAuthRequest;
import com.fisglobal.fsg.dip.response.entity.MnrlAuthResponse;
import com.fisglobal.fsg.dip.service.headers.HeaderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MnrlAuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlAuthService.class);

	

	@Inject
	ServiceHandlerEnq serviceHandler;

	

	// ObjectMapper objectMapper = new ObjectMapper();
	

	


	public String getMnrlAuthtoken(MnrlAuthRequest mnrlAuthRequest) throws ClientProtocolException, JSONException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException 
			 {

		MnrlAuthResponse mnrlAuthResponse = new MnrlAuthResponse();
		
		String request = MnrlLoadData.gson.toJson(mnrlAuthRequest);

		LOGGER.debug("MNRL auth request [{}]", request);

		

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlAuthURL(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlAuthAPIHeader());
		LOGGER.info("Response [{}]", response);
		if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			mnrlAuthResponse = MnrlLoadData.gson.fromJson(response, MnrlAuthResponse.class);
			LOGGER.info("MNRL auth token  [{}]", mnrlAuthResponse.getToken());
			return mnrlAuthResponse.getToken();
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}

	

}
}

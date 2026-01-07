package com.fisglobal.fsg.dip.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.RestClient;
import com.fisglobal.fsg.dip.core.service.RestClientHttps;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.core.utils.Connection_VO;
import com.fisglobal.fsg.dip.request.entity.MnrlDataRequest;
import com.fisglobal.fsg.dip.response.entity.MnrlDataResponse;
import com.fisglobal.fsg.dip.service.headers.HeaderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MnrlDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlDataService.class);

	Connection_VO connHttp;
	RestClient restClient;
	RestClientHttps restClientHttps;

	@Inject
	ServiceHandlerEnq serviceHandler;

	// ObjectMapper objectMapper = new ObjectMapper();
	

	
	

	public String getMnrlData(MnrlDataRequest mnrlDataRequest) throws ClientProtocolException, JSONException,
			IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		//MnrlDataResponse mnrlDataResponse = new MnrlDataResponse();
		String request = MnrlLoadData.gson.toJson(mnrlDataRequest);

		LOGGER.debug("MNRL Data request [{}]", request);

		

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlDataURL(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlDataAPIHeader());
		LOGGER.debug("Response [{}]", response);
		return response;
		/*if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			mnrlDataResponse = gson.fromJson(response, MnrlDataResponse.class);
			LOGGER.debug("MNRL Data response  [{}]", mnrlDataResponse);
			return mnrlDataResponse;
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}*/

	}

}

package com.fisglobal.fsg.dip.service.V2;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlDataRequestV2;

@Service
public class MnrlDataServiceV2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlDataServiceV2.class);

	

	@Inject
	ServiceHandlerEnq serviceHandler;

	// ObjectMapper objectMapper = new ObjectMapper();
	

	
	

	public String getMnrlData(MnrlDataRequestV2 mnrlDataRequest) throws ClientProtocolException, JSONException,
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

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
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.request.entity.MnrlReactivatedDataRequest;
import com.fisglobal.fsg.dip.response.entity.MnrlDataResponse;
import com.fisglobal.fsg.dip.service.headers.HeaderService;

@Service
public class MnrlReactivatedDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlReactivatedDataService.class);

	@Inject
	ServiceHandlerEnq serviceHandler;


	

	public String getMnrlReactivatedData(MnrlReactivatedDataRequest mnrlReactivatedDataRequest) throws ClientProtocolException, JSONException,
			IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		//MnrlDataResponse mnrlDataResponse = new MnrlDataResponse();
		String request = MnrlLoadData.gson.toJson(mnrlReactivatedDataRequest);

		LOGGER.debug("MNRL Reactivated Data request [{}]", request);

		

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedDataURL(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedDataAPIHeader());
		LOGGER.debug("Response [{}]", response);
		return response;
		/*
		 * if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
		 * mnrlDataResponse = gson.fromJson(response, MnrlDataResponse.class);
		 * LOGGER.debug("MNRL Data response  [{}]", mnrlDataResponse); return
		 * mnrlDataResponse; } else { String errorarray[] = response.split("!_!");
		 * LOGGER.info("Response Failure with status code [{}] Response [{}]",
		 * errorarray[0], errorarray[1]); return null; }
		 */

	}

}

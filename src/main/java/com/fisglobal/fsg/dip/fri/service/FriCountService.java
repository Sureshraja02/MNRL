package com.fisglobal.fsg.dip.fri.service;

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
import com.fisglobal.fsg.dip.fri.entity.FRICountBody;
import com.fisglobal.fsg.dip.fri.entity.FRICountPayload;
import com.fisglobal.fsg.dip.fri.entity.FRICountRequest;
import com.fisglobal.fsg.dip.fri.entity.FRICountResponse;
import com.fisglobal.fsg.dip.fri.entity.FRIFetchCountRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIHeaders;
import com.fisglobal.fsg.dip.service.MnrlService;
import com.fisglobal.fsg.dip.service.headers.HeaderService;

@Service
public class FriCountService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FriCountService.class);

	@Inject
	ServiceHandlerEnq serviceHandler;

	// ObjectMapper objectMapper = new ObjectMapper();

	

	

	@Inject
	private MnrlService mrnlService;

	public FRICountResponse getFriCount(FRICountRequest mnrlCountRequest) throws ClientProtocolException, JSONException,
			IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		FRICountResponse friCountResponse = new FRICountResponse();
		String request = MnrlLoadData.gson.toJson(mnrlCountRequest);

		LOGGER.debug("FRI count request [{}]", request);

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getFriCountURL(),
				MnrlLoadData.propdetails.getAppProperty().getFriCountAPIHeader());
		LOGGER.info("Response [{}]", response);
		if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			friCountResponse = MnrlLoadData.gson.fromJson(response, FRICountResponse.class);
			LOGGER.info("FRI count response  [{}]", friCountResponse);
			return friCountResponse;
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}

	}

	public FRICountRequest setFriCountRequest(String token,String fetchDate) {

		FRICountRequest friCountRequest = new FRICountRequest();

		FRICountPayload payload = new FRICountPayload();
		/*if ("Y".equals(MnrlLoadData.propdetails.getAppProperty().getFriCountDateFlag())) {
			payload.setDate(MnrlLoadData.propdetails.getAppProperty().getFriCountDate());
		} else {
			payload.setDate(mrnlService.getCurrentDateAsString());
		}*/
		payload.setDate(fetchDate);
		FRICountBody body = new FRICountBody();
		body.setPayload(payload);

		FRIHeaders header = new FRIHeaders();
		header.setAuthorization(token);

		FRIFetchCountRequest request = new FRIFetchCountRequest();
		request.setBody(body);
		request.setHeaders(header);

		friCountRequest.setFRIFetchCountRequest(request);

		LOGGER.info("MNRL count request [{}]", MnrlLoadData.gson.toJson(friCountRequest));

		return friCountRequest;

	}

}

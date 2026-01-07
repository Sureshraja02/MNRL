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
import com.fisglobal.fsg.dip.request.entity.MNRLCountBody;
import com.fisglobal.fsg.dip.request.entity.MNRLCountPayload;
import com.fisglobal.fsg.dip.request.entity.MNRLFetchCountRequest;
import com.fisglobal.fsg.dip.request.entity.MNRLHeaders;
import com.fisglobal.fsg.dip.request.entity.MnrlCountRequest;
import com.fisglobal.fsg.dip.response.entity.MnrlCountResponse;
import com.fisglobal.fsg.dip.service.headers.HeaderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class MnrlCountService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlCountService.class);

	

	@Inject
	ServiceHandlerEnq serviceHandler;
	

	

	//ObjectMapper objectMapper = new ObjectMapper();
	
	


	
	@Inject
	private MnrlService mrnlService;


	public MnrlCountResponse getMnrlCount(MnrlCountRequest mnrlCountRequest)
			throws ClientProtocolException, JSONException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		
		
		MnrlCountResponse mnrlCountResponse = new MnrlCountResponse();
		String request = MnrlLoadData.gson.toJson(mnrlCountRequest);
		
		LOGGER.debug("MNRL count request [{}]" , request);
		
		
		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlCountURL(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlCountAPIHeader());
		LOGGER.info("Response [{}]",response);
		if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			mnrlCountResponse = MnrlLoadData.gson.fromJson(response, MnrlCountResponse.class);
			LOGGER.info("MNRL count response  [{}]" , mnrlCountResponse);
			return mnrlCountResponse;
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}

	}
	
	public MnrlCountRequest setMnrlCountRequest(String token) {
		
		MnrlCountRequest mrnlCountRequest=new MnrlCountRequest();
		
		MNRLCountPayload payload=new MNRLCountPayload();
		if ("Y".equals(MnrlLoadData.propdetails.getAppProperty().getMnrlCountDateFlag())) {
			payload.setDate(MnrlLoadData.propdetails.getAppProperty().getMnrlCountDate());
		} else {
			payload.setDate(mrnlService.getCurrentDateAsString());
		}
		
		MNRLCountBody body=new MNRLCountBody();
		body.setPayload(payload);
		
		MNRLHeaders header=new MNRLHeaders();
		header.setAuthorization(token);
		
		MNRLFetchCountRequest request = new MNRLFetchCountRequest();
		request.setBody(body);
		request.setHeaders(header);
		
		mrnlCountRequest.setMNRLFetchCount_Request(request);
		
		LOGGER.info("MNRL count request [{}]",MnrlLoadData.gson.toJson(mrnlCountRequest));
		
		return mrnlCountRequest;
		
	}

}

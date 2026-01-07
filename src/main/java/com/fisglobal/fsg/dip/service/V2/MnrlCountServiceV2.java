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
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLCountBodyV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLCountPayloadV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLFetchCountRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MNRLHeadersV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlCountRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlCountResponseV2;
import com.fisglobal.fsg.dip.service.MnrlService;

@Service
public class MnrlCountServiceV2 {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlCountServiceV2.class);

	

	@Inject
	ServiceHandlerEnq serviceHandler;
	
	@Inject
	private MnrlService mrnlService;


	public MnrlCountResponseV2 getMnrlCount(MnrlCountRequestV2 mnrlCountRequest)
			throws ClientProtocolException, JSONException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		
		
		MnrlCountResponseV2 mnrlCountResponse = new MnrlCountResponseV2();
		String request = MnrlLoadData.gson.toJson(mnrlCountRequest);
		
		LOGGER.debug("MNRL count request [{}]" , request);
		
		
		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlCountURL(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlCountAPIHeader());
		LOGGER.info("Response [{}]",response);
		if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			mnrlCountResponse = MnrlLoadData.gson.fromJson(response, MnrlCountResponseV2.class);
			LOGGER.info("MNRL count response  [{}]" , mnrlCountResponse);
			return mnrlCountResponse;
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}

	}
	
	public MnrlCountRequestV2 setMnrlCountRequest(String token,String fetchDate) {
		
		MnrlCountRequestV2 mrnlCountRequest=new MnrlCountRequestV2();
		
		MNRLCountPayloadV2 payload=new MNRLCountPayloadV2();
		/*if ("Y".equals(MnrlLoadData.propdetails.getAppProperty().getMnrlCountDateFlag())) {
			payload.setDate(MnrlLoadData.propdetails.getAppProperty().getMnrlCountDate());
		} else {
			payload.setDate(mrnlService.getCurrentDateAsString());
		}*/
		payload.setDate(fetchDate);
		MNRLCountBodyV2 body=new MNRLCountBodyV2();
		body.setPayload(payload);
		
		MNRLHeadersV2 header=new MNRLHeadersV2();
		header.setAuthorization(token);
		
		MNRLFetchCountRequestV2 request = new MNRLFetchCountRequestV2();
		request.setBody(body);
		request.setHeaders(header);
		
		mrnlCountRequest.setMNRLFetchCount_Request(request);
		
		LOGGER.info("MNRL count request [{}]",MnrlLoadData.gson.toJson(mrnlCountRequest));
		
		return mrnlCountRequest;
		
	}

}

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
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlReactivatedCountRequestV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlReactivatedCountResponseV2;
import com.fisglobal.fsg.dip.service.MnrlService;

@Service
public class MnrlReactivatedCountServiceV2 {
	
private static final Logger LOGGER = LoggerFactory.getLogger(MnrlReactivatedCountServiceV2.class);

	

	@Inject
	ServiceHandlerEnq serviceHandler;
	

	
	@Inject
	private MnrlService mrnlService;


	public MnrlReactivatedCountResponseV2 getMnrlReactivatedCount(MnrlReactivatedCountRequestV2 mnrlReactivatedCountRequest)
			throws ClientProtocolException, JSONException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		
		
		MnrlReactivatedCountResponseV2 mnrlCountResponse = new MnrlReactivatedCountResponseV2();
		String request = MnrlLoadData.gson.toJson(mnrlReactivatedCountRequest);
		
		LOGGER.debug("MNRL Reactivated count request [{}]" , request);
		
		
		
		String response = serviceHandler.sendRequest(request,   
				MnrlLoadData.propdetails.getRestClient(), MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedCountURL(),
				MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedCountAPIHeader());
		LOGGER.info("Response [{}]",response);
		if (!(StringUtils.isNotBlank(response) && response.contains("!_!"))) {
			mnrlCountResponse = MnrlLoadData.gson.fromJson(response, MnrlReactivatedCountResponseV2.class);
			LOGGER.info("MNRL Reactivated count response  [{}]" , mnrlCountResponse);
			return mnrlCountResponse;
		} else {
			String errorarray[] = response.split("!_!");
			LOGGER.info("Response Failure with status code [{}] Response [{}]", errorarray[0], errorarray[1]);
			return null;
		}

	}
	
	public MnrlReactivatedCountRequestV2 setMnrlReactivatedCountRequest(String token,String fetchDate) {
		
		MnrlReactivatedCountRequestV2 mrnlReactivatedCountRequest=new MnrlReactivatedCountRequestV2();
		
		MNRLCountPayloadV2 payload=new MNRLCountPayloadV2();
		/*if ("Y".equals(MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedCountDateFlag())) {
			payload.setDate(MnrlLoadData.propdetails.getAppProperty().getMnrlReactivatedCountDate());
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
		
		mrnlReactivatedCountRequest.setMNRLReactiveNumRequest(request);
		
		LOGGER.info("MNRL Reactiavted count request [{}]",MnrlLoadData.gson.toJson(mrnlReactivatedCountRequest));
		
		return mrnlReactivatedCountRequest;
		
	}


}

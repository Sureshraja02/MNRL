package com.fisglobal.fsg.dip.core.cbs.service;

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

import com.fisglobal.fsg.dip.core.cbs.entity.CIFBlockRequestVo;
import com.fisglobal.fsg.dip.core.cbs.entity.CIFBlockResponseVo;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;

@Service
public class CIFBlockService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CIFBlockService.class);
	@Inject
	ServiceHandlerCbs serviceHandler;
	

	public CIFBlockResponseVo processCifBlock(String mobileNumber,String indicator) throws KeyManagementException, ClientProtocolException, JSONException, NoSuchAlgorithmException, KeyStoreException, IOException  {

		CIFBlockRequestVo cifBlockRequest = new CIFBlockRequestVo();
		//ErrorVo errorResponse = new ErrorVo();

		CIFBlockResponseVo cifBlockResponse = new CIFBlockResponseVo();
		cifBlockRequest.setMobileNumber(mobileNumber);
		String request = MnrlLoadData.gson.toJson(cifBlockRequest);
		LOGGER.info("CIF Block Request [{}]", request);
		

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getCbsCifBlockUrl(),
				indicator+MnrlLoadData.propdetails.getAppProperty().getCbsCifBlockHeader());
		LOGGER.info("Response [{}]", response);
	/*	if (!response.contains("ErrorResponse")) {

			cifBlockResponse = gson.fromJson(response, CIFBlockResponseVo.class);
			LOGGER.info("CIF Block Response [{}]", cifBlockResponse);
		} else {
			errorResponse = gson.fromJson(response, ErrorVo.class);
			LOGGER.info("CIF Block Error Response [{}]", errorResponse);
			cifBlockResponse=null;
		}*/
		try {
		
			cifBlockResponse = MnrlLoadData.gson.fromJson(response, CIFBlockResponseVo.class);
			LOGGER.info("CIF Block Response [{}]", cifBlockResponse);
		}catch(Exception e) {
			LOGGER.error("Exception in CIF block",e);
			cifBlockResponse=null;
		}

		return cifBlockResponse;

	}

}

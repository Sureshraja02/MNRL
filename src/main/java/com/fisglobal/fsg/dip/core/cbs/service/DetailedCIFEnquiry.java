package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.DetailedCIFEnquiryRequestVo;
import com.fisglobal.fsg.dip.core.cbs.entity.DetailedCIFEnquiryResponseVo;
import com.fisglobal.fsg.dip.core.cbs.entity.ErrorVo;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;

@Service
public class DetailedCIFEnquiry {
	private static final Logger LOGGER = LoggerFactory.getLogger(DetailedCIFEnquiry.class);
	@Inject
	ServiceHandlerCbs serviceHandler;

	public DetailedCIFEnquiryResponseVo getCifDetails(BigInteger cifNumber, String indicator)
			throws KeyManagementException, ClientProtocolException, JSONException, NoSuchAlgorithmException,
			KeyStoreException, IOException {

		DetailedCIFEnquiryRequestVo cifRequest = new DetailedCIFEnquiryRequestVo();
		//ErrorVo errorResponse = new ErrorVo();

		DetailedCIFEnquiryResponseVo cifResponse = new DetailedCIFEnquiryResponseVo();
		cifRequest.setcIFNumber(cifNumber);
		String request = MnrlLoadData.gson.toJson(cifRequest);
		LOGGER.info("CIF Request [{}]", request);

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getCbsCifEnqUrl(),
				indicator + MnrlLoadData.propdetails.getAppProperty().getCbsCifEnqHeader());
		LOGGER.info("Response [{}]", response);
		/*if (!response.contains("ErrorResponse")) {

			cifResponse = MnrlLoadData.gson.fromJson(response, DetailedCIFEnquiryResponseVo.class);
			LOGGER.info("CIF Response [{}]", cifResponse);
		} else {
			errorResponse = MnrlLoadData.gson.fromJson(response, ErrorVo.class);
			LOGGER.info("CIF Error Response [{}]", errorResponse);
			cifResponse = null;
		}*/
		
		try {
			cifResponse = MnrlLoadData.gson.fromJson(response, DetailedCIFEnquiryResponseVo.class);
			LOGGER.info("CIF Response [{}]", cifResponse);
		}catch(Exception e) {
			LOGGER.error("Exception in CIF enquiry [{}]", e);
			cifResponse = null;
		}

		return cifResponse;

	}
}

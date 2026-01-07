package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisglobal.fsg.dip.core.cbs.entity.Body;
import com.fisglobal.fsg.dip.core.cbs.entity.CIFAccountEnqRequest;
import com.fisglobal.fsg.dip.core.cbs.entity.CIFBlockResponseVo;
import com.fisglobal.fsg.dip.core.cbs.entity.ErrorVo;
import com.fisglobal.fsg.dip.core.cbs.entity.MobileEnquiryRequestVo;
import com.fisglobal.fsg.dip.core.cbs.entity.MobileEnquiryResponseVo;
import com.fisglobal.fsg.dip.core.cbs.entity.Payload;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;

@Service
public class MobileNumberEnquiryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MobileNumberEnquiryService.class);

	@Inject
	ServiceHandlerCbs serviceHandler;

	@Value("${error.invalid.header:Invalid Header}")
	private String invalidHeader;

	@Inject
	private CommonMethodUtils commonMethodUtils;

	public MobileEnquiryResponseVo getCIFAccountDetails(String mobilenumber, String countryCode, String indicator)
			throws KeyManagementException, ClientProtocolException, JSONException, NoSuchAlgorithmException,
			KeyStoreException, IOException {
		MobileEnquiryResponseVo mobileEnquiryResponseVo = null;
		//ErrorVo errorResponse = new ErrorVo();

		MobileEnquiryRequestVo mobileEnquiryRequest = new MobileEnquiryRequestVo();
		long beforeRequest = System.currentTimeMillis();
		CIFAccountEnqRequest cifAccReq = setMobileNumberEnqRequest(mobilenumber, countryCode);
		long afterRequest = System.currentTimeMillis();
		long elapsedRequest = afterRequest - beforeRequest;
		LOGGER.info("Mob request set elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsedRequest));
		mobileEnquiryRequest.setCIFAccountEnqRequest(cifAccReq);
		String request = MnrlLoadData.gson.toJson(mobileEnquiryRequest);

		LOGGER.info("Mobile enquiry Request [{}]", request);

		long beforeParams = System.currentTimeMillis();

		// RestClient mnrlrestTemplate = ;
		String url = MnrlLoadData.propdetails.getAppProperty().getCbsMobileEnqUrl();
		String indicatorMW = indicator + MnrlLoadData.propdetails.getAppProperty().getCbsMobileEnqHeader();
		long afterParams = System.currentTimeMillis();
		long elapsedParams = afterParams - beforeParams;
		LOGGER.info("Params elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsedParams));

		long beforeHandlerCall = System.currentTimeMillis();
		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(), url,
				indicatorMW);
		long afterHandlerCall = System.currentTimeMillis();
		long elapsedHandlerCall = afterHandlerCall - beforeHandlerCall;
		LOGGER.info("HandlerCall elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsedHandlerCall));

		LOGGER.info("Response [{}]", response);
		ObjectMapper objMapper = new ObjectMapper();
		JsonNode root = objMapper.readTree(response);

		JsonNode nameNode = commonMethodUtils.getByPath(root, "CIF_Account_Enq_Response.Body.Payload.Collection");
		String jsonType = commonMethodUtils.typeOf(nameNode);
		LOGGER.info("jsonType [{}]", jsonType);
		if (jsonType.equals("STRING")) {
			LOGGER.info("Mobile Enquiry Response [{}]", nameNode.textValue());
			mobileEnquiryResponseVo = new MobileEnquiryResponseVo();
			mobileEnquiryResponseVo.setErrorMessage(nameNode.textValue());
		} else {
			try {
				LOGGER.info("Before Mobile Enquiry Response ");
				mobileEnquiryResponseVo = MnrlLoadData.gson.fromJson(response, MobileEnquiryResponseVo.class);
				LOGGER.info("Mobile Enquiry Response [{}]", mobileEnquiryResponseVo);
			} catch (Exception e) {
				LOGGER.error("Exception in Mobile Enquiry", e);
				mobileEnquiryResponseVo = null;
			}
		}
		return mobileEnquiryResponseVo;
	}

	/*
	 * if (!response.contains("ErrorResponse")) { if
	 * (response.contains(MnrlLoadData.propdetails.getAppProperty().
	 * getMobileEnqNoDataError())) { LOGGER.info("No Data found");
	 * mobileEnquiryResponseVo.setFlag("Y"); // mobileEnquiryResponseVo = null; }
	 * else { mobileEnquiryResponseVo = MnrlLoadData.gson.fromJson(response,
	 * MobileEnquiryResponseVo.class); mobileEnquiryResponseVo.setFlag("N");
	 * LOGGER.info("Mobile enquiry Response [{}]", mobileEnquiryResponseVo); } }
	 * else { String[] a = response.split("!_!"); if (a.length == 0) { errorResponse
	 * = MnrlLoadData.gson.fromJson(response, ErrorVo.class); } else { errorResponse
	 * = MnrlLoadData.gson.fromJson(a[1], ErrorVo.class); }
	 * LOGGER.info("Mobile enquiry Error Response [{}]", errorResponse); if
	 * (errorResponse.getErrorResponse().getAdditionalinfo().getExcepText().equals(
	 * invalidHeader)) { mobileEnquiryResponseVo.setFlag("H"); return
	 * mobileEnquiryResponseVo; }
	 * 
	 * mobileEnquiryResponseVo = null; }
	 */

	public CIFAccountEnqRequest setMobileNumberEnqRequest(String mobilenumber, String countryCode) {

		CIFAccountEnqRequest cIFAccountEnqRequest = new CIFAccountEnqRequest();
		Body body = new Body();
		Payload payload = new Payload();

		payload.setMobileNo(mobilenumber);
		payload.setCountryCode(countryCode);
		body.setPayload(payload);
		cIFAccountEnqRequest.setBody(body);
		return cIFAccountEnqRequest;
	}

}

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

import com.fisglobal.fsg.dip.core.cbs.entity.MobileRemovalRequestVo;
import com.fisglobal.fsg.dip.core.cbs.entity.MobileRemovalResponseVo;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;

@Service
public class MobileNumberRemovalService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MobileNumberRemovalService.class);
	@Inject
	ServiceHandlerCbs serviceHandler;
	
	

	// ObjectMapper objectMapper = new ObjectMapper();
	

	
	
	//@Scheduled(fixedDelay = 20000)
	/*
	 * public void cbstrigger() throws KeyManagementException,
	 * ClientProtocolException, NoSuchAlgorithmException, KeyStoreException,
	 * JSONException, IOException { int mobilenumber=1234567890; String
	 * countryCode="91"; //getCIFAccountDetails(mobilenumber,countryCode);
	 * //processMobileRemoval(1234567,"91",1234567890,"D","testing"); }
	 */
	
	public MobileRemovalResponseVo processMobileRemoval(BigInteger customerNumber,String countryCode,String mobileNumber,String flag,String reason,String indicator) throws KeyManagementException, ClientProtocolException, JSONException, NoSuchAlgorithmException, KeyStoreException, IOException {
		MobileRemovalResponseVo mobileRemovalResponseVo =new MobileRemovalResponseVo();
		//ErrorVo errorResponse=new ErrorVo();
		
		MobileRemovalRequestVo mobileRemovalRequest=new MobileRemovalRequestVo();
		
		mobileRemovalRequest=setMobileNumberRemovalRequest(customerNumber, countryCode, mobileNumber, flag, reason);
		
		String request = MnrlLoadData.gson.toJson(mobileRemovalRequest);
		
		LOGGER.info("Mobile Removal Request [{}]",request);
		
		
		
		
		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getCbsMobileRemovalUrl(),
				indicator+MnrlLoadData.propdetails.getAppProperty().getCbsMobileRemovalHeader());
		LOGGER.info("Response [{}]",response);
		/*if(!response.contains("ErrorResponse")) {
			
			mobileRemovalResponseVo = gson.fromJson(response, MobileRemovalResponseVo.class);
			LOGGER.info("Mobile Removal Response [{}]",mobileRemovalResponseVo);
		}
		else {
			errorResponse = gson.fromJson(response, ErrorVo.class);
			LOGGER.info("Mobile Removal Error Response [{}]",errorResponse);
			mobileRemovalResponseVo=null;
		}*/
		try {
		
			mobileRemovalResponseVo = MnrlLoadData.gson.fromJson(response, MobileRemovalResponseVo.class);
			LOGGER.info("Mobile Removal Response [{}]",mobileRemovalResponseVo);
		}catch(Exception e) {
			LOGGER.error("Exception in mobile removal ",e);
			mobileRemovalResponseVo=null;
			
		}
		
		return mobileRemovalResponseVo;
		
	}
	
	public MobileRemovalRequestVo setMobileNumberRemovalRequest(BigInteger customerNumber,String countryCode,String mobileNumber,String flag,String reason) {
		
		MobileRemovalRequestVo mobileRemovalRequestVo = new MobileRemovalRequestVo();
		mobileRemovalRequestVo.setCustomerNumber(customerNumber);
		mobileRemovalRequestVo.setCountryCode(countryCode);
		mobileRemovalRequestVo.setMobileNumber(mobileNumber);
		mobileRemovalRequestVo.setMobileDeleteFlag(flag);
		mobileRemovalRequestVo.setReason(reason);
		
		return mobileRemovalRequestVo;
	}

}

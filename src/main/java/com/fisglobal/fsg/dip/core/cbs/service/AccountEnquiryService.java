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

import com.fisglobal.fsg.dip.core.cbs.entity.AccountEnquiryRequestVo;
import com.fisglobal.fsg.dip.core.cbs.entity.AccountEnquiryResponse;
import com.fisglobal.fsg.dip.core.cbs.entity.DebitFreezeResponse;
import com.fisglobal.fsg.dip.core.cbs.entity.ErrorVo;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;

@Service
public class AccountEnquiryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountEnquiryService.class);
	@Inject
	ServiceHandlerCbs serviceHandler;

	// ObjectMapper objectMapper = new ObjectMapper();
	

	public AccountEnquiryResponse getAccountDetails(BigInteger accountNumber, String indicator)
			throws KeyManagementException, ClientProtocolException, JSONException, NoSuchAlgorithmException,
			KeyStoreException, IOException {

		AccountEnquiryResponse accResponse = new AccountEnquiryResponse();
	//	ErrorVo errorResponse = new ErrorVo();

		AccountEnquiryRequestVo accRequest = new AccountEnquiryRequestVo();
		accRequest.setCIFAccountNumber(accountNumber);
		String request = MnrlLoadData.gson.toJson(accRequest);
		LOGGER.info("Account Request [{}]", request);
		// Header[] header =
		// headerService.getMiddlewareHeaders(indicator+MnrlLoadData.propdetails.getAppProperty().getCbsAccEnqHeader());

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getCbsAccEnqUrl(),
				indicator + MnrlLoadData.propdetails.getAppProperty().getCbsAccEnqHeader());
		LOGGER.info("Response [{}]", response);
		/*
		 * if (!response.contains("ErrorResponse")) {
		 * 
		 * accResponse = MnrlLoadData.gson.fromJson(response,
		 * AccountEnquiryResponse.class); LOGGER.info("Account Response [{}]",
		 * accResponse); } else { errorResponse = MnrlLoadData.gson.fromJson(response,
		 * ErrorVo.class); LOGGER.info("Account Error Response [{}]", errorResponse);
		 * accResponse = null; }
		 */
		
		try {
			accResponse = MnrlLoadData.gson.fromJson(response, AccountEnquiryResponse.class);
			LOGGER.info("Acc enquiry  Response [{}]", accResponse);
		} catch (Exception e) {
			LOGGER.error("Exception in Acc enquiry [{}]", e);
			accResponse = null;
		}

		return accResponse;

	}

}

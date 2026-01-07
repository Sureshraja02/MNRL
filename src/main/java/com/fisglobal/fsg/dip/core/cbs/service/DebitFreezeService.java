package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.math.BigInteger;
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

import com.fisglobal.fsg.dip.core.cbs.entity.DebitFreezeRequest;
import com.fisglobal.fsg.dip.core.cbs.entity.DebitFreezeResponse;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerCbs;

@Service
public class DebitFreezeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DebitFreezeService.class);
	@Inject
	ServiceHandlerCbs serviceHandler;

	public DebitFreezeResponse processDebitFreeze(BigInteger accountNumber, String indicator)
			throws KeyManagementException, ClientProtocolException, JSONException, NoSuchAlgorithmException,
			KeyStoreException, IOException {

		DebitFreezeRequest dbtFrzrequest = new DebitFreezeRequest();
		// ErrorVo errorResponse = new ErrorVo();
		String stopReason = "38";
		String accountCondition = "3";
		DebitFreezeResponse dbtfrzResponse = new DebitFreezeResponse();
		dbtFrzrequest.setAccountNumber(accountNumber);
		if (StringUtils.isNotBlank(MnrlLoadData.propdetails.getAppProperty().getStopReason())) {
			stopReason = MnrlLoadData.propdetails.getAppProperty().getStopReason();
		}
		dbtFrzrequest.setStopReason(stopReason);
		if (StringUtils.isNotBlank(MnrlLoadData.propdetails.getAppProperty().getAccountCondition())) {
			accountCondition = MnrlLoadData.propdetails.getAppProperty().getAccountCondition();
		}
		dbtFrzrequest.setAccountCondition(accountCondition);
		String request = MnrlLoadData.gson.toJson(dbtFrzrequest);
		LOGGER.info("Debit Freeze Request [{}]", request);

		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getDebitFreezeUrl(),
				indicator + MnrlLoadData.propdetails.getAppProperty().getDebitFreezeHeader());
		LOGGER.info("Response [{}]", response);
		try {
			dbtfrzResponse = MnrlLoadData.gson.fromJson(response, DebitFreezeResponse.class);
			LOGGER.info("Debit Freeze  Response [{}]", dbtfrzResponse);
		} catch (Exception e) {
			LOGGER.error("Exception in Debit freeze [{}]", e);
			dbtfrzResponse = null;
		}

		return dbtfrzResponse;

	}

}

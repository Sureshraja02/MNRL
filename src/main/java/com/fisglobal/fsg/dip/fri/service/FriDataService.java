package com.fisglobal.fsg.dip.fri.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.ServiceHandlerEnq;
import com.fisglobal.fsg.dip.fri.entity.FRIDataRequest;
import com.fisglobal.fsg.dip.service.MnrlCountService;

@Service
public class FriDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlCountService.class);

	

	@Inject
	ServiceHandlerEnq serviceHandler;

	

	public String getFRIData(FRIDataRequest friDataRequest) throws Exception {

		//FRIDataResponse friDataResponse = new FRIDataResponse();
		String request = MnrlLoadData.gson.toJson(friDataRequest);

		LOGGER.debug("FRI Data request [{}]", request);

		
		String response = serviceHandler.sendRequest(request, MnrlLoadData.propdetails.getRestClient(),
				MnrlLoadData.propdetails.getAppProperty().getFriDataURL(),
				MnrlLoadData.propdetails.getAppProperty().getFriDataAPIHeader());
		LOGGER.debug("Response [{}]", response);
		return response;

	}

}

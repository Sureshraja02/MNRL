package com.fisglobal.fsg.dip.core.cbs.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.cbs.entity.MobileEnquiryResponseVo;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;
import com.fisglobal.fsg.dip.entity.MnrlCbsData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlData_DAO;
import com.fisglobal.fsg.dip.entity.repo.MnrlCbsDataRepo;
import com.fisglobal.fsg.dip.entity.repo.MnrlDataRepo;

@Service
public class MnrlMobileEnquiryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlMobileEnquiryService.class);
	
	@Inject
	private AccountService accountService;
	
	@Inject
	private MnrlDataRepo dataRepo;

	@Inject
	private MnrlCbsDataRepo cbsDataRepo;

	@Inject
	private MobileNumberEnquiryService mobileNumberEnquiryService;

	@Inject
	private UpdateFlagService updateFlagService;

	public HashMap<String, String>  respMap= new HashMap<String, String>();

	//public HashMap<String, String>  = new HashMap<String, String>();

	@Inject
	private CommonMethodUtils commonMethodUtils;
	
	@Inject
	private MnrlAsyncService mnrlAsyncService;

	public String processMobileEnquriy(MnrlData_DAO mnrlentity) throws Exception {

		String respXML = null;
		respMap.put(mnrlentity.getMobileNo(), mnrlentity.getMobileNo());
		LOGGER.info("Before async call [{}]",respMap.size());
		CompletableFuture<MnrlCbsData_DAO> asyncResp = mnrlAsyncService.mobileEnquiry(mnrlentity);
		
		if(respMap.containsKey(mnrlentity.getMobileNo())){
			respMap.remove(mnrlentity.getMobileNo());
			LOGGER.info("After async call [{}]",respMap.size());
		}
		
		return respXML;
	}
	
	
}

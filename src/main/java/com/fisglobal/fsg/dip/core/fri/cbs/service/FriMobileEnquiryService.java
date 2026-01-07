package com.fisglobal.fsg.dip.core.fri.cbs.service;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.entity.FriCbsData_DAO;
import com.fisglobal.fsg.dip.entity.FriData_DAO;
@Service
public class FriMobileEnquiryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FriMobileEnquiryService.class);
	
	@Inject
	private FriAsyncService friAsyncService;
	
	public HashMap<String, String>  respMap= new HashMap<String, String>();

	public String processMobileEnquriy(FriData_DAO frientity) throws Exception {

		String respXML = null;
		respMap.put(frientity.getMobileNo(), frientity.getMobileNo());
		LOGGER.info("Before async call [{}]",respMap.size());
		CompletableFuture<FriCbsData_DAO> asyncResp = friAsyncService.mobileEnquiry(frientity);
		
		if(respMap.containsKey(frientity.getMobileNo())){
			respMap.remove(frientity.getMobileNo());
			LOGGER.info("After async call [{}]",respMap.size());
		}
		
		return respXML;
	}

}

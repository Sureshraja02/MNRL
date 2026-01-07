package com.fisglobal.fsg.dip.core.service.v1;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.entity.repo.UserRepo;
import com.fisglobal.fsg.dip.entity.User_Master;

@Service
public class AuthTokenService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenService.class);
	
	@Inject
	private UserRepo userRepo;


	public String getUserName() {
		String userName = "";
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			CustomUserDetails accessToken = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOGGER.info("UserName [{}]",accessToken.getUsername());
			userName=accessToken.getUsername();
		}
		
		return userName;
	}
	
	public String getIntitutionId() {
		String institutionId = "";
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			CustomUserDetails accessToken = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOGGER.info("UserName [{}]",accessToken.getUsername());
			String userName=accessToken.getUsername();
			User_Master userMaster = userRepo.findByUserID(userName);
			institutionId = userMaster.getInstID();
		}
		
		return institutionId;
	}
	
	public String getMakerId() {
		String userid = "";
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			CustomUserDetails accessToken = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			LOGGER.info("UserName [{}]",accessToken.getUsername());
			String userName=accessToken.getUsername();
			User_Master userMaster = userRepo.findByUserID(userName);
			userid = String.valueOf(userMaster.getUserID());
		}
		
		return userid;
	}
}

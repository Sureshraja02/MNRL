package com.fisglobal.fsg.dip.core.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationFilter.class);
	
	
	public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException {
		String authorizationHeader = request.getHeader("Basic");
		LOGGER.info("authorizationHeader : {}",authorizationHeader);
		if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
			response.getWriter().write("Missing or invalid Authorization header");
			return;
		}

		try {
			super.doFilterInternal(request, response, chain);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
			response.getWriter().write("Authorization failed : " + e.getMessage());
		}
	}

}

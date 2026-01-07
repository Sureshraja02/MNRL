package com.fisglobal.fsg.dip.core.service;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fisglobal.fsg.dip.service.headers.HeaderService;

@Component
public class ServiceHandlerCbs {

	private final Logger log = LoggerFactory.getLogger(ServiceHandlerCbs.class);
	//String responseStr;

	@Inject
	private HeaderService headerService;

	public String sendRequest(String request, RestClient restClientHttps, String callbackUrl, String headerPoint)
			throws ClientProtocolException, IOException, JSONException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		// log.info("Request [{}]",request);
		long beforeSendRequest = System.currentTimeMillis();
		String response = send(request, restClientHttps, callbackUrl, headerPoint);
		long afterSendRequest = System.currentTimeMillis();
		long elapsedSendRequest = afterSendRequest - beforeSendRequest;
		log.info("Send request call elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsedSendRequest));

		return response;
	}

	public String send(String request, RestClient restClientHttps, String callbackUrl, String headerPoint)
			throws ClientProtocolException, IOException, JSONException, KeyManagementException,
			NoSuchAlgorithmException, KeyStoreException {
		String responseStr;
		log.info("ServiceHandler - Request URL : {}", callbackUrl);
		long beforeSendRequest = System.currentTimeMillis();
		responseStr = sendViaRestTemplate(request, callbackUrl, restClientHttps, headerPoint);
		long afterSendRequest = System.currentTimeMillis();
		long elapsedSendRequest = afterSendRequest - beforeSendRequest;
		log.info("Send call elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsedSendRequest));

		return responseStr;
	}

	public String sendViaRestTemplate(String request, String url, RestClient restClientHttps, String headerPoint)
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String responseStr = null;

		try {
			log.info("Before setting Headers");
			long beforeHeader = System.currentTimeMillis();
			HttpHeaders httpHeader = headerService.getMiddlewareEnqHeaders(headerPoint);
			log.info("After setting Headers");
			/*
			 * for (Header h : header) { if (StringUtils.isNotBlank(h.getValue())) {
			 * httpHeader.set(h.getName(), h.getValue()); }
			 * 
			 * }
			 */

			// httpHeader.set("content-type", "text/xml");
			HttpEntity<String> entity = new HttpEntity<String>(request, httpHeader);
			log.info("Entity {}", entity.getHeaders());
			log.info("url {}", url);
			long afterHeader = System.currentTimeMillis();
			long elapsedHeader = afterHeader - beforeHeader;
			log.info("Header elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsedHeader));
			long start = System.currentTimeMillis();
			RestTemplate rest = restClientHttps.getRestTemplate();
			log.info("Before sending the request ");
			log.info("After sending the request ");
			log.info("Before receiving");
			ResponseEntity<String> resp = rest.exchange(url, HttpMethod.POST, entity, String.class);
			log.info("After receiving");
			log.info("HttpStatus [{}]", resp.getStatusCode());
			log.debug("Response is : [{}] ", resp.getBody());

			int status = resp.getStatusCode().value();
			log.info("Status code: [{}] ", status);
			responseStr = resp.getBody();
			long end = System.currentTimeMillis();
			long elapsed = end - start;
			log.info("MW Response  elapsed in seconds [{}]", TimeUnit.MILLISECONDS.toSeconds(elapsed));
			//if (status == 200 && responseStr != null
			//		&& !(responseStr.contains("httpCode") || responseStr.contains("error"))) {
				return responseStr;
			//} 

		} catch (RestClientResponseException e) {
			log.error("Http status [{}] ResponseBody [{}]", e.getRawStatusCode(), e.getResponseBodyAsString());
			return  e.getResponseBodyAsString();
		}

	}

}

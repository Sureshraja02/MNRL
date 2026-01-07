package com.fisglobal.fsg.dip.core.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.springframework.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fisglobal.fsg.dip.core.utils.Connection_VO;

@Component
public class ServiceHandler {

	private final Logger log = LoggerFactory.getLogger(ServiceHandler.class);
	//String responseStr;

	public String sendRequest(String request, Header[] header, RestClient  restClientHttps, String callbackUrl)
			throws ClientProtocolException, IOException, JSONException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		//log.info("Request [{}]",request);
		String response = send(request, header,  restClientHttps, callbackUrl);
		return response;
	}

	public String send(String request, Header[] header, RestClient restClientHttps,
			 String callbackUrl)
			throws ClientProtocolException, IOException, JSONException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		String responseStr;
		

		log.info("ServiceHandler - Request URL : {}", callbackUrl);
		
			
				 responseStr=sendViaRestTemplate(request,callbackUrl,restClientHttps,header);
			
			
		return responseStr;
	}

	public String sendViaRestTemplate(String request,String url,RestClient restClientHttps,Header[] header) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException  {
		String responseStr=null;
		
		try {
		HttpHeaders httpHeader = new HttpHeaders();
		for (Header h : header) {
			if (StringUtils.isNotBlank(h.getValue())) {
				httpHeader.set(h.getName(), h.getValue());
			}

		}
		//httpHeader.set("content-type", "text/xml");
		HttpEntity<String> entity = new HttpEntity<String>(request, httpHeader);
		log.info("Entity {}", entity.getHeaders());
		log.info("url {}", url);

		RestTemplate rest = restClientHttps.getRestTemplate();

		ResponseEntity<String> resp =rest.exchange(url, HttpMethod.POST, entity, String.class);
		log.info("HttpStatus [{}]", resp.getStatusCode());
		log.debug("Response is : [{}] ", resp.getBody());
		
		int status=resp.getStatusCode().value();
		log.debug("Status code: [{}] ", status);
		responseStr = resp.getBody();
		if (status == 200 && responseStr != null && !(responseStr.contains("httpCode") || responseStr.contains("error")) ) {
			return responseStr;
		} else {
			return status + "!_!" + responseStr;
			
		}
		}catch(RestClientResponseException e) {
			log.error("Http status [{}] ResponseBody [{}]",e.getRawStatusCode(),e.getResponseBodyAsString());
			return e.getRawStatusCode()+"!_!"+e.getResponseBodyAsString();
		}
	
		
	}
	
}

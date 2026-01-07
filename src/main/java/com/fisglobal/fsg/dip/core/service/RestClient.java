package com.fisglobal.fsg.dip.core.service;

import java.net.Authenticator;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;

//@Order(2)
public class RestClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
	private RestTemplate template;

	private final HttpClientConnectionManager connectionMgr;
	private String userName;
	private String password;
	private int timeOut;
	private int conTimeOut;

	private String proxyIp;
	private int proxyPort;
	private boolean proxyEnabled;
	private boolean sslVerify;

	public RestClient(HttpClientConnectionManager connectionMgr, String userName, String password, int timeOut,
			int conTimeOut, boolean sslVerify) {
		this.connectionMgr = connectionMgr;
		this.userName = userName;
		this.password = password;
		this.timeOut = timeOut;
		this.conTimeOut = conTimeOut;
		this.proxyEnabled = false;
		this.sslVerify = sslVerify;
		init();
	}

	public RestClient(HttpClientConnectionManager connectionMgr, String userName, String password, int timeOut,
			int conTimeOut, String proxyIp, int proxyPort, boolean sslVerify) {
		this.connectionMgr = connectionMgr;
		this.userName = userName;
		this.password = password;
		this.timeOut = timeOut;
		this.conTimeOut = conTimeOut;
		this.proxyIp = proxyIp;
		this.proxyPort = proxyPort;
		this.proxyEnabled = true;
		this.sslVerify = sslVerify;
		init();
	}

	@PostConstruct
	public void init() {
		template = new RestTemplate(getClientHttpRequestFactory(proxyEnabled));
		List<HttpMessageConverter<?>> converters = new ArrayList<>();

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.getObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		converter.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
		converters.add(converter);

	}

	private BasicCredentialsProvider getCredentialsProvider(String uname, String pwd) {
		LOGGER.info("Setting basic auth credentials for Rest Client with uname {}", uname);
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(uname, pwd);
		AuthScope authScope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
		BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(authScope, creds);
		return credentialsProvider;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory(boolean proxyFlag) {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(timeOut);
		factory.setConnectTimeout(conTimeOut);
		//try {

			HttpClient httpClient = null;

			if (StringUtils.isBlank(userName)) {
				if (proxyFlag) {
					HttpHost proxy = new HttpHost(proxyIp, proxyPort);
					httpClient = HttpClients.custom().setConnectionManager(this.connectionMgr).setProxy(proxy)
							.setConnectionManagerShared(true).build();
				} else
					httpClient = HttpClients.custom().setConnectionManager(this.connectionMgr)
							.setConnectionManagerShared(true).build();
			} else {
				if (proxyFlag) {
					HttpHost proxy = new HttpHost(proxyIp, proxyPort);

					Properties systemProperties = System.getProperties();

					systemProperties.setProperty("http.proxyHost", proxyIp);
					systemProperties.setProperty("http.proxyPort", String.valueOf(proxyPort));

					System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");

					Authenticator.setDefault(new SimpleAuthenticator(userName, password));

					httpClient = HttpClientBuilder.create().setConnectionManager(this.connectionMgr).setProxy(proxy)
							.setDefaultCredentialsProvider(getCredentialsProvider(userName, password))
							.setConnectionManagerShared(true).build();
				} else
					httpClient = HttpClientBuilder.create().setConnectionManager(this.connectionMgr)
							.setDefaultCredentialsProvider(getCredentialsProvider(userName, password))
							.setConnectionManagerShared(true).build();
			}

			factory.setHttpClient(httpClient);
		//} catch (Exception e) {
			//LOGGER.error("getClientHttpRequestFactory", e);
		//}
		return factory;
	}

	public RestTemplate getRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		if (sslVerify) {

			return template;
		} else {
			TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
			SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
					.loadTrustMaterial(null, acceptingTrustStrategy).build();
			SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			return restTemplate;
		}

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

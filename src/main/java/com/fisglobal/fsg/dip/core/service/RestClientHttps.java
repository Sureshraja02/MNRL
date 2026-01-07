package com.fisglobal.fsg.dip.core.service;

import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.fisglobal.fsg.dip.core.utils.Connection_VO;

public class RestClientHttps {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientHttps.class);

	private RestTemplate template;
	private Connection_VO connectionHttpsObj;
	private int timeOut;
	private int conTimeOut;
	private String sslVersion;
	private String jkspath;
	private String jksfileSecureTerm;
	private CloseableHttpClient httpClient = null;
	private int maxConnCount;
	private int maxPerRoute;

	public RestClientHttps(Connection_VO connectionHttpsObj) {
		this.connectionHttpsObj = connectionHttpsObj;
		this.timeOut = this.connectionHttpsObj.getSoTimeOut();
		this.conTimeOut = this.connectionHttpsObj.getConnTimeOut();
		this.sslVersion = this.connectionHttpsObj.getSslVersion();
		this.jkspath = this.connectionHttpsObj.getJkspath();
		this.jksfileSecureTerm = this.connectionHttpsObj.getJksfileSecureTerm();

		this.maxConnCount = connectionHttpsObj.getMaxConnCount();
		this.maxPerRoute = connectionHttpsObj.getMaxPerRoute();
	}

	@PostConstruct
	public void init() {
		template = new RestTemplate(getClientHttpRequestFactory());
	}

	public RestTemplate getRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		/*if (this.template == null) {
			this.LOGGER.info("IF - Rest template is null, so Building rest template for HTTPs ");
			this.template = new RestTemplate(getClientHttpRequestFactory());
		} else {
			this.LOGGER.info("ELSE - Rest template not null");
		}
		return this.template;*/
		
		TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
	    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
	    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;
	}

	public CloseableHttpClient getHttpClient() {
		if (this.httpClient != null) {
			return this.httpClient;
		}
		synchronized (this) {
			if (this.httpClient == null) {
				this.httpClient = buildHttpClient();
			}
		}
		return this.httpClient;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(timeOut);
		factory.setConnectTimeout(conTimeOut);
		factory.setConnectionRequestTimeout(conTimeOut);
		SSLContext context;
		try {
			File jksFile = null;
			try {
				jksFile = ResourceUtils.getFile(jkspath);
				LOGGER.info("FILE : " + jksFile.getAbsolutePath());
			} catch (Exception e) {
				LOGGER.error("Exception : {}", e);
			}
			if (jksFile != null) {
				context = new SSLContextBuilder().loadTrustMaterial(jksFile, jksfileSecureTerm.toCharArray())
						.useProtocol(sslVersion).build();
				SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(context);
				// SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(context, new
				// String[] {"TLSv1", "TLSv1.1", "TLSv1.2"}, null,
				// SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				CloseableHttpClient httpClients = HttpClientBuilder.create()// .setConnectionManager(connMgr) //
																			// .setDefaultRequestConfig(config)
						.setSSLSocketFactory(csf).build();
				factory.setHttpClient(httpClients);
			} else {
				LOGGER.info("FILE NOT FOUND");
			}
		} catch (Exception e) {
			LOGGER.error("getClientHttpRequestFactory : {}", e);
		}
		return factory;
	}

	@SuppressWarnings("deprecation")
	private CloseableHttpClient buildHttpClient() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(timeOut);
		factory.setConnectTimeout(conTimeOut);
		factory.setConnectionRequestTimeout(conTimeOut);
		SSLContext context;
		CloseableHttpClient httpClients = null;
		try {
			File jksFile = null;
			if (jkspath != null && StringUtils.isNotBlank(jkspath)) {
				LOGGER.info("jkspath : " + jkspath);
				try {
					jksFile = ResourceUtils.getFile(jkspath);
					LOGGER.info("FILE : " + jksFile.getAbsolutePath());
				} catch (Exception e) {
					LOGGER.error("Exception : {}", e);
				}
			} else {
				LOGGER.info("jkspath : " + jkspath);

			}

			if (jksFile != null) {
				LOGGER.info("jksfilepwd : [{}]", jksfileSecureTerm);
				RequestConfig defRequestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(conTimeOut).build();

				context = new SSLContextBuilder()
						.loadTrustMaterial(ResourceUtils.getFile(jkspath), jksfileSecureTerm.toCharArray())
						.useProtocol(sslVersion).build();
				SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(context);

				Registry socketFactoryRegistry = RegistryBuilder.create().register("https", csf).build();

				PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(
						socketFactoryRegistry);

				connMgr.setMaxTotal(maxConnCount);
				connMgr.setDefaultMaxPerRoute(maxPerRoute);

				httpClients = HttpClientBuilder.create().setConnectionManager(connMgr)
						.setDefaultRequestConfig(defRequestConfig).setSSLSocketFactory(csf).build();

				/*
				 * httpClients = HttpClients.custom().setConnectionManager(connMgr)
				 * .setDefaultRequestConfig(defRequestConfig).setSSLSocketFactory(csf).build();
				 */

				/*
				 * httpClients = HttpClientBuilder.create()// .setConnectionManager(connMgr) //
				 * .setDefaultRequestConfig(config) .setSSLSocketFactory(csf).build();
				 * factory.setHttpClient(httpClients);
				 */
			} else {
				LOGGER.info("FILE NOT FOUND");
			}
		} catch (Exception e) {
			LOGGER.error("buildHttpClient : {}", e);
		}
		return httpClients;
	}
}

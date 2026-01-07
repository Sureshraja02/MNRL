package com.fisglobal.fsg.dip.service.headers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.comm.beans.HeaderProperty;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;

@Service
public class HeaderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeaderService.class);

	@Inject
	private CommonMethodUtils commonMethodUtils;

	@Inject
	Environment env;

	public Header[] getMiddlewareHeaders(String middleChannelBlockAPIHeaderPoint) {
		Header[] headers = null;
		/*if (StringUtils.isNotBlank(middleChannelBlockAPIHeaderPoint)
				&& middleChannelBlockAPIHeaderPoint.equalsIgnoreCase("UAT")) {
			LOGGER.debug("Before getting [{}] Middleware headers", middleChannelBlockAPIHeaderPoint);
			headers = getUatMiddleWareHeader();
		} else if (StringUtils.isNotBlank(middleChannelBlockAPIHeaderPoint)
				&& middleChannelBlockAPIHeaderPoint.equalsIgnoreCase("PREPROD")) {
			LOGGER.debug("Before getting [{}] Middleware headers", middleChannelBlockAPIHeaderPoint);
			headers = getPreprodMiddleWareHeader();
		} else if (StringUtils.isNotBlank(middleChannelBlockAPIHeaderPoint)
				&& middleChannelBlockAPIHeaderPoint.equalsIgnoreCase("PROD")) {
			LOGGER.debug("Before getting [{}] Middleware headers", middleChannelBlockAPIHeaderPoint);
			headers = getProdMiddleWareHeader();
		}*/
		
		/*List<String> list = Stream.of(MnrlLoadData.propdetails.getAppProperty().getHeaderEnvironments().split(","))
                .collect(Collectors.toList());
		
		for(String s: list) {
			if(s.equalsIgnoreCase(middleChannelBlockAPIHeaderPoint)) {
				headers = getMiddleWareHeaderValues();
			}
		}*/
		LOGGER.info("Header key [{}]",middleChannelBlockAPIHeaderPoint);
		if(MnrlLoadData.headerMap.containsKey(middleChannelBlockAPIHeaderPoint)) {
			headers = getMiddleWareHeaderValues(MnrlLoadData.headerMap.get(middleChannelBlockAPIHeaderPoint));
		}
		
		return headers;
	}
	
	public HttpHeaders getMiddlewareEnqHeaders(String middleChannelBlockAPIHeaderPoint) {
		HttpHeaders headers = null;
		/*if (StringUtils.isNotBlank(middleChannelBlockAPIHeaderPoint)
				&& middleChannelBlockAPIHeaderPoint.equalsIgnoreCase("UAT")) {
			LOGGER.debug("Before getting [{}] Middleware headers", middleChannelBlockAPIHeaderPoint);
			headers = getUatMiddleWareHeader();
		} else if (StringUtils.isNotBlank(middleChannelBlockAPIHeaderPoint)
				&& middleChannelBlockAPIHeaderPoint.equalsIgnoreCase("PREPROD")) {
			LOGGER.debug("Before getting [{}] Middleware headers", middleChannelBlockAPIHeaderPoint);
			headers = getPreprodMiddleWareHeader();
		} else if (StringUtils.isNotBlank(middleChannelBlockAPIHeaderPoint)
				&& middleChannelBlockAPIHeaderPoint.equalsIgnoreCase("PROD")) {
			LOGGER.debug("Before getting [{}] Middleware headers", middleChannelBlockAPIHeaderPoint);
			headers = getProdMiddleWareHeader();
		}*/
		
		/*List<String> list = Stream.of(MnrlLoadData.propdetails.getAppProperty().getHeaderEnvironments().split(","))
                .collect(Collectors.toList());
		
		for(String s: list) {
			if(s.equalsIgnoreCase(middleChannelBlockAPIHeaderPoint)) {
				headers = getMiddleWareHeaderValues();
			}
		}*/
		LOGGER.info("Header key [{}]",middleChannelBlockAPIHeaderPoint);
		if(MnrlLoadData.headerMap.containsKey(middleChannelBlockAPIHeaderPoint)) {
			headers = getMiddleWareHeaderEnqValues(MnrlLoadData.headerMap.get(middleChannelBlockAPIHeaderPoint));
		}
		
		return headers;
	}
	
	public HttpHeaders getMiddleWareHeaderEnqValues(HeaderProperty header) {
		long beforeInteraction = System.currentTimeMillis();
		String apiInteractionId = commonMethodUtils.getInteractionId();
		long afterInteraction = System.currentTimeMillis();
		long elapsedInteraction = afterInteraction-beforeInteraction;
		LOGGER.info("Interaction Id elapsed in seconds [{}]",TimeUnit.MILLISECONDS.toSeconds(elapsedInteraction));
		HttpHeaders headers = new HttpHeaders();
	//	headers.set("Accept-Encoding", header.getHeaderAcceptEncoding());
		headers.set("Accept-Encoding", header.getHeaderAcceptEncoding());
		headers.set("Branch-Number", header.getHeaderBranchNumber());
		headers.set("Channel", header.getHeaderChannel());
		headers.set("Content-Type", header.getHeaderContentType());
		headers.set("HealthCheck", header.getHeaderHealthCheck());
		headers.set("HealthType", header.getHeaderHealthType());
		headers.set("Override-Flag",header.getHeaderOverrideFlag());
		headers.set("Recovery-Flag",header.getHeaderRecoveryFlag());
		headers.set("Teller-Number", header.getHeaderAcctStatementTellerNumber());
		headers.set("Terminal-Number",header.getHeaderTerminalNumber());
		headers.set("X-API-Interaction-ID", header.getHeaderChannel() + apiInteractionId);
		headers.set("X-Client-Certificate", header.getHeaderXclientCertificate());
		headers.set("X-IB-Client-Id", header.getHeaderXIbClientId());
		headers.set("X-IB-Client-Secret",header.getHeaderXIbClientIdSecret());

		/*for (Header hrs : headers) {
			LOGGER.debug("Middleware UAT header name [{}] value [{}]", hrs.getName(), hrs.getValue());
		}*/
		return headers;

	}
	
	public Header[] getMiddleWareHeaderValues(HeaderProperty header) {

		String apiInteractionId = commonMethodUtils.getInteractionId();

		Header[] headers = { new BasicHeader("Accept-Encoding", header.getHeaderAcceptEncoding()),
				new BasicHeader("Branch-Number", header.getHeaderBranchNumber()),
				new BasicHeader("Channel", header.getHeaderChannel()),
				new BasicHeader("Content-Type", header.getHeaderContentType()),
				new BasicHeader("HealthCheck", header.getHeaderHealthCheck()),
				new BasicHeader("HealthType", header.getHeaderHealthType()),
				new BasicHeader("Override-Flag",header.getHeaderOverrideFlag()),
				new BasicHeader("Recovery-Flag",header.getHeaderRecoveryFlag()),
				new BasicHeader("Teller-Number", header.getHeaderAcctStatementTellerNumber()),
				new BasicHeader("Terminal-Number",header.getHeaderTerminalNumber()),
				new BasicHeader("X-API-Interaction-ID", header.getHeaderChannel() + apiInteractionId),
				new BasicHeader("X-Client-Certificate", header.getHeaderXclientCertificate()),
				new BasicHeader("X-IB-Client-Id", header.getHeaderXIbClientId()),
				new BasicHeader("X-IB-Client-Secret",header.getHeaderXIbClientIdSecret()) };

		/*for (Header hrs : headers) {
			LOGGER.debug("Middleware UAT header name [{}] value [{}]", hrs.getName(), hrs.getValue());
		}*/
		return headers;

	}

	/*public Header[] getUatMiddleWareHeader() {

		String apiInteractionId = commonMethodUtils.getInteractionId();

		Header[] headers = { new BasicHeader("Accept-Encoding", MnrlLoadData.propdetails.getUatProperty().getHeaderAcceptEncoding()),
				new BasicHeader("Branch-Number", MnrlLoadData.propdetails.getUatProperty().getHeaderBranchNumber()),
				new BasicHeader("Channel", MnrlLoadData.propdetails.getUatProperty().getHeaderChannel()),
				new BasicHeader("Content-Type", MnrlLoadData.propdetails.getUatProperty().getHeaderContentType()),
				new BasicHeader("HealthCheck", MnrlLoadData.propdetails.getUatProperty().getHeaderHealthCheck()),
				new BasicHeader("HealthType", MnrlLoadData.propdetails.getUatProperty().getHeaderHealthType()),
				new BasicHeader("Override-Flag",MnrlLoadData.propdetails.getUatProperty().getHeaderOverrideFlag()),
				new BasicHeader("Recovery-Flag", MnrlLoadData.propdetails.getUatProperty().getHeaderRecoveryFlag()),
				new BasicHeader("Teller-Number", MnrlLoadData.propdetails.getUatProperty().getHeaderAcctStatementTellerNumber()),
				new BasicHeader("Terminal-Number", MnrlLoadData.propdetails.getUatProperty().getHeaderTerminalNumber()),
				new BasicHeader("X-API-Interaction-ID", MnrlLoadData.propdetails.getUatProperty().getHeaderChannel() + apiInteractionId),
				new BasicHeader("X-Client-Certificate", MnrlLoadData.propdetails.getUatProperty().getHeaderXclientCertificate()),
				new BasicHeader("X-IB-Client-Id", MnrlLoadData.propdetails.getUatProperty().getHeaderXIbClientId()),
				new BasicHeader("X-IB-Client-Secret", MnrlLoadData.propdetails.getUatProperty().getHeaderXIbClientIdSecret()) };

		for (Header hrs : headers) {
			LOGGER.debug("Middleware UAT header name [{}] value [{}]", hrs.getName(), hrs.getValue());
		}
		return headers;

	}

	public Header[] getPreprodMiddleWareHeader() {

		Random rnd = new Random();

		String apiInteractionId = commonMethodUtils.getInteractionId();

		Header[] headers = { new BasicHeader("Accept-Encoding", MnrlLoadData.propdetails.getPreProdProperty().getHeaderAcceptEncoding()),
				new BasicHeader("Branch-Number", MnrlLoadData.propdetails.getPreProdProperty().getHeaderBranchNumber()),
				new BasicHeader("Channel", MnrlLoadData.propdetails.getPreProdProperty().getHeaderChannel()),
				new BasicHeader("Content-Type", MnrlLoadData.propdetails.getPreProdProperty().getHeaderContentType()),
				new BasicHeader("HealthCheck", MnrlLoadData.propdetails.getPreProdProperty().getHeaderHealthCheck()),
				new BasicHeader("HealthType", MnrlLoadData.propdetails.getPreProdProperty().getHeaderHealthType()),
				new BasicHeader("Override-Flag",MnrlLoadData.propdetails.getPreProdProperty().getHeaderOverrideFlag()),
				new BasicHeader("Recovery-Flag", MnrlLoadData.propdetails.getPreProdProperty().getHeaderRecoveryFlag()),
				new BasicHeader("Teller-Number", MnrlLoadData.propdetails.getPreProdProperty().getHeaderAcctStatementTellerNumber()),
				new BasicHeader("Terminal-Number", MnrlLoadData.propdetails.getPreProdProperty().getHeaderTerminalNumber()),
				new BasicHeader("X-API-Interaction-ID", MnrlLoadData.propdetails.getPreProdProperty().getHeaderChannel() + apiInteractionId),
				new BasicHeader("X-Client-Certificate", MnrlLoadData.propdetails.getPreProdProperty().getHeaderXclientCertificate()),
				new BasicHeader("X-IB-Client-Id", MnrlLoadData.propdetails.getPreProdProperty().getHeaderXIbClientId()),
				new BasicHeader("X-IB-Client-Secret", MnrlLoadData.propdetails.getPreProdProperty().getHeaderXIbClientIdSecret()) };

		for (Header hrs : headers) {
			LOGGER.debug("Middleware PrePROD header name [{}] value [{}]", hrs.getName(), hrs.getValue());
		}
		return headers;

	}

	public Header[] getProdMiddleWareHeader() {

		String apiInteractionId = commonMethodUtils.getInteractionId();

		Header[] headers = { new BasicHeader("Accept-Encoding", MnrlLoadData.propdetails.getProdProperty().getHeaderAcceptEncoding()),
				new BasicHeader("Branch-Number", MnrlLoadData.propdetails.getProdProperty().getHeaderBranchNumber()),
				new BasicHeader("Channel", MnrlLoadData.propdetails.getProdProperty().getHeaderChannel()),
				new BasicHeader("Content-Type", MnrlLoadData.propdetails.getProdProperty().getHeaderContentType()),
				new BasicHeader("HealthCheck", MnrlLoadData.propdetails.getProdProperty().getHeaderHealthCheck()),
				new BasicHeader("HealthType", MnrlLoadData.propdetails.getProdProperty().getHeaderHealthType()),
				new BasicHeader("Override-Flag",MnrlLoadData.propdetails.getProdProperty().getHeaderOverrideFlag()),
				new BasicHeader("Recovery-Flag", MnrlLoadData.propdetails.getProdProperty().getHeaderRecoveryFlag()),
				new BasicHeader("Teller-Number", MnrlLoadData.propdetails.getProdProperty().getHeaderAcctStatementTellerNumber()),
				new BasicHeader("Terminal-Number", MnrlLoadData.propdetails.getProdProperty().getHeaderTerminalNumber()),
				new BasicHeader("X-API-Interaction-ID", MnrlLoadData.propdetails.getProdProperty().getHeaderChannel() + apiInteractionId),
				new BasicHeader("X-Client-Certificate", MnrlLoadData.propdetails.getProdProperty().getHeaderXclientCertificate()),
				new BasicHeader("X-IB-Client-Id", MnrlLoadData.propdetails.getProdProperty().getHeaderXIbClientId()),
				new BasicHeader("X-IB-Client-Secret", MnrlLoadData.propdetails.getProdProperty().getHeaderXIbClientIdSecret()) };

		for (Header hrs : headers) {
			LOGGER.debug("Middleware PROD header name [{}] value [{}]", hrs.getName(), hrs.getValue());
		}

		return headers;

	}*/
}

package com.fisglobal.fsg.dip.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CustomLoggerUtill {
	Logger analytics = LoggerFactory.getLogger("analytics-DIPReqRes");
	private final SimpleDateFormat sdft = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
	private final Logger LOGGER = LoggerFactory.getLogger(CustomLoggerUtill.class);

	public void toWriteReqRespCallbkLogs(String dateTime, String ackno, String lableParm, String dataParam, String encDataParam) throws JsonProcessingException {
		if (dataParam != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			 Object jsonObject = objectMapper.readValue(dataParam, Object.class);
			String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);

			StringBuilder sbldr = null;
			sbldr = new StringBuilder();
			sbldr.append(dateTime + " [" + ackno + "] ");
			sbldr.append(lableParm + ":");
			sbldr.append("\n");
			if(StringUtils.isNotBlank(encDataParam)) {
				sbldr.append("Encrypt :\n");
				sbldr.append(encDataParam);
				sbldr.append("\n\n");
			}
			sbldr.append("Clear JSON :");
			sbldr.append("\n\n");
			sbldr.append(prettyJson);
			sbldr.append("\n");
			analytics.info(sbldr.toString());
		}
	}

	public void toWriteLogs(CustomLoggerVO customLogger) {
		StringBuilder sbldr = null;
		if (customLogger != null) {
			LOGGER.info("ReqMsgId : [{}] - Service Name : [{}] - Elapsed Time : [{}]", customLogger.getReqMsgId(),
					customLogger.getServiceName(), customLogger.getTotalResponseTime());
			sbldr = new StringBuilder();
			sbldr.append(customLogger.getInstCode());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getChannelId());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getReqMsgId());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getServiceName());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getRequestType());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getRequestTime());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getResponseTime());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getTotalResponseTime());
			sbldr.append(Constants.COMMA_SEPARATER);
			sbldr.append(customLogger.getRequestStatus());
			analytics.info(sbldr.toString());
		}
	}

	public String toGetCurrentTime() {
		return sdft.format(new Date());
	}
}

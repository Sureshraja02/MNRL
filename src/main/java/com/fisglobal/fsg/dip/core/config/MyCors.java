package com.fisglobal.fsg.dip.core.config;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MyCors implements Filter {

	@Value("${allowed.origin}")
	private String allowedOrgins;
	
	@Value("${rateLimit:500}")
	private Integer rateLimit;
	
	static int limitCount = 0;
	static int remaining = 0;
	static int tempSecond = 0;

	private static final Logger LOGGER = LoggerFactory.getLogger(MyCors.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpServletRequest servletRequest = (HttpServletRequest) request;

		System.out.println("Origin : " + servletRequest.getHeader("Origin"));
		System.out.println("Host : " + servletRequest.getHeader("host"));
		if (servletRequest.getHeader("Origin") != null) {
			servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));
		}
		if (servletRequest.getHeader("host") != null) {
			servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("host"));
		}
		servletResponse.setHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"));
		servletResponse.setHeader("Access-Control-Allow-Methods", "POST");
		servletResponse.setHeader("Access-Control-Allow-Headers", "*");
		servletResponse.setHeader("X-FRAME-OPTIONS", "DENY");
		servletResponse.setHeader("X-FRAME-OPTIONS", "SAMEORIGIN");
		servletResponse.setHeader("Content-Security-Policy", "default-src 'self' 'unsafe-inline' 'unsafe-eval';");
		servletResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
		addRateLimt(servletResponse);
		LOGGER.info("In Method Filter [" + servletRequest.getMethod() + "]");
		if (servletRequest.getMethod().equals("DELETE")) {
			servletResponse.setStatus(405);
			servletResponse.setContentType("message/http");
			servletResponse.getWriter().println("DELETE method not allowed");
			servletResponse.getWriter().flush();
			return;
		}
		if (servletRequest.getMethod().equals("PUT")) {
			servletResponse.setStatus(405);
			servletResponse.setContentType("message/http");
			servletResponse.getWriter().println("PUT method not allowed");
			servletResponse.getWriter().flush();
			return;
		}
		if (servletRequest.getMethod().equals("PATCH")) {
			
			servletResponse.setStatus(405);
			servletResponse.setContentType("message/http");
			servletResponse.getWriter().println("PUT method not allowed");
			servletResponse.getWriter().flush();
			return;
		}
		if (servletRequest.getMethod().equals("OPTIONS")) {
			
			servletResponse.setStatus(405);
			servletResponse.setContentType("message/http");
			servletResponse.getWriter().println("OPTIONS method not allowed");
			servletResponse.getWriter().flush();
			return;
		}
		if (servletRequest.getMethod().equals("TRACE")) {
			servletResponse.setStatus(405);
			servletResponse.setContentType("message/http");
			servletResponse.getWriter().println("TRACE method not allowed");
			servletResponse.getWriter().flush();
			return;
		}
		if (servletRequest.getMethod().equals("HEAD")) {
			
			servletResponse.setStatus(405);
			servletResponse.setContentType("message/http");
			servletResponse.getWriter().println("HEAD method not allowed");
			servletResponse.getWriter().flush();
			return;
		}
/*
		Date dt = new Date();
		int second = dt.getSeconds();
		LOGGER.info("second[{}] - tempSecond [{}] ", Integer.valueOf(second), Integer.valueOf(tempSecond));
		if (second == tempSecond) {
			limitCount += 1;
			remaining = this.rateLimit.intValue() - limitCount;
			LOGGER.info("rateLimit[{}] - limitCount [{}] ", this.rateLimit, Integer.valueOf(limitCount));
			LOGGER.info("rateLimit - limitCount = remaining [{}]", Integer.valueOf(remaining));
			if (remaining <= 0) {
				servletResponse.setHeader("X-Ratelimit-Limit", String.valueOf(this.rateLimit));
				servletResponse.setHeader("X-Ratelimit-Remaining", String.valueOf(remaining));
				servletResponse.setStatus(429);
				servletResponse.setContentType("message/http");
				servletResponse.getWriter().println("Rate Limit Reached in over all limit. Too Many Requests");
				servletResponse.getWriter().flush();
				limitCount = 0;
				remaining = this.rateLimit.intValue();
				return;
			}
			servletResponse.setHeader("X-Ratelimit-Limit", String.valueOf(this.rateLimit));
			servletResponse.setHeader("X-Ratelimit-Remaining", String.valueOf(remaining));
		} else {
			tempSecond = second;
			remaining = this.rateLimit.intValue();
			LOGGER.info("[Not Equal seconds], second[{}] - tempSecond [{}] ", Integer.valueOf(second),
					Integer.valueOf(tempSecond));
			servletResponse.setHeader("X-Ratelimit-Limit", String.valueOf(this.rateLimit));
			servletResponse.setHeader("X-Ratelimit-Remaining", String.valueOf(remaining));
		}*/
		
		chain.doFilter(servletRequest, servletResponse);

	}
	
	void addRateLimt(HttpServletResponse servletResponse) throws IOException{

		Date dt = new Date();
		int second = dt.getSeconds();
		LOGGER.info("second[{}] - tempSecond [{}] ", Integer.valueOf(second), Integer.valueOf(tempSecond));
		if (second == tempSecond) {
			limitCount += 1;
			remaining = this.rateLimit.intValue() - limitCount;
			LOGGER.info("rateLimit[{}] - limitCount [{}] ", this.rateLimit, Integer.valueOf(limitCount));
			LOGGER.info("rateLimit - limitCount = remaining [{}]", Integer.valueOf(remaining));
			if (remaining <= 0) {
				servletResponse.setHeader("X-Ratelimit-Limit", String.valueOf(this.rateLimit));
				servletResponse.setHeader("X-Ratelimit-Remaining", String.valueOf(remaining));
				servletResponse.setStatus(429);
				servletResponse.setContentType("message/http");
				servletResponse.getWriter().println("Rate Limit Reached in over all limit. Too Many Requests");
				servletResponse.getWriter().flush();
				limitCount = 0;
				remaining = this.rateLimit.intValue();
				return;
			}
			servletResponse.setHeader("X-Ratelimit-Limit", String.valueOf(this.rateLimit));
			servletResponse.setHeader("X-Ratelimit-Remaining", String.valueOf(remaining));
		} else {
			tempSecond = second;
			remaining = this.rateLimit.intValue();
			LOGGER.info("[Not Equal seconds], second[{}] - tempSecond [{}] ", Integer.valueOf(second),
					Integer.valueOf(tempSecond));
			servletResponse.setHeader("X-Ratelimit-Limit", String.valueOf(this.rateLimit));
			servletResponse.setHeader("X-Ratelimit-Remaining", String.valueOf(remaining));
		}
	}

}

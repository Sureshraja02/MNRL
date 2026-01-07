package com.fisglobal.fsg.dip.core.utils;

import java.util.Optional;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class MaskingPatternLayout{

	private String maskPrefix;
	private Optional<Pattern> pattern;
	private static final Logger LOGGER = LoggerFactory.getLogger(MaskingPatternLayout.class);

	public String getMaskPrefix() {
		return maskPrefix;
	}

	public void setMaskPrefix(String maskPrefix) {
		this.maskPrefix = maskPrefix;
		if (this.maskPrefix != null) {
			this.pattern = Optional.of(Pattern.compile(maskPrefix, Pattern.MULTILINE));
		} else {
			this.pattern = Optional.empty();
		}
	}
//	
//	@Override
//	public String doLayout(ILoggingEvent event) {
//		String messageStr = (super.doLayout(event));
//		return pattern.get().matcher(messageStr).replaceAll("****");
//
//
//	}
}

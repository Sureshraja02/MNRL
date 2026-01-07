package com.fisglobal.fsg.dip.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SensitiveUtils {
	@Value("${logging.xml.mask.regex:}")
	private String maskRegex;
	@Value("${logging.xml.hash.regex:}")
	private String hashRegex;
	@Value("${logging.json.mask.regex:}")
	private String maskjsonRegex;
	@Value("${logging.json.hash.regex:}")
	private String hashjsonRegex;
	private static List<Pattern> maskPattern;
	private static List<Pattern> hashPattern;
	private static List<Pattern> maskjsonPattern;
	private static List<Pattern> hashjsonPattern;

	@PostConstruct
	public void init() {
		setMaskPrefix();
		setHashPrefix();
		setMaskjsonPrefix();
		setHashjsonPrefix();

	}

	public void setMaskPrefix() {

		maskPattern = new ArrayList<Pattern>();
		if (this.maskRegex != null && maskRegex.trim().length() > 1) {
			for (String maskRegx : maskRegex.split("##")) {
				maskPattern.add(Pattern.compile(maskRegx, Pattern.MULTILINE));

			}

		}
	}

	public void setHashPrefix() {

		hashPattern = new ArrayList<Pattern>();
		if (this.hashRegex != null && hashRegex.trim().length() > 1) {
			for (String hashRegx : hashRegex.split("##")) {
				hashPattern.add(Pattern.compile(hashRegx, Pattern.MULTILINE));

			}

		}
	}

	public void setMaskjsonPrefix() {

		maskjsonPattern = new ArrayList<Pattern>();
		if (this.maskjsonRegex != null && maskjsonRegex.trim().length() > 1) {
			for (String maskjsonRegx : maskjsonRegex.split("##")) {
				maskjsonPattern.add(Pattern.compile(maskjsonRegx, Pattern.MULTILINE));

			}

		}
	}

	public void setHashjsonPrefix() {

		hashjsonPattern = new ArrayList<Pattern>();
		if (this.hashjsonRegex != null && hashjsonRegex.trim().length() > 1) {
			for (String hashjsonRegx : hashjsonRegex.split("##")) {
				hashjsonPattern.add(Pattern.compile(hashjsonRegx, Pattern.MULTILINE));

			}

		}
	}

	public static String getLogXml(String xml) {

		if (xml == null) {
			return xml;
		}

		String message = xml;

		if (maskPattern != null) {
			message = mask(message);
		}
		if (hashPattern != null) {
			message = hash(message);
		}

		return message;
	}

	public static String getLogJson(String json) {
		if (json == null) {
			return json;
		}

		String message = json;

		if (maskjsonPattern != null) {
			message = jsonMask(message);
		}
		if (hashjsonPattern != null) {
			message = jsonHash(message);
		}

		return message;
	}

	public static String mask(String message) {
		return processMsg(message, false, maskPattern);
	}

	public static String hash(String message) {
		return processMsg(message, true, hashPattern);
	}

	public static String jsonMask(String message) {
		return processMsg(message, false, maskjsonPattern);
	}

	public static String jsonHash(String message) {
		return processMsg(message, true, hashjsonPattern);
	}

	public static String processMsg(String message, boolean hash, List<Pattern> pattern) {

		if (pattern != null) {
			for (Pattern regx : pattern) {
				Matcher m = regx.matcher(message);
				StringBuffer sb = new StringBuffer();

				while (m.find()) {
					if (m.groupCount() == 2) {
						m.appendReplacement(sb, m.group(1) + (hash ? getHash(m.group(2)) : getMask(m.group(2))));
					} else {
						m.appendReplacement(sb, (hash ? getHash(m.group()) : getMask(m.group())));
					}
				}
				m.appendTail(sb);
				message = sb.toString();
			}
			return message;
		}

		return message;
	}

	public static String getMask(String input) {
		return MaskingPatternLayoutHashing.getMask(input);
	}

	public static String getHash(String input) {
		return MaskingPatternLayoutHashing.getHash(input);
	}

	public static String getHashVpa(String input) {
		return MaskingPatternLayoutHashing.getHashVpa(input);
	}

	public static String getUrl(String input) {
		return MaskingPatternLayoutHashing.getUrl(input);
	}

}

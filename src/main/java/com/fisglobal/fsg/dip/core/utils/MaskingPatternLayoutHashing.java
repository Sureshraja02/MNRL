package com.fisglobal.fsg.dip.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class MaskingPatternLayoutHashing extends PatternLayout{

	private static final Logger LOGGER = LoggerFactory.getLogger(MaskingPatternLayout.class);

	private String maskPrefix;
	private String hashPrefix;

	private static List<Pattern> maskPattern;
	private static List<Pattern> hashPattern;

	public String getMaskPrefix() {
		return maskPrefix;
	}

	public void setMaskPrefix(String maskPrefix) {

		this.maskPrefix = maskPrefix;
		maskPattern = new ArrayList<Pattern>();
		if (this.maskPrefix != null && maskPrefix.trim().length() > 1) {
			for (String maskRegx : maskPrefix.split("##")) {
				maskPattern.add(Pattern.compile(maskRegx, Pattern.MULTILINE));

			}

		}
	}

	public String getHashPrefix() {
		return hashPrefix;
	}

	public void setHashPrefix(String hashPrefix) {
		this.hashPrefix = hashPrefix;

		hashPattern = new ArrayList<Pattern>();
		if (this.hashPrefix != null && hashPrefix.trim().length() > 1) {
			for (String hashRegx : hashPrefix.split("##")) {
				hashPattern.add(Pattern.compile(hashRegx, Pattern.MULTILINE));

			}

		}
	}

	@Override
	public String doLayout(ILoggingEvent event) {
		String message = super.doLayout(event);

		if (maskPattern != null) {
			message = mask(message);
		}
		if (hashPattern != null) {
			message = hash(message);
		}

		return message;
	}

	public static String mask(String message) {
		return processMsg(message, false, maskPattern);
	}

	public static String hash(String message) {
		return processMsg(message, true, hashPattern);
	}

	public static String processMsg(String message, boolean hash, List<Pattern> pattern) {

		if (pattern != null) {
			for (Pattern regx : pattern) {
				Matcher m = regx.matcher(message);
				StringBuffer sb = new StringBuffer();

				while (m.find()) {
					if (m.groupCount() == 2) {
						m.appendReplacement(sb, m.group(1) + (hash ? getHash(m.group(2)) : (m.group(2))));
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

	public static String leftPad(String originalString, int length, String padCharacter) {
		String paddedString = originalString;
		while (paddedString.length() < length) {
			paddedString = padCharacter + paddedString;
		}
		return paddedString;
	}

	public static String MiddlePad(String originalString, int length, char padCharacter) {
		// String paddedString = originalString;
		if (length <= 2) {
			return originalString;
		}
		int len = (length / 2);
		int mod = len % 2;
		int i = len / 2;
		int j;
		if (mod == 1) {
			j = i + 1;
		} else {
			j = i;
		}
		//System.out.println("::::::::::::::::::::::::::::"+originalString);
		// System.out.println(a.substring(j, a.length()-i));
		char[] paddedString = originalString.toCharArray();
		for (int k = j+3; k < (length - i); k++) {
			paddedString[k] = padCharacter;
		}
		// System.out.println(String.valueOf(ch));
		return String.valueOf(paddedString);

	}

	public static String getMask(String input) {
		if (input == null || input.equalsIgnoreCase("null") || input.equalsIgnoreCase("<null>"))
			return input;
		// return leftPad("", input.length(), "X");
		return MiddlePad(input, input.length(), 'x');
	}

	public static String getHash(String input) {
		try {
			if (input == null || input.equalsIgnoreCase("null") || input.equalsIgnoreCase("<null>"))
				return input;
			return DigestUtils.sha256Hex(input);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return input;
	}

	public static Object getHashobj(Object input) {
		try {
			if (input == null)
				return input;
			return DigestUtils.sha256Hex((String) input);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return input;
	}

	public static String getHashVpa(String input) {
		if (input == null || input.equalsIgnoreCase("null") || input.equalsIgnoreCase("<null>"))
			return input;
		try {
			String[] arr = input.split("@");
			String value = null;
			if (arr.length == 2) {
				String name = arr[0];
				String vpa = arr[1];
				value = getHash(name) + "@" + vpa;
			}
			return value;
		} catch (Exception e) {
			return input;
		}

	}

	public static String getUrl(String input) {

		// String input =
		// "http://10.192.69.211:9297/fis-rest-api/app/rest/v2.0/account/deposit?number=50190000003401";
		String[] arr = input.split("\\?");

		String url = arr[0];
		// String num = arr[1];

		return url;

	}
}

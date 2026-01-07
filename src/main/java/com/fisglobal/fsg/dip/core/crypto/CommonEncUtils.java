package com.fisglobal.fsg.dip.core.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class CommonEncUtils {

	public static String keyDigest(String key1, String key2) {
		String returnKey = null;
		try {
			// Combine the keys
			String combinedKeys = key1 + key2;

			// Get the digest
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(combinedKeys.getBytes());

			// Convert the byte array to a hexadecimal string
			StringBuilder hexString = new StringBuilder();
			for (byte b : digest) {
				hexString.append(String.format("%02x", b));
			}

			System.out.println("Digest Key: " + hexString.toString());
			returnKey = hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return returnKey;
	}

	public static void main(String arg[]) {
		String key1 = "yourFirstKey";
		String key2 = "yourSecondKey";
		String retnKey = keyDigest(key1, key2);
		System.out.println(" Rtun : " + retnKey);
		String base64Pattern = "^[A-Za-z0-9+/=]+$";
		String regex = ".*[^a-zA-Z0-9].*";
		String smp ="iVwu66WXjHKy168HE1u4mA==";
		System.out.println(" Is Encrypted : " + Pattern.matches(regex, smp));
		System.out.println(" Is Encrypted : " + smp.matches(regex));
		//string2BitCalculation(retnKey);
	}

	static void string2BitCalculation(String checkStr) {

		StringBuilder bitRepresentation = new StringBuilder();

		for (char c : checkStr.toCharArray()) {
			bitRepresentation.append(String.format("%8s", Integer.toBinaryString(c)).replaceAll(" ", "0"));
		}
		System.out.println("Bit representation: " + bitRepresentation.toString());
	}
}

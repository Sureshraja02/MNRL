package com.fisglobal.fsg.dip.core.crypto;

import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import com.fisglobal.fsg.dip.core.utils.Constants;

@Service
public class EncryptionUtils {

	public String encryption256(String strToEncrypt, String SECRET_KEY, String SALT) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			// IvParameterSpec ivspec = generateIv();

			SecretKeyFactory factory = SecretKeyFactory.getInstance(Constants.sha256);
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secSavi = new SecretKeySpec(tmp.getEncoded(), Constants.ENC_TYPE);

			Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secSavi, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isEncrypted(String str) {
		// Regular expression for base64 encoded strings
		// String base64Pattern = "^[A-Za-z0-9+/=]+$";
		String regex = ".*[^a-zA-Z0-9].*";
		return Pattern.matches(regex, str);
	}
}

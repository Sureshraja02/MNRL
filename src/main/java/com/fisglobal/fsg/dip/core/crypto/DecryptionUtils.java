package com.fisglobal.fsg.dip.core.crypto;

import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.utils.Constants;

@Service
public class DecryptionUtils {
	
	public String decrypt256(String strToDecrypt, String algorByte, String nammk) {
		try {
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			// IvParameterSpec ivspec = generateIv();

			SecretKeyFactory factory = SecretKeyFactory.getInstance(Constants.sha256);
			KeySpec spec = new PBEKeySpec(algorByte.toCharArray(), nammk.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secSavi = new SecretKeySpec(tmp.getEncoded(), Constants.ENC_TYPE);

			Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secSavi, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

}

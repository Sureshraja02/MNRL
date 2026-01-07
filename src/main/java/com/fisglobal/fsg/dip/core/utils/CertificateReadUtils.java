package com.fisglobal.fsg.dip.core.utils;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

import javax.crypto.Cipher;

public class CertificateReadUtils {

	
	static void toReadCrt(byte plainTextKey [] ){
		try {
			FileInputStream flis = new FileInputStream("path/to/keystore.jks");
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(flis, "keystorePassword".toCharArray());

            // Get the certificate
            String alias = "myalias";
           // Certificate cert = keystore.getCertificate(alias);
			
			X509Certificate cert = (X509Certificate) keystore.getCertificate(alias); // Replace with your certificate alias

			// Use cert for encryption
			PublicKey publicKey = cert.getPublicKey();
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			byte[] encryptedKey = cipher.doFinal(plainTextKey); // Assume plainTextKey is defined

			// Decrypt with private key
			PrivateKey privateKey = (PrivateKey) keystore.getKey(alias,"your_password".toCharArray()); // Replace with your password
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] decryptedKey = cipher.doFinal(encryptedKey);
		} catch (Exception e) {

		}
	}
	
}

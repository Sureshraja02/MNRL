package com.fisglobal.fsg.dip.core.convertor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.fri.entity.FRIAuthRequest;
import com.fisglobal.fsg.dip.fri.entity.FRIDataResponse;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlDataResponseV2;
import com.fisglobal.fsg.dip.req.res.entity.V2.MnrlReactivatedDataResponseV2;
import com.fisglobal.fsg.dip.service.MnrlService;
import com.fisglobal.fsg.dip.service.V2.MnrlAtrServiceV2;
import com.fisglobal.fsg.dip.service.V2.MnrlAuthServiceV2;

@Service
public class CryptoApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(CryptoApp.class);

	private static final int AES_KEY_SIZE = 256; // 256-bit AES
	private static final int GCM_IV_LENGTH = 12; // 12-byte IV for GCM

	@Inject
	private MnrlService mnrlService;

	@Inject
	private MnrlAuthServiceV2 authServiceV2;
	
	@Inject
	private MnrlAtrServiceV2 atrServiceV2;

	public String encryptData(String data) throws Exception {
		String publicKeyPem = new String(
				Files.readAllBytes(Paths.get(MnrlLoadData.propdetails.getAppProperty().getFriPublicKeyPath())));

		// Load public key (PKCS#8 base64)
		publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
				.replaceAll("\\s", "");

		byte[] publicKeyDer = Base64.getDecoder().decode(publicKeyPem);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyDer));

		// Generate AES key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(AES_KEY_SIZE);
		SecretKey aesKey = keyGenerator.generateKey();

		// Encrypt data with AES-GCM
		Cipher cipherAes = Cipher.getInstance("AES/GCM/NoPadding");
		byte[] iv = new byte[GCM_IV_LENGTH];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
		cipherAes.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);
		byte[] encryptedDataWithTag = cipherAes.doFinal(data.getBytes("UTF-8"));

		// Split into ciphertext and auth tag (16 bytes for 128-bit tag)
		int tagLength = 16;
		byte[] encryptedData = new byte[encryptedDataWithTag.length - tagLength];
		byte[] authTag = new byte[tagLength];
		System.arraycopy(encryptedDataWithTag, 0, encryptedData, 0, encryptedData.length);
		System.arraycopy(encryptedDataWithTag, encryptedData.length, authTag, 0, tagLength);

		// Encrypt AES key with RSA OAEP, SHA-256 for both hash and MGF1
		Cipher cipherRsa = Cipher.getInstance("RSA/ECB/OAEPPadding");
		OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
				PSource.PSpecified.DEFAULT);
		cipherRsa.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);
		byte[] encryptedAESKey = cipherRsa.doFinal(aesKey.getEncoded());

		FRIAuthRequest friAuthRequest = new FRIAuthRequest();
		friAuthRequest.setEncryptedKey(Base64.getEncoder().encodeToString(encryptedAESKey));
		friAuthRequest.setEncryptedData(Base64.getEncoder().encodeToString(encryptedData));
		friAuthRequest.setAuthTag(Base64.getEncoder().encodeToString(authTag));
		friAuthRequest.setNonce(Base64.getEncoder().encodeToString(iv));
		LOGGER.info("Encrypted JSON Request [{}]", MnrlLoadData.gson.toJson(friAuthRequest));
		return MnrlLoadData.gson.toJson(friAuthRequest);
	}

	public String encryptMnrlData(String data, String apiName,String token) throws Exception {
		String publicKeyPem = new String(
				Files.readAllBytes(Paths.get(MnrlLoadData.propdetails.getAppProperty().getMnrlPubKeyPath())));

		// Load public key (PKCS#8 base64)
		publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
				.replaceAll("\\s", "");

		byte[] publicKeyDer = Base64.getDecoder().decode(publicKeyPem);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyDer));

		// Generate AES key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(AES_KEY_SIZE);
		SecretKey aesKey = keyGenerator.generateKey();

		// Encrypt data with AES-GCM
		Cipher cipherAes = Cipher.getInstance("AES/GCM/NoPadding");
		byte[] iv = new byte[GCM_IV_LENGTH];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
		cipherAes.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);
		byte[] encryptedDataWithTag = cipherAes.doFinal(data.getBytes("UTF-8"));

		// Split into ciphertext and auth tag (16 bytes for 128-bit tag)
		int tagLength = 16;
		byte[] encryptedData = new byte[encryptedDataWithTag.length - tagLength];
		byte[] authTag = new byte[tagLength];
		System.arraycopy(encryptedDataWithTag, 0, encryptedData, 0, encryptedData.length);
		System.arraycopy(encryptedDataWithTag, encryptedData.length, authTag, 0, tagLength);

		// Encrypt AES key with RSA OAEP, SHA-256 for both hash and MGF1
		Cipher cipherRsa = Cipher.getInstance("RSA/ECB/OAEPPadding");
		OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
				PSource.PSpecified.DEFAULT);
		cipherRsa.init(Cipher.ENCRYPT_MODE, publicKey, oaepParams);
		byte[] encryptedAESKey = cipherRsa.doFinal(aesKey.getEncoded());

		String requestEncryptKey = Base64.getEncoder().encodeToString(encryptedAESKey);
		String requestEncryptData = Base64.getEncoder().encodeToString(encryptedData);
		String requestAuthTag = Base64.getEncoder().encodeToString(authTag);
		String requestNonce = Base64.getEncoder().encodeToString(iv);
		String response = null;
		if (apiName.equals("AUTHV2")) {
			response = authServiceV2.setMnrlAuthRequestV2(requestEncryptKey, requestEncryptData, requestAuthTag,
					requestNonce);
		}
		if(apiName.equals("ATRV2")) {
			response = atrServiceV2.setMnrlAtrRequest(requestEncryptKey, requestEncryptData, requestAuthTag,
					requestNonce, token);
		}
		return response;

	}

	public String decryptData(String jsonContent) throws Exception {
		// Parse JSON
		// JSONParser parser = new JSONParser();
		// JSONObject json = (JSONObject) parser.parse(jsonContent);

		FRIDataResponse response = MnrlLoadData.gson.fromJson(jsonContent, FRIDataResponse.class);

		// Decode base64
		String encryptedKeyB64 = response.getEncryptedKey();
		String encryptedDataB64 = response.getEncryptedData();
		String nonceB64 = response.getNonce();
		String authTagB64 = response.getAuthTag();
		byte[] encryptedKey = Base64.getDecoder().decode(encryptedKeyB64);
		byte[] ciphertext = Base64.getDecoder().decode(encryptedDataB64);
		byte[] nonce = Base64.getDecoder().decode(nonceB64);
		byte[] authTag = Base64.getDecoder().decode(authTagB64);

		KeyStore keyStore = loadKeyStore(new File(MnrlLoadData.propdetails.getAppProperty().getFrikeystoreFilePath()),
				mnrlService.decodePassword(MnrlLoadData.propdetails.getAppProperty().getFrikeystoreSecureTerm()),
				MnrlLoadData.propdetails.getAppProperty().getFriKeystoreType());
		System.out.println("KeyStore Loaded Successfully");
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(
				mnrlService.decodePassword(MnrlLoadData.propdetails.getAppProperty().getFrikeystoreAlias()),
				mnrlService.decodePassword(MnrlLoadData.propdetails.getAppProperty().getFrikeystoreSecureTerm())
						.toCharArray());

		// Load private key (PKCS#8 base64)
		/*
		 * privateKeyPem = privateKeyPem .replace("-----BEGIN PRIVATE KEY-----", "")
		 * .replace("-----END PRIVATE KEY-----", "") .replaceAll("\\s", ""); byte[]
		 * privateKeyDer = Base64.getDecoder().decode(privateKeyPem); KeyFactory
		 * keyFactory = KeyFactory.getInstance("RSA"); PrivateKey privateKey =
		 * keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDer));
		 */

		// Decrypt AES key with RSA OAEP, SHA-256 for both hash and MGF1
		Cipher cipherRsa = Cipher.getInstance("RSA/ECB/OAEPPadding");
		OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
				PSource.PSpecified.DEFAULT);
		cipherRsa.init(Cipher.DECRYPT_MODE, privateKey, oaepParams);
		byte[] aesKeyBytes = cipherRsa.doFinal(encryptedKey);

		// Decrypt data with AES-GCM
		SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
		GCMParameterSpec gcmSpec = new GCMParameterSpec(128, nonce);
		Cipher cipherAes = Cipher.getInstance("AES/GCM/NoPadding");
		cipherAes.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);
		byte[] encryptedDataWithTag = new byte[ciphertext.length + authTag.length];
		System.arraycopy(ciphertext, 0, encryptedDataWithTag, 0, ciphertext.length);
		System.arraycopy(authTag, 0, encryptedDataWithTag, ciphertext.length, authTag.length);
		byte[] plaintext = cipherAes.doFinal(encryptedDataWithTag);

		return new String(plaintext, "UTF-8");
	}

	public String decryptMnrlData(String jsonContent, String apiName) throws Exception {
		// Parse JSON
		// JSONParser parser = new JSONParser();
		// JSONObject json = (JSONObject) parser.parse(jsonContent);

		String encryptedKeyB64 = null;
		String encryptedDataB64 = null;
		String nonceB64 = null;
		String authTagB64 = null;

		if (apiName.equals("MNRLDATAV2")) {
			MnrlDataResponseV2 response = MnrlLoadData.gson.fromJson(jsonContent, MnrlDataResponseV2.class);
			 encryptedKeyB64 = response.getMNRLFetchDataResponse().getBody().getPayload().getEncryptedKey();
			 encryptedDataB64 = response.getMNRLFetchDataResponse().getBody().getPayload().getEncryptedData();
			 nonceB64 = response.getMNRLFetchDataResponse().getBody().getPayload().getNonce();
			 authTagB64 = response.getMNRLFetchDataResponse().getBody().getPayload().getAuthTag();

		}
		
		if (apiName.equals("MNRLREACTIVATEDDATAV2")) {
			MnrlReactivatedDataResponseV2 response = MnrlLoadData.gson.fromJson(jsonContent, MnrlReactivatedDataResponseV2.class);
			 encryptedKeyB64 = response.getMNRLReactiveDataResponse().getBody().getPayload().getEncryptedKey();
			 encryptedDataB64 = response.getMNRLReactiveDataResponse().getBody().getPayload().getEncryptedData();
			 nonceB64 = response.getMNRLReactiveDataResponse().getBody().getPayload().getNonce();
			 authTagB64 = response.getMNRLReactiveDataResponse().getBody().getPayload().getAuthTag();

		}

		// Decode base64

	

	byte[] encryptedKey = Base64.getDecoder().decode(encryptedKeyB64);
	byte[] ciphertext = Base64.getDecoder().decode(encryptedDataB64);
	byte[] nonce = Base64.getDecoder().decode(nonceB64);
	byte[] authTag = Base64.getDecoder().decode(authTagB64);

	KeyStore keyStore = loadKeyStore(new File(MnrlLoadData.propdetails.getAppProperty().getKeystoreFilePath()),
			mnrlService.decodePassword(MnrlLoadData.propdetails.getAppProperty().getKeystoreSecureTerm()),
			MnrlLoadData.propdetails.getAppProperty()
					.getKeystoreType());System.out.println("KeyStore Loaded Successfully");
	PrivateKey privateKey = (PrivateKey) keyStore.getKey(
			mnrlService.decodePassword(MnrlLoadData.propdetails.getAppProperty().getKeystoreAlias()),
			mnrlService.decodePassword(MnrlLoadData.propdetails.getAppProperty().getKeystoreSecureTerm())
					.toCharArray());

	// Load private key (PKCS#8 base64)
	/*
	 * privateKeyPem = privateKeyPem .replace("-----BEGIN PRIVATE KEY-----", "")
	 * .replace("-----END PRIVATE KEY-----", "") .replaceAll("\\s", ""); byte[]
	 * privateKeyDer = Base64.getDecoder().decode(privateKeyPem); KeyFactory
	 * keyFactory = KeyFactory.getInstance("RSA"); PrivateKey privateKey =
	 * keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyDer));
	 */

	// Decrypt AES key with RSA OAEP, SHA-256 for both hash and MGF1
	Cipher cipherRsa = Cipher.getInstance("RSA/ECB/OAEPPadding");
	OAEPParameterSpec oaepParams = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256,
			PSource.PSpecified.DEFAULT);cipherRsa.init(Cipher.DECRYPT_MODE,privateKey,oaepParams);
	byte[] aesKeyBytes = cipherRsa.doFinal(encryptedKey);

	// Decrypt data with AES-GCM
	SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
	GCMParameterSpec gcmSpec = new GCMParameterSpec(128, nonce);
	Cipher cipherAes = Cipher.getInstance("AES/GCM/NoPadding");cipherAes.init(Cipher.DECRYPT_MODE,aesKey,gcmSpec);
	byte[] encryptedDataWithTag = new byte[ciphertext.length
			+ authTag.length];System.arraycopy(ciphertext,0,encryptedDataWithTag,0,ciphertext.length);System.arraycopy(authTag,0,encryptedDataWithTag,ciphertext.length,authTag.length);
	byte[] plaintext = cipherAes.doFinal(encryptedDataWithTag);

	return new String(plaintext,"UTF-8");
	}

	public static KeyStore loadKeyStore(final File keystoreFile, final String password, final String keyStoreType)
			throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
		if (null == keystoreFile) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		System.out.println("Initializing key store: {}" + keystoreFile.getAbsolutePath());
		final URI keystoreUri = keystoreFile.toURI();
		final URL keystoreUrl = keystoreUri.toURL();
		final KeyStore keystore = KeyStore.getInstance(keyStoreType);
		InputStream is = null;
		try {
			is = keystoreUrl.openStream();
			keystore.load(is, null == password ? null : password.toCharArray());
			System.out.println("Loaded key store");
		} finally {
			if (null != is) {
				is.close();
			}
		}
		return keystore;
	}
}

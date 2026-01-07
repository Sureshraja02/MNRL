package com.fisglobal.fsg.dip.core.convertor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import com.fisglobal.fsg.dip.core.utils.Constants;

@Component
public class AESEncryption {

	private static final String SECRET_KEY = "s+Uj6hsgZQkDkdgHmUi+blAvQiozx+iudjpsrknwasi=";
	//private static final String CIPHER_TYPE = "AES/CBC/PKCS5Padding"; // PKCS5 = PKCS7 for AES
	//private static final String ENC_TYPE = "AES";

	public static byte[] getKey() {

		byte[] key = Base64.getDecoder().decode(SECRET_KEY);

		if (key.length != 32) {
			throw new IllegalArgumentException("invalid key length : " + key.length + " bytes");
		}

		return key;
	}

	public static byte[] getKeyWithParam(String pramSECRET_KEY) {

		byte[] key = Base64.getDecoder().decode(pramSECRET_KEY);

		if (key.length != 32) {
			throw new IllegalArgumentException("invalid key length : " + key.length + " bytes");
		}

		return key;
	}

	public static byte[] generateIV() {
		byte[] iv = new byte[16]; // 16 zero's as default IV
		new SecureRandom().nextBytes(iv);
		return iv;
	}

	public static String encrypt(String text) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(getKey(), Constants.ENC_TYPE);
		IvParameterSpec ivSpec = new IvParameterSpec(generateIV());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		byte[] encryptedBytes = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	private static String decrypt(String encryptedText) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {

		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);

		SecretKeySpec keySpec = new SecretKeySpec(getKey(), Constants.ENC_TYPE);
		IvParameterSpec ivSpec = new IvParameterSpec(generateIV());

		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);

		byte[] decryptedBytes = cipher.doFinal(decodedBytes);

		return new String(decryptedBytes, "UTF-8");

	}

	public static String decryptwithKey(String encryptedText, String decKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {

		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);

		SecretKeySpec keySpec = new SecretKeySpec(getKeyWithParam(decKey), Constants.ENC_TYPE);
		IvParameterSpec ivSpec = new IvParameterSpec(generateIV());

		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);

		byte[] decryptedBytes = cipher.doFinal(decodedBytes);

		return new String(decryptedBytes, "UTF-8");

	}

	public static String decryptwithKeyRevoce16DigiIV(String encryptedText, String decKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(getKeyWithParam(decKey), Constants.ENC_TYPE);
		byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] iv = Arrays.copyOf(decodedBytes, 16);
		System.out.println("decoded bytes count : " + decodedBytes.length);
		decodedBytes = Arrays.copyOfRange(decodedBytes, 16, decodedBytes.length);
		System.out.println("iv count : " + iv.length + "decode Bytes count : " + decodedBytes.length);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
		byte[] decryptedBytes = cipher.doFinal(decodedBytes);
		return new String(decryptedBytes, "UTF-8");

	}

	public static String encryptwithPaddingIVinFront(String encStrParam, String key)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
		while (encStrParam.getBytes().length % 16 != 0) {
			encStrParam += '\u0020';
		}
		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(getKeyWithParam(key), Constants.ENC_TYPE);
		byte[] iv = generateIV();
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		byte[] encryptedBytes = cipher.doFinal(encStrParam.getBytes("UTF-8"));

		byte[] finalArray = new byte[iv.length + encryptedBytes.length];
		System.arraycopy(iv, 0, finalArray, 0, iv.length);
		System.arraycopy(encryptedBytes, 0, finalArray, iv.length, encryptedBytes.length);
		return Base64.getEncoder().encodeToString(finalArray);
	}

	// need to seperate IV value// then we will decrypt
	public static String decryptwithKeyRemoveIV(String encryptedText, String decKey) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(getKeyWithParam(decKey), Constants.ENC_TYPE);
		byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
		byte IVdata[] = null;
		byte afterRmvIv[] = null;
		if (decodedBytes != null && decodedBytes.length > 17) {
			afterRmvIv = Arrays.copyOfRange(decodedBytes, 16, decodedBytes.length);
			IVdata = Arrays.copyOf(decodedBytes, 16);
			System.out.println("----Actual with IV From I4C------->>>" + decodedBytes.length);
			System.out.println("----IV From I4C (Splited)------->>>" + IVdata.length);
			System.out.println("----After Remove IV I4C (Splited)------->>>" + afterRmvIv.length);
		} else {
			IVdata = generateIV();
		}
		cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IVdata));
		byte[] decryptedBytes = cipher.doFinal(afterRmvIv);
		return new String(decryptedBytes, "UTF-8");//java.util.Base64.getMimeDecoder.decode(
		//return new String(java.util.Base64.getMimeDecoder().decode(decryptedBytes));
		
	}

	public static String encryptwithKey(String text, String key) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);
		SecretKeySpec keySpec = new SecretKeySpec(getKeyWithParam(key), Constants.ENC_TYPE);
		IvParameterSpec ivSpec = new IvParameterSpec(generateIV());
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		byte[] encryptedBytes = cipher.doFinal(text.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public static void main(String args[]) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException,
			IOException {
//		String plainText = "Hello world";
//		System.out.println("Text to encrypt : " + plainText);
//		String encryptedText = encrypt(plainText);
//		System.out.println("Encrypted Text : " + encryptedText);
//		String decryptedText = decrypt(encryptedText);
//		System.out.println("Decrypted Text : " + decryptedText);
//		String plainText1 = "Hello world";
//		String encTxt = encryptwithKey(plainText1, "s+Uj6hsgZQkDkdgHmUi+blAvQiozx+iudjpsrknwasi=");
//		String tt = "3a2QZYyFSax1H28N7/qeoCKQb2MaBP7zntFeUQ+5VV08O3oQDgd06nBKqHk462ZB5JEP0jJpAUFTKe8oyoUMJf6CFHnL3x9cmWjCRq0rrDAxB6E1idR+PnFXwaWnhUYDpRPjmqIZyskvl20NGDudHN1jcHghKo89TlG0tDGnywp4Rw+Yy+VX/Wl1w4uf+rNgjnFj8PMFCcS5d2vDNwK85A==";
//		String decryptedText2 = decryptwithKey(tt, "s+Uj6hsgZQkDkdgHmUi+blAvQiozx+iudjpsrknwasi=");
//		System.out.println("encrypted encTxt : " + encTxt);
//		System.out.println("Decrypted decryptedText2 : " + decryptedText2);
		
		//String aa ="{\"acknowledgement_no\":\"22909240073155\",\"job_id\":\"IND-cf149277-650e-45d3-a297-876543757912\",\"transactions\":[{\"rrn\":\"465499418452\",\"amount\":\"42030.34\",\"transaction_datetime\":\"2024-10-14 00:00:00\",\"disputed_amount\":\"14000\",\"txn_type\":\"CR\",\"root_account_number\":\"5014328XXXX\",\"root_bankid\":\"19\",\"status_code\":1,\"remarks\":\"Record not found\",\"root_rrn_transaction_id\":\"465499418386\",\"pos_transaction_id\":\"465499418386\"}]}";
		//String aa ="{\"acknowledgement_no\":\"21311240051469\",\"job_id\":\"IND-cf149277-650e-45d3-a299-876543758913\",\"transactions\":[{\"rrn\":\"424046215178\",\"amount\":\"2000.0\",\"transaction_datetime\":\"2024-08-27 12:25:12\",\"disputed_amount\":\"2000.0\",\"txn_type\":\"CR\",\"root_account_number\":\"766536XXXX\",\"root_bankid\":\"19\",\"status_code\":\"01\",\"remarks\":\"Record not found\",\"root_rrn_transaction_id\":\"424046213919\",\"pos_transaction_id\":\"424046213919\"}]}";
		String aa ="{\"acknowledgement_no\":\"22712240053300\",\"job_id\":\"IND-cf149255-750e-45d3-a959-886543758213\",\"transactions\":[{\"rrn\":\"46750974489\",\"amount\":\"5000.0\",\"transaction_datetime\":\"2024-08-27 12:25:12\",\"disputed_amount\":\"5000.0\",\"txn_type\":\"CR\",\"root_account_number\":\"5033744XXXX\",\"root_bankid\":\"19\",\"status_code\":\"01\",\"remarks\":\"Record not found\",\"root_rrn_transaction_id\":\"467509738871\",\"pos_transaction_id\":\"467509738871\"}]}";
		String enc =encryptwithPaddingIVinFront(aa,SECRET_KEY);
		System.out.println(enc);
		String decStr="vhxdhcaomlYnDE+PdHuhYfk86cZQUdOy64TtshV5bN6f2yrRsvAsccRIvxSwQjJOnd9UX1kdegE0nr6fjNfBLhh\\/YMYO+LwCKpTDmM\\/dSulhtYDLdZofotSIvmDOavFjpgusWisEiOPZxhjO6E6v5837Wh09YPQtUWR64YU8qlszedYGZSF8kksPntuGgNxfrFmMqaEZDCXz7tKbIKoERqkuCIRuP9MzJp+M8JVdjINWa5f2dLg7pJJtlBNStkgL";
		System.out.println(decryptwithKeyRemoveIV(decStr, SECRET_KEY));
		
	}

}

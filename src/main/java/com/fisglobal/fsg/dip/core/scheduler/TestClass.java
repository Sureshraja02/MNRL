package com.fisglobal.fsg.dip.core.scheduler;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestClass {

	public static void main(String arg[]) throws JsonProcessingException, UnsupportedEncodingException {
		ObjectMapper objectMapper = new ObjectMapper();
		
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		String resposne = "{\"ErrorResponse\":{\"metadata\":{\"status\":{\"code\":\"200\",\"desc\":\"Bad Request\"}},\"additionalinfo\":{\"excepCode\":\"CBS-0598\",\"excepText\":\"ERR 00 TRANSACTION ALREADY CORRECTED\",\"excepMetaData\":\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\"}}}";
		String reqObjStr = objectMapper.writeValueAsString(resposne.toString());
		System.out.println("------------" + reqObjStr);
		JSONObject jb = new JSONObject();
		
		/* assertEquals("John", user.name); */
		//AccountStatementApiReponse mainStatement_Response = null;

		// mainStatement_Response =
		// objectMapper.readValue(reqObjStr,AccountStatementApiReponse.class);
		//MiddleWareErrorResponse_VO error = objectMapper.readValue(reqObjStr, MiddleWareErrorResponse_VO.class);

		String sizeTest = "{\"ErrorResponse\":{\"metadata\":{\"status\":{\"code\":\"200\",\"desc\":\"Bad Request\"}},\"additionalinfo\":{\"excepCode\":\"CBS-0598\",\"excepText\":\"ERR 00 TRANSACTION ALREADY CORRECTED\",\"excepMetaData\":\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "{\\\"ErrorResponse\\\":{\\\"metadata\\\":{\\\"status\\\":{\\\"code\\\":\\\"200\\\",\\\"desc\\\":\\\"Bad Request\\\"}},\\\"additionalinfo\\\":{\\\"excepCode\\\":\\\"CBS-0598\\\",\\\"excepText\\\":\\\"ERR 00 TRANSACTION ALREADY CORRECTED\\\",\\\"excepMetaData\\\":\\\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\\\"}}}"
				+ "";
		
		System.out.println("----SIZE OF KB 1--------" + getStringSizeInKB(sizeTest));
		
		String valueToEncode ="wqewewqwqwqe";
		
		Header[] headers = new Header[1];
		
			//headers = accountFetchScheduler.getMiddleAccountStatementWareHeader();
			if (headers != null && headers.length > 0) {
				headers[headers.length-1] = new BasicHeader("Authorization",
						"Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes()));
			}
		
			//System.out.println("----SIZE OF KB--headers------" + headers[0]);
			
			String resposne1 = "??D68~c?y?$?S{\"ErrorResponse\":{\"metadata\":{\"status\":{\"code\":\"200\",\"desc\":\"Bad Request\"}},\"additionalinfo\":{\"excepCode\":\"CBS-0598\",\"excepText\":\"ERR 00 TRANSACTION ALREADY CORRECTED\",\"excepMetaData\":\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\"}}}";
			
			String cleanedJson = removeJunkChars(resposne1);
			System.out.println("----------->>>"+cleanedJson);
			
			String i4crequestStr ="PRABAKARANRAJASE-KASR-SUdha";
			String modifiedI4cString =null, i4cIV=null;
			if (StringUtils.isNotBlank(i4crequestStr)) {
				if (i4crequestStr.length() > 17) {
					modifiedI4cString = i4crequestStr.substring(16);
					i4cIV = i4crequestStr.substring(0, 16);
					System.out.println("------modifiedI4cString----->>>"+modifiedI4cString);
					System.out.println("----i4cIV------->>>"+i4cIV);
					
					byte decodedBytes[] = i4crequestStr.getBytes();
				byte rs[]=	Arrays.copyOf(decodedBytes, 16);
				byte rs1[]=	Arrays.copyOfRange(decodedBytes, 16, decodedBytes.length);
				byte rs13[]=	Arrays.copyOfRange(decodedBytes, 0, 16);
				String str = new String(rs, StandardCharsets.UTF_8);
				String str1 = new String(rs1, StandardCharsets.UTF_8);
				String str2 = new String(rs13, StandardCharsets.UTF_8);
					System.out.println("----tttttttt------->>>"+str);
					System.out.println("----tttttttt------->>>"+str1);
					System.out.println("----tttttttt------->>>"+str2);
					
				}

				BigDecimal tt = new BigDecimal(0000000.0000000000000);
				if(!tt.equals(0)) {
					System.out.println("----tttttttt------->>>"+tt);
				} else {
					System.out.println("----Else------->>>"+tt);
				}
			}
			
			
			List<String> options =  new ArrayList<>();
			
			options.add("IDBI Bank");
			options.add("ICICI Bank");
			options.add("DCB Bank (Development Credit Bank)");
			options.add("City Union Bank");
			options.add("City Indian Bank");
			options.add("Indian Bank (including Allahabad Bank)");
			options.add("Bank of India");
					
	        String query = "Indian Bank";
	 
	        List<String> matches = findMatches(options, query);
	 
	        System.out.println("Matches for query '" + query + "': " + matches);
		    
	}
	
	public static String removeJunkChars(String jsonString) throws UnsupportedEncodingException {
		byte[] utf8Bytes = jsonString.getBytes("UTF8");
	    byte[] defaultBytes = jsonString.getBytes();
		String roundTrip = new String(utf8Bytes, "UTF8");
	    System.out.println("roundTrip = " + roundTrip);
		
        return jsonString.chars()
                .filter(ch -> ch >= 32 && ch <= 126) // ASCII printable characters range
                .mapToObj(ch -> String.valueOf((char) ch))
                .collect(Collectors.joining());
    }
	

    /*public static void main(String[] args) {
        String jsonString = "{\"name\":\"John\\u0000Doe\",\"age\":30}";
        String cleanedJson = removeJunkChars(jsonString);
        System.out.println(cleanedJson);
    }*/
	
	public static String removeJunkChars2(String jsonString) {
        // Define a regex pattern to match junk characters
        //String regex = "??D68~c?y?$?S{\"ErrorResponse\":}{}"; // Matches non-printable ASCII characters
        //Pattern pattern = Pattern.compile(regex);
      //  return pattern.matcher(jsonString).replaceAll("").toString();
       // System.out.println("----SIZE OF KB--headers------" + pattern.matcher(jsonString).replaceAll(""));
        //System.out.println("----SIZE OF KB--headers------" + pattern.matcher(jsonString).replaceAll(""));
       String rr []= jsonString.split("{");
       System.out.println("---test------" + rr[0]);
      // System.out.println("----SIZE OF KB--headers------" + rr[1]);
        return "";
    }
	
	public static int getStringSizeInKB(String str) {
        // Convert string to bytes using UTF-8 encoding
        byte[] byteArray = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        // Calculate size in KB
        int sizeInBytes = byteArray.length;
        int sizeInKB = (int) Math.ceil(sizeInBytes / 1024.0);
        return sizeInKB;
    }
	
	public static List<String> findMatches(List<String> options, String query) {
        List<String> matches = new ArrayList<>();
 
        for (String option : options) {
        	
            if (StringUtils.containsIgnoreCase(option, query)) {
                matches.add(option);
                System.out.println("---test------2");
            } 
            
        }
 
        return matches;
    }

}

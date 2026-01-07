package com.fisglobal.fsg.dip.core.scheduler;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fisglobal.fsg.dip.core.utils.Constants;

public class TestingClass {
	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();
		// String resposne
		// ="{\"MainStatement_Response\":{\"metaData\":{\"status\":{\"code\":\"200\",\"desc\":\"Success\"}},\"Body\":{\"Payload\":{\"Account_No\":\"6200686780\",\"Title_Code\":\"01|Mr.\",\"First_Name\":\"RAJASELVAN\",\"Middle_Name\":\"\"}}}}";
		/*
		 * String resposne
		 * ="{\"ErrorResponse\":{\"metadata\":{\"status\":{\"code\":\"200\",\"desc\":\"Bad Request\"}},\"additionalinfo\":{\"excepCode\":\"CBS-0598\",\"excepText\":\"ERR 00 TRANSACTION ALREADY CORRECTED\",\"excepMetaData\":\"NO BUSINESS ERROR HAS BEEN UPDATED HERE\"}}}"
		 * ; MiddleWareErrorResponse_VO errorResponse = objectMapper.readValue(resposne,
		 * MiddleWareErrorResponse_VO.class);
		 * System.out.println("---->"+errorResponse.getErrorResponse().getMetadata().
		 * getStatus().getCode());
		 * System.out.println("---->"+errorResponse.getErrorResponse().getAdditionalinfo
		 * ().getExcepText());
		 * 
		 */
		
		
		
		
		
		
		
		String narration="TO FI CASH WDL TRANSFER TO 7570494259  KUMAR A45606209KALLAKURICHI TNIN/AEPS-ONUS-CW-503618341110-INU0000T5500214-VASANTHA/BRANCH :  CORE BANKING DATA CENTRE";
		
		
		try {
			if (narration.contains(Constants.AEPS) && narration.contains("AEPS-ONUS")) {

				if (narration.contains("AEPS-ONUS")) {
					int seqNoindex = narration.indexOf("AEPS-ONUS");
					String shortNarration = narration.substring(seqNoindex + 9, seqNoindex + 35);

					String rrn[] = shortNarration.split("-");
					if (rrn.length >= 2) {
						System.out.println("------rrn-->" + rrn[2]);
					}

				}
			}
		} catch (Exception e) {

		}
		
		
		
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		 
		 
		/* String SelfRe="SELF BANKSELF MAHA TAKA";
		 String regex1 = "^SELF\\+\\-+";
		 System.out.println(SelfRe.replaceAll(regex1, ""));
		 
		 String SelfRef="SELF BANKSELF MAHA TAKA";
		 String regex2 = "^SELF+";
		 System.out.println(SelfRef.replaceAll(regex2, ""));*/
		 
		
		
	/*	long startTime = System.currentTimeMillis();
		System.out.println("------>" + startTime);

		String resposne = "{\"MainStatement_Response\":{\"metaData\":{\"status\":{\"code\":\"200\",\"desc\":\"Success\"}},\"Body\":{\"Payload\":{\"Collection\":[{\"Valid_Date\":\"06112024\",\"Post_Date\":\"06112024\",\"Transaction_Type\":\"DR\",\"Narration\":\"YESB0YBLUPI/KOVAI SILKS/XXXXX  /Q975841384@ybl /UPI/590246043152/Payment from PhonePe /BRANCH :  ATM SERVICE BRANCH\",\"Amount\":\"1400.000-\",\"Current_Balance\":\"48594.520+\",\"Cheque_Number\":\"\",\"Journal_Number\":\"061353399\"},{\"Valid_Date\":\"06112024\",\"Post_Date\":\"06112024\",\"Transaction_Type\":\"DR\",\"Narration\":\"SBIN0008506/SUSHIL  PEGU  /XXXXX  /9707668594@ybl /UPI/755610389527/Payment from PhonePe /BRANCH :  ATM SERVICE BRANCH\",\"Amount\":\"17000.000-\",\"Current_Balance\":\"49994.520+\",\"Cheque_Number\":\"\",\"Journal_Number\":\"060152140\"}],\"Account_No\":\"6200686780\",\"Title_Code\":\"01|Mr.\",\"First_Name\":\"RAJASELVAN\",\"Middle_Name\":\"\",\"Last_Name\":\"S\",\"Address_1\":\"H.K.SUPERVISIOR\",\"Address_2\":\"KMCH\",\"Address_3\":\"AVINASHI ROAD\",\"Address_4\":\"COIMBATORE\",\"Pin_Code\":\"641014\",\"Isd_Code\":\"91\",\"Mobile\":\"8870490323\",\"Email\":\"\",\"Available_Balanace\":\"52591.060+\",\"Hold_Amount\":\"0.000+\",\"Uncleared_Balance\":\"0.000+\",\"Current_Balance\":\"52591.060+\",\"Opening_Balance\":\"130026.200+\",\"Closing_Balance\":\"48594.520+\"}}}}";

		String narration = "TRANSFER FROM 94962000122  NEFT/UTIB/AXNGG30871773413  /GOOGLE INDIA/  /BRANCH :  MUMBAI FORT";
		if (narration.contains("NEFT")) {
			int seqNoindex = narration.indexOf("NEFT");

			String seq = narration.substring(seqNoindex, seqNoindex + 35);
			String seqNoarr[] = seq.split("/");

			System.out.println("--NEFT--->----->" + seqNoarr[2].trim());
			for (int i = 0; i <= 50; i++) {
				System.out.println(i + "--EFRM--->----->" + "EFRM" + getReqid());
			}
		}
		*/

		/*
		 * AccountStatementApiReponse errorResponse = objectMapper.readValue(resposne,
		 * AccountStatementApiReponse.class);
		 * 
		 * System.out.println("---->"+errorResponse.getMainStatement_Response().
		 * getMetaData().getStatus().getCode());
		 * 
		 * 
		 */
		// AccountStatementApiReponse mainStatement_Response =
		// objectMapper.readValue(resposne,AccountStatementApiReponse.class);

		// System.out.println("---->"+mainStatement_Response.getMainStatement_Response().getMetaData().getStatus().getCode());

		// String narrationUPI="CNRB0000033/SYEDSALMA /XXXXX /8297667610@ybl
		// /UPI/756848703565/Payment from PhonePe /BRANCH : ATM SERVICE BRANCH";
		// String narrationUPI="BRANCH : KMCH GOLDWINS/MERCHNT-ID:99207362 IOCL COCO
		// KANGEYAMPAL COIMBAT POS TXN SEQ NO 430709032489 TERMINAL-ID 00293450 /";
		// String narrationUPI="TRANSFER FROM 94962000122 NEFT/UTIB/AXNGG30871769026
		// /GOOGLE INDIA/ /BRANCH : MUMBAI FORT";
		// String narrationUPI="TRANSFER TO 98906000129 Txn Amt. 7,00,000.00
		// Charges..00/RTGS/HDFC/IDIBR52024112842373275/ANTRED P/. /BRANCH : MUMBAI
		// FORT";
		// String narrationUPI ="TRANSFER TO 97158046478
		// 060226022646/MAHB/Donation//IMPS/P2A/427512432023//BRANCH : ATM SERVICE
		// BRANCH";

		/*
		 * Random rnd = new Random(); int number1 = rnd.nextInt(999999); int number2 =
		 * rnd.nextInt(999999); String apiInteractionId = "9876543210" +
		 * String.format("%06d", number1)+ String.format("%06d", number2);
		 * System.out.println("---->"+apiInteractionId);
		 */
	
		
	
	
	}
	
	
	public static boolean findTarget( String target, String source ) {

	    int target_len = target.length();
	    int source_len = source.length();

	    boolean found = false;

	    for(int i = 0; ( i < source_len && !found ); ++i) {

	    int j = 0;

	        while( !found ) {

	            if( j >= target_len ) {
	                break;
	            }
	            /**
	             * Learning Concept:
	             *
	             *  String target = "for";
	             *  String source = "Searching for a string within a string the hard way.";
	             *
	             *  1 - target.charAt( j ) :
	             *    The character at position 0 > The first character in 'Target' > character 'f', index 0.
	             *
	             *  2 - source.charAt( i + j) :
	             *
	             *    The source strings' array index is searched to determine if a match is found for the
	             *    target strings' character index position. The position for each character in the target string
	             *    is then compared to the position of the character in the source string.
	             *
	             *    If the condition is true, the target loop continues for the length of the target string.
	             *
	             *    If all of the source strings' character array element position matches the target strings' character array element position
	             *    Then the condition succeeds ..
	             */else if( target.charAt( j ) != source.charAt( i + j ) ) {
	                break;
	            } else {
	                ++j;                if( j == target_len ) {
	                    found = true;
	                }            }        }
	    }
	    return found;

	}

	
	 public static String dateOfBirthFrmt(String dob, String frmFrmt, String toFrmt) { //dd/mm/yyyy to yyyymmdd
			SimpleDateFormat formFormat = null;
			SimpleDateFormat toFormat = null;
			String todateStr = null;
			try {
				formFormat = new SimpleDateFormat(frmFrmt);
				toFormat = new SimpleDateFormat(toFrmt);
				Date frmdateDt = formFormat.parse(dob);
				todateStr = toFormat.format(frmdateDt);
			} catch (Exception e) {
				
			}
			return todateStr;
		}

	public static String getReqid() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date datObj = new Date();
		SecureRandom rand = new SecureRandom();
		// int randomPIN = (int) (Math.random() * 9000) + 1000;
		int randomPIN = rand.nextInt(100000000);
		String rquid = format.format(datObj) + String.valueOf(randomPIN);
		if (StringUtils.isNoneBlank(rquid)) {
			if (rquid.length() < 22) {
				System.out.println("rquid.length() : " + rquid.length());
				// log.info("rquid.length() : {}", rquid.length() );
				String randInc = "";
				for (int i = 0; i < (22 - rquid.length()); i++) {
					randInc += "0";
				}
				String totalInc = "1" + randInc;
				// log.info("totalInc : [{}]",totalInc);
				System.out.println("totalInc :  " + totalInc);
				int randomPIN1 = rand.nextInt((Integer.parseInt(totalInc)));
				rquid = rquid + randomPIN1;
				System.out.println("IF Request Id : " + rquid);
			}
		}
		return rquid;
	}
	
	public static String removeATMKeywordStart(String pramStr) {
		String finalStr = null;
		if(StringUtils.isNotBlank(pramStr)) {
			String regex1 = "ATM$";
		
			finalStr = pramStr.replaceAll(regex1, "");
		
			
			
			finalStr = finalStr.trim();
			System.out.println("---------" + finalStr);
		}
		return finalStr;
	}
	

}

package com.fisglobal.fsg.dip.core.service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fisglobal.fsg.dip.core.config.MnrlLoadData;
import com.fisglobal.fsg.dip.core.utils.Constants;
import com.fisglobal.fsg.dip.core.utils.DateTimeFormatSupport;

@Component
public class CommonMethodUtils {
	private final static Logger LOGGER = LoggerFactory.getLogger(CommonMethodUtils.class);
	public static Map<String, Object> latchReqMap = new HashMap<String, Object>();
	public static Map<String, Object> latchResMap = new HashMap<String, Object>();

	@Inject
	Environment env;

	/*
	 * @Autowired private TransactionRepo transRepo;
	 */

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final SecureRandom RANDOM = new SecureRandom();

	public static int getStringSizeInKB(String str) {
		// Convert string to bytes using UTF-8 encoding
		byte[] byteArray = str.getBytes(java.nio.charset.StandardCharsets.UTF_8);
		// Calculate size in KB
		int sizeInBytes = byteArray.length;
		int sizeInKB = (int) Math.ceil(sizeInBytes / 1024.0);
		return sizeInKB;
	}

	public String toChangeDateFormat(String toDateFormat, Date dateObj) {
		SimpleDateFormat sdft = new SimpleDateFormat(toDateFormat);
		String strDate = sdft.format(dateObj);
		return strDate;
	}

	public static String appdenzeroOnTime(String timeVar) {
		String timeVall = timeVar;
		if (StringUtils.isBlank(timeVar) || timeVar.contains("NA") || timeVar.contains("N/A")) {
			timeVall = "000000";
		} else {
			if (StringUtils.isNotBlank(timeVar)) {
				if (timeVar.length() < 6) {
					for (int i = 1; i <= 6 - timeVar.length(); i++) {
						timeVall += "0";
					}
				}
			}
		}
		return timeVall;
	}

	public static String changeTimeFormat(String timeVar) {
		if (StringUtils.isNotBlank(timeVar)) {
			Date parsedDate = DateTimeFormatSupport.parseDate(timeVar);
			return new SimpleDateFormat(Constants.HH_mm_ss).format(parsedDate);
		} else {
			return "00:00:00";
		}
	}

	public static String toChageDateFrom2ToFrmt(String dob, String frmFrmt, String toFrmt) { // dd/mm/yyyy to yyyymmdd
		SimpleDateFormat formFormat = null;
		SimpleDateFormat toFormat = null;
		String todateStr = null;
		Date frmdateDt = null;
		try {
			formFormat = new SimpleDateFormat(frmFrmt);
			toFormat = new SimpleDateFormat(toFrmt);
			frmdateDt = formFormat.parse(dob);
			todateStr = toFormat.format(frmdateDt);
		} catch (Exception e) {

		} finally {
			formFormat = null;
			toFormat = null;
			frmdateDt = null;
		}
		return todateStr;
	}

	public static Date dateFormatToDate(String dob, String frmFrmt) { // dd/mm/yyyy to yyyymmdd
		SimpleDateFormat formFormat = null;
		Date frmdateDt = null;
		try {
			formFormat = new SimpleDateFormat(frmFrmt);
			frmdateDt = formFormat.parse(dob);
			// todateStr = toFormat.format(frmdateDt);
		} catch (Exception e) {

		} finally {
			formFormat = null;
		}
		return frmdateDt;
	}

	public static String toChageTimeFrom2ToFrmt(String dob, String frmFrmt, String toFrmt) { // dd/mm/yyyy to yyyymmdd
		SimpleDateFormat formFormat = null;
		SimpleDateFormat toFormat = null;
		String todateStr = null;
		Date frmdateDt = null;
		try {
			if (StringUtils.isNotBlank(dob)) {
				formFormat = new SimpleDateFormat(frmFrmt);
				toFormat = new SimpleDateFormat(toFrmt);
				frmdateDt = formFormat.parse(dob);
				todateStr = toFormat.format(frmdateDt);
			} else {
				todateStr = null;
			}
		} catch (Exception e) {

		} finally {
			formFormat = null;
			toFormat = null;
			frmdateDt = null;
		}
		return todateStr;
	}

	public Integer toGetRequestMQCount() {
		Integer reqCont = 0;
		if (CommonMethodUtils.latchReqMap != null) {
			reqCont = CommonMethodUtils.latchReqMap.size();
			LOGGER.debug("Request - ActiveMQ Count Test : [{}]", reqCont);
		}
		return reqCont;

	}

	public Integer toGetResposeMQCount() {
		Integer resCont = 0;
		if (CommonMethodUtils.latchResMap != null) {
			resCont = CommonMethodUtils.latchResMap.size();
			LOGGER.debug("Response - ActiveMQ Count Test : [{}]", resCont);
		}
		return resCont;
	}

	public String getRanNumber() {
		SecureRandom rand = new SecureRandom();
		int randomPIN = rand.nextInt(10000);
		String rquid = String.valueOf(randomPIN);
		if (StringUtils.isNoneBlank(rquid)) {
			if (rquid.length() < 4) {
				String randInc = "";
				for (int i = 0; i < (4 - rquid.length()); i++) {
					randInc += "0";
				}
				String totalInc = "1" + randInc;
				// System.out.println("totalInc : " + totalInc);
				int randomPIN1 = rand.nextInt((Integer.parseInt(totalInc)));
				rquid = rquid + randomPIN1;
				// System.out.println("IF Request Id : " + rquid);

			}
		}
		System.out.println("Random ---->" + rquid);

		return rquid;

	}

	public String getInteractionId() {
		SimpleDateFormat format = new SimpleDateFormat(Constants.yyyyMMddHHmmss);
		Date datObj = new Date();
		SecureRandom rand = new SecureRandom();
		// int randomPIN = (int) (Math.random() * 9000) + 1000;
		int randomPIN = rand.nextInt(100000000);
		String rquid = format.format(datObj) + String.valueOf(randomPIN);
		if (StringUtils.isNotBlank(rquid)) {
			if (rquid.length() < 22) {
				// System.out.println("rquid.length() : " + rquid.length());
				LOGGER.debug("rquid.length() : [{}]", rquid.length());
				String randInc = "";
				for (int i = 0; i < (22 - rquid.length()); i++) {
					randInc += "0";
				}
				String totalInc = "1" + randInc;
				LOGGER.debug("totalInc : [{}]", totalInc);
				// System.out.println("totalInc : " + totalInc);
				int randomPIN1 = rand.nextInt((Integer.parseInt(totalInc)));
				rquid = rquid + randomPIN1;
				// System.out.println("IF Request Id : " + rquid);
				LOGGER.debug("IF Request Id : [{}]", totalInc);
			}
		}

		if (StringUtils.isNotBlank(rquid) && rquid.length() < 22) {
			for (int i = 0; i < (22 - rquid.length()); i++) {
				rquid += "0";
			}
		}
		LOGGER.info("::::: INTERACTION ID - [{}] Length : [{}]::::::: ", rquid, rquid.length());
		return rquid;
	}

	public static List<String> findMatches(List<String> options, String query) {
		List<String> matches = new ArrayList<>();
		for (String option : options) {
			if (StringUtils.containsIgnoreCase(option, query)) {
				matches.add(option);
			}
		}
		return matches;
	}

	public String removeLeadingZeros(String digits) {
		if (StringUtils.isNotBlank(digits)) {
			// String.format("%.0f", Double.parseDouble(digits)) //Alternate Solution
			boolean zeroValidation = digits.startsWith("0000");
			if (zeroValidation) {
				String regex = "^0+";
				return digits.replaceAll(regex, "");
			} else {

				return digits;
			}

		} else {
			return digits;
		}
	}

	public String removeSELFKeywordStart(String pramStr) {
		String finalStr = null;
		if (StringUtils.isNotBlank(pramStr)) {
			String regex1 = "^SELF\\+\\-+";
			String regex5 = "^SELF\\-\\++";
			String regex2 = "^SELF\\++";
			String regex3 = "^SELF\\-+";
			String regex4 = "^SELF+";
			String regex6 = "^\\-+";
			String regex7 = "^\\++";
			finalStr = pramStr.replaceAll(regex1, "");
			finalStr = pramStr.replaceAll(regex5, "");
			finalStr = finalStr.replaceAll(regex2, "");
			finalStr = finalStr.replaceAll(regex3, "");

			if (finalStr.startsWith("SELF")) {
				finalStr = finalStr.replaceAll(regex4, "");
			}
			if (finalStr.startsWith("-")) {
				finalStr = finalStr.replaceAll(regex6, "");
			}
			if (finalStr.startsWith("+")) {
				finalStr = finalStr.replaceAll(regex7, "");
			}

			finalStr = finalStr.trim();
			System.out.println("---------" + finalStr);
		}
		return finalStr;
	}

	public String removeATMKeywordStart(String pramStr) {
		String finalStr = null;
		if (StringUtils.isNotBlank(pramStr)) {
			String regex1 = "ATM$";
			finalStr = pramStr.replaceAll(regex1, "");
			finalStr = finalStr.trim();
			System.out.println("---------" + finalStr);
		}
		return finalStr;
	}

	public String getToDateFromBetwnDate(String paramFromDate, String paranTodate, Integer paramWithInNoDay) {
		Calendar fromCaldr = null, toCaldr = null;
		String finalTodate = null;
		try {
			fromCaldr = Calendar.getInstance();
			fromCaldr.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(paramFromDate));
			toCaldr = Calendar.getInstance();
			toCaldr.setTime(new SimpleDateFormat("dd-MM-yyyy").parse(paranTodate));
			boolean isWithInDays = isBetweenDateCount(fromCaldr, toCaldr, paramWithInNoDay);
			if (isWithInDays) {
				finalTodate = paranTodate;
			} else {
				fromCaldr.add(Calendar.DAY_OF_YEAR, paramWithInNoDay - 1);
				finalTodate = new SimpleDateFormat("dd-MM-yyyy").format(fromCaldr.getTime());
			}
		} catch (Exception e) {
			finalTodate = paranTodate;
		} finally {
			fromCaldr = null;
			toCaldr = null;
		}
		return finalTodate;
	}

	public boolean isBetweenDateCount(Calendar date1, Calendar date2, Integer noDiffDay) {
		// Calculate the difference in milliseconds
		long diffInMillis = Math.abs(date2.getTimeInMillis() - date1.getTimeInMillis());
		// Convert milliseconds to days
		long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
		// Check if the difference is exactly no. days
		return diffInDays <= noDiffDay;
	}

	public boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			// ex.printStackTrace();
		}
		return date != null;
	}

	public String formatDate(String inDate) {
		SimpleDateFormat inSDF = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String outDate = "";
		if (inDate != null) {
			try {
				Date date = inSDF.parse(inDate);
				outDate = outSDF.format(date);
			} catch (ParseException ex) {
			}
		}
		return outDate;
	}
	/*
	 * public static void main (String args[]) {
	 * 
	 * System.out.println(isValidFormat("yyyy-MM-dd HH:mm:ss","08/04/2025"));
	 * if(!isValidFormat("yyyy-MM-dd HH:mm:ss","08/04/2025")) {
	 * System.out.println(formatDate("08/04/2025")); } }
	 */

	public String getMobileNumberWithoutCC(String mobile) {

		if (mobile.length() == 12) {
			mobile = mobile.substring(2);
		}
		LOGGER.debug("Mobile [{}]", mobile);
		return mobile;
	}

	public String getMobileNumberWithCC(String mobile) {

		if (mobile.length() == 10) {
			mobile = "91" + mobile;
		}
		LOGGER.debug("Mobile [{}]", mobile);
		return mobile;
	}

	public String getCountryCode() {
		String countryCode = "91";
		if (StringUtils.isNotBlank(MnrlLoadData.propdetails.getAppProperty().getCountryCode())) {
			countryCode = MnrlLoadData.propdetails.getAppProperty().getCountryCode();
		}
		return countryCode;
	}

	/*public String generateRandomString() {

		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 12; i++) {
			int index = RANDOM.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(index));
		}
		return sb.toString();
	}*/
	
	public String generateRandom() {

		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			int index = RANDOM.nextInt(CHARACTERS.length());
			sb.append(CHARACTERS.charAt(index));
		}
		return sb.toString();
	}

	public String getUuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public JsonNode getByPath(JsonNode root, String dotPath) {
		JsonNode cur = root;
		for (String key : dotPath.split("\\.")) {
			if (cur == null)
				return null;
			cur = cur.get(key);
		}
		return cur;
	}

	public String typeOf(JsonNode node) {
		if (node == null || node.isMissingNode())
			return "MISSING";
		if (node.isTextual())
			return "STRING";
		if (node.isObject())
			return "OBJECT";
		if (node.isArray())
			return "ARRAY";
		if (node.isNumber())
			return "NUMBER";
		if (node.isBoolean())
			return "BOOLEAN";
		if (node.isNull())
			return "NULL";
		return "UNKNOWN";
	}

	public  String getDateTime() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYYMMddHHmmss");
		return dateFormat.format(LocalDateTime.now());
	}
	public  String generateRandomString() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("Y");
		String year = dateFormat.format(LocalDateTime.now());
		year = year.substring(year.length() - 1);
		String julian = String.format("%03d", LocalDate.now().getDayOfYear());
		String hhStr = getDateTime().substring(8, 10);
		return year + julian + hhStr + generateRandom();
	}

	public String getCurrentDateAsString() {
		SimpleDateFormat SDFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String curr_date = SDFormat.format(cal.getTime());
		LOGGER.info("current date [{}]", curr_date);
		return curr_date;
	}
}

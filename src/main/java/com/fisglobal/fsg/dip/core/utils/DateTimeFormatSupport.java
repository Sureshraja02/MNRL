package com.fisglobal.fsg.dip.core.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateTimeFormatSupport {
	private static final List<String> dateFormats = new ArrayList<>();

	static {
		dateFormats.add("yyyy-MM-dd");
		dateFormats.add("dd/MM/yyyy");
		dateFormats.add("MM-dd-yyyy");
		dateFormats.add("yyyy-MM-dd HH:mm:ss");
		dateFormats.add("dd MMM yyyy HH:mm:ss");
		dateFormats.add("HH:mm:ss");
		dateFormats.add("hh:mm");
		dateFormats.add("hh:mm:ss");
		
	}

	public static Date parseDate(String dateString) {
		for (String format : dateFormats) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
				return sdf.parse(dateString);
			} catch (ParseException ignored) {
				// Try next format
			}
		}
		throw new IllegalArgumentException("Unknown date format: " + dateString);
	}

	public static String formatDateTime(LocalDateTime dateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
		return dateTime.format(formatter);
	}

	public static String formatZonedDateTime(ZonedDateTime dateTime, String format) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH);
		return dateTime.format(formatter);
	}

	public static void main(String[] args) {
		String[] testDates = { "2024-01-31", "31/01/2024", "01-31-2024", "2024-01-31 14:30:00", "31 Jan 2024 14:30:00",
				"Wed, 31 Jan 2024 14:30:00 +0000" };

		for (String dateString : testDates) {
			try {
				Date parsedDate = parseDate(dateString);
				System.out.println("Parsed: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parsedDate));
			} catch (Exception e) {
				System.out.println("Failed to parse: " + dateString);
			}
		}

		LocalDateTime now = LocalDateTime.now();
		System.out.println("Formatted LocalDateTime: " + formatDateTime(now, "dd MMM yyyy HH:mm:ss"));
		
		String timestr="05:12:40";
		Date parsedDate = parseDate(timestr);
		System.out.println("Parsed: " + new SimpleDateFormat("HH:mm:ss").format(parsedDate));
	}
}

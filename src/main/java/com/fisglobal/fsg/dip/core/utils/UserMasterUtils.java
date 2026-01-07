package com.fisglobal.fsg.dip.core.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

@Service
public class UserMasterUtils {
	
	public boolean checkpassworddate(LocalDateTime passwordDate) {
		LocalDateTime now = LocalDateTime.now();
		LocalDate secureTermDat = passwordDate.toLocalDate();
		System.out.println(ChronoUnit.DAYS.between(LocalDate.now(), secureTermDat));
		if(ChronoUnit.DAYS.between(LocalDate.now(), secureTermDat) > 30) {
			return false;
		}
		System.out.println(now.minusDays(30));
		return true;
	}

}

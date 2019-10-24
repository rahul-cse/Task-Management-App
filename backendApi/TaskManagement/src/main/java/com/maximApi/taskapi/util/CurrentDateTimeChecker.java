package com.maximApi.taskapi.util;

import java.time.LocalDateTime;

public class CurrentDateTimeChecker {

	public boolean check(LocalDateTime ldt){
		LocalDateTime currentDateTime = LocalDateTime.now();
		if(ldt==null || ldt.isAfter(currentDateTime)  ) {
			return true;
		}
		else {
			return false;
		}
			
	}
}

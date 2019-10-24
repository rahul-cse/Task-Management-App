package com.maximApi.taskapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DateRangeException  extends RuntimeException{

	String startdate;
	
	String endDate;

	public DateRangeException(String startdate, String endDate) {
		super(String.format("end-date %s is not greater than start-date %s" ,endDate,startdate));
		this.startdate = startdate;
		this.endDate = endDate;
	}
	
	public DateRangeException(String startdate, String endDate, String s) {
		super(String.format(startdate +" or"+endDate+" "+s));
		this.startdate = startdate;
		this.endDate = endDate;
	}

	public String getStartdate() {
		return startdate;
	}


	public String getEndDate() {
		return endDate;
	}


	
}

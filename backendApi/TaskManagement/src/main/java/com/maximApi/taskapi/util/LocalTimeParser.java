package com.maximApi.taskapi.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class LocalTimeParser {

	public DateTimeFormatter getTimeParser(){
		DateTimeFormatter format = new DateTimeFormatterBuilder()
		        .appendPattern("yyyy-MM-dd[ HH:mm:ss]")
		        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
		        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		        .toFormatter();
		return format;
	}
	
	
	
	
}

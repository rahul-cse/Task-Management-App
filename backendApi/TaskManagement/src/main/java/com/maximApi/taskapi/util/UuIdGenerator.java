package com.maximApi.taskapi.util;

import java.util.UUID;

public class UuIdGenerator {
 
	String uuId;
	
	public String getUuid() {
		return uuId = UUID.randomUUID().toString();
	}
}


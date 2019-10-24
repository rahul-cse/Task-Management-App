package com.maximApi.taskapi.service;

import com.maximApi.taskapi.model.AppUser;

public interface AppUserService {

	void createAppUser(AppUser appUser);
	
	AppUser findAppUser(String uuId);
	
	boolean isExistByUuid(String s);
}

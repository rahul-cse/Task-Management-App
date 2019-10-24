package com.maximApi.taskapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.repository.AppUserRepository;
import com.maximApi.taskapi.service.AppUserService;
import com.maximApi.taskapi.util.UuIdGenerator;

@Service
public class AppUserServiceImpl implements AppUserService{
	
	
	AppUserRepository appUserRepository;
	
	
	@Autowired
	public AppUserServiceImpl(AppUserRepository appUserRepository) {
		this.appUserRepository = appUserRepository;
	}



	@Override
	public void createAppUser(AppUser appUser) {
		// TODO Auto-generated method stub
		UuIdGenerator uuid = new UuIdGenerator();
		appUser.setUuId(uuid.getUuid());
		appUserRepository.save(appUser);
	}



	@Override
	public AppUser findAppUser(String uuId) {
		// TODO Auto-generated method stub
		return (AppUser) appUserRepository.findByUuId(uuId);
	}



	@Override
	public boolean isExistByUuid(String uuId) {
		// TODO Auto-generated method stub
		AppUser appUser = appUserRepository.findByUuId(uuId);
		if(appUser!=null)
			return true;
		else
			return false;
	}

}

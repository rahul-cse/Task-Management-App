package com.maximApi.taskapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.maximApi.taskapi.model.AppUser;


public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	AppUser findByUuId(String uuId);

}

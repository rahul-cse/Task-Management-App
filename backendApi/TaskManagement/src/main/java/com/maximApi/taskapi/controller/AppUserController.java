package com.maximApi.taskapi.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maximApi.taskapi.model.AppUser;
import com.maximApi.taskapi.model.payload.ApiResponse;
import com.maximApi.taskapi.service.AppUserService;

@RestController
@RequestMapping("api/task")
@CrossOrigin("*")
public class AppUserController {
	
	@Autowired
	AppUserService appUserService;

	private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
	
	@PostMapping("create-appUser")
	public ResponseEntity<ApiResponse> createAppUser(@Valid @RequestBody AppUser appUser) {
		System.out.println(appUser.getUserName()+appUser.getPassword());
		appUserService.createAppUser(appUser);
		return ResponseEntity.ok(new ApiResponse(true, appUser));
		
	}
	
}

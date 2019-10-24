package com.maximApi.taskapi.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.maximApi.taskapi.service.AppUserService;
import com.maximApi.taskapi.validation.annotation.ValidUuid;



public class ValidUuIdValidator implements ConstraintValidator<ValidUuid, String>{

	@Autowired
	AppUserService appUserService;
	
	@Override
	public boolean isValid(String str, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return  appUserService.isExistByUuid(str);
	}

}

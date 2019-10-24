package com.maximApi.taskapi.validation.annotation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.maximApi.taskapi.validation.validator.ValidUuIdValidator;

@Documented
@Constraint(validatedBy = ValidUuIdValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUuid {

	public String message() default " Invalid UUID !";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};

}

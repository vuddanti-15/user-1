package com.user.custom.annotation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.StringUtils;

import com.user.util.UserUtil;

public class CustomMobileNumberValidation implements ConstraintValidator<ValidInternationalMobileNumber, String> {

	private String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";

	@Override
	public boolean isValid(String mobileNumber, ConstraintValidatorContext context) {
		if(StringUtils.hasText(mobileNumber)) {
			return UserUtil.isValidPattern(regex, mobileNumber);
		}else{
			return true;
		}
	}

}

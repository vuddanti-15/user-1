package com.user.custom.annotation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.user.configuration.ApplicationProperties;
import com.user.util.UserUtil;

public class CustomEmailValidator implements ConstraintValidator<ValidEmail, String> {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Override
	public boolean isValid(String emailId, ConstraintValidatorContext context) {
		if (StringUtils.hasText(emailId)) {
			return UserUtil.isValidPattern(applicationProperties.getEmailPattern(), emailId);
		} else {
			return true;
		}
	}

}

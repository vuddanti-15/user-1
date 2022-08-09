package com.user.custom.annotation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.user.configuration.ApplicationProperties;
import com.user.util.UserUtil;

public class CustomAadharValidator implements ConstraintValidator<ValidAadharNumber, String> {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Override
	public boolean isValid(String aadharNmber, ConstraintValidatorContext context) {
		if (StringUtils.hasText(aadharNmber)) {
			return UserUtil.isValidPattern(applicationProperties.getAadharPattern(), aadharNmber);
		} else {
			return true;
		}
	}

}

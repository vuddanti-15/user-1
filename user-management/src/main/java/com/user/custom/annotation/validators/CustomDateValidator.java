package com.user.custom.annotation.validators;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.user.configuration.ApplicationProperties;

public class CustomDateValidator implements ConstraintValidator<ValidDate, String> {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Override
	public boolean isValid(String date, ConstraintValidatorContext context) {
		if (StringUtils.hasText(date)) {
			try {
				DateFormat df = new SimpleDateFormat(applicationProperties.getDateFormat());
				df.setLenient(false);
				df.parse(date);
				return true;
			} catch (ParseException e) {
				return false;
			}
		} else {
			return true;
		}

	}

}

package com.user.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.user.configuration.ApplicationProperties;

@Component
public class UserUtilComponent {

	@Autowired
	private ApplicationProperties applicationProperties;

	public Date convertStringToDate(String dateInString) {
		SimpleDateFormat formatter = new SimpleDateFormat(applicationProperties.getDateFormat(), Locale.ENGLISH);
		Date date = null;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			date = new Date();
		}
		return date;
	}

	public String encodePassword(String password) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public String getLoggedInUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) authentication.getPrincipal();
		if (userDetail != null) {
			return userDetail.getUsername();
		} else {
			return "";
		}

	}

}

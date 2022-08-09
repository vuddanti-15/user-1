package com.user.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

public class UserUtil {

	public static String getTrimmedText(String text) {
		if (StringUtils.hasText(text)) {
			return text.trim();
		} else {
			return text;
		}
	}

	public static boolean isValidPattern(String regExpression, String input) {
		if (StringUtils.hasText(input)) {
			Pattern pattern = Pattern.compile(regExpression);
			Matcher matcher = pattern.matcher(input);
			return matcher.matches();
		} else {
			return false;

		}

	}

}

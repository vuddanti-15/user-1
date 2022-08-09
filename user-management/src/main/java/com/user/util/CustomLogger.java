package com.user.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {

	public static Logger addLoggerClass(Class<?> clazz) {
		final Logger logger = LoggerFactory.getLogger(clazz);
		return logger;
	}
	

}

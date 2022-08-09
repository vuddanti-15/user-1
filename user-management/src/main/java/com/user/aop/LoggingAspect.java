package com.user.aop;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.user.util.CustomLogger;

@Aspect
@Component
public class LoggingAspect {

	private Logger logger = CustomLogger.addLoggerClass(LoggingAspect.class);

	@Around("logAllMethods()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
		try {
			Object result = joinPoint.proceed();
			logger.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
					joinPoint.getSignature().getName(), result);
			long timeTaken = System.currentTimeMillis() - startTime;
			logger.info("Time Taken by {} is {}", joinPoint, timeTaken);
			return result;
		} catch (BadRequest e) {
			throw e;
		} catch (ConstraintViolationException e) {
			throw e;
		} catch (DataIntegrityViolationException e) {
			throw e;
		} catch (Exception e) {
			String methodName = joinPoint.getTarget().getClass().getName() + "-" + joinPoint.getSignature().getName()
					+ "(" + Arrays.toString(joinPoint.getArgs()) + ")";
			logger.error("Error In method " + methodName, e);
			throw e;
		}
	}

	@Pointcut("execution(* com.user.controller.*.*(..))")
	private void logAllMethods() {

	}
}

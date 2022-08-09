package com.user.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError errorResponse = addCustomErrorMessages(ex);
		return handleExceptionInternal(ex, errorResponse, headers, errorResponse.getStatus(), request);
	}

	private ApiError addCustomErrorMessages(MethodArgumentNotValidException ex) {
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			ErrorMessage errorMessage = new ErrorMessage();
			errorMessage.setFieldName(error.getField());
			errorMessage.setMessage(error.getDefaultMessage());
			errorMessages.add(errorMessage);
		}
		ApiError errorResponse = new ApiError(HttpStatus.BAD_REQUEST, errorMessages, "Invalid Input", null, new Date());
		return errorResponse;
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<ErrorMessage> requestDTOErrors = new ArrayList<ErrorMessage>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String propertyPath = String.valueOf(violation.getPropertyPath());
			String fieldName = propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
			String message = violation.getMessage();
			ErrorMessage requestDTOError = new ErrorMessage();
			requestDTOError.setMessage(message);
			requestDTOError.setFieldName(fieldName);
			requestDTOErrors.add(requestDTOError);
		}
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, requestDTOErrors, "Invalid Input", null, new Date());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String description = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		String message = ex.getLocalizedMessage();
		ApiError apiError = constructApiError(description, message, HttpStatus.NOT_FOUND);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	private ApiError constructApiError(String description, String message, HttpStatus httpStatus) {
		ApiError apiError = new ApiError(httpStatus, message, description, new Date());
		return apiError;
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String description = ex.getParameterName() + " parameter is missing";
		String message = ex.getLocalizedMessage();
		ApiError apiError = constructApiError(description, message, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			WebRequest request) {
		String description = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		ApiError apiError = constructApiError(description, ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
		ApiError apiError = constructApiError(builder.toString(), ex.getLocalizedMessage(),
				HttpStatus.METHOD_NOT_ALLOWED);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
		ApiError apiError = constructApiError(builder.substring(0, builder.length() - 2), ex.getLocalizedMessage(),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable mostSpecificCause = ex.getMostSpecificCause();
		String message = mostSpecificCause.getMessage();
		String dtoClassName = message.substring(message.indexOf("through reference chain:"));
		String columnName = dtoClassName.substring(dtoClassName.indexOf("["));
		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(columnName);
		List<ErrorMessage> requestDTOErrors = new ArrayList<ErrorMessage>();
		while (m.find()) {
			ErrorMessage requestDTOError = new ErrorMessage();
			requestDTOError.setFieldName(m.group(1));
			requestDTOError.setMessage("Invalid input");
			requestDTOErrors.add(requestDTOError);
		}
		ApiError errorResponse = new ApiError(HttpStatus.BAD_REQUEST, requestDTOErrors, "Invalid Input",
				ex.getLocalizedMessage(), new Date());
		return new ResponseEntity<Object>(errorResponse, new HttpHeaders(), errorResponse.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = constructApiError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError apiError = constructApiError(ex.getMessage(), ex.getLocalizedMessage(),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = constructApiError(ex.getMessage(), ex.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/******** User defined exceptions **************/

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {
		String message = ex.getMessage();
		String customMessage = "";
		if (message.contains("user_name_unique_index")) {
			customMessage = "Duplicate emailId";
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			ErrorMessage errorMessage = new ErrorMessage("emailId", customMessage);
			errorMessages.add(errorMessage);
			ApiError apiError = new ApiError(HttpStatus.CONFLICT, errorMessages, "Invalid Input", null, new Date());
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
		} else {
			ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
					String.valueOf(ex.getMostSpecificCause()), new Date());
			return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
		}

	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<ApiError> handleNullPointerException(NullPointerException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE, "Null Pointer",
				String.valueOf(ex.getStackTrace()[0]), new Date());
		return new ResponseEntity<ApiError>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {
		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(),
				String.valueOf(ex.getCause()), new Date());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiError> resourceNotFoundException(EntityNotFoundException ex, WebRequest request) {
		ApiError apiError = constructApiError(ex.getMessage(), ex.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
	}

}

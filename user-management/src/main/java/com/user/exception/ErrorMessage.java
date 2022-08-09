package com.user.exception;

import lombok.Data;

@Data
public class ErrorMessage {
	
	public ErrorMessage() {
		
	}
	
	public ErrorMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	private String fieldName;
	
	private String message;

}

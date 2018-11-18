package com.mobiquity.exception;

public class APIException extends Exception {
	
	public static final String INVALID_PATTERN = "Package Description Is Written In The Wrong Format";
	public static final String FILE_NOT_FOUND = "File Not Found";
	public static final String INVALID_PARAMS = "indexNumber, weight, cost should be numbers";

	public APIException() {
		super();
	}
	
	public APIException(String message) {
		super(message);
	}

}

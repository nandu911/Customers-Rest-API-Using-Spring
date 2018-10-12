package com.springdemo.exceptionhandling;

public class CustomersEmptyException extends RuntimeException {

	public CustomersEmptyException() {
		// TODO Auto-generated constructor stub
	}

	public CustomersEmptyException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CustomersEmptyException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CustomersEmptyException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CustomersEmptyException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}

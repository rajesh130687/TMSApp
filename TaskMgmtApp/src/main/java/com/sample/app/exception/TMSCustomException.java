package com.sample.app.exception;

@SuppressWarnings("serial")
public class TMSCustomException extends Exception {

	private String message = null;

	public TMSCustomException() {
		super();
	}

	public TMSCustomException(String message) {
		super(message);
		this.message = message;
	}

	public TMSCustomException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
package com.fmo.wom.common.exception;

public class WOMAuthorizationException extends WOMProcessException {

	public WOMAuthorizationException() { }

	public WOMAuthorizationException(String message) {
		super(message);
	}

	public WOMAuthorizationException(Throwable cause) {
		super(cause);
	}

	public WOMAuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}
}

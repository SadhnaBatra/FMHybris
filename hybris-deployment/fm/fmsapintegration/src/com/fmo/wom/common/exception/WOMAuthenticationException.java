package com.fmo.wom.common.exception;

public class WOMAuthenticationException extends WOMValidationException {

	public WOMAuthenticationException() { }

	public WOMAuthenticationException(String message) {
		super(message);
	}

	public WOMAuthenticationException(Throwable cause) {
		super(cause);
	}

	public WOMAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMAuthenticationException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMAuthenticationException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMAuthenticationException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMAuthenticationException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}

}

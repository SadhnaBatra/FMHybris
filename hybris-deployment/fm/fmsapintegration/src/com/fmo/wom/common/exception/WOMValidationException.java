package com.fmo.wom.common.exception;

public class WOMValidationException extends WOMBaseCheckedException {

	public WOMValidationException() { }

	public WOMValidationException(String message) {
		super(message);
	}

	public WOMValidationException(Throwable cause) {
		super(cause);
	}

	public WOMValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMValidationException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMValidationException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMValidationException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMValidationException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

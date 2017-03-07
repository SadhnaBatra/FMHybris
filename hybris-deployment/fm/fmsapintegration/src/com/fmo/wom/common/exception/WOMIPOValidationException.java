package com.fmo.wom.common.exception;

public class WOMIPOValidationException extends WOMValidationException {

	public WOMIPOValidationException() { }

	public WOMIPOValidationException(String message) {
		super(message);
	}

	public WOMIPOValidationException(Throwable cause) {
		super(cause);
	}

	public WOMIPOValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMIPOValidationException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMIPOValidationException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMIPOValidationException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMIPOValidationException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
}

package com.fmo.wom.common.exception;

public class WOMIPOClientException extends WOMValidationException{

	public WOMIPOClientException() { super(); }
	
	public WOMIPOClientException(String message) {
		super(message);
	}

	public WOMIPOClientException(Throwable cause) {
		super(cause);
	}

	public WOMIPOClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMIPOClientException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMIPOClientException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMIPOClientException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMIPOClientException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

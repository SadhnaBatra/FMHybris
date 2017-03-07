package com.fmo.wom.common.exception;

public class WOMConnectionException extends WOMResourceException {

	public WOMConnectionException() { }

	public WOMConnectionException(String message) {
		super(message);
	}

	public WOMConnectionException(Throwable cause) {
		super(cause);
	}

	public WOMConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMConnectionException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMConnectionException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMConnectionException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMConnectionException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

package com.fmo.wom.common.exception;

public class WOMBusinessAccessException extends WOMProcessException {

	public WOMBusinessAccessException() { }

	public WOMBusinessAccessException(String message) {
		super(message);
	}

	public WOMBusinessAccessException(Throwable cause) {
		super(cause);
	}

	public WOMBusinessAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMBusinessAccessException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMBusinessAccessException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMBusinessAccessException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMBusinessAccessException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

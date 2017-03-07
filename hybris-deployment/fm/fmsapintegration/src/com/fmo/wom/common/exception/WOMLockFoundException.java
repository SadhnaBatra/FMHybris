package com.fmo.wom.common.exception;

public class WOMLockFoundException extends WOMBaseCheckedException {

	public WOMLockFoundException() { }

	public WOMLockFoundException(String message) {
		super(message);
	}

	public WOMLockFoundException(Throwable cause) {
		super(cause);
	}

	public WOMLockFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMLockFoundException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMLockFoundException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMLockFoundException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMLockFoundException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

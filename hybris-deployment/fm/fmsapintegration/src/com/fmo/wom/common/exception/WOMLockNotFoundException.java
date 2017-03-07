package com.fmo.wom.common.exception;

public class WOMLockNotFoundException extends WOMBaseCheckedException {

	public WOMLockNotFoundException() { }

	public WOMLockNotFoundException(String message) {
		super(message);
	}

	public WOMLockNotFoundException(Throwable cause) {
		super(cause);
	}

	public WOMLockNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMLockNotFoundException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMLockNotFoundException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMLockNotFoundException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMLockNotFoundException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

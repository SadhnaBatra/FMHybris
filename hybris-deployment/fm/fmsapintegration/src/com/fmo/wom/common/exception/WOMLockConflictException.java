package com.fmo.wom.common.exception;

public class WOMLockConflictException extends WOMBaseCheckedException {

	public WOMLockConflictException() { }

	public WOMLockConflictException(String message) {
		super(message);
	}

	public WOMLockConflictException(Throwable cause) {
		super(cause);
	}

	public WOMLockConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMLockConflictException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMLockConflictException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMLockConflictException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMLockConflictException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

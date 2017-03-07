package com.fmo.wom.common.exception;

public class WOMResourceException extends WOMBaseCheckedException {

	public WOMResourceException() { }

	public WOMResourceException(String message) {
		super(message);
	}

	public WOMResourceException(Throwable cause) {
		super(cause);
	}

	public WOMResourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMResourceException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMResourceException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMResourceException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMResourceException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

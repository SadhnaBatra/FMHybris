package com.fmo.wom.common.exception;

public class WOMMailException extends WOMResourceException {

	public WOMMailException() { }

	public WOMMailException(String message) {
		super(message);
	}

	public WOMMailException(Throwable cause) {
		super(cause);
	}

	public WOMMailException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMMailException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMMailException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMMailException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMMailException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

package com.fmo.wom.common.exception;

public class WOMProcessException extends WOMBaseCheckedException {

	public WOMProcessException() { }

	public WOMProcessException(String message) {
		super(message);
	}

	public WOMProcessException(Throwable cause) {
		super(cause);
	}

	public WOMProcessException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMProcessException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMProcessException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMProcessException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMProcessException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

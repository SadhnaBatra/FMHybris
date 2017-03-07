package com.fmo.wom.common.exception;

public class WOMIPOServerException extends WOMResourceException {

	public WOMIPOServerException() { }

	public WOMIPOServerException(String message) {
		super(message);
	}

	public WOMIPOServerException(Throwable cause) {
		super(cause);
	}

	public WOMIPOServerException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMIPOServerException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMIPOServerException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMIPOServerException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMIPOServerException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

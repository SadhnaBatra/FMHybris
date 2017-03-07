package com.fmo.wom.common.exception;

public class WOMNoResultException extends WOMPersistanceException {

	public WOMNoResultException() { }

	public WOMNoResultException(String message) {
		super(message,false);
	}

	public WOMNoResultException(Throwable cause) {
		super(cause,false);
	}

	public WOMNoResultException(String message, Throwable cause) {
		super(message, cause,false);
	}

	public WOMNoResultException(boolean doSelfLog) {
		super(false);
	}

	public WOMNoResultException(String message, boolean doSelfLog) {
		super(message, false);
	}

	public WOMNoResultException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, false);
	}

	public WOMNoResultException(Throwable cause, boolean doSelfLog) {
		super(cause, false);
	}
}

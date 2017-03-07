package com.fmo.wom.common.exception;

public class WOMPersistanceException extends WOMIntegrationException {

	public WOMPersistanceException() { }

	public WOMPersistanceException(String message) {
		super(message);
	}

	public WOMPersistanceException(Throwable cause) {
		super(cause);
	}

	public WOMPersistanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMPersistanceException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMPersistanceException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMPersistanceException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMPersistanceException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

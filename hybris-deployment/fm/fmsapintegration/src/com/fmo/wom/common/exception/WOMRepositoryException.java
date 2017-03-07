package com.fmo.wom.common.exception;

public class WOMRepositoryException extends WOMConnectionException {

	public WOMRepositoryException() { }

	public WOMRepositoryException(String message) {
		super(message);
	}

	public WOMRepositoryException(Throwable cause) {
		super(cause);
	}

	public WOMRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMRepositoryException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMRepositoryException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMRepositoryException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMRepositoryException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

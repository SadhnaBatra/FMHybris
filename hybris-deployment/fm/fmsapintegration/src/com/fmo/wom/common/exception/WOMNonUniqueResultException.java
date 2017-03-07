package com.fmo.wom.common.exception;

public class WOMNonUniqueResultException extends WOMPersistanceException {

	public WOMNonUniqueResultException() { }

	public WOMNonUniqueResultException(String message) {
		super(message);
	}

	public WOMNonUniqueResultException(Throwable cause) {
		super(cause);
	}

	public WOMNonUniqueResultException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMNonUniqueResultException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMNonUniqueResultException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMNonUniqueResultException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMNonUniqueResultException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

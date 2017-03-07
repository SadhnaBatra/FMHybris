package com.fmo.wom.common.exception;

public class WOMBusinessCreditHoldException extends WOMBusinessAccessException {

	public WOMBusinessCreditHoldException() { }

	public WOMBusinessCreditHoldException(String message) {
		super(message);
	}

	public WOMBusinessCreditHoldException(Throwable cause) {
		super(cause);
	}

	public WOMBusinessCreditHoldException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMBusinessCreditHoldException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMBusinessCreditHoldException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMBusinessCreditHoldException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMBusinessCreditHoldException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

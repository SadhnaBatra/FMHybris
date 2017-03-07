package com.fmo.wom.common.exception;

public class WOMTransactionException extends WOMIntegrationException {

	public WOMTransactionException() { }

	public WOMTransactionException(String message) {
		super(message);
	}

	public WOMTransactionException(Throwable cause) {
		super(cause);
	}

	public WOMTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMTransactionException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMTransactionException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMTransactionException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMTransactionException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
}

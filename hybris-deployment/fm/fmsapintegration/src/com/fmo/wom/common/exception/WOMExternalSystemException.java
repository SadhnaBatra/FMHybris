package com.fmo.wom.common.exception;

public class WOMExternalSystemException extends WOMConnectionException {
	
	public WOMExternalSystemException() { }

	public WOMExternalSystemException(String message) {
		super(message);
	}

	public WOMExternalSystemException(Throwable cause) {
		super(cause);
	}

	public WOMExternalSystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMExternalSystemException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMExternalSystemException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMExternalSystemException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMExternalSystemException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

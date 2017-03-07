package com.fmo.wom.common.exception;

public class WOMIntegrationException extends WOMResourceException {

	public WOMIntegrationException() { }

	public WOMIntegrationException(String message) {
		super(message);
	}

	public WOMIntegrationException(Throwable cause) {
		super(cause);
	}

	public WOMIntegrationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMIntegrationException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMIntegrationException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMIntegrationException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMIntegrationException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

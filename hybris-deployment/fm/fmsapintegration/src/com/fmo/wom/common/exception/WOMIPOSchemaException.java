package com.fmo.wom.common.exception;

public class WOMIPOSchemaException extends WOMIPOValidationException {

	public WOMIPOSchemaException() { }

	public WOMIPOSchemaException(String message) {
		super(message);
	}

	public WOMIPOSchemaException(Throwable cause) {
		super(cause);
	}

	public WOMIPOSchemaException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMIPOSchemaException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMIPOSchemaException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMIPOSchemaException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMIPOSchemaException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

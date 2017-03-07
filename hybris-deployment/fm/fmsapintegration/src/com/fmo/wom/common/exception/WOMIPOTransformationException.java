package com.fmo.wom.common.exception;

public class WOMIPOTransformationException extends WOMIPOValidationException {

	public WOMIPOTransformationException() { }

	public WOMIPOTransformationException(String message) {
		super(message);
	}

	public WOMIPOTransformationException(Throwable cause) {
		super(cause);
	}

	public WOMIPOTransformationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMIPOTransformationException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMIPOTransformationException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMIPOTransformationException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMIPOTransformationException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

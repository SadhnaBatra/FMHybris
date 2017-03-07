package com.fmo.wom.common.exception;

public class WOMCommunicationException extends WOMBaseCheckedException {

	public WOMCommunicationException() { }

	public WOMCommunicationException(String message) {
		super(message);
	}

	public WOMCommunicationException(Throwable cause) {
		super(cause);
	}

	public WOMCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMCommunicationException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMCommunicationException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMCommunicationException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMCommunicationException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

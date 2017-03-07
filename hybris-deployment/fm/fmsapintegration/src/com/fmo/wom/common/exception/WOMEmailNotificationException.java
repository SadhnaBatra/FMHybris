package com.fmo.wom.common.exception;

public class WOMEmailNotificationException extends WOMCommunicationException {

	public WOMEmailNotificationException() { }

	public WOMEmailNotificationException(String message) {
		super(message);
	}

	public WOMEmailNotificationException(Throwable cause) {
		super(cause);
	}

	public WOMEmailNotificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMEmailNotificationException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMEmailNotificationException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMEmailNotificationException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMEmailNotificationException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

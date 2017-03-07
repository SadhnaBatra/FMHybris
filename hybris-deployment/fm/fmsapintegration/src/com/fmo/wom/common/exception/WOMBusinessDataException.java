package com.fmo.wom.common.exception;

public class WOMBusinessDataException extends WOMProcessException{
	
	public WOMBusinessDataException() { }

	public WOMBusinessDataException(String message) {
		super(message);
	}

	public WOMBusinessDataException(Throwable cause) {
		super(cause);
	}

	public WOMBusinessDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMBusinessDataException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMBusinessDataException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMBusinessDataException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMBusinessDataException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	

}

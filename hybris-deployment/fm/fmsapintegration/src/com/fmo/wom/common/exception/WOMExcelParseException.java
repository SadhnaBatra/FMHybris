package com.fmo.wom.common.exception;

public class WOMExcelParseException extends WOMBaseCheckedException {

	public WOMExcelParseException() { }

	public WOMExcelParseException(String message) {
		super(message);
	}

	public WOMExcelParseException(Throwable cause) {
		super(cause);
	}

	public WOMExcelParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMExcelParseException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMExcelParseException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMExcelParseException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMExcelParseException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
	
	
}

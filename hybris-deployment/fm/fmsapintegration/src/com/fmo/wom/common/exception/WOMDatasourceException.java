package com.fmo.wom.common.exception;

public class WOMDatasourceException extends WOMConnectionException {

	public WOMDatasourceException() { }

	public WOMDatasourceException(String message) {
		super(message);
	}

	public WOMDatasourceException(Throwable cause) {
		super(cause);
	}

	public WOMDatasourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public WOMDatasourceException(boolean doSelfLog) {
		super(doSelfLog);
	}

	public WOMDatasourceException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public WOMDatasourceException(String message, Throwable cause,
			boolean doSelfLog) {
		super(message, cause, doSelfLog);
	}

	public WOMDatasourceException(Throwable cause, boolean doSelfLog) {
		super(cause, doSelfLog);
	}
}

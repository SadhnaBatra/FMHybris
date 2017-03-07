package com.fmo.wom.common.exception;

public class WOMBusinessInvoiceViewException extends WOMBusinessAccessException {

	public WOMBusinessInvoiceViewException() { }

	public WOMBusinessInvoiceViewException(String message) {
		super(message);
	}

	public WOMBusinessInvoiceViewException(Throwable cause) {
		super(cause);
	}

	public WOMBusinessInvoiceViewException(String message, Throwable cause) {
		super(message, cause);
	}
}

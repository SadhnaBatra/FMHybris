package com.fmo.wom.common.exception;

import org.apache.log4j.Logger;

public class GATPCreditCheckException extends WOMExternalSystemException {

	protected static Logger logger = Logger.getLogger(GATPCreditCheckException.class);
	
	private static final long serialVersionUID = 1L;

	public GATPCreditCheckException() {
		super();
	}

	public GATPCreditCheckException(String message, boolean doSelfLog) {
		super(message, doSelfLog);
	}

	public GATPCreditCheckException(String message) {
		super(message);
		setLogged(true);
		logger.error("");
	}

}

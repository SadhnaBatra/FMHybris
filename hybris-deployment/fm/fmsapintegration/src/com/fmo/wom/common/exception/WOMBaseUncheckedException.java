/**
 * 
 */
package com.fmo.wom.common.exception;

import org.apache.log4j.Logger;





/**
 * @author amarnr85
 *
 */
public class WOMBaseUncheckedException extends RuntimeException {

	protected static Logger logger = Logger.getLogger(WOMBaseUncheckedException.class);
	private boolean logged = false;
	
	public WOMBaseUncheckedException() {
		log();
	}

	/**
	 * Construct this exception with instructions on logging.
	 * @param doSelfLog if false, this exception will not log itself upon construction
	 */
	public WOMBaseUncheckedException(boolean doSelfLog) {
		
		if (doSelfLog)
			log();
	}

	public WOMBaseUncheckedException(String message) {
		super(message);
		log();
	}
	
	/**
	 * Construct this exception with instructions on logging.
	 * @param message
	 * @param doSelfLog if false, this exception will not log itself upon construction
	 */
	public WOMBaseUncheckedException(String message, boolean doSelfLog) {
		super(message);
		
		if (doSelfLog)
			log();
	}

	public WOMBaseUncheckedException(Throwable cause) {
		super(cause);
		
		// being contructed with a Throwable to wrap - set logged appropriately
		setLogged(cause);
		
		log();
	}
	
	/**
	 * Construct this exception with instructions on logging.
	 * @param cause
	 * @param doSelfLog if false, this exception will not log itself upon construction
	 */
	public WOMBaseUncheckedException(Throwable cause, boolean doSelfLog) {
		super(cause);
		
		// Because we are getting instructions on whether to self log, we do not care what is in 
		// the Throwable.  
		if (doSelfLog) 
			log();
	}
	
	public WOMBaseUncheckedException(String message, Throwable cause) {
		super(message, cause);
		
		// being constructed with a Throwable to wrap - set logged appropriately
		setLogged(cause);
		
		log();
	}
	
	/**
	 * Construct this exception with instructions on logging.
	 * @param message
	 * @param cause
	 * @param doSelfLog if false, this exception will not log itself upon construction
	 */
	public WOMBaseUncheckedException(String message, Throwable cause, boolean doSelfLog) {
		super(message, cause);
		
		// Because we are getting instructions on whether to self log, we do not care what is in 
		// the Throwable.  
		if (doSelfLog) 
			log();
	}
	
	/**
	 * Check if we have logged ourselves
	 * @return true if logged; false otherwise
	 */
	public boolean isLogged() {
		return logged;
	}
	
	/**
	 * Override the logged flag.  Providing a means to reset logging if necessary
	 */
	public void setLogged(boolean isLogged) {
		this.logged = isLogged;
	}
	
	/**
	 * make sure we set logged if someone externally sets the cause
	 */
	@Override
	public Throwable initCause(Throwable cause) {
		setLogged(cause);
		return super.initCause(cause);
	}

	/**
	 * Log myself if I haven't already.
	 */
	private void log() {
		if (logged)
			return;
		
		logger.error(getMessage(), this);
		logged = true;
	}

	
	/**
	 * If the exception we are wrapping is an instance of the our Base Unchecked, this means someone
	 * has constructed a new exception with an existing one that has been logged.  This is an odd
	 * scenario, but we'll use the logged status of the existing exception.
	 * 
	 * Note this is not checking if the instance is the WOMBaseCecked - in that situation, someone
	 * has purposely changed from checked to unchecked, so I'll log it.
	 * @param cause the wrapped exception
	 */
	private void setLogged(Throwable cause) {
		
		if (cause instanceof WOMBaseUncheckedException) {
			logged = ((WOMBaseUncheckedException)cause).isLogged();
		}
	}
}

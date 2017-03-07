package com.falcon.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RegistrationFailureException extends Exception{


	public RegistrationFailureException(String message) {
		super(message);
	}

	public RegistrationFailureException(Throwable cause) {
		super(cause);
	}

}

/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : CRMResponseDTO.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.dto;

/**
 * CRM Integration response DTO class.
 * 
 */
public class CRMResponseDTO
{

	private String errorCode;
	private String responseCode;

	/**
	 * @return the responseCode
	 */
	public String getResponseCode()
	{
		return responseCode;
	}

	/**
	 * @param responseCode
	 *           the responseCode to set
	 */
	public void setResponseCode(final String responseCode)
	{
		this.responseCode = responseCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @param errorCode
	 *           the errorCode to set
	 */
	public void setErrorCode(final String errorCode)
	{
		this.errorCode = errorCode;
	}

}

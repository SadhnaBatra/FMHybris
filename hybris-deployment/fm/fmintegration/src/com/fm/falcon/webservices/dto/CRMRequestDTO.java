/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : CRMRequestDTO.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.dto;

/**
 * CRM Integration request DTO class.
 * 
 */
public class CRMRequestDTO
{

	/** Field for serviceName */
	private String serviceName;

	/**
	 * Method to retrieve service name
	 * 
	 * @return the serviceName
	 */
	public String getServiceName()
	{
		return serviceName;
	}

	/**
	 * Method to set service name
	 * 
	 * @param serviceName
	 *           the serviceName to set
	 */
	public void setServiceName(final String serviceName)
	{
		this.serviceName = serviceName;
	}
}

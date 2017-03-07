/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : AbstractSoapMessage.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */

package com.fm.falcon.webservices.soap;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.CRMResponseDTO;


/**
 * Interface method for creating SOAP message
 * 
 */
public interface AbstractSoapMessage
{

	/**
	 * Abstract method for posting the message to external system and gets the parsed response object
	 * 
	 * @param crmRequestDTO
	 *           Request transfer object
	 * @return CRM response transfer object
	 * @throws UnsupportedOperationException
	 * @throws SOAPException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public CRMResponseDTO sendSOAPMessage(final CRMRequestDTO crmRequestDTO) throws UnsupportedOperationException, SOAPException,
			IOException, ClassNotFoundException;

}

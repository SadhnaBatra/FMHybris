/**
 * 
 */
package com.federalmogul.facades.account;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import com.fm.falcon.webservices.dto.LicensePlateResponseDTO;
import com.fm.falcon.webservices.dto.VinLookupResponseDTO;


/**
 * @author SU276498
 * 
 */
public interface FMVinLookupFacade
{

	/**
	 * @param vin
	 * @throws UnsupportedOperationException
	 * @throws ClassNotFoundException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public VinLookupResponseDTO vinLookup(String vin) throws UnsupportedOperationException, ClassNotFoundException, SOAPException,
			IOException;

	/**
	 * 
	 * @param lincesePlate
	 * @param State
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws ClassNotFoundException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public LicensePlateResponseDTO licensePlate(String lincesePlate, String states) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;


}

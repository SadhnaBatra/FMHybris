/**
 * 
 */
package com.federalmogul.facades.account;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import com.federalmogul.facades.order.data.FMReturnOrderData;


/**
 * @author Balaji
 * 
 */
public interface FMCustomerAccountReturnOrderFacade
{

	/**
	 * @param fmReturnItemsData
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws ClassNotFoundException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public FMReturnOrderData createCrmReturnOrder(FMReturnOrderData fmReturnItemsData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;

	/**
	 * @param fmReturnOrderData
	 * @throws UnsupportedOperationException
	 * @throws ClassNotFoundException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public FMReturnOrderData createReturn(FMReturnOrderData fmReturnOrderData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;


}

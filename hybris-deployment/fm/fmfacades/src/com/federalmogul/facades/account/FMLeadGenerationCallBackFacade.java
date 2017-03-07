/**
 * 
 */
package com.federalmogul.facades.account;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import com.federalmogul.core.model.FMLeadGenerationCallBackModel;
import com.federalmogul.facades.customer.data.FMLeadGenerationCallBackData;


/**
 * @author Balaji
 * 
 */
public interface FMLeadGenerationCallBackFacade
{

	/**
	 * @param fmReturnItemsData
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws ClassNotFoundException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public FMLeadGenerationCallBackModel createCrmCallBack(FMLeadGenerationCallBackData fMLeadGenerationCallBackData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException;

	/**
	 * @param fmReturnOrderData
	 * @throws UnsupportedOperationException
	 * @throws ClassNotFoundException
	 * @throws SOAPException
	 * @throws IOException
	 */
	public void createCallBack(FMLeadGenerationCallBackData fMLeadGenerationCallBackData) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;


}

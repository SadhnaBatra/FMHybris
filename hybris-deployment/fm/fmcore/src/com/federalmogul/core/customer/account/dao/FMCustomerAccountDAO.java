/**
 * 
 */
package com.federalmogul.core.customer.account.dao;

import de.hybris.platform.core.model.c2l.RegionModel;

import java.util.List;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMTaxDocumentModel;



/**
 * @author sai
 * 
 */
public interface FMCustomerAccountDAO
{


	/**
	 * 
	 * @return FMTaxDocumentsModel
	 */
	public List<FMTaxDocumentModel> getAllFMTaxDocuments(String searchTaxDocName, String sortOption);

	/**
	 * 
	 * @param taxDocPK
	 * @return
	 */
	public FMTaxDocumentModel getFMTaxDocumentsForPK(String taxDocPK);


	FMCustomerAccountModel getFMB2BUnit(final String uid);

	/**
	 * @param fmmodel
	 * @param regionModel
	 * @return
	 */
	public boolean validateStateWiseTaxDocument(FMCustomerModel fmmodel, RegionModel regionModel);

}

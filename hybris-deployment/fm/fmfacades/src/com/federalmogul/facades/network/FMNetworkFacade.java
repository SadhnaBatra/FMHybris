/**
 * 
 */
package com.federalmogul.facades.network;

import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;


/**
 * @author su244261
 * 
 */
public interface FMNetworkFacade extends CompanyB2BCommerceFacade
{
	FMCustomerData getFMCustomerDataForUid(String uid);


	CustomerNameStrategy getCustomerNameStrategy();


	/**
	 * @param fmCustomerData
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */
	void updateFMUser(FMCustomerData fmCustomerData) throws DuplicateUidException, UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException;


	/**
	 * @return
	 */
	FMCustomerData getCurrentFMCustomer();


	/**
	 * @param fmCustomerData
	 * @param addressData
	 * @throws DuplicateUidException
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */
	void updateFMUser(FMCustomerData fmCustomerData, AddressData addressData,boolean passwordUpdate) throws DuplicateUidException,
			UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException;

	public SearchPageData<CustomerData> getAllFMUsers(final PageableData pageableData);

	public SearchPageData<CustomerData> getAllFMUsersByName(final PageableData pageableData, String userName);

	public SearchPageData<B2BUnitData> getAllFMAccounts(final PageableData pageableData);


	/**
	 * @return
	 */
	List<B2BUnitData> getAllFMAccounts();


	/**
	 * @return
	 */
	Map<String, String> getAllFMAccountList();


	/**
	 * @param unitId
	 * @return
	 */
	B2BUnitData getUnitForUID(String unitId);







	/**
	 * @param pageableData
	 * @param unit
	 * @return
	 */
	SearchPageData<CustomerData> getUsersforB2bAdmin(PageableData pageableData, String unit);


	/**
	 * @return
	 */
	List<FMCustomerAccountData> getAllAccountsforCSR();

	public void enableCustomer(final String uid) throws DuplicateUidException;

	/**
	 * @param accountId
	 * @return
	 */
	FMCustomerAccountData getAccountID(String accountId);

	/**
	 * @param companyName
	 * @param line1
	 * @param townCity
	 * @param postcode
	 * @param region
	 */
	List<FMCustomerAccountData> fetchAddress(String companyName, String line1, String townCity, String postcode, String region);

	/**
	 * @param accountId
	 * @return
	 */
	FMCustomerAccountData getAccountByNabs(String accountId);


	/**
	 * @param unitUid
	 * @return
	 */
	List<String> getAllFMAccountsByID(String unitUid);

	/**
	 * Method to retrieve the Ship Tos for a given Sold To
	 * 
	 * @param uid
	 *           Sold To
	 * @return List of Ship Tos
	 */
	List<FMCustomerAccountData> getAdminUserUnits(final String uid);

}

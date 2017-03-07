/**
 * 
 */
package com.federalmogul.core.network;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.List;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.user.data.FMCustomerAccountData;



/**
 * @author su244261
 * 
 */
public interface FMNetworkService extends CompanyB2BCommerceService
{

	<T extends FMCustomerModel> T getFMCustomerForUid(String uid);

	public SearchPageData<B2BCustomerModel> getAllFMUsers(final PageableData pageableData);

	public SearchPageData<B2BUnitModel> getAllFMAccounts(final PageableData pageableData);

	/**
	 * @return
	 */
	List<B2BUnitModel> getAllFMAccounts();

	/**
	 * @param unitId
	 * @return
	 */
	B2BUnitModel getUnitForUID(String unitId);

	public SearchPageData<B2BCustomerModel> getAllFMUsersByName(final PageableData pageableData, String userName);

	/**
	 * @param pageableData
	 * @param unit
	 * @return
	 */
	SearchPageData<B2BCustomerModel> getUsersforB2bAdmin(PageableData pageableData, String unit);

	/**
	 * @return
	 */
	List<FMCustomerAccountModel> getAllAccountsforCSR();

	/**
	 * @param accountId
	 * @return
	 */
	FMCustomerAccountModel getAccountID(String accountId);

	/**
	 * @param companyName
	 * @param line1
	 * @param townCity
	 * @param postcode
	 * @param region
	 * @return
	 */
	List<FMCustomerAccountModel> fetchAddress(String companyName, String line1, String townCity, String postcode, String region);

	/**
	 * @param uid
	 * @throws DuplicateUidException
	 */
	void enableCustomer(String uid) throws DuplicateUidException;

	/**
	 * @param accountId
	 * @return
	 */
	FMCustomerAccountModel getAccountByNabs(String accountId);

	/**
	 * @param unitUid
	 * @return
	 */
	List<B2BUnitModel> getAllFMAccountsByID(String unitUid);

	/**
	 * Method to retrieve the Ship Tos for a given Sold To
	 * 
	 * @param uid
	 *           Sold To
	 * @return List of Ship Tos
	 */
	List<FMCustomerAccountData> getAdminUserUnits(final String uid);
	
	public List<FMCustomerModel> getAllFMCustomers();

}

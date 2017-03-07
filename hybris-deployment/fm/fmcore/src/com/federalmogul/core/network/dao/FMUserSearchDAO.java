/**
 * 
 */
package com.federalmogul.core.network.dao;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.model.B2BUserGroupModel;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.List;

import com.federalmogul.core.graph.form.CSBProductPermissionForm;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.falcon.core.model.CPL1CustomerModel;
import com.federalmogul.falcon.core.model.CSBPercents3612Model;
import com.federalmogul.falcon.core.model.CategorySalesBenchmarkPercentsModel;
import com.federalmogul.falcon.core.model.FMCsrAccountListModel;


/**
 * @author su244261
 * 
 */
public interface FMUserSearchDAO
{
	public FMCustomerModel getUserForUID(String userId);

	public List<FMCustomerAccountModel> getprospectSoldToUsers();

	public SearchPageData<B2BCustomerModel> getAllFMUsers(final PageableData pageableData);

	public SearchPageData<B2BUnitModel> getAllFMAccounts(final PageableData pageableData);

	public boolean checkUidForID(String email);

	List<B2BUnitModel> getAllFMAccounts();

	public AddressModel getAddressByID(String addressID);

	public boolean checkTaxForID(String taxId);

	public boolean checkProspectid(String userUid);

	B2BUnitModel getUnitForUID(String unitId);

	/**
	 * @param uid
	 * @return
	 */
	public List<AddressData> getSoldToUnitAddress(String uid);

	/**
	 * @param uid
	 * @return
	 */
	public List<FMCustomerAccountModel> getSoldToShipToUnitUid(String uid);


	public SearchPageData<B2BCustomerModel> getAllFMUsersByName(final PageableData pageableData, String userName);

	/**
	 * @param pageableData
	 * @param unitUid
	 * @return
	 */
	SearchPageData<B2BCustomerModel> getUsersforB2bAdmin(PageableData pageableData, String unitUid);

	/**
	 * @return
	 */
	public List<FMCustomerAccountModel> getAllAccountsforCSR();

	/**
	 * @param companyName
	 * @param line1
	 * @param townCity
	 * @param postcode
	 * @param region
	 * @return
	 */
	public List<FMCustomerAccountModel> fetchAddress(String companyName, String line1, String townCity, String postcode,
			String region);



	/**
	 * @param accountId
	 * @return
	 */
	public FMCustomerAccountModel getAccountID(String accountId);

	public List<FMCustomerAccountModel> getB2BShipToAddressForSoldTo(final String inputString, final String accountID,
			final String searchType, String loggedUserType);

	public B2BUserGroupModel getGroupByID(String groupID);

	List<FMCsrAccountListModel> getFMCSRAccountList(String userID);

	public List<FMCustomerAccountModel> getB2BAddressSearch(String searchString);

	/**
	 * @param accountId
	 * @return
	 */
	public FMCustomerAccountModel getAccountByNabs(String accountId);

	/**
	 * @param uid
	 * @return
	 */
	List<B2BUnitModel> getAllFMAccountsByID(String uid);

	List<FMCustomerAccountModel> getAdminUserUnits(final String uid);


	public List<CSBProductPermissionForm> getPermissions(String soldToAccount);

	public List<CategorySalesBenchmarkPercentsModel> getUnitorSaleChangePercentsModel(String cpl1Code);

	public String getDesc(String productCode);

	/**
	 * @param inputProductCode
	 * @return
	 */
	public CSBPercents3612Model getYearOverYearComparison(String inputProductCode);

	/**
	 * @return
	 */
	public List<CPL1CustomerModel> getAllProducts();

	/**
	 * @param uid
	 * @return
	 */
	public String checkSoldToShipto(String uid);
	
	public List<FMCustomerModel> getAllFMCustomers();

}

/**
 * 
 */
package com.federalmogul.core.account;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorservices.customer.B2BCustomerAccountService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.List;

import com.federalmogul.core.model.FMCustomerModel;


/**
 * @author SR279690
 * 
 */
public interface FMCustomerAccountService extends B2BCustomerAccountService
{


	public void updateFMProfile(FMCustomerModel fmcustomerModel, String titleCode, String name, String login)
			throws DuplicateUidException;

	/**
	 * @param currentCustomer
	 * @return
	 */
	public List<AddressModel> getFMAddressBookEntries(FMCustomerModel currentCustomer);

	/**
	 * @param currentCustomer
	 * @return
	 */
	public AddressModel getFMDefaultAddress(FMCustomerModel currentCustomer);

	/**
	 * @param fmCustomerModel
	 * @param oldPassword
	 * @param newPassword
	 */
	public void changeFMPassword(FMCustomerModel fmCustomerModel, String oldPassword, String newPassword)
			throws PasswordMismatchException;

	public void fmUpdatePassword(String token, String newPassword) throws TokenInvalidatedException;

	/**
	 * @param pageableData
	 * @return
	 */
	SearchPageData<B2BUnitModel> getAdminAddressBookEntries(PageableData pageableData);

	/**
	 * @param unitUid
	 * @return
	 */
	B2BUnitModel getUnitForUid(String unitUid);

	/**
	 * @param addressID
	 * @return
	 */
	AddressModel getAddressByID(String addressID);

	/**
	 * @param customerModel
	 * @param addressModel
	 */
	/* void saveAdminAddressEntry(B2BUnitModel customerModel, AddressModel addressModel); */

	/**
	 * @param addressModel
	 */
	void saveAdminAddressEntry(AddressModel addressModel);

	/**
	 * @param addressModel
	 */
	void removeAdminAddress(AddressModel addressModel);
}

/**
 * 
 */
package com.federalmogul.core.account;

import de.hybris.platform.b2bacceleratorservices.customer.B2BCustomerAccountService;
import de.hybris.platform.commercefacades.user.data.AddressData;

import java.util.List;

import com.federalmogul.core.graph.form.CSBProductPermissionForm;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.falcon.core.model.CPL1CustomerModel;
import com.federalmogul.falcon.core.model.CSBPercents3612Model;
import com.federalmogul.falcon.core.model.CategorySalesBenchmarkPercentsModel;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
 

/**
 * @author SI279688
 * 
 *         Interface for FmCustomer service for registration
 */
public interface FMCustomerService extends B2BCustomerAccountService
{
	public void createCustomerAccount(FMCustomerModel fmcustomerModel, String password);

	/**
	 * @param email
	 * @return
	 */
	public boolean checkUidExists(String email);

	public boolean checkTaxIdExists(String taxId);

	public boolean checkProspectid(String userUid);

	/**
	 * @param uid
	 * @return
	 */
	public List<AddressData> getSoldToUnitAddress(String uid);

	/**
	 * @param uid
	 * @return
	 */
	public List<FMCustomerAccountData> getSoldToShipToUnitUid(String uid);

	public List<FMCustomerAccountModel> getB2BAddressSearch(String searchString);


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

	public void createReportLog(FMCustomerAccountModel accModel,FMCustomerModel custModel,String eventType);

} 

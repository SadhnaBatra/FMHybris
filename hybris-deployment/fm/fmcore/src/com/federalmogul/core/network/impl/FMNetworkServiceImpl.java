/**
 * 
 */
package com.federalmogul.core.network.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorservices.company.impl.DefaultCompanyB2BCommerceService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.network.FMNetworkService;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.facades.user.data.FMCustomerAccountData;


/**
 * @author su244261
 * 
 */
public class FMNetworkServiceImpl extends DefaultCompanyB2BCommerceService implements FMNetworkService
{

	@Resource
	FMUserSearchDAO fmUserSearchDAO;

	private static final Logger LOG = Logger.getLogger(FMNetworkServiceImpl.class);

	@Override
	public <T extends FMCustomerModel> T getFMCustomerForUid(final String uid)
	{
		//return (T) fmUserSerachDAO.getUserForUID(uid, FMCustomerModel.class);


		return (T) fmUserSearchDAO.getUserForUID(uid);
	}

	@Override
	public SearchPageData<B2BCustomerModel> getAllFMUsers(final PageableData pageableData)
	{
		LOG.info("---------4444444444444444-------------");


		return fmUserSearchDAO.getAllFMUsers(pageableData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.core.network.FMNetworkService#getUsersforB2bAdmin(de.hybris.platform.commerceservices.search.
	 * pagedata.PageableData, de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData)
	 */
	@Override
	public SearchPageData<B2BCustomerModel> getUsersforB2bAdmin(final PageableData pageableData, final String unit)
	{
		return fmUserSearchDAO.getUsersforB2bAdmin(pageableData, unit);
	}

	@Override
	public SearchPageData<B2BCustomerModel> getAllFMUsersByName(final PageableData pageableData, final String userName)
	{
		LOG.info("---------4444444444444444-------------");


		return fmUserSearchDAO.getAllFMUsersByName(pageableData, userName);
	}

	/*
	 * 
	 * @Override public SearchPageData<B2BCustomerModel> getPagedCustomers(final PageableData pageableData) {
	 * 
	 * return getPagedB2BCustomerDao().findPagedCustomers("byName", pageableData); }
	 */
	@Override
	public SearchPageData<B2BUnitModel> getAllFMAccounts(final PageableData pageableData)
	{
		return fmUserSearchDAO.getAllFMAccounts(pageableData);
	}

	@Override
	public List<B2BUnitModel> getAllFMAccounts()
	{
		return fmUserSearchDAO.getAllFMAccounts();
	}

	@Override
	public List<B2BUnitModel> getAllFMAccountsByID(final String unitUid)
	{
		return fmUserSearchDAO.getAllFMAccountsByID(unitUid);
	}

	@Override
	public B2BUnitModel getUnitForUID(final String unitId)
	{
		LOG.info("**********fmUserSearchDAO.getUnitForUID(unitId)::" + fmUserSearchDAO.getUnitForUID(unitId));
		return fmUserSearchDAO.getUnitForUID(unitId);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.FMNetworkService#getAllAccountsforCSR()
	 */
	@Override
	public List<FMCustomerAccountModel> getAllAccountsforCSR()
	{
		return fmUserSearchDAO.getAllAccountsforCSR();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.FMNetworkService#getAccountID(java.lang.String)
	 */
	@Override
	public FMCustomerAccountModel getAccountID(final String accountId)
	{

		return fmUserSearchDAO.getAccountID(accountId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.FMNetworkService#fetchAddress(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<FMCustomerAccountModel> fetchAddress(final String companyName, final String line1, final String townCity,
			final String postcode, final String region)
	{
		return fmUserSearchDAO.fetchAddress(companyName, line1, townCity, postcode, region);

	}

	@Override
	public void enableCustomer(final String uid) throws DuplicateUidException
	{
		LOG.info("INSIDE   FMNETWORKSERVICEFACADEIMPL");
		final B2BCustomerModel customerModel = getCustomerForUid(uid);
		//validateParameterNotNullStandardMessage("B2BCustomer", uid);
		//	customerModel.setActive(Boolean.TRUE);
		customerModel.setLoginDisabled(false);
		this.saveModel(customerModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.FMNetworkService#getAccountByNabs(java.lang.String)
	 */
	@Override
	public FMCustomerAccountModel getAccountByNabs(final String accountId)
	{
		return fmUserSearchDAO.getAccountByNabs(accountId);
	}

	/**
	 * Method to retrieve the Ship Tos for a given Sold To
	 * 
	 * @param uid
	 *           Sold To
	 * @return List of Ship Tos
	 */

	@Override
	public List<FMCustomerAccountData> getAdminUserUnits(final String uid)
	{

		final List<FMCustomerAccountModel> listGetAdminUserUnitsModel = fmUserSearchDAO.getAdminUserUnits(uid);
		final List<FMCustomerAccountData> listGetFMUnitUidData = new ArrayList<FMCustomerAccountData>();

		if (listGetAdminUserUnitsModel != null)
		{
			final Iterator i = listGetAdminUserUnitsModel.iterator();
			while (i.hasNext())
			{
				final FMCustomerAccountData fmUnitUidData = new FMCustomerAccountData();
				final FMCustomerAccountModel fMUnitUidModel = (FMCustomerAccountModel) i.next();
				fmUnitUidData.setUid(fMUnitUidModel.getUid());
				listGetFMUnitUidData.add(fmUnitUidData);
			}

		}
		return listGetFMUnitUidData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.FMNetworkService#getAllFMCustomers()
	 */
	@Override
	public List<FMCustomerModel> getAllFMCustomers()
	{
		return fmUserSearchDAO.getAllFMCustomers();
	}
}

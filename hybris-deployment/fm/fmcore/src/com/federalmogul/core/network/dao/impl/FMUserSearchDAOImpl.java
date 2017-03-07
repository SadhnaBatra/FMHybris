/**
 * 
 */
package com.federalmogul.core.network.dao.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.model.B2BUserGroupModel;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SortData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.util.FMUtils;

import com.federalmogul.core.graph.form.CSBProductPermissionForm;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMCustomerPartnerFunctionModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.falcon.core.model.CPL1CustomerModel;
import com.federalmogul.falcon.core.model.CSBCustomerGroupModel;
import com.federalmogul.falcon.core.model.CSBPercents3612Model;
import com.federalmogul.falcon.core.model.CategorySalesBenchmarkCustomerModel;
import com.federalmogul.falcon.core.model.CategorySalesBenchmarkPercentsModel;
import com.federalmogul.falcon.core.model.FMCsrAccountListModel;


/**
 * @author su244261
 * 
 */
public class FMUserSearchDAOImpl implements FMUserSearchDAO
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getUserForUID(java.lang.String, java.lang.Class)
	 */

	@Resource
	private FlexibleSearchService flexibleSearchService;

	private Populator<AddressModel, AddressData> addressPopulator;

	private static final Logger LOG = Logger.getLogger(FMUserSearchDAOImpl.class);

	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}

	@Override
	public FMCustomerModel getUserForUID(final String userId)
	{

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerModel.PK + "}");
		query.append("FROM {" + FMCustomerModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerModel.UID + "} ='" + userId + "'");

		LOG.info("query ::" + query);

		flexibleSearchService = getFlexibleSearchService();
		//final SearchResult<FMCustomerModel> result = flexibleSearchService.search(query.toString(), params);
		final SearchResult<FMCustomerModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerModel> fmCustomerList = result.getResult();
			final FMCustomerModel fmCustomer = fmCustomerList.iterator().next();
			return fmCustomer;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}

	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}



	/**
	 * Method that retrieves all the users for admin view.
	 * 
	 * @return List of customer model.
	 */
	@Override
	public SearchPageData<B2BCustomerModel> getAllFMUsers(final PageableData pageableData)
	{
		LOG.info("---------55555555555555-------------" + pageableData);

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerModel.PK + "}");
		query.append("FROM {" + FMCustomerModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerModel.UID + "} NOT IN (" + Config.getParameter("excludeAdminIDs") + ")");
		query.append(" Order By ");
		query.append(" {" + FMCustomerModel.NAME + "} " + pageableData.getSort());
		query.append(", {" + FMCustomerModel.LASTNAME + "} " + pageableData.getSort());

		flexibleSearchService = getFlexibleSearchService();
		LOG.info("---------6666666666666666-------------query::" + query + "\n--------------query.toString()::" + query.toString());
		final SearchResult<FMCustomerModel> result = flexibleSearchService.search(query.toString());
		LOG.info("---------7777777777777777777-------------" + result);
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("---------8888888888888888 Resuult contains value-------------" + result);
			LOG.info("---------55555555555555------------- size " + pageableData.getPageSize());
			final SearchPageData searchResult = createSearchPageData();
			searchResult.setResults(result.getResult());
			searchResult.setPagination(createPagination(pageableData, result));

			LOG.info("--------99999999999999999999------Returning All the customers from DAO " + result.getResult().size());
			LOG.info("---------1010101010101010--------" + searchResult);
			return searchResult;
		}
		else
		{
			LOG.info("---------8888888888888888 Resuult NULL-------------" + result);
			LOG.error("No Customers Found");
			return null;
		}

	}

	@Override
	public SearchPageData<B2BCustomerModel> getUsersforB2bAdmin(final PageableData pageableData, final String unitUid)
	{

		LOG.info("UNITuID" + unitUid);

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerModel.PK + "}");
		query.append(" FROM {" + FMCustomerModel._TYPECODE + " AS cust");
		query.append(" JOIN " + FMCustomerAccountModel._TYPECODE + " AS acc");
		query.append(" ON {cust." + FMCustomerModel.DEFAULTB2BUNIT + "} = {acc." + FMCustomerAccountModel.PK + "} }");
		query.append(" WHERE {acc." + FMCustomerAccountModel.UID + "} = ?" + FMCustomerAccountModel.UID);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMCustomerAccountModel.UID, unitUid);

		LOG.info("query :: " + query.toString());

		final SearchResult<FMCustomerAccountModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		final SearchPageData searchResult = createSearchPageData();
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("SIZE" + result.getResult().size());

			searchResult.setResults(result.getResult());
			searchResult.setPagination(createPagination(pageableData, result));



		}
		return searchResult;

	}

	@Override
	public SearchPageData<B2BCustomerModel> getAllFMUsersByName(final PageableData pageableData, final String userName)
	{
		LOG.info("---------55555555555555-------------" + pageableData);

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerModel.PK + "}");
		query.append("FROM {" + FMCustomerModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerModel.UID + "} NOT IN (" + Config.getParameter("excludeAdminIDs") + ")");
		query.append(" and upper({name}) like upper('%" + userName + "%')");

		flexibleSearchService = getFlexibleSearchService();
		LOG.info("---------6666666666666666-------------query::" + query + "--------------query.toString()::" + query.toString());
		final SearchResult<FMCustomerModel> result = flexibleSearchService.search(query.toString());
		LOG.info("---------7777777777777777777-------------" + result);
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("---------8888888888888888 Resuult contains value-------------" + result);
			final SearchPageData searchResult = createSearchPageData();
			searchResult.setResults(result.getResult());
			searchResult.setPagination(createPagination(pageableData, result));

			LOG.info("--------99999999999999999999------Returning All the customers from DAO " + result.getResult().size());
			LOG.info("---------1010101010101010--------" + searchResult);
			return searchResult;
		}
		else
		{
			LOG.info("---------8888888888888888 Resuult NULL-------------" + result);
			LOG.error("No Customers Found");
			return null;
		}

	}


	/*
	 * @Override public SearchPageData<B2BCustomerModel> findPagedCustomers(final String sortCode, final PageableData
	 * pageableData) { final List<SortQueryData> sortQueries = Arrays.asList( createSortQueryData("byName", new
	 * HashMap<String, Object>(), SortParameters.singletonAscending(UserModel.NAME), pageableData),
	 * createSortQueryData("byUnit", FIND_B2BCUSTOMERS_QUERY + ORDERBY_UNIT_NAME));
	 * 
	 * return getPagedFlexibleSearchService().search(sortQueries, sortCode, new HashMap<String, Object>(), pageableData);
	 * }
	 */
	/**
	 * Method that retrieves all the customer accounts for admin view.
	 * 
	 * @return List of customer model.
	 */
	@Override
	public SearchPageData<B2BUnitModel> getAllFMAccounts(final PageableData pageableData)
	{
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerAccountModel.UID + "} NOT IN (" + Config.getParameter("excludeAccountIDs") + ")");
		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<B2BUnitModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{
			final SearchPageData searchResult = createSearchPageData();
			searchResult.setResults(result.getResult());
			searchResult.setPagination(createPagination(pageableData, result));

			LOG.info("Returning All the customer accounts from DAO " + result.getResult().size());
			return searchResult;
		}
		else
		{
			LOG.error("No Customer Accounts Found");
			return null;
		}
	}

	@Override
	public List<B2BUnitModel> getAllFMAccounts()
	{
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerAccountModel.UID + "} NOT IN (" + Config.getParameter("excludeAccountIDs") + ")");
		flexibleSearchService = getFlexibleSearchService();

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<B2BUnitModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{
			final List<B2BUnitModel> fmUnitModel = result.getResult();

			LOG.info("Returning All the Unit from DAO " + fmUnitModel.size());
			return fmUnitModel;
		}
		else
		{
			LOG.error("No Unit Found");
			return null;
		}


	}

	@Override
	public List<B2BUnitModel> getAllFMAccountsByID(final String uid)
	{
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerAccountModel.UID + "} NOT IN (" + Config.getParameter("excludeAccountIDs") + ")");
		query.append("AND {" + FMCustomerAccountModel.UID + "} like " + "'" + uid + "%'");
		flexibleSearchService = getFlexibleSearchService();

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<B2BUnitModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{
			final List<B2BUnitModel> fmUnitModel = result.getResult();

			LOG.info("Returning All the Unit from DAO " + fmUnitModel.size());
			return fmUnitModel;
		}
		else
		{
			LOG.error("No Unit Found");
			return null;
		}


	}

	protected PaginationData createPaginationData()
	{
		return new PaginationData();
	}

	protected SortData createSortData()
	{
		return new SortData();
	}

	protected <T> SearchPageData<T> createSearchPageData()
	{
		return new SearchPageData();
	}

	protected <T> PaginationData createPagination(final PageableData pageableData, final SearchResult<T> searchResult)
	{

		LOG.info("pageableData :: " + pageableData.getPageSize());
		LOG.info("pageableData :: " + pageableData.getSort());
		LOG.info("pageableData :: " + pageableData.getCurrentPage());

		final PaginationData paginationData = createPaginationData();
		paginationData.setPageSize(pageableData.getPageSize());
		paginationData.setSort(pageableData.getSort());
		paginationData.setTotalNumberOfResults(searchResult.getTotalCount());
		LOG.info("PAGINATION RESULT;" + paginationData.getTotalNumberOfResults());
		LOG.info("PAGINATION RESULT;" + paginationData.getPageSize());

		final double temp = (double) paginationData.getTotalNumberOfResults() / paginationData.getPageSize();
		LOG.info("TEMP VALUE " + temp);
		paginationData.setNumberOfPages((int) Math.ceil(temp));
		
	LOG.info("NUMBER OF PAGES::" + paginationData.getNumberOfPages());
		paginationData.setCurrentPage(Math.max(0, Math.min(paginationData.getNumberOfPages(), pageableData.getCurrentPage())));

		return paginationData;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#checkUidForID(java.lang.String)
	 */
	@Override
	public boolean checkUidForID(final String email)
	{
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerModel.PK + "}");
		query.append("FROM {" + FMCustomerModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerModel.UID + "} ='" + email + "'");
		//query.append("WHERE {" + FMCustomerModel.UID + "} =?email ");

		//final Map<String, Object> params = new HashMap<String, Object>();
		//params.put("email", email);

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerModel> result = flexibleSearchService.search(query.toString());

		LOG.info("Printing query result from DAO" + result.getCount());
		if (result.getCount() == 1)
		{
			LOG.info("count > 0");
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean checkTaxForID(final String taxId)
	{
		LOG.info("Printing query result from DAO Before Query" + taxId);
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerAccountModel.TAXID + "} ='" + taxId + "'");

		LOG.info("query :: " + query);
		/*
		 * final Map<String, Object> params = new HashMap<String, Object>(1); params.put("taxId", taxId);
		 */
		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerAccountModel> result1 = flexibleSearchService.search(query.toString());

		LOG.info("Printing query result from DAO" + result1.getCount());
		if (result1.getCount() == 1)
		{
			LOG.info("count > 0");
			return true;
		}
		else
		{
			return false;
		}
	}


	@Override
	public boolean checkProspectid(final String userUid)
	{
		LOG.info("Printing query result from DAO Before Query" + userUid);

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerAccountModel.UID + "} ='" + userUid + "'");
		query.append("OR {" + FMCustomerAccountModel.PROSPECTUID + "} ='" + userUid + "'");

		LOG.info("query :: " + query);
		/*
		 * final Map<String, Object> params = new HashMap<String, Object>(1); params.put("taxId", taxId);
		 */
		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerAccountModel> result2 = flexibleSearchService.search(query.toString());

		LOG.info("Printing query result from DAO" + result2.getCount());
		if (result2.getCount() >= 1)
		{
			LOG.info("count = " + result2.getCount());
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Method that returns the list of customer account details for which Sold-To to be number to be created.
	 * 
	 * @return List of Customer account details
	 */
	@Override
	public List<FMCustomerAccountModel> getprospectSoldToUsers()
	{
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerAccountModel.UID + "} = {" + FMCustomerAccountModel.PROSPECTUID + "}");
		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerAccountModel> fmCustomerAccountModel = result.getResult();

			LOG.info("Returning Prospect IDs for Sold-To creation" + fmCustomerAccountModel.size());
			return fmCustomerAccountModel;
		}
		else
		{
			LOG.error("No Prospect IDs found to send for Sold-To creation");
			return null;
		}

	}

	@Override
	public AddressModel getAddressByID(final String addressID)
	{

		final StringBuilder query = new StringBuilder("SELECT {" + AddressModel.PK + "}");
		query.append("FROM {" + AddressModel._TYPECODE + " } ");
		query.append("WHERE {" + AddressModel.PK + "} =?addressID ");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("addressID", addressID);

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<AddressModel> result = flexibleSearchService.search(query.toString(), params);

		if (null != result && result.getResult().size() > 0)
		{
			final List<AddressModel> fmAddressList = result.getResult();
			final AddressModel fmAddress = fmAddressList.iterator().next();
			return fmAddress;
		}
		else
		{
			LOG.error("Result NULL");
			return null;
		}
	}

	@Override
	public B2BUnitModel getUnitForUID(final String unitId)
	{
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCustomerAccountModel.UID + "} =?unitId ");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("unitId", unitId);

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<B2BUnitModel> result = flexibleSearchService.search(query.toString(), params);

		if (null != result && result.getResult().size() > 0)
		{
			final List<B2BUnitModel> b2bUnitList = result.getResult();
			final B2BUnitModel b2bunitModel = b2bUnitList.iterator().next();
			return b2bunitModel;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getParentunitAddress(java.lang.String)
	 */
	@Override
	public List<AddressData> getSoldToUnitAddress(final String uid)
	{
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {")
				.append(FMCustomerPartnerFunctionModel.FMSOURCECUSTOMERACCOUNT);
		//final StringBuilder query = new StringBuilder("SELECT DISTINCT {").append(FMCustomerPartnerFunctionModel.FMTARGETCUSTOMERACCOUNT);
		query.append("} FROM {").append(FMCustomerPartnerFunctionModel._TYPECODE);
		query.append("} WHERE {").append(FMCustomerPartnerFunctionModel.CODE);
		query.append("} LIKE ?").append(FMCustomerPartnerFunctionModel.CODE);


		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(FMCustomerPartnerFunctionModel.CODE, "%" + uid + "%");

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString(), params);



		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("Size :: " + result.getResult().size());
			final List<FMCustomerAccountModel> queryresult = result.getResult();

			final List<AddressData> listAddressdata = new ArrayList<AddressData>();

			final Iterator i1 = queryresult.iterator();
			while (i1.hasNext())
			{
				final FMCustomerAccountModel soldto = (FMCustomerAccountModel) i1.next();

				final List<AddressModel> listAddressmodel = (List<AddressModel>) soldto.getAddresses();
				final Iterator i = listAddressmodel.iterator();
				while (i.hasNext())
				{
					final AddressModel addressmodel = (AddressModel) i.next();
					final AddressData addressdata = new AddressData();
					getAddressPopulator().populate(addressmodel, addressdata);
					listAddressdata.add(addressdata);
					LOG.info("list_addressdata :: " + listAddressdata);
				}

			}

			return listAddressdata;
		}
		else
		{
			LOG.error("result none");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getSoldToShipToUnitUid(java.lang.String)
	 */
	@Override
	public List<FMCustomerAccountModel> getSoldToShipToUnitUid(final String uid)
	{
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {")
				.append(FMCustomerPartnerFunctionModel.FMSOURCECUSTOMERACCOUNT);
		//query.append("} , {").append(FMCustomerPartnerFunctionModel.SHIPTOACCOUNTUID);
		query.append("} FROM {").append(FMCustomerPartnerFunctionModel._TYPECODE);
		query.append("} WHERE {").append(FMCustomerPartnerFunctionModel.CODE);
		query.append("} LIKE ?").append(FMCustomerPartnerFunctionModel.CODE);

		final Map<String, Object> params = new HashMap<String, Object>();
		//params.put(FMCustomerPartnerFunctionModel.CODE, "%" + uid + "%");
		params.put(FMCustomerPartnerFunctionModel.CODE, uid + "%");

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString(), params);
		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerAccountModel> queryresult = result.getResult();
			//final FMCustomerAccountModel UnitUidModel = queryresult.iterator().next();
			return queryresult;
		}
		else
		{
			LOG.error("result none");
			return null;
		}

	}

	/**
	 * @return the addressPopulator
	 */
	public Populator<AddressModel, AddressData> getAddressPopulator()
	{
		return addressPopulator;
	}

	/**
	 * @param addressPopulator
	 *           the addressPopulator to set
	 */
	public void setAddressPopulator(final Populator<AddressModel, AddressData> addressPopulator)
	{
		this.addressPopulator = addressPopulator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getAllAccountsforCSR()
	 */
	@Override
	public List<FMCustomerAccountModel> getAllAccountsforCSR()
	{

		final StringBuilder query = new StringBuilder(Config.getParameter("csrquery"));	

		flexibleSearchService = getFlexibleSearchService();

		LOG.info("query for getAllAccountsforCSR :: " + query.toString());
		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString());
		//LOG.info("SIZE" + result.getResult().size());



		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerAccountModel> queryresult = result.getResult();
			return queryresult;
		}
		else
		{
			LOG.error("No Unit Found");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getAccountID(java.lang.String)
	 */
	@Override
	public FMCustomerAccountModel getAccountID(final String accountId)
	{

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + "}");
		query.append("WHERE {" + FMCustomerAccountModel.UID + "}");
		query.append("LIKE ('%").append(accountId);
		query.append("')");

		flexibleSearchService = getFlexibleSearchService();
		//final Map<String, Object> params = new HashMap<String, Object>();
		//params.put(FMCustomerPartnerFunctionModel.CODE, "%" + uid + "%");
		//params.put("accountCode", accountId);

		LOG.info("query for getAccountID:: " + query.toString());

		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString());

		LOG.info("Printing query result from DAO" + result.getCount());
		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerAccountModel> listAccountId = result.getResult();
			final FMCustomerAccountModel accountIdModel = listAccountId.iterator().next();
			return accountIdModel;

		}
		else
		{
			LOG.error("Result NULL");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getAccountByNabs(java.lang.String)
	 */
	@Override
	public FMCustomerAccountModel getAccountByNabs(final String accountId)
	{

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + "}");
		query.append("WHERE {" + FMCustomerAccountModel.NABSACCOUNTCODE + "} ='" + accountId + "'");

		flexibleSearchService = getFlexibleSearchService();

		LOG.info("query for getAccountID:: " + query.toString());

		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString());

		LOG.info("Printing query result from DAO" + result.getCount());
		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerAccountModel> listAccountId = result.getResult();
			final FMCustomerAccountModel accountIdModel = listAccountId.iterator().next();
			return accountIdModel;

		}
		else
		{
			LOG.error("Result NULL");
			return null;
		}

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#fetchAddress(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<FMCustomerAccountModel> fetchAddress(final String companyName, final String line1, final String townCity,
			final String postcode, final String region)
	{
		final StringBuilder query = new StringBuilder("");

		query.append("select {fmCustAcc." + FMCustomerAccountModel.PK + "} from {" + FMCustomerAccountModel._TYPECODE
				+ " As fmCustAcc JOIN " + AddressModel._TYPECODE + " As address ON {fmCustAcc." + FMCustomerAccountModel.PK
				+ "} = {address." + AddressModel.OWNER + "} JOIN " + RegionModel._TYPECODE + " As reg ON { address."
				+ AddressModel.REGION + "} = {reg." + RegionModel.PK + "} }");

		if ((companyName != null && companyName != "") || (line1 != null && line1 != "") || (townCity != null && townCity != "")
				|| (postcode != null && postcode != "") || (region != null && region != ""))
		{
			query.append(" where");
		}

		if (companyName != null && companyName != "")
		{
			query.append(" UPPER({fmCustAcc." + FMCustomerAccountModel.LOCNAME + "}) like upper('%" + companyName + "%')");
		}

		if (line1 != null && line1 != "")
		{
			query.append(" and UPPER({address." + AddressModel.STREETNAME + "}) like UPPER('%" + line1 + "%')");
		}

		if (townCity != null && townCity != "")
		{
			query.append(" and UPPER({address." + AddressModel.TOWN + "}) = UPPER('" + townCity + "')");
		}
		if (postcode != null && postcode != "")
		{
			query.append(" and {address." + AddressModel.POSTALCODE + "} = '" + postcode + "'");
		}
		if (region != null && region != "")
		{
			query.append(" and   UPPER({reg." + RegionModel.ISOCODE + "} ) like  UPPER('%" + region + "%')");
		}

		LOG.info("Query :: " + query.toString());

		LOG.info(" AND VALUE  :: " + query.indexOf("and"));



		if (query.indexOf("and") == 178)
		{
			query.replace(query.indexOf("and"), 181, "");
		}


		LOG.info("Query value" + query);

		flexibleSearchService = getFlexibleSearchService();

		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{

			LOG.info("result Size is  :: " + result.getResult().size());
		}

		LOG.info("Printing query result from DAO" + result.getCount());

		final List<FMCustomerAccountModel> listResultModel = result.getResult();
		return listResultModel;
	}

	@Override
	public B2BUserGroupModel getGroupByID(final String groupID)
	{


		final StringBuilder query = new StringBuilder("SELECT {" + B2BUserGroupModel.PK + "}");
		query.append("FROM {" + B2BUserGroupModel._TYPECODE + " } ");
		//query.append("WHERE {" + B2BUserGroupModel.PK + "} =?groupID ");
		query.append("WHERE {" + B2BUserGroupModel.UID + "}  ='" + groupID + "'");

		/*
		 * final Map<String, Object> params = new HashMap<String, Object>(); params.put("addressID", addressID);
		 */
		flexibleSearchService = getFlexibleSearchService();
		//	final SearchResult<AddressModel> result = flexibleSearchService.search(query.toString(), params);
		final SearchResult<B2BUserGroupModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{
			final List<B2BUserGroupModel> fmGroupList = result.getResult();
			final B2BUserGroupModel fmGroup = fmGroupList.iterator().next();
			return fmGroup;
		}
		else
		{
			LOG.error("Result NULL");
			return null;
		}
	}

	@Override
	public List<FMCsrAccountListModel> getFMCSRAccountList(final String userID)
	{


		final StringBuilder query = new StringBuilder("SELECT {" + FMCsrAccountListModel.PK + "}");
		query.append("FROM {" + FMCsrAccountListModel._TYPECODE + " } ");
		query.append("WHERE {" + FMCsrAccountListModel.CSRUSERID + "} ='" + userID + "'");
		query.append("ORDER BY {" + FMCsrAccountListModel.DATE + "} DESC");

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCsrAccountListModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCsrAccountListModel> fmCSRList = result.getResult();

			return fmCSRList;
		}
		else
		{

			LOG.error("No csr unitFound");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getB2BAddressSearch(java.lang.String)
	 */
	@Override
	public List<FMCustomerAccountModel> getB2BAddressSearch(final String searchString)
	{

		final StringBuilder query = new StringBuilder("");

		query.append("select {fmCustAcc." + FMCustomerAccountModel.PK + "} from {" + FMCustomerAccountModel._TYPECODE
				+ " As fmCustAcc JOIN " + AddressModel._TYPECODE + " As address ON {fmCustAcc." + FMCustomerAccountModel.PK
				+ "} = {address." + AddressModel.OWNER + "} }");
		query.append("WHERE UPPER({fmCustAcc." + FMCustomerAccountModel.LOCNAME + "})");
		query.append("LIKE UPPER('%" + searchString + "%')");
		query.append("OR UPPER({fmCustAcc." + FMCustomerAccountModel.NABSACCOUNTCODE + "})");
		query.append("LIKE UPPER('%" + searchString + "%')");
		query.append("OR UPPER({fmCustAcc." + FMCustomerAccountModel.UID + "})");
		query.append("LIKE UPPER('%" + searchString + "%')");
		query.append("OR UPPER({address." + AddressModel.TOWN + "})");
		query.append("LIKE UPPER('%" + searchString + "%')");

		LOG.info("Query :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();

		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{

			LOG.info("result Size is  :: " + result.getResult().size());

			LOG.info("Printing query result from DAO" + result.getCount());

			final List<FMCustomerAccountModel> listResultModel = result.getResult();

			return listResultModel;

		}
		else
		{
			LOG.error("NO RECORDS FOUND");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getSoldToShipToUnitUid(java.lang.String)
	 */
	@Override
	public List<FMCustomerAccountModel> getAdminUserUnits(final String uid)
	{
		final StringBuilder query = new StringBuilder("SELECT {").append(FMCustomerPartnerFunctionModel.FMTARGETCUSTOMERACCOUNT);
		//query.append("} , {").append(FMCustomerPartnerFunctionModel.SHIPTOACCOUNTUID);
		query.append("} FROM {").append(FMCustomerPartnerFunctionModel._TYPECODE);
		query.append("} WHERE {").append(FMCustomerPartnerFunctionModel.CODE);
		query.append("} LIKE ?").append(FMCustomerPartnerFunctionModel.CODE);

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(FMCustomerPartnerFunctionModel.CODE, "%" + uid + "%");

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString(), params);
		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerAccountModel> queryresult = result.getResult();
			LOG.info("" + queryresult.size());
			//final FMCustomerAccountModel UnitUidModel = queryresult.iterator().next();
			return queryresult;
		}
		else
		{
			LOG.error("result none");
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getPermissions(java.lang.String)
	 */

	@Override
	public List<CSBProductPermissionForm> getPermissions(String soldToAccount)
	{	
		if (soldToAccount.length() < 10)
		{
			final int accnumberSize = 10 - soldToAccount.length();
			for (int i = 0; i < accnumberSize; i++)
			{
				soldToAccount = "0" + soldToAccount;
				LOG.info("size of nabs:::::::::" + soldToAccount);
			}

		}
		final StringBuilder query = new StringBuilder();

		query.append("SELECT DISTINCT {A." + CategorySalesBenchmarkCustomerModel.CPL1CUSTOMERCODE + "}, {B."
				+ CSBCustomerGroupModel.POSDOLLARS + "}, {B." + CSBCustomerGroupModel.POSUNITS + "} ");
		query.append("FROM {" + CSBCustomerGroupModel._TYPECODE + " AS B ");
		query.append("JOIN " + CategorySalesBenchmarkCustomerModel._TYPECODE + " AS A ");
		query.append("ON {A." + CategorySalesBenchmarkCustomerModel.CSBCUSTOMERGROUPCODE + "} = {B."
				+ CSBCustomerGroupModel.CSBCUSTOMERGROUPCODE + "}} ");
		query.append("WHERE {A." + CategorySalesBenchmarkCustomerModel.CSBSOLDTOACCOUNT + "} = '" + soldToAccount + "'");

		LOG.info("permissions query ::" + query);

		flexibleSearchService = getFlexibleSearchService();

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		resultClassList.add(String.class);
		resultClassList.add(String.class);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());

		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<String> searchResult = flexibleSearchService.search(flexibleSearchQuery);

		final List result = searchResult.getResult();
		final List<CSBProductPermissionForm> list = new ArrayList();

		//LOG.info("GET PERMISSIONS SIZE" + result.size());
		//LOG.info("GET PERMISSIONS list" + result.get(0));
		final Iterator ii = result.iterator();

		while (ii.hasNext())
		{
			Object permList = ii.next();
			permList = permList.toString().replace("[", "");
			final String newPermList = permList.toString().replace("]", "");
			System.out.println("permList   " + newPermList);
			final String[] myList = newPermList.split(",");
			final CSBProductPermissionForm form = new CSBProductPermissionForm();
			form.setCpl1CustomerCode(myList[0].trim());
			form.setPosDollars(myList[1].trim());
			form.setPosUnits(myList[2].trim());
			list.add(form);

		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getUnitorSaleChangePercentsModel(java.lang.String, java.lang.String)
	 */
	@Override
	public List<CategorySalesBenchmarkPercentsModel> getUnitorSaleChangePercentsModel(final String cpl1Code)
	{
		final StringBuilder query = new StringBuilder();
		query.append("SELECT {" + CategorySalesBenchmarkPercentsModel.PK + "} ");
		query.append("FROM {" + CategorySalesBenchmarkPercentsModel._TYPECODE + "} ");
		query.append("WHERE {" + CategorySalesBenchmarkCustomerModel.CPL1CUSTOMERCODE + "} = '" + cpl1Code + "'");
		query.append("ORDER BY {" + CategorySalesBenchmarkPercentsModel.CSBCALENDERYEARMONTH + "} ");

		flexibleSearchService = getFlexibleSearchService();
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		final SearchResult<CategorySalesBenchmarkPercentsModel> searchResult = flexibleSearchService.search(flexibleSearchQuery);
		final List<CategorySalesBenchmarkPercentsModel> result = searchResult.getResult();

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getDesc(java.lang.String)
	 */
	@Override
	public String getDesc(final String productCode)
	{
		final StringBuilder query = new StringBuilder();
		query.append("SELECT {" + CPL1CustomerModel.PK + "} ");
		query.append("FROM {" + CPL1CustomerModel._TYPECODE + "} ");
		query.append("WHERE {" + CPL1CustomerModel.CPL1PRODUCTCODE + "} = '" + productCode + "'");
		flexibleSearchService = getFlexibleSearchService();
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		final SearchResult<CPL1CustomerModel> searchResult = flexibleSearchService.search(flexibleSearchQuery);
		final List<CPL1CustomerModel> result = searchResult.getResult();
		String desc = null;
		for (final CPL1CustomerModel cpl1 : result)
		{
			desc = cpl1.getCpl1ProductDescription();
		}
		return desc;
	}

	/*
	 * (non-Javadoc) 
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getYearOverYearComparison(java.lang.String)
	 */
	@Override
	public CSBPercents3612Model getYearOverYearComparison(final String inputProductCode)
	{
		// YTODO Auto-generated method stub 
		final StringBuilder query = new StringBuilder();
		query.append("SELECT {" + CSBPercents3612Model.PK + "} ");
		query.append("FROM {" + CSBPercents3612Model._TYPECODE + "} ");
		query.append("WHERE {" + CSBPercents3612Model.CPL1CUSTOMERCODE + "} = '" + inputProductCode + "'");
		flexibleSearchService = getFlexibleSearchService();
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		final SearchResult<CSBPercents3612Model> searchResult = flexibleSearchService.search(flexibleSearchQuery);
		final List<CSBPercents3612Model> resultList = searchResult.getResult();
		final CSBPercents3612Model result = resultList.iterator().next();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getAllProducts()
	 */
	@Override
	public List<CPL1CustomerModel> getAllProducts()
	{
		// YTODO Auto-generated method stub
		final StringBuilder query = new StringBuilder();
		query.append("SELECT DISTINCT{" + CPL1CustomerModel.PK + "} ");
		query.append("FROM {" + CPL1CustomerModel._TYPECODE + "} ");
		flexibleSearchService = getFlexibleSearchService();
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		final SearchResult<CPL1CustomerModel> searchResult = flexibleSearchService.search(flexibleSearchQuery);
		final List<CPL1CustomerModel> resultList = searchResult.getResult();
		return resultList;
	}
/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getB2BShipToAddressForSoldTo(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<FMCustomerAccountModel> getB2BShipToAddressForSoldTo(final String searchString, final String accountID,
			final String searchType, final String loggedUserType)
	{
		// YTODO Auto-generated method stub
		final StringBuilder query = new StringBuilder("SELECT DISTINCT { fmCustAcc." + FMCustomerAccountModel.PK + "} ");
		query.append(" FROM  {" + FMCustomerPartnerFunctionModel._TYPECODE + " AS fmCustPartnerAcc ");
		query.append(" JOIN " + FMCustomerAccountModel._TYPECODE + " AS fmCustAcc ");

		if (("ShipTo").equalsIgnoreCase(loggedUserType))
		{
			query.append(" ON {fmCustPartnerAcc." + FMCustomerPartnerFunctionModel.FMSOURCECUSTOMERACCOUNT + "} = {fmCustAcc."
					+ FMCustomerAccountModel.PK + "} ");
		}
		else
		{
			query.append(" ON {fmCustPartnerAcc." + FMCustomerPartnerFunctionModel.FMTARGETCUSTOMERACCOUNT + "} = {fmCustAcc."
					+ FMCustomerAccountModel.PK + "} ");
		}
		query.append(" JOIN " + AddressModel._TYPECODE + " AS address ");
		query.append(" ON {address." + AddressModel.OWNER + "} = {fmCustAcc." + FMCustomerAccountModel.PK + "} ");
		query.append(" JOIN " + RegionModel._TYPECODE + " AS reg ");
		query.append(" ON {reg." + RegionModel.PK + "} = {address." + AddressModel.REGION + "} } ");
		query.append(" WHERE {fmCustPartnerAcc." + FMCustomerPartnerFunctionModel.CODE + "}");
		query.append(" LIKE ('%" + accountID + "%') ");
		query.append(" AND UPPER ({fmCustPartnerAcc." + FMCustomerPartnerFunctionModel.PARTNERFMACCOUNTTYPE + "})");
		query.append(" = '" + searchType.toUpperCase() + "'");

		if (null != searchString && !"getAll*".equals(searchString))
		{
			query.append(" AND( UPPER({fmCustAcc." + FMCustomerAccountModel.LOCNAME + "}) ");
			query.append(" LIKE UPPER('%" + searchString + "%') ");
			query.append(" OR UPPER({fmCustAcc." + FMCustomerAccountModel.NABSACCOUNTCODE + "}) ");
			query.append(" LIKE UPPER('%" + searchString + "%') ");
			query.append(" OR UPPER({fmCustAcc." + FMCustomerAccountModel.UID + "}) ");
			query.append(" LIKE UPPER('%" + searchString + "%') ");
			query.append(" OR UPPER({address." + AddressModel.TOWN + "}) ");
			query.append(" LIKE UPPER('%" + searchString + "%') ) ");
		}

		LOG.info("getB2BShipToAddressForSoldTo ::" + query.toString());
		flexibleSearchService = getFlexibleSearchService();

		final SearchResult<FMCustomerAccountModel> result = flexibleSearchService.search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{

			LOG.info("result Size is  :: " + result.getResult().size());

			LOG.info("Printing query result from DAO" + result.getCount());

			final List<FMCustomerAccountModel> listResultModel = result.getResult();

			return listResultModel;

		}
		else
		{
			LOG.error("NO RECORDS FOUND");
			return null;
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#checkSoldToShipto(java.lang.String)
	 */
	@Override
	public String checkSoldToShipto(final String uid)
	{
		String result = null;
		final StringBuilder query = new StringBuilder("SELECT {").append(FMCustomerPartnerFunctionModel.PK);
		query.append("} FROM {").append(FMCustomerPartnerFunctionModel._TYPECODE);
		query.append("} WHERE {").append(FMCustomerPartnerFunctionModel.CODE);
		query.append("} = ?").append(FMCustomerPartnerFunctionModel.CODE);

		final Map<String, Object> params = new HashMap<String, Object>();
		//params.put(FMCustomerPartnerFunctionModel.CODE, "%" + uid + "%");
		params.put(FMCustomerPartnerFunctionModel.CODE, uid + uid);

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerPartnerFunctionModel> queryResult = flexibleSearchService.search(query.toString(), params);
		if (null != queryResult && queryResult.getResult().size() > 0)
		{
			LOG.info("Sold  To");
			result = FmCoreConstants.SOLDTO;
		}
		else
		{
			LOG.info("ShipTo");
                        result = FmCoreConstants.SHIPTO;			
		}
		final List<FMCustomerPartnerFunctionModel> res = queryResult.getResult();
		for (final FMCustomerPartnerFunctionModel pfunc : res)
		{
			if(!FMUtils.isPartnerFunctionCodeValid(pfunc.getCode()))
			{
				LOG.info("invalid");
				result = FmCoreConstants.INVALID;
			}
		}
		return result;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.network.dao.FMUserSearchDAO#getAllFMCustomers()
	 */
	@Override
	public List<FMCustomerModel> getAllFMCustomers()
	{
		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerModel.PK + "}");
		query.append("FROM {" + FMCustomerModel._TYPECODE + " } ");
		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMCustomerModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{

			LOG.info("result Size is  :: " + result.getResult().size());

			LOG.info("Printing query result from DAO" + result.getCount());

			final List<FMCustomerModel> listResultModel = result.getResult();
			for (final FMCustomerModel fmcustomer : listResultModel)
			{
				if (fmcustomer.getLogindate() != null)
				{
					LOG.info("date is :::" + fmcustomer.getLogindate());
				}
			}
			return listResultModel;

		}
		else
		{
			LOG.error("NO RECORDS FOUND");
			return null;
		}
	}

}

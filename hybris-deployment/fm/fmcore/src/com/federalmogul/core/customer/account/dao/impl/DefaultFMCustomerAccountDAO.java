/**
 * 
 */
package com.federalmogul.core.customer.account.dao.impl;

import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.List;

import org.apache.log4j.Logger;

import com.federalmogul.core.customer.account.dao.FMCustomerAccountDAO;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMTaxDocumentModel;



/**
 * @author sai
 * 
 */
public class DefaultFMCustomerAccountDAO implements FMCustomerAccountDAO
{

	private static final Logger LOG = Logger.getLogger(DefaultFMCustomerAccountDAO.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getYMMFitmentData(java.lang.String)
	 */

	private FlexibleSearchService flexibleSearchService;

	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2BUnitService;

	/**
	 * @return the b2BUnitService
	 */
	public B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2BUnitService()
	{
		return b2BUnitService;
	}

	/**
	 * @param b2bUnitService
	 *           the b2BUnitService to set
	 */
	public void setB2BUnitService(final B2BUnitService<B2BUnitModel, B2BCustomerModel> b2bUnitService)
	{
		b2BUnitService = b2bUnitService;
	}

	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.customer.account.dao.FMCustomerAccountDAO#getAllFMTaxDocuments()
	 */
	@Override
	public List<FMTaxDocumentModel> getAllFMTaxDocuments(final String searchTaxDocName, final String sortOption)
	{
		LOG.info("getAllFMTaxDocuments DAO Impl Method Called :: " + searchTaxDocName);

		/*
		 * final StringBuilder query = new StringBuilder("SELECT {taxDoc." + FMTaxDocumentsModel.PK + "}");
		 * query.append("FROM {" + FMTaxDocumentsModel._TYPECODE + " AS taxDoc} where {taxDoc." + FMTaxDocumentsModel.PK +
		 * "}='8796551827676'");
		 */

		/*
		 * final StringBuilder query = new StringBuilder("SELECT {taxDoc." + FMTaxDocumentsModel.PK + "}");
		 * query.append("FROM {" + FMTaxDocumentsModel._TYPECODE + " AS taxDoc}");
		 */
		StringBuilder query = null;
		if (searchTaxDocName != null && searchTaxDocName != "")
		{

			query = new StringBuilder("SELECT {taxDoc." + FMTaxDocumentModel.PK + "}");
			query.append("FROM {" + FMTaxDocumentModel._TYPECODE + " AS taxDoc} where UPPER({docname}) like UPPER('%"
					+ searchTaxDocName + "%')  ORDER BY  {docname} " + sortOption);

		}
		else
		{
			query = new StringBuilder("SELECT {taxDoc." + FMTaxDocumentModel.PK + "}");
			query.append("FROM {" + FMTaxDocumentModel._TYPECODE + " AS taxDoc} ORDER BY  {docname}" + sortOption);
		}



		LOG.info("TAX DOC Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMTaxDocumentModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("GOT the Results and Its Size is :: " + result.getResult().size());


			for (final FMTaxDocumentModel fmTaxDocumentsModel : result.getResult())
			{
				LOG.info(fmTaxDocumentsModel + " :: OBJECT ");
				if (fmTaxDocumentsModel != null)
				{
					LOG.info(fmTaxDocumentsModel.getDocname() + " :: OBJECT DATA");
				}
			}

			final List<FMTaxDocumentModel> fmTaxDocuments = result.getResult();


			return fmTaxDocuments;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");

			return null;
		}

	}

	@Override
	public FMTaxDocumentModel getFMTaxDocumentsForPK(final String taxDocPK)
	{

		LOG.info("getFMTaxDocumentsForPK DAO Impl Method Called ##### " + taxDocPK);
		final StringBuilder query = new StringBuilder("SELECT {taxDoc." + FMTaxDocumentModel.PK + "}");
		query.append("FROM {" + FMTaxDocumentModel._TYPECODE + " AS taxDoc} where {taxDoc." + FMTaxDocumentModel.PK + "}='"
				+ taxDocPK + "'");

		LOG.info("TAX DOC Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMTaxDocumentModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("GOT the Results and Its Size is :: " + result.getResult().size());


			for (final FMTaxDocumentModel fmTaxDocumentsModel : result.getResult())
			{
				LOG.info(fmTaxDocumentsModel + " :: OBJECT ");
				if (fmTaxDocumentsModel != null)
				{
					LOG.info(fmTaxDocumentsModel.getDocname() + " :: OBJECT DATA");
				}
			}

			final FMTaxDocumentModel fmTaxDocumentsModel = result.getResult().get(0);


			return fmTaxDocumentsModel;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}


	}

	@Override
	public FMCustomerAccountModel getFMB2BUnit(final String uid)
	{
		LOG.error("Uid ==>" + uid);

		FMCustomerAccountModel b2bunit = null;
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {dc:" + FMCustomerAccountModel.PK + "} ");
		query.append("FROM  {" + FMCustomerAccountModel._TYPECODE + " AS dc } ");
		//query.append("WHERE {dc." + FMCustomerAccountModel.UID + "} = ?" + FMCustomerAccountModel.UID);
		//final Map<String, Object> params = new HashMap<String, Object>(1);
		//params.put(FMCustomerAccountModel.UID, Uid);
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		LOG.error("flexibleSearchQuery == >" + flexibleSearchQuery.getQuery().toString());
		final SearchResult<FMCustomerAccountModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);
		LOG.error("searchResult.getResult() ==>");
		if (searchResult.getResult() != null)
		{
			LOG.error("Inside searchResult" + searchResult.getResult());
			b2bunit = searchResult.getResult().get(0);
			LOG.error("Inside searchResult :: b2bunit" + b2bunit);

		}

		//		final B2BUnitModel parent = getB2BUnitService().getParent(b2bunit);

		return b2bunit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.core.customer.account.dao.FMCustomerAccountDAO#validateStateWiseTaxDocument(com.federalmogul.
	 * core.model.FMCustomerModel, de.hybris.platform.core.model.c2l.RegionModel)
	 */
	@Override
	public boolean validateStateWiseTaxDocument(final FMCustomerModel fmmodel, final RegionModel regionModel)
	{

		LOG.info("validateStateWiseTaxDocument DAO Impl Method Called :: ");
		LOG.info("fmmodel PK " + fmmodel.getPk().toString());
		LOG.info("regionModel PK " + regionModel.getPk().toString());

		final StringBuilder query = new StringBuilder("SELECT {taxDoc." + FMTaxDocumentModel.PK + "}");
		query.append("FROM {" + FMTaxDocumentModel._TYPECODE + " AS taxDoc} where {" + FMTaxDocumentModel.UPLOADEDBY + "} like '"
				+ fmmodel.getPk().toString() + "' AND" + "{" + FMTaxDocumentModel.STATE + "} like '" + regionModel.getPk().toString()
				+ "' ");



		LOG.info("VALIDATE STATE WISE TAX DOC Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		boolean resultRet;
		final SearchResult<FMTaxDocumentModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			resultRet = true;
			//return true;
		}
		else
		{
			resultRet = false;
			//return false;
		}

		return resultRet;
	}
}

/**
 * 
 */
package com.federalmogul.core.search.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.federalmogul.core.search.dao.FMFitmentSearchDAO;
import com.federalmogul.falcon.core.model.FMFitmentModel;


/**
 * @author mamud
 * 
 */
public class DefaultFMFitmentSearchDAO extends DefaultGenericDao<FMFitmentModel> implements FMFitmentSearchDAO
{

	private static final Logger LOG = Logger.getLogger(DefaultFMFitmentSearchDAO.class);


	/**
	 * @param typecode
	 */
	public DefaultFMFitmentSearchDAO(final String typecode)
	{
		super(typecode);
		LOG.info("DefaultFMFitmentSearchDAO :: typecode " + typecode);
		// YTODO Auto-generated constructor stub
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getYMMFitmentData()
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentData()
	{
		// YTODO Auto-generated method stub
		return find();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getYMMFitmentYearData()
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentYearData(final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");

		final Map parameters = new HashMap();
		parameters.put(FMFitmentModel.VEHICLESEGMENT, vehicleSegment);

		return find(parameters);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getYMMFitmentMakeData(java.lang.String)
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentMakeData(final String year, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");
		validateParameterNotNull(year, "year  must not be null!");

		final Map parameters = new HashMap();
		parameters.put(FMFitmentModel.VEHICLESEGMENT, vehicleSegment);
		parameters.put(FMFitmentModel.YEAR, year);

		return find(parameters);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getYMMFitmentModelData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentModelData(final String year, final String make, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");
		validateParameterNotNull(year, "year  must not be null!");
		validateParameterNotNull(make, "make  must not be null!");

		final Map parameters = new HashMap();
		parameters.put(FMFitmentModel.VEHICLESEGMENT, vehicleSegment);
		parameters.put(FMFitmentModel.YEAR, year);
		parameters.put(FMFitmentModel.MAKE, make);

		return find(parameters);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getFitmentData()
	 */
	@Override
	public List<String> getFitmentData()
	{
		// YTODO Auto-generated method stub

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMFitmentModel.VEHICLESEGMENT + "} ");
		query.append("FROM  {" + FMFitmentModel._TYPECODE + " AS fit } ");
		query.append(" ORDER BY {fit:" + FMFitmentModel.VEHICLESEGMENT + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<List> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		final List outputList = searchResult.getResult();

		return outputList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getFitmentYearData()
	 */
	@Override
	public List<String> getFitmentYearData(final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMFitmentModel.YEAR + "} ");
		query.append("FROM  {" + FMFitmentModel._TYPECODE + " AS fit } ");
		query.append("WHERE {fit." + FMFitmentModel.VEHICLESEGMENT + "}=?" + FMFitmentModel.VEHICLESEGMENT);
		query.append(" ORDER BY {fit:" + FMFitmentModel.YEAR + "} DESC ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMFitmentModel.VEHICLESEGMENT, vehicleSegment);

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<List> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		final List outputList = searchResult.getResult();

		return outputList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getFitmentMakeData(java.lang.String)
	 */
	@Override
	public List<String> getFitmentMakeData(final String year, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");
		validateParameterNotNull(year, "year  must not be null!");

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMFitmentModel.MAKE + "} ");
		query.append("FROM  {" + FMFitmentModel._TYPECODE + " AS fit } ");
		query.append("WHERE {fit." + FMFitmentModel.VEHICLESEGMENT + "}=?" + FMFitmentModel.VEHICLESEGMENT);
		query.append(" AND {fit." + FMFitmentModel.YEAR + "}=?" + FMFitmentModel.YEAR);
		query.append(" ORDER BY {fit:" + FMFitmentModel.MAKE + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMFitmentModel.VEHICLESEGMENT, vehicleSegment);
		flexibleSearchQuery.addQueryParameter(FMFitmentModel.YEAR, year);

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<List> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		final List outputList = searchResult.getResult();

		return outputList;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getFitmentModelData(java.lang.String, java.lang.String)
	 */

	@Override
	public List<String> getFitmentModelData(final String year, final String make, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");
		validateParameterNotNull(year, "year  must not be null!");
		validateParameterNotNull(make, "make  must not be null!");

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMFitmentModel.MODEL + "} ");
		query.append("FROM  {" + FMFitmentModel._TYPECODE + " AS fit } ");
		query.append("WHERE {fit." + FMFitmentModel.VEHICLESEGMENT + "}=?" + FMFitmentModel.VEHICLESEGMENT);
		query.append(" AND {fit." + FMFitmentModel.YEAR + "}=?" + FMFitmentModel.YEAR);
		query.append(" AND {fit." + FMFitmentModel.MAKE + "}=?" + FMFitmentModel.MAKE);
		query.append(" ORDER BY {fit:" + FMFitmentModel.MODEL + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMFitmentModel.VEHICLESEGMENT, vehicleSegment);
		flexibleSearchQuery.addQueryParameter(FMFitmentModel.YEAR, year);
		flexibleSearchQuery.addQueryParameter(FMFitmentModel.MAKE, make);

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<List> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		final List outputList = searchResult.getResult();

		return outputList;
	}


}

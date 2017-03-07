/**
 * 
 */
package com.federalmogul.core.search.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.federalmogul.core.search.dao.FMYearMakeModelVehicleTypeDAO;
import com.federalmogul.falcon.core.model.FMPartAlsoFitsModel;
import com.federalmogul.falcon.core.model.FMYearMakeModelVehicleTypeModel;


public class DefaultFMYearMakeModelVehicleTypeDAO extends DefaultGenericDao<FMYearMakeModelVehicleTypeModel> implements
		FMYearMakeModelVehicleTypeDAO
{

	private static final Logger LOG = Logger.getLogger(DefaultFMYearMakeModelVehicleTypeDAO.class);


	/**
	 * @param typecode
	 */
	public DefaultFMYearMakeModelVehicleTypeDAO(final String typecode)
	{
		super(typecode);
		LOG.info("DefaultFMFitmentSearchDAO :: typecode " + typecode);
		// YTODO Auto-generated constructor stub
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMYearMakeModelVehicleTypeDAO#getVehicleTypeData(java.lang.String)
	 */
	@Override
	public List<String> getFitmentData()
	{
		// YTODO Auto-generated method stub

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMYearMakeModelVehicleTypeModel.VEHICLETYPE + "} ");
		query.append("FROM  {" + FMYearMakeModelVehicleTypeModel._TYPECODE + " AS fit } ");
		query.append(" ORDER BY {fit:" + FMYearMakeModelVehicleTypeModel.VEHICLETYPE + "} ");
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
	public List<String> getYearData(final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMYearMakeModelVehicleTypeModel.YEAR + "} ");
		query.append("FROM  {" + FMYearMakeModelVehicleTypeModel._TYPECODE + " AS fit } ");
		query.append("WHERE {fit." + FMYearMakeModelVehicleTypeModel.VEHICLETYPE + "}=?"
				+ FMYearMakeModelVehicleTypeModel.VEHICLETYPE);
		query.append(" ORDER BY {fit:" + FMYearMakeModelVehicleTypeModel.YEAR + "} DESC");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMYearMakeModelVehicleTypeModel.VEHICLETYPE, vehicleSegment);

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
	public List<String> getMakeData(final String year, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");
		validateParameterNotNull(year, "year  must not be null!");

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMYearMakeModelVehicleTypeModel.MAKE + "} ");
		query.append("FROM  {" + FMYearMakeModelVehicleTypeModel._TYPECODE + " AS fit } ");
		query.append("WHERE {fit." + FMYearMakeModelVehicleTypeModel.VEHICLETYPE + "}=?"
				+ FMYearMakeModelVehicleTypeModel.VEHICLETYPE);
		query.append(" AND {fit." + FMYearMakeModelVehicleTypeModel.YEAR + "}=?" + FMYearMakeModelVehicleTypeModel.YEAR);
		query.append(" ORDER BY {fit:" + FMYearMakeModelVehicleTypeModel.MAKE + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMYearMakeModelVehicleTypeModel.VEHICLETYPE, vehicleSegment);
		flexibleSearchQuery.addQueryParameter(FMYearMakeModelVehicleTypeModel.YEAR, year);

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
	public List<String> getModelData(final String year, final String make, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(vehicleSegment, "vehicleSegment must not be null!");
		validateParameterNotNull(year, "year  must not be null!");
		validateParameterNotNull(make, "make  must not be null!");

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {fit:" + FMYearMakeModelVehicleTypeModel.MODEL + "} ");
		query.append("FROM  {" + FMYearMakeModelVehicleTypeModel._TYPECODE + " AS fit } ");
		query.append("WHERE {fit." + FMYearMakeModelVehicleTypeModel.VEHICLETYPE + "}=?"
				+ FMYearMakeModelVehicleTypeModel.VEHICLETYPE);
		query.append(" AND {fit." + FMYearMakeModelVehicleTypeModel.YEAR + "}=?" + FMYearMakeModelVehicleTypeModel.YEAR);
		query.append(" AND {fit." + FMYearMakeModelVehicleTypeModel.MAKE + "}=?" + FMYearMakeModelVehicleTypeModel.MAKE);
		query.append(" ORDER BY {fit:" + FMYearMakeModelVehicleTypeModel.MODEL + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMYearMakeModelVehicleTypeModel.VEHICLETYPE, vehicleSegment);
		flexibleSearchQuery.addQueryParameter(FMYearMakeModelVehicleTypeModel.YEAR, year);
		flexibleSearchQuery.addQueryParameter(FMYearMakeModelVehicleTypeModel.MAKE, make);

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<List> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		final List outputList = searchResult.getResult();

		return outputList;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMYearMakeModelVehicleTypeDAO#getAlsoFits(java.lang.String)
	 */
	public List<FMPartAlsoFitsModel> getAlsoFits(final String productCode)
	{
		// YTODO Auto-generated method stub
		LOG.info("productCode:::::::::::::::" + productCode);
		validateParameterNotNull(productCode, "productCode must not be null!");


		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(FMPartAlsoFitsModel.PK).append("} ");
		builder.append("FROM {").append(FMPartAlsoFitsModel._TYPECODE).append("} ");
		builder.append("WHERE {").append(FMPartAlsoFitsModel.CODE + "}=?" + FMPartAlsoFitsModel.CODE);

		LOG.debug("query to get Also Fits information : " + builder.toString());

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(FMPartAlsoFitsModel.CODE, productCode);
		final SearchResult<FMPartAlsoFitsModel> finalResults = getFlexibleSearchService().search(builder.toString(), params);

		return finalResults.getResult();


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMYearMakeModelVehicleTypeDAO#getRegion(java.lang.String)
	 */
	@Override
	public List<RegionModel> getRegion(final String name)
	{
		// YTODO Auto-generated method stub
		LOG.info("Region Name:::::::::::::::" + name);
		validateParameterNotNull(name, "productCode must not be null!");

		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(RegionModel.PK).append("} ");
		builder.append("FROM {").append(RegionModel._TYPECODE).append("} ");
		builder.append("WHERE {").append(RegionModel.ISOCODESHORT + "}=?" + RegionModel.ISOCODESHORT);

		LOG.debug("query to get Also Fits information : " + builder.toString());

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(RegionModel.ISOCODESHORT, name);
		final SearchResult<RegionModel> finalResults = getFlexibleSearchService().search(builder.toString(), params);

		return finalResults.getResult();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMYearMakeModelVehicleTypeDAO#getCountryModel(java.lang.String)
	 */
	@Override
	public List<CountryModel> getCountry(final String name)
	{
		// YTODO Auto-generated method stub
		LOG.info("country Name:::::::::::::::" + name);
		validateParameterNotNull(name, "productCode must not be null!");

		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(CountryModel.PK).append("} ");
		builder.append("FROM {").append(CountryModel._TYPECODE).append("} ");
		builder.append("WHERE {").append(CountryModel.ISOCODE + "}=?" + CountryModel.ISOCODE);

		LOG.debug("query to get Also Fits information : " + builder.toString());

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(CountryModel.ISOCODE, name);
		final SearchResult<CountryModel> finalResults = getFlexibleSearchService().search(builder.toString(), params);

		return finalResults.getResult();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMYearMakeModelVehicleTypeDAO#getBaseStore(java.lang.String)
	 */
	@Override
	public List<BaseStoreModel> getBaseStore(final String name)
	{
		// YTODO Auto-generated method stub
		LOG.info("Base Store Name:::::::::::::::" + name);
		validateParameterNotNull(name, "name must not be null!");

		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(BaseStoreModel.PK).append("} ");
		builder.append("FROM {").append(BaseStoreModel._TYPECODE).append("} ");
		builder.append("WHERE {").append(BaseStoreModel.UID + "}=?" + BaseStoreModel.UID);

		LOG.debug("query to get Also Fits information : " + builder.toString());

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(BaseStoreModel.UID, name);
		final SearchResult<BaseStoreModel> finalResults = getFlexibleSearchService().search(builder.toString(), params);

		return finalResults.getResult();
	}

	public List<PointOfServiceModel> getPointOfserviceName(String name)
	{

		LOG.info("Point Of Service Name:::::::::::::::" + name);
		validateParameterNotNull(name, "Point Of Service Name must not be null!");

		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(PointOfServiceModel.PK).append("} ");
		builder.append("FROM {").append(PointOfServiceModel._TYPECODE).append("} ");
		builder.append("WHERE {").append(PointOfServiceModel.NAME + "}=?" + PointOfServiceModel.NAME);

		LOG.debug("query to get Also Fits information : " + builder.toString());

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(PointOfServiceModel.NAME, name);
		final SearchResult<PointOfServiceModel> finalResults = getFlexibleSearchService().search(builder.toString(), params);

		return finalResults.getResult();

	}


}

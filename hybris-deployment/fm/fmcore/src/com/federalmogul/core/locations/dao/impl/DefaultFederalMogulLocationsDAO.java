/**
 * 
 */
package com.federalmogul.core.locations.dao.impl;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.locations.dao.FederalMogulLocationsDAO;
import com.federalmogul.falcon.core.model.FMZonesModel;


/**
 * @author SA297584
 * 
 */
public class DefaultFederalMogulLocationsDAO implements FederalMogulLocationsDAO
{

	private static final Logger LOG = Logger.getLogger(DefaultFederalMogulLocationsDAO.class);


	private FlexibleSearchService flexibleSearchService;

	@Autowired
	private ModelService modelService;



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.locations.dao.FederalMogulLocationsDAO#getALLFMLocations()
	 */
	@Override
	public List<AddressModel> getALLFMLocations(final String countryISOCode, final String stateISOCode)
	{

		LOG.info("getALLFMLocations DAO Impl Method Called :: ");

		StringBuilder query = null;
		LOG.info("countryISOCode " + countryISOCode);

		if (stateISOCode != null && stateISOCode != "")
		{
			query = new StringBuilder("SELECT {address." + AddressModel.PK + "} FROM ");
			query.append("{" + AddressModel._TYPECODE + " 	AS address " + " JOIN " + RegionModel._TYPECODE
					+ "	AS region 	ON {region.pk} = {address.region} " + " JOIN " + CountryModel._TYPECODE
					+ "	AS country	 ON {country.pk} = {address.country} }");
			query.append(" WHERE  {region.isocode} like '" + stateISOCode + "'");
			query.append(" AND  {country.isocode}  like '" + countryISOCode + "'");
			query.append(" AND  {" + AddressModel.COMPANY + "}  like 'FederalMogulCrop' ");
		}
		else
		{

			query = new StringBuilder("SELECT {address." + AddressModel.PK + "} FROM ");
			query.append("{" + AddressModel._TYPECODE + " 	AS address " + " JOIN " + CountryModel._TYPECODE
					+ "	AS country	 ON {country.pk} = {address.country} }");
			query.append(" WHERE   {country.isocode}  like '" + countryISOCode + "'");
			query.append(" AND  {" + AddressModel.COMPANY + "}  like 'FederalMogulCrop' ");
		}


		LOG.info("Address List  Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<AddressModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("GOT the Results and Its Size is :: " + result.getResult().size());
			final List<AddressModel> fmLocationsList = result.getResult();
			return fmLocationsList;
		}
		else
		{
			LOG.info("No Results Found");
			LOG.error("Result NULL");
			return null;
		}
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}




	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}




	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}




	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.locations.dao.FederalMogulLocationsDAO#getALLFMCountries()
	 */
	@Override
	public List<CountryModel> getALLFMCountries(final String zone)
	{

		LOG.info("getALLFMCountries DAO Impl Method Called :: ");

		StringBuilder query = null;


		query = new StringBuilder("SELECT {zone." + FMZonesModel.PK + "}");
		query.append("FROM {" + FMZonesModel._TYPECODE + " AS zone}");
		query.append(" WHERE {" + FMZonesModel.CODE + "} like '" + zone + "'");

		LOG.info("File Download List  Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMZonesModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("GOT the Results and Its Size is :: " + result.getResult().size());

			final List<CountryModel> fmCountriesList = result.getResult().get(0).getCountry();


			return fmCountriesList;
		}
		else
		{
			LOG.info("No Results Found");
			LOG.error("Result NULL");

			return null;
		}


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.locations.dao.FederalMogulLocationsDAO#getALLFMZones(java.lang.String)
	 */
	@Override
	public List<FMZonesModel> getALLFMZones()
	{

		LOG.info("getALLFMZones DAO Impl Method Called :: ");

		StringBuilder query = null;


		query = new StringBuilder("SELECT {zone." + FMZonesModel.PK + "}");
		query.append("FROM {" + FMZonesModel._TYPECODE + " AS zone}");

		LOG.info("File Download List  Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMZonesModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("GOT the Results and Its Size is :: " + result.getResult().size());

			final List<FMZonesModel> fmZonesList = result.getResult();


			return fmZonesList;
		}
		else
		{
			LOG.info("No Results Found");
			LOG.error("Result NULL");

			return null;
		}


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.locations.dao.FederalMogulLocationsDAO#getAllFMStates(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<RegionModel> getAllFMStates(final String zone, final String countryISOCode)
	{

		LOG.info("getALLFMZones DAO Impl Method Called :: ");

		StringBuilder query = null;


		query = new StringBuilder("SELECT {zone." + FMZonesModel.PK + "}");
		query.append("FROM {" + FMZonesModel._TYPECODE + " AS zone}");
		query.append(" WHERE {" + FMZonesModel.CODE + "} like '" + zone + "'");


		LOG.info("File Download List  Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMZonesModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			final List<RegionModel> fmStatesList = result.getResult().get(0).getRegion();
			final List<RegionModel> regList = new ArrayList<RegionModel>();

			for (final RegionModel regionModel : fmStatesList)
			{

				final String regCountryISOCode = regionModel.getCountry().getIsocode();

				if (regCountryISOCode != null && regCountryISOCode.equalsIgnoreCase(countryISOCode))
				{
					regList.add(regionModel);
				}
			}
			return regList;
		}
		else
		{
			LOG.info("No Results Found");
			LOG.error("Result NULL");

			return null;
		}


	}

}

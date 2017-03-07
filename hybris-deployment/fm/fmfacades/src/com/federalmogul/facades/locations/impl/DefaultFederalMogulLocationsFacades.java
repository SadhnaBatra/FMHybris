/**
 * 
 */
package com.federalmogul.facades.locations.impl;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.federalmogul.core.locations.dao.FederalMogulLocationsDAO;
import com.federalmogul.facades.locations.FederalMogulLocationsFacades;
import com.federalmogul.falcon.core.model.FMZonesModel;


/**
 * @author SA297584
 * 
 */
public class DefaultFederalMogulLocationsFacades implements FederalMogulLocationsFacades
{

	private static final Logger LOG = Logger.getLogger(DefaultFederalMogulLocationsFacades.class);
	/**
	 * 
	 */
	@Resource
	FederalMogulLocationsDAO federalMogulLocationsDAO;

	/**
	 * @return the federalMogulLocationsDAO
	 */
	public FederalMogulLocationsDAO getFederalMogulLocationsDAO()
	{
		return federalMogulLocationsDAO;
	}



	/**
	 * @param federalMogulLocationsDAO
	 *           the federalMogulLocationsDAO to set
	 */
	public void setFederalMogulLocationsDAO(final FederalMogulLocationsDAO federalMogulLocationsDAO)
	{
		this.federalMogulLocationsDAO = federalMogulLocationsDAO;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.locations.FederalMogulLocationsFacades#getAllFMLocations()
	 */
	@Override
	public List<AddressModel> getAllFMLocations(final String countryISOCode, final String stateISOCode)
	{

		return getFederalMogulLocationsDAO().getALLFMLocations(countryISOCode, stateISOCode);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.locations.FederalMogulLocationsFacades#getAllFMContries()
	 */
	@Override
	public List<CountryModel> getAllFMContries(final String zone)
	{
		return federalMogulLocationsDAO.getALLFMCountries(zone);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.locations.FederalMogulLocationsFacades#getAllFMZones()
	 */
	@Override
	public List<FMZonesModel> getAllFMZones()
	{
		return federalMogulLocationsDAO.getALLFMZones();
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.locations.FederalMogulLocationsFacades#getAllFMStates(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<RegionModel> getAllFMStates(final String zone, final String countryISOCode)
	{
		return federalMogulLocationsDAO.getAllFMStates(zone, countryISOCode);
	}
}

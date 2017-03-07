/**
 * 
 */
package com.federalmogul.core.locations.dao;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.List;

import com.federalmogul.falcon.core.model.FMZonesModel;


/**
 * @author SA297584
 * 
 */
public interface FederalMogulLocationsDAO
{
	/**
	 * 
	 * @return
	 */
	List<AddressModel> getALLFMLocations(String country, String state);

	/**
	 * 
	 * @return
	 */
	List<CountryModel> getALLFMCountries(String zone);

	/**
	 * 
	 * @param stateISOCode
	 * @return
	 */
	List<FMZonesModel> getALLFMZones();

	/**
	 * 
	 * @return
	 */
	List<RegionModel> getAllFMStates(String zone, String countryISOCode);


}

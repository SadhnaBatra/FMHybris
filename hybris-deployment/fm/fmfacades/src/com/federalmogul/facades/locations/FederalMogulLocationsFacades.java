/**
 * 
 */
package com.federalmogul.facades.locations;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.List;

import com.federalmogul.falcon.core.model.FMZonesModel;


/**
 * @author SA297584
 * 
 */
public interface FederalMogulLocationsFacades
{
	/**
	 * 
	 * @return
	 */
	List<AddressModel> getAllFMLocations(String countryISOCode, String stateISOCode);

	/**
	 * 
	 * @return
	 */
	List<CountryModel> getAllFMContries(String zone);

	/**
	 * 
	 * @return
	 */
	List<FMZonesModel> getAllFMZones();

	/**
	 * 
	 * @param zone
	 * @param countryISOCode
	 * @return
	 */
	public List<RegionModel> getAllFMStates(final String zone, final String countryISOCode);

}

/**
 * 
 */
package com.federalmogul.core.search.dao;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.List;

import com.federalmogul.falcon.core.model.FMPartAlsoFitsModel;


/**
 * @author SU276498
 * 
 */
public interface FMYearMakeModelVehicleTypeDAO
{

	List<String> getFitmentData();

	List<String> getModelData(String year, String make, String vehicleSegment);

	/**
	 * @param vehicleSegment
	 * @return
	 */
	List<String> getYearData(String vehicleSegment);

	/**
	 * @param year
	 * @param vehicleSegment
	 * @return
	 */
	List<String> getMakeData(String year, String vehicleSegment);

	/**
	 * 
	 * @param productCode
	 * @return
	 */
	List<FMPartAlsoFitsModel> getAlsoFits(String productCode);

	/**
	 * Added for Where to Buy Dealer Region
	 * 
	 * @param name
	 * @return
	 */
	List<RegionModel> getRegion(String name);

	/**
	 * Added for Where to Buy Dealer Country
	 * 
	 * @param name
	 * @return
	 */
	List<CountryModel> getCountry(String name);

	/**
	 * Added for Where to Buy Dealer Base Store
	 * 
	 * @param name
	 * @return
	 */
	List<BaseStoreModel> getBaseStore(String name);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<PointOfServiceModel> getPointOfserviceName(String name);

}

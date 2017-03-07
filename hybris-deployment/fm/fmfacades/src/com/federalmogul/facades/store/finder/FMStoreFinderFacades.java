/**
 * 
 */
package com.federalmogul.facades.store.finder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.federalmogul.core.model.BrandInformationModel;
import com.federalmogul.core.model.TSCLocationModel;
import com.federalmogul.facades.storelocator.data.DistributorData;
import com.federalmogul.facades.storelocator.data.OnlineRetailerData;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;




/**
 * @author anapande
 * 
 */
public interface FMStoreFinderFacades
{

	Map<TSCLocationModel, Integer> getTSCLocationByZipCode(final String zipCode) throws IOException;

	TSCLocationModel getStore(final String code);
	
	StoreFinderSearchPageData<PointOfServiceData> positionSearch(GeoPoint geoPoint, PageableData pageableData,
			Double maxRadius,String shopType,String brand, String partType, String subBrand,String countryIsoCode,String radiusMeasurementType);
	List<BrandInformationModel>  getBrandInformationByName(String brand);
	
	List<DistributorData> getDistributors(String country,String brand);
	
	List<OnlineRetailerData> getOnlineRetailers(String country,String brand);
	
	Map<String,Map<String,String>> getDealerLocatorMasterData();
	

}

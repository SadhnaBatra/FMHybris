package com.federalmogul.core.services;

import java.util.List;

import com.federalmogul.core.model.DistributorModel;
import com.federalmogul.core.model.OnlineRetailerModel;
import com.federalmogul.facades.storelocator.data.DealerLocatorMasterData;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.store.BaseStoreModel;

public interface FMStoreFinderService {
	  public StoreFinderSearchPageData<PointOfServiceDistanceData> getPOSLocationsForBrand(BaseStoreModel baseStoreModel,GeoPoint geoPoint,PageableData pageableData,
			  Double maxRadius,String shopType,String brand,String partType, String subBrand);
	  
	  public List<DistributorModel> getDistributorsByCountryAndBrand(String countryIsoCode,String brand);
	  
	  public List<OnlineRetailerModel> getOnlineRetailersByCountryAndBrand(String countryIsoCode,String brand);
	  
	  public List<DealerLocatorMasterData> getDealerLocatorMasterData();
}

package com.federalmogul.core.search.dao;

import java.util.List;

import com.federalmogul.core.model.DistributorModel;
import com.federalmogul.core.model.OnlineRetailerModel;
import com.federalmogul.facades.storelocator.data.DealerLocatorMasterData;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public interface FMStoreFinderDAO {
	 public List<PointOfServiceModel> getPOSLocationsForBrand(BaseStoreModel baseStoreModel, GeoPoint geoPoint,
				PageableData pageableData, Double maxRadius,String shopType, String brand,
				String partType, String subBrand);
	   
	   public List<DistributorModel> getDistributorsByCountryAndBrand(CountryModel country,String brand);
	   
	   public List<OnlineRetailerModel> getOnlineRetailersByCountryAndBrand(CountryModel country,String brand);
	   
	   public List<DealerLocatorMasterData> getDealerLocatorMasterData();   
}

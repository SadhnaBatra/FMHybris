package com.federalmogul.core.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.model.DistributorModel;
import com.federalmogul.core.model.OnlineRetailerModel;
import com.federalmogul.core.search.dao.FMStoreFinderDAO;
import com.federalmogul.core.services.FMStoreFinderService;
import com.federalmogul.facades.storelocator.data.DealerLocatorMasterData;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.commerceservices.storefinder.impl.DefaultStoreFinderService;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.exception.PointOfServiceDaoException;
import de.hybris.platform.storelocator.impl.DefaultGPS;
import de.hybris.platform.storelocator.impl.GeometryUtils;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class DefaultFMStoreFinderService extends DefaultStoreFinderService implements FMStoreFinderService {

	@Autowired
	private FMStoreFinderDAO fMStoreFinderDAO;
	
	@Resource
	private CommonI18NService commonI18NService;

	@Override
	public StoreFinderSearchPageData<PointOfServiceDistanceData> getPOSLocationsForBrand(
			BaseStoreModel baseStoreModel, GeoPoint geoPoint,
			PageableData pageableData, Double maxRadius,String shopType, String brand,
			String partType, String subBrand) {
		// TODO Auto-generated method stub
		 int resultRangeStart = pageableData.getCurrentPage() * pageableData.getPageSize();
		    int resultRangeEnd = (pageableData.getCurrentPage() + 1) * pageableData.getPageSize();
	    
		List<PointOfServiceModel> posResults = fMStoreFinderDAO
				.getPOSLocationsForBrand(baseStoreModel, geoPoint,
						pageableData, maxRadius,shopType, brand, partType, subBrand);
		if (posResults != null)
	    {
	      List orderedResults = calculateDistances(geoPoint, posResults);
	      PaginationData paginationData = createPagination(pageableData, posResults.size());
	      
	      List  orderedResultsWindow = orderedResults.subList(Math.min(orderedResults.size(), resultRangeStart), 
	        Math.min(orderedResults.size(), resultRangeEnd));
	      
	      return createSearchResult(null, geoPoint, orderedResultsWindow, paginationData);
	    }
		return null;
	}

	@Override
	public List<DistributorModel> getDistributorsByCountryAndBrand(String countryIsoCode,String brand) {
		// TODO Auto-generated method stub
		CountryModel countryModel = commonI18NService.getCountry(countryIsoCode);
		List<DistributorModel> distributorsList = fMStoreFinderDAO.getDistributorsByCountryAndBrand(countryModel, brand);
		return distributorsList;
	}

	@Override
	public List<OnlineRetailerModel> getOnlineRetailersByCountryAndBrand(String countryIsoCode,String brand) {
		CountryModel countryModel = commonI18NService.getCountry(countryIsoCode);
		List<OnlineRetailerModel> onlineRetailerList = fMStoreFinderDAO.getOnlineRetailersByCountryAndBrand(countryModel,brand);
		return onlineRetailerList;
	}

	@Override
	public List<DealerLocatorMasterData> getDealerLocatorMasterData() {
		return fMStoreFinderDAO.getDealerLocatorMasterData();
	}


}

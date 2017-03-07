/**
 * 
 */
package com.federalmogul.facades.store.finder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.model.BrandInformationModel;
import com.federalmogul.core.model.DistributorModel;
import com.federalmogul.core.model.OnlineRetailerModel;
import com.federalmogul.core.model.TSCLocationModel;
import com.federalmogul.core.services.FMCommonServices;
import com.federalmogul.core.services.FMStoreFinderService;
import com.federalmogul.core.tsclookup.TSCLookupService;
import com.federalmogul.facades.storelocator.data.DealerLocatorMasterData;
import com.federalmogul.facades.storelocator.data.DistributorData;
import com.federalmogul.facades.storelocator.data.OnlineRetailerData;

import de.hybris.platform.commercefacades.storefinder.impl.DefaultStoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.util.Config;


/**
 * @author anapande
 * 
 */
public class FMStoreFinderFacadesImpl extends DefaultStoreFinderFacade implements FMStoreFinderFacades
{

	private static final Logger LOG = Logger.getLogger(FMStoreFinderFacadesImpl.class);


	@Resource(name = "tsclookupService")
	private TSCLookupService tsclookupService;
	@Resource
	private FMStoreFinderService fmStoreFinderService;
	@Resource
	private FMCommonServices fmCommonServices;
	
    private Converter<DistributorModel,DistributorData> distributorConverter;
	
	private Converter<OnlineRetailerModel,OnlineRetailerData> onlineRetailerConverter;
	
	@Resource
	private CommonI18NService commonI18NService;


	public Converter<DistributorModel, DistributorData> getDistributorConverter() {
		return distributorConverter;
	}


	public void setDistributorConverter(
			Converter<DistributorModel, DistributorData> distributorConverter) {
		this.distributorConverter = distributorConverter;
	}


	public Converter<OnlineRetailerModel, OnlineRetailerData> getOnlineRetailerConverter() {
		return onlineRetailerConverter;
	}


	public void setOnlineRetailerConverter(
			Converter<OnlineRetailerModel, OnlineRetailerData> onlineRetailerConverter) {
		this.onlineRetailerConverter = onlineRetailerConverter;
	}


	@Override
	public Map<TSCLocationModel, Integer> getTSCLocationByZipCode(final String zipCode) throws IOException
	{

		LOG.info("inside  getTSCLocationByZipCode in service");

		LOG.info("inside getTSCLocationByZipCode in FacadesImpl");


		final Map<TSCLocationModel, Integer> listValues = tsclookupService.getTSCLocationByZipCode(zipCode);

		try
		{
			LOG.error("ListValues object's value in Facade==>" + listValues);
		}
		catch (final NullPointerException e)
		{
			LOG.error("inside catch in FacadesImpl");

		}


		return listValues;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.store.finder.FMStoreFinderFacades#getStore(java.lang.String)
	 */
	@Override
	public TSCLocationModel getStore(final String code)
	{
		// YTODO Auto-generated method stub

		return tsclookupService.getStore(code);
	}



	@Override
	public StoreFinderSearchPageData<PointOfServiceData> positionSearch(
			GeoPoint geoPoint, PageableData pageableData, Double maxRadius,String shopType,
			String brand, String partType, String subBrand,String countryIsoCode, String radiusMeasurementType) {
		CountryModel countryModel = commonI18NService.getCountry(countryIsoCode.toUpperCase());
		BaseStoreModel currentBaseStore=null;
		if(null!=countryModel) 
		{
			String baseStoreName=null;
			if(("US".equals(countryModel.getIsocode())||"CA".equals(countryModel.getIsocode()))){
				baseStoreName = Config.getParameter(FmCoreConstants.STORE_NAME); 
			}else{
			   baseStoreName = Config.getParameter(FmCoreConstants.STORE_NAME_EMEA); 	 
		  }
		  currentBaseStore = getBaseStoreService().getBaseStoreForUid(baseStoreName);
		}  
		final StoreFinderSearchPageData<PointOfServiceDistanceData> searchPageData = fmStoreFinderService.getPOSLocationsForBrand(
				currentBaseStore, geoPoint, pageableData, maxRadius,shopType,brand, partType, subBrand);
		List<PointOfServiceDistanceData> inputDataList = searchPageData.getResults();
		List<PointOfServiceDistanceData> outputDataList = new ArrayList<PointOfServiceDistanceData>();
		for(PointOfServiceDistanceData pointOfServiceDistanceData:inputDataList)
		{
			pointOfServiceDistanceData.setRadiusMeasurementType(radiusMeasurementType);
			if(StringUtils.isBlank(pointOfServiceDistanceData.getPointOfService().getAddress().getLine2())){
				pointOfServiceDistanceData.getPointOfService().getAddress().setLine2(null);
			}
			outputDataList.add(pointOfServiceDistanceData);
		}
		searchPageData.setResults(outputDataList);
		return getSearchPagePointOfServiceDistanceConverter().convert(searchPageData);
	}


	@Override
	public List<BrandInformationModel> getBrandInformationByName(String brand) {
		// TODO Auto-generated method stub
		return fmCommonServices.getBrandInformationByName(brand);
	}


	@Override
	public List<DistributorData> getDistributors(String country,String brand) {
		// TODO Auto-generated method stub
		List<DistributorModel> distributorsList = fmStoreFinderService.getDistributorsByCountryAndBrand(country,brand);
		List<DistributorData> distributors = null;
		if(distributorsList!=null && distributorsList.size()>0)
		{
			distributors = new ArrayList<DistributorData>();
			for (DistributorModel model:distributorsList){
				DistributorData data = distributorConverter.convert(model);
				distributors.add(data);
			}
		}
		return distributors;
	}


	@Override
	public List<OnlineRetailerData> getOnlineRetailers(String country,String brand) {
		List<OnlineRetailerModel> onlineRetailersList = fmStoreFinderService.getOnlineRetailersByCountryAndBrand(country,brand);
		List<OnlineRetailerData> onlineRetailers = null;
		if(onlineRetailersList!=null && onlineRetailersList.size()>0)
		{
			onlineRetailers = new ArrayList<OnlineRetailerData>();
			for (OnlineRetailerModel model:onlineRetailersList){
				OnlineRetailerData data = onlineRetailerConverter.convert(model);
				onlineRetailers.add(data);
			}
		}
		return onlineRetailers;
	}

	public Map<String,Map<String,String>> getDealerLocatorMasterData() {
		List<DealerLocatorMasterData> dealerLocatorMasterDatas = fmStoreFinderService.getDealerLocatorMasterData();
		Map<String,Map<String,String>> dealerLocatorDataMap= new HashMap<String,Map<String,String>>();
		Map<String,String> shopTypeBrandMap= null;
		for(DealerLocatorMasterData data:dealerLocatorMasterDatas){
			if(!dealerLocatorDataMap.containsKey(data.getCountryISO())){
				shopTypeBrandMap= new HashMap<String,String>();
				shopTypeBrandMap.put(data.getShopType(), data.getBrand());	
			}else{
				shopTypeBrandMap = dealerLocatorDataMap.get(data.getCountryISO());
				if(!shopTypeBrandMap.containsKey(data.getShopType())){
					shopTypeBrandMap.put(data.getShopType(),data.getBrand());
				}else{
					String brand = shopTypeBrandMap.get(data.getShopType());
					brand = brand + "," + data.getBrand();
					shopTypeBrandMap.put(data.getShopType(),brand);
				}	
			}
			dealerLocatorDataMap.put(data.getCountryISO(),shopTypeBrandMap);
		}
		return dealerLocatorDataMap;
	}



}

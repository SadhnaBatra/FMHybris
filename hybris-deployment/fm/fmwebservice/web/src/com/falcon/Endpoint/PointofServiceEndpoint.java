/**
 * 
 */
package com.falcon.Endpoint;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.model.BrandInformationModel;
import com.federalmogul.core.util.FMUtils;


import com.federalmogul.facades.store.finder.FMStoreFinderFacades;
import com.federalmogul.facades.storelocator.data.DistributorData;
import com.federalmogul.facades.storelocator.data.OnlineRetailerData;
import com.fm.services.Brands;
import com.fm.services.DealerLocatorMasterDataResponse;
import com.fm.services.DealerLocatorMasterDataResponse.Country;
import com.fm.services.DistributorResponse;
import com.fm.services.DistributorResponse.Distributor;
import com.fm.services.GetBrandInformationRequest;
import com.fm.services.GetBrandInformationResponse;
import com.fm.services.GetBrandInformationResponse.Brand;
import com.fm.services.GetDealerLocatorMasterDataRequest;
import com.fm.services.GetDealerLocatorMasterDataResponse;
import com.fm.services.GetDistributorsRequest;
import com.fm.services.GetDistributorsResponse;
import com.fm.services.GetOnlineRetailersRequest;
import com.fm.services.GetOnlineRetailersResponse;
import com.fm.services.OnlineRetailerResponse;
import com.fm.services.OnlineRetailerResponse.OnlineRetailer;
import com.fm.services.PartType;
import com.fm.services.PointofserviceRequest;
import com.fm.services.PointofserviceResponse;
import com.fm.services.PointofserviceResponseType;
import com.fm.services.PointofserviceResponseType.Store;
import com.fm.services.ShopType;
import com.fm.services.SubBrands;

import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.core.Registry;
import de.hybris.platform.util.Config;


/**
 * Author SU276498
 */

@Endpoint
@Component
public class PointofServiceEndpoint
{
	private static final Logger LOG = Logger.getLogger(PointofServiceEndpoint.class);
	
	private FMStoreFinderFacades fmStoreFinderFacades;
	
	public FMStoreFinderFacades getFmStoreFinderFacades() {
		return fmStoreFinderFacades;
	}

	public void setFmStoreFinderFacades(FMStoreFinderFacades fmStoreFinderFacades) {
		this.fmStoreFinderFacades = fmStoreFinderFacades;
	}

	@PayloadRoot(localPart = "pointofserviceRequest", namespace = "http://services.fm.com")
	@ResponsePayload
	public PointofserviceResponse getPointofService(@RequestPayload final PointofserviceRequest addRequest)
	{
		LOG.info("Inside getPointofService() ");
		final PointofserviceResponse response = new PointofserviceResponse();
		final PointofserviceResponseType ordResp = new PointofserviceResponseType();
		try
		{
			String brand = addRequest.getPointofserviceInfo().getBrand();
			String shopType = addRequest.getPointofserviceInfo().getShopType();
			final String zipCode = addRequest.getPointofserviceInfo().getLocationQuery();
			String radius = addRequest.getPointofserviceInfo().getRadius();
			final String showNearest = addRequest.getPointofserviceInfo().getShowNearest();
			final String partType  = addRequest.getPointofserviceInfo().getPartType();
			final String subBrand = addRequest.getPointofserviceInfo().getSubBrand();
			final String countryIsoCode=addRequest.getPointofserviceInfo().getCountry();
			String radiusMeasurementType=addRequest.getPointofserviceInfo().getRadiusMeasurementType();
			
			LOG.info("Brand:::::::::::::::::: " + brand);
			LOG.info("shopType:::::::::::::::::: " + shopType);
			LOG.info("zipCode:::::::::::::::::: " + zipCode);
			LOG.info("radius:::::::::::::::::: " + radius);
			LOG.info("showNearest:::::::::::::::::: " + showNearest);
			
			Registry.activateMasterTenant();
			String url = Config.getParameter(FmCoreConstants.GEOCODE_URL);
			url += URLEncoder.encode(zipCode, "UTF-8");
			
			// calling readURL method
			final String content = FMUtils.readURLContent(url);
			final String latlng = FMUtils.extractMiddle(content, "\"location\" : {", "},");
			final double latitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lat\" :", ","));
			final double longitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lng\" :"));

			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude);
			geoPoint.setLongitude(longitude);

			Double maxRadius = null;
			Double actualRadius = null;
			/*if (radius.isEmpty() || null==radius || radius.equals("?"))
			{
				//maxRadius = null;
				actualRadius=null;
			}else{*/
			if (!(radius.isEmpty() || null==radius || radius.equals("?")))
			{	
				if(!radiusMeasurementType.equalsIgnoreCase(FmCoreConstants.KILOMETER)){
					maxRadius = Double.parseDouble(radius) * FmCoreConstants.MILESTOKMCONVERSIONRATIO;
					radiusMeasurementType=FmCoreConstants.MILES;
				}else{
					maxRadius = Double.parseDouble(radius);
				}
				actualRadius=Double.parseDouble(radius);
			}
					
			StoreFinderSearchPageData<PointOfServiceData> searchResult = fmStoreFinderFacades.positionSearch(geoPoint, createPageableData(), maxRadius,shopType, brand, partType, subBrand,countryIsoCode,radiusMeasurementType);
			LOG.info(searchResult);	
			final GeoPoint newGeoPoint = new GeoPoint();
			final List<PointOfServiceData> pointOfServiceList = new ArrayList<PointOfServiceData>();
			for (int i = 0; i < searchResult.getResults().size(); i++)
			{
				String[] mapRadius =null;
				if(FmCoreConstants.KILOMETER.equalsIgnoreCase(radiusMeasurementType)){
					mapRadius = searchResult.getResults().get(i).getFormattedDistance().split(FmCoreConstants.KILOMETER);
				}else{
					mapRadius = searchResult.getResults().get(i).getFormattedDistance().split(" "+FmCoreConstants.MILES);
				}
				
				
			    if ( ((showNearest.isEmpty() || showNearest.equals("?") || (Integer.parseInt(showNearest)) > pointOfServiceList
					.size())))
				{
				   if(null==actualRadius||actualRadius >= Double.parseDouble(mapRadius[0].toString()))
				   {		   
					  pointOfServiceList.add(searchResult.getResults().get(i));
					  newGeoPoint.setLatitude(searchResult.getSourceLatitude());
					  newGeoPoint.setLongitude(searchResult.getSourceLongitude());
				   } 
			    }
			}

			int count = 0;
			ordResp.setTotalStores(pointOfServiceList.size());
			for (PointOfServiceData posStore:pointOfServiceList)
			{
				Store pointofserviceStore=populateResponseObjectForPOSLocation(posStore);
				Brands brands = new Brands();
				if(StringUtils.isEmpty(brand)||FmCoreConstants.ALL.equalsIgnoreCase(brand)||"?".equals(brand))
				{
					brands.getBrand().addAll(posStore.getBrandList());
				}else{
				    brands.getBrand().add(brand);
				}    
				pointofserviceStore.getBrands().add(brands);
				if(!"?".equals(partType))
				{	
				pointofserviceStore.setPartType(partType);
				}else{
					pointofserviceStore.setPartType("");
				}
				if(!"?".equals(subBrand))
				{	
				pointofserviceStore.setSubBrand(subBrand);
				}else{
					pointofserviceStore.setSubBrand("");
				}
				//Override for Champion -- SOCAL-43
				//This is a workaround for now . Permanent fix will be implemented and this use case will be data driven.
				//The steps for permanent fix is also provided in SOCAL-43, which will be implemented at a later date
				if(FmCoreConstants.CHAMPION.equalsIgnoreCase(brand))
				{		
				 if(pointofserviceStore.getPointofserviceName().equalsIgnoreCase(FmCoreConstants.PEP_BOYS)){
					pointofserviceStore.setIsPreferredVendor(true);
				 }else{
					pointofserviceStore.setIsPreferredVendor(false);
				 }
				}
				ordResp.getStore().add(count, pointofserviceStore);
		    	count++;
		 	}
			ordResp.setMsgCode("0");
			ordResp.setMsgDesc("success");
			response.setPointofserviceResponseInfo(ordResp);
		    return response;
		}
		catch (final Exception e)
		{
			e.printStackTrace();
			ordResp.setMsgCode("1");
			ordResp.setMsgDesc("failure");
			response.setPointofserviceResponseInfo(ordResp);
			return response;
		}
	}	
	@PayloadRoot(localPart = "getBrandInformationRequest", namespace = "http://services.fm.com")
	@ResponsePayload
	public GetBrandInformationResponse getBrandInformation(@RequestPayload final GetBrandInformationRequest getBrandInformationRequest)
	{
		LOG.info("inside here");
		String brand = getBrandInformationRequest.getBrand();
		Registry.activateMasterTenant();
		List<BrandInformationModel> brandInformationList = fmStoreFinderFacades.getBrandInformationByName(brand);
		Map<String,String> partTypeSubBrandMap = new HashMap<String,String>();
		for(BrandInformationModel model:brandInformationList){
			if(partTypeSubBrandMap.containsKey(model.getPartType())){
				String value = partTypeSubBrandMap.get(model.getPartType());
				value = value +";"+model.getSubBrand();
				partTypeSubBrandMap.put(model.getPartType(), value);
			}else{
				partTypeSubBrandMap.put(model.getPartType(), model.getSubBrand());	
			}
		}
		GetBrandInformationResponse brandInformationResponse = new GetBrandInformationResponse();
		Brand brandResponse = new Brand();
		brandResponse.setBrandName(brand);
		brandInformationResponse.setBrand(brandResponse);		

		Iterator<String> it = partTypeSubBrandMap.keySet().iterator();
		while(it.hasNext()){
			String partType = (String)it.next();
			if(null!=partType){
			PartType pt= new PartType();
			pt.setPartTypeName(partType);
			String subBrand = partTypeSubBrandMap.get(partType);
			if(null!=subBrand)
			{	
			  String[] arrayOfSubBrands = subBrand.split(";");
			  List<String> subBrandList = new ArrayList<String>(Arrays.asList(arrayOfSubBrands));
			  SubBrands subBrands = new SubBrands();
			  if(subBrandList.size()>0){
				  for(String sb:subBrandList)
				  {	  
					  subBrands.getSubBrandName().add(sb);
				  }
			  }		 
			  pt.setSubBrands(subBrands);
			 } 
			brandInformationResponse.getBrand().getPartType().add(pt);
		  }
		}
		return brandInformationResponse;
	}

	@PayloadRoot(localPart = "getDistributorsRequest", namespace = "http://services.fm.com")
	@ResponsePayload
	public GetDistributorsResponse getDistributors(@RequestPayload final GetDistributorsRequest distributorsRequest)
	{   
		Registry.activateMasterTenant();
		String countryISO = distributorsRequest.getDistributorsRequestInfo().getCountry();
	    String brand =  distributorsRequest.getDistributorsRequestInfo().getBrand();
	    List<DistributorData> distributorDataList = fmStoreFinderFacades.getDistributors(countryISO,brand);
	    GetDistributorsResponse response=new GetDistributorsResponse();
	    DistributorResponse distributorResponseInfo = new DistributorResponse();
	    if(distributorDataList!=null && distributorDataList.size()>0)
	    {
	    	distributorResponseInfo.setTotalStores(distributorDataList.size());
	    	int count = 0;
	    	for(DistributorData data : distributorDataList){
	    		Distributor distributor = new Distributor();
	    		distributor.setCountryName(data.getCountryName());
	    		distributor.setDistributorName(data.getDistributorName());		
	    		distributor.setFormattedAddress(data.getAddress()!=null?data.getAddress().getFormattedAddress():"");
	    		distributor.setOfficePhone(data.getOfficePhone());
	    		distributor.setStoreWebsite(data.getStoreWebsite());
	    		distributor.setEmail(data.getEmail());
	    		Brands brands = new Brands();
	    		if(StringUtils.isEmpty(brand)||FmCoreConstants.ALL.equalsIgnoreCase(brand)||"?".equals(brand))
				{
					brands.getBrand().addAll(data.getBrandList());
				}else{
				    brands.getBrand().add(brand);
				}    
	    		distributor.getBrands().add(brands);
	    		distributorResponseInfo.getDistributor().add(count,distributor);
	    		count++;
	    	}
	    	distributorResponseInfo.setMsgCode("0");
	    	distributorResponseInfo.setMsgDesc("success");
	    }
	    response.setDistributorsResponseInfo(distributorResponseInfo);
	    return response;
	}
	
	@PayloadRoot(localPart = "getOnlineRetailersRequest", namespace = "http://services.fm.com")
	@ResponsePayload
	public GetOnlineRetailersResponse getOnlineRetailers(@RequestPayload final GetOnlineRetailersRequest onlineRetailersRequest)
	{   
		Registry.activateMasterTenant();
		String countryISO = onlineRetailersRequest.getOnlineRetailerRequestInfo().getCountry();
	    String brand =   onlineRetailersRequest.getOnlineRetailerRequestInfo().getBrand();
	    List<OnlineRetailerData> onlineRetailerDataList = fmStoreFinderFacades.getOnlineRetailers(countryISO, brand);
	    GetOnlineRetailersResponse response=new GetOnlineRetailersResponse();
	    OnlineRetailerResponse onlineRetailerResponseInfo = new OnlineRetailerResponse();
	    if(onlineRetailerDataList!=null && onlineRetailerDataList.size()>0)
	    {
	    	onlineRetailerResponseInfo.setTotalStores(onlineRetailerDataList.size());
	    	int count = 0;
	    	for(OnlineRetailerData data : onlineRetailerDataList){
	    		OnlineRetailer onlineRetailer = new OnlineRetailer();
	    		onlineRetailer.setCountryName(data.getCountryName());
	    		onlineRetailer.setDistributorName(data.getOnlineRetailerName());		
	    		onlineRetailer.setFormattedAddress(data.getAddress()!=null?data.getAddress().getFormattedAddress():"");	 
	    		onlineRetailer.setOfficePhone(data.getOfficePhone());
	    		onlineRetailer.setStoreWebsite(data.getStoreWebsite());
	    		onlineRetailer.setEmail(data.getEmail());
	    		Brands brands = new Brands();
	    		if(StringUtils.isEmpty(brand)||FmCoreConstants.ALL.equalsIgnoreCase(brand)||"?".equals(brand))
				{
					brands.getBrand().addAll(data.getBrandList());
				}else{
				    brands.getBrand().add(brand);
				}    
	    		onlineRetailer.getBrands().add(brands);
	    		onlineRetailerResponseInfo.getOnlineRetailer().add(count,onlineRetailer);
	    		count++;
	    	}
	    	onlineRetailerResponseInfo.setMsgCode("0");
	    	onlineRetailerResponseInfo.setMsgDesc("success");
	    }
	    response.setOnlineRetailerResponseInfo(onlineRetailerResponseInfo);
	    return response;
	}
	
	@PayloadRoot(localPart = "getDealerLocatorMasterDataRequest", namespace = "http://services.fm.com")
	@ResponsePayload
	public GetDealerLocatorMasterDataResponse getDealerLocatorMasterData(@RequestPayload final GetDealerLocatorMasterDataRequest request)
	{ 
		Registry.activateMasterTenant();
		GetDealerLocatorMasterDataResponse response = new GetDealerLocatorMasterDataResponse();
		Map<String,Map<String,String>> dealerLocatorDataMap = null;
		dealerLocatorDataMap = fmStoreFinderFacades.getDealerLocatorMasterData();
		Set<String> countrySet = dealerLocatorDataMap.keySet();
		Iterator<String> iter = countrySet.iterator();	
		while(iter.hasNext()){
			String countryIso = (String)iter.next();
			DealerLocatorMasterDataResponse dealerLocatorMasterDataResponse =new DealerLocatorMasterDataResponse();
			Country country = new Country();
			country.setCountryISO(countryIso);
			dealerLocatorMasterDataResponse.setCountry(country);
			Map<String,String> shopTypeBrandMap = dealerLocatorDataMap.get(countryIso);
			Set<String> keySet = shopTypeBrandMap.keySet();
			Iterator<String> shopTypeIterator = keySet.iterator();
			List<ShopType> shopTypeList = country.getShopType();
			while(shopTypeIterator.hasNext()){
				String shopType = shopTypeIterator.next();			
				ShopType st = new ShopType();
				st.setShopTypeName(shopType);
				String brands = shopTypeBrandMap.get(shopType);
				List<String> brandList = Arrays.asList(brands.split(","));
				Brands brand = new Brands();
				brand.getBrand().addAll(brandList);
				st.setBrands(brand);
				shopTypeList.add(st);
			}
			response.getDealerLocatorMasterDataResponseInfo().add(dealerLocatorMasterDataResponse);
		}
		return response;
	}
	

	public PageableData createPageableData()
	{
		final PageableData pageableData = new PageableData();
		pageableData.setPageSize(260000);
		return pageableData;
	}
	
	private Store populateResponseObjectForPOSLocation(PointOfServiceData pointOfServiceData){
		final Store pointofserviceStore = new Store();
		pointofserviceStore.setPointofserviceName(pointOfServiceData.getDisplayName());
		pointofserviceStore.setLongitude(String.valueOf(pointOfServiceData.getGeoPoint().getLongitude()));
		pointofserviceStore.setLatitude(String.valueOf(pointOfServiceData.getGeoPoint().getLatitude()));
		if (pointOfServiceData.getAddress() != null){
			pointofserviceStore.setFormattedAddress(pointOfServiceData.getAddress().getFormattedAddress());
			if(null!=pointOfServiceData.getAddress().getCountry())
			{
				pointofserviceStore.setCountry(pointOfServiceData.getAddress().getCountry().getIsocode());
			}else{
				pointofserviceStore.setCountry("");
			}
			if(null!=pointOfServiceData.getAddress().getPhone())
			{
				pointofserviceStore.setOfficePhone(pointOfServiceData.getAddress().getPhone());
			}else{
				pointofserviceStore.setOfficePhone("");
			}
		}
		pointofserviceStore.setDistance(pointOfServiceData.getFormattedDistance());
		pointofserviceStore.setShopType(pointOfServiceData.getShopType());
		pointofserviceStore.setIsPreferredVendor(pointOfServiceData.isPreferredVendor());
		pointofserviceStore.setIsActive(pointOfServiceData.isActive());
		pointofserviceStore.setStorewebsite(pointOfServiceData.getStoreWebsite());
		pointofserviceStore.setEmail(pointOfServiceData.getAddress().getEmail());
		pointofserviceStore.setStorewebsite(pointOfServiceData.getStoreWebsite());
		pointofserviceStore.setScheduleAppointmentUrl(pointOfServiceData.getScheduleAppointmentUrl());
		return pointofserviceStore;
	}


}

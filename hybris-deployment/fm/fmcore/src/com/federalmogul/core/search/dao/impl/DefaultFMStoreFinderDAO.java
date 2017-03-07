package com.federalmogul.core.search.dao.impl;

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
import com.federalmogul.core.search.dao.FMStoreFinderDAO;
import com.federalmogul.core.util.FMUtils;
import com.federalmogul.facades.storelocator.data.DealerLocatorMasterData;

import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.storelocator.GPS;
import de.hybris.platform.storelocator.exception.PointOfServiceDaoException;
import de.hybris.platform.storelocator.impl.DefaultGPS;
import de.hybris.platform.storelocator.impl.GeometryUtils;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

public class DefaultFMStoreFinderDAO implements FMStoreFinderDAO{
	
	private static final Logger LOG = Logger.getLogger(DefaultFMStoreFinderDAO.class);

	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private ConfigurationService configurationService;
	
	@Override	
	public List<PointOfServiceModel> getPOSLocationsForBrand(
			BaseStoreModel baseStoreModel, GeoPoint geoPoint,
			PageableData pageableData, Double maxRadius,String shopType, String brand,
			String partType, String subBrand) {	
		Double latMax = null;
		Double lonMax = null;
		Double latMin = null;
		Double lonMin = null;
		if(null!=maxRadius)
		{	
		GPS referenceGps = new DefaultGPS(geoPoint.getLatitude(), geoPoint.getLongitude());
		  
		  List<GPS> corners = GeometryUtils.getSquareOfTolerance(referenceGps, maxRadius);
	      if ((corners == null) || (corners.isEmpty()) || (corners.size() != 2)) {
	        throw new PointOfServiceDaoException("Could not fetch locations from database. Unexpected neighborhood");
	      }
	      latMax = Double.valueOf(((GPS)corners.get(1)).getDecimalLatitude());
	      lonMax = Double.valueOf(((GPS)corners.get(1)).getDecimalLongitude());
	      latMin = Double.valueOf(((GPS)corners.get(0)).getDecimalLatitude());
	      lonMin = Double.valueOf(((GPS)corners.get(0)).getDecimalLongitude());
	      
	      LOG.info("::" + latMin + "::" + latMax + "::"+ lonMin + "::"+ lonMax);
		} 
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<String,Object>();
		sb.append("SELECT {pos:PK} FROM {PointOfService AS pos} WHERE");
		if(StringUtils.isNotBlank(brand) && ! FmCoreConstants.ALL.equalsIgnoreCase(brand) && !"?".equals(brand))
		{	
		  sb.append(" {pos:PK} IN ");
		  sb.append("( SELECT d FROM ( {{ ");
		  sb.append("SELECT {pos2b:source} AS d FROM {PointOfService2BrandInformation AS pos2b} ");
		  sb.append("where {pos2b.target} IN ( {{ ");
		  sb.append("Select {PK} from {BrandInformation} where {brand}=?brand");
		  params.put("brand", brand);
		  if(StringUtils.isNotBlank(partType)&&(!FmCoreConstants.ALL.equalsIgnoreCase(partType))&&(!"?".equals(partType.trim())))
		  {	
		     params.put("partType", partType);
		     sb.append(" and {partType}=?partType");
		  }
		  if(StringUtils.isNotBlank(subBrand)&&(!"?".equals(subBrand.trim())))
		  {
			params.put("subBrand", subBrand);
			sb.append(" and {subBrand}=?subBrand");	
		  }	
		  sb.append(" }} )");
		  sb.append(" }} )");
		  sb.append(" ) AND");
		}
		if(baseStoreModel!=null){
			sb.append(" {").append("pos.baseStore").append("} = ?baseStore");
			params.put("baseStore", baseStoreModel);
			if(configurationService.getConfiguration().getProperty(FmCoreConstants.STORE_NAME_EMEA).equals(baseStoreModel.getUid()))
			{
				sb.append(" AND {pos.active}=?active");
				params.put("active", "1");
			}
		}
		List<String> shopTypeList = FMUtils.formShopTypeFilterString(shopType);
		if(StringUtils.isNotBlank(shopType))
		{	
		   params.put("shopType", shopTypeList);
		   sb.append(" and {pos.shopType} in (?shopType)");
		}
		if(null!=maxRadius)
		{	
		sb.append(" and {pos.latitude} is not null and {pos.longitude} is not null");
		sb.append(" AND {pos.latitude").append("} >= ?latMin AND {")
        .append("pos.latitude").append("} <= ?latMax AND {").append("pos.longitude").append(
        "} >= ?lonMin AND {").append("pos.longitude").append("} <= ?lonMax");
		params.put("latMax", latMax);
		params.put("latMin", latMin);
		params.put("lonMax", lonMax);
		params.put("lonMin", lonMin);
		}
		
		FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameters(params);
		SearchResult<PointOfServiceModel> result = flexibleSearchService.search(query);
		List<PointOfServiceModel> list = result.getResult();
		if(FmCoreConstants.ALL.equalsIgnoreCase(partType)&&list!=null&&list.size()>0)
		{
		  list = processStoresWhichContainsAllParts(brand,list);
		}  
		return list;
	}

	@Override
	public List<DistributorModel> getDistributorsByCountryAndBrand(
			CountryModel country, String brand) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(brand) && ! FmCoreConstants.ALL.equalsIgnoreCase(brand) && !"?".equals(brand))
		{	
		  sb.append("SELECT {dis:PK} FROM {Distributor AS dis} WHERE {dis:PK} IN");
		  sb.append("( SELECT d FROM ( {{");
		  sb.append(" SELECT {dis2b:source} AS d FROM {Distributor2BrandInformation AS dis2b} ");
		  sb.append("where {dis2b.target} IN ( {{ ");
		  sb.append("Select {PK} from {BrandInformation} where {brand}=?brand and {partType} is null and {subBrand} is null");
		  params.put("brand", brand);
		  sb.append(" }} )");
		  sb.append(" }} ))");
		  sb.append(" and {dis.country}=?country");  
		}else{
			sb.append("SELECT {dis:PK} FROM {Distributor AS dis} WHERE {dis.country}=?country");
		}
		params.put("country", country);	
		FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameters(params);
		SearchResult<DistributorModel> result = flexibleSearchService.search(query);
		List<DistributorModel> list = result.getResult();
		return list;
	}

	@Override
	public List<OnlineRetailerModel> getOnlineRetailersByCountryAndBrand(
			CountryModel country, String brand) {
		StringBuilder sb = new StringBuilder();
		Map<String,Object> params = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(brand) && ! FmCoreConstants.ALL.equalsIgnoreCase(brand) && !"?".equals(brand))
		{	
		  sb.append("SELECT {onr:PK} FROM {OnlineRetailer AS onr} WHERE {onr:PK} IN");
		  sb.append("( SELECT o FROM ( {{");
		  sb.append(" SELECT {onr2b:source} AS o FROM {OnlineRetailer2BrandInfo AS onr2b} ");
		  sb.append("where {onr2b.target} IN ( {{ ");
		  sb.append("Select {PK} from {BrandInformation} where {brand}=?brand and {partType} is null and {subBrand} is null");
		  params.put("brand", brand);
		  sb.append(" }} )");
		  sb.append("}} ))");
		  sb.append(" and {onr.country}=?country");  
		}else{
			sb.append("SELECT {onr:PK} FROM {OnlineRetailer AS onr} WHERE {onr.country}=?country");
		}
		sb.append(" and {onr.storeWebsite} is not null");
		params.put("country", country);	
		FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		query.addQueryParameters(params);
		SearchResult<OnlineRetailerModel> result = flexibleSearchService.search(query);
		List<OnlineRetailerModel> list = result.getResult();
		return list;
	}

	@Override
	public List<DealerLocatorMasterData> getDealerLocatorMasterData() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (({{ ");
		sb.append("select {c.isocode},{p.shopType},{b.brand} from ");
		sb.append("{pointofservice as p join address as a on {p.address}={a.PK} ");
		sb.append("join country as c on {a.country}={c.pk} join PointOfService2BrandInformation ");
		sb.append("as pos2b on {p.pk}={pos2b.source} join BrandInformation as b on {pos2b.target}={b.pk}} ");
		sb.append("group by {c.isocode},{p.shopType},{b.brand} ");
		sb.append("}} UNION {{ ");
		sb.append("select {c.isocode},'DISTRIBUTOR' as shopType,{b.brand} from ");
		sb.append("{Distributor as d join Country as c on {d.country}={c.pk} join ");
		sb.append("Distributor2BrandInformation as dis2b on {d.pk}={dis2b.source} join ");
		sb.append("BrandInformation as b on {dis2b.target}={b.pk}} group by {c.isocode},{b.brand} ");
		sb.append("}} UNION {{ ");
		sb.append("select {c.isocode},'ONLINERETAILER' as shopType,{b.brand} from ");
		sb.append("{OnlineRetailer as o join Country as c on {o.country}={c.pk} join ");
		sb.append("OnlineRetailer2BrandInfo as onr2b on {o.pk}={onr2b.source} join ");
		sb.append("BrandInformation as b on {onr2b.target}={b.pk}} group by {c.isocode},{b.brand} ");
		sb.append("}} UNION {{ ");
		sb.append("select {c.isocode},'DISTRIBUTOR' as shopType,{b.brand} from "); 
		sb.append("{Distributor as d join address as a on {d.address}={a.PK} ");
		sb.append("join country as c on {a.country}={c.pk} join Distributor2BrandInformation ");
		sb.append("as dis2b on {d.pk}={dis2b.source} join BrandInformation as b on {dis2b.target}={b.pk}} ");
		sb.append("group by {c.isocode},{b.brand} ");
		sb.append("}} UNION {{ ");
		sb.append("select {c.isocode},'ONLINERETAILER' as shopType,{b.brand} from ");
		sb.append("{OnlineRetailer as r join address as a on {r.address}={a.PK} ");
		sb.append("join country as c on {a.country}={c.pk} join OnlineRetailer2BrandInfo ");
		sb.append("as onr2b on {r.pk}={onr2b.source} join BrandInformation as b on {onr2b.target}={b.pk}} ");
		sb.append("group by {c.isocode},{b.brand} ");
		sb.append("}} ))");
		FlexibleSearchQuery query = new FlexibleSearchQuery(sb.toString());
		final List<Class> resultClassList = new ArrayList<Class>();
		resultClassList.add(String.class);
		resultClassList.add(String.class);
		resultClassList.add(String.class);
		query.setResultClassList(resultClassList);
		final SearchResult<List> searchResult = flexibleSearchService.search(query);
		final List outputList = searchResult.getResult();
		List<DealerLocatorMasterData> dealerLocatorMasterDataList = null;
		if (null != outputList && !outputList.isEmpty())
		{
			dealerLocatorMasterDataList = new ArrayList<DealerLocatorMasterData>();
 			for(Object row:outputList){
 				List rowList = (List) row;
 				DealerLocatorMasterData dealerLocatorMasterData = new DealerLocatorMasterData();
 				dealerLocatorMasterData.setCountryISO((String)rowList.get(0));
 				dealerLocatorMasterData.setShopType(((String)rowList.get(1)).toUpperCase());
 				dealerLocatorMasterData.setBrand((String)rowList.get(2));
 				dealerLocatorMasterDataList.add(dealerLocatorMasterData);
 			}
		}
		return dealerLocatorMasterDataList;
	}
	
	private List<PointOfServiceModel> processStoresWhichContainsAllParts(String brand,List<PointOfServiceModel> list){
		List<PointOfServiceModel> updatedList =new ArrayList<PointOfServiceModel>();
		FlexibleSearchQuery query=new FlexibleSearchQuery("select {pk} from {brandInformation} where {brand}=?brand and {partType} is not null");
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("brand", brand);
		query.addQueryParameters(params);
		SearchResult<BrandInformationModel> bresult = flexibleSearchService.search(query);
		List<BrandInformationModel> bresultList = bresult.getResult();	
		if(bresultList!=null&&bresultList.size()>0)
		{	
		 for(PointOfServiceModel pos:list)
		 {	
		   boolean isContainsAll=true;
		   for(BrandInformationModel binfo:bresultList)
		   {
		     if(!pos.getBrandInformation().contains(binfo)){
		    	  isContainsAll=false;
		    	  break;
		       }
		   }
		   if(isContainsAll==true)
		   {
			updatedList.add(pos);
		   }	
		  }
		}else{
			updatedList=list;
		}
		return updatedList;
	 }

}

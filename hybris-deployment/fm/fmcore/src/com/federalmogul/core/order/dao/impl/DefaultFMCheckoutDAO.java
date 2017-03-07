/**
 * 
 */
package com.federalmogul.core.order.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.federalmogul.core.order.dao.FMCheckoutDAO;
import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDCShippingModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.federalmogul.falcon.core.model.FMShipperOrderTrackingModel;


/**
 * @author mamud
 * 
 */
public class DefaultFMCheckoutDAO extends DefaultGenericDao<FMDistrubtionCenterModel> implements FMCheckoutDAO
{

	/**
	 * @param typecode
	 */
	public DefaultFMCheckoutDAO(final String typecode)
	{
		super(typecode);
		// YTODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.order.dao.FMCheckoutDAO#getDCData()
	 */
	@Override
	public List<FMDistrubtionCenterModel> getDCData()
	{
		// YTODO Auto-generated method stub
		return find();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.order.dao.FMCheckoutDAO#getDCData(java.lang.String)
	 */
	@Override
	public List<FMDistrubtionCenterModel> getDCData(final String code)
	{

		// YTODO Auto-generated method stub
		validateParameterNotNull(code, "dc Code must not be null!");
		final Map parameters = new HashMap();
		parameters.put(FMDistrubtionCenterModel.CODE, code);
		return find(parameters);
	}


	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getYMMFitmentModelData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public FMDistrubtionCenterModel getDistrubtionCenterData(final String code)
	{
		// YTODO Auto-generated method stub
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {dc:" + FMDistrubtionCenterModel.PK + "} ");
		query.append("FROM  {" + FMDistrubtionCenterModel._TYPECODE + " AS dc } ");
		query.append("WHERE {dc." + FMDistrubtionCenterModel.CODE + "} = ?" + FMDistrubtionCenterModel.CODE);
		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put(FMDistrubtionCenterModel.CODE, code);
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString(), params);
		final SearchResult<FMDistrubtionCenterModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);
		FMDistrubtionCenterModel fmDistrubtionCenterModel = null;
		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			fmDistrubtionCenterModel = searchResult.getResult().get(0);
		}

		return fmDistrubtionCenterModel;


	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.search.dao.FMFitmentSearchDAO#getYMMFitmentModelData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public FMDCCenterModel getCutOffTimeForDC(final String code)
	{
		// YTODO Auto-generated method stub
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {dc:" + FMDCCenterModel.PK + "} ");
		query.append("FROM  {" + FMDCCenterModel._TYPECODE + " AS dc} ");
		query.append("WHERE {dc." + FMDCCenterModel.DCCODE + "} = ?" + FMDCCenterModel.DCCODE);
		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put(FMDCCenterModel.DCCODE, code);
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString(), params);
		final SearchResult<FMDCCenterModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);
		FMDCCenterModel fmDCCenterModel = null;
		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			fmDCCenterModel = searchResult.getResult().get(0);

		}

		return fmDCCenterModel;


	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.order.dao.FMCheckoutDAO#getCarrierName(java.lang.String)
	 */
	@Override
	public List<String> getCarrierName(final String code)
	{
		// YTODO Auto-generated method stub

		final List<String> dcCarrierList = new ArrayList<String>();

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {sm:" + FMDCShippingModel.CARRIER_NAME + "} ");
		query.append(", {sm:" + FMDCShippingModel.CARRIER_CD + "} ");
		//query.append(", {sm:" + FMDCShippingModel.SORT_ORDER + "} ");
		query.append("FROM  {" + FMDCShippingModel._TYPECODE + " AS sm} ");
		query.append("WHERE {sm." + FMDCShippingModel.DIST_CNTR_CD + "} = ?" + FMDCShippingModel.DIST_CNTR_CD);
		/* Mahaveer - code For UPS change - Active flag added */
		query.append(" AND {sm." + FMDCShippingModel.ACTIVE_FLG + "} = ?" + FMDCShippingModel.ACTIVE_FLG);

		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put(FMDCShippingModel.DIST_CNTR_CD, code);
		/* Mahaveer - code For UPS change - Active flag added */
		params.put(FMDCShippingModel.ACTIVE_FLG, "Y");

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString(), params);

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		resultClassList.add(String.class);
		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<List> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		final List outputList = searchResult.getResult();

		if (null != outputList && !outputList.isEmpty())
		{
			final Iterator rowIterator = outputList.iterator();
			while (rowIterator.hasNext())
			{
				final List row = (List) rowIterator.next();
				if (null != row && !row.isEmpty())
				{
					dcCarrierList.add((String) row.get(0) + "-" + (String) row.get(1));
				}
			}
		}
		return dcCarrierList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.order.dao.FMCheckoutDAO#getShippingMethodName(java.lang.String)
	 */
	@Override
	public List<String> getShippingMethodName(final String code, final String dcCode, final String shipToCountry)
	{
		// YTODO Auto-generated method stub
		final List<String> dcShipppingMethod = new ArrayList<String>();

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {sm:" + FMDCShippingModel.SORT_ORDER + "} ");
		query.append(", {sm:" + FMDCShippingModel.SHIP_MTHD_CD + "} ");
		query.append(", {sm:" + FMDCShippingModel.SHIP_MTHD_DESC + "} ");
		query.append("FROM  {" + FMDCShippingModel._TYPECODE + " AS sm} ");
		query.append("WHERE {sm." + FMDCShippingModel.CARRIER_CD + "} = ?" + FMDCShippingModel.CARRIER_CD);
		query.append(" AND {sm." + FMDCShippingModel.DIST_CNTR_CD + "} = ?" + FMDCShippingModel.DIST_CNTR_CD);
		query.append(" AND {sm." + FMDCShippingModel.TO_ISO_CNTRY_CD + "}= ?" + FMDCShippingModel.TO_ISO_CNTRY_CD);
		/* Mahaveer - code For UPS change - Active flag added */
		query.append(" AND {sm." + FMDCShippingModel.ACTIVE_FLG + "} = ?" + FMDCShippingModel.ACTIVE_FLG);
		query.append(" ORDER BY {sm:" + FMDCShippingModel.SORT_ORDER + "}");

		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put(FMDCShippingModel.CARRIER_CD, code);
		params.put(FMDCShippingModel.DIST_CNTR_CD, dcCode);
		params.put(FMDCShippingModel.TO_ISO_CNTRY_CD, shipToCountry.toUpperCase());
		/* Mahaveer - code For UPS change - Active flag added */
		params.put(FMDCShippingModel.ACTIVE_FLG, "Y");

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString(), params);

		final List resultClassList = new ArrayList();
		resultClassList.add(String.class);
		resultClassList.add(String.class);
		resultClassList.add(String.class);
		flexibleSearchQuery.setResultClassList(resultClassList);

		final SearchResult<List> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		final List outputList = searchResult.getResult();

		if (null != outputList && !outputList.isEmpty())
		{
			final Iterator rowIterator = outputList.iterator();
			while (rowIterator.hasNext())
			{
				final List row = (List) rowIterator.next();
				if (null != row && !row.isEmpty())
				{
					dcShipppingMethod.add((String) row.get(2) + "-" + (String) row.get(1));
				}
			}
		}
		return dcShipppingMethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.order.dao.FMCheckoutDAO#getShippingMethod(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public FMDCShippingModel getShippingMethod(final String code, final String carrier, final String shipmethod,
			final String shipToCountry)
	{
		// YTODO Auto-generated method stub

		FMDCShippingModel shippingmethod = null;

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {sm:" + FMDCShippingModel.PK + "} ");
		query.append("FROM  {" + FMDCShippingModel._TYPECODE + " AS sm} ");
		query.append("WHERE {sm." + FMDCShippingModel.DIST_CNTR_CD + "} = ?" + FMDCShippingModel.DIST_CNTR_CD);
		query.append(" AND {sm." + FMDCShippingModel.CARRIER_CD + "}=?" + FMDCShippingModel.CARRIER_CD);
		query.append(" AND {sm." + FMDCShippingModel.SHIP_MTHD_CD + "}=?" + FMDCShippingModel.SHIP_MTHD_CD);
		query.append(" AND {sm." + FMDCShippingModel.TO_ISO_CNTRY_CD + "}=?" + FMDCShippingModel.TO_ISO_CNTRY_CD);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.DIST_CNTR_CD, code);
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.CARRIER_CD, carrier);
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.SHIP_MTHD_CD, shipmethod);
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.TO_ISO_CNTRY_CD, shipToCountry.toUpperCase());

		final SearchResult<FMDCShippingModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			shippingmethod = searchResult.getResult().get(0);
		}

		return shippingmethod;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.order.dao.FMCheckoutDAO#getShipperOrderDetails(java.lang.String)
	 */
	@Override
	public FMShipperOrderTrackingModel getShipperOrderDetails(final String shipperCode)
	{
		// YTODO Auto-generated method stub

		FMShipperOrderTrackingModel shipperDetail = null;

		final StringBuilder query = new StringBuilder("SELECT DISTINCT {sot:" + FMShipperOrderTrackingModel.PK + "} ");
		query.append("FROM  {" + FMShipperOrderTrackingModel._TYPECODE + " AS sot} ");
		query.append("WHERE {sot." + FMShipperOrderTrackingModel.SHIPPERCODE + "} = ?" + FMShipperOrderTrackingModel.SHIPPERCODE);
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMShipperOrderTrackingModel.SHIPPERCODE, shipperCode);

		final SearchResult<FMShipperOrderTrackingModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			shipperDetail = searchResult.getResult().get(0);
			return shipperDetail;
		}
		else
		{

			return null;
		}


	}

}

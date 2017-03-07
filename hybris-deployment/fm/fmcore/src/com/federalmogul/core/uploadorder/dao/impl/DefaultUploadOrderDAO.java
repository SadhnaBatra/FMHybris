/**
 * 
 */
package com.federalmogul.core.uploadorder.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.federalmogul.core.enums.UploadOrderStatus;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.uploadorder.dao.UploadOrderDAO;
import com.federalmogul.falcon.core.model.UploadOrderModel;


/**
 * @author mamud
 * 
 */
/**
 * Default implementation of the {@link UploadOrderDAO}.
 */
public class DefaultUploadOrderDAO extends DefaultGenericDao<UploadOrderModel> implements UploadOrderDAO
{
	private static final Logger LOG = Logger.getLogger(DefaultUploadOrderDAO.class);

	public static final String DATE_FORMAT = "dd-MMM-yyyy";

	public static final String MM_DD_YYYY = "MM/dd/yyyy";

	/**
	 * @param typecode
	 */
	public DefaultUploadOrderDAO(final String typecode)
	{
		super(typecode);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#createUploadorder()
	 */
	@Override
	public void createUploadorder()
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#updateUploadorder()
	 */
	@Override
	public void updateUploadorder()
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#deleteUploadorder()
	 */
	@Override
	public void deleteUploadorder()
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#createUploadorderEntry()
	 */
	@Override
	public void createUploadorderEntry()
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#updateUploadorderEntry()
	 */
	@Override
	public void updateUploadorderEntry()
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#deleteUploadorderEntry()
	 */
	@Override
	public void deleteUploadorderEntry()
	{
		// YTODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#viewUploadorder(java.lang.String)
	 */
	@Override
	public List<UploadOrderModel> viewUploadorder(final String uid)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(uid, "Part code must not be null!");
		final StringBuilder query = new StringBuilder("SELECT {" + UploadOrderModel.PK + "}");
		query.append(" FROM {" + UploadOrderModel._TYPECODE + " AS uo");
		query.append(" JOIN " + FMCustomerModel._TYPECODE + " AS cust");
		query.append(" ON {uo." + UploadOrderModel.USER + "} = {cust." + FMCustomerModel.PK + "} }");
		query.append(" WHERE {cust." + FMCustomerModel.UID + "} = ?" + FMCustomerModel.UID);
		query.append(" ORDER BY {" + UploadOrderModel.CREATIONTIME + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMCustomerModel.UID, uid);

		LOG.info("query :: " + query.toString());

		final SearchResult<UploadOrderModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{
			final List<UploadOrderModel> uploadOrderList = new ArrayList<UploadOrderModel>();
			for (final UploadOrderModel order : result.getResult())
			{
				if (!order.getUploadOrderStatus().equals(UploadOrderStatus.CANCELLED)
						&& !order.getUploadOrderStatus().equals(UploadOrderStatus.SUBMITTED))
				{
					uploadOrderList.add(order);
				}
			}
			return uploadOrderList;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}

	}

	@Override
	public UploadOrderModel viewUploadOrderEntry(final String code)
	{
		validateParameterNotNull(code, "Part code must not be null!");
		return find(Collections.singletonMap(UploadOrderModel.CODE, (Object) code)).get(0);
	}

	@Override
	public UploadOrderModel viewUploadOrderHistory(final String code)
	{
		validateParameterNotNull(code, "Part code must not be null!");
		return find(Collections.singletonMap(UploadOrderModel.CODE, (Object) code)).get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#searchUploadorder(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UploadOrderModel> searchUploadorder(final String uid, final String ponumber, final String sapordernumber,
			final String sdate, final String edate, final String status)
	{
		// YTODO Auto-generated method stub

		// YTODO Auto-generated method stub
		validateParameterNotNull(uid, "Part code must not be null!");
		final StringBuilder query = new StringBuilder("SELECT {" + UploadOrderModel.PK + "}");
		query.append(" FROM {" + UploadOrderModel._TYPECODE + " AS uo");
		query.append(" JOIN " + FMCustomerModel._TYPECODE + " AS cust");
		query.append(" ON {uo." + UploadOrderModel.USER + "} = {cust." + FMCustomerModel.PK + "} }");
		//query.append(" JOIN " + UploadOrderStatus._TYPECODE + " AS status");
		//query.append(" ON {uo." + UploadOrderModel.UPLOADORDERSTATUS + "} = {status." + UploadOrderStatus.+ "} }");
		query.append(" WHERE {cust." + FMCustomerModel.UID + "} = ?" + FMCustomerModel.UID);

		if (ponumber != null && ponumber != "")
		{
			query.append(" AND {uo." + UploadOrderModel.PONUMBER + "} = ?" + UploadOrderModel.PONUMBER);
		}

		if (sapordernumber != null && sapordernumber != "")
		{
			query.append(" AND {uo." + UploadOrderModel.CODE + "} = ?" + UploadOrderModel.CODE);
		}

		if (sdate != null && sdate != "")
		{
			query.append(" AND {uo." + UploadOrderModel.CREATIONTIME + "} >= ?startDate");
		}

		if (edate != null && edate != "")
		{
			query.append(" AND  {uo." + UploadOrderModel.CREATIONTIME + "} <= ?endDate");
		}
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMCustomerModel.UID, uid);

		if (ponumber != null && ponumber != "")
		{
			flexibleSearchQuery.addQueryParameter(UploadOrderModel.PONUMBER, ponumber);
		}

		if (sapordernumber != null && sapordernumber != "")
		{
			flexibleSearchQuery.addQueryParameter(UploadOrderModel.CODE, sapordernumber);
		}

		if (sdate != null && sdate != "")
		{
			flexibleSearchQuery.addQueryParameter("startDate", parseToDateFromString(sdate));
		}

		if (edate != null && edate != "")
		{
			flexibleSearchQuery.addQueryParameter("endDate", parseToDateFromString(edate));
		}

		query.append(" ORDER BY {" + UploadOrderModel.CREATIONTIME + "} ");
		LOG.info("query :: " + query.toString());

		final SearchResult<UploadOrderModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{
			//final List<UploadOrderModel> uploadOrderList = result.getResult();

			List<UploadOrderModel> uploadOrderList = new ArrayList<UploadOrderModel>();
			if (status == null || "All".equalsIgnoreCase(status))
			{
				uploadOrderList = result.getResult();
			}
			else
			{
				for (final UploadOrderModel order : result.getResult())
				{

					if ("Requiring Attention".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.PARTRESOLUTIONERROR))
						{
							uploadOrderList.add(order);
						}
					}
					else if ("Submitted".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.SUBMITTED))
						{
							uploadOrderList.add(order);
						}
					}
					else if ("Hold".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.FILEPARSEERROR))
						{
							uploadOrderList.add(order);
						}
					}
				}
			}


			return uploadOrderList;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}
	}

	public Date parseToDateFromString(final String fromDate)
	{
		final SimpleDateFormat simpleDate = new SimpleDateFormat(MM_DD_YYYY);
		Date date = null;
		if (fromDate != null && fromDate != "")
		{
			try
			{
				date = simpleDate.parse(fromDate);
			}
			catch (final ParseException e1)
			{

				LOG.error(e1.getMessage());
				return null;
			}

		}
		LOG.info("Cponverted Date :: " + date);
		return date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#viewCSRUploadorder(java.lang.String)
	 */
	@Override
	public List<UploadOrderModel> viewCSRUploadorder(final String accCode)
	{
		// YTODO Auto-generated method stub
		// YTODO Auto-generated method stub
		validateParameterNotNull(accCode, "Part code must not be null!");
		final StringBuilder query = new StringBuilder("SELECT {" + UploadOrderModel.PK + "}");
		query.append(" FROM {" + UploadOrderModel._TYPECODE + " AS uo");
		query.append(" JOIN " + FMCustomerAccountModel._TYPECODE + " AS acc");
		query.append(" ON {uo." + UploadOrderModel.SHIPTOACCOUNT + "} = {acc." + FMCustomerAccountModel.PK + "} }");
		query.append(" WHERE {acc." + FMCustomerAccountModel.UID + "} = ?" + FMCustomerAccountModel.UID);
		query.append(" ORDER BY {" + UploadOrderModel.CREATIONTIME + "} ");

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMCustomerAccountModel.UID, accCode);

		LOG.info("query :: " + query.toString());

		final SearchResult<UploadOrderModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{
			final List<UploadOrderModel> uploadOrderList = new ArrayList<UploadOrderModel>();
			for (final UploadOrderModel order : result.getResult())
			{
				if (!order.getUploadOrderStatus().equals(UploadOrderStatus.CANCELLED)
						&& !order.getUploadOrderStatus().equals(UploadOrderStatus.SUBMITTED))
				{
					uploadOrderList.add(order);
				}
			}
			return uploadOrderList;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#searchCSRUploadorder(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<UploadOrderModel> searchCSRUploadorder(final String accCode, final String ponumber, final String sapordernumber,
			final String sdate, final String edate, final String status)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(accCode, "Part code must not be null!");
		final StringBuilder query = new StringBuilder("SELECT {" + UploadOrderModel.PK + "}");
		query.append(" FROM {" + UploadOrderModel._TYPECODE + " AS uo");
		query.append(" JOIN " + FMCustomerAccountModel._TYPECODE + " AS acc");
		query.append(" ON {uo." + UploadOrderModel.SHIPTOACCOUNT + "} = {acc." + FMCustomerAccountModel.PK + "} }");
		//query.append(" JOIN " + UploadOrderStatus._TYPECODE + " AS status");
		//query.append(" ON {uo." + UploadOrderModel.UPLOADORDERSTATUS + "} = {status." + UploadOrderStatus.+ "} }");
		query.append(" WHERE {acc." + FMCustomerAccountModel.UID + "} = ?" + FMCustomerAccountModel.UID);

		if (ponumber != null && ponumber != "")
		{
			query.append(" AND {uo." + UploadOrderModel.PONUMBER + "} = ?" + UploadOrderModel.PONUMBER);
		}

		if (sapordernumber != null && sapordernumber != "")
		{
			query.append(" AND {uo." + UploadOrderModel.CODE + "} = ?" + UploadOrderModel.CODE);
		}

		if (sdate != null && sdate != "")
		{
			query.append(" AND {uo." + UploadOrderModel.CREATIONTIME + "} >= ?startDate");
		}

		if (edate != null && edate != "")
		{
			query.append(" AND {uo." + UploadOrderModel.CREATIONTIME + "} <= ?endDate");
		}
		query.append(" ORDER BY {" + UploadOrderModel.CREATIONTIME + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMCustomerAccountModel.UID, accCode);

		if (ponumber != null && ponumber != "")
		{
			flexibleSearchQuery.addQueryParameter(UploadOrderModel.PONUMBER, ponumber);
		}

		if (sapordernumber != null && sapordernumber != "")
		{
			flexibleSearchQuery.addQueryParameter(UploadOrderModel.CODE, sapordernumber);
		}

		if (sdate != null && sdate != "")
		{
			flexibleSearchQuery.addQueryParameter("startDate", parseToDateFromString(sdate));
		}

		if (edate != null && edate != "")
		{
			flexibleSearchQuery.addQueryParameter("endDate", parseToDateFromString(edate));
		}


		LOG.info("query :: " + query.toString());

		final SearchResult<UploadOrderModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{
			List<UploadOrderModel> uploadOrderList = new ArrayList<UploadOrderModel>();
			if (status == null || "All".equalsIgnoreCase(status))
			{
				uploadOrderList = result.getResult();
			}
			else
			{
				for (final UploadOrderModel order : result.getResult())
				{
					if ("Requiring Attention".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.PARTRESOLUTIONERROR))
						{
							uploadOrderList.add(order);
						}
					}
					else if ("Submitted".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.SUBMITTED))
						{
							uploadOrderList.add(order);
						}
					}
					else if ("Hold".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.FILEPARSEERROR))
						{
							uploadOrderList.add(order);
						}
					}
				}
			}
			return uploadOrderList;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.uploadorder.dao.UploadOrderDAO#viewCSRUploadorder()
	 */
	@Override
	public List<UploadOrderModel> viewCSRUploadorder()
	{
		// YTODO Auto-generated method stub
		//validateParameterNotNull(uid, "Part code must not be null!");
		LOG.info("viewCSRUploadorder{} :: Inside ");
		final StringBuilder query = new StringBuilder("SELECT {" + UploadOrderModel.PK + "}");
		query.append(" FROM {" + UploadOrderModel._TYPECODE + " AS uo }");
		query.append(" ORDER BY {" + UploadOrderModel.CREATIONTIME + "} ");
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());

		LOG.info("query :: " + query.toString());

		final SearchResult<UploadOrderModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{
			final List<UploadOrderModel> uploadOrderList = new ArrayList<UploadOrderModel>();
			for (final UploadOrderModel order : result.getResult())
			{
				if (!order.getUploadOrderStatus().equals(UploadOrderStatus.CANCELLED)
						&& !order.getUploadOrderStatus().equals(UploadOrderStatus.SUBMITTED))
				{
					uploadOrderList.add(order);
				}
			}
			return uploadOrderList;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}
	}

	/**
	 * @param ponumber
	 * @param sapordernumber
	 * @param sdate
	 * @param edate
	 * @param status
	 * @return
	 */
	@Override
	public List<UploadOrderModel> viewGlobalCSRUploadorder(final String ponumber, final String sapordernumber, final String sdate,
			final String edate, final String status)
	{
		LOG.info("viewGlobalCSRUploadorder{} :: Inside ");
		final StringBuilder query = new StringBuilder("SELECT {" + UploadOrderModel.PK + "}");
		query.append(" FROM {" + UploadOrderModel._TYPECODE + " AS uo } ");
		boolean firstparam = false;
		if (ponumber != null && ponumber != "")
		{
			query.append(" WHERE {uo." + UploadOrderModel.PONUMBER + "} = ?" + UploadOrderModel.PONUMBER);
			firstparam = true;
		}
		if (sapordernumber != null && sapordernumber != "")
		{
			if (!firstparam)
			{
				query.append(" WHERE {uo." + UploadOrderModel.CODE + "} = ?" + UploadOrderModel.CODE);
				firstparam = true;
			}
			else
			{
				query.append(" AND {uo." + UploadOrderModel.CODE + "} = ?" + UploadOrderModel.CODE);
			}
		}
		if (sdate != null && sdate != "")
		{
			if (!firstparam)
			{
				query.append(" WHERE {uo." + UploadOrderModel.CREATIONTIME + "} >= ?startDate");
				firstparam = true;
			}
			else
			{
				query.append(" AND {uo." + UploadOrderModel.CREATIONTIME + "} >= ?startDate");
			}

		}
		if (edate != null && edate != "")
		{
			if (!firstparam)
			{
				query.append(" WHERE {uo." + UploadOrderModel.CREATIONTIME + "} <= ?endDate");
				firstparam = true;
			}
			else
			{
				query.append(" AND {uo." + UploadOrderModel.CREATIONTIME + "} <= ?endDate");
			}
		}
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());

		if (ponumber != null && ponumber != "")
		{
			flexibleSearchQuery.addQueryParameter(UploadOrderModel.PONUMBER, ponumber);
		}

		if (sapordernumber != null && sapordernumber != "")
		{
			flexibleSearchQuery.addQueryParameter(UploadOrderModel.CODE, sapordernumber);
		}

		if (sdate != null && sdate != "")
		{
			flexibleSearchQuery.addQueryParameter("startDate", parseToDateFromString(sdate));
		}

		if (edate != null && edate != "")
		{
			flexibleSearchQuery.addQueryParameter("endDate", parseToDateFromString(edate));
		}

		query.append(" ORDER BY {" + UploadOrderModel.CREATIONTIME + "} ");

		LOG.info("query :: " + query.toString());

		final SearchResult<UploadOrderModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{
			List<UploadOrderModel> uploadOrderList = new ArrayList<UploadOrderModel>();
			if (status == null || "All".equalsIgnoreCase(status))
			{
				uploadOrderList = result.getResult();
			}
			else
			{
				for (final UploadOrderModel order : result.getResult())
				{
					if ("Requiring Attention".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.PARTRESOLUTIONERROR))
						{
							uploadOrderList.add(order);
						}
					}
					else if ("Submitted".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.SUBMITTED))
						{
							uploadOrderList.add(order);
						}
					}
					else if ("Hold".equalsIgnoreCase(status))
					{
						if (order.getUploadOrderStatus().equals(UploadOrderStatus.FILEPARSEERROR))
						{
							uploadOrderList.add(order);
						}
					}
				}
			}
			return uploadOrderList;
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}
	}
}

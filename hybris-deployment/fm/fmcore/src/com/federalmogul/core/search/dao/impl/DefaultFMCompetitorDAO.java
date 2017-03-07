/**
 * 
 */
package com.federalmogul.core.search.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.search.dao.FMCompetitorDAO;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.falcon.core.model.PartInterchangeModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;
import com.fmo.wom.model.FMIPOOrderModel;
import com.federalmogul.falcon.core.model.FMReportLogModel;


/**
 * @author SU276498
 * 
 */
public class DefaultFMCompetitorDAO extends DefaultGenericDao<PartInterchangeModel> implements FMCompetitorDAO
{
	private static final Logger LOG = Logger.getLogger(DefaultFMCompetitorDAO.class);

	/*
	 * @Autowired private ModelService modelService;
	 */@Autowired
	private FlexibleSearchService flexibleSearchService;

	/**
	 * @param typecode
	 */
	public DefaultFMCompetitorDAO(final String typecode)
	{
		super(typecode);
		// YTODO Auto-generated constructor stub
	}

	public static final String MM_DD_YYYY = "MM/dd/yyyy";

	@Override
	public List<PartInterchangeModel> getExternalPartInfo(final String externalPart)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(externalPart, "externalPart must not be null!");


		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(PartInterchangeModel.PK).append("} ");
		builder.append("FROM {").append(PartInterchangeModel._TYPECODE).append("} ");
		builder.append("WHERE {").append(PartInterchangeModel.RAWPARTNUMBER + "}=?" + PartInterchangeModel.RAWPARTNUMBER);
		builder.append(" OR {" + PartInterchangeModel.PARTNUMBER + "}=?" + PartInterchangeModel.PARTNUMBER);

		LOG.debug("query to get Part InterchangeTo information : " + builder.toString());

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(PartInterchangeModel.RAWPARTNUMBER, externalPart.toUpperCase());
		params.put(PartInterchangeModel.PARTNUMBER, externalPart.toUpperCase());
		final SearchResult<PartInterchangeModel> finalResults = flexibleSearchService.search(builder.toString(), params);

		return finalResults.getResult();


	}

	@Override
	public List<FMPartModel> getPartNumberInfo(final String partNumber)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(partNumber, "partNumber must not be null!");


		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(FMPartModel.PK).append("} ");
		builder.append("FROM {").append(FMPartModel._TYPECODE).append("} ");
		builder.append("WHERE {").append(FMPartModel.RAWPARTNUMBER + "}=?" + FMPartModel.RAWPARTNUMBER);
		builder.append(" OR {" + FMPartModel.PARTNUMBER + "}=?" + FMPartModel.PARTNUMBER);

		LOG.debug("query to get Part InterchangeTo information : " + builder.toString());
		String partNo = partNumber.replaceAll("\\W","");

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put(FMPartModel.RAWPARTNUMBER, partNo.toUpperCase());
		params.put(FMPartModel.PARTNUMBER, partNo.toUpperCase());
		final SearchResult<FMPartModel> finalResults = flexibleSearchService.search(builder.toString(), params);

		return finalResults.getResult();


	}

	@Override
       public List<String> getAppilcationUsageReports(final String accountNumber, final String userId, final String sDate,
                     final String eDate)
       {



              final StringBuilder query = new StringBuilder();

              query.append("SELECT UNIQUEID, DESCRIPTION, ");
              query.append("SUM(CASE WHEN FMORDERTYPE = 'Emergency' THEN CNT ELSE 0 END) AS Emergency, ");
              query.append("SUM(CASE WHEN FMORDERTYPE = 'Regular' THEN CNT ELSE 0 END) AS Regular, ");
              query.append("SUM(CASE WHEN FMORDERTYPE = 'Stock' THEN CNT ELSE 0 END) AS Stock, ");
              query.append("SUM(CASE WHEN FMORDERTYPE = 'pickup' THEN CNT ELSE 0 END) AS Pickup, ");
              query.append("SUM(CASE WHEN FMORDERTYPE = 'IPO-ORDER' THEN CNT ELSE 0 END) AS IPO_Order, ");
              query.append("SUM(CASE WHEN FMORDERTYPE = 'UPLOAD-ORDER' THEN CNT ELSE 0 END) AS UPLOAD_ORDER, ");
              query.append("SUM(CASE WHEN TYPE = 'Inventory' THEN INV ELSE 0 END) AS INVENTORY, ");
              query.append("SUM(CASE WHEN TYPE = 'orderStatus' THEN INV ELSE 0 END) AS ORDERSTATUS, ");
              query.append("SUM(CASE WHEN TYPE = 'backOrder' THEN INV ELSE 0 END) AS BACKORDER, ");
              query.append("SUM(CASE WHEN TYPE = 'Invoice' THEN INV ELSE 0 END) AS INVOICE,  ");
              query.append("SUM(CASE WHEN TYPE = 'PartInterChange' THEN INV ELSE 0 END) AS PARTINTERCHANGE  ");
              query.append("FROM (SELECT A.UNIQUEID,DESCRIPTION,FMORDERTYPE,COUNT(PURCHASEORDERNUMBER) AS CNT,TYPE,COUNT(RC) AS INV ");
              query.append("FROM ({{SELECT {B." + UserGroupModel.UID + " },{B." + UserGroupModel.DESCRIPTION + "},{A."
                           + OrderModel.FMORDERTYPE + "} AS FMORDERTYPE, P_PURCHASEORDERNUMBER AS PURCHASEORDERNUMBER,'Inventor' AS TYPE,'0' AS RC ");
              query.append("FROM {" + UserGroupModel._TYPECODE + " AS B ");
              query.append("LEFT JOIN " + OrderModel._TYPECODE + " AS A ");
              query.append("ON {A." + OrderModel.UNIT + "} = {B." + UserGroupModel.PK + "}} ");
              if (sDate != null && sDate != "")
              {
                     query.append("WHERE {A." + OrderModel.DATE + "} >= ?startDate_1 ");
              }
              if (eDate != null && eDate != "")
              {
                     query.append(" AND {A." + OrderModel.DATE + "} <= ?endDate_1 ");
              }
             
		query.append("AND {B." + UserGroupModel.UID + " } NOT LIKE '004%' ");
		query.append("AND {B." + UserGroupModel.UID + " } NOT LIKE '003%' ");
		query.append("AND {B." + UserGroupModel.UID + " } NOT LIKE '164%' ");

              if (userId != null && userId != "" && !userId.isEmpty())
              {
                     query.append("AND USERPK=({{SELECT PK FROM {" + UserModel._TYPECODE + "} WHERE UNIQUEID='" + userId + "' }}) ");
              }


              if (accountNumber != null && accountNumber != "" && !accountNumber.isEmpty())
              {
                     query.append("AND {A." + OrderModel.UNIT + "} = ({{SELECT PK FROM {" + UserGroupModel._TYPECODE + "} WHERE UNIQUEID = '"
                                  + accountNumber + "' }})");
              }

              query.append("}}");

              query.append(" UNION ALL {{ ");
              query.append("SELECT {D." + UserGroupModel.UID + "},{D." + UserGroupModel.DESCRIPTION
                           + "},'UPLOAD-ORDER' AS FMORDERTYPE,{E." + UploadOrderModel.PONUMBER + "} AS PURCHASEORDERNUMBER,'Inventor' AS TYPE,'0' AS RC ");
              query.append("FROM {" + UserGroupModel._TYPECODE + " AS D ");
              query.append("LEFT JOIN " + UploadOrderModel._TYPECODE + " AS E ");
              query.append("ON {E." + UploadOrderModel.SHIPTOACCOUNT + "} = {D." + UserGroupModel.PK + "}} ");
              if (sDate != null && sDate != "")
              {
                     //query.append("WHERE {E." + UploadOrderModel.UPDATEDTIME + "} BETWEEN '" + startDate + "' AND '?endDate' ");
                     query.append("WHERE {E." + UploadOrderModel.UPDATEDTIME + "} >= ?startDate_2 ");
              }
              if (eDate != null && eDate != "")
              {
                     query.append(" AND {E." + UploadOrderModel.UPDATEDTIME + "} <= ?endDate_2 ");
              }
              
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '004%' ");
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '003%' ");
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '164%' ");

              if (userId != null && userId != "" && !userId.isEmpty())
              {
                     query.append("AND P_USER = ({{SELECT PK FROM {" + UserModel._TYPECODE + "} WHERE UNIQUEID='" + userId + "' }}) ");
              }
              if (accountNumber != null && accountNumber != "" && !accountNumber.isEmpty())
              {
                     query.append("AND {E." + UploadOrderModel.SHIPTOACCOUNT + "} = ({{SELECT PK FROM {" + UserGroupModel._TYPECODE
                                  + "} WHERE UNIQUEID = '" + accountNumber + "' }})");
              }

              query.append("}} UNION ALL {{ ");
              query.append("SELECT {D." + UserGroupModel.UID + "},{D." + UserGroupModel.DESCRIPTION + "},'IPO-ORDER' AS FMORDERTYPE,{E."
                           + FMIPOOrderModel.CODE + "} AS PURCHASEORDERNUMBER,'Inventor' AS TYPE,'0' AS RC ");
              query.append("FROM {" + UserGroupModel._TYPECODE + " AS D ");
              query.append("LEFT JOIN " + FMIPOOrderModel._TYPECODE + " AS E ");
              query.append("ON {E." + FMIPOOrderModel.BILLTOPARTY + "} = {D." + UserGroupModel.PK + "}} ");
              if (sDate != null && sDate != "")
              {                     
                     query.append("WHERE {E." + FMIPOOrderModel.CREATIONTIME + "} >= ?startDate_3 ");
              }
              if (eDate != null && eDate != "")
              {
                     query.append(" AND {E." + FMIPOOrderModel.CREATIONTIME + "} <= ?endDate_3 ");
              }
 
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '004%' ");
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '003%' ");
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '164%' ");
		
		
		
		query.append("}}");

        query.append(" UNION ALL {{ ");
        query.append("SELECT {D." + UserGroupModel.UID + "},{D." + UserGroupModel.DESCRIPTION
                     + "},'SAMPLE' AS FMORDERTYPE,'0' AS PURCHASEORDERNUMBER,'Inventory' AS TYPE,{E." + FMReportLogModel.CHECKCOUNT+ "} AS RC  ");
        query.append("FROM {" + UserGroupModel._TYPECODE + " AS D ");
        query.append("LEFT JOIN " + FMReportLogModel._TYPECODE + " AS E ");
        query.append("ON {E." + FMReportLogModel.CUSTACCOUNT + "} = {D." + UserGroupModel.PK + "}} ");
        query.append(" WHERE {E." + FMReportLogModel.TYPE + "}='Inventory'");
       
        if (sDate != null && sDate != "")
        {
               //query.append("WHERE {E." + UploadOrderModel.UPDATEDTIME + "} BETWEEN '" + startDate + "' AND '?endDate' ");
               query.append(" AND {E." + FMReportLogModel.STARTDATE + "} >= ?startDate_2 ");
        }
        if (eDate != null && eDate != "")
        {
               query.append(" AND {E." + FMReportLogModel.ENDDATE + "} <= ?endDate_2 ");
        }
        
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '004%' ");
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '003%' ");
		query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '164%' ");

        if (userId != null && userId != "" && !userId.isEmpty())
        {
               query.append("AND {E." + FMReportLogModel.USER + "} = ({{SELECT PK FROM {" + UserModel._TYPECODE + "} WHERE UNIQUEID='" + userId + "' }}) ");
        }
        if (accountNumber != null && accountNumber != "" && !accountNumber.isEmpty())
        {
               query.append("AND {E." + FMReportLogModel.CUSTACCOUNT + "} = ({{SELECT PK FROM {" + UserGroupModel._TYPECODE
                            + "} WHERE UNIQUEID = '" + accountNumber + "' }})");
        }


		
        query.append("}} UNION ALL {{ ");
        
        query.append("SELECT {D." + UserGroupModel.UID + "},{D." + UserGroupModel.DESCRIPTION
                + "}, 'SAMPLE' AS FMORDERTYPE,'0' AS PURCHASEORDERNUMBER,'orderStatus' AS TYPE,{E." + FMReportLogModel.CHECKCOUNT+ "} AS RC  ");
   query.append("FROM {" + UserGroupModel._TYPECODE + " AS D ");
   query.append("LEFT JOIN " + FMReportLogModel._TYPECODE + " AS E ");
   query.append("ON {E." + FMReportLogModel.CUSTACCOUNT + "} = {D." + UserGroupModel.PK + "}} ");
   query.append(" WHERE {E." + FMReportLogModel.TYPE + "}='orderStatus'");
   if (sDate != null && sDate != "")
   {
          //query.append("WHERE {E." + UploadOrderModel.UPDATEDTIME + "} BETWEEN '" + startDate + "' AND '?endDate' ");
          query.append("AND {E." + FMReportLogModel.STARTDATE + "} >= ?startDate_2 ");
   }
   if (eDate != null && eDate != "")
   {
          query.append(" AND {E." + FMReportLogModel.ENDDATE + "} <= ?endDate_2 ");
   }
   
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '004%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '003%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '164%' ");

   if (userId != null && userId != "" && !userId.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.USER + "} = ({{SELECT PK FROM {" + UserModel._TYPECODE + "} WHERE UNIQUEID='" + userId + "' }}) ");
   }
   if (accountNumber != null && accountNumber != "" && !accountNumber.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.CUSTACCOUNT + "} = ({{SELECT PK FROM {" + UserGroupModel._TYPECODE
                       + "} WHERE UNIQUEID = '" + accountNumber + "' }})");
   }
   
   query.append("}}");

   query.append(" UNION ALL {{ ");
   query.append("SELECT {D." + UserGroupModel.UID + "},{D." + UserGroupModel.DESCRIPTION
                + "},'SAMPLE' AS FMORDERTYPE,'0' AS PURCHASEORDERNUMBER,'backOrder' AS TYPE,{E." + FMReportLogModel.CHECKCOUNT+ "} AS RC  ");
   query.append("FROM {" + UserGroupModel._TYPECODE + " AS D ");
   query.append("LEFT JOIN " + FMReportLogModel._TYPECODE + " AS E ");
   query.append("ON {E." + FMReportLogModel.CUSTACCOUNT + "} = {D." + UserGroupModel.PK + "}} ");
   query.append(" WHERE {E." + FMReportLogModel.TYPE + "}='backOrder'");
  
   if (sDate != null && sDate != "")
   {
          //query.append("WHERE {E." + UploadOrderModel.UPDATEDTIME + "} BETWEEN '" + startDate + "' AND '?endDate' ");
          query.append(" AND {E." + FMReportLogModel.STARTDATE + "} >= ?startDate_2 ");
   }
   if (eDate != null && eDate != "")
   {
          query.append(" AND {E." + FMReportLogModel.ENDDATE + "} <= ?endDate_2 ");
   }
   
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '004%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '003%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '164%' ");

   if (userId != null && userId != "" && !userId.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.USER + "} = ({{SELECT PK FROM {" + UserModel._TYPECODE + "} WHERE UNIQUEID='" + userId + "' }}) ");
   }
   if (accountNumber != null && accountNumber != "" && !accountNumber.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.CUSTACCOUNT + "} = ({{SELECT PK FROM {" + UserGroupModel._TYPECODE
                       + "} WHERE UNIQUEID = '" + accountNumber + "' }})");
   }

   query.append("}}");

   query.append(" UNION ALL {{ ");
   query.append("SELECT {D." + UserGroupModel.UID + "},{D." + UserGroupModel.DESCRIPTION
                + "},'SAMPLE' AS FMORDERTYPE,'0' AS PURCHASEORDERNUMBER,'Invoice' AS TYPE,{E." + FMReportLogModel.CHECKCOUNT+ "} AS RC  ");
   query.append("FROM {" + UserGroupModel._TYPECODE + " AS D ");
   query.append("LEFT JOIN " + FMReportLogModel._TYPECODE + " AS E ");
   query.append("ON {E." + FMReportLogModel.CUSTACCOUNT + "} = {D." + UserGroupModel.PK + "}} ");
   query.append(" WHERE {E." + FMReportLogModel.TYPE + "}='Invoice'");
  
   if (sDate != null && sDate != "")
   {
          //query.append("WHERE {E." + UploadOrderModel.UPDATEDTIME + "} BETWEEN '" + startDate + "' AND '?endDate' ");
          query.append(" AND {E." + FMReportLogModel.STARTDATE + "} >= ?startDate_2 ");
   }
   if (eDate != null && eDate != "")
   {
          query.append(" AND {E." + FMReportLogModel.ENDDATE + "} <= ?endDate_2 ");
   }
   
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '004%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '003%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '164%' ");

   if (userId != null && userId != "" && !userId.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.USER + "} = ({{SELECT PK FROM {" + UserModel._TYPECODE + "} WHERE UNIQUEID='" + userId + "' }}) ");
   }
   if (accountNumber != null && accountNumber != "" && !accountNumber.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.CUSTACCOUNT + "} = ({{SELECT PK FROM {" + UserGroupModel._TYPECODE
                       + "} WHERE UNIQUEID = '" + accountNumber + "' }})");
   }
   
   query.append("}}");

   query.append(" UNION ALL {{ ");
   query.append("SELECT {D." + UserGroupModel.UID + "},{D." + UserGroupModel.DESCRIPTION
                + "},'SAMPLE' AS FMORDERTYPE,'0' AS PURCHASEORDERNUMBER,'PartInterChange' AS TYPE,{E." + FMReportLogModel.CHECKCOUNT+ "} AS RC  ");
   query.append("FROM {" + UserGroupModel._TYPECODE + " AS D ");
   query.append("LEFT JOIN " + FMReportLogModel._TYPECODE + " AS E ");
   query.append("ON {E." + FMReportLogModel.CUSTACCOUNT + "} = {D." + UserGroupModel.PK + "}} ");
   query.append(" WHERE {E." + FMReportLogModel.TYPE + "}='PartInterChange'");
  
   if (sDate != null && sDate != "")
   {
          //query.append("WHERE {E." + UploadOrderModel.UPDATEDTIME + "} BETWEEN '" + startDate + "' AND '?endDate' ");
          query.append(" AND {E." + FMReportLogModel.STARTDATE + "} >= ?startDate_2 ");
   }
   if (eDate != null && eDate != "")
   {
          query.append(" AND {E." + FMReportLogModel.ENDDATE + "} <= ?endDate_2 ");
   }
   
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '004%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '003%' ");
	query.append("AND {D." + UserGroupModel.UID + " } NOT LIKE '164%' ");

   if (userId != null && userId != "" && !userId.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.USER + "} = ({{SELECT PK FROM {" + UserModel._TYPECODE + "} WHERE UNIQUEID='" + userId + "' }}) ");
   }
   if (accountNumber != null && accountNumber != "" && !accountNumber.isEmpty())
   {
          query.append("AND {E." + FMReportLogModel.CUSTACCOUNT + "} = ({{SELECT PK FROM {" + UserGroupModel._TYPECODE
                       + "} WHERE UNIQUEID = '" + accountNumber + "' }})");
   }



           query.append("}} ) A ");
              query.append("GROUP BY UNIQUEID,DESCRIPTION,A.FMORDERTYPE,A.TYPE) ");
              query.append("GROUP BY UNIQUEID,DESCRIPTION ");

                LOG.info("query formed :: " + query);

              final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
              if (sDate != null && sDate != "")
              {		String startDate = parseToDateFromString(sDate);
                     flexibleSearchQuery.addQueryParameter("startDate_1", startDate);
                     flexibleSearchQuery.addQueryParameter("startDate_2", startDate );
		     flexibleSearchQuery.addQueryParameter("startDate_3", startDate );

              }

              if (eDate != null && eDate != "")
              {
			String endDate = parseToDateFromString(eDate);

                     flexibleSearchQuery.addQueryParameter("endDate_1", endDate);
                     flexibleSearchQuery.addQueryParameter("endDate_2", endDate);
 		     flexibleSearchQuery.addQueryParameter("endDate_3", endDate);

              }
              final List resultClassList = new ArrayList();
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);
              resultClassList.add(String.class);

              flexibleSearchQuery.setResultClassList(resultClassList);

              final SearchResult<String> finalResults = flexibleSearchService.search(flexibleSearchQuery);

              final List outputList = finalResults.getResult();

              LOG.info("result size" + outputList.size());

              LOG.info("result" + outputList.toString());

              return outputList;

       }


	public String parseToDateFromString(final String fromDate)
	{
		LOG.info("In Parse Date :: " + fromDate.trim());
		final SimpleDateFormat simpleDate = new SimpleDateFormat(MM_DD_YYYY);
		Date date = null;
		String date_1 = null;
		final SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yy 12:mm:ss a");
		if (fromDate != null && fromDate != "")
		{
			try
			{
				date = simpleDate.parse(fromDate);
				LOG.info("Try Converted Date :: " + date);
				date_1 = sdf2.format(date);
				System.out.println("Formatted Date:" + sdf2.format(date));
			}
			catch (final ParseException e1)
			{

				LOG.error(" Exception ::::" + e1.getMessage());
				return null;
			}

		}
		LOG.info("Converted Date :: " + date_1);
		return date_1;
	}

	public String parseEndDateFromString(final String fromDate)
	{
		LOG.info("In Parse Date :: " + fromDate.trim());
		final SimpleDateFormat simpleDate = new SimpleDateFormat(MM_DD_YYYY);
		Date date = null;
		String date_1 = null;
		String modifiedEndDate = null;
		final SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yy 12:mm:ss a");
		if (fromDate != null && fromDate != "")
		{
			try
			{
				date = simpleDate.parse(fromDate);
				LOG.info("Try Converted Date :: " + date);
				date_1 = sdf2.format(date);
				Date newdate = sdf2.parse(date_1);
				final Calendar c = Calendar.getInstance();
				c.setTime(newdate);
				c.add(Calendar.DATE, 1);
				newdate = c.getTime();
				modifiedEndDate = sdf2.format(newdate);
			}
			catch (final ParseException e1)
			{

				LOG.error(" Exception ::::" + e1.getMessage());
				return null;
			}

		}
		LOG.info("Converted Date :: " + date_1);
		return modifiedEndDate;
	}





       





}

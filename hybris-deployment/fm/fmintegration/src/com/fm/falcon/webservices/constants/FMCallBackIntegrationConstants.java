/**
 * 
 */
package com.fm.falcon.webservices.constants;

/**
 * @author Balaji
 *
 */


/**
 * Interface for integration request/response XML element names to be referred as constants
 * 
 */

public interface FMCallBackIntegrationConstants
{
	String URN = "ecom";
	String NAME_SPACE = "urn://Falcon//Interface//eCommerceCallBack";

	String QUERYTYPE = "queryType";
	String SAPACCOUNTCODE = "sapaccountcode";
	String JOBTITLE = "JobTitle";
	String DEPARTMENT = "Department";
	String COMPANY = "Company";

	String FIRSTNAME = "FirstName";
	String LASTNAME = "LastName";
	String COUNTRY = "Country";
	String EMAIL = "Email";

	String TELEPHONE = "Telephone";
	String STREETADDRESS1 = "StreetAddress1";
	String STREETADDRESS2 = "StreetAddress2";
	String CITY = "City";

	String ZIPCODE = "Zipcode";
	String STATECODE = "StateCode";
	String FREETEXT = "freeText";
	String QUERYDESCRIPTION="queryDescription";


	String PART_NUMBER = "PartNumber";
	String BRAND = "Brand";
	String INVOICE_NUMBER = "InvoiceNumber";
	String ORDER_NUMBER = "OrderNumber";
	String BEST_WAY_TO_CONTACT = "BestWaytoContact";
	String PHONE_DAYS = "PhoneDays";
	String PHONE_TIME = "PhoneTime";
	String PHONE_TIME_ZONE = "PhoneTimeZone";
	String CUSTOMER_ID = "CustomerID";
}

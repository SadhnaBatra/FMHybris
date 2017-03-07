/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : FMIntegrationConstants.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.constants;

/**
 * Interface for integration request/response XML element names to be referred as constants
 * 
 */
public interface FMIntegrationConstants
{

	String URN = "loy";
	String NAME_SPACE = "urn://Falcon/Interface/LoyaltyProgram";

	String SHIPTO_NAME_SPACE = "urn://Falcon/Interface/CustomerMaster";
	String SHIPTO_URN = "urn";

	//Element names for user registration request message
	String CUSTOMEREMAILID = "CustomerEmailID";
	String ACCOUNTCODE = "AccountCode";
	String FIRSTNAME = "FirstName";
	String LASTNAME = "LastName";
	String SOLD_SHIPTO = "Sold_ShipTo";
	String STREETNAME1 = "StreetName1";
	String STREETNAME2 = "StreetName2";
	String CITY = "City";
	String STATE = "State";
	String POSTALCODE = "PostCode";
	String COUNTRY = "Country";
	String TELEPHONE = "Telephone";
	String NEWSLETTERFLAG = "Newsletter_Flag";
	String CUSTOMERTYPE = "Customer_Type";
	String COMPANYNAME = "CompanyName";
	String TAXNUMBER = "TaxNumber";
	String COMPSTREETNAME1 = "CompStreetName1";
	String COMPSTREETNAME2 = "CompStreetName2";
	String COMPCITY = "CompCity";
	String COMPSTATE = "CompState";
	String COMPPOSTCODE = "CompPostCode";
	String COMPCOUNTRY = "CompCountry";
	String COMPTELEPHONE = "CompTelephone";
	String ASSOCIATION = "Association";
	String PROSPECTID = "ProspectID";
	String LOYALTYENROLLMENTFLAG = "LoyaltyEnrollmentFlag";
	String TRAININGPARTICIPTAIONFLAG = "TrainingParticiptaionFlag";
	String NEWPRODUCTALERT = "NewProductAlert";
	String NEWPROMOTIONFLAG = "NewPromotionFlag";
	String PROFILEUPDATEFLAG = "ProfileUpdate";
	String CUSTOMERCONTACTID = "CustomerID";
	//Element name for Ship-To request message
	String NAME1 = "name1";
	String POSTAL_CODE = "postal_code";
	String SHIPTO_COUNTRY = "country";
	String SHIPTO_CITY = "city";
	String SHIPTO_STATE = "state";
	String TAX_CODE = "tax_code";
	String COMP_ADDRESSLINE1 = "CompanyAddressLine1";
	String COMP_TAX_CODE = "tax_code";
	String COMP_ADDRESSLINE2 = "CompanyAddressLine12";
	String PHONENUMBER = "Phonenumber";
	String FAXNUMBER = "Faxnumber";
	String SOLD_ACC_NUMBER = "SoldToaccountnumber";
	String SHIPTOREQ = "ShipToReq";
	String LMSID = "TechAcademyUserid";
    String REFERRALEMAILID = "ReferralEmailId";
    String SURVEYCONTACTID = "ContactPersonID";
	String SURVEYUNIQUEID = "UniqueID";
	String SURVEYPROSPECTID = "ProspectID";

	String SURVEYNAMESPACE = "urn://Falcon/Interface/Survey";
	String SURVEYURN = "sur";
	String PROMO_CODE = "PromoCode";
	//FAL - 90 - Social Media changes
	String REVIEWFLAG = "ProductReviewFlag";
	String SOCIALMEDIAFLAG = "SocialMediaFlag";
	String MEMBERSHIPID = "MembershipID";
       String CONTACT="Contact";
}

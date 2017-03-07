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

public interface FMReturnsIntegrationConstants
{
	String URN = "ret";
	//String NAME_SPACE = "urn://Falcon//Interface//Returns^SI_RETURNSREQUEST_HYBRIS_SEND_OUT";
	String NAME_SPACE = "urn://Falcon//Interface//Returns";
	//String NAME_SPACE = "urn://Falcon//Interface//Returns";


	//Element names for ORDER RETURN request message
	//<ReturnsData>
	String SAPACCOUNTCODE = "sapAccountCode";
	String PURCHASEORDERNUMBER = "purchaseOrderNumber";
	String ORIGINALORDERNUMBER = "originalOrderNumber";
	String ACCOUNTNUMBER = "accountNumber";
	String RETURNDESCRIPTION = "returnDescription";
	// </ReturnsData>

	//<!--Zero or more repetitions:-->
	//<LineItems>
	//<!--Optional:-->
	String BRANDPART = "partBrandCode";
	String QUANTITY = "quantity";
	String UNIT = "unit";
	String RETURNREASON = "returnReason";
	//	</LineItems>
}

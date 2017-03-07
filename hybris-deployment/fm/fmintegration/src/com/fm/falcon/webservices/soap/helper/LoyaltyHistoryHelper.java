/**
 * 
 */
package com.fm.falcon.webservices.soap.helper;

import de.hybris.platform.util.Config;

import java.io.IOException;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.fm.falcon.webservices.constants.FMLoyaltyHistoryIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.CRMResponseDTO;
import com.fm.falcon.webservices.dto.LoyaltyHistoryRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyHistoryResponseDTO;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;




/**
 * @author SI279688
 * 
 */
public class LoyaltyHistoryHelper extends SoapMessageCreator
{
	private static final Logger LOG = Logger.getLogger(LoyaltyHistoryHelper.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fm.falcon.webservices.soap.AbstractSoapMessage#sendSOAPMessage(com.fm.falcon.webservices.dto.CRMRequestDTO)
	 */
	@Override
	public CRMResponseDTO sendSOAPMessage(final CRMRequestDTO crmRequestDTO) throws UnsupportedOperationException, SOAPException,
			IOException, ClassNotFoundException
	{
		final SOAPMessage loyaltyHistoryMessage = createSoapMessage();
		this.authenticateLoyaltyHistoryRequest();
		final SOAPBodyElement userrequest = createLoyaltyHistorySoapBody(loyaltyHistoryMessage,
				FMLoyaltyHistoryIntegrationConstants.URN, FMLoyaltyHistoryIntegrationConstants.NAME_SPACE,
				crmRequestDTO.getServiceName());
		createChildElements(userrequest, (LoyaltyHistoryRequestDTO) crmRequestDTO);
		loyaltyHistoryMessage.saveChanges();
		final SOAPBody response = sendSoapRequest(loyaltyHistoryMessage, Config.getParameter("loyaltyHistoryURL"));
		return parseResponse(response);

	}

	/**
	 * @param response
	 * @return
	 */
	private LoyaltyHistoryResponseDTO parseResponse(final SOAPBody response)
	{

LOG.info("loy helper");
		final LoyaltyHistoryResponseDTO result = new LoyaltyHistoryResponseDTO();
		/*
		 * if (response.getElementsByTagName("Balance_Points").item(0) != null) {
		 */

		if (response != null)
		{
			if (response.getElementsByTagName("Balance_Points").item(0) != null)
			{
				final String balPts = response.getElementsByTagName("Balance_Points").item(0).getTextContent();
				LOG.info("balPts" + balPts);

				if (balPts.contains("."))
				{
					LOG.info("INSIDE DOUBLE POINTS");
					result.setBalancePoints(balPts.substring(0, balPts.indexOf('.')));
				}

				else
				{
					LOG.info("INSIDE SINGLE POINTS");
					result.setBalancePoints(balPts);
				}
			}
		}
		return result;
	}

	/**
	 * @param userrequest
	 * @param crmRequestDTO
	 * @throws SOAPException
	 */
	private void createChildElements(final SOAPBodyElement bodyElement, final LoyaltyHistoryRequestDTO crmRequestDTO)
			throws SOAPException
	{
		System.out.println("bodyElement::::::: " + bodyElement);
		//final SOAPElement vinRequest = bodyElement.addChildElement("MT_Hybris_LoyaltyPointsHistory_Request");
		final SOAPElement soap_Element = bodyElement.addChildElement(createQname("Records"));

		//	LOG.info("vinRequest::::::: " + vinRequest);

		/*
		 * final SOAPElement vin =
		 * vinRequest.addChildElement(createQname(FMVinLookupIntegrationConstants.VIN)).addTextNode(
		 * vinLookupRequestDTO.getVin());
		 */
		//bodyElement.addChildElement(createQname("loy:MT_Hybris_LoyaltyPointsHistory_Request"));
		//bodyElement.addChildElement(createQname("RECORDS"));
		soap_Element.addChildElement(createQname("MembershipID")).addTextNode(crmRequestDTO.getMembership_Id());
		//		/soap_Element.addChildElement(createQname("Email_ID")).addTextNode(crmRequestDTO.getEmail_Id());
		soap_Element.addChildElement(createQname("Flag")).addTextNode(crmRequestDTO.getFlag());
	}
}

/**
 *
 * Copyright 2015 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : ShipToCreationHelper.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :13-FEB-2015
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.soap.helper;

//import de.hybris.platform.core.Registry;
import de.hybris.platform.util.Config;

import java.io.IOException;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.fm.falcon.webservices.constants.FMIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.UserRegistrationRequestDTO;
import com.fm.falcon.webservices.dto.UserRegistrationResponseDTO;
import com.fm.falcon.webservices.soap.AbstractSoapMessage;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;


/**
 * Class implements sendSOAPMessage method of {@link AbstractSoapMessage} to send Ship-To request for B2b.
 * 
 */
public class ShipToCreationHelper extends SoapMessageCreator
{

	/**
	 * Logger class to log messages
	 */
	private static final Logger LOG = Logger.getLogger(ShipToCreationHelper.class);

	/**
	 * User Ship-To creation request realization method for posting the message to external system and gets the parsed
	 * response object
	 * 
	 * @param crmRequestDTO
	 *           Request transfer object
	 * @return CRM response transfer object
	 * @throws SOAPException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public UserRegistrationResponseDTO sendSOAPMessage(final CRMRequestDTO crmRequestDTO) throws UnsupportedOperationException,
			SOAPException, IOException, ClassNotFoundException
	{
		final SOAPMessage shiptoCreationMsg = createSoapMessage();
		this.authenticategCRMRequest();
		final SOAPBodyElement userrequest = createSoapBody(shiptoCreationMsg, FMIntegrationConstants.SHIPTO_URN,
				FMIntegrationConstants.SHIPTO_NAME_SPACE, crmRequestDTO.getServiceName());

		createChildElements(userrequest, (UserRegistrationRequestDTO) crmRequestDTO);
		shiptoCreationMsg.saveChanges();

		final SOAPBody response = sendSoapRequest(shiptoCreationMsg, Config.getParameter("b2bShipToCreationEndPointURL"));
		//"http://134.238.100.239:50200/XISOAPAdapter/MessageServlet?senderParty=&senderService=BSE_HYBRIS&receiverParty=&receiverService=&interface=SI_Hybris_ShipTo_Request_Response_OUT&interfaceNamespace=urn://Falcon/Interface/CustomerMaster");

		return parseResponse(response);
	}

	/**
	 * Method parses the response and retrieves the response element values.
	 * 
	 * @param response
	 *           Response SOAPBody
	 * @return UserRegistrationResponseDTO
	 */

	private UserRegistrationResponseDTO parseResponse(final SOAPBody response)
	{


		final UserRegistrationResponseDTO responseDTO = new UserRegistrationResponseDTO();

		if ("s".equalsIgnoreCase(response.getElementsByTagName("status").item(0).getTextContent()))
		{
			responseDTO.setSeverity(response.getElementsByTagName("status").item(0).getTextContent());
			responseDTO.setResponseCode(response.getElementsByTagName("ShipToAccountNumber").item(0).getTextContent());

			LOG.info("Shipto Number " + responseDTO.getSeverity() + " " + responseDTO.getResponseCode());
		}
		return responseDTO;

	}

	private void createChildElements(final SOAPBodyElement bodyElement, final UserRegistrationRequestDTO userRequestDTO)
			throws SOAPException
	{
		final SOAPElement bodyChildElements = bodyElement.addChildElement(createQname(FMIntegrationConstants.SHIPTOREQ));

		bodyChildElements.addChildElement(createQname(FMIntegrationConstants.NAME1)).addTextNode(userRequestDTO.getCompanyName());
		bodyChildElements.addChildElement(createQname(FMIntegrationConstants.SHIPTO_CITY)).addTextNode(userRequestDTO.getCity());
		bodyChildElements.addChildElement(createQname(FMIntegrationConstants.SHIPTO_COUNTRY)).addTextNode(
				userRequestDTO.getCountry());
		bodyChildElements.addChildElement(createQname(FMIntegrationConstants.SHIPTO_STATE)).addTextNode(userRequestDTO.getState());
		bodyChildElements.addChildElement(createQname(FMIntegrationConstants.POSTAL_CODE)).addTextNode(
				userRequestDTO.getPostalCode());
		if (userRequestDTO.getTaxNumber() != null)
		{
			bodyChildElements.addChildElement(createQname(FMIntegrationConstants.TAX_CODE)).addTextNode(
					userRequestDTO.getTaxNumber());
		}
		else
		{
			bodyChildElements.addChildElement(createQname(FMIntegrationConstants.TAX_CODE));
		}

		bodyChildElements.addChildElement(createQname(FMIntegrationConstants.COMP_ADDRESSLINE1)).addTextNode(
				userRequestDTO.getCompStreetName1());


		if (userRequestDTO.getStreetName2() != null)
		{
			bodyChildElements.addChildElement(createQname(FMIntegrationConstants.COMPSTREETNAME2)).addTextNode(
					userRequestDTO.getStreetName2());
		}
		if (userRequestDTO.getCompTelephone() != null)
		{
			bodyChildElements.addChildElement(createQname(FMIntegrationConstants.PHONENUMBER)).addTextNode(
					userRequestDTO.getCompTelephone());
		}
		bodyChildElements.addChildElement(createQname(FMIntegrationConstants.SOLD_ACC_NUMBER)).addTextNode(
				userRequestDTO.getSold_ShipTo());
		if (userRequestDTO.getCompTelephone() != null)
		{
			bodyChildElements.addChildElement(createQname(FMIntegrationConstants.FAXNUMBER)).addTextNode(
					userRequestDTO.getCompTelephone());
		}
	}

	public static void main(final String[] args)
	{
		//		final UserRegistrationRequestDTO dto = new UserRegistrationRequestDTO();
		//		dto.setServiceName("MT_Hybris_ShipTo_Request");
		//
		//		dto.setCustomerEmailID("b2bcustomer3@email.com");
		//		dto.setAccountCode("038");
		//		dto.setFirstName("test");
		//		dto.setCompTelephone("1234567890");
		//		dto.setLastName("test123");
		//		dto.setSold_ShipTo("0010021332");
		//		dto.setStreetName1("test");
		//		dto.setStreetName2("test");
		//		dto.setCity("Troy");
		//		dto.setState("MI");
		//		dto.setPostalCode("48084");
		//		dto.setCountry("US");
		//		dto.setTelephone("122799798");
		//		dto.setNewsletterFlag("X");
		//		dto.setCustomerType("B2b");
		//		dto.setProspectID("0030000028");
		//		dto.setCompanyName("test comp");
		//		dto.setTaxNumber("9700070");
		//		dto.setCompStreetName1("comp street");
		//		dto.setCompStreetName2("comp street2");
		//		dto.setCompCity("Sanjose");
		//		dto.setCompState("CA");
		//		dto.setCompPostCode("55423");
		//		dto.setCompCountry("US");
		//		//dto.setAssociation("retailer");
		//		dto.setLoyaltyEnrollmentFlag("X");
		//		dto.setTrainingParticiptaionFlag("X");
		//		dto.setNewProductAlert("X");
		//		dto.setNewPromotionFlag("X");
		//
		//
		//		final ShipToCreationHelper helperclas = new ShipToCreationHelper();
		//		try
		//		{
		//			Registry.activateMasterTenant();
		//			helperclas.sendSOAPMessage(dto);
		//		}
		//		catch (final UnsupportedOperationException e)
		//		{
		//			LOG.error(e.getMessage());
		//		}
		//		catch (final ClassNotFoundException e)
		//		{
		//			LOG.error(e.getMessage());
		//		}
		//		catch (final SOAPException e)
		//		{
		//			LOG.error(e.getMessage());
		//		}
		//		catch (final IOException e)
		//		{
		//			LOG.error(e.getMessage());
		//		}
	}
}

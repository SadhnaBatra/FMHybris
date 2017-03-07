/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : UserRegistrationHelper.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.soap.helper;

import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
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
 * Class implements sendSOAPMessage method of {@link AbstractSoapMessage} to send registration request for B2B/B2b/B2C.
 * 
 */

public class UserRegistrationHelper extends SoapMessageCreator {

	/**
	 * Logger class to log messages
	 */
	private static final Logger LOG = Logger.getLogger(UserRegistrationHelper.class);

	private static final String CUSTOMER_TYPE_CODE_B2C = "B2C";
	private static final String CUSTOMER_TYPE_CODE_SHOP_OWNER_TECHNICIAN = "B2B_RS"; // This is an old Customer Type and it will NOT be used for new user registrations!!

	private static final String CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_LIGHT_VEHICLE = "B2B_LV";
	private static final String CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_COMMERCIAL_VEHICLE = "B2B_CV";
	private static final String CUSTOMER_TYPE_CODE_RETAILER = "B2B_RT";
	private static final String CUSTOMER_TYPE_CODE_JOBBER = "B2B_JB";
	private static final String CUSTOMER_TYPE_CODE_REPAIR_SHOP_OWNER = "B2B_RS";
	private static final String CUSTOMER_TYPE_CODE_TECHNICIAN = "B2T_TC";
	private static final String CUSTOMER_TYPE_CODE_CONSUMER = "CON_AN";
	private static final String CUSTOMER_TYPE_CODE_STUDENT = "B2T_ST";
	
	private static final String USER_REG_RESPONSE_SEVERITY_E = "E";

	/**
	 * User registration request realization method for posting the message to external system and gets the parsed
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
			SOAPException, IOException, ClassNotFoundException {
		LOG.info("Inside 'sendSOAPMessage(...)'");
		final SOAPMessage userRegistrationMsg = createSoapMessage();
		final SOAPBodyElement userrequest = createSoapBody(userRegistrationMsg, FMIntegrationConstants.URN,
				FMIntegrationConstants.NAME_SPACE, crmRequestDTO.getServiceName());

		LOG.info("sendSOAPMessage(...): Creating Child Elements");
		createChildElements(userrequest, (UserRegistrationRequestDTO) crmRequestDTO);
		userRegistrationMsg.saveChanges();

		LOG.info("sendSOAPMessage(...): Sending SOAP Request to SAP CRM");
		final SOAPBody response = sendSoapRequest(userRegistrationMsg, Config.getParameter("userRegistrationEndPointURL"), Config.getParameter("userRegistrationServiceUID"), Config.getParameter("userRegistrationServicePWD"));

		return parseResponse(response, ((UserRegistrationRequestDTO) crmRequestDTO).getCustomerType());
	}

	/**
	 * Method parses the response and retrieves the response element values.
	 * 
	 * @param response
	 *           Response SOAPBody
	 * @return UserRegistrationResponseDTO
	 */

	private UserRegistrationResponseDTO parseResponse(final SOAPBody response, final String customerType) {
		final UserRegistrationResponseDTO responseDTO = new UserRegistrationResponseDTO();
		String message = "";
		responseDTO.setResponseCode(response.getElementsByTagName("Code").item(0).getTextContent());
		int errorCount = 0;
		final int messageErrorCount = response.getElementsByTagName("Severity").getLength();
		for (int i = 0; i < messageErrorCount; i++) {
			if (USER_REG_RESPONSE_SEVERITY_E.equalsIgnoreCase(response.getElementsByTagName("Severity").item(i).getTextContent())) {
				errorCount++;
				message = response.getElementsByTagName("text").item(i).getTextContent();
				LOG.info("parseResponse(...): ERROR COUNT IS: " + errorCount);
				LOG.info("parseResponse(...): ERROR MESSAGE IS: " + message);
			}
		}

		LOG.info("parseResponse(...): ERROR COUNT IS: " + errorCount);
		if (errorCount == 0) {
			if (CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_COMMERCIAL_VEHICLE.equals(customerType)
					|| CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_LIGHT_VEHICLE.equals(customerType)
					|| CUSTOMER_TYPE_CODE_RETAILER.equals(customerType)
					|| CUSTOMER_TYPE_CODE_JOBBER.equals(customerType)
					|| CUSTOMER_TYPE_CODE_REPAIR_SHOP_OWNER.equals(customerType)
					|| CUSTOMER_TYPE_CODE_TECHNICIAN.equals(customerType)
					|| CUSTOMER_TYPE_CODE_STUDENT.equals(customerType)) {
				responseDTO.setContactID(response.getElementsByTagName("Contact").item(0).getTextContent());
			}

			if (CUSTOMER_TYPE_CODE_REPAIR_SHOP_OWNER.equals(customerType)
					|| CUSTOMER_TYPE_CODE_TECHNICIAN.equals(customerType)) {
				responseDTO.setProspectID(response.getElementsByTagName("Prospect").item(0).getTextContent());
				if (response.getElementsByTagName("MembershipID").item(0) != null) {
					responseDTO.setB2cLoyaltyMembershipId(response.getElementsByTagName("MembershipID").item(0).getTextContent());

					LOG.info("parseResponse(...): From CRM: LOYALTY ID MembershipID: "
							+ response.getElementsByTagName("MembershipID").item(0).getTextContent());
				}

				if (response.getElementsByTagName("Membership_ID").item(0) != null) {
					LOG.info("parseResponse(...): From CRM: LOYALTY ID Membership_ID: "
							+ response.getElementsByTagName("Membership_ID").item(0).getTextContent());
				}

				if (response.getElementsByTagName("MembershipId").item(0) != null) {
					LOG.info("parseResponse(...): From CRM: LOYALTY ID MembershipId: "
							+ response.getElementsByTagName("MembershipId").item(0).getTextContent());
				}

				if (response.getElementsByTagName("Membership_Id").item(0) != null) {
					LOG.info("parseResponse(...): From CRM: LOYALTY ID Membership_Id: "
							+ response.getElementsByTagName("Membership_Id").item(0).getTextContent());
				}
			}
		}

		if (CUSTOMER_TYPE_CODE_CONSUMER.equals(customerType)) {
			LOG.info("parseResponse(...): INSIDE OF CUSTOMER_TYPE_CODE_CONSUMER");
			if (response.getElementsByTagName("MembershipID").item(0) != null) {
				LOG.info(response.getElementsByTagName("MembershipID").item(0).getTextContent());
				responseDTO.setB2cLoyaltyMembershipId(response.getElementsByTagName("MembershipID").item(0).getTextContent());
			} else {
				final int messageCount = response.getElementsByTagName("Severity").getLength();
				for (int i = 0; i < messageCount; i++) {
					if (USER_REG_RESPONSE_SEVERITY_E.equals(response.getElementsByTagName("Severity").item(i))) {
						message = response.getElementsByTagName("text").item(i).getTextContent();
						LOG.info(message);
					}
				}
			}
		}

		responseDTO.setSeverityText(message);

		LOG.info("parseResponse(...): B4 RETURN");
		return responseDTO;
	}

	private void createChildElements(final SOAPBodyElement bodyElement, final UserRegistrationRequestDTO userRequestDTO)
			throws SOAPException {

		bodyElement.addChildElement(createQname(FMIntegrationConstants.CUSTOMEREMAILID)).addTextNode(
				userRequestDTO.getCustomerEmailID());
		bodyElement.addChildElement(createQname(FMIntegrationConstants.ACCOUNTCODE)).addTextNode(userRequestDTO.getAccountCode());
		bodyElement.addChildElement(createQname(FMIntegrationConstants.FIRSTNAME)).addTextNode(userRequestDTO.getFirstName());
		bodyElement.addChildElement(createQname(FMIntegrationConstants.LASTNAME)).addTextNode(userRequestDTO.getLastName());

		if (userRequestDTO.getSold_ShipTo() != null) {
			bodyElement.addChildElement(createQname(FMIntegrationConstants.SOLD_SHIPTO))
					.addTextNode(userRequestDTO.getSold_ShipTo());
		}

		if (userRequestDTO.getStreetName1() != null) {
			bodyElement.addChildElement(createQname(FMIntegrationConstants.STREETNAME1))
					.addTextNode(userRequestDTO.getStreetName1());
		}

		if (userRequestDTO.getStreetName2() != null) {
			bodyElement.addChildElement(createQname(FMIntegrationConstants.STREETNAME2))
					.addTextNode(userRequestDTO.getStreetName2());
		}

		if (!CUSTOMER_TYPE_CODE_B2C.equals(userRequestDTO.getCustomerType())) {
			if (userRequestDTO.getCity() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.CITY)).addTextNode(userRequestDTO.getCity());
			}

			if (userRequestDTO.getState() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.STATE)).addTextNode(userRequestDTO.getState());
			}

			if (userRequestDTO.getPostalCode() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.POSTALCODE)).addTextNode(
						userRequestDTO.getPostalCode());
			}

			if (userRequestDTO.getTelephone() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.TELEPHONE)).addTextNode(userRequestDTO.getTelephone());
			}
		}

		if (userRequestDTO.getCountry() != null) {
			bodyElement.addChildElement(createQname(FMIntegrationConstants.COUNTRY)).addTextNode(userRequestDTO.getCountry());
		}

		if (userRequestDTO.getNewsletterFlag() != null) {
			bodyElement.addChildElement(createQname(FMIntegrationConstants.NEWSLETTERFLAG)).addTextNode(
					userRequestDTO.getNewsletterFlag());
		}

		if (userRequestDTO.getProspectID() != null) {
			bodyElement.addChildElement(createQname(FMIntegrationConstants.PROSPECTID)).addTextNode(userRequestDTO.getProspectID());
		}

		if (userRequestDTO.getReferralEmailId() != null) {
			bodyElement.addChildElement(createQname(FMIntegrationConstants.REFERRALEMAILID)).addTextNode(
					userRequestDTO.getReferralEmailId());
		}

		bodyElement.addChildElement(createQname(FMIntegrationConstants.CUSTOMERTYPE)).addTextNode(userRequestDTO.getCustomerType());

		if (CUSTOMER_TYPE_CODE_REPAIR_SHOP_OWNER.equals(userRequestDTO.getCustomerType())
				|| CUSTOMER_TYPE_CODE_TECHNICIAN.equals(userRequestDTO.getCustomerType())
				|| CUSTOMER_TYPE_CODE_STUDENT.equals(userRequestDTO.getCustomerType())) {

			if (userRequestDTO.getTaxNumber() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.TAXNUMBER)).addTextNode(userRequestDTO.getTaxNumber());
			}

			if (userRequestDTO.getCompanyName() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.COMPANYNAME)).addTextNode(
						userRequestDTO.getCompanyName());
			}

			if (userRequestDTO.getCompStreetName1() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.COMPSTREETNAME1)).addTextNode(
						userRequestDTO.getCompStreetName1());
			}

			if (userRequestDTO.getCompStreetName2() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.COMPSTREETNAME2)).addTextNode(
						userRequestDTO.getCompStreetName2());
			}

			if (userRequestDTO.getCompCity() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.COMPCITY)).addTextNode(userRequestDTO.getCompCity());
			}

			if (userRequestDTO.getCompState() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.COMPSTATE)).addTextNode(userRequestDTO.getCompState());
			}

			if (userRequestDTO.getCompPostCode() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.COMPPOSTCODE)).addTextNode(
						userRequestDTO.getCompPostCode());
			}

			if (userRequestDTO.getCompCountry() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.COMPCOUNTRY)).addTextNode(
						userRequestDTO.getCompCountry());
			}

			if (userRequestDTO.getAssociation() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.ASSOCIATION)).addTextNode(
						userRequestDTO.getAssociation());
			}

			if (userRequestDTO.getLmsId() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.LMSID)).addTextNode(userRequestDTO.getLmsId());
			}

			if (userRequestDTO.getPromoCode() != null && !userRequestDTO.getPromoCode().isEmpty()) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.PROMO_CODE))
						.addTextNode(userRequestDTO.getPromoCode());
			}

			if (userRequestDTO.getUniqueID() != null && !userRequestDTO.getUniqueID().isEmpty()) {
				for (int i = 0; i < userRequestDTO.getUniqueID().size(); i++) {
					if (userRequestDTO.getUniqueID().get(i) != null) {
						bodyElement.addChildElement(createQname(FMIntegrationConstants.SURVEYUNIQUEID)).addTextNode(
								((List<String>) userRequestDTO.getUniqueID()).get(i).trim());
					}
				}
			}

			if (userRequestDTO.getLoyaltyEnrollmentFlag() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.LOYALTYENROLLMENTFLAG)).addTextNode(
						userRequestDTO.getLoyaltyEnrollmentFlag());
			}

			if (userRequestDTO.getTrainingParticiptaionFlag() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.TRAININGPARTICIPTAIONFLAG)).addTextNode(
						userRequestDTO.getTrainingParticiptaionFlag());
			}

			if (userRequestDTO.getNewProductAlert() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.NEWPRODUCTALERT)).addTextNode(
						userRequestDTO.getNewProductAlert());
			}

			if (userRequestDTO.getNewPromotionFlag() != null) {
				bodyElement.addChildElement(createQname(FMIntegrationConstants.NEWPROMOTIONFLAG)).addTextNode(
						userRequestDTO.getNewPromotionFlag());
			}
		}
	}

	public static void main(final String[] args) {
		//		final UserRegistrationRequestDTO dto = new UserRegistrationRequestDTO();
		//		dto.setServiceName("Request");
		//
		//		dto.setCustomerEmailID("b2bcustomer3@email.com");
		//		dto.setAccountCode("038");
		//		dto.setFirstName("test");
		//		dto.setLastName("test123");
		//		//dto.setSold_ShipTo("0000000006");
		//		dto.setStreetName1("test");
		//		dto.setStreetName2("test");
		//		dto.setCity("Sanjose");
		//		dto.setState("CA");
		//		dto.setPostalCode("56010");
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
		//		final UserRegistrationHelper helperclas = new UserRegistrationHelper();
		//		try
		//		{
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

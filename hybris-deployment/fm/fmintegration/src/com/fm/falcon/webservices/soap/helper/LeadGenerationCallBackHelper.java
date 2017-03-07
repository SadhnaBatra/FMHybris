/**
 * 
 */
package com.fm.falcon.webservices.soap.helper;

import com.fm.falcon.webservices.constants.FMCallBackIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.LeadGenerationCallBackRequestDTO;
import com.fm.falcon.webservices.dto.LeadGenerationCallBackResponseDTO;
import com.fm.falcon.webservices.soap.AbstractSoapMessage;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;
import de.hybris.platform.util.Config;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;


/**
 * @author Balaji Class implements sendSOAPMessage method of {@link AbstractSoapMessage} to send return request.
 * 
 */
public class LeadGenerationCallBackHelper extends SoapMessageCreator
{
	/**
	 * Logger class to log messages
	 */
	private static final Logger LOG = Logger.getLogger(LeadGenerationCallBackHelper.class);

	/**
	 * CallBack request realization method for posting the message to external system and gets the parsed response object
	 * 
	 * @param crmRequestDTO
	 *           Request transfer object
	 * @return CRM response transfer object
	 * @throws SOAPException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public LeadGenerationCallBackResponseDTO sendSOAPMessage(final CRMRequestDTO crmRequestDTO)
			throws UnsupportedOperationException, SOAPException, IOException, ClassNotFoundException
	{

		LOG.info("Inside sendSOAPMessage");

		final SOAPMessage callBackMsg = createSoapMessage();
		this.authenticategCallBackRequest();
		final SOAPBodyElement callbackrequest = createSoapBodyForReturnCallBack(callBackMsg, crmRequestDTO.getServiceName());

		createChildElements(callbackrequest, (LeadGenerationCallBackRequestDTO) crmRequestDTO);
		callBackMsg.saveChanges();

		LOG.info(" callBack WSDL URL:::::" + Config.getParameter("leadGenerationEndPointURL"));

		final SOAPBody response = sendSoapRequest(callBackMsg, Config.getParameter("leadGenerationEndPointURL"));
		

		LOG.info("\n  in sendSOAPMessage After sending request to PI:" + response);
		return parseResponse(response);
	}

	/**
	 * Method parses the response and retrieves the response element values.
	 * 
	 * @param response
	 *           Response SOAPBody
	 * @return UserRegistrationResponseDTO
	 */

	@SuppressWarnings("unused")
	private LeadGenerationCallBackResponseDTO parseResponse(final SOAPBody response)
	{

		LOG.info("Inside parseResponse::" + response);

		final LeadGenerationCallBackResponseDTO responseDTO = new LeadGenerationCallBackResponseDTO();

		return responseDTO;
	}


	

	private void callBackData(final SOAPBodyElement bodyElement,
			final LeadGenerationCallBackRequestDTO leadGenerationCallBackRequestDTO) throws SOAPException
	{

		LOG.info(" Start Call Back data ==>");

		final SOAPElement leadTask = bodyElement.addChildElement(createQname("LEAD_TASK"));

		LOG.info(" leadTask " + leadTask);

		final SOAPElement queryType = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.QUERYTYPE)).addTextNode(
				leadGenerationCallBackRequestDTO.getQueryType());
		final SOAPElement queryDescription = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.QUERYDESCRIPTION)).addTextNode(
				leadGenerationCallBackRequestDTO.getQueryDescription());
		
		LOG.info(" queryType " + queryType);

		if (leadGenerationCallBackRequestDTO.getJobTitle() != null)
		{
			final SOAPElement jobTitle = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.JOBTITLE)).addTextNode(
					leadGenerationCallBackRequestDTO.getJobTitle());
			LOG.info(" JobTitle " + jobTitle);
		}
		/*
		 * final SOAPElement department = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.DEPARTMENT))
		 * .addTextNode(leadGenerationCallBackRequestDTO.getDepartment()); LOG.info(" Department " + department);
		 */
		if (leadGenerationCallBackRequestDTO.getCompany() != null)
		{
			final SOAPElement company = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.COMPANY)).addTextNode(
					leadGenerationCallBackRequestDTO.getCompany());
			LOG.info(" Company " + company);
		}

		final SOAPElement firstName = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.FIRSTNAME)).addTextNode(
				leadGenerationCallBackRequestDTO.getFirstName());
		LOG.info(" FirstName " + firstName);
		final SOAPElement lastName = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.LASTNAME)).addTextNode(
				leadGenerationCallBackRequestDTO.getLastName());
		LOG.info(" LastName " + lastName);
		if (leadGenerationCallBackRequestDTO.getCountry() != null)
		{
			final SOAPElement country = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.COUNTRY)).addTextNode(
					leadGenerationCallBackRequestDTO.getCountry());
			LOG.info(" Country " + country);
		}

		final SOAPElement email = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.EMAIL)).addTextNode(
				leadGenerationCallBackRequestDTO.getEmail());
		LOG.info(" Email " + email);
		final SOAPElement telephone = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.TELEPHONE)).addTextNode(
				leadGenerationCallBackRequestDTO.getTelephone());
		LOG.info(" Telephone " + telephone);
		if (leadGenerationCallBackRequestDTO.getStreetAddress1() != null)
		{
			final SOAPElement streetAddress1 = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.STREETADDRESS1))
					.addTextNode(leadGenerationCallBackRequestDTO.getStreetAddress1());
			LOG.info(" StreetAddress1 " + streetAddress1);
		}
		if (leadGenerationCallBackRequestDTO.getStreetAddress2() != null)
		{
			final SOAPElement streetAddress2 = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.STREETADDRESS2))
					.addTextNode(leadGenerationCallBackRequestDTO.getStreetAddress2());
			LOG.info(" StreetAddress2 " + streetAddress2);
		}

		if (leadGenerationCallBackRequestDTO.getCity() != null)
		{
			final SOAPElement city = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.CITY)).addTextNode(
					leadGenerationCallBackRequestDTO.getCity());
			LOG.info(" City " + city);
		}
		if (leadGenerationCallBackRequestDTO.getZipcode() != null)
		{
			final SOAPElement zipcode = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.ZIPCODE)).addTextNode(
					leadGenerationCallBackRequestDTO.getZipcode());
			LOG.info(" Zipcode " + zipcode);
		}

		if (leadGenerationCallBackRequestDTO.getStateCode() != null)
		{
			final SOAPElement stateCode = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.STATECODE))
					.addTextNode(leadGenerationCallBackRequestDTO.getStateCode());
			LOG.info(" StateCode " + stateCode);
		}

		final SOAPElement freeText = leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.FREETEXT)).addTextNode(
				leadGenerationCallBackRequestDTO.getFreeText());
		LOG.info(" freeText " + freeText);

		leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.BEST_WAY_TO_CONTACT)).addTextNode(leadGenerationCallBackRequestDTO.getBestWayToContact());

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getPartNumber())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.PART_NUMBER)).addTextNode(leadGenerationCallBackRequestDTO.getPartNumber());
		}

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getBrand())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.BRAND)).addTextNode(leadGenerationCallBackRequestDTO.getBrand());
		}

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getInvoiceNumber())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.INVOICE_NUMBER)).addTextNode(leadGenerationCallBackRequestDTO.getInvoiceNumber());
		}

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getOrderNumber())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.ORDER_NUMBER)).addTextNode(leadGenerationCallBackRequestDTO.getOrderNumber());
		}

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getPhoneDays())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.PHONE_DAYS)).addTextNode(leadGenerationCallBackRequestDTO.getPhoneDays());
		}

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getPhoneTime())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.PHONE_TIME)).addTextNode(leadGenerationCallBackRequestDTO.getPhoneTime());
		}

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getPhoneTimeZone())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.PHONE_TIME_ZONE)).addTextNode(leadGenerationCallBackRequestDTO.getPhoneTimeZone());
		}

		if (StringUtils.isNotBlank(leadGenerationCallBackRequestDTO.getCustomerID())) {
			leadTask.addChildElement(createQname(FMCallBackIntegrationConstants.CUSTOMER_ID)).addTextNode(leadGenerationCallBackRequestDTO.getCustomerID());
		}

		LOG.info(" End Call Back data ==>");


	}


	private void createChildElements(final SOAPBodyElement bodyElement,
			final LeadGenerationCallBackRequestDTO leadGenerationCallBackRequestDTO) throws SOAPException
	{

		LOG.info(" createChildElements ");

		callBackData(bodyElement, leadGenerationCallBackRequestDTO);


	}

	public static void main(final String[] args)
	{
		LOG.info("inside main method");

		
	}


}

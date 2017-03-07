package com.fm.falcon.webservices.soap.helper;

import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.fm.falcon.webservices.constants.FMReturnsIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.ReturnItemsDTO;
import com.fm.falcon.webservices.dto.ReturnsRequestDTO;
import com.fm.falcon.webservices.dto.ReturnsResponseDTO;
import com.fm.falcon.webservices.soap.AbstractSoapMessage;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;


/**
 * @author Balaji Class implements sendSOAPMessage method of {@link AbstractSoapMessage} to send return request.
 * 
 */
public class ReturnsHelper extends SoapMessageCreator
{
	/**
	 * Logger class to log messages
	 */
	private static final Logger LOG = Logger.getLogger(ReturnsHelper.class);

	/**
	 * Returns request realization method for posting the message to external system and gets the parsed response object
	 * 
	 * @param crmRequestDTO
	 *           Request transfer object
	 * @return CRM response transfer object
	 * @throws SOAPException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public ReturnsResponseDTO sendSOAPMessage(final CRMRequestDTO crmRequestDTO) throws UnsupportedOperationException,
			SOAPException, IOException, ClassNotFoundException
	{

		LOG.info("Inside sendSOAPMessage");

		final SOAPMessage returnsMsg = createSoapMessage();
		this.authenticategReturnRequest();
		final SOAPBodyElement returnsrequest = createSoapBodyForReturnCallBack(returnsMsg, crmRequestDTO.getServiceName());

		createChildElements(returnsrequest, (ReturnsRequestDTO) crmRequestDTO);
		returnsMsg.saveChanges();

		//String returnEndPointURL = "http://134.238.100.239:50200/XISOAPAdapter/MessageServlet?channel=:BSE_HYBRIS:CC_SOAP_SND_RETURN_REQUEST_C09&version=3.0&Sender.Service=BSE_HYBRIS&Interface=urn://Falcon//Interface//Returns";

		LOG.info("WSDL URL:::::" + Config.getParameter("returnEndPointURL"));

		final SOAPBody response = sendSoapRequest(returnsMsg, Config.getParameter("returnEndPointURL"));
		LOG.info("Inside response::::::::::::" + response);

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
	private ReturnsResponseDTO parseResponse(final SOAPBody response)
	{

		LOG.info("Inside parseResponse==>" + response);

		final ReturnsResponseDTO responseDTO = new ReturnsResponseDTO();

		responseDTO.setSeverity(response.getElementsByTagName("Severity").item(0).getTextContent());
		LOG.info("Inside response Severity:::" + response.getElementsByTagName("Severity").item(0).getTextContent());
		responseDTO.setCode(response.getElementsByTagName("Code").item(0).getTextContent());
		//responseDTO.setResponseCode(response.getElementsByTagName("Code").item(0).getTextContent());
		LOG.info("Inside response Code:::" + response.getElementsByTagName("Code").item(0).getTextContent());
		responseDTO.setText(response.getElementsByTagName("Text").item(0).getTextContent());
		LOG.info("Inside response Text:::" + response.getElementsByTagName("Text").item(0).getTextContent());

		LOG.info("Inside response code:::" + responseDTO.getCode());

		//if (responseDTO.getCode().equals("000"))
		//{
		if (("000").equals(responseDTO.getCode()))
		{
			responseDTO.setReturnId(response.getElementsByTagName("ReturnID").item(0).getTextContent());
			LOG.info("Inside response ReturnId:::" + response.getElementsByTagName("ReturnID").item(0).getTextContent());
		}

		return responseDTO;
	}


	private void createChildElements(final SOAPBodyElement bodyElement, final ReturnsRequestDTO returnsRequestDTO)
			throws SOAPException
	{

		LOG.info("Start createChildElements ");

		//returnTestData(bodyElement);

		returnData(bodyElement, returnsRequestDTO);

		LOG.info("End createChildElements ");

	}

	//	public void returnTestData(final SOAPBodyElement bodyElement) throws SOAPException
	//	{
	//		LOG.info("Start Inside returnTestData ");
	//
	//		final SOAPElement header = bodyElement.addChildElement(createQname("Header"));
	//
	//		LOG.info(" header " + header);
	//
	//		header.addChildElement(createQname(FMReturnsIntegrationConstants.SAPACCOUNTCODE)).addTextNode("0000000768");
	//		//LOG.info(" sapactcode " + sapactcode);
	//		final SOAPElement ordernumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ORIGINALORDERNUMBER))
	//				.addTextNode("0700000052");
	//		LOG.info(" ordernumber " + ordernumber);
	//		final SOAPElement accountnumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ACCOUNTNUMBER))
	//				.addTextNode("0000000768");
	//		LOG.info(" accountnumber " + accountnumber);
	//		final SOAPElement returnreason = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNREASON))
	//				.addTextNode("ZR02");
	//		LOG.info(" returnreason " + returnreason);
	//		final SOAPElement returndescription = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNDESCRIPTION))
	//				.addTextNode("Returning items due to incompatible type ");
	//		LOG.info(" returndescription " + returndescription);
	//
	//
	//		final SOAPElement lineItems = bodyElement.addChildElement(createQname("LineItems"));
	//		LOG.info(" LineItems " + lineItems);
	//
	//		final SOAPElement partBrandCode = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.BRANDPART))
	//				.addTextNode("AXXES800606");
	//		LOG.info(" partBrandCode " + partBrandCode);
	//		final SOAPElement qty = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.QUANTITY)).addTextNode("1");
	//		LOG.info(" qty " + qty);
	//		final SOAPElement unit = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.UNIT)).addTextNode("EAU");
	//		LOG.info(" unit " + unit);
	//
	//		LOG.info("End Inside returnTestData ");
	//
	//	}

	public void returnData(final SOAPBodyElement bodyElement, final ReturnsRequestDTO returnsRequestDTO) throws SOAPException
	{
		LOG.info("Start Inside returnData ");


		final SOAPElement header = bodyElement.addChildElement(createQname("Header"));

		LOG.info(" header " + header);

		final SOAPElement sapactcode = header.addChildElement(createQname(FMReturnsIntegrationConstants.SAPACCOUNTCODE))
				.addTextNode(returnsRequestDTO.getSapAccountCode());
		//final SOAPElement sapactcode = header.addChildElement(createQname(FMReturnsIntegrationConstants.SAPACCOUNTCODE)).addTextNode("10014254");

		LOG.info(" sapactcode " + sapactcode);
		if (returnsRequestDTO.getOriginalOrderNumber() != null)
		{
			final SOAPElement ordernumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ORIGINALORDERNUMBER))
					.addTextNode(returnsRequestDTO.getOriginalOrderNumber());
			LOG.info(" ordernumber:: " + ordernumber);
		}
		else
		{
			final SOAPElement ordernumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ORIGINALORDERNUMBER))
					.addTextNode("104695374");
			LOG.info(" ordernumber:: " + ordernumber);
		}

		//LOG.info(" ordernumber " + ordernumber);
		//final SOAPElement accountnumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ACCOUNTNUMBER)).addTextNode("10014254");
		final SOAPElement accountnumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ACCOUNTNUMBER))
				.addTextNode(returnsRequestDTO.getAccountNumber());
		LOG.info(" accountnumber " + accountnumber);
		//final SOAPElement returnreason = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNREASON)).addTextNode("ZR02");
		final SOAPElement returnreason = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNREASON))
				.addTextNode(returnsRequestDTO.getReasonOfReturn());
		LOG.info(" returnreason " + returnreason);
		//final SOAPElement returndescription = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNDESCRIPTION)).addTextNode("Returning items due to incompatible type ");
		final SOAPElement returndescription = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNDESCRIPTION))
				.addTextNode(returnsRequestDTO.getReturnDescription());
		LOG.info(" returndescription " + returndescription);


		final SOAPElement lineItems = bodyElement.addChildElement(createQname("LineItems"));
		LOG.info(" LineItems " + lineItems);

		//final SOAPElement partBrandCode = LineItems.addChildElement(createQname(FMReturnsIntegrationConstants.BRANDPART)).addTextNode("AXXES800606");

		final List<ReturnItemsDTO> returnItemDetails = returnsRequestDTO.getReturnItems();

		LOG.info(" returnItemDetails " + returnItemDetails.size());

		if (!returnItemDetails.isEmpty())
		{
			for (final java.util.Iterator<ReturnItemsDTO> returnItems = returnItemDetails.iterator(); returnItems.hasNext();)
			{
				final ReturnItemsDTO returnItemsDTO = returnItems.next();

				final String partCode = returnItemsDTO.getBrandPart();
				final String returnQty = returnItemsDTO.getReturnqty();
				final String partDes = returnItemsDTO.getItemDescription();
				//final String returnableQty = returnItemsDTO.getBrandPart();

				final SOAPElement partBrandCode = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.BRANDPART))
						.addTextNode(partCode);
				LOG.info(" partBrandCode :" + partBrandCode);
				LOG.info(" partBrandCode " + partDes + " : " + partBrandCode);
				final SOAPElement qty = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.QUANTITY)).addTextNode(
						returnQty);
				LOG.info(" qty " + qty);
				final SOAPElement unit = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.UNIT))
						.addTextNode("EAU");
				LOG.info(" unit " + unit);
			}
		}


		LOG.info("End Inside returnData ");

	}

	//	public void returnData1(final SOAPBodyElement bodyElement, final ReturnsRequestDTO returnsRequestDTO) throws SOAPException
	//	{
	//		LOG.info("Start Inside returnData ");
	//
	//		final SOAPElement header = bodyElement.addChildElement(createQname("Header"));
	//
	//		LOG.info(" header " + header);
	//
	//		header.addChildElement(createQname(FMReturnsIntegrationConstants.SAPACCOUNTCODE)).addTextNode("10014254");
	//		//LOG.info(" sapactcode " + sapactcode);
	//		final SOAPElement ordernumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ORIGINALORDERNUMBER))
	//				.addTextNode("104695374");
	//		LOG.info(" ordernumber " + ordernumber);
	//		final SOAPElement accountnumber = header.addChildElement(createQname(FMReturnsIntegrationConstants.ACCOUNTNUMBER))
	//				.addTextNode("10014254");
	//		LOG.info(" accountnumber " + accountnumber);
	//		final SOAPElement returnreason = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNREASON))
	//				.addTextNode("ZR02");
	//		LOG.info(" returnreason " + returnreason);
	//		final SOAPElement returndescription = header.addChildElement(createQname(FMReturnsIntegrationConstants.RETURNDESCRIPTION))
	//				.addTextNode("Returning items due to incompatible type ");
	//		LOG.info(" returndescription " + returndescription);
	//
	//
	//		final SOAPElement lineItems = bodyElement.addChildElement(createQname("LineItems"));
	//		LOG.info(" LineItems " + lineItems);
	//
	//		final SOAPElement partBrandCode = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.BRANDPART))
	//				.addTextNode("AXXES800606");
	//		LOG.info(" partBrandCode " + partBrandCode);
	//		final SOAPElement qty = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.QUANTITY)).addTextNode("1");
	//		LOG.info(" qty " + qty);
	//		final SOAPElement unit = lineItems.addChildElement(createQname(FMReturnsIntegrationConstants.UNIT)).addTextNode("EAU");
	//		LOG.info(" unit " + unit);
	//
	//		LOG.info("End Inside returnData ");
	//
	//	}


	public static void main(final String[] args)
	{
		LOG.info("inside main method");

		//		final ReturnsRequestDTO dto = new ReturnsRequestDTO();
		//		dto.setServiceName("Request");
		//
		//		dto.setSapAccountCode("sapAcc001");
		//		dto.setPurchaseOrderNumber("PO420");
		//		dto.setOriginalOrderNumber("OAN9211");
		//		dto.setAccountNumber("AccNo555");
		//
		//		//dto.setBrandPart("AMG6197");
		//		//dto.setQuantity("1");
		//		//	dto.setUnit("pieces");
		//
		//		//dto.setReturnReason("DAMAGED");
		//
		//
		//		final ReturnsHelper helperclas = new ReturnsHelper();
		//		try
		//		{
		//			helperclas.sendSOAPMessage(dto);
		//		}
		//		catch (final Exception e)
		//		{
		//			LOG.error(e.getMessage());
		//		}
	}


}

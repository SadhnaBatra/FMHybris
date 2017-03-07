/**
 * 
 */
package com.fm.falcon.webservices.soap.helper;

import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.fm.falcon.webservices.constants.FMLoyaltyHistoryIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.CRMResponseDTO;
import com.fm.falcon.webservices.dto.PromoCodeRequestDTO;
import com.fm.falcon.webservices.dto.PromoCodeResponseDTO;
import com.fm.falcon.webservices.dto.PromoCodeResponseFault;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;




/**
 * @author SI279688
 * 
 */
public class PromoCodeHelper extends SoapMessageCreator
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
		//this.authenticateLoyaltyHistoryRequest();
		final SOAPBodyElement userrequest = createPromoCodeSoapBody(loyaltyHistoryMessage,
				FMLoyaltyHistoryIntegrationConstants.URN, FMLoyaltyHistoryIntegrationConstants.NAME_SPACE,
				crmRequestDTO.getServiceName());
		createChildElements(userrequest, (PromoCodeRequestDTO) crmRequestDTO);
		loyaltyHistoryMessage.saveChanges();
		final SOAPBody response = sendSoapRequest(loyaltyHistoryMessage, Config.getParameter("promoCodeUrl"),Config.getParameter("loyaltyHistoryUID"), Config.getParameter("loyaltyHistoryPWD"));
		return parseResponse(response);

	}

	/**
	 * @param response
	 * @return
	 */
	private PromoCodeResponseDTO parseResponse(final SOAPBody response)
	{

		final PromoCodeResponseDTO responseDTO = new PromoCodeResponseDTO();


		/*
		 * final List<PromoCodeResponseFault> faults = new ArrayList<PromoCodeResponseFault>();
		 * 
		 * final NodeList PromoCodeResponseFault = response.getElementsByTagName("FaultDetail"); for (int i = 0; i <
		 * PromoCodeResponseFault.getLength(); i++) { final PromoCodeResponseFault fault = new PromoCodeResponseFault();
		 * final Element element = (Element) PromoCodeResponseFault.item(i);
		 * fault.setSeverity(element.getElementsByTagName("Severity").item(0).getTextContent());
		 * fault.setSeverityText(element.getElementsByTagName("Text").item(0).getTextContent());
		 * fault.setCode(element.getElementsByTagName("Code").item(0).getTextContent()); faults.add(fault); }
		 * responseDTO.setFault(faults);
		 */
		if (response.getElementsByTagName("Severity").item(0) != null)
		{
			responseDTO.setSeverity(response.getElementsByTagName("Severity").item(0).getTextContent());
		}
		if (response.getElementsByTagName("Text").item(0) != null)
		{
			responseDTO.setSeverityText(response.getElementsByTagName("Text").item(0).getTextContent());
		}
		if (response.getElementsByTagName("Code").item(0) != null)
		{
			responseDTO.setErrorCode(response.getElementsByTagName("Code").item(0).getTextContent());
		}
		return responseDTO;
	}

	/**
	 * @param userrequest
	 * @param crmRequestDTO
	 * @throws SOAPException
	 */
	private void createChildElements(final SOAPBodyElement bodyElement, final PromoCodeRequestDTO crmRequestDTO)
			throws SOAPException
	{
		System.out.println("bodyElement::::::: " + bodyElement);
		//final SOAPElement vinRequest = bodyElement.addChildElement("MT_Hybris_LoyaltyPointsHistory_Request");
		final SOAPElement soap_Element = bodyElement.addChildElement(createQname("Loyalty"));
		if (crmRequestDTO.getPromoCode() != null)
		{
			soap_Element.addChildElement(createQname("PromoCode")).addTextNode(crmRequestDTO.getPromoCode());
		}
		if (crmRequestDTO.getMembershipID() != null)
		{
			soap_Element.addChildElement(createQname("MembershipID")).addTextNode(crmRequestDTO.getContactPersonId());
		}
		if (crmRequestDTO.getContactPersonId() != null)
		{
			soap_Element.addChildElement(createQname("ContactPersonId")).addTextNode(crmRequestDTO.getMembershipID());
		}
		if (crmRequestDTO.getProspectID() != null)
		{
			soap_Element.addChildElement(createQname("ProspectID")).addTextNode(crmRequestDTO.getProspectID());
		}

	}
}

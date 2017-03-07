/**
 * 
 */
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

import com.fm.falcon.webservices.constants.FMLoyaltyHistoryIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.CRMResponseDTO;
import com.fm.falcon.webservices.dto.LoyaltyOrderConfirmRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyOrderConfirmResponseDTO;
import com.fm.falcon.webservices.dto.LoyaltyOrderRedemptionProductInfobean;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;


/**
 * @author SI279688
 * 
 */
public class LoyaltyOrderConfirmHelper extends SoapMessageCreator
{
	private static final Logger LOG = Logger.getLogger(LoyaltyOrderConfirmHelper.class);

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
		final SOAPMessage loyaltyOrderRedemptionMessage = createSoapMessage();
		this.authenticateLoyaltyHistoryRequest();
		final SOAPBodyElement userrequest = createLoyaltyOrderConfirmSoapBody(loyaltyOrderRedemptionMessage,
				FMLoyaltyHistoryIntegrationConstants.URN, FMLoyaltyHistoryIntegrationConstants.NAME_SPACE,
				crmRequestDTO.getServiceName());
		createChildElements(userrequest, (LoyaltyOrderConfirmRequestDTO) crmRequestDTO);
		loyaltyOrderRedemptionMessage.saveChanges();
		final SOAPBody response = sendSoapRequest(loyaltyOrderRedemptionMessage, Config.getParameter("loyaltyOrderRedumtionURL"));
		return parseResponse(response);

	}

	/**
	 * @param response
	 * @return
	 */
	private LoyaltyOrderConfirmResponseDTO parseResponse(final SOAPBody response)
	{
		final LoyaltyOrderConfirmResponseDTO result = new LoyaltyOrderConfirmResponseDTO();
		if (response.getElementsByTagName("OBJECT_ID").item(0) != null)
		{
			result.setObj_Id(response.getElementsByTagName("OBJECT_ID").item(0).getTextContent());
		}
		if (response.getElementsByTagName("Description").item(0) != null)
		{
			result.setDesc(response.getElementsByTagName("Description").item(0).getTextContent());
		}
		if (response.getElementsByTagName("FailureReason").item(0) != null)
		{
			result.setFailureReason(response.getElementsByTagName("FailureReason").item(0).getTextContent());
		}
		return result;
	}

	/**
	 * @param userrequest
	 * @param crmRequestDTO
	 * @throws SOAPException
	 */
	private void createChildElements(final SOAPBodyElement bodyElement, final LoyaltyOrderConfirmRequestDTO crmRequestDTO)
			throws SOAPException
	{
		final SOAPElement soap_Element = bodyElement.addChildElement(createQname("REDUMPTIONDATA"));
		if (crmRequestDTO.getMemberShipId() != null)
		{
			soap_Element.addChildElement(createQname("Membership_ID")).addTextNode(crmRequestDTO.getMemberShipId());
		}
		if (crmRequestDTO.getSapAccountCode() != null)
		{
			soap_Element.addChildElement(createQname("SapAccountcode")).addTextNode(crmRequestDTO.getSapAccountCode());
		}
		if (crmRequestDTO.getSapAccountCode() != null)
		{
			soap_Element.addChildElement(createQname("SapAccount_code")).addTextNode(crmRequestDTO.getSapAccountCode());
		}
		if (crmRequestDTO.getSapAccCodeAddress1() != null)
		{
			soap_Element.addChildElement(createQname("SapAccountCode_Address1")).addTextNode(crmRequestDTO.getSapAccCodeAddress1());
		}
		if (crmRequestDTO.getSapAccCodeAddress2() != null)
		{
			soap_Element.addChildElement(createQname("SapAccountCode_Address2")).addTextNode(crmRequestDTO.getSapAccCodeAddress2());
		}
		if (crmRequestDTO.getSapAccCodeCity() != null)
		{
			soap_Element.addChildElement(createQname("SapAccountCode_City")).addTextNode(crmRequestDTO.getSapAccCodeCity());
		}
		if (crmRequestDTO.getSapAccCodeState() != null)
		{
			soap_Element.addChildElement(createQname("SapAccountCode_State")).addTextNode(crmRequestDTO.getSapAccCodeState());
		}
		if (crmRequestDTO.getSapAccCodeCountry() != null)
		{
			soap_Element.addChildElement(createQname("SapAccountCode_Country")).addTextNode(crmRequestDTO.getSapAccCodeCountry());
		}
		if (crmRequestDTO.getSapAccCodeZipCode() != null)
		{
			soap_Element.addChildElement(createQname("SapAccountCode_ZipCode")).addTextNode(crmRequestDTO.getSapAccCodeZipCode());
		}
		final List<LoyaltyOrderRedemptionProductInfobean> productInfo = crmRequestDTO.getProductInfo();
		for (final LoyaltyOrderRedemptionProductInfobean product : productInfo)
		{
			final SOAPElement soap_Element_ProductInfo = soap_Element.addChildElement(createQname("ProductInfo"));
			if (product.getBrandCode() != null)
			{
				soap_Element_ProductInfo.addChildElement(createQname("Brand_code")).addTextNode(product.getBrandCode());
			}
			if (product.getQty() != null)
			{
				soap_Element_ProductInfo.addChildElement(createQname("Quantity")).addTextNode(product.getQty());
			}
			if (product.getPointsRedeemed() != null)
			{
				soap_Element_ProductInfo.addChildElement(createQname("Points_Redemeed")).addTextNode(product.getPointsRedeemed());
			}
		}
	}
}

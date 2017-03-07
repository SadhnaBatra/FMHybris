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

import com.fm.falcon.webservices.constants.FMIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.CRMResponseDTO;
import com.fm.falcon.webservices.dto.LoyaltySurveyRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltySurveyResponseDTO;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;


/**
 * @author JA324889
 * 
 */
public class LoyaltySurveyHelper extends SoapMessageCreator
{

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
		final SOAPMessage surveyMsg = createSoapMessage();
		this.authenticategCRMRequest();
		final SOAPBodyElement surveyRequest = createLoyaltySurveySoapBody(surveyMsg, FMIntegrationConstants.SURVEYURN,
				FMIntegrationConstants.SURVEYNAMESPACE, crmRequestDTO.getServiceName());

		createChildElements(surveyRequest, (LoyaltySurveyRequestDTO) crmRequestDTO);
		surveyMsg.saveChanges();

		final SOAPBody response = sendSoapRequest(surveyMsg, Config.getParameter("surveyEndPointURL"));
		return parseResponse(response);
	}

	private void createChildElements(final SOAPBodyElement bodyElement, final LoyaltySurveyRequestDTO surveyRequestDTO)
			throws SOAPException
	{
		final SOAPElement soap_Element = bodyElement.addChildElement(createQname("SurveyReq"));


		if (surveyRequestDTO.getProspectID() != null)
		{
			soap_Element.addChildElement(createQname(FMIntegrationConstants.SURVEYPROSPECTID)).addTextNode(
					surveyRequestDTO.getProspectID());
		}
		if (surveyRequestDTO.getContactPersonID() != null)
		{
			soap_Element.addChildElement(createQname(FMIntegrationConstants.SURVEYCONTACTID)).addTextNode(
					surveyRequestDTO.getContactPersonID());
		}
		if (null!=surveyRequestDTO.getUniqueID() && surveyRequestDTO.getUniqueID().size() > 0)
		{
			final String uids = surveyRequestDTO.getUniqueID().toString().replace('[', ' ');
			final String uids2 = uids.replace(']', ' ');
			final String[] uids3 = uids2.trim().split(",");
			for (int i = 0; i < uids3.length; i++)
			{
				soap_Element.addChildElement(createQname(FMIntegrationConstants.SURVEYUNIQUEID)).addTextNode(uids3[i].trim());
			}
		}

if (surveyRequestDTO.getProductReviewFlag() != null)
		{
			soap_Element.addChildElement(createQname(FMIntegrationConstants.REVIEWFLAG)).addTextNode(
					surveyRequestDTO.getProductReviewFlag());
		}
		//FAL - 90 - Social Media changes
		if (surveyRequestDTO.getSocialMediaFlag() != null)
		{
			soap_Element.addChildElement(createQname(FMIntegrationConstants.SOCIALMEDIAFLAG)).addTextNode(
					surveyRequestDTO.getSocialMediaFlag());
		}
		if (surveyRequestDTO.getMembershipID() != null)
		{
			soap_Element.addChildElement(createQname(FMIntegrationConstants.MEMBERSHIPID)).addTextNode(
					surveyRequestDTO.getMembershipID());
		}	
}

	/**
	 * @param response
	 * @return
	 */
	private LoyaltySurveyResponseDTO parseResponse(final SOAPBody response)
	{
		final LoyaltySurveyResponseDTO result = new LoyaltySurveyResponseDTO();

		if (response.getElementsByTagName("Status").item(0) != null)
		{
			result.setStatus(response.getElementsByTagName("Status").item(0).getTextContent());
		}
		if (response.getElementsByTagName("UniqueID").item(0) != null)
		{
			final List<String> uniqueId = new ArrayList<String>();
			final int count = response.getElementsByTagName("UniqueID").getLength();
			if (count > 0)
			{
				for (int i = 0; i < count; i++)
				{
					uniqueId.add(response.getElementsByTagName("UniqueID").item(i).getTextContent());
				}
			}
			result.setUniqueId(uniqueId);
		}
		return result;
	}
}

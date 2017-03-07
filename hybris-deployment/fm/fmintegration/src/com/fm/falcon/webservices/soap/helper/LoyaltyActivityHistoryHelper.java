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
import com.fm.falcon.webservices.dto.LoyaltyHistoryBean;
import com.fm.falcon.webservices.dto.LoyaltyHistoryRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyHistoryResponseDTO;
import com.fm.falcon.webservices.dto.LoyaltyItemDetailsList;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;




/**
 * @author SI279688
 * 
 */
public class LoyaltyActivityHistoryHelper extends SoapMessageCreator
{
	private static final Logger LOG = Logger.getLogger(LoyaltyActivityHistoryHelper.class);

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

		final LoyaltyHistoryResponseDTO result = new LoyaltyHistoryResponseDTO();
LOG.info("inside new helper");
		/*
		 * if (response.getElementsByTagName("Balance_Points").item(0) != null) {
		 */

		if (response != null)
		{

			final NodeList nodes = response.getElementsByTagName("Records");
			final List<LoyaltyHistoryBean> itemDetailsList1 = new ArrayList<LoyaltyHistoryBean>();
			final List<LoyaltyHistoryBean> itemDetailsList2 = new ArrayList<LoyaltyHistoryBean>();
			final int items1 = nodes.getLength();
			for (int je = 0; je < items1; je++)
			{

				final LoyaltyHistoryBean bean = new LoyaltyHistoryBean();

				final Element element = (Element) nodes.item(je);


				if (response.getElementsByTagName("Activity_ID").item(je) != null)
				{
					LOG.info("ACTIVITY ID" + response.getElementsByTagName("Activity_ID").item(je).getTextContent());
					bean.setActivityId(response.getElementsByTagName("Activity_ID").item(je).getTextContent());
				}
				if (response.getElementsByTagName("PostingDate").item(je) != null)
				{
					LOG.info("Posting date " + response.getElementsByTagName("PostingDate").item(je).getTextContent());
					bean.setPostingDate(response.getElementsByTagName("PostingDate").item(je).getTextContent());
				}
				if (response.getElementsByTagName("URL").item(je) != null)
				{
					LOG.info("Tracking URL :: " + response.getElementsByTagName("URL").item(je).getTextContent());
					bean.setTrackingURL(response.getElementsByTagName("URL").item(je).getTextContent());
				}
				final NodeList itemDetails = element.getElementsByTagName("ItemDetails");
				final int items = itemDetails.getLength();
				LOG.info("items LENGTH IS " + items);
				final List<LoyaltyItemDetailsList> itemsList = new ArrayList<LoyaltyItemDetailsList>();
				for (int i = 0; i < items; i++)
				{

					final LoyaltyItemDetailsList itemBean = new LoyaltyItemDetailsList();
					final Element element2 = (Element) itemDetails.item(i);
					itemBean.setProductId(element2.getElementsByTagName("Product_ID").item(0).getTextContent());
					LOG.info("product id" + element2.getElementsByTagName("Product_ID").item(0).getTextContent());
					itemBean
							.setProductActivity(element2.getElementsByTagName("Product_Activity_Description").item(0).getTextContent());
					LOG.info("Product_Activity_Description"
							+ element2.getElementsByTagName("Product_Activity_Description").item(0).getTextContent());
					final String pts = element2.getElementsByTagName("Points").item(0).getTextContent();
					itemBean.setPoints(pts.substring(0, pts.indexOf('.')));
					LOG.info("Points" + element2.getElementsByTagName("Points").item(0).getTextContent());
					itemBean.setStatus(element2.getElementsByTagName("Status").item(0).getTextContent());
					LOG.info("Status" + element2.getElementsByTagName("Status").item(0).getTextContent());
					itemBean.setOrderId(element2.getElementsByTagName("OrderId").item(0).getTextContent());
					itemsList.add(itemBean);

					bean.setItemsDetail(itemsList);


				}

			if (items > 0)
			{

				if (bean.getItemsDetail().iterator().next().getPoints().startsWith("-", 0))
				{
					itemDetailsList1.add(bean);
					result.setResultRedemption(itemDetailsList1);
				}
				else
				{
					itemDetailsList2.add(bean);
					result.setResultActivity(itemDetailsList2);
				}
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

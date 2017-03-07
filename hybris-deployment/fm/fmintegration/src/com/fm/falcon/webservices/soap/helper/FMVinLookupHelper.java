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
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;

import com.fm.falcon.webservices.constants.FMVinLookupIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.VinLookupRequestDTO;
import com.fm.falcon.webservices.dto.VinLookupResponseDTO;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;


/**
 * @author SU276498 For VIN look up service
 * 
 */
public class FMVinLookupHelper extends SoapMessageCreator
{
	/**
	 * Logger class to log messages
	 */
	private static final Logger LOG = Logger.getLogger(FMVinLookupHelper.class);

	/**
	 * 
	 * @param SOAP
	 *           Request call
	 * @return CRM response transfer object
	 * @throws SOAPException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public VinLookupResponseDTO sendSOAPMessage(final CRMRequestDTO crmRequestDTO) throws UnsupportedOperationException,
			SOAPException, IOException, ClassNotFoundException
	{
		LOG.info("Inside sendSOAPMessage");

		final SOAPMessage vinLookupMsg = createSoapMessage();
		this.authenticateVinLookupRequest();
		final SOAPBodyElement callbackrequest = createSoapBody(vinLookupMsg, FMVinLookupIntegrationConstants.URN,
				FMVinLookupIntegrationConstants.NAME_SPACE, crmRequestDTO.getServiceName());

		createVinLookupChildElements(callbackrequest, (VinLookupRequestDTO) crmRequestDTO);
		vinLookupMsg.saveChanges();

		LOG.info(" vin Lookup WSDL URL:::::" + Config.getParameter("vinLookupEndPointURL"));
		final SOAPBody response = sendSoapRequest(vinLookupMsg, Config.getParameter("vinLookupEndPointURL"));
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
	private VinLookupResponseDTO parseResponse(final SOAPBody response)
	{
		LOG.info("Inside parseResponse");
		final VinLookupResponseDTO responseDTO = new VinLookupResponseDTO();
		responseDTO.setResponseCode(response.getElementsByTagName("returnCode").item(0).getTextContent());
		LOG.info("Inside response Code:::" + response.getElementsByTagName("returnCode").item(0).getTextContent());

		for (int i = 0; i < response.getElementsByTagName("fields").getLength(); i++)
		{
			final NamedNodeMap map = response.getElementsByTagName("fields").item(i).getAttributes();
			for (int j = 0; j < map.getLength(); j++)
			{
				final Attr node = (Attr) map.item(j);
				final String val = node.getValue();
				if ("ACES_YEAR_ID".equals(val))
				{
					responseDTO.setYear(response.getElementsByTagName("fields").item(i).getTextContent());
					LOG.info("Year::::::::::" + responseDTO.getYear());
				}
				if ("ACES_MAKE_NAME".equals(val))
				{
					responseDTO.setMake(response.getElementsByTagName("fields").item(i).getTextContent());
					LOG.info("make::::::::::" + responseDTO.getMake());
				}
				if ("ACES_MODEL_NAME".equals(val))
				{
					responseDTO.setModel(response.getElementsByTagName("fields").item(i).getTextContent());
					LOG.info("model::::::::::" + responseDTO.getModel());
				}
			}
		}

		return responseDTO;
	}


	private void vinLookupData(final SOAPBodyElement bodyElement, final VinLookupRequestDTO vinLookupRequestDTO)
			throws SOAPException
	{

		LOG.info(" Start Vin Lookup data ==>");
		final SOAPElement vinRequest = bodyElement.addChildElement(createQname("VinRequest"));
		LOG.info("vinRequest::::::: " + vinRequest);

		final SOAPElement vin = vinRequest.addChildElement(createQname(FMVinLookupIntegrationConstants.VIN)).addTextNode(
				vinLookupRequestDTO.getVin());
		LOG.info(" vin " + vin);

		LOG.info(" End VIN Lookup data ==>");



	}

	private void createVinLookupChildElements(final SOAPBodyElement bodyElement, final VinLookupRequestDTO vinLookupRequestDTO)
			throws SOAPException
	{
		LOG.info(" createChildElements ");
		vinLookupData(bodyElement, vinLookupRequestDTO);
	}

	public static void main(final String[] args)
	{
		LOG.info("inside main method");

		//		final VinLookupRequestDTO dto = new VinLookupRequestDTO();
		//		dto.setServiceName("Request");
		//
		//
		//		final FMVinLookupHelper helperclas = new FMVinLookupHelper();
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

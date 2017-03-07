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

import com.fm.falcon.webservices.constants.FMLicensePlateIntegrationConstants;
import com.fm.falcon.webservices.dto.CRMRequestDTO;
import com.fm.falcon.webservices.dto.LicensePlateRequestDTO;
import com.fm.falcon.webservices.dto.LicensePlateResponseDTO;
import com.fm.falcon.webservices.soap.common.SoapMessageCreator;


/**
 * @author SU276498 For Lincense Plate service
 * 
 */
public class FMLicensePlateHelper extends SoapMessageCreator
{
	/**
	 * Logger class to log messages
	 */
	private static final Logger LOG = Logger.getLogger(FMLicensePlateHelper.class);

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
	public LicensePlateResponseDTO sendSOAPMessage(final CRMRequestDTO crmRequestDTO) throws UnsupportedOperationException,
			SOAPException, IOException, ClassNotFoundException
	{
		LOG.info("Inside sendSOAPMessage");

		final SOAPMessage lookupMsg = createSoapMessage();
		this.authenticateLicensePlateRequest();
		final SOAPBodyElement callbackrequest = createSoapBodyLicensePlate(lookupMsg, FMLicensePlateIntegrationConstants.URN,
				FMLicensePlateIntegrationConstants.NAME_SPACE, crmRequestDTO.getServiceName());

		createLicensePlateChildElements(callbackrequest, (LicensePlateRequestDTO) crmRequestDTO);
		lookupMsg.saveChanges();

		LOG.info(" License Plate WSDL URL:::::" + Config.getParameter("licensePlateEndPointURL"));
		final SOAPBody response = sendSoapRequest(lookupMsg, Config.getParameter("licensePlateEndPointURL"));
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
	private LicensePlateResponseDTO parseResponse(final SOAPBody response)
	{
		LOG.info("Inside parseResponse");
		final LicensePlateResponseDTO responseDTO = new LicensePlateResponseDTO();
		responseDTO.setResponseCode(response.getElementsByTagName("errorCode").item(0).getTextContent());
		LOG.info("Inside response Code:::" + response.getElementsByTagName("errorCode").item(0).getTextContent());

		for (int i = 0; i < response.getElementsByTagName("field").getLength(); i++)
		{
			final NamedNodeMap map = response.getElementsByTagName("field").item(i).getAttributes();
			for (int j = 0; j < map.getLength(); j++)
			{
				final Attr node = (Attr) map.item(j);
				final String val = node.getValue();
				if ("modelYear".equals(val))
				{
					responseDTO.setYear(response.getElementsByTagName("field").item(i).getTextContent());
					LOG.info("Year::::::::::" + responseDTO.getYear());
				}
				if ("make".equals(val))
				{
					// responseDTO.setMake(response.getElementsByTagName("field").item(i).getTextContent());
					
					String vehicleMake = response.getElementsByTagName("field").item(i).getTextContent();
					String rawMake = vehicleMake;
					vehicleMake = scrubVehicleReturnValue(vehicleMake);
					
					String vehicleMakeCheck = Config.getParameter("vehicle.make." + vehicleMake.toLowerCase());			
					if (vehicleMakeCheck != null && !vehicleMakeCheck.isEmpty()) {
						vehicleMake = vehicleMakeCheck;
					}
					responseDTO.setMake(vehicleMake);
					
					LOG.info("make:::::::::: raw=" + rawMake + " scrubbed=" + responseDTO.getMake());
				}
				if ("modelDesc".equals(val))
				{
					String vehicleModel = response.getElementsByTagName("field").item(i).getTextContent();
					String rawModel = vehicleModel;
					vehicleModel = scrubVehicleReturnValue(vehicleModel);
					
					String vehicleModelCheck = Config.getParameter("vehicle.model." + vehicleModel.toLowerCase());			
					if (vehicleModelCheck != null && !vehicleModelCheck.isEmpty()) {
						vehicleModel = vehicleModelCheck;
					}					
					responseDTO.setModel(vehicleModel);
					
					LOG.info("model:::::::::: raw:" + rawModel + " scrubbed=" + responseDTO.getModel());
				}
			}
		}


		return responseDTO;
	}

	private String scrubVehicleReturnValue(String vehicleMakeOrModel) {
		
		if (vehicleMakeOrModel == null || vehicleMakeOrModel.isEmpty()) return vehicleMakeOrModel;
		
		String scrubbedValue = vehicleMakeOrModel.toLowerCase();
		scrubbedValue = scrubbedValue.substring(0, 1).toUpperCase() + scrubbedValue.substring(1);
		scrubbedValue = scrubbedValue.replaceAll(" ", ".");
		
		LOG.info("scrubbedValue::::::::::" + scrubbedValue);
		
		return scrubbedValue;
	}

	private void licensePlateData(final SOAPBodyElement bodyElement, final LicensePlateRequestDTO licensePlateRequestDTO)
			throws SOAPException
	{

		LOG.info(" Start License Plate Service data ==>");
		final SOAPElement parameter = bodyElement.addChildElement(createQname("parameter"));
		LOG.info(" parameter " + parameter);
		final SOAPElement processingType = parameter.addChildElement(
				createQname(FMLicensePlateIntegrationConstants.PROCESSING_TYPE)).addTextNode(
				licensePlateRequestDTO.getProcessingType());
		LOG.info(" processingType " + processingType);
		final SOAPElement input = parameter.addChildElement(createQname(FMLicensePlateIntegrationConstants.INPUT));
		final SOAPElement quoteBackId = input.addChildElement(createQname(FMLicensePlateIntegrationConstants.QUOTEBACKID))
				.addTextNode(licensePlateRequestDTO.getQuotebackId());
		LOG.info(" quoteBackId " + quoteBackId);
		final SOAPElement permissibleUsage = input
				.addChildElement(createQname(FMLicensePlateIntegrationConstants.PERMISSIBLEUSAGE)).addTextNode(
						licensePlateRequestDTO.getPermissibleUsage());
		LOG.info(" permissibleUsage " + permissibleUsage);
		final SOAPElement vehicle = input.addChildElement(createQname(FMLicensePlateIntegrationConstants.VEHICLE));
		LOG.info(" vehicle " + vehicle);
		final SOAPElement licensePlate = vehicle.addChildElement(createQname("field"));
		licensePlate.setAttribute("name", "licensePlate");
		licensePlate.addTextNode(licensePlateRequestDTO.getLicensePlate());

		final SOAPElement licenseState = vehicle.addChildElement(createQname("field"));
		licenseState.setAttribute("name", "licenseState");
		licenseState.addTextNode(licensePlateRequestDTO.getState());


		LOG.info(" End License Plate data ==>");



	}

	private void createLicensePlateChildElements(final SOAPBodyElement bodyElement,
			final LicensePlateRequestDTO licensePlateRequestDTO) throws SOAPException
	{
		LOG.info(" createChildElements ");
		licensePlateData(bodyElement, licensePlateRequestDTO);
	}

	public static void main(final String[] args)
	{
		LOG.info("inside main method");
		//
		//		final LicensePlateRequestDTO dto = new LicensePlateRequestDTO();
		//		dto.setServiceName("Request");
		//
		//
		//		final FMLicensePlateHelper helperclas = new FMLicensePlateHelper();
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

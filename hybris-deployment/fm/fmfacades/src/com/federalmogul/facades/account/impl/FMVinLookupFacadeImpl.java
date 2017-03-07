/**
 * 
 */
package com.federalmogul.facades.account.impl;

import java.io.IOException;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.facades.account.FMVinLookupFacade;
import com.federalmogul.facades.search.impl.DefaultFMFitmentSearchFacade;
import com.fm.falcon.webservices.dto.LicensePlateRequestDTO;
import com.fm.falcon.webservices.dto.LicensePlateResponseDTO;
import com.fm.falcon.webservices.dto.VinLookupRequestDTO;
import com.fm.falcon.webservices.dto.VinLookupResponseDTO;
import com.fm.falcon.webservices.soap.helper.FMLicensePlateHelper;
import com.fm.falcon.webservices.soap.helper.FMVinLookupHelper;


/**
 * @author SU276498
 * 
 */
public class FMVinLookupFacadeImpl implements FMVinLookupFacade
{

	private static final Logger LOG = Logger.getLogger(FMVinLookupFacadeImpl.class);

	@Autowired
	private DefaultFMFitmentSearchFacade defaultFMFitmentSearchFacade;

	@Override
	public VinLookupResponseDTO vinLookup(final String vin) throws UnsupportedOperationException, ClassNotFoundException,
			SOAPException, IOException
	{
		final VinLookupResponseDTO vinLookupResponseDTO = getVin(vin);
		return vinLookupResponseDTO;
	}


	@Override
	public LicensePlateResponseDTO licensePlate(final String licensePlate, final String state)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		final LicensePlateResponseDTO licensePlateResponseDTO = getLicensePlate(licensePlate, state);
		// Mahaveer To fetch the value for year make model from DB
		final List<String> makes = defaultFMFitmentSearchFacade.getMakeData(licensePlateResponseDTO.getYear(),
				"Passenger Car Light Truck");
		for (final String make : makes)
		{
			if (make.toLowerCase().contains(licensePlateResponseDTO.getMake().toLowerCase()))
			{
				licensePlateResponseDTO.setMake(make);
				break;
			}
		}

		final List<String> models = defaultFMFitmentSearchFacade.getModelData(licensePlateResponseDTO.getYear(),
				licensePlateResponseDTO.getMake(), "Passenger Car Light Truck");
		for (final String model : models)
		{
			if (model.toLowerCase().contains(licensePlateResponseDTO.getModel().toLowerCase()))
			{
				licensePlateResponseDTO.setModel(model);
				break;
			}
		}

		return licensePlateResponseDTO;

	}

	/**
	 ** 
	 * VIN Service Helper Call
	 * 
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */


	public VinLookupResponseDTO getVin(final String vin) throws UnsupportedOperationException, ClassNotFoundException,
			SOAPException, IOException
	{
		LOG.info("Emntered getVin In FacadeImpl");

		final FMVinLookupHelper fmVinLookupHelper = new FMVinLookupHelper();
		final VinLookupResponseDTO vinLookupResponseDTO = fmVinLookupHelper.sendSOAPMessage(convertDataToDTO(vin));
		if ("0".equals(vinLookupResponseDTO.getResponseCode()))
		{
			LOG.info("Vin Lookup service SUCCESS:::::::::::::::::::::::::::::");

		}
		return vinLookupResponseDTO;
	}

	public LicensePlateResponseDTO getLicensePlate(final String licensePlate, final String state)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		LOG.info("Emntered getLicensePlate In FacadeImpl");

		final FMLicensePlateHelper fmLicensePlateHelper = new FMLicensePlateHelper();
		final LicensePlateResponseDTO licensePlateResponseDTO = fmLicensePlateHelper.sendSOAPMessage(convertLicenseDataToDTO(
				licensePlate, state));
		if ("".equals(licensePlateResponseDTO.getResponseCode()))
		{
			LOG.info("License Plate service SUCCESS:::::::::::::::::::::::::::::");

		}
		return licensePlateResponseDTO;
	}

	/**
	 * Set VIN SERVICE Details to DTO
	 * 
	 * @param vin
	 * @return
	 */
	private VinLookupRequestDTO convertDataToDTO(final String vin)
	{
		final VinLookupRequestDTO reqDTO = new VinLookupRequestDTO();
		reqDTO.setServiceName("decodeVin");
		reqDTO.setVin(vin);

		return reqDTO;
	}

	private LicensePlateRequestDTO convertLicenseDataToDTO(final String licensePlate, final String state)
	{
		final LicensePlateRequestDTO reqDTO = new LicensePlateRequestDTO();
		reqDTO.setServiceName("get");
		reqDTO.setLicensePlate(licensePlate);
		reqDTO.setState(state);
		reqDTO.setPermissibleUsage("PV");
		reqDTO.setProcessingType("A");
		reqDTO.setQuotebackId("Plan A Test");

		return reqDTO;
	}


}

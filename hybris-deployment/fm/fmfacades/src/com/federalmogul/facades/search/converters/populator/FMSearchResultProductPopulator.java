/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */

package com.federalmogul.facades.search.converters.populator;


import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.federalmogul.core.product.PartService;
import com.federalmogul.facades.product.data.FMCorporateData;
import com.federalmogul.facades.product.data.FMFitmentData;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMFitmentModel;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * Converter implementation for {@link de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData} as
 * source and {@link de.hybris.platform.commercefacades.product.data.ProductData} as target type. Adds all the
 * information related to fmpart products.
 */
public class FMSearchResultProductPopulator extends SearchResultProductPopulator
{
	private Converter<FMFitmentModel, FMFitmentData> fitmentConverter;
	private PartService partService;
	private static final Logger LOG = Logger.getLogger(FMSearchResultProductPopulator.class);

	/**
	 * @return the fitmentConverter
	 */
	public Converter<FMFitmentModel, FMFitmentData> getFitmentConverter()
	{
		return fitmentConverter;
	}

	/**
	 * @return the partService
	 */
	public PartService getPartService()
	{
		return partService;
	}

	/**
	 * @param partService
	 *           the partService to set
	 */
	public void setPartService(final PartService partService)
	{
		this.partService = partService;
	}

	/**
	 * @param fitmentConverter
	 *           the fitmentConverter to set
	 */
	public void setFitmentConverter(final Converter<FMFitmentModel, FMFitmentData> fitmentConverter)
	{
		this.fitmentConverter = fitmentConverter;
	}



	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		super.populate(source, target);
		//populateFitment(source, target);
		final int loyaltyPoints = (null != this.<Integer> getValue(source, "loyaltyPoints")) ? this.<Integer> getValue(source,
				"loyaltyPoints") : 0;

		target.setLoyaltyPoints(String.valueOf(loyaltyPoints));
		//for review count
		target.setNumberOfReviews(this.<Integer> getValue(source, "reviewCount"));
		populateSolrFitment(source, target);

	}

	protected void populateSolrFitment(final SearchResultValueData source, final ProductData target)
	{
		final StringBuffer fitmantValue = new StringBuffer();
		final String code = this.<String> getValue(source, "code");
		try
		{
			final FMPartModel partModel = getPartService().getPartForCode(code);
			if (partModel != null)
			{
				populateBrand(partModel, target);
			}
			final ArrayList<String> solrFitments = this.<ArrayList<String>> getValue(source, "ymmtext");
			if (solrFitments != null && !solrFitments.isEmpty())
			{
				for (final String fits : solrFitments)
				{
					fitmantValue.append(fits);
				}
			}
			final String[] fitmentRecords = getFitmentRecords(fitmantValue.toString());
			final List<FMFitmentData> ymmDisplays = new ArrayList<FMFitmentData>();
			for (final String fitmentRecord : fitmentRecords)
			{
				if (fitmentRecord != null && !fitmentRecord.isEmpty())
				{
					ymmDisplays.add(processFitmentRecord(fitmentRecord));
				}
			}

			target.setPartFitments(ymmDisplays);
		}
		catch (final UnknownIdentifierException ex)
		{
			target.setPartFitments(null);
			target.setBrands(null);
			//LOG.error(ex.getMessage());
		}

	}

	private FMFitmentData processFitmentRecord(final String fitmentRecord)
	{
		final String[] attributesWithHeader = fitmentRecord.split("\\|");

		final FMFitmentData fitment = new FMFitmentData();

		for (final String attributeWithHeader : attributesWithHeader)
		{
			if (attributeWithHeader != null && !attributeWithHeader.isEmpty())
			{
				if (attributeWithHeader.startsWith("ymm"))
				{
					final String[] ymmCodeStringWithHeaderTokens = attributeWithHeader.split("\\:");
					if (ymmCodeStringWithHeaderTokens.length >= 2)
					{
						final String[] ymmValues = ymmCodeStringWithHeaderTokens[1].split("\\$");
						fitment.setYear(ymmValues[0]);
						fitment.setMake(ymmValues[1]);
						fitment.setModel(ymmValues[2]);
					}
					else
					{
						fitment.setYear("");
						fitment.setMake("");
						fitment.setModel("");
					}
				}
				else
				{
					final String[] ymmAttributeWithHeaderTokens = attributeWithHeader.split("\\:");

					if (ymmAttributeWithHeaderTokens.length == 2 && ymmAttributeWithHeaderTokens[1] != null
							&& !ymmAttributeWithHeaderTokens[1].isEmpty() && !"null".equals(ymmAttributeWithHeaderTokens[1])
							&& ymmAttributeWithHeaderTokens[0] != null)
					{

						if ("product".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setProducts(ymmAttributeWithHeaderTokens[1]);
						}

						if ("submodel".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setSubmodel(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineBase".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineBase(ymmAttributeWithHeaderTokens[1]);
						}
						if ("appQty".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setAppQuantity(ymmAttributeWithHeaderTokens[1]);
						}


						if ("driveType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setDriveType(ymmAttributeWithHeaderTokens[1]);
						}

						if ("driveType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setDriveType(ymmAttributeWithHeaderTokens[1]);
						}

						if ("engineDesignation".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineDesignation(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineVIN".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineVIN(ymmAttributeWithHeaderTokens[1]);
						}
						if ("aspiration".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setAspiration(ymmAttributeWithHeaderTokens[1]);
						}
						if ("cylinderHeadType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setCylinderHeadType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("fuelType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setFuelType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("ignitionSystemType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setIgnitionSystemType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineVersion".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineVersion(ymmAttributeWithHeaderTokens[1]);
						}
						if ("fuelDeliveryType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setFuelDeliveryType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("fuelDeliverySubType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setFuelDeliverySubType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("fuelSystemControlType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setFuelSystemControlType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("fuelSystemDesign".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setFuelSystemDesign(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineMfr".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineMfr(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineValves".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineValves(ymmAttributeWithHeaderTokens[1]);
						}
						if ("powerOutput".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setPowerOutput(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineArrangementNumber".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineArrangementNumber(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineSerialNumber".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineSerialNumber(ymmAttributeWithHeaderTokens[1]);
						}
						if ("engineCPLNumber".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setEngineCPLNumber(ymmAttributeWithHeaderTokens[1]);
						}
						if ("vehicleType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setVehicleType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("region".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setRegion(ymmAttributeWithHeaderTokens[1]);
						}
						if ("bodyNumDoors".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setBodyNumDoors(ymmAttributeWithHeaderTokens[1]);
						}
						if ("bodyType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setBodyType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("bedLength".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setBedLength(ymmAttributeWithHeaderTokens[1]);
						}
						if ("bedType".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setBedType(ymmAttributeWithHeaderTokens[1]);
						}
						if ("mfrBodyCode".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setMfrBodyCode(ymmAttributeWithHeaderTokens[1]);
						}
						if ("wheelBase".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setWheelBase(ymmAttributeWithHeaderTokens[1]);
						}
						if ("position".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setPosition(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment1".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment1(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment2".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment2(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment3".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment3(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment4".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment4(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment5".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment5(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment6".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment6(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment7".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment7(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment8".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment8(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment9".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment9(ymmAttributeWithHeaderTokens[1]);
						}
						if ("comment10".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setComment10(ymmAttributeWithHeaderTokens[1]);
						}




						if ("applicationNote1".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote1(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote2".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote2(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote3".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote3(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote4".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote4(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote5".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote5(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote6".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote6(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote7".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote7(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote8".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote8(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote9".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote9(ymmAttributeWithHeaderTokens[1]);
						}
						if ("applicationNote10".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setApplicationNote10(ymmAttributeWithHeaderTokens[1]);
						}



						if ("interchangeNote1".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote1(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote2".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote2(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote3".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote3(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote4".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote4(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote5".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote5(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote6".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote6(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote7".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote7(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote8".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote8(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote9".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote9(ymmAttributeWithHeaderTokens[1]);
						}
						if ("interchangeNote10".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setInterchangeNote10(ymmAttributeWithHeaderTokens[1]);
						}



						if ("partNumber".equalsIgnoreCase(ymmAttributeWithHeaderTokens[0]))
						{
							fitment.setPartNumber(ymmAttributeWithHeaderTokens[1]);
						}





					}
				}
			}
		}
		return fitment;
	}

	private String[] getFitmentRecords(final String fitmentDisplayText)
	{
		final String[] fitmentRecords = fitmentDisplayText.split("\\|\\|");

		return fitmentRecords;
	}

	protected void populateFitment(final SearchResultValueData source, final ProductData target)
	{

		final String code = this.<String> getValue(source, "code");
		final List<FMFitmentData> fitmentList = new ArrayList<FMFitmentData>();
		final List<String> fitmentcode = new ArrayList<String>();
		if (StringUtils.isNotEmpty(code))
		{
			try
			{
				final FMPartModel partModel = getPartService().getPartForCode(code);
				if (partModel != null)
				{

					populateBrand(partModel, target);
					final Collection<FMFitmentModel> fitmentModels = partModel.getFitments();
					if (fitmentModels != null && !fitmentModels.isEmpty())
					{
						for (final FMFitmentModel fitmentModel : fitmentModels)
						{
							if (!fitmentcode.contains(fitmentModel.getCode().toString()))
							{
								final FMFitmentData fmFitmentData = getFitmentConverter().convert(fitmentModel);
								fitmentcode.add(fmFitmentData.getCode().toString());
								fitmentList.add(fmFitmentData);
							}
						}
						target.setPartFitments(fitmentList);
					}
					else
					{
						target.setPartFitments(null);
					}
				}
			}
			catch (final UnknownIdentifierException ex)
			{
				target.setPartFitments(null);
				LOG.error(ex.getMessage());
			}
		}


	}

	protected void populateBrand(final FMPartModel source, final ProductData target)
	{

		try
		{
			final Collection<FMCorporateModel> corporates = source.getCorporate();
			final FMCorporateData brand = new FMCorporateData();
			if (corporates != null && !corporates.isEmpty())
			{
				for (final FMCorporateModel sources : corporates)
				{
					if (sources != null)
					{
						brand.setCorpcode(sources.getCorpcode() != null ? sources.getCorpcode().toString() : null);
						brand.setCorpid(sources.getCorpid() != null ? sources.getCorpid() : null);
						brand.setCorpname(sources.getCorpname() != null ? sources.getCorpname().toString() : null);
						brand.setCorptype(sources.getCorptype() != null ? sources.getCorptype().toString() : null);
						if (sources.getThumbnail() != null)
						{
							final ImageData brandImage = new ImageData();
							final MediaModel image = sources.getThumbnail();
							brandImage.setUrl(image.getURL());
							brandImage.setImageType(ImageDataType.PRIMARY);
							brandImage.setFormat("thumbnail");
							brand.setImage(brandImage);
						}
					}
				}
				target.setBrands(brand);

			}
			else
			{
				target.setBrands(null);
			}
			target.setRawPartNumber(source.getCode() != null ? source.getCode().toString() : null);
			target.setPartNumber(source.getPartNumber() != null ? source.getPartNumber().toString() : null);
		}
		catch (final UnknownIdentifierException ex)
		{
			target.setBrands(null);
			LOG.error(ex.getMessage());
		}
	}
}

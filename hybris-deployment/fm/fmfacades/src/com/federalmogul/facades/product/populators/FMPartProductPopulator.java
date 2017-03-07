/**
 * 
 */
package com.federalmogul.facades.product.populators;

import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.federalmogul.facades.product.data.FMCorporateData;
import com.federalmogul.facades.product.data.FMFitmentData;
import com.federalmogul.facades.product.data.FMPartProductData;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMFitmentModel;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
public class FMPartProductPopulator implements Populator<ProductModel, FMPartProductData>
{
	private Converter<FMFitmentModel, FMFitmentData> fitmentConverter;
	private static final Logger LOG = Logger.getLogger(FMPartProductPopulator.class);

	/**
	 * @return the fitmentConverter
	 */
	public Converter<FMFitmentModel, FMFitmentData> getFitmentConverter()
	{
		return fitmentConverter;
	}

	/**
	 * @param fitmentConverter
	 *           the fitmentConverter to set
	 */
	public void setFitmentConverter(final Converter<FMFitmentModel, FMFitmentData> fitmentConverter)
	{
		this.fitmentConverter = fitmentConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final ProductModel sourcePreCast, final FMPartProductData target) throws ConversionException
	{
		// YTODO Auto-generated method stub
		if (sourcePreCast == null)
		{
			throw new IllegalArgumentException("The provided source product model argument cannot be null");
		}
		if (target == null)
		{
			throw new IllegalArgumentException("The provided target product data argument cannot be null");
		}

		if (!(sourcePreCast instanceof FMPartModel))
		{
			return;
		}
		final FMPartModel source = (FMPartModel) sourcePreCast;
		target.setPartNumber(source.getPartNumber() != null ? source.getPartNumber().toString() : null);
		target.setRawPartNumber(source.getRawPartNumber() != null ? source.getRawPartNumber().toString() : null);
		target.setDigitalAssetURL(source.getDigitalAssetURL() != null ? source.getDigitalAssetURL().toString() : null);
		target.setCarLightTruck(source.getCarLightTruck());
		target.setWarrantydescription(source.getWarrantydescription());
		target.setWarranty(source.getWarranty());
		target.setFaqQestion1(source.getFaqQestion1());
		target.setFaqQestion2(source.getFaqQestion2());
		target.setFaqQestion3(source.getFaqQestion3());
		target.setFaqQestion4(source.getFaqQestion4());
		target.setFaqQestion5(source.getFaqQestion5());
		target.setFaqAnswer1(source.getFaqAnswer1());
		target.setFaqAnswer2(source.getFaqAnswer2());
		target.setFaqAnswer3(source.getFaqAnswer3());
		target.setFaqAnswer4(source.getFaqAnswer4());
		target.setFaqAnswer5(source.getFaqAnswer5());


		populateFitment(source, target);
		populateBrand(source, target);
	}

	protected void populateFitment(final FMPartModel source, final ProductData target)
	{

		final List<FMFitmentData> fitmentList = new ArrayList<FMFitmentData>();
		final List<String> fitmentcode = new ArrayList<String>();
		try
		{
			final Collection<FMFitmentModel> fitmentModels = source.getFitments();

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
		catch (final UnknownIdentifierException ex)
		{
			target.setPartFitments(null);
			LOG.error(ex.getMessage());
		}
	}

	protected void populateBrand(final FMPartModel source, final FMPartProductData target)
	{
		final List<FMCorporateData> brandList = new ArrayList<FMCorporateData>();
		try
		{
			final Collection<FMCorporateModel> corporates = source.getCorporate();

			if (corporates != null && !corporates.isEmpty())
			{
				for (final FMCorporateModel sources : corporates)
				{
					final FMCorporateData targets = new FMCorporateData();
					final ImageData corpImage = new ImageData();
					if (sources != null)
					{
						targets.setCorpcode(sources.getCorpcode() != null ? sources.getCorpcode().toString() : null);
						targets.setCorpid(sources.getCorpid() != null ? sources.getCorpid() : null);
						targets.setCorpname(sources.getCorpname() != null ? sources.getCorpname().toString() : null);
						targets.setCorptype(sources.getCorptype() != null ? sources.getCorptype().toString() : null);
						if (sources.getThumbnail() != null)
						{
							final MediaModel image = sources.getThumbnail();
							corpImage.setUrl(image.getURL());
							corpImage.setImageType(ImageDataType.PRIMARY);
							corpImage.setFormat("thumbnail");
							targets.setImage(corpImage);
						}
						target.setBrands(targets);
					}

					if (!brandList.contains(targets))
					{
						brandList.add(targets);
					}
				}
				target.setCorporate(brandList);
			}
			else
			{
				target.setCorporate(null);
			}
		}
		catch (final UnknownIdentifierException ex)
		{
			target.setCorporate(null);
			LOG.error(ex.getMessage());
		}
	}

	protected FMCorporateData converterBrand(final FMCorporateModel source, final FMCorporateData target)
	{
		if (source != null && source.getCorpcode() != null)
		{
			target.setCorpcode(source.getCorpcode() != null ? source.getCorpcode().toString() : null);
			target.setCorpid(source.getCorpid() != null ? source.getCorpid() : null);
			target.setCorpname(source.getCorpname() != null ? source.getCorpname().toString() : null);
			target.setCorptype(source.getCorptype() != null ? source.getCorptype().toString() : null);
		}
		return target;

	}
}

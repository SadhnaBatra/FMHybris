/**
 * 
 */
package com.federalmogul.facades.product.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.federalmogul.facades.product.data.FMPartProductData;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
public class FMParttProductPopulator implements Populator<ProductModel, FMPartProductData>
{
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

		target.setDigitalAssetURL(source.getDigitalAssetURL() != null ? source.getDigitalAssetURL().toString() : null);
		target.setCarLightTruck(source.getCarLightTruck());


	}

}

package com.federalmogul.facades.storefinder.populators;

import java.text.DecimalFormat;

import com.federalmogul.core.constants.FmCoreConstants;

import de.hybris.platform.basecommerce.enums.DistanceUnit;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.storefinder.data.PointOfServiceDistanceData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

public class FMPointOfServiceDistanceDataPopulator implements Populator<PointOfServiceDistanceData, PointOfServiceData>
{
	
	@Override
	public void populate(PointOfServiceDistanceData source,
			PointOfServiceData target) throws ConversionException {
		if (source != null)
		{
			DistanceUnit distanceUnit = null;
			Double distance= null;
			if(FmCoreConstants.KILOMETER.equalsIgnoreCase(source.getRadiusMeasurementType())){
				distanceUnit = DistanceUnit.KM;
				distance =  Double.valueOf(source.getDistanceKm());
			}else if(FmCoreConstants.MILES.trim().equalsIgnoreCase(source.getRadiusMeasurementType().trim())){
				distanceUnit = DistanceUnit.MILES;
				 distance = Double.valueOf(source.getDistanceKm() * 0.62137D);			
			}
			DecimalFormat distanceFormat = new DecimalFormat("###,###.#");
			String formattedDistance = distanceFormat.format(distance.doubleValue()) + " " + distanceUnit.name();
			target.setFormattedDistance(formattedDistance);
		}
		
	} 

}

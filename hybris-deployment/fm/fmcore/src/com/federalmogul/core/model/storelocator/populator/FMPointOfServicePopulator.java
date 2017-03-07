/**
 * 
 */
package com.federalmogul.core.model.storelocator.populator;

import java.util.ArrayList;
import java.util.List;

import com.federalmogul.core.model.BrandInformationModel;

import de.hybris.platform.commercefacades.storelocator.converters.populator.PointOfServicePopulator;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.storelocator.model.PointOfServiceModel;


/**
 * @author anapande
 * 
 */
public class FMPointOfServicePopulator extends PointOfServicePopulator
{

	private String brand;
	private String shopType;

	@Override
	public void populate(final PointOfServiceModel source, final PointOfServiceData target)
	{
		super.populate(source, target);
		target.setBrand(source.getBrand());
		target.setShopType(source.getShopType());
		target.setPreferredVendor(source.isPreferredVendor());
		target.setActive(source.isActive());
		List<String> brandList=null;
		if(source.getBrandInformation()!=null && source.getBrandInformation().size()>0){
			brandList = new ArrayList<String>();
			for(BrandInformationModel bim:source.getBrandInformation()){
				if(!brandList.contains(bim.getBrand())){
					brandList.add(bim.getBrand());
				}			
			}
		}
		target.setBrandList(brandList);
		if(source.getStore()!=null)
		{		
		   target.setScheduleAppointmentUrl(source.getStore().getScheduleAppointmentUrl());
		   target.setStoreWebsite(source.getStore().getStoreUrl());
		}   
	}


	public String getBrand()
	{
		return brand;
	}


	public void setBrand(final String brand)
	{
		this.brand = brand;
	}

	public String getShopType()
	{
		return shopType;
	}

	public void setShopType(final String shopType)
	{
		this.shopType = shopType;
	}
}

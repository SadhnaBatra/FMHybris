package com.federalmogul.facades.storefinder.populators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.federalmogul.core.model.BrandInformationModel;
import com.federalmogul.core.model.DistributorModel;
import com.federalmogul.facades.storelocator.data.DistributorData;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;



public class DistributorPopulator implements Populator<DistributorModel,DistributorData>{
	
	private Converter<AddressModel, AddressData> addressConverter;
	 
	public Converter<AddressModel, AddressData> getAddressConverter() {
		return addressConverter;
	}

	public void setAddressConverter(
			Converter<AddressModel, AddressData> addressConverter) {
		this.addressConverter = addressConverter;
	}



	@Override
	public void populate(DistributorModel source, DistributorData target)
			throws ConversionException {
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setDistributorName(source.getDistributorName());
		target.setCountryName(source.getCountry().getName());
		target.setStoreWebsite(source.getStoreWebsite());
		target.setEmail(source.getEmail());
		target.setOfficePhone(source.getPhone1());
		target.setSecondaryPhone(source.getPhone2());
		target.setCellPhone(source.getCellPhone());
		List<String> brands=new ArrayList<String>();
		if(source.getBrandInfoDistributor()!=null && source.getBrandInfoDistributor().size()>0){
			for(BrandInformationModel bim:source.getBrandInfoDistributor()){
				brands.add(bim.getBrand());
			}
		}
		target.setBrandList(brands);
		if(null!=source.getAddress())
		{
		  target.setAddress(getAddressConverter().convert(source.getAddress()));
		}
	}

	
	
	
}

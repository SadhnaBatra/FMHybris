package com.federalmogul.facades.storefinder.populators;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.federalmogul.core.model.BrandInformationModel;
import com.federalmogul.core.model.OnlineRetailerModel;
import com.federalmogul.facades.storelocator.data.OnlineRetailerData;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

public class OnlineRetailerPopulator implements Populator<OnlineRetailerModel , OnlineRetailerData> {
   
	private Converter<AddressModel, AddressData> addressConverter;
	public Converter<AddressModel, AddressData> getAddressConverter() {
		return addressConverter;
	}
	public void setAddressConverter(
			Converter<AddressModel, AddressData> addressConverter) {
		this.addressConverter = addressConverter;
	}
	@Override
	public void populate(OnlineRetailerModel source, OnlineRetailerData target)
			throws ConversionException {
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");
		target.setOnlineRetailerName(source.getOnlineRetailerName());
		target.setCountryName(source.getCountry().getName());
		//target.setRegionName(source.getRegion().getName());
		target.setStoreWebsite(source.getStoreWebsite());
		target.setEmail(source.getEmail());
		target.setOfficePhone(source.getPhone1());
		target.setSecondaryPhone(source.getPhone2());
		target.setCellPhone(source.getCellPhone());		
		List<String> brands=new ArrayList<String>();
		if(source.getBrandInfoOnlineRet()!=null && source.getBrandInfoOnlineRet().size()>0){
			for(BrandInformationModel bim:source.getBrandInfoOnlineRet()){
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

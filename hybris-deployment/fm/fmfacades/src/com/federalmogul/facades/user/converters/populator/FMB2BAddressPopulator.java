/**
 * 
 */
package com.federalmogul.facades.user.converters.populator;

import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.facades.address.data.FMB2bAddressData;


/**
 * @author SR279690
 * 
 */
public class FMB2BAddressPopulator implements Populator<AddressModel, FMB2bAddressData>
{
	private CommonI18NService commonI18NService;
	private FlexibleSearchService flexibleSearchService;


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final AddressModel source, final FMB2bAddressData target) throws ConversionException,
			UnknownIdentifierException
	{
		//Assert.notNull(source, "Parameter source cannot be null.");
		//Assert.notNull(FMB2bAddressData, "Parameter FMB2bAddressData cannot be null.");
		//LOG.info("inside populator");
		/*
		 * if (source.getTitleCode() != null) { final TitleModel title = new TitleModel();
		 * title.setCode(source.getTitleCode());
		 * FMB2bAddressData.setTitle(flexibleSearchService.getModelByExample(title)); }
		 */
		target.setId(source.getPk().toString());
		target.setFirstName(source.getFirstname());
		target.setLastName(source.getLastname());
		target.setCompanyName(source.getCompany());
		target.setLine1(source.getLine1());
		target.setLine2(source.getLine2());
		target.setTown(source.getTown());
		target.setPostalCode(source.getPostalcode());
		target.setPhone(source.getPhone1());
		target.setCompanyFax(source.getFax());
		//FMB2bAddressData.setUrl(source.getCompanyUrl());
		final CountryModel countryModel = source.getCountry();

		if (countryModel != null)
		{
			//String isoCode = null;
			final CountryData countryData = new CountryData();
			countryData.setIsocode(countryModel.getIsocode());
			//isoCode = countryModel.getIsocode();
			countryData.setName(countryModel.getName());
			target.setCountry(countryData);
		}

		final RegionModel regionModel = source.getRegion();
		if (regionModel != null)
		{
			final RegionData regionData = new RegionData();
			regionData.setIsocode(regionModel.getIsocode());
			regionData.setIsocodeShort(regionModel.getIsocodeShort());
			regionData.setName(regionModel.getName());
			if (regionModel.getCountry() != null)
			{
				regionData.setCountryIso(regionModel.getCountry().getIsocode());
			}
			target.setRegion(regionData);
		}

		/*
		 * if (source.getCountry() != null) { final String isocode = source.getCountry().getIsocode(); try { final
		 * CountryData countryData = new CountryData(); countryData.setIsocode(countryModel.getIsocode()); isoCode =
		 * countryModel.getIsocode(); countryData.setName(countryModel.getName()); target.setCountry(countryData); } catch
		 * (final UnknownIdentifierException e) { throw new ConversionException("No country with the code " + isocode +
		 * " found.", e); } catch (final AmbiguousIdentifierException e) { throw new
		 * ConversionException("More than one country with the code " + isocode + " found.", e); } }
		 * 
		 * if (source.getRegion() != null) { final String isocode = source.getRegion().getIsocode(); try { final
		 * RegionModel regionModel = getCommonI18NService().getRegion(
		 * getCommonI18NService().getCountry(source.getCountry().getIsocode()), isocode); target.setRegion(regionModel); }
		 * catch (final UnknownIdentifierException e) { throw new ConversionException("No region with the code " + isocode
		 * + " found.", e); } catch (final AmbiguousIdentifierException e) { throw new
		 * ConversionException("More than one region with the code " + isocode + " found.", e); } }
		 */
		/*
		 * FMB2bAddressData.setBillingAddress(Boolean.valueOf(source.isBillingAddress()));
		 * FMB2bAddressData.setShippingAddress(Boolean.valueOf(source.isShippingAddress()));
		 * FMB2bAddressData.setVisibleInAddressBook(Boolean.valueOf(source.isVisibleInAddressBook()));
		 */


	}

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	@Required
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	protected FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}

	@Required
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}


}

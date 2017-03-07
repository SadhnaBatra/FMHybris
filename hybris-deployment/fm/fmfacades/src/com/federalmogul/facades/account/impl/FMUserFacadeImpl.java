/**
 * 
 */
package com.federalmogul.facades.account.impl;

import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.impl.DefaultUserFacade;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.user.AddressModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.federalmogul.core.account.FMCustomerAccountService;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.account.FMUserFacade;


/**
 * @author su244261
 * 
 */
public class FMUserFacadeImpl extends DefaultUserFacade implements FMUserFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultUserFacade.class);

	@Resource
	private FMCustomerAccountService fmCustomerAccountService;

	@Override
	public List<AddressData> getAddressBook(final String userID)
	{
		// Get the current customer's addresses
		final Collection<AddressModel> addresses = getFmCustomerAccountService().getFMAddressBookEntries(
				(FMCustomerModel) getUserService().getUserForUID(userID));



		if (addresses != null && !addresses.isEmpty())
		{
			for (final AddressModel addressModel : addresses)
			{
				LOG.info("Address model first Name:::" + addressModel.getFirstname() + "Address model address:::"
						+ addressModel.getLine1() + addressModel.getLine2());
			}


			final Collection<CountryModel> deliveryCountries = getCommerceCommonI18NService().getAllCountries();

			final List<AddressData> result = new ArrayList<AddressData>();
			final AddressData defaultAddress = getFMDefaultAddress();

			// Filter for delivery addresses
			for (final AddressModel address : addresses)
			{
				if (address.getCountry() != null)
				{
					final boolean validForSite = deliveryCountries != null && deliveryCountries.contains(address.getCountry());
					// Filter out invalid addresses for the site
					if (validForSite)
					{
						final AddressData addressData = getAddressConverter().convert(address);
						if (defaultAddress != null && defaultAddress.getId() != null
								&& defaultAddress.getId().equals(addressData.getId()))
						{
							addressData.setDefaultAddress(true);
							result.add(0, addressData);
						}
						else
						{
							result.add(addressData);
						}
					}
				}
			}
			LOG.info("Result data in userfacade:::" + result);

			return result;
		}
		return Collections.emptyList();
	}

	/**
	 * @return the fmCustomerAccountService
	 */
	public FMCustomerAccountService getFmCustomerAccountService()
	{
		return fmCustomerAccountService;
	}

	/**
	 * @param fmCustomerAccountService
	 *           the fmCustomerAccountService to set
	 */
	public void setFmCustomerAccountService(final FMCustomerAccountService fmCustomerAccountService)
	{
		this.fmCustomerAccountService = fmCustomerAccountService;
	}

	@Override
	public AddressData getFMDefaultAddress()
	{
		LOG.info("In getfmdefault address - in my facade");

		final FMCustomerModel currentCustomer = (FMCustomerModel) getUserService().getCurrentUser();

		LOG.info("Addresssessss" + currentCustomer.getAddresses());

		AddressData defaultAddressData = null;

		final AddressModel defaultAddress = getFmCustomerAccountService().getFMDefaultAddress(currentCustomer);
		if (defaultAddress != null)
		{
			defaultAddressData = getAddressConverter().convert(defaultAddress);
		}
		else
		{
			final List<AddressModel> addresses = getFmCustomerAccountService().getFMAddressBookEntries(currentCustomer);
			if (CollectionUtils.isNotEmpty(addresses))
			{
				defaultAddressData = getAddressConverter().convert(addresses.get(0));
			}
		}
		return defaultAddressData;
	}




}

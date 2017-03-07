package com.federalmogul.facades.user.converters.populator;

import de.hybris.platform.commercefacades.user.converters.populator.AddressPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.AddressModel;

public class FMAddressPopulator extends AddressPopulator{
	
	@Override
	public void populate(final AddressModel source, final AddressData target)
	{
		super.populate(source, target);
		target.setCellphone(source.getCellphone());
		target.setLine3(source.getLine3());
		target.setSecondaryPhone(source.getPhone2());
	}
}

/**
 * 
 */
package com.federalmogul.facades.fmpopulator;

import de.hybris.platform.b2bacceleratorfacades.order.populators.B2BUnitPopulator;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.user.data.FMCustomerAccountData;


/**
 * @author SR279690
 * 
 */
public class FMCustomerAccountPopulator implements Populator<FMCustomerAccountModel, FMCustomerAccountData>
{

	private static final Logger LOG = Logger.getLogger(FMCustomerAccountPopulator.class);

	private Populator<AddressModel, FMB2bAddressData> fmb2bAddressPopulator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final FMCustomerAccountModel source, final FMCustomerAccountData target) throws ConversionException
	{
		// YTODO Auto-generated method stub

		target.setNabsAccountCode(source.getNabsAccountCode());

		final List<FMB2bAddressData> unitAddress = new ArrayList<FMB2bAddressData>();
		final List<AddressModel> unitAddressModel = (List<AddressModel>) source.getAddresses();
		for (final AddressModel address : unitAddressModel)
		{
			final FMB2bAddressData fmB2bAddressData = new FMB2bAddressData();
			getFmb2bAddressPopulator().populate(address, fmB2bAddressData);
			fmB2bAddressData.setCompanyName(source.getLocName());
			unitAddress.add(fmB2bAddressData);
			target.setFMB2baddress(fmB2bAddressData);
		}
		target.setUnitAddress(unitAddress);
		target.setDistributionChannel(source.getDistributionChannel());
		target.setSalesorg(source.getSalesorg());
		target.setDivision(source.getDivision());
		target.setChannelCode(source.getChannelCode());
		target.setProspectId(source.getProspectuid());
		target.setUid(source.getUid());
		target.setName(source.getLocName());
	}

	/**
	 * @return the fmb2bAddressPopulator
	 */
	public Populator<AddressModel, FMB2bAddressData> getFmb2bAddressPopulator()
	{
		return fmb2bAddressPopulator;
	}

	/**
	 * @param fmb2bAddressPopulator
	 *           the fmb2bAddressPopulator to set
	 */
	public void setFmb2bAddressPopulator(final Populator<AddressModel, FMB2bAddressData> fmb2bAddressPopulator)
	{
		this.fmb2bAddressPopulator = fmb2bAddressPopulator;
	}
}

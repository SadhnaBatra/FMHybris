/**
 * 
 */
package com.federalmogul.storefront.forms;

import de.hybris.platform.commercefacades.user.data.AddressData;



/**
 * @author KI302538
 * 
 */
public class FmB2bAddressSort extends AddressData implements Comparable<AddressData>
{


	/**
	 * <i>Generated property</i> for <code>FMB2bAddressData.companyFax</code> property defined at extension
	 * <code>fmfacades</code>.
	 */
	private String companyFax;
	/**
	 * <i>Generated property</i> for <code>FMB2bAddressData.companyUrl</code> property defined at extension
	 * <code>fmfacades</code>.
	 */
	private String companyUrl;

	private AddressData addressData;


	/**
	 * @return the addressData
	 */
	public AddressData getAddressData()
	{
		return addressData;
	}


	/**
	 * @param addressData
	 *           the addressData to set
	 */
	public void setAddressData(final AddressData addressData)
	{
		this.addressData = addressData;
	}


	public void setCompanyFax(final String companyFax)
	{
		this.companyFax = companyFax;
	}


	public String getCompanyFax()
	{
		return companyFax;
	}


	public void setCompanyUrl(final String companyUrl)
	{
		this.companyUrl = companyUrl;
	}


	public String getCompanyUrl()
	{
		return companyUrl;
	}


	@Override
	public int compareTo(final AddressData addData)
	{
		//ascending order
		return this.getPostalCode().compareTo(addData.getPostalCode());
	}

}

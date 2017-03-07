/**
 * 
 */
package com.federalmogul.storefront.forms;

/**
 * @author SA297584
 * 
 */
public class FMAddressForm extends AddressForm
{
	private String region;

	private String contactNo;

	/**
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * @param region
	 *           the region to set
	 */
	public void setRegion(final String region)
	{
		this.region = region;
	}

	/**
	 * @return the contactNo
	 */
	public String getContactNo()
	{
		return contactNo;
	}

	/**
	 * @param contactNo
	 *           the contactNo to set
	 */
	public void setContactNo(final String contactNo)
	{
		this.contactNo = contactNo;
	}

}

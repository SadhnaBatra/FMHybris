/**
 * 
 */
package com.federalmogul.storefront.forms;

/**
 * @author ryadav93
 * 
 */
public class EmailForm
{

	private String primaryEmail;

	/**
	 * @return the primaryEmail
	 */
	public String getPrimaryEmail()
	{
		return primaryEmail;
	}

	/**
	 * @param primaryEmail
	 *           the primaryEmail to set
	 */
	public void setPrimaryEmail(final String primaryEmail)
	{
		this.primaryEmail = primaryEmail;
	}

	/**
	 * @return the secondaryEmail
	 */
	public String getSecondaryEmail()
	{
		return secondaryEmail;
	}

	/**
	 * @param secondaryEmail
	 *           the secondaryEmail to set
	 */
	public void setSecondaryEmail(final String secondaryEmail)
	{
		this.secondaryEmail = secondaryEmail;
	}

	private String secondaryEmail;

}

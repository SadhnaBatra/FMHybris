/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SI279688
 * 
 */
public class LoyaltyHistoryRequestDTO extends CRMRequestDTO
{
	private String membership_Id;

	/**
	 * @return the membership_Id
	 */
	public String getMembership_Id()
	{
		return membership_Id;
	}

	/**
	 * @param membership_Id
	 *           the membership_Id to set
	 */
	public void setMembership_Id(final String membership_Id)
	{
		this.membership_Id = membership_Id;
	}

	/**
	 * @return the email_Id
	 */
	public String getEmail_Id()
	{
		return email_Id;
	}

	/**
	 * @param email_Id
	 *           the email_Id to set
	 */
	public void setEmail_Id(final String email_Id)
	{
		this.email_Id = email_Id;
	}

	/**
	 * @return the flag
	 */
	public String getFlag()
	{
		return Flag;
	}

	/**
	 * @param flag
	 *           the flag to set
	 */
	public void setFlag(final String flag)
	{
		Flag = flag;
	}

	private String email_Id;
	private String Flag;


}

/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SI279688
 * 
 */
public class PromoCodeRequestDTO extends CRMRequestDTO
{

	private String promoCode;
	private String membershipID;
	private String contactPersonId;
	private String prospectID;

	/**
	 * @return the promoCode
	 */
	public String getPromoCode()
	{
		return promoCode;
	}

	/**
	 * @param promoCode
	 *           the promoCode to set
	 */
	public void setPromoCode(final String promoCode)
	{
		this.promoCode = promoCode;
	}

	/**
	 * @return the membershipID
	 */
	public String getMembershipID()
	{
		return membershipID;
	}

	/**
	 * @param membershipID
	 *           the membershipID to set
	 */
	public void setMembershipID(final String membershipID)
	{
		this.membershipID = membershipID;
	}

	/**
	 * @return the contactPersonId
	 */
	public String getContactPersonId()
	{
		return contactPersonId;
	}

	/**
	 * @param contactPersonId
	 *           the contactPersonId to set
	 */
	public void setContactPersonId(final String contactPersonId)
	{
		this.contactPersonId = contactPersonId;
	}

	/**
	 * @return the prospectID
	 */
	public String getProspectID()
	{
		return prospectID;
	}

	/**
	 * @param prospectID
	 *           the prospectID to set
	 */
	public void setProspectID(final String prospectID)
	{
		this.prospectID = prospectID;
	}




}

/**
 * 
 */
package com.fm.falcon.webservices.dto;

import java.util.List;


/**
 * @author SI279688
 * 
 */
public class LoyaltySurveyRequestDTO extends CRMRequestDTO
{
	private String ProspectID;
	private String ContactPersonID;
	private List<String> UniqueID;
	private String ProductReviewFlag;
	private String SocialMediaFlag;

	private String MembershipID;

	
	/**
	 * @return the membershipID
	 */
	public String getMembershipID()
	{
		return MembershipID;
	}

	/**
	 * @param membershipID
	 *           the membershipID to set
	 */
	public void setMembershipID(final String membershipID)
	{
		MembershipID = membershipID;
	}

	/**
	 * @return the productReviewFlag
	 */
	public String getProductReviewFlag()
	{
		return ProductReviewFlag;
	}

	/**
	 * @param productReviewFlag
	 *           the productReviewFlag to set
	 */
	public void setProductReviewFlag(final String productReviewFlag)
	{
		ProductReviewFlag = productReviewFlag;
	}


	/**
	 * @return the socialMediaFlag
	 */
	public String getSocialMediaFlag()
	{
		return SocialMediaFlag;
	}

	/**
	 * @param socialMediaFlag
	 *           the socialMediaFlag to set
	 */
	public void setSocialMediaFlag(final String socialMediaFlag)
	{
		SocialMediaFlag = socialMediaFlag;
	}

	/**
	 * @return the prospectID
	 */
	public String getProspectID()
	{
		return ProspectID;
	}

	/**
	 * @param prospectID
	 *           the prospectID to set
	 */
	public void setProspectID(final String prospectID)
	{
		ProspectID = prospectID;
	}

	/**
	 * @return the contactPersonID
	 */
	public String getContactPersonID()
	{
		return ContactPersonID;
	}

	/**
	 * @param contactPersonID
	 *           the contactPersonID to set
	 */
	public void setContactPersonID(final String contactPersonID)
	{
		ContactPersonID = contactPersonID;
	}

	/**
	 * @return the uniqueID
	 */
	public List<String> getUniqueID()
	{
		return UniqueID;
	}

	/**
	 * @param uniqueID
	 *           the uniqueID to set
	 */
	public void setUniqueID(final List<String> uniqueID)
	{
		UniqueID = uniqueID;
	}


}

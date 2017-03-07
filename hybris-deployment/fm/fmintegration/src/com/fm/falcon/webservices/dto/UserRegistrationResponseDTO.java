/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : UserRegistrationResponseDTO.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.dto;

/**
 * User registration response DTO class.
 * 
 */
public class UserRegistrationResponseDTO extends CRMResponseDTO
{

	private String contactID;
	private String severity;
	private String severityText;
	private String prospectID;
	private String b2cLoyaltyMembershipId;

	/**
	 * @return the contactID
	 */
	public String getContactID()
	{
		return contactID;
	}

	/**
	 * @param contactID
	 *           the contactID to set
	 */
	public void setContactID(final String contactID)
	{
		this.contactID = contactID;
	}

	/**
	 * @return the severity
	 */
	public String getSeverity()
	{
		return severity;
	}

	/**
	 * @param severity
	 *           the severity to set
	 */
	public void setSeverity(final String severity)
	{
		this.severity = severity;
	}

	/**
	 * @return the severityText
	 */
	public String getSeverityText()
	{
		return severityText;
	}

	/**
	 * @param severityText
	 *           the severityText to set
	 */
	public void setSeverityText(final String severityText)
	{
		this.severityText = severityText;
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

	/**
	 * @return the b2cLoyaltyMembershipId
	 */
	public String getB2cLoyaltyMembershipId()
	{
		return b2cLoyaltyMembershipId;
	}

	/**
	 * @param b2cLoyaltyMembershipId the b2cLoyaltyMembershipId to set
	 */
	public void setB2cLoyaltyMembershipId(String b2cLoyaltyMembershipId)
	{
		this.b2cLoyaltyMembershipId = b2cLoyaltyMembershipId;
	}


}

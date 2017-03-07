/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SU276498
 * 
 */
public class LicensePlateRequestDTO extends CRMRequestDTO
{
	private String licensePlateRequest;

	/**
	 * @return the licensePlateRequest
	 */
	public String getLicensePlateRequest()
	{
		return licensePlateRequest;
	}

	/**
	 * @param licensePlateRequest
	 *           the licensePlateRequest to set
	 */
	public void setLicensePlateRequest(final String licensePlateRequest)
	{
		this.licensePlateRequest = licensePlateRequest;
	}

	/**
	 * @return the licensePlate
	 */
	public String getLicensePlate()
	{
		return licensePlate;
	}

	/**
	 * @param licensePlate
	 *           the licensePlate to set
	 */
	public void setLicensePlate(final String licensePlate)
	{
		this.licensePlate = licensePlate;
	}

	/**
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *           the state to set
	 */
	public void setState(final String state)
	{
		this.state = state;
	}

	/**
	 * @return the processingType
	 */
	public String getProcessingType()
	{
		return processingType;
	}

	/**
	 * @param processingType
	 *           the processingType to set
	 */
	public void setProcessingType(final String processingType)
	{
		this.processingType = processingType;
	}

	/**
	 * @return the quotebackId
	 */
	public String getQuotebackId()
	{
		return quotebackId;
	}

	/**
	 * @param quotebackId
	 *           the quotebackId to set
	 */
	public void setQuotebackId(final String quotebackId)
	{
		this.quotebackId = quotebackId;
	}

	/**
	 * @return the permissibleUsage
	 */
	public String getPermissibleUsage()
	{
		return permissibleUsage;
	}

	/**
	 * @param permissibleUsage
	 *           the permissibleUsage to set
	 */
	public void setPermissibleUsage(final String permissibleUsage)
	{
		this.permissibleUsage = permissibleUsage;
	}

	private String licensePlate;
	private String state;
	private String processingType;
	private String quotebackId;
	private String permissibleUsage;


}

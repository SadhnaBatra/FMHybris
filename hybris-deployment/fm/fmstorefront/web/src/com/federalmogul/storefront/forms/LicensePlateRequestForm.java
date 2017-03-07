/**
 * 
 */
package com.federalmogul.storefront.forms;

/**
 * @author SU276498
 * 
 */
public class LicensePlateRequestForm
{

	private String licensePlate;
	private String state;
	private String sourceRequestURL;



	/**
	 * @return the sourceRequestURL
	 */
	public String getSourceRequestURL()
	{
		return sourceRequestURL;
	}

	/**
	 * @param sourceRequestURL
	 *           the sourceRequestURL to set
	 */
	public void setSourceRequestURL(final String sourceRequestURL)
	{
		this.sourceRequestURL = sourceRequestURL;
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



}

/**
 * 
 */
package com.federalmogul.storefront.forms;

/**
 * @author SU276498
 * 
 */
public class VinLookupRequestForm
{

	private String vinRequest;
	private String vin;
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
	 * @return the vinRequest
	 */
	public String getVinRequest()
	{
		return vinRequest;
	}

	/**
	 * @param vinRequest
	 *           the vinRequest to set
	 */
	public void setVinRequest(final String vinRequest)
	{
		this.vinRequest = vinRequest;
	}

	/**
	 * @return the vin
	 */
	public String getVin()
	{
		return vin;
	}

	/**
	 * @param vin
	 *           the vin to set
	 */
	public void setVin(final String vin)
	{
		this.vin = vin;
	}



}

/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SU276498
 * 
 */
public class VinLookupRequestDTO extends CRMRequestDTO
{
	private String vinRequest;
	private String vin;

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


}

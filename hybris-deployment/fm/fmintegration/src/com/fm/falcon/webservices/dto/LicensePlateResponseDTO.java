/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SU276498
 * 
 */
public class LicensePlateResponseDTO extends CRMResponseDTO
{

	private String year;
	private String make;
	private String model;

	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}

	/**
	 * @param year
	 *           the year to set
	 */
	public void setYear(final String year)
	{
		this.year = year;
	}

	/**
	 * @return the make
	 */
	public String getMake()
	{
		return make;
	}

	/**
	 * @param make
	 *           the make to set
	 */
	public void setMake(final String make)
	{
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel()
	{
		return model;
	}

	/**
	 * @param model
	 *           the model to set
	 */
	public void setModel(final String model)
	{
		this.model = model;
	}


}

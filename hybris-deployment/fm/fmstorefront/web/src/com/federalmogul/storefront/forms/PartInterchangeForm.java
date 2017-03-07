/**
 * 
 */
package com.federalmogul.storefront.forms;

/**
 * @author SU276498
 * 
 */
public class PartInterchangeForm
{
	private String externalPart;

	private String partNumber;

	/**
	 * @return the partNumber
	 */
	public String getPartNumber()
	{
		return partNumber;
	}

	/**
	 * @param partNumber
	 *           the partNumber to set
	 */
	public void setPartNumber(final String partNumber)
	{
		this.partNumber = partNumber;
	}

	/**
	 * @return the externalPart
	 */
	public String getExternalPart()
	{
		return externalPart;
	}

	/**
	 * @param externalPart
	 *           the externalPart to set
	 */
	public void setExternalPart(final String externalPart)
	{
		this.externalPart = externalPart;
	}

}

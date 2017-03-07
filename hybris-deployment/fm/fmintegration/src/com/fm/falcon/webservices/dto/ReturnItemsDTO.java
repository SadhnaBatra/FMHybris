/**
 * 
 */
package com.fm.falcon.webservices.dto;




/**
 * @author Balaji
 * 
 */
public class ReturnItemsDTO extends CRMRequestDTO
{

	private String brandPart;

	/**
	 * @return the brandPart
	 */
	public String getBrandPart()
	{
		return brandPart;
	}

	/**
	 * @param brandPart
	 *           the brandPart to set
	 */
	public void setBrandPart(final String brandPart)
	{
		this.brandPart = brandPart;
	}

	/**
	 * @return the itemDescription
	 */
	public String getItemDescription()
	{
		return itemDescription;
	}

	/**
	 * @param itemDescription
	 *           the itemDescription to set
	 */
	public void setItemDescription(final String itemDescription)
	{
		this.itemDescription = itemDescription;
	}

	/**
	 * @return the orderedqty
	 */
	public String getOrderedqty()
	{
		return orderedqty;
	}

	/**
	 * @param orderedqty
	 *           the orderedqty to set
	 */
	public void setOrderedqty(final String orderedqty)
	{
		this.orderedqty = orderedqty;
	}

	/**
	 * @return the returnqty
	 */
	public String getReturnqty()
	{
		return returnqty;
	}

	/**
	 * @param returnqty
	 *           the returnqty to set
	 */
	public void setReturnqty(final String returnqty)
	{
		this.returnqty = returnqty;
	}

	/**
	 * @return the unit
	 */
	public String getUnit()
	{
		return unit;
	}

	/**
	 * @param unit
	 *           the unit to set
	 */
	public void setUnit(final String unit)
	{
		this.unit = unit;
	}

	private String itemDescription;
	private String orderedqty;
	private String returnqty;
	private String unit;



}

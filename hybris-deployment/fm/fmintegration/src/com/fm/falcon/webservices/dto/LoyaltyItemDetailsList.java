/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SI279688
 * 
 */
public class LoyaltyItemDetailsList
{
	private String productId;
	private String productActivity;
	private String points;
	private String status;
	private String orderId;

	/**
	 * @return the productId
	 */
	public String getProductId()
	{
		return productId;
	}

	/**
	 * @param productId
	 *           the productId to set
	 */
	public void setProductId(final String productId)
	{
		this.productId = productId;
	}

	/**
	 * @return the productActivity
	 */
	public String getProductActivity()
	{
		return productActivity;
	}

	/**
	 * @param productActivity
	 *           the productActivity to set
	 */
	public void setProductActivity(final String productActivity)
	{
		this.productActivity = productActivity;
	}

	/**
	 * @return the points
	 */
	public String getPoints()
	{
		return points;
	}

	/**
	 * @param points
	 *           the points to set
	 */
	public void setPoints(final String points)
	{
		this.points = points;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final String status)
	{
		this.status = status;
	}

	/**
	 * @return the orderId
	 */
	public String getOrderId()
	{
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId)
	{
		this.orderId = orderId;
	}


}

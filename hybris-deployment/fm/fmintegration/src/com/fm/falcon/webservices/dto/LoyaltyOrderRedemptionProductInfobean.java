/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SI279688
 * 
 */
public class LoyaltyOrderRedemptionProductInfobean extends CRMRequestDTO
{

	private String brandCode;
	private String qty;
	private String pointsRedeemed;

	/**
	 * @return the brandCode
	 */
	public String getBrandCode()
	{
		return brandCode;
	}

	/**
	 * @param brandCode
	 *           the brandCode to set
	 */
	public void setBrandCode(final String brandCode)
	{
		this.brandCode = brandCode;
	}

	/**
	 * @return the qty
	 */
	public String getQty()
	{
		return qty;
	}

	/**
	 * @param qty
	 *           the qty to set
	 */
	public void setQty(final String qty)
	{
		this.qty = qty;
	}

	/**
	 * @return the pointsRedeemed
	 */
	public String getPointsRedeemed()
	{
		return pointsRedeemed;
	}

	/**
	 * @param pointsRedeemed
	 *           the pointsRedeemed to set
	 */
	public void setPointsRedeemed(final String pointsRedeemed)
	{
		this.pointsRedeemed = pointsRedeemed;
	}

}

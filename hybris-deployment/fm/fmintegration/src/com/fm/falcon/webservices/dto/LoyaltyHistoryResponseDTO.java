/**
 * 
 */
package com.fm.falcon.webservices.dto;

import java.util.List;



/**
 * @author SI279688
 * 
 */
public class LoyaltyHistoryResponseDTO extends CRMResponseDTO
{

	String earnedPoints;
	String consumedPoints;
	String balancePoints;
	String url;
	List<LoyaltyHistoryBean> resultRedemption;
	List<LoyaltyHistoryBean> resultActivity;

	/**
	 * @return the earnedPoints
	 */
	public String getEarnedPoints()
	{
		return earnedPoints;
	}

	/**
	 * @param earnedPoints
	 *           the earnedPoints to set
	 */
	public void setEarnedPoints(final String earnedPoints)
	{
		this.earnedPoints = earnedPoints;
	}

	/**
	 * @return the consumedPoints
	 */
	public String getConsumedPoints()
	{
		return consumedPoints;
	}

	/**
	 * @param consumedPoints
	 *           the consumedPoints to set
	 */
	public void setConsumedPoints(final String consumedPoints)
	{
		this.consumedPoints = consumedPoints;
	}

	/**
	 * @return the balancePoints
	 */
	public String getBalancePoints()
	{
		return balancePoints;
	}

	/**
	 * @param balancePoints
	 *           the balancePoints to set
	 */
	public void setBalancePoints(final String balancePoints)
	{
		this.balancePoints = balancePoints;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url
	 *           the url to set
	 */
	public void setUrl(final String url)
	{
		this.url = url;
	}

	/**
	 * @return the resultRedemption
	 */
	public List<LoyaltyHistoryBean> getResultRedemption()
	{
		return resultRedemption;
	}

	/**
	 * @param resultRedemption
	 *           the resultRedemption to set
	 */
	public void setResultRedemption(final List<LoyaltyHistoryBean> resultRedemption)
	{
		this.resultRedemption = resultRedemption;
	}

	/**
	 * @return the resultActivity
	 */
	public List<LoyaltyHistoryBean> getResultActivity()
	{
		return resultActivity;
	}

	/**
	 * @param resultActivity
	 *           the resultActivity to set
	 */
	public void setResultActivity(final List<LoyaltyHistoryBean> resultActivity)
	{
		this.resultActivity = resultActivity;
	}



}

/**
 * 
 */
package com.fm.falcon.webservices.dto;

import java.util.List;


/**
 * @author SI279688
 * 
 */
public class LoyaltyHistoryBean
{
	private String activityId;
	private String postingDate;
	private List<LoyaltyItemDetailsList> itemsDetail;
	private String trackingURL;



	/**
	 * @return the trackingURL
	 */
	public String getTrackingURL()
	{
		return trackingURL;
	}

	/**
	 * @param trackingURL
	 *           the trackingURL to set
	 */
	public void setTrackingURL(final String trackingURL)
	{
		this.trackingURL = trackingURL;
	}

	/**
	 * @return the activityId
	 */
	public String getActivityId()
	{
		return activityId;
	}

	/**
	 * @param activityId
	 *           the activityId to set
	 */
	public void setActivityId(final String activityId)
	{
		this.activityId = activityId;
	}

	/**
	 * @return the postingDate
	 */
	public String getPostingDate()
	{
		return postingDate;
	}

	/**
	 * @param postingDate
	 *           the postingDate to set
	 */
	public void setPostingDate(final String postingDate)
	{
		this.postingDate = postingDate;
	}


	/**
	 * @return the itemsDetail
	 */
	public List<LoyaltyItemDetailsList> getItemsDetail()
	{
		return itemsDetail;
	}

	/**
	 * @param itemsDetail
	 *           the itemsDetail to set
	 */
	public void setItemsDetail(final List<LoyaltyItemDetailsList> itemsDetail)
	{
		this.itemsDetail = itemsDetail;
	}


}

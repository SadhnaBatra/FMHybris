/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SR279690
 * 
 */
public class LoyaltyOrderConfirmResponseDTO extends CRMResponseDTO
{

	private String obj_Id;
	private String desc;
	private String failureReason;

	/**
	 * @return the obj_Id
	 */
	public String getObj_Id()
	{
		return obj_Id;
	}

	/**
	 * @param obj_Id
	 *           the obj_Id to set
	 */
	public void setObj_Id(final String obj_Id)
	{
		this.obj_Id = obj_Id;
	}

	/**
	 * @return the desc
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * @param desc
	 *           the desc to set
	 */
	public void setDesc(final String desc)
	{
		this.desc = desc;
	}

	/**
	 * @return the failureReason
	 */
	public String getFailureReason()
	{
		return failureReason;
	}

	/**
	 * @param failureReason
	 *           the failureReason to set
	 */
	public void setFailureReason(final String failureReason)
	{
		this.failureReason = failureReason;
	}
}

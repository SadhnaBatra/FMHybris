/**
 * 
 */
package com.fm.falcon.webservices.dto;

import java.util.List;


/**
 * @author JA324889
 * 
 */
public class LoyaltySurveyResponseDTO extends CRMResponseDTO
{

	private String status;
	private List<String> uniqueId;

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
	 * @return the uniqueId
	 */
	public List<String> getUniqueId()
	{
		return uniqueId;
	}

	/**
	 * @param uniqueId
	 *           the uniqueId to set
	 */
	public void setUniqueId(final List<String> uniqueId)
	{
		this.uniqueId = uniqueId;
	}

}

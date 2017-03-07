package com.fm.falcon.webservices.dto;

/**
 * @author Balaji Return response DTO class.
 * 
 */
public class LeadGenerationCallBackResponseDTO extends CRMResponseDTO
{
	private String severity;
	private String text;
	private String code;
	private String returnId;
	private String returnStatus;

	/**
	 * @return the severity
	 */
	public String getSeverity()
	{
		return severity;
	}

	/**
	 * @param severity
	 *           the severity to set
	 */
	public void setSeverity(final String severity)
	{
		this.severity = severity;
	}

	/**
	 * @return the text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text
	 *           the text to set
	 */
	public void setText(final String text)
	{
		this.text = text;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code
	 *           the code to set
	 */
	public void setCode(final String code)
	{
		this.code = code;
	}

	/**
	 * @return the returnId
	 */
	public String getReturnId()
	{
		return returnId;
	}

	/**
	 * @param returnId
	 *           the returnId to set
	 */
	public void setReturnId(final String returnId)
	{
		this.returnId = returnId;
	}

	/**
	 * @return the returnStatus
	 */
	public String getReturnStatus()
	{
		return returnStatus;
	}

	/**
	 * @param returnStatus
	 *           the returnStatus to set
	 */
	public void setReturnStatus(final String returnStatus)
	{
		this.returnStatus = returnStatus;
	}



}

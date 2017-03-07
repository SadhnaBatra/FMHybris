
/**
 * 
 */
package com.fm.falcon.webservices.dto;




/**
 * @author JA324889
 * 
 */
public class PromoCodeResponseDTO extends CRMResponseDTO
{


	private String severity;
	private String severityText;
	private String code;

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
	 * @return the severityText
	 */
	public String getSeverityText()
	{
		return severityText;
	}

	/**
	 * @param severityText
	 *           the severityText to set
	 */
	public void setSeverityText(final String severityText)
	{
		this.severityText = severityText;
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







}

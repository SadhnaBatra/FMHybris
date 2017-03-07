/**
 * 
 */
package com.fm.falcon.webservices.dto;

/**
 * @author SU276498
 * 
 */
public class VinLookupResponseDTO extends CRMResponseDTO
{
	private String severity;
	private String text;
	private String code;
	private String returnId;
	private String returnStatus;
	private String fieldNames;

	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}

	/**
	 * @param year
	 *           the year to set
	 */
	public void setYear(final String year)
	{
		this.year = year;
	}

	/**
	 * @return the make
	 */
	public String getMake()
	{
		return make;
	}

	/**
	 * @param make
	 *           the make to set
	 */
	public void setMake(final String make)
	{
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel()
	{
		return model;
	}

	/**
	 * @param model
	 *           the model to set
	 */
	public void setModel(final String model)
	{
		this.model = model;
	}

	private String year;
	private String make;
	private String model;


	/**
	 * @return the severity
	 */
	public String getSeverity()
	{
		return severity;
	}

	/**
	 * @return the fieldNames
	 */
	public String getFieldNames()
	{
		return fieldNames;
	}

	/**
	 * @param fieldNames
	 *           the fieldNames to set
	 */
	public void setFieldNames(final String fieldNames)
	{
		this.fieldNames = fieldNames;
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

/**
 * 
 */
package com.federalmogul.storefront.forms;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author ryadav93
 * 
 */
public class OrderUploadForm
{
	private MultipartFile uploadFile;
	private String description;
	private String PONumber;




	/**
	 * @return the uploadFile
	 */
	public MultipartFile getUploadFile()
	{
		return uploadFile;
	}

	/**
	 * @param uploadFile
	 *           the uploadFile to set
	 */
	public void setUploadFile(final MultipartFile uploadFile)
	{
		this.uploadFile = uploadFile;
	}



	/**
	 * @return the pONumber
	 */
	public String getPONumber()
	{
		return PONumber;
	}

	/**
	 * @param pONumber
	 *           the pONumber to set
	 */
	public void setPONumber(final String pONumber)
	{
		PONumber = pONumber;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *           the description to set
	 */
	public void setDescription(final String description)
	{
		this.description = description;
	}



}

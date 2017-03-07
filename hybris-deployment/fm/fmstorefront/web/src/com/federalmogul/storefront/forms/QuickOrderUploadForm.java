/**
 * 
 */
package com.federalmogul.storefront.forms;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author ryadav93
 * 
 */
public class QuickOrderUploadForm
{
	private MultipartFile quickOrderFile;

	/**
	 * @return the quickOrderFile
	 */
	public MultipartFile getQuickOrderFile()
	{
		return quickOrderFile;
	}

	/**
	 * @param quickOrderFile
	 *           the quickOrderFile to set
	 */
	public void setQuickOrderFile(final MultipartFile quickOrderFile)
	{
		this.quickOrderFile = quickOrderFile;
	}



}

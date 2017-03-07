/**
 * 
 */
package com.federalmogul.core.customer.account.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.model.FMFileDownloadModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;



/**
 * @author sai
 * 
 */
public interface FMUserOnlineToolsDAO
{



	/**
	 * 
	 * @return
	 */
	public boolean uploadFilesToFMFileDownloadModel();

	/**
	 * @param directoryName
	 * @return
	 */
	List<FMFileDownloadModel> getAllFilesForDownload(String directoryName);

	/**
	 * 
	 * @param mFile
	 * @return
	 */
	public boolean uploadResearchFileToFMFileDownloadModel(MultipartFile mFile);


public CatalogVersionModel getContentCatalogVersion(String code);
public CatalogVersionModel addContentCatalogVersion(String code);


}

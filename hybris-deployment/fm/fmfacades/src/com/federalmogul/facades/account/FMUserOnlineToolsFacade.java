/**
 * 
 */
package com.federalmogul.facades.account;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.model.FMFileDownloadModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;


/**
 * @author sa297584
 * 
 */
public interface FMUserOnlineToolsFacade
{

	/**
	 * 
	 * @return
	 */
	public List<FMFileDownloadModel> getAllFilesForDownload(String directoryName);

	/**
	 * 
	 * @param mFile
	 * @return
	 */
	public boolean uploadResearchFileToFMFileDownloadModel(MultipartFile mFile);

public CatalogVersionModel getCatalogVersion(final String id);

public CatalogVersionModel addCatalogVersions(final String contentcm);


}

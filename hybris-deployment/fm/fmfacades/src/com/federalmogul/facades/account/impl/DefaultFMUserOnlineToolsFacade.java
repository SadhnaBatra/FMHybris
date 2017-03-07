/**
 * 
 */
package com.federalmogul.facades.account.impl;




import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.customer.account.dao.FMUserOnlineToolsDAO;
import com.federalmogul.core.model.FMFileDownloadModel;
import com.federalmogul.facades.account.FMUserOnlineToolsFacade;
import de.hybris.platform.catalog.model.CatalogVersionModel;



/**
 * @author su244261
 * 
 */
public class DefaultFMUserOnlineToolsFacade implements FMUserOnlineToolsFacade
{

	private static final Logger LOG = Logger.getLogger(DefaultFMUserOnlineToolsFacade.class);

	@Resource
	private FMUserOnlineToolsDAO defaultFMUserOnlineToolsDAO;



	/**
	 * @return the defaultFMUserOnlineToolsDAO
	 */
	public FMUserOnlineToolsDAO getDefaultFMUserOnlineToolsDAO()
	{
		return defaultFMUserOnlineToolsDAO;
	}



	/**
	 * @param defaultFMUserOnlineToolsDAO
	 *           the defaultFMUserOnlineToolsDAO to set
	 */
	public void setDefaultFMUserOnlineToolsDAO(final FMUserOnlineToolsDAO defaultFMUserOnlineToolsDAO)
	{
		this.defaultFMUserOnlineToolsDAO = defaultFMUserOnlineToolsDAO;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.account.FMUserOnlineToolsFacade#getAllFilesForDownload(java.lang.String)
	 */
	@Override
	public List<FMFileDownloadModel> getAllFilesForDownload(final String directoryName)
	{
		LOG.info("getAllFilesForDownload under FMUser ");
		// YTODO Auto-generated method stub
		return getDefaultFMUserOnlineToolsDAO().getAllFilesForDownload(directoryName);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.facades.account.FMUserOnlineToolsFacade#uploadResearchFileToFMFileDownloadModel(org.springframework
	 * .web.multipart.MultipartFile)
	 */
	@Override
	public boolean uploadResearchFileToFMFileDownloadModel(final MultipartFile mFile)
	{
		LOG.info("uploadResearchFileToFMFileDownloadModel in Faced  ");
		// YTODO Auto-generated method stub
		return getDefaultFMUserOnlineToolsDAO().uploadResearchFileToFMFileDownloadModel(mFile);
	}


@Override
	public CatalogVersionModel getCatalogVersion(final String id)
	{
		// YTODO Auto-generated method stub
		return getDefaultFMUserOnlineToolsDAO().getContentCatalogVersion(id);
	}
@Override
	public CatalogVersionModel addCatalogVersions(final String id)
	{
		// YTODO Auto-generated method stub
		return getDefaultFMUserOnlineToolsDAO().addContentCatalogVersion(id);
	}


}

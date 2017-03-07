/**
 * 
 */
package com.federalmogul.core.customer.account.dao.impl;

import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import de.hybris.platform.jalo.JaloSession;


import javax.annotation.Resource;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.federalmogul.core.customer.account.dao.FMUserOnlineToolsDAO;
import com.federalmogul.core.model.FMFileDownloadModel;


/**
 * @author sai
 * 
 */

public class DefaultFMUserOnlineToolsDAO implements FMUserOnlineToolsDAO
{
	private static final Logger LOG = Logger.getLogger(DefaultFMUserOnlineToolsDAO.class);

	public static final String FM_ONLINE_TOOL_FILES_LOCATION = "online.tool.files.path";

	private FlexibleSearchService flexibleSearchService;

	@Resource
	private CatalogService catalogService;


	@Resource
	private MediaService mediaService;

	@Autowired
	private ModelService modelService;






	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}




	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}




	/**
	 * @param flexibleSearchService
	 *           the flexibleSearchService to set
	 */
	public void setFlexibleSearchService(final FlexibleSearchService flexibleSearchService)
	{
		this.flexibleSearchService = flexibleSearchService;
	}




	/**
	 * @return the flexibleSearchService
	 */
	public FlexibleSearchService getFlexibleSearchService()
	{
		return flexibleSearchService;
	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.customer.account.dao.FMUserOnlineToolsDAO#getAllFilesForDownload()
	 */
	@Override
	public List<FMFileDownloadModel> getAllFilesForDownload(final String directoryName)
	{

		LOG.info("getAllFilesForDownload DAO Impl Method Called :: ");

		StringBuilder query = null;


		query = new StringBuilder("SELECT {fileDownload." + FMFileDownloadModel.PK + "}");
		query.append("FROM {" + FMFileDownloadModel._TYPECODE + " AS fileDownload}");
		query.append(" where {" + FMFileDownloadModel.SUBFOLDERPATH + "} like '" + directoryName + "'");
		query.append(" order by {" + FMFileDownloadModel.FILENAME + "} desc");

		LOG.info("File Download List  Querry :: " + query.toString());

		flexibleSearchService = getFlexibleSearchService();
		final SearchResult<FMFileDownloadModel> result = flexibleSearchService.search(query.toString());
		if (null != result && result.getResult().size() > 0)
		{
			LOG.info("GOT the Results and Its Size is :: " + result.getResult().size());


			final List<FMFileDownloadModel> fmFileDownloadsList = result.getResult();


			return fmFileDownloadsList;
		}
		else
		{
			LOG.info("No Results Found");
			LOG.error("Result NULL");

			return null;
		}


	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.customer.account.dao.FMUserOnlineToolsDAO#uploadFilesToFMFileDownloadModel()
	 */
	@Override
	public boolean uploadFilesToFMFileDownloadModel()
	{


		final boolean filesDeleted = isCleanFileDownLoad();
		if (filesDeleted)
		{
			LOG.info("Files sucessfully deleted in folder");
		}
		else
		{
			LOG.info("Files not sucessfully deleted in folder");
		}

		FMFileDownloadModel fmFileDownloadModel = null;
		boolean uploadFileStatus = false;
		final StringBuilder filePath = new StringBuilder(Config.getParameter(FM_ONLINE_TOOL_FILES_LOCATION));

		LOG.info("filePath :: " + filePath);

		final File onlineToolFParentFolder = new File(filePath.toString());

		final File[] listOfParentFolders = onlineToolFParentFolder.listFiles();


		try
		{

			for (final File onlineToolParentFile : listOfParentFolders)
			{
				//LOG.info("Directory :: " + onlineToolParentFile.isDirectory() + " Files :: " + onlineToolParentFile.list().length);
				if (onlineToolParentFile.isDirectory() && onlineToolParentFile.list().length > 0)
				{
					for (final File onlineToolFiles : onlineToolParentFile.listFiles())
					{
						if (onlineToolFiles.isFile() && onlineToolFiles.getName().toUpperCase().contains(".TXT"))
						{
							System.err.println("onlineToolFile file type ::" + onlineToolFiles);
							final InputStream inputStream = new FileInputStream(onlineToolFiles);
							final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
							String aLine;
							int count = 1;
							while ((aLine = bufferedReader.readLine()) != null)
							{
								if ("".equals(aLine.trim()) || count <= 8)
								{
									count++;
									continue;
								}

								if ("NOTES:".equals(aLine.trim()))
								{
									break;
								}
								final String actualFileName = getValue(aLine, 0, 14).trim();
								File onlineToolFile = null;
								InputStream in = null;
								try
								{

									onlineToolFile = new File(onlineToolFiles.getParent() + "/" + actualFileName.trim() + ".ZIP");
									in = new FileInputStream(onlineToolFile);
								}
								catch (final FileNotFoundException e)
								{
									try
									{
										onlineToolFile = new File(onlineToolFiles.getParent() + "/" + actualFileName.trim() + ".zip");
										in = new FileInputStream(onlineToolFile);
									}
									catch (final Exception a)
									{
										continue;
									}

								}
								catch (final Exception e)
								{
									continue;
								}

								final String mimeType = URLConnection.guessContentTypeFromName(onlineToolFile.getName());
								final String fileOriginalName = onlineToolFile.getName();

								final String parentFolder = onlineToolFiles.getParent();
								final String parentFolderName = parentFolder.substring(parentFolder.lastIndexOf("/") + 1);
								final DiskFileItem fileItem = new DiskFileItem("file", mimeType, false, onlineToolFile.getName(),
										(int) onlineToolFile.length(), onlineToolFile.getParentFile());
								fileItem.getOutputStream();
								final MultipartFile mFile = new CommonsMultipartFile(fileItem);
								final double fSize = mFile.getSize();
								final double kilobytes = (fSize / 1024);
								final double sizeInMegaBytes = (kilobytes / 1024);
								fmFileDownloadModel = new FMFileDownloadModel();
								fmFileDownloadModel.setCode(parentFolderName + "-" + fileOriginalName);
								fmFileDownloadModel.setSize((long) sizeInMegaBytes);
								final CatalogModel cm = catalogService.getCatalogForId("federalmogulContentCatalog");
								final Set catalogModelSet = cm.getCatalogVersions();
								if (catalogModelSet != null)
								{
									final Iterator itr = catalogModelSet.iterator();
									final CatalogVersionModel catalogVersionModel = (CatalogVersionModel) itr.next();
									fmFileDownloadModel.setCatalogVersion(catalogVersionModel);
								}
								try
								{
									fmFileDownloadModel.setFileName(actualFileName);
									fmFileDownloadModel.setDescription(getValue(aLine, 14, 40));
									fmFileDownloadModel.setLastRevisedDate(getValue(aLine, 54, 11));
									fmFileDownloadModel.setFileFormat(getValue(aLine, 65, 20));
									fmFileDownloadModel.setSubFolderPath(parentFolderName);
									modelService.save(fmFileDownloadModel);

									mediaService.setStreamForMedia(fmFileDownloadModel, in, mFile.getOriginalFilename(),
											mFile.getContentType());
									final double fileSize = fmFileDownloadModel.getSize();
									final double fileSizeKB = (fileSize / 1024);
									fmFileDownloadModel.setFileSize(Double.toString(Math.floor(fileSizeKB)) + " kb");
									modelService.save(fmFileDownloadModel);
								}
								catch (final Exception e)
								{
									uploadFileStatus = false;
								}
								if (uploadFileStatus)
								{
									if (onlineToolFile.delete())
									{
										LOG.info(onlineToolFile.getName() + " is deleted!");
									}
									else
									{
										LOG.info(onlineToolFile.getName() + " Delete operation is failed.");
									}
								}
							}
						}
					}
				}
			}
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage());
			ex.printStackTrace();
			uploadFileStatus = false;
		}
		LOG.info("####################### Final uploadFileStatus  " + uploadFileStatus);
		return uploadFileStatus;
	}

	protected boolean isCleanFileDownLoad()
	{

		final StringBuilder query = new StringBuilder("SELECT {" + FMFileDownloadModel.PK + "}");
		query.append(" FROM {" + FMFileDownloadModel._TYPECODE + "}");
		query.append(" WHERE {" + FMFileDownloadModel.SUBFOLDERPATH + "} NOT LIKE 'Research' AND ");
		query.append("  {" + FMFileDownloadModel.SUBFOLDERPATH + "} NOT LIKE 'Marketyourshop' AND ");
		query.append("  {" + FMFileDownloadModel.SUBFOLDERPATH + "} NOT LIKE 'HelpCenter' ");


		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		LOG.info("query :: " + query.toString());

		final SearchResult<FMFileDownloadModel> result = getFlexibleSearchService().search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{
			modelService.removeAll(result.getResult());
			LOG.info("DEleted-----");
		}
		else
		{
			LOG.error("Result NULL");
			return false;
		}


		return true;
	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.customer.account.dao.FMUserOnlineToolsDAO#uploadResearchFileToFMFileDownloadModel(org.
	 * springframework.web.multipart.MultipartFile)
	 */
	@Override
	public boolean uploadResearchFileToFMFileDownloadModel(final MultipartFile mFile)
	{

		LOG.info("#################3 uploadResearchFileToFMFileDownloadModel ################# 1");
		FMFileDownloadModel fmFileDownloadModel = null;
		boolean uploadFileStatus = false;
		final String parentFolderName = "Research";

		try
		{


			final InputStream in = mFile.getInputStream();

			//final byte[] bytes = mFile.getBytes();
			final double fSize = mFile.getSize();
			LOG.info("fSize ::: " + fSize);
			final double kilobytes = (fSize / 1024);
			final double sizeInMegaBytes = (kilobytes / 1024);
			fmFileDownloadModel = new FMFileDownloadModel();
			fmFileDownloadModel.setCode(parentFolderName + "-" + mFile.getOriginalFilename());

			LOG.info("sizeInMegaBytes ::: " + sizeInMegaBytes);
			fmFileDownloadModel.setSize((long) sizeInMegaBytes);


			//media.setDocname(file.getOriginalFilename());
			final CatalogModel cm = catalogService.getCatalogForId("federalmogulContentCatalog");
			final Set catalogModelSet = cm.getCatalogVersions();
			if (catalogModelSet != null)
			{
				final Iterator itr = catalogModelSet.iterator();
				final CatalogVersionModel catalogVersionModel = (CatalogVersionModel) itr.next();
				fmFileDownloadModel.setCatalogVersion(catalogVersionModel);
			}
			fmFileDownloadModel.setDescription(mFile.getOriginalFilename());
			fmFileDownloadModel.setSubFolderPath(parentFolderName);
			modelService.save(fmFileDownloadModel);
			try
			{
				//final InputStream inputStream = new ByteArrayInputStream(bytes);
				//InputStream in = new FileInputStream(mFile);
				mediaService.setStreamForMedia(fmFileDownloadModel, in, mFile.getOriginalFilename(), mFile.getContentType());
				modelService.save(fmFileDownloadModel);
				uploadFileStatus = true;
			}
			catch (final Exception e)
			{
				LOG.error(e.getMessage());
				e.printStackTrace();
				uploadFileStatus = false;
			}

		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage());
			ex.printStackTrace();
			uploadFileStatus = false;
		}
		LOG.info("####################### Final uploadFileStatus  " + uploadFileStatus);
		return uploadFileStatus;
	}

	public String getValue(final String line, final int start, final int length)
	{

		// try to do some sanity checking here to avoid substring errors.
		// return nothing when these are encountered and let the validity checking handle it

		// if the length of the line is less than where we want to start == bad
		if (line.length() < start)
		{
			return "";
		}

		// if the length is shorter than the field length we want to get, use the shorter value
		final int endIndex = line.length() < start + length ? line.length() : start + length;

		return line.substring(start, endIndex).trim();
	}


@Override
	public CatalogVersionModel getContentCatalogVersion(final String code)
	{
		final String version = "Online";


		final CatalogModel catalog = (CatalogModel) flexibleSearchService
				.search("SELECT {PK} FROM {Catalog} WHERE {id} =?id", Collections.singletonMap("id", code)).getResult().get(0);

		final StringBuilder query = new StringBuilder("SELECT {pk}");
		query.append("FROM {CatalogVersion} ");
		query.append("WHERE {version}=?version ");
		query.append("AND {catalog}=?catalog ");
		final Map params = new HashMap();
		params.put("version", version);
		params.put("catalog", catalog);

		final SearchResult searchRes = flexibleSearchService.search(query.toString(), params);

		if ((searchRes.getResult() != null) && (!(searchRes.getResult().isEmpty())))
		{
			final CatalogVersionModel productCatalogVersion = (CatalogVersionModel) searchRes.getResult().get(0);

			if (productCatalogVersion != null)
			{
				return productCatalogVersion;
			}


			return null;
		}


		return null;
	}

public CatalogVersionModel addContentCatalogVersion(final String contentcm)
	{

		final String version = "Online";

		final CatalogModel catalog = (CatalogModel) flexibleSearchService
				.search("SELECT {PK} FROM {Catalog} WHERE {id}=?contentcm", Collections.singletonMap("contentcm", contentcm))
				.getResult().get(0);




		final StringBuilder query = new StringBuilder("SELECT {pk}");
		query.append("FROM {CatalogVersion} ");
		query.append("WHERE {version}=?version ");
		query.append("AND {catalog}=?catalog ");
		final Map params = new HashMap();
		params.put("version", version);
		params.put("catalog", catalog);

		final SearchResult searchRes = flexibleSearchService.search(query.toString(), params);

		if ((searchRes.getResult() != null) && (!(searchRes.getResult().isEmpty())))
		{
			final CatalogVersionModel catalog_version_1 = (CatalogVersionModel) searchRes.getResult().get(0);

			if (catalog_version_1 != null)
			{
				JaloSession.getCurrentSession().getSessionContext()
						.setAttribute("catalogversions", modelService.toPersistenceLayer(Collections.singletonList(catalog_version_1)));
				LOG.info("category version for cms:  " + catalog_version_1.getPk());

				return catalog_version_1;
			}


		}


		return null;



	}




}
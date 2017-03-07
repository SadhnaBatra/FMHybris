/**
 * 
 */
package com.federalmogul.core.dao.dataimport.task.impl;

/**
 * @author SU276498
 * 
 */

import de.hybris.platform.acceleratorservices.dataimport.batch.BatchHeader;
import de.hybris.platform.acceleratorservices.dataimport.batch.HeaderTask;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.impex.ImpExResource;
import de.hybris.platform.servicelayer.impex.ImportConfig;
import de.hybris.platform.servicelayer.impex.ImportResult;
import de.hybris.platform.servicelayer.impex.ImportService;
import de.hybris.platform.servicelayer.impex.impl.StreamBasedImpExResource;
import de.hybris.platform.servicelayer.session.Session;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.federalmogul.core.event.FMBatchLoadErrorEmailEvent;
import com.federalmogul.core.event.FMBatchLoadSuccessEmailEvent;
import com.federalmogul.core.model.FMBatchLoadErrorEmailModel;
import com.federalmogul.core.model.FMBatchLoadSuccessEmailModel;



/**
 * Task that imports an impex file by executing impex.
 */
public abstract class FmAbstractImpexRunnerTask implements HeaderTask
{
	private static final Logger LOG = Logger.getLogger(FmAbstractImpexRunnerTask.class);

	private SessionService sessionService;
	private ImportService importService;
	private EventService eventService;
	private BaseStoreService baseStoreService;
	private UserService userService;

	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	/**
	 * @param userService
	 *           the userService to set
	 */
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	/**
	 * @return the baseStoreService
	 */
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}

	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	/**
	 * @return the baseSiteService
	 */
	public BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	/**
	 * @param baseSiteService
	 *           the baseSiteService to set
	 */
	public void setBaseSiteService(final BaseSiteService baseSiteService)
	{
		this.baseSiteService = baseSiteService;
	}

	/**
	 * @return the commonI18NService
	 */
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

	private BaseSiteService baseSiteService;
	private CommonI18NService commonI18NService;

	/**
	 * @return the eventService
	 */
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *           the eventService to set
	 */
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	@Override
	public BatchHeader execute(final BatchHeader header) throws FileNotFoundException
	{
		Assert.notNull(header);
		Assert.notNull(header.getEncoding());
		int count = 0;
		if (CollectionUtils.isNotEmpty(header.getTransformedFiles()))
		{
			final Session localSession = getSessionService().createNewSession();
			try
			{
				for (final File file : header.getTransformedFiles())
				{
					processFile(file, header.getEncoding(), count);
					count++;
				}
			}
			finally
			{
				getSessionService().closeSession(localSession);
			}
		}
		return header;
	}

	/**
	 * Process an impex file using the given encoding
	 * 
	 * @param file
	 * @param encoding
	 * @throws FileNotFoundException
	 */
	protected void processFile(final File file, final String encoding, final int count) throws FileNotFoundException
	{
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			final ImportConfig config = getImportConfig();
			final ImpExResource resource = new StreamBasedImpExResource(fis, encoding);
			config.setScript(resource);
			final ImportResult importResult = getImportService().importData(config);
			String filename = null;
			if (file.getName() != null)
			{
				final String fileName[] = file.getName().split("impex_1_");
				if (fileName.length > 1)
				{
					LOG.info("Impex File Name:::::" + fileName[1]);
					filename = fileName[1];
				}
			}
			if (importResult.isSuccessful() && count == 0)
			{
				if(Config.getParameter("dataloadEmailTrigger").equalsIgnoreCase("true")){
				final FMBatchLoadSuccessEmailModel fmBatchLoadSuccessEmailModel = new FMBatchLoadSuccessEmailModel();
				fmBatchLoadSuccessEmailModel.setEmailId(Config.getParameter("Dataload.batch.error.email"));
				LOG.info("Processed File name" + filename);
				fmBatchLoadSuccessEmailModel.setFileName(filename);
				getBaseSiteService().setCurrentBaseSite("federalmogul", true);
				LOG.info("getBaseSiteService().getCurrentBaseSite():::" + getBaseSiteService().getCurrentBaseSite());
				fmBatchLoadSuccessEmailModel.setSite(getBaseSiteService().getCurrentBaseSite());
				fmBatchLoadSuccessEmailModel.setStore(getBaseStoreService().getCurrentBaseStore());
				LOG.info("getBaseStoreService().getCurrentBaseStore():::" + getBaseStoreService().getCurrentBaseStore());
				fmBatchLoadSuccessEmailModel.setLanguage(getCommonI18NService().getCurrentLanguage());
				final CustomerModel customerModel = (CustomerModel) userService.getCurrentUser();
				fmBatchLoadSuccessEmailModel.setCustomer(customerModel);
				final FMBatchLoadSuccessEmailEvent fmBatchLoadSuccessEmailEvent = new FMBatchLoadSuccessEmailEvent(
						fmBatchLoadSuccessEmailModel);

				getEventService().publishEvent(initializeEvent(fmBatchLoadSuccessEmailEvent, customerModel));
}

			}
			if (importResult.isError() && importResult.hasUnresolvedLines())
			{
				if (importResult.hasUnresolvedLines() && count == 0)
				{
					if(Config.getParameter("dataloadEmailTrigger").equalsIgnoreCase("true")){
					LOG.error(importResult.getUnresolvedLines().getPreview());

					final FMBatchLoadErrorEmailModel fmBatchLoadErrorEmailModel = new FMBatchLoadErrorEmailModel();
					fmBatchLoadErrorEmailModel.setEmailId(Config.getParameter("Dataload.batch.error.email"));
					LOG.info("Error File name" + filename);
					fmBatchLoadErrorEmailModel.setFileName(filename);
					fmBatchLoadErrorEmailModel.setErrors(importResult.getUnresolvedLines().getPreview());
					getBaseSiteService().setCurrentBaseSite("federalmogul", true);
					LOG.info("getBaseSiteService().getCurrentBaseSite():::" + getBaseSiteService().getCurrentBaseSite());
					fmBatchLoadErrorEmailModel.setSite(getBaseSiteService().getCurrentBaseSite());
					fmBatchLoadErrorEmailModel.setStore(getBaseStoreService().getCurrentBaseStore());
					LOG.info("getBaseStoreService().getCurrentBaseStore():::" + getBaseStoreService().getCurrentBaseStore());
					fmBatchLoadErrorEmailModel.setLanguage(getCommonI18NService().getCurrentLanguage());
					final CustomerModel customerModel = (CustomerModel) userService.getCurrentUser();
					//customerModel.setUid("eBusinessSupports@federalmogul.com");
					//customerModel.setName("test");
					fmBatchLoadErrorEmailModel.setCustomer(customerModel);
					final FMBatchLoadErrorEmailEvent fmBatchLoadErrorEmailEvent = new FMBatchLoadErrorEmailEvent(
							fmBatchLoadErrorEmailModel);
					
					getEventService().publishEvent(initializeEvent(fmBatchLoadErrorEmailEvent, customerModel));
					}
				}
			}
		}
		finally
		{
			IOUtils.closeQuietly(fis);
		}
	}

	public FMBatchLoadErrorEmailEvent initializeEvent(final FMBatchLoadErrorEmailEvent event, final CustomerModel customerModel)
	{

		try
		{
			event.setCustomer(customerModel);
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	public FMBatchLoadSuccessEmailEvent initializeEvent(final FMBatchLoadSuccessEmailEvent event, final CustomerModel customerModel)
	{

		try
		{
			event.setCustomer(customerModel);
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	public SessionService getSessionService()
	{
		return sessionService;
	}

	@Required
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	public ImportService getImportService()
	{
		return importService;
	}

	@Required
	public void setImportService(final ImportService importService)
	{
		this.importService = importService;
	}

	/**
	 * Lookup method to return the import config
	 * 
	 * @return import config
	 */
	public abstract ImportConfig getImportConfig();
}

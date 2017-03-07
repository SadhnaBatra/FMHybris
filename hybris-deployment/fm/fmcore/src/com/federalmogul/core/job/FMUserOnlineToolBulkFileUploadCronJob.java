package com.federalmogul.core.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.Config;

import java.io.File;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.federalmogul.core.customer.account.dao.FMUserOnlineToolsDAO;
import com.federalmogul.core.network.dao.FMUserSearchDAO;


/**
 * Cron job to create the Sold-To creation of Prospect ID to PI interface
 * 
 * 
 */

public class FMUserOnlineToolBulkFileUploadCronJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(FMUserOnlineToolBulkFileUploadCronJob.class);

	public static final String FM_ONLINE_TOOL_FILES_LOCATION = "online.tool.files.path";

	@Resource
	FMUserSearchDAO fmUserSearchDAO;

	@Resource
	FMUserOnlineToolsDAO fmUserOnlineToolsDAO;

	/**
	 * Method reads the B2b prospect details and creates a file under the configured path with date & time stamp.
	 */
	@Override
	public PerformResult perform(final CronJobModel cronJobModel)
	{

		final File dir = new File(Config.getParameter(FM_ONLINE_TOOL_FILES_LOCATION));
		LOG.info("Prospect ID file path is " + dir.getAbsolutePath());

		if (!dir.exists())
		{
			dir.mkdirs();
		}
		try
		{
			fmUserOnlineToolsDAO.uploadFilesToFMFileDownloadModel();
		}
		catch (final Exception e)
		{
			LOG.info("Cron job Failed " + e.getMessage());
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
		}
		LOG.info("JOB SUCCESS");
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
}

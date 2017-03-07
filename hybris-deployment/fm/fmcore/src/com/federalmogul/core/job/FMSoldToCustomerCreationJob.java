package com.federalmogul.core.job;

import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.util.Config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;


/**
 * Cron job to create the Sold-To creation of Prospect ID to PI interface
 * 
 * 
 */

public class FMSoldToCustomerCreationJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(FMSoldToCustomerCreationJob.class);

	@Resource
	FMUserSearchDAO fmUserSearchDAO;

	/**
	 * Method reads the B2b prospect details and creates a file under the configured path with date & time stamp.
	 */
	@Override
	public PerformResult perform(final CronJobModel cronJobModel)
	{
		final StringBuilder filePath = new StringBuilder();

		LOG.info("filePath.toString() :: " + filePath.toString());

		final File dir = new File(Config.getParameter("b2buserSoldToCreationPath"));
		LOG.info("Prospect ID file path is " + dir.getAbsolutePath());

		if (!dir.exists())
		{
			dir.mkdirs();
		}
		try
		{
			// Create the file with prospect ID
			final File prospectIDFile = new File(dir.getAbsolutePath() + File.separator
					+ Config.getParameter("b2buserSoldToFileName") + new SimpleDateFormat("mmddyyyyhhss").format(new Date()) + ".txt");
			prospectIDFile.createNewFile();

			//Write the ID to file
			final List<FMCustomerAccountModel> prospectList = fmUserSearchDAO.getprospectSoldToUsers();
			final BufferedWriter outwriter = new BufferedWriter(new FileWriter(prospectIDFile));
			final Iterator<FMCustomerAccountModel> prospectItr = prospectList.iterator();
			while (prospectItr.hasNext())
			{
				final FMCustomerAccountModel customerAcc = prospectItr.next();
				final AddressModel customerAddress = customerAcc.getAddresses().iterator().next();
				final String prospectfileentry = customerAcc.getLocName() + ";" + customerAddress.getTown() + ";"
						+ customerAddress.getLine1() + ";" + customerAddress.getCountry().getIsocode() + ";"
						+ customerAddress.getPhone1() + ";" + customerAddress.getFax() + ";"
						+ customerAddress.getRegion().getIsocodeShort() + ";" + customerAddress.getPostalcode() + ";"
						+ customerAcc.getTaxID() + "\n";
				LOG.info(" prospectfileentry " + prospectfileentry);
				LOG.info(prospectfileentry);
				outwriter.write(prospectfileentry);
			}
			LOG.info("after while block ");

			outwriter.close();
			LOG.info("Server File Location=" + prospectIDFile.getAbsolutePath());
			LOG.info("File Path " + prospectIDFile.getPath());
		}
		catch (final IOException e)
		{
			LOG.error(e.getMessage());
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
		}

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}
}

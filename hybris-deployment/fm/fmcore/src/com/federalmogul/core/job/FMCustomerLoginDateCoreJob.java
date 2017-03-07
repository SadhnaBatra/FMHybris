/**
 * 
 */
package com.federalmogul.core.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.network.FMNetworkService;
import de.hybris.platform.util.Config;
import de.hybris.platform.servicelayer.model.ModelService;

/**
 * @author SR279690
 * 
 */
public class FMCustomerLoginDateCoreJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(FMCustomerLoginDateCoreJob.class);

	@Resource
	private FMNetworkService fmNetworkService;

	@Resource 
	private ModelService modelService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel
	 * )
	 */
	@SuppressWarnings("unused")
	@Override
	public PerformResult perform(final CronJobModel arg0)
	{
		// YTODO Auto-generated method stub
		LOG.info("Job is called");
		final List<FMCustomerModel> fmcustomerResult = fmNetworkService.getAllFMCustomers();

		final Date todayDate = new Date();
		Date loginDate = null;
		Date currentDate = null;
		final Calendar cal1 = Calendar.getInstance();
		final Calendar cal2 = Calendar.getInstance();
		long numOfDays = 0L;
		for (final FMCustomerModel fmcustomer : fmcustomerResult)
		{
			if (fmcustomer.getLogindate() != null)
			{
				LOG.info("date is :::" + fmcustomer.getLogindate());
				final DateFormat compareFormat = new SimpleDateFormat("yyyy-MM-dd");
				final String s_loginDate = compareFormat.format(fmcustomer.getLogindate());
				final String s_currentDate = compareFormat.format(todayDate);
				try
				{
					loginDate = compareFormat.parse(s_loginDate);
					currentDate = compareFormat.parse(s_currentDate);
					final long diff = currentDate.getTime() - loginDate.getTime();
					numOfDays = diff / (24 * 60 * 60 * 1000);
					String days=Config.getParameter("passwordCheck");
					if (numOfDays >= Integer.parseInt(days))
					{
						fmcustomer.setPwdMeetsLatestStandards(false);
                        fmcustomer.setLogindate(currentDate);
						modelService.save(fmcustomer);
					}
				}
				catch (final ParseException e)
				{
					LOG.error(e.getMessage());
					return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
				}

							
			}
		}
		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);

	}

	/**
	 * @param cal1
	 * @param cal2
	 */
	private long finddaysBetween(final Calendar loginDate, final Calendar currentDate)
	{
		final Calendar date = (Calendar) loginDate.clone();
		long daysBetween = 0;
		while (date.before(currentDate))
		{
			date.add(Calendar.DAY_OF_MONTH, 1);
			daysBetween++;
		}
		return daysBetween;

	}
}

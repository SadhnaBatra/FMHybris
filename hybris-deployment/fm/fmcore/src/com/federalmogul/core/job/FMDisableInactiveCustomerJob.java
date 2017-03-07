/**
 * 
 */
package com.federalmogul.core.job;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;


/**
 * @author aseemkohli
 * 
 */
public class FMDisableInactiveCustomerJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(FMDisableInactiveCustomerJob.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel
	 * )
	 */
	@Override
	public PerformResult perform(final CronJobModel cronJobModel)
	{
		final int pageSize = 1000;
		int offset = 0;

		final FlexibleSearchQuery query = new FlexibleSearchQuery("SELECT {PK} FROM {Customer} ORDER BY {PK}");
		query.setCount(pageSize);
		query.setNeedTotal(false);

		SearchResult<CustomerModel> result = null;
		do
		{
			query.setStart(offset);
			result = flexibleSearchService.search(query);

			final DateTime end = new DateTime(new java.util.Date());

			for (final CustomerModel customer : result.getResult())
			{
				LOG.debug("Processing customer: " + customer.getUid());
				if (customer.getLastLogin() != null)
				{
					final DateTime start = new DateTime(customer.getLastLogin());
					int daysBetween = 0;
					daysBetween = Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays();
					LOG.debug("No of Days: " + daysBetween);
					if (daysBetween > 180)
					{
						customer.setLoginDisabled(true);
						LOG.debug("Disabling customer: " + customer.getUid());
					}
				}
			}

			offset += pageSize;
		}
		while (result.getCount() == pageSize);

		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}

}

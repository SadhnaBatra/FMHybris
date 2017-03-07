package com.federalmogul.core.event;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.model.ReferAFriendEmailProcessModel;
import com.federalmogul.falcon.core.model.FMPartModel;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import com.federalmogul.falcon.core.model.FMReportLogModel;
import java.lang.Double;
import java.util.Date;
public class ReportLogEventListener extends AbstractEventListener<ReportLogEvent>{

	
	private static final Logger LOG = Logger.getLogger(ReportLogEventListener.class);


	@Autowired
	private ModelService modelService;
	

	public BusinessProcessService getBusinessProcessService()
	{
		return (BusinessProcessService) Registry.getApplicationContext().getBean("businessProcessService");
	}

	


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.event.impl.AbstractEventListener#onEvent(de.hybris.platform.servicelayer.event
	 * .events.AbstractEvent)
	 */
	@Override
	protected void onEvent(final ReportLogEvent event)
	{
		{
			try
			{
				
				Double d=1.0;
				LOG.info("onEvent started");
				
				
				final FMReportLogModel reportLog = modelService.create(FMReportLogModel.class);
				
				LOG.info("event.getBaseStore() " + event.getProcess().getCustomerAccount());
				LOG.info("event.getBaseStore() " + event.getProcess().getUser());
				reportLog.setCustAccount(event.getProcess().getCustomerAccount());
				reportLog.setUser(event.getProcess().getUser());
				reportLog.setCheckCount("1");
				reportLog.setCount(d);
				reportLog.setStartDate(new Date());
				reportLog.setEndDate(new Date());
				
				if(event.getProcess().getEventType().equals("INVENTORY")){
				LOG.info("in Inventory check");
				reportLog.setType("Inventory");
				}
				if(event.getProcess().getEventType().equals("ORDERSTATUS")){
				LOG.info("in ORDERSTATUS check");
				reportLog.setType("orderStatus");
					}
				else{
				reportLog.setType(event.getProcess().getEventType());

				}

				
				modelService.save(reportLog);
				
				
			}
			catch (final Exception e)
			{
			LOG.error("exception"+e.getMessage());
			}

		}
	}

}

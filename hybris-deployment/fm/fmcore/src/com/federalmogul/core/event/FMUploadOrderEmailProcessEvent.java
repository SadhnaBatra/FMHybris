/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.UploadOrderProcessEmailNotificationModel;


/**
 * @author su244261
 * 
 */
public class FMUploadOrderEmailProcessEvent extends AbstractCommerceUserEvent
{

	private final UploadOrderProcessEmailNotificationModel process;

	private static final Logger LOG = Logger.getLogger(FMUploadOrderEmailProcessEvent.class);

	public FMUploadOrderEmailProcessEvent(final UploadOrderProcessEmailNotificationModel process)
	{
		this.process = process;
		LOG.info(" UploadOrderProcessEmailNotificationModel Process Event");
	}


	public UploadOrderProcessEmailNotificationModel getProcess()
	{
		return process;
	}

}

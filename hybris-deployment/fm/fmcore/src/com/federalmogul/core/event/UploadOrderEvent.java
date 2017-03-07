/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import com.federalmogul.core.model.UploadOrderProcessModel;


/**
 * @author mamud
 * 
 */
public class UploadOrderEvent extends AbstractCommerceUserEvent
{
	private final UploadOrderProcessModel process;

	public UploadOrderEvent(final UploadOrderProcessModel process)
	{
		this.process = process;

	}


	public UploadOrderProcessModel getProcess()
	{
		return process;
	}
}

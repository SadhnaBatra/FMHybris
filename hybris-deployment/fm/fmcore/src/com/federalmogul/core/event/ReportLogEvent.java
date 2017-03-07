package com.federalmogul.core.event;

import com.federalmogul.core.model.ReferAFriendEmailProcessModel;
import com.federalmogul.core.model.ReportLogProcessModel;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

@SuppressWarnings("serial")
public class ReportLogEvent extends AbstractCommerceUserEvent {

	private final ReportLogProcessModel process;

	public ReportLogProcessModel getProcess() {
		return process;
	}

	
	public ReportLogEvent(final ReportLogProcessModel process)
	{
		this.process = process;

	}

	

}

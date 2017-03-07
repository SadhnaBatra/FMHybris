package com.federalmogul.core.event;

import com.federalmogul.core.model.FMContactUsProcessModel;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

/**
 * Created by steve on 11/29/16.
 */
public class FMContactUsEmailEvent extends AbstractCommerceUserEvent {

	private final FMContactUsProcessModel process;

	public FMContactUsEmailEvent(final FMContactUsProcessModel process) {
		this.process = process;
	}

	public FMContactUsProcessModel getProcess() {
		return process;
	}
}

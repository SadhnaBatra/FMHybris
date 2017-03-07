/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.federalmogul.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;

import com.federalmogul.core.model.UploadOrderProcessEmailNotificationModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;


/**
 * Velocity context for a customer email.
 */
public class FMUploadOrderEmailContext extends AbstractEmailContext<UploadOrderProcessEmailNotificationModel>
{

	/**
	 * @return the order
	 */
	public UploadOrderModel getOrder()
	{
		return order;
	}

	/**
	 * @param order
	 *           the order to set
	 */
	public void setOrder(final UploadOrderModel order)
	{
		this.order = order;
	}

	private UploadOrderModel order;

	@Override
	public void init(final UploadOrderProcessEmailNotificationModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(businessProcessModel, emailPageModel);
		order = businessProcessModel.getOrder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final UploadOrderProcessEmailNotificationModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final UploadOrderProcessEmailNotificationModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getOrder().getUser();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final UploadOrderProcessEmailNotificationModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

}

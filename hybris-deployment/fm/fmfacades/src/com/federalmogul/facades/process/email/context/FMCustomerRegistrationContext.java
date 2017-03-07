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
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.core.model.FMCustomerRegistrationProcessModel;


/**
 * Velocity context for a customer email.
 */
public class FMCustomerRegistrationContext extends AbstractEmailContext<FMCustomerRegistrationProcessModel>
{
	private Converter<UserModel, CustomerData> customerConverter;
	private CustomerData customerData;
	String fmHomePage = "FMHOME_PAGE";
	private static final Logger LOG = Logger.getLogger(FMCustomerRegistrationContext.class);

	/**
	 * @return the fmHomePage
	 */
	public String getFmHomePage()
	{
		return fmHomePage;
	}





	/**
	 * @param fmHomePage
	 *           the fmHomePage to set
	 */
	public void setFmHomePage(final String fmHomePage)
	{
		this.fmHomePage = fmHomePage;
	}





	@Override
	public void init(final FMCustomerRegistrationProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{


		super.init(businessProcessModel, emailPageModel);

		InetAddress localHost;
		/*try
		{
			//localHost = InetAddress.getLocalHost();
			//final String ipAddress = localHost.getHostAddress();
			fmHomePage = Config.getParameter("email.host.httpaddress");
		}
		catch (final UnknownHostException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage());
		}*/



		fmHomePage = Config.getParameter("email.host.httpaddress");

		customerData = getCustomerConverter().convert(getCustomer(businessProcessModel));

	}





	protected Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	@Required
	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
	}

	public CustomerData getCustomer()
	{
		return customerData;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final FMCustomerRegistrationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final FMCustomerRegistrationProcessModel businessProcessModel)
	{
		return businessProcessModel.getCustomer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final FMCustomerRegistrationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getLanguage();
	}
	/**
	 * @return the customerData
	 */
	public CustomerData getCustomerData()
	{
		return customerData;
	}





	/**
	 * @param customerData
	 *           the customerData to set
	 */
	public void setCustomerData(final CustomerData customerData)
	{
		this.customerData = customerData;
	}
}

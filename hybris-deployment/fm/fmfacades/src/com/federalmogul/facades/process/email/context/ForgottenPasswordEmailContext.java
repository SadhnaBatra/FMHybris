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
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.model.process.ForgottenPasswordProcessModel;
import de.hybris.platform.commerceservices.model.process.StoreFrontCustomerProcessModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.util.Config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.urlresolver.FMSiteBaseUrlResolutionService;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.user.data.FMCustomerData;


/**
 * Velocity context for a forgotten password email.
 */
public class ForgottenPasswordEmailContext extends CustomerEmailContext
{
	private static final Logger LOG = Logger.getLogger(ForgottenPasswordEmailContext.class);

	private int expiresInMinutes = 30;
	private String token;
	private String fmHomePage = "FMHOME_PAGE";
	private String secureFMHomePage = "";
	@Resource
	private Converter<UserModel, CustomerData> customerConverter;
	@Resource(name = "fmNetworkFacade")
	protected FMNetworkFacade fmNetworkFacade;

	private CustomerData customerData;

	private String userType = null;

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

	@Resource
	private FMSiteBaseUrlResolutionService fmSiteBaseUrlResolutionService;

	public int getExpiresInMinutes()
	{
		return expiresInMinutes;
	}

	public void setExpiresInMinutes(final int expiresInMinutes)
	{
		this.expiresInMinutes = expiresInMinutes;
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(final String token)
	{
		this.token = token;
	}

	public String getURLEncodedToken() throws UnsupportedEncodingException
	{
		return URLEncoder.encode(token, "UTF-8");
	}

	public String getRequestResetPasswordUrl() throws UnsupportedEncodingException, UnknownHostException
	{
		return fmSiteBaseUrlResolutionService.getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), false,
				"/password-reset");
	}

	public String getSecureRequestResetPasswordUrl() throws UnsupportedEncodingException, UnknownHostException
	{
		return fmSiteBaseUrlResolutionService.getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
				"/password-reset");
	}

	public String getResetPasswordUrl() throws UnsupportedEncodingException, UnknownHostException
	{
		return fmSiteBaseUrlResolutionService.getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), false,
				"/password-reset/change", "token=" + getURLEncodedToken());
	}

	public String getSecureResetPasswordUrl() throws UnsupportedEncodingException, UnknownHostException
	{
		/*
		 * return fmSiteBaseUrlResolutionService.getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
		 * "/password-reset/change", "token=" + getURLEncodedToken());
		 */
		return secureFMHomePage + "/password-reset/change?clear=true&site=federalmogul&token=" + getURLEncodedToken();
	}

	public String getDisplayResetPasswordUrl() throws UnsupportedEncodingException, UnknownHostException
	{
		return fmSiteBaseUrlResolutionService.getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), false,
				"/my-account/fmUpdate-password", "token=" + getURLEncodedToken());
	}

	public String getDisplaySecureResetPasswordUrl() throws UnsupportedEncodingException, UnknownHostException
	{
		return fmSiteBaseUrlResolutionService.getWebsiteUrlForSite(getBaseSite(), getUrlEncodingAttributes(), true,
				"/my-account/fmUpdate-password", "token=" + getURLEncodedToken());
	}

	@Override
	public void init(final StoreFrontCustomerProcessModel storeFrontCustomerProcessModel, final EmailPageModel emailPageModel)
	{
		super.init(storeFrontCustomerProcessModel, emailPageModel);

		try
		{
			/*
			 * final InetAddress ip = InetAddress.getLocalHost(); final String ipAddress = ip.getHostAddress();
			 */


			//fmHomePage = "http://" + ipAddress + ":9001/fmstorefront/?site=federalmogul";
			fmHomePage = Config.getParameter("email.host.httpaddress");
			secureFMHomePage = Config.getParameter("email.host.httpsaddress");
			customerData = getCustomerConverter().convert(getCustomer(storeFrontCustomerProcessModel));

			if (customerData != null)
			{
				LOG.info("CUSTOMER ID IS " + customerData.getUid());

				final FMCustomerData fmcustomerData = fmNetworkFacade.getFMCustomerDataForUid(customerData.getUid());
				if (fmcustomerData != null)
				{
					if (fmcustomerData.getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN))
					{
						userType = "b2t";
						LOG.info("CUSTOMER IS A B2T CUSTOMER");
					}
				}
			}
			else
			{
				LOG.info("INSIDE NULL");
			}
		}
		catch (final Exception e)
		{
			LOG.error("Exception ...." + e.getMessage());
		}
		if (storeFrontCustomerProcessModel instanceof ForgottenPasswordProcessModel)
		{
			setToken(((ForgottenPasswordProcessModel) storeFrontCustomerProcessModel).getToken());
		}
	}

	/**
	 * @return the customerConverter
	 */
	@Override
	public Converter<UserModel, CustomerData> getCustomerConverter()
	{
		return customerConverter;
	}

	/**
	 * @param customerConverter
	 *           the customerConverter to set
	 */
	@Override
	public void setCustomerConverter(final Converter<UserModel, CustomerData> customerConverter)
	{
		this.customerConverter = customerConverter;
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

	/**
	 * @return the userType
	 */
	public String getUserType()
	{
		return userType;
	}

}

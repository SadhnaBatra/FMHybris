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
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.ReferAFriendEmailProcessModel;


/**
 * Velocity context for a refer a friend email.
 */
public class ReferAFriendEmailContext extends AbstractEmailContext<ReferAFriendEmailProcessModel>
{

	@Resource(name = "sessionService")
	private SessionService sessionService;



	@Autowired
	private ModelService modelService;

	@Resource
	private UserService userService;


	String fmHomePage = "FMHOME_PAGE";
	private String referalFriendUrl = "REFERRAL_FRIEND_URL";
	private static final Logger LOG = Logger.getLogger(ReferAFriendEmailContext.class);

	private String mediaUrl1 = null;

	@Resource
	private Converter<OrderModel, OrderData> orderConverter;

	@Resource(name = "productService")
	private ProductService productService;


	private String refereeFirstName = null;

	private String refereeLastName = null;

	private String refereeEmail = null;

	//private String referer = null;

	private FMCustomerModel referer = null;

	/**
	 * @return the refereeFirstName
	 */
	public String getRefereeFirstName()
	{
		return refereeFirstName;
	}





	/**
	 * @return the refereeLastName
	 */
	public String getRefereeLastName()
	{
		return refereeLastName;
	}





	/**
	 * @return the refereeEmail
	 */
	public String getRefereeEmail()
	{
		return refereeEmail;
	}






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
	public void init(final ReferAFriendEmailProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{


		super.init(businessProcessModel, emailPageModel);

		InetAddress localHost;
		try
		{
			localHost = InetAddress.getLocalHost();
			final String ipAddress = localHost.getHostAddress();
			fmHomePage = "http://" + ipAddress + ":9001/fmstorefront/?site=federalmogul";
			//orderNumber = businessProcessModel.getOrderNumber();
			referalFriendUrl = "http://" + ipAddress + ":9001/fmstorefront/federalmogul/en/USD/registration/referalFriendRegistration";
			LOG.info("INSIDE INIT OF CONTEXT FILE");



		}
		catch (final UnknownHostException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage());
		}


		final String baseUrl = (String) get(BASE_URL);
		LOG.info("base url " + baseUrl);
		final String mediaUrl = baseUrl.substring(0, baseUrl.indexOf('f'));
		LOG.info(" NEW mediaUrl" + mediaUrl);
		//		put(MEDIA_BASE_URL, mediaUrl);
		mediaUrl1 = mediaUrl;
		//put(EMAIL, businessProcessModel.getRefereeEmail());
		LOG.info("AFTER EMAIL PUT");
		refereeFirstName = businessProcessModel.getRefereeFirstName();
		refereeLastName = businessProcessModel.getRefereeLastName();
		refereeEmail = businessProcessModel.getRefereeEmail();
		setReferer(businessProcessModel.getReferer());
		put(EMAIL, businessProcessModel.getRefereeEmail());
		put(DISPLAY_NAME, businessProcessModel.getRefereeFirstName() + "" + businessProcessModel.getRefereeLastName());
		/*
		 * put(FROM_DISPLAY_NAME, businessProcessModel.getRefereeFirstName());
		 * 
		 * final EmailAddressModel emailAddressModel = modelService.create(EmailAddressModel.class);
		 * emailAddressModel.setEmailAddress(businessProcessModel.getRefereeEmail());
		 * emailAddressModel.setDisplayName(refereeFirstName); modelService.save(emailAddressModel);
		 */

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final ReferAFriendEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return businessProcessModel.getSite();
	}










	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final ReferAFriendEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}


	/**
	 * @return the mediaUrl1
	 */
	public String getMediaUrl1()
	{
		return mediaUrl1;
	}





	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final ReferAFriendEmailProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub

		return null;
	}








	/**
	 * @return the referalFriendUrl
	 */
	public String getReferalFriendUrl()
	{
		return referalFriendUrl;
	}





	/**
	 * @param referalFriendUrl
	 *           the referalFriendUrl to set
	 */
	public void setReferalFriendUrl(final String referalFriendUrl)
	{
		this.referalFriendUrl = referalFriendUrl;
	}





	/**
	 * @return the referer
	 */
	public FMCustomerModel getReferer()
	{
		return referer;
	}





	/**
	 * @param referer
	 *           the referer to set
	 */
	public void setReferer(final FMCustomerModel referer)
	{
		this.referer = referer;
	}
}

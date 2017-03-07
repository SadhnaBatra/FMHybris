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
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.core.model.LoyaltyOrderConfirmationProcessModel;


/**
 * Velocity context for a customer email.
 */
public class LoyaltyOrderConfirmationContext extends AbstractEmailContext<LoyaltyOrderConfirmationProcessModel>
{

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource
	private UserService userService;

	private Converter<UserModel, CustomerData> customerConverter;
	private CustomerData customerData;
	String fmHomePage = "FMHOME_PAGE";
	private static final Logger LOG = Logger.getLogger(LoyaltyOrderConfirmationContext.class);
	CartData loyaltyCart = null;
	private OrderModel order;
	private OrderData orderData;
	private String mediaUrl1 = null;

	@Resource
	private Converter<OrderModel, OrderData> orderConverter;

	@Resource(name = "productService")
	private ProductService productService;

	/**
	 * @return the loyaltyCart
	 */
	public CartData getLoyaltyCart()
	{
		return loyaltyCart;
	}





	/**
	 * @param loyaltyCart
	 *           the loyaltyCart to set
	 */
	public void setLoyaltyCart(final CartData loyaltyCart)
	{
		this.loyaltyCart = loyaltyCart;
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
	public void init(final LoyaltyOrderConfirmationProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{


		super.init(businessProcessModel, emailPageModel);

		InetAddress localHost;
		try
		{
			localHost = InetAddress.getLocalHost();
			final String ipAddress = localHost.getHostAddress();
			fmHomePage = "http://" + ipAddress + ":9001/fmstorefront/?site=federalmogul";
			//orderNumber = businessProcessModel.getOrderNumber();
			loyaltyCart = sessionService.getAttribute("loyaltyCartData");
			order = businessProcessModel.getOrder();
			LOG.info("INSIDE INIT OF CONTEXT FILE");
			orderData = getOrderConverter().convert(businessProcessModel.getOrder());

			final List<OrderEntryData> entries = orderData.getEntries();
			LOG.info("TOTAL ENTRIES" + entries.size());
			if (entries != null)
			{
				for (final OrderEntryData entry : entries)
				{
					LOG.info("PRODUCT CODE " + entry.getProduct().getCode());
					LOG.info("PRODUCT URL" + entry.getProduct().getUrl());
					entry.getProduct().setLoyaltyPoints(getLoyaltyPointsForProduct(entry.getProduct().getCode()));
					final List<ImageData> images = (List<ImageData>) entry.getProduct().getImages();
					if (images != null)
					{
						for (final ImageData image : images)
						{
							LOG.info("IMAGE FORMAT" + image.getFormat());
							LOG.info("IMAGE URL" + image.getUrl());
						}
					}

				}
			}
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
		put(EMAIL, businessProcessModel.getCustomer().getUid());

		customerData = getCustomerConverter().convert(getCustomer(businessProcessModel));
		LOG.info("current customer name after populator" + customerData.getFirstName());
		LOG.info("current customer name after populator" + customerData.getName());
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
	protected BaseSiteModel getSite(final LoyaltyOrderConfirmationProcessModel businessProcessModel)
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
	protected CustomerModel getCustomer(final LoyaltyOrderConfirmationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
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
	protected LanguageModel getEmailLanguage(final LoyaltyOrderConfirmationProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}





	/**
	 * @return the order
	 */
	public OrderModel getOrder()
	{
		return order;
	}





	/**
	 * @param order
	 *           the order to set
	 */
	public void setOrder(final OrderModel order)
	{
		this.order = order;
	}





	/**
	 * @return the orderData
	 */
	public OrderData getOrderData()
	{
		return orderData;
	}





	/**
	 * @param orderData
	 *           the orderData to set
	 */
	public void setOrderData(final OrderData orderData)
	{
		this.orderData = orderData;
	}





	/**
	 * @return the orderConverter
	 */
	public Converter<OrderModel, OrderData> getOrderConverter()
	{
		return orderConverter;
	}





	/**
	 * @param orderConverter
	 *           the orderConverter to set
	 */
	public void setOrderConverter(final Converter<OrderModel, OrderData> orderConverter)
	{
		this.orderConverter = orderConverter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	/*
	 * @Override protected CustomerModel getCustomer(final LoyaltyOrderConfirmationProcessModel businessProcessModel) {
	 * return businessProcessModel.getCustomer(); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	/*
	 * @Override protected LanguageModel getEmailLanguage(final LoyaltyOrderConfirmationProcessModel
	 * businessProcessModel) { // YTODO Auto-generated method stub return businessProcessModel.getLanguage(); }
	 */
	private String getLoyaltyPointsForProduct(final String productCode)
	{

		String loyaltyPoints = "";
		final ProductModel productModel = productService.getProductForCode(productCode);
		if (productModel != null)
		{
			loyaltyPoints = productModel.getLoyaltyPoints();
		}
		return loyaltyPoints;
	}





	/**
	 * @return the mediaUrl1
	 */
	public String getMediaUrl1()
	{
		return mediaUrl1;
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

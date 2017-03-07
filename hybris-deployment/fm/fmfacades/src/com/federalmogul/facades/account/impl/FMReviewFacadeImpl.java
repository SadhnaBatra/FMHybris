/**
 * 
 */
package com.federalmogul.facades.account.impl;

import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.commercefacades.product.impl.DefaultProductFacade;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.Resource;
import org.springframework.util.Assert;

import com.federalmogul.core.event.FMCustomerReviewProcessEvent;
import com.federalmogul.core.model.FMCustomerReviewProcessModel;
import com.federalmogul.facades.account.FMReviewFacade;
import de.hybris.platform.customerreview.enums.CustomerReviewApprovalType;
import com.federalmogul.core.product.PartService;
import com.federalmogul.falcon.core.model.FMPartModel;

/**
 * @author SR279690
 * 
 */
public class FMReviewFacadeImpl extends DefaultProductFacade implements FMReviewFacade
{
	private static final Logger LOG = Logger.getLogger(FMReviewFacadeImpl.class);
	
	@Autowired
	private EventService eventService;

	@Autowired
	private BaseSiteService baseSiteService;

	@Autowired
	private BaseStoreService baseStoreService;
	
	@Resource
	private PartService partService;
	
	@Override
	public ReviewData postReview(final String productCode, final ReviewData reviewData, final Boolean flag)
	{
		LOG.info("inside postReview method of FMReviewFacadeImpl...");
		Assert.notNull(reviewData, "Parameter reviewData cannot be null.");
		final FMPartModel fmPart = partService.getPartForCode(productCode);
		final UserModel userModel = getUserService().getCurrentUser();
		final CustomerReviewModel customerReviewModel = getCustomerReviewService().createCustomerReview(reviewData.getRating(),
				reviewData.getHeadline(), reviewData.getComment(), userModel, fmPart);
		customerReviewModel.setLanguage(getCommonI18NService().getCurrentLanguage());
		customerReviewModel.setAlias(reviewData.getAlias());
		if (flag)
		{
			customerReviewModel.setBlocked(Boolean.valueOf(true));
		}
		else
		{
			customerReviewModel.setBlocked(Boolean.valueOf(false));
		}
		//Code to set brand for the product in customer review model
		if(fmPart.getCorporate()!=null && fmPart.getCorporate().size()>0)
		{
			LOG.info("setting brand for customer product review..");
			customerReviewModel.setBrand(fmPart.getCorporate().get(0).getCorpname());
		}
		getModelService().save(customerReviewModel);

		/* For Email */
		final FMCustomerReviewProcessModel reviewprocessmodel = new FMCustomerReviewProcessModel();
		reviewprocessmodel.setCustomerReview(customerReviewModel);

		final FMCustomerReviewProcessEvent reviewprocessevent = new FMCustomerReviewProcessEvent(reviewprocessmodel);
		eventService.publishEvent(initializeEvent(reviewprocessevent, userModel));
		
		return (ReviewData) getCustomerReviewConverter().convert(customerReviewModel);
	}
	
		/**
	 * @param reviewprocessevent
	 * @param userModel
	 * @return
	 */
	private FMCustomerReviewProcessEvent initializeEvent(final FMCustomerReviewProcessEvent reviewprocessevent,
			final UserModel userModel)
	{
		LOG.info("Inside initialise event");
		try
		{
			LOG.info("inside try");
			reviewprocessevent.setBaseStore(baseStoreService.getCurrentBaseStore());
			LOG.info("BASE STORE" + baseStoreService.getCurrentBaseStore().getName());
			reviewprocessevent.setSite(baseSiteService.getCurrentBaseSite());
			LOG.info("BASE SITE" + baseSiteService.getCurrentBaseSite().getName());
			reviewprocessevent.setCustomer((CustomerModel) userModel);
			LOG.info("FROM EVENT CUSTOMER ID" + reviewprocessevent.getCustomer().getUid());
			LOG.info("b4 returning event");
			return reviewprocessevent;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

}

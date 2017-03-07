/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.storefront.breadcrumb.impl.LoyaltyHomeBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;


/**
 * @author SI279688
 * 
 */
@Controller
@RequestMapping(value = "/**/garageGuru")
public class GarageGuruController extends AbstractPageController
{
	private static final String FM_REWARDS_PAGE = "FMRewardsAboutPage";

	private static final Logger LOG = Logger.getLogger(GarageGuruController.class);

	@Resource(name = "loyaltyBreadcrumbBuilder")
	protected LoyaltyHomeBreadcrumbBuilder loyaltyBreadcrumbBuilder;

	@RequestMapping(method = RequestMethod.GET, value = "/aboutRewards")
	public String getAboutRewards(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("INSIDE GARAge guru");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_REWARDS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_REWARDS_PAGE));
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getAboutRewardsBreadcrumbs("About Garage Rewards"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.Rewardsaboutpage;
	}


}

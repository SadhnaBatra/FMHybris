/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

/**
 * @author SA297584
 *
 */
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.storefront.controllers.ControllerConstants;



@Controller
@Scope("tenant")
@RequestMapping("/**/fm-GlobalError")
public class FMGlobalErrorPageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(FMGlobalErrorPageController.class);


	private static final String FM_GLOBAL_ERROR_PAGE = "FMGlobalErrorPages";

	@RequestMapping(value = "/ErrorPage", method = RequestMethod.GET)
	public String getGlobalErrorPage(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("############## getGlobalErrorPage ##################### 11");



		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_GLOBAL_ERROR_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_GLOBAL_ERROR_PAGE));
		model.addAttribute("breadcrumbs", "Online Tools");
		model.addAttribute("metaRobots", "no-index,no-follow");

		LOG.info("############## getGlobalErrorPage ##################### 22");

		return ControllerConstants.Views.Pages.Account.FMGlobalErrorPage;
	}




}

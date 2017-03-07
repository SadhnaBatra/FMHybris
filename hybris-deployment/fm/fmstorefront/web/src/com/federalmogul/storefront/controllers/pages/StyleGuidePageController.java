package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.util.XSSFilterUtil;


@Controller
@RequestMapping(value = "/style-guide")
public class StyleGuidePageController extends AbstractPageController {
	private static final Logger LOG = Logger.getLogger(StyleGuidePageController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getHomePage() {
		LOG.info("### getStyleGuidePage #### ");
		return ControllerConstants.Views.Pages.StyleGuide.Home;
	}

	@RequestMapping(value = "/base", method = RequestMethod.GET)
	public String getBasePage() throws CMSItemNotFoundException
	{
		return ControllerConstants.Views.Pages.StyleGuide.Base;
	}

	@RequestMapping(value = "/components", method = RequestMethod.GET)
	public String getComponentsPage() throws CMSItemNotFoundException
	{
		return ControllerConstants.Views.Pages.StyleGuide.Components;
	}

	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public String getLayoutPage() throws CMSItemNotFoundException
	{
		return ControllerConstants.Views.Pages.StyleGuide.Layout;
	}

	@RequestMapping(value = "/plugins", method = RequestMethod.GET)
	public String getPluginsPage() throws CMSItemNotFoundException
	{
		return ControllerConstants.Views.Pages.StyleGuide.Plugins;
	}
}
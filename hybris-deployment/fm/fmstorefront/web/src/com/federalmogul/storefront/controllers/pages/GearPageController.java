/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.storefront.breadcrumb.impl.MyCompanyBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;


/**
 * @author SR279690
 * 
 */
@Controller
@Scope
@RequestMapping("/**/gear")
public class GearPageController extends AbstractPageController
{
	@Resource(name = "myCompanyBreadcrumbBuilder")
	protected MyCompanyBreadcrumbBuilder myCompanyBreadcrumbBuilder;

	private static final String LEARNING_CMS_PAGE = "LearningCenterPage";

	//private static final Logger LOG = Logger.getLogger(LearningCenterPageController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String learningCenter(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", myCompanyBreadcrumbBuilder.getBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(LEARNING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LEARNING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.LearningCenter;
	}
}

/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.facades.account.FMUserFacade;
import com.federalmogul.facades.search.impl.DefaultFMCompetitorFacade;
import com.federalmogul.falcon.core.model.PartInterchangeModel;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.forms.PartInterchangeForm;


/**
 * @author SU276498
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping("/partInterchange")
public class FMCompetitorLookupController extends SearchPageController
{
	private static final Logger LOG = Logger.getLogger(FMCompetitorLookupController.class);

	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";
	private static final String PART_INTERCHANGE_PAGE = "partInterchangePage";


	/*
	 * @Resource(name = "accountBreadcrumbBuilder") private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	 */

	@Resource
	protected FMUserFacade fmUserfacade;

	/*
	 * @Resource(name = "searchBreadcrumbBuilder") private SearchBreadcrumbBuilder searchBreadcrumbBuilder;
	 */
	@Autowired
	DefaultFMCompetitorFacade defaultFMCompetitorFacade;


	/**
	 * controller mapping to get the FM Parts for the requested External part, if success returns to Search listing page
	 * 
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/getFMParts", method = RequestMethod.POST)
	public String getFMParts(@ModelAttribute("partInterchangeForm") final PartInterchangeForm partInterchangeForm,
			final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Get FM Parts request ==>");
		final List<PartInterchangeModel> partInterchangeFromModel = defaultFMCompetitorFacade
				.getExternalPartInfo(partInterchangeForm.getExternalPart());

		if (partInterchangeFromModel != null)
		{
			/*
			 * for (final PartInterchangeFromModel modelRequest : partInterchangeFromModel) { q.append("?q=" +
			 * modelRequest.getCorpCode() + modelRequest.getRawPartNumber()); }
			 */
			model.addAttribute("partInterchangeFromModel", partInterchangeFromModel);
			//getSessionService().setAttribute("partInterchangeForm", partInterchangeForm);
			//return REDIRECT_URL_SEARCH + q;

			model.addAttribute("metaRobots", "no-index,no-follow");
			storeCmsPageInModel(model, getContentPageForLabelOrId(PART_INTERCHANGE_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PART_INTERCHANGE_PAGE));

			return ControllerConstants.Views.Pages.Account.PartInterchange;

		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		return getViewForPage(model);
	}

}

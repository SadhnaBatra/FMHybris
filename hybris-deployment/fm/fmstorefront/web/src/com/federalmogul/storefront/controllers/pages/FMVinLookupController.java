/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.facades.account.FMUserFacade;
import com.federalmogul.facades.account.FMVinLookupFacade;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.VinLookupRequestForm;
import com.fm.falcon.webservices.dto.VinLookupResponseDTO;


/**
 * @author SU276498
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping("/vinLookup")
public class FMVinLookupController extends SearchPageController
{
	private static final Logger LOG = Logger.getLogger(FMVinLookupController.class);

	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";
	private static final String REDIRECT_URL_SEARCH = REDIRECT_PREFIX + "/search";

	/*
	 * @Resource(name = "accountBreadcrumbBuilder") private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	 */
	@Resource
	protected FMUserFacade fmUserfacade;

	@Resource(name = "vinLookupFacade")
	protected FMVinLookupFacade fmVinLookupFacade;

	/*
	 * @Resource(name = "searchBreadcrumbBuilder") private SearchBreadcrumbBuilder searchBreadcrumbBuilder;
	 */


	/**
	 * controller mapping to get the YMM from VIN Service, if success returns to Search listing page
	 * 
	 * @param callBackRequestForm
	 * @param result
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/vinSearch", method = RequestMethod.POST)
	public String vinLookup(@ModelAttribute("vinLookupFormData") final VinLookupRequestForm vinLookupRequestForm,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		LOG.info("vinSearch request ==>");
		VinLookupResponseDTO vinLookupResponseDTO = null;

		try
		{
			vinLookupResponseDTO = fmVinLookupFacade.vinLookup(vinLookupRequestForm.getVin());
		}
		catch (final Exception e)
		{
			GlobalMessages.addInfoMessage(model, "Vin Lookup Service not available.. Please try again");
			LOG.error("Exception ...." + e.getMessage());

		}
		if (vinLookupResponseDTO != null && "0".equals(vinLookupResponseDTO.getResponseCode()))
		{
			final String year = vinLookupResponseDTO.getYear();
			final String make = vinLookupResponseDTO.getMake();
			final String model1 = vinLookupResponseDTO.getModel();

			final String ymmString = year + make + model1 + "|";
			LOG.info("VIN ymmString:::" + ymmString);
			final StringBuffer q = new StringBuffer();
			q.append("?q=:name-asc:vehiclesegment:" + ymmString);
			q.append("Passenger+Car+Light+Truck:year:" + ymmString);
			q.append(year + ":make:" + ymmString + make);
			q.append(":model:" + ymmString + model1 + "&text=#");

			LOG.info("VIN queryParam:::" + q);

			return REDIRECT_URL_SEARCH + q;
		}
		updatePageTitle(null, model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		return getViewForPage(model);

	}
}

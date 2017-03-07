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
import com.federalmogul.storefront.forms.LicensePlateRequestForm;
import com.fm.falcon.webservices.dto.LicensePlateResponseDTO;


/**
 * @author SU276498
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping("/licensePlate")
public class FMLicensePlateController extends SearchPageController
{
	private static final Logger LOG = Logger.getLogger(FMLicensePlateController.class);

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
	 * controller mapping to get the YMM from License Plate Service, if success returns to Search listing page
	 * 
	 * @param callBackRequestForm
	 * @param result
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/licensePlateSearch", method = RequestMethod.POST)
	public String licensePlateSearch(
			@ModelAttribute("LicensePlateRequestForm") final LicensePlateRequestForm licensePlateRequestForm, final Model model,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		LOG.info("licensePlateSearch request ==>");
		LicensePlateResponseDTO licensePlateResponseDTO = null;
		final String[] state = licensePlateRequestForm.getState().split(",");

		LOG.info("LicensePlate#::::::::::::::::::: ==>" + licensePlateRequestForm.getLicensePlate());
		LOG.info("State::::::::::::::::::: ==>" + state[1]);

		try
		{
			licensePlateResponseDTO = fmVinLookupFacade.licensePlate(licensePlateRequestForm.getLicensePlate(), state[1]);
		}
		catch (final Exception e)
		{
			GlobalMessages.addInfoMessage(model, "License Plate Service not available.. Please try again");
			LOG.error("Exception ...." + e.getMessage());

		}
		if (licensePlateResponseDTO != null && licensePlateResponseDTO.getResponseCode().isEmpty())
		{
			final String year = licensePlateResponseDTO.getYear();
			final String make = licensePlateResponseDTO.getMake();
			final String model1 = licensePlateResponseDTO.getModel();

			final String ymmString = year + make + model1 + "|";
			LOG.info("License Plate ymmString:::" + ymmString);
			final StringBuffer q = new StringBuffer();
			q.append("?q=:name-asc:vehiclesegment:" + ymmString);
			q.append("Passenger+Car+Light+Truck:year:" + ymmString);
			q.append(year + ":make:" + ymmString + make);
			q.append(":model:" + ymmString + model1 + "&text=#");

			LOG.info("License Plate queryParam:::" + q);

			return REDIRECT_URL_SEARCH + q;
		}
		updatePageTitle(null, model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		return getViewForPage(model);

	}
}

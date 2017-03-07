package com.federalmogul.storefront.controllers.ajax;

import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.facades.search.impl.DefaultFMFitmentSearchFacade;


/**
 * Mamud
 * 
 * Controller for search page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/ymmsearch")
public class YMMSearchController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(YMMSearchController.class);

	@Autowired
	private DefaultFMFitmentSearchFacade defaultFMFitmentSearchFacade;

	@Autowired
	private CMSSiteService cmsSiteService;

	/**
	 * @return the cmsSiteService
	 */
	public CMSSiteService getCmsSiteService()
	{
		return cmsSiteService;
	}

	/**
	 * @param cmsSiteService
	 *           the cmsSiteService to set
	 */
	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vehicleSegment")
	public String getAutoVehicleSegment(final Model model)
	{
		LOG.info("Inside vehicleSegment Method");
		final String pages = "pages/fm/ajax/summaryDetails";
		//final List<FMFitmentModel> fitmentList = defaultFMFitmentSearchFacade.getYMMFitmentData();
		//return getFitment("vehicleSegment", fitmentList, model);
		//final List<String> vehicleSegement = defaultFMFitmentSearchFacade.getFitmentData();
		final List<String> vehicleSegement = defaultFMFitmentSearchFacade.getVehicleTypeData();
		model.addAttribute("vehicleSegment", vehicleSegement);
		model.addAttribute("vehicleSegmentSize", vehicleSegement.size());
		return pages;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/autoyear/{vehicleSegment}")
	public String getAutoYear(@PathVariable("vehicleSegment") final String vehicleSegment, final Model model)
	{
		LOG.info("Inside Year Method vehicleSegment :: " + vehicleSegment.toString());
		//final List<FMFitmentModel> fitmentList = defaultFMFitmentSearchFacade.getYMMFitmentYearData(vehicleSegment);
		//return getFitment("years", fitmentList, model);
		final String pages = "pages/fm/ajax/summaryDetails";
		//final List<String> years = defaultFMFitmentSearchFacade.getFitmentYearData(vehicleSegment);
		final List<String> years = defaultFMFitmentSearchFacade.getYearData(vehicleSegment);
		model.addAttribute("years", years);
		model.addAttribute("yearsSize", years.size());
		return pages;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/automake/{year}/{vehicleSegment}")
	public String getAutocompleteMake(@PathVariable("year") final String year,
			@PathVariable("vehicleSegment") final String vehicleSegment, final Model model)
	{
		LOG.info("Inside Make Method vehicleSegment :: " + vehicleSegment.toString() + ":: year :: " + year.toString());
		//final List<FMFitmentModel> fitmentList = defaultFMFitmentSearchFacade.getYMMFitmentMakeData(year, vehicleSegment);
		//return getFitment("make", fitmentList, model);

		final String pages = "pages/fm/ajax/summaryDetails";
		//final List<String> makes = defaultFMFitmentSearchFacade.getFitmentMakeData(year, vehicleSegment);
		final List<String> makes = defaultFMFitmentSearchFacade.getMakeData(year, vehicleSegment);
		model.addAttribute("make", makes);
		model.addAttribute("makeSize", makes.size());
		return pages;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/automodel/{year}/{make}/{vehicleSegment}")
	public String getAutocompleteModel(@PathVariable("year") final String year, @PathVariable("make") final String make,
			@PathVariable("vehicleSegment") final String vehicleSegment, final Model model)
	{
		LOG.info("Inside Model Method vehicleSegment :: " + vehicleSegment.toString() + "::year :: " + year.toString()
				+ ":: Make:: " + make.toString());
		//final List<FMFitmentModel> fitmentList = defaultFMFitmentSearchFacade.getYMMFitmentModelData(year, make, vehicleSegment);
		//return getFitment("models", fitmentList, model);

		final String pages = "pages/fm/ajax/summaryDetails";
		//final List<String> models = defaultFMFitmentSearchFacade.getFitmentModelData(year, make, vehicleSegment);
		final List<String> models = defaultFMFitmentSearchFacade.getModelData(year, make, vehicleSegment);
		model.addAttribute("models", models);
		model.addAttribute("modelsSize", models.size());
		return pages;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/autoSubCategory/{categoryCode}")
	public String getAutocompleteSubCategory(@PathVariable("categoryCode") final String categoryCode, final Model model)
	{

		final List<CategoryModel> rootCategories = getCmsSiteService().getCurrentCatalogVersion().getCatalog().getRootCategories();
		if (rootCategories != null && !rootCategories.isEmpty())
		{
			return getCategories("subcategory", rootCategories, model, categoryCode);
		}
		return null;

	}

	private String getCategories(final String reqType, final List<CategoryModel> rootCategories, final Model model,
			final String categoryCode)
	{
		final String pages = "pages/fm/ajax/categoryDetails";
		final List<String> categoryList = new ArrayList<String>();
		final List<String> categoryListCode = new ArrayList<String>();
		if (rootCategories != null && rootCategories.size() > 0)
		{
			for (int i = 0; i < rootCategories.size(); i++)
			{
				final CategoryModel categoryModel = rootCategories.get(i);
				if (null != categoryModel)
				{
					if (reqType != null && "category".equalsIgnoreCase(reqType))
					{
						if (!categoryList.contains(categoryModel.getName()) && categoryModel.getName() != null)
						{
							categoryList.add(categoryModel.getName());
							categoryListCode.add(categoryModel.getCode());
						}
					}
					else if (reqType != null && "subcategory".equalsIgnoreCase(reqType))
					{
						for (int j = 0; j < categoryModel.getCategories().size(); j++)
						{
							if (categoryModel.getCategories().get(j).getName() != null && categoryModel.getName() != null
									&& !categoryList.contains(categoryModel.getCategories().get(j).getName())
									&& categoryModel.getName().equalsIgnoreCase(categoryCode))
							{
								categoryList.add(categoryModel.getCategories().get(j).getName());
								categoryListCode.add(categoryModel.getCategories().get(j).getCode());
							}
						}

					}
				}

			}
			categoryList.remove(null);
			if (categoryList.size() > 1)
			{
				Collections.sort(categoryList);
			}
		}

		model.addAttribute(reqType, categoryList);
		model.addAttribute(reqType + "Code", categoryListCode);
		model.addAttribute(reqType + "Size", categoryList.size());
		return pages;
	}

	//	private String getFitment(final String reqType, final List<FMFitmentModel> fitmentList, final Model model)
	//	{
	//		final String pages = "pages/fm/ajax/summaryDetails";
	//		final List<String> fits = new ArrayList<String>();
	//		if (fitmentList != null && fitmentList.size() > 0)
	//		{
	//			for (int i = 0; i < fitmentList.size(); i++)
	//			{
	//				final FMFitmentModel fitment = fitmentList.get(i);
	//				if (null != fitment)
	//				{
	//					if (reqType != null && "models".equalsIgnoreCase(reqType))
	//					{
	//						if (!fits.contains(fitment.getModel()))
	//						{
	//							fits.add(fitment.getModel());
	//						}
	//					}
	//					else if (reqType != null && "make".equalsIgnoreCase(reqType))
	//					{
	//						if (!fits.contains(fitment.getMake()))
	//						{
	//							fits.add(fitment.getMake());
	//						}
	//					}
	//					else if (reqType != null && "years".equalsIgnoreCase(reqType))
	//					{
	//						if (!fits.contains(fitment.getYear()))
	//						{
	//							fits.add(fitment.getYear());
	//						}
	//					}
	//					else if (reqType != null && "vehicleSegment".equalsIgnoreCase(reqType))
	//					{
	//						if (!fits.contains(fitment.getVehiclesegment()))
	//						{
	//							fits.add(fitment.getVehiclesegment());
	//						}
	//					}
	//				}
	//			}
	//			fits.remove(null);
	//			if (fits.size() > 1)
	//			{
	//				Collections.sort(fits);
	//			}
	//		}
	//		model.addAttribute(reqType, fits);
	//		model.addAttribute(reqType + "Size", fits.size());
	//		return pages;
	//
	//	}
}

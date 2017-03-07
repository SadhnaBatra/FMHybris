package com.federalmogul.storefront.controllers.misc;

import de.hybris.platform.acceleratorservices.urlresolver.SiteBaseUrlResolutionService;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.contents.components.CMSLinkComponentModel;
import de.hybris.platform.cms2.model.navigation.CMSNavigationNodeModel;
import de.hybris.platform.cms2.servicelayer.services.CMSNavigationService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import java.util.Locale;
import java.util.LinkedHashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.AbstractPageController;
import com.federalmogul.facades.account.FMUserOnlineToolsFacade;


@Controller
@RequestMapping(value = "/**/siteMap")
public class SiteMapController extends AbstractPageController
{
	protected static final Logger LOG = Logger.getLogger(SiteMapController.class);
	@Resource(name = "cmsSiteService")
	private CMSSiteService cmsSiteService;
	private static final String siteMapPage = "FMSiteMapPage";
	@Resource
	protected FlexibleSearchService flexibleSearchService;
	@Resource
	private CatalogService catalogService;

	@Resource(name = "siteBaseUrlResolutionService")
	private SiteBaseUrlResolutionService siteBaseUrlResolutionService;

	@Autowired
	private CMSNavigationService cmsNavigationService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private   FMUserOnlineToolsFacade onlineToolsfacade;


	@Resource(name = "categoryModelUrlResolver")
	private UrlResolver<CategoryModel> categoryModelUrlResolver;

	@Autowired
	private CatalogVersionService catalogVersionService;


	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(value = "site", required = false) final String site, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", "Site Map", null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		final String cm = cmsSiteService.getCurrentSite().getUid();
		if(cm.equalsIgnoreCase("loyalty")){
			return "redirect:" + "/siteMap?site=federalmogul&clear=true";
		}

		final CMSNavigationNodeModel CatalogNode = cmsNavigationService.getNavigationNodeForId("CatalogNavNode");
		if (CatalogNode != null)
		{
			final List<CMSLinkComponentModel> catalogNavNodeLinks = CatalogNode.getLinks();
				final Iterator catalogNavitr = catalogNavNodeLinks.iterator();
				final Map<String, String> catalog = new LinkedHashMap<String, String>();
				while (catalogNavitr.hasNext())
				{
					final CMSLinkComponentModel catalogNavcomponentmodel = (CMSLinkComponentModel) catalogNavitr.next();

					catalog.put(catalogNavcomponentmodel.getLinkName(), catalogNavcomponentmodel.getUrl());

					model.addAttribute("catalogMap", catalog);

				}
			
		}

		final String content = cmsSiteService.getCurrentSite().getContentCatalogs().get(0).getId();
		final CatalogVersionModel catalogVersion = onlineToolsfacade.addCatalogVersions(content);
		LOG.info("catalogVersion for CMS" + catalogVersion.getPk());
		try
		{

			final CMSNavigationNodeModel onlineToolsNode = cmsNavigationService.getNavigationNodeForId("OnlineToolsNavNode");
			if (onlineToolsNode != null)
			{
				final List<CMSLinkComponentModel> onlineToolsNavNoderootNodeLinks = onlineToolsNode.getLinks();
				final Iterator onlineToolsitr = onlineToolsNavNoderootNodeLinks.iterator();
				final Map<String, String> onlineTools = new LinkedHashMap<String, String>();
				while (onlineToolsitr.hasNext())
				{

					final CMSLinkComponentModel onlineToolscomponentmodel = (CMSLinkComponentModel) onlineToolsitr.next();

					onlineTools.put(onlineToolscomponentmodel.getLinkName(), onlineToolscomponentmodel.getUrl());

					model.addAttribute("onlineToolsMap", onlineTools);

				}

			}
			final CMSNavigationNodeModel learningCenterHeaderNode = cmsNavigationService
					.getNavigationNodeForId("LearningCenterNavNode");
			if (learningCenterHeaderNode != null)
			{
				final List<CMSLinkComponentModel> learningCenterHeaderNoderootNodeLinks = learningCenterHeaderNode.getLinks();
				final Iterator learningCenter = learningCenterHeaderNoderootNodeLinks.iterator();
				final Map<String, String> learningCenterlinks = new LinkedHashMap<String, String>();
				while (learningCenter.hasNext())
				{

					final CMSLinkComponentModel learningCenterscomponentmodel = (CMSLinkComponentModel) learningCenter.next();
                                       
					if(learningCenterscomponentmodel.getLinkName(Locale.ENGLISH).equalsIgnoreCase("Overview")){                                        
					learningCenterlinks.put(learningCenterscomponentmodel.getLinkName(), learningCenterscomponentmodel.getUrl());

					model.addAttribute("learningCenterMap", learningCenterlinks);
					}
					
				}

			}
			final CMSNavigationNodeModel supportHeaderNode = cmsNavigationService.getNavigationNodeForId("SupportNavNode");
			if (supportHeaderNode != null)
			{
				final List<CMSLinkComponentModel> supportHeaderNodelinks = supportHeaderNode.getLinks();
				final Iterator supportHeaderlinks = supportHeaderNodelinks.iterator();
				final Map<String, String> supportHeaderlinksMap = new LinkedHashMap<String, String>();
				while (supportHeaderlinks.hasNext())
				{

					final CMSLinkComponentModel supportHeadercomponentmodel = (CMSLinkComponentModel) supportHeaderlinks.next();

					supportHeaderlinksMap.put(supportHeadercomponentmodel.getLinkName(), supportHeadercomponentmodel.getUrl());

					model.addAttribute("supportHeaderMap", supportHeaderlinksMap);

				}

			}
			final CMSNavigationNodeModel aboutUsNavNode = cmsNavigationService.getNavigationNodeForId("AboutUsNavNode");
			if (aboutUsNavNode != null)
			{
				final List<CMSLinkComponentModel> aboutUsNavNodelinks = aboutUsNavNode.getLinks();
				final Iterator aboutUsNavNodelinksitr = aboutUsNavNodelinks.iterator();
				final Map<String, String> aboutUsNavNodelinksMap = new LinkedHashMap<String, String>();
				while (aboutUsNavNodelinksitr.hasNext())
				{

					final CMSLinkComponentModel aboutusNavComponentmodel = (CMSLinkComponentModel) aboutUsNavNodelinksitr.next();

					aboutUsNavNodelinksMap.put(aboutusNavComponentmodel.getLinkName(), aboutusNavComponentmodel.getUrl());

					model.addAttribute("aboutUslinksMap", aboutUsNavNodelinksMap);

				}

			}
			final CMSNavigationNodeModel partsFinderNavNode = cmsNavigationService.getNavigationNodeForId("PartsFinderNavNode");
			if (partsFinderNavNode != null)
			{
				final List<CMSLinkComponentModel> partsFinderNavNodelinks = partsFinderNavNode.getLinks();
				final Iterator partsFindeNavNodelinksitr = partsFinderNavNodelinks.iterator();
				final Map<String, String> partsFindeNavNodelinksMap = new LinkedHashMap<String, String>();
				while (partsFindeNavNodelinksitr.hasNext())
				{

					final CMSLinkComponentModel partsFindeNavComponentmodel = (CMSLinkComponentModel) partsFindeNavNodelinksitr.next();

					partsFindeNavNodelinksMap.put(partsFindeNavComponentmodel.getLinkName(), partsFindeNavComponentmodel.getUrl());

					model.addAttribute("partsFindeNavMap", partsFindeNavNodelinksMap);

				}

			}
			final CMSNavigationNodeModel originalEquipmentNode = cmsNavigationService.getNavigationNodeForId("OriginalEquipmentNavNode");
			if (originalEquipmentNode != null)
			{
				final List<CMSLinkComponentModel> originalEquipmentlinks = originalEquipmentNode.getLinks();
				final Iterator originalEquipmentlinksitr = originalEquipmentlinks.iterator();
				final Map<String, String> originalEquipmentlinksMap = new LinkedHashMap<String, String>();
				while (originalEquipmentlinksitr.hasNext())
				{

					final CMSLinkComponentModel originalEquipmentlinkComponentmodel = (CMSLinkComponentModel) originalEquipmentlinksitr.next();

					originalEquipmentlinksMap.put(originalEquipmentlinkComponentmodel.getLinkName(), originalEquipmentlinkComponentmodel.getUrl());

					model.addAttribute("originalEqupmentMap", originalEquipmentlinksMap);

				}

			}


		}
		catch (final NullPointerException e)
		{
			e.printStackTrace();
		}
		
		storeCmsPageInModel(model, getContentPageForLabelOrId("SITEMAP"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("SITEMAP"));
		
		return ControllerConstants.Views.Pages.Account.SITEMAP;

	}

	

	protected Set<CategoryModel> RootCategories(final Collection<CatalogVersionModel> catalogVersions)
	{
		//System.out.println("*************** Invoking RootCategories Method **************** ");


		final Set<CategoryModel> result = new HashSet();

		for (final CatalogVersionModel catalogVersion : catalogVersions)
		{
			//System.out.println(": Catalog ID : " + catalogVersion.getCatalog().getId());
			//System.out.println(": Catalog Version : " + catalogVersion.getCatalog().getVersion());
			try
			{
				for (final CategoryModel category : catalogVersion.getRootCategories())
				{
					//System.out.println("category Name:  " + category.getName());
					LOG.info("category Name:  " + category.getName());
					result.add(category);
				}
				return result;

			}
			catch (final UnknownIdentifierException localUnknownIdentifierException)
			{
				LOG.warn("Failed to load category from catalog version [" + catalogVersion + "]");
			}
		}

		if (result.isEmpty())
		{
			LOG.error("Failed to load category from catalog version [" + catalogVersions + "]");
		}
		else
		{
			return result;
		}


		return null;
	}


	




}

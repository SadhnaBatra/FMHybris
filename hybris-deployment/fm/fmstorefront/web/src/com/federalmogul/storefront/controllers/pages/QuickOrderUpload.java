/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.enums.ArticleApprovalStatus;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.ordersplitting.WarehouseService;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.keygenerator.impl.PersistentKeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.enums.PartStatus;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.product.PartService;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.QuickOrderUploadForm;
import com.fmo.wom.business.as.ItemASUSCanImpl;
import com.fmo.wom.business.as.WOMServices;
import com.fmo.wom.common.exception.WOMBaseCheckedException;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.WeightBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;


/**
 * @author mamud
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping("/orderupload")
public class QuickOrderUpload extends AbstractPageController
{

	private static final Logger LOG = Logger.getLogger(QuickOrderUpload.class);

	public static final String PAGE_ROOT = "pages/";
	public static final String ROOT = "/";

	public static final String CMS_PAGE_MODEL = "cmsPage";
	public static final String CMS_PAGE_TITLE = "pageTitle";
	private static final String FMORDER_HISTORY_CMS_PAGE = "OrderHistoryPage";

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMdd");

	private static final String VALID_FILE_INPUT = "^.+[~;,][0-9]+$";
	private static final String FILE_DELIMITERS = "[~;,]";


	B2BUnitData shipToUnit = null;
	AddressData shipToAddress = new AddressData();
	AddressData soldToAddress = new AddressData();
	B2BUnitData soldToUnit = null;

	@Deprecated
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "cartFacade")
	private de.hybris.platform.b2bacceleratorfacades.api.cart.CartFacade b2bCartFacade;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	@Resource(name = "partService")
	private PartService partService;

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UploadOrderFacade uploadorderFacade;

	@Autowired
	private CommonI18NService commonI18NService;
	@Autowired
	private UserService userService;

	@Autowired
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource
	private WarehouseService warehouseService;

	public static final String SUCCESSFUL_MODIFICATION_CODE = "success";
	private static final String ERROR_MSG_TYPE = "errorMsg";

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource
	private ProductService productService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource
	CatalogVersionService catalogVersionService;

	@Resource
	private PersistentKeyGenerator orderCodeGenerator;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Autowired
	private CommerceCartService commerceCartService;

	@RequestMapping(method = RequestMethod.GET, value = "/add/{code}/{qty}/{prevPart}/{preQty}/{partFlag}")
	public String addToCart(final Model model, @PathVariable("code") final String code,
			@PathVariable("qty") final String quantity, @PathVariable("prevPart") final String prevPart,
			@PathVariable("preQty") final String preQuantity, @PathVariable("partFlag") final String partFlag)
	{
		try
		{

			final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
			final String prevPartCode = (prevPart != null && !"null".equalsIgnoreCase(prevPart)) ? prevPart.toUpperCase() : null;
			final String partFlagCode = (partFlag != null && !"null".equalsIgnoreCase(partFlag)) ? partFlag.toUpperCase() : null;
			String partCode = code.toUpperCase();
			final ItemBO itemBO = getItemBO(partCode, null, null, quantity);
			itemBO.setLineNumber(1);
			if (partFlagCode != null && null != prevPartCode && prevPartCode.equalsIgnoreCase(partCode))
			{
				itemBO.setProductFlag(partFlagCode);
				partCode = partFlagCode + partCode;
			}
			itemBOList.add(itemBO);

			final List<ItemBO> result = womQuickUploadOrder(itemBOList);//itemBOList
			final int qty = Integer.parseInt(quantity);
			final int preQty = Integer.parseInt(preQuantity);
			if (prevPart != null && code.equalsIgnoreCase(prevPart))
			{
				if (preQty != 0 && preQty != qty)
				{
					partModification(partCode, "0");
				}
			}
			else
			{
				if (prevPartCode != null)
				{
					partModification(partFlag != null ? partFlagCode + prevPartCode : prevPartCode, "0");
				}
				/*
				 * else { partModification(code.toUpperCase(), "0"); }
				 */
			}
			String issues = null;
			boolean reslovedPart = true;
			String partProblem = null;
			for (final ItemBO item : result)
			{
				if (item.hasProblemItem())
				{
					final List<ProblemBO> problemBOList = item.getProblemItemList();
					for (final ProblemBO problem : problemBOList)
					{
						LOG.info("resolvedItem : " + item.getPartNumber() + "message :" + problem.getMessageKey());
						LOG.info("resolvedItem : " + item.getPartNumber() + "Status :" + problem.getStatusCategory().name());
						if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(problem.getMessageKey()))
						{
							issues = getMessageSource().getMessage(problem.getMessageKey(), null, getI18nService().getCurrentLocale());
							model.addAttribute("MULITPLE_PARTS_SIZE", problem.getCorrectiveOptions().size());
							model.addAttribute("MULITPLE_PARTS_FOUND", problem.getCorrectiveOptions());
							reslovedPart = false;
						}
						if (null != problem.getStatusCategory() && !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
						{
							issues = getMessageSource().getMessage(problem.getMessageKey(), null, getI18nService().getCurrentLocale());
							reslovedPart = false;
						}
						if (issues != null)
						{
							if (partProblem == null)
							{
								partProblem = issues;
							}
							else
							{
								partProblem = partProblem + "~" + issues;
							}
						}
					}
					if (partProblem != null)
					{
						model.addAttribute("errorMSG", issues);
					}

				}
			}
			if (reslovedPart)
			{
				prepareItemforCart(result, false);
				createProductList(model, result);
			}
		}
		catch (final CMSItemNotFoundException e)
		{
			LOG.error("CMSItemNotFoundException::" + e.getMessage());
		}
		return "pages/fm/ajax/orderDetails";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addPI")
	public String addToCartPI(final Model model, final HttpServletRequest request,
			@RequestParam("piPartNumbers") final String[] partNumbers, @RequestParam("piQtys") final String[] qtys)
			throws CMSItemNotFoundException
	{

		final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
		final List<ItemBO> piItemBOList = new ArrayList<ItemBO>();
		final List<ItemBO> piResItemBOList = new ArrayList<ItemBO>();
		int lineNumber = 1;
		for (int i = 0; i < partNumbers.length; i++)
		{
			boolean isNabsProduct = false;
			boolean isCatalogProduct = true;
			FMPartModel productModel = null;
			try
			{
				productModel = partService.getPartForCode(partNumbers[i].toUpperCase());
			}
			catch (final UnknownIdentifierException e)
			{
				LOG.error("Part Is not Found in the catalog");
				isCatalogProduct = false;
			}
			if (null != productModel && isCatalogProduct)
			{
				for (final FMCorporateModel corp : productModel.getCorporate())
				{
					if ("FELPF".equalsIgnoreCase(corp.getCorpcode()) || "SPDPF".equalsIgnoreCase(corp.getCorpcode())
							|| "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
					{
						isNabsProduct = true;
					}
				}
			}

			for (int j = 0; j < qtys.length; j++)
			{
				if (i == j && Integer.parseInt(qtys[j]) > 0)
				{
					try
					{
						final ItemBO anItem = new PartBO();
						//Part is found in catalog
						if (null != productModel && isCatalogProduct)
						{
							if (isNabsProduct)
							{
								anItem.setPartNumber(productModel.getRawPartNumber());
								anItem.setDisplayPartNumber(productModel.getRawPartNumber());
								anItem.setProductFlag(productModel.getProductFlag());
								anItem.setExternalSystem(ExternalSystem.NABS);
							}
							else
							{
								anItem.setPartNumber(productModel.getCode());
								anItem.setDisplayPartNumber(productModel.getCode());
								anItem.setExternalSystem(ExternalSystem.EVRST);
							}
						}
						//Part is Not found in catalog
						else
						{
							anItem.setPartNumber(partNumbers[i].toUpperCase());
							anItem.setDisplayPartNumber(partNumbers[i].toUpperCase());
						}
						anItem.setItemQty(getQuantityBO(qtys[j]));
						anItem.setLineNumber(lineNumber);
						itemBOList.add(anItem);
						piItemBOList.add(anItem);
						lineNumber++;
					}
					catch (final Exception e)
					{
						// YTODO Auto-generated catch block
						LOG.error(e.getMessage());
					}
					break;
				}
			}
		}
		final CartData cartData = cartFacade.getSessionCart();
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				final ItemBO itemBO = getItemBO(entry.getProduct().getCode(), null, null, String.valueOf(entry.getQuantity())
						.toString());
				itemBO.setLineNumber(lineNumber);
				itemBOList.add(itemBO);
				lineNumber++;
			}
		}
		final List<ItemBO> result = womQuickUploadOrder(itemBOList);
		for (final ItemBO piItem : piItemBOList)
		{
			for (final ItemBO resItem : result)
			{
				if ((resItem.getPartNumber().equalsIgnoreCase(piItem.getPartNumber()) || resItem.getDisplayPartNumber()
						.equalsIgnoreCase(piItem.getDisplayPartNumber()))
						&& resItem.getItemQty().getRequestedQuantity() == piItem.getItemQty().getRequestedQuantity())
				{
					piResItemBOList.add(resItem);
					break;
				}
			}
		}
		prepareItemforCart(piResItemBOList, false);
		createProductList(model, result);
		model.addAttribute("quickOrderUpload", new QuickOrderUploadForm());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Quick Order "));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMQuickOrderPage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/addmultimatch/{partNumber}/{qty}/{productFlag}/{brandState}")
	public String addPartToCart(final Model model, @PathVariable("partNumber") final String partNumber,
			@PathVariable("qty") final String quantity, @PathVariable("productFlag") final String productFlag,
			@PathVariable("brandState") final String brandState)
	{
		try
		{
			final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
			final ItemBO item = new PartBO();
			item.setDisplayPartNumber(partNumber);
			item.setProductFlag(productFlag);
			item.setBrandState(brandState);
			item.setItemQty(getQuantityBO(quantity));
			item.setLineNumber(1);
			itemBOList.add(item);
			final List<ItemBO> result = womQuickUploadOrder(itemBOList);
			prepareItemforCart(result, true);
			createProductList(model, result);

			final List<ItemBO> multiMatchItemBO = (List<ItemBO>) getSessionService().getAttribute("multiMatchPart");

			if (multiMatchItemBO != null && !multiMatchItemBO.isEmpty())
			{
				//multiMatchItemBO.addAll(result);
				result.addAll(multiMatchItemBO);
				getSessionService().setAttribute("multiMatchPart", result);
			}
			else
			{
				getSessionService().setAttribute("multiMatchPart", result);
			}
		}
		catch (final CMSItemNotFoundException e)
		{
			LOG.error("CMSItemNotFoundException::" + e.getMessage());
		}
		return "pages/fm/ajax/orderDetails";
	}


	//Listing Page AddtoCart

	@RequestMapping(method = RequestMethod.GET, value = "/category/{partNumber}/{qty}")
	public String addToCart(final Model model, @PathVariable("partNumber") final String partNumber,
			@PathVariable("qty") final String qty)
	{
		try
		{

			boolean isNabsProduct = false;
			final FMPartModel productModel = partService.getPartForCode(partNumber.toUpperCase());
			for (final FMCorporateModel corp : productModel.getCorporate())
			{
				if ("FELPF".equalsIgnoreCase(corp.getCorpcode()) || "SPDPF".equalsIgnoreCase(corp.getCorpcode())
						|| "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
				{
					isNabsProduct = true;
				}
			}
			final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
			final ItemBO anItem = new PartBO();
			if (isNabsProduct)
			{
				anItem.setPartNumber(productModel.getRawPartNumber());
				anItem.setDisplayPartNumber(productModel.getPartNumber());
				anItem.setProductFlag(productModel.getProductFlag());
				anItem.setExternalSystem(ExternalSystem.NABS);
			}
			else
			{
				anItem.setPartNumber(productModel.getCode());
				anItem.setDisplayPartNumber(productModel.getCode());
				anItem.setExternalSystem(ExternalSystem.EVRST);
				//anItem.setProductFlag(productModel.getProductFlag());
			}
			anItem.setItemQty(getQuantityBO(qty));
			anItem.setLineNumber(1);
			itemBOList.add(anItem);
			final List<ItemBO> result = womQuickUploadOrder(itemBOList);
			prepareItemforCart(result, false);
			createProductList(model, result);
		}
		catch (final CMSItemNotFoundException e)
		{
			LOG.error("CMSItemNotFoundException::" + e.getMessage());
		}
		return "pages/fm/ajax/orderDetails";
	}




	private boolean partModification(final String partNumber, final String qty)
	{
		boolean isPartModified = false;
		try
		{
			final CartData cartData = cartFacade.getSessionCart();
			if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{
					if (entry.getProduct().getCode().equalsIgnoreCase(partNumber))
					{
						entry.setQuantity(Long.parseLong(qty));
						final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(entry);
						LOG.info("Cart Modification message" + cartModification.getStatusMessage());
						isPartModified = true;
						break;

					}
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error("Exception::" + e.getMessage());
		}
		LOG.info("removePart :: isPartModified ==>" + isPartModified);
		return isPartModified;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/remove/{partNumber}")
	public String removePartFromCart(final Model model, @PathVariable("partNumber") final String partNumber)

	{
		LOG.info("Remove Part from Cart :: " + partNumber);
		final boolean isRemovedPart = partModification(partNumber, "0");
		LOG.info("isRemovedPart ==>" + isRemovedPart);
		model.addAttribute("removePart", "Sucess");
		return "pages/fm/ajax/orderDetails";
	}

	@RequestMapping(value = "/upload-order-file", method = RequestMethod.POST)
	public String quickUploadOrder(final Model model,
			@ModelAttribute("quickOrderUpload") final QuickOrderUploadForm quickOrderUploadForm) throws CMSItemNotFoundException
	{
		LOG.info("Upload File Info ==>" + quickOrderUploadForm.getQuickOrderFile().getName());
		final MultipartFile uploadFile = quickOrderUploadForm.getQuickOrderFile();
		List<ItemBO> item = null;
		try
		{
			item = processFile(uploadFile.getInputStream());
			model.addAttribute("item", item);
			createProductList(model, item);
		}
		catch (final WOMProcessException e)
		{
			LOG.error("WOMProcessException::" + e.getMessage());
		}
		catch (final IOException e)
		{
			LOG.error("IOException::" + e.getMessage());
		}
		catch (final WOMBaseCheckedException e)
		{
			LOG.error("WOMBaseCheckedException::" + e.getMessage());
		}
		model.addAttribute("quickOrderUpload", new QuickOrderUploadForm());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Quick Order "));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMQuickOrderPage;
	}



	@RequestMapping(value = "/quick-order", method = RequestMethod.GET)
	@RequireHardLogIn
	public String quickOrder(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Inside Quick Order Method ==>");

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Quick Order"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("quickOrderUpload", new QuickOrderUploadForm());
		model.addAttribute("clickedlink", "ClickedQuickOrder");
		final CartData cartData = cartFacade.getSessionCart();
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
			final List<ItemBO> multiMatchItemBO = (List<ItemBO>) getSessionService().getAttribute("multiMatchPart");
			for (final OrderEntryData entry : cartData.getEntries())
			{
				//ItemBO itemBO = new PartBO();

				ItemBO multiItem = null;
				if (multiMatchItemBO != null && !multiMatchItemBO.isEmpty())
				{
					for (final ItemBO item : multiMatchItemBO)
					{
						String displayPartNumber = item.getDisplayPartNumber();
						if ((item.getExternalSystem() != null && item.getExternalSystem().equals(ExternalSystem.NABS))
								|| (null != item.getProductFlag()))
						{
							displayPartNumber = item.getProductFlag() + item.getDisplayPartNumber();
						}
						if (item.getDisplayPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
								|| item.getPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
								|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode()))
						{
							multiItem = item;
							multiItem.setLineNumber(entry.getEntryNumber() + 1);
							multiItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
						}
					}
				}
				boolean catalogProduct = true;
				boolean isDummyPart = false;
				final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
				for (final FMCorporateModel corp : productModel.getCorporate())
				{
					if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
							|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
					{
						catalogProduct = false;
					}
					if ("dummy".equalsIgnoreCase(corp.getCorpcode()))
					{
						isDummyPart = true;
					}
				}
				String displayPartNumber = productModel.getPartNumber();
				if (isDummyPart && productModel.getProductFlag() != null && null == multiItem)
				{
					displayPartNumber = displayPartNumber.substring(1);
					System.err.println("displayPartNumber :: " + displayPartNumber);
				}
				if (!catalogProduct && null != productModel.getFlagcode() && !isDummyPart)
				{
					displayPartNumber = productModel.getRawPartNumber();
				}
				final ItemBO itemBO = getItemBO(!catalogProduct ? displayPartNumber : productModel.getCode(), null, null, String
						.valueOf(entry.getQuantity()).toString());
				if (!catalogProduct && null != productModel.getFlagcode() && !isDummyPart)
				{
					itemBO.setProductFlag(productModel.getFlagcode());
					itemBO.setExternalSystem(ExternalSystem.NABS);
				}

				if (isDummyPart && productModel.getProductFlag() != null && null == multiItem)
				{
					itemBO.setProductFlag(productModel.getProductFlag());
					itemBO.setExternalSystem(ExternalSystem.NABS);
				}
				else
				{
					itemBO.setExternalSystem(ExternalSystem.EVRST);
				}
				itemBO.setLineNumber(entry.getEntryNumber() + 1);
				if (multiItem != null)
				{
					LOG.info("multiItem ==>" + multiItem);
					itemBOList.add(multiItem);
				}
				else
				{
					itemBOList.add(itemBO);
				}

			}
			final List<ItemBO> result = womQuickUploadOrder(itemBOList);
			createProductList(model, result);
		}
		else
		{
			model.addAttribute("cartData", cartData);
		}
		return ControllerConstants.Views.Pages.Account.FMQuickOrderPage;
	}

	@SuppressWarnings("null")
	protected void createProductList(final Model model, final List<ItemBO> items) throws CMSItemNotFoundException
	{
		final CartData cartData = cartFacade.getSessionCart();
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			final List<OrderEntryData> entryies = new ArrayList<OrderEntryData>();
			for (final OrderEntryData entry : cartData.getEntries())
			{
				String code = entry.getProduct().getCode();
				boolean catalogProduct = true;
				final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
				for (final FMCorporateModel corp : productModel.getCorporate())
				{
					if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
							|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
					{
						catalogProduct = false;
					}
				}
				if (!catalogProduct)
				{
					code = null != productModel.getFlagcode() ? productModel.getFlagcode().toUpperCase()
							+ productModel.getRawPartNumber() : productModel.getCode();
				}
				for (final ItemBO item : items)
				{
					String displayPartNumber = item.getDisplayPartNumber();
					if ((item.getExternalSystem() != null && item.getExternalSystem().equals(ExternalSystem.NABS))
							|| (null != item.getProductFlag()))
					{
						displayPartNumber = item.getProductFlag() + item.getDisplayPartNumber();
					}
					final List<ProblemBO> problemBOList = item.getProblemItemList();
					if (entry.getProduct().getCode().equalsIgnoreCase(item.getPartNumber())
							|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode())
							|| (code.equalsIgnoreCase(displayPartNumber)))
					{
						model.addAttribute("RawPartNumber", item.getDisplayPartNumber());
						model.addAttribute("DispPartNumber", item.getPartNumber());
						model.addAttribute("Description",
								entry.getProduct().getName() != null ? entry.getProduct().getName() : item.getDescription());
						model.addAttribute("partFlag", item.getProductFlag());

						entry.getProduct().setPartNumber(!catalogProduct ? productModel.getRawPartNumber() : productModel.getCode());
						entry.getProduct().setPartFlag(item.getProductFlag());
						//entry.getProduct().setCode(!catalogProduct ? productModel.getRawPartNumber() : productModel.getCode());
						model.addAttribute("itemPrice", entry.getBasePrice());
						LOG.info("item.isKit() :: " + item.isKit());
						if (item.hasProblemItem() || item.isKit())
						{
							String partProblem = null;
							if (item.isKit())
							{
								partProblem = getMessageSource().getMessage(MessageResourceUtil.SOLD_IN_SETS_ONLY, null,
										getI18nService().getCurrentLocale());
								LOG.info("Inside isKit Con Problem::" + partProblem);
							}
							if (problemBOList != null && !problemBOList.isEmpty())
							{
								for (final ProblemBO problem : problemBOList)
								{

									LOG.info("resolvedItem : " + item.getPartNumber() + "message :" + problem.getMessageKey());
									LOG.info("resolvedItem : " + item.getPartNumber() + "Status :" + problem.getStatusCategory().name());
									String issues = null;
									String vintageissue=null;
									if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(problem.getMessageKey()))
									{
										final int qty = roundUpToMultiplesQuantity(item.getItemQty().getRequestedQuantity(), item
												.getItemQty().getSoldInMultipleQuantity());
										final String[] parms =
										{ item.getDisplayPartNumber(),
												String.valueOf(item.getItemQty().getSoldInMultipleQuantity()).toString() };
										issues = getMessageSource().getMessage(problem.getMessageKey(), parms,
												getI18nService().getCurrentLocale())
												+ "@" + qty;
									}
									if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
										model.addAttribute("MULITPLE_PARTS_SIZE", problem.getCorrectiveOptions().size());
										model.addAttribute("MULITPLE_PARTS_FOUND", problem.getCorrectiveOptions());
										entry.setMultiSelect(problem.getCorrectiveOptions());
									}
									if (MessageResourceUtil.PART_SUPERCEDED.equals(problem.getMessageKey()))
									{
										final String[] parms =
										{ item.getDisplayPartNumber(), item.getPartNumber() };
										issues = getMessageSource().getMessage(problem.getMessageKey(), parms,
												getI18nService().getCurrentLocale());
										model.addAttribute("RawPartNumber", item.getPartNumber());
										model.addAttribute("DispPartNumber", item.getDisplayPartNumber());
									}
									if (MessageResourceUtil.REASONABLE_QUANTITY_THRESHOLD.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.NO_INVENTORY.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.OBSOLETE.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.OBSOLETE_NO_RETURN_ALLOWED.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.OBSOLETE_RETURN_ALLOWED.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.NO_RETURN.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.NOT_ALLOWED_TO_ORDER.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.PART_BEING_DISCONTINUED.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.SOLD_IN_SETS_ONLY.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.PART_IS_PRERELEASE.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.NO_ITEM_CATEGORY_AVAILABLE.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.MISSING_PRICE.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (MessageResourceUtil.VINTAGE_PART.equals(problem.getMessageKey()))
									{
										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
										if(item.getItemQty().getSoldInMultipleQuantity()>1 &&  (item.getItemQty().getRequestedQuantity()%item.getItemQty().getSoldInMultipleQuantity()) != 0){										
										final int qty = roundUpToMultiplesQuantity(item.getItemQty().getRequestedQuantity(), item
												.getItemQty().getSoldInMultipleQuantity());
										final String[] parms =
										{ item.getDisplayPartNumber(),
												String.valueOf(item.getItemQty().getSoldInMultipleQuantity()).toString() };
										if(issues==null){
										issues = getMessageSource().getMessage(MessageResourceUtil.SOLD_IN_MULTIPLES, parms,
												getI18nService().getCurrentLocale())
												+ "@" + qty;
										}else{
											
											vintageissue=getMessageSource().getMessage(MessageResourceUtil.SOLD_IN_MULTIPLES, parms,
													getI18nService().getCurrentLocale())
													+ "@" + qty;
										}
										issues=issues+ "~" + vintageissue;
										
									}
								}
							if (null != problem.getStatusCategory()
											&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
									{

										issues = getMessageSource().getMessage(problem.getMessageKey(), null,
												getI18nService().getCurrentLocale());
									}
									if (issues != null)
									{
										if (partProblem == null)
										{
											partProblem = issues;
										}
										else
										{
											partProblem = partProblem + "~" + issues;
										}
									}
								}
							}
							if (partProblem != null)
							{
								entry.setErrorMessage(partProblem);
								model.addAttribute("errorMSG", partProblem);
							}
						}

					}
				}
				entryies.add(entry);
			}
			cartData.setEntries(entryies);
			model.addAttribute("cartData", cartData);
			addItemBOToSession(items);
		}
		else
		{
			LOG.info("No cart data");
		}
	}

	@Override
	protected void storeCmsPageInModel(final Model model, final AbstractPageModel cmsPage)
	{
		if (model != null && cmsPage != null)
		{
			model.addAttribute(CMS_PAGE_MODEL, cmsPage);
			if (cmsPage instanceof ContentPageModel)
			{
				storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(cmsPage.getTitle()));
			}
		}
	}

	@Override
	protected void setUpMetaDataForContentPage(final Model model, final ContentPageModel contentPage)
	{
		setUpMetaData(model, contentPage.getKeywords(), contentPage.getDescription());
	}

	protected OrderEntryData getOrderEntryData(final long quantity, final String productCode, final Integer entryNumber)
	{
		final OrderEntryData orderEntry = new OrderEntryData();
		orderEntry.setQuantity(quantity);
		orderEntry.setProduct(new ProductData());
		orderEntry.getProduct().setCode(productCode);
		orderEntry.setEntryNumber(entryNumber);
		return orderEntry;
	}

	protected void addStatusMessages(final Model model, final CartModificationData modification)
	{
		final boolean hasMessage = StringUtils.isNotEmpty(modification.getStatusMessage());
		if (hasMessage)
		{
			if (SUCCESSFUL_MODIFICATION_CODE.equals(modification.getStatusCode()))
			{
				GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, modification.getStatusMessage(), null);
			}
			else if (!model.containsAttribute(ERROR_MSG_TYPE))
			{
				GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, modification.getStatusMessage(), null);
			}
		}
	}

	public void createPriceRowForUser(final Double price, final FMPartModel part, final String currency, final WeightBO weight)
	{
		final UserModel user = userService.getCurrentUser();
		final Collection<PriceRowModel> partPrices = new LinkedList<PriceRowModel>();
		final Collection<PriceRowModel> pricerowModels = part.getEurope1Prices();
		for (final PriceRowModel pricerowModel : pricerowModels)
		{
			if (pricerowModel.getUser() != null && pricerowModel.getUser().getUid().equalsIgnoreCase(user.getUid()))
			{
				modelService.remove(pricerowModel.getPk());
			}
			partPrices.add(pricerowModel);
		}
		modelService.refresh(part);
		final PriceRowModel priceRowModel = modelService.create(PriceRowModel.class);
		priceRowModel.setProduct(part);
		priceRowModel.setCatalogVersion(part.getCatalogVersion());
		//if (price != 0.0)
		if (getSessionService().getAttribute("logedUserType").equals("ShipTo"))
		{
			priceRowModel.setPrice(0.0);
		}
		else
		{
			if (Double.doubleToLongBits(price) != 0)
			{
				priceRowModel.setPrice(price);
			}
			else
			{
				priceRowModel.setPrice(0.0);
			}
		}		/*
		 * if (currency != null && ("CAD").equalsIgnoreCase(currency)) {
		 * priceRowModel.setCurrency(commonI18NService.getCurrency(currency)); } else {
		 * priceRowModel.setCurrency(commonI18NService.getCurrency("USD")); }
		 */
		priceRowModel.setCurrency(commonI18NService.getCurrency("USD"));
		UnitModel unit = productService.getUnit("lbs");
		if (unit == null)
		{
			unit = createUnit("lbs", "pound", "FM-pound", 1.0);
		}
		priceRowModel.setUnit(unit);
		priceRowModel.setUnitFactor(1);
		priceRowModel.setUser(user);
		partPrices.add(priceRowModel);
		part.setEurope1Prices(partPrices);
		part.setUnit(unit);
		part.setWeightUOM(String.valueOf(weight.getWeight()).toString());
		modelService.save(part);
	}

	private List<ItemBO> processFile(final InputStream data) throws IOException, WOMBaseCheckedException, WOMProcessException
	{

		final List<ItemBO> itemBOList = buildItemBOListFromFile(data);
		final List<ItemBO> cartItemBOList = new ArrayList<ItemBO>();
		//cart change
		int lineNumber = itemBOList.size() + 1;
		final CartData cartData = cartFacade.getSessionCart();
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				boolean catalogProduct = true;
				boolean isDummyPart = false;
				final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
				for (final FMCorporateModel corp : productModel.getCorporate())
				{
					if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
							|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
					{
						catalogProduct = false;
					}
					if ("dummy".equalsIgnoreCase(corp.getCorpcode()))
					{
						isDummyPart = true;
					}
				}

				String displayPartNumber = !catalogProduct ? productModel.getPartNumber() : productModel.getCode();
				if (isDummyPart && productModel.getProductFlag() != null)
				{
					displayPartNumber = displayPartNumber.substring(1);
				}
				boolean isCartProduct = false;
				for (final ItemBO item : itemBOList)
				{
					if (entry.getProduct().getCode().equalsIgnoreCase(item.getPartNumber())
							|| item.getDisplayPartNumber().equalsIgnoreCase(displayPartNumber))
					{
						isCartProduct = true;
					}
				}
				if (!isCartProduct)
				{
					final ItemBO itemBO = getItemBO(entry.getProduct().getCode(), null, null, String.valueOf(entry.getQuantity())
							.toString());
					itemBO.setLineNumber(lineNumber);
					cartItemBOList.add(itemBO);
					lineNumber++;
				}
			}
		}
		cartItemBOList.addAll(itemBOList);
		final List<ItemBO> result = womQuickUploadOrder(cartItemBOList);
		final List<ItemBO> CartResult = new ArrayList<ItemBO>();
		for (final ItemBO item : result)
		{
			for (final ItemBO inputItem : itemBOList)
			{
				if (item.getDisplayPartNumber().equalsIgnoreCase(inputItem.getDisplayPartNumber()))
				{
					CartResult.add(item);
				}
			}
		}
		prepareItemforCart(CartResult, false);
		return result;
	}

	private void prepareItemforCart(final List<ItemBO> result, boolean isMultipleMatchPart)
	{
		if (result != null)
		{
			for (final ItemBO item : result)
			{
				FMPartModel part = null;
				boolean isSupercededPart = false;
				int qty = item.getItemQty().getRequestedQuantity();
				if (item.hasProblemItem())
				{
					final List<ProblemBO> problemBOList = item.getProblemItemList();
					for (final ProblemBO problem : problemBOList)
					{
						if (MessageResourceUtil.PART_SUPERCEDED.equals(problem.getMessageKey()))
						{
							isSupercededPart = true;

						}
						if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(problem.getMessageKey()))
						{

							final int cqty = roundUpToMultiplesQuantity(item.getItemQty().getRequestedQuantity(), item.getItemQty()
									.getSoldInMultipleQuantity());
							qty = cqty;
						}


						if (MessageResourceUtil.VINTAGE_PART.equals(problem.getMessageKey()))
						{
						if(item.getItemQty().getSoldInMultipleQuantity()>1 && (item.getItemQty().getRequestedQuantity() % item.getItemQty().getSoldInMultipleQuantity())!=0){

							final int cqty = roundUpToMultiplesQuantity(item.getItemQty().getRequestedQuantity(),
									item.getItemQty().getSoldInMultipleQuantity());
							qty = cqty;
							}
					  }
					}
				}
				String displayPartNumber = item.getDisplayPartNumber();
				if ((item.getExternalSystem() != null && item.getExternalSystem().equals(ExternalSystem.NABS))
						|| (null != item.getProductFlag()))
				{
					displayPartNumber = null != item.getProductFlag() ? item.getProductFlag() + item.getDisplayPartNumber() : item
							.getDisplayPartNumber();
					isMultipleMatchPart = true;
				}
				try
				{
					LOG.info("isMultipleMatchPart Part " + isMultipleMatchPart + "displayPartNumber ::" + displayPartNumber);
					part = partService.getPartForCode(isSupercededPart ? item.getPartNumber() : displayPartNumber);
				}
				catch (final UnknownIdentifierException e)
				{
					LOG.info("Part Not Found in system");

					part = createPart(item, isSupercededPart, isMultipleMatchPart);
				}
				if (item.getPartNumber().equalsIgnoreCase(part.getCode())
						|| item.getDisplayPartNumber().equalsIgnoreCase(part.getCode())
						|| displayPartNumber.equalsIgnoreCase(part.getCode()))
				{
					//BUG:UAT create stocks for the  product on runtime.
					if (part.getStockLevels().isEmpty())
					{
						createStockLevel(part);
					}
					final WeightBO weight = item.getItemWeight();
					if (item.getItemPrice() != null)
					{
						final PriceBO priceBO = item.getItemPrice();
						try
						{
							uploadorderFacade.createPriceRowForUser(priceBO.getDisplayPrice(), part, priceBO.getCurrency().getCode(), weight);
							//createPriceRowForUser(priceBO.getDisplayPrice(), part, priceBO.getCurrency().getCode(), weight);
						}
						catch (final UnknownIdentifierException e)
						{
							LOG.info("createPriceRowForUser : Price is not able to create for this product");
						}
					}
				}
				try
				{
					final CartModificationData modification = b2bCartFacade.addOrderEntry(getOrderEntryData(Long.valueOf(qty)
							.longValue(), part.getCode(), null));
					LOG.info("modification" + modification);
				}
				catch (final UnknownIdentifierException e)
				{
					LOG.info("Error inside the function");
				}
			}
			recalculate();
		}

	}

	private List<ItemBO> buildItemBOListFromFile(final InputStream data) throws IOException
	{
		final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
		String strLine;
		String[] tokens;
		int lineNumber = 1;
		try
		{
			final BufferedReader br = new BufferedReader(new InputStreamReader(data));
			while ((strLine = br.readLine()) != null)
			{

				if (isValidInput(strLine))
				{
					tokens = strLine.split(FILE_DELIMITERS);
					final String partNumber = tokens[0].toUpperCase();
					final String qty = tokens[1];
					//addItem(partNumber, qty, lineNumber);

					//ItemBO itemBO = new PartBO();
					final ItemBO itemBO = getItemBO(partNumber.toUpperCase(), null, null, qty);
					itemBO.setLineNumber(lineNumber);
					itemBOList.add(itemBO);
				}

				else
				{
					final ItemBO itemBO = getItemBO("invalidPart", null, null, "1");
					itemBO.setLineNumber(lineNumber);
					itemBOList.add(itemBO);
					//addItem("invalidPart", "1", lineNumber);
				}
				lineNumber++;

			}
			br.close();


		}
		catch (final IOException e)
		{
			LOG.error("Exception while trying to upload a file: {}." + e.getMessage());
			throw new IOException(e);
		}
		finally
		{
			LOG.debug("buildItemBOListFromFile() END");
		}
		return itemBOList;
	}

	/*
	 * private void addItem(final String partNumber, final String qty, final int lineNumber) { ItemBO itemBO = new
	 * ItemBO(); itemBO = getItemBO(partNumber, null, null, qty); itemBO.setLineNumber(lineNumber);
	 * //itemBOList.add(itemBO); }
	 */

	private ItemBO getItemBO(final String partNumber, final String productFlag, final String brandState, final String qty)
	{
		final ItemBO item = new PartBO();
		item.setDisplayPartNumber(partNumber);
		item.setProductFlag(productFlag);
		item.setBrandState(brandState);
		item.setItemQty(getQuantityBO(qty));
		//item.setLineNumber(1);
		return item;
	}

	public QuantityBO getQuantityBO(final String qty)
	{
		final QuantityBO quantity = new QuantityBO();
		quantity.setRequestedQuantity(Integer.parseInt(qty));
		return quantity;
	}

	private QuantityBO getDefaultQuanity(final long quantity)
	{
		final int iquantity = Long.valueOf(quantity).intValue();
		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(iquantity);
		return qty;
	}

	private boolean isValidInput(final String input)
	{
		boolean isValid = false;

		final Pattern pattern = Pattern.compile(VALID_FILE_INPUT);
		final Matcher matcher = pattern.matcher(input);

		if (matcher.find())
		{
			LOG.debug("isValidInput():IS_VALID: {} :: " + input);
			isValid = true;
		}
		else
		{
			LOG.debug("isValidInput():IS_NOT_VALID: {} ::" + input);
		}

		return isValid;
	}

	//Creating Missing Products in System

	private FMPartModel createPart(final ItemBO item, final boolean isSupercededPart, final boolean isMultipleMatchPart)
	{
		final FMPartModel dummypart = partService.getPartForCode("dummy");
		final FMPartModel part = modelService.create(FMPartModel.class);
		part.setApprovalStatus(ArticleApprovalStatus.APPROVED);
		final String displayPartNumber = (isMultipleMatchPart && null != item.getProductFlag()) ? item.getProductFlag()
				+ item.getDisplayPartNumber() : item.getDisplayPartNumber();
		part.setPartNumber(displayPartNumber);
		part.setRawPartNumber(item.getPartNumber());

		LOG.info("createPart :: isMultipleMatchPart " + isMultipleMatchPart + "displayPartNumber ::" + displayPartNumber);
		part.setCode(isSupercededPart ? item.getPartNumber() : displayPartNumber);
		part.setName(item.getDescription());
		if (null != item.getProductFlag())
		{
			part.setProductFlag(item.getProductFlag());
			part.setFlagcode(item.getProductFlag());
		}
		part.setCatalogVersion(dummypart.getCatalogVersion());
		part.setPartstatus(PartStatus.ACTIVEPART);
		part.setCorporate(dummypart.getCorporate());
		modelService.save(part);
		createStockLevel(part);
		return part;
	}

	private void createStockLevel(final FMPartModel part)
	{
		final WarehouseModel wareHouseModel = warehouseService.getWarehouseForCode("federalmogul");
		LOG.info("Ware House ==>" + siteConfigService.getString("fm.part.warehouse", "default"));
		final StockLevelModel stockLevel = modelService.create(StockLevelModel.class);
		stockLevel.setProductCode(part.getCode());
		stockLevel.setProduct(part);
		stockLevel.setWarehouse(wareHouseModel);
		stockLevel.setAvailable(100000);
		stockLevel.setOverSelling(0);
		stockLevel.setReserved(0);
		//stockLevel.setInStockStatus(status);
		modelService.save(stockLevel);
	}

	private UnitModel createUnit(final String code, final String name, final String type, final Double conversion)
	{
		final UnitModel unit = modelService.create(UnitModel.class);
		unit.setCode(code);
		unit.setName(name);
		unit.setUnitType(type);
		unit.setConversion(conversion);
		modelService.save(unit);
		return unit;
	}


	protected List<ItemBO> womQuickUploadOrder(final List<ItemBO> anItemBOList)
	{
		final ItemASUSCanImpl partResolutionservice = WOMServices.getPartResolutionService();
		//unitDetails();
		List<ItemBO> itemsOrderable = new ArrayList<ItemBO>();
		try
		{
			itemsOrderable = partResolutionservice.checkOrderable(getPONumber(), anItemBOList, getBillTOAccount(),
					getShiptoAccount());
			LOG.info("womQuickUploadOrder :: itemsOrderable ==>" + itemsOrderable);

		}
		catch (WOMExternalSystemException | WOMValidationException e)
		{
			LOG.error("WOMExternalSystemException::" + e.getMessage());
		}
		catch (final WOMTransactionException e)
		{
			LOG.error("WOMTransactionException::" + e.getMessage());
		}
		catch (final WOMBusinessDataException e)
		{
			LOG.error("WOMBusinessDataException::" + e.getMessage());
		}
		return itemsOrderable;
	}

	private String getPONumber()
	{

		final StringBuffer masterOrderNumber = new StringBuffer("W");
		masterOrderNumber.append(dateFormatter.format(new Date()));
		final CartData cartData = cartFacade.getSessionCart();
		if (null != cartData && null != cartData.getCode())
		{
			masterOrderNumber.append(cartData.getCode().substring(2));
		}
		else
		{
			masterOrderNumber.append(((String) orderCodeGenerator.generate()).substring(2));
		}
		return masterOrderNumber.toString();
	}

	private AccountBO getBillTOAccount()
	{
		final AccountBO billToAcct = new BillToAcctBO();

		LOG.info("Session Code ::" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));


		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);

		//final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		//final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
		//.getUnitForUid(shipToAcc);

		//Mahaveer JOBBER Price change
		//final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		//if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		//{
		//	fmCustomerAccModel = sfmCustomerAccModel;
		//}



		billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//soldToUnit.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());

		final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
		custSalesOrg.setDistributionChannel(fmCustomerAccModel.getDistributionChannel());
		custSalesOrg.setDivision(fmCustomerAccModel.getDivision());

		final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
		salesOrg.setCode(fmCustomerAccModel.getSalesorg());

		custSalesOrg.setSalesOrganization(salesOrg);
		sapAccount.setCustomerSalesOrganization(custSalesOrg);
		billToAcct.setSapAccount(sapAccount);
		return billToAcct;
	}

	private AccountBO getShiptoAccount()
	{
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		final AccountBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//shipToUnit.getUid());
		return shipToAcct;
	}



	private int roundUpToMultiplesQuantity(final int inputQuantity, final int soldInMultiplesQuantity)
	{

		final int upMultiple = (int) Math.ceil((double) inputQuantity / (double) soldInMultiplesQuantity);
		return upMultiple * soldInMultiplesQuantity;
	}

	public void recalculate()
	{
		LOG.info("cart refresh");
		final UserModel user = userService.getCurrentUser();
		final CartModel cart = user.getCarts().iterator().next();
		LOG.info("cart ==>" + cart.getCode());
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cart);
		try
		{
			commerceCartService.recalculateCart(parameter);
			LOG.info("cart refereshed");
		}
		catch (final CalculationException e)
		{
			LOG.error("WOMBusinessDataException::" + e.getMessage());
		}
	}


	private void addItemBOToSession(final List<ItemBO> itemBOList)
	{

		final List<ItemBO> sessionItemList = new ArrayList<ItemBO>();

		List<ItemBO> itemList = (List<ItemBO>) getSessionService().getAttribute(WebConstants.RESOLUTION_ITEMS);
		if (itemList != null && itemList.size() > 0)
		{
			System.err.println("Inside");
			for (final ItemBO item : itemBOList)
			{
				boolean isPartFound = false;
				for (final ItemBO sItem : itemList)
				{
					if (item.getPartNumber().equalsIgnoreCase(sItem.getPartNumber())
							&& item.getDisplayPartNumber().equalsIgnoreCase(sItem.getDisplayPartNumber()))
					{
						isPartFound = true;
						if (null != item.getProductFlag() && null != sItem.getProductFlag()
								&& !item.getProductFlag().equalsIgnoreCase(sItem.getProductFlag()))
						{
							isPartFound = false;
						}
						if (isPartFound)
						{
							break;
						}

					}
				}
				if (!isPartFound)
				{
					sessionItemList.add(item);
				}
			}
			sessionItemList.addAll(itemList);
			getSessionService().setAttribute(WebConstants.RESOLUTION_ITEMS, sessionItemList);
		}
		else
		{
			itemList = itemBOList;
			getSessionService().setAttribute(WebConstants.RESOLUTION_ITEMS, itemList);
		}
	}
}

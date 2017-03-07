/**
 * 
 */
package com.federalmogul.facades.wom.impl;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.federalmogul.facades.wom.PartResolutionFacade;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.fmo.wom.business.as.ItemASUSCanImpl;
import com.fmo.wom.business.as.WOMServices;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.util.MessageResourceUtil;


/**
 * @author mamud
 * 
 */
public class DefaultPartResolutionFacade implements PartResolutionFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultPartResolutionFacade.class);

	/*
	 * @Resource(name = "productModelUrlResolver") private UrlResolver<ProductModel> productModelUrlResolver;
	 */

	/*
	 * @Resource(name = "b2bProductFacade") private ProductFacade productFacade;
	 */

	/*
	 * @Resource(name = "productService") private ProductService productService;
	 */

	/*
	 * @Resource(name = "partService") private PartService partService;
	 */

	/*
	 * @Resource(name = "b2bFutureStockFacade") private B2BFutureStockFacade b2bFutureStockFacade;
	 */

	@Autowired
	private UserService userService;

	@Autowired
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Autowired
	private ModelService modelService;

	/*
	 * @Autowired private EnumerationService enumerationService;
	 */

	@Autowired
	private CommonI18NService commonI18NService;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.facades.wom.PartResolutionFacade#getPartResolutionMessage(com.federalmogul.falcon.core.model.
	 * FMPartModel, com.fmo.wom.domain.AccountBO, com.fmo.wom.domain.AccountBO, java.lang.String)
	 */
	@Override
	public String getPartResolutionMessage(final FMPartModel productModel, final AccountBO billTo, final AccountBO shipTo,
			final String poNumber)
	{
		// YTODO Auto-generated method stub
		return getPartResolutions(productModel, billTo, shipTo, poNumber);
	}

	private String getPartResolutions(final FMPartModel productModel, final AccountBO billTo, final AccountBO shipTo,
			final String poNumber)
	{
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);

		if (!isUserAnonymous)
		{
			//INvoke Part resolution for product.
			try
			{
				boolean isNabsProduct = false;

				for (final FMCorporateModel corp : productModel.getCorporate())
				{
					if ("FELPF".equalsIgnoreCase(corp.getCorpcode()) || "SPDPF".equalsIgnoreCase(corp.getCorpcode()))
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
				anItem.setAaiaBrand(siteConfigService.getString("wom.item.aaiabrand", "DZH"));
				anItem.setScacCode(siteConfigService.getString("wom.item.scaccode", "UPS-REG"));
				anItem.setItemQty(getDefaultQuanity());
				anItem.setLineNumber(1);
				itemBOList.add(anItem);
				final List<ItemBO> items = womQuickUploadOrder(itemBOList, billTo, shipTo, poNumber);
				for (final ItemBO item : items)
				{

					if (item.getItemPrice() != null)
					{
						final PriceBO priceBO = item.getItemPrice();
						createPriceRowForUser(priceBO.getDisplayPrice(), productModel);
					}
					final List<ProblemBO> problemBOList = item.getProblemItemList();
					if (item.hasProblemItem() || item.isKit())
					{
						String partProblem = null;
						if (item.isKit())
						{
							partProblem = messageSource.getMessage(MessageResourceUtil.NOT_ALLOWED_TO_ORDER, null,
									i18nService.getCurrentLocale());
							LOG.info("Inside isKit Con Problem::" + partProblem);
						}
						if (problemBOList != null && !problemBOList.isEmpty())
						{
							for (final ProblemBO problem : problemBOList)
							{
								LOG.error("resolvedItem : " + item.getPartNumber() + "message :" + problem.getMessageKey());
								LOG.error("resolvedItem : " + item.getPartNumber() + "Status :" + problem.getStatusCategory().name());
								String issues = null;
								if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(problem.getMessageKey()))
								{
									final int qty = roundUpToMultiplesQuantity(item.getItemQty().getRequestedQuantity(), item.getItemQty()
											.getSoldInMultipleQuantity());
									final String[] parms =
									{ item.getPartNumber(), String.valueOf(item.getItemQty().getSoldInMultipleQuantity()).toString() };
									issues = messageSource.getMessage(problem.getMessageKey(), parms, i18nService.getCurrentLocale())
											+ "@" + qty;
								}
								if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
									//model.addAttribute("MULITPLE_PARTS_SIZE", problem.getCorrectiveOptions().size());
									//model.addAttribute("MULITPLE_PARTS_FOUND", problem.getCorrectiveOptions());
									//entry.setMultiSelect(problem.getCorrectiveOptions());
								}
								if (MessageResourceUtil.PART_SUPERCEDED.equals(problem.getMessageKey()))
								{
									final String[] parms =
									{ item.getDisplayPartNumber(), item.getPartNumber() };
									issues = messageSource.getMessage(problem.getMessageKey(), parms, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.REASONABLE_QUANTITY_THRESHOLD.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.NO_INVENTORY.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.OBSOLETE.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.OBSOLETE_NO_RETURN_ALLOWED.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.OBSOLETE_RETURN_ALLOWED.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.NO_RETURN.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.NOT_ALLOWED_TO_ORDER.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.PART_BEING_DISCONTINUED.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.SOLD_IN_SETS_ONLY.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.PART_IS_PRERELEASE.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.NO_ITEM_CATEGORY_AVAILABLE.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (MessageResourceUtil.MISSING_PRICE.equals(problem.getMessageKey()))
								{
									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
								}
								if (null != problem.getStatusCategory()
										&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
								{

									issues = messageSource.getMessage(problem.getMessageKey(), null, i18nService.getCurrentLocale());
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
							//entry.setErrorMessage(partProblem);
							return partProblem;
						}
					}

				}
			}
			catch (final Exception e)
			{
				return messageSource.getMessage(MessageResourceUtil.NOT_ALLOWED_TO_ORDER, null, i18nService.getCurrentLocale());
			}

		}

		return "success";
	}

	protected final List<ItemBO> womQuickUploadOrder(final List<ItemBO> anItemBOList, final AccountBO billTo,
			final AccountBO shipTo, final String poNumber)
	{
		final ItemASUSCanImpl partResolutionservice = WOMServices.getPartResolutionService();
		List<ItemBO> itemsOrderable = new ArrayList<ItemBO>();
		try
		{
			itemsOrderable = partResolutionservice.checkOrderable(poNumber, anItemBOList, billTo, shipTo);
		}
		catch (WOMExternalSystemException | WOMValidationException e)
		{
			LOG.error(e.getMessage());
		}
		catch (final WOMTransactionException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage());
		}
		catch (final WOMBusinessDataException e)
		{
			// YTODO Auto-generated catch block
			LOG.error(e.getMessage());
		}
		return itemsOrderable;
	}

	private QuantityBO getDefaultQuanity()
	{

		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(1);
		return qty;
	}

	private int roundUpToMultiplesQuantity(final int inputQuantity, final int soldInMultiplesQuantity)
	{

		final int upMultiple = (int) Math.ceil((double) inputQuantity / (double) soldInMultiplesQuantity);
		return upMultiple * soldInMultiplesQuantity;
	}

	public void createPriceRowForUser(final Double price, final FMPartModel part)
	{
		final UserModel user = userService.getCurrentUser();
		boolean diffPrice = true;
		final Collection<PriceRowModel> partPrices = new LinkedList<PriceRowModel>();
		final Collection<PriceRowModel> pricerowModels = part.getEurope1Prices();
		for (final PriceRowModel pricerowModel : pricerowModels)
		{
			if (pricerowModel.getUser() != null && pricerowModel.getUser().getUid().equalsIgnoreCase(user.getUid()))
			{
				if (!pricerowModel.getPrice().equals(price))
				{
					modelService.remove(pricerowModel.getPk());
				}
				else
				{
					diffPrice = false;
					LOG.info("Same Price");
				}
			}
			partPrices.add(pricerowModel);
		}
		modelService.refresh(part);
		if (diffPrice)
		{
			final PriceRowModel priceRowModel = modelService.create(PriceRowModel.class);
			priceRowModel.setProduct(part);
			priceRowModel.setCatalogVersion(part.getCatalogVersion());
			if (!(Math.abs(price - 0.0) < 0.00001))
			{
				priceRowModel.setPrice(price);
			}
			else
			{
				priceRowModel.setPrice(0.0);
			}
			priceRowModel.setNet(true);
			priceRowModel.setCurrency(commonI18NService.getBaseCurrency());
			priceRowModel.setUser(user);
			partPrices.add(priceRowModel);
			part.setEurope1Prices(partPrices);
			modelService.save(part);
		}
	}
}

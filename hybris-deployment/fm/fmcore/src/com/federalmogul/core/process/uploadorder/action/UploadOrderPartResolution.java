/**
 * 
 */
package com.federalmogul.core.process.uploadorder.action;

import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.task.RetryLaterException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.federalmogul.core.enums.UploadOrderStatus;
import com.federalmogul.core.event.FMUploadOrderEmailProcessEvent;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.UploadOrderProcessEmailNotificationModel;
import com.federalmogul.core.model.UploadOrderProcessModel;
import com.federalmogul.falcon.core.model.UploadOrderEntryModel;
import com.federalmogul.falcon.core.model.UploadOrderHistoryModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;
import com.fmo.wom.business.as.ItemASUSCanImpl;
import com.fmo.wom.business.as.WOMServices;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
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
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.util.MessageResourceUtil;


/**
 * @author mamud
 * 
 */
public class UploadOrderPartResolution extends AbstractSimpleDecisionAction
{


	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Autowired
	private EventService eventService;
	




	private static final Logger LOG = Logger.getLogger(UploadOrderPartResolution.class);

	/**
	 * @return the i18nService
	 */
	public I18NService getI18nService()
	{
		return i18nService;
	}

	/**
	 * @param i18nService
	 *           the i18nService to set
	 */
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.processengine.action.AbstractSimpleDecisionAction#executeAction(de.hybris.platform.processengine
	 * .model.BusinessProcessModel)
	 */
	@Override
	public Transition executeAction(final BusinessProcessModel bp) throws RetryLaterException, Exception
	{
		// YTODO Auto-generated method stub
		final UploadOrderProcessModel orderProcess = (UploadOrderProcessModel) bp;
		final UploadOrderModel orderModel = orderProcess.getOrder();
		modelService.refresh(orderModel);
		if (orderModel.getUploadOrderStatus().equals(UploadOrderStatus.FILEPARSED)
				|| orderModel.getUploadOrderStatus().equals(UploadOrderStatus.INPROGRESS))
		{
			final Transition result = getPartResolution(orderModel);
			if (result == Transition.NOK)
			{
				updateErrorStatus(orderModel, orderProcess);
			}
			else
			{
				updateSuccessStatus(orderModel);
			}
			return result;
		}
		else
		{
			return Transition.NOK;
		}

	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource()
	{
		return messageSource;
	}

	/**
	 * @param messageSource
	 *           the messageSource to set
	 */
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	protected Transition getPartResolution(final UploadOrderModel orderModel)
	{
		//WOM Code Integration for Part Resolution --> Mahaveer A --Start
		final ItemASUSCanImpl partResolutionservice = WOMServices.getPartResolutionService();
		if (orderModel.getEntries() != null && !orderModel.getEntries().isEmpty())
		{

			final List<ItemBO> items = new ArrayList<ItemBO>();
			{
				for (final UploadOrderEntryModel entry : orderModel.getEntries())
				{
					if (isValidPart(entry))
					{
						final String[] entryno = entry.getOrderentryNumber().split("_");
						final ItemBO anItem = new PartBO();
						anItem.setDisplayPartNumber(entry.getPartNumber());
						anItem.setPartNumber(entry.getPartNumber());
						anItem.setLineNumber(Integer.valueOf(entryno[1]).intValue());
						anItem.setComment(orderModel.getOrdercomments());
						anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
						anItem.setExternalSystem(ExternalSystem.EVRST);
						if (entry.getPartFlag() != null)
						{
							anItem.setProductFlag(entry.getPartFlag());
						}
						items.add(anItem);
					}
				}
			}

			final FMCustomerAccountModel soldToUnit = orderModel.getSoldToAccount();
			final FMCustomerAccountModel shipToUnit = orderModel.getShipToAccount();


			final AccountBO billToAcct = new BillToAcctBO();
			billToAcct.setAccountCode(soldToUnit.getNabsAccountCode());//Nabs Sold to account code

			final SapAcctBO sapAccount = new SapAcctBO();
			sapAccount.setSapAccountCode(soldToUnit.getUid());
			billToAcct.setSapAccount(sapAccount);

			final AccountBO shipToAcct = new ShipToAcctBO();
			shipToAcct.setAccountCode(shipToUnit.getNabsAccountCode());

			final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
			custSalesOrg.setDistributionChannel(soldToUnit.getDistributionChannel());
			custSalesOrg.setDivision(soldToUnit.getDivision());

			final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
			salesOrg.setCode(soldToUnit.getSalesorg());
			custSalesOrg.setSalesOrganization(salesOrg);
			sapAccount.setCustomerSalesOrganization(custSalesOrg);
			List<ItemBO> itemsOrderable = new ArrayList<ItemBO>();
			try
			{
				try
				{
					LOG.info("Before Part Resolution :: " + items);
					itemsOrderable = partResolutionservice.checkOrderable(orderModel.getCode(), items, billToAcct, shipToAcct);
					

					LOG.info("After Part Resolution :: " + itemsOrderable);
				}
				catch (final WOMTransactionException e)
				{
					return Transition.NOK;

				}
				catch (final WOMBusinessDataException e)
				{
					// YTODO Auto-generated catch block
					return Transition.NOK;
				}
				if (itemsOrderable != null)
				{
					for (final UploadOrderEntryModel entry : orderModel.getEntries())
					{
						final String[] entryno = entry.getOrderentryNumber().split("_");
						for (final ItemBO resolvedItem : itemsOrderable)
						{
							final List<ProblemBO> problemBOList = resolvedItem.getProblemItemList();
							if (Integer.valueOf(entryno[1]).intValue() == resolvedItem.getLineNumber())
							{
								entry.setPartDescription(resolvedItem.getDescription());
								/* Mahaveer Bug to reslove item */
								//final String partFlag = resolvedItem.getProductFlag().trim();
								//if (null != partFlag && !partFlag.isEmpty())
								//{
								//entry.setPartFlag(partFlag);
								//}
								entry.setPartbrandState(resolvedItem.getBrandState());
								//entry.setPartNumber(resolvedItem.getPartNumber());
								entry.setRawPartNumber(resolvedItem.getPartNumber());
								if (resolvedItem.getExternalSystem() != null)
								{
									entry.setExternalSystem(resolvedItem.getExternalSystem().name());
								}

								if (resolvedItem.hasProblemItem() || resolvedItem.isKit())
								{
									String partProblem = null;
									if (resolvedItem.isKit())
									{
										partProblem = "This part number cannot currently be ordered";
									}
									for (final ProblemBO problem : problemBOList)
									{
										String issues = null;
										if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(problem.getMessageKey()))
										{
											//issues = resolvedItem.getPartNumber() + " is only sold in multiple quantities of "
											//	+ String.valueOf(resolvedItem.getItemQty().getSoldInMultipleQuantity()).toString();
											entry.setPartResolutionMsg("Part Resloved");
											final int qty = roundUpToMultiplesQuantity(resolvedItem.getItemQty().getRequestedQuantity(),
													resolvedItem.getItemQty().getSoldInMultipleQuantity());
											entry.setQuantity(qty);

										}
										if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(problem.getMessageKey()))
										{

											issues = "Multiple matching parts found";
										}

										if (MessageResourceUtil.PART_SUPERCEDED.equals(problem.getMessageKey()))
										{

											entry.setPartResolutionMsg("Part Resloved");
										}
										if (MessageResourceUtil.VINTAGE_PART.equals(problem.getMessageKey()))
										{
											entry.setPartResolutionMsg("Part Resloved");
											if(resolvedItem.getItemQty().getSoldInMultipleQuantity()>1 && (resolvedItem.getItemQty().getRequestedQuantity()%resolvedItem.getItemQty().getSoldInMultipleQuantity())!=0){
											final int qty = roundUpToMultiplesQuantity(resolvedItem.getItemQty().getRequestedQuantity(),
													resolvedItem.getItemQty().getSoldInMultipleQuantity());
											entry.setQuantity(qty);
											}

											
										}
										if (MessageResourceUtil.REASONABLE_QUANTITY_THRESHOLD.equals(problem.getMessageKey()))
										{
											//issues = "Please check order quantity.  Click Process Order to proceed";
											entry.setPartResolutionMsg("Part Resloved");
											setHistory(entry, "PartStatus", "PARTRESOLUTION", "PARTRESOLUTION", "Part Resloved");
										}
										if (MessageResourceUtil.NO_INVENTORY.equals(problem.getMessageKey()))
										{
											issues = "No inventory available. Only back order all available";
										}
										if (MessageResourceUtil.OBSOLETE.equals(problem.getMessageKey()))
										{
											issues = "Part # is obsolete";
										}
										if (MessageResourceUtil.OBSOLETE_NO_RETURN_ALLOWED.equals(problem.getMessageKey()))
										{
											issues = "Part# is obsolete. No returns allowed.";
										}
										if (MessageResourceUtil.OBSOLETE_RETURN_ALLOWED.equals(problem.getMessageKey()))
										{
											issues = "Part # is obsolete. Returns allowed";
										}
										if (MessageResourceUtil.NO_RETURN.equals(problem.getMessageKey()))
										{
											//issues = "This part can not be returned.";
											entry.setPartResolutionMsg("Part Resloved");
										}
										if (MessageResourceUtil.NOT_ALLOWED_TO_ORDER.equals(problem.getMessageKey()))
										{
											issues = "This part number cannot currently be ordered";
										}
										if (MessageResourceUtil.SOLD_IN_SETS_ONLY.equals(problem.getMessageKey()))
										{
											issues = "This part number cannot currently be ordered";
										}
										if (MessageResourceUtil.PART_IS_PRERELEASE.equals(problem.getMessageKey()))
										{
											issues = "This part number cannot currently be ordered";
										}

										if (MessageResourceUtil.PRICE_UNAVAILABLE.equals(problem.getMessageKey()))
										{
											entry.setPartResolutionMsg("Part Resloved");
										}

										if (null != problem.getStatusCategory()
												&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
										{

											issues = "Part set up error. Please call Customer Service for assistance";
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
										entry.setPartResolutionMsg(partProblem);
										setHistory(entry, "PartStatus", "PARTRESOLUTIONERROR", "PARTRESOLUTIONERROR",
												"Part Resolution Error");
									}
								}
								else
								{
									entry.setPartResolutionMsg("Part Resloved");
									setHistory(entry, "PartStatus", "PARTRESOLUTION", "PARTRESOLUTION", "Part Resloved");
								}
								if (resolvedItem.getItemPrice() != null)
								{
									final PriceBO priceBO = resolvedItem.getItemPrice();
									entry.setBasePrice(priceBO.getFreightPrice() != null ? priceBO.getFreightPrice().doubleValue() : 0.0);
								}
								else
								{
									entry.setBasePrice(0.0);
								}
							}

						}

						if (!isValidPart(entry) && null == entry.getPartResolutionMsg())
						{
							LOG.info("entry :: " + entry.getPartNumber());
							entry.setPartResolutionMsg("Invalid Part");
						}
						entry.setUpdatedTime(new Date());
						modelService.save(entry);
						modelService.refresh(entry);
					}
				}
			}
			catch (WOMExternalSystemException | WOMValidationException e)
			{
				// YTODO Auto-generated catch block
				return Transition.NOK;
			}

		}
		if (!canProcessOrder(orderModel.getEntries()))
		{
			return Transition.NOK;
		}
		else
		{
			return Transition.OK;
		}
	}

	private QuantityBO getDefaultQuanity(final int quantity)
	{
		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(quantity);
		return qty;
	}

	private boolean canProcessOrder(final List<UploadOrderEntryModel> entrys)
	{
		for (final UploadOrderEntryModel entry : entrys)
		{
			if (entry.getPartResolutionMsg() != null && !"Part Resloved".equalsIgnoreCase(entry.getPartResolutionMsg()))
			{
				return false;
			}
		}
		return true;
	}

	protected void setHistory(final UploadOrderModel orderModel, final String attribute, final String from, final String to,
			final String status)
	{
		final UploadOrderHistoryModel history = modelService.create(UploadOrderHistoryModel.class);
		history.setModifiedattribute(attribute);
		history.setUpdatedTime(new Date());
		history.setModifiedValueFrom(from);
		history.setModifiedValueTo(to);
		history.setOrder(orderModel);
		history.setStatus(status);
		modelService.save(history);
		modelService.save(orderModel);
		modelService.refresh(orderModel);
	}

	private void updateErrorStatus(final UploadOrderModel orderModel, final UploadOrderProcessModel orderProcess)
	{
		modelService.refresh(orderModel);
		orderModel.setUploadOrderStatus(UploadOrderStatus.PARTRESOLUTIONERROR);
		orderModel.setUpdatedTime(new Date());
		setHistory(orderModel, "OrderStatus", "PARTRESOLUTIONERROR", "PARTRESOLUTIONERROR", "Part Resolution Error");
		modelService.save(orderModel);

		final UploadOrderProcessEmailNotificationModel uploadOrderEmail = new UploadOrderProcessEmailNotificationModel();
		uploadOrderEmail.setOrder(orderModel);
		uploadOrderEmail.setEmailId(orderModel.getUser().getUid());
		final FMUploadOrderEmailProcessEvent uploadOrderEmailEvent = new FMUploadOrderEmailProcessEvent(uploadOrderEmail);
		//LOG.info("Upload Order Email Event Starts");
		eventService.publishEvent(initializeEvent(uploadOrderEmailEvent, orderModel.getUser(), orderProcess));
	}

	private void updateSuccessStatus(final UploadOrderModel orderModel)
	{
		orderModel.setUploadOrderStatus(UploadOrderStatus.PARTSRESOLVED);
		setHistory(orderModel, "OrderStatus", "FILEPARSED", "PARTSRESOLVED", "Upload Order Parts PARTSRESOLVED");
		orderModel.setUpdatedTime(new Date());
		modelService.save(orderModel);
		modelService.refresh(orderModel);
	}

	public FMUploadOrderEmailProcessEvent initializeEvent(final FMUploadOrderEmailProcessEvent event,
			final CustomerModel customerModel, final UploadOrderProcessModel orderProcess)
	{
		try
		{
			event.setBaseStore(orderProcess.getStore());
			event.setSite(orderProcess.getSite());
			event.setCustomer(customerModel);
			event.setLanguage(orderProcess.getStore().getDefaultLanguage());
			event.setCurrency(orderProcess.getStore().getDefaultCurrency());
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	protected void setHistory(final UploadOrderEntryModel orderModel, final String attribute, final String from, final String to,
			final String status)
	{

		final UploadOrderHistoryModel history = modelService.create(UploadOrderHistoryModel.class);
		history.setModifiedattribute(attribute);
		history.setUpdatedTime(new Date());
		history.setModifiedValueFrom(from);
		history.setModifiedValueTo(to);
		history.setEntry(orderModel);
		history.setStatus(status);
		modelService.save(history);
		modelService.save(orderModel);
		modelService.refresh(orderModel);
	}

	private int roundUpToMultiplesQuantity(final int inputQuantity, final int soldInMultiplesQuantity)
	{

		final int upMultiple = (int) Math.ceil((double) inputQuantity / (double) soldInMultiplesQuantity);
		return upMultiple * soldInMultiplesQuantity;
	}

	protected boolean isValidPart(final UploadOrderEntryModel part)
	{
		return isValidPartNumber(part) && isValidQuantity(part);
	}

	protected boolean isValidPartNumber(final UploadOrderEntryModel part)
	{
		if (GenericValidator.isBlankOrNull(part.getPartNumber()))
		{
			return false;
		}
		return true;
	}

	protected boolean isValidQuantity(final UploadOrderEntryModel part)
	{
		final int quantity = part.getQuantity();

		if (quantity <= 0)
		{
			return false;
		}
		return true;
	}
}

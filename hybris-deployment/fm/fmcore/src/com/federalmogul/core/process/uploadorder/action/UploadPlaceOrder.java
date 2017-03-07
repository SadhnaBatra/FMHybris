/**
 * 
 */
package com.federalmogul.core.process.uploadorder.action;

import de.hybris.platform.core.GenericSearchConstants.LOG;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.task.RetryLaterException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.enums.UploadOrderStatus;
import com.federalmogul.core.event.FMUploadOrderEmailProcessEvent;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.UploadOrderProcessEmailNotificationModel;
import com.federalmogul.core.model.UploadOrderProcessModel;
import com.federalmogul.falcon.core.model.UploadOrderEntryModel;
import com.federalmogul.falcon.core.model.UploadOrderHistoryModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;
import com.fmo.wom.business.as.OrderASUSCanImpl;
import com.fmo.wom.common.dao.AuditInterceptor;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.UserAccountBO;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.enums.UsageType;
import com.fmo.wom.integration.nabs.action.GetMasterOrderNumberAction;


/**
 * @author mamud
 * 
 */
public class UploadPlaceOrder extends AbstractSimpleDecisionAction
{


	@Autowired
	private EventService eventService;
	private static final Logger LOG = Logger.getLogger(UploadPlaceOrder.class);

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
		if (orderModel.getUploadOrderStatus().equals(UploadOrderStatus.PARTSRESOLVED)
				|| orderModel.getUploadOrderStatus().equals(UploadOrderStatus.INPROGRESS))
		{
			try
			{
				final Transition result = ProcessOrder(orderModel, orderProcess);

				return result;
			}
			catch (final Exception e)
			{
				e.printStackTrace();
				modelService.refresh(orderModel);
				orderModel.setUploadOrderStatus(UploadOrderStatus.SYSTEMERROR);
				orderModel.setUpdatedTime(new Date());
				setHistory(orderModel, "OrderStatus", "SYSTEMERROR", "SYSTEMERROR", e.getMessage());
				modelService.save(orderModel);
				modelService.refresh(orderModel);
				return Transition.NOK;
			}
		}
		else
		{
			return Transition.NOK;
		}

	}

	public Transition ProcessOrder(final UploadOrderModel orderModel, final UploadOrderProcessModel orderProcess) throws Exception
	{
		final OrderASUSCanImpl processOrderService = new OrderASUSCanImpl();

		if (orderModel.getEntries() != null && !orderModel.getEntries().isEmpty())
		{
			if (!canProcessOrder(orderModel.getEntries()))
			{
				modelService.refresh(orderModel);
				orderModel.setUploadOrderStatus(UploadOrderStatus.PARTRESOLUTIONERROR);
				orderModel.setUpdatedTime(new Date());
				setHistory(orderModel, "OrderStatus", "PARTRESOLUTIONERROR", "PARTRESOLUTIONERROR", "Upload Order Status Changed ");
				modelService.save(orderModel);
				modelService.refresh(orderModel);
				return Transition.NOK;
			}
			final List<ItemBO> items = new ArrayList<ItemBO>();
			{
				int linenumber = 1;
				for (final UploadOrderEntryModel entry : orderModel.getEntries())
				{
					final String[] entryno = entry.getOrderentryNumber().split("_");

					if (entry.getExternalSystem() != null && entry.getExternalSystem().equalsIgnoreCase(ExternalSystem.NABS.name()))
					{
						final ItemBO anItem = new PartBO();
						anItem.setDisplayPartNumber(entry.getPartNumber());
						anItem.setPartNumber(entry.getRawPartNumber());
						anItem.setLineNumber(linenumber++);
						anItem.setComment(orderModel.getOrdercomments());
						anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));

						anItem.setExternalSystem(ExternalSystem.NABS);
						anItem.setProductFlag(entry.getPartFlag());
						anItem.setBrandState(entry.getPartbrandState());
						anItem.setBackorderPolicy(BackOrderPolicy.SHIP_AND_BACKORDER);
						final PriceBO price = new PriceBO();
						price.setFreightPrice(entry.getBasePrice());
						anItem.setItemPrice(price);
						/*
						 * final DistributionCenterBO distrCenter = new DistributionCenterBO();
						 * distrCenter.setDistCenterId(Long.parseLong("22")); distrCenter.setCode("019");
						 * distrCenter.setName("Maysville"); //distrCenter.setAvailabilityDate(getCutoffTime().getTime());
						 * distrCenter.setEmergencyCutoffTime(getCutoffTime().getTime());
						 * 
						 * final InventoryBO inventoryToUse = new InventoryBO();
						 * inventoryToUse.setDistributionCenter(distrCenter); inventoryToUse.setSelectedLocation(true);
						 * inventoryToUse.setDistCntrId(distrCenter.getDistCenterId());
						 * inventoryToUse.setAvailableQty(entry.getQuantity());
						 * inventoryToUse.setAssignedQty(entry.getQuantity());
						 * 
						 * 
						 * final ShippingCodeBO shippingCode = new ShippingCodeBO(); final NabsShippingCodeBO nabsShippingCode
						 * = new NabsShippingCodeBO(); nabsShippingCode.setStockShippingCode("FXIPR");
						 * nabsShippingCode.setEmergencyShippingCode("FXIPR");
						 * shippingCode.setNabsShippingCode(nabsShippingCode); inventoryToUse.setShippingCode(shippingCode);
						 * anItem.addInventory(inventoryToUse);
						 */

						items.add(anItem);
					}
					else
					{
						final ItemBO anHeader = new PartBO();
						anHeader.setDisplayPartNumber(entry.getPartNumber());
						anHeader.setPartNumber(entry.getRawPartNumber());
						anHeader.setComment("This is The Header Record :: " + entry.getRawPartNumber());
						((PartBO) anHeader).setHeader(true);
						((PartBO) anHeader).setProcessOrderLineNumber(Integer.valueOf(entryno[1]).intValue());
						anHeader.setItemQty(getDefaultQuanity(entry.getQuantity().intValue()));
						anHeader.setLineNumber(linenumber++);
						anHeader.setExternalSystem(ExternalSystem.EVRST);

						final ItemBO anItem = new PartBO();
						anItem.setDisplayPartNumber(entry.getPartNumber());
						anItem.setPartNumber(entry.getRawPartNumber());
						anItem.setLineNumber(linenumber++);
						anItem.setComment(orderModel.getOrdercomments());
						anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
						anItem.setExternalSystem(ExternalSystem.EVRST);
						((PartBO) anItem).setProcessOrderLineNumber(Integer.valueOf(entryno[1]).intValue());
						anItem.setBackorderPolicy(BackOrderPolicy.SHIP_AND_BACKORDER);
						anItem.setProductFlag(entry.getPartFlag());

						final PriceBO price = new PriceBO();
						price.setFreightPrice(entry.getBasePrice());
						anItem.setItemPrice(price);

						/*
						 * final InventoryBO anInventory = new InventoryBO();
						 * anInventory.setAvailableQty(entry.getQuantity()); anInventory.setAssignedQty(0); final
						 * DistributionCenterBO distCenter = new DistributionCenterBO(); distCenter.setCode("2156");
						 * distCenter.setAvailabilityDate(GregorianCalendar.getInstance().getTime());
						 * distCenter.setEmergencyCutoffTime(getCutoffTime().getTime());
						 * anInventory.setDistributionCenter(distCenter); anItem.addInventory(anInventory);
						 * 
						 * final ShippingCodeBO shippingCode = new ShippingCodeBO(); final SapShippingCodeBO sapShippingCode =
						 * new SapShippingCodeBO();
						 * 
						 * sapShippingCode.setCarrierCode("");
						 * 
						 * sapShippingCode.setIncoTerms("ZPA"); sapShippingCode.setRoute("");
						 * shippingCode.setSapShippingCode(sapShippingCode); anInventory.setShippingCode(shippingCode);
						 * anInventory.setSelectedLocation(true);
						 */
						items.add(anHeader);
						items.add(anItem);
					}

				}
			}
			final OrderBO order = new OrderBO();
			order.setOrderType(OrderType.STOCK);
			order.setUsageType(UsageType.PURCHASE);
			order.setOrderSource(OrderSource.HYBRIS);
			order.setOrderedBy(orderModel.getUser().getName());
			order.setReceivesFreeFreight(true);
			order.setMarketCode(processOrderService.getMarket());
			order.setFutureDate(null);
			if (orderModel.getPONumber() != null)
			{
				order.setCustPoNbr(orderModel.getPONumber());
			}
			else
			{
				order.setCustPoNbr(getPONumber());
			}
			order.setUserAccount(getUserAccount(orderModel.getUser(), orderModel.getSoldToAccount()));
			order.setBillToAcct(getBillTOAccount(orderModel.getSoldToAccount()));

			final AddressModel manualShipToAddress = orderModel.getShipToAddress();
			if (manualShipToAddress != null && null != manualShipToAddress.getRemarks()
					&& manualShipToAddress.getRemarks().equalsIgnoreCase("manualShipToAddress"))
			{
				order.setManualShipTo(true);
				order.setManualShipToAddress(getManualShipToAddress(manualShipToAddress));
			}

			order.setShipToAcct(getShiptoAccount(orderModel.getShipToAddress(), orderModel.getShipToAccount()));
			order.setMstrOrdNbr(orderModel.getCode());
			order.setItemList(items);

			if (orderModel.getOrdercomments() != null && !orderModel.getOrdercomments().isEmpty())
			{
				order.setComment1(orderModel.getOrdercomments());
			}
			else
			{
				order.setComment1("No Comments");
			}
			LOG.info("Free Fright :: " + order.receivesFreeFreight());
			LOG.info("Before Order BO :" + order);
			final OrderBO sapOrder = processOrderService.processUploadOrder(order);
			LOG.info("Order after Result BO :: " + sapOrder);
			orderModel.setSapordernumber(sapOrder.getSapConfirmationNumber());
			setHistory(orderModel, "OrderStatus", orderModel.getUploadOrderStatus().getCode(), "submitted",
					"Upload Order Submitted: " + sapOrder.getSapConfirmationNumber());
			orderModel.setUploadOrderStatus(UploadOrderStatus.SUBMITTED);
			orderModel.setUpdatedTime(new Date());
			modelService.save(orderModel);
			modelService.refresh(orderModel);
		}
		final UploadOrderProcessEmailNotificationModel uploadOrderEmail = new UploadOrderProcessEmailNotificationModel();
		uploadOrderEmail.setOrder(orderModel);
		uploadOrderEmail.setEmailId(orderModel.getUser().getUid());
		final FMUploadOrderEmailProcessEvent uploadOrderEmailEvent = new FMUploadOrderEmailProcessEvent(uploadOrderEmail);
		//LOG.info("Upload Order Email Event Starts");
		eventService.publishEvent(initializeEvent(uploadOrderEmailEvent, orderModel.getUser(), orderProcess));
		return Transition.OK;
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

	private Calendar getCutoffTime()
	{
		final Calendar calculationTime = GregorianCalendar.getInstance();

		final Calendar cutoffTimeCal = new GregorianCalendar(calculationTime.get(GregorianCalendar.YEAR),
				calculationTime.get(GregorianCalendar.MONTH), calculationTime.get(GregorianCalendar.DAY_OF_MONTH), 17, 00);
		return cutoffTimeCal;
	}


	private QuantityBO getDefaultQuanity(final int quantity)
	{
		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(quantity);
		return qty;
	}

	private ManualShipToAddressBO getManualShipToAddress(final AddressModel manualShipToAddress)
	{
		final ManualShipToAddressBO manualAddress = new ManualShipToAddressBO();
		manualAddress.setName(manualShipToAddress.getFirstname() + " " + manualShipToAddress.getLastname());
		final AddressBO manualShipTo = new AddressBO();
		manualShipTo.setCity(manualShipToAddress.getTown());
		manualShipTo.setAddrLine1(manualShipToAddress.getLine1());
		manualShipTo.setAddrLine2(manualShipToAddress.getLine2());
		manualShipTo.setZipOrPostalCode(manualShipToAddress.getPostalcode());
		manualShipTo.setStateOrProv(manualShipToAddress.getRegion().getIsocode().split("-")[1]);
		final CountryBO country = new CountryBO();
		country.setIsoCountryCode(manualShipToAddress.getRegion().getIsocode().split("-")[0]);
		manualShipTo.setCountry(country);
		manualAddress.setAddress(manualShipTo);
		return manualAddress;
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

	private UserAccountBO getUserAccount(final UserModel user, final FMCustomerAccountModel soldToUnit)
	{
		final UserAccountBO userAcct = new UserAccountBO();
		userAcct.setLoggedInAsBillto(true);
		userAcct.setUserID(user.getName());
		userAcct.setAccountCode(soldToUnit.getNabsAccountCode());
		AuditInterceptor.setUserAccount(userAcct);
		return userAcct;
	}

	private BillToAcctBO getBillTOAccount(final FMCustomerAccountModel soldToUnit)
	{
		final BillToAcctBO billToAcct = new BillToAcctBO();
		billToAcct.setAccountCode(soldToUnit.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(soldToUnit.getUid());
		billToAcct.setMinFreeFreightAmt(new BigDecimal(soldToUnit.getFreeFreightAmt()));
		final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
		custSalesOrg.setDistributionChannel(soldToUnit.getDistributionChannel());
		custSalesOrg.setDivision(soldToUnit.getDivision());
		final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
		salesOrg.setCode(soldToUnit.getSalesorg());
		custSalesOrg.setSalesOrganization(salesOrg);
		sapAccount.setCustomerSalesOrganization(custSalesOrg);
		sapAccount.setCustomerSalesOrganization(custSalesOrg);
		billToAcct.setSapAccount(sapAccount);
		return billToAcct;
	}

	private ShipToAcctBO getShiptoAccount(final AddressModel ship2Address, final FMCustomerAccountModel shipToUnit)
	{
		//final ShipToAcctBO aShipTo = new ShipToAcctBO();
		//aShipTo.setAccountName(shipToUnit.getLocName());
		//aShipTo.setAccountCode(shipToUnit.getUid());

		final ShipToAcctBO aShipTo = new ShipToAcctBO();
		aShipTo.setAccountName(shipToUnit.getLocName());
		aShipTo.setAccountCode(shipToUnit.getNabsAccountCode());//shipToUnit.getUid());

		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(shipToUnit.getUid());
		aShipTo.setSapAccount(sapAccount);


		final AddressBO anAddress = new AddressBO();
		anAddress.setCity(ship2Address.getTown());
		anAddress.setAddrLine1(ship2Address.getLine1());
		anAddress.setAddrLine2(ship2Address.getLine2());
		anAddress.setZipOrPostalCode(ship2Address.getPostalcode());
		anAddress.setStateOrProv(ship2Address.getRegion().getName());
		final CountryBO country = new CountryBO();
		country.setIsoCountryCode(ship2Address.getCountry().getIsocode());
		anAddress.setCountry(country);
		aShipTo.setAddress(anAddress);
		return aShipTo;

	}

	private String getPONumber()
	{
		final GetMasterOrderNumberAction masterOrderNumAction = new GetMasterOrderNumberAction();
		masterOrderNumAction.setCallingSystem(null);
		final String poNumber = masterOrderNumAction.executeAction();
		return poNumber;
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

}

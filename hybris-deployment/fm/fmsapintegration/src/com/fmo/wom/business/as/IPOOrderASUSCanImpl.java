package com.fmo.wom.business.as;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDCShippingModel;
import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMBusinessAccessException;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMPersistanceException;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.NabsShippingCodeBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.SapShippingCodeBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.ShippingCodeBO;
import com.fmo.wom.domain.SupplierCdProductFlgBO;
import com.fmo.wom.domain.TpAccountBO;
import com.fmo.wom.domain.TradingPartnerBO;
import com.fmo.wom.domain.TradingPartnerShippingMethodBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderStatus;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.enums.UsageType;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.ProblemBOFactory;
import com.fmo.wom.model.FMIPOOrderEntryModel;
import com.fmo.wom.model.FMIPOOrderModel;
import com.fmo.wom.model.FMtpcarriershipmethodsModel;


public class IPOOrderASUSCanImpl extends OrderASUSCanImpl implements OrderASUSCan, USCanAS
{

	private static Logger logger = Logger.getLogger(IPOOrderASUSCanImpl.class);

	@Override
	protected Logger getLogger()
	{
		return logger;
	}

	@Override
	protected OrderBO preprocessOrder(final OrderBO order) throws WOMValidationException, WOMBusinessDataException,
			WOMBusinessAccessException, WOMResourceException
	{
		logger.info("preprocessOrder(...): Order Source: " + order.getOrderSource());
		if (order.getOrderSource() == OrderSource.IPO)
		{
			final MarketASUSCanImpl marketASUSCan = new MarketASUSCanImpl();
			order.setOrderType(OrderType.EMERGENCY); // IPO orders are always emergency order
			order.setManualShipTo(true); //IPO orders are always manual ShipTo
			order.setWeightUOM(marketASUSCan.getDefaultWeightUnitOfMeasure().description());

			if (GenericValidator.isBlankOrNull(order.getUserId()))
			{
				order.setUserId("IPOUSER");
			}
			// set ordered by field to IPO
			order.setOrderedBy("IPO");

			order.setBillToAcct(getBillTOAccount(order.getBillToAcct().getAccountCode()));
			//order.setShipToAcct(getShiptoAccount(order.getShipToAcct().getAccountCode()));

			// transfer the account code
			if (order.getShipToAcct() != null)
			{
				transferShipToAddressToManualShipTo(order);
				// do not want the ship to account code anymore.
				order.getShipToAcct().setAccountCode(null);
			}
			final TradingPartnerAS tradingPartnerAs = new TradingPartnerAS();
			// further validate trading partner
			final TpAccountBO tradingPartnerAccount = tradingPartnerAs.getTradingPartnerAccount(order.getTpSecretKey(), order
					.getBillToAcct().getAccountCode());
			logger.info("preprocessOrder(...): TP Account Code: " + order.getBillToAcct().getAccountCode());
			logger.info("preprocessOrder(...): TP Secret Key: " + order.getTpSecretKey());
			logger.info("preprocessOrder(...): Setting Trading Partner for the order"); 
			order.setTradingPartner(tradingPartnerAccount.getTradingPartner());

			/* Look up from Hybris Service */
			/*
			 * // IPO needs to get the account info AccountBO nabsAccountBillTo =
			 * accountASUSCan.getAccountInfo(order.getBillToAcct().getAccountCode());
			 * order.setBillToAcct(nabsAccountBillTo); AccountBO nabsAccountShipTo = null; AccountBO nabsShipTo =
			 * order.getShipToAcct();
			 * 
			 * if(accountASUSCan.checkIfAccountExists(nabsShipTo.getAccountCode())) {
			 * 
			 * // Nabs ShipTo info provided in incoming BOD and exists, get corresponding SAP shipTo info String
			 * sapShipToCode = null; try { //Fetch nabs ship to account details nabsAccountShipTo =
			 * accountASUSCan.getAccountInfo(order.getShipToAcct().getAccountCode()); // When NABS Shipto account does not
			 * have a corresponding SAP Acct code // do not populate SAP Acct details. if
			 * (nabsAccountShipTo.getSapAccount()!=null) { sapShipToCode =
			 * nabsAccountShipTo.getSapAccount().getSapAccountCode();
			 * nabsAccountBillTo.getSapAccount().setShipToCode(sapShipToCode); // also set the NABS ship to account //
			 * order.setShipToAcct(nabsAccountShipTo); } } catch (WOMBaseCheckedException e) { // Allow it to proceed.
			 * Could not find a shipto code. Let // integration layer handle granularity of this case.
			 * logger.info("SAP shipto account code not found for account: {}", order.getShipToAcct().getAccountCode()); }
			 * } else { // didn't exist. null it out so nothing gets the inputted value nabsShipTo.setAccountCode(null); }
			 */

			/* For Demo Get the Hard Coded AccountBO */

			/* Look up from Hybris Service */
			// this looks weird, but we want to make sure the parent order object is set on all items in the items list
			order.setItemList(order.getItemList());
		}
		return order;
	}

	private void transferShipToAddressToManualShipTo(final OrderBO order)
	{
		order.setManualShipToAccountCode(order.getShipToAcct().getAccountCode());
		final ManualShipToAddressBO manualShipTo = new ManualShipToAddressBO();

		final AccountBO shipToAccount = order.getShipToAcct();
		manualShipTo.setName(shipToAccount.getAccountName());
		manualShipTo.setManualShiptoAcctCd(shipToAccount.getAccountCode());

		final AddressBO manualShipToAddress = new AddressBO();

		final AddressBO incomingAddress = shipToAccount.getAddress();
		manualShipToAddress.setAddrLine1(incomingAddress.getAddrLine1());
		manualShipToAddress.setAddrLine2(incomingAddress.getAddrLine2());
		manualShipToAddress.setAddrName(incomingAddress.getAddrName());
		manualShipToAddress.setCity(incomingAddress.getCity());
		manualShipToAddress.setEmailAddr(incomingAddress.getEmailAddr());
		manualShipToAddress.setFaxNum(incomingAddress.getFaxNum());
		manualShipToAddress.setPhoneNum(incomingAddress.getPhoneNum());
		manualShipToAddress.setStateOrProv(incomingAddress.getStateOrProv());
		String zipOrPostalCode = incomingAddress.getZipOrPostalCode();
		if (StringUtil.isAValidCanadianPostalCode(zipOrPostalCode) == true)
		{
			// this is a valid canadian postal code.  Put a space between the Forward Segmentation Area and the Local Delivery Unit sections so
			// all backends like it
			zipOrPostalCode = StringUtil.getFormattedCanadianPostalCode(zipOrPostalCode);
		}
		manualShipToAddress.setZipOrPostalCode(zipOrPostalCode);
		manualShipToAddress.setCountry(incomingAddress.getCountry());

		manualShipTo.setAddress(manualShipToAddress);
		order.setManualShipToAddress(manualShipTo);
	}

	@Override
	public OrderBO processOrder(OrderBO order) throws WOMResourceException, WOMValidationException, WOMProcessException
	{
		logger.info("START - IPOOrderASUSCanImpl processOrder(OrderBO order)");
		logger.info("processOrder(...): comment1: [>" + order.getComment1() + "<]");
		if (order.getItemList() != null && order.getItemList().size() > 0) {
			logger.info("processOrder(...): order.getItemList().get(0).getRequestedInventory(): " + order.getItemList().get(0).getRequestedInventory());
			if (order.getItemList().get(0).getRequestedInventory() != null) {
				// Get some details for passed-in Ship From Party.
				FMDCCenterModel fmDcCenterModel = getFMDistributionCenterBO(order.getItemList().get(0).getRequestedInventory().getDistributionCenter().getCode());
				if (fmDcCenterModel != null) {
					AddressBO dcAddr = new AddressBO();
					dcAddr.setAddrLine1(fmDcCenterModel.getAddressLine1());
					dcAddr.setCity(fmDcCenterModel.getCity());
					dcAddr.setStateOrProv(fmDcCenterModel.getState());
					CountryBO dcCountry = new CountryBO();
					dcCountry.setIsoCountryCode(fmDcCenterModel.getIsoCountryCode());
					dcAddr.setCountry(dcCountry);
					order.getItemList().get(0).getRequestedInventory().getDistributionCenter().setAddress(dcAddr);
				}
			}
		}

		final String customerOrderNumber = order.getCustPoNbr();

		//Master Order Number - Mahaveer A - fix for IPO

		String masterOrderNumber = order.getMstrOrdNbr();
		if (GenericValidator.isBlankOrNull(order.getMstrOrdNbr()))
		{
			// order needs a master order number to process
			masterOrderNumber = getMasterOrderNumber();
			order.setMstrOrdNbr(masterOrderNumber.trim());
		}
		// make sure market is set
		order.setMarketCode(getMarket());

		// If a manual ship to address is associated with the order, make sure the ID of the address is 0
		// so that it will be persisted correctly.  Manual ship to address has a one to one relationship
		// with an order
		logger.debug("processOrder(...): Manual Ship to address is " + order.getManualShipToAddress());
		final ManualShipToAddressBO manualShipToAddress = order.getManualShipToAddress();
		if (manualShipToAddress != null && manualShipToAddress.getManualShipToAddrId() != 0)
		{
			manualShipToAddress.setManualShipToAddrId(0);
		}

		logger.debug("processOrder(...): Beginning pre-process order for Customer po " + customerOrderNumber);
		preprocessOrder(order);
		logger.debug("processOrder(...): Customer PO # " + customerOrderNumber + " - Completed pre-processing.");

		// validate the order data 
		validateOrderData(order);
		logger.debug("processOrder(...): Customer PO # " + customerOrderNumber + " - Completed order validation.");

		if (order.hasAtLeastOneGoodItem() == false)
		{
			logger.error("processOrder(...): No processable items remaining on order. Process order completing prematurely. Customer PO #: "
					+ customerOrderNumber);
			order.setOrderStatus(OrderStatus.UNPROCESSED);
			return order;
		}

		// Check the parts on the order
		final List<ItemBO> resolvedParts = checkOrderable(order);
		logger.debug("processOrder(...): Customer PO # " + customerOrderNumber + " - Completed part resolution");

		if (resolvedParts != null && resolvedParts.size() > 0) {
			logger.info("processOrder(...): resolvedParts.get(0).getRequestedInventory(): (BEFORE 'resolveItemProblems(...)'): " + 
				resolvedParts.get(0).getRequestedInventory());
		}
		// process resolved results for processing this order.  Will use both the order and the resolved parts.
		resolveItemProblems(order, resolvedParts);

		// check inventory stage complete
		order.setOrderStatus(OrderStatus.CHECK_INVENTORY);
		order.setItemList(resolvedParts);
		shippingAs.setPromisedShipDate(order);
		if (order.hasAtLeastOneGoodItem() == false)
		{
			// no items to process on the order
			logger.error("processOrder(...): No processable items remaining on order. Process order completing prematurely. Customer PO #: "
					+ customerOrderNumber);
			return order;
		}

		if (UsageType.QUOTE == order.getUsageType())
		{
			// this is a quote so we are done.

			return order;
		}

		// We are passed the IPO stage so this order is going to be processed.  Need to rework the
		// quantities a touch based on user back order policy selection where applicable.
		updateItemQuantities(order);

		if (order.getItemList() != null && order.getItemList().size() > 0) {
			logger.info("processOrder(...): order.getItemList().get(0).getRequestedInventory(): " +
					"(BEFORE 'prepareForOrderSubmissionForAllMarket(...)'): " + 
						order.getItemList().get(0).getRequestedInventory());
		}
		order = prepareForOrderSubmissionForAllMarket(order);
		logger.debug("processOrder(...): Master Order : " + masterOrderNumber + " - Completed order submission preparation.");

		// if order is not allowed to proceed then we stop
		if (canContinueProcessing(order) == false)
		{
			logger.error("processOrder(...): Master Order : " + masterOrderNumber
							+ " - Order submission stopped. Order still has problem line items that need resolution.");
			order.setOrderStatus(OrderStatus.UNPROCESSED);
			return order;
		}

		///Mahaveer Remove

		final List<ItemBO> orgItemBOList = order.getItemList();
		final List<ItemBO> tempItemBOList = checkInventoryProblem(orgItemBOList);
		if (null != tempItemBOList && tempItemBOList.isEmpty())
		{
			logger.error("processOrder(...): Master Order : " + masterOrderNumber + " - tempItemBOList is Empty."
					+ " - Order submission stopped. Order still has problem line items that need resolution.");
			order.setOrderStatus(OrderStatus.UNPROCESSED);
			return order;
		}
		order.setItemList(tempItemBOList);
		/* Attach Header ItemBO for ECC Items */
		addHeaderItems(order.getItemList());
		if (order.getItemList() != null && order.getItemList().size() > 0) {
			logger.info("processOrder(...): order.getItemList().get(0).getRequestedInventory(): (BEFORE 'submitOrder(...)'): " + 
				order.getItemList().get(0).getRequestedInventory());
		}
		// submitOrder
		final OrderBO submittedOrder = submitOrder(order);
		if (submittedOrder != null) {
			logger.info("processOrder(...): orderSavedNoDeliveryCreatedFlag: " + submittedOrder.isOrderSavedButNoDeliveryCreatedFlag());
		}

		/* Remove the Header ItemBO for ECC Items */
		removeHeaderItems(order.getItemList());
		order.setItemList(orgItemBOList);
		// allow any clean up
		final OrderBO completedOrder = postProcessOrder(submittedOrder);

		// create order fulfillments
		createOrderFulfillment(completedOrder);

		submittedOrder.setOrderStatus(OrderStatus.FULFILLED);
		// send confirmation 
		if (order.getOrderSource() != OrderSource.IPO)
		{
			sendOrderConfirmationEmail(completedOrder);
		}

		// persist
		//Update Hybris - Mahaveer
		try
		{
			createIPOOrder(completedOrder);
		}
		catch (final Exception e)
		{
			logger.info("processOrder(...): 'createIPOOrder(completedOrder)' caused an Exception.");
		}
		logger.info("End - IPOOrderASUSCanImpl processOrder(OrderBO order)");

		return completedOrder;
	}

	@Override
	protected List<ItemBO> checkOrderable(final OrderBO anOrder) throws WOMExternalSystemException, WOMTransactionException,
			WOMBusinessDataException, WOMValidationException
	{
		List<ItemBO> resolvedParts;
		// call checkOrderable
		final IPOItemASUSCanImpl itemASUSCan = new IPOItemASUSCanImpl();
		resolvedParts = itemASUSCan.ipoCheckOrderable(anOrder, anOrder.getMstrOrdNbr(), anOrder.getItemList(),
				anOrder.getBillToAcct(), anOrder.getShipToAcct());
		final FreeFreightAS freeFreightAs = new FreeFreightAS();
		// determine free freight here as IPO RFQ may want it
		anOrder.setReceivesFreeFreight(freeFreightAs.isQualifyForFreeFreight(anOrder));
		return resolvedParts;
	}

	@Override
	protected OrderBO prepareForOrderSubmission(final OrderBO order) throws WOMValidationException
	{
		final List<ItemBO> itemList = order.getItemList();
		
		// Get the 'Enable Shipping From TSC' flag for the associated Trading Partner.
		boolean enableShippingFromTscForTp = order.getTradingPartner().isEnableShippingFromTsc();
		logger.info("prepareForOrderSubmission(...): enableShippingFromTscForTp: " + enableShippingFromTscForTp);

		//Check if the order contains both FM parts and Carter parts
		checkCarterMixedOrder(itemList);

		if (itemList != null) {
			logger.info("prepareForOrderSubmission(...): itemList.size(): " + itemList.size());
		}

		for (final ItemBO anItem : itemList)
		{
			if (anItem.canProcessItem())
			{
				final InventoryBO anInventory = anItem.getSelectedInventory();
				final InventoryBO requestedInventory = anItem.getRequestedInventory();
				ShippingCodeBO shippingCode = null;
				logger.info("prepareForOrderSubmission(...): ----------------");

				if (requestedInventory != null && enableShippingFromTscForTp) {
					shippingCode = getShippingCode(anItem, order, true);
					anItem.getRequestedInventory().setShippingCode(shippingCode);
					anItem.getRequestedInventory().setSelectedLocation(true);
					
					logger.info("prepareForOrderSubmission(...): + Replacing 'inventory' with a list containing 'requestedInventory' for this Item");
					List<InventoryBO> requestedInventories = new ArrayList<InventoryBO>();
					requestedInventories.add(requestedInventory);
					// *** Replace this Item's Inventory with Requested Inventory that was passed-in!! ***
					anItem.setInventory(requestedInventories);
				} else {
					logger.info("prepareForOrderSubmission(...): requestedInventory is NULL");
					logger.info("prepareForOrderSubmission(...): NOT replacing 'inventory' with a list containing 'requestedInventory' for this Item");
					shippingCode = getShippingCode(anItem, order);
					anInventory.setShippingCode(shippingCode);					
				}

				logger.info("prepareForOrderSubmission(...): anItem: " + anItem.toString());
				logger.info("prepareForOrderSubmission(...): ----------------");
				
				/*
				 * AccountBO shipTo = order.getShipToAcct();
				 * 
				 * // if this order is IPO, we need to retrieve the shipping method via the trading partner
				 * if(order.getOrderSource() == OrderSource.IPO) { TradingPartnerBO tp = order.getTradingPartner();
				 * if(!assignShippingMethodForInventory(anItem, tp)) { // No shipping method found, do not bother resolving
				 * for shipping codes. continue; } }
				 * 
				 * AddressBO shipToAddress = null; if (shipTo != null) { shipToAddress = shipTo.getAddress(); }
				 * 
				 * if (order.getManualShipToAddress() != null) { shipToAddress =
				 * order.getManualShipToAddress().getAddress(); } if (shipToAddress != null) {
				 * //resolveShippingCodes(anItem, shipToAddress); }
				 */
			}
		}

		return order;
	}

	/**
	 * Finds the SAP and NABS shipping code for each item. All inventory in the item need to have shipping method
	 * identified. Treat as problem if shipping method is not found.
	 * 
	 * @param anItem
	 * @param tradingPartner
	 */
	private boolean assignShippingMethodForInventory(final ItemBO anItem, final TradingPartnerBO tradingPartner)
	{
		TradingPartnerShippingMethodBO tpShippingMethod = null;
		try
		{
			final String scac = anItem.getScacCode();
			if (GenericValidator.isBlankOrNull(scac))
			{
				tpShippingMethod = shippingAs.findTradingPartnerShipMtd(tradingPartner, anItem.getCarrierCode(),
						anItem.getShippingMethodCode());
			}
			else
			{
				tpShippingMethod = shippingAs.findTradingPartnerShipMtd(tradingPartner, anItem.getScacCode());
			}
			anItem.setSelectedShippingMethod(tpShippingMethod.getShippingMethod());
		}
		catch (final WOMPersistanceException e)
		{
			final ProblemBO invalidScac = ProblemBOFactory.createProblem(MessageResourceUtil.INVALID_SCAC_CODE_OR_SHIPPING_METHOD);
			anItem.addProblemItem(invalidScac);
			return false;
		}
		return true;
	}

	protected ShippingCodeBO getShippingCode(final ItemBO theItem, final OrderBO order)
	{ //throws WOMNoResultException , WOMNonUniqueResultException {

		//getTPCarrierShipMthd(final String tpCode, final String dcCode, final String shipToCountry,
		//final String scacCode)

		final String tpCode = String.valueOf(order.getTradingPartner().getTpId());
		final String dcCode = theItem.getSelectedInventory().getDistributionCenter().getCode();
		final String shipToCountry = order.getManualShipToAddress().getAddress().getCountry().getIsoCountryCode();
		final String scacCode = theItem.getScacCode();
		FMDCShippingModel shippingMethodBO = null;
		if (GenericValidator.isBlankOrNull(scacCode))
		{
			shippingMethodBO = getShippingMethodBO(tpCode, dcCode, shipToCountry, theItem.getCarrierCode(),
					theItem.getShippingMethodCode());
		}
		else
		{
			shippingMethodBO = getShippingMethodBO(tpCode, dcCode, shipToCountry, scacCode);
		}

		if (null != shippingMethodBO)
		{
			ShippingCodeBO shippingCode = null;
			if (theItem.getExternalSystem() == ExternalSystem.NABS)
			{
				shippingCode = new ShippingCodeBO();
				final NabsShippingCodeBO nabsShippingCode = new NabsShippingCodeBO();
				nabsShippingCode.setStockShippingCode(shippingMethodBO.getSTOCK_SHIP_CD());
				nabsShippingCode.setEmergencyShippingCode(shippingMethodBO.getEMERG_SHIP_CD());
				shippingCode.setNabsShippingCode(nabsShippingCode);
			}
			else
			{
				final SapShippingCodeBO sapShippingCodeBo = new SapShippingCodeBO();
				sapShippingCodeBo.setIncoTerms(shippingMethodBO.getINCO_TERMS());
				sapShippingCodeBo.setRoute(shippingMethodBO.getROUTE());
				sapShippingCodeBo.setCarrierCode(shippingMethodBO.getSAP_CARRIER_CD());
				shippingCode = new ShippingCodeBO();
				shippingCode.setSapShippingCode(sapShippingCodeBo);
			}
			return shippingCode;
		}
		else
		{
			logger.error("getShippingCode(final ItemBO theItem, final OrderBO order): Shipping Method Object has Null for this Data :: \n" + 
							"tpCode : " + tpCode + ", DC : " + dcCode + ", scacCode: " + scacCode + ", shipToCountry: " + shipToCountry);
			return null;
		}
	}

	protected ShippingCodeBO getShippingCode(final ItemBO theItem, final OrderBO order, final boolean useRequestedInventory)
	{
		if (!useRequestedInventory) {
			return getShippingCode(theItem, order);
		} else {
			final String tpCode = String.valueOf(order.getTradingPartner().getTpId());
			final String dcCode = theItem.getRequestedInventory().getDistributionCenter().getCode();
			final String shipToCountry = order.getManualShipToAddress().getAddress().getCountry().getIsoCountryCode();
			final String scacCode = theItem.getScacCode();
			
			FMDCShippingModel shippingMethodBO = null;
			if (GenericValidator.isBlankOrNull(scacCode))
			{
				shippingMethodBO = getShippingMethodBO(tpCode, dcCode, shipToCountry, theItem.getCarrierCode(),
						theItem.getShippingMethodCode());
			}
			else
			{
				shippingMethodBO = getShippingMethodBO(tpCode, dcCode, shipToCountry, scacCode);
			}

			if (null != shippingMethodBO)
			{
				ShippingCodeBO shippingCode = null;
				if (theItem.getExternalSystem() == ExternalSystem.NABS)
				{
					shippingCode = new ShippingCodeBO();
					final NabsShippingCodeBO nabsShippingCode = new NabsShippingCodeBO();
					nabsShippingCode.setStockShippingCode(shippingMethodBO.getSTOCK_SHIP_CD());
					nabsShippingCode.setEmergencyShippingCode(shippingMethodBO.getEMERG_SHIP_CD());
					shippingCode.setNabsShippingCode(nabsShippingCode);
				}
				else
				{
					final SapShippingCodeBO sapShippingCodeBo = new SapShippingCodeBO();
					sapShippingCodeBo.setIncoTerms(shippingMethodBO.getINCO_TERMS());
					sapShippingCodeBo.setRoute(shippingMethodBO.getROUTE());
					sapShippingCodeBo.setCarrierCode(shippingMethodBO.getSAP_CARRIER_CD());
					shippingCode = new ShippingCodeBO();
					shippingCode.setSapShippingCode(sapShippingCodeBo);
				}
				return shippingCode;
			}
			else
			{
				logger.error("getShippingCode(final ItemBO theItem, final OrderBO order, final boolean useRequestedInventory): " + 
								"Shipping Method Object has Null for this Data :: \ntpCode : " + tpCode + ", DC : " + dcCode + ", scacCode: " + 
								scacCode + ", shipToCountry: " + shipToCountry);
				return null;
			}
		}
	}

	/* We need to add Headers for ECC(ExternalSystem.EVRST) Items */
	private void addHeaderItems(final List<ItemBO> itemList)
	{
		if (itemList != null && (!itemList.isEmpty()))
		{
			final List<Integer> lineNumbersList = new ArrayList<Integer>(itemList.size());
			final List<ItemBO> headerItemsList = new ArrayList<ItemBO>();
			int processOrderLineNumber = 1;
			for (final ItemBO anItem : itemList)
			{
				lineNumbersList.add(anItem.getLineNumber());
				if (ExternalSystem.EVRST == anItem.getExternalSystem())
				{
					//Header ItemBO for current ECC Item
					final ItemBO anHeader = new PartBO();
					anHeader.setDisplayPartNumber(anItem.getDisplayPartNumber());
					anHeader.setPartNumber(anItem.getPartNumber());
					anHeader.setComment("This is The Header Record :: " + anItem.getPartNumber());
					anHeader.setInventory(anItem.getInventory());
					anHeader.setRequestedInventory(anItem.getRequestedInventory());
					anHeader.setScacCode(anItem.getScacCode());
					anHeader.setCarrierCode(anItem.getCarrierCode());
					anHeader.setShippingMethodCode(anItem.getShippingMethodCode());
					anHeader.setTransportationMethodCode(anItem.getTransportationMethodCode());
					((PartBO) anHeader).setHeader(true);
					((PartBO) anHeader).setProcessOrderLineNumber(processOrderLineNumber);

					anHeader.setItemQty(anItem.getItemQty());
					anHeader.setExternalSystem(ExternalSystem.EVRST);

					//Make Sure Actual Item also has the same ProcessOrderLineNumber
					anItem.setProcessOrderLineNumber(processOrderLineNumber);

					headerItemsList.add(anHeader);
					processOrderLineNumber++;
				}
			}
			if (!headerItemsList.isEmpty())
			{
				//Find the Highest line number
				Arrays.sort(lineNumbersList.toArray());
				int itemLineNumber = lineNumbersList.get(lineNumbersList.size() - 1);
				for (final ItemBO headerItem : headerItemsList)
				{
					headerItem.setLineNumber(++itemLineNumber);
				}
				//Make Sure the Header Items are inserted first - Enforced by ECC RFC
				itemList.addAll(0, headerItemsList);
				logger.debug("addHeaderItems(...): Added Header Items for ECC Items " + headerItemsList.size());
			}
		}
	}

	/* We need to remove the Header added for ECC(ExternalSystem.EVRST) Items */
	private void removeHeaderItems(final List<ItemBO> itemList)
	{
		if (itemList != null && (!itemList.isEmpty()))
		{
			final Iterator<ItemBO> itemListIterator = itemList.iterator();
			while (itemListIterator.hasNext())
			{
				final ItemBO itemBO = itemListIterator.next();
				if (itemBO.isHeader())
				{
					itemListIterator.remove();
				}
			}
		}
	}

	/* we need to process the no inventory items */

	private List<ItemBO> checkInventoryProblem(final List<ItemBO> itemList)
	{
		final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
		if (itemList != null && (!itemList.isEmpty()))
		{
			final Iterator<ItemBO> itemListIterator = itemList.iterator();
			while (itemListIterator.hasNext())
			{
				final ItemBO itemBO = itemListIterator.next();
				boolean isProblemPart = false;
				if (null != itemBO.getProblemItemList() && !itemBO.getProblemItemList().isEmpty())
				{
					final List<ProblemBO> problemBOList = itemBO.getProblemItemList();
					for (final ProblemBO problem : problemBOList)
					{
						if (MessageResourceUtil.NO_INVENTORY.equals(problem.getMessageKey()))
						{
							isProblemPart = true;
						}
					}
				}
				if (!isProblemPart)
				{
					itemBOList.add(itemBO);
				}
			}

		}
		return itemBOList;
	}

	private BillToAcctBO getBillTOAccount(final String billToAcc)
	{

		final FMCustomerAccountModel fmCustomerAccModel = getAccountByNabs(billToAcc);
		if (fmCustomerAccModel == null) {
			logger.info("getBillTOAccount(...): fmCustomerAccModel is null for billToAcc '" + billToAcc + "'");
		}
		final BillToAcctBO billToAcct = new BillToAcctBO();

		billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//soldToUnit.getNabsAccountCode());
		billToAcct.setAccountName(fmCustomerAccModel.getLocName());
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

		AddressModel addres = fmCustomerAccModel.getBillingAddress();
		for (final AddressModel address : fmCustomerAccModel.getAddresses())
		{
			addres = address;
		}

		final AddressBO anAddress = new AddressBO();
		anAddress.setCity(addres.getTown());
		anAddress.setAddrLine1(addres.getLine1());
		anAddress.setAddrLine2(addres.getLine2());
		anAddress.setZipOrPostalCode(addres.getPostalcode());
		anAddress.setStateOrProv(addres.getRegion().getIsocodeShort());
		final CountryBO country = new CountryBO();
		country.setIsoCountryCode(addres.getCountry().getIsocode());
		anAddress.setCountry(country);
		billToAcct.setAddress(anAddress);
		return billToAcct;
	}

	private ShipToAcctBO getShiptoAccount(final String shipToAcc)
	{
		final FMCustomerAccountModel fmCustomerAccModel = getAccountByNabs(shipToAcc);
		final ShipToAcctBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//soldToUnit.getNabsAccountCode());
		shipToAcct.setAccountName(fmCustomerAccModel.getLocName());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
		shipToAcct.setSapAccount(sapAccount);

		AddressModel addres = fmCustomerAccModel.getBillingAddress();
		for (final AddressModel address : fmCustomerAccModel.getAddresses())
		{
			addres = address;
		}

		final AddressBO anAddress = new AddressBO();
		anAddress.setCity(addres.getTown());
		anAddress.setAddrLine1(addres.getLine1());
		anAddress.setAddrLine2(addres.getLine2());
		anAddress.setZipOrPostalCode(addres.getPostalcode());
		anAddress.setStateOrProv(addres.getRegion().getIsocodeShort());
		final CountryBO country = new CountryBO();
		country.setIsoCountryCode(addres.getCountry().getIsocode());
		anAddress.setCountry(country);
		shipToAcct.setAddress(anAddress);
		return shipToAcct;
	}

	private FlexibleSearchService getFlexibleSearchService()
	{
		final FlexibleSearchService flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext().getBean(
				"flexibleSearchService");
		return flexibleSearchService;
	}

	private FMCustomerAccountModel getAccountByNabs(final String accountId)
	{

		final StringBuilder query = new StringBuilder("SELECT {" + FMCustomerAccountModel.PK + "}");
		query.append("FROM {" + FMCustomerAccountModel._TYPECODE + "}");
		query.append("WHERE {" + FMCustomerAccountModel.NABSACCOUNTCODE + "} ='" + accountId + "'");

		final SearchResult<FMCustomerAccountModel> result = getFlexibleSearchService().search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{
			final List<FMCustomerAccountModel> listAccountId = result.getResult();
			final FMCustomerAccountModel accountIdModel = listAccountId.iterator().next();
			return accountIdModel;
		}
		else
		{
			logger.info("getAccountByNabs(...): Search returned null or empty result for NABS Account Code '" + accountId + "'");
			return null;
		}
	}

	private FMDCShippingModel getShippingMethodBO(final String tpCode, final String dcCode, final String shipToCountry,
			final String scacCode)
	{
		FMDCShippingModel shippingmethod = null;
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {sm:" + FMDCShippingModel.PK + "} ");
		query.append(" FROM  {" + FMDCShippingModel._TYPECODE + " AS sm ");
		query.append(" JOIN " + FMtpcarriershipmethodsModel._TYPECODE + " AS csm");
		query.append(" ON {sm." + FMDCShippingModel.CARRIER_CD + "} = {csm." + FMtpcarriershipmethodsModel.CARRIER_CD + "} ");
		query.append(" AND {sm." + FMDCShippingModel.SHIP_MTHD_CD + "} = {csm." + FMtpcarriershipmethodsModel.SHIP_MTHD_CD + "} }");
		query.append(" WHERE {sm." + FMDCShippingModel.DIST_CNTR_CD + "} = ?" + FMDCShippingModel.DIST_CNTR_CD);
		query.append(" AND {csm." + FMtpcarriershipmethodsModel.SCAC_CD + "} = ?" + FMtpcarriershipmethodsModel.SCAC_CD);
		query.append(" AND {csm." + FMtpcarriershipmethodsModel.TP_ID + "} = ?" + FMtpcarriershipmethodsModel.TP_ID);
		query.append(" AND {sm." + FMDCShippingModel.TO_ISO_CNTRY_CD + "} = ?" + FMDCShippingModel.TO_ISO_CNTRY_CD);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.DIST_CNTR_CD, dcCode);
		flexibleSearchQuery.addQueryParameter(FMtpcarriershipmethodsModel.SCAC_CD, scacCode.toUpperCase());
		flexibleSearchQuery.addQueryParameter(FMtpcarriershipmethodsModel.TP_ID, tpCode);
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.TO_ISO_CNTRY_CD, shipToCountry.toUpperCase());

		final SearchResult<FMDCShippingModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			shippingmethod = searchResult.getResult().get(0);
			return shippingmethod;
		}
		else
		{
			return shippingmethod;
		}
	}

	/**
	 * @param tpCode
	 * @param dcCode
	 * @param shipToCountry
	 * @param carrierCode
	 * @param shippingMethodCode
	 * @return
	 */
	private FMDCShippingModel getShippingMethodBO(final String tpCode, final String dcCode, final String shipToCountry,
			final String carrierCode, final String shippingMethodCode)
	{
		FMDCShippingModel shippingmethod = null;
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {sm:" + FMDCShippingModel.PK + "} ");
		query.append(" FROM  {" + FMDCShippingModel._TYPECODE + " AS sm ");
		query.append(" JOIN " + FMtpcarriershipmethodsModel._TYPECODE + " AS csm");
		query.append(" ON {sm." + FMDCShippingModel.CARRIER_CD + "} = {csm." + FMtpcarriershipmethodsModel.CARRIER_CD + "} ");
		query.append(" AND {sm." + FMDCShippingModel.SHIP_MTHD_CD + "} = {csm." + FMtpcarriershipmethodsModel.SHIP_MTHD_CD + "} }");
		query.append(" WHERE {sm." + FMDCShippingModel.DIST_CNTR_CD + "} = ?" + FMDCShippingModel.DIST_CNTR_CD);
		query.append(" AND {sm." + FMDCShippingModel.CARRIER_CD + "} = ?" + FMDCShippingModel.CARRIER_CD);
		query.append(" AND {sm." + FMDCShippingModel.SHIP_MTHD_CD + "} = ?" + FMDCShippingModel.SHIP_MTHD_CD);
		query.append(" AND {csm." + FMtpcarriershipmethodsModel.TP_ID + "} = ?" + FMtpcarriershipmethodsModel.TP_ID);
		query.append(" AND {sm." + FMDCShippingModel.TO_ISO_CNTRY_CD + "} = ?" + FMDCShippingModel.TO_ISO_CNTRY_CD);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.DIST_CNTR_CD, dcCode);
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.CARRIER_CD, carrierCode.toUpperCase());
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.SHIP_MTHD_CD, shippingMethodCode.toUpperCase());
		flexibleSearchQuery.addQueryParameter(FMtpcarriershipmethodsModel.TP_ID, tpCode);
		flexibleSearchQuery.addQueryParameter(FMDCShippingModel.TO_ISO_CNTRY_CD, shipToCountry.toUpperCase());

		final SearchResult<FMDCShippingModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			shippingmethod = searchResult.getResult().get(0);
		}

		return shippingmethod;
	}

	/**
	 * This method is used to Store the IPO orders into Hybris for using to retrieve the order status(GS Services)
	 * 
	 * @param completedOrder
	 */
	private void createIPOOrder(final OrderBO completedOrder)
	{
		// YTODO Auto-generated method stub
		final FMIPOOrderModel order = getmodelService().create(FMIPOOrderModel.class);
		order.setCode(completedOrder.getMstrOrdNbr().trim());
		order.setBilltoparty(getAccountByNabs(completedOrder.getBillToAcct().getAccountCode()));
		order.setShiptoparty(getAccountByNabs(completedOrder.getShipToAcct().getAccountCode()));
		order.setPONumber(completedOrder.getCustPoNbr());
		order.setOrdercomments(completedOrder.getComment1() + completedOrder.getComment2() + completedOrder.getComment3());
		order.setUserFirstName("IPO USER");
		getmodelService().save(order);
		createIPOOrderEntry(order, completedOrder.getItemList());
	}

	/**
	 * @param order
	 * @param list
	 */
	private void createIPOOrderEntry(final FMIPOOrderModel order, final List<ItemBO> items)
	{
		// YTODO Auto-generated method stub
		if (null != items && !items.isEmpty())
		{
			for (final ItemBO item : items)
			{
				final FMIPOOrderEntryModel orderEntry = getmodelService().create(FMIPOOrderEntryModel.class);
				orderEntry.setOrderentryNumber(order.getCode().trim() + "_" + String.valueOf(item.getLineNumber()).toString());
				orderEntry.setPartNumber(item.getDisplayPartNumber());
				orderEntry.setRawPartNumber(item.getPartNumber());
				orderEntry.setPartDescription(item.getDescription());
				orderEntry.setScaccode(item.getScacCode());
				orderEntry.setBrandAAIAId(item.getAaiaBrand());
				orderEntry.setShippingMethod(item.getShippingMethodCode());
				orderEntry.setTransportationMethod(item.getTransportationMethodCode());
				orderEntry.setCommonCarrier(item.getCarrierCode());
				orderEntry.setOrder(order);
				orderEntry.setPromisedShipDate(item.getPromisedShipDate());
				orderEntry.setBasePrice(Double.valueOf(item.getItemPrice().getDisplayPrice()));
				for (final SupplierCdProductFlgBO supplier : item.getSupplierCodeProductFlagList())
				{
					orderEntry.setSuppliercode(supplier.getSupplierCd());
				}
				orderEntry.setQuantity(Integer.valueOf(item.getItemQty().getRequestedQuantity()));
				for (final InventoryBO inventory : item.getInventory())
				{
					orderEntry.setShipfromparty(getFMDistributionCenterBO(inventory.getDistributionCenter().getCode()));
				}
				getmodelService().save(orderEntry);
			}
		}
	}

	private FMDCCenterModel getFMDistributionCenterBO(final String dcCode)
	{
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {dc:" + FMDCCenterModel.PK + "} ");
		query.append(" FROM  {" + FMDCCenterModel._TYPECODE + " AS dc }");
		query.append(" WHERE {dc." + FMDCCenterModel.DCCODE + "} = ?" + FMDCCenterModel.DCCODE);
		logger.debug("getFMDistributionCenterBO(): dcCode: " + dcCode);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMDCCenterModel.DCCODE, dcCode.trim());

		final SearchResult<FMDCCenterModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			final FMDCCenterModel dc = searchResult.getResult().get(0);

			return dc;
		}
		else
		{
			return null;
		}
	}

	private ModelService getmodelService()
	{
		final ModelService modelService = (ModelService) Registry.getApplicationContext().getBean("modelService");
		return modelService;
	}
}

package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.common.util.MessageResourceConstants;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;


public class InventoryASUSCanImpl extends InventoryASBase implements InventoryAS, USCanAS
{

	private static Logger logger = Logger.getLogger(InventoryASUSCanImpl.class);

	private final NabsService nabsService;

	private final MarketAS marketASUSCan;

	@Override
	protected MarketAS getMarketAS()
	{
		return marketASUSCan;
	}

	public InventoryASUSCanImpl()
	{
		super();
		marketASUSCan = new MarketASUSCanImpl();
		nabsService = WOMServices.getNabsService();
	}

	@Override
	public Market getMarket()
	{
		return Market.US_CANADA;
	}

	@Override
	public List<ItemBO> checkInventory(final String masterOrderNumber, final List<ItemBO> itemList, final AccountBO billToAcct,
			final AccountBO shipToAcct) throws WOMBusinessDataException, WOMExternalSystemException, WOMTransactionException,
			WOMValidationException
	{
		return null;
	}

	/**
	 * Make Check Inventory calls to both NABS and SAP ERP systems. Then determine and set problems for each item.
	 * @param masterOrderNumber
	 * @param itemList
	 * @param billToAcct
	 * @param shipToAcct
	 * @param salesOrg
	 * @param orderType
	 * @param orderSource
     * @param bypassMultipleInventoryLocationsProblem For Emergency orders, this flag can be set to TRUE (when calling Check Inventory with
     * 													Order Type as 'Stock') so all Inventory Locations returned by SAP can be displayed
     * 													on the screen thereby allowing GATP Proposed Inventory Locations override capability!!
	 * @return
	 * @throws WOMBusinessDataException
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMValidationException
	 */
	public List<ItemBO> checkInventory(final String masterOrderNumber, final List<ItemBO> itemList, final AccountBO billToAcct,
			final AccountBO shipToAcct, final CustomerSalesOrgBO salesOrg, final OrderType orderType, final OrderSource orderSource,
			boolean bypassMultipleInventoryLocationsProblem
		    /*
		     * ,
			 * String
			 * paymentMethod
			 */)
			throws WOMBusinessDataException, WOMExternalSystemException, WOMTransactionException, WOMValidationException
	{

		logger.info("checkInventory(...): bypassMultipleInventoryLocationsProblem: " + bypassMultipleInventoryLocationsProblem);
		final List<ItemBO> nabsList = new ArrayList<ItemBO>();
		final List<ItemBO> sapList = new ArrayList<ItemBO>();

		// for the given input list, put the ones that do not have problems into the appropriate external system list.
		for (final ItemBO lineItem : itemList)
		{
			// no need to check it if we can't process it
			if (lineItem.canProcessItem())
			{
				if (lineItem.getExternalSystem() == ExternalSystem.NABS)
				{
					nabsList.add(lineItem);
				}
				else if (lineItem.getExternalSystem() == ExternalSystem.EVRST)
				{
					sapList.add(lineItem);
				}
			}
		}

		final String billToAcctCode = billToAcct != null ? billToAcct.getAccountCode() : null;
		String shipToAcctCode = shipToAcct != null ? shipToAcct.getAccountCode() : null;
		// call NABS check inventory
		if (nabsList.size() > 0)
		{
			nabsService.checkInventory(masterOrderNumber, nabsList, billToAcctCode, shipToAcctCode, orderType);
		}
		final SapAcctBO sapAcct = billToAcct != null ? billToAcct.getSapAccount() : null;
		final String sapAccoutCode = sapAcct != null ? sapAcct.getSapAccountCode() : null;

		//For ShipToAcctCode
		final SapAcctBO sapShipToAcct = shipToAcct != null ? shipToAcct.getSapAccount() : null;
		final String sapShipToAccoutCode = sapShipToAcct != null ? sapShipToAcct.getSapAccountCode() : null;
		shipToAcctCode = sapShipToAccoutCode != null ? sapShipToAccoutCode : shipToAcctCode;

		// Call SAP check Inventory
		SAPService.checkInventory(sapList, sapAccoutCode, shipToAcctCode, salesOrg, orderType.getEconCode(),
				orderSource.getOrderSource()/* ,paymentMethod */);

		if (sapList != null && sapList.size() > 0 && sapList.get(0).getInventory() != null) {
			logger.debug("checkInventory(...): sapList.get(0).getInventory().size(): " + sapList.get(0).getInventory().size());
		}

		if (itemList != null && itemList.size() > 0 && itemList.get(0).getInventory() != null) {
			logger.debug("checkInventory(...): itemList.get(0).getInventory().size() (BEFORE setInventoryProblem(...)): " + itemList.get(0).getInventory().size());
		}

		// Go through Item list, find out no inventory, no sufficient inventory
		// problem
		if (bypassMultipleInventoryLocationsProblem) {
			setInventoryProblem(itemList, orderSource, bypassMultipleInventoryLocationsProblem);
		} else {
			setInventoryProblem(itemList, orderSource);
		}
		if (itemList != null && itemList.size() > 0 && itemList.get(0).getInventory() != null) {
			logger.debug("checkInventory(...): itemList.get(0).getInventory().size() (AFTER setInventoryProblem(...)): " + itemList.get(0).getInventory().size());
		}

		return itemList;
	}

	public List<ItemBO> checkInventory(final String masterOrderNumber, final List<ItemBO> itemList, final AccountBO billToAcct,
			final AccountBO shipToAcct, final CustomerSalesOrgBO salesOrg, final OrderType orderType, final OrderSource orderSource
		    /*
		     * ,
			 * String
			 * paymentMethod
			 */)
			throws WOMBusinessDataException, WOMExternalSystemException, WOMTransactionException, WOMValidationException
	{
		return checkInventory(masterOrderNumber, itemList, billToAcct, shipToAcct, salesOrg, orderType, orderSource, false);
	}

	public List<ItemBO> tscPickUpCheckInventory(final List<ItemBO> itemsList, final AccountBO billToAcct,
			final AccountBO shipToAcct, final CustomerSalesOrgBO salesOrg, final OrderType orderType, final OrderSource orderSource)
			throws WOMBusinessDataException, WOMExternalSystemException, WOMTransactionException, WOMValidationException
	{
		final List<ItemBO> eccItemsList = new ArrayList<ItemBO>();

		for (final ItemBO lineItem : itemsList)
		{
			// no need to check it if we can't process it
			if (lineItem.canProcessItem())
			{
				if (lineItem.getExternalSystem() == ExternalSystem.EVRST)
				{
					eccItemsList.add(lineItem);
				}
			}
		}

		final SapAcctBO sapBillToAcct = billToAcct != null ? billToAcct.getSapAccount() : null;
		final String sapBillToAccoutCode = sapBillToAcct != null ? sapBillToAcct.getSapAccountCode() : null;

		final SapAcctBO sapShipToAcct = shipToAcct != null ? shipToAcct.getSapAccount() : null;
		final String sapshipToAccoutCode = sapShipToAcct != null ? sapShipToAcct.getSapAccountCode() : null;

		SAPService.tscPickUpcheckInventory(eccItemsList, sapBillToAccoutCode, sapshipToAccoutCode, salesOrg,
				orderType.getEconCode(), orderSource.getOrderSource());

		setInventoryProblem(eccItemsList, orderSource);
		return eccItemsList;
	}

	/**
	 * Reduce Inventory List to Selected Inventory Location OR Main DC Inventory Location for ALL items in the passed-in Item List
	 * unless the Items are found in multiple locations. All other Inventory Locations are discarded.
	 * 
	 * @param itemList List<ItemBO> List of Items to work on
	 * @return Updated list of Items with either Selected Inventory Location OR Main DC Inventory Location retained 
	 */
	protected List<ItemBO> getSelectedInventoryForAllItems(final List<ItemBO> itemList)
	{
		for (final ItemBO anItem : itemList)
		{
			getSelectedInventoryForAnItem(anItem);
		}
		return itemList;
	}

	/**
	 * Reduce Inventory List to Selected Inventory Location OR Main DC Inventory Location for the passed-in Item
	 * unless the Item is found in multiple locations. All other Inventory Locations are discarded.
	 * 
	 * @param anItem ItemBO Item to work on
	 * @return Updated Item with either Selected Inventory Location OR Main DC Inventory Location retained 
	 */
	protected ItemBO getSelectedInventoryForAnItem(final ItemBO anItem) {
		if (!isPartFoundInMultipleLocations(anItem)) {
			anItem.setInventory(shortListRelevantInventory(anItem));
			anItem.setAdditionalInventory(new ArrayList<InventoryBO>());
		}
		
		return anItem;
	}

	/**
	 * Use Selected Inventory Location OR segregate Selected Inventory Location from Additional Inventory Locations based on
	 * whether Shipping from TSCs has been enabled for an IPO Trading Partner.
	 * 
	 *  @param itemList List<ItemBO> List of Items to work on
	 *  @param shippingFromTscEnabledForTP boolean Flag indicating whether Shipping from TSCs has been enabled for an IPO Trading Partner
	 */
	protected List<ItemBO> getSelectedOrSegregateInventoryForAllItems(final List<ItemBO> itemList, boolean shippingFromTscEnabledForTP) {
		if (!shippingFromTscEnabledForTP) { // Shipping from TSC Not Enabled
			// Use only Selected Inventory Location for all items in the passed-in Item List.
			getSelectedInventoryForAllItems(itemList);
		} 
		else // Shipping from TSC Enabled
		{
			// Segregate Selected Inventory Location from Additional Inventory Location.
			segregateSelectedAndAdditionalInventoryForAllItems(itemList);
		}
		
		return itemList;
	}

	/**
	 * Segregate Selected Inventory Location from Additional Inventory Locations for all items in the passed-in Item List.
	 * @param itemList List<ItemBO> List of Items to work on
	 * @return Updated list of Items with segregated Inventory Locations
	 */
	protected List<ItemBO> segregateSelectedAndAdditionalInventoryForAllItems(final List<ItemBO> itemList)
	{
		// If a part is found in more than one location, separate the "Selected" inventory from the rest.
		for (final ItemBO anItem : itemList)
		{
			segregateSelectedAndAdditionalInventoryForAnItem(anItem);
		}

		return itemList;
	}

	/**
	 * Segregate Selected Inventory Location from Additional Inventory Locations for the passed-in Item.
	 * @param anItem ItemBO Item to work on
	 * @return Updated Item with segregated Inventory Locations
	 */
	protected ItemBO segregateSelectedAndAdditionalInventoryForAnItem(ItemBO anItem) {
		logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): Line # - Display Part #: " + anItem.getLineNumber() + " - " + anItem.getDisplayPartNumber());
		logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): anItem.getInventory().size(): " + anItem.getInventory().size());

		List<InventoryBO> shortListedInventory = shortListRelevantInventory(anItem);
		if (shortListedInventory != null) {
			logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): shortListedInventory.size(): " + shortListedInventory.size());
		} else {
			logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): shortListedInventory is null");
		}

		if (!isPartFoundInMultipleLocations(anItem) || shortListedInventory == null || shortListedInventory.size() == 0) {
			anItem.setInventory(shortListedInventory);
			anItem.setAdditionalInventory(new ArrayList<InventoryBO>());
		} else { // Part found in multiple locations OR short-listed inventory is not null or empty

			List<InventoryBO> additionalInventory = new ArrayList<InventoryBO>();
			boolean sufficientQtyAvailable = hasSufficientQty(shortListedInventory, anItem);
			if (sufficientQtyAvailable) { // Short-listed Inventory has sufficient qty
				// Get remaining inventory locations. We will set them as additional inventory.
				additionalInventory = getRemainingInventoryList(anItem, shortListedInventory);
			
				anItem.setInventory(shortListedInventory);
				anItem.setAdditionalInventory(additionalInventory);
			} else { // Short-listed Inventory does NOT have sufficient qty
				anItem = handleInsufficientQtyInShortlistedInventory(shortListedInventory, anItem);
				if (shortListedInventory != null) {
					logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): shortListedInventory.size(): " + shortListedInventory.size());
				}
			}
		}

		if (anItem.getAdditionalInventory() != null) {
			logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): anItem.getAdditionalInventory().size(): " + anItem.getAdditionalInventory().size());
		} else {
			logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): anItem.getAdditionalInventory() is null");
		}
		logger.info("segregateSelectedAndAdditionalInventoryForAnItem(...): ----------");

		return anItem;
	}

	// Utility method to check the passed-in part and return true 
	// if it contains a PART_FOUND_IN_MULTIPLE_LOCATIONS problem.
	public boolean isPartFoundInMultipleLocations(final ItemBO anItem)
	{
		if (anItem.getProblemItemList() == null)
		{
			return false;
		}

		for (final ProblemBO aProblem : anItem.getProblemItemList())
		{
			if (MessageResourceConstants.PART_FOUND_IN_MULTIPLE_LOCATIONS.equals(aProblem.getMessageKey()))
			{
				// found one
				return true;
			}
		}

		// made it through
		return false;
	}

	/**
	 * Selects inventory that is relevant or selected or that can fulfill the request. The MainDC is the inventory that
	 * is selected by default as it is returned by NABS and is needed to be supplied back to NABS. The selected inventory
	 * is the next best that can fulfill the request.
	 * 
	 * @param inventory
	 * @return List of reduced selected inventories.
	 */
	private List<InventoryBO> shortListRelevantInventory(final ItemBO anItem)
	{
		final InventoryBO selected = anItem.getSelectedInventory();
		final InventoryBO main = anItem.getMainDCInventory();

		logger.info("shortListRelevantInventory(...): selected: " + selected);
		logger.info("shortListRelevantInventory(...): main: " + main);
		logger.info("shortListRelevantInventory(...): ------------------------");
		
		final Set<InventoryBO> temp = new HashSet<InventoryBO>();
		if (selected != null)
		{
			temp.add(selected);
		}
		if (main != null)
		{
			temp.add(main);
		}

		return new ArrayList<InventoryBO>(temp);
	}
	
	private boolean hasSufficientQty(List<InventoryBO> inventoryList, ItemBO anItem) {
		if (inventoryList == null || inventoryList.size() == 0 || anItem == null) {
			return false;
		}
		
		int requestedQty = anItem.getItemQty().getRequestedQuantity();
		logger.info("hasSufficientQty(...): requestedQty: " + requestedQty);
		InventoryBO tempInventory = null;
		for (InventoryBO inventory : inventoryList) {
			if (tempInventory == null) {
				if (inventory.getAvailableQty() >= requestedQty) {
					tempInventory = inventory;
				}
			} else if (inventory.getAvailableQty() > tempInventory.getAvailableQty()) {
				tempInventory = inventory;
			}
		}
		
		if (tempInventory == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * To return a list of inventory locations excluding the passed-in inventory locations.
	 * @param anItem
	 * @param excludeInventoryList
	 * @return
	 */
	private List<InventoryBO> getRemainingInventoryList(ItemBO anItem, List<InventoryBO> excludeInventoryList) {
		List<InventoryBO> remainingInventory = new ArrayList<InventoryBO>();
		String inventoryDcCode = "";
		for (InventoryBO anInventory : anItem.getInventory()) {
			inventoryDcCode = anInventory.getDistributionCenter().getCode();
			logger.info("getRemainingInventoryList(...): inventoryDcCode: " + inventoryDcCode);
			for (InventoryBO anExcludeInventory : excludeInventoryList) {
				logger.info("getRemainingInventoryList(...): anExcludeInventory DC Code: " + anExcludeInventory.getDistributionCenter().getCode());
				if (!inventoryDcCode.equals(anExcludeInventory.getDistributionCenter().getCode())) {
					logger.info("getRemainingInventoryList(...): Adding DC Code '" + inventoryDcCode + "' to additionalInventory");
					remainingInventory.add(anInventory);
				}
			}				
		}
		
		return remainingInventory;
	}

	private ItemBO handleInsufficientQtyInShortlistedInventory(List<InventoryBO> shortListedInventory, ItemBO anItem) {
		List<InventoryBO> remainingInventory = getRemainingInventoryList(anItem, shortListedInventory);

		List<InventoryBO> tempInventoryList = new ArrayList<InventoryBO>();
		InventoryBO tempRegInventory = null;
		InventoryBO tempTscInventory = null;

		logger.info("handleInsufficientQtyInShortlistedInventory(...): shortListedInventory has insufficient/zero qty!");
		// Identify regular DC and TSC with highest available qty.
		for (InventoryBO aRemainingInventory : remainingInventory) {
			if (aRemainingInventory.getAvailableQty() <= 0) {
				continue;
			}

			if (aRemainingInventory.getDistributionCenter().getTscFlag() != 1) { // Regular DC
				if (tempRegInventory == null || aRemainingInventory.getAvailableQty() > tempRegInventory.getAvailableQty()) {
					tempRegInventory = aRemainingInventory;
				}
			} else { // TSC
				if (tempTscInventory == null || aRemainingInventory.getAvailableQty() > tempTscInventory.getAvailableQty()) {
					tempTscInventory = aRemainingInventory;
				}
			}
		}
		
		boolean zeroInventoryFound = false;
		if (tempRegInventory == null && tempTscInventory != null) { // None of the regular DCs has sufficient qty
			logger.info("handleInsufficientQtyInShortlistedInventory(...): Adding TSC with Code '" + 
							tempTscInventory.getDistributionCenter().getCode() + "' to 'tempInventoryList'");
			// Some TSC in additional inventory locations has the ordered qty.
			tempTscInventory.setSelectedLocation(true);
			tempInventoryList.add(tempTscInventory);
		} else if (tempRegInventory != null && tempTscInventory != null) { // Some regular DC(s) and some TSC(s) have sufficient qty
			logger.info("handleInsufficientQtyInShortlistedInventory(...): Both regular DC and TSC in remaining inventory are not null. So, comparing them...");
			if (tempRegInventory.getAvailableQty() >= tempTscInventory.getAvailableQty()) {
				logger.info("handleInsufficientQtyInShortlistedInventory(...): Regular DC with Code '" + 
							tempRegInventory.getDistributionCenter().getCode() + "' has more Available Qty than TSC with Code '" + 
							tempTscInventory.getDistributionCenter().getCode() + "'. Will use that.");
				tempRegInventory.setSelectedLocation(true);
				tempInventoryList.add(tempRegInventory);
			} else {
				logger.info("handleInsufficientQtyInShortlistedInventory(...): TSC with Code '" + 
							tempTscInventory.getDistributionCenter().getCode() + "' has more Available Qty than regular DC with Code '" + 
							tempRegInventory.getDistributionCenter().getCode() + "'. Will use that.");
				tempTscInventory.setSelectedLocation(true);
				tempInventoryList.add(tempTscInventory);
			}
		} else if (tempTscInventory == null) { // Regular DC has the ordered qty
			logger.info("handleInsufficientQtyInShortlistedInventory(...): Adding regular DC with Code '" +
						tempRegInventory.getDistributionCenter().getCode() + "' to 'tempInventoryList'");
			tempRegInventory.setSelectedLocation(true);
			tempInventoryList.add(tempRegInventory);
		} else {
			logger.info("handleInsufficientQtyInShortlistedInventory(...): *** zeroInventoryFound = true ***");
			zeroInventoryFound = true;
		}
		
		if (!zeroInventoryFound) {
			logger.info("handleInsufficientQtyInShortlistedInventory(...): Setting inventory for this item to 'tempInventoryList'");
			anItem.setInventory(tempInventoryList);
			logger.info("handleInsufficientQtyInShortlistedInventory(...): Recreating the additional inventory...");
			List<InventoryBO> additionalInventory = new ArrayList<InventoryBO>();
			// Loop thru the additional inventory list, compare it with the latest Inventory List and filter out any existing inventory location.
			for (InventoryBO aRemainingInventory : remainingInventory) {
				if (!tempInventoryList.get(0).getDistributionCenter().getCode().equals(aRemainingInventory.getDistributionCenter().getCode())) {
					additionalInventory.add(aRemainingInventory);
				}
			}
			anItem.setAdditionalInventory(additionalInventory);
		} else {
			anItem.setInventory(new ArrayList<InventoryBO>());
			anItem.setInventory(new ArrayList<InventoryBO>());
		}
		
		return anItem;
	}
	
}

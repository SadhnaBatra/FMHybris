package com.fmo.wom.business.as;

import java.util.List;

import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;


public interface InventoryAS
{


	/**
	 * Check the inventory on the parts in the given item list using the given bill to accounts. This will assume a null
	 * ship to account.
	 * 
	 * @param masterOrderNumber
	 *           master order number for this call to check inventory
	 * @param itemList
	 *           list of parts to get inventories for
	 * @param bilToAcct
	 *           the bill to account to get inventories for - affects home DC returned.
	 * @param orderSource
	 *           IPO or WEB
	 * @return
	 * @throws WOMBusinessDataException
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMValidationException
	 */
	public List<ItemBO> checkInventory(String masterOrderNumber, List<ItemBO> itemList, AccountBO billToAcct, AccountBO shipToAcct)
			throws WOMBusinessDataException, WOMExternalSystemException, WOMTransactionException, WOMValidationException;


	/**
	 * Check the inventory on the parts in the given item list using the given bill to accounts. This will assume a null
	 * ship to account.
	 * 
	 * @param masterOrderNumber
	 *           master order number for this call to check inventory
	 * @param itemList
	 *           list of parts to get inventories for
	 * @param bilToAcct
	 *           the bill to account to get inventories for - affects home DC returned.
	 * @param orderSource
	 *           IPO or WEB
	 * @return
	 * @throws WOMBusinessDataException
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMValidationException
	 */
	public List<ItemBO> checkInventory(String masterOrderNumber, List<ItemBO> itemList, AccountBO billToAcct,
			AccountBO shipToAcct, CustomerSalesOrgBO salesOrg, OrderType orderType, OrderSource orderSource)
			throws WOMBusinessDataException, WOMExternalSystemException, WOMTransactionException, WOMValidationException;

	/**
	 * Check the inventory on the parts in the given item list using the given bill to accounts. This will assume a null
	 * ship to account.
	 * 
	 * @param masterOrderNumber
	 *           master order number for this call to check inventory
	 * @param itemList
	 *           list of parts to get inventories for
	 * @param bilToAcct
	 *           the bill to account to get inventories for - affects home DC returned.
	 * @param orderSource
	 *           IPO or WEB
	 * @return
	 * @throws WOMBusinessDataException
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 * @throws WOMValidationException
	 */
	public List<ItemBO> tscPickUpCheckInventory(List<ItemBO> itemsList, AccountBO billToAcct, AccountBO shipToAcct,
			CustomerSalesOrgBO salesOrg, OrderType orderType, OrderSource orderSource) throws WOMBusinessDataException,
			WOMExternalSystemException, WOMTransactionException, WOMValidationException;

}

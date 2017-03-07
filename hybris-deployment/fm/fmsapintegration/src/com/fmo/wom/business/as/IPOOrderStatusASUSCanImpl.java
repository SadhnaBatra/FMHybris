package com.fmo.wom.business.as;

import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.fmo.wom.common.exception.WOMBusinessAccessException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;
import com.fmo.wom.model.FMIPOOrderEntryModel;
import com.fmo.wom.model.FMIPOOrderModel;


public class IPOOrderStatusASUSCanImpl extends OrderStatusASUSCanImpl
{

	/**
	 * Perform a search and order detail retrieval for all orders for the given Bill-To and Master Order Number and
	 * Customer PO Number. If the Master Order Number is provided, the Customer PO Number is ignored i.e. the Master
	 * Order Number is treated as the real input to avoid mismatches.
	 * 
	 * @param accountCode
	 *           The Account Code to use to retrieve the orders
	 * @param masterOrderNumber
	 *           Master Order Number for the order - can be null/empty
	 * @param custPoNumber
	 *           Customer PO Number for order search - can be null/empty
	 * @return List of matching order, with all shipment detail populated, matching the input criteria
	 * @throws WOMBusinessAccessException
	 * @throws WOMValidationException
	 * @throws WOMResourceException
	 */
	@Override
	public List<ShippedOrderBO> getOrderAndShipmentStatus(final String nabsBillToAcctCode, final String masterOrderNumber,
			String custPoNumber) throws WOMBusinessAccessException, WOMValidationException, WOMResourceException
	{

		if (GenericValidator.isBlankOrNull(masterOrderNumber) && GenericValidator.isBlankOrNull(custPoNumber))
		{
			throw new WOMValidationException("Either Supplier Order Number or Customer PO Number is required. Both cannot be empty.");
		}

		//if master order number present, ignore customer PO number
		if (!GenericValidator.isBlankOrNull(masterOrderNumber))
		{
			custPoNumber = null;
		}

		// perform order search on the input
		final List<ShippedOrderBO> shippedOrders = getOrderStatusInfo(nabsBillToAcctCode, masterOrderNumber, custPoNumber);

		//find out order detail for the returned results
		getOrderDetail(shippedOrders, "ORDERS");

		//Mahaveer - Ship from party fix 
		setShipfromParty(shippedOrders);
		// do any data massaging for the retrieved orders
		updateForIpo(shippedOrders, nabsBillToAcctCode);

		return shippedOrders;
	}

	/**
	 * @param shippedOrders
	 *           function used to set up the DC inforamtion.
	 */
	private void setShipfromParty(final List<ShippedOrderBO> shippedOrders)
	{
		// YTODO Auto-generated method stub

		if (shippedOrders == null)
		{
			return;

		}
		else
		{
			for (final ShippedOrderBO shippedOrderBO : shippedOrders)
			{
				for (final OrderUnitBO orderUnit : shippedOrderBO.getOrderUnitList())
				{
					for (final ShippingUnitBO shippingUnitBO : orderUnit.getShippingUnitList())
					{
						shippingUnitBO.setScacCode(shippingUnitBO.getScacCode());
						shippingUnitBO.setShipFrom(getFMDistributionCenterBO(shippingUnitBO.getShipFrom().getCode().toString()));

					}
				}
			}
		}

	}

	/**
	 * @param accountCode
	 * @param masterOrderNumber
	 * @param custPoNumber
	 * @return
	 * @throws WOMBusinessAccessException
	 * @throws WOMValidationException
	 * @throws WOMResourceException
	 */
	@Override
	public List<ShippedOrderBO> getOrderStatusInfo(final String nabsBillToAcctCode, final String masterOrderNumber,
			final String custPoNumber) throws WOMBusinessAccessException, WOMValidationException, WOMResourceException
	{

		// get account info with the given NABS bill to account code to get the associate sap info
		/* Look up from Hybris Service */
		//AccountBO nabsAccountBillToAcct = accountASFactory.getAccountInfo(nabsBillToAcctCode);
		final AccountBO nabsAccountBillToAcct = getBillTOAccount(nabsBillToAcctCode);
		final NabsService nabsService = new NabsService();
		// Call NABS to get shipment info
		final List<ShippedOrderBO> nabShippedOrderList = nabsService.getShipmentInfo(nabsBillToAcctCode, masterOrderNumber,
				custPoNumber, null, null, null);

		// go through the nabs orders and make sure the account is on them
		for (final ShippedOrderBO nabShippedOrder : nabShippedOrderList)
		{
			if (nabsAccountBillToAcct.getAccountCode().equals(nabShippedOrder.getBillToAccount().getAccountCode()))
			{
				// copy back the bill-to info into the DD112 validated object
				nabShippedOrder.setBillToAccount(nabsAccountBillToAcct);
			}
		}

		// Call SAP if this account has an associated SAP account
		List<ShippedOrderBO> sapShippedOrderList = null;
		final String sapAcctCode = nabsAccountBillToAcct.getSapAccount().getSapAccountCode();
		if (!GenericValidator.isBlankOrNull(sapAcctCode))
		{
			sapShippedOrderList = SAPService.getShipmentInfo(sapAcctCode, masterOrderNumber, custPoNumber, "ORDERS");
		}
		// ShippedOrderBO is identical with ShippingUnit in BOD, one ShippedOrder/ShippingUnit only have one master order number
		// Make a map of all the orders and their master order numbers.
		final Map<String, ShippedOrderBO> shippedOrderMap = new HashMap<String, ShippedOrderBO>();

		// add all the NABS orders to the map with its master order number as the key
		for (final ShippedOrderBO nabShippedOrder : nabShippedOrderList)
		{
			shippedOrderMap.put(nabShippedOrder.getMasterOrderNumber(), nabShippedOrder);
		}

		// now go through the SAP list.  If there is a match in the NABS list, merge it. If not, just set the account
		// info and put it in the map
		for (final ShippedOrderBO sapShippedOrder : sapShippedOrderList)
		{
			if (nabsAccountBillToAcct.getSapAccount().getSapAccountCode()
					.equals(sapShippedOrder.getBillToAccount().getSapAccount().getSapAccountCode()))
			{
				// copy back the bill-to info into the DD112 validated object
				nabsAccountBillToAcct.setSapAccount(nabsAccountBillToAcct.getSapAccount());
				sapShippedOrder.setBillToAccount(nabsAccountBillToAcct);
			}

			final ShippedOrderBO nabsShippedOrder = shippedOrderMap.get(sapShippedOrder.getMasterOrderNumber());

			if (nabsShippedOrder == null)
			{
				// no NABS shipped order, so just need to populate NABS BillTo Account info for non-split SAP order
				// and put in the map
				sapShippedOrder.setBillToAccount(nabsAccountBillToAcct);
				shippedOrderMap.put(sapShippedOrder.getMasterOrderNumber(), sapShippedOrder);
			}
			else
			{
				// there is a NABS order side for this master order number.  Merge the order lists from both orders  
				final List<OrderUnitBO> orderUnitList = new ArrayList<OrderUnitBO>();
				orderUnitList.addAll(nabsShippedOrder.getOrderUnitList());
				orderUnitList.addAll(sapShippedOrder.getOrderUnitList());
				nabsShippedOrder.setOrderUnitList(orderUnitList);
			}
		}

		// all our orders are in the values collection of the map
		final List<ShippedOrderBO> shippedOrders = new ArrayList<ShippedOrderBO>(shippedOrderMap.values());

		return shippedOrders;
	}

	/**
	 * Find Order details from NABS and SAP
	 * 
	 * @param shippedOrderList
	 *           ,externalSystem
	 * @return
	 * @throws WOMTransactionException
	 * @throws WOMExternalSystemException
	 * @throws WOMNoResultException
	 * @throws WOMNonUniqueResultException
	 */
	@Override
	public void getOrderDetail(final List<ShippedOrderBO> shippedOrderList, final String orderRetrunFlag)
			throws WOMExternalSystemException, WOMTransactionException
	{

		//find out order detail
		getOrderDetail(shippedOrderList, ExternalSystem.NABS, orderRetrunFlag);
		getOrderDetail(shippedOrderList, ExternalSystem.EVRST, orderRetrunFlag);
	}


	private void getOrderDetail(final List<ShippedOrderBO> shippedOrderList, final ExternalSystem externalSystem,
			final String anOrderRetrunFlag) throws WOMExternalSystemException, WOMTransactionException
	{

		if (shippedOrderList == null)
		{
			return;
		}

		for (final ShippedOrderBO shippedOrder : shippedOrderList)
		{
			getShipmentDetail(shippedOrder, externalSystem, anOrderRetrunFlag);
		}
	}



	private BillToAcctBO getBillTOAccount(final String billToAcc)
	{

		final FMCustomerAccountModel fmCustomerAccModel = getAccountByNabs(billToAcc);
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
		anAddress.setStateOrProv(addres.getRegion().getName());
		final CountryBO country = new CountryBO();
		country.setIsoCountryCode(addres.getCountry().getIsocode());
		anAddress.setCountry(country);
		billToAcct.setAddress(anAddress);
		return billToAcct;
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
			return null;
		}

	}

	public FMDistrubtionCenterModel getDistrubtionCenterBO(final String code)
	{
		// YTODO Auto-generated method stub
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {dc:" + FMDistrubtionCenterModel.PK + "} ");
		query.append("FROM  {" + FMDistrubtionCenterModel._TYPECODE + " AS dc } ");
		query.append("WHERE {dc." + FMDistrubtionCenterModel.CODE + "} = ?" + FMDistrubtionCenterModel.CODE);
		final Map<String, Object> params = new HashMap<String, Object>(1);
		params.put(FMDistrubtionCenterModel.CODE, code);
		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString(), params);
		final SearchResult<FMDistrubtionCenterModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);
		FMDistrubtionCenterModel fmDistrubtionCenterModel = null;
		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			fmDistrubtionCenterModel = searchResult.getResult().get(0);
		}

		return fmDistrubtionCenterModel;


	}

	private DistributionCenterBO getFMDistributionCenterBO(final String dcCode)
	{		
		final DistributionCenterBO distributionCenterBO = new DistributionCenterBO();
		final StringBuilder query = new StringBuilder("SELECT DISTINCT {dc:" + FMDCCenterModel.PK + "} ");
		query.append(" FROM  {" + FMDCCenterModel._TYPECODE + " AS dc }");
		query.append(" WHERE {dc." + FMDCCenterModel.DCCODE + "} = ?" + FMDCCenterModel.DCCODE);
		//logger.info("getFMDistributionCenterBO():  " + dcCode);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(FMDCCenterModel.DCCODE, dcCode.trim());

		final SearchResult<FMDCCenterModel> searchResult = getFlexibleSearchService().search(flexibleSearchQuery);

		if (searchResult.getResult() != null && searchResult.getResult().size() > 0)
		{
			final FMDCCenterModel dc = searchResult.getResult().get(0);
			distributionCenterBO.setCode(dcCode);
			distributionCenterBO.setName(dc.getDcName());
			final AddressBO address = new AddressBO();
			address.setAddrLine1(dc.getAddressLine1());
			//address.setAddrLine2(dc.geta);
			address.setCity(dc.getCity());
			address.setStateOrProv(dc.getState());
			address.setZipOrPostalCode(String.valueOf(dc.getZipCode().intValue()));
			final CountryBO country = new CountryBO();
			country.setIsoCountryCode(dc.getIsoCountryCode());
			address.setCountry(country);
			distributionCenterBO.setAddress(address);
			//logger.info("getFMDistributionCenterBO(): distributionCenterBO " + distributionCenterBO);
			return distributionCenterBO;
		}

		else
		{
			return distributionCenterBO;
		}

	}

	private void updateForIpo(final List<ShippedOrderBO> shippedOrders, final String billToAcc)
	{

		if (shippedOrders == null)
		{
			return;
		}

		// go through and load the orders
		for (final ShippedOrderBO aShippedOrder : shippedOrders)
		{
			// load the order
			FMIPOOrderModel ourOrder = null;
			try
			{
				ourOrder = findByMasterOrderNumber(aShippedOrder.getMasterOrderNumber());
			}
			catch (final Exception e)
			{
				// that order number didn't exist.  In theory, this does not necessarily need to be
				// an error.  We are only trying to massage data, but if we can't find anything to
				// massage with, we should be able to respond with the actual back end result. 
				// Just continue on to the next one
				continue;
			}

			if (ourOrder == null)
			{
				continue;
			}

			// for IPO, we need to put the manual ship to code back out on the ship to account code
			//final AccountBO responseShipToAccount = aShippedOrder.getShipToAccount();
			//responseShipToAccount.setAccountCode(ourOrder.getShiptoparty().getNabsAccountCode());

			final AccountBO responseBillToAccount = aShippedOrder.getBillToAccount();
			responseBillToAccount.setAccountCode(billToAcc);

			// Its possible the back end system returning the customer po number has turned it into
			// upper case.  If that is the situation, we want to revert it to what on the original order
			if (GenericValidator.isBlankOrNull(ourOrder.getPONumber()) == false
					&& ourOrder.getPONumber().toUpperCase().equals(aShippedOrder.getCustomerPurchaseOrderNum()))
			{
				aShippedOrder.setCustomerPurchaseOrderNum(ourOrder.getPONumber());
			}

			final Map<String, FMIPOOrderEntryModel> partNumberMap = createPartNumberMap(ourOrder);

			// load each order and populate fields we cannot determine from backend shipment
			// functionality
			// populate AAIA brand, display part number for each shippedItem, and shipping info off
			// the first part onto the shipping unit where applicable.
			for (final OrderUnitBO orderUnit : aShippedOrder.getOrderUnitList())
			{
				for (final ShippingUnitBO shippingUnit : orderUnit.getShippingUnitList())
				{
					boolean first = true;
					for (final ShippedItemBO shippedItem : shippingUnit.getShippedItemsList())
					{
						final FMIPOOrderEntryModel item = partNumberMap.get(shippedItem.getPartNumber().toUpperCase());
						if (item != null)
						{
							if (first == true)
							{
								first = false;
								shippingUnit.setCarrierCode(item.getCommonCarrier());
								shippingUnit.setTransportationMethodCode(item.getTransportationMethod());
								shippingUnit.setShippingMethodCode(item.getShippingMethod());
								shippingUnit.setScacCode(item.getScaccode());
							}

							shippedItem.setAaiaBrand(item.getBrandAAIAId());
							if (GenericValidator.isBlankOrNull(item.getPartNumber()))
							{
								shippedItem.setDisplayPartNumber(item.getRawPartNumber());
							}
							else
							{
								shippedItem.setDisplayPartNumber(item.getPartNumber());
							}
						}
					}
				}
			}
		}
	}

	/**
	 * @param ourOrder
	 * @return
	 */
	private Map<String, FMIPOOrderEntryModel> createPartNumberMap(final FMIPOOrderModel ourOrder)
	{
		// YTODO Auto-generated method stub
		final Map<String, FMIPOOrderEntryModel> partNumberMap = new HashMap<String, FMIPOOrderEntryModel>();
		for (final FMIPOOrderEntryModel entry : ourOrder.getEntries())
		{
			partNumberMap.put(entry.getRawPartNumber(), entry);
		}
		return partNumberMap;
	}

	/**
	 * @param masterOrderNumber
	 * @return
	 */
	private FMIPOOrderModel findByMasterOrderNumber(final String masterOrderNumber)
	{
		// YTODO Auto-generated method stub

		final StringBuilder query = new StringBuilder("SELECT {" + FMIPOOrderModel.PK + "}");
		query.append("FROM {" + FMIPOOrderModel._TYPECODE + "}");
		query.append("WHERE {" + FMIPOOrderModel.CODE + "} ='" + masterOrderNumber.trim() + "'");

		final SearchResult<FMIPOOrderModel> result = getFlexibleSearchService().search(query.toString());

		if (null != result && result.getResult().size() > 0)
		{
			final List<FMIPOOrderModel> listAccountId = result.getResult();
			final FMIPOOrderModel order = listAccountId.iterator().next();
			return order;

		}
		else
		{
			return null;
		}

	}




}

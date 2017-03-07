package com.fmo.wom.business.orderstatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.ShippedOrderBO;

public class OrderStatusHelper {
	
	private static Logger logger = Logger.getLogger(OrderStatusHelper.class);
	//private static final String DATE_PATTERN = "MM/dd/yyyy";
	//private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
	
	private List<ShippedOrderBO> searchedOrdersList;
	
	private List<ShippedOrder> wrappedList = null;
	
	public OrderStatusHelper() {
		super();
	}
	
	public OrderStatusHelper(List<ShippedOrderBO> aSearchedOrdersList) {
		super();
		this.searchedOrdersList = aSearchedOrdersList;
		if(searchedOrdersList != null && (! searchedOrdersList.isEmpty())){
			wrappedList = new ArrayList<ShippedOrder>(searchedOrdersList.size());
			for (ShippedOrderBO shippedOrderBO: searchedOrdersList) {
				ShippedOrder wrapper = new ShippedOrder(shippedOrderBO);
				wrappedList.add(wrapper);
			}
		}
	}
	
	public List<ShippedOrderBO> getSearchedOrderList() {
		return searchedOrdersList;
	}

	public void setSearchedOrderList(List<ShippedOrderBO> searchedOrderList) {
		this.searchedOrdersList = searchedOrderList;
	}
	
	public List<ShippedOrder> getShippedOrderList() {
		if (this.wrappedList == null) {
			this.wrappedList = new ArrayList<ShippedOrder>(0);
		}
		logger.debug("GET SHIPPED ORDER LIST - SIZE IS "+ this.wrappedList.size());
		return this.wrappedList;
	}
	
	public List<OrderStatusResult> sortByOrderDate() {
		//this.sortType = ORDER_DATE_SORT;
		return performOrderDateSort(getShippedOrderList());
		
	}
	
	public List<OrderStatusResult> sortByOrderNumber() {
		//this.sortType = ORDER_NUMBER_SORT;
		return performOrderNumberSort(getShippedOrderList());
		
	}
	
	public List<OrderStatusResult> sortByPackingSlipNumber() {
		//this.sortType = PACKING_SLIP_NUMBER_SORT;
		return performPackingSlipNumberSort(getShippedOrderList());
	}
	
	public List<OrderStatusResult> sortByPurchaseOrderNumber() {
		//this.sortType = PURCHASE_ORDER_SORT;
		return performPurchaseOrderNumberSort(getShippedOrderList());
	}
	
	public List<OrderStatusResult> sortByShipDate() {
		//this.sortType = SHIP_DATE_SORT;
		return performShipDateSort(getShippedOrderList());
	}
	
	public List<OrderStatusResult> sortByShippingDC() {
		//this.sortType = SHIPPING_DC_SORT;
		return performShippingDcSort(getShippedOrderList());
	}
	
	public List<OrderStatusResult> sortByStatus() {
		//this.sortType = STATUS_SORT;
		return performStatusSort(getShippedOrderList());
	}
	
	//private LocaleManager localeManager;

	
	/*public void setLocaleManager(LocaleManager lm) {
		logger.debug("SETTING LOCALE MANAGER");
		localeManager = lm;
	}*/
	
	

	/**
	 * Creates list of Order Status Results in Order Date sort order
	 * based on the search results contained in the
	 * <code>shippedOrderList</code> parameter.
	 * 
	 * @param shippedOrderList
	 *            the list of search results
	 * @return sorted list of OrderStatusResult objects
	 */
	public List<OrderStatusResult> performOrderDateSort(List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<OrderStatusResult>(0); // nothing in - nothing out
		}
		// 1. Create list of unsorted order status results from list of shipped orders
		List<OrderStatusResult> resultList = createOrderStatusResultList(shippedOrderList);
		// 2. Sort list by Order Date
		Collections.sort(resultList, byOrderDate);
		// 3. Remove consecutive PO # and Conf # from order status results
		removeDuplicatePoAndMo(resultList);
		logResults(resultList, "ORDER DATE SORT");
		return resultList;
	}
	
	/**
	 * Creates list of Order Status Results in Order Number sort order
	 * based on the search results contained in the
	 * <code>shippedOrderList</code> parameter.
	 * 
	 * @param shippedOrderList
	 *            the list of search results
	 * @return sorted list of OrderStatusResult objects
	 */
	public List<OrderStatusResult> performOrderNumberSort(
			List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<OrderStatusResult>(0); // nothing in - nothing out
		}
		// 1. Create list of unsorted order status results from list of shipped orders
		List<OrderStatusResult> resultList = createOrderStatusResultList(shippedOrderList);
		// 2. Sort list by Order Number
		Collections.sort(resultList, byOrderNumber);
		// 3. Remove consecutive PO # and Conf # from order status results
		removeDuplicatePoAndMo(resultList);
		logResults(resultList, "ORDER NUMBER SORT");
		return resultList;
	}
	
	/**
	 * Creates list of Order Status Results in Packing Slip Number sort order
	 * based on the search results contained in the
	 * <code>shippedOrderList</code> parameter.
	 * 
	 * @param shippedOrderList
	 *            the list of search results
	 * @return sorted list of OrderStatusResult objects
	 */
	public List<OrderStatusResult> performPackingSlipNumberSort(List<ShippedOrder> shippedOrderList )  {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<OrderStatusResult>(0); // nothing in - nothing out
		}
		// 1. Create list of unsorted order status results from list of shipped orders
		List<OrderStatusResult> resultList = createOrderStatusResultList(shippedOrderList);
		// 2. Sort list by Packing Slip Number
		Collections.sort(resultList, byPackingSlipNumber);
		// 3. Remove consecutive PO # and Conf # from order status results
		removeDuplicatePoAndMo(resultList);
		logResults(resultList, "PACKING SLIP NUMBER SORT");
		return resultList;
	}
	
	/**
	 * Creates list of Order Status Results in Purchase Order Number sort order
	 * based on the search results contained in the
	 * <code>shippedOrderList</code> parameter.
	 * 
	 * @param shippedOrderList
	 *            the list of search results
	 * @return sorted list of OrderStatusResult objects
	 */
	public List<OrderStatusResult> performPurchaseOrderNumberSort(
			List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<OrderStatusResult>(0); // nothing in - nothing out
		}
		// 1. Create list of unsorted order status results from list of shipped orders
		List<OrderStatusResult> resultList = createOrderStatusResultList(shippedOrderList);
		// 2. Sort list by Purchase Order Number 
		Collections.sort(resultList, byPurchaseOrderNumber);
		// 3. Remove duplicate Purchase Order Numbers and Confirmation Numbers in successive rows
		removeDuplicatePoAndMo(resultList);
		logResults(resultList, "PURCHASE ORDER NUMBER SORT");
		return resultList;
	}

	/**
	 * Creates list of Order Status Results in Ship Date sort order
	 * based on the search results contained in the
	 * <code>shippedOrderList</code> parameter.
	 * 
	 * @param shippedOrderList
	 *            the list of search results
	 * @return sorted list of OrderStatusResult objects
	 */
	public List<OrderStatusResult> performShipDateSort(
			List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<OrderStatusResult>(0); // nothing in - nothing out
		}
		// 1. Create list of unsorted order status results from list of shipped orders
		List<OrderStatusResult> resultList = createOrderStatusResultList(shippedOrderList);
		// 2. Sort list by Ship Date
		Collections.sort(resultList, byShipDate);
		// 3. Remove consecutive PO # and Conf # from order status results
		removeDuplicatePoAndMo(resultList);
		logResults(resultList, "SHIP DATE SORT");
		return resultList;
	}
	
	/**
	 * Creates list of Order Status Results in Shipping Distribution Center sort order
	 * based on the search results contained in the
	 * <code>shippedOrderList</code> parameter.
	 * 
	 * @param shippedOrderList
	 *            the list of search results
	 * @return sorted list of OrderStatusResult objects
	 */
	public List<OrderStatusResult> performShippingDcSort(
			List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<OrderStatusResult>(0); // nothing in - nothing out
		}
		// 1. Create list of unsorted order status results from list of shipped orders
		List<OrderStatusResult> resultList = createOrderStatusResultList(shippedOrderList);
		// 2. Sort list by Shipping Distribution Center
		Collections.sort(resultList, byShippingDc);
		// 3. Remove consecutive PO # and Conf # from order status results
		removeDuplicatePoAndMo(resultList);
		logResults(resultList, "SHIPPING DC SORT");
		return resultList;
	}
	
	/**
	 * Creates list of Order Status Results in Order Status sort order
	 * based on the search results contained in the
	 * <code>shippedOrderList</code> parameter.
	 * 
	 * @param shippedOrderList
	 *            the list of search results
	 * @return sorted list of OrderStatusResult objects
	 */
	public List<OrderStatusResult> performStatusSort(
			List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<OrderStatusResult>(0); // nothing in - nothing out
		}
		// 1. Create list of unsorted order status results from list of shipped orders
		List<OrderStatusResult> resultList = createOrderStatusResultList(shippedOrderList);
		// 2. Sort list by Order Status
		Collections.sort(resultList, byStatus);
		// 3. Remove consecutive PO # and Conf # from order status results
		removeDuplicatePoAndMo(resultList);
		logResults(resultList, "STATUS SORT");
		return resultList;
	}
	
	private List<OrderStatusResult> createOrderStatusResultList(List<ShippedOrder> shippedOrderList) {
		List<OrderStatusResult> resultList = new ArrayList<OrderStatusResult>();
		for (ShippedOrder shippedOrder : shippedOrderList) {
			List<OrderShippingUnit> osuList = shippedOrder.getOrderShippingUnitList();
			for (OrderShippingUnit osu : osuList) {
				String customerPurchaseOrderNumber = shippedOrder.getCustomerPurchaseOrderNumber();
				String masterOrderNumber = shippedOrder.getMasterOrderNumber();
				String orderNumber = osu.getOrderNumber();
				String packingSlip = osu.getPackingSlipNumber();
				Date originalOrderDate = osu.getOriginalOrderDate();
				String shippingDistributionCenter = osu.getDistributionCenterName();
				String packingStatus = osu.getPackingStatus();
				Date shipDate = osu.getShipDate();
				String trackingNumber = osu.getTrackingNumber();
				String source = osu.getOrderSourceKey();
				String trackingURL = osu.getTrackingURL();
				OrderStatusResult osr = new OrderStatusResult(
						customerPurchaseOrderNumber, masterOrderNumber,
						orderNumber, packingSlip, originalOrderDate,
						shippingDistributionCenter, packingStatus, shipDate,
						trackingNumber,trackingURL, source, shippedOrder, osu);
				resultList.add(osr);
			}
		}
		
		return resultList;
	}

	private void removeDuplicatePoAndMo(List<OrderStatusResult> resultList) {
		String lastPurchaseOrderNumber = "";
		String lastMasterOrderNumber = "";
		if (resultList != null) {
			for (OrderStatusResult result: resultList) {
				if (result.getPurchaseOrderNumber().compareToIgnoreCase(lastPurchaseOrderNumber) == 0) {
					result.setPurchaseOrderNumber("");
					
					// this purchase order number is the same as the previous.  Now ok to check the
					// master order number.  We DON"T want an order with a different customer po number to be considered
					// the same order regardless of confirmation number.  Yes, that makes no sense but we have
					// to deal with human input orders where these fields are totally editable.
					if (result.getConfirmationNumber().compareToIgnoreCase(lastMasterOrderNumber) == 0) {
	                    result.setConfirmationNumber("");
	                } else {
	                    lastMasterOrderNumber = result.getConfirmationNumber();
	                }
				} else {
					lastPurchaseOrderNumber = result.getPurchaseOrderNumber();
					lastMasterOrderNumber = result.getConfirmationNumber();
				}
			}
		}
	}
	
	private Comparator<OrderStatusResult> byOrderDate = new Comparator<OrderStatusResult>() {

		@Override
		public int compare(OrderStatusResult osr1, OrderStatusResult osr2) {
			int result = 0;
			Date orderDate1 = osr1.getOrderDate();
			Date orderDate2 = osr2.getOrderDate();
			String po1 = osr1.getPurchaseOrderNumber();
			String po2 = osr2.getPurchaseOrderNumber();
			String orderNumber1 = osr1.getOrderNumber();
			String orderNumber2 = osr2.getOrderNumber();
			String packingSlipNumber1 = osr1.getPackingSlipNumber();
			String packingSlipNumber2 = osr2.getPackingSlipNumber();
			// Compare orderDates in Descending order
			if (orderDate1 == null && orderDate2 == null) {
				result = 0;										// two null dates are equal
			} else if (orderDate1 == null) {
				result = 1;  									// A null date appears later in the list than a non-null date
			} else if (orderDate2 == null) {
				result = -1; 									// A non-null date appears earlier in the list than a null date 
			} else {
				result = orderDate2.compareTo(orderDate1);  	// Descending order - reverse comparison
			}
			if (result == 0) {
				// If dates are equal, compare purchase order numbers
				result = po1.compareToIgnoreCase(po2);
				if (result == 0) {
					// If purchase order numbers are equal, compare order numbers
					result = orderNumber1.compareToIgnoreCase(orderNumber2);
					if (result == 0) {
						// If order numbers are equal, compare packing slip numbers
						result = packingSlipNumber1.compareToIgnoreCase(packingSlipNumber2);
					}
				}
			}
			return result;
		}
	};

	private Comparator<OrderStatusResult> byOrderNumber = new Comparator<OrderStatusResult>() {

		@Override
		public int compare(OrderStatusResult osr1, OrderStatusResult osr2) {
			int result = 0;
			String orderNumber1 = osr1.getOrderNumber();
			String packingSlipNumber1 = osr1.getPackingSlipNumber();
			String orderNumber2 = osr2.getOrderNumber();
			String packingSlipNumber2 = osr2.getPackingSlipNumber();
			// Compare order numbers first
			result = orderNumber1.compareToIgnoreCase(orderNumber2);
			// If they are equal, compare packing slip number and use that
			// result
			if (result == 0) {
				result = packingSlipNumber1
						.compareToIgnoreCase(packingSlipNumber2);
			}
			return result;
		}
	};

	private Comparator<OrderStatusResult> byPackingSlipNumber = new Comparator<OrderStatusResult>() {

		@Override
		public int compare(OrderStatusResult osr1, OrderStatusResult osr2) {
			int result = 0;
			String packingSlipNumber1 = osr1.getPackingSlipNumber();
			String orderNumber1 = osr1.getOrderNumber();
			String packingSlipNumber2 = osr2.getPackingSlipNumber();
			String orderNumber2 = osr2.getOrderNumber();
			// If either packing slip number is blank, compare using order number
			if (packingSlipNumber1.length() == 0 || packingSlipNumber2.length() == 0) {
				result = orderNumber1.compareToIgnoreCase(orderNumber2);
			} else {
				result = packingSlipNumber1.compareToIgnoreCase(packingSlipNumber2);
			}
			return result;
		}
	};

	private Comparator<OrderStatusResult> byPurchaseOrderNumber = new Comparator<OrderStatusResult>() {

		@Override
		public int compare(OrderStatusResult osr1, OrderStatusResult osr2) {

			int result = 0;
			String po1 = osr1.getPurchaseOrderNumber();
			String po2 = osr2.getPurchaseOrderNumber();
			String orderNumber1 = osr1.getOrderNumber();
			String orderNumber2 = osr2.getOrderNumber();
			String packingSlipNumber1 = osr1.getPackingSlipNumber();
			String packingSlipNumber2 = osr2.getPackingSlipNumber();
			result = po1.compareToIgnoreCase(po2);
			if (result == 0) {
				result = orderNumber1.compareToIgnoreCase(orderNumber2);
				if (result == 0) {
					result = packingSlipNumber1.compareToIgnoreCase(packingSlipNumber2);
				}
			}
			return result;
		}
	};
	
	private Comparator<OrderStatusResult> byShipDate = new Comparator<OrderStatusResult>() {

		@Override
		public int compare(OrderStatusResult osr1, OrderStatusResult osr2) {
			int result = 0;
			Date shipDate1 = osr1.getShipDate();
			Date shipDate2 = osr2.getShipDate();
			String po1 = osr1.getPurchaseOrderNumber();
			String po2 = osr2.getPurchaseOrderNumber();
			String orderNumber1 = osr1.getOrderNumber();
			String orderNumber2 = osr2.getOrderNumber();
			String packingSlipNumber1 = osr1.getPackingSlipNumber();
			String packingSlipNumber2 = osr2.getPackingSlipNumber();
			// Compare shipDates in Ascending order
			if (shipDate1 == null && shipDate2 == null) {
				result = 0;										// two null dates are equal
			} else if (shipDate1 == null) {
				result = 1;  									// A null date appears later in the list than a non-null date
			} else if (shipDate2 == null) {
				result = -1; 									// A non-null date appears earlier in the list than a null date 
			} else {
				result = shipDate1.compareTo(shipDate2);  		// If neither is null, use Ascending comparison
			}
			if (result == 0) {
				// If dates are equal, compare purchase order numbers
				result = po1.compareToIgnoreCase(po2);
				if (result == 0) {
					// If purchase order numbers are equal, compare order numbers
					result = orderNumber1.compareToIgnoreCase(orderNumber2);
					if (result == 0) {
						// If order numbers are equal, compare packing slip numbers
						result = packingSlipNumber1.compareToIgnoreCase(packingSlipNumber2);
					}
				}
			}
			return result;
		}
	};

	private Comparator<OrderStatusResult> byShippingDc = new Comparator<OrderStatusResult>() {

		@Override
		public int compare(OrderStatusResult osr1, OrderStatusResult osr2) {

			int result = 0;
			String dc1 = osr1.getShippingDC();
			String dc2 = osr2.getShippingDC();
			String orderNumber1 = osr1.getOrderNumber();
			String orderNumber2 = osr2.getOrderNumber();
			String packingSlipNumber1 = osr1.getPackingSlipNumber();
			String packingSlipNumber2 = osr2.getPackingSlipNumber();
			result = dc1.compareToIgnoreCase(dc2);
			if (result == 0) {
				result = orderNumber1.compareToIgnoreCase(orderNumber2);
				if (result == 0) {
					result = packingSlipNumber1.compareToIgnoreCase(packingSlipNumber2);
				}
			}
			return result;
		}
	};
	
	private Comparator<OrderStatusResult> byStatus = new Comparator<OrderStatusResult>() {

		@Override
		public int compare(OrderStatusResult osr1, OrderStatusResult osr2) {

			int result = 0;
			String statusKey1 = osr1.getStatus();
			String statusKey2 = osr2.getStatus();
			// Use localized value of status to sort 
			String status1 = statusKey1;//localeManager.getLocalizedValue(statusKey1);
			String status2 = statusKey2;//localeManager.getLocalizedValue(statusKey2);
			String orderNumber1 = osr1.getOrderNumber();
			String orderNumber2 = osr2.getOrderNumber();
			String packingSlipNumber1 = osr1.getPackingSlipNumber();
			String packingSlipNumber2 = osr2.getPackingSlipNumber();
			result = status1.compareToIgnoreCase(status2);
			if (result == 0) {
				// if statuses are equal, compare order numbers
				result = orderNumber1.compareToIgnoreCase(orderNumber2);
				if (result == 0) {
					// if order numbers are equal, compare packing slip numbers
					result = packingSlipNumber1.compareToIgnoreCase(packingSlipNumber2);
				}
			}
			return result;
		}
	};

	private void logResults(List<OrderStatusResult> resultList, String sortType) {
		if (resultList == null) {
			return;
		}
		logger.debug("SORT TYPE IS "+ sortType);
		for (OrderStatusResult result: resultList) {
			logger.debug(" result "+result.getPurchaseOrderNumber() + ", "
					+ result.getConfirmationNumber() + ", "
					+ result.getOrderNumber() + ", "
					+ result.getPackingSlipNumber() + ", "
					+ result.getOrderDateString() + ", "
					+ result.getShippingDC() + ", "
					+ result.getStatus() + ", "
					+ result.getShipDateString() + ", "
					+ result.getTrackingNumber() + ", "
					+ result.getSource());

		}
	}
	
/*	public void outputResults(List<ShippedOrderBO> results) {
		for (ShippedOrderBO order : results) {
			outputResults(order);
		}
	}

	private void outputResults(ShippedOrderBO order) {
		
		 * ============== ShippedOrderBO ============== private String
		 * customerPurchaseOrderNum; private String masterOrderNumber;
		 * 
		 * private AccountBO billToAccount; private AccountBO shipToAccount;
		 * 
		 * private List<OrderUnitBO> orderUnitList;
		 
		logger.debug("==== ShippedOrderBO ====");
		logger.debug("mon = {}", order.getMasterOrderNumber());
		logger.debug("po num = {}", order.getCustomerPurchaseOrderNum());
		logAccountInfo(order.getBillToAccount(), "Biil To");
		logAccountInfo(order.getShipToAccount(), "Ship To");
		
		 * =========== OrderUnitBO =========== private String orderNumber;
		 * private String orderSourceKey; private String orderSourceDescription;
		 * 
		 * private List<String> commentsList; private Date originalOrderDate;
		 * private ExternalSystem externalSystem;
		 * 
		 * private List<ShippingUnitBO> shippingUnitList;
		 
		for (OrderUnitBO orderUnit : order.getOrderUnitList()) {
			logger.debug("\n");
			logger.debug("\t==== OrderUnitBO ====");
			logger.debug("\torder number = " + orderUnit.getOrderNumber());
			logger.debug("\torderSourceKey = " + orderUnit.getOrderSourceKey());
			logger.debug("\torderSourceDescription = "
					+ orderUnit.getOrderSourceDescription());
			if (orderUnit.getOriginalOrderDate() != null) {
				logger.debug("\toriginal order date = "
						+ sdf.format(orderUnit.getOriginalOrderDate()));
			}
			logger.debug("\texternalSystem = " + orderUnit.getExternalSystem());
			if (orderUnit.getCommentsList() != null) {
				for (String comment : orderUnit.getCommentsList()) {
					logger.debug("\tCOMMENT:{" + comment + "}");
				}
			}
			
			 * ============== ShippingUnitBO ============== private String
			 * scacCode; private String carrierName; private String carrierCode;
			 * private String transportationMethodCode; private String
			 * shippingMethodCode; private String trackingNumber; private String
			 * packingSlip; private String packingStatus; private String
			 * packingStatusDescription;
			 * 
			 * private Date shipDate;
			 * 
			 * private DistributionCenterBO shipFrom;
			 * 
			 * List<ShippedItemBO> shippedItemsList;
			 
			for (ShippingUnitBO shippingUnit : orderUnit.getShippingUnitList()) {
				logger.debug("\t\t==== ShippingUnitBO ====");
				logger.debug("\t\tscacCode = {}", shippingUnit.getScacCode());
				logger.debug("\t\tcarriername = {}",
						shippingUnit.getCarrierName());
				logger.debug("\t\tcarrierCode = {}",
						shippingUnit.getCarrierCode());
				logger.debug("\t\ttransportationMethodCode = {}",
						shippingUnit.getTransportationMethodCode());
				logger.debug("\t\tshippingMethodCode = {}",
						shippingUnit.getShippingMethodCode());
				logger.debug("\t\ttrackingNumber = {}",
						shippingUnit.getTrackingNumber());
				logger.debug("\t\tpackingSlip = {}",
						shippingUnit.getPackingSlip());
				logger.debug("\t\tpackingStatus = {}",
						shippingUnit.getPackingStatus());
				logger.debug("\t\tpackingStatusDescription = {}",
						shippingUnit.getPackingStatusDescription());
				if (shippingUnit.getShipDate() != null) {
					logger.debug("\t\tshipDate = "
							+ sdf.format(shippingUnit.getShipDate()));
				}
				if (shippingUnit.getShipFrom() != null) {
					logger.debug("\t\tdc id = {}", shippingUnit.getShipFrom()
							.getDistCenterId());
					logger.debug("\t\tdc name = {}", shippingUnit.getShipFrom()
							.getName());
				}

				List<ShippedItemBO> items = shippingUnit.getShippedItemsList();
				if (items != null) {
					
					 * ============= ShippedItemBO ============= private int
					 * lineNumber; private String partNumber; private String
					 * displayPartNumber; private String description; private
					 * String aaiaBrand; private String productFlag; private
					 * String brandState; private ExternalSystem externalSystem;
					 * 
					 * private int orderedQuantity; private int
					 * backorderedQuantity; private int assignedQuantity;
					 * private int shippedQuantity;
					 
					for (ShippedItemBO anItem : shippingUnit
							.getShippedItemsList()) {
						logger.debug("\t\t\t==== ShippedItemBO ====");
						logger.debug("\t\t\t" + anItem.getLineNumber()
								+ ": part number=" + anItem.getPartNumber()
								+ ", displaypartnum="
								+ anItem.getDisplayPartNumber()
								+ ", description=" + anItem.getDescription());
						logger.debug("\t\t\t\taaiabrand="
								+ anItem.getAaiaBrand() + ", productflag="
								+ anItem.getProductFlag() + ", externalSystem="
								+ orderUnit.getExternalSystem());
						logger.debug("\t\t\t\torderedQuantity="
								+ anItem.getOrderedQuantity()
								+ ", backorderedQuantity="
								+ anItem.getBackorderedQuantity()
								+ ", assignedQuantity="
								+ anItem.getAssignedQuantity()
								+ ", shippedQuantity="
								+ anItem.getShippedQuantity());

					}
				}
			}
		}
	}*/

	/*private void logAccountInfo(AccountBO account, String message) {
		logger.debug("***{}***", message);
		if (account != null) {
			logger.debug("name = {}", account.getAccountName());
			logger.debug("code = {}", account.getAccountCode());
		} else {
			logger.debug("No account information");
		}
	}*/
	
}

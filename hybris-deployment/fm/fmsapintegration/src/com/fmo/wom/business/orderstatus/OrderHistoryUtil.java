package com.fmo.wom.business.orderstatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.fmo.wom.domain.ShippedOrder;

public class OrderHistoryUtil {
	
	//Comparator's
	
	public List<ShippedOrder> performOrderDateSort(List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<ShippedOrder>(0); // nothing in - nothing out
		}
		Collections.sort(shippedOrderList, byOrderDate);
		return shippedOrderList;
	}
	
	
	public List<ShippedOrder> performOrderNumberSort(List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<ShippedOrder>(0); // nothing in - nothing out
		}
		Collections.sort(shippedOrderList, byOrderNumber);
		return shippedOrderList;
	}
	
	public List<ShippedOrder> performPackingSlipNumberSort(List<ShippedOrder> shippedOrderList )  {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<ShippedOrder>(0); // nothing in - nothing out
		}
		Collections.sort(shippedOrderList, byPackingSlipNumber);
		return shippedOrderList;
	}
	
	public List<ShippedOrder> performPurchaseOrderNumberSort(List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<ShippedOrder>(0); // nothing in - nothing out
		}
		Collections.sort(shippedOrderList, byPurchaseOrderNumber);
		return shippedOrderList;
	}
	
	public List<ShippedOrder> performShipDateSort(List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<ShippedOrder>(0); // nothing in - nothing out
		}
		Collections.sort(shippedOrderList, byShipDate);
		return shippedOrderList;
	}
	
	public List<ShippedOrder> performShippingDcSort(List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<ShippedOrder>(0); // nothing in - nothing out
		}
		Collections.sort(shippedOrderList, byShippingDc);
		return shippedOrderList;
	}
	
	public List<ShippedOrder> performStatusSort(List<ShippedOrder> shippedOrderList) {
		if (shippedOrderList == null || shippedOrderList.isEmpty()) {
			return new ArrayList<ShippedOrder>(0); // nothing in - nothing out
		}
		Collections.sort(shippedOrderList, byStatus);
		return shippedOrderList;
	}
	
	private Comparator<ShippedOrder> byOrderDate = new Comparator<ShippedOrder>() {
		@Override
		public int compare(ShippedOrder osr1, ShippedOrder osr2) {
			int result = 0;
			Date orderDate1 = osr1.getOrderDate();
			Date orderDate2 = osr2.getOrderDate();
			
			String po1 = osr1.getPoNumber();
			String po2 = osr2.getPoNumber();
			
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

	private Comparator<ShippedOrder> byOrderNumber = new Comparator<ShippedOrder>() {

		@Override
		public int compare(ShippedOrder osr1, ShippedOrder osr2) {
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

	private Comparator<ShippedOrder> byPackingSlipNumber = new Comparator<ShippedOrder>() {

		@Override
		public int compare(ShippedOrder osr1, ShippedOrder osr2) {
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

	private Comparator<ShippedOrder> byPurchaseOrderNumber = new Comparator<ShippedOrder>() {

		@Override
		public int compare(ShippedOrder osr1, ShippedOrder osr2) {

			int result = 0;
			String po1 = osr1.getPoNumber();
			String po2 = osr2.getPoNumber();
			
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
	
	private Comparator<ShippedOrder> byShipDate = new Comparator<ShippedOrder>() {

		@Override
		public int compare(ShippedOrder osr1, ShippedOrder osr2) {
			int result = 0;
			Date shipDate1 = osr1.getShipDate();
			Date shipDate2 = osr2.getShipDate();
			
			String po1 = osr1.getPoNumber();
			String po2 = osr2.getPoNumber();
			
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

	private Comparator<ShippedOrder> byShippingDc = new Comparator<ShippedOrder>() {

		@Override
		public int compare(ShippedOrder osr1, ShippedOrder osr2) {

			int result = 0;
			String dc1 = osr1.getShippingDCName();
			String dc2 = osr2.getShippingDCName();
	
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
	
	private Comparator<ShippedOrder> byStatus = new Comparator<ShippedOrder>() {

		@Override
		public int compare(ShippedOrder osr1, ShippedOrder osr2) {

			int result = 0;
			String statusKey1 = osr1.getOrderStatus();
			String statusKey2 = osr2.getOrderStatus();

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

}

package com.fmo.wom.business.util.backorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.BackOrderUnitBO;
import com.fmo.wom.domain.BackOrderedItemBO;
import com.fmo.wom.domain.DistributionCenterBO;

public class BackOrderHelper {
	
	private static Logger logger = Logger.getLogger(BackOrderHelper.class); 
	
	private List<BackOrderBO> backOrders = null;
	
	private List<BackOrderSearchValues> backOrderValuesList = null;

	public BackOrderHelper(List<BackOrderBO> backOrders) {
		super();
		this.backOrders = backOrders;
		backOrderValuesList = buildBackOrderSearchValuesList(this.backOrders);
	}

	/*public List<BackOrderBO> getBackOrders() {
		return backOrders;
	}

	public void setBackOrders(List<BackOrderBO> backOrders) {
		this.backOrders = backOrders;
	}*/
	
	public List<BackOrderSearchValues> getBackOrderValuesList() {
		return backOrderValuesList;
	}

	/**
	 * Build the Back Order list for display on the screen, based on the Back Order listed fetched.
	 * @param backOrders
	 * @return <code>List&lt;BackOrderSearchValues&gt;<code>
	 */
	private List<BackOrderSearchValues> buildBackOrderSearchValuesList(List<BackOrderBO> backOrders) {

		logger.debug("Inside 'buildBackOrderSearchValuesList()'");
		
		List<BackOrderSearchValues> boSearchValuesList = new ArrayList<BackOrderSearchValues>();
		
		if (backOrders == null || backOrders.size() == 0) return boSearchValuesList;
		
		for (BackOrderBO backOrder : backOrders) {
			
			for (BackOrderUnitBO boUnit : backOrder.getBackOrderUnits()) {
				
				for (BackOrderedItemBO boItem : boUnit.getBackOrderedItems()) {

					BackOrderSearchValues boSearchValues = new BackOrderSearchValues();
					
					boSearchValues.setBillToAccount(backOrder.getBillToAccount());
					boSearchValues.setShipToAccount(backOrder.getShipToAccount());
					boSearchValues.setMasterOrderNumber(backOrder.getMasterOrderNumber());
					boSearchValues.setCustomerPONumber(backOrder.getCustomerPurchaseOrderNumber());
					
					boSearchValues.setOrderNumber(boUnit.getOrderNumber());
					boSearchValues.setOrderDate(boUnit.getOrderDate());
					DistributionCenterBO dc = boUnit.getDistributionCenter();
					// don't display DC NOT FOUND
					if (dc != null && DistributionCenterBO.NOT_FOUND.equals(dc.getName())) {
					    dc.setName("-");
					}
					boSearchValues.setDistCntr(dc);
					
					boSearchValues.setPartNumber(boItem.getPartNumber());
					boSearchValues.setOriginalOrderQty(boItem.getOriginalOrderQty());
					boSearchValues.setBackOrderQty(boItem.getBackOrderQty());
					boSearchValues.setAvailabilityDate(boItem.getAvailabilityDate());
					
					boSearchValuesList.add(boSearchValues);
				}
			}
		}
		
		return boSearchValuesList;
	}
	
	public void performPartNumberSort() {
		Collections.sort(this.backOrderValuesList, Collections
				.reverseOrder(new Comparator<BackOrderSearchValues>() {
					public int compare(BackOrderSearchValues obj1,
							BackOrderSearchValues obj2) {
						return obj2.getPartNumber().compareToIgnoreCase(
								obj1.getPartNumber());
				}
		}));
	}

	public void performOrderNumberSort() {
		Collections.sort(this.backOrderValuesList, Collections
				.reverseOrder(new Comparator<BackOrderSearchValues>() {

					public int compare(BackOrderSearchValues obj1,
							BackOrderSearchValues obj2) {
						int result = obj2.getOrderNumber()
								.compareToIgnoreCase(obj1.getOrderNumber());
						if (result == 0) { // Order #s are equal
							// Compare Part #s.
							result = obj2.getPartNumber()
									.compareToIgnoreCase(
											obj1.getPartNumber());
						}
						return result;
				}
		}));
	}

	public void performPurchaseOrderNumberSort() {
		Collections.sort(this.backOrderValuesList, Collections
				.reverseOrder(new Comparator<BackOrderSearchValues>() {

					public int compare(BackOrderSearchValues obj1,
							BackOrderSearchValues obj2) {
						int result = obj2.getCustomerPONumber()
								.compareToIgnoreCase(
										obj1.getCustomerPONumber());
						if (result == 0) { // Purchase Order #s are equal
							// Compare Part #s.
							result = obj2.getPartNumber()
									.compareToIgnoreCase(
											obj1.getPartNumber());
						}
						return result;
				}
		}));
	}
	
	
	public void performOrderDateSort() {
		Collections.sort(this.backOrderValuesList, Collections
				.reverseOrder(new Comparator<BackOrderSearchValues>() {

					public int compare(BackOrderSearchValues obj1,
							BackOrderSearchValues obj2) {
						int result = obj2.getOrderDate().compareTo(
								obj1.getOrderDate());
						if (result == 0) { // Order Dates are equal
							// Compare Order #s.
							result = obj2.getOrderNumber()
									.compareToIgnoreCase(
											obj1.getOrderNumber());
							if (result == 0) { // Order #s are equal
								// Compare Part #s.
								result = obj2.getPartNumber()
										.compareToIgnoreCase(
												obj1.getPartNumber());
							}
						}
						return result;
					}
		}));
	}

}

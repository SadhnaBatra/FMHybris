package com.fmo.wom.business.orderstatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;

public class OrderShippingUnit {

	private OrderUnitBO orderUnitBO;
	private ShippingUnitBO shippingUnitBO;
	private List<ShippedItemWrapper> shippedItemWrapperList;
	public static final String UPS = "ups";
	public static final String FEDEX = "fedex";
	
	public OrderShippingUnit(OrderUnitBO orderUnitBO) {
        this(orderUnitBO, null);
    }
	
	public OrderShippingUnit(OrderUnitBO orderUnitBO,
			ShippingUnitBO shippingUnitBO) {
		super();
		this.orderUnitBO = orderUnitBO;
		this.shippingUnitBO = shippingUnitBO;
	}

	/* Wrapper Accessors */
	
	/* ORDER INFO */

	public Date getCancelledDate() {
		Date date = null;
		if (orderUnitBO != null) {
			date = orderUnitBO.getCancelledDate();
		}
		return date;
	}
	
	public List<String> getCommentsList() {
		List<String> commentsList = null;
		if (orderUnitBO != null) {
			commentsList = orderUnitBO.getCommentsList();
		}
		return commentsList == null ? new ArrayList<String>(0) : commentsList;
	}
	
	public String getExternalSystem() {
		String externalSystemName = null;
		if (orderUnitBO != null) {
			ExternalSystem externalSystem = orderUnitBO.getExternalSystem();
			if (externalSystem != null) {
				externalSystemName = externalSystem.name();
			}
		}
		return externalSystemName == null ? "" : externalSystemName; 
	}
	
	public String getOrderNumber() {
		String orderNumber = null;
		if (orderUnitBO != null) {
			orderNumber = orderUnitBO.getOrderNumber();
		}
		return orderNumber == null ? "" : orderNumber;
	}

	public String getOrderSourceDescription() {
		String orderSourceDescription = null;
		if (orderUnitBO != null) {
			orderSourceDescription = orderUnitBO.getOrderSourceDescription();
		}
		return orderSourceDescription == null ? "" : orderSourceDescription; 
	}
	
	public String getOrderSourceKey() {
		String orderSourceKey = null;
		if (orderUnitBO != null) {
			orderSourceKey = orderUnitBO.getOrderSourceKey();
		}
		return orderSourceKey == null ? "notAvailable" : orderSourceKey; 
	}
	
	public Date getOriginalOrderDate() {
		Date originalOrderDate = null;
		if (orderUnitBO != null) {
			originalOrderDate = orderUnitBO.getOriginalOrderDate();
		}
		return originalOrderDate;
	}
	
	public String getPackingSlipNumber() {
		String packingSlipNumber = null;
		if (shippingUnitBO != null) {
			packingSlipNumber = shippingUnitBO.getPackingSlip();
		}
		return packingSlipNumber == null ? "" : packingSlipNumber;
	}

	public String getPackingStatus() {
		String packingStatus = null;
		if (shippingUnitBO != null) {
			packingStatus = shippingUnitBO.getPackingStatus();
		}
		return packingStatus == null ? "IN_PROCESS" : packingStatus;
	}

	public Date getRequestedDeliveryDate() {
		Date date = null;
		if (orderUnitBO != null) {
			date = orderUnitBO.getRequestedDeliveryDate();
		}
		return date;
	}
	
	/* SHIPPING INFORMATION */

	public String getCarrier() {
		String carrier = null;
		if (shippingUnitBO != null) {
			carrier = shippingUnitBO.getCarrierName();
		}
		return carrier == null ? "" : carrier;
	}
	
	public String getTrackingURL() {
		String trackingURL = null;
		if (shippingUnitBO != null) {
			trackingURL = shippingUnitBO.getTrackingURL();
		}
		if(null!= trackingURL){
			if(trackingURL.contains(UPS) || trackingURL.contains(FEDEX))
				trackingURL =trackingURL+getTrackingNumber();
		}   
		return trackingURL == null ? "" : trackingURL;
	}

	public String getDistributionCenterName() {
		String dcName = "";
		if (shippingUnitBO != null) {
			DistributionCenterBO dc = shippingUnitBO.getShipFrom();
			if (dc != null) {
				dcName = dc.getName() == null ? "" : dc.getName();
			}
		}
		return DistributionCenterBO.NOT_FOUND.equals(dcName) ? "" : dcName;
	}
	
	public Date getShipDate() {
		Date shipDate = null;
		
		if (shippingUnitBO != null) {
			shipDate =  shippingUnitBO.getShipDate();
		}
		return shipDate;
	}

	public String getTrackingNumber() {
		String trackingNumber = null;
		if (shippingUnitBO != null) {
			trackingNumber = shippingUnitBO.getTrackingNumber();
		}
		return trackingNumber == null ? "" : trackingNumber;
	}

	/* ORDER STATISTICS */

	@SuppressWarnings("unused")
	public int getTotalLines() {
		int totalLines = 0;
		if (shippingUnitBO != null) {
			List<ShippedItemBO> shippedItemsList = shippingUnitBO.getShippedItemsList();
			if (shippedItemsList != null) {
				for (ShippedItemBO shippedItem : shippedItemsList) {
					totalLines += 1;
				}
			}
		}
		return totalLines;
	}
	
	public int getLinesShipped() {
		int linesShipped = 0;
		if (shippingUnitBO != null) {
			List<ShippedItemBO> shippedItemsList = shippingUnitBO.getShippedItemsList();
			if (shippedItemsList != null) {
				for (ShippedItemBO shippedItem : shippedItemsList) {
					if (shippedItem.getShippedQuantity() > 0) {
						linesShipped += 1;
					}
				}
			}
		}
		return linesShipped;
	}

	public int getTotalPieces() {
		int totalPieces = 0;
		if (shippingUnitBO != null) {
			List<ShippedItemBO> shippedItemsList = shippingUnitBO.getShippedItemsList();
			if (shippedItemsList != null) {
				for (ShippedItemBO shippedItem : shippedItemsList) {
					totalPieces += shippedItem.getOrderedQuantity();
				}
			}
		}
		return totalPieces;
	}

	public int getTotalPiecesShipped() {
		int piecesShipped = 0;
		if (shippingUnitBO != null) {
			List<ShippedItemWrapper> shippedItemsList = getShippedItemsList();
			if (shippedItemsList != null) {
				for (ShippedItemWrapper shippedItemWrapper : shippedItemsList) {
					piecesShipped += shippedItemWrapper.getShippedQuantity();
				}
			}
		}
		return piecesShipped;
	}
	
	/* ORDER LINE ITEMS */

	public List<ShippedItemWrapper> getShippedItemsList(){
		if (shippingUnitBO != null) {
			if (this.shippedItemWrapperList == null) {
				List<ShippedItemBO> shippedItemsList = shippingUnitBO.getShippedItemsList();
				if (shippedItemsList != null) {
					this.shippedItemWrapperList = new ArrayList<ShippedItemWrapper>(shippedItemsList.size());
					for (ShippedItemBO shippedItem: shippedItemsList) {
						ShippedItemWrapper shippedItemWrapper = new ShippedItemWrapper(shippedItem);
						shippedItemWrapper.setPackingSlipNumber(shippingUnitBO.getPackingSlip());
						shippedItemWrapperList.add(shippedItemWrapper);
					}
				}
			}
		}
		return shippedItemWrapperList == null ? new ArrayList<ShippedItemWrapper>(0) : shippedItemWrapperList;
	}

	/* Accessors */
	
	public OrderUnitBO getOrderUnitBO() {
		return orderUnitBO;
	}

	public void setOrderUnitBO(OrderUnitBO orderUnitBO) {
		this.orderUnitBO = orderUnitBO;
	}

	public ShippingUnitBO getShippingUnitBO() {
		return shippingUnitBO;
	}

	public void setShippingUnitBO(ShippingUnitBO shippingUnitBO) {
		this.shippingUnitBO = shippingUnitBO;
	}
	
	
}

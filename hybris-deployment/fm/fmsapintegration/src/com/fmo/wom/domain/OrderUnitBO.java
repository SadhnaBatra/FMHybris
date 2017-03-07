package com.fmo.wom.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.enums.ExternalSystem;

public class OrderUnitBO {

	private String orderNumber;
	private ExternalSystem externalSystem;

	private String orderSourceKey;
	private String orderSourceDescription;

	private List<String> commentsList;
	private Date originalOrderDate;
	private Date invoiceDate;
	private Date cancelledDate;
	private Date requestedDeliveryDate;
	private String orderBy;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	private List<ShippingUnitBO> shippingUnitList;


	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public ExternalSystem getExternalSystem() {
		return externalSystem;
	}

	public void setExternalSystem(ExternalSystem externalSystem) {
		this.externalSystem = externalSystem;
	}

	public List<ShippingUnitBO> getShippingUnitList() {
		return this.shippingUnitList;
	}

	public void setShippingUnitList(List<ShippingUnitBO> shippingUnitList) {
		this.shippingUnitList = shippingUnitList;
	}

	public Date getOriginalOrderDate() {
		return originalOrderDate;
	}

	public void setOriginalOrderDate(Date originalOrderDate) {
		this.originalOrderDate = originalOrderDate;
	}

	public String getOrderSourceKey() {
		return orderSourceKey;
	}

	public void setOrderSourceKey(String orderSourceKey) {
		this.orderSourceKey = orderSourceKey;
	}

	public String getOrderSourceDescription() {
		return orderSourceDescription;
	}

	public void setOrderSourceDescription(String orderSourceDescription) {
		this.orderSourceDescription = orderSourceDescription;
	}

	public List<String> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<String> commentsList) {
		this.commentsList = commentsList;
	}

	public void addComment(String aComment) {
		if (commentsList == null) {
			commentsList = new ArrayList<String>();
		}
		if (GenericValidator.isBlankOrNull(aComment) == false) {
			commentsList.add(aComment);
		}
	}

	public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public Date getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    public void setRequestedDeliveryDate(Date requestedDeliveryDate) {
        this.requestedDeliveryDate = requestedDeliveryDate;
    }

    @Override
	public String toString() {
		return "OrderUnitBO [orderNumber=" + orderNumber + ", externalSystem="
				+ externalSystem + ", orderSourceKey=" + orderSourceKey
				+ ", orderSourceDescription=" + orderSourceDescription
				+ ", commentsList=" + commentsList + ", originalOrderDate="
				+ originalOrderDate + ", shippingUnitList=" + shippingUnitList
				+ "]";
	}

    public void addShippingUnit(ShippingUnitBO shippingUnit) {
        if (shippingUnit == null) return;
        
        if (getShippingUnitList() == null) {
            shippingUnitList = new ArrayList<ShippingUnitBO>();
        }
        
        shippingUnitList.add(shippingUnit);
    }
}

/*
 * Created on Jun 6, 2011
 *
 */
package com.fmo.wom.domain;

import java.io.Serializable;

//import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;


/**
 * The persistent class for the QUANTITY database table.
 * 
 * NOTE: Do NOT extend this class from either WOMBaseBO or WOMCodeDescBO since
 * 			the underlying 'QUANTITY' table does not contain any audit column.
 * 			Else, the audit columns will be included in SQL statements sent to
 * 			the database, resulting in errors. Currently there is no way in JPA
 * 			to suppress extra attributes passed on from the '//MappedSuperclass'.
 * 
 * //author amarnr85
 */
//Entity
//Table(name = "QUANTITY")
public class QuantityBO extends WOMBaseBO implements Serializable {
    
	private static final long serialVersionUID = 1L;

	public QuantityBO(ItemBO itemBO) {
	    setItem(itemBO);
	}
	
	//Id
	//SequenceGenerator(name = "QUANTITY_QTYID_GENERATOR", sequenceName = "SEQ_QTY_ID")
	//GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUANTITY_QTYID_GENERATOR")
	//Column(name = "QTY_ID")
	private long qtyId;

//	//Column(name = "BCKORD_QTY")
//	private int backorderedQty;

	//Column(name = "BULK_FLG")
	//Type(type="yes_no")
	private boolean bulk;

//	//Transient
//	private int orderedQty;

//	//Column(name = "CANCELED_QTY")
//	private int cancelledQty;

	//Column(name = "FACTOR_QTY")
	private int factorQuantity;

	//Column(name = "OVER_QTY")
	private int overQuantity;

	//Column(name = "ROUNDED_ORD_QTY")
	private int roundedOrderQty;

	//Column(name = "RQSTD_QTY")
	private int requestedQuantity;

//	//Column(name = "RSRVD_QTY")
//	private int reservedQty;

	//OneToOne(fetch = FetchType.LAZY)
	//JoinColumn(name = "ORD_DTL_ID")
	//XmlTransient
	private ItemBO item;
	
	//Column(name = "QTY_UOM_CD")
	private String qtyUomCd = "EA";

	//Transient
	private UnitOfMeasureBO qtyUOM;

	//Column(name = "SOLD_IN_MULTIPLES_FLG")
	//Type(type="yes_no")
	private boolean soldInMultiples;

	//Column(name = "SOLD_IN_MULTIPLES_QTY")
	private int soldInMultipleQuantity;

	//Transient
	private int reasonableQuantityThreshold;
	
	//Transient 
	private int largeQuantityThreshold;
	
	//Transient 
    private int minimumOrderQuantity = 0;
    
	public QuantityBO() {
	}

	public long getQtyId() {
		return qtyId;
	}

	public void setQtyId(long qtyId) {
		this.qtyId = qtyId;
	}

//	public int getBackorderedQty() {
//		return backorderedQty;
//	}
//
//	public void setBackorderedQty(int backorderedQty) {
//		this.backorderedQty = backorderedQty;
//	}

	public boolean isBulk() {
		return bulk;
	}

	public void setBulk(boolean bulk) {
		this.bulk = bulk;
	}

//	public int getOrderedQty() {
//		return orderedQty;
//	}
//
//	public void setOrderedQty(int orderedQty) {
//		this.orderedQty = orderedQty;
//	}

//	public int getCancelledQty() {
//		return cancelledQty;
//	}
//
//	public void setCancelledQty(int cancelledQty) {
//		this.cancelledQty = cancelledQty;
//	}

	public int getFactorQuantity() {
		return factorQuantity;
	}

	public void setFactorQuantity(int factorQuantity) {
		this.factorQuantity = factorQuantity;
	}

	public int getOverQuantity() {
		return overQuantity;
	}

	public void setOverQuantity(int overQuantity) {
		this.overQuantity = overQuantity;
	}

	public int getRoundedOrderQty() {
		return roundedOrderQty;
	}

	public void setRoundedOrderQty(int roundedOrderQty) {
		this.roundedOrderQty = roundedOrderQty;
	}

	public int getRequestedQuantity() {
		return requestedQuantity;
	}

	public void setRequestedQuantity(int requestedQuantity) {
		this.requestedQuantity = requestedQuantity;
	}

//	public int getReservedQty() {
//		return reservedQty;
//	}
//
//	public void setReservedQty(int reservedQty) {
//		this.reservedQty = reservedQty;
//	}

	public ItemBO getItem() {
		return item;
	}

	@XmlTransient
	public void setItem(ItemBO item) {
		this.item = item;
	}

	public String getQtyUomCd() {
		return qtyUomCd;
	}

	public void setQtyUomCd(String qtyUomCd) {
		this.qtyUomCd = qtyUomCd;
	}

	public UnitOfMeasureBO getQtyUOM() {
		return qtyUOM;
	}

	public void setQtyUOM(UnitOfMeasureBO qtyUOM) {
		this.qtyUOM = qtyUOM;
	}

	public boolean isSoldInMultiples() {
		return soldInMultiples;
	}

	public void setSoldInMultiples(boolean soldInMultiples) {
		this.soldInMultiples = soldInMultiples;
	}

	public int getSoldInMultipleQuantity() {
		return soldInMultipleQuantity;
	}

	public void setSoldInMultipleQuantity(int soldInMultipleQuantity) {
		this.soldInMultipleQuantity = soldInMultipleQuantity;
	}
	/*
	public void afterUnmarshal(Unmarshaller u, Object parent){
		this.item = (ItemBO) parent;
	}*/

	public int getReasonableQuantityThreshold() {
	    return reasonableQuantityThreshold;
	}

	public void setReasonableQuantityThreshold(int reasonableQuantityThreshold) {
	    this.reasonableQuantityThreshold = reasonableQuantityThreshold;
	}

	public int getLargeQuantityThreshold() {
	    return largeQuantityThreshold;
	}

	public void setLargeQuantityThreshold(int largeQuantityThreshold) {
	    this.largeQuantityThreshold = largeQuantityThreshold;
	}
	
	public int getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public void setMinimumOrderQuantity(int minimumOrderQuantity) {
        this.minimumOrderQuantity = minimumOrderQuantity;
    }

    @Override
	public String toString() {
		return "QuantityBO [qtyId=" + qtyId + ", bulk=" + bulk
				+ ", factorQuantity="
				+ factorQuantity + ", overQuantity=" + overQuantity
				+ ", roundedOrderQty=" + roundedOrderQty
				+ ", requestedQuantity=" + requestedQuantity + ", qtyUomCd=" + qtyUomCd
				+ ", qtyUOM=" + qtyUOM + ", soldInMultiples=" + soldInMultiples + ", soldInMultipleQuantity="
				+ soldInMultipleQuantity + ", minimumOrderQuantity=" + minimumOrderQuantity 
				+ "]";
	}

}

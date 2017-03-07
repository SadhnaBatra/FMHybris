package com.fmo.wom.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;
//import javax.persistence.*;


/**
 * The persistent class for the ORDER_FULFILLMENT database table.
 * 
 * //author vishws74
 */
//Entity
//Table(name="ORDER_FULFILLMENT")
public class OrderFulfillmentBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	//Id
	//SequenceGenerator(name="ORDER_FULFILLMENT_ORDFULFILLMENTID_GENERATOR", sequenceName="SEQ_ORD_FULFILLMENT_ID")
	//GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ORDER_FULFILLMENT_ORDFULFILLMENTID_GENERATOR")
	//Column(name="ORD_FULFILLMENT_ID")
	private long ordFulfillmentId;

	//Column(name="ASSGND_QTY")
	private int assignedQty;

	//Column(name="BCKORD_QTY")
	private int backorderedQty;

	//Column(name="DIST_CNTR_ID")
	private long distCntrId;

	//Column(name="FULFILLMENT_SEQ_NBR")
	private long fulfillmentSeqNbr;

	//Column(name="RSRVD_QTY")
	private int reservedQty;

	//bi-directional many-to-one association to ItemBO
	//ManyToOne(fetch=FetchType.LAZY)
	//JoinColumn(name="ORD_DTL_ID")
	@XmlTransient
	private ItemBO item;

    public OrderFulfillmentBO() {
    }

	public long getOrdFulfillmentId() {
		return this.ordFulfillmentId;
	}

	public void setOrdFulfillmentId(long ordFulfillmentId) {
		this.ordFulfillmentId = ordFulfillmentId;
	}

	public int getAssignedQty() {
		return this.assignedQty;
	}

	public void setAssignedQty(int assignedQty) {
		this.assignedQty = assignedQty;
	}

	public int getBackorderedQty() {
		return this.backorderedQty;
	}

	public void setBackorderedQty(int backorderedQty) {
		this.backorderedQty = backorderedQty;
	}

	public long getDistCntrId() {
		return this.distCntrId;
	}

	public void setDistCntrId(long distCntrId) {
		this.distCntrId = distCntrId;
	}

	public long getFulfillmentSeqNbr() {
		return this.fulfillmentSeqNbr;
	}

	public void setFulfillmentSeqNbr(long fulfillmentSeqNbr) {
		this.fulfillmentSeqNbr = fulfillmentSeqNbr;
	}

	public int getReservedQty() {
		return this.reservedQty;
	}

	public void setReservedQty(int reservedQty) {
		this.reservedQty = reservedQty;
	}

	public ItemBO getItem() {
		return this.item;
	}
	@XmlTransient
	public void setItem(ItemBO item) {
		this.item = item;
	}
	/*
	public void afterUnmarshal(Unmarshaller u, Object parent){
		this.item = (ItemBO) parent;
	}*/

	//Override
	public String toString() {
		return "OrderFulfillmentBO [ordFulfillmentId=" + ordFulfillmentId
				+ ", assignedQty=" + assignedQty + ", backorderedQty="
				+ backorderedQty + ", distCntrId=" + distCntrId
				+ ", fulfillmentSeqNbr=" + fulfillmentSeqNbr + ", reservedQty="
				+ reservedQty + ", createUserId="
				+ createUserId + ", createTimestamp=" + createTimestamp
				+ ", updateUserId=" + updateUserId + ", updateTimestamp="
				+ updateTimestamp + "]";
	}

}
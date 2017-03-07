package com.fmo.wom.domain;

import java.util.Date;


/**
 * Defines the entity object associated with the <code>CART_HEADER</code> table.
 * <p>
 * A cart is uniquely identified by the UserId (LDAP id) of the owner and the
 * account code of the associated Bill To account. The cart has a one to one
 * relationship with an encapsulated <code>OrderBO</code> object that contains
 * all order-related information.
 * </p>
 * <p>
 * 
 * <pre>
 * Table Name = CART_HEADER
 * 
 * PK  	CART_HEADER_ID          NUMBER(10)      
 *     	OWNER_USERID            VARCHAR2(10)      
 *     	ORD_HDR_ID              NUMBER(10)     
 *     	ACCOUNT_CODE            VARCHAR2(10)                                                                        
 *     	CART_CREATE_TIMESTAMP   TIMESTAMP
 * 
 *     	CREATE_USERID           VARCHAR2(10)
 *     	CREATE_TIMESTAMP        TIMESTAMP
 *     	UPDATE_USERID           VARCHAR2(10)
 *     	UPDATE_TIMESTAMP        TIMESTAMP
 *     
 *     	Sequence name = SEQ_CART_HEADER_ID
 * </pre>
 * 
 * </p>
 * 
 * @author Jim Miller
 * 
 */
public class CartBO extends WOMBaseBO {

	/**
	 * Identifies CART HDR table row in database
	 */
	private long cartHeaderId;

	/**
	 * LDAP ID of cart owner
	 */
	private String ownerUserId;

	/**
	 * ID of related ORDER_HDR table row
	 */
	private long ordHdrId;

	/**
	 * Account code of related BillTo account
	 */
	// @Column(name = "BILL_TO_ACCOUNT_CODE")
	private String billToAccountCode;

	/**
	 * Account code of related ShipTo account
	 */
	// @Column(name = "SHIP_TO_ACCOUNT_CODE")
	private String shipToAccountCode;

	/**
	 * Date and time the cart was created
	 */
	private Date cartCreationTime;

	/**
	 * Related order
	 */
	private OrderBO order = null;

	/**
	 * Related Manual Ship To Address
	 */
	// @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	// @PrimaryKeyJoinColumn
	private ManualShipToBO manualShipToAddress = null;

	/**
	 * Gets the account code of the BillTo account associated with the cart.
	 * 
	 * @return <code>String</code> value of BillTo account code
	 */
	public String getBillToAccountCode() {
		return this.billToAccountCode;
	}

	/**
	 * Sets the account code of the BillTo account associated with the cart to
	 * the value defined by the <code>billToAccountCode</code> parameter.
	 * 
	 * @param billToAccountCode
	 *            <code>String</code> value of the BillTo account code.
	 */
	public void setBillToAccountCode(String billToAccountCode) {
		this.billToAccountCode = billToAccountCode;
	}

	/**
	 * Gets the date ane time the cart was created
	 * 
	 * @return <code>Date<</code> representing the date and time of cart
	 *         creation
	 */
	public Date getCartCreationTime() {
		return cartCreationTime;
	}

	/**
	 * Sets the cart creation time to the value defined by the
	 * <code>cartCreationTime</code> parameter.
	 * 
	 * @param cartCreationTime
	 *            the <code>Date</code> representing the date and time the cart
	 *            was created
	 */
	public void setCartCreationTime(Date cartCreationTime) {
		this.cartCreationTime = cartCreationTime;
	}

	/**
	 * Gets the unique Cart identifier
	 * 
	 * @return <code>long</code> value of Cart Header ID
	 */
	public long getCartHeaderId() {
		return cartHeaderId;
	}

	/**
	 * Sets the unique Cart identifier to the value of the
	 * <code>cartHeaderId</code> parameter.
	 * 
	 * @param cartHeaderId
	 *            <code>long</code> value representing the unique id for this
	 *            Cart
	 */
	public void setCartHeaderId(long cartHeaderId) {
		this.cartHeaderId = cartHeaderId;
	}

	/**
	 * Gets the identifier of the associated order header row.
	 * 
	 * @return <code>long</code> value of the identifier
	 */
	public long getOrdHdrId() {
		return ordHdrId;
	}

	/**
	 * Sets the identifier of the associated order header row to the value of
	 * the <code>ordHdrId</code> parameter.
	 * 
	 * @param ordHdrId
	 *            <code>long</code> value ot the order header row identifier
	 */
	public void setOrdHdrId(long ordHdrId) {
		this.ordHdrId = ordHdrId;
	}

	/**
	 * Gets the owner UserID associated with the cart owner
	 * 
	 * @return <code>String</code> user id of the cart owner
	 */
	public String getOwnerUserId() {
		return ownerUserId;
	}

	/**
	 * Sets the owner UserID of the cart to the value of the
	 * <code>ownerUserId</code> parameter.
	 * 
	 * @param ownerUserId
	 *            <code>String</code> value of the UserID of the cart owner.
	 */
	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	/**
	 * Gets the order associated with the cart
	 * 
	 * @return <code>OrderBO</code> associated with cart
	 */
	public OrderBO getOrder() {
		return order;
	}

	/**
	 * Sets the order associated with the cart to the value defined by the
	 * <code>order</code> parameter.
	 * 
	 * @param order
	 *            <code>OrderBO</code> representing order associated with the
	 *            cart.
	 */
	public void setOrder(OrderBO order) {
		this.order = order;
	}

	/**
	 * Gets the Manual Ship To information associated with the cart.
	 * 
	 * @return the manualShipToAddress
	 */
	public ManualShipToBO getManualShipToAddress() {
		return manualShipToAddress;
	}

	/**
	 * Sets the Manual Ship To information associated with thee cart.
	 * 
	 * @param manualShipToAddress
	 *            the manualShipToAddress to set
	 */
	public void setManualShipToAddress(ManualShipToBO manualShipToAddress) {
		this.manualShipToAddress = manualShipToAddress;
	}

	/**
	 * Gets the account code of the ShipTo account associated with the cart.
	 * 
	 * @return <code>String</code> value of ShipTo account code
	 */
	public String getShipToAccountCode() {
		return shipToAccountCode;
	}

	/**
	 * Sets the account code of the ShipTo account associated with the cart to
	 * the value defined by the <code>shipToAccountCode</code> parameter.
	 * 
	 * @param shipToAccountCode
	 *            <code>String</code> value of the ShipTo account code.
	 */

	public void setShipToAccountCode(String shipToAccountCode) {
		this.shipToAccountCode = shipToAccountCode;
	}

	public String toString() {
		return "CartBO [cartHeaderId=" + cartHeaderId + ", billToAccountCode="
				+ billToAccountCode + ", cartCreationTime=" + cartCreationTime
				+ ", ordHdrId=" + ordHdrId + ", ownerUserId=" + ownerUserId
				+ ", shipToAccountCode=" + shipToAccountCode + "]";
	}

	/**
	 * Generate hash code for CartBO using billToAccountCode, cartHeaderId,
	 * ordHdrId, ownerUserId, and shipToAccountCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((billToAccountCode == null) ? 0 : billToAccountCode
						.hashCode());
		result = prime * result + (int) (cartHeaderId ^ (cartHeaderId >>> 32));
		result = prime * result + (int) (ordHdrId ^ (ordHdrId >>> 32));
		result = prime * result
				+ ((ownerUserId == null) ? 0 : ownerUserId.hashCode());
		result = prime
				* result
				+ ((shipToAccountCode == null) ? 0 : shipToAccountCode
						.hashCode());
		return result;
	}

	/**
	 * Tests for equality using billToAccountCode, cartHeaderId,
	 * ordHdrId, ownerUserId, and shipToAccountCode
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CartBO other = (CartBO) obj;
		if (billToAccountCode == null) {
			if (other.billToAccountCode != null) {
				return false;
			}
		} else if (!billToAccountCode.equals(other.billToAccountCode)) {
			return false;
		}
		if (cartHeaderId != other.cartHeaderId) {
			return false;
		}
		if (ordHdrId != other.ordHdrId) {
			return false;
		}
		if (ownerUserId == null) {
			if (other.ownerUserId != null) {
				return false;
			}
		} else if (!ownerUserId.equals(other.ownerUserId)) {
			return false;
		}
		if (shipToAccountCode == null) {
			if (other.shipToAccountCode != null) {
				return false;
			}
		} else if (!shipToAccountCode.equals(other.shipToAccountCode)) {
			return false;
		}
		return true;
	}

}

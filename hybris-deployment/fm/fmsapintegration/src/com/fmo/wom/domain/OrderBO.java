package com.fmo.wom.domain;

import static java.math.RoundingMode.HALF_UP;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderStatus;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.enums.UsageType;

/**
 * The persistent class for the ORDER_HDR database table.
 * 
 * //author vishws74
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="OrderBO")

public class OrderBO extends WOMBaseBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long ordHdrId;
	
	//Transient
	private String tpSecretKey;

	//Column(name = "MSTR_ORD_NBR")
	private String mstrOrdNbr;
	
	//Column(name = "SAP_CONFIRMATION_NBR")
	private String sapConfirmationNumber;

	//Column(name = "BILLTO_ACCT_CD")
	private String billToAcctCd;

	//Column(name = "SHIPTO_ACCT_CD")
	private String shipToAcctCd;

	//Column(name = "MANUAL_SHIPTO_ACCT_CD")
	private String manualShipToAccountCode;
	
	//Column(name = "ORD_TYP_CD")
	//Enumerated(EnumType.STRING)
	private OrderType orderType;

	// bi-directional many-to-one association to OrderDtlBO
	@XmlElement(name="parts")
	private List<ItemBO> itemList;

	/** Extra fields compared to the original OrderBO entity: **/
	//Column(name = "COMMENT1")
	private String comment1;

	//Column(name = "COMMENT2")
	private String comment2;

	//Column(name = "COMMENT3")
	private String comment3;
	
	//Column(name = "CUST_PO_NBR")
	private String custPoNbr;

    //Column(name = "SALES_ORG_CD")
	private String salesOrgCode;

	//Column(name = "DIST_CHANNEL")
	private String distChannel;

	//Column(name = "DIVISION")
	private String division;
	
	//Column(name = "ORDERED_BY")
	private String orderedBy;

	//Column(name = "FM_IPO_DOC_NBR")
	private String fmIpoDocNbr;

	//Temporal(TemporalType.DATE)
	//Column(name = "FUTURE_DATE")
	private Date futureDate;

	//uni-directional one-to-one relationship to ManualShiptoAddress
	//OneToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	//JoinColumn(name="MANUAL_SHIPTO_ADDR_ID")
	@XmlTransient
	private ManualShipToAddressBO manualShipToAddress;

	//Column(name = "ORD_SRC_CD")
	////Enumerated(EnumType.STRING)
    private String orderSource;

	//Column(name = "ORD_STATUS_CD")
	private String ordStatusCd;

	//Temporal(TemporalType.DATE)
	//Column(name = "RSRVD_TIME")
	private Date rsrvdTime;
	
	//Column(name = "USAGE_TYP_CD")
	private String usageTypeCd = UsageType.PURCHASE.getCode();
	
	//Transient
	@XmlTransient
	private TradingPartnerBO tradingPartner = null;

	//Transient
	private AccountBO billToAcct = null;

	//Transient
	private AccountBO shipToAcct = null;
	
	//Transient
	private UserAccountBO userAccount = null;
	
	//Column(name = "FREE_FREIGHT_FLG")
	//Type(type="yes_no")
	boolean receivesFreeFreight = false;
	
	//Column(name="USER_ID")
	private String userId;
	
	//Transient
	private boolean manualShipTo = false;
	
	//Column(name="MARKET_CODE")
	private String marketCode;

	//Column(name="EMAIL_ADDR1")
    private String customerEmailAddress1;
	
	//Column(name="EMAIL_ADDR2")
    private String customerEmailAddress2;
	
	//Transient
	private String weightUOM = "";

	private String pkupOrderType;
	
	//Transient
	private boolean orderSavedButNoDeliveryCreatedFlag = false;

	public boolean isOrderSavedButNoDeliveryCreatedFlag() {
		return orderSavedButNoDeliveryCreatedFlag;
	}

	public void setOrderSavedButNoDeliveryCreatedFlag(
			boolean orderSavedButNoDeliveryCreatedFlag) {
		this.orderSavedButNoDeliveryCreatedFlag = orderSavedButNoDeliveryCreatedFlag;
	}

	/**
	 * @return the pkupOrderType
	 */
	public String getPkupOrderType()
	{
		return pkupOrderType;
	}

	/**
	 * @param pkupOrderType
	 *           the pkupOrderType to set
	 */
	public void setPkupOrderType(final String pkupOrderType)
	{
		this.pkupOrderType = pkupOrderType;
	}

	public String getWeightUOM()
	{
		return weightUOM;
	}

	public void setWeightUOM(String weightUOM) {
		this.weightUOM = weightUOM;
	}

	public OrderBO() {
	}

	public long getOrdHdrId() {
		return this.ordHdrId;
	}

	public void setOrdHdrId(long ordHdrId) {
		this.ordHdrId = ordHdrId;
	}

	public String getTpSecretKey() {
		return tpSecretKey;
	}

	public void setTpSecretKey(String tpSecretKey) {
		this.tpSecretKey = tpSecretKey;
	}

	public String getMstrOrdNbr() {
		return this.mstrOrdNbr;
	}

	public void setMstrOrdNbr(String mstrOrdNbr) {
		this.mstrOrdNbr = mstrOrdNbr;
	}

	public String getBillToAcctCd() {
		return this.billToAcctCd;
	}

	public void setBillToAcctCd(String billToAcctCd) {
		this.billToAcctCd = billToAcctCd;
	}

	public String getShipToAcctCd() {
		return this.shipToAcctCd;
	}

	public void setShipToAcctCd(String shipToAcctCd) {
		this.shipToAcctCd = shipToAcctCd;
	}

	public String getManualShipToAccountCode() {
        return manualShipToAccountCode;
    }

    public void setManualShipToAccountCode(String manualShipToAccountCode) {
        this.manualShipToAccountCode = manualShipToAccountCode;
    }

    public ManualShipToAddressBO getManualShipToAddress() {
		return manualShipToAddress;
	}

	public void setManualShipToAddress(ManualShipToAddressBO manualShipToAddress) {
		this.manualShipToAddress = manualShipToAddress;
	}

	public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<ItemBO> getItemList() {
		if (null == itemList) {
		    itemList = new ArrayList<ItemBO>();
		}
		return itemList;
	}

	public void setItemList(List<ItemBO> itemList) {
	    if (itemList != null) {
	        for (ItemBO anItem : itemList) {
	            anItem.setOrderHdr(this);
	        }
	    }
		this.itemList = itemList;
	}

	public void clearItemList() {
	    if (itemList != null) {
            for (ItemBO anItem : itemList) {
                anItem.setOrderHdr(null);
            }
	    }
	    
	    itemList = new ArrayList<ItemBO>();
	}
	  
	/** Getters and setters for extra fields: **/
	public String getComment1() {
		return this.comment1;
	}

	public void setComment1(String comment1) {
		this.comment1 = removeLineFeedsReturnCharsTabs(comment1);
	}

	public String getComment2() {
		return this.comment2;
	}

	public void setComment2(String comment2) {
		this.comment2 = removeLineFeedsReturnCharsTabs(comment2);
	}

	public String getComment3() {
		return this.comment3;
	}

	public void setComment3(String comment3) {
		this.comment3 = removeLineFeedsReturnCharsTabs(comment3);
	}

	public String getCustPoNbr() {
		return this.custPoNbr;
	}

	public void setCustPoNbr(String custPoNbr) {
		this.custPoNbr = custPoNbr;
	}

	public String getDistChannel() {
		return this.distChannel;
	}

	public void setDistChannel(String distChannel) {
		this.distChannel = distChannel;
	}

	public String getSalesOrgCode() {
        return salesOrgCode;
    }

    public void setSalesOrgCode(String salesOrgCode) {
        this.salesOrgCode = salesOrgCode;
    }

    public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getFmIpoDocNbr() {
		return this.fmIpoDocNbr;
	}

	public void setFmIpoDocNbr(String fmIpoDocNbr) {
		this.fmIpoDocNbr = fmIpoDocNbr;
	}

	public Date getFutureDate() {
		return this.futureDate;
	}

	public void setFutureDate(Date futureDate) {
		this.futureDate = futureDate;
	}

	/**
	 * Use the enum method
	 * //return
	 */
	//Deprecated
	public String getOrdStatusCd() {
		return this.ordStatusCd;
	}

	/**
	 * Use the enum method
	 * //param ordStatusCd
	 */
	//Deprecated
	public void setOrdStatusCd(String ordStatusCd) {
		this.ordStatusCd = ordStatusCd;
	}
	
	public OrderStatus getOrderStatus() {
	    if (GenericValidator.isBlankOrNull(ordStatusCd) == false) {
	        return OrderStatus.getFromCode(ordStatusCd);
	    } else {
	        return null;
	    }
	}

	public void setOrderStatus(OrderStatus orderStatus) {
	    if (orderStatus != null) {
	        setOrdStatusCd(orderStatus.getCode());
	    } else {
	        setOrdStatusCd(null);
	    }
	}
	
	public Date getRsrvdTime() {
		return this.rsrvdTime;
	}

	public void setRsrvdTime(Date rsrvdTime) {
		this.rsrvdTime = rsrvdTime;
	}

	/**
     * Use the enum method
     */
    //Deprecated
    public String getUsageTypeCd() {
		return this.usageTypeCd;
	}

    /**
     * Use the enum method
     * //param usageTypCd
     */
    //Deprecated
    public void setUsageTypeCd(String usageTypeCd) {
		this.usageTypeCd = usageTypeCd;
	}
    
    public UsageType getUsageType() {
        if (GenericValidator.isBlankOrNull(usageTypeCd) == false) {
            return UsageType.getFromCode(usageTypeCd);
        } else {
            return null;
        }
    }

    public void setUsageType(UsageType usageType) {
        if (usageType != null) {
            setUsageTypeCd(usageType.getCode());
        } else {
            setUsageTypeCd(null);
        }
    }

	public TradingPartnerBO getTradingPartner() {
		return tradingPartner;
	}

	public void setTradingPartner(TradingPartnerBO tradingPartner) {
		this.tradingPartner = tradingPartner;
	}

	public AccountBO getBillToAcct() {
		return billToAcct;
	}

	public void setBillToAcct(AccountBO billToAcct) {
		this.billToAcct = billToAcct;
		
		if (billToAcct != null) {
			this.billToAcctCd = this.billToAcct.getAccountCode();
			SapAcctBO sapAccount = billToAcct.getSapAccount();
			if (sapAccount != null) {
			    CustomerSalesOrgBO selectedCustomerSalesOrg = sapAccount.getCustomerSalesOrganization();
			    if (selectedCustomerSalesOrg != null) {
			        this.distChannel = selectedCustomerSalesOrg.getDistributionChannel();
			        this.division = selectedCustomerSalesOrg.getDivision();
			        this.salesOrgCode = selectedCustomerSalesOrg.getSalesOrganization().getCode();
			    }
			}
		}
	}

	public AccountBO getShipToAcct() {
		return shipToAcct;
	}

	public void setShipToAcct(AccountBO shipToAcct) {
		this.shipToAcct = shipToAcct;
		if(this.shipToAcct != null){
			this.shipToAcctCd = this.shipToAcct.getAccountCode();
		}
	}
	
	public OrderSource getOrderSource() {
        return OrderSource.getFromCode(this.orderSource);
    }

    public void setOrderSource(OrderSource orderSource) {
        this.orderSource = orderSource.getOrderSource();
    }

	public UserAccountBO getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccountBO userAccount) {
		this.userAccount = userAccount;
	}

	public boolean receivesFreeFreight() {
		return receivesFreeFreight;
	}

	public void setReceivesFreeFreight(boolean receivesFreeFreight) {
		this.receivesFreeFreight = receivesFreeFreight;
	}

	public String getSapConfirmationNumber() {
		return sapConfirmationNumber;
	}

	public void setSapConfirmationNumber(String sapConfirmationNumber) {
		this.sapConfirmationNumber = sapConfirmationNumber;
	}

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public boolean isManualShipTo() {
		return manualShipTo;
	}

	public void setManualShipTo(boolean manualShipTo) {
		this.manualShipTo = manualShipTo;
	}

	public Market getMarketCode() {
        return Market.getFromCode(marketCode);
    }

    public void setMarketCode(Market market) {
        this.marketCode = market.getCode();
    }
	
	public String getCustomerEmailAddress1() {
        return customerEmailAddress1;
    }

    public void setCustomerEmailAddress1(String customerEmailAddress1) {
        this.customerEmailAddress1 = customerEmailAddress1;
    }

    public String getCustomerEmailAddress2() {
        return customerEmailAddress2;
    }

    public void setCustomerEmailAddress2(String customerEmailAddress2) {
        this.customerEmailAddress2 = customerEmailAddress2;
    }


	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}
	
    public BackOrderPolicy getBackOrderPolicy() {
    	// For All upload orders the Back order policy should always be Ship And Back Order. 
    	if (this.getOrderSource()== OrderSource.UPLOAD)
    		return BackOrderPolicy.DEFAULT;
    	
		for(ItemBO anItem: itemList) {
			if( anItem.getBackorderPolicy() == BackOrderPolicy.SHIP_AND_BACKORDER || 
			    anItem.getBackorderPolicy() == BackOrderPolicy.BACKORDER_ALL) {
				return BackOrderPolicy.SHIP_AND_BACKORDER;
			}
		}
		return BackOrderPolicy.SHIP_AND_CANCEL;
	}
	
	public boolean hasAtLeastOneGoodItem() {
		List<ItemBO> itemList = this.getItemList();
		
		for(ItemBO anItem: itemList) {
			if(anItem.canProcessItem() ) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAllProcessableItems() {
		List<ItemBO> itemList = this.getItemList();
		
		for(ItemBO anItem: itemList) {
			if(!anItem.canProcessItem() ) {
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * utility method to retrieve the customer sales org description embedded down in the
	 * objected hierarchy for the bill to on this order
	 * //param completedOrder
	 * //return
	 */
    public String getCustomerSalesOrganizationDescription() {
        String customerSalesOrganizationDesc = null;
        AccountBO billToAccount = getBillToAcct();
        if (billToAccount != null) {
            SapAcctBO sapAccount = billToAccount.getSapAccount();
            if (sapAccount != null) {
                CustomerSalesOrgBO customerSalesOrg = sapAccount.getCustomerSalesOrganization();
                if (customerSalesOrg != null) {
                    SalesOrganizationBO salesOrganization = customerSalesOrg.getSalesOrganization();
                    if (salesOrganization != null) {
                        customerSalesOrganizationDesc = salesOrganization.getDesc();
                    }
                }
            }
        }
        return customerSalesOrganizationDesc == null ? "" : customerSalesOrganizationDesc;
    }
	 
	
	@XmlElement
	public String getTotalItemPrice(){
		double itemPrice = 0;
		for (ItemBO item : getItemList()) {
		    if (item.getItemPrice() != null) {
		        itemPrice += item.getItemPrice().getDisplayPrice() * item.getOrderedQty();
		    }
		}
		
		DecimalFormat format = new DecimalFormat("0.00"); 
		return format.format(itemPrice);
	}

	public BigDecimal getTotalOrderPrice() {
		BigDecimal totalOrderPrice = new BigDecimal("0.00");
		for (ItemBO item: getItemList()) {
			if (item.getItemPrice() != null) {
				BigDecimal quantity = (new BigDecimal(item.getItemQty().getRequestedQuantity()).setScale(2, HALF_UP));
				BigDecimal itemPrice = (new BigDecimal(item.getItemPrice().getDisplayPrice()).setScale(2, HALF_UP));
				BigDecimal lineItemTotalPrice = quantity.multiply(itemPrice).setScale(2, HALF_UP);
				totalOrderPrice = totalOrderPrice.add(lineItemTotalPrice).setScale(2);
			}
		}
		return totalOrderPrice;
	}
	
	//Override
	public String toString() {
		return "OrderBO [ordHdrId=" + ordHdrId + ", tpSecretKey=" + tpSecretKey
				+ ", mstrOrdNbr=" + mstrOrdNbr 
				+ ", sapConfirmationNumber=" + sapConfirmationNumber 
				+ ", billToAcctCd=" + billToAcctCd
				+ ", shipToAcctCd=" + shipToAcctCd + ", orderType=" + orderType
//				+ ", itemList=" + itemList 
				+ ", comment1=" + comment1
				+ ", comment2=" + comment2 + ", comment3=" + comment3
				+ ", custPoNbr=" + custPoNbr + ", distChannel=" + distChannel
				+ ", division=" + division + ", fmIpoDocNbr=" + fmIpoDocNbr
				+ ", futureDate=" + futureDate 
				+ ", manualShipToAddress="	+ manualShipToAddress 
				+ ", orderSource=" + orderSource
				+ ", ordStatusCd=" + ordStatusCd + ", rsrvdTime=" + rsrvdTime
				+ ", usageTypeCd=" + usageTypeCd
				+ ", userId=" + userId 
				+ ", billToAcct=" + billToAcct
				+ ", shipToAcct=" + shipToAcct
				+"]";
	}

	public String toMultiLineString() {
	    StringBuffer out = new StringBuffer();
	    
	    // get the object and exclude items
	    out.append(new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE) {
	                     protected boolean accept(Field f) {
	                            return super.accept(f) && !f.getName().equals("itemList");
	                     }
	             }).toString();
	    //return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	    
	    out.append("\nItem List:\n");
        
	    // now go through the items and make them look nice
	    if (itemList != null) {
	        for (ItemBO anItem : itemList) {
	            out.append("Line Number "+anItem.getLineNumber()+": "+new ReflectionToStringBuilder(anItem, ToStringStyle.MULTI_LINE_STYLE) {
                    protected boolean accept(Field f) {
                        return super.accept(f) && 
                            !f.getName().equals("orderHdr") &&
                            !f.getName().equals("lineNumber");
                 }
         }).toString();
	            out.append("\n");
	        }
	    }
	    
	    return out.toString();
	}

	/**
	 * total up the weights of all the processable items in this order.  NOTE: assumes same unit of measure
	 * //return
	 */
    public double getTotalWeight() {
        double totalWeight = 0;
        for (ItemBO item : getItemList()) {
            if (item.canProcessItem() && item.getItemWeight() != null) {
                totalWeight += item.getItemWeight().getWeight() * item.getItemQty().getRequestedQuantity();
            }
        }
        
        return Round(totalWeight,2);
    }
	

    
    public static double Round(double Rval, int Rpl) {
    	double p = (double)Math.pow(10,Rpl);
    	Rval = Rval * p;
    	return (double)Math.round(Rval)/p;
    }

    /*
     * This method should be in WOMCommon util. But, due to WOMCommon 
     * importing this module WOMDomain that is not possible.
     */
	public static String removeLineFeedsReturnCharsTabs(String str) {
		String cleanStr = str;
		cleanStr = cleanStr.replaceAll("\n", " ");
		cleanStr = cleanStr.replaceAll("\t", " ");
		cleanStr = cleanStr.replaceAll("\r", " ");
		return cleanStr;
	}
}
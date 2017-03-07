/*
 * Created on Jun 6, 2011
 *
 */
package com.fmo.wom.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.ExternalSystemStatusBO.StatusCategory;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.LineStatus;
import com.fmo.wom.helper.BooleanAdapter;

/**
 * The persistent class for the ORDER_DTL database table.
 * 
 * @author	vishws74
 */
//Table(name="ORDER_DTL")

public class ItemBO extends WOMBaseBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long ordDtlId;

	// bi-directional many-to-one association to OrderHdrBO
	private OrderBO orderHdr;

	private int lineNumber;

	private String partNumber;

	private String displayPartNumber;

	private String aaiaBrand;

	private String scacCode;

	private String carrierCode;

	private String shippingMethodCode;
	
	private String transportationMethodCode;

    private String backorderPolicyDbCode;

	private boolean kit;

	private PriceBO itemPrice;

	private String comment;

	private String lineStatusCd;

	private String lineStatusRsnCd;

	// bi-directional many-to-one association to OrderDtlBO
	private List<OrderFulfillmentBO> orderFulfillmentList;

	private List<InventoryBO> inventory;
	
	private List<InventoryBO> additionalInventory;
	
	private InventoryBO requestedInventory;

	private QuantityBO itemQty;

	private String description;

	private String productFlag;
	
	private String fmBrandCode;
	
	private String brandState;

	private String orderEntryComment;

	private String brStShortDesc;

	private String salesSymbol;

	private String packageCode;

	private String tenDigitUPC;

	private String predecessorReason;

	private ShippingUnitBO shipUnit;

	private WeightBO itemWeight;

	private ExternalSystem externalSystem;

	private boolean vendorDirect;

	private boolean isBeingDiscontinued = false;

	private boolean canReturnForCredit = true;

	private boolean kitAddable = false;

	private List<ProblemBO> problemItemList;
	
	// list of product flags for the given supplier code
	private List<SupplierCdProductFlgBO> supplierCodeProductFlagList;
	
	private String predecessorPartNumber;
	
	private ShippingMethodBO selectedShippingMethod;

	private int shippedQuantity;

    private Date promisedShipDate;

	private boolean sendEngExpress;

	private boolean engExpressAllowed;

	private boolean foundInMultipleLocations;
	
	private InventoryBO chosenInventoryLocation;
	
	private String vintagePart = "N";

	/*Will have value
	 * 12 - Shipping
	 * 13 - Store Pickup
	 */
	private int deliveryMethod = 0;
	
	/*Pickup Location incase of  13- Store Pickup */
	private String pickupStore = null;
	
	/*Added for ECC Items -- required during place order*/
	private boolean isHeader;

	private int processOrderLineNumber;
	
	public boolean isHeader() {
		return isHeader;
	}

	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}

	public int getProcessOrderLineNumber() {
		return processOrderLineNumber;
	}

	public void setProcessOrderLineNumber(int processOrderLineNumber) {
		this.processOrderLineNumber = processOrderLineNumber;
	}
	
	/*Added for ECC Items */
	
	
	public ItemBO() {
	}

	public String getPredecessorPartNumber() {
		return predecessorPartNumber;
	}

	public void setPredecessorPartNumber(String predecessorPartNumber) {
		this.predecessorPartNumber = predecessorPartNumber;
	}

	public long getOrdDtlId() {
		return ordDtlId;
	}

	public void setOrdDtlId(long ordDtlId) {
		this.ordDtlId = ordDtlId;
	}

	public OrderBO getOrderHdr() {
		return orderHdr;
	}

	@XmlTransient
	public void setOrderHdr(OrderBO orderHdr) {
		this.orderHdr = orderHdr;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDisplayPartNumber() {
		return displayPartNumber;
	}

	public void setDisplayPartNumber(String displayPartNumber) {
		this.displayPartNumber = displayPartNumber;
	}

	public String getAaiaBrand() {
		return aaiaBrand;
	}

	public void setAaiaBrand(String aaiaBrand) {
		this.aaiaBrand = aaiaBrand;
	}

	public String getScacCode() {
		return scacCode;
	}

	public void setScacCode(String scacCode) {
		this.scacCode = scacCode;
	}
	
	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getShippingMethodCode() {
		return shippingMethodCode;
	}

	public void setShippingMethodCode(String shippingMethodCode) {
		this.shippingMethodCode = shippingMethodCode;
	}

	public String getTransportationMethodCode() {
		return transportationMethodCode;
	}

	public void setTransportationMethodCode(String transportationMethodCode) {
		this.transportationMethodCode = transportationMethodCode;
	}

	@XmlJavaTypeAdapter( BooleanAdapter.class )
	public Boolean isBackorder() {
		return getBackorderPolicy() == BackOrderPolicy.SHIP_AND_BACKORDER ||
		       getBackorderPolicy() == BackOrderPolicy.SHIP_AND_CANCEL;
	}

	public boolean isKit() {
		return kit;
	}

	public void setKit(boolean kit) {
		this.kit = kit;
	}

	public PriceBO getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(PriceBO itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLineStatusCd() {
		return lineStatusCd;
	}

	public void setLineStatusCd(LineStatus lineStatusCd) {
		this.lineStatusCd = lineStatusCd.name();
	}

	public LineStatus getLineStatusRsnCd() {
		return LineStatus.valueOf(lineStatusRsnCd);
	}

	public void setLineStatusRsnCd(String lineStatusRsnCd) {
		this.lineStatusRsnCd = lineStatusRsnCd;
	}

	public List<OrderFulfillmentBO> getOrderFulfillmentList() {
		if (null == this.orderFulfillmentList) {
			return new ArrayList<OrderFulfillmentBO>();
		}
		return orderFulfillmentList;
	}

	public void setOrderFulfillmentList(
			List<OrderFulfillmentBO> orderFulfillmentList) {
		this.orderFulfillmentList = orderFulfillmentList;
		
		if (orderFulfillmentList != null) {
		    for (OrderFulfillmentBO anOrderFulfillment : orderFulfillmentList ) {
		        anOrderFulfillment.setItem(this);
		    }
		}
	}

	public List<InventoryBO> getInventory() {
		return inventory;
	}
	
	public void setInventory(List<InventoryBO> inventory) {
		this.inventory = inventory;
		if (inventory != null) {
			for (InventoryBO anInventory : inventory ) {
                anInventory.setItem(this);
            }
        }
	}
	
	public List<InventoryBO> getAdditionalInventory() {
		return additionalInventory;
	}

	public void setAdditionalInventory(List<InventoryBO> additionalInventory) {
		this.additionalInventory = additionalInventory;
		if (additionalInventory != null) {
			for (InventoryBO anAdditionalInventory : additionalInventory ) {
                anAdditionalInventory.setItem(this);
            }
        }
	}

	public InventoryBO getRequestedInventory() {
		return requestedInventory;
	}

	public void setRequestedInventory(InventoryBO requestedInventory) {
		this.requestedInventory = requestedInventory;
		if (requestedInventory != null) {
			requestedInventory.setItem(this);
        }
	}

	public void addInventory(InventoryBO anInventory) {
	    if (anInventory == null) {
	        return;
	    }
	    if (inventory == null) {
	        inventory = new ArrayList<InventoryBO>();
	    }
	    anInventory.setItem(this);
	    inventory.add(anInventory);
	}
	
	/**
	 * go through the list of inventories in this items inventory list and return
	 * the one that is selected.  It could be that multiple are set to selected -
	 * at this juncture, we are returning the first one although one could argue 
	 * that's an error condition.
	 * @return THE FIRST InventoryBO that is set to selected. 
	 */
	public InventoryBO getSelectedInventory() {

	    List<InventoryBO> inventoryList = getInventory();
	    if (inventoryList == null) {
	        return null;
	    }
	    
	    InventoryBO inventoryToUse = null;
	    for (InventoryBO anInventory : inventoryList) {
	        if (anInventory.isSelectedLocation()) {
	            inventoryToUse = anInventory;
	            break;
	        }
	    }   
	    return inventoryToUse;
	}
	
	/**
	 * returns highest quantity amongst all inventories available. 
	 * @param inventory
	 * @return  - highest quantity amongst all inventories available 
	 */
	public InventoryBO getMaximumAvailableQty() {

	    InventoryBO maxInventory = null;
	    if (this.inventory == null) {
	        return null;
	    }

		for(InventoryBO anInventory : this.inventory){
			// go through inventory list
			int availableQty = anInventory.getAvailableQty();
			if (maxInventory == null) {
			    maxInventory = anInventory;
			    continue;
			}
			if(availableQty > maxInventory.getAvailableQty()) {
				maxInventory = anInventory;
			} else if (availableQty == maxInventory.getAvailableQty() && anInventory.isMainDC()) {
			    // these are the same but the new one is the home. Use that
			    maxInventory = anInventory;
			}
		}	
		return maxInventory;
	}

	/**
     * go through the list of inventories in this items inventory list and return
     * the one that is identified as the main DC.  There should never be more than 
     * one set to main but this will return the first one it finds.
     * @return InventoryBO that is set to main. Null if there isn't one
     */
    public InventoryBO getMainDCInventory() {

        List<InventoryBO> inventoryList = getInventory();
        if (inventoryList == null) {
            return null;
        }
        
        InventoryBO inventoryToUse = null;
        for (InventoryBO anInventory : inventoryList) {
            if (anInventory.isMainDC()) {
                inventoryToUse = anInventory;
                break;
            }
        }   
        return inventoryToUse;
    }

    @XmlElement
	public Integer getOrderedQty() {
		return getSelectedInventory()!=null?getSelectedInventory().getAssignedQty():0;
	}

	public QuantityBO getItemQty() {
		return itemQty;
	}

	public void setItemQty(QuantityBO itemQty) {
		this.itemQty = itemQty;
		if (itemQty != null) {
		    itemQty.setItem(this);
		}
	}

	/**
	 * There are numerous fields that appear to make up a full part description.
	 * Use this method when wanting all relevant ones put together.
	 * @return Currently description + brand state short desc + sales symbol where populated
	 */
	@XmlElement
	public String getFullDescription() {
	    
	    StringBuffer fullDescription = new StringBuffer("");
	    
	    if (GenericValidator.isBlankOrNull(getDescription()) == false) {
	        fullDescription.append(getDescription().trim());
	    }
	    
	    if ( GenericValidator.isBlankOrNull(getBrStShortDesc()) == false) {
	        fullDescription.append(" - ");
	        fullDescription.append(getBrStShortDesc().trim());
	    }
	    
	    if ( GenericValidator.isBlankOrNull(getSalesSymbol()) == false) {
	        fullDescription.append(" - ");
            fullDescription.append(getSalesSymbol().trim());
        }
        
      return fullDescription.toString();
	    
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductFlag() {
		return productFlag;
	}

	public void setProductFlag(String productFlag) {
		this.productFlag = productFlag;
	}
	
	public String getFmBrandCode() {
		return fmBrandCode;
	}

	public void setFmBrandCode(String fmBrandCode) {
		this.fmBrandCode = fmBrandCode;
	}

	public String getBrandState() {
		return brandState;
	}

	public void setBrandState(String brandState) {
		this.brandState = brandState;
	}

	public String getOrderEntryComment() {
		return orderEntryComment;
	}

	public void setOrderEntryComment(String orderEntryComment) {
		this.orderEntryComment = orderEntryComment;
	}

	public String getBrStShortDesc() {
		return brStShortDesc;
	}

	public void setBrStShortDesc(String brStShortDesc) {
		this.brStShortDesc = brStShortDesc;
	}

	public String getSalesSymbol() {
		return salesSymbol;
	}

	public void setSalesSymbol(String salesSymbol) {
		this.salesSymbol = salesSymbol;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getTenDigitUPC() {
		return tenDigitUPC;
	}

	public void setTenDigitUPC(String tenDigitUPC) {
		this.tenDigitUPC = tenDigitUPC;
	}

	public String getPredecessorReason() {
		return predecessorReason;
	}

	public void setPredecessorReason(String predecessorReason) {
		this.predecessorReason = predecessorReason;
	}

	public ShippingUnitBO getShipUnit() {
		return shipUnit;
	}

	public void setShipUnit(ShippingUnitBO shipUnit) {
		this.shipUnit = shipUnit;
	}

	public WeightBO getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(WeightBO itemWeight) {
		this.itemWeight = itemWeight;
	}

	public ExternalSystem getExternalSystem() {
		return externalSystem;
	}

	public void setExternalSystem(ExternalSystem externalSystem) {
		this.externalSystem = externalSystem;
	}

	public boolean isVendorDirect() {
		return vendorDirect;
	}

	public void setVendorDirect(boolean vendorDirect) {
		this.vendorDirect = vendorDirect;
	}

	public boolean isBeingDiscontinued() {
		return isBeingDiscontinued;
	}

	public void setBeingDiscontinued(boolean isBeingDiscontinued) {
		this.isBeingDiscontinued = isBeingDiscontinued;
	}

	public boolean isCanReturnForCredit() {
		return canReturnForCredit;
	}

	public void setCanReturnForCredit(boolean canReturnForCredit) {
		this.canReturnForCredit = canReturnForCredit;
	}

	public boolean isKitAddable() {
		return kitAddable;
	}

	public void setKitAddable(boolean kitAddable) {
		this.kitAddable = kitAddable;
	}

	public BackOrderPolicy getBackorderPolicy() {
		return BackOrderPolicy.getFromDBCode(backorderPolicyDbCode);
	}

	public void setBackorderPolicy(BackOrderPolicy policy) {
	    if (policy != null) {
	        backorderPolicyDbCode = policy.getDbCode();
	    } else {
	        backorderPolicyDbCode = null;
	    }
    }
	
	public List<ProblemBO> getProblemItemList() {
		return problemItemList;
	}

	public void setProblemItemList(List<ProblemBO> problemItemList) {
	    this.problemItemList = problemItemList;
	}

	/**
	 * This returns the first non processable problem item in the problem item list if there are any,
	 * then the first processable problem or null otherwise.
	 * This method is not depracated per se but is a utility method to be used sparingly - mostly in 
	 * situations where you only need one problem, you know there is only one problem or you only 
	 * care about the first problem in the list following the above algorithm. Examples might be 
	 * Test Case assertions or the web service layer.  
	 * @return
	 */
    @XmlElement
    @Deprecated
	public ProblemBO getProblemItem() {
	 
	    if (problemItemList == null) {
	        return null;
	    }
	    ProblemBO returnProblem = null;
	    for (ProblemBO aProblem : problemItemList) {
	        if (returnProblem == null) {
	            returnProblem = aProblem;
	        }
	        if (aProblem.isAllowedToProcess() == false) {
	            returnProblem = aProblem;
	            break;
	        }
	    }
	     
	    return returnProblem;
	}
 
	public void addProblemItem(ProblemBO problemItem) {
	    if (problemItemList == null) {
	        problemItemList = new ArrayList<ProblemBO>();
	    }
	    problemItemList.add(problemItem);
	}
	
	/**
	 * If any of the problems in this item are not allowed to process, this item
	 * is not allowed to process
	 * @return
	 */
	public boolean canProcessItem() {
	    if (problemItemList == null) {
	        return true;
	    }
	    
	    for (ProblemBO aProblem : problemItemList) {
	        if (aProblem.isAllowedToProcess() == false) {
	            return false;
	        }
	    }
	    
	    // made it through
	    return true;
	}
	
	public boolean isMigrated() {
	    if (problemItemList == null) {
	        return false;
	    }
	        
	    for (ProblemBO aProblem : problemItemList) {
	        if (aProblem.getStatusCategory() == StatusCategory.PART_MIGRATED) {
	            return true;
	        }
	    }
	        
	    // made it through
	    return false;
	}

    public boolean hasProblemItem() {
        return problemItemList != null && problemItemList.size() > 0;
    }
    
	public List<SupplierCdProductFlgBO> getSupplierCodeProductFlagList() {
        return supplierCodeProductFlagList;
    }

    public void setSupplierCodeProductFlagList(
            List<SupplierCdProductFlgBO> supplierCodeProductFlagList) {
        this.supplierCodeProductFlagList = supplierCodeProductFlagList;
    }

	public ShippingMethodBO getSelectedShippingMethod() {
		return selectedShippingMethod;
	}

	public void setSelectedShippingMethod(ShippingMethodBO selectedShippingMethod) {
		this.selectedShippingMethod = selectedShippingMethod;
	}

	public int getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(int shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }
  
    public Date getPromisedShipDate() {
        return promisedShipDate;
    }

    public void setPromisedShipDate(Date promisedShipDate) {
        this.promisedShipDate = promisedShipDate;
    }    
	public boolean isSendEngExpress() {
		return sendEngExpress;
	}

	public void setSendEngExpress(boolean sendEngExpress) {
		this.sendEngExpress = sendEngExpress;
	}

    public boolean isEngExpressAllowed() {
        return engExpressAllowed;
    }

    public void setEngExpressAllowed(boolean engExpressAllowed) {
        this.engExpressAllowed = engExpressAllowed;
    }
    @XmlElement
    public String getPromisedShipDateIsoDate() {
    	if(promisedShipDate==null){
    		return "";
    	}
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		return formatter.format(promisedShipDate);    	
    }
    @XmlElement
    public String getPromisedShipDateIsoDateTime() {
    	if(promisedShipDate==null){
    		return "";
    	}
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat timeZoneFormatter = new SimpleDateFormat("Z");
		StringBuffer sb = new StringBuffer(formatter.format(promisedShipDate));
		String tz = timeZoneFormatter.format(promisedShipDate);
		sb.append(tz.substring(0,3)).append(":00");		
		return sb.toString();
	}

    public void populateInitialCopy(ItemBO inputItem) {
        initialCopy(inputItem, this);
    }

	public boolean isCarterPart() {
		
		if (hasProblemItem()) {
			for (ProblemBO aProblem : problemItemList) {				   
				if( aProblem.getMessageKey().equalsIgnoreCase("CARTER_PARTS"))  	                  
					  return true;
			}      
		}	   		   
		return false;
	}
    
    public boolean isFoundInMultipleLocations() {
		return foundInMultipleLocations;
	}

	public void setFoundInMultipleLocations(boolean foundInMultipleLocations) {
		this.foundInMultipleLocations = foundInMultipleLocations;
	}

	public InventoryBO getChosenInventoryLocation() {
		return chosenInventoryLocation;
	}

	public void setChosenInventoryLocation(InventoryBO chosenInventoryLocation) {
		this.chosenInventoryLocation = chosenInventoryLocation;
	}

	
	
	public String getVintagePart() {
		return vintagePart;
	}

	public void setVintagePart(String vintagePart) {
		this.vintagePart = vintagePart;
	}
	
	public boolean isVintagePart(){
		if(vintagePart != null && vintagePart.equalsIgnoreCase("Y"))
			return true;
		else
			return false;
	}

	
	public int getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(int deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	public String getPickupStore() {
		return pickupStore;
	}

	public void setPickupStore(String pickupStore) {
		this.pickupStore = pickupStore;
	}

	/**
     * Utility to copy certain fields on an item that are required for the initial
     * check orderable call (and possibly other calls later). 
     * @return a new ItemBO with relevant input fields retained
     */
    public ItemBO createInitialCopy() {
        
        ItemBO newItem;
        
        if (this instanceof PartBO) {
            newItem = new PartBO();
        } else {
            newItem = new ItemBO();
        }
        
        initialCopy(this, newItem);
        return newItem;
    }
   
    protected void initialCopy(ItemBO inputItem, ItemBO newItem) {
       
    	newItem.setPartNumber(inputItem.getPartNumber());
    	newItem.setDisplayPartNumber(inputItem.getDisplayPartNumber());
    	newItem.setLineNumber(inputItem.getLineNumber());
    	newItem.setProductFlag(inputItem.getProductFlag());
    	newItem.setBrandState(inputItem.getBrandState());
    	newItem.setFmBrandCode(inputItem.getFmBrandCode());
    	newItem.setComment(inputItem.getComment()); 
    	newItem.setAaiaBrand(inputItem.getAaiaBrand());
    	newItem.setScacCode(inputItem.getScacCode());
    	newItem.setCarrierCode(inputItem.getCarrierCode());
    	newItem.setVintagePart(inputItem.getVintagePart());
    	newItem.setShippingMethodCode(inputItem.getShippingMethodCode());
    	newItem.setTransportationMethodCode(inputItem.getTransportationMethodCode());
    	newItem.setSupplierCodeProductFlagList(inputItem.getSupplierCodeProductFlagList());
    	QuantityBO qty = inputItem.getItemQty();
    	
    	if (qty != null) {
    		QuantityBO newQty = new QuantityBO();
    		newQty.setRequestedQuantity(qty.getRequestedQuantity());
    		newQty.setQtyUomCd(inputItem.getItemQty().getQtyUomCd());
    		newItem.setItemQty(newQty);
       	}
    	newItem.setSelectedShippingMethod(inputItem.getSelectedShippingMethod());
    	if (null != inputItem.getBackorderPolicy()) {
    		newItem.setBackorderPolicy(inputItem.getBackorderPolicy());
       	}
    	newItem.setChosenInventoryLocation(inputItem.getChosenInventoryLocation());
    	newItem.setRequestedInventory(inputItem.getRequestedInventory());
    }

    /**
     * "deep" copy of this item. 
     * @return
     * @throws Exception
     */
    public ItemBO cloneBean() throws Exception {
       ItemBO newItem = (ItemBO) BeanUtils.cloneBean(this);
       newItem.setOrdDtlId(0);
       newItem.setOrderHdr(null);
       
       QuantityBO oldQuantity = getItemQty();
       QuantityBO newQuantity = (QuantityBO) BeanUtils.cloneBean(oldQuantity);
       oldQuantity.setItem(this);
       newQuantity.setQtyId(0);
       newItem.setItemQty(newQuantity);
       
       if (inventory != null) {
           List<InventoryBO> newInventoryList = new ArrayList<InventoryBO>();
           for (InventoryBO anInventory : inventory ) {
               anInventory.setItem(this);
               InventoryBO aNewInventory = (InventoryBO) BeanUtils.cloneBean(anInventory);
               aNewInventory.setInvntryLocId(0);
               aNewInventory.setItem(newItem);
               newInventoryList.add(aNewInventory);
           }
           newItem.setInventory(newInventoryList);
       }
       
       return newItem;
   }
   
   @Override
	public String toString() {
	    
	    StringBuffer problems = new StringBuffer("[");
	    if (problemItemList != null) {
	        for (ProblemBO aProblem : problemItemList) {
	            problems.append(aProblem.getMessageKey());
	            problems.append(", ");
	        }
	    }
	    problems.append("]");
	    
		return "ItemBO [ordDtlId=" + ordDtlId + ", lineNumber=" + lineNumber 
				+ ", partNumber=" + partNumber + ", displayPartNumber=" + displayPartNumber 
				+ ", aaiaBrand=" + aaiaBrand + ", scacCode=" + scacCode 
				+ ", carrierCode=" + carrierCode + ", shippingMethodCode=" + shippingMethodCode
				+ ", transportationMethodCode=" + transportationMethodCode
				+ ", kit=" + kit + ", itemPrice="
				+ itemPrice + ", comment=" + comment + ", lineStatusCd="
				+ lineStatusCd + ", lineStatusRsnCd=" + lineStatusRsnCd
				+ ", orderFulfillmentList=" + orderFulfillmentList
				+ ", inventory=" + inventory + ", additionalInventory=" + additionalInventory 
				+ ", itemQty=" + itemQty
				+ ", description=" + description + ", productFlag="
				+ productFlag + ", brandState=" + brandState
				+ ", orderEntryComment=" + orderEntryComment
				+ ", brStShortDesc=" + brStShortDesc + ", salesSymbol="
				+ salesSymbol + ", packageCode=" + packageCode
				+ ", tenDigitUPC=" + tenDigitUPC + ", predecessorReason="
				+ predecessorReason + ", shipUnit=" + shipUnit
				+ ", itemWeight=" + itemWeight + ", externalSystem="
				+ externalSystem + ", vendorDirect=" + vendorDirect
				+ ", promisedShipDate=" + promisedShipDate
				+ ", isBeingDiscontinued=" + isBeingDiscontinued
				+ ", canReturnForCredit=" + canReturnForCredit
				+ ", kitAddable=" + kitAddable + ", backorderPolicy="
                + getBackorderPolicy() + ", problemItemList=" + problems
				+ ", predecessorPartNumber=" + predecessorPartNumber 
				+ ", sendEngExpress=" + sendEngExpress 
				+ ", engExpressAllowed=" + engExpressAllowed 
				+ ", shippedQuantity=" + shippedQuantity
				+ ", foundInMultipleLocations=" + foundInMultipleLocations
				+ ", chosenInventoryLocation=" + chosenInventoryLocation + "]";
	}

}

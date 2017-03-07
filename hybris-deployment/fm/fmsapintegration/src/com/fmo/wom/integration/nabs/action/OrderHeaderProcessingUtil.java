package com.fmo.wom.integration.nabs.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.dao.AuditInterceptor;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.ComponentBO;
import com.fmo.wom.domain.CreditCardBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.NabsShippingCodeBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.OversizeBO;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.KitType;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.nabs.OrderCommentBO;
import com.fmo.wom.domain.nabs.OrderCommentPK;
import com.fmo.wom.domain.nabs.OrderCreditCardBO;
import com.fmo.wom.domain.nabs.OrderDetailBO;
import com.fmo.wom.domain.nabs.OrderDetailChangeBO;
import com.fmo.wom.domain.nabs.OrderDetailChangePK;
import com.fmo.wom.domain.nabs.OrderDetailCommentBO;
import com.fmo.wom.domain.nabs.OrderDetailCommentPK;
import com.fmo.wom.domain.nabs.OrderDetailPK;
import com.fmo.wom.domain.nabs.OrderHeaderBO;
import com.fmo.wom.domain.nabs.OrderHeaderPK;
import com.fmo.wom.domain.nabs.OrderKitComponentBO;
import com.fmo.wom.domain.nabs.OrderKitComponentChangeBO;
import com.fmo.wom.domain.nabs.OrderKitComponentChangePK;
import com.fmo.wom.domain.nabs.OrderKitComponentPK;
import com.fmo.wom.integration.dao.nabs.OrderHeaderDAO;
import com.fmo.wom.integration.dao.nabs.OrderHeaderDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.ProcessOrderAction.CheckoutCommand;
import com.fmo.wom.integration.util.ItemListUtil;

public class OrderHeaderProcessingUtil {
	Logger logger = Logger.getLogger(OrderHeaderProcessingUtil.class);
	private OrderHeaderDAO orderHeaderDAO;
	
	public OrderHeaderProcessingUtil() {
		super();
		this.orderHeaderDAO = new OrderHeaderDAOImpl();
	}

	/**
     * Persist the order in the Order header and sub tables for initial assignment call.
     * First searches for an existing entry - if it finds it, it will update this back
     * for initial assignment.  If not, construct new and persist.
     * @param order
     */
    // @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void storeOrderInitialAssignment(OrderBO order) throws WOMTransactionException{
    	OrderHeaderDAOImpl daoImpl = (OrderHeaderDAOImpl)orderHeaderDAO;
    	EntityTransaction transaction = null;
        try {
        	transaction = daoImpl.getEntityManager().getTransaction(); 
        	if(transaction != null && (!transaction.isActive())){
        		transaction.begin();
    			OrderHeaderBO orderHeader = orderHeaderDAO.findByMasterOrderNumber(order.getMstrOrdNbr());
    			logger.info("storeOrderInitialAssignment() OrderHeader from DB "+orderHeader);
    			if (orderHeader == null) {
    				logger.info("storeOrderInitialAssignment() OrderHeader==null ");
    			    // not found (which should be the norm)
    			    orderHeader = new OrderHeaderBO();
    			    OrderHeaderPK headerPK = new OrderHeaderPK();
    			    headerPK.setMasterOrderNumber(order.getMstrOrdNbr());
    			    orderHeader.setId(headerPK);
    			}
    			orderHeader = populateOrderHeader(orderHeader, order, CheckoutCommand.INITIAL_ASSIGN );
    			logger.info("storeOrderInitialAssignment() ... persisting  .."+orderHeader);
    			orderHeaderDAO.merge(orderHeader);
    			daoImpl.getEntityManager().flush();
    			transaction.commit();
        	}
		} catch (Exception commitException) {
			if(transaction !=null && transaction.isActive()){
				try {
					transaction.rollback();
				} catch (Exception rollBackException) {
					throw new WOMTransactionException(rollBackException);
				}
			}
			throw new WOMTransactionException(commitException);
		}
    }
	
	public void updateOrderForBook(OrderBO order)  throws WOMTransactionException{
    	OrderHeaderDAOImpl daoImpl = (OrderHeaderDAOImpl)orderHeaderDAO;
    	EntityTransaction transaction = null;
    	try{
    		transaction = daoImpl.getEntityManager().getTransaction(); 
    		if(transaction != null && (!transaction.isActive())){
	    		transaction.begin();
				OrderHeaderBO orderHeader = orderHeaderDAO.findByMasterOrderNumber(order.getMstrOrdNbr());
		        logger.info("updateOrderForBook() orderHeader from DB "+orderHeader);
		        orderHeader = populateOrderHeader(orderHeader, order, CheckoutCommand.BOOK );
		        logger.info("updateOrderForBook() persisting ... OrderHeader .."+orderHeader);
		        orderHeaderDAO.merge(orderHeader);
		        daoImpl.getEntityManager().flush();
		        transaction.commit();
    		}
		} catch (Exception commitException) {
			if(transaction !=null && transaction.isActive()){
				try {
					transaction.rollback();
				} catch (Exception rollBackException) {
					throw new WOMTransactionException(rollBackException);
				}
			}
			throw new WOMTransactionException(commitException);
		}
    }
	
	 //@Transactional(propagation=Propagation.REQUIRES_NEW)
	 public void updateOrderForCancel(OrderBO order, String cancelReasonCode) throws WOMTransactionException {
     	OrderHeaderDAOImpl daoImpl = (OrderHeaderDAOImpl)orderHeaderDAO;
     	EntityTransaction transaction = null;
	    try {
	    	transaction = daoImpl.getEntityManager().getTransaction();
	    	if(transaction != null && (!transaction.isActive())){
		    	transaction.begin();
		    	OrderHeaderBO orderHeader = orderHeaderDAO.findByMasterOrderNumber(order.getMstrOrdNbr());
				logger.info("updateOrderForCancel() orderHeader from DB "+ orderHeader);
				if (orderHeader == null) {
					if(transaction !=null && transaction.isActive()){
						transaction.commit();
					}
					return;
				}
				orderHeader = populateOrderHeader(orderHeader, order,CheckoutCommand.CANCEL_ASSIGN);
				orderHeader.setCancelReasonCode(cancelReasonCode);
				orderHeaderDAO.merge(orderHeader);
				daoImpl.getEntityManager().flush();
				transaction.commit();
	    	}
		} catch (Exception commitException) {
			if(transaction !=null && transaction.isActive()){
				try {
					transaction.rollback();
				} catch (Exception rollBackException) {
					throw new WOMTransactionException(rollBackException);
				}
			}
			throw new WOMTransactionException(commitException);
		}
	 }
	
	
	
	/**
     * Update an existing order to make a change.  Finds the order header via the master order
     * number and updates all aspects in place with the CHANGE check out command attributes
     * @param order
     */
   // @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void updateOrderForProcessChanges(OrderBO order)  throws WOMTransactionException {
    	OrderHeaderDAOImpl daoImpl = (OrderHeaderDAOImpl)orderHeaderDAO;
    	EntityTransaction transaction = null;
        try {
        	transaction = daoImpl.getEntityManager().getTransaction();
        	if(transaction != null && (!transaction.isActive())){
	        	transaction.begin();
				OrderHeaderBO orderHeader = orderHeaderDAO.findByMasterOrderNumber(order.getMstrOrdNbr());
				logger.info("updateOrderForProcessChanges() orderHeader from DB "+orderHeader);
				orderHeader = populateOrderHeader(orderHeader, order, CheckoutCommand.PROCESS_CHANGES );
				logger.info("updateOrderForProcessChanges() persisting "+orderHeader);
				orderHeaderDAO.merge(orderHeader);
				daoImpl.getEntityManager().flush();
				transaction.commit();
        	}
		}catch (Exception commitException) {
			if(transaction !=null && transaction.isActive()){
				try {
					transaction.rollback();
				} catch (Exception rollBackException) {
					throw new WOMTransactionException(rollBackException);
				}
			}
			throw new WOMTransactionException(commitException);
		}
    }
    
    private OrderHeaderBO populateOrderHeader(OrderHeaderBO orderHeader, OrderBO order, CheckoutCommand checkoutCommand ) {
    	logger.info("populateOrderHeader() checkoutCommand "+checkoutCommand);
        orderHeader.setInitByCustomer(AuditInterceptor.getUserAccount().getAccountCode());
        
        String billToAccountCode = order.getBillToAcct().getAccountCode();
        orderHeader.setUserGuid(billToAccountCode);
        orderHeader.setBillToCustomer(billToAccountCode);
        if (order.getShipToAcct() != null) {
            String shipToAccountCode = order.getShipToAcct().getAccountCode();
            orderHeader.setShipToCustomer(shipToAccountCode);
        }
        
        if(order.isManualShipTo()) {
        	orderHeader.setShipToCustomer(NabsConstants.NABS_MANUAL_SHIP_TO_CODE);
        }
        
        orderHeader.setWomStatusCode(checkoutCommand.getRequestCode());
        orderHeader.setWomStatusCodeTimestamp(new Date());
        orderHeader.setBackOrderType(order.getBackOrderPolicy().getCode());
        orderHeader.setPoNumber(order.getCustPoNbr());
        
        orderHeader.setEmergencyFlag(NabsConstants.NABS_NO);
        if (order.getOrderType() == OrderType.EMERGENCY) {
            orderHeader.setEmergencyFlag(NabsConstants.NABS_YES);
        }
        
        if(order.getFutureDate()!=null){
	        orderHeader.setWantDate(NabsConstants.futureDateFormatter.format(order.getFutureDate()));
        }   
        // this purposely not set 
        orderHeader.setHostStatusCode(NabsConstants.NABS_BLANK);
       
        
        AddressBO shipToAddress = null;
        
        if (order.getShipToAcct() != null) {
            shipToAddress = order.getShipToAcct().getAddress(); 
            orderHeader.setShipToName(order.getShipToAcct().getAccountName());
        }
        
        if (order.isManualShipTo()) {
            shipToAddress = order.getManualShipToAddress().getAddress();
            orderHeader.setShipToName(order.getManualShipToAddress().getName());
        }
        
        orderHeader.setShipToAddress1(shipToAddress.getAddrLine1());
        orderHeader.setShipToAddress2(shipToAddress.getAddrLine2());
        orderHeader.setShipToCity(shipToAddress.getCity());
        orderHeader.setShipToCountryISO(shipToAddress.getCountry().getIsoCountryCode());
        
        String zipOrPostalCode = shipToAddress.getZipOrPostalCode();
        if (StringUtil.isAValidCanadianPostalCode(zipOrPostalCode) == true) {
            // this is a valid canadian postal code.  Put a space between the Forward Segmentation Area and the Local Delivery Unit sections so
            // NABS reads it as a postal code and not a zip.  Hopefully this was done way up front, but this is a failsafe
            zipOrPostalCode = StringUtil.getFormattedCanadianPostalCode(zipOrPostalCode);
        }
        orderHeader.setShipToPostalCode(zipOrPostalCode);
        orderHeader.setShipToState(shipToAddress.getStateOrProv());
        
        orderHeader.setOrderedByName(order.getOrderedBy());
        orderHeader.setFreeFreightFlag(order.receivesFreeFreight() ? NabsConstants.NABS_YES : NabsConstants.NABS_NO );
        populateOrderDetails(orderHeader, order, checkoutCommand);
            
        // set comments
        populateOrderComments (orderHeader, order);
        
        // set credit card info
       
        return orderHeader;
    }
    
    private void populateOrderComments(OrderHeaderBO orderHeader, OrderBO order) {

        List<OrderCommentBO> orderComments = orderHeader.getOrderComments();
        if (orderComments == null) {
            orderComments = new ArrayList<OrderCommentBO>();
            orderHeader.setOrderComments(orderComments);
            
            List<String> commentsList = new ArrayList<String>();
            if (order.getComment1() != null) {
                commentsList.add(order.getComment1()); 
            }
            if (order.getComment2() != null) {
                commentsList.add(order.getComment2()); 
            }
            if (order.getComment3() != null) {
                commentsList.add(order.getComment3()); 
            }
            
            int index = 1;
            for (String aComment : commentsList) {
                OrderCommentBO anOrderComment = new OrderCommentBO();
                OrderCommentPK aPk = new OrderCommentPK();
                aPk.setCommentSequenceNumber(index);
                aPk.setOrderHeaderFK(orderHeader);
                anOrderComment.setId(aPk);
                anOrderComment.setComment(aComment);
                orderComments.add(anOrderComment);
                index++;
            }
            
        }
    }
    
    
    private void populateCreditCard(OrderHeaderBO orderHeader, OrderBO order) {

        CreditCardBO orderCreditCardInfo = order.getBillToAcct().getCreditCard();
        
        if (orderCreditCardInfo == null) {
            orderHeader.setOrderCreditCard(null);
            return;
        }
        
        OrderCreditCardBO creditCardInfo = orderHeader.getOrderCreditCard();
        if (creditCardInfo == null) {
            creditCardInfo = new OrderCreditCardBO();
            creditCardInfo.setOrderHeader(orderHeader);
            orderHeader.setOrderCreditCard(creditCardInfo);
        }
        
        creditCardInfo.setAccountNumber(orderCreditCardInfo.getCcNumber());
        creditCardInfo.setExpireMMYY(orderCreditCardInfo.getCcExpiration());
        creditCardInfo.setCreditCardTypeCode(orderCreditCardInfo.getCcType());
        creditCardInfo.setAccountNumber(orderCreditCardInfo.getCcNumber());
        
    }
    
    /** 
     * Create order detail records for insertion into the NABS check out tables.
     * This will only return detail records for parts in the given order declared
     * as NABS parts
     * @param order the order to process
     * @return item information translated onto the order detail for checkout
     */
     private void populateOrderDetails(OrderHeaderBO orderHeader, OrderBO order, CheckoutCommand checkoutCommand) {
    	 logger.info("populateOrderDetails() checkoutCommand "+checkoutCommand);
         List<ItemBO> nabsParts = ItemListUtil.getNabsItemList(order.getItemList());
        
         List<OrderDetailBO> orderDetailList = orderHeader.getOrderDetails();
         logger.info("populateOrderDetails() orderDetailList from db  "+orderDetailList);
         if (orderDetailList == null) {
        	 logger.info("populateOrderDetails() orderDetailList == null "+checkoutCommand);
             orderDetailList = new ArrayList<OrderDetailBO>();
             orderHeader.setOrderDetails(orderDetailList);
         }
         
         for (ItemBO aNabsPart : nabsParts) {
             
             if (aNabsPart.canProcessItem()) {
                 populateOrderDetail(orderHeader, aNabsPart, checkoutCommand);
             }
         }
     }

     private void populateOrderDetail(OrderHeaderBO orderHeader, ItemBO aNabsPart, CheckoutCommand checkoutCommand) {
    	 
         OrderDetailBO orderDetail = findOrderDetailByLineSequence(orderHeader.getOrderDetails(), aNabsPart.getLineNumber());
         logger.info("populateOrderDetail(orderHeader,aNabsPart,checkoutCommand) for nabs part "+aNabsPart.getPartNumber()+" checkoutCommand "+checkoutCommand);
         if (orderDetail == null) {
        	 logger.info("populateOrderDetail orderDetail == null "+checkoutCommand);
             // wasn't found
             OrderDetailPK aPk = new OrderDetailPK();
             aPk.setParentOrderHeaderFK(orderHeader);
             aPk.setLineSequenceNumber(aNabsPart.getLineNumber());
             orderDetail = new OrderDetailBO();
             orderDetail.setId(aPk);
             orderHeader.getOrderDetails().add(orderDetail);
         }
         
         populateOrderDetail(orderDetail, aNabsPart, checkoutCommand);
     }
     
     private OrderDetailBO findOrderDetailByLineSequence(List<OrderDetailBO> orderDetails, int lineNumber) {
         OrderDetailBO matchingDetail = null;
         for (OrderDetailBO anOrderDetail : orderDetails) {
             if (anOrderDetail.getId().getLineSequenceNumber() == lineNumber) {
                 matchingDetail = anOrderDetail;
                 break;
             }
         }
         return matchingDetail;
     }
     
     private void populateOrderDetail(OrderDetailBO orderDetail, ItemBO aNabsPart, CheckoutCommand checkoutCommand) {
    	 logger.info("populateOrderDetail(orderDetail,aNabsPart,checkoutCommand) for nabs part "+aNabsPart.getPartNumber()+" checkoutCommand "+checkoutCommand);
         orderDetail.setSquashedPartNumber(aNabsPart.getPartNumber());
         orderDetail.setProductFlag(aNabsPart.getProductFlag());
         orderDetail.setBrandState(aNabsPart.getBrandState());
      
         orderDetail.setOrderQuantity(aNabsPart.getItemQty().getRequestedQuantity());
         
         BackOrderPolicy backOrder = BackOrderPolicy.SHIP_AND_CANCEL;
         if (aNabsPart.getBackorderPolicy() != null) {
             backOrder = aNabsPart.getBackorderPolicy();
         }
         
         orderDetail.setBackorderType(backOrder.getCode());
         
         populateShippingInformation(orderDetail, aNabsPart);
         
         // bit of a wag here.
         orderDetail.setKitComponentNetDeletes(0);
         
         populateOrderDetailChange(orderDetail, aNabsPart, checkoutCommand);
         
         
         // kit stuff
         orderDetail.setKitFlag(NabsConstants.NABS_NO);
         orderDetail.setEngineExpressFlag(NabsConstants.NABS_NO);
        
         if (aNabsPart.isKit()) {
             
             KitBO aKit = (KitBO) aNabsPart;
             orderDetail.setKitFlag(NabsConstants.NABS_YES);
             if (aKit.isEngExpressAllowed()) {
                 orderDetail.setEngineExpressFlag(NabsConstants.NABS_YES);
             }
             
             populateKitComponents(orderDetail, (KitBO) aNabsPart, checkoutCommand);
         }
     }
     
     private void populateShippingInformation(OrderDetailBO orderDetail, ItemBO aNabsPart) {
    	 logger.info("populateShippingInformation(orderDetail,aNabsPart) for nabs part "+aNabsPart.getPartNumber());
         // populate the inventory information.
         InventoryBO inventoryToUse = aNabsPart.getSelectedInventory();
         
         // Set the home dc on the main attributes
         DistributionCenterBO dc = inventoryToUse.getDistributionCenter();
         NabsShippingCodeBO shippingCode = inventoryToUse.getShippingCode().getNabsShippingCode();
         
         OrderType type = aNabsPart.getOrderHdr().getOrderType();
         String shippingCodeToUse = shippingCode.getStockShippingCode();
         if (OrderType.EMERGENCY == type) {
             shippingCodeToUse = shippingCode.getEmergencyShippingCode();
         }
         
         orderDetail.setShipViaCode(shippingCodeToUse); 
        
         // change 1 - added thsi line
		//  orderDetail.setAssignQuantity(inventoryToUse.getAssignedQty());
     }
     
     /**
      * Update/create the order detail level comment.  This method looks a bit funny, but there is only
      * one comment field on the given ItemBO, so while we are dealing with a list in the mapping, there will
      * only ever be one currently.
      * @param orderDetail
      * @param aNabsPart
      */
     private void populateOrderDetailComments(OrderDetailBO orderDetail, ItemBO aNabsPart) {

         List<OrderDetailCommentBO> orderDetailComments = orderDetail.getOrderDetailComments();
         if (orderDetailComments == null) {
             orderDetailComments = new ArrayList<OrderDetailCommentBO>();
             orderDetail.setOrderDetailComments(orderDetailComments);
         }
             
         if (GenericValidator.isBlankOrNull(aNabsPart.getComment()) == false) {
             
             if (orderDetailComments.size() == 0) {
                 // nothing there yet
                 OrderDetailCommentBO anOrderDetailComment = new OrderDetailCommentBO();
                 OrderDetailCommentPK aPk = new OrderDetailCommentPK();
                 aPk.setCommentSequenceNumber(1);
                 aPk.setOrderDetailFK(orderDetail);
                 anOrderDetailComment.setId(aPk);
                 orderDetailComments.add(anOrderDetailComment);
             }
             
             OrderDetailCommentBO theOrderDetailComment = orderDetailComments.get(0);
             theOrderDetailComment.setComment(aNabsPart.getComment());
         }
     }
     
     // master order number will eventually be gone when we map these properly as its the pk of the hdr
     private void populateOrderDetailChange(OrderDetailBO orderDetail, ItemBO aNabsPart, CheckoutCommand checkoutCommand) {
    	 logger.info("populateOrderDetailChange(orderDetail,aNabsPart,checkoutCommand) for nabs part "+aNabsPart.getPartNumber());
         OrderDetailChangeBO orderDetailChange = orderDetail.getOrderDetailChange();
         logger.info("orderDetailChange from DB "+orderDetailChange);
         if (orderDetailChange == null) {
        	// nothing there
			orderDetailChange = new OrderDetailChangeBO();
			OrderDetailChangePK aPk = new OrderDetailChangePK();
			aPk.setOrderDetailFK(orderDetail);
			orderDetailChange.setId(aPk);
			orderDetail.setOrderDetailChange(orderDetailChange);
         }
        
         if (orderDetailChange != null) {
			orderDetailChange.setAddOrRemoveIndicator(checkoutCommand.getAddRemoveIndicator());
			orderDetailChange.setOrderQuantity(aNabsPart.getItemQty().getRequestedQuantity());
			populateShippingInformation(orderDetailChange, aNabsPart);
			orderDetailChange.setChangeStatus(NabsConstants.PROCESS_LINE_CHANGE);
			// we can only assign the number of kits that are actually available regardless of what we
			// do with the components
			if (aNabsPart.isKit()) {
				KitBO aKit = (KitBO) aNabsPart;
				orderDetailChange.setAssignQuantity(aKit.getAvailableQuantityForKit());
				orderDetailChange.setChangeStatus(NabsConstants.PROCESS_COMPONENT_CHANGE);
			}
		} 
    }
     
     private void populateShippingInformation(OrderDetailChangeBO orderDetailChange, ItemBO aNabsPart) {
    	 logger.info("populateShippingInformation(orderDetailChange, aNabsPart) "+aNabsPart.getPartNumber());
         // populate the inventory information.
         InventoryBO inventoryToUse = aNabsPart.getSelectedInventory();
         
         // Set the dc on the main attributes
         DistributionCenterBO dc = inventoryToUse.getDistributionCenter();
         orderDetailChange.setShipLocationId(dc.getCode());
         orderDetailChange.setAssignQuantity(inventoryToUse.getAssignedQty());
     }
     
     private void populateKitComponents(OrderDetailBO orderDetail, KitBO aNabsKit, CheckoutCommand checkoutCommand) {
         
         if (aNabsKit.getConfiguredKitComponentCategories() == null) {
             //logger.debug("no configured components.");
             return;
         }
         
         List<OrderKitComponentBO> orderKitComponents = orderDetail.getOrderKitComponents();
         if (orderKitComponents == null) {
             orderKitComponents = new ArrayList<OrderKitComponentBO>();
             orderDetail.setOrderKitComponents(orderKitComponents);
         }
         
         for (ComponentBO aKitComponent : aNabsKit.getAllConfiguredKitComponents()) {
             populateKitComponent(orderDetail, aKitComponent, aNabsKit, checkoutCommand);
         }
     }

     
     private void populateKitComponent(OrderDetailBO orderDetail, ComponentBO aKitComponent, KitBO theKit, CheckoutCommand checkoutCommand) {

         OrderKitComponentBO orderKitComponent = findOrderKitComponentByComponentSequence(orderDetail.getOrderKitComponents(), aKitComponent.getLineNumber());
         
         if (orderKitComponent == null) {
             // wasn't found
             OrderKitComponentPK aPk = new OrderKitComponentPK();
             aPk.setOrderDetailFK(orderDetail);
             aPk.setComponentSequenceNumber(aKitComponent.getLineNumber());
             orderKitComponent = new OrderKitComponentBO();
             orderKitComponent.setId(aPk);
             orderDetail.getOrderKitComponents().add(orderKitComponent);
         }
         
         populateKitComponent(orderKitComponent, aKitComponent, theKit, checkoutCommand, orderDetail);
        
    }
     
     private OrderKitComponentBO findOrderKitComponentByComponentSequence(List<OrderKitComponentBO> orderKitComponents, int lineNumber) {
         
         OrderKitComponentBO matchingKitComponent = null;
         for (OrderKitComponentBO anOrderKitComponent : orderKitComponents) {
             if (anOrderKitComponent.getId().getComponentSequenceNumber() == lineNumber) {
                 matchingKitComponent = anOrderKitComponent;
                 break;
             }
         }
         return matchingKitComponent;
     } 

    private void populateKitComponent(OrderKitComponentBO orderKitComponent, ComponentBO aKitComponent, 
                                      KitBO theKit, CheckoutCommand checkoutCommand, OrderDetailBO orderDetail) {
        
        OversizeBO selectedOversize = aKitComponent.getSelectedOversize();
        if (selectedOversize != null) {
            // transfer up the part number
            aKitComponent.setPartNumber(selectedOversize.getDisplayPartNumber());
            aKitComponent.setDisplayPartNumber(selectedOversize.getDisplayPartNumber());
        }
        
        orderKitComponent.setBrandState(aKitComponent.getBrandState());
        
        if (KitType.PRESELECT == theKit.getType()) {
            orderKitComponent.setOriginalComponentFlag(NabsConstants.NABS_YES);
        } else {
            orderKitComponent.setOriginalComponentFlag(NabsConstants.NABS_NO);
        }
        
        orderKitComponent.setProductFlag(aKitComponent.getProductFlag());
        
        orderKitComponent.setQuantityPerKit(aKitComponent.getDefaultQuantity());
        
        orderKitComponent.setRemovedFlag(NabsConstants.NABS_NO);
       
        // i think we need the parent kit for these
        InventoryBO inventoryToUse = theKit.getSelectedInventory();
        // Set the dc on the main attributes
        DistributionCenterBO dc = inventoryToUse.getDistributionCenter();
        orderKitComponent.setShippedLocationId(dc.getCode()); 
        orderKitComponent.setSquashedPartNumber(aKitComponent.getPartNumber());
        orderKitComponent.setSubKitPartNumber(aKitComponent.getParentPartNumber());
        
        populateKitComponentChange(orderKitComponent, aKitComponent, theKit, checkoutCommand);
        
    }
     
    private void populateKitComponentChange(OrderKitComponentBO orderKitComponent, ComponentBO aKitComponent, 
                                            KitBO theKit, CheckoutCommand checkoutCommand) {

        OrderKitComponentChangeBO orderKitComponentChange = orderKitComponent.getOrderKitComponentChange();

        if (orderKitComponentChange == null) {
            // nothing there
            orderKitComponentChange = new OrderKitComponentChangeBO();
            OrderKitComponentChangePK aPk = new OrderKitComponentChangePK();
            aPk.setOrderKitComponentFK(orderKitComponent);
            orderKitComponentChange.setId(aPk);
            orderKitComponent.setOrderKitComponentChange(orderKitComponentChange);
            orderKitComponentChange.setChangeStatus(NabsConstants.LINE_CHANGE_PROCESSED_SUCCESSFULLY);
            
        } else {
            // found it - set status
            orderKitComponentChange.setChangeStatus(NabsConstants.PROCESS_LINE_CHANGE);
        }
       
        // i think we need the parent kit for these
        InventoryBO inventoryToUse = theKit.getSelectedInventory();
        int assignedKitQuantity = inventoryToUse.getAssignedQty();
        int requestedComponentQuantity = assignedKitQuantity * aKitComponent.getDefaultQuantity();
        
        orderKitComponentChange.setRemoveFromKit(NabsConstants.NABS_NO);
        requestedComponentQuantity = aKitComponent.getQuantityToBeOrdered(assignedKitQuantity);
        int requestedComponentBackorderedQuantity = aKitComponent.getQuantityToBeBackordered(assignedKitQuantity);
           
        // not sure this is needed.  Affects some statements I'm told but not much else.
        // if (requestedComponentQuantity == 0) {
        //    orderKitComponentChange.setRemoveFromKit(NabsConstants.NABS_YES);
        // }
            
        orderKitComponentChange.setAssignQuantity(requestedComponentQuantity);
        orderKitComponent.setOrderQuantity(requestedComponentBackorderedQuantity + requestedComponentQuantity);
    }

	public void releaseResources() {
		if(orderHeaderDAO != null){
			orderHeaderDAO.releaseEntityManager();
		}
	}

}

package com.fmo.wom.integration.nabs.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.integration.util.ItemListUtil;

public class ProcessOrderAction extends NabsActionBase {
	
	private static Logger logger = Logger.getLogger(ProcessOrderAction.class);
    
   /* private OrderHeaderProcessingUtil orderHeaderProcessingUtil;*/
    private NABSOrderProcessingUtil nabsOrderProcessingUtil;
    public ProcessOrderAction() {
		super();
		/*orderHeaderProcessingUtil = new OrderHeaderProcessingUtil();*/
		nabsOrderProcessingUtil = new NABSOrderProcessingUtil();
	}

	public Logger getLogger() {
        return logger;
    }
    
    public OrderBO executeInitialAssignment(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
        
    	logger.info("executeInitialAssignment() START");
        // if this order has already been assigned, we need to reset the change statuses.  If not, this call
        // does nothing so no harm
    	if (orderHasNabsItemsForCheckout(order.getItemList()) == false)
            return order;
    	
        // store the order for initial assignment
        /*orderHeaderProcessingUtil.storeOrderInitialAssignment(order);*/
    	nabsOrderProcessingUtil.storeOrderInitialAssignment(order);
    	
        
        // create an input object
        NabsProcessOrderInput initialAssignInput = new NabsProcessOrderInput(order.getMstrOrdNbr(), CheckoutCommand.INITIAL_ASSIGN);
        
        // call nabs
        return executeCheckout(initialAssignInput, order);
    }
    
    public OrderBO executeProcessChanges(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
        
    	if (orderHasNabsItemsForCheckout(order.getItemList()) == false)
            return order;
    	
    	logger.info("executeProcessChanges() START");
        // update the order
        /*orderHeaderProcessingUtil.updateOrderForProcessChanges(order);*/
        nabsOrderProcessingUtil.updateOrderForProcessChanges(order);
        // create an input object
        NabsProcessOrderInput processChangesInput = new NabsProcessOrderInput(order.getMstrOrdNbr(), CheckoutCommand.PROCESS_CHANGES);
       
        // call nabs
        return executeCheckout(processChangesInput, order);
    }
    
	public OrderBO executeBook(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
        
        // do updates needed to book
		if (orderHasNabsItemsForCheckout(order.getItemList()) == false)
            return order;

		logger.info("executeBook() START");
       /* orderHeaderProcessingUtil.updateOrderForBook(order);*/
		nabsOrderProcessingUtil.updateOrderForBook(order);
        // create an input object
        NabsProcessOrderInput bookOrderInput = new NabsProcessOrderInput(order.getMstrOrdNbr(), CheckoutCommand.BOOK);
        
        // call nabs
        return executeCheckout(bookOrderInput, order);
    }
	
	public OrderBO executeCancel(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
        
		// do updates needed to book
       /* orderHeaderProcessingUtil.updateOrderForCancel(order, " ");*/
		nabsOrderProcessingUtil.updateOrderForCancel(order, " ");
        
        // create an input object
        NabsProcessOrderInput bookOrderInput = new NabsProcessOrderInput(order.getMstrOrdNbr(), CheckoutCommand.CANCEL_ASSIGN);
        
        // call nabs
        return executeCheckout(bookOrderInput, order);
    }

	private OrderBO executeCheckout(NabsProcessOrderInput input, OrderBO order) 
	           throws WOMExternalSystemException, WOMTransactionException {
	        
        if (orderHasNabsItemsForCheckout(order.getItemList()) == false)
            return order;
        
        // validate input for the call?
        
        // We need to use a specific output for this NABS call
        NabsProcessOrderOutput output = new NabsProcessOrderOutput();
     
        // call nabs
        executeNabsCall(input, output);
        
        return order;
    }
	
	/**
     * Check if this order has any NABS items in it that can be checked out.
     * @param order order to check
     * @return true if there are items to check out for NABS, false otherwise
     */
    private boolean orderHasNabsItemsForCheckout(List<ItemBO> itemList) {
        
        // if there's anything wrong with the input, there aren't any items to process
        if (itemList == null || itemList.size() == 0) {
            return false;
        }
        
        // first start with all the NABS items only
        List<ItemBO> nabsItemList = ItemListUtil.getNabsItemList(itemList);
        boolean hasNabsItemToCheckout = false;
        for (ItemBO anItem : nabsItemList ) {
            if (anItem.canProcessItem()) {
                // no problem on this item or the problem is a processable one, so we'll process it
                hasNabsItemToCheckout = true;
                break;
            }
        }
        
        return hasNabsItemToCheckout;
    }
    
	public enum CheckoutCommand {
        
        /* ADD_LINE_ITEM = "A";
    		public static final String REMOVE_LINE_ITEM = "R";
    		public static final String CHANGE_EXISTING_LINE_ITEM = " ";
         */
        
        INITIAL_ASSIGN("INITIAL ASSIGN", "10", "A"),
        PROCESS_CHANGES("PROCESS CHANGES", "20", " "),
        CANCEL_ASSIGN("CANCEL ASSIGN", "40", "R"),
        BOOK("BOOK", "30", " ");
        
        String nabsPurpose;
        String requestCode;
        String addRemoveIndicator;
        
        private CheckoutCommand(String napsPurpose, String requestCode, String addRemoveIndicator) {
            this.nabsPurpose = napsPurpose;
            this.requestCode = requestCode;
            this.addRemoveIndicator = addRemoveIndicator;
        }
        
        public String getNabsPurpose() {
            return nabsPurpose;
        }

        public String getRequestCode() {
            return requestCode;
        }
        
        public String getAddRemoveIndicator() {
            return addRemoveIndicator;
        }
    }

	public void releaseResource() {
		/*orderHeaderProcessingUtil.releaseResources();*/
		nabsOrderProcessingUtil.releaseResources();
	}

}

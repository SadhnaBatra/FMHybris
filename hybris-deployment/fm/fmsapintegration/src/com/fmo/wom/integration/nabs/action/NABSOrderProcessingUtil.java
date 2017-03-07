package com.fmo.wom.integration.nabs.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.impl.SessionImpl;

import com.fmo.wom.common.dao.AuditInterceptor;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.NabsShippingCodeBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.nabs.OrderCommentBO;
import com.fmo.wom.domain.nabs.OrderCommentPK;
import com.fmo.wom.domain.nabs.OrderDetailBO;
import com.fmo.wom.domain.nabs.OrderDetailChangeBO;
import com.fmo.wom.domain.nabs.OrderDetailChangePK;
import com.fmo.wom.domain.nabs.OrderDetailPK;
import com.fmo.wom.domain.nabs.OrderHeaderBO;
import com.fmo.wom.domain.nabs.OrderHeaderPK;
import com.fmo.wom.integration.dao.nabs.OrderHeaderDAO;
import com.fmo.wom.integration.dao.nabs.OrderHeaderDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.ProcessOrderAction.CheckoutCommand;
import com.fmo.wom.integration.util.ItemListUtil;

public class NABSOrderProcessingUtil {
	
	Logger logger = Logger.getLogger(NABSOrderProcessingUtil.class);
	
	private OrderHeaderDAO orderHeaderDAO;
	
	public NABSOrderProcessingUtil() {
		super();
		this.orderHeaderDAO = new OrderHeaderDAOImpl();
	}
	
	
	private Connection getConnection(){
		logger.info("getConnection() ..");
		OrderHeaderDAOImpl daoImpl = (OrderHeaderDAOImpl)orderHeaderDAO;
		EntityManager entityManager = daoImpl.getEntityManager();
		EntityManagerImpl entityManagerImpl = (EntityManagerImpl)entityManager;
    	SessionImpl sessioImpl =  (SessionImpl)entityManagerImpl.getSession();
    	return sessioImpl.getJDBCContext().getConnectionManager().borrowConnection();
	}
	
	//Insert to DD04.WOM_ORD_HDR
	private boolean insertIntoOrderHeader(PreparedStatement orderHdrInsertPstmt, OrderHeaderBO anOrderHeader) throws Exception{
		
		boolean orderHdrInserted = false;
		if(anOrderHeader == null){
			logger.info("No Order Header to Insert");
			return false;
		}
		
	    if(orderHdrInsertPstmt != null){
	    	try{
	    		logger.info("insertIntoOrderHeader() Order Header to Insert "+anOrderHeader);
		    	orderHdrInsertPstmt.setString(1, anOrderHeader.getBackOrderType());
		    	orderHdrInsertPstmt.setString(2, anOrderHeader.getBillToCustomer());
		    	orderHdrInsertPstmt.setString(3, anOrderHeader.getCancelReasonCode());
		    	orderHdrInsertPstmt.setString(4, anOrderHeader.getEmergencyFlag());
		    	orderHdrInsertPstmt.setString(5, anOrderHeader.getErrorMessage());
		    	orderHdrInsertPstmt.setString(6, anOrderHeader.getFreeFreightFlag());
		    	orderHdrInsertPstmt.setString(7, anOrderHeader.getHomeLocationId());
		    	orderHdrInsertPstmt.setString(8, anOrderHeader.getHostStatusCode());
		    	orderHdrInsertPstmt.setTimestamp(9, new Timestamp(anOrderHeader.getHostStatusCodeTimestamp().getTime()));
		    	orderHdrInsertPstmt.setString(10, anOrderHeader.getInitByCustomer());
		    	orderHdrInsertPstmt.setString(11, anOrderHeader.getOrderedByName());
		    	orderHdrInsertPstmt.setString(12, anOrderHeader.getPoNumber());
		    	orderHdrInsertPstmt.setString(13, anOrderHeader.getShipToAddress1());
		    	orderHdrInsertPstmt.setString(14, anOrderHeader.getShipToAddress2());
		    	orderHdrInsertPstmt.setString(15, anOrderHeader.getShipToCity());
		    	orderHdrInsertPstmt.setString(16, anOrderHeader.getShipToCountryISO());
		    	orderHdrInsertPstmt.setString(17, anOrderHeader.getShipToCustomer());
		    	orderHdrInsertPstmt.setString(18, anOrderHeader.getShipToName());
		    	orderHdrInsertPstmt.setString(19, anOrderHeader.getShipToPostalCode());
		    	orderHdrInsertPstmt.setString(20, anOrderHeader.getShipToState());
		    	orderHdrInsertPstmt.setString(21, anOrderHeader.getUserGuid());
		    	orderHdrInsertPstmt.setString(22, anOrderHeader.getWantDate());
		    	orderHdrInsertPstmt.setString(23, anOrderHeader.getWomStatusCode());
		    	orderHdrInsertPstmt.setTimestamp(24, new Timestamp(anOrderHeader.getWomStatusCodeTimestamp().getTime()));
		    	orderHdrInsertPstmt.setString(25, anOrderHeader.getId().getMasterOrderNumber());
		    	
		    	int ordHdrInsertCount =  orderHdrInsertPstmt.executeUpdate();
		    	if(ordHdrInsertCount > 0){
		    		orderHdrInserted = true;
		    		logger.info("insertIntoOrderHeader() Order Header Inserted for "+anOrderHeader.getId().getMasterOrderNumber()+" "+anOrderHeader.getPoNumber());
		    	}
	    	} catch(Exception ex){
	    		logger.error("Failed to Insert Order Header ", ex);
	    		throw ex;
	    	} 
	    }
	    return orderHdrInserted;
	}
	
	//Insert to DD04.WOM_ORD_DTL
	private boolean insertIntoOrderDetail(PreparedStatement orderDetailPstmt, OrderHeaderBO anOrderHeader) throws Exception{
		
		boolean orderDtlInserted = false;
		List<OrderDetailBO> orderDetailsBOList = anOrderHeader.getOrderDetails();
		if(orderDetailsBOList == null || orderDetailsBOList.isEmpty()){
			logger.info("insertIntoOrderDetail() No Order Detail to insert");
			return false;
		}
		if(orderDetailPstmt != null){
			logger.info(" insertIntoOrderDetail() Order Details to insert "+orderDetailsBOList.size());
			
			try{
				
				for(OrderDetailBO orderDetailBO : orderDetailsBOList){
					logger.info("Order Detail to Insert "+orderDetailBO);
					orderDetailPstmt.setString(1, orderDetailBO.getAltShipLocationId());
					orderDetailPstmt.setString(2, orderDetailBO.getAltShipVia());
					orderDetailPstmt.setBigDecimal(3, new BigDecimal(orderDetailBO.getAssignQuantity()));
					orderDetailPstmt.setString(4, orderDetailBO.getBackorderType());
					orderDetailPstmt.setString(5, orderDetailBO.getBrandState());
					orderDetailPstmt.setString(6, orderDetailBO.getEngineExpressFlag());
					orderDetailPstmt.setString(7, orderDetailBO.getErrorMessage());
					orderDetailPstmt.setBigDecimal(8, new BigDecimal(orderDetailBO.getKitComponentNetDeletes()));
					orderDetailPstmt.setString(9, orderDetailBO.getKitFlag());
					orderDetailPstmt.setBigDecimal(10, new BigDecimal(orderDetailBO.getOrderQuantity()));
					orderDetailPstmt.setString(11, orderDetailBO.getOriginalOrderedBrandState());
					orderDetailPstmt.setString(12, orderDetailBO.getOriginalOrderedFlag());
					orderDetailPstmt.setString(13, orderDetailBO.getOriginalOrderedPart());
					orderDetailPstmt.setString(14, orderDetailBO.getPartFlippedReason());
					orderDetailPstmt.setString(15, orderDetailBO.getProductFlag());
					orderDetailPstmt.setString(16, orderDetailBO.getShipLocationId());
					orderDetailPstmt.setString(17, orderDetailBO.getShipViaCode());
					orderDetailPstmt.setString(18, orderDetailBO.getSquashedPartNumber());
					orderDetailPstmt.setBigDecimal(19, new BigDecimal(orderDetailBO.getId().getLineSequenceNumber()));
					orderDetailPstmt.setString(20, orderDetailBO.getId().getMasterOrderNumber());
					
					orderDetailPstmt.addBatch();
				}
				
				int [] orderDtlInsertCnt = orderDetailPstmt.executeBatch();
				
				if(orderDtlInsertCnt.length > 0){
					for(int i=0; i <orderDtlInsertCnt.length; i++){
	    				if(orderDtlInsertCnt[i]> 0 ){
	    					orderDtlInserted = true;
	    				}
					}
	    		}
				if(orderDtlInserted){
					logger.info("insertIntoOrderDetail() Order Detail Inserted for"+anOrderHeader.getId().getMasterOrderNumber()+" "+anOrderHeader.getPoNumber());
				}
				
			}catch(Exception ex){
				logger.error("Failed to Insert Order Detail ", ex);
				throw ex;
			}
		}
		return orderDtlInserted;
	}
	
	//Insert to DD04.WOM_ORD_DTL_CHNG
	private boolean insertOrderDtlChange(PreparedStatement orderDetailChngPstmt, OrderHeaderBO anOrderHeader) throws Exception{
		boolean orderDtlChngInserted = false;
		
		List<OrderDetailBO> orderDetailsBOList = anOrderHeader.getOrderDetails();
		if(orderDetailsBOList == null || orderDetailsBOList.isEmpty()){
			logger.info("insertOrderDtlChange() No Order Detail Chnage to insert");
			return true;
		}
		
		if(orderDetailChngPstmt != null){
			
			try{
				logger.info("Order Detail Changes to insert "+orderDetailsBOList.size());
    			
    			for(OrderDetailBO orderDetailBO : orderDetailsBOList){
    				OrderDetailChangeBO orderDetailChangeBO = orderDetailBO.getOrderDetailChange();
    				logger.info("Order DetailChange to Insert "+orderDetailChangeBO);
    				orderDetailChngPstmt.setString(1, orderDetailChangeBO.getAddOrRemoveIndicator());
    				orderDetailChngPstmt.setString(2, orderDetailChangeBO.getAltShipLocationId());
    				orderDetailChngPstmt.setBigDecimal(3, new BigDecimal(orderDetailChangeBO.getAssignQuantity()));
    				orderDetailChngPstmt.setString(4, orderDetailChangeBO.getChangeStatus());
    				orderDetailChngPstmt.setString(5, orderDetailChangeBO.getErrorMessage());
    				orderDetailChngPstmt.setBigDecimal(6, new BigDecimal(orderDetailChangeBO.getOrderQuantity()));
    				orderDetailChngPstmt.setString(7, orderDetailChangeBO.getShipLocationId());
    				orderDetailChngPstmt.setBigDecimal(8, new BigDecimal(orderDetailBO.getId().getLineSequenceNumber()));
    				orderDetailChngPstmt.setString(9, orderDetailBO.getId().getMasterOrderNumber());
    				
    				orderDetailChngPstmt.addBatch();
    			}
	    			
    			int[] orderDtlChngInsertCnt = orderDetailChngPstmt.executeBatch();
	    			
    			if(orderDtlChngInsertCnt != null && orderDtlChngInsertCnt.length > 0){
    				for(int j=0; j <orderDtlChngInsertCnt.length; j++){
	    				if(orderDtlChngInsertCnt[j]> 0 ){
	    					orderDtlChngInserted = true;
	    				}
	    			}
    			}
    			if(orderDtlChngInserted){
    				logger.info("insertOrderDtlChange() Order Detail Change Inserted for "+anOrderHeader.getId().getMasterOrderNumber()+" "+anOrderHeader.getPoNumber());
    			}
    			
			} catch(Exception ex){
				logger.error("Failed to Insert Order Detail Change ", ex);
				throw ex;
			}
		}
		return orderDtlChngInserted;
	}
		
	//Insert to DD04.WOM_ORD_COMMENT
	private boolean insertOrderComments(PreparedStatement orderCommentsPstmt, OrderHeaderBO anOrderHeader) throws Exception{
		boolean orderCommentInserted = false;
		
		List<OrderCommentBO> orderCommentsList = anOrderHeader.getOrderComments(); 
		
		if(orderCommentsList == null || orderCommentsList.isEmpty()){
			logger.info("insertOrderComments() No Order Comments to insert");
			return false;
		}
		 
		 if(orderCommentsPstmt != null){
			 try{
				 for(OrderCommentBO orderCommentBO : orderCommentsList){
					 logger.info("Order Comment to Insert "+orderCommentBO);
					 orderCommentsPstmt.setString(1, orderCommentBO.getComment());
					 orderCommentsPstmt.setBigDecimal(2, new BigDecimal(orderCommentBO.getId().getCommentSequenceNumber()));
					 orderCommentsPstmt.setString(3, orderCommentBO.getId().getCommentTypeId());
					 orderCommentsPstmt.setString(4, orderCommentBO.getId().getOrderHeaderFK().getId().getMasterOrderNumber());
					 orderCommentsPstmt.addBatch();
				 }
				 int [] orderCommentInsrtCnt = orderCommentsPstmt.executeBatch();
				  
				 if(orderCommentInsrtCnt != null && orderCommentInsrtCnt.length > 0){
	 				 for(int j=0; j <orderCommentInsrtCnt.length; j++){
	    				if(orderCommentInsrtCnt[j]> 0 ){
	    					orderCommentInserted = true;
	    			    }
	 			  	}
			 	}
				if(orderCommentInserted){
					logger.info("insertOrderComments() Order Comments Inserted for "+anOrderHeader.getId().getMasterOrderNumber()+" "+anOrderHeader.getPoNumber());
				}
			 }catch (Exception ex){
				 logger.error("Failed to Insert Order Comments ", ex);
				 throw ex;
			 }
		 }
		 return orderCommentInserted;
	}
	
	
	//Get The OrderHeaderBo from Table (schema="DD04", name="WOM_ORD_HDR")
	private boolean findOrderHdr(Connection aConnection, OrderBO anOrder) throws Exception{
		boolean orderHdrFound = false;
		String mstrOrdNbr = anOrder.getMstrOrdNbr();
		String orderHeaderSearchQuery = "SELECT * FROM DD04.WOM_ORD_HDR ORDERHEADERTABLE WHERE ORDERHEADERTABLE.FMO_MSTR_ORD_NBR='"+ mstrOrdNbr+"'"; 
		logger.info("findOrderHdr() orderHeaderSearchQuery "+orderHeaderSearchQuery+" for "+anOrder.getCustPoNbr());
		Statement orderHdrSrchStmt = null;
		ResultSet orderHeaderSrchResultSet = null;
		try {
			orderHdrSrchStmt = aConnection.createStatement();
			orderHeaderSrchResultSet = orderHdrSrchStmt.executeQuery(orderHeaderSearchQuery);
			while (orderHeaderSrchResultSet.next()) {
				orderHdrFound = true;
			}
			if(orderHdrFound){
				logger.info("findOrderHdr() Order Header found "+mstrOrdNbr+" for "+anOrder.getCustPoNbr());
			}
		} catch (Exception e) {
			logger.error("findOrderHdr() failed "+anOrder.getMstrOrdNbr()+" "+anOrder.getCustPoNbr(), e);
			throw e;
		}finally{
			if(orderHdrSrchStmt != null) {orderHdrSrchStmt.close();}
			if(orderHeaderSrchResultSet !=null){orderHeaderSrchResultSet.close();}
		}
		
		return orderHdrFound;
	}
	
	//Get The OrderChangeDtlBO from Table (schema="DD04", name="WOM_ORD_DTL_CHNG")
	private boolean findOrderDetailChange(Connection aConnection, OrderBO anOrderBo) throws Exception{
		boolean ordDtlchngFound = false;
		List<ItemBO> nabsParts = ItemListUtil.getNabsItemList(anOrderBo.getItemList());
		List<Integer> lineItemNumList = new ArrayList<Integer>(nabsParts.size());
		for (ItemBO aNabsPart : nabsParts) {
             if (aNabsPart.canProcessItem()) {
            	 lineItemNumList.add(aNabsPart.getLineNumber());
             }
		 }
		 String mstrOrdNbr = anOrderBo.getMstrOrdNbr();
		 String ordDtlChngfindQry = "SELECT ORDERDTLCHNG.LINE_SEQ_NBR as lineSequenseNbr FROM DD04.WOM_ORD_DTL_CHNG ORDERDTLCHNG WHERE ORDERDTLCHNG.FMO_MSTR_ORD_NBR='"+mstrOrdNbr+"'";
		 logger.info("findOrderDetailChange() "+ordDtlChngfindQry);
		 Statement ordDtlChngStmt = null;
		 ResultSet ordDtlChngResSet = null;
		 try {
			 ordDtlChngStmt = aConnection.createStatement();
			 ordDtlChngResSet = ordDtlChngStmt.executeQuery(ordDtlChngfindQry);
			 while(ordDtlChngResSet.next()){
				int lineSeqNbr = ordDtlChngResSet.getInt("lineSequenseNbr");
				if(lineItemNumList.contains(lineSeqNbr)){
					ordDtlchngFound = true;
				} 
			}
			if(ordDtlchngFound){
				logger.info("findOrderDetailChange() order detail change found for "+mstrOrdNbr+" "+anOrderBo.getCustPoNbr()); 
			}
		} catch (Exception e) {
			logger.error("findOrderDetailChange() failed for "+anOrderBo.getMstrOrdNbr());
			throw e;
		} finally{
			try {
				if(ordDtlChngResSet != null){
					ordDtlChngResSet.close();
				}
				 if(ordDtlChngStmt != null){
					 ordDtlChngStmt.close();
				 }
			} catch (Exception e) {
				logger.error("findOrderDetailChange() failed to close stmt and resSet");
			}
		}
		 
		 return ordDtlchngFound;
	}
	
	private List<ItemBO> getNabsItemList(OrderBO anOrderBO){
		List<ItemBO> nabsParts = ItemListUtil.getNabsItemList(anOrderBO.getItemList());
		List<ItemBO> nabsItemsList = new ArrayList<ItemBO>(nabsParts.size());
		for (ItemBO aNabsPart : nabsParts) {
             if (aNabsPart.canProcessItem()) {
            	 nabsItemsList.add(aNabsPart);
             }
		 }
		return nabsItemsList;
	}
	
	private boolean updateOrder(OrderBO anOrderBO, CheckoutCommand aCommand) throws Exception {
		boolean isUpdated = false;
		logger.info("Update Order for "+anOrderBO.getMstrOrdNbr()+" "+anOrderBO.getCustPoNbr()+" Command "+aCommand);
		
		PreparedStatement ordHdrUpdatePstmt = null;
		PreparedStatement ordDtlChngPStmt = null;
		Connection connection = getConnection();
		try{
			
			boolean orderHdrFound = findOrderHdr(connection, anOrderBO);
			boolean orderDtlChngFound = findOrderDetailChange(connection, anOrderBO); 
			
			if(orderHdrFound && orderDtlChngFound){
				
				connection.setAutoCommit(false);
				String mstOrdNbr = anOrderBO.getMstrOrdNbr();
				
				if("CANCEL ASSIGN".equalsIgnoreCase(aCommand.getNabsPurpose())){
					String orderHdrUpdtQry = "UPDATE DD04.WOM_ORD_HDR SET CANCEL_REASON_CD=?, WOM_STATUS_CD=?, WOM_STATUS_CD_TS=? where FMO_MSTR_ORD_NBR=?";
					
					ordHdrUpdatePstmt = connection.prepareStatement(orderHdrUpdtQry);
					
					ordHdrUpdatePstmt.setString(1, " ");
					ordHdrUpdatePstmt.setString(2, aCommand.getRequestCode());
					ordHdrUpdatePstmt.setTimestamp(3, new Timestamp(new Date().getTime()));
					ordHdrUpdatePstmt.setString(4, mstOrdNbr);
					
				} else if(! "CANCEL ASSIGN".equalsIgnoreCase(aCommand.getNabsPurpose())){
					String orderHdrUpdtQry = "UPDATE DD04.WOM_ORD_HDR SET WOM_STATUS_CD=?, WOM_STATUS_CD_TS=? where FMO_MSTR_ORD_NBR=?";
					
					ordHdrUpdatePstmt = connection.prepareStatement(orderHdrUpdtQry);
					
					ordHdrUpdatePstmt.setString(1, aCommand.getRequestCode());
					ordHdrUpdatePstmt.setTimestamp(2, new Timestamp(new Date().getTime()));
					ordHdrUpdatePstmt.setString(3, mstOrdNbr);
				}
				
				boolean isOrdrHdrUpdated = false;
				int ordHdrUpdtCnt = ordHdrUpdatePstmt.executeUpdate();
				if(ordHdrUpdtCnt > 0){
					isOrdrHdrUpdated = true;
				}
				
				boolean isOrdrDtlChngUpdated = false; 
				
				String orddtlChngUpdQry = "UPDATE DD04.WOM_ORD_DTL_CHNG set ADD_OR_REMOVE_IND=?, CHANGE_STATUS=?, ORDER_QTY=?, SHIP_LOC_ID=? where LINE_SEQ_NBR=? and FMO_MSTR_ORD_NBR=?";
						
				ordDtlChngPStmt = connection.prepareStatement(orddtlChngUpdQry);
				List<ItemBO> nabsItemList = getNabsItemList(anOrderBO);
				for(ItemBO nabsItem : nabsItemList){
					ordDtlChngPStmt.setString(1, aCommand.getAddRemoveIndicator());
					ordDtlChngPStmt.setString(2, NabsConstants.PROCESS_LINE_CHANGE);
					
					InventoryBO inventoryToUse = nabsItem.getSelectedInventory();
			        DistributionCenterBO dc = inventoryToUse.getDistributionCenter();
					
			        //ordDtlChngPStmt.setBigDecimal(3, new BigDecimal(inventoryToUse.getAssignedQty()));
					
			        ordDtlChngPStmt.setBigDecimal(3, new BigDecimal(nabsItem.getItemQty().getRequestedQuantity()));
					ordDtlChngPStmt.setString(4, dc.getCode());
					ordDtlChngPStmt.setBigDecimal(5, new BigDecimal(nabsItem.getLineNumber()));
					ordDtlChngPStmt.setString(6, mstOrdNbr);
					ordDtlChngPStmt.addBatch();
				}
				int [] ordDtlCngCnt = ordDtlChngPStmt.executeBatch();
				if(ordDtlCngCnt != null){
					for(int i=0;i<ordDtlCngCnt.length;i++){
						if(ordDtlCngCnt[i]>0){
							isOrdrDtlChngUpdated = true;
						}
					}
				}
				if(isOrdrHdrUpdated && isOrdrDtlChngUpdated){
					isUpdated = true;
					connection.commit();
				}
			}
			
		} catch(Exception ex){
			logger.error(" updateOrder() failed for "+anOrderBO.getMstrOrdNbr()+" "+anOrderBO.getCustPoNbr()+" Command "+aCommand);
			connection.rollback();
			throw ex;
		} finally{
			connection.setAutoCommit(true);
			if(ordHdrUpdatePstmt != null){
				ordHdrUpdatePstmt.close();
			}
			if(ordDtlChngPStmt != null){
				ordDtlChngPStmt.close();
			}
		}
		return isUpdated;
	}
	
	
	/******************************************************************************************************************************/
	
	private OrderHeaderBO populateOrderHeader(OrderHeaderBO orderHeader, OrderBO order, CheckoutCommand checkoutCommand ) {
        
	String userAccountCd = AuditInterceptor.getUserAccount().getAccountCode();
	if (userAccountCd == null || userAccountCd.isEmpty())
	{
	    logger.info("Logged in User Nabs Account Code is not available");
	    userAccountCd = " ";
	}

	orderHeader.setInitByCustomer(userAccountCd);
        
        String billToAccountCode = order.getBillToAcct().getAccountCode();
        orderHeader.setUserGuid(billToAccountCode);
        orderHeader.setBillToCustomer(billToAccountCode);
        if (order.getShipToAcct() != null) {
            String shipToAccountCode = order.getShipToAcct().getAccountCode();
            logger.info(" populateOrderHeader() shipToAccountCode "+shipToAccountCode);
			if (shipToAccountCode == null || shipToAccountCode.isEmpty())
			{
				logger.info("ShipTo Nabs Account Code is not available");
				shipToAccountCode = " ";
			}
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
       
        return orderHeader;
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
         if (orderDetailList == null) {
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
         if (orderDetail == null) {
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
     }
     
     private void populateShippingInformation(OrderDetailBO orderDetail, ItemBO aNabsPart) {
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
		} 
    }
     
     private void populateShippingInformation(OrderDetailChangeBO orderDetailChange, ItemBO aNabsPart) {
         // populate the inventory information.
         InventoryBO inventoryToUse = aNabsPart.getSelectedInventory();
         
         // Set the dc on the main attributes
         DistributionCenterBO dc = inventoryToUse.getDistributionCenter();
         orderDetailChange.setShipLocationId(dc.getCode());
         orderDetailChange.setAssignQuantity(inventoryToUse.getAssignedQty());
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
     
     private String getOrderHeaderInsertQuery(OrderHeaderBO anOrderHeader){
    	 
    	 if(anOrderHeader != null) {
    		 StringBuilder orderHdrInsertQueryBuilder = new StringBuilder("");
    		 orderHdrInsertQueryBuilder.append("INSERT INTO DD04.WOM_ORD_HDR (");
    		 orderHdrInsertQueryBuilder.append("BACKORDER_TYPE, ");
    		 orderHdrInsertQueryBuilder.append("BILL_TO_CUST, ");
    		 orderHdrInsertQueryBuilder.append("CANCEL_REASON_CD, ");
    		 orderHdrInsertQueryBuilder.append("EMERG_FLG, ");
    		 orderHdrInsertQueryBuilder.append("ERROR_MSG, ");
    		 orderHdrInsertQueryBuilder.append("FREE_FRGHT_FLG, ");
    		 orderHdrInsertQueryBuilder.append("HOME_LOC_ID, ");
    		 orderHdrInsertQueryBuilder.append("HOST_STATUS_CD, ");
    		 orderHdrInsertQueryBuilder.append("HOST_STATUS_CD_TS, ");
    		 orderHdrInsertQueryBuilder.append("INIT_BY_CUST, ");
    		 orderHdrInsertQueryBuilder.append("ORDERED_BY_NAME, ");
    		 orderHdrInsertQueryBuilder.append("PO_NBR, ");
    		 orderHdrInsertQueryBuilder.append("SHIPTO_ADDR1, ");
    		 orderHdrInsertQueryBuilder.append("SHIPTO_ADDR2, ");
    		 orderHdrInsertQueryBuilder.append("SHIPTO_CITY, ");
    		 orderHdrInsertQueryBuilder.append("SHIPTO_CNTRY_ISO, ");
    		 orderHdrInsertQueryBuilder.append("SHIP_TO_CUST, "); 
    		 orderHdrInsertQueryBuilder.append("SHIPTO_NAME, ");
    		 orderHdrInsertQueryBuilder.append("SHIPTO_POSTAL_CD, ");
    		 orderHdrInsertQueryBuilder.append("SHIPTO_STATE, ");
    		 orderHdrInsertQueryBuilder.append("USER_GUID, ");
    		 orderHdrInsertQueryBuilder.append("WANT_DATE, ");
    		 orderHdrInsertQueryBuilder.append("WOM_STATUS_CD, "); 
    		 orderHdrInsertQueryBuilder.append("WOM_STATUS_CD_TS, ");
    		 orderHdrInsertQueryBuilder.append("FMO_MSTR_ORD_NBR)");
    		 orderHdrInsertQueryBuilder.append(" VALUES(");																						 
    		 orderHdrInsertQueryBuilder.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?").append(")");
    		 
    		 return orderHdrInsertQueryBuilder.toString(); 
    	 }
    	 
    	 return null;
     }
     
     private String getOrderDetailInsertQuery(OrderHeaderBO anOrderHeader){
    	
    	 if(anOrderHeader !=null){
    		 
    		 List<OrderDetailBO> orderDetailsBOList = anOrderHeader.getOrderDetails();
    		 
    		 if(orderDetailsBOList != null && (! orderDetailsBOList.isEmpty())){
    			 
    			 StringBuilder orderDtlInsertQryBuilder = new StringBuilder("");
				 orderDtlInsertQryBuilder.append("INSERT INTO DD04.WOM_ORD_DTL (");
				 orderDtlInsertQryBuilder.append("ALT_SHIP_LOC_ID, ");
				 orderDtlInsertQryBuilder.append("ALT_SHIP_VIA, "); 
				 orderDtlInsertQryBuilder.append("ASSIGN_QTY, "); 
				 orderDtlInsertQryBuilder.append("BACKORDER_TYPE, "); 
				 orderDtlInsertQryBuilder.append("BRST, "); 
				 orderDtlInsertQryBuilder.append("ENG_EXPRESS_FLG, "); 
				 orderDtlInsertQryBuilder.append("ERROR_MSG, "); 
				 orderDtlInsertQryBuilder.append("KIT_COMP_NET_DLETS, "); 
				 orderDtlInsertQryBuilder.append("KIT_FLG, "); 
				 orderDtlInsertQryBuilder.append("ORDER_QTY, "); 
				 orderDtlInsertQryBuilder.append("ORIG_ORDERED_BRST, "); 
				 orderDtlInsertQryBuilder.append("ORIG_ORDERED_FLAG, "); 
				 orderDtlInsertQryBuilder.append("ORIG_ORDERED_PART, "); 
				 orderDtlInsertQryBuilder.append("PART_FLIPPED_REAS, "); 
				 orderDtlInsertQryBuilder.append("PROD_FLG, "); 
				 orderDtlInsertQryBuilder.append("SHIP_LOC_ID, "); 
				 orderDtlInsertQryBuilder.append("SHIP_VIA_CODE, "); 
				 orderDtlInsertQryBuilder.append("SQSH_PT_NBR, "); 
				 orderDtlInsertQryBuilder.append("LINE_SEQ_NBR, "); 
				 orderDtlInsertQryBuilder.append("FMO_MSTR_ORD_NBR)");
				 
				 orderDtlInsertQryBuilder.append(" VALUES (");
				 
				 orderDtlInsertQryBuilder.append("?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?").append(")");
				 
				 return orderDtlInsertQryBuilder.toString();
    		 }
    	 }
    	 return null;
     }
     
     private String getOrderDetailChangeInsertQuery(OrderHeaderBO anOrderHeader){
    	 
    	 if(anOrderHeader !=null){
    		
    		 List<OrderDetailBO> orderDetailsBOList = anOrderHeader.getOrderDetails();
    		 if(orderDetailsBOList != null && (! orderDetailsBOList.isEmpty())){
    			 
    			 StringBuilder orderDtlChangeInsertQryBuilder = new StringBuilder("");
				 orderDtlChangeInsertQryBuilder.append("INSERT INTO DD04.WOM_ORD_DTL_CHNG (");
				 orderDtlChangeInsertQryBuilder.append("ADD_OR_REMOVE_IND, ");
				 orderDtlChangeInsertQryBuilder.append("ALT_SHIP_LOC_ID, ");
				 orderDtlChangeInsertQryBuilder.append("ASSIGN_QTY, ");
				 orderDtlChangeInsertQryBuilder.append("CHANGE_STATUS, ");
				 orderDtlChangeInsertQryBuilder.append("ERROR_MSG, ");
				 orderDtlChangeInsertQryBuilder.append("ORDER_QTY, ");
				 orderDtlChangeInsertQryBuilder.append("SHIP_LOC_ID, ");
				 orderDtlChangeInsertQryBuilder.append("LINE_SEQ_NBR, ");
				 orderDtlChangeInsertQryBuilder.append("FMO_MSTR_ORD_NBR)");
				 
				 orderDtlChangeInsertQryBuilder.append(" VALUES (");
				 orderDtlChangeInsertQryBuilder.append("?, ?, ?, ?, ?, ?, ?, ?, ?").append(")");
    			 
    			 return orderDtlChangeInsertQryBuilder.toString();
    		 }
    	 }
    	 return null;
     }
     
     private String getOrderCommentInsertQuery(OrderHeaderBO anOrderHeader){
    	 
    	 List<OrderCommentBO> orderCommentsList = anOrderHeader.getOrderComments();
    	 if(orderCommentsList != null && (!orderCommentsList.isEmpty())){
    		 StringBuilder orderCommentInsertQueryBuilder = new StringBuilder("");
    		 orderCommentInsertQueryBuilder.append("INSERT INTO DD04.WOM_ORD_COMMENT (");
    		 orderCommentInsertQueryBuilder.append("\"COMMENT\", ");
    		 orderCommentInsertQueryBuilder.append("COMMENT_SEQ_NBR, ");
    		 orderCommentInsertQueryBuilder.append("COMNT_TYPE_ID, ");
    		 orderCommentInsertQueryBuilder.append("FMO_MSTR_ORD_NBR)");
    		 
    		 orderCommentInsertQueryBuilder.append(" VALUES(");
    		 orderCommentInsertQueryBuilder.append("?, ?, ?, ?").append(")");
    		 
    		 return orderCommentInsertQueryBuilder.toString(); 
    	 }
    	
    	 return null;
     }
     
     public void releaseResources() {
 		if(orderHeaderDAO != null){
 			orderHeaderDAO.releaseEntityManager();
 		}
 	}

    /*********************************************************************************************/
     
	public void storeOrderInitialAssignment(OrderBO order) throws WOMTransactionException{
		
		Connection connection = getConnection();
		
		PreparedStatement orderHdrInsertPStmt = null;
		PreparedStatement orderDtlInsertPStmt = null;
		PreparedStatement orderDtlChngInsertPStmt = null;
		PreparedStatement orderCommentInsertPStmt = null;
		try {
			
			if(connection !=null && (!connection.isClosed())){
				logger.info("storeOrderInitialAssignment() got the DB Connection");
				
				boolean orderHdrFound = false; 
				orderHdrFound =	findOrderHdr(connection, order);
				
				//Insert the OrderHeader Data
				if(! orderHdrFound ){
					logger.info("storeOrderInitialAssignment() order header with mstrOrdNbr "+order.getMstrOrdNbr()+" not found will insert "+order.getCustPoNbr());
					OrderHeaderBO orderHeader = new OrderHeaderBO();
	 			    OrderHeaderPK headerPK = new OrderHeaderPK();
	 			    headerPK.setMasterOrderNumber(order.getMstrOrdNbr());
	 			    orderHeader.setId(headerPK);
	 			    orderHeader = populateOrderHeader(orderHeader, order, CheckoutCommand.INITIAL_ASSIGN );
	 			    
	 			    //Begin Transaction
	 			    connection.setAutoCommit(false);
	 			  
	 			    boolean ordHdrInserted = false;
	 			    
	 			    String orderHdrInsertQuery = getOrderHeaderInsertQuery(orderHeader);
	 			    logger.info("orderHdrInsertQuery "+orderHdrInsertQuery);
	 			    if(orderHdrInsertQuery != null && (! orderHdrInsertQuery.isEmpty())){
	 			    	 orderHdrInsertPStmt = connection.prepareStatement(orderHdrInsertQuery);
	 	    			 ordHdrInserted = insertIntoOrderHeader(orderHdrInsertPStmt, orderHeader);
	 			    }
	 			   
	 			    if(ordHdrInserted){
	 			    	
	 			    	String orderDtlInsertQuery = getOrderDetailInsertQuery(orderHeader);
	 			    	
	 			    	boolean orderDtlInserted = false;
	 			    	if(orderDtlInsertQuery != null && (! orderDtlInsertQuery.isEmpty())){
	 			    		logger.info("orderDtlInsertQuery "+orderDtlInsertQuery);
	 			    		orderDtlInsertPStmt = connection.prepareStatement(orderDtlInsertQuery);
	 			    		orderDtlInserted = insertIntoOrderDetail(orderDtlInsertPStmt, orderHeader);
	 			    	}
	 			    	
	 			    	boolean orderDtlChngInserted = false;
	 			    	if(orderDtlInserted){
	 			    		String orderDtlChangeInsertQuery = getOrderDetailChangeInsertQuery(orderHeader);
	 			    		logger.info("orderDtlChangeInsertQuery "+orderDtlChangeInsertQuery);
	 			    		if(orderDtlChangeInsertQuery != null && (! orderDtlChangeInsertQuery.isEmpty())){
	 			    			orderDtlChngInsertPStmt = connection.prepareStatement(orderDtlChangeInsertQuery);
	         			    	orderDtlChngInserted = insertOrderDtlChange(orderDtlChngInsertPStmt, orderHeader);
	 			    		}
	 			    	}
	 			    	
	 			    	boolean orderCommentsInsered = false;
	 			    	
	 			    	String orderCommentInsertQuery = getOrderCommentInsertQuery(orderHeader);
	 			    	if(orderCommentInsertQuery != null && (! orderCommentInsertQuery.isEmpty())){
	 			    		logger.info("orderCommentInsertQuery "+orderCommentInsertQuery);
	 			    		orderCommentInsertPStmt = connection.prepareStatement(orderCommentInsertQuery);
	 			    		orderCommentsInsered = insertOrderComments(orderCommentInsertPStmt, orderHeader);
	 			    	}
	 			    }
	 			    connection.commit();
	 			    logger.info("storeOrderInitialAssignment() order header with mstrOrdNbr "+order.getMstrOrdNbr()+" "+order.getCustPoNbr()+" inserted");
				}
			}
		} catch (Exception e) {
			logger.info("storeOrderInitialAssignment() failed .. "+order.getMstrOrdNbr()+" "+order.getCustPoNbr(), e);
			if(connection != null){
				try {
					connection.rollback();
				} catch (SQLException e1) {
					throw new WOMTransactionException(e1);
				}
			}
			throw new WOMTransactionException(e);
		} finally{
			try {
				if(orderHdrInsertPStmt != null){
					orderHdrInsertPStmt.close();
				}
				if(orderDtlInsertPStmt != null){
					orderDtlInsertPStmt.close();
				}
				if(orderDtlChngInsertPStmt != null){
					orderDtlChngInsertPStmt.close();
				}
				if(orderCommentInsertPStmt != null){
					orderCommentInsertPStmt.close();
				}
				if(connection != null){
					connection.setAutoCommit(true);
				}
			} catch (SQLException e) {
				logger.info("storeOrderInitialAssignment() failed to close the prepared statements.. ", e);
			}
		}
	} 

	public void updateOrderForProcessChanges(OrderBO order) throws WOMTransactionException {
		try {
			logger.info("updateOrderForProcessChanges() "+order.getMstrOrdNbr()+" "+order.getCustPoNbr());
			boolean updateProcessChng = updateOrder(order, CheckoutCommand.PROCESS_CHANGES);
			if(updateProcessChng){
				logger.info("updateOrderForProcessChanges() succes "+order.getMstrOrdNbr()+" "+order.getCustPoNbr());
			}
		} catch (Exception e) {
			logger.error("updateOrderForProcessChanges() failed ", e);
			throw new WOMTransactionException(e);
		}
	}


	public void updateOrderForBook(OrderBO order) throws WOMTransactionException{
		try {
			logger.info("updateOrderForBook() "+order.getMstrOrdNbr()+" "+order.getCustPoNbr());
			boolean updateOrderBook = updateOrder(order, CheckoutCommand.BOOK);
			if(updateOrderBook){
				logger.info("updateOrderForBook() succes "+order.getMstrOrdNbr()+" "+order.getCustPoNbr());
			}
		} catch (Exception e) {
			logger.error("updateOrderForBook() failed ", e);
			throw new WOMTransactionException(e);
		}
		
	}


	public void updateOrderForCancel(OrderBO order, String string) throws WOMTransactionException {
		try {
			logger.info("updateOrderForCancel() "+order.getMstrOrdNbr()+" "+order.getCustPoNbr());
			boolean updateOrderBook = updateOrder(order, CheckoutCommand.CANCEL_ASSIGN);
			if(updateOrderBook){
				logger.info("updateOrderForCancel() succes "+order.getMstrOrdNbr()+" "+order.getCustPoNbr());
			}
		} catch (Exception e) {
			logger.error("updateOrderForCancel() failed ", e);
			throw new WOMTransactionException(e);
		}
		
	}
	
	
}

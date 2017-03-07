package com.fmo.wom.integration.nabs.action.partresolution;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.impl.SessionImpl;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.nabs.InventoryLocationBO;
import com.fmo.wom.domain.nabs.InventoryLocationPK;
import com.fmo.wom.domain.nabs.PartResolutionInventoryBO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;
import com.fmo.wom.integration.dao.nabs.InventoryLocationDAO;
import com.fmo.wom.integration.dao.nabs.InventoryLocationDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.util.DistributionCenterCache;
import com.fmo.wom.integration.util.PartNumberSquasher;

public class NABSCheckInventoryUtil {
	
	private static Logger logger = Logger.getLogger(NABSCheckInventoryUtil.class);
	
	private Connection getConnection(InventoryLocationDAO aInventoryLocationDAO){
		logger.info("getConnection() ..");
		InventoryLocationDAOImpl daoImpl = (InventoryLocationDAOImpl)aInventoryLocationDAO;
		EntityManager entityManager = daoImpl.getEntityManager();
		EntityManagerImpl entityManagerImpl = (EntityManagerImpl)entityManager;
    	SessionImpl sessioImpl =  (SessionImpl)entityManagerImpl.getSession();
    	return sessioImpl.getJDBCContext().getConnectionManager().borrowConnection();
	}
	
	private String getInsertItemsQuery(){
		StringBuilder queryBuilder = new StringBuilder("");
		queryBuilder.append("INSERT INTO WOM8.PART_INVENTORY (");
		queryBuilder.append("LINE_SEQ_NBR, ");
		queryBuilder.append("ORD_QTY, ");
		queryBuilder.append("PART_NBR, ");
		queryBuilder.append("TRANS_CD, ");
		queryBuilder.append("TRANS_SEQ_ID, ");
		queryBuilder.append("BRAND_STATE, ");
		queryBuilder.append("PROD_FLG, ");
		queryBuilder.append("CREATION_DATE)");
		queryBuilder.append(" VALUES (");
		queryBuilder.append("?, ?, ?, ?, ?, ?, ?, ?").append(")");
		return queryBuilder.toString();
	}

	
	private PartResolutionInventoryBO createCheckInventoryEntry(int transactionId, String transactionCode, String masterOrderNumber,
            boolean useDisplayPartNumber, ItemBO anItem) {
        PartResolutionInventoryBO aPart = new PartResolutionInventoryBO();
        // set the PK
        PartResolutionInventoryPK aPK = new PartResolutionInventoryPK(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId, ""+anItem.getLineNumber(), transactionCode);
        aPart.setId(aPK);
        String inputPartNumberToUse = "";
        if (useDisplayPartNumber == true) {
            // display part number can be anything from the user, so need to squash and upper
            inputPartNumberToUse = anItem.getDisplayPartNumber();
            inputPartNumberToUse = PartNumberSquasher.squashNabsPartNumber(inputPartNumberToUse);
            inputPartNumberToUse = inputPartNumberToUse.toUpperCase();
        } else {
            inputPartNumberToUse = anItem.getPartNumber();
        }
        aPart.setPartNbr(inputPartNumberToUse);
        aPart.setMasterOrderNbr(masterOrderNumber);
        aPart.setProdFlg(anItem.getProductFlag());
        aPart.setFmBrandCode(anItem.getFmBrandCode());
        aPart.setBrandState(anItem.getBrandState());
        aPart.setOrdQty(1);
        if (anItem.getItemQty() != null) {
            aPart.setOrdQty(anItem.getItemQty().getRequestedQuantity());
        }
        aPart.setLineComment(anItem.getComment());
        return aPart;
    }
	
	public void storeInputItems(int transactionId, String transactionCode, String masterOrderNumber, List<ItemBO> itemList, boolean useDisplayPartNumber) throws WOMTransactionException{
		if(itemList != null && (itemList.isEmpty())){
			logger.error("storeInputItems() No Items suppiled For Check Inventory");
			return;
		}
		InventoryLocationDAO inventoryLocationDAO = new InventoryLocationDAOImpl();
		boolean dataInserted = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try{
			 List<PartResolutionInventoryBO> partResolutionInventoryBOList = new ArrayList<PartResolutionInventoryBO>();
			 for(ItemBO anItem:itemList){
				 partResolutionInventoryBOList.add(createCheckInventoryEntry(transactionId, transactionCode, masterOrderNumber, useDisplayPartNumber, anItem));
			 }
			 connection = getConnection(inventoryLocationDAO);
			 connection.setAutoCommit(false);
			 String storeInputItemsQuery = getInsertItemsQuery();
			 pstmt = connection.prepareStatement(storeInputItemsQuery);
			 for (PartResolutionInventoryBO aPart : partResolutionInventoryBOList) {
	              pstmt.setString(1, aPart.getId().getLineSeqNbr());
	              pstmt.setBigDecimal(2, new BigDecimal(aPart.getOrdQty()));
	              pstmt.setString(3, aPart.getPartNbr());
				  pstmt.setString(4, aPart.getId().getTxnCd());
				  pstmt.setString(5, aPart.getId().getTransactionId());
				  pstmt.setString(6, aPart.getBrandState());
				  pstmt.setString(7, aPart.getProdFlg());
				  final java.sql.Date sqlDate = new java.sql.Date(aPart.getCreationDate().getTime());
				  pstmt.setDate(8, sqlDate);
				  pstmt.addBatch();
	         }
			 
			 int [] inputItemStoreCnt = pstmt.executeBatch();
			 connection.commit();
			 if(inputItemStoreCnt.length > 0){
				for(int i=0; i <inputItemStoreCnt.length; i++){
    				if(inputItemStoreCnt[i]> 0 ){
    					dataInserted = true;
    				}
				}
	    	 }
			 if(dataInserted){
				logger.info("storeInputItems() part resolution - items Inserted for "+NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId);
		 	 }
			 
		}catch(Exception ex){
			logger.error("storeInputItems() failed to insert data", ex);
			try {
				connection.rollback();
			} catch (SQLException e) {
				logger.error("storeInputItems() failed to roll back data", e);
				throw new WOMTransactionException(e);
			}
			throw new WOMTransactionException(ex);
		} finally {
			try {
				if(pstmt !=null){
					pstmt.close();
				}
				if(connection != null){
					connection.setAutoCommit(true);
				}
				if(inventoryLocationDAO !=null){
					inventoryLocationDAO.releaseEntityManager();
				}
				inventoryLocationDAO = null;
			} catch (SQLException e) {
				logger.error("storeInputItems() failed to close prepared statemt", e);
			}
		}
	}

/**************************************************************************************************************************************************/
	private String getInventoryLocationSelectQuery(String aTransactionSeqId, String aTransactionCode){
		StringBuilder invLocQryBuilder = new StringBuilder("");
		invLocQryBuilder.append("SELECT INVLOCTABLE.LINE_SEQ_NBR AS lineSeqNbr, "); 
		invLocQryBuilder.append("INVLOCTABLE.PLANT_CD AS plantCd, "); 
		invLocQryBuilder.append("INVLOCTABLE.TRANS_SEQ_ID AS transactionId, "); 
		invLocQryBuilder.append("INVLOCTABLE.TRANS_CD AS txnCd, "); 
		invLocQryBuilder.append("INVLOCTABLE.AVAIL_QTY AS availQty, "); 
		invLocQryBuilder.append("INVLOCTABLE.CAN_STOCK_FLG AS canStock, "); 
		invLocQryBuilder.append("INVLOCTABLE.HOME_INVNT_LOC_FLG AS homeInvLocFlag, "); 
		invLocQryBuilder.append("INVLOCTABLE.MSTR_ORD_NBR AS masterOrderNbr "); 
		invLocQryBuilder.append("FROM WOM8.INVENTORY_LOCATION INVLOCTABLE ");
		invLocQryBuilder.append("WHERE ");
		invLocQryBuilder.append("INVLOCTABLE.TRANS_SEQ_ID='").append(aTransactionSeqId).append("'");
		invLocQryBuilder.append(" AND ");
		invLocQryBuilder.append("INVLOCTABLE.TRANS_CD='").append(aTransactionCode).append("'");
	
		return invLocQryBuilder.toString();
			
	}
	
	private Map<Integer, List<InventoryLocationBO>> populateInventoryLocationData(ResultSet invLocRs) throws SQLException{
		Map<Integer, List<InventoryLocationBO>> invLocaMap = null;
		if(invLocRs !=null){
			try {
				invLocaMap = new HashMap<Integer, List<InventoryLocationBO>>();
				while(invLocRs.next()){
					InventoryLocationBO inventoryLocationBO = new InventoryLocationBO();
					
					String transactionId = invLocRs.getString("transactionId");
					String lineSeqNbr = invLocRs.getString("lineSeqNbr");
					String txnCd = invLocRs.getString("txnCd");
					String plantCd = invLocRs.getString("plantCd");
					InventoryLocationPK inventoryLocationPK = new InventoryLocationPK(transactionId, lineSeqNbr, txnCd, plantCd);
					
					inventoryLocationBO.setId(inventoryLocationPK);
					List<InventoryLocationBO> invLocList = invLocaMap.get(Integer.valueOf(lineSeqNbr.trim()));
					if(invLocList == null){
					   invLocList = new ArrayList<InventoryLocationBO>();
					   invLocaMap.put(Integer.valueOf(lineSeqNbr.trim()), invLocList);
					}
					
					inventoryLocationBO.setMasterOrderNbr(invLocRs.getString("masterOrderNbr"));
					inventoryLocationBO.setAvailQty(invLocRs.getLong("availQty"));
					inventoryLocationBO.setCanStock("Y".equalsIgnoreCase(invLocRs.getString("canStock"))?true:false);
					inventoryLocationBO.setHomeInventoryLocation("Y".equalsIgnoreCase(invLocRs.getString("homeInvLocFlag"))?true:false);
					
					invLocList.add(inventoryLocationBO);
				}
			} catch (NumberFormatException e) {
				logger.error("populateInventoryLocationData() failed to parse the lineSeqNbr", e);
			} catch (SQLException e) {
				logger.error("populateInventoryLocationData() failed to read data from WOM8.INVENTORY_LOCATION", e);
				throw e;
			}
		}
		return invLocaMap;
	}
	
	private DistributionCenterBO createDistributionCenter(InventoryLocationBO aLocation) {
        String code = aLocation.getId().getPlantCd().trim();
        // Find Distribution Center irrespective of whether it is Active/Inactive.
        //DistributionCenterBO distCenter = DistributionCenterCache.getDistributionCenterWithDefault(Market.US_CANADA, code);
        DistributionCenterBO distCenter = new DistributionCenterBO();
        distCenter.setCode(code);
        DistributionCenterBO dcFrmCache = DistributionCenterCache.getDistributionCenterCache().getDistributionCenter(code);
	    if(dcFrmCache != null){
	    	distCenter.setName(dcFrmCache.getName());
	    }
        return distCenter;
    }
	
	public void loadInventoryLocationResponseData(int transactionId, String transactionCode, List<ItemBO> inputItemList, OrderType ordertype) throws WOMTransactionException{
		InventoryLocationDAO inventoryLocationDAO = new InventoryLocationDAOImpl();
		Connection connection = null;
		Statement invLocStmt = null;
		ResultSet invLocRs = null;
		try{
			connection = getConnection(inventoryLocationDAO);
			String invLocationQuery = getInventoryLocationSelectQuery(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId, transactionCode);
			logger.info("loadInventoryLocationResponseData() invLocationQuery "+invLocationQuery); 
			invLocStmt = connection.createStatement();
			invLocRs = invLocStmt.executeQuery(invLocationQuery);
			
			Map<Integer, List<InventoryLocationBO>> invLocMap = populateInventoryLocationData(invLocRs);
			if(invLocMap != null && (!invLocMap.isEmpty())){
				// for each input item, load the inventory locations for it
		        for (ItemBO anItem : inputItemList ) {
			        
		            List<InventoryBO> dcList = new ArrayList<InventoryBO>();
		             
		            // load all the inventory locations for this master order number and line sequence
		            List<InventoryLocationBO> inventoryLocationList =  invLocMap.get(Integer.valueOf(anItem.getLineNumber()));
		            
		            InventoryBO homeInventoryLocation = null;
		            for (InventoryLocationBO aLocation : inventoryLocationList ) {
		                // this is a 7 digit field, so this cast should never be
		                // in danger of going over MAXINT
		                int availableAtLocation = (int)aLocation.getAvailQty();
		                // create the inventory bo item for this entry
		                InventoryBO anInventory = new InventoryBO();
		                anInventory.setItem(anItem);
		                DistributionCenterBO distCenter = createDistributionCenter(aLocation);
		                anInventory.setDistributionCenter(distCenter);
		                anInventory.setDistCntrId(distCenter.getDistCenterId());
		                // this is a 7 digit field, so this cast should never be
		                // in danger of going over MAXINT
		                anInventory.setAvailableQty(availableAtLocation);
		                // nabs has told us this is the home inventory location, set this as selected.
		                if (aLocation.isHomeInventoryLocation()) {
		                    anInventory.setMainDC(true);
		                    // retain this for outside the loop in case no locations stock.
		                    homeInventoryLocation = anInventory;
		                }
		                // if CAN STOCK is false, this is not a valid location
		                if (aLocation.isCanStock() == true) {
		                    // this is a valid stocking location
		                    dcList.add(anInventory);
		                }
		            }
		            // check if any locations can stock
		            if (dcList.size() == 0 && homeInventoryLocation != null) {
		                // none! Add the home.
		                dcList.add(homeInventoryLocation);
		            }
		            anItem.setInventory(dcList);
		            logger.info("Populated Item Data: "+ anItem);
		        }
			}
		}catch(Exception ex){
			logger.error("loadInventoryLocationResponseData() failed to load Inventory Location data", ex);
			throw new WOMTransactionException(ex);
		}finally{
			try {
				if(invLocRs!=null){
					invLocRs.close();
				}
				if(invLocStmt!=null){
					invLocStmt.close();
				}
				if(inventoryLocationDAO!=null){
					inventoryLocationDAO.releaseEntityManager();
				}
				inventoryLocationDAO = null;
			} catch (SQLException e) {
				logger.error("loadInventoryLocationResponseData() failed to close stmt, connection", e);
			}
		}
	}

}

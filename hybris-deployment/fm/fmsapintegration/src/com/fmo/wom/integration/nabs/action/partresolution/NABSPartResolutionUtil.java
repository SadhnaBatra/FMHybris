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

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.ejb.EntityManagerImpl;
import org.hibernate.impl.SessionImpl;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.CurrencyTypeBO;
import com.fmo.wom.domain.ExternalSystemStatusBO.StatusCategory;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.WeightBO;
import com.fmo.wom.domain.enums.CustomerBusinessType;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.nabs.PartResolutionInventoryBO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;
import com.fmo.wom.domain.nabs.ProblemPartBO;
import com.fmo.wom.domain.nabs.ProblemPartPK;
import com.fmo.wom.integration.dao.nabs.PartResolutionDAO;
import com.fmo.wom.integration.dao.nabs.PartResolutionDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.util.ItemListUtil;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.fmo.wom.integration.util.PartNumberSquasher;
import com.fmo.wom.integration.util.ProblemBOFactory;

public class NABSPartResolutionUtil {
	
	private static Logger logger = Logger.getLogger(NABSPartResolutionUtil.class);
	private static final String SUCCESS_KEY = "00";
	
	private Connection getConnection(PartResolutionDAO aPartResolutionDAO){
		logger.info("getConnection() ..");
		PartResolutionDAOImpl daoImpl = (PartResolutionDAOImpl)aPartResolutionDAO;
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

	
	private PartResolutionInventoryBO createPartResolutionEntry(int transactionId, String transactionCode, String masterOrderNumber,
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
			logger.error("storeInputItems() No Items suppiled For PartResolution");
			return;
		}
		PartResolutionDAO partResolutionDAO = new PartResolutionDAOImpl();
		boolean dataInserted = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try{
			 List<PartResolutionInventoryBO> partResolutionInventoryBOList = new ArrayList<PartResolutionInventoryBO>();
			 for(ItemBO anItem:itemList){
				 partResolutionInventoryBOList.add(createPartResolutionEntry(transactionId, transactionCode, masterOrderNumber, useDisplayPartNumber, anItem));
			 }
			 connection = getConnection(partResolutionDAO);
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
				if(partResolutionDAO !=null){
					partResolutionDAO.releaseEntityManager();
				}
				partResolutionDAO = null;
			} catch (SQLException e) {
				logger.error("storeInputItems() failed to close prepared statemt", e);
			}
		}
	}
	
/**************************************************************************************************************************/
	
	private String getResolvedPartSelectQuery(String aTransactionSeqId, String aTransactionCode){
		StringBuilder partSelectQryBuilder = new StringBuilder("");
		partSelectQryBuilder.append("SELECT PARTINVENTORYTBL.LINE_SEQ_NBR AS lineSeqNbr, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.TRANS_SEQ_ID AS transactionSeqId, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.TRANS_CD AS txnCd, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ALLW_COMP_ADDS_FLG AS allowCompAddsFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ALT_BRAND_STATE AS altBrandState, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ALT_CTLG_PART AS altCtlgPart, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ALT_DESC AS altDesc, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ALT_MULT_QTY AS altMultQty, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ALT_PROD_FLG AS altProdFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.DISCONTINUED_FLG AS discontinuedFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.BRAND_STATE AS brandState, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.BRND_ST_SHRT_DESC AS brandStShortDesc, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.BULK_PART_FLG AS bulkPartFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.CTLG_PART_NBR AS catalogPartNbr, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.CREATION_DATE AS creationDate, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.CUST_PART_NBR AS custPartNbr, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.DISPLAY_PRICE AS displayPrice, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ENGKIT_EXPRESS_FLG AS engKitExpressFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.FACTOR_QTY AS factorQty, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.FM_BRAND_CODE AS fmBrandCode, "); 
		partSelectQryBuilder.append("PARTINVENTORYTBL.FREE_FRGHT_KIT_FLG AS freeFrghtKitFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.FRGHT_NET_PRICE AS frghtNetPrice, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ITEM_STATUS_CD AS itemStatusCd, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ITEM_STATUS_MSG AS itemStatusMsg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.OK_TO_ADD_TO_A_KIT AS okToAddToAKit, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.KIT_ASSGN_TYP_FLG AS kitAssgnTypeFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.KIT_FLG AS kitFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.KIT_OVRSZ_BYPS_FLG AS kitOversizeBypsFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.LINE_COMMENT AS lineComment, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.LOQ_QTY AS loqQty, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.MSTR_ORD_NBR AS masterOrderNbr, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.MFG_CD AS mfgCd, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.MSG AS msg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.MULT_QTY AS multQty, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.NBR_OF_CATEGORIES AS nbrOfCategories, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.NBR_OF_COMPONENTS AS nbrOfComp, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.NO_RETURN_PART_FLG AS noReturnPartFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ORD_QTY AS ordQty, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ORIG_PART_NBR AS origPartNbr, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.PART_DESC AS partDesc, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.PART_FLIPPED_FLG AS partFlippedFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.PART_NBR AS partNbr, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.PCE_WGT_LBS AS pceWgtLbs, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.PKG_CD AS pkgCd, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.PRICE_TYP_CD AS priceTypeCd, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.PROD_FLG AS prodFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.ROUNDED_ORD_QTY AS roundedOrdQty, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.SALES_SYMBOL AS salesSymbol, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.SQSH_PART_NBR AS sqshPartNbr, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.SUBJ_TO_BCKORD_FLG AS subjToBackordFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.TEN_DIGIT_UPC AS tenDigitUPC, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.UOM AS uom, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.VENDOR_DIRECT_FLG AS vendorDirectFlg, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.VERIFY_LOQ AS verifyLOQ, ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.VINTAGE_PART_FLG AS vintageFlg ");
		partSelectQryBuilder.append("FROM WOM8.PART_INVENTORY PARTINVENTORYTBL ");
		partSelectQryBuilder.append("WHERE ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.TRANS_SEQ_ID='").append(aTransactionSeqId).append("'");
		partSelectQryBuilder.append(" AND ");
		partSelectQryBuilder.append("PARTINVENTORYTBL.TRANS_CD='").append(aTransactionCode).append("'");
		return partSelectQryBuilder.toString(); 
		
	}
	
	private String getPartProblemSelectQuery(String aTransactionSeqId, String aTransactionCode){
		StringBuilder partProblemSelectQryBuilder = new StringBuilder("");
		partProblemSelectQryBuilder.append("SELECT PARTPROBLEMTBL.ALLWD_TO_ORD_FLG AS allwdToOrdFlg, ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.BRAND_STATE AS brandState,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.CTLG_PART_NBR AS ctlgPartNbr,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.LINE_SEQ_NBR AS lineSeqNbr,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.MSG AS msg,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.PART_DESC AS partDesc,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.PROB_SEQ_NBR AS probSeqNbr,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.PROD_FLG AS prodFlg,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.TRANS_CD AS txnCd,  ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.TRANS_SEQ_ID AS transactionSeqId  ");
		partProblemSelectQryBuilder.append("FROM WOM8.PROBLEM_PART_DTL PARTPROBLEMTBL ");
		partProblemSelectQryBuilder.append("WHERE ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.TRANS_SEQ_ID='").append(aTransactionSeqId).append("'");
		partProblemSelectQryBuilder.append(" AND ");
		partProblemSelectQryBuilder.append("PARTPROBLEMTBL.TRANS_CD='").append(aTransactionCode).append("'");
		return partProblemSelectQryBuilder.toString();
	}
	
	private Map<PartResolutionInventoryPK, List<ProblemPartBO>> populatePartProblem(ResultSet partProblemRs) throws SQLException{
		Map<PartResolutionInventoryPK, List<ProblemPartBO>> partProblemMap = null;
		if(partProblemRs !=null){
			try {
				partProblemMap = new HashMap<PartResolutionInventoryPK, List<ProblemPartBO>>();
				while(partProblemRs.next()){
					String transactionSeqId = partProblemRs.getString("transactionSeqId");
					String lineSeqNbr = partProblemRs.getString("lineSeqNbr");
					String txnCd = partProblemRs.getString("txnCd");
					
					PartResolutionInventoryPK resolvedPartFK = new PartResolutionInventoryPK(transactionSeqId, lineSeqNbr, txnCd);
					List<ProblemPartBO> partPrblemList = partProblemMap.get(resolvedPartFK);
					if(partPrblemList == null){
						partPrblemList = new ArrayList<ProblemPartBO>();
						partProblemMap.put(resolvedPartFK, partPrblemList);
					}
					ProblemPartBO problemPartBO = new ProblemPartBO();
						int probSeqNbr = partProblemRs.getInt("probSeqNbr");
						ProblemPartPK problemPartPK = new ProblemPartPK(); 
						problemPartPK.setProbSeqNbr(probSeqNbr);
					
					problemPartBO.setId(problemPartPK);
					
					problemPartBO.setAllwdToOrdFlg(partProblemRs.getString("allwdToOrdFlg"));
					problemPartBO.setBrandState(partProblemRs.getString("brandState"));
					problemPartBO.setCtlgPartNbr(partProblemRs.getString("ctlgPartNbr"));
					problemPartBO.setMsg(partProblemRs.getString("msg"));		
					problemPartBO.setPartDesc(partProblemRs.getString("partDesc"));	
					problemPartBO.setProdFlg(partProblemRs.getString("prodFlg"));
					partPrblemList.add(problemPartBO);
				}
			} catch (SQLException e) {
				logger.error("populatePartProblem() failed to read data from WOM8.PROBLEM_PART_DTL", e);
				throw e;
			}
		}
		return partProblemMap;
	}
	
	private List<PartResolutionInventoryBO> populateResolvedParts(ResultSet resolvedPartRs, Map<PartResolutionInventoryPK, 
																		                 List<ProblemPartBO>> partProblemsMap) throws SQLException{
		
		List<PartResolutionInventoryBO> resolvedPartsLst = new ArrayList<PartResolutionInventoryBO>();
		if(resolvedPartRs !=null){
			
			try {
				while (resolvedPartRs.next()) {
					PartResolutionInventoryBO resolvedPart = new PartResolutionInventoryBO();
					
					String transactionSeqId = resolvedPartRs.getString("transactionSeqId");
					String lineSeqNbr = resolvedPartRs.getString("lineSeqNbr");
					String txnCd = resolvedPartRs.getString("txnCd");
					PartResolutionInventoryPK resolvedPartPK = new PartResolutionInventoryPK(transactionSeqId, lineSeqNbr, txnCd);
					
					resolvedPart.setId(resolvedPartPK);
					
					resolvedPart.setAllowCompAdditionsFlg(resolvedPartRs.getString("allowCompAddsFlg"));
					resolvedPart.setAltBrandState(resolvedPartRs.getString("altBrandState"));
					resolvedPart.setAltCtlgPart(resolvedPartRs.getString("altCtlgPart"));
					resolvedPart.setAltDesc(resolvedPartRs.getString("altDesc"));
					resolvedPart.setAltMultQty(resolvedPartRs.getDouble("altMultQty"));
					resolvedPart.setAltProdFlg(resolvedPartRs.getString("altProdFlg"));
					
					resolvedPart.setBrandState(resolvedPartRs.getString("brandState"));
					resolvedPart.setBrandStateShortDesc(resolvedPartRs.getString("brandStShortDesc"));
					resolvedPart.setBulkPartFlg(resolvedPartRs.getString("bulkPartFlg"));
					
					resolvedPart.setCreationDate(resolvedPartRs.getDate("creationDate"));
					resolvedPart.setCatalogPartNbr(resolvedPartRs.getString("catalogPartNbr"));
					resolvedPart.setCustPartNbr(resolvedPartRs.getString("custPartNbr"));
					
					resolvedPart.setDiscontinuedFlg(resolvedPartRs.getString("discontinuedFlg"));
					resolvedPart.setDisplayPrice(resolvedPartRs.getDouble("displayPrice"));
					
					resolvedPart.setEngKitExpressFlg(resolvedPartRs.getString("engKitExpressFlg"));
					
					resolvedPart.setFactorQty(resolvedPartRs.getDouble("factorQty"));
					resolvedPart.setFmBrandCode(resolvedPartRs.getString("fmBrandCode"));
					resolvedPart.setFreeFrghtKitFlg(resolvedPartRs.getString("freeFrghtKitFlg"));
					resolvedPart.setFrghtNetPrice(resolvedPartRs.getDouble("frghtNetPrice"));
					
					resolvedPart.setItemStatusCd(resolvedPartRs.getString("itemStatusCd"));
					resolvedPart.setItemStatusMsg(resolvedPartRs.getString("itemStatusMsg"));
					
					resolvedPart.setKitAssgnTypeFlg(resolvedPartRs.getString("kitAssgnTypeFlg"));
					resolvedPart.setKitFlg(resolvedPartRs.getString("kitFlg"));
					resolvedPart.setKitOversizeBypassFlg(resolvedPartRs.getString("kitOversizeBypsFlg"));
					
					resolvedPart.setLineComment(resolvedPartRs.getString("lineComment"));
					resolvedPart.setLoqQty(resolvedPartRs.getDouble("loqQty"));
					
					resolvedPart.setMasterOrderNbr(resolvedPartRs.getString("masterOrderNbr"));
					resolvedPart.setMfgCd(resolvedPartRs.getString("mfgCd"));
					resolvedPart.setMsg(resolvedPartRs.getString("msg"));
					resolvedPart.setMultQty(resolvedPartRs.getDouble("multQty"));
					
					resolvedPart.setNbrOfCategories(resolvedPartRs.getInt("nbrOfCategories"));
					resolvedPart.setNbrOfComp(resolvedPartRs.getInt("nbrOfComp"));
					resolvedPart.setNoReturnPartFlg(resolvedPartRs.getString("noReturnPartFlg"));
					
					resolvedPart.setOkToAddToAKit(resolvedPartRs.getString("okToAddToAKit"));
					resolvedPart.setOrdQty(resolvedPartRs.getDouble("ordQty"));
					resolvedPart.setOrigPartNbr(resolvedPartRs.getString("origPartNbr"));
					
					resolvedPart.setPartDesc(resolvedPartRs.getString("partDesc"));
					resolvedPart.setPartFlippedFlg(resolvedPartRs.getString("partFlippedFlg"));
					resolvedPart.setPartNbr(resolvedPartRs.getString("partNbr"));
					resolvedPart.setPceWgtLbs(resolvedPartRs.getDouble("pceWgtLbs"));
					resolvedPart.setPkgCd(resolvedPartRs.getString("pkgCd"));
					resolvedPart.setPriceTypeCd(resolvedPartRs.getString("priceTypeCd"));
					resolvedPart.setProdFlg(resolvedPartRs.getString("prodFlg"));
					
					resolvedPart.setRoundedOrdQty(resolvedPartRs.getDouble("roundedOrdQty"));
					
					resolvedPart.setSalesSymbol(resolvedPartRs.getString("salesSymbol"));
					resolvedPart.setSqshPartNbr(resolvedPartRs.getString("sqshPartNbr"));
					resolvedPart.setSubjToBackordFlg(resolvedPartRs.getString("subjToBackordFlg"));

					resolvedPart.setTenDigitUPC(resolvedPartRs.getString("tenDigitUPC"));
					
					resolvedPart.setUom(resolvedPartRs.getString("uom"));
					
					resolvedPart.setVendorDirectFlg(resolvedPartRs.getString("vendorDirectFlg"));
					resolvedPart.setVerifyLOQ(resolvedPartRs.getDouble("verifyLOQ"));
					resolvedPart.setVintageFlg(resolvedPartRs.getString("vintageFlg"));
					resolvedPart.setBeingDiscontinued((resolvedPart.getDiscontinuedFlg() != null && "Y".equals(resolvedPart
							.getDiscontinuedFlg())) ? true : false);
					resolvedPart.setProblemParts(partProblemsMap.get(resolvedPartPK));

					resolvedPartsLst.add(resolvedPart);
				}
								
			} catch (SQLException e) {
				logger.error("populatePartProblem() failed to read data from WOM8.PART_INVENTORY", e);
				throw e;
			}
			
		}
		return resolvedPartsLst;
	}
	
	public List<ItemBO> loadPartResolutionResponseData(int transactionId, String transactionCode, List<ItemBO>validItemsList) throws WOMTransactionException{
		List<ItemBO> resolvedItems = new ArrayList<ItemBO>();
		
		PartResolutionDAO partResolutionDAO = new PartResolutionDAOImpl();
		Connection connection = null;
		Statement partProblemStmt = null;
		Statement resolvedPartStmt = null;
		ResultSet partProblemRs = null;
		ResultSet resolvedPartRs = null;
		try{
			connection = getConnection(partResolutionDAO);
			//first load the ProlemBO
			String partProblemSelectQry = getPartProblemSelectQuery(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId, transactionCode);
			logger.info("loadPartResolutionResponseData() partProblemSelectQry "+partProblemSelectQry); 
			partProblemStmt = connection.createStatement();
			partProblemRs = partProblemStmt.executeQuery(partProblemSelectQry);
			
			//Load The Resolved Parts
			String resolvedPartSelectQry = getResolvedPartSelectQuery(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId, transactionCode);
			logger.info("loadPartResolutionResponseData() resolvedPartSelectQry "+resolvedPartSelectQry);
			resolvedPartStmt = connection.createStatement();
			resolvedPartRs = resolvedPartStmt.executeQuery(resolvedPartSelectQry);
			
			Map<PartResolutionInventoryPK, List<ProblemPartBO>> partProblemMap = populatePartProblem(partProblemRs);
			
			List<PartResolutionInventoryBO> results = populateResolvedParts(resolvedPartRs, partProblemMap);
			
			// create a map for easier retrieval of input items via line number
	        Map<Integer, ItemBO> lineNumberInputItemMap = ItemListUtil.createLineNumberItemMap(validItemsList);
	        for (PartResolutionInventoryBO aResult : results) {
                // now find the input item based on the line seq
                ItemBO anItem = lineNumberInputItemMap.get(new Integer(aResult.getId().getLineSeqNbr()));
                ItemBO newItem = null;
                // create a new copy. If the input is a kit, clone it
                newItem = anItem.createInitialCopy();  
                // populate part data from the response
                populateItemData(newItem,aResult);
                // process the status code and set problems
                processStatus(newItem, aResult);
                // put it on the list
                resolvedItems.add(newItem);
            }
		}catch(Exception e){
			logger.error("loadPartResolutionResponseData() failed to load part resolution data", e);
			throw new WOMTransactionException(e);
		}finally{
			try {
				if(resolvedPartRs!=null){
					resolvedPartRs.close();
				}
				if(partProblemRs!=null){
					partProblemRs.close();
				}
				if(resolvedPartStmt!=null){
					resolvedPartStmt.close();
				}
				if(partProblemStmt!=null){
					partProblemStmt.close();
				}
				if(partResolutionDAO!=null){
					partResolutionDAO.releaseEntityManager();
				}
				partResolutionDAO = null;
			} catch (SQLException e) {
				logger.error("loadPartResolutionResponseData() failed to close stmt, connection", e);
			}
		}
		
		return resolvedItems;
	}
	
	/**
     * Using the given PartResolutionInventory object, populate the relevant part data
     * @param anItem ItemBO to be populated
     * @param aResult NABS response data for this part
     */
    private void populateItemData(ItemBO anItem, PartResolutionInventoryBO aResult) {
        
        anItem.setExternalSystem(ExternalSystem.NABS);
        anItem.setLineNumber(new Integer(aResult.getId().getLineSeqNbr()));
        
        // the partNbr field on aResult is merely our input.  The catalog part number
        // now contains the real resolved part value if all went well.
        if (GenericValidator.isBlankOrNull(aResult.getCatalogPartNbr()) == false) {
            anItem.setPartNumber(aResult.getCatalogPartNbr());
        }
        anItem.setProductFlag(aResult.getProdFlg());
        anItem.setBrandState(aResult.getBrandState());
        anItem.setVendorDirect(aResult.isVendorDirect());
        anItem.setDescription(aResult.getPartDesc());
        anItem.setItemPrice(createPrice(aResult));
        anItem.setItemWeight(createWeight(aResult));
        anItem.setItemQty(createQuantity(anItem.getItemQty(), aResult));
        anItem.setKitAddable(aResult.isKitAddable());
        anItem.setPredecessorReason(aResult.getPartFlippedFlg());
        anItem.setBeingDiscontinued(aResult.isBeingDiscontinued());
        anItem.setBrStShortDesc(aResult.getBrandStateShortDesc());
        anItem.setSalesSymbol(aResult.getSalesSymbol());
        anItem.setOrderEntryComment(aResult.getLineComment());
        anItem.setPackageCode(aResult.getPkgCd());
        anItem.setTenDigitUPC(aResult.getTenDigitUPC());
		anItem.setKit(aResult.isKit());
		anItem.setVintagePart(aResult.getVintageFlg());
       
        // try to make this readable - too many negations in the old way.
        // If NABS tells us the part is not returnable, that means we cannot return it for credit
        anItem.setCanReturnForCredit(true);
        boolean partNotReturnable = aResult.isNoReturn();
        anItem.setCanReturnForCredit(partNotReturnable == false);
   
        // See if this part has been superceded 
        if (isPartSuperceded(aResult)) {
            createPartSupercededProblem(anItem, aResult);
        }
        
        // see if this part is being discontinued
        if (aResult.isBeingDiscontinued()) {
            ProblemBO beingDiscontinued = ProblemBOFactory.createProblem(MessageResourceUtil.PART_BEING_DISCONTINUED, ExternalSystem.NABS);
            beingDiscontinued.setAllowedToProcess(true);
            anItem.addProblemItem(beingDiscontinued);
        }
        
    }
    
    /**
     * Process the status of each of the parts. Using the status code on the PartResolutionInventory,
     * retrieve all ProblemPart objects and process them appropriately
     * @param anItem
     * @param aResult
     */
    private void processStatus(ItemBO anItem, PartResolutionInventoryBO aResult) {
        
        String statusCode = aResult.getItemStatusCd();
        
        // if the status is not success, process the problem item
        if (SUCCESS_KEY.equals(statusCode) == false) {
            
            String statusKey = MessageResourceUtil.getNabsStatusKeyViaStatusCode(statusCode);
            ProblemBO realProblem = ProblemBOFactory.createProblem(statusKey, ExternalSystem.NABS);
            anItem.addProblemItem(realProblem);
        
            // Multiple found requires special processing of the ProblemPart collection
            if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(statusKey)) {
            
                Map matchingPartsMap = new HashMap();
                List<ProblemPartBO> problemParts = aResult.getProblemParts();
                for (ProblemPartBO aProblemPart : problemParts) {
                    
                    ItemBO aMatchedPart = new ItemBO();
                    aMatchedPart.setDisplayPartNumber(aProblemPart.getCtlgPartNbr());
                    aMatchedPart.setPartNumber(aProblemPart.getCtlgPartNbr());
                    aMatchedPart.setProductFlag(aProblemPart.getProdFlg());
                    aMatchedPart.setBrandState(aProblemPart.getBrandState());
                    aMatchedPart.setDescription(aProblemPart.getPartDesc());
                   
                    matchingPartsMap.put(new Integer(aProblemPart.getId().getProbSeqNbr()), aMatchedPart);
                }
                
                realProblem.setCorrectiveOptions(matchingPartsMap);
               
            }
            
            if (realProblem.getStatusCategory() == StatusCategory.PART_MIGRATED) {
                // this guy was migrated.  Alt ctlg part number is the REAL part number to use.
                anItem.setPartNumber(aResult.getAltCtlgPart());
            }
            
            //If it's a Carter part
            if (MessageResourceUtil.CARTER_PARTS.equals(statusKey)) {
            	realProblem.setAllowedToProcess(true);           	  
            }
            
        }
    }
    
    private QuantityBO createQuantity(QuantityBO originalQuantity, PartResolutionInventoryBO aResult) {
        QuantityBO qty = new QuantityBO();
        
        qty.setSoldInMultiples(true);
        qty.setSoldInMultipleQuantity((int)aResult.getMultQty()); 
        if (qty.getSoldInMultipleQuantity() == 0)
        {
            // Don't want divide by 0 exceptions
            qty.setSoldInMultipleQuantity(1);
            qty.setSoldInMultiples(false);
        }
        qty.setOverQuantity((int)aResult.getVerifyLOQ());
        qty.setReasonableQuantityThreshold((int) aResult.getVerifyLOQ());
        qty.setLargeQuantityThreshold((int)aResult.getLoqQty());
        qty.setFactorQuantity((int)aResult.getFactorQty());
        // no need to get requested from response as we have it in the original
        qty.setRequestedQuantity(originalQuantity.getRequestedQuantity());
        if (aResult.getUom() != null && "".equals(aResult.getUom().trim()) == false) {
            qty.setQtyUomCd(aResult.getUom());
        } else {
            qty.setQtyUomCd("EA");
        }
        qty.setRoundedOrderQty((int)aResult.getRoundedOrdQty());
        return qty;
    }

    private WeightBO createWeight(PartResolutionInventoryBO aResult) {
        WeightBO weight = new WeightBO();
        weight.setWeight(aResult.getPceWgtLbs());
        return weight;
    }
    
    private PriceBO createPrice(PartResolutionInventoryBO aResult) {
        PriceBO price = new PriceBO();
        
        CurrencyTypeBO aCurrency = new CurrencyTypeBO();
        aCurrency.setCode(CurrencyTypeBO.USD);
        price.setCurrency(aCurrency);
        price.setPriceType(CustomerBusinessType.getFromCode(aResult.getPriceTypeCd()));
        price.setDisplayPrice(aResult.getDisplayPrice());
        price.setFreightPrice(aResult.getFrghtNetPrice());
        return price;
    }
    
    /**
     * Utility method to determine if a part has been superceded
     * @param aResult - current object in the NABS response representing
     * this part.
     * @return true if the alternate part number is populated 
     */
    private boolean isPartSuperceded(PartResolutionInventoryBO aResult) {
        String newPartNumber = aResult.getAltCtlgPart();
        return (newPartNumber != null && newPartNumber.trim().length() > 0);
    }
    
    private void createPartSupercededProblem(ItemBO anItem, PartResolutionInventoryBO aResult) {
        // set the old part number info
        String oldPartNumber = aResult.getAltCtlgPart();
        
        if (oldPartNumber.equals(anItem.getPartNumber()) == false) {
            
            // part numbers are not equal
            ProblemBO supercededPartProblem = ProblemBOFactory.createSupercededPartProblem( anItem.getPartNumber(), oldPartNumber);
            anItem.setPredecessorPartNumber( anItem.getDisplayPartNumber());
            anItem.addProblemItem(supercededPartProblem);
        }
    }
}

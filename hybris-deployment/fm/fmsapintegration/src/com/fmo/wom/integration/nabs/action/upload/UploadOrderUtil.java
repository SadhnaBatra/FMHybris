
package com.fmo.wom.integration.nabs.action.upload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.nabs.upload.UploadOrderBO;
import com.fmo.wom.domain.nabs.upload.UploadOrderDataBO;
import com.fmo.wom.domain.nabs.upload.UploadOrderDataPK;
import com.fmo.wom.integration.dao.nabs.upload.UploadOrderDAO;
import com.fmo.wom.integration.dao.nabs.upload.UploadOrderDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.upload.format.Detail;
import com.fmo.wom.integration.nabs.action.upload.format.Header;
import com.fmo.wom.integration.nabs.action.upload.format.Header1E;
import com.fmo.wom.integration.nabs.action.upload.format.Header1F;
import com.fmo.wom.integration.nabs.action.upload.format.ManualShipTo;
import com.fmo.wom.integration.nabs.action.upload.format.ManualShipTo5A;
import com.fmo.wom.integration.nabs.action.upload.format.ManualShipTo5B;
import com.fmo.wom.integration.nabs.action.upload.format.OrderComment;
import com.fmo.wom.integration.nabs.action.upload.format.Record;
import com.fmo.wom.integration.nabs.action.upload.format.ShippingComment;
import com.fmo.wom.integration.nabs.action.upload.format.Trailer;

public class UploadOrderUtil {

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UploadOrderUtil.class);
    
    private UploadOrderDAO uploadOrderDAO;
    
    public UploadOrderUtil() {
		super();
		uploadOrderDAO = new UploadOrderDAOImpl();
	}

	// @Transactional(propagation=Propagation.REQUIRES_NEW) //, value="dtransactionManager" )
    public void storeOrderData(int transactionId, OrderBO anOrder) throws WOMTransactionException{
    	UploadOrderDAOImpl daoImpl = (UploadOrderDAOImpl)uploadOrderDAO;
    	EntityTransaction transaction = null;
    	try{
    		transaction = daoImpl.getEntityManager().getTransaction(); 
    		if(transaction != null && (!transaction.isActive())){
    			transaction.begin();
    			// this might be big, but want one transaction, so not committing details piecemeal.
    	        List<UploadOrderDataBO> dataList = new ArrayList<UploadOrderDataBO>();
    	        
    	        // create the top level guy
    	        UploadOrderBO parent = createUploadOrder(anOrder, transactionId);
    	        
    	        // each record has a sequence number.  Use this value to track it.
    	        // Yes, I'm aware I could have used the size of the dataList, but this
    	        // should be more clear.
    	        int index = 1;
    	        for (FileLineType aType : FileLineType.values()) {
    	            
    	            // create the list of objects for this record type
    	            List<UploadOrderDataBO> dataListForRecordType = createUploadOrderDataList(parent, aType, anOrder, transactionId, index);
    	            // advance the index
    	            index += dataListForRecordType.size();
    	            
    	            // add them to the big list
    	            dataList.addAll(dataListForRecordType);
    	        }
    	        
    	        // set the data list
    	        parent.setOrderData(dataList);
    	        
    	        // create and hope for the best!
    	        uploadOrderDAO.persist(parent);
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
   
   // @Transactional(propagation=Propagation.REQUIRES_NEW) //, value="dtransactionManager" )
    public void updateOrderData(int transactionSequenceId, String statusCode) throws WOMTransactionException{
    	UploadOrderDAOImpl daoImpl = (UploadOrderDAOImpl)uploadOrderDAO;
    	EntityTransaction transaction = null;
    	try{
    		transaction = daoImpl.getEntityManager().getTransaction(); 
    		if(transaction != null && (!transaction.isActive())){
    			transaction.begin();
    			UploadOrderBO parent = uploadOrderDAO.findById(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionSequenceId);
    	        parent.setStatus(statusCode);
    	        uploadOrderDAO.merge(parent);
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
     * Create the parent record.  All this data is in the actual record level data, but for
     * audit and queries, we'll put it here also.
     * @param anOrder
     * @param transactionId
     * @return
     */
    private UploadOrderBO createUploadOrder(OrderBO anOrder, int transactionId) {
        UploadOrderBO uploadOrder = new UploadOrderBO();
        uploadOrder.setTransactionId(NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId);
        uploadOrder.setBillToAccountCd(anOrder.getBillToAcctCd());
        uploadOrder.setCustomerPONumber(anOrder.getCustPoNbr());
        uploadOrder.setCreationDate(new Date());
        // no status yet, until told to switch
        uploadOrder.setStatus(" ");
        return uploadOrder;
    }

    private List<UploadOrderDataBO> createUploadOrderDataList(UploadOrderBO parent, FileLineType aType, OrderBO anOrder, int transactionId, int index) {
    
        List<UploadOrderDataBO> uploadOrderDataList = new ArrayList<UploadOrderDataBO>();
        
        // first get the implementing class
        Record implementingClass = aType.getImplementingClassInstance();
        
        // temp null check before all record types are implemented
        if (implementingClass == null) return uploadOrderDataList;
        
        // now get the foratted records for the record
        List<String> dataList = aType.getImplementingClassInstance().getFormattedStringList(transactionId, anOrder);
        
        // go through and create the BOs.
        for (String dataString : dataList) {
            
            UploadOrderDataBO aData = new UploadOrderDataBO();
            UploadOrderDataPK aPK = new UploadOrderDataPK();
            aPK.setParentOrderDataFK(parent);
            aPK.setLineSeqNbr(index++);
            aData.setId(aPK);
            aData.setData(dataString);
            uploadOrderDataList.add(aData);
        }
        
        return uploadOrderDataList;
    }

    
    public void releaseResource(){
    	if(uploadOrderDAO != null){
    		uploadOrderDAO.releaseEntityManager();
    	}
    }

    protected enum FileLineType {
        HEADER(Header.class),
        HEADER_1E(Header1E.class),
        HEADER_1F(Header1F.class),
        DETAIL(Detail.class),
        ORDER_COMMENT(OrderComment.class),
        SHIPPING_COMMENT(ShippingComment.class),
        MANUAL_SHIP_TO(ManualShipTo.class),
        MANUAL_SHIP_TO_5A(ManualShipTo5A.class),
        MANUAL_SHIP_TO_5B(ManualShipTo5B.class),
        TRAILER(Trailer.class);
        
        private final Class<? extends Record> implementingClass;
        
        private FileLineType(Class<? extends Record> clazz) {
            this.implementingClass = clazz;
        }
        
        public Record getImplementingClassInstance() {
            if (implementingClass == null) return null;
            
            try {
                return (Record) implementingClass.newInstance();
            } catch (Exception e) {
                logger.error("Unable to instantiate "+  implementingClass, e);
                return null;
            }
        }
    }
}
    
       
    
    
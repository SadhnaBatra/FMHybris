package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.nabs.InventoryLocationBO;
import com.fmo.wom.domain.nabs.InventoryLocationPK;
import com.fmo.wom.integration.nabs.NabsConstants;
//import org.springframework.transaction.annotation.Transactional;
import com.fmo.wom.common.dao.JpaDAODB2;


public class InventoryLocationDAOImpl extends JpaDAODB2<InventoryLocationPK, InventoryLocationBO> implements InventoryLocationDAO {
    
    private static Logger logger = Logger.getLogger(InventoryLocationDAOImpl.class);
    
    public InventoryLocationDAOImpl() {
    	//setEntityManager(DB2ConnectionUtil.getEntityManager());
	}
    
    //@SuppressWarnings("unchecked")
   // @Transactional(readOnly=true)
    public List<InventoryLocationBO> findByTransactionIdAndLineSeq(int transactionId, int lineSequenceNumber, String transactionCode) {   
        
        logger.debug("Retrieving InventoryLocationBO via transaction id "+NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId+" and seq number "+lineSequenceNumber);
        EntityManager anEntityManager = getEntityManager();
        CriteriaBuilder cb = anEntityManager.getCriteriaBuilder();
        CriteriaQuery<InventoryLocationBO> cq = cb.createQuery(InventoryLocationBO.class);
        Root<InventoryLocationBO> invLocation = cq.from(InventoryLocationBO.class);
        
        // readability
        Predicate masterOrderNumberEqual = cb.equal(invLocation.get("id").get("transactionId"),NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId);
        Predicate lineSequencNbrEqual  = cb.equal(invLocation.get("id").get("lineSeqNbr"),""+lineSequenceNumber);
        Predicate txnCodeEqual  = cb.equal(invLocation.get("id").get("txnCd"),transactionCode);
        
        cq.where(cb.and(masterOrderNumberEqual, lineSequencNbrEqual, txnCodeEqual));
        TypedQuery<InventoryLocationBO> q = anEntityManager.createQuery(cq);
        final List<InventoryLocationBO> result = q.getResultList();
        logger.debug("Found "+result.size()+" InventoryLocationBO results.");
        return result; 
    }
    
}


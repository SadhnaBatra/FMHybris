package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.fmo.wom.common.dao.JpaDAODB2;
import com.fmo.wom.domain.nabs.PartResolutionInventoryBO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;
import com.fmo.wom.integration.nabs.NabsConstants;

public class PartResolutionDAOImpl extends JpaDAODB2<PartResolutionInventoryPK, PartResolutionInventoryBO> implements PartResolutionDAO {

    private static Logger logger = Logger.getLogger(PartResolutionDAO.class);
    
    public PartResolutionDAOImpl() {
    	//setEntityManager(DB2ConnectionUtil.getEntityManager());
	}

    @Override
    public List<PartResolutionInventoryBO> findByTransactionId(int transactionId, String transactionCode) {
       
        logger.debug("Retrieving PartResoltuionInventory via transaction id "+NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId+" and txn code "+transactionCode);
        EntityManager anEntityManager = getEntityManager();
        Query query = anEntityManager.createNamedQuery("findByTransactionId");
        query.setParameter("transactionId", NabsConstants.HYBRIS_TRANSACTION_IDENTIFIER+transactionId);
        query.setParameter("transactionCode", transactionCode);
        
        List<PartResolutionInventoryBO> results = (List<PartResolutionInventoryBO>) query.getResultList();
        logger.debug("Found "+results.size()+" PartResolutionInventory results.");
        return results;
    }
    
    
    //@Override
    /*public List<PartResolutionInventoryBO> findByMasterOrderNumber(String masterOrderNumber, String transactionCode) {

        logger.debug("Retrieving PartResoltuionInventory via masterordernumber "+masterOrderNumber+" and txn code "+transactionCode);
        EntityManager anEntityManager = getEntityManager();
        CriteriaBuilder cb = anEntityManager.getCriteriaBuilder();
        CriteriaQuery<PartResolutionInventoryBO> cq = cb
                .createQuery(PartResolutionInventoryBO.class);
        Root<PartResolutionInventoryBO> invLocation = cq
                .from(PartResolutionInventoryBO.class);

        // readability
        Predicate masterOrderNumberEqual = cb.equal(invLocation.get("id").get("masterOrderNbr"), masterOrderNumber);
        Predicate txnCodeEqual = cb.equal(invLocation.get("id").get("txnCd"), transactionCode);

        cq.where(cb.and(masterOrderNumberEqual, txnCodeEqual));
        TypedQuery<PartResolutionInventoryBO> q = anEntityManager.createQuery(cq);
        final List<PartResolutionInventoryBO> result = q.getResultList();
        logger.debug("Found "+result.size()+" PartResolutionInventory results.");
        return result;
    }*/
    
}

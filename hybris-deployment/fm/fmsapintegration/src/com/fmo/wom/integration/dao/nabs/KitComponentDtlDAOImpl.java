package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.fmo.wom.common.dao.JpaDAODB2;
import com.fmo.wom.domain.nabs.KitComponentDtlBO;
import com.fmo.wom.domain.nabs.KitComponentDtlPK;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;

public class KitComponentDtlDAOImpl extends JpaDAODB2<KitComponentDtlPK, KitComponentDtlBO> implements KitComponentDtlDAO {
    
    private static Logger logger = Logger.getLogger(KitComponentDtlDAOImpl.class);

    @Override
    public List<KitComponentDtlBO> findByParentPart(PartResolutionInventoryPK parentPartResolutionPk) {
        
        logger.debug("Finding all records for "+parentPartResolutionPk);
        EntityManager anEntityManager = getEntityManager();
        Query query = anEntityManager.createNamedQuery("findByParentPartId");
        query.setParameter("transactionId", parentPartResolutionPk.getTransactionId());
        query.setParameter("transactionCode",parentPartResolutionPk.getTxnCd());
        query.setParameter("lineSeqNbr", parentPartResolutionPk.getLineSeqNbr().trim());
        return query.getResultList();
    }
}

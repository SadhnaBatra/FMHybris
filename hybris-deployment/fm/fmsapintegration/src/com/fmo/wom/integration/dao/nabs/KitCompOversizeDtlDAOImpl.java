package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.fmo.wom.common.dao.JpaDAODB2;
import com.fmo.wom.domain.nabs.KitCompOversizeDtlBO;
import com.fmo.wom.domain.nabs.KitCompOversizeDtlPK;
import com.fmo.wom.domain.nabs.KitComponentDtlPK;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;

public class KitCompOversizeDtlDAOImpl extends JpaDAODB2<KitCompOversizeDtlPK, KitCompOversizeDtlBO> implements KitCompOversizeDtlDAO {

    private static Logger logger = Logger.getLogger(KitCompOversizeDtlDAOImpl.class);

    @Override
    public List<KitCompOversizeDtlBO> findByParentKitComponent(KitComponentDtlPK kitComponentDtlPk) {
        
        logger.debug("Finding all records for "+kitComponentDtlPk);
        EntityManager anEntityManager = getEntityManager();
        Query query = anEntityManager.createNamedQuery("findByParentKitCompId");
        query.setParameter("transactionId", kitComponentDtlPk.getTransactionId());
        query.setParameter("transactionCode",kitComponentDtlPk.getTxnCd());
        query.setParameter("lineSeqNbr", kitComponentDtlPk.getLineSeqNbr());
        query.setParameter("compSeqNbr", kitComponentDtlPk.getCompSeqNbr());
        return query.getResultList();
    }
    

    @Override
    public List<KitCompOversizeDtlBO> findByParentKit(PartResolutionInventoryPK kitPk) {
        
        logger.debug("Finding all records for "+kitPk);
        EntityManager anEntityManager = getEntityManager();
        Query query = anEntityManager.createNamedQuery("findByParentKitId");
        query.setParameter("transactionId", kitPk.getTransactionId());
        query.setParameter("transactionCode",kitPk.getTxnCd());
        query.setParameter("lineSeqNbr", kitPk.getLineSeqNbr());
        return query.getResultList();
    }
}

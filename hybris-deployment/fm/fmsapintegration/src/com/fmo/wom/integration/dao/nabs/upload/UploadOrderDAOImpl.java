package com.fmo.wom.integration.dao.nabs.upload;

import com.fmo.wom.common.dao.JpaDAODB2;
import com.fmo.wom.domain.nabs.upload.UploadOrderBO;

public class UploadOrderDAOImpl extends JpaDAODB2<String, UploadOrderBO>
        implements UploadOrderDAO {

//    @Override
//    public List<UploadOrderDataBO> findByTransactionId(int transactionId) {
//
//        Query query = entityManager.createNamedQuery("findUploadOrderDataByTransactionId");
//        query.setParameter("transactionId", transactionId);
//        return (List<UploadOrderDataBO>) query.getResultList();
//    }
}

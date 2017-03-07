package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryBO;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;


public interface PartResolutionDAO extends BaseDAO<PartResolutionInventoryPK, PartResolutionInventoryBO>  {

    /*public List<PartResolutionInventoryBO> findByMasterOrderNumber(String masterOrderNumber, String transactionCode);*/
    public List<PartResolutionInventoryBO> findByTransactionId(int transactionId, String transactionCode);
    
}

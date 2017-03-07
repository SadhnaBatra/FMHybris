package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.domain.nabs.InventoryLocationBO;
import com.fmo.wom.domain.nabs.InventoryLocationPK;

public interface InventoryLocationDAO extends BaseDAO<InventoryLocationPK, InventoryLocationBO>  {
    
    public List<InventoryLocationBO> findByTransactionIdAndLineSeq(int transactionId, 
                                                int lineSequenceNumber, String transactionCode);
}


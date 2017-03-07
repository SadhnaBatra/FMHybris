package com.fmo.wom.integration.dao.nabs;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.domain.nabs.OrderHeaderBO;
import com.fmo.wom.domain.nabs.OrderHeaderPK;

public interface OrderHeaderDAO extends BaseDAO<OrderHeaderPK, OrderHeaderBO> {

    /*public String findErrorMessage(String masterOrderNumber) throws WOMNonUniqueResultException, WOMNoResultException;*/
    public OrderHeaderBO findByMasterOrderNumber(String masterOrderNumber);
}

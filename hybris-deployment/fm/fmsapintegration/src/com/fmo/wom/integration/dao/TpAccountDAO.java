package com.fmo.wom.integration.dao;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;
import com.fmo.wom.domain.TpAccountBO;

public interface TpAccountDAO extends BaseDAO<Long, TpAccountBO> {

	public TpAccountBO findTradingPartnerAccountByAcct(String acctCode)
			throws WOMNonUniqueResultException, WOMNoResultException;

	public TpAccountBO findActiveTradingPartnerAccountByAcct(String acctCode)
		throws WOMNonUniqueResultException, WOMNoResultException;

	public TpAccountBO findInactiveTradingPartnerAccountByAcct(String acctCode)
		throws WOMNonUniqueResultException, WOMNoResultException;
}

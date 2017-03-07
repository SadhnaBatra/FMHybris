package com.fmo.wom.business.as;

import com.fmo.wom.common.exception.WOMAuthenticationException;
import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.TpAccountBO;
import com.fmo.wom.integration.dao.TpAccountDAO;
import com.fmo.wom.integration.dao.TpAccountDAOImpl;

/**
 * @author joyr0011
 *
 */
public class TradingPartnerAS {

	//@Autowired
	private TpAccountDAO tpAcctDao;
	
	public TradingPartnerAS(){
		tpAcctDao = new TpAccountDAOImpl();
	}

	/**
	 * 
	 * @param ipoSecretKey
	 * @param accountCode
	 * @return TpAccountBO
	 * @throws WOMValidationException
	 * 
	 * Application Service to validate / fetch the IPO Trading Partner.
	 */
	public TpAccountBO getTradingPartnerAccount(String ipoSecretKey, String accountCode)
	        throws WOMValidationException {
	    
		TpAccountBO tpAcct = null;
		try {
			tpAcct = tpAcctDao.findActiveTradingPartnerAccountByAcct(accountCode);
		} catch (WOMNonUniqueResultException noUniqueResult) {
			throw new WOMValidationException("Account does not exist.");
		} catch (WOMNoResultException noResult) {
			throw new WOMValidationException("Account does not exist.");
		}
				
		if(!ipoSecretKey.equalsIgnoreCase(tpAcct.getTradingPartner().getIpoSecurityKey()))
			throw new WOMAuthenticationException("IPO Account is invalid");

		return tpAcct;
	}

}

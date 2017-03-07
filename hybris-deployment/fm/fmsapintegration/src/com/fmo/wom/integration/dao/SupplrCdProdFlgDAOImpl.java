package com.fmo.wom.integration.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Query;

import com.fmo.wom.common.dao.JpaDAOOracle;
import com.fmo.wom.domain.SupplierCdProductFlgBO;
import com.fmo.wom.integration.util.MessageResourceUtil;

public class SupplrCdProdFlgDAOImpl extends JpaDAOOracle<Long, SupplierCdProductFlgBO> 
		implements SupplrCdProdFlgDAO {
    
	public SupplrCdProdFlgDAOImpl(){
		super();
	}
	
	public List<SupplierCdProductFlgBO> findProdFlagBySupplCd(String supplrCd)  {
		Query query = entityManager.createNamedQuery("findProdFlagBySupplCd");
        query.setParameter("suppCode", supplrCd);
		List<Boolean> activeFlags = Arrays.asList(true, false);
		query.setParameter("activeFlags", activeFlags);
        List<SupplierCdProductFlgBO> optionList = (List<SupplierCdProductFlgBO>) query.getResultList();
		return optionList;
	}
    
	public List<SupplierCdProductFlgBO> findActiveProdFlagBySupplCd(String supplrCd)  {
		List<SupplierCdProductFlgBO> optionList = MessageResourceUtil.getSupplierCodeProductFlagList(supplrCd.toUpperCase());
		/*Query query = entityManager.createNamedQuery("findProdFlagBySupplCd");
        query.setParameter("suppCode", supplrCd);
		List<Boolean> activeFlags = Arrays.asList(true);
		query.setParameter("activeFlags", activeFlags);
        List<SupplierCdProductFlgBO> optionList = (List<SupplierCdProductFlgBO>) query.getResultList();*/
		return optionList;
	}
    
	public List<SupplierCdProductFlgBO> findInactiveProdFlagBySupplCd(String supplrCd)  {
		Query query = entityManager.createNamedQuery("findProdFlagBySupplCd");
        query.setParameter("suppCode", supplrCd);
		List<Boolean> activeFlags = Arrays.asList(false);
		query.setParameter("activeFlags", activeFlags);
        List<SupplierCdProductFlgBO> optionList = (List<SupplierCdProductFlgBO>) query.getResultList();
		return optionList;
	}
    
}
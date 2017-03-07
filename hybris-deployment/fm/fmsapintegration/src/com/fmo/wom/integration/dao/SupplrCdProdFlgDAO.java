package com.fmo.wom.integration.dao;

import java.util.List;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.domain.SupplierCdProductFlgBO;

public interface SupplrCdProdFlgDAO extends	BaseDAO<Long, SupplierCdProductFlgBO> {

	public List<SupplierCdProductFlgBO> findProdFlagBySupplCd(String supplrCd);

	public List<SupplierCdProductFlgBO> findActiveProdFlagBySupplCd(String supplrCd);

	public List<SupplierCdProductFlgBO> findInactiveProdFlagBySupplCd(String supplrCd);

}
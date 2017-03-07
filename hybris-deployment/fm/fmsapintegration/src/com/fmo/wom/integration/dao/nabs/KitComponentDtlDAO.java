package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.domain.nabs.KitComponentDtlBO;
import com.fmo.wom.domain.nabs.KitComponentDtlPK;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;

public interface KitComponentDtlDAO extends BaseDAO<KitComponentDtlPK, KitComponentDtlBO> {

    public List<KitComponentDtlBO> findByParentPart(PartResolutionInventoryPK parentPartResolutionPk);
}

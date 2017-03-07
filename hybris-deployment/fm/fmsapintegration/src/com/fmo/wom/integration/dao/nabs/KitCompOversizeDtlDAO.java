package com.fmo.wom.integration.dao.nabs;

import java.util.List;

import com.fmo.wom.common.dao.BaseDAO;
import com.fmo.wom.domain.nabs.KitCompOversizeDtlBO;
import com.fmo.wom.domain.nabs.KitCompOversizeDtlPK;
import com.fmo.wom.domain.nabs.KitComponentDtlPK;
import com.fmo.wom.domain.nabs.PartResolutionInventoryPK;

public interface KitCompOversizeDtlDAO extends BaseDAO<KitCompOversizeDtlPK, KitCompOversizeDtlBO> {

    /**
     * Original query to get these, but we're seeing some weird stuff, possibly due to big endian / little
     * endian on the comp seq number.  Use this in multiple client environment with caution.
     * @param kitComponentDtlPk
     * @return
     */
    public List<KitCompOversizeDtlBO> findByParentKitComponent(KitComponentDtlPK kitComponentDtlPk);
    
    public List<KitCompOversizeDtlBO> findByParentKit(PartResolutionInventoryPK kitPk);
}

package com.fmo.wom.integration.dao.nabs;

public interface MasterOrderNumberDAO {


    public String getMasterOrderNumber();
    public String getMasterOrderNumber(String callingSystem);
    public String getRandomMasterOrderNumber();
    public void releaseEntityManager();
}

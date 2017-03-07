package com.fmo.wom.integration.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.enums.Market;
//import com.fmo.wom.integration.dao.DistributionCenterDAO;

public class DistributionCenterCache {

    private static Logger logger = Logger.getLogger(DistributionCenterCache.class);
    private static Map<Market, MarketDistributionCenterCache> marketDistCenterCacheMap;
    private static Map<Long, DistributionCenterBO> distCenterIdMap;
    private static Map<String, DistributionCenterBO> distCenterCodeMap;
    private static DistributionCenterCache distributionCenterCache = null;
    
    private DistributionCenterCache() {
        loadDistributionCenters();
    }
    
    public static DistributionCenterCache getDistributionCenterCache(){
    	if(distributionCenterCache == null){
    		distributionCenterCache = new DistributionCenterCache();
    	}
    	return distributionCenterCache;
    }
    
//    private DistributionCenterDAO distributionCenterDAO;
    
 /*   public DistributionCenterCache(DistributionCenterDAO distributionCenterDAO) {
        setDistributionCenterDAO(distributionCenterDAO);
        loadDistributionCenters();
    }
    
    public DistributionCenterDAO getDistributionCenterDAO() {
        return distributionCenterDAO;
    }

    public void setDistributionCenterDAO(DistributionCenterDAO distributionCenterDAO) {
        this.distributionCenterDAO = distributionCenterDAO;
    }
    */
    public static DistributionCenterBO getDistributionCenterWithDefault(Market market, String dcCode) {
       return marketDistCenterCacheMap.get(market).getDistributionCenterWithDefault(dcCode);
    }
    
    protected static DistributionCenterBO getDistributionCenter(Market market, String dcCode) {
        return marketDistCenterCacheMap.get(market).getDistributionCenter(dcCode);
    }
    
    public static DistributionCenterBO getDistributionCenter(Long dcId) {
        return distCenterIdMap.get(dcId);
    }
    
    public DistributionCenterBO getDistributionCenter(String dcCode) {
        return distCenterCodeMap.get(dcCode.trim());
    }
    
	protected static DistributionCenterBO getActiveDistributionCenter(String market, String dcCode) {
        return marketDistCenterCacheMap.get(market).getActiveDistributionCenter(dcCode);
    }
    
    public static DistributionCenterBO getActiveDistributionCenter(Long dcId){
        DistributionCenterBO distCenter = distCenterIdMap.get(dcId);
        if (distCenter != null && distCenter.isActive()) {
            return distCenter;
        }
        
        return null;
    }

    public static DistributionCenterBO getActiveDistributionCenterWithDefault(Market market, String dcCode) {
        return marketDistCenterCacheMap.get(market).getActiveDistributionCenterWithDefault(dcCode);
     }
    
    public static Set<String> getAllDCCodes(Market market) {
        return marketDistCenterCacheMap.get(market).getAllDCCodes();
    }
    
    /*private synchronized void loadDistributionCenters() {
        
        distCenterIdMap = new HashMap<Long, DistributionCenterBO>(22);
        
        //_2015_madness
        //List<DistributionCenterBO> allDistributionCenters = getDistributionCenterDAO().findAll();
        List<DistributionCenterBO> allDistributionCenters = new ArrayList<DistributionCenterBO>();
        
        
        // put them in the map based on id
        for (DistributionCenterBO aDC : allDistributionCenters) {
            // i want the stuff instantiated
            String aDCString = "" + aDC;
            logger.info("Loaded DC: {}", aDCString);
            distCenterIdMap.put(aDC.getDistCenterId(), aDC);

        }

        // now create each market keyed cache
        marketDistCenterCacheMap = new HashMap<Market, MarketDistributionCenterCache>();
        for (Market aMarket : Market.values()) {
            MarketDistributionCenterCache aMarketCache = new MarketDistributionCenterCache(aMarket, allDistributionCenters);
            marketDistCenterCacheMap.put(aMarket, aMarketCache);
        }
    }*/
    
    private static void loadDistributionCenters(){
    	List<String> distCtrDetailsList  = readDistributionCenterFile();
    	if(distCtrDetailsList != null && (!distCtrDetailsList.isEmpty())){
    		distCenterCodeMap = new HashMap<String, DistributionCenterBO>(22);
    		String dcCode = null;
    		String dcName = null;
    		for(String distCtrDetail:distCtrDetailsList){
    			StringTokenizer strTokenizer = new StringTokenizer(distCtrDetail, ",");
				dcCode = strTokenizer.nextToken();
				dcName = strTokenizer.nextToken();
				DistributionCenterBO aDc = new DistributionCenterBO();
				aDc.setCode(dcCode);
				aDc.setName(dcName);
				distCenterCodeMap.put(dcCode, aDc);
    		}
    	}
    }
    
    private static List<String> readDistributionCenterFile(){
		BufferedReader buffReader = null;
		List<String> distCtrDetailsList = null;
		try {
			buffReader = new BufferedReader(new FileReader(new File("../WOMConfig/DistributionCenter.csv")));
			distCtrDetailsList = new ArrayList<String>();
			String lineFrmFile = null;
			while((lineFrmFile=buffReader.readLine())!=null){
				distCtrDetailsList.add(lineFrmFile);
			}
			buffReader.close();
		} catch (Exception e) {
			logger.error("Exception while reading DistributionCenter CSV File : ", e);
		}
		logger.debug("readDistributionCenterFile :: File Read successfully");
    	return distCtrDetailsList;
    }

    /**
     * Not sure if this is still relevant, but leaving it on here for peoples use.  This will return ALL
     * DCs in the DB from all markets!
     * @return
     */
    public static Collection<DistributionCenterBO> getAllDistributionCenters() {
        //return distCenterIdMap.values();
    	return distCenterCodeMap.values();
    }
    
    public static void main(String[] args) {
		//System.out.println(DistributionCenterCache.getDistributionCenterCache().getAllDistributionCenters());
		//System.out.println(DistributionCenterCache.getDistributionCenterCache().getDistributionCenter("019"));
		//System.out.println(DistributionCenterCache.getDistributionCenterCache().getDistributionCenter("2152"));
		
	}
}

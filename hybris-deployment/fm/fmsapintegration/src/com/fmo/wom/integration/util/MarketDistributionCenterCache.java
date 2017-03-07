package com.fmo.wom.integration.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.enums.Market;

public class MarketDistributionCenterCache {

    private static Logger logger = Logger.getLogger(MarketDistributionCenterCache.class);
    private Map<String, DistributionCenterBO> distCenterMap;
    
    MarketDistributionCenterCache(Market market, List<DistributionCenterBO> allDistributionCenters) {
        loadDistributionCenters(market, allDistributionCenters);
    }
    
    DistributionCenterBO getDistributionCenterWithDefault(String dcCode) {
        DistributionCenterBO distCenter = distCenterMap.get(dcCode);
        
        if (distCenter == null) {
            distCenter = createDefaultDistributionCenter(dcCode);
        }
        return distCenter;
     }
    
    DistributionCenterBO getDistributionCenter(String dcCode) {
        return distCenterMap.get(dcCode);
     }
    
    DistributionCenterBO getActiveDistributionCenter(String dcCode) {
        DistributionCenterBO distCenter = distCenterMap.get(dcCode);
        if (distCenter != null && distCenter.isActive()) {
            return distCenter;
        }
        
        return null;
     }
    
    DistributionCenterBO getActiveDistributionCenterWithDefault(String dcCode) {
        DistributionCenterBO distCenter = distCenterMap.get(dcCode);
        if (distCenter != null && distCenter.isActive()) {
            return distCenter;
        }
        
        return createDefaultDistributionCenter(dcCode);
     }
    
    Set<String> getAllDCCodes() {
        return distCenterMap.keySet();
    }
    
    
    synchronized void loadDistributionCenters(Market market, List<DistributionCenterBO> allDistributionCenters) {
        
         distCenterMap = new HashMap<String, DistributionCenterBO>();
         
         // put them in the map
         for (DistributionCenterBO aDC : allDistributionCenters ) {
             if (aDC.getMarketCode() != market) {
                 // not for the market we have been told to create cache for.  Skip it
                 continue;
             }
             
             // i want the stuff instantiated
             String aDCString = "" + aDC;
             logger.info("Loaded DC: "+ aDCString);
             distCenterMap.put(aDC.getCode(), aDC);
        }
    }

    private static DistributionCenterBO createDefaultDistributionCenter(String dcCode) {
        logger.error("Problem retrieving DC code "+ dcCode+ ". Continuing with processing.");
        DistributionCenterBO distCenter = new DistributionCenterBO();
        distCenter.setName(DistributionCenterBO.NOT_FOUND);
        distCenter.setCode(dcCode);
        distCenter.setAddress(createDefaultAddress());
        return distCenter;
    }
    
    private static AddressBO createDefaultAddress() {
        
        AddressBO addr = new AddressBO();
        addr.setAddrLine1("123 main st");
        addr.setCity("City");
        CountryBO country = new CountryBO();
        country.setCountryName("Country");
        addr.setCountry(country);
        addr.setZipOrPostalCode("12345");
        return addr;
    }

}

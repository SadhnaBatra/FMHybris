package com.fmo.wom.integration.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.CarrierBO;

public class CarrierCache {

	private static Logger logger = Logger.getLogger(CarrierCache.class);
	
	private static CarrierCache carrierCache = null;
	
	private static HashMap<String, CarrierBO> carrierCodeMap = null;
    
    private CarrierCache() {
        loadCarriers();
    }
    
    public static CarrierCache getCarrierCache(){
    	if(carrierCache == null){
    		carrierCache = new CarrierCache();
    	}
    	return carrierCache;
    }
	
    private static void loadCarriers(){
    	List<String> carrierDetailsList  = readCarrierFile();
    	if(carrierDetailsList != null && (!carrierDetailsList.isEmpty())){
    		carrierCodeMap = new HashMap<String, CarrierBO>(4);
    		String carrierCode = null;
    		String carrierName = null;
    		for(String carrierDetail:carrierDetailsList){
    			StringTokenizer strTokenizer = new StringTokenizer(carrierDetail, ",");
				carrierCode = strTokenizer.nextToken().trim();
				carrierName = strTokenizer.nextToken().trim();
				CarrierBO aCarrier = new CarrierBO();
				aCarrier.setCarrierCode(carrierCode);
				aCarrier.setCarrierName(carrierName);
				carrierCodeMap.put(carrierCode, aCarrier);
    		}
    	}
    }
    
    private static List<String> readCarrierFile(){
		BufferedReader buffReader = null;
		List<String> carrierDetailsList = null;
		try {
			buffReader = new BufferedReader(new FileReader(new File("../WOMConfig/Carrier.csv")));
			carrierDetailsList = new ArrayList<String>();
			String lineFrmFile = null;
			while((lineFrmFile=buffReader.readLine())!=null){
				carrierDetailsList.add(lineFrmFile);
			}
			buffReader.close();
		} catch (Exception e) {
			logger.error("Exception while reading Carrier CSV File : ", e);
			e.printStackTrace();
		}
		logger.debug("readCarrierFile :: File Read successfully");
    	return carrierDetailsList;
    }
    
    public CarrierBO getcarrier(String aCarrierCode){
    	return carrierCodeMap.get(aCarrierCode.trim());
    }
    
	public static void main(String[] args) {
		//System.out.println(" Carrier "+CarrierCache.getCarrierCache().getcarrier("0006001450"));
	}

}

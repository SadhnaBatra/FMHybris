package com.fmo.wom.integration.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.fmo.wom.common.util.MessageResourceConstants;
import com.fmo.wom.domain.ExternalSystemStatusBO;
import com.fmo.wom.domain.ExternalSystemStatusBO.StatusCategory;
import com.fmo.wom.domain.ExternalSystemStatusPK;
import com.fmo.wom.domain.SupplierCdProductFlgBO;
import com.fmo.wom.domain.enums.ExternalSystem;
//import org.springframework.context.MessageSource;
//import com.fmo.wom.integration.dao.ExternalSystemStatusDAO;

public class MessageResourceUtil {

    public static final String SUCCESS = MessageResourceConstants.SUCCESS;
    
    public static final String NOT_ALLOWED_TO_ORDER = MessageResourceConstants.NOT_ALLOWED_TO_ORDER;  
    public static final String PART_NOT_FOUND = MessageResourceConstants.PART_NOT_FOUND; 
    public static final String PART_IS_PRERELEASE = MessageResourceConstants.PART_IS_PRERELEASE;
    public static final String OBSOLETE_USE_TO_COMPLETION = MessageResourceConstants.OBSOLETE_USE_TO_COMPLETION;
    public static final String OBSOLETE_RETURN_ALLOWED = MessageResourceConstants.OBSOLETE_RETURN_ALLOWED;
    public static final String OBSOLETE_NO_RETURN_ALLOWED = MessageResourceConstants.OBSOLETE_NO_RETURN_ALLOWED;
    public static final String OBSOLETE = MessageResourceConstants.OBSOLETE;
    public static final String PART_NO_RETURN_FOR_CREDIT = MessageResourceConstants.PART_NO_RETURN_FOR_CREDIT;
    public static final String PART_BEING_DISCONTINUED = MessageResourceConstants.PART_BEING_DISCONTINUED;
    
    public static final String INVALID_SCAC_CODE_OR_SHIPPING_METHOD = MessageResourceConstants.INVALID_SCAC_CODE_OR_SHIPPING_METHOD;
    public static final String NO_INTERNAL_SHIPCODE_MATCH_FOR_SCAC_CODE = MessageResourceConstants.NO_INTERNAL_SHIPCODE_MATCH_FOR_SCAC_CODE;
    
    public static final String MISSING_STANDARD_COST = MessageResourceConstants.MISSING_STANDARD_COST;
    public static final String MISSING_PRICE = MessageResourceConstants.MISSING_PRICE;
    public static final String NO_SOURCE_SPECIFIED = MessageResourceConstants.NO_SOURCE_SPECIFIED;
    public static final String NO_VALID_BOM = MessageResourceConstants.NO_VALID_BOM;
    public static final String NO_ITEM_CATEGORY_AVAILABLE = MessageResourceConstants.NO_ITEM_CATEGORY_AVAILABLE;
    public static final String KIT_NEEDS_CONFIG = MessageResourceConstants.KIT_NEEDS_CONFIG;
    public static final String SETUP_ERROR = MessageResourceConstants.SETUP_ERROR;
    public static final String NON_STOCK = MessageResourceConstants.NON_STOCK;
    public static final String VENDOR_DIRECT = MessageResourceConstants.VENDOR_DIRECT; 
    public static final String SOLD_IN_SETS_ONLY = MessageResourceConstants.SOLD_IN_SETS_ONLY;
    public static final String SOLD_IN_MULTIPLES = MessageResourceConstants.SOLD_IN_MULTIPLES;
    public static final String MINIMUM_QUANTITY_NOT_MET = MessageResourceConstants.MINIMUM_QUANTITY_NOT_MET;
    public static final String MULITPLE_PARTS_FOUND = MessageResourceConstants.MULITPLE_PARTS_FOUND;
    public static final String PART_SUPERCEDED = MessageResourceConstants.PART_SUPERCEDED;
    public static final String TOO_MANY_FOUND = MessageResourceConstants.TOO_MANY_FOUND;
    public static final String BAD_ACCOUNT = MessageResourceConstants.BAD_ACCOUNT;
    public static final String BAD_INPUT = MessageResourceConstants.BAD_INPUT;
    public static final String NO_KITS_ALLOWED = MessageResourceConstants.NO_KITS_ALLOWED;
    public static final String CRITICAL_ERROR = MessageResourceConstants.CRITICAL_ERROR;
    public static final String CARTER_PARTS = MessageResourceConstants.CARTER_PARTS; 
    public static final String NO_RETURN = MessageResourceConstants.NO_RETURN; 
	public static final String VINTAGE_PART = MessageResourceConstants.VINTAGE_PART;
	public static final String VINTAGE_PART_ECC = MessageResourceConstants.VINTAGE_PART_ECC;

    public static final String NO_INVENTORY = MessageResourceConstants.NO_INVENTORY;
    public static final String INSUFFICIENT_INVENTORY = MessageResourceConstants.INSUFFICIENT_INVENTORY;
    public static final String DISCONTINUED_AND_INSUFFICIENT_INVENTORY = MessageResourceConstants.DISCONTINUED_AND_INSUFFICIENT_INVENTORY;
    public static final String DISCONTINUED_AND_NO_INVENTORY = MessageResourceConstants.DISCONTINUED_AND_NO_INVENTORY;

    public static final String PART_FOUND_IN_MULTIPLE_LOCATIONS = MessageResourceConstants.PART_FOUND_IN_MULTIPLE_LOCATIONS;

    public static final String MISSING_AAIA_BRAND = MessageResourceConstants.MISSING_AAIA_BRAND;
    public static final String MISSING_PART_NUMBER = MessageResourceConstants.MISSING_PART_NUMBER;
    public static final String MISSING_REQ_QTY = MessageResourceConstants.MISSING_REQ_QTY;
    public static final String ZERO_REQ_QTY = MessageResourceConstants.ZERO_REQ_QTY;
    public static final String DUP_SEQ_NUMBER = MessageResourceConstants.DUP_SEQ_NUMBER;
    
    public static final String PROCESS_ORDER_ERROR = MessageResourceConstants.PROCESS_ORDER_ERROR;
    
    public static final String PRICE_UNAVAILABLE = MessageResourceConstants.PRICE_UNAVAILABLE;
    public static final String PRICETYPE_IS_NA = MessageResourceConstants.PRICETYPE_IS_NA;
    public static final String REASONABLE_QUANTITY_THRESHOLD = MessageResourceConstants.REASONABLE_QUANTITY_THRESHOLD;
    public static final String LARGE_QUANTITY_THRESHOLD = MessageResourceConstants.LARGE_QUANTITY_THRESHOLD;
    
    //private ExternalSystemStatusDAO externalSystemStatusDAO;
    //private static MessageSource webMessageSource;
    //private static MessageSource ipoMessageSource;
    
    private static Map<String, ExternalSystemStatusBO> statusKeyMap;
    private static Map<ExternalSystem, Map<String, ExternalSystemStatusBO>> statusCodeMapByExternalSystemMap;
    
    //  separate these into categories
    private static Map<StatusCategory, Map<String, ExternalSystemStatusBO>> statusCodeMapByStatusCategory;
    
    private static Map<ExternalSystem, Map<String, ExternalSystemStatusBO>> statusKeyMapByExternalSystemMap;
    
    //../WOMConfig/SUPPLIERCD_PRODUCTFLG.csv
    private static Map<String, List<SupplierCdProductFlgBO>> supplierCdPrdFlgMap = null; 

    public MessageResourceUtil() {
    /*	URL clsLoader =  getClass().getClassLoader().getResource("ExtSystemStatus.CSV");
    	//System.out.println(" Class Loader path "+clsLoader.getPath());
    	extSystemStatusFilepath = clsLoader.getPath();
    	loadSystemStatuses();*/
    }
    
    public static void main(String[] args) {
	}
    
   private static Logger logger = Logger.getLogger(MessageResourceUtil.class);
    
   static{
	   logger.info("com.fmo.wom.integration.util.MessageResourceUtil Class is being loaded ...");	
	   logger.info("Current Working Dir=  "+System.getProperty("user.dir"));
	   	//ClassLoader clsLoader = MessageResourceUtil.class.getClassLoader();
	   	InputStream resourceStream = null;//clsLoader.getResourceAsStream("com/fmo/wom/integration/util/ExtSystemStatus.CSV");		
    	if(statusKeyMap == null){
    		loadSystemStatuses(resourceStream);
    	}
    	logger.info("Successfully loaded the com.fmo.wom.integration.util.MessageResourceUtil Class");
    	if(statusKeyMap != null && (!statusKeyMap.isEmpty())){
    		logger.info("Successfully loaded MessageResourceUtil statusKeyMap...");
    	}
    	logger.info("loading SUPPLIERCD_PRODUCTFLG");
    	if(supplierCdPrdFlgMap == null){
    		loadSupplierCodeProductFlag();
    	}
    	if(supplierCdPrdFlgMap != null && (! supplierCdPrdFlgMap.isEmpty())){
    		logger.info("Successfully loaded supplierCdPrdFlgMap ...");
    	}
    }
   
   
    
   /* public ExternalSystemStatusDAO getExternalSystemStatusDAO() {
        return externalSystemStatusDAO;
    }

    public void setExternalSystemStatusDAO(ExternalSystemStatusDAO externalSystemStatusDAO) {
        this.externalSystemStatusDAO = externalSystemStatusDAO;
    }
    */
    /*
    public MessageSource getWebMessageSource() {
        return webMessageSource;
    }

    public void setWebMessageSource(MessageSource webMessageSource) {
        this.webMessageSource = webMessageSource;
    }

    public MessageSource getIpoMessageSource() {
        return ipoMessageSource;
    }

    public void setIpoMessageSource(MessageSource ipoMessageSource) {
        this.ipoMessageSource = ipoMessageSource;
    }
*/
    public static ExternalSystemStatusBO getEverestExtSystemStatusViaStatusKey(String statusKey) {
        return getExtSystemStatusViaStatusKey(statusKey, ExternalSystem.EVRST);
     }
     
	public static ExternalSystemStatusBO getNabsExtSystemStatusViaStatusKey(String statusKey) {
         return getExtSystemStatusViaStatusKey(statusKey, ExternalSystem.NABS);
     }

     public static ExternalSystemStatusBO getExtSystemStatusViaStatusKey(String statusKey) {
         
         ExternalSystemStatusBO aStatusBO = statusKeyMap.get(statusKey);
         if (aStatusBO == null) {
             throw new IllegalArgumentException("Fatal Error: statusKey " + statusKey + " is not defined");
         }
        return aStatusBO;
     }
     
     public static ExternalSystemStatusBO getExtSystemStatusViaStatusKey(String statusKey, ExternalSystem externalSystem) {
         
         Map<String, ExternalSystemStatusBO> collectionToUse = statusKeyMapByExternalSystemMap.get(externalSystem);
         if (collectionToUse == null) {
             throw new IllegalArgumentException("Fatal Error: ExternalSystem " + externalSystem + " is not defined in database");
         }
         
         ExternalSystemStatusBO aStatusBO = collectionToUse.get(statusKey);
         if (aStatusBO == null) {
             // this wasn't found in the list for this external system.  Try without.
             aStatusBO = getExtSystemStatusViaStatusKey(statusKey);
         }
        return aStatusBO;
     }
    

     public static String getRPSapStatusKeyViaStatusCode(String statusCode) {
         return getStatusKeyViaStatusCode(statusCode, ExternalSystem.RP1);
     }
     
    public static String getEverestStatusKeyViaStatusCode(String statusCode) {
       return getStatusKeyViaStatusCode(statusCode, ExternalSystem.EVRST);
    }
    
    public static String getNabsStatusKeyViaStatusCode(String statusCode) {
        return getStatusKeyViaStatusCode(statusCode, ExternalSystem.NABS);
    }

    public static String getBpcsStatusKeyViaStatusCode(String statusCode) {
        return getStatusKeyViaStatusCode(statusCode, ExternalSystem.BPCS);
    }

    public static String getStatusKeyViaStatusCode(String statusCode, ExternalSystem externalSystem) {
        
    	/*if(statusCodeMapByExternalSystemMap == null){
    		new MessageResourceUtil();
    	}*/
        Map<String, ExternalSystemStatusBO> collectionToUse = statusCodeMapByExternalSystemMap.get(externalSystem);
        if (collectionToUse == null) {
            throw new IllegalArgumentException("Fatal Error: ExternalSystem " + externalSystem + " is not defined in database");
        }
        
        ExternalSystemStatusBO aStatusBO = collectionToUse.get(statusCode);
        if (aStatusBO == null) {
            throw new IllegalArgumentException("Fatal Error: StatusCode " + statusCode + " is not defined for " + externalSystem);
        }
       return aStatusBO.getStatusKey();
    }
   /* 
    public static String getWebMessage(String messageKey, List<Object> params) {
        return getMessage(webMessageSource, messageKey, params);
    }
    
    public static String getIPOMessage(String messageKey, List<Object> params) {
        return getMessage(ipoMessageSource, messageKey, params);
    }
   */ 
    public static String getNabsPackageStatusKeyViaCode(String orderStatus) {
        return getPackageStatusKeyViaCode(orderStatus);
    }

    public static String getSapPackageStatusKeyViaCode(String orderStatus) {
        return getPackageStatusKeyViaCode(orderStatus);
    }

    private static String getPackageStatusKeyViaCode(String packingStatus) {
        
        // get the map for the category
        Map<String, ExternalSystemStatusBO> collectionToUse = statusCodeMapByStatusCategory.get(StatusCategory.PACKAGE_STATUS);
        if (collectionToUse == null) {
            throw new IllegalArgumentException("Fatal Error: StatusCategory.PACKAGE_STATUS " + StatusCategory.PACKAGE_STATUS + " is not defined in database");
        }
        
        ExternalSystemStatusBO aStatusBO = collectionToUse.get(packingStatus);
        if (aStatusBO == null) {
            throw new IllegalArgumentException("Fatal Error: StatusCode " + packingStatus + " is not defined.");
        }
        return aStatusBO.getStatusKey();
    }
    
  /*  private static String getMessage(MessageSource source, String messageKey, List<Object> params) {
    	if (params == null)
    		params = new ArrayList<Object>();
        return source.getMessage(messageKey, params.toArray(), messageKey, null);
    }
   */
    
    public static synchronized void loadSystemStatuses(InputStream resourceStream) {
        
        List<String> extSysStatusList = readExternalSytemStatus(); //resourceStream
        List<ExternalSystemStatusBO> allSystemStatuses = null; 
        
        if(extSysStatusList !=null && (!extSysStatusList.isEmpty())){
        
        	statusCodeMapByExternalSystemMap = new HashMap<ExternalSystem, Map<String, ExternalSystemStatusBO>>();
            statusCodeMapByStatusCategory = new HashMap<StatusCategory, Map<String, ExternalSystemStatusBO>>();
            statusKeyMapByExternalSystemMap = new HashMap<ExternalSystem, Map<String, ExternalSystemStatusBO>>();
            statusKeyMap = new HashMap<String, ExternalSystemStatusBO>();
            
        	allSystemStatuses = new ArrayList<ExternalSystemStatusBO>();
        	String systemRetCd = null;
			String externalSystemCd = null;
			String statusKey = null;
			String statusCategory = null;
			String ipoItemStatusCode = null;
			
			ExternalSystemStatusBO extSysStatusBo = null;
			ExternalSystemStatusPK extSysStatusPk = null;
        	
			for(String extSysStatus :extSysStatusList){
        		StringTokenizer strTokenizer = new StringTokenizer(extSysStatus, ",");
				systemRetCd = strTokenizer.nextToken();
				externalSystemCd = strTokenizer.nextToken();
				statusKey = strTokenizer.nextToken();
				statusCategory = strTokenizer.nextToken();
				ipoItemStatusCode = strTokenizer.nextToken();
				
				extSysStatusPk = new ExternalSystemStatusPK();
				extSysStatusPk.setExternalSystem(ExternalSystem.getFromCode(externalSystemCd));
				extSysStatusPk.setStatusCode(systemRetCd);
				extSysStatusPk.setStatusKey(statusKey);
				
				extSysStatusBo = new ExternalSystemStatusBO();	
				extSysStatusBo.setId(extSysStatusPk);
				extSysStatusBo.setIpoItemStatusCode(ipoItemStatusCode);
				extSysStatusBo.setStatusCategory(StatusCategory.valueOf(statusCategory));
				
				allSystemStatuses.add(extSysStatusBo);
        	}
        
	        // put them in the right spots
	        for (ExternalSystemStatusBO anExternalSystemStatus : allSystemStatuses ) {
	            
	            // put them in the main map
	            statusKeyMap.put(anExternalSystemStatus.getStatusKey(), anExternalSystemStatus);
	            
	            // populate the status code map
	            insertIntoStatusCodeMap(anExternalSystemStatus);
	            
	            // populate the status category map
	            insertIntoStatusCategoryMap(anExternalSystemStatus);
	            
	            // populate the status key map
	            insertIntoStatusKeyMap(anExternalSystemStatus);
	        }
	        //System.out.println("Successfully Loaded the CSV");
        }
    }
    
    private static List<String> readExternalSytemStatus(/*InputStream resourceStream*/){
		BufferedReader buffReader = null;
		List<String> extSysStatusList = null;
		try {
			//buffReader = new BufferedReader(new FileReader(csvFile));
			//buffReader = new BufferedReader(new InputStreamReader(resourceStream));
			buffReader = new BufferedReader(new FileReader(new File("../WOMConfig/ExtSystemStatus.CSV")));
			extSysStatusList = new ArrayList<String>();
			String lineFrmFile = null;
			while((lineFrmFile=buffReader.readLine())!=null){
				extSysStatusList.add(lineFrmFile);
			}
			//resourceStream.close();
			buffReader.close();
		} catch (Exception e) {
			logger.error("Exception while reading ExternalSystemStatus CSV File : ", e);
			e.printStackTrace();
		}
		logger.info("readExternalSytemStatus :: File Read successfully");
    	return extSysStatusList;
    }
    
    private static void insertIntoStatusKeyMap( ExternalSystemStatusBO anExternalSystemStatus) {

        ExternalSystem extSystem = anExternalSystemStatus.getExternalSystem();
        Map<String, ExternalSystemStatusBO> statusKeyMapForExtSystem = statusKeyMapByExternalSystemMap.get(extSystem);
        if (statusKeyMapForExtSystem == null) {
            statusKeyMapForExtSystem = new HashMap<String, ExternalSystemStatusBO>();
            statusKeyMapByExternalSystemMap.put(extSystem, statusKeyMapForExtSystem);
        }
        // Put it in the map 
        statusKeyMapForExtSystem.put(anExternalSystemStatus.getStatusKey(), anExternalSystemStatus);
        
    }

    private static void insertIntoStatusCategoryMap( ExternalSystemStatusBO anExternalSystemStatus) {
        StatusCategory category = anExternalSystemStatus.getStatusCategory();
        Map<String, ExternalSystemStatusBO> statusCodeMapForCategory = statusCodeMapByStatusCategory.get(category);
        if (statusCodeMapForCategory == null) {
            statusCodeMapForCategory = new HashMap<String, ExternalSystemStatusBO>();
            statusCodeMapByStatusCategory.put(category, statusCodeMapForCategory);
        }
        // Put it in the map if there is a status code
        if (anExternalSystemStatus.getStatusCode() != null) {
            statusCodeMapForCategory.put(anExternalSystemStatus.getStatusCode(), anExternalSystemStatus);
        }
        
    }

    private static void insertIntoStatusCodeMap(ExternalSystemStatusBO anExternalSystemStatus) {
        ExternalSystem extSystem = anExternalSystemStatus.getExternalSystem();
        Map<String, ExternalSystemStatusBO> statusCodeMapForExtSystem = statusCodeMapByExternalSystemMap.get(extSystem);
        if (statusCodeMapForExtSystem == null) {
            statusCodeMapForExtSystem = new HashMap<String, ExternalSystemStatusBO>();
            statusCodeMapByExternalSystemMap.put(extSystem, statusCodeMapForExtSystem);
        }
        // Put it in the map if there is a status code
        if (anExternalSystemStatus.getStatusCode() != null) {
            statusCodeMapForExtSystem.put(anExternalSystemStatus.getStatusCode(), anExternalSystemStatus);
        }
    }
    
    private static void loadSupplierCodeProductFlag() {
    	List<String> supplierCdProdFlgList = readSupplierCodeProductFlagFrmFile();
	   	if(supplierCdProdFlgList !=null && (!supplierCdProdFlgList.isEmpty())){
	   		supplierCdPrdFlgMap = new HashMap<String, List<SupplierCdProductFlgBO>>();
	   		for(String supplierCdProdFlg :supplierCdProdFlgList){
	    		StringTokenizer strTokenizer = new StringTokenizer(supplierCdProdFlg, ",");
				String supplierCdProdFlgId = strTokenizer.nextToken();
	    		String supplierCd = strTokenizer.nextToken(); 
	    		String prodFlg = strTokenizer.nextToken();
	    		String externalSystemCd = strTokenizer.nextToken();
	    		String brandDesc =  strTokenizer.nextToken();
	    		String activeFlg = strTokenizer.nextToken();
	    		
	    		List<SupplierCdProductFlgBO> prodFlgBOList = supplierCdPrdFlgMap.get(supplierCd.trim());
	    		if(prodFlgBOList == null){
	    			prodFlgBOList = new ArrayList<SupplierCdProductFlgBO>();
	    			supplierCdPrdFlgMap.put(supplierCd.trim().toUpperCase(), prodFlgBOList);
	    		}
	    		SupplierCdProductFlgBO prdFlgBo = new SupplierCdProductFlgBO();
	    		prdFlgBo.setSupplierCdProdFlgId(Long.valueOf(supplierCdProdFlgId.trim()));
	    		prdFlgBo.setSupplierCd(supplierCd.trim());
	    		prdFlgBo.setProdFlg(prodFlg.trim());
	    		prdFlgBo.setExternalSystemCd(externalSystemCd.trim());
	    		prdFlgBo.setBrandDesc(brandDesc.trim());
	    		prdFlgBo.setActive("Y".equalsIgnoreCase(activeFlg.trim()));
	    		
	    		prodFlgBOList.add(prdFlgBo);
	   		}
	   	}
    }
    
    private static List<String> readSupplierCodeProductFlagFrmFile(){
		BufferedReader buffReader = null;
		List<String> supplierCodeProductFlagList = null;
		try {
			//buffReader = new BufferedReader(new FileReader(csvFile));
			//buffReader = new BufferedReader(new InputStreamReader(resourceStream));
			buffReader = new BufferedReader(new FileReader(new File("../WOMConfig/SUPPLIERCD_PRODUCTFLG.csv")));
			supplierCodeProductFlagList = new ArrayList<String>();
			String lineFrmFile = null;
			while((lineFrmFile=buffReader.readLine())!=null){
				supplierCodeProductFlagList.add(lineFrmFile);
			}
			//resourceStream.close();
			buffReader.close();
		} catch (Exception e) {
			logger.error("Exception while reading SUPPLIERCD_PRODUCTFLG CSV File : ", e);
			e.printStackTrace();
		}
		logger.info("SUPPLIERCD_PRODUCTFLG :: File Read successfully");
		//remove the header line
		supplierCodeProductFlagList.remove(0);
    	return supplierCodeProductFlagList;
    } 
    
    public static List<SupplierCdProductFlgBO> getSupplierCodeProductFlagList(String aSupplierrCode){
    	return supplierCdPrdFlgMap.get(aSupplierrCode.trim());
    }
}

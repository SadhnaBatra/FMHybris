package com.fmo.wom.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.domain.enums.Environment;

public class PropertiesUtil {

    private static Logger logger = Logger.getLogger(PropertiesUtil.class);
    
	// These are the current known property categories, defined in properties files.
	private static Properties commonProperties;
	private static Properties dbProperties;
	private static Properties sapFileProperties;
	private static Properties IPOMessageProperties;

	// Change this variable to false if you would like this to pick up the settings in the sap.properties
	// for local testing.  If this is set to true, the sap connection properties will be loaded out of the DB.	
	private static boolean useDBForSAP = true;
	
	// This the map for all prop categories dynamically loaded from the database
	private static Map<String, Properties> categoriesMap = new HashMap<String, Properties>();
	
	// Keys for categories
	public static final String NABS_KEY = "NABS";
	public static final String COMMON_KEY = "common";
	public static final String DB_KEY = "db";
	public static final String SAP_KEY = "SAP";
	public static final String APPLICATION_USAGE_PROFILING_KEY = "APPLICATION_USAGE_PROFILING";
	public static final String ORDER_KEY = "ORDER";
	public static final String MARKET_KEY = "MARKET";
	public static final String SYSTEM_KEY = "SYSTEM";
	public static final String APP_ENV_KEY = "APP_ENV";
	public static final String APP_DEBUG_KEY = "APP_DEBUG";

	
	public PropertiesUtil() {
		super();
	}


	public static void main(String[] args) {
	}
	
	static {
		logger.info(" Loading Common,Db and Sap Properties :: PropertiesUtil ....");
	    load();
	}
	
	public static List<String> getAllCategoriesList() {
	    List<String> categories = new ArrayList<String>();
	    categories.add(COMMON_KEY);
	    categories.add(DB_KEY);
	    
	    synchronized (categoriesMap) {
	        categories.addAll(categoriesMap.keySet());
	    }
	    
	    return categories;
	}
	
	
	public static synchronized void load() {
		
	    commonProperties = null;
		dbProperties = null;
		sapFileProperties = null;
		IPOMessageProperties = null;
		
		synchronized (categoriesMap) {
		    //categoriesMap = null;
		    loadAllAppProperties();
		    /*AppPropertiesUtil appPropsBean = new AppPropertiesUtil();
		    //AppPropertiesUtil appPropsBean = ApplicationContextProvider.getAppPropertiesBean();
		    categoriesMap = appPropsBean.getCategoriesMap();*/
		}
		
		
		//PropertiesLoader commonProps = ApplicationContextProvider.getCommonPropertiesBean();
		//commonProperties = commonProps.getProperties();
		commonProperties = loadCommonProperties();

		/*PropertiesLoader dbProps = ApplicationContextProvider.getDbPropertiesBean();
		dbProperties = dbProps.getProperties();
		*/
		dbProperties = loadDbProperties();
		/*PropertiesLoader sapFileProps = ApplicationContextProvider.getSapPropertiesBean();
        sapFileProperties = sapFileProps.getProperties();*/
		sapFileProperties = loadSapFileProperties();
		IPOMessageProperties = loadIPOMessageProperties();
	}	
	
	private static Properties loadCommonProperties(){
		Properties lCommonProperties =  new Properties();
		try {
			lCommonProperties.load(new BufferedReader(new FileReader(new File("../WOMConfig/common.properties"))));
			logger.info(" Read common.properties ..."+lCommonProperties);
		} catch (FileNotFoundException fnfe) {
			logger.error("common.properties file not found ",fnfe);
		} catch (IOException ioe) {
			logger.error("IO Exception while loading common.properties file ",ioe);
		}
		return lCommonProperties;
	}
	private static Properties loadIPOMessageProperties()
	{
		final Properties loadIPOMessageProperties = new Properties();
		try
		{
			loadIPOMessageProperties.load(new BufferedReader(new FileReader(new File("../WOMConfig/ipomessages.properties"))));
			logger.info(" Read common.properties ..." + loadIPOMessageProperties);
		}
		catch (final FileNotFoundException fnfe)
		{
			logger.error("common.properties file not found ", fnfe);
		}
		catch (final IOException ioe)
		{
			logger.error("IO Exception while loading common.properties file ", ioe);
		}
		return loadIPOMessageProperties;
	}
	private static Properties loadDbProperties(){
		Properties commomPrperties =  new Properties();
		try {
			commomPrperties.load(new BufferedReader(new FileReader(new File("../WOMConfig/db.properties"))));
			logger.info(" Read db.properties ..."+commomPrperties);
		} catch (FileNotFoundException fnfe) {
			logger.error("db.properties file not found ",fnfe);
		} catch (IOException ioe) {
			logger.error("IO Exception while loading db.properties file ",ioe);
		}
		return commomPrperties;
	}
	
	private static Properties loadSapFileProperties(){
		Properties commomPrperties =  new Properties();
		try {
			commomPrperties.load(new BufferedReader(new FileReader(new File("../WOMConfig/sap.properties"))));
			logger.info(" Read sap.properties ..."+commomPrperties);
		} catch (FileNotFoundException fnfe) {
			logger.error("sap.properties file not found ",fnfe);
		} catch (IOException ioe) {
			logger.error("IO Exception while loading sap.properties file ",ioe);
		}
		return commomPrperties;
	}
	
	
	public static synchronized void loadAllAppProperties() {
        //AppPropertiesUtil appPropsBean = ApplicationContextProvider.getAppPropertiesBean();
		//appPropsBean.initialize();
        synchronized (categoriesMap) {
            categoriesMap = null;
            categoriesMap = AppPropertiesUtil.getCategoriesMap();
        }
	}
	
	/*public static void refresh() {
		ApplicationContextProvider.refresh();	
	}*/
	
	public static void refreshCategory(String category) {
        
	    synchronized (categoriesMap) {
	        Properties oldProps = categoriesMap.get(category);
	        if (oldProps == null) {
	            // couldn't find this one
	            //logger.error("Unable to find Properties for category {}", category);
	            return;
	        }
	    
	        // now refresh this category
	        Properties newProps;
	        try {
	            newProps = AppPropertiesUtil.loadProperties(category);
	        } catch (Exception e) {
	            // unable to reload.  Continue to use the old one.
	            //logger.error("Unable to reload Properties for category {}. Error={}", category, e.getMessage());
	            newProps = oldProps;
	        }
	        categoriesMap.remove(category);
	        categoriesMap.put(category, newProps);
	    }
    }
	
	public static String getCommonProperty(String key) {
		return commonProperties.getProperty(key);
	}
	
	public static String getCommonProperty(String key, String defaultValue) {
	   return getPropertyWithDefaultValue(commonProperties, key, defaultValue);
    }
	public static String getIPOMessageProperty(final String key)
	{
		return IPOMessageProperties.getProperty(key);
	}

	public static String getIPOMessageProperty(final String key, final String defaultValue)
	{
		return getPropertyWithDefaultValue(IPOMessageProperties, key, defaultValue);
	}
	public static String getDBProperty(String key) {
		return dbProperties.getProperty(key);
	}
	
	public static String getDBProperty(String key, String defaultValue) {
	    return getPropertyWithDefaultValue(dbProperties, key, defaultValue);
    }
	
	public static String getSapProperty(String key) {
	    if (useDBForSAP == true) {
	        return getProperty(SAP_KEY, key);
	    } else {
	        return sapFileProperties.getProperty(key); 
	    }
	}

    public static String getSapProperty(String key, String defaultValue) {
        if (useDBForSAP == true) {
            synchronized (categoriesMap) {
                return getPropertyWithDefaultValue(categoriesMap.get(SAP_KEY), key, defaultValue);
            }
        } else {
            return getPropertyWithDefaultValue(sapFileProperties, key, defaultValue);
        }
    }
    
    public static String getNabsProperty(String key) {
        return getProperty(NABS_KEY, key);
    }
	
    public static String getNabsProperty(String key, String defaultValue) {
        synchronized (categoriesMap) {
            return getPropertyWithDefaultValue(categoriesMap.get(NABS_KEY), key, defaultValue);
        }
    }
    
    public static String getOrderProperty(String key) {
        return getProperty(ORDER_KEY, key);
    }
    
    public static String getOrderProperty(String key, String defaultValue) {
        synchronized (categoriesMap) {
            return getPropertyWithDefaultValue(categoriesMap.get(ORDER_KEY), key, defaultValue);
        }
    }
    
    public static List<String> getOrderPropertyList(String propertyPrefixKey) {
        List<String> propertyList = new ArrayList<String>();
        int index = 1;
        String key = propertyPrefixKey + "." + index;
        while (GenericValidator.isBlankOrNull(getOrderProperty(key)) == false) {
            propertyList.add(getOrderProperty(key));
            index++;
            key = propertyPrefixKey + "." + index;
        }
        return propertyList;
    }
    
    public static String getMarketProperty(String key) {
        return getProperty(MARKET_KEY, key);
    }
    
    public static String getMarketProperty(String key, String defaultValue) {
        synchronized (categoriesMap) {
            return getPropertyWithDefaultValue(categoriesMap.get(MARKET_KEY), key, defaultValue);
        }
    }
    
    public static String getSystemProperty(String key) {
        return getProperty(SYSTEM_KEY, key);
    }
    
    public static String getSystemProperty(String key, String defaultValue) {
        synchronized (categoriesMap) {
            return getPropertyWithDefaultValue(categoriesMap.get(SYSTEM_KEY), key, defaultValue);
        }
    }
	
    public static Environment getEnvironment() {
        String envString;
        synchronized (categoriesMap) {
            envString = getProperty(SYSTEM_KEY, APP_ENV_KEY);
        }
        
        if (GenericValidator.isBlankOrNull(envString)) {
            envString = "PROD";
        }
        
        return Environment.valueOf(envString);
    }
    
	public static Properties getProperties(String category) {
        
        if (COMMON_KEY.equals(category)) {
            return commonProperties;
        } else if (DB_KEY.equals(category)) {
            return dbProperties;
        } else {
            synchronized (categoriesMap) {
                return categoriesMap.get(category);
            }
        }
    }
	
	public static String getProperty(String category, String key, String defaultValue) {
        String returnValue = getProperty(category, key);
	    if (returnValue == null) 
            return defaultValue;
	    return returnValue.trim();
    }
	
	
	/**
	 * utility method to faciliate default values
	 * @param props the properties object to retrieve propery from
	 * @param key key of the property we want
	 * @param defaultValue the value to use if the property for key is not found
	 * @return if found, the property value for key.  If not found, defaultValue is returned.
	 */
	private static String getPropertyWithDefaultValue(Properties props, String key, String defaultValue) {
	    
	    String valStr = props != null ? props.getProperty(key) : null;
        if (valStr == null) {
        	logger.debug("getPropertyWithDefaultValue() - Property value not found for "+key+" returning defaultValue= "+defaultValue);
        	return defaultValue;
        }
            
        return valStr.trim();
	}

	 
    private static String getProperty(String category, String key) {
        
        Properties props = getProperties(category);
        if (props != null) {
        	String value = props.getProperty(key); 
        	if(value == null){
        		logger.debug("getProperty() - Property value not found for "+category+":"+key);
        	}
        	return (value !=null)?value.trim():value;
        }
        return null;
    }


  
}

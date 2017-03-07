package com.fmo.wom.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AppPropertiesUtil {
	private static Logger logger = Logger.getLogger(AppPropertiesUtil.class);
	
	private static Map<String, Properties> categoriesMap;
	//private static PropertyDAO propertyDAO;
	//private DataSource dataSource;
	private List<String> categories;
	private String tableName;
	private String categoryColumn;
	private String keyColumn;
	private String valueColumn;

	/*public void setPropertyDAO(PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}*/
	
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setCategoryColumn(String categoryColumn) {
		this.categoryColumn = categoryColumn;
	}

	public void setKeyColumn(String keyColumn) {
		this.keyColumn = keyColumn;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public static synchronized void initialize() throws Exception {
	       
	    Properties properties;
	    categoriesMap = new HashMap<String, Properties>();
	    /*APPLICATION_USAGE_PROFILING
	    APP_DEBUG
	    BATCH
	    DATAPURGING
	    MARKET
	    NABS
	    ORDER
	    SAP
	    SERVER_INFO
	    SYSTEM
	     */    
	    // get the categories
	    //List<String> categories = propertyDAO.findAllCategories();
	    List<String> categories = new ArrayList<String>(Arrays.asList("APPLICATION_USAGE_PROFILING","APP_DEBUG","BATCH","DATAPURGING",
	    																"MARKET","NABS","ORDER","SAP","SERVER_INFO","SYSTEM"));
	    
	    for (String aCategory: categories) {
	            
	        // aCategory now holds one of our categories
	        // Instantiate a properties map for it and put in the categories map
	        properties = loadProperties(aCategory);
	        categoriesMap.put(aCategory, properties);
	    }
	}
	
	public static synchronized Properties loadProperties(String category) throws Exception {
        
	    Properties properties = new Properties();
	    String appPropertiesFileName = "../WOMConfig/AppProperties-"+category.toUpperCase()+".properties";
	    try {
			properties.load(new BufferedInputStream(new FileInputStream(new File(appPropertiesFileName))));
			logger.info("AppPropertiesUtil loaded :: "+appPropertiesFileName);
		} catch (FileNotFoundException fnfe) {
			logger.error("File not found :: Exception ",fnfe);
		} catch (IOException ioe) {
			logger.error("IO Exception while loading "+appPropertiesFileName,ioe);
			ioe.printStackTrace();
		}
        // Now get all the properties for that category and put in the props map
        /*List<PropertyBO> propsForCategory = propertyDAO.findByCategory(category);
        for (PropertyBO aPropertyBO : propsForCategory) {
            String aKey = aPropertyBO.getId().getKey();
            String value = aPropertyBO.getValue();
            properties.put(aKey, value);
        }*/
        return properties;
    }
	
//	
//	public synchronized void initializeOldWay() throws Exception {
//		
//		Properties properties;
//		categoriesMap = new HashMap<String, Properties>();
//		
//		// get the categories
//		Configuration categoryConfig = new DatabaseConfiguration(dataSource, tableName, categoryColumn, keyColumn);
//		Iterator<String> it = categoryConfig.getKeys();
//		while (it.hasNext()) {
//			String aCategory = it.next();
//			
//			// aCategory now holds one of our categories
//			// Instantiate a properties map for it and put in the categores map
//			properties = new Properties();
//			categoriesMap.put(aCategory, properties);
//			
//			// Now get all the properties for that category and put in the props map
//			Configuration propertyConfig = new DatabaseConfiguration(dataSource, tableName, categoryColumn, keyColumn, valueColumn, aCategory);
//			
//			Iterator<String> keyIt = (Iterator<String>) propertyConfig.getKeys();
//			while (keyIt.hasNext()) {
//				String aKey = keyIt.next();
//				String value = (String) propertyConfig.getProperty(aKey);
//				properties.put(aKey, value);
//			}
//		}
//	}

	public static String getProperty(String categoryName, String name) {
		Map categoryMap = (Map)categoriesMap.get(categoryName);
		return (String) categoryMap.get(name);
	}
	
	static Map<String, Properties>  getCategoriesMap() {
		if(categoriesMap == null){
			try {
				initialize();
			} catch (Exception e) {
				logger.error("Exception While loading the Application Properties");
				e.printStackTrace();
			}
		}
		return categoriesMap;
	}
}

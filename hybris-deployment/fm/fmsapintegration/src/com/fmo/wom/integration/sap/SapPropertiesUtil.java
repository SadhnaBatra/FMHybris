package com.fmo.wom.integration.sap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fmo.wom.common.util.PropertiesUtil;
import com.sap.conn.jco.ext.DestinationDataProvider;

/**
 * Utility class providing methods and encapsulating keys for access to the sap.properties file
 *
 */
public class SapPropertiesUtil {
	private static Logger logger = Logger.getLogger(SapPropertiesUtil.class);

	public static final String ENVIRONMENTS_KEY = "envs.list";
	public static final String DEFAULT_ENV_KEY = "default.env";
	
	// Env specific prop keys
	public static final String USERID_KEY = "userid";
	public static final String PASSWORD_KEY = "passwd";
	public static final String SYSNUMBER_KEY = "sysnumber";
	public static final String CLIENT_KEY = "client";
	public static final String ASHOST_KEY = "ashost";
	public static final String MSHOME_KEY = "mshost";
	public static final String R3NAME_KEY = "r3name";
	public static final String GROUP_KEY = "group";
	public static final String TYPE_KEY = "type";
	
	public static List<String> getDestinationKeys() {
		
		String keysString = PropertiesUtil.getSapProperty(ENVIRONMENTS_KEY);
		logger.info("Configured SAP Envirnoments .. "+keysString);
		List<String> destinationKeys = new ArrayList<String>();
		
		String[] keys = keysString.split(",");
		for (int x=0; x<keys.length; x++) {
			destinationKeys.add(keys[x]);
		}
	 
		return destinationKeys;
	}

	/**
	 * Get the defined default destination key from the properties
	 * file.  If not defined, the first elements in the envs list 
	 * is used.
	 * @return default key
	 */
	public static String getDefaultDestinationKey() {
		
		String defaultKey = PropertiesUtil.getSapProperty(DEFAULT_ENV_KEY);
		if (defaultKey == null) {
			// didn't find one defined.  Use the envs list
			List<String> destinationKeys = getDestinationKeys();
			if (destinationKeys != null && destinationKeys.size() > 0) {
					defaultKey = destinationKeys.get(0);
			}
		}
		
		return defaultKey;
	}

	/**
	 * Load the properties for the given sap destination and associate them to the required
	 * SAP property names
	 * @param destinationName name of destination to load props for
	 * @return the SAP specific key/value properties
	 */
	public static Properties loadProperties(String destinationName) {
		
		Properties destinationProperties = new Properties();
	    if ("test".equals(destinationName) ) {
	        
	        destinationProperties.setProperty(DestinationDataProvider.JCO_USER, "WEBWOM");
	        destinationProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "1234comm");
	        destinationProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
	        destinationProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "sfldmilx271.federalmogul.com");
	        destinationProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "010");	
	        /*destinationProperties.setProperty(DestinationDataProvider.JCO_MSHOST, MSHOME_KEY);
	        destinationProperties.setProperty(DestinationDataProvider.JCO_R3NAME, R3NAME_KEY);
	        destinationProperties.setProperty(DestinationDataProvider.JCO_GROUP, GROUP_KEY);*/
            
	        return destinationProperties;
	    }
	    
		//Properties destinationProperties = new Properties();
		
		setDestinationProperty(destinationProperties,DestinationDataProvider.JCO_USER,destinationName,USERID_KEY);
		setDestinationProperty(destinationProperties,DestinationDataProvider.JCO_PASSWD,destinationName,PASSWORD_KEY);
		setDestinationProperty(destinationProperties,DestinationDataProvider.JCO_SYSNR,destinationName,SYSNUMBER_KEY);
		
		//for single connection
		setDestinationProperty(destinationProperties, DestinationDataProvider.JCO_ASHOST,destinationName,ASHOST_KEY);
		setDestinationProperty(destinationProperties, DestinationDataProvider.JCO_CLIENT,destinationName,CLIENT_KEY);
		
		//for logon group
		setDestinationProperty(destinationProperties,DestinationDataProvider.JCO_MSHOST,destinationName,MSHOME_KEY);
		setDestinationProperty(destinationProperties,DestinationDataProvider.JCO_R3NAME,destinationName,R3NAME_KEY);
		setDestinationProperty(destinationProperties,DestinationDataProvider.JCO_GROUP,destinationName,GROUP_KEY);
		
		/* for connection pooling */
		destinationProperties.put(DestinationDataProvider.JCO_POOL_CAPACITY, "6");
		destinationProperties.put(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");
		
		return destinationProperties;
		
		
	}

	public static String getDestinationType(String destinationKey) {
	    return getEnvironmentProperty(destinationKey, TYPE_KEY);
	}
	
	/**
	 * Get the sap.property value for the given destination and key.  The property can
	 * be defined stand alone with no destination or with the destination.
	 * <br>
	 * Example:<br>
	 * 
	 * property1=value1  - this is a stand alone property
	 * destination1.property1=value2 - this is a destination specific property.
	 * 
	 * @param destination the destination key
	 * @param key the property key
	 * @return the value for the given property key.  The destination specific value has precedence over 
	 * the stand alone if both are defined.
	 */
	public static String getEnvironmentProperty(String destination, String key) {
		
		String fullKey = destination + "." + key;
		
		String envProperty = PropertiesUtil.getSapProperty(fullKey);
		if (envProperty == null) {
			// did not find it.  See if the property is defined standalone
			envProperty = PropertiesUtil.getSapProperty(key);
		}
		
		return envProperty;
	}
	
	/**
	 * Helper to get value for the given destination and sap property keys into the given Properties
	 * object with the given SAP specific jcoProperyKey.  This is essentially to avoid trying to put
	 * null objects into a Properties object.  If the property is not defined, we don't put it in.
	 * Its up to SAP to know what properties are required or not in the Properties, not us.
	 */
	private static void setDestinationProperty( Properties destinationProperties, String jcoPropertyKey, 
												String destinationName, String sapPropertyKey) {
		
		String value = getEnvironmentProperty(destinationName,sapPropertyKey);
		
		if (value != null && (!value.isEmpty()) && (!"null".equalsIgnoreCase(value))) {
			destinationProperties.put(jcoPropertyKey, value);
		}
	}
}

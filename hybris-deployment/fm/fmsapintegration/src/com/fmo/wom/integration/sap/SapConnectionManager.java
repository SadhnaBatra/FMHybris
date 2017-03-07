package com.fmo.wom.integration.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.Environment;

public class SapConnectionManager { 
	
	protected static Logger logger = Logger.getLogger(SapConnectionManager.class);
	private static SapConnectionManager instance;
	
	private Map<SAPDestinationType, List<String>> destinationListByType;
	private Map<SAPDestinationType,String> currentDestinationMap;
	SapDestinationDataProvider sapDestinationDataProvider;
	
	private SapConnectionManager() {
	    currentDestinationMap = new HashMap<SAPDestinationType,String>();
		initializeDestinations();
		setDefaultDestinations();
	}
	
	public static SapConnectionManager getInstance() {
		if (instance == null) {
			synchronized (SapConnectionManager.class) {
				if(instance == null){
					logger.info(" SapConnectionManager : getInstance() ");
					instance = new SapConnectionManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Construct all destinations defined in the sap.properties file
	 */
	private void initializeDestinations() {
		logger.info("initializeDestinations():start");
		// create the destination data provider we use
		sapDestinationDataProvider = SapDestinationDataProvider.getInstance();
			
		// set the lists
		destinationListByType = new HashMap<SAPDestinationType, List<String>>();
		List<String> sapDestinationKeys = SapPropertiesUtil.getDestinationKeys();
		//List<String> sapDestinationKeys = new ArrayList<String>();
        //sapDestinationKeys.add("test");
		
		for (String aDestinationKey : sapDestinationKeys ) {
			
			//aDestinationKey ="test";
		    // load the properties for this destination and add it to the provider
			logger.info("initializeDestinations: "+aDestinationKey);
		    Properties destinationProperties = SapPropertiesUtil.loadProperties(aDestinationKey);
		    logger.info(" "+aDestinationKey+" Property Values: "+destinationProperties);
		    sapDestinationDataProvider.addDestination(aDestinationKey, destinationProperties);
		    SAPDestinationType type = SAPDestinationType.valueOf(SapPropertiesUtil.getDestinationType(aDestinationKey));
		    List<String> destinationList = destinationListByType.get(type);
		    if (destinationList == null) {
		        destinationList = new ArrayList<String>();
		        destinationListByType.put(type, destinationList);
		    }
		    destinationList.add(aDestinationKey);
		}
		logger.info("initializeDestinations():end");
	}

	public SapDestinationDataProvider getSapDestinationDataProvider() {
		return sapDestinationDataProvider;
	}

	public void setSapDestinationDataProvider(SapDestinationDataProvider sapDestinationDataProvider) {
		this.sapDestinationDataProvider = sapDestinationDataProvider;
	}

	/**
	 * Set the destination to use for all connection request to the destination defined
	 * by the given key
	 * @param destinationKey name of the destination to use
	 */
	public void setCurrentDestination(SAPDestinationType type, String destinationKey) {
		logger.info("Inside SapConnectionManager: setCurrentDestination");
	    currentDestinationMap.remove(type);
	    List<String> destinationList = destinationListByType.get(type);
		if (destinationList.contains(destinationKey)) {
			currentDestinationMap.put(type, destinationKey);
			sapDestinationDataProvider.update(destinationKey);
			// try to reach the new one
			logger.info("SapConnectionManager: setCurrentDestination - try to reach the new one @@@ "+destinationKey);
			if (pingJCoDestination(destinationKey) == false) {
			    // can't reach it.  Reset to default logic which finds one.
				logger.info("SapConnectionManager: setCurrentDestination - can't reach it.  Reset to default logic which finds one @@@ "+ type);
			    setDefaultDestination(type);
			}
		}
	}
	
	/**
	 * returns the currently defined destination data provider
	 * @return
	 */
	public String getCurrentDestination(SAPDestinationType type) {
		return currentDestinationMap.get(type);
	}
	
	/**
	 * Get the list of currently defined destinations
	 * @return list of keys for the defined destinations in this env
	 */
	public Map<SAPDestinationType, List<String>> getDestinationKeys() {
		return destinationListByType;
	}
	
	/**
	 * get a JCoDestination for use to send commands
	 * @return the JCoDestination for the current destination defined
	 * @throws JCoException
	 */
	public JCoDestination getJCoDestination(SAPDestinationType type) throws JCoException, WOMExternalSystemException {
	    
		//Since SapconnectionManger is singleton
		if(currentDestinationMap != null && (!currentDestinationMap.isEmpty()) && currentDestinationMap.get(type) != null){
			return JCoDestinationManager.getDestination(currentDestinationMap.get(type));	
		} else {
			synchronized (this) {
				// check if we have anything defined for this type.  If not, retry to get one that's working
			    if (currentDestinationMap.get(type) == null ) {
			        initializeDestinations();
			        setDefaultDestination(type);
			    }
			    
			    // if its still not there, we got an issue:
			    if (currentDestinationMap.get(type) == null ) {
			        
			        StringBuffer keyList = new StringBuffer();
			        for (String aKey : destinationListByType.get(type)) {
			            keyList.append(aKey).append(", ");
			        }
			        logger.error("Unable to connect to any defined SAP servers for "+type+ "! Tried ["+ keyList.toString()+"]");
			        throw new WOMExternalSystemException("No destinations currently accessible for " + type + ".  Tried ["+keyList.toString()+"]");
			    }
			}
		}
		
		return JCoDestinationManager.getDestination(currentDestinationMap.get(type));
	}
	
	@Override
	public void finalize() {
	    try {
            Environment.unregisterDestinationDataProvider(sapDestinationDataProvider);
        } catch (Exception e) { 
            // this is ok;
            ;
        }
	}
	
	private void setDefaultDestinations() {
		logger.info("@@ Inside setDefaultDestination : SapConnectionManager @@");//Added by Abhijit
	    for (SAPDestinationType type : SAPDestinationType.values()) {
	        setDefaultDestination(type);
	    }
	}
	
	/**
	 * This calculates the default destination and sets the current destination to that.
	 * The envs list supports multiple entries but it isn't required.  If the envs list only
	 * has one entry, that one is used and no default definition is required.
	 * 
	 * If there are more than one in the list, the default entry if specified is used. If no
	 * default entry is specified (or doesn't exist), the first entry in the list is used.
	 */
	private void setDefaultDestination(SAPDestinationType type) {
		
	    String keyToUse = null;
	    List<String> destinationList = destinationListByType.get(type);
	    boolean foundGoodDestination = false;
	    
	    // if the list is empty, this is a fruitless exercise
		if (destinationList != null && destinationList.size() == 0) {
			logger.error("Unable to connect to any defined SAP servers for "+type+ "!");
		}
		// If there is only one in the map, try that.
		else if (destinationList != null && destinationList.size() == 1) {
			
			Iterator<String> keyIt = destinationList.iterator();	
			keyToUse = destinationList.get(0);
			logger.info("Only one valid destination in map for "+type+ " Using "+keyToUse);
			// if there's only one, ping to see what's what
			if (pingJCoDestination(keyToUse)) {
			    setCurrentDestination(type, keyToUse);
			    foundGoodDestination = true;
			}
		}
	      // There must be more than one in the list.  Figure out which one to use
		else {
		    boolean foundKey = false;
		    // First try the default
		    String defaultDestinationKey = SapPropertiesUtil.getDefaultDestinationKey();
		    if (defaultDestinationKey != null) {
		        // found a key - try to use it.
		    	logger.info("Found defined default destination for "+type+ " in map.  Using "+ defaultDestinationKey);
		        if (pingJCoDestination(defaultDestinationKey)) {
		            keyToUse = defaultDestinationKey;
		            foundKey = true;
		            foundGoodDestination = true;
		        }
		    } 

		    // There may not have been a default or it was unable to be used.  Go through 
		    // the list
		    if (foundKey == false) {
		        
		        for (String aDestination : destinationList ) {
		        	//aDestination="test";
		            if (pingJCoDestination(aDestination)) {
	                    keyToUse = aDestination;
	                    foundKey = true;
	                    foundGoodDestination = true;
	                    logger.info("Connected to destination = using "+keyToUse);
	                    break;
	                }
		        }
		    }
		    if (foundKey) {
		        // set this one
		    	//type= SAPDestinationType.EVEREST; //@@@ Added by Abhijit @@@
		        setCurrentDestination(type, keyToUse);
		    }
		}
		
	}
	
	public boolean pingJCoDestination(String destinationKey) {

		logger.info("@@ Inside pingJCoDestination "+ destinationKey);
        // some kind of sanity check here prior to allowing use
        try {
            JCoDestinationManager.getDestination(destinationKey).ping();
            logger.info("Destination "+ destinationKey+ " successfully pinged ");
            return true;
        } 
        catch (JCoException e) {
        	logger.error("Unable to ping destination {"+destinationKey+"}.  Not able to connect: {"+destinationKey+"} " , e);
            return false;
        }
	}
	
	public enum SAPDestinationType {
	    EVEREST,
	    //RP;
	}
	
	
}

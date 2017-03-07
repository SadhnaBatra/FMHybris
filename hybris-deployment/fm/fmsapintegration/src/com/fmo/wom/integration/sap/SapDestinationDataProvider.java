package com.fmo.wom.integration.sap;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

/**
 * This is the implementation of DestinationDataProvider for accessing FMO SAP
 */
public class SapDestinationDataProvider implements DestinationDataProvider {
	
	protected static Logger logger = Logger.getLogger(SapDestinationDataProvider.class);

	private DestinationDataEventListener eventListener;
	private Map<String, Properties> destinationPropertiesMap = new HashMap<String,Properties>();
	
	private static SapDestinationDataProvider instance = null;
	
	private SapDestinationDataProvider() {
	    try {
	        Environment.unregisterDestinationDataProvider(this);
	    } catch (Throwable e) {
	        ; // this is ok
	    }
	    try {
	        Environment.registerDestinationDataProvider(this);
	    } catch (Throwable e) {
            ; // this is ok
        }
	}
	
	public static SapDestinationDataProvider getInstance() {
		if (instance == null ) {
			synchronized (SapDestinationDataProvider.class) {
				if(instance == null){
					instance = new SapDestinationDataProvider();
				}
			}
		}
		return instance;
	}
	/**
	 * Implementation for DestinationDataProvider.  Returns this destinations properties
	 */
	public Properties getDestinationProperties(String destinationName) {

		return destinationPropertiesMap.get(destinationName);
	}

	/**
	 * Set the event listener on this destination
	 */
	public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
		this.eventListener = eventListener;
	}

	public boolean supportsEvents() {
		return true;
	}

	public void update(String destinationName) {
		if (destinationPropertiesMap.get(destinationName) != null && eventListener != null) {
			eventListener.updated(destinationName);
		}
	}
	
	public void delete(String destinationName) {
		if (eventListener != null) {
			eventListener.deleted(destinationName);
		}
	}
	
	public void addDestination(String destinationName, Properties destinationProperties ) {
		destinationPropertiesMap.put(destinationName, destinationProperties);
		update(destinationName);
	}
	
	public void showProperties() {
		
		Iterator it = destinationPropertiesMap.keySet().iterator();
		while (it.hasNext()) {
			String destinationKey = (String) it.next();
			Properties aDestinationProperties = destinationPropertiesMap.get(destinationKey);
			Enumeration e = aDestinationProperties.keys();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				logger.debug("Showing properties for Destination: "+ destinationKey);
				logger.debug("\t "+ key+ " = "+ aDestinationProperties.getProperty(key));
			}
		}
	}

	@Override
	public void finalize() {
	    try {
	        Environment.unregisterDestinationDataProvider(this);
	    } catch (Exception e) { 
	        // this is ok;
	        ;
	    }
	}
}

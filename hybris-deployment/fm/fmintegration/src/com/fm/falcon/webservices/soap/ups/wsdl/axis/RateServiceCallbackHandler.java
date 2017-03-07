/**
 * RateServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:08:57 BST)
 */
package com.fm.falcon.webservices.soap.ups.wsdl.axis;


/**
 *  RateServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class RateServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public RateServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public RateServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for processRate method
     * override this method for handling normal response from processRate operation
     */
    public void receiveResultprocessRate(
        com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from processRate operation
     */
    public void receiveErrorprocessRate(java.lang.Exception e) {
    }
}

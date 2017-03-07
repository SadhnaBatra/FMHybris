/**
 * RateErrorMessage.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.3  Built on : May 30, 2016 (04:08:57 BST)
 */
package com.fm.falcon.webservices.soap.ups.wsdl.axis;

public class RateErrorMessage extends java.lang.Exception {
    private static final long serialVersionUID = 1472061719571L;
    private com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.Errors faultMessage;

    public RateErrorMessage() {
        super("RateErrorMessage");
    }

    public RateErrorMessage(java.lang.String s) {
        super(s);
    }

    public RateErrorMessage(java.lang.String s, java.lang.Throwable ex) {
        super(s, ex);
    }

    public RateErrorMessage(java.lang.Throwable cause) {
        super(cause);
    }

    public void setFaultMessage(
        com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.Errors msg) {
        faultMessage = msg;
    }

    public com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.Errors getFaultMessage() {
        return faultMessage;
    }
}

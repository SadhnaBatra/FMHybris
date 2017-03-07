package com.fmo.wom.integration.nabs.action;

public interface NabsOutputParseable {

    public void parseOutputString(String outputString);
    public boolean isReturnStatusSuccessful();
    public String getStatusCode();
    public String getStatusMessage();
        
}

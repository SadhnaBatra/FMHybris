/**
 * 
 */
package com.fmo.wom.integration.nabs.ims;

public class NabsIMSUser {
    private String id = "";
    private String password = "";
    public NabsIMSUser(String id, String pwd)
    {
        super();
        if(null != id)
            this.id = id;
        if(null != pwd)
            this.password = pwd;
    }
    public String getId()
    {
        return id;
    }
    public String getPassword()
    {
        return password;
    }
    public String toString()
    {
        String str = "NabsImsUser: "+getId()+" and "+getPassword();
        return str;
    }

}

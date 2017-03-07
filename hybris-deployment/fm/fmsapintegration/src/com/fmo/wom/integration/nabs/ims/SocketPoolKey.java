/**
 * 
 */
package com.fmo.wom.integration.nabs.ims;

/**
 * Implements key for the socket pool
 * @author amarnr85
 *
 */
public class SocketPoolKey {

    private String  hostName = "";
    private int     portNumber = 0;
    private String  key = "";
    
    /**
     * @param hostName
     * @param portNumber
     */
    public SocketPoolKey(String hostName, int portNumber) {
        super();
        if(null != hostName) {
            this.hostName = hostName;
            this.key = hostName + ":" + portNumber;
        } else {
            this.key = this.hostName + ":" + portNumber;
        }
        if(0 != portNumber)
            this.portNumber = portNumber;
        
    }
    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }
    /**
     * @param hostName the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    /**
     * @return the portNumber
     */
    public int getPortNumber() {
        return portNumber;
    }
    /**
     * @param portNumber the portNumber to set
     */
    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((hostName == null) ? 0 : hostName.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + portNumber;
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof SocketPoolKey)) {
            return false;
        }
        SocketPoolKey other = (SocketPoolKey) obj;
        if (hostName == null) {
            if (other.hostName != null) {
                return false;
            }
        } else if (!hostName.equals(other.hostName)) {
            return false;
        }
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (portNumber != other.portNumber) {
            return false;
        }
        return true;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SocketPoolKey [hostName=" + hostName + ", portNumber="
                + portNumber + ", key=" + key + ", getHostName()="
                + getHostName() + ", getPortNumber()=" + getPortNumber()
                + ", getKey()=" + getKey() + ", hashCode()=" + hashCode() + "]";
    }
    
}

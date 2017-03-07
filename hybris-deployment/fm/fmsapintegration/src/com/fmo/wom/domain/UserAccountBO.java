package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.Locale;

//import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/*//Entity
//Table(name="USER_ACCOUNT")
//NamedQueries({
    //NamedQuery(name = "findByUserID",
            	query = "from UserAccountBO userAcct " +
            			"where upper(userAcct.userID) = upper(:userID)"),
    //NamedQuery(name = "findByUserIDs",
                query = "from UserAccountBO userAcct " +
                		"where upper(userAcct.userID) in :userIDs"),
    //NamedQuery(name = "findByUserIDsAndMarketCode",
    			query = "from UserAccountBO userAcct " +
            			"where upper(userAcct.userID) in :userIDs " +
            			"and upper(userAcct.marketCode) = upper(:mrktCode)"),
    //NamedQuery(name = "findUserAccountsByAccountCode",
        		query = "from UserAccountBO userAcct " +
        				"where upper(trim(userAcct.accountCode)) = upper(trim(:acctCode))")
})
*/
public class UserAccountBO implements Serializable, Comparable<UserAccountBO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Transient
	private UserBO ldapUser; // Populated from LDAP
	
	//Id
	//Column(name="USER_ID")
	private String userID;
	
	//Column(name="MARKET_CODE")
	private String marketCode;
	
	//Column(name="ACCOUNT_CODE")
	private String accountCode;
	
	//Transient
	private boolean loggedInAsBillto = true;
	
	//Transient
	@XmlTransient
    private Locale currentLocale = Locale.ENGLISH;
    
	public boolean isLoggedInAsBillto() {
		return loggedInAsBillto;
	}
	public void setLoggedInAsBillto(boolean loggedInAsBillto) {
		this.loggedInAsBillto = loggedInAsBillto;
	}
	
	public UserBO getLdapUser() {
		return ldapUser;
	}
	public void setLdapUser(UserBO ldapUser) {
		this.ldapUser = ldapUser;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getMarketCode() {
		return marketCode;
	}
	public void setMarketCode(String marketCode) {
		this.marketCode = marketCode;
	}
	
	public String getAccountCode() {
		if (accountCode != null) {
			accountCode = accountCode.trim();
		}
		return accountCode;
	}
	
	public void setAccountCode(String accountCode) {
		if (accountCode != null) {
			this.accountCode = accountCode.trim();
		} else {
			this.accountCode = accountCode;
		}
	}
	
	@XmlTransient
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }
    
    @Override
	public String toString() {
		return "UserAccountBO [ldapUser=" + ldapUser + ", userID=" + userID
				+ ", marketCode=" + marketCode + ", accountCode=" + accountCode
				+ ", loggedInAsBillto=" + loggedInAsBillto + "]";
	}	
	
    
    public int compareTo(UserAccountBO obj2) {
        return this.accountCode.compareTo(obj2.accountCode);
        // (obj1.accountCode>obj2.accountCode ? -1 : (obj1.accountCode.equals(obj2.accountCode) ? 0 : 1));
    }

    
}

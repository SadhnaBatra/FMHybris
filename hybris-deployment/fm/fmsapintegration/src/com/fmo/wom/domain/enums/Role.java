
package com.fmo.wom.domain.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Type safe representation of possible roles. This is to avoid the text declaration of all the roles 
 * everywhere someone needs to check against them.
 */
public enum Role  {
    
    CSR_APPROVER("fmoWomCSRApprovers", true), 
    BUSINESS_OWNER_ADMIN("fmoWomBusOwnerAdmin", true),
    BUSINESS_OWNER_VIEW_ONLY("fmoWomBusOwnerView", true),
    BACKORDER_ADMIN("fmoWomBackAdmin", true),
    CSR_ADMIN("fmoWomCSRAdmin", true), 
    BUSINESS_OWNER_VIEW_INVOICE("fmoWomBusOwnerViewInv", true),
    CUSTOMER_FULL_ACCESS("fmoWomCustomerFullAccess", false),
    CUSTOMER_VIEW_ONLY("fmoWomCustomerViewOnly", false),
    CUSTOMER_NO_INVOICE("fmoWomCustomerNoInv", false);
    
    private String ldapRoleName;
    private boolean csr;
    // could construct these each time the method is called, but seems against the intent
    // of enum behaviour
    private static List<String> csrRoleLdapNameList; 
    private static List<String> customerRoleLdapNameList;
    
    Role(final String ldapRoleName, final boolean csr) {
        this.ldapRoleName = ldapRoleName;
        this.csr = csr;
    }

    public String getLdapRoleName() {
        return ldapRoleName;
    }

    public boolean isCsr() {
        return csr;
    }

    public static List<String> getCsrRoleLdapNameList() {
        if (csrRoleLdapNameList == null) {
            createLists();
        }
        return csrRoleLdapNameList;
    }

    public static List<String> getCustomerRoleLdapNameList() {
        if (customerRoleLdapNameList == null) {
            createLists();
        }
        return customerRoleLdapNameList;
    }
    
    public static String[] getCsrRoleLdapNameArray() {
        return getCsrRoleLdapNameList().toArray(new String[0]);
    }

    public static String[] getCustomerRoleLdapNameArray() {
        return getCustomerRoleLdapNameList().toArray(new String[0]);
    }

    public static Role getFromLdapRoleName(final String anLdapRoleName) {

        if (anLdapRoleName == null) return null;
        
        for (Role aRole : Role.values()) {

            if (aRole.getLdapRoleName().equals(anLdapRoleName) == true) {
                return aRole;
            }
        }

        return null;
    }

    private static void createLists() {
        csrRoleLdapNameList = new ArrayList<String>();
        customerRoleLdapNameList = new ArrayList<String>();
        for (Role aRole : values()) {
            // if csr, put in that list. Non-csr is customer
            if (aRole.isCsr()) {
                csrRoleLdapNameList.add(aRole.getLdapRoleName());
            } else {
                customerRoleLdapNameList.add(aRole.getLdapRoleName());
            }
        }
    }


}

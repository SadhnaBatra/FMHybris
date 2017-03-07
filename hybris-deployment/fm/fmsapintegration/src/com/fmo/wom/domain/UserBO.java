package com.fmo.wom.domain;

import java.util.List;

import com.fmo.wom.domain.enums.Role;

/**
 * @author joyr0011
 *
 * This class holds the LDAP attributes for the logged in User. 
 */
public class UserBO extends WOMBaseBO {

	private String firstName = null;
	private String lastName = null;
	private String phoneNbr = null;
	private String phoneNbrExt = null;
	private String emailAddress = null;
	private String guid = null;
	private String userID = null;
	private List<String> roles;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNbr() {
		return phoneNbr;
	}

	public void setPhoneNbr(String phoneNbr) {
		this.phoneNbr = phoneNbr;
	}

	public String getPhoneNbrExt() {
		return phoneNbrExt;
	}

	public void setPhoneNbrExt(String phoneNbrExt) {
		this.phoneNbrExt = phoneNbrExt;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/**
	 * Return true when user belongs to any of the roles thats passed in
	 * @param roles	List<String> list of roles to check
	 * @return true when user belongs to any of the roles thats passed in, false otherwise
	 */
	public boolean isUserInRole(List<String> roles){
		for(String role:roles){
			if(isUserInRole(role)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Return true when user belongs to role thats passed in
	 * @param role	String role to check
	 * @return true when user belongs to role thats passed in, false otherwise
	 */
	public boolean isUserInRole(String role){
		return this.roles.contains(role);
	}
	
	/**
	 * Return true when user has a CSR role.
	 * @return true when the user has a CSR role, false otherwise
	 */
	public boolean isUserInCsrRole(){
		List<String> csrRoles = Role.getCsrRoleLdapNameList();
		for (String csrRole : csrRoles) {
			if (this.roles.contains(csrRole)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "UserBO [firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNbr=" + phoneNbr + ", phoneNbrExt=" + phoneNbrExt
				+ ", emailAddress=" + emailAddress + ", guid=" + guid
				+ ", userID=" + userID + ", roles=" + roles
				+ ", createUserID=" + this.getCreateUserId()
				+ ", updateUserID=" + this.getUpdateUserId()
				+ ", createTimestamp=" + this.getCreateTimestamp()
				+ ", updateTimestamp=" + this.getUpdateTimestamp()  + "]";
	}

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
	
}

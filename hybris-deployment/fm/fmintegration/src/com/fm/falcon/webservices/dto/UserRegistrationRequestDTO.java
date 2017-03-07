/**
 *
 * Copyright 2014 Wipro Technologies All rights reserved.
 * 
 * Customer specific copyright notice     :Federal Mogul
 *
 * File Name       : UserRegistrationRequestDTO.java
 *
 * Version         : 0.0.0.1
 *
 * Created Date    :26-DEC-2014
 * 
 * Modification History:Modified by, on date.
 */
package com.fm.falcon.webservices.dto;

import java.util.List;


/**
 * User registration request DTO class.
 * 
 */
public class UserRegistrationRequestDTO extends CRMRequestDTO {
	private String customerEmailID;
	private String accountCode;
	private String firstName;
	private String LastName;
	private String sold_ShipTo;
	private String streetName1;
	private String streetName2;
	private String city;
	private String state;
	private String postalCode;
	private String country;
	private String telephone;
	private String newsletterFlag;
	private String customerType;
	private String companyName;
	private String taxNumber;
	private String compStreetName1;
	private String compStreetName2;
	private String compCity;
	private String compState;
	private String compPostCode;
	private String compCountry;
	private String compTelephone;
	private String association;
	private String prospectID;
	private String loyaltyEnrollmentFlag;
	private String trainingParticiptaionFlag;
	private String newProductAlert;
	private String newPromotionFlag;
	private String profileUpdateFlag;
	private String lmsId;
    private String referralEmailId;
	private String promoCode;
	private List<String> UniqueID;
	private String customerContactId;

	/**
	 * @return the uniqueID
	 */
	public List<String> getUniqueID() {
		return UniqueID;
	}

	/**
	 * @param uniqueID
	 *           the uniqueID to set
	 */
	public void setUniqueID(final List<String> uniqueID) {
		UniqueID = uniqueID;
	}

	/**
	 * @return the customerEmailID
	 */
	public String getCustomerEmailID() {
		return customerEmailID;
	}

	/**
	 * @param customerEmailID
	 *           the customerEmailID to set
	 */
	public void setCustomerEmailID(final String customerEmailID) {
		this.customerEmailID = customerEmailID;
	}

	/**
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode
	 *           the accountCode to set
	 */
	public void setAccountCode(final String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *           the firstName to set
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return LastName;
	}

	/**
	 * @param lastName
	 *           the lastName to set
	 */
	public void setLastName(final String lastName) {
		LastName = lastName;
	}

	/**
	 * @return the sold_ShipTo
	 */
	public String getSold_ShipTo() {
		return sold_ShipTo;
	}

	/**
	 * @param sold_ShipTo
	 *           the sold_ShipTo to set
	 */
	public void setSold_ShipTo(final String sold_ShipTo) {
		this.sold_ShipTo = sold_ShipTo;
	}

	/**
	 * @return the streetName1
	 */
	public String getStreetName1() {
		return streetName1;
	}

	/**
	 * @param streetName1
	 *           the streetName1 to set
	 */
	public void setStreetName1(final String streetName1) {
		this.streetName1 = streetName1;
	}

	/**
	 * @return the streetName2
	 */
	public String getStreetName2() {
		return streetName2;
	}

	/**
	 * @param streetName2
	 *           the streetName2 to set
	 */
	public void setStreetName2(final String streetName2) {
		this.streetName2 = streetName2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *           the city to set
	 */
	public void setCity(final String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *           the state to set
	 */
	public void setState(final String state) {
		this.state = state;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 *           the postalCode to set
	 */
	public void setPostalCode(final String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *           the country to set
	 */
	public void setCountry(final String country) {
		this.country = country;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone
	 *           the telephone to set
	 */
	public void setTelephone(final String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the newsletterFlag
	 */
	public String getNewsletterFlag() {
		return newsletterFlag;
	}

	/**
	 * @param newsletterFlag
	 *           the newsletterFlag to set
	 */
	public void setNewsletterFlag(final String newsletterFlag) {
		this.newsletterFlag = newsletterFlag;
	}

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType
	 *           the customerType to set
	 */
	public void setCustomerType(final String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *           the companyName to set
	 */
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the taxNumber
	 */
	public String getTaxNumber() {
		return taxNumber;
	}

	/**
	 * @param taxNumber
	 *           the taxNumber to set
	 */
	public void setTaxNumber(final String taxNumber) {
		this.taxNumber = taxNumber;
	}

	/**
	 * @return the compStreetName1
	 */
	public String getCompStreetName1() {
		return compStreetName1;
	}

	/**
	 * @param compStreetName1
	 *           the compStreetName1 to set
	 */
	public void setCompStreetName1(final String compStreetName1) {
		this.compStreetName1 = compStreetName1;
	}

	/**
	 * @return the compStreetName2
	 */
	public String getCompStreetName2() {
		return compStreetName2;
	}

	/**
	 * @param compStreetName2
	 *           the compStreetName2 to set
	 */
	public void setCompStreetName2(final String compStreetName2) {
		this.compStreetName2 = compStreetName2;
	}

	/**
	 * @return the compCity
	 */
	public String getCompCity() {
		return compCity;
	}

	/**
	 * @param compCity
	 *           the compCity to set
	 */
	public void setCompCity(final String compCity) {
		this.compCity = compCity;
	}

	/**
	 * @return the compState
	 */
	public String getCompState() {
		return compState;
	}

	/**
	 * @param compState
	 *           the compState to set
	 */
	public void setCompState(final String compState) {
		this.compState = compState;
	}

	/**
	 * @return the compPostCode
	 */
	public String getCompPostCode() {
		return compPostCode;
	}

	/**
	 * @param compPostCode
	 *           the compPostCode to set
	 */
	public void setCompPostCode(final String compPostCode) {
		this.compPostCode = compPostCode;
	}

	/**
	 * @return the compCountry
	 */
	public String getCompCountry() {
		return compCountry;
	}

	/**
	 * @param compCountry
	 *           the compCountry to set
	 */
	public void setCompCountry(final String compCountry) {
		this.compCountry = compCountry;
	}

	/**
	 * @return the compTelephone
	 */
	public String getCompTelephone() {
		return compTelephone;
	}

	/**
	 * @param compTelephone
	 *           the compTelephone to set
	 */
	public void setCompTelephone(final String compTelephone) {
		this.compTelephone = compTelephone;
	}

	/**
	 * @return the association
	 */
	public String getAssociation() {
		return association;
	}

	/**
	 * @param association
	 *           the association to set
	 */
	public void setAssociation(final String association) {
		this.association = association;
	}

	/**
	 * @return the prospectID
	 */
	public String getProspectID() {
		return prospectID;
	}

	/**
	 * @param prospectID
	 *           the prospectID to set
	 */
	public void setProspectID(final String prospectID) {
		this.prospectID = prospectID;
	}

	/**
	 * @return the loyaltyEnrollmentFlag
	 */
	public String getLoyaltyEnrollmentFlag() {
		return loyaltyEnrollmentFlag;
	}

	/**
	 * @param loyaltyEnrollmentFlag
	 *           the loyaltyEnrollmentFlag to set
	 */
	public void setLoyaltyEnrollmentFlag(final String loyaltyEnrollmentFlag) {
		this.loyaltyEnrollmentFlag = loyaltyEnrollmentFlag;
	}

	/**
	 * @return the trainingParticiptaionFlag
	 */
	public String getTrainingParticiptaionFlag() {
		return trainingParticiptaionFlag;
	}

	/**
	 * @param trainingParticiptaionFlag
	 *           the trainingParticiptaionFlag to set
	 */
	public void setTrainingParticiptaionFlag(final String trainingParticiptaionFlag) {
		this.trainingParticiptaionFlag = trainingParticiptaionFlag;
	}

	/**
	 * @return the newProductAlert
	 */
	public String getNewProductAlert() {
		return newProductAlert;
	}

	/**
	 * @param newProductAlert
	 *           the newProductAlert to set
	 */
	public void setNewProductAlert(final String newProductAlert) {
		this.newProductAlert = newProductAlert;
	}

	/**
	 * @return the newPromotionFlag
	 */
	public String getNewPromotionFlag() {
		return newPromotionFlag;
	}

	/**
	 * @param newPromotionFlag
	 *           the newPromotionFlag to set
	 */
	public void setNewPromotionFlag(final String newPromotionFlag) {
		this.newPromotionFlag = newPromotionFlag;
	}

	/**
	 * @return the profileUpdateFlag
	 */
	public String getProfileUpdateFlag() {
		return profileUpdateFlag;
	}

	/**
	 * @param profileUpdateFlag
	 *           the profileUpdateFlag to set
	 */
	public void setProfileUpdateFlag(String profileUpdateFlag) {
		this.profileUpdateFlag = profileUpdateFlag;
	}

	/**
	 * @return the lmsId
	 */
	public String getLmsId() {
		return lmsId;
	}

	/**
	 * @param lmsId the lmsId to set
	 */
	public void setLmsId(String lmsId) {
		this.lmsId = lmsId;
	}


/**
	 * @return the referralEmailId
	 */
	public String getReferralEmailId() {
		return referralEmailId;
	}

	/**
	 * @param referralEmailId
	 *           the referralEmailId to set
	 */
	public void setReferralEmailId(final String referralEmailId) {
		this.referralEmailId = referralEmailId;
	}

	/**
	 * @return the promoCode
	 */
	public String getPromoCode() {
		return promoCode;
	}

	/**
	 * @param promoCode the promoCode to set
	 */
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	/**
	 * @return the customerContactId
	 */
	public String getCustomerContactId() {
		return customerContactId;
	}

	/**
	 * @param customerContactId the customerContactId to set
	 */
	public void setCustomerContactId(String customerContactId) {
		this.customerContactId = customerContactId;
	}
}

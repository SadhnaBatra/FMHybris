/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.federalmogul.storefront.forms;

import com.federalmogul.core.enums.Fmusertype;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Pojo for fm registration page
 */
public class FMRegistrationForm extends B2BCustomerForm
{

	/*
	 * @NotEmpty(message = "{register.field.mandatory}") private String firstName; private String lastName;
	 */

	private String email;
	private String password;
	private Boolean newsLetterSubscription = false;
	private String taxID;
	private Fmusertype usertypedesc;
	private String SecretQuestion;
	private String SecretAnswer;
	private MultipartFile taxDocument;
	private String fileName;
	private Boolean newProductAlerts = false;
	private Boolean promotionInfoSubscription = false;
	private Boolean loyaltySignup = false;
	private Boolean techAcademySubscription = true;
	private String addressline1;
	private String addressline2;
	private String city;
	private String state;
	private String country;
	private String zipCode;
	private String phoneno;
	private String companyName;
	private String companycity;
	private String companystate;
	private String companycountry;
	private String companyzipCode;
	private String companyaddressline1;
	private String companyaddressline2;
	private String companyPhoneNumber;
	private String companyFax;
	private String companyWebsite;
	private String accCode;
	private String confpwd;
	private String workContactNo;
	private String association;
	private String fmRole;
	private Boolean isLoginDisabled;
	private Boolean isSameAddress;
	private String lmsSigniId;
	private String employeeId;
	private String confirmEmail;

	//Technician Flow chages
	private Boolean isGarageRewardMember = false;
	private String aboutShop;
	private String shopType;
	private String shopBays;
	private String shopBanner;
	private List<String> mostIntersted;
	private List<String> brands;
    private Boolean refered = false;
	private String referredBy;
	private String promoCode;
    private String techType;
	private String AlternativeAccountId;


	/**
	 * @return the alternativeAccountId
	 */
	public String getAlternativeAccountId()
	{
		return AlternativeAccountId;
	}

	/**
	 * @param alternativeAccountId
	 *           the alternativeAccountId to set
	 */
	public void setAlternativeAccountId(final String alternativeAccountId)
	{
		AlternativeAccountId = alternativeAccountId;
	}


	/**
	 * @return the techType
	 */
	public String getTechType()
	{
		return techType;
	}

	/**
	 * @param techType
	 *           the techType to set
	 */
	public void setTechType(final String techType)
	{
		this.techType = techType;
	}

	/**
	 * @return the shopBanner
	 */
	public String getShopBanner()
	{
		return shopBanner;
	}

	/**
	 * @param shopBanner
	 *           the shopBanner to set
	 */
	public void setShopBanner(final String shopBanner)
	{
		this.shopBanner = shopBanner;
	}

	/**
	 * @return the isGarageRewardMember
	 */
	public Boolean getIsGarageRewardMember()
	{
		return isGarageRewardMember;
	}

	/**
	 * @param isGarageRewardMember
	 *           the isGarageRewardMember to set
	 */
	public void setIsGarageRewardMember(final Boolean isGarageRewardMember)
	{
		this.isGarageRewardMember = isGarageRewardMember;
	}

	/**
	 * @return the aboutShop
	 */
	public String getAboutShop()
	{
		return aboutShop;
	}

	/**
	 * @param aboutShop
	 *           the aboutShop to set
	 */
	public void setAboutShop(final String aboutShop)
	{
		this.aboutShop = aboutShop;
	}

	/**
	 * @return the shopType
	 */
	public String getShopType()
	{
		return shopType;
	}

	/**
	 * @param shopType
	 *           the shopType to set
	 */
	public void setShopType(final String shopType)
	{
		this.shopType = shopType;
	}

	/**
	 * @return the shopBays
	 */
	public String getShopBays()
	{
		return shopBays;
	}

	/**
	 * @param shopBays
	 *           the shopBays to set
	 */
	public void setShopBays(final String shopBays)
	{
		this.shopBays = shopBays;
	}

	/**
	 * @return the mostIntersted
	 */
	public List<String> getMostIntersted()
	{
		return mostIntersted;
	}

	/**
	 * @param mostIntersted
	 *           the mostIntersted to set
	 */
	public void setMostIntersted(final List<String> mostIntersted)
	{
		this.mostIntersted = mostIntersted;
	}

	/**
	 * @return the brands
	 */
	public List<String> getBrands()
	{
		return brands;
	}

	/**
	 * @param brands
	 *           the brands to set
	 */
	public void setBrands(final List<String> brands)
	{
		this.brands = brands;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return companyName;
	}

	/**
	 * @param companyName
	 *           the companyName to set
	 */
	public void setCompanyName(final String companyName)
	{
		this.companyName = companyName;
	}

	/**
	 * @return the companycity
	 */
	public String getCompanycity()
	{
		return companycity;
	}

	/**
	 * @param companycity
	 *           the companycity to set
	 */
	public void setCompanycity(final String companycity)
	{
		this.companycity = companycity;
	}

	/**
	 * @return the companystate
	 */
	public String getCompanystate()
	{
		return companystate;
	}

	/**
	 * @param companystate
	 *           the companystate to set
	 */
	public void setCompanystate(final String companystate)
	{
		this.companystate = companystate;
	}

	/**
	 * @return the companycountry
	 */
	public String getCompanycountry()
	{
		return companycountry;
	}

	/**
	 * @param companycountry
	 *           the companycountry to set
	 */
	public void setCompanycountry(final String companycountry)
	{
		this.companycountry = companycountry;
	}

	/**
	 * @return the companyzipCode
	 */
	public String getCompanyzipCode()
	{
		return companyzipCode;
	}

	/**
	 * @param companyzipCode
	 *           the companyzipCode to set
	 */
	public void setCompanyzipCode(final String companyzipCode)
	{
		this.companyzipCode = companyzipCode;
	}

	/**
	 * @return the companyaddressline1
	 */
	public String getCompanyaddressline1()
	{
		return companyaddressline1;
	}

	/**
	 * @param companyaddressline1
	 *           the companyaddressline1 to set
	 */
	public void setCompanyaddressline1(final String companyaddressline1)
	{
		this.companyaddressline1 = companyaddressline1;
	}

	/**
	 * @return the companyaddressline2
	 */
	public String getCompanyaddressline2()
	{
		return companyaddressline2;
	}

	/**
	 * @param companyaddressline2
	 *           the companyaddressline2 to set
	 */
	public void setCompanyaddressline2(final String companyaddressline2)
	{
		this.companyaddressline2 = companyaddressline2;
	}

	/**
	 * @return the companyPhoneNumber
	 */
	public String getCompanyPhoneNumber()
	{
		return companyPhoneNumber;
	}

	/**
	 * @param companyPhoneNumber
	 *           the companyPhoneNumber to set
	 */
	public void setCompanyPhoneNumber(final String companyPhoneNumber)
	{
		this.companyPhoneNumber = companyPhoneNumber;
	}

	/**
	 * @return the companyFax
	 */
	public String getCompanyFax()
	{
		return companyFax;
	}

	/**
	 * @param companyFax
	 *           the companyFax to set
	 */
	public void setCompanyFax(final String companyFax)
	{
		this.companyFax = companyFax;
	}

	/**
	 * @return the companyWebsite
	 */
	public String getCompanyWebsite()
	{
		return companyWebsite;
	}

	/**
	 * @param companyWebsite
	 *           the companyWebsite to set
	 */
	public void setCompanyWebsite(final String companyWebsite)
	{
		this.companyWebsite = companyWebsite;
	}



	/**
	 * @return the confpwd
	 */
	public String getConfpwd()
	{
		return confpwd;
	}

	/**
	 * @param confpwd
	 *           the confpwd to set
	 */
	public void setConfpwd(final String confpwd)
	{
		this.confpwd = confpwd;
	}

	/**
	 * @return the accCode
	 */
	public String getAccCode()
	{
		return accCode;
	}

	/**
	 * @param accCode
	 *           the accCode to set
	 */
	public void setAccCode(final String accCode)
	{
		this.accCode = accCode;
	}

	/**
	 * @return the phoneno
	 */
	public String getPhoneno()
	{
		return phoneno;
	}

	/**
	 * @param phoneno
	 *           the phoneno to set
	 */
	public void setPhoneno(final String phoneno)
	{
		this.phoneno = phoneno;
	}


	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}

	/**
	 * @param city
	 *           the city to set
	 */
	public void setCity(final String city)
	{
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * @param state
	 *           the state to set
	 */
	public void setState(final String state)
	{
		this.state = state;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country
	 *           the country to set
	 */
	public void setCountry(final String country)
	{
		this.country = country;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode()
	{
		return zipCode;
	}

	/**
	 * @param zipCode
	 *           the zipCode to set
	 */
	public void setZipCode(final String zipCode)
	{
		this.zipCode = zipCode;
	}


	/**
	 * @return the usertypedesc
	 */
	public Fmusertype getUsertypedesc()
	{
		return usertypedesc;
	}

	/**
	 * @param usertypedesc
	 *           the usertypedesc to set
	 */
	public void setUsertypedesc(final Fmusertype usertypedesc)
	{

		this.usertypedesc = usertypedesc;
	}


	/**
	 * @return the newProductAlerts
	 */
	public Boolean getNewProductAlerts()
	{
		return newProductAlerts;
	}

	/**
	 * @param newProductAlerts
	 *           the newProductAlerts to set
	 */
	public void setNewProductAlerts(final Boolean newProductAlerts)
	{
		this.newProductAlerts = newProductAlerts;
	}

	/**
	 * @return the promotionInfoSubscription
	 */
	public Boolean getPromotionInfoSubscription()
	{
		return promotionInfoSubscription;
	}

	/**
	 * @param promotionInfoSubscription
	 *           the promotionInfoSubscription to set
	 */
	public void setPromotionInfoSubscription(final Boolean promotionInfoSubscription)
	{
		this.promotionInfoSubscription = promotionInfoSubscription;
	}

	/**
	 * @return the addressline1
	 */
	public String getAddressline1()
	{
		return addressline1;
	}

	/**
	 * @param addressline1
	 *           the addressline1 to set
	 */
	public void setAddressline1(final String addressline1)
	{
		this.addressline1 = addressline1;
	}

	/**
	 * @return the addressline2
	 */
	public String getAddressline2()
	{
		return addressline2;
	}

	/**
	 * @param addressline2
	 *           the addressline2 to set
	 */
	public void setAddressline2(final String addressline2)
	{
		this.addressline2 = addressline2;
	}




	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/**
	 * @param fileName
	 *           the fileName to set
	 */
	public void setFileName(final String fileName)
	{
		this.fileName = fileName;
	}

	/**
	 * @return the taxDocument
	 */
	public MultipartFile getTaxDocument()
	{
		return taxDocument;
	}

	/**
	 * @param taxDocument
	 *           the taxDocument to set
	 */
	public void setTaxDocument(final MultipartFile taxDocument)
	{
		this.taxDocument = taxDocument;
	}

	/**
	 * @return the secretQuestion
	 */
	public String getSecretQuestion()
	{
		return SecretQuestion;
	}

	/**
	 * @return the secretAnswer
	 */
	public String getSecretAnswer()
	{
		return SecretAnswer;
	}

	/**
	 * @param secretQuestion
	 *           the secretQuestion to set
	 */
	public void setSecretQuestion(final String secretQuestion)
	{
		SecretQuestion = secretQuestion;
	}

	/**
	 * @param secretAnswer
	 *           the secretAnswer to set
	 */
	public void setSecretAnswer(final String secretAnswer)
	{
		SecretAnswer = secretAnswer;
	}



	/**
	 * @return the loyaltySignup
	 */
	public Boolean getLoyaltySignup()
	{
		return loyaltySignup;
	}


	/**
	 * @return the taxID
	 */
	public String getTaxID()
	{
		return taxID;
	}



	/**
	 * @param loyaltySignup
	 *           the loyaltySignup to set
	 */
	public void setLoyaltySignup(final Boolean loyaltySignup)
	{
		this.loyaltySignup = loyaltySignup;
	}



	/**
	 * @param taxID
	 *           the taxID to set
	 */
	public void setTaxID(final String taxID)
	{
		this.taxID = taxID;
	}


	@Override
	public void setEmail(final String email)
	{
		this.email = email;
	}

	/**
	 * @param password
	 *           the password to set
	 */
	public void setPassword(final String password)
	{
		this.password = password;
	}


	@Override
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the techAcademySubscription
	 */
	public Boolean getTechAcademySubscription()
	{
		return techAcademySubscription;
	}

	/**
	 * @param techAcademySubscription
	 *           the techAcademySubscription to set
	 */
	public void setTechAcademySubscription(final Boolean techAcademySubscription)
	{
		this.techAcademySubscription = techAcademySubscription;
	}

	/**
	 * @return the newsLetterSubscription
	 */
	public Boolean getNewsLetterSubscription()
	{
		return newsLetterSubscription;
	}

	/**
	 * @param newsLetterSubscription
	 *           the newsLetterSubscription to set
	 */
	public void setNewsLetterSubscription(final Boolean newsLetterSubscription)
	{
		this.newsLetterSubscription = newsLetterSubscription;
	}



	public String getWorkContactNo()
	{

		return workContactNo;
	}

	public void setWorkContactNo(final String workContactNo)
	{
		this.workContactNo = workContactNo;
	}

	/**
	 * @return the association
	 */
	public String getAssociation()
	{
		return association;
	}

	/**
	 * @param association
	 *           the association to set
	 */
	public void setAssociation(final String association)
	{
		this.association = association;
	}

	public String getFmRole()
	{
		return fmRole;
	}

	public void setFmRole(final String fmRole)
	{
		this.fmRole = fmRole;
	}



	/**
	 * @return the isLoginDisabled
	 */
	public Boolean getIsLoginDisabled()
	{
		return isLoginDisabled;
	}

	/**
	 * @param isLoginDisabled
	 *           the isLoginDisabled to set
	 */
	public void setIsLoginDisabled(final Boolean isLoginDisabled)
	{
		this.isLoginDisabled = isLoginDisabled;
	}

	/**
	 * @return the isSameAddress
	 */
	public Boolean getIsSameAddress()
	{
		return isSameAddress;
	}

	/**
	 * @param isSameAddress
	 *           the isSameAddress to set
	 */
	public void setIsSameAddress(final Boolean isSameAddress)
	{
		this.isSameAddress = isSameAddress;
	}

	/**
	 * @return the lmsSigniId
	 */
	public String getLmsSigniId()
	{
		return lmsSigniId;
	}

	/**
	 * @param lmsSigniId
	 *           the lmsSigniId to set
	 */
	public void setLmsSigniId(final String lmsSigniId)
	{
		this.lmsSigniId = lmsSigniId;
	}

/**
	 * @return the referredBy
	 */
	public String getReferredBy()
	{
		return referredBy;
	}

	/**
	 * @param referredBy
	 *           the referredBy to set
	 */
	public void setReferredBy(final String referredBy)
	{
		this.referredBy = referredBy;
	}

	/**
	 * @return the refered
	 */
	public Boolean getRefered()
	{
		return refered;
	}

	/**
	 * @param refered
	 *           the refered to set
	 */
	public void setRefered(final Boolean refered)
	{
		this.refered = refered;
	}

	/**
	 * @return the promoCode
	 */
	public String getPromoCode()
	{
		return promoCode;
	}

	/**
	 * @param promoCode the promoCode to set
	 */
	public void setPromoCode(String promoCode)
	{
		this.promoCode = promoCode;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}
}

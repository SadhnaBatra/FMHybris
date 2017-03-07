/**
 * 
 */
package com.federalmogul.storefront.forms;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.enums.Fmusertype;


/**
 * @author SA297584
 * 
 */
public class FMUpdateProfileForm extends UpdateProfileForm
{
	private boolean newsLetterSubscription = true;


	//Added Newly for UpdateProfile 

	private String email;
	private String password;

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
	 * @return the promoCode
	 */
	public String getPromoCode()
	{
		return promoCode;
	}

	/**
	 * @param promoCode
	 *           the promoCode to set
	 */
	public void setPromoCode(final String promoCode)
	{
		this.promoCode = promoCode;
	}

	/**
	 * @return the refered
	 */
	public Boolean getRefered()
	{
		return refered;
	}

	private String taxID;
	private Fmusertype usertypedesc;
	private String SecretQuestion;
	private String SecretAnswer;
	private MultipartFile taxDocument;
	private String fileName;
	private final Boolean newProductAlerts = false;
	private final Boolean promotionInfoSubscription = false;
	private Boolean loyaltySignup = false;
	private final Boolean techAcademySubscription = true;
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

	//Technician Flow chages
	private Boolean isGarageRewardMember = false;
	private String aboutShop;
	private String shopType;
	private String shopBays;
	private String shopBanner;
	private List<String> mostIntersted;
	private List<String> brands;
	private final Boolean refered = false;
	private String referredBy;
	private String promoCode;
	private String isLoyaltyRequestedMember;

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *           the email to set
	 */
	public void setEmail(final String email)
	{
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 *           the password to set
	 */
	public void setPassword(final String password)
	{
		this.password = password;
	}

	/**
	 * @return the taxID
	 */
	public String getTaxID()
	{
		return taxID;
	}

	/**
	 * @param taxID
	 *           the taxID to set
	 */
	public void setTaxID(final String taxID)
	{
		this.taxID = taxID;
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
	 * @return the secretQuestion
	 */
	public String getSecretQuestion()
	{
		return SecretQuestion;
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
	 * @return the secretAnswer
	 */
	public String getSecretAnswer()
	{
		return SecretAnswer;
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
	 * @return the workContactNo
	 */
	public String getWorkContactNo()
	{
		return workContactNo;
	}

	/**
	 * @param workContactNo
	 *           the workContactNo to set
	 */
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

	/**
	 * @return the fmRole
	 */
	public String getFmRole()
	{
		return fmRole;
	}

	/**
	 * @param fmRole
	 *           the fmRole to set
	 */
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
	 * @return the newProductAlerts
	 */
	public Boolean getNewProductAlerts()
	{
		return newProductAlerts;
	}

	/**
	 * @return the promotionInfoSubscription
	 */
	public Boolean getPromotionInfoSubscription()
	{
		return promotionInfoSubscription;
	}


	/**
	 * @return the techAcademySubscription
	 */
	public Boolean getTechAcademySubscription()
	{
		return techAcademySubscription;
	}

	/**
	 * @return the isGarageRewardMember
	 */
	public Boolean getIsGarageRewardMember()
	{
		return isGarageRewardMember;
	}

	/**
	 * @param newsLetterSubscription
	 *           the newsLetterSubscription to set
	 */
	public void setNewsLetterSubscription(final Boolean newsLetterSubscription)
	{
		this.newsLetterSubscription = newsLetterSubscription;
	}

	/**
	 * @return the newsLetterSubscription
	 */
	public boolean getNewsLetterSubscription()
	{
		return newsLetterSubscription;
	}

	/**
	 * @param newsLetterSubscription
	 *           the newsLetterSubscription to set
	 */
	public void setNewsLetterSubscription(final boolean newsLetterSubscription)
	{
		this.newsLetterSubscription = newsLetterSubscription;
	}
	/**
	 * @return the loyaltySignup
	 */
	public Boolean getLoyaltySignup()
	{
		return loyaltySignup;
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
	 * @param isGarageRewardMember
	 *           the isGarageRewardMember to set
	 */
	public void setIsGarageRewardMember(final Boolean isGarageRewardMember)
	{
		this.isGarageRewardMember = isGarageRewardMember;
	}

	/**
	 * @return the isLoyaltyRequestedMember
	 */
	public String getIsLoyaltyRequestedMember()
	{
		return isLoyaltyRequestedMember;
	}

	/**
	 * @param isLoyaltyRequestedMember
	 *           the isLoyaltyRequestedMember to set
	 */
	public void setIsLoyaltyRequestedMember(final String isLoyaltyRequestedMember)
	{
		this.isLoyaltyRequestedMember = isLoyaltyRequestedMember;
	}
}

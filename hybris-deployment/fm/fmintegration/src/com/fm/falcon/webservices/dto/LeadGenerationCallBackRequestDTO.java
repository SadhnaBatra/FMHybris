/**
 * 
 */
package com.fm.falcon.webservices.dto;




/**
 * @author Balaji
 * 
 */
public class LeadGenerationCallBackRequestDTO extends CRMRequestDTO
{

	private String queryType;
	private String sapaccountcode;
	private String jobTitle;
	private String department;
	private String company;

	private String firstName;
	private String lastName;
	private String country;
	private String email;

	private String telephone;
	private String streetAddress1;
	private String streetAddress2;
	private String city;

	private String zipcode;
	private String stateCode;
	private String freeText;

	private String queryDescription;

	private String bestWayToContact;
	private String phoneDays;
	private String phoneTime;
	private String phoneTimeZone;

	private String partNumber;
	private String brand;
	private String invoiceNumber;
	private String orderNumber;
	private String customerID;

	public String getQueryDescription()
	{
		return queryDescription;
	}
	
	public void setQueryDescription(final String queryDescription)
	{
		this.queryDescription = queryDescription;
	}


	/**
	 * @return the queryType
	 */
	public String getQueryType()
	{
		return queryType;
	}

	/**
	 * @param queryType
	 *           the queryType to set
	 */
	public void setQueryType(final String queryType)
	{
		this.queryType = queryType;
	}

	/**
	 * @return the sapaccountcode
	 */
	public String getSapaccountcode()
	{
		return sapaccountcode;
	}

	/**
	 * @param sapaccountcode
	 *           the sapaccountcode to set
	 */
	public void setSapaccountcode(final String sapaccountcode)
	{
		this.sapaccountcode = sapaccountcode;
	}

	/**
	 * @return the jobTitle
	 */
	public String getJobTitle()
	{
		return jobTitle;
	}

	/**
	 * @param jobTitle
	 *           the jobTitle to set
	 */
	public void setJobTitle(final String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the department
	 */
	public String getDepartment()
	{
		return department;
	}

	/**
	 * @param department
	 *           the department to set
	 */
	public void setDepartment(final String department)
	{
		this.department = department;
	}

	/**
	 * @return the company
	 */
	public String getCompany()
	{
		return company;
	}

	/**
	 * @param company
	 *           the company to set
	 */
	public void setCompany(final String company)
	{
		this.company = company;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *           the firstName to set
	 */
	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *           the lastName to set
	 */
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
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
	 * @return the telephone
	 */
	public String getTelephone()
	{
		return telephone;
	}

	/**
	 * @param telephone
	 *           the telephone to set
	 */
	public void setTelephone(final String telephone)
	{
		this.telephone = telephone;
	}

	/**
	 * @return the streetAddress1
	 */
	public String getStreetAddress1()
	{
		return streetAddress1;
	}

	/**
	 * @param streetAddress1
	 *           the streetAddress1 to set
	 */
	public void setStreetAddress1(final String streetAddress1)
	{
		this.streetAddress1 = streetAddress1;
	}

	/**
	 * @return the streetAddress2
	 */
	public String getStreetAddress2()
	{
		return streetAddress2;
	}

	/**
	 * @param streetAddress2
	 *           the streetAddress2 to set
	 */
	public void setStreetAddress2(final String streetAddress2)
	{
		this.streetAddress2 = streetAddress2;
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
	 * @return the zipcode
	 */
	public String getZipcode()
	{
		return zipcode;
	}

	/**
	 * @param zipcode
	 *           the zipcode to set
	 */
	public void setZipcode(final String zipcode)
	{
		this.zipcode = zipcode;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode()
	{
		return stateCode;
	}

	/**
	 * @param stateCode
	 *           the stateCode to set
	 */
	public void setStateCode(final String stateCode)
	{
		this.stateCode = stateCode;
	}

	/**
	 * @return the freeText
	 */
	public String getFreeText()
	{
		return freeText;
	}

	/**
	 * @param freeText
	 *           the freeText to set
	 */
	public void setFreeText(final String freeText)
	{
		this.freeText = freeText;
	}

	public String getBestWayToContact() {
		return bestWayToContact;
	}

	public void setBestWayToContact(String bestWayToContact) {
		this.bestWayToContact = bestWayToContact;
	}

	public String getPhoneDays() {
		return phoneDays;
	}

	public void setPhoneDays(String phoneDays) {
		this.phoneDays = phoneDays;
	}

	public String getPhoneTime() {
		return phoneTime;
	}

	public void setPhoneTime(String phoneTime) {
		this.phoneTime = phoneTime;
	}

	public String getPhoneTimeZone() {
		return phoneTimeZone;
	}

	public void setPhoneTimeZone(String phoneTimeZone) {
		this.phoneTimeZone = phoneTimeZone;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
}

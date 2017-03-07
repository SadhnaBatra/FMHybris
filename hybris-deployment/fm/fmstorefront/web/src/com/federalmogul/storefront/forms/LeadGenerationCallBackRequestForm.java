/**
 * 
 */
package com.federalmogul.storefront.forms;

import com.federalmogul.facades.user.data.FMCustomerData;


/**
 * @author Balaji
 * 
 */
public class LeadGenerationCallBackRequestForm extends FMCustomerData
{

	private String subjects;
	private String callBackDescription;
	private String department;
	private String email;
	private String firstName;
	private String lastName;
	private String phoneno;
	private String country;
	private String bestWayToContact;
	private String [] contactDays;
	private String [] contactTimeOfDays;
	private String contactTimeZone;
	private String partNumber;
	private String brand;
	private String invoiceNumber;
	private String orderNumber;
	private String customerID;

	/**
	 * @return the subjects
	 */
	public String getSubjects()
	{
		return subjects;
	}

	/**
	 * @param subjects
	 *           the subjects to set
	 */
	public void setSubjects(final String subjects)
	{
		this.subjects = subjects;
	}

	/**
	 * @return the email
	 */
	@Override
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *           the email to set
	 */
	@Override
	public void setEmail(final String email)
	{
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	@Override
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName
	 *           the firstName to set
	 */
	@Override
	public void setFirstName(final String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Override
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName
	 *           the lastName to set
	 */
	@Override
	public void setLastName(final String lastName)
	{
		this.lastName = lastName;
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
	 * @return the callBackDescription
	 */
	public String getCallBackDescription()
	{
		return callBackDescription;
	}

	/**
	 * @param callBackDescription
	 *           the callBackDescription to set
	 */
	public void setCallBackDescription(final String callBackDescription)
	{
		this.callBackDescription = callBackDescription;
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

	public String getBestWayToContact() {
		return bestWayToContact;
	}

	public void setBestWayToContact(String bestWayToContact) {
		this.bestWayToContact = bestWayToContact;
	}

	public String[] getContactDays() {
		return contactDays;
	}

	public void setContactDays(String[] contactDays) {
		this.contactDays = contactDays;
	}

	public String[] getContactTimeOfDays() {
		return contactTimeOfDays;
	}

	public void setContactTimeOfDays(String[] contactTimeOfDays) {
		this.contactTimeOfDays = contactTimeOfDays;
	}

	public String getContactTimeZone() {
		return contactTimeZone;
	}

	public void setContactTimeZone(String contactTimeZone) {
		this.contactTimeZone = contactTimeZone;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}

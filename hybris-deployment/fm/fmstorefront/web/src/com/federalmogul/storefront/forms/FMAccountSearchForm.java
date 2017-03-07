/**
 * 
 */
package com.federalmogul.storefront.forms;

/**
 * @author SA297584
 * 
 */
public class FMAccountSearchForm extends FMAddressForm
{
	private String accountNumber;

	private String companyName;

	private String nabsAccount;

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *           the accountNumber to set
	 */
	public void setAccountNumber(final String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName()
	{
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	/**
	 * @return the nabsAccount
	 */
	public String getNabsAccount()
	{
		return nabsAccount;
	}

	/**
	 * @param nabsAccount the nabsAccount to set
	 */
	public void setNabsAccount(String nabsAccount)
	{
		this.nabsAccount = nabsAccount;
	}


}

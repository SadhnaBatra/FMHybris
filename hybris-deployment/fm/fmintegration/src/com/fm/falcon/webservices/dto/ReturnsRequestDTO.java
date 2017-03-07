/**
 * 
 */
package com.fm.falcon.webservices.dto;

import java.util.List;




/**
 * @author Balaji
 * 
 */
public class ReturnsRequestDTO extends CRMRequestDTO
{

	private String reasonOfReturn;
	private String salesOrderNumber;
	private String confirmationNumber;
	private String returnDescription;
	private String invoiceNumber;

	private String sapAccountCode;
	private String purchaseOrderNumber;
	private String originalOrderNumber;
	private String accountNumber;
	private List<ReturnItemsDTO> returnItems;

	/**
	 * @return the returnItems
	 */
	public List<ReturnItemsDTO> getReturnItems()
	{
		return returnItems;
	}

	/**
	 * @param returnItems
	 *           the returnItems to set
	 */
	public void setReturnItems(final List<ReturnItemsDTO> returnItems)
	{
		this.returnItems = returnItems;
	}

	/**
	 * @return the reasonOfReturn
	 */
	public String getReasonOfReturn()
	{
		return reasonOfReturn;
	}

	/**
	 * @param reasonOfReturn
	 *           the reasonOfReturn to set
	 */
	public void setReasonOfReturn(final String reasonOfReturn)
	{
		this.reasonOfReturn = reasonOfReturn;
	}

	/**
	 * @return the salesOrderNumber
	 */
	public String getSalesOrderNumber()
	{
		return salesOrderNumber;
	}

	/**
	 * @param salesOrderNumber
	 *           the salesOrderNumber to set
	 */
	public void setSalesOrderNumber(final String salesOrderNumber)
	{
		this.salesOrderNumber = salesOrderNumber;
	}

	/**
	 * @return the confirmationNumber
	 */
	public String getConfirmationNumber()
	{
		return confirmationNumber;
	}

	/**
	 * @param confirmationNumber
	 *           the confirmationNumber to set
	 */
	public void setConfirmationNumber(final String confirmationNumber)
	{
		this.confirmationNumber = confirmationNumber;
	}

	/**
	 * @return the returnDescription
	 */
	public String getReturnDescription()
	{
		return returnDescription;
	}

	/**
	 * @param returnDescription
	 *           the returnDescription to set
	 */
	public void setReturnDescription(final String returnDescription)
	{
		this.returnDescription = returnDescription;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber()
	{
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber
	 *           the invoiceNumber to set
	 */
	public void setInvoiceNumber(final String invoiceNumber)
	{
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the sapAccountCode
	 */
	public String getSapAccountCode()
	{
		return sapAccountCode;
	}

	/**
	 * @param sapAccountCode
	 *           the sapAccountCode to set
	 */
	public void setSapAccountCode(final String sapAccountCode)
	{
		this.sapAccountCode = sapAccountCode;
	}

	/**
	 * @return the purchaseOrderNumber
	 */
	public String getPurchaseOrderNumber()
	{
		return purchaseOrderNumber;
	}

	/**
	 * @param purchaseOrderNumber
	 *           the purchaseOrderNumber to set
	 */
	public void setPurchaseOrderNumber(final String purchaseOrderNumber)
	{
		this.purchaseOrderNumber = purchaseOrderNumber;
	}

	/**
	 * @return the originalOrderNumber
	 */
	public String getOriginalOrderNumber()
	{
		return originalOrderNumber;
	}

	/**
	 * @param originalOrderNumber
	 *           the originalOrderNumber to set
	 */
	public void setOriginalOrderNumber(final String originalOrderNumber)
	{
		this.originalOrderNumber = originalOrderNumber;
	}

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

}

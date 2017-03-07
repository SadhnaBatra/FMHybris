/**
 * 
 */
package com.federalmogul.storefront.forms;

import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.product.data.ProductData;

import com.federalmogul.core.enums.FMReasonForReturn;


/**
 * @author Balaji
 * 
 */
public class ReturnRequestForm extends OrderData
{

	private String confirmationform;
	private Integer invoiceNumberform;
	private String salesOrderNumberform;
	private FMReasonForReturn fmReasonOfReturn;
	private String returnQuantityform;
	private ProductData product;
	private String productCodeform;
	private String returnDescriptionform;
	private String sapAccountCodeform;
	private String accountNumberform;
	private String purchaseOrderNumberform;
	private String originalOrderNumberform;
	private String sapReturnReasonform;
	private String returnReason;

	/**
	 * @return the returnReason
	 */
	public String getReturnReason()
	{
		return returnReason;
	}

	/**
	 * @param returnReason
	 *           the returnReason to set
	 */
	public void setReturnReason(final String returnReason)
	{
		this.returnReason = returnReason;
	}

	/**
	 * @return the confirmationform
	 */
	public String getConfirmationform()
	{
		return confirmationform;
	}

	/**
	 * @param confirmationform
	 *           the confirmationform to set
	 */
	public void setConfirmationform(final String confirmationform)
	{
		this.confirmationform = confirmationform;
	}

	/**
	 * @return the invoiceNumberform
	 */
	public Integer getInvoiceNumberform()
	{
		return invoiceNumberform;
	}

	/**
	 * @param invoiceNumberform
	 *           the invoiceNumberform to set
	 */
	public void setInvoiceNumberform(final Integer invoiceNumberform)
	{
		this.invoiceNumberform = invoiceNumberform;
	}

	/**
	 * @return the salesOrderNumberform
	 */
	public String getSalesOrderNumberform()
	{
		return salesOrderNumberform;
	}

	/**
	 * @param salesOrderNumberform
	 *           the salesOrderNumberform to set
	 */
	public void setSalesOrderNumberform(final String salesOrderNumberform)
	{
		this.salesOrderNumberform = salesOrderNumberform;
	}


	/**
	 * @return the fmReasonOfReturn
	 */
	public FMReasonForReturn getFmReasonOfReturn()
	{
		return fmReasonOfReturn;
	}

	/**
	 * @param fmReasonOfReturn
	 *           the fmReasonOfReturn to set
	 */
	public void setFmReasonOfReturn(final FMReasonForReturn fmReasonOfReturn)
	{
		this.fmReasonOfReturn = fmReasonOfReturn;
	}

	/**
	 * @return the product
	 */
	public ProductData getProduct()
	{
		return product;
	}

	/**
	 * @param product
	 *           the product to set
	 */
	public void setProduct(final ProductData product)
	{
		this.product = product;
	}


	/**
	 * @return the productCodeform
	 */
	public String getProductCodeform()
	{
		return productCodeform;
	}

	/**
	 * @param productCodeform
	 *           the productCodeform to set
	 */
	public void setProductCodeform(final String productCodeform)
	{
		this.productCodeform = productCodeform;
	}

	/**
	 * @return the returnDescriptionform
	 */
	public String getReturnDescriptionform()
	{
		return returnDescriptionform;
	}

	/**
	 * @param returnDescriptionform
	 *           the returnDescriptionform to set
	 */
	public void setReturnDescriptionform(final String returnDescriptionform)
	{
		this.returnDescriptionform = returnDescriptionform;
	}

	/**
	 * @return the returnQuantityform
	 */
	public String getReturnQuantityform()
	{
		return returnQuantityform;
	}

	/**
	 * @param returnQuantityform
	 *           the returnQuantityform to set
	 */
	public void setReturnQuantityform(final String returnQuantityform)
	{
		this.returnQuantityform = returnQuantityform;
	}

	/**
	 * @return the sapAccountCodeform
	 */
	public String getSapAccountCodeform()
	{
		return sapAccountCodeform;
	}

	/**
	 * @param sapAccountCodeform
	 *           the sapAccountCodeform to set
	 */
	public void setSapAccountCodeform(final String sapAccountCodeform)
	{
		this.sapAccountCodeform = sapAccountCodeform;
	}

	/**
	 * @return the accountNumberform
	 */
	public String getAccountNumberform()
	{
		return accountNumberform;
	}

	/**
	 * @param accountNumberform
	 *           the accountNumberform to set
	 */
	public void setAccountNumberform(final String accountNumberform)
	{
		this.accountNumberform = accountNumberform;
	}

	/**
	 * @return the purchaseOrderNumberform
	 */
	public String getPurchaseOrderNumberform()
	{
		return purchaseOrderNumberform;
	}

	/**
	 * @param purchaseOrderNumberform
	 *           the purchaseOrderNumberform to set
	 */
	public void setPurchaseOrderNumberform(final String purchaseOrderNumberform)
	{
		this.purchaseOrderNumberform = purchaseOrderNumberform;
	}

	/**
	 * @return the originalOrderNumberform
	 */
	public String getOriginalOrderNumberform()
	{
		return originalOrderNumberform;
	}

	/**
	 * @param originalOrderNumberform
	 *           the originalOrderNumberform to set
	 */
	public void setOriginalOrderNumberform(final String originalOrderNumberform)
	{
		this.originalOrderNumberform = originalOrderNumberform;
	}

	/**
	 * @return the sapReturnReasonform
	 */
	public String getSapReturnReasonform()
	{
		return sapReturnReasonform;
	}

	/**
	 * @param sapReturnReasonform
	 *           the sapReturnReasonform to set
	 */
	public void setSapReturnReasonform(final String sapReturnReasonform)
	{
		this.sapReturnReasonform = sapReturnReasonform;
	}



}

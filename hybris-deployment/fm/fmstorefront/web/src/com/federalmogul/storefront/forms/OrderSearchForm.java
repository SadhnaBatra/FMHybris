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




/**
 * Pojo for fm registration page
 */
public class OrderSearchForm
{
	private String purchaseOrderNumber;
	private String confirmationOrderNumber;
	private String startDate;
	private String endDate;
	private String status;

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
	 * @return the confirmationOrderNumber
	 */
	public String getConfirmationOrderNumber()
	{
		return confirmationOrderNumber;
	}

	/**
	 * @param confirmationOrderNumber
	 *           the confirmationOrderNumber to set
	 */
	public void setConfirmationOrderNumber(final String confirmationOrderNumber)
	{
		this.confirmationOrderNumber = confirmationOrderNumber;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate
	 *           the startDate to set
	 */
	public void setStartDate(final String startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate()
	{
		return endDate;
	}

	/**
	 * @param endDate
	 *           the endDate to set
	 */
	public void setEndDate(final String endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *           the status to set
	 */
	public void setStatus(final String status)
	{
		this.status = status;
	}


}

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


public class StoreFinderForm
{
	private String q; //NOPMD
	private String brand; //NOPMD
	private String radius; //NOPMD
	private String showNearest; //NOPMD
	private String shopType; //NOPMD
	private String country;

	public String getQ()
	{
		return q;
	}

	public void setQ(final String q) //NOPMD
	{
		this.q = q;
	}

	/**
	 * @return the brand
	 */
	public String getBrand()
	{
		return brand;
	}

	/**
	 * @param brand
	 *           the brand to set
	 */
	public void setBrand(final String brand)
	{
		this.brand = brand;
	}

	/**
	 * @return the radius
	 */
	public String getRadius()
	{
		return radius;
	}

	/**
	 * @param radius
	 *           the radius to set
	 */
	public void setRadius(final String radius)
	{
		this.radius = radius;
	}

	/**
	 * @return the showNearest
	 */
	public String getShowNearest()
	{
		return showNearest;
	}

	/**
	 * @param showNearest
	 *           the showNearest to set
	 */
	public void setShowNearest(final String showNearest)
	{
		this.showNearest = showNearest;
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

}

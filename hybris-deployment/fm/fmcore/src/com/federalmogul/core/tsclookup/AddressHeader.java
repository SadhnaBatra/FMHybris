package com.federalmogul.core.tsclookup;

/**
 * @author Balaji
 * 
 */
public class AddressHeader
{
	private String addrID;
	private String streetnumber1;
	private String streetnumber2;
	private String town;
	private String region;
	private String country;
	private String postalcode;
	private String phone1;
	private String brand;
	private String storeName;
	private String shopType;
	private double latitude;

	/**
	 * @return the latitude
	 */
	public double getLatitude()
	{
		return latitude;
	}


	/**
	 * @param latitude
	 *           the latitude to set
	 */
	public void setLatitude(final double latitude)
	{
		this.latitude = latitude;
	}


	/**
	 * @return the longitude
	 */
	public double getLongitude()
	{
		return longitude;
	}


	/**
	 * @param longitude
	 *           the longitude to set
	 */
	public void setLongitude(final double longitude)
	{
		this.longitude = longitude;
	}



	private double longitude;


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
	 * @return the streetnumber1
	 */
	public String getStreetnumber1()
	{
		return streetnumber1;
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
	 * @param streetnumber1
	 *           the streetnumber1 to set
	 */
	public void setStreetnumber1(final String streetnumber1)
	{
		this.streetnumber1 = streetnumber1;
	}


	/**
	 * @return the streetnumber2
	 */
	public String getStreetnumber2()
	{
		return streetnumber2;
	}


	/**
	 * @param streetnumber2
	 *           the streetnumber2 to set
	 */
	public void setStreetnumber2(final String streetnumber2)
	{
		this.streetnumber2 = streetnumber2;
	}


	/**
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}


	/**
	 * @param region
	 *           the region to set
	 */
	public void setRegion(final String region)
	{
		this.region = region;
	}


	/**
	 * @return the storeName
	 */
	public String getStoreName()
	{
		return storeName;
	}


	/**
	 * @param storeName
	 *           the storeName to set
	 */
	public void setStoreName(final String storeName)
	{
		this.storeName = storeName;
	}


	/**
	 * @return the addrID
	 */
	public String getAddrID()
	{
		return addrID;
	}


	/**
	 * @param addrID
	 *           the addrID to set
	 */
	public void setAddrID(final String addrID)
	{
		this.addrID = addrID;
	}

	/**
	 * @return the postalcode
	 */
	public String getPostalcode()
	{
		return postalcode;
	}


	/**
	 * @param postalcode
	 *           the postalcode to set
	 */
	public void setPostalcode(final String postalcode)
	{
		this.postalcode = postalcode;
	}


	/**
	 * @return the town
	 */
	public String getTown()
	{
		return town;
	}


	/**
	 * @param town
	 *           the town to set
	 */
	public void setTown(final String town)
	{
		this.town = town;
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
	 * @return the phone1
	 */
	public String getPhone1()
	{
		return phone1;
	}


	/**
	 * @param phone1
	 *           the phone1 to set
	 */
	public void setPhone1(final String phone1)
	{
		this.phone1 = phone1;
	}

	public AddressHeader(final double latitude, final double longitude)
	{
		super();
		this.latitude = latitude;
		this.longitude = longitude;

	}

	public AddressHeader(final String addrID, final String streetnumber1, final String streetnumber2, final String town,
			final String region, final String country, final String postalcode, final String phone1, final String storeName,
			final String brand, final String shopType)
	{
		super();

		this.addrID = addrID;
		this.streetnumber1 = streetnumber1;
		this.streetnumber2 = streetnumber2;
		this.town = town;
		this.region = region;
		this.country = country;
		this.postalcode = postalcode;
		this.phone1 = phone1;
		this.storeName = storeName;
		this.brand = brand;
		this.shopType = shopType;
	}



	@Override
	public String toString()
	{
		return "Address Data [addrID=" + addrID + ", streetnumber1=" + streetnumber1 + ", streetnumber2=" + streetnumber2
				+ ", town=" + town + ",region=" + region + ", country=" + country + ", postalcode=" + postalcode + ", phone1="
				+ phone1 + ",storeName=" + storeName + ",brand=" + brand + "shopType=" + shopType + "]";
	}

}

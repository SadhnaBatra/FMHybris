/**
 * 
 */
package com.federalmogul.core.tsclookup;

import com.federalmogul.core.model.TSCLocationModel;


/**
 * @author anapande
 * 
 */
public class TSCAddressData extends TSCLookupServiceImpl
{


	private String address;
	private double latitude;
	private double longitude;
	private double distance;


	public TSCAddressData(final String address, final double latitude, final double longitude)
	{

		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;

	}

	// Compute the distance in meters based on the latitude and longitude from getTSCLocation Method
	// Compute the distance in meters
	public double distanceTo(final TSCLocationModel tscLocationModel)

	{
		final TSCAddressData tscAddressData = new TSCAddressData();

		populate(tscLocationModel, tscAddressData);

		final double earthRadius = 3958.75;
		final double dLat = Math.toRadians(latitude - tscAddressData.latitude);
		final double dLng = Math.toRadians(longitude - tscAddressData.longitude);
		final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latitude))
				* Math.cos(Math.toRadians(latitude)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		final double dist = earthRadius * c;

		return dist;
	}

	@Override
	public String toString()
	{
		return address + " (" + latitude + ", " + longitude + ")";
	}


	/**
	 * 
	 */
	public TSCAddressData()
	{
		// YTODO Auto-generated constructor stub
	}


	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 *           the address to set
	 */
	public void setAddress(final String address)
	{
		this.address = address;
	}

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

	/**
	 * @return the distance
	 */
	public double getDistance()
	{
		return distance;
	}

	/**
	 * @param distance
	 *           the distance to set
	 */
	public void setDistance(final double distance)
	{
		this.distance = distance;
	}
}
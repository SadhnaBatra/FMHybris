/**
 * 
 */
package com.federalmogul.core.tsclookup;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.model.TSCLocationModel;


/**
 * @author anapande
 * 
 */
public class TSCLookupServiceImpl implements TSCLookupService, Populator<TSCLocationModel, TSCAddressData>

{
	private static final Logger LOG = Logger.getLogger(TSCLookupServiceImpl.class);
	@Autowired
	private FlexibleSearchService flexibleSearchService;


	@Override
	public Map<TSCLocationModel, Integer> getTSCLocationByZipCode(final String zipCode) throws IOException
	{

		LOG.info("InstTSCLocationByZipCode of ServiceImpl ");

		final Map<TSCLocationModel, Integer> map = new HashMap<TSCLocationModel, Integer>();
		final ArrayList<TSCLocationModel> stores = getTSCLocations();
		final String addr = zipCode;
		final TSCAddressData loc = makeLocation(addr);//l & l
		LOG.info("Your TSCLocation is at: " + loc);
		LOG.info("");
		LOG.info("Entering inside for loop for fetching 5 records:");

		final double minDistance = -1;
		final double maxDistance = 100;
		for (final TSCLocationModel location : stores)
		{
			final double distance = loc.distanceTo(location);
			LOG.info("distance :: " + distance + "location :: " + location.getTSCLocId());
			if (distance > minDistance && distance <= maxDistance)
			{

				LOG.info("The closet TSC Location is at: " + location);
				LOG.info("The distance is " + distance + " Miles");
				final List<Double> distanceList = new ArrayList<Double>();
				distanceList.add(distance);
				Collections.sort(distanceList);

				map.put(location, (int) distance);
			}
		}


		return sortMapByValue(map);
	}

	/**
	 * Sorts map by values in ascending order.
	 * 
	 * @param <K>
	 *           map keys type
	 * @param <V>
	 *           map values type
	 * @param map
	 * @return
	 */
	@SuppressWarnings("javadoc")
	public <K, V extends Comparable<V>> LinkedHashMap<K, V> sortMapByValue(final Map<K, V> map) throws IOException
	{

		LOG.info("Inside sortMapByValue of ServiceImpl ");

		final List<Entry<K, V>> sortedEntries = sortEntriesByValue(map.entrySet());
		final LinkedHashMap<K, V> sortedMap = new LinkedHashMap<K, V>(map.size());
		for (final Entry<K, V> entry : sortedEntries)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	/**
	 * Sorts map entries by value in ascending order.
	 * 
	 * @param <K>
	 *           map keys type
	 * @param <V>
	 *           map values type
	 * @param entries
	 * @return
	 */
	@SuppressWarnings("javadoc")
	private static <K, V extends Comparable<V>> List<Entry<K, V>> sortEntriesByValue(final Set<Entry<K, V>> entries)
	{
		final List<Entry<K, V>> sortedEntries = new ArrayList<Entry<K, V>>(entries);
		Collections.sort(sortedEntries, new ValueComparator<V>());
		return sortedEntries;
	}

	/**
	 * Komparator podla hodnot v polozkach mapy.
	 * 
	 * @param <V>
	 *           typ hodnot
	 */
	private static class ValueComparator<V extends Comparable<V>> implements Comparator<Entry<?, V>>
	{
		@Override
		public int compare(final Entry<?, V> entry1, final Entry<?, V> entry2)
		{
			return entry1.getValue().compareTo(entry2.getValue());
		}
	}



	// Make a Location object from a string address
	//get longitude and latitude from this method into addressData bean
	public static TSCAddressData makeLocation(final String addr) throws IOException
	{
		String url = "http://maps.google.com/maps/api/geocode/json?sensor=false&address=";
		url += URLEncoder.encode(addr, "UTF-8");

		// calling readURL method
		final String content = readURLContent(url);

		final String address = extractMiddle(content, "\"formatted_address\" : \"", "\",");
		final String latlng = extractMiddle(content, "\"location\" : {", "},");
		final double latitude = Double.parseDouble(extractMiddle(latlng, "\"lat\" :", ","));
		final double longitude = Double.parseDouble(extractMiddle(latlng, "\"lng\" :"));

		return new TSCAddressData(address, latitude, longitude);
	}




	//Get storelocation ID, longitude and latitude from addressData bean through Flexisearch query
	public ArrayList<TSCLocationModel> getTSCLocations()
	{
		final ArrayList<TSCLocationModel> locations = new ArrayList<TSCLocationModel>();



		final String queryString = "SELECT {p:" + TSCLocationModel.PK + "}" + "FROM {" + TSCLocationModel._TYPECODE + " AS p}";


		//final String queryString = "SELECT {p:" + TSCLocationModel.TSCLOCID + "} +{p:" + TSCLocationModel.LATITUDE + "} +{p:"
		//	+ TSCLocationModel.LONGITUDE + "}" + "FROM {" + TSCLocationModel._TYPECODE + " AS p}";



		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);


		final List<TSCLocationModel> result = flexibleSearchService.<TSCLocationModel> search(query).getResult();

		locations.addAll(result);

		LOG.info("Done locations.addAll(result) in getTSCLocations()");

		return locations;
	}

	// Compute the distance in meters based on the latitude and longitude from getTSCLocation Method


	@Override
	public String toString()
	{
		final TSCAddressData tscAddressData = new TSCAddressData();

		final double latitude = tscAddressData.getLatitude();
		final double longitude = tscAddressData.getLongitude();
		final String address = tscAddressData.getAddress();

		return address + " (" + latitude + ", " + longitude + ")";
	}


	// Read from URL and return the content in a String
	public static String readURLContent(final String urlString) throws IOException
	{
		final URL url = new URL(urlString);
		String content = new String();
		LOG.info("url ==>" + url.toString());
		try
		{
			// PROXY

			LOG.info("Connecting to the url...\n");
			//System.setProperty("http.proxyHost", "proxy2.wipro.com");
			//System.setProperty("http.proxyPort", "8080");

			url.openConnection();

			LOG.info("connection opened...");
			LOG.info(" ");
			LOG.info("Entering inside scanner class to open url.openStream");
			final Scanner scan = new Scanner(url.openStream());
			LOG.info("scan ==>" + scan.toString());
			LOG.info("url.openStream() ==>" + url.openStream());
			while (scan.hasNext())
			{
				content += scan.nextLine();
			}
			scan.close();
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

		return content;
	}

	// Extract the middle part of a string from "open" to "close"
	public static String extractMiddle(final String str, final String open, final String close)
	{
		final int begin = str.indexOf(open) + open.length();
		final int end = str.indexOf(close, begin);
		return str.substring(begin, end);
	}


	// Extract the middle part of a string from "open" to the end
	public static String extractMiddle(final String str, final String open)
	{
		final int begin = str.indexOf(open) + open.length();
		return str.substring(begin);
	}


	@Override
	public void populate(final TSCLocationModel source, final TSCAddressData target) throws ConversionException
	{
		try
		{
			target.setAddress(source.getAddressLine1());
			target.setLatitude(source.getLatitude());
			target.setLongitude(source.getLongitude());
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.tsclookup.TSCLookupService#getStore(java.lang.String)
	 */
	@Override
	public TSCLocationModel getStore(final String code)
	{
		// YTODO Auto-generated method stub


		validateParameterNotNull(code, "Store code must not be null!");
		final StringBuilder query = new StringBuilder("SELECT {" + TSCLocationModel.PK + "}");
		query.append(" FROM {" + TSCLocationModel._TYPECODE + " AS tsc }");
		query.append(" WHERE {tsc." + TSCLocationModel.CODE + "} = ?" + TSCLocationModel.CODE);

		final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(query.toString());
		flexibleSearchQuery.addQueryParameter(TSCLocationModel.CODE, code);

		LOG.info("query :: " + query.toString());

		final SearchResult<TSCLocationModel> result = flexibleSearchService.search(flexibleSearchQuery);
		if (null != result && result.getResult().size() > 0)
		{

			return result.getResult().get(0);
		}
		else
		{
			//LOG.error("No tax row  found for the given tax");
			LOG.error("Result NULL");
			return null;
		}



	}

}

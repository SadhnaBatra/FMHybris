/**
 * 
 */
package com.federalmogul.core.tsclookup;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.apache.log4j.Logger;


/**
 * @author BA300528
 * 
 */
public class TSCHelperUtil
{
	private static final Logger LOG = Logger.getLogger(TSCHelperUtil.class);

	// Make a Location object from a string address
	//get longitude and latitude from this method into addressData bean
	public static AddressHeader makeLocation(final String addr) throws IOException
	{
		String url = "http://maps.google.com/maps/api/geocode/json?sensor=false&address=";
		url += URLEncoder.encode(addr, "UTF-8");

		// calling readURL method
		final String content = readURLContent(url);

		//final String address = extractMiddle(content, "\"formatted_address\" : \"", "\",");
		final String latlng = extractMiddle(content, "\"location\" : {", "},");
		final double latitude = Double.parseDouble(extractMiddle(latlng, "\"lat\" :", ","));
		final double longitude = Double.parseDouble(extractMiddle(latlng, "\"lng\" :"));

		return new AddressHeader(latitude, longitude);
	}

	// Read from URL and return the content in a String
	public static String readURLContent(final String urlString) throws IOException
	{
		final URL url = new URL(urlString);
		String content = new String();
		//LOG.info("url ==>" + url.toString());
		try
		{
			// PROXY

			LOG.info("Connecting to the url...\n");
			
			url.openConnection();

			LOG.info("connection opened...");
			LOG.info(" ");
			LOG.info("Entering inside scanner class to open url.openStream");
			final Scanner scan = new Scanner(url.openStream());
			//LOG.info("scan ==>" + scan.toString());
			//LOG.info("url.openStream() ==>" + url.openStream());
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


}

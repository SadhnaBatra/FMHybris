package com.federalmogul.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.federalmogul.core.constants.FmCoreConstants;

import de.hybris.platform.util.Config;

public class FMUtils {
	
	    private static final Logger LOG = Logger.getLogger(FMUtils.class);
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
		
		public static String readURLContent(final String urlString) throws IOException
		{
			final URL url = new URL(urlString);
			String content = new String();
			try
			{
				url.openConnection();
				final Scanner scan = new Scanner(url.openStream());
				while (scan.hasNext())
				{
					content += scan.nextLine();
				}
				scan.close();
			}
			catch (final Exception e)
			{
				LOG.error("Exception...." + e.getMessage());
			}
			return content;
		}
		
		public static String makeLocation(final String addr) throws IOException
		{
			//move to local.properties
			String url = Config.getParameter(FmCoreConstants.GEOCODE_URL);
			url += URLEncoder.encode(addr, "UTF-8");
			// calling readURL method
			final String content = FMUtils.readURLContent(url);
			//final String address = extractMiddle(content, "\"formatted_address\" : \"", "\",");
			final String latlng = FMUtils.extractMiddle(content, "\"location\" : {", "},");
			final double latitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lat\" :", ","));
			final double longitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lng\" :"));
			final String latlang = latitude + "_" + longitude;
			return latlang;
		}
		
		public static String getLocationByLocationQuery(final String locationQuery) throws IOException
		{
			String url = Config.getParameter(FmCoreConstants.GEOCODE_URL);
			url += URLEncoder.encode(locationQuery, "UTF-8");
			// calling readURL method
			final String content = FMUtils.readURLContent(url);
			final String latlng = FMUtils.extractMiddle(content, "\"location\" : {", "},");
			return latlng;
		}
		
		public static List<String> formShopTypeFilterString(String shopType){
			List<String> shopTypeList = new ArrayList<String>();
			if(FmCoreConstants.JOBBER.equals(shopType)){
				shopTypeList.add(FmCoreConstants.JOBBER);	
				shopTypeList.add(FmCoreConstants.BOTH);
			}else if(FmCoreConstants.INSTALLER.equals(shopType)){
				shopTypeList.add(FmCoreConstants.INSTALLER);	
				shopTypeList.add(FmCoreConstants.BOTH);
		    }else if(FmCoreConstants.ALL.equals(shopType)||FmCoreConstants.BOTH.equals(shopType)){
		    	shopTypeList.add(FmCoreConstants.JOBBER);
		    	shopTypeList.add(FmCoreConstants.INSTALLER);
		    	shopTypeList.add(FmCoreConstants.BOTH);
		    }else{
		    	shopTypeList.add(FmCoreConstants.INVALIDSHOPTYPE);
		    }
			return shopTypeList;
		}	
		
		/**
		 * For Retrieving files
		 * 
		 * @return
		 */
	    public static List<String> retrieveFiles(String code)
		{
			List<String> fileNames = null;
			String tempDirectory = getTempDirectory(code);
			LOG.info("In the retrieveFiles() method");
			final File inputDirectory = getFileInputDirectory(code);
			final String[] rawFileNames = inputDirectory.list();
			if (rawFileNames.length < 1)
			{
				LOG.info("No files to process");
				return fileNames;
			}
			fileNames = new ArrayList<String>();
			for (int i = 0; i < rawFileNames.length; i++)
			{
				fileNames.add(tempDirectory + rawFileNames[i]);
			}
			return fileNames;
		}
	     
	     private static File getFileInputDirectory(String code){
				File inputDirectory=null;
				if(FmCoreConstants.ONLINE_RETAILER.equals(code)){
					inputDirectory = new File(Config.getParameter(FmCoreConstants.ONLINE_RETAILER_FILE_LOCATION));
				}else if(FmCoreConstants.DISTRIBUTOR.equals(code)){
					inputDirectory = new File(Config.getParameter(FmCoreConstants.DISTRIBUTOR_FILE_LOCATION));
				}
				return inputDirectory;
			}
		
		private static String getTempDirectory(String code){
			String tempDirectory=null;
			if(FmCoreConstants.ONLINE_RETAILER.equals(code)){
				tempDirectory = Config.getParameter(FmCoreConstants.ONLINE_RETAILER_TEMP_LOCATION);
			}else if(FmCoreConstants.DISTRIBUTOR.equals(code)){
				tempDirectory = Config.getParameter(FmCoreConstants.DISTRIBUTOR_FILE_TEMP_LOCATION);
			}
			return tempDirectory;
		}
		
		public static double distanceTo(final Double refLat,final Double refLon,double latitude,double longitude)
		{
			final double earthRadius = 3958.75;
			final double dLat = Math.toRadians(latitude - refLat);
			final double dLng = Math.toRadians(longitude - refLon);
			final double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latitude))
					* Math.cos(Math.toRadians(latitude)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
			final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			final double dist = earthRadius * c;

			return dist;
		}
                
                /*This method will determine if the account code is invalid or not
                  This "002" check for invalid account code is happening while fulfilling order in SAP 
                  Hence we have this condition to flag as invalid Hybris itself
                  This piece of code should go away once the issue is fixed in SAP*/
                public static boolean isPartnerFunctionCodeValid(String code){
                  if (code.startsWith(FmCoreConstants.INVALIDACCOUNTIDCODE))
                  {
                    LOG.info("DynamicFMCustomerType invalid");
                    return false;
                  }
                  return true;
                }

}

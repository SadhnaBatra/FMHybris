package com.federalmogul.core.tsclookup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * @author Balaji
 * 
 */
public class CsvFileReader
{

	//Delimiter used in CSV file
	private static final String COMMA_DELIMITER = ";";
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final int ADDRESS_ADDR_ID_IDX = 0;
	private static final int ADDRESS_STREETNUMBER1 = 1;
	private static final int ADDRESS_STREETNUMBER2 = 2;
	private static final int ADDRESS_TOWN = 3;
	private static final int ADDRESS_REGION = 4;
	private static final int ADDRESS_COUNTRY = 5;
	private static final int ADDRESS_POSTALCODE = 6;
	private static final int ADDRESS_PHONE = 7;
	private static final int ADDRESS_STORENAME = 8;
	private static final int ADDRESS_BRAND = 9;
	private static final int ADDRESS_SHOPTYPE = 10;
	private static final Logger LOG = Logger.getLogger(CsvFileReader.class);


	public static void readCsvFile(final String fileName)
	{

		LOG.info("File name:::" + fileName);
		BufferedReader fileReader = null;
		final String addressFileName = System.getProperty("user.home") + "/address.csv";
		LOG.info("outputFileName:::" + addressFileName);
		final String pointOfServiceFileName = System.getProperty("user.home") + "/pointOfService.csv";
		LOG.info("pointOfServiceFileName:::" + pointOfServiceFileName);

		AddressHeader addressData;

		try
		{

			//Create a new list of address to be filled by CSV file data 
			final List<AddressHeader> addressDataList = new ArrayList<AddressHeader>();

			String line = "";

			final String fileImpexHeader = "INSERT_UPDATE PointOfService|name[unique=true]|type(code)|address(&addrID)|latitude|longitude|basestore(uid)[default=$storeUid]|brand|shopType";
			//final String ADDRESS_IMPEX_HEADER = "INSERT_UPDATE Address;&addrID;streetnumber;town;streetname;postalcode;owner(PointOfService.name)[unique=true]";

			final String addressImpexHeader = "INSERT_UPDATE Address;&addrID;owner(PointOfService.name)[unique=true];streetname;streetnumber;postalcode;town;country(isocode);phone1;";

			//Create the file reader
			fileReader = new BufferedReader(new FileReader(fileName));

			//Read the CSV file header to skip it
			//fileReader.readLine();

			//Read the file line by line starting from the second line
			while ((line = fileReader.readLine()) != null)
			{
				if (line == fileImpexHeader)
				{
					LOG.info("FILE_IMPEX_HEADER:::" + fileImpexHeader);
				}
				else
				{
					//System.out.println("inside else loop");
					//Get all tokens available in line
					final String[] tokens = line.split(COMMA_DELIMITER);
					//System.out.println("tokens:::" + tokens);
					if (tokens.length > 0)
					{
						//Create a new address object and fill his  data
						//final Student student = new Student(Long.parseLong(tokens[STUDENT_ID_IDX]), tokens[STUDENT_FNAME_IDX],tokens[STUDENT_LNAME_IDX], tokens[STUDENT_GENDER], Integer.parseInt(tokens[STUDENT_AGE]));
						//students.add(student);

						addressData = new AddressHeader(tokens[ADDRESS_ADDR_ID_IDX], tokens[ADDRESS_STREETNUMBER1],
								tokens[ADDRESS_STREETNUMBER2], tokens[ADDRESS_TOWN], tokens[ADDRESS_REGION], tokens[ADDRESS_COUNTRY],
								tokens[ADDRESS_POSTALCODE], tokens[ADDRESS_PHONE], tokens[ADDRESS_STORENAME], tokens[ADDRESS_BRAND],
								tokens[ADDRESS_SHOPTYPE]);
						addressDataList.add(addressData);
					}
				}
			}
			// Start Writing file as per address impex file	
			LOG.info(" After reading:" + fileName + " csv file ...Writing in:" + addressFileName + " CSV file:");
			FileWriter fileWriter = null;

			try
			{
				fileWriter = new FileWriter(addressFileName);
				LOG.info(" addressDataList:" + addressDataList.size());
				//Write the CSV file header
				fileWriter.append(addressImpexHeader.toString());
				for (final AddressHeader addressData1 : addressDataList)
				{
					LOG.info(addressData1.toString());
					//Add a new line separator after the header
					fileWriter.append(NEW_LINE_SEPARATOR);

					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(addressData1.getAddrID());//addrID 1

					fileWriter.append(COMMA_DELIMITER);

					final String nameBrand = addressData1.getStoreName() + "-" + addressData1.getBrand();
					LOG.info("nameBrand::" + nameBrand);

					fileWriter.append(nameBrand);//owner 2

					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(addressData1.getStreetnumber1());//streetnumber 3
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(addressData1.getTown());//town 4
					fileWriter.append(COMMA_DELIMITER);

					final String zipCode = addressData1.getRegion() + " " + addressData1.getPostalcode();
					LOG.info("zipCode::" + zipCode);

					fileWriter.append(zipCode);//postalcode 5

					fileWriter.append(COMMA_DELIMITER);

					fileWriter.append("");//streetname 6
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append("");//owner 7
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append("");//phone 8

					//					fileWriter.append(addressData1.getPhone1());//Not Manadatory
					//					fileWriter.append(COMMA_DELIMITER);
					//					fileWriter.append(addressData1.getRegion());//Not Manadatory
					//					fileWriter.append(COMMA_DELIMITER);
					//					fileWriter.append(addressData1.getCountry());//Not Manadatory
					//					fileWriter.append(COMMA_DELIMITER);
				}

				LOG.info(":: " + addressFileName + " ::CSV file  created successfully !!!");

			}
			catch (final Exception e)
			{
				LOG.info("Error in CsvFileWriter !!!");
				LOG.error(e.getMessage());

			}
			finally
			{

				try
				{
					fileWriter.flush();
					fileWriter.close();
				}
				catch (final IOException e)
				{
					LOG.info("Error while flushing/closing fileWriter !!!");
					LOG.error(e.getMessage());
				}

			}

			// End Writing file as per address impex file

			// Start Writing file as per pointOfService impex file	
			LOG.info(" After reading:" + fileName + " csv file ...Writing in:" + pointOfServiceFileName + " CSV file:");
			//final String posImpexHeader = "INSERT_UPDATE PointOfService;name[unique=true];type(code)[default=STORE];address(&addrID);latitude;longitude;basestore(uid)[default=$storeUid];brand;shopType";

			final String posImpexHeader = "INSERT_UPDATE PointOfService;name[unique=true];type(code);address(&addrID);latitude;longitude;basestore(uid)[default=$storeUid];brand;shopType";
			FileWriter fileWriterPos = null;

			try
			{
				fileWriterPos = new FileWriter(pointOfServiceFileName);
				LOG.info(" addressDataList:" + addressDataList.size());
				//Write the CSV file header
				fileWriterPos.append(posImpexHeader.toString());
				for (final AddressHeader addressData1 : addressDataList)
				{
					LOG.info(addressData1.toString());
					//Add a new line separator after the header
					fileWriterPos.append(NEW_LINE_SEPARATOR);

					fileWriterPos.append(COMMA_DELIMITER);

					final String nameBrand = addressData1.getStoreName() + "-" + addressData1.getBrand();
					LOG.info("nameBrand::" + nameBrand);

					fileWriterPos.append(nameBrand);//owner(name) 1
					fileWriterPos.append(COMMA_DELIMITER);
					fileWriterPos.append("STORE"); // type STORE 2
					fileWriterPos.append(COMMA_DELIMITER);
					fileWriterPos.append(addressData1.getAddrID()); //addrID (address) 3
					fileWriterPos.append(COMMA_DELIMITER);

					final String addr1 = addressData1.getStreetnumber1() + addressData1.getStreetnumber2()
							+ addressData1.getPostalcode();

					if (addr1.isEmpty())
					{
						LOG.info("Address to get latitude and longitude ::" + addr1);
					}
					else
					{
						LOG.info("Address to get latitude and longitude ::" + addr1);
						final AddressHeader posLatLog = TSCHelperUtil.makeLocation(addr1);
						LOG.info("latitude::" + posLatLog.getLatitude());
						LOG.info("longitude::" + posLatLog.getLongitude());

						fileWriterPos.append(String.valueOf(posLatLog.getLatitude()));//latitude 4
						fileWriterPos.append(COMMA_DELIMITER);

						fileWriterPos.append(String.valueOf(posLatLog.getLongitude()));//longitude 5
						fileWriterPos.append(COMMA_DELIMITER);
					}

					fileWriterPos.append("");//basestore  6
					fileWriterPos.append(COMMA_DELIMITER);

					fileWriterPos.append(addressData1.getBrand());//brand 7
					fileWriterPos.append(COMMA_DELIMITER);
					fileWriterPos.append(addressData1.getShopType());//ShopType 8
					fileWriterPos.append(COMMA_DELIMITER);

				}

				LOG.info(":: " + pointOfServiceFileName + " ::CSV file  created successfully !!!");

			}
			catch (final Exception e)
			{
				LOG.info("Error in CsvFileWriter !!!");
				LOG.error(e.getMessage());
			}
			finally
			{

				try
				{
					fileWriterPos.flush();
					fileWriterPos.close();
				}
				catch (final IOException e)
				{
					LOG.info("Error while flushing/closing fileWriterPos !!!");
					LOG.error(e.getMessage());
				}

			}

			//End	Writing file as per pointOfService impex file		

		}
		catch (final Exception e)
		{
			LOG.info("Error in CsvFileReader !!!");
			LOG.error(e.getMessage());
		}
		finally
		{
			try
			{
				fileReader.close();
			}
			catch (final IOException e)
			{
				LOG.info("Error while closing fileReader !!!");
				LOG.error(e.getMessage());
			}
		}

	}
}

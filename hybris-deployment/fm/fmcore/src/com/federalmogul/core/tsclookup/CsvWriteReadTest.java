package com.federalmogul.core.tsclookup;

/**
 * @author Balaji
 * 
 */
public class CsvWriteReadTest
{

	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		final String inputFileName = System.getProperty("user.home") + "/storeadd20150331-092454-130.csv";

		CsvFileReader.readCsvFile(inputFileName);

	}

}

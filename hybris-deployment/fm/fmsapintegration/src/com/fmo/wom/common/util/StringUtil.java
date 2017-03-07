/**
 * 
 */
package com.fmo.wom.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.apache.commons.lang.math.NumberUtils;

import com.fmo.wom.common.exception.WOMIPOValidationException;
import com.fmo.wom.common.exception.WOMResourceException;

/**
 * Utilities for manipulating String objects.
 *
 */
public class StringUtil {

	private static final String DELIMITER = " ";
	private static final String US_ZIP_REGEX = "^\\d{5}[-]*(\\d{4})?$";
    private static final String CANADA_POSTAL_CODE_REGEX = "(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Z,a-z]{1})[- ]*(\\d{1}[A-Z,a-z]{1}\\d{1})$";  
	    
	private static final String ALPHA_NUMERIC_CHARS_WITH_ASTERICK =
		"*0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String ALPHA_NUMERIC_CHARACTERS =
		"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final String NUMERIC_CHARACTERS =
		"0123456789";
	private static final String ALPHA_CHARACTERS =
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static final String JAXP_SCHEMA_LANGUAGE ="http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
	static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	public static String getStackTrace(Throwable aThrowable) {
	    final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	}

	  
	/**
	 * Concatenate character ch until str is length len.
	 * If str is null, the returned string will consist of only
	 * len number of character ch.
	 * 
	 * @param str - <code>String</code> to be concatenated
	 * @param ch - <code>char</code> letter to use when padding
	 * @param len - <code>int</code> minimum length of returned String
	 * @return <code>String</code>
	 */
	public static String stringPad(String str, char ch, int len) {
		if (str == null) {
			str = "";
		}
		StringBuffer sb = new StringBuffer(str);
		for (int i = 1; i <= (len - str.length()); i++) {
			sb.append(ch);
		}
		return sb.substring(0, len);
	}

	/**
	 * Return a String that is stripped off non-alphanumeric characters except '*'
	 * return null if source is null.
	 *
	 * @param source - <code>String</code>
	 * @return <code>String</code>
	 */
	public static String stringSquash(String source) {
		if(null == source)
			return source;
		StringBuffer newString = new StringBuffer();

		for (int i = 0; i < source.length(); i++) {
			if (ALPHA_NUMERIC_CHARS_WITH_ASTERICK
				.indexOf(source.substring(i, i + 1))
				>= 0) {
				newString.append(source.substring(i, i + 1));
			}
		}
		return newString.toString();
	}

	/**
	 * Return a String formatted to have upper case letter at the
	 * begining of each word, and lower case letters for the rest.
	 *
	 * @param item - <code>String</code>
	 * @return <code>String</code>
	 */
	public static String stringCapitalize(String source) {
		StringBuffer newString = new StringBuffer();
		String token = null;

		if (null == source || source.trim().length() == 0) {
			return source;
		}

		StringTokenizer tokenizer = new StringTokenizer(source, DELIMITER);
		if (tokenizer.countTokens() > 0) {
			while (tokenizer.hasMoreTokens()) {
				token = tokenizer.nextToken();
				if (token != null) {
					if (token.length() > 1) {
						if (newString.length() > 0) {
							newString.append(DELIMITER);
						}
						newString.append(token.substring(0, 1).toUpperCase());
						newString.append(token.substring(1).toLowerCase());
					} else
						newString.append(token.toUpperCase());
				}
			}
			return newString.toString();
		} else
			return null;
	}

	/**
     * Convert the value to an string then prepend 0s until the
     * length of the result string is len.
     * 
     * @param value - <code>long</code> value to be converted to a string
     * @param len - <code>int</code> minimum length of returned String
     * @return <code>String</code>
     */
    public static String stringPad(long value, int len) {
        if (value < 0 || len < 0)
            throw new IllegalArgumentException(
                    "Bad arguments: value: " + value + " length:" + len);
        String valueString = "" + value;
        if (len < valueString.length())
            throw new IllegalArgumentException(
                "Bad arguments: value: " + value + " longer than length:" + len);
        StringBuffer sb = new StringBuffer(len);

        for (int i = 0, limit = len - valueString.length(); i < limit; i++) {
            sb.append('0');
        }
        sb.append(valueString);
        return (sb.toString());
    }
	/**
	 * Convert the value to an string then prepend 0s until the
	 * length of the result string is len.
	 * 
	 * @param value - <code>int</code> value to be converted to a string
	 * @param len - <code>int</code> minimum length of returned String
	 * @return <code>String</code>
	 */
	public static String stringPad(int value, int len) {
	    long longValue = (long) value;
		return stringPad(longValue, len);
	}

	/** 
	 * Left Pad Prefixes the string input with enough copies of pad that it
	 * has length equal to length.  Returns null and logs an error if
	 * padding with pad cannot produce a string of exactly length
	 * Usually, pad should be a single character.
	 * @param input - <code>String</code> input string to be manipulated
	 * @param length - <code>int</code> minimum length of returned String
	 * @param pad - <code>String</code> padding to be added
	 * @return <code>String</code>
	 */
	public static String lpad(String input, int length, String pad) {
		return pad(input, length, pad, false);
	}
	
	/**
	 * Left Pad only for Numeric String values
	 *  
	 */
	public static String lpadForNumericStringOnly(String input, int length, String pad) {
		if (NumberUtils.isDigits(input))
			return pad(input, length, pad, false);
		return input;
		
	}
	
	/**
	 * Right Pad- Prefixes the string input with enough copies of pad that it
	 * has length equal to length.  Returns null and logs an error if
	 * padding with pad cannot produce a string of exactly length.
	 * Usually, pad should be a single character.
	 * @param input - <code>String</code> input string to be manipulated
	 * @param length - <code>int</code> minimum length of returned String
	 * @param pad - <code>String</code> padding to be added
	 */
	public static String rpad(String input, int length, String pad) {	
		return pad(input, length, pad, true);
	}
	
	private static String pad(String input, int length, String pad, boolean rightPad){
		if( null == input) 
			return null;
		if(null == pad)
			return input;
		if(length <= 0)
			return input;
		StringBuffer sb = new StringBuffer();
		int padLength = length - input.length();
		while (sb.length() < padLength) {
			sb.append(pad);
		}
		sb.append(input);
		
		if(rightPad) {
			sb.delete(0, sb.length());
			
			sb.append(input);
			for(int i = 0; i < padLength; i++){
				sb.append(pad);
			}
		}
		if (sb.length() != length) {
			return input;
		}
		return sb.toString();
	
	}

	/**
	* Return a String that is stripped off non-alphanumeric characters.
	*
	* @param item - <code>String</code>
	* @return <code>String</code>
	*/
	public static String stringSquashNoAsterick(String source) {
		if (null == source)
			return null;
		if(source.length() <= 0)
			return source;
		StringBuffer newString = new StringBuffer();

		for (int i = 0; i < source.length(); i++) {
			if (ALPHA_NUMERIC_CHARACTERS.indexOf(source.substring(i, i + 1))
				>= 0) {
				newString.append(source.substring(i, i + 1));
			}
		}
		return newString.toString();
	}
	
	public static  Source getStringSource(String xmlString) 
		  throws WOMIPOValidationException  {
			DOMSource source = new DOMSource(StringUtil.getDocument(xmlString));
			return source;
	}
	public static  Document getDocument(String xmlString) 
	  throws WOMIPOValidationException {
		return getDocument(xmlString,false,null,null);
	}

	public static  Document getDocument(String xmlString, boolean isValidate,
			ClassLoader classLoader, String schemaFile) 
		  throws WOMIPOValidationException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		XMLErrorHandler errorHandler = null; 
		if(isValidate){

			URL schemaUrl = classLoader.getResource(schemaFile);
			factory.setValidating(true);
			factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
			factory.setAttribute(JAXP_SCHEMA_SOURCE, schemaUrl!=null?schemaUrl.toString():"");
			errorHandler = new XMLErrorHandler();
		}
		DocumentBuilder builder;
		Document document = null;
		try {
			builder = factory.newDocumentBuilder();
			builder.setErrorHandler(errorHandler);
			document = builder.parse(new InputSource(new StringReader(xmlString)));
		}catch (Exception e) {
			throw new WOMIPOValidationException("Error while creating xml Document object from xml string", e);			
		}
		if(errorHandler!=null && errorHandler.getErrorCount()>0){
			throw new WOMIPOValidationException("Schema validation error:"+errorHandler.getAllErrorMessages());
		}
		return document;
	}
	public static String readFileFromClasspath(Class className, String file) throws WOMResourceException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(className.getResourceAsStream(file)));
		StringBuffer contents = new StringBuffer();
		String line = null;
		try {
			while((line=reader.readLine())!=null){
				contents.append(line);
			}
			reader.close();
		} catch (IOException e) {
			throw new WOMResourceException("Unable to read the file:"+file,e);
		}
		
		return contents.toString();
	}
	
	public static String readFile(String file) throws WOMResourceException{
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			reader = new BufferedReader( new FileReader (file));
			String line  = null;     
			     
			String ls = System.getProperty("line.separator");     
			while( ( line = reader.readLine() ) != null ) {         
				stringBuilder.append( line );         
				stringBuilder.append( ls );     
			}     
		} catch (FileNotFoundException e) {
			throw new WOMResourceException("Error reading file:"+file,e);
		} catch (IOException e) {
			throw new WOMResourceException("Error reading file:"+file,e);
		}     
		return stringBuilder.toString(); 
 
	}
	public static String getDateString(Date date, String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
	/** return the ISO 8601 standard Date Format. Use this in place of SimpleDateFormat which is not thread safe.
	 * @param date
	 * @return
	 */
	public static String getISODate(Date date) {
		SimpleDateFormat B2BDateFormat = new SimpleDateFormat("yyyy-MM-dd");	// SDF is not thread-safe, so create these every time
		return B2BDateFormat.format(date);
	}
	public static String getISODate(Date date, int daysToAdd) {
		Calendar calDate = Calendar.getInstance();
		calDate.setTime(date);
		calDate.add(Calendar.DAY_OF_YEAR, daysToAdd);
		SimpleDateFormat B2BDateFormat = new SimpleDateFormat("yyyy-MM-dd");	// SDF is not thread-safe, so create these every time
		return B2BDateFormat.format(calDate.getTime());
	}
	/** return the ISO 8601 standard DateTime Format
	 * @param date
	 * @return
	 */
	public static String getISODateTime(Date datetime) {
		SimpleDateFormat B2BDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	// SDF is not thread-safe, so create these every time
		SimpleDateFormat B2BTimeZone = new SimpleDateFormat("Z");
		StringBuffer sb = new StringBuffer(B2BDateTimeFormat.format(datetime));
		String tz = B2BTimeZone.format(datetime);
		sb.append(tz.substring(0,3)).append(":00");		
		return sb.toString();
	}
	
	/**
	 * This method returns true if the parameter string is a valid US zip code.  If this
	 * seems to be taking long time, we could precompile the pattern I suppose.
	 */
	public static boolean isNumeric(String value) {
	     return NumberUtils.isDigits(value);
	}
	
	/**
	 * This method returns true if the parameter string is a valid US zip code.  If this
	 * seems to be taking long time, we could precompile the pattern I suppose.
	 */
	public static boolean isAValidUSZipCode(String zip) {
	     return Pattern.matches(US_ZIP_REGEX, zip);
	}
	 
	/**
     * This method returns true if the parameter string is a valid Canadian Postal code.  Same blurbie
     * for recompile as above.
     */
    public static boolean isAValidCanadianPostalCode(String aPostalCode) {
        if (aPostalCode == null) return false;
        return Pattern.matches(CANADA_POSTAL_CODE_REGEX, aPostalCode);
    }
 
    public static String getFormattedCanadianPostalCode(String aPostalCode) {
       if (isAValidCanadianPostalCode(aPostalCode) == false) {
           // returning the input since not sure what we have here
           return aPostalCode;
       }
       
       Pattern p = Pattern.compile(CANADA_POSTAL_CODE_REGEX);
       Matcher m = p.matcher(aPostalCode);
       if (m.matches() == false) {
           return aPostalCode;
       }

       return m.group(1).toUpperCase()+" "+m.group(2).toUpperCase();
   }
    
    
    public static String getFormattedUpldCanadianPostalCode(String aPostalCode) {
        if (isAValidCanadianPostalCode(aPostalCode) == false) {
            // returning the input since not sure what we have here
            return aPostalCode;
        }
        
        Pattern p = Pattern.compile(CANADA_POSTAL_CODE_REGEX);
        Matcher m = p.matcher(aPostalCode);
        if (m.matches() == false) {
            return aPostalCode;
        }

        return m.group(1).toUpperCase()+"-"+m.group(2).toUpperCase();
    }
}

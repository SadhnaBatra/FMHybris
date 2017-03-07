package com.federalmogul.storefront.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;


public class UtilDate
{

	private static final Logger LOG = Logger.getLogger(UtilDate.class);
	public static final String DTLONG = "yyyyMMddHHmmss";

	public static final String SIMPLE = "yyyy-MM-dd HH:mm:ss";

	public static final String DTSHORT = "yyyyMMdd";

	public static final String DTTIMEZONE = "yyyy-MM-dd HH:mm:ss z";

	public static final String SIMPLEDATE = "MM/dd/yyyy";



	public static String getOrderNum()
	{
		final Date date = new Date();
		final DateFormat df = new SimpleDateFormat(DTLONG);
		return df.format(date);
	}

	public static String getDateFormatter(final TimeZone timezone)
	{
		final Date date = new Date();
		final DateFormat df = new SimpleDateFormat(SIMPLE);
		if (timezone != null)
		{
			df.setTimeZone(timezone);
		}
		return df.format(date);
	}

	public static String getDate()
	{
		final Date date = new Date();
		final DateFormat df = new SimpleDateFormat(DTSHORT);
		return df.format(date);
	}

	public static String getDate(final TimeZone timezone)
	{
		final Calendar cal = Calendar.getInstance();
		final Date date = cal.getTime();
		final DateFormat df = new SimpleDateFormat(DTSHORT);
		if (timezone != null)
		{
			df.setTimeZone(timezone);
		}
		return df.format(date);
	}

	public static String getFullDate(final TimeZone timezone)
	{
		final Calendar cal = Calendar.getInstance();
		final Date date = cal.getTime();
		final DateFormat df = new SimpleDateFormat(DTTIMEZONE);
		if (timezone != null)
		{
			df.setTimeZone(timezone);
		}
		return df.format(date);
	}

	public static String getDate(final String dateFormate, final String date)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat(SIMPLEDATE);
		try
		{
			final Date convertedDate = sdf.parse(date);
			final DateFormat df = new SimpleDateFormat(dateFormate);
			return df.format(convertedDate);

		}
		catch (final ParseException e)
		{
			LOG.error("Error" + e.getMessage());
			return "";
		}



	}

	public static Date getUtilDate(final String dateFormate, final String date)
	{
		if (date != null)
		{

			final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
			try
			{
				final Date convertedDate = sdf.parse(date);
				return convertedDate;

			}
			catch (final ParseException e)
			{
				LOG.error("Error" + e.getMessage());
				return new Date();
			}
		}
		else
		{
			return new Date();
		}

	}
}

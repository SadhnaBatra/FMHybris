package com.fmo.wom.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

public class DateUtil {
	
    private static List<Date> stats;
    
    // This should eventually be migrated to a database table that is updated
    // each year to reflect the stats, but for now, we'll just define some standard
    // ones
    static {
        
        stats = new ArrayList<Date>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        
        // new years
        Calendar aStat = GregorianCalendar.getInstance();
        aStat.set(year, Calendar.JANUARY, 1);
        stats.add(new Date(aStat.getTimeInMillis()));
        
        // july 4th
        aStat.set(year, Calendar.JULY, 4);
        stats.add(new Date(aStat.getTimeInMillis()));
        
        // christmas
        aStat.set(year, Calendar.DECEMBER, 25);
        stats.add(new Date(aStat.getTimeInMillis()));
        
        // thanksgiving (US)
        aStat.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        aStat.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
        stats.add(new Date(aStat.getTimeInMillis()));
        
    }
    
    /**
     * Calculate the next business day that is daysFromToday days away from
     * the given start date
     * @param startDate date to start the calculation
     * @param daysFromToday days aways from start day. 
     * @return the next business day daysFromToday away from startDate.  If the
     * daysFromToday attribute is negative, we should be supporting back in time.
     */
    public static Date getNextBusinessDay(Date startDate, int daysFromToday) {
        
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_YEAR, daysFromToday);
        
        // don't allow saturdays or sundays or stats
        while (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || 
               cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
               isAStatutoryHoliday(cal) ) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        return cal.getTime();
    }
    
    /**
     * Calculate the next business day that is daysFromToday days away from now
     * @param daysFromToday days aways from now
     * @return the next business day daysFromToday away from now.  If the
     * daysFromToday attribute is negative, we should be supporting back in time.
     */
    public static Date getNextBusinesDay(int daysFromToday) {
		return getNextBusinessDay(new Date(), daysFromToday);
	}

    public static Calendar getNoTimeCalendar(Date aDate) {
        
        // get a calendar
        Calendar cal = Calendar.getInstance();
        // set the date on it
        cal.setTime(aDate);
        // clear the time
        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.HOUR_OF_DAY);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
        return cal;
    }
    /**
     * Check if the given date falls on a pre-defined stat holiday. 
     * @param date
     * @return true if the given date is a stat, false otherwise.
     */
    private static boolean isAStatutoryHoliday(Calendar date) {
		
        // go through all our holiday
        for (Date aStat : stats){
            if (DateUtils.isSameDay(aStat, date.getTime())) {
                return true;
            }
        }
        return false;
    }
}

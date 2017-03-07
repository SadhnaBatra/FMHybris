package com.fmo.wom.business.as;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;
import com.fmo.wom.common.util.DateUtil;
import com.fmo.wom.common.util.PropertiesUtil;
import com.fmo.wom.domain.DcShippingMethodBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.NabsShippingCodeBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.SapShippingCodeBO;
import com.fmo.wom.domain.ShippingCodeBO;
import com.fmo.wom.domain.ShippingMethodBO;
import com.fmo.wom.domain.TradingPartnerBO;
import com.fmo.wom.domain.TradingPartnerShippingMethodBO;
import com.fmo.wom.domain.enums.OrderType;


public class ShippingAS {
	
	private static final String EMERGENCY_ORDER_DAYS_ADD_KEY = "emerg.order.days.add";
	private static final String EMERGENCY_ORDER_DAYS_ADD_DEFAULT = "0";
	
	public void setPromisedShipDate(OrderBO order) {
		
		if(order.getOrderType() == OrderType.PICKUP){
			return;
		}
		for (ItemBO anItem : order.getItemList()) {
			anItem.setPromisedShipDate(calculatePromisedShipDate(anItem,
					order.getFutureDate()));
		}
	}
	
	
	public Date calculatePromisedShipDate(ItemBO anItem, Date futureOrderDate) {

		// doing a little bit here to support the future order date attribute in
		// the given order.
		// If its not there, order date is now.
		Date orderDate = futureOrderDate != null ? futureOrderDate : new Date();

		Date now = new Date();
		int daysToAdd = new Integer(PropertiesUtil.getOrderProperty(
				EMERGENCY_ORDER_DAYS_ADD_KEY, EMERGENCY_ORDER_DAYS_ADD_DEFAULT));

		// Get the dc from the inventory we're going to be using to check if the
		// cutoff time has expired.
		// If the order date is in the future, this shouldn't matter.
		if (orderDate.after(now) == false) {
			// this order is not in the future - dc cut off time matters
			InventoryBO selectedInventory = anItem.getSelectedInventory();
			if (selectedInventory != null) {
				DistributionCenterBO shippingDC = selectedInventory.getDistributionCenter();
				if (shippingDC.getEmergencyCutoffTime() != null && isCutOffTimeExpired(orderDate, shippingDC)) {
					// we're passed the emergency cutoff time - add one more day
					daysToAdd += 1;
				}
			}
		}

		// currently just returning the next biz day from order date, but we'll
		// want to put in the inventory
		// cut off check and all that here
		return DateUtil.getNextBusinessDay(orderDate, daysToAdd);
	}
		
	public boolean isCutOffTimeExpired(Date calculationDate,
			DistributionCenterBO distributionCenter) {

		if (distributionCenter == null)
			return false;

		Calendar dcCalculationTime = getDCCalculationTime(calculationDate,
				distributionCenter);

		// now get the cut off time and apply it to the max time.
		Calendar compareTimeMax = getCutoffTime(dcCalculationTime, distributionCenter);

		// check to see if current time is in the the cutoff time warning range
		return dcCalculationTime.getTime().getTime() >= compareTimeMax.getTime()
				.getTime();
	}
	
	private Calendar getDCCalculationTime(Date calculationDate,
			DistributionCenterBO distributionCenter) {

		Calendar calculationTime = GregorianCalendar.getInstance();
		if (calculationDate != null) {
			calculationTime.setTime(calculationDate);
		}

		// Time zone of server where time is calculated
		TimeZone tz = calculationTime.getTimeZone();
		// Eastern Time Zone
        TimeZone easternTZ = TimeZone.getTimeZone("US/Eastern");

		// We must offset the time in the calculation date by the distribution
		// center's time zone difference, therefore no matter where the client
		// exists, all time limit comparisons
		// are in the context of the local time where the distribution
		// center is located.
		int timeZoneOffset = distributionCenter.getTimezoneOffset();

        // The DC timezone offset in the database assumes an offset from the Eastern Time Zone of the US
        // If the server is located in a timezone other than the Eastern Time Zone, 
        // this must also be taken into account.  This can be accomplished by comparing the
        // raw timezone offset of the server with the raw timezone offset of the Eastern Time Zone.
		// The raw offset is used because it does not take Daylight Savings into account. (This is handled later in this method.)
        // The raw offset of the Eastern Time Zone is subtracted from the raw offset of the server time zone
        // to determine the server offset.  For example, if a server is located in the Central Time Zone,
        // its raw offset would be -6, and the raw offset of the eastern TZ is -5.
        // The server offset in this case would be -1 (= -6 - (-5))
        int serverOffset = (tz.getRawOffset()/3600000) - (easternTZ.getRawOffset()/3600000);
		
		// Now we compensate for the server offset by adding it to the time zone offset.
        // Example:  DC in Central TZ has timeZoneOffset of 1.   
        //           Assume Central TZ server (instead of Eastern).  
        //           Calculated server offset will be -1.
        //           Adjusted time zone offset would be zero ( 1 + (-1) = 0)
        //           This is correct since server and DC are in same TZ.
		timeZoneOffset += serverOffset;

		// If the given date uses Daylight Savings, and the given DC does not,
		// then we need to add 1 hour to the offset. If the given date does NOT participate in
		// DST, but the given DC does, then we must subtract one hour from the offset
		if (calculationTime.getTimeZone().useDaylightTime()
				&& distributionCenter.isParticipatesInDaylightSavings() == false) {
			timeZoneOffset += 1;
		} else if (calculationTime.getTimeZone().useDaylightTime() == false
				&& distributionCenter.isParticipatesInDaylightSavings()) {
			timeZoneOffset -= 1;
		}

		// apply the offset to the calculation time
		calculationTime.add(Calendar.HOUR_OF_DAY, -(timeZoneOffset));
		return calculationTime;
	}
		
	private Calendar getCutoffTime(Calendar calculationTime, DistributionCenterBO distributionCenter) {

		// now get the cut off time and apply it to the max time. Will use the
		// given date for yy/mm/dd
		Date dcCutoffTime = distributionCenter.getEmergencyCutoffTime();
		Calendar dcCutoffTimeCalendar = GregorianCalendar.getInstance();
		dcCutoffTimeCalendar.setTime(dcCutoffTime);
		int cutoffHourOfDay = dcCutoffTimeCalendar.get(Calendar.HOUR_OF_DAY);
		int cutoffMinute = dcCutoffTimeCalendar.get(Calendar.MINUTE);
		Calendar cutoffTimeCal = new GregorianCalendar(
				calculationTime.get(GregorianCalendar.YEAR),
				calculationTime.get(GregorianCalendar.MONTH),
				calculationTime.get(GregorianCalendar.DAY_OF_MONTH),
				cutoffHourOfDay,
				cutoffMinute);

		return cutoffTimeCal;
	}	
	
	public TradingPartnerShippingMethodBO findTradingPartnerShipMtd(
			TradingPartnerBO tp, String scac)
			throws WOMNonUniqueResultException, WOMNoResultException {

		return null;
		/*return tpShipMthdDAO.findByActiveTradingPartnerAndScacCode(
				tp.getTpId(), scac);*/
	}

	/**
	 * find CarrierShippingMethod based on TP and commonCarrier and
	 * ipoShippingMtd
	 * 
	 * @param tp
	 * @param commonCarrier
	 * @param ipoShippingMtd
	 * @return
	 */
	public TradingPartnerShippingMethodBO findTradingPartnerShipMtd(
			TradingPartnerBO tp, String commonCarrier, String ipoShippingMtd)
			throws WOMNonUniqueResultException, WOMNoResultException {

		TradingPartnerShippingMethodBO tsm = new TradingPartnerShippingMethodBO();
		/*tsm = tpShipMthdDAO.findByActiveTpCarrierAndShipmtd(tp.getTpId(),
				commonCarrier, ipoShippingMtd);*/

		return tsm;
	}
	
	public ShippingCodeBO findNabsShipCode(long dcId, ShippingMethodBO sm,
			long fromToCty) throws WOMNonUniqueResultException,
			WOMNoResultException {

		ShippingCodeBO shippingCode = new ShippingCodeBO();
		return null;
		/*DcShippingMethodBO distShipMtd = dcShipMthdDAO.findByDcAndShippingMethod(dcId, sm.getShippingMethodId());
		NabsShippingCodeBO nabsShipCode = nabsShipCodeDAO.findByDcShipMthdAndShipMthdCntry(
						distShipMtd.getDcShippingMethodId(), fromToCty);

		shippingCode.setNabsShippingCode(nabsShipCode);

		return shippingCode;
*/	}
	
	public ShippingCodeBO findSapShipCode(long dcId, ShippingMethodBO sm,
			long fromToCty) throws WOMNonUniqueResultException,
			WOMNoResultException {

		return null;
		/*ShippingCodeBO shippingCode = new ShippingCodeBO();

		DcShippingMethodBO distShipMtd = dcShipMthdDAO.findByDcAndShippingMethod(dcId, sm.getShippingMethodId());
		SapShippingCodeBO sapShipCode = sapShipCodeDAO.findByDcShipMthdAndShipMthdCntry(
						distShipMtd.getDcShippingMethodId(), fromToCty);
		shippingCode.setSapShippingCode(sapShipCode);
		return shippingCode;*/
	}
}

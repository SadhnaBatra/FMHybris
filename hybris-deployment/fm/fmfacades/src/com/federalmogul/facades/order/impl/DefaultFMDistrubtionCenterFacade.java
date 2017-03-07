/**
 * 
 */
package com.federalmogul.facades.order.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.order.dao.FMCheckoutDAO;
import com.federalmogul.facades.order.FMDistrubtionCenterFacade;
import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDCShippingModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateErrorMessage;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateRequest;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateResponse;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.UPSSecurity;

import de.hybris.platform.util.Config;


/**
 * @author mamud
 * 
 */
public class DefaultFMDistrubtionCenterFacade implements FMDistrubtionCenterFacade
{
	private static final String ENDPOINT_URL="ENDPOINT_URL";

	/**
	 * @return the fmCheckoutDAO
	 */
	public FMCheckoutDAO getFmCheckoutDAO()
	{
		return fmCheckoutDAO;
	}

	/**
	 * @param fmCheckoutDAO
	 *           the fmCheckoutDAO to set
	 */
	public void setFmCheckoutDAO(final FMCheckoutDAO fmCheckoutDAO)
	{
		this.fmCheckoutDAO = fmCheckoutDAO;
	}

	private static final Logger LOG = Logger.getLogger(DefaultFMDistrubtionCenterFacade.class);

	@Autowired
	private FMCheckoutDAO fmCheckoutDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMCheckoutFacade#getDCData()
	 */
	@Override
	public List<FMDistrubtionCenterModel> getDCData()
	{
		// YTODO Auto-generated method stub

		return fmCheckoutDAO.getDCData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMCheckoutFacade#getDCData(java.lang.String)
	 */
	@Override
	public List<FMDistrubtionCenterModel> getDCData(final String code)
	{
		// YTODO Auto-generated method stub
		return fmCheckoutDAO.getDCData(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMCheckoutFacade#getDistrubtionCenterData(java.lang.String)
	 */
	@Override
	public FMDistrubtionCenterModel getDistrubtionCenterData(final String code)
	{
		// YTODO Auto-generated method stub
		return fmCheckoutDAO.getDistrubtionCenterData(code);
	}

	@Override
	public FMDCCenterModel getCutOffTimeForDC(final String code)
	{
		// YTODO Auto-generated method stub
		return fmCheckoutDAO.getCutOffTimeForDC(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMDistrubtionCenterFacade#isCutOffTimeApproaching(java.util.Date,
	 * com.federalmogul.falcon.core.model.FMDCCenterModel)
	 */
	@Override
	public boolean isCutOffTimeApproaching(final Date date, final FMDCCenterModel dc)
	{
		// YTODO Auto-generated method stub

		if (dc == null)
		{
			return false;
		}
		final Calendar calculationTime = getDCCalculationTime(date, dc);
		final Calendar compareTimeMax = getCutoffTime(calculationTime, dc);
		final Calendar compareTimeMin = getCutoffTime(calculationTime, dc);
		final String cutoffWarningMinutes = "15";
		compareTimeMin.add(GregorianCalendar.MINUTE, -(new Integer(cutoffWarningMinutes).intValue()));
		boolean isCutOffTimeApproaching  = false;
		// check to see if current time is in the the cutoff time warning range
		if ((calculationTime.getTime().getTime() < compareTimeMax.getTime().getTime())
				&& (calculationTime.getTime().getTime() >= compareTimeMin.getTime().getTime()))
		{
			isCutOffTimeApproaching = true;
		}
		else
		{
			isCutOffTimeApproaching = false;
		}
		return isCutOffTimeApproaching;


		//return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMDistrubtionCenterFacade#isCutOffTimeExpired(java.util.Date,
	 * com.federalmogul.falcon.core.model.FMDCCenterModel)
	 */
	@Override
	public boolean isCutOffTimeExpired(final Date date, final FMDCCenterModel dc)
	{
		// YTODO Auto-generated method stub

		if (dc == null)
		{
			return false;
		}

		final Calendar dcCalculationTime = getDCCalculationTime(date, dc);

		// now get the cut off time and apply it to the max time.
		final Calendar compareTimeMax = getCutoffTime(dcCalculationTime, dc);

		// check to see if current time is in the the cutoff time warning range
		return dcCalculationTime.getTime().getTime() >= compareTimeMax.getTime().getTime();


		//return false;
	}


	private Calendar getDCCalculationTime(final Date calculationDate, final FMDCCenterModel distributionCenter)
	{

		final Calendar calculationTime = GregorianCalendar.getInstance();
		if (calculationDate != null)
		{
			calculationTime.setTime(calculationDate);
		}
		final TimeZone tz = calculationTime.getTimeZone();
		final TimeZone easternTZ = TimeZone.getTimeZone("US/Eastern");
		int timeZoneOffset = distributionCenter.getLocTimeZoneOffSet();

		// The DC timezone offset in the database assumes an offset from the Eastern Time Zone of the US
		// If the server is located in a timezone other than the Eastern Time Zone, 
		// this must also be taken into account.  This can be accomplished by comparing the
		// raw timezone offset of the server with the raw timezone offset of the Eastern Time Zone.
		// The raw offset is used because it does not take Daylight Savings into account. (This is handled later in this method.)
		// The raw offset of the Eastern Time Zone is subtracted from the raw offset of the server time zone
		// to determine the server offset.  For example, if a server is located in the Central Time Zone,
		// its raw offset would be -6, and the raw offset of the eastern TZ is -5.
		// The server offset in this case would be -1 (= -6 - (-5))
		final int serverOffset = (tz.getRawOffset() / 3600000) - (easternTZ.getRawOffset() / 3600000);

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
		if (calculationTime.getTimeZone().useDaylightTime() && distributionCenter.getParticipateFlag() == false)
		{
			timeZoneOffset += 1;
		}
		else if (calculationTime.getTimeZone().useDaylightTime() == false && distributionCenter.getParticipateFlag())
		{
			timeZoneOffset -= 1;
		}

		// apply the offset to the calculation time
		calculationTime.add(Calendar.HOUR_OF_DAY, -(timeZoneOffset));
		return calculationTime;
	}

	private Calendar getCutoffTime(final Calendar calculationTime, final FMDCCenterModel distributionCenter)
	{

		// now get the cut off time and apply it to the max time. Will use the
		// given date for yy/mm/dd
		final Date dcCutoffTime = distributionCenter.getEmergencyCutOffTime();
		final Calendar dcCutoffTimeCalendar = GregorianCalendar.getInstance();
		dcCutoffTimeCalendar.setTime(dcCutoffTime);
		final int cutoffHourOfDay = dcCutoffTimeCalendar.get(Calendar.HOUR_OF_DAY);
		final int cutoffMinute = dcCutoffTimeCalendar.get(Calendar.MINUTE);

		LOG.info("cutoffHourOfDay :: " + cutoffHourOfDay);
		LOG.info("cutoffMinute :: " + cutoffMinute);
		final Calendar cutoffTimeCal = new GregorianCalendar(calculationTime.get(GregorianCalendar.YEAR),
				calculationTime.get(GregorianCalendar.MONTH), calculationTime.get(GregorianCalendar.DAY_OF_MONTH), cutoffHourOfDay,
				cutoffMinute);

		return cutoffTimeCal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMDistrubtionCenterFacade#getShippingMethod(java.lang.String)
	 */
	@Override
	public FMDCShippingModel getShippingMethod(final String code, final String carrier, final String shipmethod,
			final String shipToCountry)
	{
		// YTODO Auto-generated method stub
		return fmCheckoutDAO.getShippingMethod(code, carrier, shipmethod, shipToCountry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMDistrubtionCenterFacade#getCarrierName(java.lang.String)
	 */
	@Override
	public List<String> getCarrierName(final String code)
	{
		// YTODO Auto-generated method stub
		return fmCheckoutDAO.getCarrierName(code);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.order.FMDistrubtionCenterFacade#getShippingMethodName(java.lang.String)
	 */
	@Override
	public List<String> getShippingMethodName(final String code, final String dcCode, final String shipToCountry)
	{
		// YTODO Auto-generated method stub
		return fmCheckoutDAO.getShippingMethodName(code, dcCode, shipToCountry);
	}


	
	/**
	 * Below method getEstimatedShippingCost(RateRequest,UPSSecurity) will invoke UPS webservice RateWS with required parameters (RateRequest and UPSSecurity)
	 */
	@Override
	public RateResponse getEstimatedShippingCost(RateRequest rateRequest, UPSSecurity upsSecurity) {
		LOG.info("inside DefaultFMDistributionFacade class -> getEstimatedShippingCost() method: "+Config.getParameter(ENDPOINT_URL));
		RateResponse rateResponse=null;
	    String statusCode = null;
		String description = null;

		try {
			RateServiceStub rateServiceStub = new RateServiceStub(Config.getParameter("ENDPOINT_URL"));
			rateResponse = rateServiceStub.processRate(rateRequest, upsSecurity);
		}
		catch (Exception e) {
			LOG.error("getEstimatedShippingCost(...): Exception occured while invoking UPS Shipping Rate WebService: " + e.getMessage());
			if (e instanceof RateErrorMessage) {
				RateErrorMessage rateErrorMessage = (RateErrorMessage) e;

				statusCode = rateErrorMessage.getFaultMessage().getErrorDetail()[0].getPrimaryErrorCode().getCode();
				description = rateErrorMessage.getFaultMessage().getErrorDetail()[0].getPrimaryErrorCode().getDescription();
        		LOG.error("getEstimatedShippingCost(...): statusCode: " + statusCode + "\tdescription: " + description);
			} else {
        		statusCode = e.getMessage();
        		description = e.toString();
        		LOG.error("getEstimatedShippingCost(...): statusCode: " + statusCode + "\tdescription: " + description);
        	}
			
			e.printStackTrace();
		}

		return rateResponse;
	}

}
/**
 * 
 */
package com.federalmogul.facades.order;

import java.util.Date;
import java.util.List;

import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDCShippingModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateRequest;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateResponse;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.UPSSecurity;


/**
 * @author mamud
 * 
 */
public interface FMDistrubtionCenterFacade
{
	public List<FMDistrubtionCenterModel> getDCData();

	public List<FMDistrubtionCenterModel> getDCData(String code);

	public FMDistrubtionCenterModel getDistrubtionCenterData(String code);

	public FMDCCenterModel getCutOffTimeForDC(String code);

	boolean isCutOffTimeApproaching(Date date, FMDCCenterModel dc);

	boolean isCutOffTimeExpired(Date date, FMDCCenterModel dc);

	FMDCShippingModel getShippingMethod(String code, String carrier, String shipmethod, String shipToCountry);

	List<String> getCarrierName(String code);

	List<String> getShippingMethodName(String code, String dcCode, String shipToCountry);

	RateResponse getEstimatedShippingCost(RateRequest rateRequest, UPSSecurity upsSecurity);

}
/**
 * 
 */
package com.federalmogul.core.order.dao;

import java.util.List;

import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDCShippingModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.federalmogul.falcon.core.model.FMShipperOrderTrackingModel;


/**
 * @author mamud
 * 
 */
public interface FMCheckoutDAO
{

	List<FMDistrubtionCenterModel> getDCData();

	List<FMDistrubtionCenterModel> getDCData(String code);

	FMDistrubtionCenterModel getDistrubtionCenterData(final String code);

	FMDCCenterModel getCutOffTimeForDC(final String code);

	FMDCShippingModel getShippingMethod(String code, String carrier, String shipmethod, String shipToCountry);

	List<String> getCarrierName(String code);

	List<String> getShippingMethodName(String code, String dcCode, String shipToCountry);

	FMShipperOrderTrackingModel getShipperOrderDetails(String shipperCode);
}

/**
 * 
 */
package com.federalmogul.facades.order.converters.populator;

import de.hybris.platform.commercefacades.order.converters.populator.OrderEntryPopulator;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.federalmogul.facades.order.data.DistrubtionCenterData;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
public class FMOrderEntryPopulator extends OrderEntryPopulator
{
	private static Logger logger = Logger.getLogger(FMOrderEntryPopulator.class);
	
	public Logger getLogger() {
		return logger;
	}
	
	@Override
	public void populate(final AbstractOrderEntryModel source, final OrderEntryData target)
	{
		super.populate(source, target);
		populateDistrubtionCenter(source, target);

	}

	void populateDistrubtionCenter(final AbstractOrderEntryModel source, final OrderEntryData target)
	{
		final List<DistrubtionCenterData> distrubtionCenters = new ArrayList<DistrubtionCenterData>();
		final List<FMDistrubtionCenterModel> dcEntries = source.getDistrubtionCenter();
		String partNumber=null;
		if(source.getProduct() instanceof FMPartModel){
			final FMPartModel productModel = (FMPartModel) source.getProduct();
			partNumber = productModel.getCode();
			for (final FMCorporateModel corp : productModel.getCorporate())
			{
				// Check if this is NABS part.
				if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
					|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
				{
					partNumber = productModel.getRawPartNumber();
					break;
				}
			}
			target.getProduct().setRawPartNumber(partNumber);
			logger.info("populateDistrubtionCenter(...): target.getProduct().getRawPartNumber(): " + target.getProduct().getRawPartNumber());
		}
		if (dcEntries != null && dcEntries.size() > 0)
		{
			for (final FMDistrubtionCenterModel dc : dcEntries)
			{
				final DistrubtionCenterData distrubtionCenter = new DistrubtionCenterData();

				distrubtionCenter.setCode(dc.getCode());
				distrubtionCenter.setDistrubtionCenterDataCode(dc.getDistrubtionCenterDataCode());
				distrubtionCenter.setDistrubtionCenterDataName(dc.getDistrubtionCenterDataName());
				distrubtionCenter.setAvailableQTY(dc.getAvailableQTY());
				distrubtionCenter.setRawpartNumber(dc.getRawpartNumber());
				distrubtionCenter.setPartNumber(partNumber);
				distrubtionCenter.setAvailableDate(dc.getAvailableDate());
				distrubtionCenter.setShippingMethod(dc.getShippingMethod());
				distrubtionCenter.setCarrierName(dc.getCarrierName());
				distrubtionCenter.setCarrierDispName(dc.getCarrierDispName());
				distrubtionCenter.setShippingMethodName(dc.getShippingMethodName());
				distrubtionCenter.setCarrierAccountCode(dc.getCarrierAccountCode());
				distrubtionCenter.setBackorderQTY(dc.getBackorderQTY());
				distrubtionCenter.setBackorderQTYAll(dc.getBackorderQTYAll());
				distrubtionCenter.setBackorderFlag(dc.getBackorderFlag());
				distrubtionCenter.setTscFlag(dc.getTscFlag());
				distrubtionCenter.setFreightCost(dc.getFreightCost());
				distrubtionCenter.setFreightCostCurrencyCode(dc.getFreightCostCurrencyCode());

				distrubtionCenters.add(distrubtionCenter);
			}
			target.setDistrubtionCenter(distrubtionCenters);
		}
	}
}

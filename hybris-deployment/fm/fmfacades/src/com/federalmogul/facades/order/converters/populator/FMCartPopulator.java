/**
 * 
 */
package com.federalmogul.facades.order.converters.populator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;

import de.hybris.platform.commercefacades.order.converters.populator.CartPopulator;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;


/**
 * @author mamud

 * 
 */
/**
 * Converter implementation for {@link de.hybris.platform.core.model.order.CartModel} as source and
 * {@link de.hybris.platform.commercefacades.order.data.CartData} as target type.
 */
public class FMCartPopulator<T extends CartData> extends CartPopulator<T>
{

	private static final Logger LOG = Logger.getLogger(FMCartPopulator.class);

	@Override
	public void populate(final CartModel source, final T target)
	{
		super.populate(source, target);
		populateSAPCustDetails(source, target);
		populateTotalShippingCost(source, target);
	}

	void populateSAPCustDetails(final AbstractOrderModel source, final AbstractOrderData target)
	{
		target.setFmordertype(source.getFmordertype());
		target.setCustponumber(source.getCustponumber());
		target.setSapordernumber(source.getSapordernumber());
		target.setOrdercomments(source.getOrdercomments());
		target.setPocustid(source.getPocustid());
	}

	void populateTotalShippingCost(final CartModel source, final CartData target) 
	{
		BigDecimal freightCostForDC = BigDecimal.ZERO;
		BigDecimal totalFreightCost = BigDecimal.ZERO;
		List<AbstractOrderEntryModel> srcEntries = source.getEntries();
		Set<String> dcCodesSet = new HashSet<String>();

		LOG.info("populateTotalShippingCost(...): --------------------------------------------------------------------------");
		if (srcEntries != null && srcEntries.size() > 0) {
			for (AbstractOrderEntryModel srcEntry : srcEntries) {
				List<FMDistrubtionCenterModel> dcModels = srcEntry.getDistrubtionCenter();
				if (dcModels != null && dcModels.size() > 0) {
					for (FMDistrubtionCenterModel dcModel : dcModels) {
						LOG.info("populateTotalShippingCost(...): DC Code: " + dcModel.getDistrubtionCenterDataCode());
						// Check if the DC Code exists in the DCs Set already.
						if (!dcCodesSet.contains(dcModel.getDistrubtionCenterDataCode())) {
							LOG.info("populateTotalShippingCost(...): DC Code '" + dcModel.getDistrubtionCenterDataCode() + 
										"' does NOT exist in the DC Codes Set. Adding Freight Cost to the Total");
							freightCostForDC = dcModel.getFreightCost();
							if (freightCostForDC != null) {
								freightCostForDC = freightCostForDC.setScale(2, BigDecimal.ROUND_HALF_UP);
								LOG.info("populateTotalShippingCost(...): Freight Cost for DC: " + freightCostForDC);
								totalFreightCost = totalFreightCost.add(freightCostForDC);
								totalFreightCost = totalFreightCost.setScale(2, BigDecimal.ROUND_HALF_UP);
							}
							
							// Add the DC Code to the DCs Set.
							dcCodesSet.add(dcModel.getDistrubtionCenterDataCode());
						} else {
							LOG.info("populateTotalShippingCost(...): DC Code '" + dcModel.getDistrubtionCenterDataCode() + 
										"' already exists in the set. So, NOT adding Freight Cost to the Total");
						}
						LOG.info("populateTotalShippingCost(...): ----- *** -----");
					}
				}
			}
		}
		
		LOG.info("populateTotalShippingCost(...): Total Freight Cost: " + totalFreightCost);
		LOG.info("populateTotalShippingCost(...): --------------------------------------------------------------------------");
		
		PriceData deliveryCost = new PriceData();
		deliveryCost.setValue(totalFreightCost);
		//deliveryCost.setCurrencyIso(commonI18NService.getCurrentCurrency().getIsocode());
		target.setDeliveryCost(deliveryCost);
	}
}

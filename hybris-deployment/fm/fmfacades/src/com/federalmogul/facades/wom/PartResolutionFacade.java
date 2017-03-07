/**
 * 
 */
package com.federalmogul.facades.wom;

import com.federalmogul.falcon.core.model.FMPartModel;
import com.fmo.wom.domain.AccountBO;


/**
 * @author mamud
 * 
 */
public interface PartResolutionFacade
{

	String getPartResolutionMessage(final FMPartModel productModel, final AccountBO billTo, final AccountBO shipTo,
			final String poNumber);

}

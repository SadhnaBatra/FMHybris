/**
 * 
 */
package com.federalmogul.facades.account;

import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ReviewData;


/**
 * @author SR279690
 * 
 */
public interface FMReviewFacade
{
	public ReviewData postReview(String productCode, ReviewData reviewData, Boolean flag);
}

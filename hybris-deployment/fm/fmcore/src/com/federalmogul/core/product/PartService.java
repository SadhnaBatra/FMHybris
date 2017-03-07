/**
 * 
 */
package com.federalmogul.core.product;

import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
public interface PartService
{
	/**
	 * Returns the Part with the specified code. As default the search uses the current session user, the current session
	 * language and the current active catalog versions (which are stored at the session in the attribute
	 * {@link de.hybris.platform.catalog.constants.CatalogConstants#SESSION_CATALOG_VERSIONS SESSION_CATALOG_VERSIONS}).
	 * For modifying the search session context see {@link de.hybris.platform.servicelayer.search.FlexibleSearchQuery
	 * FlexibleSearchQuery}.
	 * 
	 * @param code
	 *           the code of the Part
	 * @return the Part with the specified code.
	 * @throws de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException
	 *            if no Part with the specified code is found
	 * @throws de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException
	 *            if more than one Part with the specified code is found
	 * @throws IllegalArgumentException
	 *            if parameter code is <code>null</code>
	 */
	FMPartModel getPartForCode(String code);


	/**
	 * Returns the Nabs Part with the specified partNumber and productflag. As default the search uses the current
	 * session user, the current session language and the current active catalog versions (which are stored at the
	 * session in the attribute {@link de.hybris.platform.catalog.constants.CatalogConstants#SESSION_CATALOG_VERSIONS
	 * SESSION_CATALOG_VERSIONS}). For modifying the search session context see
	 * {@link de.hybris.platform.servicelayer.search.FlexibleSearchQuery FlexibleSearchQuery}.
	 * 
	 * @param partNumber
	 *           the partNumber of the Part
	 * @param productFlag
	 *           the productFlag of the Part
	 * @return the Part with the specified code.
	 * @throws de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException
	 *            if no Part with the specified code is found
	 * @throws de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException
	 *            if more than one Part with the specified code is found
	 * @throws IllegalArgumentException
	 *            if parameter code is <code>null</code>
	 */
	FMPartModel getNABSPartForCode(String partNumber, String productFlag);
}

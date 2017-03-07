/**
 * 
 */
package com.federalmogul.core.product.dao;

import de.hybris.platform.servicelayer.internal.dao.Dao;

import java.util.List;

import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
public interface PartDAO extends Dao
{

	/**
	 * Returns for the given part <code>code</code> the {@link FMPartModel} collection.
	 * 
	 * @param code
	 *           the Part <code>code</code>
	 * @return a {@link FMPartModel}
	 * @throws IllegalArgumentException
	 *            if the given <code>code</code> is <code>null</code>
	 */
	List<FMPartModel> findPartsByCode(final String code);

	/**
	 * Returns for the given part <code>code</code> the {@link FMPartModel} collection.
	 * 
	 * @param partNumber
	 *           the Part <code>code</code>
	 * @return a {@link FMPartModel}
	 * @throws IllegalArgumentException
	 *            if the given <code>code</code> is <code>null</code>
	 */
	List<FMPartModel> findNABSPartsByCode(final String partNumber, String productFlag);
}

/**
 * 
 */
package com.federalmogul.core.product.dao.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.federalmogul.core.product.dao.PartDAO;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
/**
 * Default implementation of the {@link PartDAO}.
 */
public class DefaultPartDAO extends DefaultGenericDao<FMPartModel> implements PartDAO
{

	public DefaultPartDAO(final String typecode)
	{
		super(typecode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.product.dao.PartDAO#findPartsByCode(java.lang.String)
	 */
	@Override
	public List<FMPartModel> findPartsByCode(final String code)
	{
		validateParameterNotNull(code, "Part code must not be null!");

		return find(Collections.singletonMap(FMPartModel.CODE, (Object) code));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.product.dao.PartDAO#findNABSPartsByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public List<FMPartModel> findNABSPartsByCode(final String partNumber, final String productFlag)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(partNumber, "Part code must not be null!");
		validateParameterNotNull(productFlag, "Part code must not be null!");
		final Map parameters = new HashMap();
		parameters.put(FMPartModel.RAWPARTNUMBER, partNumber);
		parameters.put(FMPartModel.FLAGCODE, productFlag);

		return find(parameters);
	}
}

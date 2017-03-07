/**
 * 
 */
package com.federalmogul.core.product.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateIfSingleResult;
import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;
import static java.lang.String.format;

import de.hybris.platform.servicelayer.internal.service.AbstractBusinessService;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.core.product.PartService;
import com.federalmogul.core.product.dao.PartDAO;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author mamud
 * 
 */
public class DefaultPartService extends AbstractBusinessService implements PartService
{

	private PartDAO partDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.product.PartService#getPartForCode(java.lang.String)
	 */
	@Override
	public FMPartModel getPartForCode(final String code)
	{
		validateParameterNotNull(code, "Parameter code must not be null");
		final List<FMPartModel> part = partDAO.findPartsByCode(code);

		validateIfSingleResult(part, format("part with code '%s' not found!", code),
				format("Product code '%s' is not unique, %d part found!", code, Integer.valueOf(part.size())));

		return part.get(0);
	}

	@Required
	public void setPartDAO(final PartDAO partDAO)
	{
		this.partDAO = partDAO;
	}

	/**
	 * @return the productDao
	 */
	protected PartDAO getPartDAO()
	{
		return partDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.core.product.PartService#getNABSPartForCode(java.lang.String, java.lang.String)
	 */
	@Override
	public FMPartModel getNABSPartForCode(final String partNumber, final String productFlag)
	{
		// YTODO Auto-generated method stub
		validateParameterNotNull(partNumber, "Parameter code must not be null");
		validateParameterNotNull(productFlag, "Parameter code must not be null");
		final List<FMPartModel> part = partDAO.findNABSPartsByCode(partNumber, productFlag);

		validateIfSingleResult(part, format("part with code '%s' not found!", partNumber),
				format("Product code '%s' is not unique, %d part found!", partNumber, Integer.valueOf(part.size())));

		return part.get(0);
	}
}

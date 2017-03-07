/**
 * 
 */
package com.federalmogul.facades.search.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.federalmogul.core.search.dao.FMCompetitorDAO;
import com.federalmogul.facades.search.FMCompetitorFacade;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.falcon.core.model.PartInterchangeModel;

/**
 * @author SU276498
 * 
 */
public class DefaultFMCompetitorFacade implements FMCompetitorFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultFMCompetitorFacade.class);
	private FMCompetitorDAO fmCompetitorDAO;

	/**
	 * @return the fmCompetitorDAO
	 */
	public FMCompetitorDAO getFmCompetitorDAO()
	{
		return fmCompetitorDAO;
	}

	/**
	 * @param fmCompetitorDAO
	 *           the fmCompetitorDAO to set
	 */
	public void setFmCompetitorDAO(final FMCompetitorDAO fmCompetitorDAO)
	{
		this.fmCompetitorDAO = fmCompetitorDAO;
	}


	@Override
	public List<PartInterchangeModel> getExternalPartInfo(final String externalPart)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getFMParts :: externalPart" + externalPart);
		return getFmCompetitorDAO().getExternalPartInfo(externalPart);
	}

	@Override
	public List<FMPartModel> getPartNumberInfo(final String partNumber)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getFMParts :: externalPart" + partNumber);
		return getFmCompetitorDAO().getPartNumberInfo(partNumber);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.search.FMCompetitorFacade#getAppilcationUsageReports(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getAppilcationUsageReports(final String accountNumber, final String userId, final String startDate,
			final String endDate)
	{
		return getFmCompetitorDAO().getAppilcationUsageReports(accountNumber, userId, startDate, endDate);
	}




}

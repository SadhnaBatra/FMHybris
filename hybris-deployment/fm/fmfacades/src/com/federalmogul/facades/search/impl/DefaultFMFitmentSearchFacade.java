/**
 * 
 */
package com.federalmogul.facades.search.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.federalmogul.core.search.dao.FMFitmentSearchDAO;
import com.federalmogul.core.search.dao.FMYearMakeModelVehicleTypeDAO;
import com.federalmogul.facades.search.FMFitmentSearchFacade;
import com.federalmogul.falcon.core.model.FMFitmentModel;
import com.federalmogul.falcon.core.model.FMPartAlsoFitsModel;


/**
 * @author mamud
 * 
 */
public class DefaultFMFitmentSearchFacade implements FMFitmentSearchFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultFMFitmentSearchFacade.class);
	private FMFitmentSearchDAO fmFitmentSearchDAO;

	private FMYearMakeModelVehicleTypeDAO fmYearMakeModelVehicleTypeDAO;


	/**
	 * @return the fmYearMakeModelVehicleTypeDAO
	 */
	public FMYearMakeModelVehicleTypeDAO getFmYearMakeModelVehicleTypeDAO()
	{
		return fmYearMakeModelVehicleTypeDAO;
	}


	/**
	 * @param fmYearMakeModelVehicleTypeDAO
	 *           the fmYearMakeModelVehicleTypeDAO to set
	 */
	public void setFmYearMakeModelVehicleTypeDAO(final FMYearMakeModelVehicleTypeDAO fmYearMakeModelVehicleTypeDAO)
	{
		this.fmYearMakeModelVehicleTypeDAO = fmYearMakeModelVehicleTypeDAO;
	}


	/**
	 * @return the fmFitmentSearchDAO
	 */
	public FMFitmentSearchDAO getFmFitmentSearchDAO()
	{
		return fmFitmentSearchDAO;
	}


	/**
	 * @param fmFitmentSearchDAO
	 *           the fmFitmentSearchDAO to set
	 */
	public void setFmFitmentSearchDAO(final FMFitmentSearchDAO fmFitmentSearchDAO)
	{
		this.fmFitmentSearchDAO = fmFitmentSearchDAO;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param vehicleSegment
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getYMMFitmentYearData()
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentYearData(final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getYMMFitmentYearData :: vehicleSegment" + vehicleSegment);
		return getFmFitmentSearchDAO().getYMMFitmentYearData(vehicleSegment);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param vehicleSegment
	 * @param year
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getYMMFitmentMakeData(java.lang.String)
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentMakeData(final String year, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getYMMFitmentMakeData ");
		return getFmFitmentSearchDAO().getYMMFitmentMakeData(year, vehicleSegment);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param vehicleSegment
	 * @param year
	 * @param make
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getYMMFitmentModelData(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentModelData(final String year, final String make, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getYMMFitmentModelData ");
		return getFmFitmentSearchDAO().getYMMFitmentModelData(year, make, vehicleSegment);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getYMMFitmentData()
	 */
	@Override
	public List<FMFitmentModel> getYMMFitmentData()
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getYMMFitmentData ");
		return getFmFitmentSearchDAO().getYMMFitmentData();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param vehicleSegment
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getFitmentYearData()
	 */
	@Override
	public List<String> getFitmentData()
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getYMMFitmentData ");
		return getFmFitmentSearchDAO().getFitmentData();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param vehicleSegment
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getFitmentYearData()
	 */
	@Override
	public List<String> getFitmentYearData(final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getFitmentYearData :: vehicleSegment" + vehicleSegment);
		return getFmFitmentSearchDAO().getFitmentYearData(vehicleSegment);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param vehicleSegment
	 * @param year
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getFitmentMakeData(java.lang.String)
	 */
	@Override
	public List<String> getFitmentMakeData(final String year, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getFitmentMakeData ");
		return getFmFitmentSearchDAO().getFitmentMakeData(year, vehicleSegment);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @param vehicleSegment
	 * @param year
	 * @param make
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getFitmentModelData(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getFitmentModelData(final String year, final String make, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getFitmentModelData ");
		return getFmFitmentSearchDAO().getFitmentModelData(year, make, vehicleSegment);
	}

	@Override
	public List<String> getVehicleTypeData()
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getYMMFitmentMakeData ");
		return getFmYearMakeModelVehicleTypeDAO().getFitmentData();
	}

	@Override
	public List<String> getYearData(final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getYearData ");
		return getFmYearMakeModelVehicleTypeDAO().getYearData(vehicleSegment);
	}

	@Override
	public List<String> getMakeData(final String year, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getMakeData ");
		return getFmYearMakeModelVehicleTypeDAO().getMakeData(year, vehicleSegment);
	}

	@Override
	public List<String> getModelData(final String year, final String make, final String vehicleSegment)
	{
		// YTODO Auto-generated method stub
		LOG.info("Inside getModelData ");
		return getFmYearMakeModelVehicleTypeDAO().getModelData(year, make, vehicleSegment);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.search.FMFitmentSearchFacade#getAlsoFits(java.lang.String)
	 */
	public List<FMPartAlsoFitsModel> getAlsoFits(final String productCode)
	{
		LOG.info("Inside getAlsoFits ");
		// YTODO Auto-generated method stub
		return getFmYearMakeModelVehicleTypeDAO().getAlsoFits(productCode);
	}

}
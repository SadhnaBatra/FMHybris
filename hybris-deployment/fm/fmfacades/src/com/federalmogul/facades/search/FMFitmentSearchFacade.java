/**
 * 
 */
package com.federalmogul.facades.search;

import java.util.List;

import com.federalmogul.falcon.core.model.FMFitmentModel;
import com.federalmogul.falcon.core.model.FMPartAlsoFitsModel;


/**
 * @author mamud
 * 
 */
public interface FMFitmentSearchFacade
{
	public List<FMFitmentModel> getYMMFitmentData();

	public List<FMFitmentModel> getYMMFitmentYearData(String vehicleSegment);

	public List<FMFitmentModel> getYMMFitmentMakeData(String year, String vehicleSegment);

	public List<FMFitmentModel> getYMMFitmentModelData(String year, String make, String vehicleSegment);

	public List<String> getFitmentData();

	public List<String> getFitmentYearData(String vehicleSegment);

	public List<String> getFitmentMakeData(String year, String vehicleSegment);

	public List<String> getFitmentModelData(String year, String make, String vehicleSegment);

	public List<String> getVehicleTypeData();

	public List<String> getYearData(String vehicleSegment);

	public List<String> getMakeData(String year, String vehicleSegment);

	public List<String> getModelData(String year, String make, String vehicleSegment);

	public List<FMPartAlsoFitsModel> getAlsoFits(String productCode);

}
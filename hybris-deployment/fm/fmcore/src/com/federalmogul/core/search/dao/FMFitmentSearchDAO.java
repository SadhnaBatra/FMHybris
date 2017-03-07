/**
 * 
 */
package com.federalmogul.core.search.dao;

import java.util.List;

import com.federalmogul.falcon.core.model.FMFitmentModel;


/**
 * @author mamud
 * 
 */
public interface FMFitmentSearchDAO
{


	/**
	 * @param vehicleSegment
	 * @return
	 */
	List<FMFitmentModel> getYMMFitmentYearData(String vehicleSegment);

	/**
	 * @param vehicleSegment
	 * @param year
	 * @return
	 */
	List<FMFitmentModel> getYMMFitmentMakeData(String year, String vehicleSegment);

	/**
	 * @param vehicleSegment
	 * @param year
	 * @param make
	 * @return
	 */
	List<FMFitmentModel> getYMMFitmentModelData(String year, String make, String vehicleSegment);

	/**
	 * @return
	 */
	List<FMFitmentModel> getYMMFitmentData();

	/**
	 * @param year
	 * @param make
	 * @param vehicleSegment
	 * @return
	 */
	List<String> getFitmentModelData(String year, String make, String vehicleSegment);

	/**
	 * @param vehicleSegment
	 * @return
	 */
	List<String> getFitmentYearData(String vehicleSegment);

	/**
	 * @param year
	 * @param vehicleSegment
	 * @return
	 */
	List<String> getFitmentMakeData(String year, String vehicleSegment);

	/**
	 * @return
	 */
	List<String> getFitmentData();
}

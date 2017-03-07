/**
 * 
 */
package com.federalmogul.storefront.tags;

import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.federalmogul.facades.product.data.FMFitmentData;


/**
 * @author mamud
 * 
 *         JSP EL Functions. This file contains static methods that are used by JSP EL.
 */
public class FMFunctions
{


	/**
	 * JSP EL Function to get an Fitment for a parts in a and check with refinement
	 * 
	 * @param fitment
	 *           the FMFitmentData
	 * @param pageData
	 *           the refinement value FacetSearchPageData
	 * @return the FMFitmentData
	 * 
	 *         Author Mahaveer a
	 */
	public static FMFitmentData getFitmentPart(final FMFitmentData fitment, final FacetSearchPageData pageData,
			final List<String> appFacetValues)
	{
		final List<BreadcrumbData> facets = pageData.getBreadcrumbs();

		/*
		 * final List<String> appFacetValues = new ArrayList<String>(); for (final BreadcrumbData facet : facets) {
		 * appFacetValues.add(facet.getFacetValueName()); }
		 */
		for (final BreadcrumbData facet : facets)
		{
			if ("Fit-Position".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getPosition() != null && !fitment.getPosition().isEmpty()
						&& !appFacetValues.contains(fitment.getPosition()))
				{
					return null;
				}
			}
			if ("Fit-Engine".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineBase() != null && !fitment.getEngineBase().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineBase()))
				{
					return null;
				}
			}
			if ("Fit-EngineDesignation".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineDesignation() != null && !fitment.getEngineDesignation().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineDesignation()))
				{
					return null;
				}
			}
			if ("Fit-EngineVIN".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineVIN() != null && !fitment.getEngineVIN().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineVIN()))
				{
					return null;
				}
			}
			if ("Fit-Aspiration".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getAspiration() != null && !fitment.getAspiration().isEmpty()
						&& !appFacetValues.contains(fitment.getAspiration()))
				{
					return null;
				}
			}

			if ("Fit-CylinderHeadType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getCylinderHeadType() != null && !fitment.getCylinderHeadType().isEmpty()
						&& !appFacetValues.contains(fitment.getCylinderHeadType()))
				{
					return null;
				}
			}
			if ("Fit-FuelType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getFuelType() != null && !fitment.getFuelType().isEmpty()
						&& !appFacetValues.contains(fitment.getFuelType()))
				{
					return null;
				}
			}
			if ("Fit-IgnitionSystemType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getIgnitionSystemType() != null && !fitment.getIgnitionSystemType().isEmpty()
						&& !appFacetValues.contains(fitment.getIgnitionSystemType()))
				{
					return null;
				}
			}
			if ("Fit-EngineVersion".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineVersion() != null && !fitment.getEngineVersion().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineVersion()))
				{
					return null;
				}
			}
			if ("Fit-FuelDeliveryType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getFuelDeliveryType() != null && !fitment.getFuelDeliveryType().isEmpty()
						&& !appFacetValues.contains(fitment.getFuelDeliveryType()))
				{
					return null;
				}
			}
			if ("Fit-FuelDeliverySubType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getFuelDeliverySubType() != null && !fitment.getFuelDeliverySubType().isEmpty()
						&& !appFacetValues.contains(fitment.getFuelDeliverySubType()))
				{
					return null;
				}
			}
			if ("Fit-FuelSystemControlType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getFuelSystemControlType() != null && !fitment.getFuelSystemControlType().isEmpty()
						&& !appFacetValues.contains(fitment.getFuelSystemControlType()))
				{
					return null;
				}
			}
			if ("Fit-FuelSystemDesign".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getFuelSystemDesign() != null && !fitment.getFuelSystemDesign().isEmpty()
						&& !appFacetValues.contains(fitment.getFuelSystemDesign()))
				{
					return null;
				}
			}
			if ("Fit-EngineMfr".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineMfr() != null && !fitment.getEngineMfr().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineMfr()))
				{
					return null;
				}
			}
			if ("Fit-EngineValves".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineValves() != null && !fitment.getEngineValves().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineValves()))
				{
					return null;
				}
			}
			if ("Fit-PowerOutput".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getPowerOutput() != null && !fitment.getPowerOutput().isEmpty()
						&& !appFacetValues.contains(fitment.getPowerOutput()))
				{
					return null;
				}
			}
			if ("Fit-EngineArrangementNumber".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineArrangementNumber() != null && !fitment.getEngineArrangementNumber().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineArrangementNumber()))
				{
					return null;
				}
			}
			if ("Fit-EngineSerialNumber".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineSerialNumber() != null && !fitment.getEngineSerialNumber().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineSerialNumber()))
				{
					return null;
				}
			}
			if ("Fit-EngineCPLNumber".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getEngineCPLNumber() != null && !fitment.getEngineCPLNumber().isEmpty()
						&& !appFacetValues.contains(fitment.getEngineCPLNumber()))
				{
					return null;
				}
			}
			if ("Fit-VehicleType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getVehicleType() != null && !fitment.getVehicleType().isEmpty()
						&& !appFacetValues.contains(fitment.getVehicleType()))
				{
					return null;
				}
			}
			//
			if ("Fit-Region".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getRegion() != null && !fitment.getRegion().isEmpty() && !appFacetValues.contains(fitment.getRegion()))
				{
					return null;
				}
			}
			if ("Fit-BodyNumDoors".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getBodyNumDoors() != null && !fitment.getBodyNumDoors().isEmpty()
						&& !appFacetValues.contains(fitment.getBodyNumDoors()))
				{
					return null;
				}
			}
			if ("Fit-BodyType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getBodyType() != null && !fitment.getBodyType().isEmpty()
						&& !appFacetValues.contains(fitment.getBodyType()))
				{
					return null;
				}
			}
			if ("Fit-BedLength".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getBedLength() != null && !fitment.getBedLength().isEmpty()
						&& !appFacetValues.contains(fitment.getBedLength()))
				{
					return null;
				}
			}
			if ("Fit-BedType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getBedType() != null && !fitment.getBedType().isEmpty() && !appFacetValues.contains(fitment.getBedType()))
				{
					return null;
				}
			}
			if ("Fit-DriveType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getDriveType() != null && !fitment.getDriveType().isEmpty()
						&& !appFacetValues.contains(fitment.getDriveType()))
				{
					return null;
				}
			}
			if ("Fit-MfrBodyCode".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getMfrBodyCode() != null && !fitment.getMfrBodyCode().isEmpty()
						&& !appFacetValues.contains(fitment.getMfrBodyCode()))
				{
					return null;
				}
			}
			if ("Fit-WheelBase".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getWheelBase() != null && !fitment.getWheelBase().isEmpty()
						&& !appFacetValues.contains(fitment.getWheelBase()))
				{
					return null;
				}
			}
			if ("Fit-FrontBrakeType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getFrontBrakeType() != null && !fitment.getFrontBrakeType().isEmpty()
						&& !appFacetValues.contains(fitment.getFrontBrakeType()))
				{
					return null;
				}
			}
			if ("Fit-RearBrakeType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getRearBrakeType() != null && !fitment.getRearBrakeType().isEmpty()
						&& !appFacetValues.contains(fitment.getRearBrakeType()))
				{
					return null;
				}
			}
			if ("Fit-BrakeSystem".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getBrakeSystem() != null && !fitment.getBrakeSystem().isEmpty()
						&& !appFacetValues.contains(fitment.getBrakeSystem()))
				{
					return null;
				}
			}
			if ("Fit-BrakeABS".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getBrakeABS() != null && !fitment.getBrakeABS().isEmpty()
						&& !appFacetValues.contains(fitment.getBrakeABS()))
				{
					return null;
				}
			}
			if ("Fit-FrontSpringType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getFrontSpringType() != null && !fitment.getFrontSpringType().isEmpty()
						&& !appFacetValues.contains(fitment.getFrontSpringType()))
				{
					return null;
				}
			}
			if ("Fit-RearSpringType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getRearSpringType() != null && !fitment.getRearSpringType().isEmpty()
						&& !appFacetValues.contains(fitment.getRearSpringType()))
				{
					return null;
				}
			}
			if ("Fit-SteeringType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getSteeringType() != null && !fitment.getSteeringType().isEmpty()
						&& !appFacetValues.contains(fitment.getSteeringType()))
				{
					return null;
				}
			}
			if ("Fit-SteeringSystem".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getSteeringSystem() != null && !fitment.getSteeringSystem().isEmpty()
						&& !appFacetValues.contains(fitment.getSteeringSystem()))
				{
					return null;
				}
			}
			if ("Fit-VehicleSeries".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getVehicleSeries() != null && !fitment.getVehicleSeries().isEmpty()
						&& !appFacetValues.contains(fitment.getVehicleSeries()))
				{
					return null;
				}
			}
			if ("Fit-TransmissionNumSpeeds".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getTransmissionNumSpeeds() != null && !fitment.getTransmissionNumSpeeds().isEmpty()
						&& !appFacetValues.contains(fitment.getTransmissionNumSpeeds()))
				{
					return null;
				}
			}
			if ("Fit-TransmissionControlType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getTransmissionControlType() != null && !fitment.getTransmissionControlType().isEmpty()
						&& !appFacetValues.contains(fitment.getTransmissionControlType()))
				{
					return null;
				}
			}
			if ("Fit-TransmissionMfrCode".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getTransmissionMfrCode() != null && !fitment.getTransmissionMfrCode().isEmpty()
						&& !appFacetValues.contains(fitment.getTransmissionMfrCode()))
				{
					return null;
				}
			}
			if ("Fit-TransmissionType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getTransmissionType() != null && !fitment.getTransmissionType().isEmpty()
						&& !appFacetValues.contains(fitment.getTransmissionType()))
				{
					return null;
				}
			}
			/*
			 * if (facet.getFacetCode().equalsIgnoreCase("MfrLabel")) { if
			 * (!appFacetValues.contains(fitment.getMfrLabel())) { return null; } }
			 */

			if ("Fit-Submodel".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getSubmodel() != null && !fitment.getSubmodel().isEmpty()
						&& !appFacetValues.contains(fitment.getSubmodel()))
				{
					return null;
				}
			}

			if ("Fit-Region".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getRegion() != null && !fitment.getRegion().isEmpty() && !appFacetValues.contains(fitment.getRegion()))
				{
					return null;
				}
			}

			if ("Fit-partType".equalsIgnoreCase(facet.getFacetCode()))
			{
				if (fitment.getProducts() != null && !fitment.getProducts().isEmpty()
						&& !appFacetValues.contains(fitment.getProducts()))
				{
					return null;
				}
			}
		}
		return fitment;
	}

	/**
	 * JSP EL Function to get an Fitment for a parts in a and check with refinement
	 * 
	 * @param fitmentlist
	 *           the FMFitmentData
	 * @param pageData
	 *           the refinement value FacetSearchPageData
	 * @param years
	 *           the refinement value year
	 * 
	 * @param make
	 *           the refinement value make
	 * 
	 * @param model
	 *           the refinement value model
	 * 
	 * @return the HashMap the map contains refined fitmentlist and fitment count
	 * 
	 *         Author Mahaveer a
	 */

	public static HashMap getAppFitmentmap(final List<FMFitmentData> fitmentlist, final FacetSearchPageData pageData,
			final String years, final String make, final String model)
	{
		final HashMap fitmentmap = new HashMap();
		final List<String> appFacetValues = new ArrayList<String>();
		final List<FMFitmentData> fitmentdatalist = new ArrayList<FMFitmentData>();
		if (pageData != null)
		{
			final List<BreadcrumbData> facets = pageData.getBreadcrumbs();
			for (final BreadcrumbData facet : facets)
			{
				String appliedFacetValue = facet.getFacetValueName();
				if (!"all".equalsIgnoreCase(facet.getFacetValueName())
						&& facet.getFacetValueName().contains((years + make + model + "|")))
				{
					appliedFacetValue = appliedFacetValue.substring((years + make + model + "|").length());
				}

				if (appliedFacetValue != null)
				{
					appFacetValues.add(appliedFacetValue);
				}
			}
		}
		for (final FMFitmentData fitment : fitmentlist)
		{
			if (years.equalsIgnoreCase(fitment.getYear()) && make.equalsIgnoreCase(fitment.getMake())
					&& model.equalsIgnoreCase(fitment.getModel()))
			{
				if (pageData != null)
				{
					final FMFitmentData fitmentpart = getFitmentPart(fitment, pageData, appFacetValues);
					if (null != fitmentpart)
					{
						fitmentdatalist.add(fitmentpart);
					}
				}
				else
				{
					fitmentdatalist.add(fitment);
				}
			}
		}
		if (fitmentdatalist.size() > 0)
		{
			fitmentmap.put("fitmentcount", fitmentdatalist.size());
			fitmentmap.put("fitmentlist", fitmentdatalist);
		}
		return fitmentmap;

	}

	/**
	 * JSP EL Function to get an Fitment for a parts in a and check with product details page
	 * 
	 * @param fitmentlist
	 *           the FMFitmentData
	 * @param years
	 *           the refinement value year
	 * 
	 * @param make
	 *           the refinement value make
	 * 
	 * @param model
	 *           the refinement value model
	 * 
	 * @return the HashMap the map contains refined fitmentlist and fitment count
	 * 
	 *         Author Mahaveer a
	 */

	public static HashMap getAppFitmentmap(final List<FMFitmentData> fitmentlist, final String years, final String make,
			final String model)
	{
		final FacetSearchPageData pageData = null;
		return getAppFitmentmap(fitmentlist, pageData, years, make, model);
	}

	public static String getFitmentCriteria(final FMFitmentData fitmentData, final String fitmentCriteria)
	{

		String finalfitmentCriteria = new String();

		if ("comment".equalsIgnoreCase(fitmentCriteria))
		{

			if (fitmentData.getComment1() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment1() + ";");
			}
			if (fitmentData.getComment2() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment2() + ";");
			}
			if (fitmentData.getComment3() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment3() + ";");
			}
			if (fitmentData.getComment4() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment4() + ";");
			}
			if (fitmentData.getComment5() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment5() + ";");
			}
			if (fitmentData.getComment6() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment6() + ";");
			}
			if (fitmentData.getComment7() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment7() + ";");
			}
			if (fitmentData.getComment8() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment8() + ";");
			}
			if (fitmentData.getComment9() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment9() + ";");
			}
			if (fitmentData.getComment10() != null)
			{
				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment10() + ";");
			}

			finalfitmentCriteria = replaceAStringAt(finalfitmentCriteria, finalfitmentCriteria.lastIndexOf(";"), " ");

		}

		if ("applicationNote".equalsIgnoreCase(fitmentCriteria))
		{

			if (fitmentData.getApplicationNote1() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote1() + ";");
			}

			if (fitmentData.getApplicationNote2() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote2() + ";");
			}

			if (fitmentData.getApplicationNote3() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote3() + ";");
			}
			if (fitmentData.getApplicationNote4() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote4() + ";");
			}
			if (fitmentData.getApplicationNote5() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote5() + ";");
			}
			if (fitmentData.getApplicationNote6() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote6() + ";");
			}
			if (fitmentData.getApplicationNote7() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote7() + ";");
			}
			if (fitmentData.getApplicationNote8() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote8() + ";");
			}
			if (fitmentData.getApplicationNote9() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote9() + ";");
			}
			if (fitmentData.getApplicationNote10() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote10() + ";");
			}

			finalfitmentCriteria = replaceAStringAt(finalfitmentCriteria, finalfitmentCriteria.lastIndexOf(";"), " ");

		}

		if ("interchangeNote".equalsIgnoreCase(fitmentCriteria))
		{

			if (fitmentData.getInterchangeNote1() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote1() + ";");
			}
			if (fitmentData.getInterchangeNote2() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote2() + ";");
			}
			if (fitmentData.getInterchangeNote3() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote3() + ";");
			}
			if (fitmentData.getInterchangeNote4() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote4() + ";");
			}
			if (fitmentData.getInterchangeNote5() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote5() + ";");
			}
			if (fitmentData.getInterchangeNote6() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote6() + ";");
			}
			if (fitmentData.getInterchangeNote7() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote7() + ";");
			}
			if (fitmentData.getInterchangeNote8() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote8() + ";");
			}
			if (fitmentData.getInterchangeNote9() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote9() + ";");
			}
			if (fitmentData.getInterchangeNote10() != null)
			{

				finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote10() + ";");
			}

			finalfitmentCriteria = replaceAStringAt(finalfitmentCriteria, finalfitmentCriteria.lastIndexOf(";"), " ");

		}



		return finalfitmentCriteria;
	}


	/**
	 * 
	 * @param fitmentData
	 * @return
	 */
	public static String getAllFitmentCriteria(final FMFitmentData fitmentData)
	{


		String finalfitmentCriteria = new String();


		if (fitmentData.getComment1() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment1() + "; ");
		}
		if (fitmentData.getComment2() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment2() + "; ");
		}
		if (fitmentData.getComment3() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment3() + "; ");
		}
		if (fitmentData.getComment4() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment4() + "; ");
		}
		if (fitmentData.getComment5() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment5() + "; ");
		}
		if (fitmentData.getComment6() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment6() + "; ");
		}
		if (fitmentData.getComment7() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment7() + "; ");
		}
		if (fitmentData.getComment8() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment8() + "; ");
		}
		if (fitmentData.getComment9() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment9() + "; ");
		}
		if (fitmentData.getComment10() != null)
		{
			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getComment10() + "; ");
		}




		if (fitmentData.getApplicationNote1() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote1() + "; ");
		}

		if (fitmentData.getApplicationNote2() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote2() + "; ");
		}

		if (fitmentData.getApplicationNote3() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote3() + "; ");
		}
		if (fitmentData.getApplicationNote4() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote4() + "; ");
		}
		if (fitmentData.getApplicationNote5() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote5() + "; ");
		}
		if (fitmentData.getApplicationNote6() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote6() + "; ");
		}
		if (fitmentData.getApplicationNote7() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote7() + "; ");
		}
		if (fitmentData.getApplicationNote8() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote8() + "; ");
		}
		if (fitmentData.getApplicationNote9() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote9() + "; ");
		}
		if (fitmentData.getApplicationNote10() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getApplicationNote10() + "; ");
		}


		if (fitmentData.getInterchangeNote1() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote1() + "; ");
		}
		if (fitmentData.getInterchangeNote2() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote2() + "; ");
		}
		if (fitmentData.getInterchangeNote3() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote3() + "; ");
		}
		if (fitmentData.getInterchangeNote4() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote4() + "; ");
		}
		if (fitmentData.getInterchangeNote5() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote5() + "; ");
		}
		if (fitmentData.getInterchangeNote6() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote6() + "; ");
		}
		if (fitmentData.getInterchangeNote7() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote7() + "; ");
		}
		if (fitmentData.getInterchangeNote8() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote8() + "; ");
		}
		if (fitmentData.getInterchangeNote9() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote9() + "; ");
		}
		if (fitmentData.getInterchangeNote10() != null)
		{

			finalfitmentCriteria = finalfitmentCriteria.concat(fitmentData.getInterchangeNote10() + "; ");
		}

		if (finalfitmentCriteria != null && finalfitmentCriteria.lastIndexOf(";") > -1)
		{
			finalfitmentCriteria = replaceAStringAt(finalfitmentCriteria, finalfitmentCriteria.lastIndexOf(";"), " ");
		}

		return finalfitmentCriteria;

	}


	/**
	 * 
	 * @param baseString
	 * @param pos
	 * @param replacementString
	 * @return String
	 */
	private static String replaceAStringAt(final String baseString, final int pos, final String replacementString)
	{
		return baseString.substring(0, pos) + replacementString + baseString.substring(pos + 1);
	}
}

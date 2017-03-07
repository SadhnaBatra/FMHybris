/**
 * 
 */
package com.federalmogul.core.solr.provider;

import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.falcon.core.model.FMFitmentModel;
import com.federalmogul.falcon.core.model.FMPartModel;


/**
 * @author SU276498
 * 
 */
public class YMMTextValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	protected FieldNameProvider getFieldNameProvider()
	{
		return this.fieldNameProvider;
	}

	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		Collection<FMFitmentModel> fitments = null;
		final StringBuilder output = new StringBuilder();
		if (model instanceof FMPartModel)
		{
			fitments = modelService.getAttributeValue(model, "fitments");
		}
		if (fitments != null && !fitments.isEmpty())
		{
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			for (final FMFitmentModel fitment : fitments)
			{
				final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
				for (final String fieldName : fieldNames)
				{
					String result = (String) getPropertyValue(fitment, indexedProperty);
					if (result != null && !result.isEmpty())
					{
						result = "||" + "ymmCode:" + fitment.getYear() + "$" + fitment.getMake() + "$" + fitment.getModel();
						output.append(result);

						if (fitment.getEngineDesignation() != null && !fitment.getEngineDesignation().isEmpty()
								&& !"ALL".equals(fitment.getEngineDesignation()))
						{

							output.append("|engineDesitination:" + fitment.getEngineDesignation());

						}

						if (fitment.getEngineVIN() != null && !fitment.getEngineVIN().isEmpty()
								&& !"ALL".equals(fitment.getEngineVIN()))
						{

							output.append("|engineVIN:" + fitment.getEngineVIN());

						}

						if (fitment.getAspiration() != null && !fitment.getAspiration().isEmpty()
								&& !"ALL".equals(fitment.getAspiration()))
						{

							output.append("|aspiration:" + fitment.getAspiration());

						}

						if (fitment.getCylinderHeadType() != null && !fitment.getCylinderHeadType().isEmpty()
								&& !"ALL".equals(fitment.getCylinderHeadType()))
						{

							output.append("|cylinderHeadType:" + fitment.getCylinderHeadType());

						}


						if (fitment.getFuelType() != null && !fitment.getFuelType().isEmpty() && !"ALL".equals(fitment.getFuelType()))
						{

							output.append("|fuelType:" + fitment.getFuelType());

						}

						if (fitment.getIgnitionSystemType() != null && !fitment.getIgnitionSystemType().isEmpty()
								&& !"ALL".equals(fitment.getIgnitionSystemType()))
						{

							output.append("|ignitionSystemType:" + fitment.getIgnitionSystemType());

						}

						if (fitment.getEngineVersion() != null && !fitment.getEngineVersion().isEmpty()
								&& !"ALL".equals(fitment.getEngineVersion()))
						{

							output.append("|engineVersion:" + fitment.getEngineVersion());

						}

						if (fitment.getFuelDeliveryType() != null && !fitment.getFuelDeliveryType().isEmpty()
								&& !"ALL".equals(fitment.getFuelDeliveryType()))
						{

							output.append("|fuelDeliveryType:" + fitment.getFuelDeliveryType());

						}

						if (fitment.getFuelDeliverySubType() != null && !fitment.getFuelDeliverySubType().isEmpty()
								&& !"ALL".equals(fitment.getFuelDeliverySubType()))
						{

							output.append("|fuelDeliverySubType:" + fitment.getFuelDeliverySubType());

						}

						if (fitment.getFuelSystemControlType() != null && !fitment.getFuelSystemControlType().isEmpty()
								&& !"ALL".equals(fitment.getFuelSystemControlType()))
						{

							output.append("|fuelSystemControlType:" + fitment.getFuelSystemControlType());

						}

						if (fitment.getFuelSystemDesign() != null && !fitment.getFuelSystemDesign().isEmpty()
								&& !"ALL".equals(fitment.getFuelSystemDesign()))
						{

							output.append("|fuelSystemDesign:" + fitment.getFuelSystemDesign());

						}

						if (fitment.getEngineMfr() != null && !fitment.getEngineMfr().isEmpty()
								&& !"ALL".equals(fitment.getEngineMfr()))
						{

							output.append("|engineMfr:" + fitment.getEngineMfr());

						}

						if (fitment.getEngineValves() != null && !fitment.getEngineValves().isEmpty()
								&& !"ALL".equals(fitment.getEngineValves()))
						{

							output.append("|engineValves:" + fitment.getEngineValves());

						}

						if (fitment.getPowerOutput() != null && !fitment.getPowerOutput().isEmpty()
								&& !"ALL".equals(fitment.getPowerOutput()))
						{

							output.append("|powerOutput:" + fitment.getPowerOutput());

						}

						if (fitment.getEngineArrangementNumber() != null && !fitment.getEngineArrangementNumber().isEmpty()
								&& !"ALL".equals(fitment.getEngineArrangementNumber()))
						{

							output.append("|engineArrangementNumber:" + fitment.getEngineArrangementNumber());

						}

						if (fitment.getEngineSerialNumber() != null && !fitment.getEngineSerialNumber().isEmpty()
								&& !"ALL".equals(fitment.getEngineSerialNumber()))
						{

							output.append("|engineSerialNumber:" + fitment.getEngineSerialNumber());

						}


						if (fitment.getEngineCPLNumber() != null && !fitment.getEngineCPLNumber().isEmpty()
								&& !"ALL".equals(fitment.getEngineCPLNumber()))
						{

							output.append("|engineCPLNumber:" + fitment.getEngineCPLNumber());

						}

						if (fitment.getVehicleType() != null && !fitment.getVehicleType().isEmpty()
								&& !"ALL".equals(fitment.getVehicleType()))
						{

							output.append("|vehicleType:" + fitment.getVehicleType());

						}

						if (fitment.getRegion() != null && !fitment.getRegion().isEmpty() && !"ALL".equals(fitment.getRegion()))
						{

							output.append("|region:" + fitment.getRegion());

						}

						if (fitment.getBodyNumDoors() != null && !fitment.getBodyNumDoors().isEmpty()
								&& !"ALL".equals(fitment.getBodyNumDoors()))
						{

							output.append("|bodyNumDoors:" + fitment.getBodyNumDoors());

						}

						if (fitment.getBodyType() != null && !fitment.getBodyType().isEmpty() && !"ALL".equals(fitment.getBodyType()))
						{

							output.append("|bodyType:" + fitment.getBodyType());

						}

						if (fitment.getBedLength() != null && !fitment.getBedLength().isEmpty()
								&& !"ALL".equals(fitment.getBedLength()))
						{

							output.append("|bedLength:" + fitment.getBedLength());

						}

						if (fitment.getMfrBodyCode() != null && !fitment.getMfrBodyCode().isEmpty()
								&& !"ALL".equals(fitment.getMfrBodyCode()))
						{

							output.append("|mfrBodyCode:" + fitment.getMfrBodyCode());

						}

						if (fitment.getBodyType() != null && !fitment.getBodyType().isEmpty() && !"ALL".equals(fitment.getBodyType()))
						{

							output.append("|bodyType:" + fitment.getBodyType());

						}

						if (fitment.getWheelBase() != null && !fitment.getWheelBase().isEmpty()
								&& !"ALL".equals(fitment.getWheelBase()))
						{

							output.append("|wheelBase:" + fitment.getWheelBase());

						}
						if (fitment.getFrontBrakeType() != null && !fitment.getFrontBrakeType().isEmpty()
								&& !"ALL".equals(fitment.getFrontBrakeType()))
						{

							output.append("|frontBrakeType:" + fitment.getFrontBrakeType());

						}

						if (fitment.getRearBrakeType() != null && !fitment.getRearBrakeType().isEmpty()
								&& !"ALL".equals(fitment.getRearBrakeType()))
						{

							output.append("|rearBrakeType:" + fitment.getRearBrakeType());

						}

						if (fitment.getBrakeSystem() != null && !fitment.getBrakeSystem().isEmpty()
								&& !"ALL".equals(fitment.getBrakeSystem()))
						{

							output.append("|brakeSystem:" + fitment.getBrakeSystem());

						}

						if (fitment.getBrakeABS() != null && !fitment.getBrakeABS().isEmpty() && !"ALL".equals(fitment.getBrakeABS()))
						{

							output.append("|brakeABS:" + fitment.getBrakeABS());

						}

						if (fitment.getFrontSpringType() != null && !fitment.getFrontSpringType().isEmpty()
								&& !"ALL".equals(fitment.getFrontSpringType()))
						{

							output.append("|frontSpringType:" + fitment.getFrontSpringType());

						}

						if (fitment.getRearSpringType() != null && !fitment.getRearSpringType().isEmpty()
								&& !"ALL".equals(fitment.getRearSpringType()))
						{

							output.append("|rearSpringType:" + fitment.getRearSpringType());

						}


						if (fitment.getSteeringSystem() != null && !fitment.getSteeringSystem().isEmpty()
								&& !"ALL".equals(fitment.getSteeringSystem()))
						{

							output.append("|steeringSystem:" + fitment.getSteeringSystem());

						}

						if (fitment.getVehicleSeries() != null && !fitment.getVehicleSeries().isEmpty()
								&& !"ALL".equals(fitment.getVehicleSeries()))
						{

							output.append("|vehicleSeries:" + fitment.getVehicleSeries());

						}

						if (fitment.getTransmissionNumSpeeds() != null && !fitment.getTransmissionNumSpeeds().isEmpty()
								&& !"ALL".equals(fitment.getTransmissionNumSpeeds()))
						{

							output.append("|transmissionNumSpeeds:" + fitment.getTransmissionNumSpeeds());

						}

						if (fitment.getTransmissionControlType() != null && !fitment.getTransmissionControlType().isEmpty()
								&& !"ALL".equals(fitment.getTransmissionControlType()))
						{

							output.append("|transmissionControlType:" + fitment.getTransmissionControlType());

						}

						if (fitment.getTransmissionMfrCode() != null && !fitment.getTransmissionMfrCode().isEmpty()
								&& !"ALL".equals(fitment.getTransmissionMfrCode()))
						{

							output.append("|transmissionMfrCode:" + fitment.getTransmissionMfrCode());

						}

						if (fitment.getTransmissionType() != null && !fitment.getTransmissionType().isEmpty()
								&& !"ALL".equals(fitment.getTransmissionType()))
						{

							output.append("|transmissionType:" + fitment.getTransmissionType());

						}

						if (fitment.getAppQty() != null && !fitment.getAppQty().isEmpty() && !"ALL".equals(fitment.getAppQty()))
						{

							output.append("|appQty:" + fitment.getAppQty());

						}

						if (fitment.getProduct() != null && !fitment.getProduct().isEmpty() && !"ALL".equals(fitment.getProduct()))
						{

							output.append("|product:" + fitment.getProduct());

						}

						if (fitment.getPosition() != null && !fitment.getPosition().isEmpty() && !"ALL".equals(fitment.getPosition()))
						{

							output.append("|position:" + fitment.getPosition());

						}
						if (fitment.getComment1() != null && !fitment.getComment1().isEmpty() && !"ALL".equals(fitment.getComment1()))
						{

							output.append("|comment1:" + fitment.getComment1());

						}
						if (fitment.getComment2() != null && !fitment.getComment2().isEmpty() && !"ALL".equals(fitment.getComment2()))
						{

							output.append("|comment2:" + fitment.getComment2());

						}
						if (fitment.getComment3() != null && !fitment.getComment3().isEmpty() && !"ALL".equals(fitment.getComment3()))
						{

							output.append("|comment3:" + fitment.getComment3());

						}

						if (fitment.getComment4() != null && !fitment.getComment4().isEmpty() && !"ALL".equals(fitment.getComment4()))
						{

							output.append("|comment4:" + fitment.getComment4());

						}
						if (fitment.getComment5() != null && !fitment.getComment5().isEmpty() && !"ALL".equals(fitment.getComment5()))
						{

							output.append("|comment5:" + fitment.getComment5());

						}
						if (fitment.getComment6() != null && !fitment.getComment6().isEmpty() && !"ALL".equals(fitment.getComment6()))
						{

							output.append("|comment6:" + fitment.getComment6());

						}
						if (fitment.getComment7() != null && !fitment.getComment7().isEmpty() && !"ALL".equals(fitment.getComment7()))
						{

							output.append("|comment7:" + fitment.getComment7());

						}
						if (fitment.getComment8() != null && !fitment.getComment8().isEmpty() && !"ALL".equals(fitment.getComment8()))
						{

							output.append("|comment8:" + fitment.getComment8());

						}
						if (fitment.getComment9() != null && !fitment.getComment9().isEmpty() && !"ALL".equals(fitment.getComment9()))
						{

							output.append("|comment9:" + fitment.getComment9());

						}
						if (fitment.getComment10() != null && !fitment.getComment10().isEmpty()
								&& !"ALL".equals(fitment.getComment10()))
						{

							output.append("|comment10:" + fitment.getComment10());

						}

						if (fitment.getApplicationNote1() != null && !fitment.getApplicationNote1().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote1()))
						{

							output.append("|applicationNote1:" + fitment.getApplicationNote1());

						}
						if (fitment.getApplicationNote2() != null && !fitment.getApplicationNote2().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote2()))
						{

							output.append("|applicationNote2:" + fitment.getApplicationNote2());

						}
						if (fitment.getApplicationNote3() != null && !fitment.getApplicationNote3().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote3()))
						{

							output.append("|applicationNote3:" + fitment.getApplicationNote3());

						}

						if (fitment.getApplicationNote4() != null && !fitment.getApplicationNote4().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote4()))
						{

							output.append("|applicationNote4:" + fitment.getApplicationNote4());

						}
						if (fitment.getApplicationNote5() != null && !fitment.getApplicationNote5().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote5()))
						{

							output.append("|applicationNote5:" + fitment.getApplicationNote5());

						}
						if (fitment.getApplicationNote6() != null && !fitment.getApplicationNote6().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote6()))
						{

							output.append("|applicationNote6:" + fitment.getApplicationNote6());

						}

						if (fitment.getApplicationNote7() != null && !fitment.getApplicationNote7().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote7()))
						{

							output.append("|applicationNote7:" + fitment.getApplicationNote7());

						}
						if (fitment.getApplicationNote8() != null && !fitment.getApplicationNote8().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote8()))
						{

							output.append("|applicationNote8:" + fitment.getApplicationNote8());

						}
						if (fitment.getApplicationNote9() != null && !fitment.getApplicationNote9().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote9()))
						{

							output.append("|applicationNote9:" + fitment.getApplicationNote9());

						}
						if (fitment.getApplicationNote10() != null && !fitment.getApplicationNote10().isEmpty()
								&& !"ALL".equals(fitment.getApplicationNote10()))
						{

							output.append("|applicationNote10:" + fitment.getApplicationNote10());

						}

						if (fitment.getInterchangeNote1() != null && !fitment.getInterchangeNote1().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote1()))
						{

							output.append("|interchangeNote1:" + fitment.getInterchangeNote1());

						}
						if (fitment.getInterchangeNote2() != null && !fitment.getInterchangeNote2().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote2()))
						{

							output.append("|interchangeNote2:" + fitment.getInterchangeNote2());

						}
						if (fitment.getInterchangeNote3() != null && !fitment.getInterchangeNote3().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote3()))
						{

							output.append("|interchangeNote3:" + fitment.getInterchangeNote3());

						}

						if (fitment.getInterchangeNote4() != null && !fitment.getInterchangeNote4().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote4()))
						{

							output.append("|interchangeNote4:" + fitment.getInterchangeNote4());

						}
						if (fitment.getInterchangeNote5() != null && !fitment.getInterchangeNote5().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote5()))
						{

							output.append("|interchangeNote5:" + fitment.getInterchangeNote5());

						}
						if (fitment.getInterchangeNote6() != null && !fitment.getInterchangeNote6().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote6()))
						{

							output.append("|interchangeNote6:" + fitment.getInterchangeNote6());

						}

						if (fitment.getInterchangeNote7() != null && !fitment.getInterchangeNote7().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote7()))
						{

							output.append("|interchangeNote7:" + fitment.getInterchangeNote7());

						}
						if (fitment.getInterchangeNote8() != null && !fitment.getInterchangeNote8().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote8()))
						{

							output.append("|interchangeNote8:" + fitment.getInterchangeNote8());

						}
						if (fitment.getInterchangeNote9() != null && !fitment.getInterchangeNote9().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote9()))
						{

							output.append("|interchangeNote9:" + fitment.getInterchangeNote9());

						}

						if (fitment.getInterchangeNote10() != null && !fitment.getInterchangeNote10().isEmpty()
								&& !"ALL".equals(fitment.getInterchangeNote10()))
						{

							output.append("|interchangeNote10:" + fitment.getInterchangeNote10());

						}

						if (fitment.getPartNumber() != null && !fitment.getPartNumber().isEmpty()
								&& !"ALL".equals(fitment.getPartNumber()))
						{

							output.append("|partNumber:" + fitment.getPartNumber());

						}


						if (fitment.getSubmodel() != null && !fitment.getSubmodel().isEmpty() && !"ALL".equals(fitment.getSubmodel()))
						{

							output.append("|submodel:" + fitment.getSubmodel());

						}

						if (fitment.getEngineBase() != null && !fitment.getEngineBase().isEmpty()
								&& !"ALL".equals(fitment.getEngineBase()))
						{

							output.append("|engineBase:" + fitment.getEngineBase());

						}

						if (fitment.getDriveType() != null && !fitment.getDriveType().isEmpty()
								&& !"ALL".equals(fitment.getDriveType()))
						{

							output.append("|driveType:" + fitment.getDriveType());

						}


					}
					fieldValues.add(new FieldValue(fieldName, output.toString()));
					output.setLength(0);
				}
			}
			return fieldValues;
		}
		else
		{
			return Collections.emptyList();
		}

	}

	/*
	 * protected Object getPropertyValue(final FMFitmentModel model) { return getPropertyValue(model, "model"); }
	 */
	protected Object getPropertyValue(final Object model, final IndexedProperty indexedProperty)
	{
		String qualifier = indexedProperty.getValueProviderParameter();

		if ((qualifier == null) || (qualifier.trim().isEmpty()))
		{
			qualifier = indexedProperty.getName();
		}
		//final Object result = null;
		return qualifier;//this.modelService.getAttributeValue(model, qualifier);
	}
}

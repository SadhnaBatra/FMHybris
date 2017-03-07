package com.federalmogul.facades.product.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import com.federalmogul.facades.product.data.FMFitmentData;
import com.federalmogul.falcon.core.model.FMFitmentModel;


/**
 * Mamud Populator for Part fitment popuplator.
 */
public class FMFitmentPopulator implements Populator<FMFitmentModel, FMFitmentData>
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final FMFitmentModel source, final FMFitmentData target) throws ConversionException
	{
		// YTODO Auto-generated method stub

		if (source == null)
		{
			throw new IllegalArgumentException("The provided source FMFitment model argument cannot be null");
		}
		if (target == null)
		{
			throw new IllegalArgumentException("The provided target FMFitment data argument cannot be null");
		}

		target.setEngineBase(source.getEngineBase() != null ? source.getEngineBase().toString() : null);
		target.setBedType(source.getBedType() != null ? source.getBedType().toString() : null);
		target.setEngineDesignation(source.getEngineDesignation() != null ? source.getEngineDesignation().toString() : null);
		target.setEngineVIN(source.getEngineVIN() != null ? source.getEngineVIN().toString() : null);
		target.setAspiration(source.getAspiration() != null ? source.getAspiration().toString() : null);
		target.setCylinderHeadType(source.getCylinderHeadType() != null ? source.getCylinderHeadType().toString() : null);
		target.setFuelType(source.getFuelType() != null ? source.getFuelType().toString() : null);
		target.setIgnitionSystemType(source.getIgnitionSystemType() != null ? source.getIgnitionSystemType().toString() : null);
		target.setEngineVersion(source.getEngineVersion() != null ? source.getEngineVersion().toString() : null);
		target.setFuelDeliveryType(source.getFuelDeliveryType() != null ? source.getFuelDeliveryType().toString() : null);
		target.setFuelDeliverySubType(source.getFuelDeliverySubType() != null ? source.getFuelDeliverySubType().toString() : null);
		target.setFuelSystemControlType(source.getFuelSystemControlType() != null ? source.getFuelSystemControlType().toString()
				: null);
		target.setFuelSystemDesign(source.getFuelSystemDesign() != null ? source.getFuelSystemDesign().toString() : null);
		target.setEngineMfr(source.getEngineMfr() != null ? source.getEngineMfr().toString() : null);
		target.setPowerOutput(source.getPowerOutput() != null ? source.getPowerOutput().toString() : null);
		target.setEngineArrangementNumber(source.getEngineArrangementNumber() != null ? source.getEngineArrangementNumber()
				.toString() : null);
		target.setEngineSerialNumber(source.getEngineSerialNumber() != null ? source.getEngineSerialNumber().toString() : null);
		target.setEngineCPLNumber(source.getEngineCPLNumber() != null ? source.getEngineCPLNumber().toString() : null);
		target.setVehicleType(source.getVehicleType() != null ? source.getVehicleType().toString() : null);
		target.setRegion(source.getRegion() != null ? source.getRegion().toString() : null);
		target.setBodyNumDoors(source.getBodyNumDoors() != null ? source.getBodyNumDoors().toString() : null);
		target.setBodyType(source.getBodyType() != null ? source.getBodyType().toString() : null);
		target.setBedLength(source.getBedLength() != null ? source.getBedLength().toString() : null);
		target.setDriveType(source.getDriveType() != null ? source.getDriveType().toString() : null);
		target.setMfrBodyCode(source.getMfrBodyCode() != null ? source.getMfrBodyCode().toString() : null);
		target.setWheelBase(source.getWheelBase() != null ? source.getWheelBase().toString() : null);
		target.setFrontBrakeType(source.getFrontBrakeType() != null ? source.getFrontBrakeType().toString() : null);
		target.setRearBrakeType(source.getRearBrakeType() != null ? source.getRearBrakeType().toString() : null);
		target.setBrakeSystem(source.getBrakeSystem() != null ? source.getBrakeSystem().toString() : null);
		target.setBrakeABS(source.getBrakeABS() != null ? source.getBrakeABS().toString() : null);
		target.setFrontSpringType(source.getFrontSpringType() != null ? source.getFrontSpringType().toString() : null);
		target.setRearSpringType(source.getRearSpringType() != null ? source.getRearSpringType().toString() : null);
		target.setSteeringType(source.getSteeringType() != null ? source.getSteeringType().toString() : null);
		target.setSteeringSystem(source.getSteeringSystem() != null ? source.getSteeringSystem().toString() : null);
		target.setVehicleSeries(source.getVehicleSeries() != null ? source.getVehicleSeries().toString() : null);
		target.setTransmissionNumSpeeds(source.getTransmissionNumSpeeds() != null ? source.getTransmissionNumSpeeds().toString()
				: null);
		target.setTransmissionControlType(source.getTransmissionControlType() != null ? source.getTransmissionControlType()
				.toString() : null);
		target.setTransmissionMfrCode(source.getTransmissionMfrCode() != null ? source.getTransmissionMfrCode().toString() : null);
		target.setTransmissionType(source.getTransmissionType() != null ? source.getTransmissionType().toString() : null);
		target.setPosition(source.getPosition() != null ? source.getPosition().toString() : null);
		target.setSubmodel(source.getSubmodel() != null ? source.getSubmodel().toString() : null);


		target.setCode(source.getCode() != null ? source.getCode().toString() : null);
		target.setYear(source.getYear() != null ? source.getYear().toString() : null);
		target.setMake(source.getMake() != null ? source.getMake().toString() : null);
		target.setModel(source.getModel() != null ? source.getModel().toString() : null);
		target.setProducts(source.getProduct() != null ? source.getProduct().toString() : null);
		target.setPartNumber(source.getPartNumber() != null ? source.getPartNumber().toString() : null);
		target.setAppQuantity(source.getAppQty() != null ? source.getAppQty().toString() : null);

		target.setMfrLabel(source.getMfrLabel() != null ? source.getMfrLabel().toString() : null);
		target.setComment1(source.getComment1() != null ? source.getComment1().toString() : null);
		target.setComment2(source.getComment2() != null ? source.getComment2().toString() : null);
		target.setComment3(source.getComment3() != null ? source.getComment3().toString() : null);
		target.setComment4(source.getComment4() != null ? source.getComment4().toString() : null);
		target.setComment5(source.getComment5() != null ? source.getComment5().toString() : null);
		target.setComment6(source.getComment6() != null ? source.getComment6().toString() : null);
		target.setComment7(source.getComment7() != null ? source.getComment7().toString() : null);
		target.setComment8(source.getComment8() != null ? source.getComment8().toString() : null);
		target.setComment9(source.getComment9() != null ? source.getComment9().toString() : null);
		target.setComment10(source.getComment10() != null ? source.getComment10().toString() : null);

		target.setInterchangeNote1(source.getInterchangeNote1() != null ? source.getInterchangeNote1().toString() : null);
		target.setInterchangeNote2(source.getInterchangeNote2() != null ? source.getInterchangeNote2().toString() : null);
		target.setInterchangeNote3(source.getInterchangeNote3() != null ? source.getInterchangeNote3().toString() : null);
		target.setInterchangeNote4(source.getInterchangeNote4() != null ? source.getInterchangeNote4().toString() : null);
		target.setInterchangeNote5(source.getInterchangeNote5() != null ? source.getInterchangeNote5().toString() : null);
		target.setInterchangeNote6(source.getInterchangeNote6() != null ? source.getInterchangeNote6().toString() : null);
		target.setInterchangeNote7(source.getInterchangeNote7() != null ? source.getInterchangeNote7().toString() : null);
		target.setInterchangeNote8(source.getInterchangeNote8() != null ? source.getInterchangeNote8().toString() : null);
		target.setInterchangeNote9(source.getInterchangeNote9() != null ? source.getInterchangeNote9().toString() : null);
		target.setInterchangeNote10(source.getInterchangeNote10() != null ? source.getInterchangeNote10().toString() : null);

		target.setApplicationNote1(source.getApplicationNote1() != null ? source.getApplicationNote1().toString() : null);
		target.setApplicationNote2(source.getApplicationNote2() != null ? source.getApplicationNote2().toString() : null);
		target.setApplicationNote3(source.getApplicationNote3() != null ? source.getApplicationNote3().toString() : null);
		target.setApplicationNote4(source.getApplicationNote4() != null ? source.getApplicationNote4().toString() : null);
		target.setApplicationNote5(source.getApplicationNote5() != null ? source.getApplicationNote5().toString() : null);
		target.setApplicationNote6(source.getApplicationNote6() != null ? source.getApplicationNote6().toString() : null);
		target.setApplicationNote7(source.getApplicationNote7() != null ? source.getApplicationNote7().toString() : null);
		target.setApplicationNote8(source.getApplicationNote8() != null ? source.getApplicationNote8().toString() : null);
		target.setApplicationNote9(source.getApplicationNote9() != null ? source.getApplicationNote9().toString() : null);
		target.setApplicationNote10(source.getApplicationNote10() != null ? source.getApplicationNote10().toString() : null);

		target.setAssetLogicalName(source.getAssetLogicalName() != null ? source.getAssetLogicalName().toString() : null);
		target.setAssetItemRef(source.getAssetLogicalName() != null ? source.getAssetLogicalName().toString() : null);
		target.setAssetItemOrder(source.getAssetLogicalName() != null ? source.getAssetLogicalName().toString() : null);
		target.setAssetFileName(source.getAssetLogicalName() != null ? source.getAssetLogicalName().toString() : null);
		target.setVehiclesegment(source.getVehiclesegment() != null ? source.getVehiclesegment().toString() : null);


	}
}
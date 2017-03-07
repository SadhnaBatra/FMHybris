<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="fitment" required="true"
	type="com.federalmogul.facades.product.data.FMFitmentData"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="statusIndex" required="true" type="java.lang.Integer" %>
<%@ attribute name="fitmentCount" required="true" type="java.lang.Integer" %>
<%@ taglib prefix="federalmogul" uri="http://federalmogul.com/tld/federalmogultags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

	<div class="multipleAppSpec<c:if test="${statusIndex ge 3}">  hideAppSpec${fitment.partNumber}</c:if>"  style="<c:if test="${statusIndex ge 3}">display:none;</c:if><c:if test="${statusIndex lt 3}">display:block;</c:if>">
		<ul>
			<%-- <c:if test="${not empty fitment.mfrLabel && fitment.mfrLabel != 'ALL'}">
				<li><span class="specLabel">Manufacture Label:</span> ${ fitment.mfrLabel}</li>
			</c:if> --%>
			
			<c:if test="${not empty fitment.products && fitment.products != 'ALL'}">
				<li><span class="specLabel">Products:</span> ${ fitment.products}</li>
			</c:if>
			<c:if test="${not empty fitment.position && fitment.position != 'ALL' }">
				<li><span class="specLabel">Position:</span> ${ fitment.position}</li>
			</c:if>
			<c:if test="${not empty fitment.engineBase && fitment.engineBase != 'ALL'}">
				<li><span class="specLabel">EngineBase:</span> ${ fitment.engineBase}</li>
			</c:if>
			<c:if test="${not empty fitment.driveType && fitment.driveType != 'ALL'}">
				<li><span class="specLabel">Drive Type:</span> ${ fitment.driveType}</li>
			</c:if>
			<c:if test="${not empty fitment.submodel && fitment.submodel != 'ALL'}">
				<li><span class="specLabel">Sub-Model:</span> ${ fitment.submodel}</li>
			</c:if>
			<c:if test="${not empty fitment.engineDesignation && fitment.engineDesignation != 'ALL'}">
				<li><span class="specLabel">Engine Designation:</span> ${ fitment.engineDesignation}</li>
			</c:if>
			<c:if test="${not empty fitment.engineVIN && fitment.engineVIN != 'ALL'}">
				<li><span class="specLabel">EngineVIN:</span> ${ fitment.engineVIN}</li>
			</c:if>
			<c:if test="${not empty fitment.bodyType && fitment.bodyType != 'ALL'}">
				<li><span class="specLabel">Body Type:</span> ${ fitment.bodyType}</li>
			</c:if>
			<c:if test="${not empty fitment.bodyNumDoors && fitment.bodyNumDoors != 'ALL'}">
				<li><span class="specLabel">Body Number Doors:</span> ${ fitment.bodyNumDoors}</li>
			</c:if>
			<c:if test="${not empty fitment.bedLength && fitment.bedLength != 'ALL'}">
				<li><span class="specLabel">Bed Length:</span> ${ fitment.bedLength}</li>
			</c:if>
			<c:if test="${not empty fitment.bedType && fitment.bedType != 'ALL' }">
				<li><span class="specLabel">Bed Type:</span> ${ fitment.bedType}</li>
			</c:if>
			<c:if test="${not empty fitment.mfrBodyCode && fitment.mfrBodyCode != 'ALL' }">
				<li><span class="specLabel">Manufacture Body Code:</span> ${ fitment.mfrBodyCode}</li>
			</c:if>
			<c:if test="${not empty fitment.wheelBase && fitment.wheelBase != 'ALL'}">
				<li><span class="specLabel">Drive Wheel:</span> ${ fitment.wheelBase}</li>
			</c:if>
			<c:if test="${not empty fitment.frontBrakeType && fitment.frontBrakeType != 'ALL' }">
				<li><span class="specLabel">Front Brake Type:</span> ${ fitment.frontBrakeType}</li>
			</c:if>
			<c:if test="${not empty fitment.rearBrakeType && fitment.rearBrakeType != 'ALL' }">
				<li><span class="specLabel">Rear Brake Type:</span> ${ fitment.rearBrakeType}</li>
			</c:if>
			<c:if test="${not empty fitment.brakeSystem && fitment.brakeSystem != 'ALL'}">
				<li><span class="specLabel">Brake System:</span> ${ fitment.brakeSystem}</li>
			</c:if>
			<c:if test="${not empty fitment.brakeABS && fitment.brakeABS != 'ALL'}">
				<li><span class="specLabel">Brake ABS:</span> ${ fitment.brakeABS}</li>
			</c:if>
			<c:if test="${not empty fitment.frontSpringType && fitment.frontSpringType != 'ALL'}">
				<li><span class="specLabel">Front Spring Type:</span> ${ fitment.frontSpringType}</li>
			</c:if>
			<c:if test="${not empty fitment.rearSpringType && fitment.rearSpringType != 'ALL'}">
				<li><span class="specLabel">Rear Spring Type:</span> ${ fitment.rearSpringType}</li>
			</c:if>
			<c:if test="${not empty fitment.steeringType && fitment.steeringType != 'ALL'}">
				<li><span class="specLabel">Steering Type:</span> ${ fitment.steeringType}</li>
			</c:if>
			<c:if test="${not empty fitment.steeringSystem && fitment.steeringSystem != 'ALL'}">
				<li><span class="specLabel">Steering System:</span> ${ fitment.steeringSystem}</li>
			</c:if>
			<c:if test="${not empty fitment.vehicleSeries && fitment.vehicleSeries != 'ALL'}">
				<li><span class="specLabel">Vehicle Series:</span> ${ fitment.vehicleSeries}</li>
			</c:if>
			<c:if test="${not empty fitment.region && fitment.region != 'ALL'}">
				<li><span class="specLabel">Region:</span> ${ fitment.region}</li>
			</c:if>
			<c:if test="${not empty fitment.vehicleType && fitment.vehicleType != 'ALL'}">
				<li><span class="specLabel">Vehicle Type:</span> ${ fitment.vehicleType}</li>
			</c:if>
			<c:if test="${not empty fitment.aspiration && fitment.aspiration != 'ALL'}">
				<li><span class="specLabel">Aspiration:</span> ${ fitment.aspiration}</li>
			</c:if>
			<c:if test="${not empty fitment.fuelType && fitment.fuelType != 'ALL'}">
				<li><span class="specLabel">Fuel Type:</span> ${ fitment.fuelType}</li>
			</c:if>
			<c:if test="${not empty fitment.cylinderHeadType && fitment.cylinderHeadType != 'ALL'}">
				<li><span class="specLabel">Cylinder Head Type:</span> ${ fitment.cylinderHeadType}</li>
			</c:if>
			<c:if test="${not empty fitment.ignitionSystemType && fitment.ignitionSystemType != 'ALL'}">
				<li><span class="specLabel">IgnitionSystem:</span> ${ fitment.ignitionSystemType}</li>
			</c:if>
			<c:if test="${not empty fitment.engineVersion && fitment.engineVersion != 'ALL'}">
				<li><span class="specLabel">Engine Version:</span> ${ fitment.engineVersion}</li>
			</c:if>
			<c:if test="${not empty fitment.fuelDeliveryType && fitment.fuelDeliveryType != 'ALL'}">
				<li><span class="specLabel">FuelDelivery:</span> ${ fitment.fuelDeliveryType}</li>
			</c:if>
			<c:if test="${not empty fitment.fuelDeliverySubType && fitment.fuelDeliverySubType != 'ALL'}">
				<li><span class="specLabel">FuelDelivery Sub Type:</span> ${ fitment.fuelDeliverySubType}</li>
			</c:if>
			<c:if test="${not empty fitment.fuelSystemControlType && fitment.fuelSystemControlType != 'ALL'}">
				<li><span class="specLabel">Fuel System Control Type:</span> ${ fitment.fuelSystemControlType}</li>
			</c:if>
			<c:if test="${not empty fitment.fuelSystemDesign && fitment.fuelSystemDesign != 'ALL'}">
				<li><span class="specLabel">Fuel System Design:</span> ${ fitment.fuelSystemDesign}</li>
			</c:if>
			<c:if test="${not empty fitment.engineMfr && fitment.engineMfr != 'ALL'}">
				<li><span class="specLabel">Engine Manufacture:</span> ${ fitment.engineMfr}</li>
			</c:if>
			<c:if test="${not empty fitment.engineValves && fitment.engineValves != 'ALL'}">
				<li><span class="specLabel">Engine Valves:</span> ${ fitment.engineValves}</li>
			</c:if>
			<c:if test="${not empty fitment.powerOutput && fitment.powerOutput != 'ALL'}">
				<li><span class="specLabel">Power Output:</span> ${ fitment.powerOutput}</li>
			</c:if>
			<c:if test="${not empty fitment.engineArrangementNumber && fitment.engineArrangementNumber != 'ALL'}">
				<li><span class="specLabel">Engine Arrangement Number:</span> ${ fitment.engineArrangementNumber}</li>
			</c:if>
			<c:if test="${not empty fitment.engineSerialNumber && fitment.engineSerialNumber != 'ALL'}">
				<li><span class="specLabel">Engine Serial Number:</span> ${ fitment.engineSerialNumber}</li>
			</c:if>
			<c:if test="${not empty fitment.engineCPLNumber && fitment.engineCPLNumber != 'ALL'}">
				<li><span class="specLabel">Engine CPL Number:</span> ${ fitment.engineCPLNumber}</li>
			</c:if>
			<c:if test="${not empty fitment.transmissionNumSpeeds && fitment.transmissionNumSpeeds != 'ALL'}">
				<li><span class="specLabel">Transmission Number Speeds:</span> ${ fitment.transmissionNumSpeeds}</li>
			</c:if>
			<c:if test="${not empty fitment.transmissionControlType && fitment.transmissionControlType != 'ALL'}">
				<li><span class="specLabel">Transmission Control Type:</span> ${ fitment.transmissionControlType}</li>
			</c:if>
			<c:if test="${not empty fitment.transmissionMfrCode && fitment.transmissionMfrCode != 'ALL'}">
				<li><span class="specLabel">Transmission Manufacture Code:</span> ${ fitment.transmissionMfrCode}</li>
			</c:if>
			<c:if test="${not empty fitment.transmissionType && fitment.transmissionType != 'ALL'}">
				<li><span class="specLabel">Transmission Type:</span> ${ fitment.transmissionType}</li>
			</c:if>
			<c:if test="${not empty fitment.appQuantity  }">
				<li><span class="specLabel">Application Quantity:</span> ${ fitment.appQuantity}</li>
			</c:if>
			
			<!-- ###################################### -->
			<%-- <c:if test="${not empty fitment.assetItemRef && fitment.assetItemRef != 'ALL'}">
				<li><span class="specLabel">AssetItemRef:</span> ${ fitment.assetItemRef}</li>
			</c:if>
			<c:if test="${not empty fitment.assetItemOrder && fitment.assetItemOrder != 'ALL' }">
				<li><span class="specLabel">Asset Item Order:</span> ${ fitment.assetItemOrder}</li>
			</c:if>
			
			<c:if test="${not empty fitment.assetLogicalName && fitment.assetLogicalName != 'ALL'}">
				<li><span class="specLabel">Asset Logical Name:</span> ${ fitment.assetLogicalName}</li>
			</c:if>
			<c:if test="${not empty fitment.assetFileName && fitment.assetFileName != 'ALL'}">
				<li><span class="specLabel">Asset File Name:</span> ${ fitment.assetFileName}</li>
			</c:if> --%>
			<li><span class="specLabel"></span></li>

		</ul>
		<c:if test="${not empty federalmogul:allFitmentCriteria(fitment) && federalmogul:allFitmentCriteria(fitment) ne ' ' }">
			<div class="clearfix additionalFitCriteria">
				<div class="additionalFitCriteriaInfo pull-left">
					<span class="tip" title="" data-placement="right"
						data-toggle="tooltip" data-original-title="More Info"><span
						class="fa fa-exclamation-triangle"></span></span>
				</div>
				<div class="additionalFitCriteriaTxt pull-right">
					<span class="specLabel">Additional Fit Criteria:</span> 
					<c:set value="${federalmogul:allFitmentCriteria(fitment)}" var="FinalfitmentCriteria" />
					 <div  >
						 ${FinalfitmentCriteria} 
					</div>
				</div>
			</div>
		</c:if>
		
		
		<%-- <c:if test="${not empty fitment.comment1 || 
					  not empty fitment.comment2 ||
					  not empty fitment.comment3 || 
					  not empty fitment.comment4 || 
					  not empty fitment.comment5 || 
					  not empty fitment.comment6 || 
					  not empty fitment.comment7 || 
					  not empty fitment.comment8 || 
					  not empty fitment.comment9 || 
					  not empty fitment.comment10    }">
			<div class="clearfix additionalFitCriteria">
				<div class="additionalFitCriteriaInfo pull-left">
					<span class="tip" title="" data-placement="right"
						data-toggle="tooltip" data-original-title="More Info"><span
						class="fa fa-exclamation-triangle"></span></span>
				</div>
				<div class="additionalFitCriteriaTxt pull-right">
					<span class="specLabel">Additional Fit Criteria:</span> 
					<c:set value="${federalmogul:fitmentCriteria(fitment,'comment')}" var="FinalfitmentCriteria" />
					 ${FinalfitmentCriteria} 
					
				</div>
			</div>
		</c:if> --%>
		
		<%-- <c:if test="${not empty fitment.applicationNote1 || 
					  not empty fitment.applicationNote2 ||
					  not empty fitment.applicationNote3 || 
					  not empty fitment.applicationNote4 || 
					  not empty fitment.applicationNote5 || 
					  not empty fitment.applicationNote6 || 
					  not empty fitment.applicationNote7 || 
					  not empty fitment.applicationNote8 || 
					  not empty fitment.applicationNote9 || 
					  not empty fitment.applicationNote10    }">
			<div class="clearfix additionalFitCriteria">
				<div class="additionalFitCriteriaInfo pull-left">
					<span class="tip" title="" data-placement="right"
						data-toggle="tooltip" data-original-title="More Info"><span
						class="fa fa-exclamation-triangle"></span></span>
				</div>
				<div class="additionalFitCriteriaTxt pull-right">
					<span class="specLabel">Additional Fit Criteria:</span>
					<c:set value="${federalmogul:fitmentCriteria(fitment,'applicationNote')}" var="FinalfitmentCriteria" />
						${FinalfitmentCriteria} 
					
				</div>
			</div>
		</c:if> --%>
		
		
		<%-- <c:if test="${not empty fitment.interchangeNote1 || 
					  not empty fitment.interchangeNote2 ||
					  not empty fitment.interchangeNote3 || 
					  not empty fitment.interchangeNote4 || 
					  not empty fitment.interchangeNote5 || 
					  not empty fitment.interchangeNote6 || 
					  not empty fitment.interchangeNote7 || 
					  not empty fitment.interchangeNote8 || 
					  not empty fitment.interchangeNote9 || 
					  not empty fitment.interchangeNote10    }">
			<div class="clearfix additionalFitCriteria">
				<div class="additionalFitCriteriaInfo pull-left">
					<span class="tip" title="" data-placement="right"
						data-toggle="tooltip" data-original-title="More Info"><span
						class="fa fa-exclamation-triangle"></span></span>
				</div>
				<div class="additionalFitCriteriaTxt pull-right">
					<span class="specLabel">Additional Fit Criteria:</span>
					<c:set value="${federalmogul:fitmentCriteria(fitment,'interchangeNote')}" var="FinalfitmentCriteria" />
					 ${FinalfitmentCriteria} 
					
				</div>
			</div>
		</c:if> --%>
	</div>

<c:if test="${statusIndex eq 3}">

	<div
		class="clearfix additionalAppNotes additionalApp${fitment.partNumber}">

		<div class="additionalFitCriteriaInfo pull-left">

			<span class="tip" title="" data-placement="right"
				data-toggle="tooltip" data-original-title="More Info"><span
				class="fa fa-exclamation-triangle"></span></span>

		</div>

		<div class="additionalFitCriteriaTxt pull-right">
			<spring:theme code="ProductSearchresultspage.fitmentinfo.multiplefits.more"/>
			${fitment.partNumber}. <a
				onClick="$('.additionalApp${fitment.partNumber}').hide(); $('.hideAppSpec${fitment.partNumber}').toggle();$('.collapseApp${fitment.partNumber}').show();">

				<spring:theme code="ProductSearchresultspage.fitmentinfo.fitCriteria"/> </a>
		</div>
	</div>
</c:if>

<c:if test="${ statusIndex ge 3 && statusIndex eq (fitmentCount -1)}">

	<div
		class="clearfix additionalAppNotes collapseApp${fitment.partNumber}"
		style="display: none">
		<div class="additionalFitCriteriaInfo pull-left">
			<span class="tip" title="" data-placement="right"
				data-toggle="tooltip" data-original-title="More Info"><span
				class="fa fa-exclamation-triangle"></span></span>
		</div>
		<div class="additionalFitCriteriaTxt pull-right">
				<spring:theme code="ProductSearchresultspage.fitmentinfo.multiplefits.more"/>
			${fitment.partNumber}. <a
				onClick="$('.additionalApp${fitment.partNumber}').show(); $('.hideAppSpec${fitment.partNumber}').toggle();$('.collapseApp${fitment.partNumber}').hide();">
				<spring:theme code="ProductSearchresultspage.fitmentinfo.multiplefits.less"/></a>
		</div>
	</div>
</c:if>




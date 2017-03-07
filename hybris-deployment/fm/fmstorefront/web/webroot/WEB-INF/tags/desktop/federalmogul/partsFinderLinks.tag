
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="whereToBuyURI"><spring:eval expression="@propertyConfigurer.getProperty('wheretobuyurl')" /></c:set>

<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 contentLHS">
	<!--- Order in Progress PANEL -->

	<div class="panel panel-default manageAccountLinks clearfix">
		<div class="panel-body orgMangPanel">
			<h3 class="text-uppercase">Parts Finder</h3>
			<ul class="">
				<li><spring:url value="/catalog/part-Number-search" var="partUrl" />
              	<a href="${partUrl}" ${partsFinderLink eq 'PartNumberSearch' ? 'class="selected"' : 'class=""'}>Federal-Mogul Part Search <span ${partsFinderLink eq 'PartNumberSearch' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a></li>
				<li><spring:url value="/catalog/partsFinderVehicleLookup" var="vehicleurl" /> <a
					href="${vehicleurl}" ${partsFinderLink eq 'VehicleLookUp' ? 'class="selected"' : 'class=""'}>Vehicle Lookup<span ${partsFinderLink eq 'VehicleLookUp' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a></li>
				<li>
				<spring:url value="/catalog/partsFinderLicensePlateLookup" var="licenseurl" />
				<a href="${licenseurl}" ${partsFinderLink eq 'LicensePlateLookup' ? 'class="selected"' : 'class=""'}>License Plate Lookup<span
						${partsFinderLink eq 'LicensePlateLookup' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a></li>
				<li>
		<spring:url value="/catalog/partsFinderLicenseVINLookup" var="vinurl" />
				<a href="${vinurl}" ${partsFinderLink eq 'LicenseVINLookup' ? 'class="selected"' : 'class=""'}>VIN Lookup <span
						${partsFinderLink eq 'LicenseVINLookup' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a></li>
						
				<li>
				
				<spring:url value="/catalog/competitor-interchange" var="competitorurl" />
				<a href="${competitorurl}" ${partsFinderLink eq 'PartInterchange' ? 'class="selected"' : 'class=""'}>Competitor Interchange <span
						${partsFinderLink eq 'PartInterchange' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a></li>
              <li><a href="${whereToBuyURI}">Where to Buy <span class="linkarow fa fa-angle-right"></span></a></li>
			</ul>
		</div>
	</div>
</div>
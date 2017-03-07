<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<div id="defaultshow">
	<p></p>
	<h1 class="text-uppercase">Locations</h1>

	<div class="form-group  regFormFieldGroup" id="fmzoneDiv">
		<select class="form-control width237" name="fmzone"
			id="fmzone">
			<c:forEach items="${fmZones}"  var="fmZone">
				<c:choose>
					<c:when test="${fmZone.code eq 'NorthAmerica'}">
						<option selected="selected" value="${fmZone.code}">${fmZone.name}</option>
					</c:when>
					<c:otherwise>
						<option  value="${fmZone.code}">${fmZone.name}</option>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>
		</select>
	</div>
	<div class="form-group  regFormFieldGroup" id="fmCountriesDiv">
		<select class="form-control width237" name="fmcountry" id="fmcountry">
			<c:forEach items="${fmcountries}"  var="fmCountry">
				<c:choose>
					<c:when test="${fmCountry.isocode eq 'US'}">
						<option selected="selected" value="${fmCountry.isocode}">${fmCountry.name}</option>
					</c:when>
					<c:otherwise>
						<option  value="${fmCountry.isocode}">${fmCountry.name}</option>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>
		</select>
	</div>
	<div class="form-group  regFormFieldGroup" id="fmStatesDiv">
		<select class="form-control width237" name="fmState" id="fmState">
			<c:forEach items="${fmStatesData}"  var="fmState">
				<c:choose>
					<c:when test="${fmState.isocode eq 'US-MI'}">
						<option selected="selected" value="${fmState.isocode}">${fmState.name}</option>
					</c:when>
					<c:otherwise>
						<option value="${fmState.isocode}">${fmState.name}</option>
					</c:otherwise>
				</c:choose>
				
			</c:forEach>
		</select>
	</div>
	<div class="btmMrgn_30 checkoutSubTitle fm_bgBlue text-uppercase">contact
		list</div>

	<div class="address" id="fmlocations">
			<c:forEach items="${fmAddressLocations}" var="fmlocation"
				varStatus="status">

				<c:if test="${(status.index)%3 == 0}">
					<div class="row">
				</c:if>
					<div class="col-md-4 col-sm-4 col-xs-12 text-capitalize">
						<label>${fmlocation.building}</label>
						<c:if test="${not empty fmlocation.appartment or not empty  fmlocation.department}">
							<p style="margin-top: 1px;">  ${fmlocation.department}  <span style="margin-left:2px;"> ${fmlocation.appartment} </span></p>
						</c:if>
						<p style="margin-top: -13px;">${fmlocation.streetname }</p>
						<p style="margin-top: -13px;"> ${fmlocation.district} &nbsp; ${fmlocation.region.isocodeShort}
						<span style="margin-left: 5px;">${fmlocation.postalcode}</span> </p>
						<p style="margin-top: -14px;">${fmlocation.country.name}</p>
					</div>
				<c:if test="${(status.index+1)%3 == 0}">
					</div>
				</c:if>
		</c:forEach>
	</div>
</div>
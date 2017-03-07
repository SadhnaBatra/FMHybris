<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="storeSearchPageData" required="false"
	type="de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ attribute name="searchPageData" required="false"
	type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData"%>
<%@ attribute name="locationQuery" required="false"
	type="java.lang.String"%>
<%@ attribute name="geoPoint" required="false"
	type="de.hybris.platform.commerceservices.store.data.GeoPoint"%>
<%@ attribute name="numberPagesShown" required="true"
	type="java.lang.Integer"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="store" tagdir="/WEB-INF/tags/desktop/store"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<c:if
       test="${storeSearchPageData ne null and !empty storeSearchPageData.results}">
       <div id="map_canvas" data-latitude='${searchPageData.sourceLatitude}'
              data-longitude='${searchPageData.sourceLongitude}'
              data-south-Latitude='${searchPageData.boundSouthLatitude}'
              data-west-Longitude='${searchPageData.boundWestLongitude}'
              data-North-Latitude='${searchPageData.boundNorthLatitude}'
              data-east-Longitude='${searchPageData.boundEastLongitude}'
              data-stores='{
              <c:forEach items="${storeSearchPageData.results}" var="singlePos" varStatus="status">
            <%--   <c:set var="posName" value="${fn:escapeXml(singlePos.name)}" /> --%>
              	         <c:set var="posName" value="${fn:split(singlePos.name, '$')}" />
								<c:if test="${not empty posName[0]}">									
									<c:set var="posName" value="${fn:escapeXml(posName[0])}" />
								</c:if>
                     <c:if test="${(status.index != 0)}">,</c:if>"store${status.index}":{"id":"${status.index}","latitude":"${singlePos.geoPoint.latitude}","longitude":"${singlePos.geoPoint.longitude}","name":"${(posName)}"}
              </c:forEach>
                     }'></div>
</c:if>

<c:if
	test="${storeSearchPageData eq null or empty storeSearchPageData.results}">
		<iframe id="" src="https://www.google.com/maps/embed" height="500" width="100%"></iframe>
	<cms:pageSlot position="SideContent" var="feature">
		<cms:component component="${feature}" element="div"
			class="cms_disp-img_slot" />
	</cms:pageSlot>
</c:if>

<c:set value="/store-finder?q=${param.q}" var="searchUrl"
	scope="session" />
<c:if test="${not empty geoPoint}">
	<c:set
		value="/store-finder?latitude=${geoPoint.latitude}&longitude=${geoPoint.longitude}&q=${param.q}"
		var="searchUrl" scope="session" />
</c:if>
<%-- searchPageData.results.size ::: ${searchPageData.searchResult.size}--%>

<c:if test="${searchPageData ne null and !empty searchPageData.results}">
	<c:set var="count" value="0" />
	<div class="orderStatusTable">
		<div class="table-responsive userTable">

			<table class="table tablesorter" id="myTable">
				<thead>
					<tr class="text-capitalize">
						<!-- <th class="header">Number</th> -->
						<th class="header" style ="width:20%">Distance</th>
						<th class="header" style ="width:20%">Name</th>
						<th class="header">Address</th>
						<th class="header"></th>
						<!-- 	<th class="header">Action</th> -->
					</tr>
				</thead>

				<tbody>

					<c:set var="count" value="1" scope="page" />
					<c:forEach items="${searchPageData.results}" var="pos"
						varStatus="loopStatus">
						<c:set scope="request"
							value="${pos.address.line1} - ${pos.address.line2} - ${pos.address.town} - ${pos.address.postalCode}"
							var="addr" />
				<c:choose>
			<c:when test="${showNearestform ge loopStatus.count}">
						<c:url value="${pos.url}" var="posUrl" />
						<tr class="storeItem">


							<td><c:out value="${loopStatus.count}" /></td>
							<td>${pos.formattedDistance}</td>
							<td>${pos.displayName}</td>
							<td><ycommerce:testId
									code="storeFinder_result_address_label">
									<c:if test="${not empty pos.address}">
										<a href="https://maps.google.com?q=${addr}" target="_blank"
											class="text-capitalize" id="mapsearch">
											<c:if test="${not empty pos.address.line1}">
											${pos.address.line1} 
											</c:if>
											<c:if test="${not empty pos.address.line2}">												
											&nbsp;${fn:toLowerCase(pos.address.line2)},											</c:if>
											<c:if test="${not empty pos.address.postalCode}">
											${pos.address.postalCode} 
											</c:if>
									</a>
									</c:if>
								</ycommerce:testId></td>
							<%-- 
								<td><a href="https://maps.google.com?q=${addr}" target = "_blank" class="orderNo">Get Directions </a></td>
									<c:set var="count" value="${count+1}" />
									<c:set scope="session" value="${count}" var="count" /> --%>

						</tr>
						</c:when>
						<c:otherwise>
						
						<c:url value="${pos.url}" var="posUrl" />
						<tr class="storeItem">


							<%-- <td><c:out value="${loopStatus.count}" /></td> --%>
							<td>${pos.formattedDistance}</td>
							<td>${pos.displayName}</td>
							<td><ycommerce:testId
									code="storeFinder_result_address_label">
									<c:if test="${not empty pos.address}">
										<a href="https://maps.google.com?q=${addr}" target="_blank"
											class="text-capitalize" id="mapsearch"><c:if test="${not empty pos.address.line1}">
											${pos.address.line1} 
											</c:if>
											<c:if test="${not empty pos.address.line2}">												
											&nbsp;${fn:toLowerCase(pos.address.line2)},
											</c:if>
											<c:if test="${not empty pos.address.postalCode}">
											${pos.address.postalCode} 
											</c:if></a>
									</c:if>
								</ycommerce:testId></td>
								<c:set var="displayName" value="${pos.displayName}"/>
								<c:if test="${fn:containsIgnoreCase(displayName,'firestone')}">
								    <td><button class="btn btn-fmDefault storefindersearchbutton" onclick="window.open('http://www.firestonecompleteautocare.com/locate/display-map/?events=event54&navZip=${locationQuery}');"><spring:theme code="wheretobuy.schedule.appointment"/></button> </td>							   
								</c:if>  
							</tr>					
						</c:otherwise>

						</c:choose>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</c:if>


<nav:whereToBuyPagination top="false" supportShowAll="false"
	supportShowPaged="false" searchPageData="${searchPageData}"
	searchUrl="${searchUrl}" msgKey="text.storefinder.desktop.page"
	numberPagesShown="${count}" />

<script type="text/javascript">
	function getComboA(sel) {
		var userInput = document.getElementById('post').value;
		alert("userInput :::" + userInput);
		var value = sel.value;
		alert("value :::" + value);

	}
</script>
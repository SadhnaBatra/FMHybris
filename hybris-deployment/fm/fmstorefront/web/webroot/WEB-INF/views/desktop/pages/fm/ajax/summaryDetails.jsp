<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<typeHeader:headerForContentType></typeHeader:headerForContentType>
<row>
  
  <VehicleSegmentResults>
  <c:set var="vctr" value="0"/>
  <c:forEach items="${vehicleSegment}" var="vvehicleSegment">
		<VehicleSegmentResultsvalue${vctr}>${vvehicleSegment}</VehicleSegmentResultsvalue${vctr}>
		<c:set var="vctr" value="${vctr+1}"/>
  </c:forEach>
  </VehicleSegmentResults>
  <VehicleSegmentResultsSize>${vehicleSegmentSize}</VehicleSegmentResultsSize>  
  <Years>
  <c:set var="yctr" value="0"/>
  <c:forEach items="${years}" var="yyear">
		<Yearsvalue${yctr}>${yyear}</Yearsvalue${yctr}>
		<c:set var="yctr" value="${yctr+1}"/>
  </c:forEach>
  </Years>
  <YearsSize>${yearsSize}</YearsSize>
  <Make>
  <c:set var="ctr" value="0"/>
  <c:forEach items="${make}" var="make">
		<Makevalue${ctr}>${make}</Makevalue${ctr}>
		<c:set var="ctr" value="${ctr+1}"/>
  </c:forEach>
  </Make>
  <MakeSize>${makeSize}</MakeSize>
  <Models>
  <c:set var="ctr1" value="0"/>
  <c:forEach items="${models}" var="model">
  		<Modelsvalue${ctr1}>${fn:escapeXml(model)}</Modelsvalue${ctr1}>
  		<c:set var="ctr1" value="${ctr1+1}"/>
  </c:forEach>
  </Models>
  <ModelsSize>${modelsSize}</ModelsSize>
<storeLocator>
  
 <c:set value="/store-finder?q=${param.q}" var="searchUrl" />
<c:if test="${not empty geoPoint}">
	<c:set value="/store-finder?latitude=${geoPoint.latitude}&longitude=${geoPoint.longitude}&q=${param.q}" var="searchUrl" />
</c:if>
  <c:if test="${searchPageData ne null and !empty searchPageData.results}">
   <c:set var="ctr1" value="0" scope="page"/>
  <c:forEach items="${searchPageData.results}" var="pos" varStatus="loopStatus">
  <c:url value="${pos.url}" var="posUrl"/>	

							
<tr>
<%-- <td class="text-uppercase" style="padding: 10px; margin: 15;"><Modelsvalue${ctr1}>${pos.name}</Modelsvalue${ctr1}></td> --%>
<td class="text-capitalize">
<c:if test="${not empty pos.address}">
								<p class="shoppingCartBold">${pos.name}</p>
								<p>${pos.address.line1} </p>
								<p>${pos.address.line2}</p>
								<p>${pos.address.town}</p>
								<p>${pos.address.region.name}</p>
								<p>${pos.address.country.name}</p>
								<p>${pos.address.postalCode}</p>
							
						</c:if></td>
<td class="text-uppercase"> <Modelsvalue${ctr1}>${pos.formattedDistance}</Modelsvalue${ctr1}></td>

<td>	
<button type="button" data-dismiss="modal" onclick = "javascript:setstores('${pos.name}','${pos.address.line1}','${pos.address.line2}','${pos.address.town}','${pos.address.region.isocode}','${pos.address.country.isocode}', '${pos.address.postalCode}');" id="abc" class="btn btn-fmDefault shoppingCartCheckOut">Pick Up In Store</button></td>
</tr>


</c:forEach>
<c:set var="ctr1" value="${ctr1+1}"/> 		
 </c:if>
 
  </storeLocator>
<cartstorepickup>
  <div class="tab-pane active" id="defaultAcc" style="display: block;">
  	<div class="reviewFirstPanelMargin  topMargn_20">
     	<p class="reviewPlaceOrderBold text-capitalize ">Pickup From</p>
		<p class="text-uppercase">${storePickupAddress.firstName}</p>
		<p>${storePickupAddress.line1}</p>
		<p>${storePickupAddress.line2}</p>
		<p>${storePickupAddress.town}</p> 
		<p>${storePickupAddress.region.name}</p>                   
		<p>${storePickupAddress.country.name}</p>
		<p>${storePickupAddress.postalCode}</p> 
	</div>
</div>
</cartstorepickup>
  
</row>
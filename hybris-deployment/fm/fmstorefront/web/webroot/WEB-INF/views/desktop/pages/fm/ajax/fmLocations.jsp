<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>

<typeHeader:headerForContentType></typeHeader:headerForContentType>
<row> 

<fmcountries> 
	
		
		<c:forEach items="${fmcountries}" var="fmCountry">
			${fmCountry.isocode}:${fmCountry.name} | 
			
		</c:forEach>
	
</fmcountries> 

<fmstates>
 
  <c:choose>
  	<c:when test="${not empty fmStatesData}">
  		<c:forEach items="${fmStatesData}" var="fmState">${fmState.isocode}:${fmState.name} |</c:forEach>
  	</c:when>
  	<c:otherwise></c:otherwise>
  </c:choose>
  
  	
</fmstates> 


 <fmlocations>
	<c:if test="${not empty fmAddressLocations}">  
		<c:forEach items="${fmAddressLocations}" var="fmlocation"
		varStatus="status">
					<div class="col-md-4 col-sm-4 col-xs-12 text-capitalize">
						<label>${fmlocation.building}, ${fmlocation.department}</label>
						<c:if test="${not empty fmlocation.appartment}">
							<p style="margin-top: 1px;">${fmlocation.appartment}</p>
						</c:if>
						<p style="margin-top: -13px;">${fmlocation.streetname }.,  ${fmlocation.district},  ${fmlocation.region.name}</p>
						<p style="margin-top: -14px;">${fmlocation.country.name} <span style="margin-left:2px;">${fmlocation.postalcode} </span></p>
					</div>
	</c:forEach>
	</c:if>	
	
</fmlocations>

  

</row>
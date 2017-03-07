<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="typeHeader" tagdir="/WEB-INF/tags/desktop/pricing"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>


	<c:if test="${not empty fmAddressLocations}"> 
	  
		<c:forEach items="${fmAddressLocations}" var="fmlocation"
		varStatus="status">
				<c:if test="${(status.index)%3 == 0}">
					<div class="row">
				</c:if>
					<div class="col-md-4 col-sm-4 col-xs-12 text-capitalize">
						<label>${fmlocation.building}  </label>
						<c:if test="${not empty fmlocation.appartment or not empty  fmlocation.department}">
							<p style="margin-top: 1px;">  ${fmlocation.department}  <span style="margin-left:2px;"> ${fmlocation.appartment} </span></p>
						</c:if>
						<p style="margin-top: -13px;">${fmlocation.streetname }</p>
						<p style="margin-top: -13px;"> ${fmlocation.district} <span style="margin-left:2px;">  ${fmlocation.region.isocodeShort} </span> 
								 	    <span style="margin-left:2px;">${fmlocation.postalcode} </span> </p>
						<p style="margin-top: -14px;">${fmlocation.country.name}</p>
					</div>
				<c:if test="${(status.index+1)%3 == 0}">
					</div>
				</c:if>	
				
		</c:forEach>
	
	</c:if>	
	

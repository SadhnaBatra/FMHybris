<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="displayAppRefInfo"	value="false" />
<c:set var="displayFitmentYear"	value="false" />
<c:set var="displayFitmentMake"	value="false" />
<c:set var="displayFitmentModel" value="false" />
<c:if test="${not empty pageData.breadcrumbs}">
	<c:forEach items="${pageData.breadcrumbs}" var="breadcrumb">
		<c:choose>
			<c:when test="${breadcrumb.facetName == 'year' }" >
				<c:set var="displayAppRefInfo"	value="false" />
				<c:set var="displayFitmentYear"	value="${breadcrumb.facetValueName}" />
			</c:when>
			<c:when test="${breadcrumb.facetName == 'make' }" >
				<c:set var="displayAppRefInfo"	value="false" />
				<c:set var="displayFitmentMake"	value="${breadcrumb.facetValueName}" />
			</c:when>
			<c:when test="${breadcrumb.facetName == 'model' }" >
				<c:set var="displayAppRefInfo"	value="false" />
				<c:set var="displayFitmentModel" value="${breadcrumb.facetValueName}" />
			</c:when>
			<c:otherwise>
				<c:set var="displayAppRefInfo"	value="true" />
			</c:otherwise>
		</c:choose>
	 </c:forEach>
	 <c:if test="${displayFitmentYear != 'false' && displayFitmentMake != 'false' && displayFitmentModel != 'false'}" >
	  	<div class="panel panel-default">
			<div class="panel-heading acc-heading"><h4 class="panel-title"><span class="acc-title">My Vehicle</span></h4></div>
				<div class="panel-body acc-body"> 
					<div class="form-group">
						<form action="#" method="get">
							<input type="hidden" name="q" value=""/>
							<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
							<label class="facet_block-label"> 
								${displayFitmentYear} &nbsp;${displayFitmentMake }  &nbsp;${ displayFitmentModel}&nbsp;&nbsp;&nbsp;
								<a onclick="$(this).closest('form').submit()" ><span class="clearbadge">Clear</span></a>
							</label>
						</form>
					</div>
				</div>
			</div>
		</c:if>
	<c:if test="${displayAppRefInfo == 'true'}" >
	  	<div class="panel panel-default">
			<div class="panel-heading acc-heading"><h4 class="panel-title"><span class="acc-title">Applied Filters</span></h4></div>
				<div class="facet">
				<div class="facetValues">
					<ul class="facet_block">
						<c:forEach items="${pageData.breadcrumbs}" var="breadcrumb">
							<c:if test="${breadcrumb.facetName != 'year' && breadcrumb.facetName !='make' && breadcrumb.facetName !='model' && breadcrumb.facetName !='vehiclesegment' && breadcrumb.facetValueName != 'ALL'}" >	
			        			<c:set var="searchQueryAll" value="%3A${breadcrumb.facetCode}%3AALL" />
			        			<li class="remove_item_left">
			        				<div class="panel-body acc-body"> 
										<c:url value="${breadcrumb.removeQuery.url}" var="removeQueryUrl"/>
										<label class="facet_block-label"><strong>${breadcrumb.facetName}: </strong> ${breadcrumb.facetValueName} &nbsp;&nbsp;&nbsp;
											<br/><span class="remove"><a href="${fn:substringBefore(removeQueryUrl,searchQueryAll)}" ><span class="clearbadge">Clear</span></a></span>
										</label>
										<div class="clear"></div>
									</div>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</c:if>
</c:if>

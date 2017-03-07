<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>	
	<%-- <nav:ymmFitment pageData="${searchPageData}"/>  --%>


<c:if test="${not empty searchPageData.breadcrumbs}">
	<c:forEach items="${searchPageData.breadcrumbs}" var="breadcrumb">
	
		<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'year' }" >
        		<c:set var="displayFitmentYear"	value="${breadcrumb.facetValueName}" />
       		</c:if>
        	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'make' }" >
        		<c:set var="displayFitmentMake"	value="${breadcrumb.facetValueName}" />
        	</c:if>
        	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'model'}" >	
        		<c:set var="displayFitmentModel" value="${breadcrumb.facetValueName}" />
        	</c:if>

  </c:forEach>
</c:if>
<c:set var="ymmCode" value="${fn:substringBefore(displayFitmentYear,'|')}"/>


	<c:forEach items="${pageData.facets}" var="facet">
		<c:choose>
			<c:when test="${facet.code eq 'availableInStores'}">
				<nav:facetNavRefinementStoresFacet facetData="${facet}" userLocation="${userLocation}"/>
			</c:when>
			<c:when test="${fn:contains(facet.code,'Fit-') && isYMMApplied }">
				<nav:facetNavRefinementFacet facetData="${facet}" ymmCode="${ymmCode}|"/>
			</c:when>
			<c:otherwise>
			<c:if test="${not empty facet.values && !fn:contains(facet.code,'Fit-') && facet.name != 'year' && facet.name !='make' && facet.name !='model' && facet.name !='Sub Category' && facet.name !='category' && facet.name !='vehiclesegment'}" >
				<nav:facetNavRefinementFacet facetData="${facet}" ymmCode="${ymmCode}"/>
			</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>



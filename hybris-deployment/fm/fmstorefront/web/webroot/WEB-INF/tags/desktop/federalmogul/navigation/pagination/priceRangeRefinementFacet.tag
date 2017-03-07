<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>


<%-- <div class="headline"><spring:theme code="search.nav.refinements"/> sssssssss</div>

	<c:forEach items="${pageData.facets}" var="facet">
		<c:choose>
			<c:when test="${facet.code eq 'availableInStores'}">
				<nav:facetNavRefinementStoresFacet facetData="${facet}" userLocation="${userLocation}"/>
			</c:when>
			<c:otherwise>
				<nav:facetNavRefinementFacet facetData="${facet}"/>
			</c:otherwise>
		</c:choose>
	</c:forEach>
 --%>


<div class="panel panel-default">
	<div class="panel-heading acc-heading">
		<h4 class="panel-title">
			<a class="accordion-toggle  collapsed" data-toggle="collapse"
				data-parent="#accordion" href="#collapseSix"> <span
				class="acc-title">Price Range</span> <span
				class="glyphicon glyphicon-question-sign"></span></a>
		</h4>
	</div>
	<div id="collapseSix" class="panel-collapse collapse">
		<div class="panel-body acc-body priceRange">
			<p>$150 - $350</p>
			<input id="ex2" type="text" class="span2" value=""
				data-slider-min="10" data-slider-max="1000" data-slider-step="5"
				data-slider-value="[150,350]" />
			<button href="#" class="btn btn-fmDefault btn-pricerange">Refresh
				Results</button>
		</div>
	</div>
</div>


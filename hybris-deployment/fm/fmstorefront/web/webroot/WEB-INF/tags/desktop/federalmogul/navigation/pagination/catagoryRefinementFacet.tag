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
				data-parent="#accordion" href="#collapseTwo"> <span
				class="acc-title">Category</span> <span
				class="glyphicon glyphicon-question-sign"></span></a>
		</h4>
	</div>
	<div id="collapseTwo" class="panel-collapse collapse">
		<div class="panel-body acc-body">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<ul class="filter-option">
						<li><input type="checkbox" checked value=""> <label>Break
								Pads</label></li>
						<li><input type="checkbox" value=""> <label>Break
								Discs</label></li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</div>

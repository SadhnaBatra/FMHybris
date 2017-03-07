<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%-- <%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>
 --%><%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<%-- <c:if test="${not empty facetData.values}">
	<div class="facet">
		<div class="facetHead">
			<spring:theme code="text.hideFacet" var="hideFacetText"/>
			<spring:theme code="text.showFacet" var="showFacetText"/>
			<a class="refinementToggle" href="#" data-hide-facet-text="${hideFacetText}" data-show-facet-text="${showFacetText}">
				<spring:theme code="search.nav.facetTitle" arguments="${facetData.name}"/>
			</a>
		</div>

		<ycommerce:testId code="facetNav_facet${facetData.name}_links">
			<div class="facetValues">
				<c:if test="${not empty facetData.topValues}">
					<div class="topFacetValues">
						<ul class="facet_block ${facetData.multiSelect ? '' : 'indent'}">
							<c:forEach items="${facetData.topValues}" var="facetValue">
								<li>
									<c:if test="${facetData.multiSelect}">
										<form action="#" method="get">
											<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
											<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
											<label class="facet_block-label">
												<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="$(this).closest('form').submit()"/>
												${facetValue.name}
												<span class="facetValueCount"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
											</label>
										</form>
									</c:if>
									<c:if test="${not facetData.multiSelect}">
										<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
										<a href="${facetValueQueryUrl}&amp;text=${searchPageData.freeTextSearch}">${facetValue.name}</a>&nbsp;
										<span class="facetValueCount"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
									</c:if>
								</li>
							</c:forEach>
						</ul>
						<span class="more">
							<a href="#" class="moreFacetValues" ><spring:theme code="search.nav.facetShowMore_${facetData.code}" /></a>
						</span>
					</div>
				</c:if>
				<div class="allFacetValues" style="${not empty facetData.topValues ? 'display:none' : ''}">
					<ul class="facet_block ${facetData.multiSelect ? '' : 'indent'}">
						<c:forEach items="${facetData.values}" var="facetValue">
							<li>
								<c:if test="${facetData.multiSelect}">
									<form action="#" method="get">
										<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
										<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
										<label class="facet_block-label">
											<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="$(this).closest('form').submit()"/>
											${facetValue.name}&nbsp;
											<span class="facetValueCount"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
										</label>
									</form>
								</c:if>
								<c:if test="${not facetData.multiSelect}">
									<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
									<a href="${facetValueQueryUrl}">${facetValue.name}</a>
									<span class="facetValueCount"><spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>
								</c:if>
							</li>
						</c:forEach>
					</ul>
					<c:if test="${not empty facetData.topValues}">
						<span class="more">
							<a href="#" class="lessFacetValues"><spring:theme code="search.nav.facetShowLess_${facetData.code}" /></a>
						</span>
					</c:if>
				</div>
			</div>
		</ycommerce:testId>
	</div>
</c:if>
 --%>


<div class="panel panel-default">
	<div class="panel-heading acc-heading">
		<h4 class="panel-title">
			<a class="accordion-toggle" data-toggle="collapse"
				data-parent="#accordion" href="#collapseOne"> <span
				class="acc-title">More Vehicle</span> <span
				class="glyphicon glyphicon-question-sign"></span></a>
		</h4>
	</div>
	<div id="detail-1" class="">
		<div class="panel-body acc-body">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<ul class="filter-option">
						<li><input type="checkbox" checked value=""> <label>Brake
								Pads <span class="badge">28</span>
						</label></li>
						<li><input type="checkbox" value=""> <label>Brake
								Discs <span class="badge ">42</span>
						</label></li>
						<li><input type="checkbox" value=""> <label>Brake
								Rotors <span class="badge">42</span>
						</label></li>
						<li><input type="checkbox" value=""> <label>Brake
								Pads <span class="badge">42</span>
						</label></li>
						<li><input type="checkbox" value=""> <label>Linings
								<span class="badge">42</span>
						</label></li>
						<li><input type="checkbox" value=""> <label>Brake
								Pads <span class="badge">42</span>
						</label></li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</div>
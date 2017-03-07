<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>
<%@ attribute name="ymmCode" required="true" type="java.lang.String"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="showFacet" value="false"/>
<c:set var="multiSelectCount" value="0"/>
<c:if test="${not empty facetData.values}">
	<c:forEach items="${facetData.values}" var="facetValue">
				<c:if test="${facetValue.selected  }">
					<c:set var="multiSelectCount" value="${multiSelectCount + 1 }"/>
				</c:if>
	</c:forEach>
</c:if>
<%-- <c:if test="${not empty facetData.values}">
	<c:forEach items="${facetData.values}" var="facetValue">
		<c:choose>
			<c:when test="${not empty ymmCode && fn:contains(facetData.code,'Fit-')}" >
				<c:if test="${!fn:contains(facetValue.name,'ALL') }">
					<c:set var="showFacet" value="true"/>
				</c:if>
				<c:if test="${facetValue.selected && !fn:contains(facetValue.name,'ALL') }">
					<c:set var="multiSelectCount" value="${multiSelectCount + 1 }"/>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${!fn:contains(facetValue.name,'ALL') }">
					<c:set var="showFacet" value="true"/>
				</c:if>
				<c:if test="${facetValue.selected && !fn:contains(facetValue.name,'ALL') }">
					<c:set var="multiSelectCount" value="${multiSelectCount + 1 }"/>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</c:if> --%>










<c:if test="${fn:contains(facetData.code,'brand') }">
<c:if test="${not empty facetData.values }">
<div class="panel panel-default">
		<div class="panel-heading acc-heading">
			<spring:theme code="text.hideFacet" var="hideFacetText"/>
			<spring:theme code="text.showFacet" var="showFacetText"/>
			<!-- <a class="refinementToggle" href="#" data-hide-facet-text="${hideFacetText}" data-show-facet-text="${showFacetText}"></a>  -->
				<h4 class="panel-title"><a href="#${facetData.code}" data-parent="#${facetData.code}" data-toggle="collapse" class="${multiSelectCount == 0 ? "toggle" :"toggle" }" ><span class="acc-title">${facetData.name}
				 <!-- <spring:theme code="search.nav.facetTitle" arguments="${facetData.name}"/> -->
				 <span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="${facetData.name}"></span>
				</span> </a> </h4>
			  
		</div>
<ycommerce:testId code="facetNav_facet${facetData.name}_links">

<div class="${multiSelectCount == 0 ? "panel-collapse collapse in" :"panel-collapse collapse in" }" id="${facetData.code}">
		
				<div class="panel-body acc-body" style="${not empty facetData.topValues ? 'display:none' : ''}">
					<div class="form-group">
					<ul class="filter-option facet_block ${facetData.multiSelect ? '' : 'indent'}">
					
						<c:forEach items="${facetData.values}" var="facetValue">
						<c:if test="${!fn:contains(facetValue.name,'ALL') && !fn:contains(facetValue.name,'dummy') }">

                                                <c:if test="${fn:contains(facetData.code,'brand') }">

								<c:set var="searchQueryAll" value="${facetData.code}:${ymmCode}ALL" />
									<c:choose>
									<c:when test="${!fn:contains(facetData.code,'Fit-') }">
										<c:set var="searchQuery" value="${facetValue.query.query.value}"/>
									</c:when>
									<c:when test="${fn:contains(facetValue.query.query.value, searchQueryAll) && facetValue.selected }" >
										<c:set var="searchQuery" value="${fn:substringBefore(facetValue.query.query.value,searchQueryAll)}" />
									</c:when>
									<c:when test="${fn:contains(facetValue.query.query.value, searchQueryAll)}" >
										<c:set var="searchQuery" value="${facetValue.query.query.value}"/>
									</c:when>
									<c:otherwise>
										<c:set var="searchQuery" value="${facetValue.query.query.value}:${searchQueryAll}"/>
									</c:otherwise>
								</c:choose>
								<c:if test="${facetData.multiSelect}">
									<form action="#" method="get">
										<input type="hidden" name="q" value="${fn:escapeXml(searchQuery)}"/>
										<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
													<li class="facet_block-label">
													<label>
														<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="openUploadOrderModal();$(this).closest('form').submit()"/>
														<span>${facetValue.name}&nbsp;</span>
													</label>
													</li>
												
											<!-- <span class="badge">${facetValue.count}<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span> --> 	</label>									
									</form>
								</c:if>
								<c:if test="${not facetData.multiSelect}">
									<a href="${facetValueQueryUrl}"><label class="facet_block-label">${facetValue.name}</label></a>
									<!-- <span class="badge">${facetValue.count}<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span> --> 
								</c:if>
							


							</c:if>
						        </c:if>
						</c:forEach>

                                                
					</ul>
					<c:if test="${not empty facetData.topValues}">
						<span class="more">
							<a href="#" class="lessFacetValues"><spring:theme code="search.nav.facetShowLess_${facetData.code}" /></a>
						</span>
					</c:if>
					</div>
				</div>
			</div>

		</ycommerce:testId>
</c:if>
</c:if>





















<c:if test="${!fn:contains(facetData.code,'brand') }">
<c:if test="${not empty facetData.values }">
	<div class="panel panel-default">
		<div class="panel-heading acc-heading">
			<spring:theme code="text.hideFacet" var="hideFacetText"/>
			<spring:theme code="text.showFacet" var="showFacetText"/>
			<!-- <a class="refinementToggle" href="#" data-hide-facet-text="${hideFacetText}" data-show-facet-text="${showFacetText}"></a>  -->
				<h4 class="panel-title"><a href="#${facetData.code}" data-parent="#${facetData.code}" data-toggle="collapse" class="${multiSelectCount > 0 ? "toggle" :"toggle collapsed" }" ><span class="acc-title">${facetData.name}
				 <!-- <spring:theme code="search.nav.facetTitle" arguments="${facetData.name}"/> -->
				 <span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="${facetData.name}"></span>
				</span> </a> </h4>
			  
		</div>
		
		<ycommerce:testId code="facetNav_facet${facetData.name}_links">
			
			<div class="${multiSelectCount > 0 ? "panel-collapse collapse in" :"panel-collapse collapse" }" id="${facetData.code}">
				
				<div class="panel-body acc-body" style="${not empty facetData.topValues ? 'display:none' : ''}">
					<div class="form-group">
					<ul class="filter-option facet_block ${facetData.multiSelect ? '' : 'indent'}">
						<c:forEach items="${facetData.values}" var="facetValue">
							<c:if test="${!fn:contains(facetValue.name,'ALL') }">
								<c:set var="searchQueryAll" value="${facetData.code}:${ymmCode}ALL" />
									<c:choose>
									<c:when test="${!fn:contains(facetData.code,'Fit-') }">
										<c:set var="searchQuery" value="${facetValue.query.query.value}"/>
									</c:when>
									<c:when test="${fn:contains(facetValue.query.query.value, searchQueryAll) && facetValue.selected }" >
										<c:set var="searchQuery" value="${fn:substringBefore(facetValue.query.query.value,searchQueryAll)}" />
									</c:when>
									<c:when test="${fn:contains(facetValue.query.query.value, searchQueryAll)}" >
										<c:set var="searchQuery" value="${facetValue.query.query.value}"/>
									</c:when>
									<c:otherwise>
										<c:set var="searchQuery" value="${facetValue.query.query.value}:${searchQueryAll}"/>
									</c:otherwise>
								</c:choose>
								<c:if test="${facetData.multiSelect}">
									<form action="#" method="get">
										<input type="hidden" name="q" value="${fn:escapeXml(searchQuery)}"/>
										<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
													<li class="facet_block-label">
													<label>
														<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="openUploadOrderModal();$(this).closest('form').submit()"/>
														<span>${facetValue.name}&nbsp;</span>
													</label>
													</li>
												
											<!-- <span class="badge">${facetValue.count}<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span> --> 	</label>									
									</form>
								</c:if>
								<c:if test="${not facetData.multiSelect}">
									<a href="${facetValueQueryUrl}"><label class="facet_block-label">${facetValue.name}</label></a>
									<!-- <span class="badge">${facetValue.count}<spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span> --> 
								</c:if>
							
							</c:if>
						</c:forEach>
						
					</ul>
					<c:if test="${not empty facetData.topValues}">
						<span class="more">
							<a href="#" class="lessFacetValues"><spring:theme code="search.nav.facetShowLess_${facetData.code}" /></a>
						</span>
					</c:if>
					</div>
				</div>
			</div>




		</ycommerce:testId>
	</div>
</c:if>
</c:if>


<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="top" required="true" type="java.lang.Boolean" %>
<%@ attribute name="supportShowAll" required="true" type="java.lang.Boolean" %>
<%@ attribute name="searchResultType" required="false" type="java.lang.String" %>
<%@ attribute name="supportShowPaged" required="true" type="java.lang.Boolean" %>
<%@ attribute name="sortQueryParams" required="false" %>
<%@ attribute name="msgKey" required="false" %>
<%@ attribute name="numberPagesShown" required="false" type="java.lang.Integer" %>

<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/desktop/nav/pagination" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>

<%-- <c:if test="${searchPageData.pagination.totalNumberOfResults == 0 && top }">
	<div class="paginationBar top clearfix">
		<ycommerce:testId code="searchResults_productsFound_label">
			<div class="totalResults"><spring:theme code="${themeMsgKey}.totalResults" arguments="${searchPageData.pagination.totalNumberOfResults}"/></div>
		</ycommerce:testId>
	</div>
</c:if> --%>



<%-- <c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">

<div class="paginationBar ${(top)?"top":"bottom"} clearfix">
	<ycommerce:testId code="searchResults_productsFound_label">
		<div class="totalResults"><spring:theme code="${themeMsgKey}.totalResults" arguments="${searchPageData.pagination.totalNumberOfResults}"/></div>
	</ycommerce:testId>
	<c:if test="${not empty searchPageData.sorts}">
		<form id="sort_form${top ? '1' : '2'}" name="sort_form${top ? '1' : '2'}" method="get" action="#"  class="sortForm">
			<c:if test="${not empty sortQueryParams}">
				<c:forEach var="queryParam" items="${fn:split(sortQueryParams, '&')}">
					<c:set var="keyValue" value="${fn:split(queryParam, '=')}"/>
					<c:if test="${not empty keyValue[1]}">
						<input type="hidden" name="${keyValue[0]}" value="${keyValue[1]}"/>
					</c:if>
				</c:forEach>
			</c:if>
			<label for="sortOptions${top ? '1' : '2'}"><spring:theme code="${themeMsgKey}.sortTitle"/></label>
			<select id="sortOptions${top ? '1' : '2'}" name="sort" class="sortOptions">
				<c:forEach items="${searchPageData.sorts}" var="sort">
					<option value="${sort.code}" ${sort.selected ? 'selected="selected"' : ''}>
						<c:choose>
							<c:when test="${not empty sort.name}">
								${sort.name}
							</c:when>
							<c:otherwise>
								<spring:theme code="${themeMsgKey}.sort.${sort.code}"/>
							</c:otherwise>
						</c:choose>
					</option>
				</c:forEach>
			</select>
			<c:if test="${not empty searchResultType}">
				<input type="hidden" name="searchResultType" value="${searchResultType}"/>
			</c:if>
			<c:catch var="errorException">
				<spring:eval expression="searchPageData.currentQuery.query" var="dummyVar"/>This will throw an exception is it is not supported
				<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
			</c:catch>

			<c:if test="${supportShowAll}">
				<ycommerce:testId code="searchResults_showAll_link">
					<input type="hidden" name="show" value="Page"/>
				</ycommerce:testId>
			</c:if>
			<c:if test="${supportShowPaged}">
				<ycommerce:testId code="searchResults_showPage_link">
					<input type="hidden" name="show" value="All"/>
				</ycommerce:testId>
			</c:if>
		</form>
	</c:if>
	
	<c:catch var="notFacetSearchPageDataInstance">
		<spring:eval expression="searchPageData.currentQuery" var="currentQuery"/>
	</c:catch>
	
	<c:if test="${empty searchPageData.sorts && empty notFacetSearchPageDataInstance && not empty searchPageData.currentQuery }">
		<div id="sort_form1">
			<input type="hidden" name="q" value="${searchPageData.currentQuery.query.value}"/>
		</div>
	</c:if>

	
		<div class="right">
			<c:if test="${supportShowPaged}">
				<spring:url value="${searchUrl}" var="showPageUrl" htmlEscape="true">
					<spring:param name="show" value="Page" />
				</spring:url>
				<ycommerce:testId code="searchResults_showPage_link">
					<a class="showPagination" href="${showPageUrl}"><spring:theme code="${themeMsgKey}.showPageResults" /></a>
				</ycommerce:testId>
			</c:if>
			<c:if test="${paginationType eq 'pagination' && (searchPageData.pagination.numberOfPages > 1)}">
				<pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
			</c:if>
			<c:if test="${supportShowAll}">
				<spring:url value="${searchUrl}" var="showAllUrl" htmlEscape="true">
					<spring:param name="show" value="All" />
				</spring:url>
				<ycommerce:testId code="searchResults_showAll_link">
					<a class="showAll" href="${showAllUrl}"><spring:theme code="${themeMsgKey}.showAllResults" /></a>
				</ycommerce:testId>
			</c:if>
		</div>


</div>

</c:if> --%>

<c:if test="${searchPageData.pagination.totalNumberOfResults == 0 && top }">
	<div class="visible-lg visible-md visible-sm">
		<div class="col-lg-3 col-md-3 col-xs-2">
			<label class="control-label ">
				<span class="search-result-number">
					${searchPageData.pagination.totalNumberOfResults}</span> Products found.
			</label>
		</div>
	</div>
</c:if>

<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">

	<div class="visible-lg visible-md visible-sm">
		<div class="col-lg-3 col-md-3 col-xs-2">
			<label class="control-label ">
				<span class="search-result-number">
					${searchPageData.pagination.totalNumberOfResults}</span> Products found. 
			</label>
		</div>
		<div class="col-lg-4 col-md-3 col-xs-4">

			<form class="form-horizontal ">
				<div class="form-group ">

					<label
						class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block "
						for="sortby">Sort by</label>
					<div
						class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
						<select class="form-control" id="sortby">
							<option>Most Popular</option>
							<option>High Price</option>
							<option>Med Price</option>
							<option>Low Price</option>
						</select>
					</div>
				</div>
			</form>

		</div>
		<div class="col-lg-2 col-md-3 col-xs-2">
			<form class="form-horizontal">
				<div class="form-group">

					<label
						class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block"
						for="display">Display</label>
					<div
						class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
						<select class="form-control" id="display">
							<option>1</option>
							<option>2</option>
							<option>3</option>
							<option>4</option>
						</select>
					</div>
				</div>
			</form>


		</div>
		<div class="col-lg-3 col-md-3 col-xs-4 text-right ">
			<form class="form-horizontal">
				<div class="form-group">

					<label
						class="control-label visible-lg-inline visible-md-inline visible-sm-inline"
						for="page">Page</label> <input type="text"
						class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45"
						value="1" /> <label
						class="control-label visible-lg-inline visible-md-inline visible-sm-inline">
						of 5</label>
					<button type="button"
						class="glyphicon glyphicon-chevron-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"></button>
					<button type="button"
						class="glyphicon glyphicon-chevron-right pagination-next-page "></button>
				</div>
			</form>
		</div>
	</div>
	<div class="visible-xs ">
		<div class="col-lg-4 col-md-4 col-xs-2">
			<button value="Fiter" class="glyphicon glyphicon-cog"></button>
		</div>
		<div class="col-lg-4 col-md-4 col-xs-3">
			<select
				class="visible-lg-inline visible-md-inline visible-sm-inline visible-xs-inline">
				<option>Popular</option>
				<option>High Price</option>
				<option>Med Price</option>
				<option>Low Price</option>
			</select>
		</div>
		<div class="col-lg-4 col-md-4 col-xs-7 text-right">
			<button type="button" class="glyphicon glyphicon-chevron-left" ></button>
			Page 2 of 5
			<button type="button" class="glyphicon glyphicon-chevron-right" style="float:right; !important;"></button>
		</div>
	</div>


</c:if>
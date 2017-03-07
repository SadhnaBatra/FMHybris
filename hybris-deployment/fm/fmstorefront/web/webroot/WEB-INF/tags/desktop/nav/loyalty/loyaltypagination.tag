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

<%@ taglib prefix="pagination" tagdir="/WEB-INF/tags/desktop/nav/pagination/loyalty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%-- <c:set var="themeMsgKey" value="${not empty msgKey ? msgKey : 'search.page'}"/>



<c:if test="${searchPageData.pagination.totalNumberOfResults == 0 && top }">

	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-2">
			<label class="control-label searchResultText">
			<span
				class="search-result-number">${searchPageData.pagination.totalNumberOfResults}</span>
				Products found. </label>
		</div>
		
	
</c:if>

<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
<section class="">
            <div class="visible-lg visible-md visible-sm clearfix">
	
		<div class="col-lg-2 col-md-3 col-sm-3 col-xs-2 width175 ">
			<label class="control-label searchResultText">
			<span
				class="search-result-number">${searchPageData.pagination.totalNumberOfResults}</span>
				Products found. </label>
		</div>
		<div class="col-lg-4 col-md-3 col-sm-3 col-xs-4 topMargn_3 lftPad_0">
			<c:if test="${not empty searchPageData.sorts}">
				<form id="sort_form${top ? '1' : '2'}"
					name="sort_form${top ? '1' : '2'}" method="get" action="#"
					class="form-horizontal ">
					<c:if test="${not empty sortQueryParams}">
						<c:forEach var="queryParam"
							items="${fn:split(sortQueryParams, '&')}">
							<c:set var="keyValue" value="${fn:split(queryParam, '=')}" />
							<c:if test="${not empty keyValue[1]}">
								<input type="hidden" name="${keyValue[0]}"
									value="${keyValue[1]}" />
							</c:if>
						</c:forEach>
					</c:if>
					<label
						class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block "
						for="sortby"  >Sort by</label>
					<div class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block" >
							 <select
								id="sortOptions${top ? '1' : '2'}" name="sort"
								class="form-control" >
								<c:forEach items="${searchPageData.sorts}" var="sort">
									<option value="${sort.code}"
										${sort.selected ? 'selected="selected"' : ''}" >
										<c:choose>
											<c:when test="${not empty sort.name}">
										${sort.name}
									</c:when>
											<c:otherwise>
												${sort.code}
											</c:otherwise>
										</c:choose>
									</option>
								</c:forEach>
							</select>
					</div>	
					<c:if test="${not empty searchResultType}">
						<input type="hidden" name="searchResultType"
							value="${searchResultType}" />
					</c:if>
					
					<c:catch var="errorException">
						<spring:eval expression="searchPageData.currentQuery.query"
							var="dummyVar" />
						<!-- This will throw an exception is it is not supported -->
						<input type="hidden" name="q"
							value="${searchPageData.currentQuery.query.value}" />
					</c:catch>

					<c:if test="${not empty searchResultType}">
						<input type="hidden" name="searchResultType"
							value="${searchResultType}" />
					</c:if>

					<c:if test="${supportShowPaged}">
						<ycommerce:testId code="searchResults_showPage_link">
							<input type="hidden" name="show" value="All" />
						</ycommerce:testId>
					</c:if>

				</form>
			</c:if>

		</div>
		<div class="col-lg-1 col-md-2 col-sm-2 col-xs-2 lftPad_0 width105 ">
			<form class="form-horizontal">
				<div class="form-group">
				<c:set var="paginationDisplayDrop" value="FMDisplay" scope="session" />
				<c:set var="paginationArrowNav" value="" scope="session" />
					<pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
				</div>
			</form>


		</div>
		<div class="col-lg-4 col-md-3 col-sm-2 col-xs-3  text-right PaginationNav lftPad_0">
			 <form class="form-horizontal">
				<div class="form-group">
					<c:if test="${paginationType eq 'pagination' && (searchPageData.pagination.numberOfPages > 1)}">
					<c:set var="paginationArrowNav" value="arrownavigation" scope="session" />
					<c:set var="paginationDisplayDrop" value="" scope="session" />
						<pagination:pageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
					</c:if>
				</div>
			</form> 
		
		</div>
	
	

</div>
		</section>
</c:if> --%>

<c:if test="${searchPageData.pagination.totalNumberOfResults == 0 && top }">

	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-2">
			<label class="control-label searchResultText">
			<span
				class="search-result-number">${searchPageData.pagination.totalNumberOfResults}</span>
				Products found. </label>
		</div>
		
	
</c:if>


<c:if test="${searchPageData.pagination.totalNumberOfResults > 0}">
	<div class="visible-lg visible-md visible-sm">
		<div class="col-lg-3 col-md-3 col-xs-2">
			<label class="control-label searchResultText"><span
				class="search-result-number">${searchPageData.pagination.totalNumberOfResults}</span>
				Products found. </label>
		</div>
		<div class="col-lg-4 col-md-3 col-xs-4">
			<c:if test="${not empty searchPageData.sorts}">
				<form id="sort_form${top ? '1' : '2'}"
					name="sort_form${top ? '1' : '2'}" method="get" action="#"
					class="form-horizontal ">
					<c:if test="${not empty sortQueryParams}">
						<c:forEach var="queryParam"
							items="${fn:split(sortQueryParams, '&')}">
							<c:set var="keyValue" value="${fn:split(queryParam, '=')}" />
							<c:if test="${not empty keyValue[1]}">
								<input type="hidden" name="${keyValue[0]}"
									value="${keyValue[1]}" />
							</c:if>
						</c:forEach>
					</c:if>
					<label
						class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block "
						for="sortby"  >Sort by</label>
					<div class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block" >
							 <select
								id="sortOptions${top ? '1' : '2'}" name="sort"
								class="form-control" >
								<c:forEach items="${searchPageData.sorts}" var="sort">
									<option value="${sort.code}"
										${sort.selected ? 'selected="selected"' : ''}" >
										<c:choose>
											<c:when test="${not empty sort.name}">
										${sort.name}
									</c:when>
											<c:otherwise>
												${sort.code}
											</c:otherwise>
										</c:choose>
									</option>
								</c:forEach>
							</select>
					</div>	
					<c:if test="${not empty searchResultType}">
						<input type="hidden" name="searchResultType"
							value="${searchResultType}" />
					</c:if>
					
					<c:catch var="errorException">
						<spring:eval expression="searchPageData.currentQuery.query"
							var="dummyVar" />
						<!-- This will throw an exception is it is not supported -->
						<input type="hidden" name="q"
							value="${searchPageData.currentQuery.query.value}" />
					</c:catch>

					<c:if test="${not empty searchResultType}">
						<input type="hidden" name="searchResultType"
							value="${searchResultType}" />
					</c:if>

					<c:if test="${supportShowPaged}">
						<ycommerce:testId code="searchResults_showPage_link">
							<input type="hidden" name="show" value="All" />
						</ycommerce:testId>
					</c:if>

				</form>
			</c:if>
		</div>
		<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
			<form class="form-horizontal">
				<div class="form-group">
				<c:set var="paginationDisplayDrop" value="loyaltyDisplay" scope="session" />
				<c:set var="paginationArrowNav" value="" scope="session" />
					<pagination:loyaltypageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
				</div>
			</form>


		</div>
		<div class="col-lg-3 col-md-3 col-sm-2 col-xs-3 text-right PaginationNav">
			 <form class="form-horizontal">
				<div class="form-group">
					<c:if test="${paginationType eq 'pagination' && (searchPageData.pagination.numberOfPages > 1)}">
					<c:set var="paginationArrowNav" value="arrownavigation" scope="session" />
					<c:set var="paginationDisplayDrop" value="" scope="session" />
						<pagination:loyaltypageSelectionPagination searchUrl="${searchUrl}" searchPageData="${searchPageData}" numberPagesShown="${numberPagesShown}" themeMsgKey="${themeMsgKey}"/>
					</c:if>
				</div>
			</form> 
		
		</div>
	</div>
	
</c:if>

<script>

function sortPage(){
	//alert("Sort URL :: "+)
	 //document.location.href = url.value;
}

</script> 


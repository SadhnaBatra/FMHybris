<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="searchUrl" required="true" %>
<%@ attribute name="searchPageData" required="true" type="de.hybris.platform.commerceservices.search.pagedata.SearchPageData" %>
<%@ attribute name="numberPagesShown" required="true" type="java.lang.Integer" %>
<%@ attribute name="themeMsgKey" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>


<!-- My Working copy -->
<c:if test="${paginationDisplayDrop == 'FMDisplay'}">
	<c:set var="limit" value="${numberPagesShown}"/>
	<c:set var="halfLimit"><fmt:formatNumber value="${limit/2}" maxFractionDigits="0"/></c:set>
	<c:set var="beginPage">
		<c:choose>
			<c:when test="${limit gt searchPageData.pagination.numberOfPages}">1</c:when>
			<c:when test="${searchPageData.pagination.currentPage + halfLimit ge limit}">
				<c:choose>
					<c:when test="${searchPageData.pagination.currentPage + halfLimit lt searchPageData.pagination.numberOfPages}">
						${searchPageData.pagination.currentPage + 1 - halfLimit}
					</c:when>
					<c:otherwise>${searchPageData.pagination.numberOfPages + 1 - limit}</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>1</c:otherwise>
		</c:choose>
	</c:set>
	<c:set var="endPage">
		<c:choose>
			<c:when test="${limit gt searchPageData.pagination.numberOfPages}">
				${searchPageData.pagination.numberOfPages}
			</c:when>
			<c:when test="${hasNextPage}">
				${beginPage + limit - 1}
			</c:when>
			<c:otherwise>
				${searchPageData.pagination.numberOfPages}
			</c:otherwise>
		</c:choose>
	</c:set>
		
   <c:if test="${searchPageData.pagination.totalNumberOfResults > 10 }">
	<label
		class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block"
		for="display">Display&nbsp;</label>
	<div class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
		
	<select class="form-control" id="display" onchange="javascript:goToPageProductCount(this);" >
	<c:forEach begin="10" end="20" var="pageNumber" step="5">

			<spring:url value="${searchUrl}" var="pageNumberUrl" htmlEscape="true">
					<spring:param name="prodCount" value="${pageNumber}"/>
			</spring:url>
			 <c:choose>
				<c:when test="${ProductCountPerPage ne pageNumber}">
					<option  value="${pageNumberUrl}" >
						<c:choose>
								
							<c:when test="${searchPageData.pagination.currentPage + 1 ne pageNumber}">
							<ycommerce:testId code="pageNumber_link">
										${pageNumber}
								</ycommerce:testId>
							</c:when>
							<c:otherwise>
								<b>${pageNumber}</b>
							</c:otherwise>
						</c:choose>
					</option>
				</c:when>
				<c:otherwise>
				<option  value="${pageNumberUrl}" selected >
					<c:choose>
						<c:when test="${searchPageData.pagination.currentPage + 1 ne pageNumber}">
							<ycommerce:testId code="pageNumber_link">
									${pageNumber}
							</ycommerce:testId>
						</c:when>
						<c:otherwise>
							<b>${pageNumber}</b>
						</c:otherwise>
					</c:choose>
				</option>
			</c:otherwise>
			</c:choose>	 
		</c:forEach>
	</select>
	</div>
   </c:if>
</c:if>
<input type="hidden" id="productsCount" value="${ProductCountPerPage }"/>
 <c:if test="${paginationArrowNav == 'arrownavigation'}">	
	<c:set var="hasPreviousPage"
		value="${searchPageData.pagination.currentPage > 0}" />
	<c:set var="hasNextPage"
		value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}" />
	
	<c:set var="limit" value="${numberPagesShown}" />
	<c:set var="halfLimit">
		<fmt:formatNumber value="${limit/2}" maxFractionDigits="0" />
	</c:set>
		<spring:url value="${searchUrl}" var="pageNumbersUrl" htmlEscape="true"/>
	
	<label
		class="control-label visible-lg-inline visible-md-inline visible-sm-inline"
		for="page" >Page</label>
	<input type="text"
		class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45"
		value="${(searchPageData.pagination.currentPage + 1)}" 
		 onchange="javascript:goToPageNumbers('${pageNumbersUrl}','${ProductCountPerPage }',this,'change');"
		 onkeypress="javascript:goToPageNumbers('${pageNumbersUrl}','${ProductCountPerPage }',this,event);"
		 
		 />
	<label
		class="control-label visible-lg-inline visible-md-inline visible-sm-inline"
		>of ${searchPageData.pagination.numberOfPages }</label>
	
	<c:if test="${hasPreviousPage}">
		<spring:url value="${searchUrl}" var="previousPageUrl"
			htmlEscape="true">
			<spring:param name="page"
				value="${searchPageData.pagination.currentPage - 1}" />
		</spring:url>
		<%-- <a href="${previousPageUrl}" rel="prev">
			<button type="button"
				class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"></button>
		</a> --%>
			<button type="button"
				class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"
				 onclick="javascript:goToPageNumber(this);" value="${previousPageUrl}"
				></button>		
	</c:if>
	
	<c:if test="${hasNextPage}">
		<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
			<spring:param name="page"
				value="${searchPageData.pagination.currentPage + 1}" />
		</spring:url>
	
		<%-- <a href="${nextPageUrl}" onClick="javascript:goToPageNumber(this);" rel="next">
			<button type="button" class="fa fa-angle-right pagination-next-page " ></button>
		</a> --%>
		<button type="button" class="fa fa-angle-right pagination-next-page " onclick="javascript:goToPageNumber(this);" value="${nextPageUrl}"></button>
	</c:if>
</c:if>	

<script>

function goToPageProductCount(url){
	
	 document.location.href = url.value;
	 
}

function goToPageNumber(url){
	
	var newUrl = url.value;
	var productsCount = document.getElementById("productsCount").value;
	document.location.href = url.value+"&prodCount="+productsCount;
}
function goToPageNumbers(url,prodCount,obj,evt){
	
	if(evt != 'change'){
    	evt = (evt) ? evt : window.event;
    	var charCode = (evt.which) ? evt.which : evt.keyCode;
   		if(charCode != 13){
    	 		return false;
    		}else{
    			evt.preventDefault();
    		}
	}
	var pageNumber = obj.value;
		if(pageNumber >= 1){
			var pages= parseInt(pageNumber) - 1
			document.location.href = url+"&page="+pages +"&prodCount="+prodCount;
			
		}
}


</script> 


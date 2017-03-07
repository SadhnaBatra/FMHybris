
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="breadcrumbs" required="true" type="java.util.List"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<c:set value="/content/loc-na/loc-us/fmmp-corporate/en_US.html" var="homeUrl" />

<ul class="breadcrumb">
	<li>
		<a href="${homeUrl}"><span class="fa fa-home"></span>&nbsp;<spring:theme code="breadcrumb.home" /></a>
	</li>
     <c:set var="count" value="0" />
	<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
		<c:set var="category" value="${breadcrumb.name}" ></c:set>

		


		
	<%-- 	<c:if test="${!fn:contains(breadcrumb.name,'-')}"> --%>
          	
          		<c:if test="${not empty category}">
			<span class="fa fa-angle-right "></span>
			</c:if>

			
			<li
				<c:if test="${not empty breadcrumb.linkClass}">class="${breadcrumb.linkClass}"</c:if>>

				<c:choose>
					<c:when test="${breadcrumb.url eq '#'}">
						<a href="${breadcrumb.url}" onclick="return false;"
							<c:if test="${status.last}">class="last"</c:if>>${breadcrumb.name}</a>

					</c:when>
					<c:when test="${breadcrumb.url eq '/my-fmaccount'}">
						<a href="${breadcrumbUrl}" onclick="return true;"
							<c:if test="${status.last}" >class="selected"</c:if>><strong>${breadcrumb.name}</strong></a>
					</c:when>					
					

					<c:otherwise>
						<c:url value="${breadcrumb.url}" var="breadcrumbUrl" />
						<c:if test="${not empty category}">
						<strong itemprop="name"> <a href="${breadcrumbUrl}"
							onclick="return true;"
							<c:if test="${status.last}" >class="selected"</c:if>><strong>${breadcrumb.name}</strong></a>
						</strong>
						</c:if>

					</c:otherwise>

				</c:choose>
				<c:set var="count" value="${count + 1 }" />
			</li>
<%-- 		</c:if> --%>

	
	</c:forEach>
</ul>




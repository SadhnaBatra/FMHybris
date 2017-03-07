<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/shared/component" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:choose>
	<c:when test="${component.popup}">
		<div class="internalPage rightHandPanel clearfix">
			<div class="clearfix">
				<div class="loyaltyslider4 carousel">
				 <c:forEach items="${productData}" var="product">
		 	 		<c:set var="actualProductUrl" value="${product.url}"/>
					<c:set var="convertedLoyaltyProductUrl" value="${fn:replace(actualProductUrl, '/p/', '/lp/')}" />
					<c:url value="${convertedLoyaltyProductUrl}" var="productQuickViewUrl"/>
					<div class="slide">
						<a href="${productQuickViewUrl}" class="thumbnail">
							<product:productPrimaryImage product="${product}" format="product"/>
						</a>
						<div class="caption">
						<a href="${productQuickViewUrl}" class="">
							<p class="title text-capitalize"> ${product.name}</p></a>
							<p>
								Item#: <span class="itemNum">${product.code}</span>
							</p>
							<p>${product.summary}</p>
						</div>
					</div>
				</c:forEach>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
	<div class="internalPage rightHandPanel clearfix">
			<div class="clearfix">
				<div class="loyaltyslider4 carousel">
				 <c:forEach items="${productData}" var="product">
		 	 		<c:set var="actualProductUrl" value="${product.url}"/>
					<c:set var="convertedLoyaltyProductUrl" value="${fn:replace(actualProductUrl, '/p/', '/lp/')}" />
					<c:url value="${convertedLoyaltyProductUrl}" var="productQuickViewUrl"/>
					<div class="slide">
						<a href="${productQuickViewUrl}" class="thumbnail">
							<product:productPrimaryImage product="${product}" format="product"/>
						</a>
						<div class="caption">
						   <a href="${productQuickViewUrl}" class="">
							<p class="title text-capitalize">${product.name}</p>
						   </a>
							<p>
								Item#: <span class="itemNum">${product.code}</span>
							</p>
							<p>${product.summary}</p>
						</div>
					</div>
				</c:forEach>
				</div>
			</div>
		</div>
	
	</c:otherwise>
</c:choose>


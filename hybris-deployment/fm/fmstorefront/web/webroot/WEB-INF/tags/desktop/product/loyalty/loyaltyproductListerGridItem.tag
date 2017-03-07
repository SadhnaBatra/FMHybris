<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product/loyalty" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="federalmogul" uri="http://federalmogul.com/tld/federalmogultags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="fitment" tagdir="/WEB-INF/tags/desktop/federalmogul/product" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:set var="string1" value="${product.url}"/>
<c:set var="productlink" value="${fn:replace(string1, 
                                '/p/', '/lp/')}" />

	

<c:url value="${productlink}" var="productUrl"/>


<spring:theme code="text.addToCart" var="addToCartText"/>


<div class="col-lg-4 col-sm-4 col-md-4 col-sm-12">
	<div class="thumbnail">
		<a href="${productUrl}" title="${product.name}">
			<product:productPrimaryImage product="${product}" format="product" />
		</a>	
		<div class="caption">
			<h4>
				<a href="${productUrl}">${product.name}</a>
			</h4>
			<span>Item:&nbsp;${product.code}</span>
			<div class="clearfix topMargn_20" id="hideAddedtoCart-1">
				<span class="price-text-color"><fmt:formatNumber type="number" value="${product.loyaltyPoints}" /> Points</span> 
				<a href="${productUrl}" class="btn btn-fmDefault">View</a>
			</div>
		</div>
	</div>
</div>

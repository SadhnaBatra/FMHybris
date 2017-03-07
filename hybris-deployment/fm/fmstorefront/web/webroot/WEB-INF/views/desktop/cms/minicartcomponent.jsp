<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<c:url value="/cart/miniCart/${totalDisplay}" var="refreshMiniCartUrl"/>
<c:url value="/cart/rollover/${component.uid}" var="rolloverPopupUrl"/>
<c:url value="/cart" var="cartUrl"/>

<sec:authorize ifNotGranted="ROLE_FMB2T">
<a href="${cartUrl}" class="minicart"><span class="fa fa-shopping-cart"></span>
	Shopping Cart
		<span class="count">(${itemCount})</span>	
</a>
<div id="miniCartLayer" class="miniCartPopup" data-refreshMiniCartUrl="${refreshMiniCartUrl}/?"  data-rolloverPopupUrl="${rolloverPopupUrl}" ></div>
</sec:authorize>
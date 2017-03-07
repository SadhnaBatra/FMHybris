<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="price" required="true" type="java.lang.Double" %>
<%@ attribute name="displayFreeForZero" required="false" type="java.lang.Boolean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
 Tag to render a currency formatted price.
 Includes the currency symbol for the specific currency.
--%>
<c:choose>
	<c:when test="${price > 0}">
		$<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${price}" />
	</c:when>
	<c:otherwise>
		<spring:theme code="text.free" text="N/A"/>
	</c:otherwise>
</c:choose>

<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="fitment" required="true"
	type="com.federalmogul.facades.product.data.FMFitmentData"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="statusIndex" required="true" type="java.lang.Integer" %>
<%@ attribute name="fitmentCount" required="true" type="java.lang.Integer" %>
<%@ taglib prefix="federalmogul" uri="http://federalmogul.com/tld/federalmogultags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
		
		<c:if test="${not empty federalmogul:allFitmentCriteria(fitment) && federalmogul:allFitmentCriteria(fitment) ne ' ' }">
			<c:set value="${federalmogul:allFitmentCriteria(fitment)}" var="FinalfitmentCriteria" />
					 <c:if test="${not empty FinalfitmentCriteria && statusIndex eq 0}">
					<p class="prodNotes"><spring:theme code="ProductSearchresultspage.additionalFitment.description"/></p>
				</c:if>
		</c:if>
		
		
		




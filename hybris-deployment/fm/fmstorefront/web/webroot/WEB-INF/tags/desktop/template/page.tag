<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true" %>
<%@ attribute name="pageCss" required="false" fragment="true" %>
<%@ attribute name="pageScripts" required="false" fragment="true" %>
<%@ attribute name="hideHeaderLinks" required="false" %>
<%@ attribute name="displayIFrame" required="false" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header" %>
<%@ taglib prefix="footer" tagdir="/WEB-INF/tags/desktop/common/footer" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<template:master pageTitle="${pageTitle}">

	<jsp:attribute name="pageCss">
		<jsp:invoke fragment="pageCss"/>
	</jsp:attribute>
 
	<jsp:attribute name="pageScripts">
		<jsp:invoke fragment="pageScripts"/>
	</jsp:attribute>

	<jsp:body>
		<%-- <div id="page" data-currency-iso-code="${currentCurrency.isocode}"> --%>
		<c:if test="${!displayIFrame}">
			<header:header hideHeaderLinks="${hideHeaderLinks}"/>
			<nav:topNavigation/>
		</c:if>
			<div id="content" class="clearfix ${displayIFrame ? 'iframed' : ''}">
				<jsp:doBody/>
			</div>
		<c:if test="${!displayIFrame}">
			<footer:footer/>
		</c:if>

	</jsp:body>
	
</template:master>

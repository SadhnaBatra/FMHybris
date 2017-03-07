<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="g" uri="http://granule.com/tags/accelerator" %>
<%@ taglib prefix="compressible" tagdir="/WEB-INF/tags/desktop/template/compressible" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/desktop/template/cms" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>

<c:url value="/" var="siteRootUrl"/>

<c:choose>
	<c:when test="${ themeResourcePath == '/fmstorefront/_ui/desktop/theme-fmmp'}">
		
		<template:javaScriptVariables/>

		<script type="text/javascript" src="${themeResourcePath}/dist/app.min.js"></script>

		<%-- AddOn JavaScript files --%>
		<c:forEach items="${addOnJavaScriptPaths}" var="addOnJavaScript">
		    <script type="text/javascript" src="${addOnJavaScript}"></script>
		</c:forEach>

	</c:when>
	<c:otherwise>

		<c:choose>
			<c:when test="${granuleEnabled}">
				<g:compress urlpattern="${encodingAttributes}">
					<compressible:js/>
				</g:compress>
			</c:when>
			<c:otherwise>
				<compressible:js/>
			</c:otherwise>
		</c:choose>

	</c:otherwise>
</c:choose>

<cms:previewJS cmsPageRequestContextData="${cmsPageRequestContextData}" />
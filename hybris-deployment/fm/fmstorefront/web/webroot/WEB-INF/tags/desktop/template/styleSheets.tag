<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="g" uri="http://granule.com/tags/accelerator"%>
<%@ taglib prefix="compressible" tagdir="/WEB-INF/tags/desktop/template/compressible" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/desktop/template/cms" %>


<c:choose>
	<c:when test="${ themeResourcePath == '/fmstorefront/_ui/desktop/theme-fmmp'}">

		<link type="text/css" rel="stylesheet" href="${themeResourcePath}/dist/vendor.min.css" media="all" />
		<link type="text/css" rel="stylesheet" href="${themeResourcePath}/dist/styles-hybris.min.css" media="all" />

		<%--  AddOn Common CSS files --%>
		<c:forEach items="${addOnCommonCssPaths}" var="addOnCommonCss">
			<link rel="stylesheet" type="text/css" media="all" href="${addOnCommonCss}"/>
		</c:forEach>

		<%--  AddOn Theme CSS files --%>
		<c:forEach items="${addOnThemeCssPaths}" var="addOnThemeCss">
			<link rel="stylesheet" type="text/css" media="all" href="${addOnThemeCss}"/>
		</c:forEach>

	</c:when>
	<c:otherwise>

		<c:choose>
			<c:when test="${granuleEnabled}">
				<g:compress urlpattern="${encodingAttributes}">
					<compressible:css/>
				</g:compress>
			</c:when>
			<c:otherwise>
				<compressible:css/>
			</c:otherwise>
		</c:choose>


		<%-- <link rel="stylesheet" href="${commonResourcePath}/blueprint/print.css" type="text/css" media="print" /> --%>
		<style type="text/css" media="print">
			@IMPORT url("${commonResourcePath}/blueprint/print.css");
		</style>

	</c:otherwise>
</c:choose>

<!--[if lt IE 9]> <link type="text/css" rel="stylesheet" href="${themeResourcePath}/css/ie8.css" media="screen, projection" /> <![endif]-->
<!--[if IE 7]> <link type="text/css" rel="stylesheet" href="${themeResourcePath}/css/ie7.css" media="screen, projection" /> <![endif]-->

<cms:previewCSS cmsPageRequestContextData="${cmsPageRequestContextData}" />

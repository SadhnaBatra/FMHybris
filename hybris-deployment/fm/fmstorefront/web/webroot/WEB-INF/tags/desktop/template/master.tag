<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true" %>
<%@ attribute name="metaDescription" required="false" %>
<%@ attribute name="metaKeywords" required="false" %>
<%@ attribute name="pageCss" required="false" fragment="true" %>
<%@ attribute name="pageScripts" required="false" fragment="true" %>
<%@taglib prefix="googletagmanager" tagdir="/WEB-INF/tags/addons/googletagmanager/shared"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="analytics" tagdir="/WEB-INF/tags/shared/analytics" %>
<%@ taglib prefix="debug" tagdir="/WEB-INF/tags/shared/debug" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="${currentLanguage.isocode}">
<head>
	<title>
		${not empty pageTitle ? pageTitle : not empty cmsPage.title ? cmsPage.title : 'Accelerator Title'}
	</title>

	<%-- Meta Content --%>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" >
	<meta http-equiv="X-UA-Compatible" content="IE=10">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="google-site-verification" content="HSE9xQSMIUVn03oSTAsib84ldybIqOB9VbbIynOuIuk" />

	<!----Google Analytics---->
	<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		ga('create', 'UA-60369248-14', 'auto');
		ga('send', 'pageview');
	</script>
	<!----End of Google Analytics---->

	<c:if test="${targetURL ne null }">
		<meta property="og:url" name="twitter:domain" content="${targetURL}" />
	</c:if>
	<c:if test="${targetURL eq null }">
		<meta property="og:url" name="twitter:domain" content="http://www.fmmotorparts.com/fmstorefront/" />
	</c:if>
    <meta property="og:type" content="website" />
    <c:set var="pageTitles" value="${fn:split(pageTitle, '|')}" />
	<c:if test="${not empty pageTitles[0]}">									
		<c:set var="pageTit" value="${fn:escapeXml(pageTitles[0])}" />
	</c:if>

    <meta property="og:title" name="twitter:title" content="${pageTit}"/>    
    <meta property="fb:admins" content="816387998436106" />	
	<meta property="og:locale" content="en_US"/>
	<meta name="twitter:card" content="summary"/>
	<meta name="twitter:creator" content="@fmmotorparts" />
	<meta name="twitter:site" content="@fmmotorparts"/>		
	<meta property="og:site_name" content="Federal-Mogul Motorparts" />
	<c:forEach items="${galleryImagesForSocialMedia}" var="container" varStatus="varStatus">
		<c:if test="${varStatus.index  == 0 }">
			<meta property="og:image" name="twitter:image" content="${container.product.url}" />
			<meta property="twitter:image" content="${container.product.url}" />
		</c:if>
	</c:forEach>
	
	<meta property="twitter:account_id" content="3068678404" />
	<meta property="fb:app_id" content="505087279678542" />
	<meta property="og:locale" content="en_US" />
	<%-- Additional meta tags --%>	
	<c:forEach var="metatag" items="${metatags}">
		<c:if test="${not empty metatag.content}" >
			<c:if test="${metatag.name == 'description' }">
				<meta name="twitter:description" property="og:${metatag.name}" name="description" content="${metatag.content}" />
				<meta name="twitter:description" property="${metatag.name}" content="${metatag.content}" />
			</c:if>
			<c:if test="${metatag.name != 'description' }">
				<meta property="${metatag.name}" content="${metatag.content}" />
			</c:if>
		</c:if>
	</c:forEach>
	
	<%-- Favourite Icon --%>
	<spring:theme code="img.favIcon" text="/" var="favIconPath"/>
    <link rel="shortcut icon" type="image/x-icon" media="all" href="${originalContextPath}${favIconPath}" />

	<%-- CSS Files Are Loaded First as they can be downloaded in parallel --%>
	<template:styleSheets/>

	<%-- Inject any additional CSS required by the page --%>
	<jsp:invoke fragment="pageCss"/>
	
	<c:if test="${!empty googleApiVersion}">
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=${googleApiVersion}&amp;key=${googleApiKey}&amp;sensor=false"></script>
	</c:if>

	<script type="text/javascript" src="/fmstorefront/_ui/desktop/theme-fmmp/dist/header.min.js"></script>
 	<%-- 
 		HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries
    	WARNING: Respond.js doesn't work if you view the page via file://
    --%>
    <!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script> 
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script> 
    <![endif]-->
</head>

<body class="${pageBodyCssClasses} ${cmsPageRequestContextData.liveEdit ? ' yCmsLiveEdit' : ''} language-${currentLanguage.isocode}">

	<!-- Google Tag Manager -->
	<noscript><iframe src="//www.googletagmanager.com/ns.html?id=GTM-MV86DW"
	height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
	<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
	new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
	j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
	'//www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
	})(window,document,'script','dataLayer','GTM-MV86DW');</script>
	<!-- End Google Tag Manager -->

	<script>
		window.dataLayer = window.dataLayer || [];
		dataLayer.push({'event':'dataLayer�initialized', 'page�type':'${pageType}'});
	</script>


	<googletagmanager:googleTagManager/>

  	<%-- Inject the page body here --%>
	 <jsp:doBody/> 

	<form name="accessiblityForm">
		<input type="hidden" id="accesibility_refreshScreenReaderBufferField" name="accesibility_refreshScreenReaderBufferField" value=""/>
	</form>
	<div id="ariaStatusMsg" class="skip" role="status" aria-relevant="text" aria-live="polite"></div>

	<%-- Load JavaScript required by the site --%>
	<template:javaScript/>
	
	<%-- Inject any additional JavaScript required by the page --%>
	<jsp:invoke fragment="pageScripts"/>	
</body>
</html>
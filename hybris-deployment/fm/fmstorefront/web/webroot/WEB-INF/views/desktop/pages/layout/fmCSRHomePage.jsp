 <%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>

<template:page pageTitle="${pageTitle}" displayIFrame="${displayIFrame}">

	<div id="globalMessages">
		<common:globalMessages />
	</div>

	<section>
			
	<!--Emulate Account Section  -->
		<section class="B2B emulateAccount">
			<div class="container">
				<div class="row">
					<federalmogul:EmulateAccount />
				</div>
			</div>
		</section>
	</section>
	<c:if test="${!displayIFrame}">	
	<!-- Brandstrip -->
		<div class="well well-sm well-brandstrip clearfix">
			<ul class="nav ">
				<c:set var="fmComponentName" value="brandStrip" scope="session" />
	
				<cms:pageSlot position="FMBrandstrip" var="feature">
					<cms:component component="${feature}" />
				</cms:pageSlot>
			</ul>
		</div>
	</c:if>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>



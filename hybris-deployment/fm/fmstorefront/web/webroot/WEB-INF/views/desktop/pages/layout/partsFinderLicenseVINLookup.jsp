<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>

<template:page pageTitle="${pageTitle}">

	<c:if test="${!loginError}">
		<div id="globalMessages">
			<common:globalMessages />
		</div>

	</c:if>
	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
				</ul>
			</div>
		</div>
	</section>




	<!-- InstanceEndEditable -->

	<!-- MAIN CONTAINER-->
	<!-- InstanceBeginEditable name="Page Content Section" -->
	<section class="productDetailPage pageContet registrationContent">
		<div class="container">
			<div class="row">
						<federalmogul:partsFinderLinks />
						<federalmogul:partsFinderLicenseVINLookup></federalmogul:partsFinderLicenseVINLookup> 
			</div>
		</div>
	</section>


	<!-- Brandstrip -->

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>



</template:page>


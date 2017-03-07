<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<!-- Custom tag files for federalmogul -->
<%@ taglib prefix="fmproduct" tagdir="/WEB-INF/tags/desktop/fmproduct"%>

<template:page pageTitle="${pageTitle}">


	<c:if test="${not empty message}">
		<spring:theme code="${message}" />
	</c:if>

	<%-- <div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div> --%>

	<div class="visible-lg visible-md row">
		<!-- InstanceBeginEditable name="Breadcrumb" -->
		<div class="breadcrumbPanel clearfix">
			<div class="col-lg-10 col-lg-offset-1">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
				</ul>
			</div>
		</div>
	</div>

	<div id="globalMessages">
		<common:globalMessages />
	</div>

	<%-- <cms:pageSlot position="Section1" var="comp" element="div" class="span-24 section1 cms_disp-img_slot">
		<cms:component component="${comp}"/>
	</cms:pageSlot>

	<product:productDetailsPanel product="${product}" galleryImages="${galleryImages}"/>
		
	<cms:pageSlot position="CrossSelling" var="comp" element="div" class="crossselling">
		<cms:component component="${comp}"/>
	</cms:pageSlot>

	<cms:pageSlot position="Section3" var="feature" element="div" class="span-20 section3 cms_disp-img_slot">
		<cms:component component="${feature}"/>
	</cms:pageSlot>

	<cms:pageSlot position="UpSelling" var="comp" element="div" class="">
		<cms:component component="${comp}"/>
	</cms:pageSlot>
	
	<product:productPageTabs />

	<cms:pageSlot position="Section4" var="feature" element="div" class="span-24 section4 cms_disp-img_slot">
		<cms:component component="${feature}"/>
	</cms:pageSlot> --%>

	<div class="row">
		<div class="productDetailPage pageContet clearfix">
			<div class="col-lg-10 col-lg-offset-1">

				<div class="col-lg-12 clearfix ">
					<ul class="printShareSaveLink pull-right">
						<li><a href="#"><span class="glyphicon glyphicon-share"></span>
								Share</a></li>
						<li><a href="#"><span class="glyphicon glyphicon-print"></span>
								Print</a></li>
						<li><a href="#"><span
								class="glyphicon glyphicon-floppy-disk"></span> Save</a></li>
					</ul>
				</div>

				
				  
				<div class="container-fluid bgwhite productDetailPageContent">

					<%-- <product:productDetailsPanel product="${product}" galleryImages="${galleryImages}"/> --%>
					
					<fmproduct:fmProductDetailsPanel product="${product}" galleryImages="${galleryImages}"/>				

					<%-- <cms:pageSlot position="FMProductSpecificationSection" var="comp">
						<cms:component component="${comp}" />
					</cms:pageSlot> --%>
					 <product:productPageTabs />
					<%--<fmproduct:fmProductPageTabs/> --%>

				</div>
		</div>
	</div>
</div>	
		<!-- Brandstrip -->
				<div class="row">
					<div class="well well-sm well-brandstrip clearfix">
						<ul class="nav ">
							<c:set var="fmComponentName" value="brandStrip" scope="session" />

							<cms:pageSlot position="FMBrandstrip" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>

						</ul>
					</div>
				</div>
</template:page>

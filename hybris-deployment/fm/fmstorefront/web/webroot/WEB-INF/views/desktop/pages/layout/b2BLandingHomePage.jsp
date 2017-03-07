 <%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>

<template:page pageTitle="${pageTitle}" displayIFrame="${displayIFrame}">

	<div id="globalMessages">
		<common:globalMessages />
	</div>

	<section>
		<!-- Quick Order, Order Upload and Orders in Progress Section -->
		<section class="B2B orderPanelsBlock">
			<div class="container">
				<div class="row">
						
					<!-- Account Details -->
					
					<federalmogul:B2BAccountDetails />
					<!--- Quick Order PANEL -->
					<federalmogul:B2BquickOrder />
					<!--- Upload Order PANEL -->
					<federalmogul:B2BorderUpload />
					
				</div>
			</div>
		</section>


		<!-- Parts finder Tabs, Announcement and Quick links Section -->
		<section class="B2B lookupSignupBg">
			<div class="container">
				<div class="row">
					<!-- PartSearch panel -->
					
					<div class="col-lg-6 ">
						<federalmogul:partSearch />
					</div>
					
					<!-- Announcement panel -->
					<div class="col-lg-3">
						<div class="panel panel-default panelAnnouncement event transparentGradient">
							<c:set var="fmComponentName" value="announcement" scope="session" />
							<cms:pageSlot position="B2Bannouncement" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
					
					<!-- QuickLinks panel -->
					<c:set var="fmComponentName" value="B2BquickLinks" scope="session" />
			        <cms:pageSlot position="B2BquickLinks" var="feature">
						<cms:component component="${feature}" />
					</cms:pageSlot>
				</div>
			</div>
		</section>
	

		<!-- Insight Section -->
	<c:if test="${!displayIFrame}">
		<section class="B2B insight">
			<div class="container">
				<div class="row">
					<div class="insightPanel col-lg-12">
						<div class="panel-heading clearfix insightPanelTitle">
							<h3 class="panel-title pull-left">
								<strong class="text-uppercase"><spring:theme code="B2BHomepage.insights"/></strong>
							</h3>
							<span class="pull-right viewall text-capitalize">
							<cms:pageSlot position="B2BInsightViewMore" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>
								&nbsp;<span
									class="fa fa-angle-right viewmorearrow"></span></span>
						</div>
						<c:set var="fmComponentName" value="insight" scope="session" />
						<div class=" ">
							<div class="row">
								<cms:pageSlot position="B2BLearningTip" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>

								<cms:pageSlot position="B2BLearningArticle" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>

								<cms:pageSlot position="B2BLearningVideos1" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>
								<cms:pageSlot position="B2BLearningVideos2" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>


		<!-- SpotLite panel -->
		<section class="brandingFlyr">
			<div class="container-fluid">
				<div class="row">

					<div id="carousel-example-generic" class="carousel slide"
						data-ride="carousel">
						<cms:pageSlot position="B2BSpotLight" var="feature">
							<c:set var="fmComponentName" value="spotLite" scope="session" />
							<cms:component component="${feature}" />
						</cms:pageSlot>
					</div>
				</div>
			</div>

		</section>

		<!-- Password updated successfuly alert message -->
		<section class="alertContainer">
			<div class="container">
				<div class="clearfix">
					<div
						class="alert alert-success col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<button type="button" class="close">
							<span class="fa fa-close"></span>
						</button>
						<span class="fa fa-check"></span> <strong><spring:theme code="B2BHomepage.orderUpload.status"/></strong>
					</div>
				</div>
			</div>
		</section>
	</section>

	<!-- Brandstrip -->

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</c:if>
	<federalmogul:B2BAddNewAddressPopUp/>
</template:page>



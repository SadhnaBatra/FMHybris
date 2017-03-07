
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>


<template:page pageTitle="${pageTitle}">

	<div id="globalMessages">
		<common:globalMessages />
	</div>


	<section>
		<section class="lookupSignupBg">
			<div class="container">
				<div class="row">
					<div class="col-lg-6 ">

					<federalmogul:partSearch />
					</div>
					
					<federalmogul:AccountDetailsB2b/>
					<federalmogul:rewardsB2b />

				</div>
			</div>
		</section>

		<!-- Message Anounce Offer panel -->


		<section class="quality">
			<div class="container">
				<div class="row">
					<div class="text-center car">
						
						<h1 class="quality qualityTitle text-uppercase">
							<strong>QUALITY, SAFETY, AND PERFOMANCE</strong>
						</h1>
						<h2 class="qualitySubtitle">That's the Federal-Mogul
							advantage.</h2>
					</div>
					<div class="col-md-12 qualityCarousel">

						<!-- <div class="carousel slide" data-ride="carousel"
							id="quote-carousel"> -->
							<c:set var="fmComponentName" value="msgAnounceOffer"
								scope="session" />
							<cms:pageSlot position="MsgAnnounceOfferB2b" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>

						<!-- </div> -->
					</div>
				</div>
			</div>
		</section>

		<!-- Emulate Account Section -->
		<%-- <section class="B2B emulateAccount">
			<div class="container">
				<div class="row">
					<federalmogul:EmulateAccount />
				</div>
			</div>
		</section>
 --%>

		<!-- Learning panel -->
			<div class="learning clearfix">
				<div class="col-lg-10 col-lg-offset-1">
					<div class="panel learningPanel">
						<div class="panel-heading clearfix">
							<h3 class="panel-title pull-left">
								<strong>TRAINING</strong>
							</h3>
							<span class="pull-right"><a href="#"> View all ></a></span>
							<c:set var="fmComponentName" value="learning" scope="session" />
						</div>
						<div class="panel-body">
							<div class="row">
								<cms:pageSlot position="LearningTipB2b" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>

								<cms:pageSlot position="LearningArticleB2b" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>

								<cms:pageSlot position="LearningVideo1B2b" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>

								<cms:pageSlot position="LearningArticle2B2b" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>

							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- SpotLite panel -->
			<div class="brandingFlyr clearfix">
				<div id="carousel-example-generic" class="carousel slide"
					data-ride="carousel">
					<c:set var="fmComponentName" value="spotLite" scope="session" />
					<cms:pageSlot position="SpotLightB2b" var="feature">
						<cms:component component="${feature}" />
					</cms:pageSlot>
				</div>
			</div>

		<section class="SmallB2b technicianQuicklinksBg">
			<div class="container">
				<div class="row">
					<federalmogul:techforumB2b />
					<federalmogul:quicklinksB2b />
				</div>
			</div>
			
		</section>
	</section>


		<!-- Brandstrip -->
	<!-- 	<div class="row"> -->
	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
		</div>
	<!-- 	</div> -->
	
		<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
	
</template:page>



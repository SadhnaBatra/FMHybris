<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>
<template:page pageTitle="${pageTitle}" displayIFrame="${displayIFrame}">

	<c:if test="${loginError}">
	<%--<div id="globalMessages">
		<common:globalMessages/>
	</div>--%>
	</c:if>
	 
	
<section>
		<section class="lookupSignupBg">
			<div class="container">
				<div class="row">
					<div class="col-lg-6 home-pane">
						<!-- PartSearch panel -->
						<federalmogul:partSearch />
					</div>
					<!-- TroubleShoot panel -->
					<div class="col-lg-3 home-pane">
          				<div class="panel panel-default panelTroubleShoot event">
							<cms:pageSlot position="anonymousTroubleshoot" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
					<!-- Signin Registration panel -->
					<div class="col-lg-3 home-pane">
						<div class="signupAndRegisterPanel">
							<federalmogul:signin displayIFrame="${displayIFrame}"/>
						</div>
					</div>
				</div>
			</div>
		</section>
	<c:if test="${!displayIFrame}">
		<!-- Brandstrip -->
	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>


		<section class="whereToBuyBlock">
			<federalmogul:wheretobuy/>
		</section>	
		<!-- Message Anounce Offer panel -->
				<section class="quality">
			<div class="container">
				<div class="row">
					<!-- <div class="text-center car">
						
						<h1 class="quality qualityTitle text-uppercase">
							<strong>QUALITY, SAFETY, AND PERFORMANCE</strong>
						</h1>
						<h2 class="qualitySubtitle">That's the Federal-Mogul
							advantage.</h2>
					</div> -->
					<c:set var="fmComponentName" value="msgAnounceOfferParagraph"
								scope="session" />
							<cms:pageSlot position="anonymousMsgAnnounceOffer" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
					<div class="col-md-12 qualityCarousel">

						<!-- <div class="carousel slide" data-ride="carousel"
							id="quote-carousel"> -->
							<c:set var="fmComponentName" value="msgAnounceOffer"
								scope="session" />
							<cms:pageSlot position="anonymousMsgAnnounceOffer" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>

						<!-- </div> -->
					</div>
				</div>
			</div>
		</section>
		<!-- SpotLite panel -->
		<section class="brandingFlyr">
			<!-- <div class="container-fluid"> -->
				<div class="clearfix">
					<div id="carousel-example-generic" class="carousel slide"
						data-ride="carousel">
						<cms:pageSlot position="anonymousSpotLight" var="feature">
							<cms:component component="${feature}" />
						</cms:pageSlot>
					</div>
				</div>
			<!-- </div> -->
		</section>
		<!-- Learning panel -->
		<section class="learning">
			<div class="container">
				<div class="row">
					<div class="learningPanel col-lg-12">
						<div class="panel-heading clearfix learningPanelTitle">
							<h3 class="panel-title pull-left">
								<strong class="text-uppercase"><spring:theme code="anonymousHome.learning" text="Learning " /></strong>
							</h3>
							<span class="pull-right viewall text-capitalize">
							<cms:pageSlot position="LearningViewMore" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot> &nbsp;<span
									class="fa fa-angle-right viewmorearrow"></span></span>
						</div>
						<div class="">
							<div class="row">
								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">
										<c:set var="fmComponentName" value="learning" scope="session" />
										<cms:pageSlot position="anonymousLearningTip" var="feature">
											<cms:component component="${feature}" />
										</cms:pageSlot>
									</div>
								</div>
								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">
										<c:set var="fmComponentName" value="learningarticle" scope="session" />
										<cms:pageSlot position="anonymousLearningArticle" var="feature">
											<cms:component component="${feature}" />
										</cms:pageSlot>
									</div>
								</div>
								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">
										<c:set var="fmComponentName" value="learningvideos1" scope="session" />							<cms:pageSlot position="anonymousLearningVideos1" var="feature">
											<cms:component component="${feature}" />
										</cms:pageSlot>
									</div>
								</div>
								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">

										<c:set var="fmComponentName" value="learningvideos2" scope="session" />
											<cms:pageSlot position="anonymousLearningVideos2" var="feature">
											<cms:component component="${feature}" />
										</cms:pageSlot>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</c:if>
	</section>
</template:page>

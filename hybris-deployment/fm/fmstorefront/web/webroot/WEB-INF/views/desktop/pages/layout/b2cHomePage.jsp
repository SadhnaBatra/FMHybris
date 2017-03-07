<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>

<template:page pageTitle="${pageTitle}">

	
	<c:if test="${!loginError}">
	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	</c:if>
<section>
		<section class="lookupSignupBg">
			<div class="container">
				<div class="row">
					<div class="col-lg-6 b2c-home-pane">
						<!-- PartSearch panel -->
						<federalmogul:partSearch />
					</div>
					
					
					<!-- TroubleShoot panel -->
					<div class="col-lg-3 b2c-home-pane">
						<div class="panel panel-default panelTroubleShoot event">
							<c:set var="fmComponentName" value="troubleShoot"
								scope="session" />
							<cms:pageSlot position="B2CTroubleshoot" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>

					<div class="col-lg-3 b2c-home-pane">
						<div class="panel panel-default panelTroubleShoot event">				
								<c:set var="fmComponentName" value="success"
								scope="session" />
							<cms:pageSlot position="B2CLoginAndRegister" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
			</div>
		</section>
	<c:if test="${!displayIFrame}">
		<section class="whereToBuyBlock">
			<federalmogul:wheretobuy/>
		</section>

		<section class="quality">
			<div class="container">
				<div class="row">
					<div class="text-center car">
						<h1 class="quality qualityTitle text-uppercase">
							<strong>Quality, Safety, and performance</strong>
						</h1>
						<h2 class="qualitySubtitle">That's the Federal-Mogul
							advantage.</h2>
					</div>
					<div class="col-md-12 qualityCarousel">
						<div class="carousel slide" data-ride="carousel" id="quote-carousel">
							<cms:pageSlot position="B2CMsgAnnounceOffer" var="feature">
								<c:set var="fmComponentName" value="msgAnounceOffer"
									scope="session" />
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
			</div>
			</section>

			<section class="brandingFlyr">
				<div class="container-fluid">
					<div class="row">
						<div id="carousel-example-generic" class="carousel slide"
							data-ride="carousel">
							<cms:pageSlot position="B2CSpotLight" var="feature">
								<c:set var="fmComponentName" value="spotLite"
									scope="session" />
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
				</section>

		<section class="learning">
			<div class="container">
				<div class="row">
					<div class="learningPanel col-lg-12">
						<div class="panel-heading clearfix learningPanelTitle">
							<h3 class="panel-title pull-left">
								<span class="text-uppercase">Learning</span>
							</h3>
							<span class="pull-right viewall text-capitalize"><a
								href="#"> View more <span
									class="fa fa-angle-right viewmorearrow"></span></a></span>
						</div>
						<c:set var="fmComponentName" value="b2clearning" scope="session" />
						<div class="">
							<div class="row">
								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">
										<p>
											<cms:pageSlot position="B2CLearningTip" var="feature">
												<cms:component component="${feature}" />
											</cms:pageSlot>
										</p>
										<div class="caption">
											<div class="blur"></div>
											<div class="caption-text">
												<h5>
													New Parts From MOOG<sup>&reg;</sup> Steering and Suspension
												</h5>
												<p class="lms_desc">Federal-Mogul Motorparts&reg;
													industry-leading MOOG&reg; Steering and Suspension brand
													has introduced a complete strut assembly</p>

												<div class="lms_btm_link">

													<span class="pull-left  text-capitalize"> <a
														href="#"> Read more <span class="fa fa-angle-right "></span></a>
													</span> <span class="pull-right  text-capitalize"><a
														href="#"><span class="fa fa-share-alt"> </span></a></span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">
										<p>
											<cms:pageSlot position="B2CLearningArticle" var="feature">
												<cms:component component="${feature}" />
											</cms:pageSlot>

										</p>
										<div class="caption">
											<div class="blur"></div>
											<div class="caption-text">
												<h5>U.S Light Vehicle Sales Trending Up Again</h5>
												<p class="lms_desc">Lorem ipsum dolor sit amet,
													consectetur adipiscing elit, sed do eiusmod tempor
													incididunt ut labore et dolore magna aliqua.</p>

												<div class="lms_btm_link">

													<span class="pull-left  text-capitalize"><a href="#">
															Read more <span class="fa fa-angle-right "></span>
													</a></span> <span class="pull-right  text-capitalize"><a
														href="#"><span class="fa fa-share-alt"> </span></a></span>
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">
										<p>
											<cms:pageSlot position="B2CLearningVideos1" var="feature">
												<cms:component component="${feature}" />
											</cms:pageSlot>
										</p>
										<div class="caption">
											<div class="blur"></div>
											<div class="caption-text">
												<h5>
													<span class="fa fa-youtube-play"></span> VIDEO: Head Bolt
													Varieties and Installation Prep
												</h5>
												<p class="lms_desc">Lorem ipsum dolor sit amet,
													consectetur adipiscing elit, sed do eiusmod tempor
													incididunt ut labore et dolore magna aliqua. y</p>

												<div class="lms_btm_link">

													<span class="pull-left  text-capitalize"><a href="#">
															Play <span class="fa fa-angle-right "></span>
													</a></span> <span class="pull-right  text-capitalize"><a
														href="#"><span class="fa fa-share-alt"> </span></a></span>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class=" col-sm-3 col-md-3 col-xs-6">
									<div class="lms_intro_hover ">
										<p>
											<cms:pageSlot position="B2CLearningVideos2" var="feature">
												<cms:component component="${feature}" />
											</cms:pageSlot>
										</p>
										<div class="caption">
											<div class="blur"></div>
											<div class="caption-text">
												<h5>
													Easier Oil Pan Installtion Using Sanp-Ups<sup>&reg;</sup>
												</h5>
												<p class="lms_desc">Lorem ipsum dolor sit amet,
													consectetur adipiscing elit, sed do eiusmod tempor
													incididunt ut labore et dolore magna aliqua.</p>

												<div class="lms_btm_link">

													<span class="pull-left  text-capitalize"><a href="#">
															Read More <span class="fa fa-angle-right "></span>
													</a></span> <span class="pull-right  text-capitalize"><a
														href="#"><span class="fa fa-share-alt"> </span></a></span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</section>

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

<script type="text/javascript">
$(document).ready(function(){
  $('.slider4').bxSlider({
    slideWidth: 500,
    minSlides: 1,
    maxSlides: 2,
    moveSlides: 1,
    slideMargin: 30,
	nextText: '<span class="fa fa-chevron-right"></span>',
	prevText: '<span class="fa fa-chevron-left"></span>'
  });
});
</script> 

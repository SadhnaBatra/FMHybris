
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>



<template:page pageTitle="${pageTitle}">

	<%-- <div id="globalMessages">
		<common:globalMessages />
	</div>
 --%>

	<section>
		<section class="lookupSignupBg">
			<div class="container">
				<div class="row">
					<div class="col-lg-6 home-page">
						<federalmogul:partSearch />
					</div>

					<div class="col-lg-3 home-pane">
						<div class="panel panel-default freeMobileApp event">
							<c:set var="fmComponentName" value="downloadB2b" scope="session" />
							<cms:pageSlot position="DownloadB2T" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>


					<%-- 	<federalmogul:AccountDetailsB2b/> --%>
					<federalmogul:rewardsB2b />

				</div>
			</div>
		</section>

		<!-- Brandstrip -->
		
	<c:if test="${!displayIFrame}">
		<section class="whereToBuyBlock">
			<federalmogul:wheretobuy />
		</section>

		<!-- Message Anounce Offer panel -->
		<section class="quality">
			<div class="container">
				<div class="row">
					<div class="col-md-12 qualityCarousel">
						<c:set var="fmComponentName" value="msgAnounceOfferParagraph"
							scope="session" />
						<cms:pageSlot position="MsgAnnounceOfferB2T" var="feature">
							<cms:component component="${feature}" />
						</cms:pageSlot>
						<div class="col-md-12 qualityCarousel">
							<c:set var="fmComponentName" value="msgAnounceOffer"
								scope="session" />
							<cms:pageSlot position="MsgAnnounceOfferB2T" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
						</div>
					</div>
				</div>
			</div>
		</section>

		<!-- SpotLite panel -->
		<div id="carousel-example-generic" class="carousel slide"
			data-ride="carousel">
			<cms:pageSlot position="SpotLightB2b" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</div>
			

		<section class="brandingFlyr">
			<div class="clearfix">
		 	<!--- Technician Forum Post PANEL -->
			<c:set var="fmComponentName" value="spotLite" scope="session" />
                <div id="carousel-example-generic" class="carousel slide"   data-ride="carousel">
					<cms:pageSlot position="TechnicianForumPostB2T" var="feature">
						<cms:component component="${feature}" />
					</cms:pageSlot>
		        </div>
			</div>
		</section>		
				



		<section class="B2B insight">
			<div class="container">
				<div class="row">
					<div class="insightPanel col-lg-12">
						<div class="panel-heading clearfix insightPanelTitle">
							<h3 class="panel-title pull-left">
								<strong class="text-uppercase">INSIGHTS</strong>
							</h3>
							<span class="pull-right viewall text-capitalize"> <a
								title="View More"
								href="/fmstorefront/federalmogul/en/USD/online-tools/overview">View
									More</a> &nbsp;<span class="fa fa-angle-right viewmorearrow"></span></span>
						</div>
						<div class=" ">
							<div class="row">
								<c:set var="fmComponentName" value="b2tlearning" scope="session" />
								<cms:pageSlot position="QuickLinksB2T" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>
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



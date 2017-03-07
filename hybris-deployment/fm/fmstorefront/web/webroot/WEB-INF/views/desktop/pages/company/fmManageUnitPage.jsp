<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">

<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
  <div class="container">
    <div class="row">
      <ul class="breadcrumb">
        <breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
      </ul>
    </div>
  </div>
</section>


	<div id="globalMessages">
		<common:globalMessages />
	</div>
	
	<section class="accountDetailPage pageContet" >
  <div class="container manageAccount">
    <div class="row"> 
				 <!-- Starts: Manage Account Left hand side panel -->
				<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
				
					<federalmogul:myNetwork/>
					
					<!-- Announcement panel -->
					<%-- <div class="">
						<div class="panel panel-default panelAnnouncement event">
							<div class="panel-body">
								<c:set var="fmComponentName" value="announcement"
									scope="session" />
								<cms:pageSlot position="Announcement1" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>
							</div>
						</div>
					</div> 
				
					<c:set var="fmComponentName" value="insight" scope="session" />
					<div class="panel panel-default messagingPanel clearfix">

						<div class="lms_intro_hover ">
							<p>
								<cms:pageSlot position="Announcement2" var="feature">
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
										industry-leading MOOG&reg; Steering and Suspension brand has
										introduced a complete strut assembly</p>
									<div class="lms_btm_link">
										<span class="pull-left  text-capitalize"><a href="#">
												Read more <span class="fa fa-angle-right "></span>
										</a></span> <span class="pull-right  text-capitalize"><a href="#"><span
												class="fa fa-share-alt"> </span></a></span>
									</div>
								</div>
							</div>
						</div>
					</div>--%>

				
					
					<div class="panel panel-default messagingPanel clearfix">
						<div class="lms_intro_hover ">
							<p>
							<%-- <c:set var="fmComponentName" value="announcement"
									scope="session" /> --%>
								<cms:pageSlot position="Announcement1" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>
							</p>
							<div class="caption">
								<div class="blur"></div>
								<div class="caption-text">
									<h5>Year Over Year Wiper Blade Sales Shows Increase</h5>
									<div class="downloadLink"><a href="#"> Download [232 Kb] <span class="fa fa-angle-right "></span></a></div>
									<p class="lms_desc">Lorem ipsum dolor sit amet, consectetur
										adipiscing elit, sed do eiusmod tempor incididunt ut labore et
										dolore magna aliqua.</p>
									<div class="lms_btm_link">
										<span class="pull-left  text-capitalize"><a href="#">
												Read More <span class="fa fa-angle-right "></span>
										</a></span> <span class="pull-right  text-capitalize"><a href="#"><span
												class="fa fa-share-alt"> </span></a></span>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="panel panel-default messagingPanel clearfix">
						<div class="lms_intro_hover ">
							<p>
								<cms:pageSlot position="Announcement2" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>
							</p>
							<div class="caption">
								<div class="blur"></div>
								<div class="caption-text">
									<h5 ><span class="fa fa-youtube-play"></span> VIDEO: Vehicles - Change in Miles Driven</h5>
									<div class="downloadLink"><a href="#"> Download [358 Kb] <span class="fa fa-angle-right "></span></a></div>
									<p class="lms_desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
									<div class="lms_btm_link"> <span class="pull-left  text-capitalize"><a href="#"> Play <span class="fa fa-angle-right "></span></a></span> <span class="pull-right  text-capitalize"><a href="#"><span class="fa fa-share-alt"> </span></a></span></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- </div> -->
				
				<federalmogul:manageUnit />
				</div>
			</div>
		
		
	</section>

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<c:set var="fmComponentName" value="brandStripB2B" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>
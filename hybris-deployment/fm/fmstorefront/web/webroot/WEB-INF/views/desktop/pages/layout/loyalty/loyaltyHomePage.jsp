<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<template:page pageTitle="${pageTitle}">

<style>
 
.loyalty-banner{
	color: white;
	position: relative;
}
.loyalty-banner	.loyaltyBannerContent {
	padding-left: 20px;
	padding-left: 2rem;
	padding-top: 40px;
	padding-top: 4rem;
	position: absolute;
	top: 0%;
	width: 100%;
}
</style>

	<section class="rewardsHomePage contentPage">
			<div class="container">
				<div class="row">
				    <div class="left-nav col-md-3 col-sm-12 bggrey">
						<c:set var="fmComponentName" value="leftnavgaragereward" scope="session" />
						<cms:pageSlot position="FMAnonymousGarageRewardsLeftNavSection" var="feature">
							<cms:component component="${feature}" />
						</cms:pageSlot>
					</div>
					<div class="col-md-9 col-sm-12 bgwhite">
					    <div class="col-md-9 col-sm-12 loyalty-banner">
					        <c:set var="fmComponentName" value="loyaltyBanner" scope="session" />
						    <cms:pageSlot position="HomeRewardsBannerSection" var="feature">
							<cms:component component="${feature}" />
						    </cms:pageSlot>
					    </div>
					    <div class="col-md-3 panel panel-default panel-body">
                            <c:set var="fmComponentName" value="Reward" scope="session" />
              		        <cms:pageSlot position="HomeMyRewardsSection" var="feature">
						        <cms:component component="${feature}" />
						    </cms:pageSlot>
                            <div class="rewardsPoints"><i class="fa fa-wrench"></i><strong><fmt:formatNumber type="number" value="${TotalPoints}" /><sub>pts</sub></strong></div>
                            <form role="form" class="form-horizontal rewardsPanelForm" id="rewardsform">
                            <div class="controls clearfix" style="text-align:center"> 
							    <a class="btn  btn-sm btn-fmDefault pull-rightn text-uppercase" href="${contextPath}/${currentsiteUID}/${currentLanguage.isocode}/${currentCurrency.isocode}/lsearch?q=:name-asc:&text=#" id="btn-login">redeem</a>
							</div>
                            <c:url value="loyalty/history" var="loyalHistoryUrl" />
                            <div class="viewall text-center btmMrgn_30">
							    <a class="text-capitalize" href="${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/loyalty/history?clear=true&site=federalmogul">view points history <span class="fa fa-angle-right fm_fntRed"></span></a>
							</div>      
                        </div>
					  
						<div class="row topMargn_20 btmMrgn_20">
      	                    <div class="col-md-9 col-sm-2 contentRHS">
                                <div class="pull-left rghtMrgn_20 rewardsCarousel">
                                    <h3 class="text-uppercase"><spring:theme code="text.EarnPoints.LatestGear"/></h3>
                                </div>
                            <div class="pull-right">
                                <form  class="form-horizontal" role="form">
                                <div class="controls clearfix topMargn_5"> 
		                            <a href="${contextPath}/${currentsiteUID}/${currentLanguage.isocode}/${currentCurrency.isocode}/lsearch?q=:name-asc:&text=#"  
                                       class="btn  btn-sm btn-fmDefault text-uppercase"><spring:theme code="text.EarnPoints.viewallitems"/>
									</a>
				                </div>
                                </form>
                            </div>
                        </div>
				  
				        <div class="col-md-12 col-sm-12">
				            <div class="col-md-9 col-sm-9 panel">
						        <c:set var="fmComponentName" value="HomeCarousel" scope="session" />
							    <cms:pageSlot position="HomeRewardsLatestProductsSection" var="feature">
								    <cms:component component="${feature}" />
							    </cms:pageSlot>		
                                <c:set var="fmComponentName" value="FAQ" scope="session" />
								<cms:pageSlot position="HomeRewardsFAQsSection" var="feature">
									<cms:component component="${feature}" />
								</cms:pageSlot>								
				            </div>	
							<div class="col-md-3 col-sm-3">
							    <div class="panel manageAccountLinks">
								    <c:set var="fmComponentName" value="learn" scope="session" />
								    <cms:pageSlot position="HomeRewardseLearnSection" var="feature">
									    <cms:component component="${feature}" />
								    </cms:pageSlot>
							    </div>
				            </div>	
				        </div>	
					</div>
				</div>
		    </div>	
	 </section>
		
					<!--- SIGN IN AND REGISTER PANEL -->	
	<div class="well well-sm well-brandstrip clearfix ">
		<ul class="nav">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />
			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
</template:page>
		

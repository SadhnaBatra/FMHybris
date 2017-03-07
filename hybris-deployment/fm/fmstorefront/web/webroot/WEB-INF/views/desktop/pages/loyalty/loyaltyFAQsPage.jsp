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
	
<section class="rewardsHomePage contentPage">
	<section class="wayToPoints accountDetailPage pageContet">
		<div class="container">
			<div class="row">
				<div class="col-md-3 bggrey">
					<c:set var="fmComponentName" value="leftnavgaragereward" scope="session" />
					<cms:pageSlot position="FMAnonymousGarageRewardsLeftNavSection" var="feature">
						<cms:component component="${feature}" />
					</cms:pageSlot>
				</div>
				<div class="col-md-9 bgwhite">
					<div class="col-md-9 loyalty-banner lftPad_0">
						<c:set var="fmComponentName" value="loyaltyBanner" scope="session" />
						<cms:pageSlot position="HomeRewardsBannerSection" var="feature">
							<cms:component component="${feature}" />
						</cms:pageSlot>
					</div>
                    <div class="rewadsMyclassPanel redeemRewardsInfo"> 
                        <div class="col-md-3 rewardsPanel">
                            <h1 class="panel-title"><span class="fa fa-user"></span><span class=" text-uppercase">&nbsp;Garage rewards</span></h1>
                            <div class="rewardsPoints"><i class="fa fa-wrench"></i><strong><fmt:formatNumber type="number" value="${TotalPoints}" /><sub>pts</sub></strong></div>
                            <div class="text-center">
                                <a class="text-capitalize addNewAddLink" href="${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/loyalty/history?clear=true&site=federalmogul">View Points History <span class="linkarow fa fa-angle-right "></span></a>
						    </div> 
                        </div>
                    </div>
					<div class="col-md-12 panel rewardsFAQandRulesScroll">
						<c:set var="fmComponentName" value="FAQ" scope="session" />
							<cms:pageSlot position="HomeRewardsFAQsSection" var="feature">
								<cms:component component="${feature}" />
						</cms:pageSlot>								
				    </div>
			    </div>
			</div>
        </div>			
	</section>
	
	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<!-- <c:set var="fmComponentName" value="brandStripB2B" scope="session" />-->
			<c:set var="fmComponentName" value="brandStrip" scope="session" />
			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
</section>
</template:page>
		

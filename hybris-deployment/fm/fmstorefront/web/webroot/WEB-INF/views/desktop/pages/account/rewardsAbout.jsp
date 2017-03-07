<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<template:page pageTitle="${pageTitle}">

<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
    <div class="container">
        <div class="row">
            <ul class="breadcrumb">
                <breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
            </ul>
        </div>
    </div>
</section>

<section class="rewardsAboutPage contentPage"> 
    <div class="container">
        <div class="row">
            <div class="left-nav col-md-3 col-sm-12">
                <c:set var="fmComponentName" value="leftnavgaragereward" scope="session" />
				<sec:authorize ifAnyGranted="ROLE_FMB2T">
				  <cms:pageSlot position="GarageRewardsLeftNavSection" var="feature">
                    <cms:component component="${feature}" />
                  </cms:pageSlot>
				</sec:authorize>
				<sec:authorize ifNotGranted="ROLE_FMB2T">
                  <cms:pageSlot position="FMAnonymousGarageRewardsLeftNavSection" var="feature">
                    <cms:component component="${feature}" />
                  </cms:pageSlot>
				</sec:authorize>  
            </div>
            <div class="rewards-content col-md-9 col-sm-12">
                <div class="hero rewards-banner">
                    <c:set var="fmComponentName" value="rewardbanner" scope="session" />
                    <cms:pageSlot position="FMBannerSection" var="feature">
                        <cms:component component="${feature}" />
                    </cms:pageSlot>
                </div>   
                <div class="rewards-program">
                    <c:set var="fmComponentName" value="program" scope="session" />
                    <cms:pageSlot position="FMProgramSection" var="feature">
                        <cms:component component="${feature}" />
                    </cms:pageSlot>
                </div>
                <div class="rewards-earnpoints-banner rewards-banner">
                    <c:set var="fmComponentName" value="rewardbanner" scope="session" />
                    <cms:pageSlot position="FMEarnPointsSection" var="feature">
                        <cms:component component="${feature}" />
                    </cms:pageSlot>
                </div>
                <div class="rewards-earnpoints-how">
                    <c:set var="fmComponentName" value="learnpara" scope="session" />
                    <cms:pageSlot position="FMPointsSection" var="feature">
                        <cms:component component="${feature}" />
                    </cms:pageSlot>         
                </div>
                <div class="rewards-benefits rewards-tout">
                    <c:set var="fmComponentName" value="rewardbanner" scope="session" />
                    <cms:pageSlot position="FMProgramBenefitsSection" var="feature">
                        <cms:component component="${feature}" />
                    </cms:pageSlot>
                </div> 
				<sec:authorize ifAnyGranted="ROLE_FMB2T">
                <div class="col-sm-6 rewards-hover-tout rules">
                    <c:set var="fmComponentName" value="benefits" scope="session" />
                    <cms:pageSlot position="FMAnonymousRulesSection" var="feature">
                        <cms:component component="${feature}" />
                    </cms:pageSlot>
                </div>
                <div class="col-sm-6 rewards-hover-tout faq"> 
                    <c:set var="fmComponentName" value="benefits" scope="session" />
                    <cms:pageSlot position="FMAnonymousFAQSection" var="feature">
                        <cms:component component="${feature}" />
                    </cms:pageSlot>
                </div> 
				</sec:authorize>
            </div>
        </div>
    </div>
</section>
<div class="well well-sm well-brandstrip clearfix">
    <ul class="nav">
        <c:set var="fmComponentName" value="brandStrip" scope="session" />

        <cms:pageSlot position="FMBrandstrip" var="feature">
          <cms:component component="${feature}" />
        </cms:pageSlot>
    </ul>
</div>
<c:set var="fmComponentName" value="footerImageLinks" scope="session" />

</template:page>




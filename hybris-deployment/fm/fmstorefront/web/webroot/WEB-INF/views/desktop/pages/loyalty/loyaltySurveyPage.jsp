<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="survey" tagdir="/WEB-INF/tags/desktop/loyalty"%>



<template:page pageTitle="${pageTitle}">
	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:surveybreadcrumb breadcrumbs="${breadcrumbs}" />
					<li><div id="surveyBreadCrumb"></div></li>
				</ul>
			</div>
		</div>
	</section>
	<section class="rewardsAboutPage contentPage">
		<section class="rewardsAboutPage contentPage">
			<section class="wayToPoints accountDetailPage pageContet">
				<div class="container champion">
					<div class="row">
						<div class="col-lg-12 col-xs-12 internalLanding surveyPage">
							<div class="internalPage rightHandPanel clearfix">
								<div class="clearfix" id="BrandSurveyBegin">
									

									<c:if test="${surveyType eq 'Brand' }">
										<c:set var="fmComponentName" value="learnpara" scope="session" />
										<cms:pageSlot position="FMproductListGridSection" var="feature">
										<cms:component component="${feature}" />
										</cms:pageSlot>
										<div class="clearfix topMargn_5">
											<button
												class="btn btn-sm btn-fmDefault text-uppercase pull-left  btmMrgn_5"
												id="BrandSurveyBegin_btn" value='1'>Begin Survey</button>
										</div>
									</c:if>
									<c:if test="${surveyType eq 'PartsBuyingSurvey' }">
										
										<c:set var="fmComponentName" value="learnpara" scope="session" />
										<cms:pageSlot position="FMSearchGridBannerSection" var="feature">
										<cms:component component="${feature}" />
										</cms:pageSlot>
										<div class="clearfix topMargn_5">
											<button
												class="btn btn-sm btn-fmDefault text-uppercase pull-left  btmMrgn_5"
												id="PartsBuySurveyBegin_btn" value='1'>Begin Survey</button>
										</div>
									</c:if>
									<c:if test="${surveyType eq 'InterestsandHobbies' }">
											<c:set var="fmComponentName" value="learnpara" scope="session" />
											<cms:pageSlot position="FMProductGridBannerSection" var="feature">
											<cms:component component="${feature}" />
											</cms:pageSlot>									
										<div class="clearfix topMargn_5">
											<button
												class="btn btn-sm btn-fmDefault text-uppercase pull-left  btmMrgn_5"
												id="IntandHobbiesSurveyBegin_btn" value='1'>Begin Survey</button>
										</div>
									</c:if>
								</div>
								<c:if test="${surveyType eq 'Brand' }">
									
									
 									<survey:loyaltyBrandSurvey/>
								</c:if>
								<c:if test="${surveyType eq 'PartsBuyingSurvey' }">
								
									
									<survey:loyaltyPartsBuyingSurvey/>
								</c:if>
								<c:if test="${surveyType eq 'InterestsandHobbies' }">
									
									
								<survey:loyaltyInterestsandHobbiesSurvey/>
								</c:if>
							</div>
						</div>
					</div>
					<div class="row">
						<div
							class="col-lg-12 col-md-12 col-sm-12 col-xs-12 internalLanding">
						</div>
					</div>
				</div>
			</section>
		</section>

		<!-- InstanceEndEditable -->

		<!-- Brandstrip -->

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<!-- <c:set var="fmComponentName" value="brandStripB2B" scope="session" />-->
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
        
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
	</section>
</template:page>





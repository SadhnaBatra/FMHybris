<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="productloyalty"
	tagdir="/WEB-INF/tags/desktop/product/loyalty"%>

<%@ taglib prefix="product"
	tagdir="/WEB-INF/tags/desktop/product/loyalty"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<template:page pageTitle="${pageTitle}">
	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
					<breadcrumb:loyaltyProductBreadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</section>

	<!-- MAIN CONTAINER-->
	<!-- InstanceBeginEditable name="Page Content Section" -->
	<section class="myRewardDetail productDetailPage">
		<section class="productDetailContent">
			<div class="container">
			    <div class="left-nav col-md-3 col-sm-12">
			      <c:set var="fmComponentName" value="leftnavgaragereward" scope="session" />
			      <cms:pageSlot position="FMAnonymousGarageRewardsLeftNavSection" var="feature">
			      <cms:component component="${feature}" />
			      </cms:pageSlot>
		        </div>
				<div class="col-md-9 col-sm-12">
				  <div class="clearfix bgwhite">
					<div class="col-md-6 topMargn_20">
						<div class="prodMainImg">
								<a href="${contextPath}/${currentsiteUID}/${currentLanguage.isocode}/${currentCurrency.isocode}/lsearch?q=:name-asc:&text=#"
									class="addNewAddLink"><span
									class="linkarow fa fa-angle-left"></span> Back to Redeem List</a>							
							<div class="col-md-12">
								<productloyalty:loyaltyProductImage product="${product}"
									galleryImages="${galleryImages}" />
							</div>
						</div>
					</div>
					<div class="col-md-6 topMargn_20">
						<div class="desNDetails">
							<div class="fitmentCheck rewardsPanel clearfix btmMrgn_30">
								<h3 class="panel-title col-lg-6 topMargn_10">
									<span class="fa fa-user"></span> <span
										class=" text-uppercase">Garage rewards</span>
								</h3>
								<div class="rewardsPoints col-lg-6">
									<span class="pull-right"><i class="fa fa-wrench"></i><strong> <fmt:formatNumber type="number" value="${TotalPoints}" /><sub>pts</sub></strong></span>
								</div>
							</div>
							<h2 >${fn:escapeXml(product.name)}</h2>
							<c:set value="${product.code}" var="productCode"/>
							<c:if test="${isVariantSelected eq false }">
								<c:set var="productCode" value="${fn:split(product.code , '-')}" />
							</c:if>
							<div class="partNoReviewLink clearfix">
								<div class="partNoNWarnty pull-left">
									<span class="partNoLabel">Item Number:</span> 
								   <c:choose>
									<c:when test="${isVariantSelected eq false  }">
										<c:if test="${not empty productCode[1]}">
											<span class="partNo" itemprop="productID">${productCode[0]}-${productCode[1]}</span>
										</c:if>
										<c:if test="${empty productCode[1]}">
											<span class="partNo" itemprop="productID">${productCode[0]}</span>
										</c:if>									
									</c:when>
									<c:otherwise>
										<span class="partNo" itemprop="productID">${product.code}</span>
									</c:otherwise>
								    </c:choose> 
								</div>
								<div class="partNoNWarnty pull-right">
									<span class="partNoLabel mainPrice"><fmt:formatNumber type="number" value="${product.loyaltyPoints}" /> </span>
									<span class="partNo">pts</span>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 topMargn_20">
									<p>${product.description}</p>
								</div>
								<div class="clearfix">
									<product:loyaltyProductVariantSelector product="${product}" userLoyaltyPoints="${TotalPoints}" />
								</div>
								<div style="display: none;" class="sizeChart clearfix">
								   <c:set var="fmComponentName" value="learnpara" scope="session" />
								   <cms:pageSlot position="ProductPageSizeChartSection" var="feature">
			                       <cms:component component="${feature}" />
			                       </cms:pageSlot>
								</div>
							  </div>
							</div>
						</div>
					</div>
				</div>
		</section>

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
	</section>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>








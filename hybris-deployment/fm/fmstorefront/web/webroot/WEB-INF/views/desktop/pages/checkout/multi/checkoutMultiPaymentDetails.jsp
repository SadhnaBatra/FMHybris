<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="payment" tagdir="/WEB-INF/tags/desktop/payment"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<template:page pageTitle="${pageTitle}">
<section class="customBgBlock checkoutPage">
  <div class="container">
    <div class="row">
      <div class="col-xs-12 checkoutSteps">
        <ul class="nav nav-pills nav-justified thumbnail setup-panel">
          <li class="complete"><a href="#step-1"><spring:theme code="checkout.page.tabs.deliveryMethod"/></a><span class="chevron"></span></li>
          <li class="active"><a href="#step-2"><spring:theme code="checkout.page.tabs.paymentDetails"/></a><span class="chevron"></span></li>
          <!-- <li class="disabled"><a href="#step-3"><spring:theme code="checkout.page.tabs.reviewPlaceOrder"/></a><span class="chevron"></span></li> -->
          <li class="disabled"><a href="#step-4"><spring:theme code="checkout.page.tabs.orderConfirmation"/></a></li>
        </ul>
      </div>
    </div>
  </div>
</section>

<section itemtype="http://schema.org/Product" itemscope="" class="breadcrumbPanel visible-lg visible-md visible-sm">
  <div class="container">
    <div class="row">
      <ul class="breadcrumb">
        <c:url value="/" var="homeUrl"/>
        <li> <a itemprop="url" class="text-capitalize" href="${homeUrl}"> <span class="fa fa-home"></span> <spring:theme code="checkout.page.home"/></a> <i class="fa fa-angle-right "></i></li>
        <c:url value="/cart" var="cartUrl"/>
        <li><a itemprop="url" class="text-capitalize" href="${cartUrl}"><spring:theme code="checkout.page.shoppingCart"/></a> <i class="fa fa-angle-right "></i></li>
        <li><a itemprop="url" class="selected text-capitalize" href="${cartUrl}"><spring:theme code="checkout.page.checkout"/></a></li>      </ul>
    </div>
  </div>
</section>
<section class="productDetailPage pageContet checkoutB2bSmall">
  <div class="container">
<order:PaymentDetails/>
</div>
</section>
<!-- InstanceEndEditable -->
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



<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<spring:theme text="Your Shopping Cart" var="title" code="cart.page.title"/>
<c:url value="/cart/checkout" var="checkoutUrl"/>
<c:url value="/cart" var="cartURL"/>
<c:url value="/cart-store-finder/inputZipCodeSearchGet/${shipToAddress.postalCode}" var="storePickup"/>
<template:page pageTitle="${pageTitle}">

	<spring:theme code="basket.add.to.cart" var="basketAddToCart"/>
	<spring:theme code="cart.page.checkout" var="checkoutText"/>
	
	<common:globalMessages/>
	<cart:cartRestoration/>
	<cart:cartValidation/>
	
<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
  <div class="container">
    <div class="row">
      <ul class="breadcrumb">
        <breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
      </ul>
    </div>
  </div>
</section>
<section class="productDetailPage pageContet" >
  <div class="container">
    <div class="bgwhite col-lg-12 shoppingCartHead clearfix">
        <div class="clearfix">
      <div class="row shoppingCartHeading">
          <div class="col-md-10 col-sm-10 col-xs-10">
            <h2><span class="fa fa-shopping-cart shoppingCartFaSCart fm_fntRed"></span><span  class="text-uppercase">shipment confirmation</span><!--<span class="shoppingCartID">(Cart ID: 123456789)</span>--></h2>
          </div>
         </div>
        </div>

	          <div class="table-responsive col-lg-12 userTable">
	            <cart:fmGATPItems cartData="${cartData}"/>
	            <div class="row shoppingCartFotter">
	            <div class=" col-mg-8 lftPad_0">
	            <sec:authorize ifNotGranted="ROLE_FMBUVOR,ROLE_FMVIEWONLYGROUP">
	            	
	                <a href="${checkoutUrl }" id="fm-gatp-a-${cartData.fmordertype}" >
	                <button id="fm-gatp-button" class="btn btn-sm btn-fmDefault text-uppercase pull-right" ${gatpAvailFlag != 'true' ? 'disabled' : '' } data-dismiss="modal" >Confirm &amp; Checkout</button></a>
	             </sec:authorize>
	                <a href="${cartURL }" id="activate-step-2" ><button class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right" data-dismiss="modal" >Back To Cart</button></a>
	              </div>
	            </div>
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

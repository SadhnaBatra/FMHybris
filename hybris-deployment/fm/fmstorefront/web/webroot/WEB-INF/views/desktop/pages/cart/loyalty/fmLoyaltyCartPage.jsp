<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/cart"%>


<spring:theme text="Your Shopping Cart" var="title"
	code="cart.page.title" />
<c:url value="/cart/checkout" var="checkoutUrl" />
<c:url value="/gatp" var="gatpUrl" />
<c:url value="/checkout/multi/paymentDetails" var="storePickup" />

<template:page pageTitle="${pageTitle}">

	<spring:theme code="basket.add.to.cart" var="basketAddToCart" />
	<spring:theme code="cart.page.checkout" var="checkoutText" />

	<common:globalMessages />
	<cart:cartRestoration />
	<cart:cartValidation />

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
	<section class="productDetailPage pageContet">
		<c:if test="${orderBlock != null && orderBlock == 'X' }">
			<c:set var="order" value="false" />
		</c:if>

		<div class="container">
			<div class="clearfix bgwhite col-lg-12 shoppingCartHead">
				<div class="clearfix">
					<spring:url value="lsearch?q=:name-asc:&text=#" var="continueShoppingUrl"
						htmlEscape="true" />
					<div class="row shoppingCartHeading">
						<div class="col-lg-9 col-md-3 col-sm-12 col-xs-12">
							<h2>
								<span class="fa fa-shopping-cart shoppingCartFaSCart fm_fntRed"></span><span
									class="text-uppercase">shopping cart</span>
								<!--<span class="shoppingCartID">(Cart ID: 123456789)</span>-->
							</h2>
						</div>
					</div>
				</div>
				<c:if test="${availablePointsCheck eq false}">
					<div class="errorMSG btmMrgn_22 lftMrgn_400">
						<span class="" style="color: red">Redemption points exceeds your available points</span>
					</div>
				</c:if>
				<c:if test="${totalReedemPoints lt 1000 and not empty cartData.entries}">
					<div class="errorMSG btmMrgn_22 lftMrgn_400">
						<span class="" style="color: red">You must redeem a minimum of 1,000 points to submit your order.</span>
					</div>
				</c:if>
				<div class="table-responsive col-lg-12 userTable">
					<c:if test="${not empty cartData.entries}">
						<cart:fmloyaltycartItems cartData="${cartData}" />
					</c:if>

					<div class="row shoppingCartFotter">
						<div class="col-lg-3">
							<span class="text-uppercase"></span>
						</div>
						<div class="col-lg-9">
							<div class="col-lg-9">
								<c:if test="${empty cartData.entries}">
									<span style="color: red"><h5>
											<b>There are no items added to cart</b>
										</h5> </span>
								</c:if>
							</div>
							<c:if test="${empty cartData.entries}">

								<a class="text-capitalize addNewAddLink pull-right" href="${continueShoppingUrl}"><spring:theme
										text="Continue Shopping" code="cart.page.continue" />&nbsp;<span
									class="linkarow fa fa-angle-right "></span></a>
							</c:if>
							<c:if test="${not empty cartData.entries}">
								<div id="gatpflowbottom">
									<c:url value="/loyalty/checkout" var="checkout" />
									<a href="${checkout}" class="a-fm-cart"><button ${availablePointsCheck eq false or totalReedemPoints lt 1000 ? 'disabled="disabled"' : '' }
											class="btn btn-fmDefault pull-right shoppingCartCheckOut">
											<spring:theme code="checkout.checkout" />
										</button></a>
								</div>
								<a class="text-capitalize addNewAddLink pull-right"
									href="${continueShoppingUrl}"><spring:theme
										text="Continue Shopping" code="cart.page.continue" />&nbsp;<span
									class="linkarow fa fa-angle-right "></span></a>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="BottomContent" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>

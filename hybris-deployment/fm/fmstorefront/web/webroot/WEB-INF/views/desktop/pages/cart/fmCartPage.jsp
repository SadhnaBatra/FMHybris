<%@ page trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c"            uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template"     tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cart"         tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="spring"       uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms"          uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt"          uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"           uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme"        tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format"       tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="common"       tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb"   tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="ycommerce"    uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form"         uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/cart" %>

<spring:theme text="Your Shopping Cart" var="title" code="cart.page.title"/>

<c:url value="/cart/checkout" var="checkoutUrl"/>
<c:url value="/gatp" var="gatpUrl"/>
<c:url value="/cart-store-finder/inputZipCodeSearchGet/${shipToAddress.postalCode}" var="storePickup"/>

<template:page pageTitle="${pageTitle}">
	<spring:theme code="basket.add.to.cart" var="basketAddToCart"/>
	<spring:theme code="cart.page.checkout" var="checkoutText"/>
	
	<common:globalMessages/>
	<cart:cartRestoration/>
	<cart:cartValidation/>
	
	<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
		<div id="fade"></div>

		<div id="modal">
			<img id="loader" src="/fmstorefront/_ui/desktop/common/images/spinner.gif" />
		</div>
	
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
				</ul>
			</div>
		</div>
	</section>
	
	<section class="productDetailPage pageContet">
		<c:set var="credit" value="true"/>
		<c:set var="order" value="true"/>

		<c:if test="${creditBlock != null && creditBlock =='X'}">
			<c:set var="credit" value="false"/>
		</c:if>

		<c:if test="${orderBlock != null && orderBlock == 'X'}">
			<c:set var="order" value="false"/>
		</c:if>
	
		<div class="container">
			<div class="clearfix bgwhite col-lg-12 shoppingCartHead"> 
				<div class="clearfix">
					<spring:url value="/" var="continueShoppingUrl" htmlEscape="true"/>

					<div class="row shoppingCartHeading">
						<div class="col-lg-9 col-md-3 col-sm-12 col-xs-12">
	            			<h2>
	            				<span class="fa fa-shopping-cart shoppingCartFaSCart fm_fntRed"></span><span class="text-uppercase"><spring:theme code="text.shopping.cart" text="shopping cart" /></span>
	            			</h2>

							<c:if test="${credit == 'false'}">
								</br>
								<p style="color: red;font-size: 1.6rem;">
									<spring:theme code="text.shopping.cart.error" 
										text="We are unable to process orders on your account at this time. Please contact the Customer Financial Services Department at 1-800-521-8605 for assistance." />
								</p>
							</c:if>
						</div>
	          			
						<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
							<c:if test="${empty cartData.entries}">
								<a class="text-capitalize addNewAddLink pull-right" href="${continueShoppingUrl}"><spring:theme text="Continue Shopping" code="cart.page.continue"/>&nbsp;<span class="linkarow fa fa-angle-right"></span></a>
							</c:if>

							<c:if test="${not empty cartData.entries}">
								<div id="tscpickuptop" ${cartData.fmordertype == 'pickup' ? 'style="display:block"' : 'style="display:none"'}>
									<c:if test="${credit == 'true'}">
										<a href="${storePickup}" class="a-fm-cart"><button class="btn btn-fmDefault pull-right shoppingCartCheckOut"><spring:theme code="checkout.checkout" /></button></a>
									</c:if>
								</div>

								<div id="gatpflowtop" ${cartData.fmordertype != 'pickup' ? 'style="display:block"' : 'style="display:none"'}>
									<c:if test="${credit == 'true'}">
										<a href="${gatpUrl}" class="a-fm-cart"><button class="btn btn-fmDefault pull-right shoppingCartCheckOut"><spring:theme code="checkout.checkout" /></button></a>
									</c:if>
								</div>

								<a class="text-capitalize addNewAddLink pull-right" href="${continueShoppingUrl}"><spring:theme text="Continue Shopping" code="cart.page.continue"/>&nbsp;<span class="linkarow fa fa-angle-right"></span></a>
							</c:if>
						</div>
					</div>

					<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer' or customerType eq 'CSRCustomer'}">     	
						<div class="cartInfo">
							<div class="row">
								<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0">
									<div class="subTitle text-uppercase"><spring:theme code="cart.page.billTo.details" text="Bill To Details" /></div>

									<div class="reviewFirstPanelMargin lftPad_10">
										<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="cart.page.soldTo" text="Sold to" /></p>
	
										<p class="text-uppercase">${soldToUnit.name}</p>
										<p>${soldToAddress.line1}</p>
										<p>${soldToAddress.line2}</p>
										<p>${soldToAddress.town},&nbsp;${soldToAddress.region.isocodeShort}&nbsp;${soldToAddress.postalCode}&nbsp;${soldToAddress.country.isocode}</p>            
										<p>${soldToUnit.uid}</p>
									</div>

									<div class="reviewFirstPanelMargin lftPad_10">
										<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="cart.page.customerCode" text="customer code" /></p>
										<p>${soldToUnit.uid}</p>
									</div>
								</div>

								<div>
									<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
										<div class="subTitle text-uppercase">
											<div class="clearfix"><spring:theme code="cart.page.shippingDetails" text="shipping details" /></div>
										</div>

										<div class="tab-content shipto lftPad_10">
											<c:if test="${ cartData.fmordertype != 'pickup' || (cartData.fmordertype == 'pickup' && (empty storePickupAddress || shippingOption != 'pickup')) }">
												<div class="tab-pane active" id="defaultAcc" style="display: block;">
													<div class="reviewFirstPanelMargin  topMargn_20">
														<p class="reviewPlaceOrderBold text-capitalize "><spring:theme code="cart.page.ShipTo" text="Ship to" /></p>
	
														<p class="text-uppercase">${shipToUnit.name}</p>
														<p>${shipToAddress.line1}</p>
														<p>${shipToAddress.line2}</p>
														<p>${shipToAddress.town},&nbsp;${shipToAddress.region.isocodeShort}&nbsp;${shipToAddress.postalCode}&nbsp;${shipToAddress.country.isocode}</p>                  
														<p>${shipToUnit.uid}</p>	
													</div>

													<c:if test="${shipToUnit.uid != null}">
														<div class="reviewFirstPanelMargin">
															<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="cart.page.customerCode" text="customer code" /></p>
															<p>${shipToUnit.uid}</p>
														</div>
													</c:if>
												</div>
											</c:if>

											<c:if test="${cartData.fmordertype == 'pickup' && not empty storePickupAddress && shippingOption == 'pickup'}">
												<div class="tab-pane active" id="defaultAcc" style="display: block;">
													<div class="reviewFirstPanelMargin  topMargn_20">
														<p class="reviewPlaceOrderBold text-capitalize "><spring:theme code="cart.page.PickupFrom" text="Pickup From" /></p>
														<p class="text-uppercase">${storePickupAddress.firstName}</p>
														<p>${storePickupAddress.line1}</p>
														<p>${storePickupAddress.line2}</p>
														<p>${storePickupAddress.town}</p> 
														<p>${storePickupAddress.region.name}</p>                   
														<p>${storePickupAddress.country.name}</p>
														<p>${storePickupAddress.postalCode}</p> 
													</div>
												</div>
											</c:if>

											<div class="tab-pane topMargn_20" id="shipTo" style="display: none;">
												<form role="form">
													<label for="changeShip" class="text-capitalize"><spring:theme code="cart.page.ShipTo" text="Ship to" /></label>
	                      
													<div class="input-group custom-search-form width275 changeShipTo btmMrgn_30">
														<input type="search" class="form-control" id="changeShip">
														<span class="input-group-btn">
															<button onclick="$('#shipTo').hide();$('#changeAddress').show();" class="btn btn-default" type="button"> <span class="fa fa-search"></span> </button>
														</span>
													</div>
												</form>
											</div>

											<div class="tab-pane" id="changeAddress" style="display: none;">
												<div class="reviewFirstPanelMargin  topMargn_20">
													<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="cart.page.ShipTo" text="Ship to" /></p>
													<p class="text-uppercase"><spring:theme code="cart.page.shipTo.address1" text="UNI-SELECT USA INC" /> </p>
													<p><spring:theme code="cart.page.shipTo.address2" text="46 E Main St" /></p>
													<p><spring:theme code="cart.page.shipTo.address3" text="Jamestone, IN 46147-0000" /></p>
													<p><spring:theme code="cart.page.shipTo.address4" text="(000) 000-0000" /></p>
												</div>
											</div>

											<div class="tab-pane" id="new" style="display: none;">
												<form role="form">
													<div class="reviewFirstPanelMargin topMargn_20">
														<div class="form-group">
															<label for="firstLastName"> <spring:theme code="address.firstName" text="First Name" /> &amp; <spring:theme code="address.lastName" text="Last Name" /></label>
															<input type="text" value="" class="form-control" id="firstLastName">
														</div>
														<div class="form-group">
															<label for="addressLine1"><spring:theme code="address.line1" text="Address Line 1" /></label>
															<input type="text" value="" class="form-control" id="addressLine1">
														</div>
														<div class="form-group">
															<label for="addressLine2"><spring:theme code="address.line2" text="Address Line 2" /></label>
															<input type="text" value="" class="form-control" id="addressLine2">
														</div>
														<div class="form-group">
															<label for="contactNumber"><spring:theme code="cart.page.contactNumber" text="Contact Number" /></label>
															<input type="text" value="" class="form-control" id="contactNumber">
														</div>
														<div class="form-group">
															<label for="customerCode"><spring:theme code="cart.page.Address.customerCode" text="Customer Code" /></label>
															<input type="text" value="" class="form-control" id="customerCode">
														</div>
													</div>
	                        
													<div class="">
														<button onclick="$('#new').hide();$('#newAddress').show();" type="button" class="btn btn-fmDefault"><spring:theme code="cart.page.Address.submit" text="Submit" /></button>
														<button type="button" class="btn btn-fmDefault"><spring:theme code="cart.page.Address.cancel" text="Cancel" /></button>
													</div>
												</form>
											</div>

											<div class="tab-pane" id="newAddress" style="display: none;">
												<div class="reviewFirstPanelMargin  topMargn_20">                          
													<p class="reviewPlaceOrderBold text-capitalize"><spring:theme code="cart.page.ShipTo" text="Ship to" /></p>
													<p class="text-uppercase"><spring:theme code="cart.page.shipTo.address1" text="UNI-SELECT USA INC" /> </p>
													<p><spring:theme code="cart.page.shipTo.address2" text="46 E Main St" /></p>
													<p><spring:theme code="cart.page.shipTo.address3" text="Jamestone, IN 46147-0000" /></p>
													<p><spring:theme code="cart.page.shipTo.address4" text="(000) 000-0000" /></p>                           
												</div>
											</div>
										</div>
									</div>
								</div>

								<c:if test="${not empty cartData.entries}">
									<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1">
										<div class="subTitle text-uppercase"><spring:theme code="cart.page.orderType" text="Order Type" /></div>

										<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
											<label for="selectOrderType" class=" text-capitalize"><spring:theme code="cart.page.selectOrder.Type" text="Select order Type" /><!--<span class="fm_fntRed">*</span>--> </label>

											<select class="form-control width245" id="selectOrderType">
												<option value="ordertype"><spring:theme code="cart.page.selectOrder.Type1" text="Select Order Type" /></option>
												<!-- <option value="Emergency">Emergency  (Expedited)</option>
												<option value="Stock">Stock</option> 
												<option value="Regular">Regular Small Package</option>  -->
	
												<!-- *** Original Order Type options - BEGIN: *** -->
												<!--  
												<option value="Emergency" ${cartData.fmordertype == 'Emergency' ? 'selected="selected"' : ''}><spring:theme code="cart.page.emergency.shipping" text="Emergency  (Expedited Shipping)" /></option>
												<option value="Regular" ${cartData.fmordertype == 'Regular' ? 'selected="selected"' : ''} ><spring:theme code="cart.page.regular.package" text="Regular Small Package" /> </option> 
												<option value="Stock" ${cartData.fmordertype == 'Stock' ? 'selected="selected"' : ''}><spring:theme code="cart.page.stock" text="Stock" /></option> 
												<option value="pickup" ${cartData.fmordertype == 'pickup' ? 'selected="selected"' : ''} ><spring:theme code="cart.page.customer.pickup" text="Fullfill from Local DC" /></option>
												-->
												<!-- *** Original Order Type options - END *** -->
	
												<option value="Emergency" ${cartData.fmordertype == 'Emergency' ? 'selected="selected"' : ''}><spring:theme code="cart.page.emergency.shipping" text="Emergency (Expedited Shipping)" /></option>
												<option value="Stock" ${cartData.fmordertype == 'Stock' ? 'selected="selected"' : ''}><spring:theme code="cart.page.stock" text="Stock" /></option> 
											</select>
	                  
											<div class="col-xs-12 topMargn_15 lftMrgn_12" id="div_1">
												<div class="poerror_show" id="ordertyp_error" style="display: none"><spring:theme code="cart.page.orderType.error" text="Please select the Order Type to continue" /></div>	
											</div>
										</div>
	
										<div id="StoreAddress" class="reviewFirstPanelMargin lftPad_10 btmMrgn_30" ${cartData.fmordertype == 'pickup' ? 'style="display:block"' : 'style="display:none"'}>
											<div class=""><span id="storeaddress">${storePickupAddress1}</span></div>
										</div>
	
										<c:if test="${futureDate != null && not empty futureDate }">
											<fmt:formatDate var="fDate" value="${futureDate }" pattern="MM-dd-yyyy"/>
										</c:if>

										<div id="futureDate" class="reviewFirstPanelMargin lftPad_10 btmMrgn_30" ${cartData.fmordertype == 'Stock' ? 'style="display:block"' : 'style="display:none"'}>
											<label for="selectFutureDate" class=" text-capitalize"><spring:theme code="cart.page.future.order.date" text="Select Future Order Date" /><!--<span class="fm_fntRed">*</span>--> </label>
											<!-- <p><input type="text" id="datepicker"  value="${fDate }" ></p> -->
											<div class="form-group">
												<div class="controls">
													<div class="input-group  col-sm-2">
														<input type="text" id="datepicker"  class= "width105 form-control" value="${fDate }" readonly="readonly" >
														<label for="datepicker" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
													</div>
												</div>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</c:if>

					<c:if test="${customerType eq 'b2cCustomer'}"> 
						<div class="col-md-12 col-sm-12 col-xs-12 topMargn_10">
							<p class="text-capitalize"><span class="shoppingCartLabel"><spring:theme code="cart.page.b2c.shipTo" text="ship to:" /> <span class="zipcode"><spring:theme code="cart.page.b2c.zipcode" text="48335" /></span></span> <a onclick="$('.changeZipCode').show();$('.shipToInfo').hide();" class="text-capitalize shoppingCartLabel fm-blue-text"><spring:theme code="cart.page.b2c.change.location" text="change location" /></a> </p>
							<p class="shipToInfo"> <spring:theme code="cart.page.b2c.shipTo.info" text="Used to estimate availabilitydate, shipping and tax" />  </p>

							<div style="display:none" class="form-group clearfix changeZipCode">
								<input type="text" class="form-control width165 pull-left" value="" id="changeZipCode">
								<button class="btn btn-fmDefault pull-left lftMrgn_20"><spring:theme code="cart.page.change.location.submit" text="submit" /> </button>
							</div>
						</div>
					</c:if>
				</div>

				<div class="table-responsive col-lg-12 userTable">
					<c:if test="${not empty cartData.entries}">
						<cart:fmcartItems cartData="${cartData}"/>
					</c:if>
	
					<div class="row shoppingCartFotter">
						<div class="col-lg-3">
							<span class="text-uppercase"></span>
						</div>
	
						<div class="col-lg-9  rgtPad_0">
							<div class="col-lg-9">
								<c:if test="${empty cartData.entries}">
									<span style="color: red"><h5><b><spring:theme code="cart.page.cartdata.empty" text="There are no items added to cart" /> </b></h5> </span>
								</c:if>
							</div>

							<c:if test="${empty cartData.entries}">
								<a class="text-capitalize addNewAddLink pull-right" href="${continueShoppingUrl}"><spring:theme text="Continue Shopping" code="cart.page.continue"/>&nbsp;<span class="linkarow fa fa-angle-right"></span></a>
							</c:if>

							<c:if test="${not empty cartData.entries}">
								<c:url value="/cart/checkout" var="checkoutUrl"/>

								<div id="tscpickupbottom" ${cartData.fmordertype == 'pickup' ? 'style="display:block"' : 'style="display:none"'}>
									<c:if test="${credit == 'true'}">
										<a href="${storePickup}" class="a-fm-cart"><button class="btn btn-fmDefault pull-right shoppingCartCheckOut"><spring:theme code="checkout.checkout" /></button></a>
									</c:if>
								</div>

								<div id="gatpflowbottom" ${cartData.fmordertype != 'pickup' ? 'style="display:block"' : 'style="display:none"'}>
									<c:if test="${credit == 'true'}">
										<a href="${gatpUrl }" class="a-fm-cart"><button class="btn btn-fmDefault pull-right shoppingCartCheckOut"><spring:theme code="checkout.checkout" /></button></a>
									</c:if>
								</div>
								<a class="text-capitalize addNewAddLink pull-right" href="${continueShoppingUrl}"><spring:theme text="Continue Shopping" code="cart.page.continue"/>&nbsp;<span class="linkarow fa fa-angle-right"></span></a>
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

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>

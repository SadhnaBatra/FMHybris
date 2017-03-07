<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>

<template:page pageTitle="${pageTitle}">
	<%-- <c:out value="<%=request.getContextPath()%>"/> --%>
	



	<!-- MAIN CONTAINER-->
	<!-- InstanceBeginEditable name="Page Content Section" -->

	<section class="customBgBlock checkoutPage">
		<div class="container">
			<div class="row">
				<div class="col-xs-12 checkoutSteps">
					<ul class="nav nav-pills nav-justified thumbnail setup-panel">
						<li class="active"><a href="#step-1">Shipping Address</a><span
							class="chevron"></span></li>
						<li class="disabled"><a href="#step-2">Review & Place
								Order</a><span class="chevron"></span></li>
						<li class="disabled"><a href="#step-3">Order Confirmation</a></li>
					</ul>
				</div>
			</div>
		</div>
	</section>
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
<!-- 	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<li><a href="homepageB2b_small.html"><span
							class="fa fa-home"></span> Home</a> <span class="fa fa-angle-right"></span></li>
					<li><a href="#" class="text-capitalize" itemprop="url">shopping
							cart</a> <span class="fa fa-angle-right "></span></li>
					<li><a href="#" class="selected text-capitalize"
						itemprop="url">checkout</a></li>
				</ul>
			</div>
		</div>
	</section> -->
	<section class="productDetailPage pageContet">
		<div class="container">
			<div class="clearfix bgwhite setup-content" id="step-1">
				<div class="col-xs-12 chekoutBillingShippingAddress">
					<div class="clearfix">
						<div class="col-lg-12 chekoutShipping chekoutShippingWithNoBorder">
							<h2 class="text-uppercase ">Ship to</h2>
							<p class="reqField">
								<span class="fm_fntRed">*</span> Required Fields
							</p>
							<div class="">
								<c:url value="/loyalty/checkout/reviewplaceorder"
									var="encodedUrl" />
								<form:form commandName="newAddress" method="POST"
									class="CheckoutBillTo" action="${encodedUrl}" id="newAddress">
									<div class="form-group  regFormFieldGroup  topMargn_10">
										<label class="" for="shipfirstName">First Name<span
											class="required fm_fntRed">*</span></label>
										<form:input type="text" id="shipfirstName" path="firstName"
											name="shipfirstName" placeholder=""
											value="${shipToAddress.firstName}"
											class="form-control width270" Required="true" />
											<div class="errorMsg fm_fntRed" style="display: none;">Please
											enter First Name</div>
									</div>
									<div class="form-group  regFormFieldGroup ">
										<label class="" for="shiplasttName">Last Name<span
											class="required fm_fntRed">*</span></label>
										<form:input type="text" path="lastName" id="shiplasttName"
											name="shiplasttName" placeholder=""
											value="${shipToAddress.lastName}"
											class="form-control width270" Required="true" />
											<div class="errorMsg fm_fntRed" style="display: none;">Please
											enter Last Name</div>
									</div>
									<div class="form-group  regFormFieldGroup ">
										<label class="" for="shipAddress">Address<span
											class="required fm_fntRed">*</span></label>
										<form:input id="shipAddress" path="line1" type="text"
											name="Address line" placeholder=""
											value="${shipToAddress.line1}" class="form-control width270" Required="true" />
											<div class="errorMsg fm_fntRed" style="display: none;">Please
											enter Address</div>
										</div>	
									<div class="form-group  regFormFieldGroup ">
										<form:input id="shipAddress2" path="line2" type="text"
											name="Address line 2" placeholder=""
											value="${shipToAddress.line2}"
											class="form-control width270 topMargn_10" />
									</div>
									<div class="form-group  regFormFieldGroup ">
										<label class="" for="shipCity">City<span
											class="required fm_fntRed">*</span></label>
										<form:input id="shipCity" path="townCity" type="text"
											name="shipcity" placeholder="" value="${shipToAddress.town}"
											class="form-control width270" Required="true" />
											<div class="errorMsg fm_fntRed" style="display: none;">Please
											enter City</div>
									</div>
									<div class="form-group  regFormFieldGroup ">
										<label class="" for="shipcountry">Country<span
											class="required fm_fntRed">*</span></label>
										<form:select id="shipcountry" path="countryIso"
											name="shipcountry" class="form-control width270"
											Required="true">


											<c:choose>
												<c:when test="${shipToAddress.country eq null}">
													<!-- 	<option value="Default">Select</option> -->
													<option value="" selected="selected">Select</option>
												</c:when>
												<c:otherwise>
													<option value="${shipToAddress.country.isocode}">${shipToAddress.country.name}</option>
												</c:otherwise>
											</c:choose>




											<c:forEach items="${countryData}" var="iso">
												<%--  <c:if test="${fn:contains(iso.isocode,'US')}"> --%>
												<option value="${iso.isocode}">${iso.name}</option>
												<%-- 	</c:if>  --%>
											</c:forEach>

										</form:select>
										<div class="errorMsg fm_fntRed" style="display: none;">Please
											select a country</div>
									</div>

									<div class="form-group  regFormFieldGroup ">
										<label class="" for="shipstateProvince">State /
											Province<span class="required fm_fntRed">*</span>
										</label>
										<form:select id="shipstateProvince" path="region"
											name="shipstateProvince" class="form-control width270"
											Required="true">


											<c:choose>
												<c:when test="${shipToAddress.region eq null}">
													<!-- 	<option value="Default">Select</option> -->
													<option value="" selected="selected">Select</option>
												</c:when>
												<c:otherwise>
													<%-- 		<option value="${fmdata.state}">${fmdata.state}</option> --%>

													<option value="${shipToAddress.region.isocodeShort}">${shipToAddress.region.name}</option>
												</c:otherwise>
											</c:choose>
											
				
											<c:forEach items="${regionsdatas}" var="reg">
												<c:forEach items="${reg}" var="val">
													<c:if test="${fn:contains(val.isocode,'US-')}">
														<option value="${val.isocodeShort}">${val.name}</option>
													</c:if>
													<c:if test="${fn:contains(val.isocode,'CA-')}">
														<option value="${val.isocodeShort}">${val.name}</option>
													</c:if>
												</c:forEach>
											</c:forEach>
										</form:select>
										<div class="errorMsg fm_fntRed" style="display: none;">Please
											select a State</div>
									</div>
									<div class="form-group  regFormFieldGroup ">
										<label class="" for="shipZipPostalCode"><span
											class="text-uppercase">Zip</span> / Postal Code<span
											class="required fm_fntRed">*</span></label>
										<form:input type="text" path="postcode" id="shipZipPostalCode"
											name="shipZipPostalCode" placeholder=""
											value="${shipToAddress.postalCode}"
											class="form-control width270" Required="true"
											onblur="javascript:validateNewShipForm();" />
										<div class="errorMsg fm_fntRed" style="display: none;">Please
											enter ZIP / Postal Code</div>

										<span class="errorMsg fm_fntRed" id="errorzip"> </span>
									</div>

									<div class="form-group  regFormFieldGroup ">

										<label class="" for="phone">Phone Number<span
											class="required fm_fntRed">*</span></label>
										<form:input type="tel" id="phone" name="phone" maxlength="15"
											placeholder="(555)555-5555" class="form-control width270"
											path="contactNo" Required="true"
											value="${shipToAddress.phone}"
											onblur="javascript:validateNewShipForm();" />
										<div class="errorMsg fm_fntRed" style="display: none;">Please
											enter Phone Number</div>
										<span style="" id="errorcnum"> </span>
									</div>
									<ul class="list-group checkboxGroup">
										<li class="list-group-item"><label for="savetomyaddress2">
												<!--  <input type="checkbox"  id="savetomyaddress2" onClick="$('.billcontactNickname2').toggle();"/>
                      &nbsp;Save to my address book -->
										</label></li>
									</ul>
									<div
										class="form-group  regFormFieldGroup  billcontactNickname2"
										style="display: none">
										<label class="" for="shipcontactNickname">Contact
											Nickname</label> <input type="text" id="shipcontactNickname"
											name="contactNickname" placeholder=""
											class="form-control width270" />
									</div>
							
					<div class="row">
						<div class="reviewPlaceOrderBtn col-lg-12">
							<c:url value="/loyalty/checkout/reviewplaceorder"
								var="encodedUrl" />
							<input type="submit"
								value="Continue to Review &amp; Place ORder" class="btn btn-sm btn-fmDefault text-uppercase pull-right rghtMrgn_20" id="continueToReview"/>
							<%--  <a href="${encodedUrl}" id="btn-fm-rp-placeorder" class="btn btn-sm btn-fmDefault text-uppercase pull-right rghtMrgn_20">Continue to Review &amp; Place ORder</a>  --%>
							<c:url value="/loyaltycart" var="encodedUrl1" />
							<a href="${encodedUrl1}" id="btn-fm-rp-placeorder"
								class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right">Return
								to Shopping Cart</a>
						</div>
					</div>
					</form:form>
				</div>
							</div>
						</div>

					</div>
			</div>
			</div>
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
</template:page>

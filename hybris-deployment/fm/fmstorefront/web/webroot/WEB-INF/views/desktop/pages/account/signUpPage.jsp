<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- jsp for getting the fm registration details -->

<template:page pageTitle="${pageTitle}">
	<%-- <c:out value="<%=request.getContextPath()%>"/> --%>

	<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
	  <div class="container">
	    <div class="row">
	      <ul class="breadcrumb">
	        <breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	      </ul>
	    </div>
	  </div>
	</section>
	<section class="productDetailPage pageContet">
		<section class="registrationContent">
			<div class="container">
				<div class="clearfix bgwhite">
					<div class="regFrm">
						<h3 class="text-uppercase registrationHeading">Registration</h3>
						<p class="reqField">
							<span class="fm_fntRed">*</span> Required Fields
						</p>
						<div id="globalMessages">
							<common:globalMessages />
						</div>

						<c:url value="/registration/createcustomer" var="submitAction" />
						<form:form class="registrationB2b" method="POST" commandName="fmdata" id="registrationB2b" name="registrationB2b" action="${submitAction}" enctype="multipart/form-data">

							<input type="hidden" id="technicianType" name="technicianType"/>
							<div class="panel  panel-primary panel-frm ">
								<div class="panel-heading clickable ">
									<h3 class="panel-title text-uppercase">Account Type</h3>
									<span class="pull-right "><i class="fa fa-minus"></i></span>
								</div>
								<div class="panel-body abc">
									<div class="form-group  regFormFieldGroup">
										<label class="" for="whatdescribes">What Best
											Describes You?<span class="fm_fntRed">*</span>
										</label> <input id="sessionusertype" value="${sessionusertype}"
											type="hidden" name="sessionusertype">

										<c:if test="${sessionusertype != null}">

											<body onload="retainRegistrationValues()" />

										</c:if>

										<form:select path="usertypedesc" id="whatdescribes" name="cars" class="form-control width375">
											<option value=""${sessionusertype == null || fn:trim(sessionusertype == '') || (
												sessionusertype.code != 'WarehouseDistributorLightVehicle' &&
												sessionusertype.code != 'WarehouseDistributorCommercialVehicle' &&
												sessionusertype.code != 'Retailer' &&
												sessionusertype.code != 'JobberPartStore' &&
												sessionusertype.code != 'ShopOwnerTechnician' &&
												sessionusertype.code != 'RepairShopOwner' &&
												sessionusertype.code != 'Technician' &&
												sessionusertype.code != 'Consumer' &&
												sessionusertype.code != 'Student' &&
												sessionusertype.code != 'SalesRep'
												) ? ' selected' : ''}>
												<spring:theme code="account.signup.select"/>
											</option>
											<c:forEach items="${fmusertype}" var="usertype">
												<c:if test="${usertype.code eq 'WarehouseDistributorLightVehicle'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.warehouse.distributor.light.vehicle"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'WarehouseDistributorCommercialVehicle'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.warehouse.distributor.commercial.vehicle"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'Retailer'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.retailer"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'JobberPartStore'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.jobber"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'RepairShopOwner'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.repair.shop.owner"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'Technician'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.technician"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'Consumer'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.fm.consumer"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'Student'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.fm.student"/></option>
												</c:if>
												<c:if test="${usertype.code eq 'SalesRep'}">
													<option value="${usertype}"${usertype eq sessionusertype ? ' selected' : ''}><spring:theme code="account.signup.fm.employee"/></option>
												</c:if>
											</c:forEach>
										</form:select>

									</div>
								</div>
							</div>
							<div id="registration-account-details" class="panel  panel-primary panel-frm panel-frm-filled" style="display: none">

								<div class="panel-heading">
									<h3 class="panel-title text-uppercase">Account Details</h3>
									<span class="pull-right "></span>
								</div>

								<div class="panel-body">

									<div class="form-group regFormFieldGroup form-B2Bbody">

										<div class="form-accCode form-group regFormFieldGroup">
											<label class="" for="accCode">Account Code
												<span class="required fm_fntRed">*</span>
												<span id="accCodeToolTip"
													  class="tip"
													  data-original-title="Don't have an Account Code? You may still register for a new account"
													  data-toggle="tooltip"
													  data-placement="right"
													  title="">
													<span class="fa fa-question-circle"></span>
												</span>
											</label>
											<div class="clearfix">
												<div class="col-lg-2 lftPad_0">
													<form:input id="accNo" type="text" name="accCode"
														class="form-control width195 pull-left" path="accCode"
														 onblur="resetDisabledCompanyFields();getUnits(true);validateAccount();" maxlength="11" />
													<div class="errorMsg fm_fntRed width195"
														style="display: none;">Please enter Account Code</div>
													
														<!-- For account code error-->
														<div class="errorMsg errorAccCode fm_fntRed width215" id="errorAccCode"></div>
												</div>
												<div class="col-lg-10 ">
													<div class="lftPad_20 pull-left">

														<p>Don't know or cant remember your Account Code?</p>
														<p>Call customer service at 1-800-334-3210</p>

													</div>
														
												</div>
											</div>
										</div>
										<div class="form-employeeId form-group regFormFieldGroup">
											<label for="employeeId">Account Code
												<span class="required fm_fntRed">*</span>
												<span id="employeeIdToolTip"
													  class="tip"
													  data-original-title="Account Code must start with SUS/SCA"
													  data-toggle="tooltip"
													  data-placement="right"
													  title="">
													<span class="fa fa-question-circle"></span>
												</span>
											</label>
											<div class="clearfix">
												<div class="col-lg-2 lftPad_0">
													<form:input id="employeeId" type="text" name="employeeId"
														class="form-control width195 pull-left" path="employeeId"
														onblur="resetDisabledCompanyFields();getUnits(true);validateAccount();" maxlength="11" />
													<div class="errorMsg fm_fntRed width237" style="display: none;">Please enter Employee ID</div>

													<!-- For employee id error-->
													<div class="errorMsg errorEmployeeId fm_fntRed width215" id="errorEmployeeId"></div>
												</div>
											</div>
										</div>

								<div id="registration-referred-by" class="form-group regFormFieldGroup">
									<div class="form-accCode form-group regFormFieldGroup form-b2bSub garagegurus-body">
										<div class="regFormFieldGroup form-reg-b2t" >
											<br><label for="addressSame">Did someone refer you?</label>
											<br/>
											<form:radiobutton path="refered" id="referredBy" name="referredBy" label="Yes" value="Yes" class="lftMrgn_5"  onclick="$('.forReferBy').show();"/> &nbsp;&nbsp;&nbsp;&nbsp;
											<form:radiobutton path="refered" id="referredBy" name="referredBy" label="No" value="No"  class="lftMrgn_5"  onclick="$('.forReferBy').hide();"/>
										</div>
									</div>

									<div class="form-group regFormFieldGroup forReferBy garagegurus-body" style="display:none;">
										<label class="" for="referById">Please enter the email address of the person who referred you</label> </br>
										<form:input class="form-control width375" id="referById" path="referredBy" type="email"/>
									</div>
								</div>
							

								<div class="form-group clearfix regFormFieldGroup">
									<label class="" for="email">Email Address<span
										class="required fm_fntRed">*</span> 
										<span class="tip"
											data-original-title="This will become your User ID"
											data-toggle="tooltip" data-placement="right" title=""><span class="fa fa-question-circle"></span>
										</span>
									</label>
									<div class="clearfix">
										<div class="col-lg-2 lftPad_0">
											<form:input id="email" type="email" name="email"
												class="form-control width195 pull-left" path="email" Required="true"
												onchange="getUnits(true);validateEmail();"/>
											<div class="errorMsg fm_fntRed width215" id="errorEmail"></div>
											<div class="errorMsg fm_fntRed width215" style="display: none;">Please enter Email Address</div>
										</div>

										<div class="col-lg-10">
											<div class="lftPad_20 pull-left">
												<p><spring:theme code="text.Gmailsignup.info" text="Text Gmailsignup"/></p>
											</div>
										</div>
									</div>
								</div>

								<div class="clearfix form-group regFormFieldGroup">
										<label class="" for="confirmEmail">
											Confirm Email Address
											<span class="required fm_fntRed">*</span>
											<span class="tip" data-original-title="This must match the email address entered above" data-toggle="tooltip" data-placement="right" title="">
												<span class="fa fa-question-circle"></span>
											</span>
										</label>
									<div class="clearfix">
										<div class="col-lg-2 lftPad_0">
											<form:input id="confirmEmail" type="email" name="confirmEmail"
												class="form-control width195" path="confirmEmail" Required="true"
												onchange="validateConfirmEmail();"/>
											<div class="errorMsg fm_fntRed width215" id="errorConfirmEmail"></div>
											<div class="errorMsg fm_fntRed width237" style="display: none;">Please enter Confirm Email Address</div>
										</div>
									</div>
								</div>

								<div class="clearfix form-group regFormFieldGroup">
									<label class="" for="setnewpwd">Password<span
										class="required fm_fntRed">*</span> <span class="tip"
										data-original-title="<spring:theme code="text.Password.Validation.Error" text="Password validation constraints"/>"
										data-toggle="tooltip" data-placement="right" title="">
											<span class="fa fa-question-circle"></span>
									</span></label>
									<div class="clearfix">
										<div class="col-lg-5 col-md-5 col-sm-8 lftPad_0">
											<form:input id="setnewpwd" type="password"
											class="form-control" name="setnewpwd" value=""
											path="password" maxlength="26" Required="true"
											onkeypress="javascript:getpassword_reg()"
											onblur="validatePassword()" />
										</div>
									<div class="errorMsg fm_fntRed width195" id="errorPwd"></div>
									<div class="errorMsg fm_fntRed width220"
										style="display: none;">Please enter Password</div>
									</div>
								</div>
								<div class="clearfix form-group regFormFieldGroup">
									<label for="confPassword">Confirm
											Password<span class="required fm_fntRed">*</span>
										</label>
									<form:input id="confPassword" type="password"
										class="form-control width237" name="confPassword" value=""
											path="confpwd" maxlength="26" Required="true" onblur="validateConfPassword()"/>
										<div class="errorMsg fm_fntRed" id="errorCpwd"></div>
									<div class="errorMsg fm_fntRed" style="display: none;">Please
										enter Confirm Password</div>
								</div>
								<div class="form-group regFormFieldGroup">
									<label class="" for="firstName">First Name<span
										class="required fm_fntRed">*</span></label>
									<form:input type="text" id="firstName" name="firstName"
										class="form-control width375" path="firstName"
										Required="true" />
									<div class="errorMsg fm_fntRed" style="display: none;">Please
										enter First Name</div>
								</div>
								<div class="form-group regFormFieldGroup">
									<label class="" for="lastName">Last Name<span
										class="required fm_fntRed">*</span></label>
									<form:input type="text" id="lastName" name="lastName"
										class="form-control width375" path="lastName" Required="true" />
									<div class="errorMsg fm_fntRed" style="display: none;">Please
										enter Last Name</div>
								</div>

								<div class="form-group regFormFieldGroup form-address1">										
									<label class="" for="contactAddress">
										<spring:theme code="address.type.fullAddress" text="Full Address" /><span class="required fm_fntRed">*</span>
									</label>
			
									<form:input id="contactAddress" type="text"
												name="Address line 1" placeholder="Address"
												class="form-control width375" path="addressline1"
												Required="true" />
													
									<div class="errorMsg fm_fntRed" style="display: none;">
										<spring:theme code="text.account.profile.contactAddress.invalid" text="Please enter Contact Address" />
									</div>
								</div>
			
								<div class="form-group regFormFieldGroup form-address2">
									<form:input id="contactAddress2" type="text"
												name="Address line 2" placeholder="Address"
												class="form-control width375 topMargn_10" path="addressline2" />
								</div>
			
								<div class="form-group regFormFieldGroup form-city">
									<label class="" for="city">
										<spring:theme code="address.city" text="City" /><span class="required fm_fntRed">*</span>
									</label>
			
									<form:input id="city" type="text" name="city"
												placeholder="City" class="form-control width375" path="city"
												Required="true" />
			
									<div class="errorMsg fm_fntRed" style="display: none;">Please enter City</div>
								</div>

								<div class="form-group regFormFieldGroup form-country">
									<label class="" for="pcountry">Country<span class="required fm_fntRed">*</span></label>

									<form:select path="country" mandatory="true" id="pcountry" name="pcountry" placeholder="" class="form-control width165">

										<c:choose>
											<c:when test="${fmdata.country eq null}">
												<option value="" selected="selected">Select</option>
											</c:when>
											<c:otherwise>
												<option value="${fmdata.country}">${countrydata.name}</option>
											</c:otherwise>
										</c:choose>

										<c:forEach items="${countryData}" var="iso">
											<option value="${iso.isocode}">${iso.name}</option>
										</c:forEach>

									</form:select>
									<div class="errorMsg fm_fntRed" style="display: none;">Please select a country</div>
								</div>

								<div class="form-group regFormFieldGroup form-state" id="userState">
									<label class="" for="state">
										<spring:theme code="address.stateOrProvince" text="State / Province" /><span class="required fm_fntRed">*</span>
									</label>

									<form:select id="state" type="text" name="state"
													class="form-control width165" path="state" Required="true"
													onBlur="getCountry()">
			
										<c:forEach items="${regionsdatas}" var="reg">
											<c:forEach items="${reg}" var="val">
												<c:if test="${fn:contains(val.isocode,'US-')}">
													<c:choose>
														<c:when test="${fmdata.state eq val.isocode}">
															<option value="${val.isocode}" selected="selected">${val.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${val.isocode}">${val.name}</option>
														</c:otherwise>
													</c:choose>
												</c:if>
			
												<c:if test="${fn:contains(val.isocode,'CA-')}">
													<c:choose>
														<c:when test="${fmdata.state eq val.isocode}">
															<option value="${val.isocode}" selected="selected">${val.name}</option>
														</c:when>
														<c:otherwise>
															<option value="${val.isocode}">${val.name}</option>
														</c:otherwise>
													</c:choose>
												</c:if>
											</c:forEach>
										</c:forEach>
			
									</form:select>

									<div class="errorMsg fm_fntRed" style="display: none;">
										<spring:theme code="address.stateOrProvince.invalid" text="Please select a State / Province" />
									</div>
								</div>

								<div class="form-group regFormFieldGroup form-zipcode">
									<label class="" for="zip">ZIP / Postal Code<span class="required fm_fntRed">*</span></label>
									<form:input type="text" id="zip" name="zip" placeholder="ZIP Code" class="form-control width175" path="zipCode" />
									<div class="errorMsg fm_fntRed" style="display: none;">Please enter ZIP / Postal Code</div>
								</div>

								<div class="form-group form-b2bSub">
									<ul class="list-group checkboxGroup">
										<li class="list-group-item">
											<label for="alert">
												<form:checkbox id="alert" path="techAcademySubscription" />
											&nbsp;Subscribe to receive new product alerts, promotions and training opportunities.
											</label>
										</li>
									</ul>
								</div>

								<%-- Shows the disabled company information fields --%>
								<c:import url="../../fragments/account/b2bCompanyFragmentReadOnly.jsp" />

							</div>

								<div id="registration-garage-rewards">
									<div class="panel-heading garagegurus form-b2bSub">
										<h3 class="panel-title text-uppercase">Garage Rewards</h3>
										<span class="pull-right "></span>
									</div>
									<br/>
									<div class="form-group regFormFieldGroup form-b2bSub">
										<ul class="list-group checkboxGroup form-sameaddress">
											<li class="list-group-item">
												<label class="" for="GarageRewardMember">
													<form:checkbox id="GarageRewardMember"
														path="isGarageRewardMember" onClick="toggleGarageRewardPromoCode(this)" checked="checked" />
													&nbsp;Yes, join the Garage Rewards loyalty program and earn free gear!</br>By registering for a Garage Rewards account, you agree to the <a data-toggle="modal" href="#terms">Garage Rewards Loyalty Program Terms and Conditions</a></label>
											</li>
										</ul>
									</div>

									<div class="form-group regFormFieldGroup" id="form-ifGarageGuruChecked">
										<div class="form-group regFormFieldGroup form-b2bSub">
											<label class="" for="promoCode">Enter Promo Code here (if applicable): </label>
											<form:input type="text" path="promoCode"  />
										</div>
									</div>
								</div>

								<div class="regFormFieldGroup form-B2Bbody form-roles">
									<div class="regFormFieldGroup form-reg">
										<label class="" for="fmRole">Access Required<span
											class="fm_fntRed">*</span>
										</label>
										<br />
										<span id="myspan1">
											<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
											label="Full Access" value="FMFullAccessGroup"
											class="lftMrgn_5" checked="checked" />
										</span>
										<br />
										<span id="myspan2">
											<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
											label="Limited Access - Can Place Orders / Cannot View Invoices"
											value="FMNoInvoiceGroup" />
										</span>
										<br />
										<span id="myspan3">
											<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
												label="View Only - Cannot Place Orders / View Invoices"
												value="FMViewOnlyGroup" />
										</span>
										<br />
									</div>
								</div>
							</div>
								<!--Subscription for B2b Ends-->

								<%-- Submit Button --%>
								<button id="regsubmit"
									class="btn btn-sm btn-fmDefault text-uppercase registrationBtns"
									type="submit">Submit</button>
									
							</div>

						</form:form>
					</div>

				</div>
			</div>
		</section>
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
	
	<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="terms" class=" modal fade termsConditions shipToModel in" style="display: none;"><div class="modal-dialog">
		<button data-dismiss="modal" class="close" type="button" ><span class="fa fa-close" aria-hidden="true"></span><span class="sr-only">Close</span></button>
		<div class="modal-content">
			<div class="modal-body">
				<div class="termsConditionContent">
					<c:set var="fmComponentName" value="registrationloginblock" scope="session" />
					<cms:pageSlot position="TermsAndConditions" var="feature">
						<cms:component component="${feature}" />
					</cms:pageSlot>
				</div>
			</div>
		</div>
	</div>
					
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>



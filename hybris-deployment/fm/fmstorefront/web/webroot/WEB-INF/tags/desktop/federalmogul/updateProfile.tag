<%@ attribute name="selected" 	required="false" type="java.lang.String" %>

<%@ tag body-content="empty" 	trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c" 			uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" 		uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" 		uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" 		uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" 			uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" 		uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" 		tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="formElement"	tagdir="/WEB-INF/tags/desktop/formElement" %>
<%@ taglib prefix="common" 		tagdir="/WEB-INF/tags/desktop/common" %>


<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
	<div class="manageUserPanel rightHandPanel clearfix">
		<div id="globalMessages">
			<common:globalMessages />
		</div>

		<h2 class="text-uppercase"><spring:theme code="text.account.profile.updateProfile" text="Update PROFILE"/></h2>
		<p class="reqField topMargn_10">
			<span class="fm_fntRed"><spring:theme code="required" text="*"/></span><spring:theme code="user.requiredFields" text="Required Fields"/>
		</p>

		<div class="regFrm">
			<form:form action="update-profile" method="post" commandName="updateProfileForm"
						class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetails">

				<div class=" addressBookFillDetailsPanelFirst">
					<div class="form-group regFormFieldGroup width270">
				
						<formElement:formSelectBox idKey="user.title" 
							path="titleCode" skipBlank="false" labelKey="user.title"
							skipBlankMessageKey="form.select.empty" items="${titleData}" 
							selectCSSClass="width270" />
						
					</div>

					<div class="form-group  regFormFieldGroup">
				
						<label class="" for="firstName">
							<spring:theme code="user.firstName" text="First Name"/><span class="required fm_fntRed"><spring:theme code="required" text="*"/></span>
						</label>

						<form:input id="firstName" class="form-control width270" type="text" required="required" name="firstName" path="firstName" />

						<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.firstName.blank" text="First Name should not be blank"/></div>
													
					</div>

					<div class="form-group  regFormFieldGroup">
						<label class="" for="surname">
							<spring:theme code="user.lastName" text="Last Name"/><span class="required fm_fntRed"><spring:theme code="required" text="*"/></span>
						</label>

						<form:input type="text" id="surname" name="surname" class="form-control width270" path="lastName" required="required" />

						<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.lastName.blank" text="Last Name should not be blank"/></div>		
					</div>

					<div class="form-group  regFormFieldGroup">
						<label class=""><spring:theme code="user.email" text="Email"/></label>
						<p>${fn:escapeXml(email)}</p>
					</div>

					<sec:authorize ifNotGranted="ROLE_FMCSR,ROLE_FMBUVOR">

						<div class="form-group  regFormFieldGroup ">
							<ul class="list-group checkboxGroup">
								<li class="list-group-item">
									<label for="subscribe">
										<form:checkbox path="newsLetterSubscription" id="subscribe" />&nbsp; <spring:theme code="text.account.profile.subscribeToFmmpNewsletters"
												text="Subscribe to Federal-Mogul Motorparts Newsletters" />
									</label>
								</li>
							</ul>
						</div>
					</sec:authorize>
				</div>

				<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMB2SB,ROLE_FMB2T,ROLE_ANONYMOUS,ROLE_FMBUVOR,ROLE_FMB2C">
				
					<sec:authorize ifAnyGranted="ROLE_FMB2BB">
						<div class="form-group regFormFieldGroup" id="form-addresssamecheckbox">
							<ul class="list-group checkboxGroup form-sameaddress">
								
								<li class="list-group-item">
									<form:checkbox id="addressSame" onClick="setHomeAddressToCompanyAddress(this)" path="isSameAddress" />
										&nbsp;<spring:theme code="text.account.profile.useCompanyAddressAsHomeAddress" text="Use my Company address as my Home address" />
								</li>
							</ul>
						</div>
					</sec:authorize>

					<div class="form-group regFormFieldGroup form-address1">										
						<sec:authorize ifAnyGranted="ROLE_FMB2BB">
							<label class="" for="contactAddress">
								<spring:theme code="address.type.homeAddress" text="Home Address" /><span class="required fm_fntRed">*</span>
							</label>
						</sec:authorize>

						<sec:authorize ifAnyGranted="ROLE_FMB2SB,ROLE_FMB2T,ROLE_ANONYMOUS,ROLE_FMBUVOR,ROLE_FMB2C">
							<label class="" for="contactAddress">
								<spring:theme code="address.type.fullAddress" text="Full Address" /><span class="required fm_fntRed">*</span>
							</label>
						</sec:authorize>

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

					<div class="form-group  regFormFieldGroup  form-country" id="userCountry">
						<label class="" for="pcountry">
							<spring:theme code="address.country" text="Country" /><span class="required fm_fntRed">*</span>
						</label>

						<form:select path="country" mandatory="true" id="pcountry"
										name="pcountry" placeholder="" class="form-control width165"
										Required="true" onBlur="getRegions()">

							<c:forEach items="${countryData}" var="iso">
								<c:choose>
									<c:when test="${updateProfileForm.country eq iso.isocode}">
										<option value="${iso.isocode}" selected="selected">${iso.name}</option>
									</c:when>
									<c:otherwise>
										<option value="${iso.isocode}">${iso.name}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>

						</form:select>

						<div class="errorMsg fm_fntRed" style="display: none;">
							<spring:theme code="address.country.invalid" text="Please select a Country" />
						</div>
					</div>

					<div class="form-group  regFormFieldGroup form-state" id="userState">
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
											<c:when test="${updateProfileForm.state eq val.isocodeShort}">
												<option value="${val.isocode}" selected="selected">${val.name}</option>
											</c:when>
											<c:otherwise>
												<option value="${val.isocode}">${val.name}</option>
											</c:otherwise>
										</c:choose>
									</c:if>

									<c:if test="${fn:contains(val.isocode,'CA-')}">
										<c:choose>
											<c:when test="${updateProfileForm.state eq val.isocodeShort}">
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
							<spring:theme code="address.stateOrProvince.invalid" text="Please select a State / Province" /></div>
					</div>

					<div class="form-group  regFormFieldGroup form-zipcode">
						<label class="" for="zip">
							<spring:theme code="address.zipOrPostalCode" text="ZIP / Postal Code" /><span class="required fm_fntRed">*</span>
						</label>

						<form:input type="text" id="zip" name="zip"
									placeholder="ZIP Code" class="form-control width175"
									path="zipCode" Required="true" />

						<div class="errorMsg fm_fntRed" style="display: none;">
							<spring:theme code="address.zipOrPostalCode.invalid" text="Please enter ZIP / Postal Code" />
						</div>
					</div>

					<div class="form-group  regFormFieldGroup clearfix form-phonenumber">
						<div class="pull-left">
							<sec:authorize ifAnyGranted="ROLE_FMB2BB">
								<label class="" for="phone">
									<spring:theme code="text.homePhoneNumber" text="Home Phone Number" /><%--<span class="required fm_fntRed">*</span> --%>
								</label>
							</sec:authorize>

							<sec:authorize ifAnyGranted="ROLE_FMB2SB,ROLE_FMB2T,ROLE_FMBUVOR,ROLE_FMB2C">
								<label class="" for="phone">
									<spring:theme code="text.phoneNumber" text="Phone Number" /><%--<span class="required fm_fntRed">*</span> --%>
								</label>
							</sec:authorize>

							<form:input type="tel" id="phone" name="companyFax"
										maxlength="15" placeholder="(555)555-5555"
										class="form-control width165" path="phoneno" />
						</div>
					</div>
							
					<sec:authorize ifAnyGranted="ROLE_FMB2BB">	
						<form:hidden path="accCode" value="${customerdata.unit.uid}" />
					</sec:authorize>
				</sec:authorize>

				<sec:authorize ifAnyGranted="ROLE_FMB2SB,ROLE_FMB2T">
					<div style="" class="panel  panel-primary panel-frm panel-frm-filled">
						<div class="panel-body">
							<div class="panel-heading garagegurus form-b2bSub">
								<h3 class="panel-title text-uppercase"><spring:theme code="text.garageGurusTM" text="Garage Gurus&#8482;" /></h3>
								<span class="pull-right"></span>
							</div>
							<div class="form-accCode form-group regFormFieldGroup form-b2bSub garagegurus-body">
								<div class="regFormFieldGroup form-reg-b2t" >
									<br>
									<label for="addressSame"><spring:theme code="text.account.profile.areYouEnrolledInGarageGurusTrainingProgram" 
										text="Are you enrolled in the Garage Gurus&#8482; Training Program?" />
									</label>
									<br/>
									<form:radiobutton path="loyaltySignup" id="fmRole" name="loyaltySignup" label="Yes" value="Yes" class="lftMrgn_5" onclick="$('.forLoyaltyRewards').show();"/> &nbsp;&nbsp;&nbsp;&nbsp;
									<form:radiobutton path="loyaltySignup" id="fmRole" name="loyaltySignup" label="No" value="No" class="lftMrgn_5" onclick="$('.forLoyaltyRewards').hide();"/>
								</div>
							</div>
							<div class="form-group regFormFieldGroup forLoyaltyRewards garagegurus-body" 
										style="${updateProfileForm.loyaltySignup eq true ? 'display: block;' : 'display: none;'}">
								<label class="" for="lmsSigniId">What is your User ID?</label> </br>
								<form:input class="form-control width375" id="lmsSigniId" path="lmsSigniId" type="text"/>
							</div>
						</div>
					</div>

					<!-- Start of New Profile changes  -->
					<div style="" class="panel  panel-primary panel-frm panel-frm-filled">

						<div class="panel-body">
							<div class="panel-heading garagegurus form-b2bSub">
								<h3 class="panel-title text-uppercase"><spring:theme code="text.garageRewards" text="Garage Rewards" /></h3>
								<span class="pull-right"></span>
							</div>
							<br>

							<sec:authorize ifNotGranted="ROLE_FMB2T">
								<div class="form-group  regFormFieldGroup form-b2bSub">
									<ul class="list-group checkboxGroup form-sameaddress">
										<li class="list-group-item">
											<label for="GarageRewardMember"	class="">
												<form:checkbox id="GarageRewardMember" path="isGarageRewardMember" 
																onClick="GarageRewardMemberQuestion(this)" />
												&nbsp;<spring:theme code="yesJoinTheGarageRewardsLoyaltyProgram" 
																	text="Yes, join the Garage Rewards loyalty program and earn free gear!" />
												<br>
												<spring:theme code="text.account.profile.garageRewardsLoyalProgramTermsPart1" 
																text="By registering for a Garage Rewards account, you agree to the " />
												<a href="#terms" data-toggle="modal">
													<spring:theme code="text.account.profile.garageRewardsLoyalProgramTermsPart1" 
																	text="Garage Rewards Loyalty Program Terms and Conditions" />
												</a>
											</label>
										</li>
									</ul>
								</div>
	
								<div id="form-ifGarageGuruChecked" class="form-group regFormFieldGroup" 
										style="${updateProfileForm.isGarageRewardMember eq true ? 'display: block;' : 'display: none;'}">
									<div class="form-group regFormFieldGroup form-b2bSub">
										<label for="promoCode" class="">
											<spring:theme code="text.account.profile.enterPromoCodeHereIfApplicable"
															text="Enter Promo Code here (if applicable)" />:&nbsp;
										</label>
										<form:input type="text" path="promoCode" />
									</div>
								</div>
							</sec:authorize>

							<!--Subscription for B2b Starts-->
							<div id="tech-rewards-questions" class="form-group regFormFieldGroup" 
									style="${updateProfileForm.isGarageRewardMember eq true ? 'display: block;' : 'display: none;'}">
								<ul class="checkboxGroup">
									<li class="list-group-item">
										<label for="Preference" class="">
											<spring:theme code="text.account.profile.pleaseAnswerAFewMoreQuestions"
															text="Please answer a few more questions so that we may customize your rewards based on your preference." />
										</label>
									</li>
									<li class="list-group-item">
										<label for="aboutShop" class="">
											<spring:theme code="text.account.profile.whatBestDescribesYourShop"
															text="What best describes your shop?" />
										</label>
									</li>
								</ul>

								<form:select path="aboutShop" id="aboutShop"
												name="cars" class="form-control width165" onchange="onAboutShop(this)">
									<option value="Select">Select</option>

									<c:forEach items="${aboutShop}" var="reg">
										<c:choose>
											<c:when test="${reg.value eq updateProfileForm.aboutShop}">
												<option selected="selected" value="${reg.value}">${reg.key}</option>
											</c:when>
											<c:otherwise>
												<option value="${reg.value}">${reg.key}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
						
								<div class="shopBanner" style="display: none;">
									<br /> 
									<label class="" for="shopBanner">
										<spring:theme code="text.account.profile.bannerProgramName"
														text="Banner Program Name" />
									</label>
									<form:input class="form-control width175" id="shopBanner" path="shopBanner" type="text" />
								</div>

								<br /> 
								<label class="" for="shopType">
									<spring:theme code="text.account.profile.typeOfShop" text="Type of Shop" />
								</label>
								<br />

								<form:select path="shopType" id="shopType" name="cars" class="form-control width165">
									<option value="Select">Select</option>

									<c:forEach items="${shopType}" var="shop">
										<c:choose>
											<c:when test="${shop.value eq updateProfileForm.shopType}">
												<option selected="selected" value="${shop.value}">${shop.key}</option>
											</c:when>
											<c:otherwise>
												<option value="${shop.value}">${shop.key}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>

								<br/>
								<label class="" for="shopBays">
									<spring:theme code="text.account.profile.numberOfBays" text="Number of Bays" />
								</label>
								<br />

								<form:select path="shopBays" id="shopBays" name="cars" class="form-control width165">
									<option value="Select">Select</option>

									<c:forEach items="${shopBays}" var="bay">
										<c:choose>
											<c:when test="${bay.value eq updateProfileForm.shopBays}">
												<option selected="selected" value="${bay.value}">${bay.key}</option>
											</c:when>
											<c:otherwise>
												<option value="${bay.value}">${bay.key}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>

								<br>
								<label for="shopBays" class="">
									<spring:theme code="text.account.profile.mostInterestedIn"
													text="What are you most interested in? (Check all that apply)" />
								</label>
								<br>

								<ul class="list-group checkboxGroup">
									<c:forEach items="${mostIntersted}" var="mostInterst" >
										<li class="list-group-item">
											<form:checkbox id="${mostInterst.key}" value="${mostInterst.value}" path="mostIntersted" />
											&nbsp;${mostInterst.key}
										</li>
									</c:forEach>
								</ul>

								<br>
								<label for="brands" class="">
									<spring:theme code="text.account.profile.brandsCurrentlyInstalling" 
													text="What brands are you currently installing? (Check all that apply)" />
								</label>
								<br>

								<ul class="list-group checkboxGroup">
									<c:forEach items="${brands}" var="brand">
										<li class="list-group-item">
											<form:checkbox id="${brand.key}" value="${brand.value}" path="brands" />
											&nbsp;${brand.key}®
										</li>
									</c:forEach>
								</ul>
							</div>

							<c:choose>
								<c:when test="${updateProfileForm.loyaltySignup eq true and updateProfileForm.isGarageRewardMember eq true}">
									<form:hidden path="isLoyaltyRequestedMember" value="true"/>
								</c:when>
								<c:otherwise>
									<form:hidden path="isLoyaltyRequestedMember" value="false"/>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<!-- End of New Profile Changes  -->
				</sec:authorize>					

				<div class="clearfix">
					<button class="btn btn-sm btn-fmDefault text-uppercase registrationBtns" type="submit">
						<spring:theme code="user.UpdateProfile.submit" text="Update" />
					</button>
				</div>
			</form:form>
		</div>
	</div>
</div>

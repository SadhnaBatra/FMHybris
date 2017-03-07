<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel clearfix">
		<h2 class="text-uppercase">Add Address</h2>

		<p class="reqField topMargn_10">
			<span class="fm_fntRed">*</span> Required Fields
		</p>

		<form:form action="admin-edit-address" method="post"
			commandName="addressForm"
			class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetails">
			
			

			<div class=" addressBookFillDetailsPanelFirst">
				<%-- <div class="form-group  regFormFieldGroup ">

					
					<label class="" for="Title">Title<span class="fm_fntRed">*</span></label>
					<form:select path="titleCode" id="Title" name="Title"
						class="form-control width270">
						<c:forEach items="${titleData}" var="title">
							<option value="${title.code}">${title.name}</option>
						</c:forEach>
					</form:select>
				</div>
				<div class="form-group  regFormFieldGroup">
					<label class="" for="firstname">First Name<span
						class="fm_fntRed">*</span></label>
					<form:input path="firstName" type="text" id="firstnam"
						name="firstnam" placeholder="" class="form-control width270" />
				</div>
				<div class="form-group  regFormFieldGroup ">
					<label class="" for="surname">Last Name<span
						class="fm_fntRed">*</span></label>
					<form:input path="lastName" type="text" id="surname" name="surname"
						placeholder="" class="form-control width270" />
				</div> --%>
				<div class="form-group  regFormFieldGroup">
				<form:hidden path="addressId" />
					<label class="" for="Addressline1">Address<span
						class="fm_fntRed">*</span></label>
					<form:input path="line1" id="Addressline1" type="text"
						name="Addressline1" placeholder="" class="form-control width270" />
					<form:input path="line2" id="Addressline2" type="text"
						name="Addressline2" placeholder=""
						class="form-control width270 topMargn_10" />
				</div>

				<div class="form-group  regFormFieldGroup ">
					<label class="" for="Town/City">City<span class="fm_fntRed">*</span></label>
					<form:input path="townCity" type="text" id="Town/City"
						name="Town/City" placeholder="" class="form-control width270" />
				</div>

				<sec:authorize ifAnyGranted="ROLE_FMB2B,ROLE_FMB2C">
					<div class="form-group  regFormFieldGroup ">
						<label class="" for="ZipCode">State/Province<span
							class="fm_fntRed">*</span></label>
						<form:select path="region" type="text" id="ZipCode" name="ZipCode"
							placeholder="" class="form-control width270">
							<c:forEach items="${regionData}" var="region">
								<c:forEach items="${region}" var="value">
									<c:if test="${fn:contains(value.isocode,'US-')}">
										<option value="${value.isocode}">${value.name}</option>
									</c:if>
								</c:forEach>
							</c:forEach>
						</form:select>


					</div>
				</sec:authorize>

				<sec:authorize ifAnyGranted="ROLE_FMB2SB">
					<div class="form-group  regFormFieldGroup ">
						<label class="" for="ZipCode">State/Province<span
							class="fm_fntRed">*</span></label>
						<form:select path="region" type="text" id="ZipCode" name="ZipCode"
							placeholder="" class="form-control width270">
							<c:forEach items="${regionData}" var="region">


								<option value="${region.isocode}">${region.isocode}</option>


							</c:forEach>
						</form:select>


					</div>
				</sec:authorize>


				<div class="form-group  regFormFieldGroup ">
					<label class="" for="ZipCode">ZIP/Postal Code<span
						class="fm_fntRed">*</span></label>
					<form:input path="postcode" type="text" id="ZipCode" name="ZipCode"
						placeholder="" class="form-control width270" />
				</div>
				<div class="form-group  regFormFieldGroup ">
					<label class="" for="Country">Country<span
						class="fm_fntRed">*</span></label>
					<form:select path="countryIso" id="Country" name="Country"
						class="form-control width270">
						<c:forEach items="${countryData}" var="country">
							<c:if test="${fn:contains(country.isocode,'US')}">
								<option value="${country.isocode}">${country.name}</option>
							</c:if>
						</c:forEach>
					</form:select>
				</div>
			</div>
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetailsPanelFirst">
				<button class="btn btn-fmDefault">Save</button>

				<%-- <c:url value="/my-fmaccount/address-book" var="backToAddressbook"/> --%>
				<%-- <button class="btn btn-fmDefault" onclick="window.location='${backToAddressbook}'"><spring:theme code="text.account.profile.cancel" text="Cancel"/></button> --%>
				<button class="btn btn-fmDefault">
					<spring:theme code="text.account.profile.cancel" text="Cancel" />
				</button>
			</div>
		</form:form>
	</div>

</div>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel clearfix">
		<h2 class="text-uppercase">Add Address</h2>

		<p class="reqField topMargn_10">
			<span class="fm_fntRed">*</span> Required Fields
		</p>

		<form:form action="add-address" method="post"
			commandName="addressForm"
			class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetails">

			<div class=" addressBookFillDetailsPanelFirst">
				<div class="form-group  regFormFieldGroup ">

					<form:hidden path="addressId" />
					<formElement:formSelectBox idKey="user.title" path="titleCode"
						mandatory="true" skipBlank="false" labelKey="user.title"
						skipBlankMessageKey="form.select.empty" items="${titleData}"
						selectCSSClass="form-control width270" />


					<%-- <label class="" for="Title">Title<span class="fm_fntRed">*</span></label>
                 <form:select path="titleCode" id="Title"  name="Title" class="form-control width270" >
                  <c:forEach items="${titleData}" var="title">
                  <option value="${title.code}">${title.name} </option>
                  </c:forEach>
                </form:select> --%>


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
				</div>
				<div class="form-group  regFormFieldGroup">
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

				<div class="form-group  regFormFieldGroup ">
					<label class="" for="Country">Country<span
						class="fm_fntRed">*</span></label>


					<form:select path="countryIso" id="Country" name="Country"
						class="form-control width270" onBlur="getRegionsForaddedit()">
						<option value="${addressForm.countryIso}">${addressForm.countryIso}</option>
						<option value="Default">Select</option>
						<c:forEach items="${countryData}" var="country">
							<c:if test="${fn:contains(country.isocode,'US')}">
								<option value="${country.isocode}">${country.name}</option>
							</c:if>
							<c:if test="${fn:contains(country.isocode,'CA')}">
								<option value="${country.isocode}">${country.name}</option>
							</c:if>
						</c:forEach>
					</form:select>
				</div>


				<sec:authorize ifAnyGranted="ROLE_FMB2B,ROLE_FMB2C,ROLE_FMB2T">
					<div class="form-group  regFormFieldGroup " id="addEditState">
						<label class="" for="ZipCode">State/Province<span
							class="fm_fntRed">*</span></label>
						<form:select path="region" type="text" id="ZipCode" name="ZipCode"
							placeholder="" class="form-control width270">
							<!-- 	<option value="Default">Select</option> -->
								<option value="${addressForm.region}">${addressForm.region}</option>
							<c:forEach items="${regionData}" var="region">
								<c:forEach items="${region}" var="value">
									<c:if test="${fn:contains(value.isocode,'US-')}">
										<option value="${value.isocode}">${value.name}</option>
									</c:if>
									<c:if test="${fn:contains(value.isocode,'CA-')}">
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
								<option value="Default">Select</option>
							<c:forEach items="${regionData}" var="region">
							<%-- <c:forEach items="${region}" var="value">
								<option value="${value.isocode}">${value.name}</option>
							</c:forEach> --%>
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

			</div>
			<div
				class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetailsPanelFirst">
				<button class="btn btn-fmDefault">Save</button>

				<c:url value="/my-fmaccount/address-book" var="backToAddressbook" />

				<a href="${backToAddressbook}" class="btn btn-fmDefault"><spring:theme
						code="text.account.profile.cancel" text="Cancel" /></a>

			</div>
		</form:form>
	</div>

</div>
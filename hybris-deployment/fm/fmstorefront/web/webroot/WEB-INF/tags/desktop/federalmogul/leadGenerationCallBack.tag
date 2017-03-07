<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>

<!-- Starts: Manage Account Right hand side panel -->

<div class="row">
	<div class="col-md-12">
		<h1 class="text-uppercase">Contact Form</h1>
	</div>
</div>

<div id="globalMessages" style="font-size: larger;">
	<common:globalMessages />
</div>

<section class="">
	<p class="reqField">
		<span class="fm_fntRed">*</span>Required Fields
	</p>
	<c:url value="/leadGeneration/contact-us-submit" var="encodedUrl" />
	<form:form action="${encodedUrl}" method="post"
		commandName="callBackFormData" class="manageUserForm" id="callBackFormDataForm">
		<div class="leadGen">
		<div class="mngUserborder clearfix">
			<div class="">
				<label class="text-capitalize" for="subjects">subject<span
					class="required fm_fntRed">*</span>
				</label>
				<form:select name="subjects" path="subjects" id="subjects" Required="true" class="form-control width270" value="${callBackFormData.subjects}" onchange="
					var currentSubject = $('#subjects').val();

					switch (currentSubject) {
						case 'Product Inquiry':
							$('#partNumberSection').show();
							$('#brandSection').hide();
							$('#invoiceNumberSection').hide();
							$('#orderNumberSection').hide();
							$('#customerIDSection').hide();
							break;
						case 'Technical Inquiry':
							$('#partNumberSection').show();
							$('#brandSection').show();
							$('#invoiceNumberSection').hide();
							$('#orderNumberSection').hide();
							$('#customerIDSection').hide();
							break;
						case 'Order Inquiry':
							$('#partNumberSection').hide();
							$('#brandSection').hide();
							$('#invoiceNumberSection').show();
							$('#orderNumberSection').show();
							$('#customerIDSection').show();
							break;
						default:
							$('#partNumberSection').hide();
							$('#brandSection').hide();
							$('#invoiceNumberSection').hide();
							$('#orderNumberSection').hide();
							$('#customerIDSection').hide();
							break;
					}
				">
					<option value="">Select Query Type</option>
					<c:forEach items="${leadGenerationSubjects}" var="subjects">
						<option value="${subjects}" <c:if test="${subjects == callBackFormData.subjects}">selected</c:if>>${subjects}</option>
					</c:forEach>
				</form:select>
				   <div class="errorMsg fm_fntRed" style="display:none;">Please Select Query Type </div>
			</div>

			<%-- Optionally-visible fields depending on chosen Query Type --%>
			<div id="partNumberSection" <c:if test="${callBackFormData.subjects != 'Product Inquiry' && callBackFormData.subjects != 'Technical Inquiry'}">style="display:none;"</c:if>>
				<label class="text-capitalize" for="partNumber">Part Number</label>
				<form:input type="text" name="partNumber" id="partNumber" path="partNumber" class="form-control width270" value="${callBackFormData.partNumber}" />
			</div>
			<div id="brandSection" <c:if test="${callBackFormData.subjects != 'Technical Inquiry'}">style="display:none;"</c:if>>
				<label class="text-capitalize" for="brand">Brand</label>
				<form:input type="text" name="brand" id="brand" path="brand" class="form-control width270" value="${callBackFormData.brand}" />
			</div>
			<div id="invoiceNumberSection" <c:if test="${callBackFormData.subjects != 'Order Inquiry'}">style="display:none;"</c:if>>
				<label class="text-capitalize" for="invoiceNumber">Invoice Number</label>
				<form:input type="text" name="invoiceNumber" id="invoiceNumber" path="invoiceNumber" class="form-control width270" value="${callBackFormData.invoiceNumber}" />
			</div>
			<div id="orderNumberSection" <c:if test="${callBackFormData.subjects != 'Order Inquiry'}">style="display:none;"</c:if>>
				<label class="text-capitalize" for="orderNumber">Order Number</label>
				<form:input type="text" name="orderNumber" id="orderNumber" path="orderNumber" class="form-control width270" value="${callBackFormData.orderNumber}" />
			</div>
			<div id="customerIDSection" <c:if test="${callBackFormData.subjects != 'Order Inquiry'}">style="display:none;"</c:if>>
				<label class="text-capitalize" for="customerID">Customer ID</label>
				<form:input type="text" name="customerID" id="customerID" path="customerID" class="form-control width270" value="${callBackFormData.customerID}" />
			</div>

			<div class="">

				<label class="text-capitalize" for="callBackDescription">Message<span
					class="required fm_fntRed">*</span></label>
				<form:textarea class="form-control width448"
					name="callBackDescription" path="callBackDescription"
					id="callBackDescription" maxlength="500" type="text"
					Required="true"></form:textarea>
					<div class="errorMsg fm_fntRed" style="display:none;">Please enter Message</div>
				<span class="char-count"></span>

			</div>
			<div class="pageSubHeading text-capitalize topMargn_20">your
				information</div>
			<div class="">
				<label class="text-capitalize" for="firstName">First name<span
					class="required fm_fntRed">*</span></label>
				<form:input type="text" name="firstName" id="firstName"
					path="firstName" class="form-control width270" Required="true"
					value="${callBackFormData.firstName}" />
					<div class="errorMsg fm_fntRed" style="display:none;">Please enter your First Name </div>
			</div>
			<div class="">
				<label class="text-capitalize" for="lastName">Last name<span
					class="required fm_fntRed">*</span></label>
				<form:input type="text" name="lastName" id="lastName"
					path="lastName" class="form-control width270" Required="true"
					value="${callBackFormData.lastName}" />
					<div class="errorMsg fm_fntRed" style="display:none;">Please enter your Last Name </div>
			</div>
			<div class="">
				<label class="text-capitalize" for="email">Email Address<span
					class="required fm_fntRed">*</span></label>
				<form:input type="email" name="email" id="email" path="email"
					class="form-control width270" Required="true"
					value="${callBackFormData.email}" />
					<div class="errorMsg fm_fntRed" style="display:none;">Please enter your Email Address</div>
			</div>
			<div class="">
				<label class="text-capitalize" for="phoneno">Telephone<span
						class="required fm_fntRed">*</span></label>
				<form:input type="tel" name="phoneno" id="phoneno" path="phoneno" maxlength="15"
							class="form-control width270" Required="required"
							value="${callBackFormData.phoneno}" />
				<div class="errorMsg fm_fntRed" style="display:none;">Please enter Phone Number</div>
			</div>
			<div class="">
				<label class="" for="country">Country</label>
				<form:select id="country" name="country" path="country" class="form-control width270">

					<c:forEach items="${countryData}" var="iso">
						<option value="${iso.isocode}" <c:if test="${iso.isocode == callBackFormData.country}">selected</c:if>>${iso.name}</option>
					</c:forEach>

				</form:select>
			</div>
			<div>
				<label class="" for="bestWayToContact">Best Way to Contact</label>
				<form:select id="bestWayToContact" name="bestWayToContact" path="bestWayToContact" class="form-control width270" onchange="
					var bestWay = $('#bestWayToContact').val();
					if (bestWay === 'Phone') {
						$('#contactPreferences').show();
					} else {
						$('#contactPreferences').hide();
					}
				">
					<option value="Email" <c:if test="${'Email' == callBackFormData.bestWayToContact}">selected</c:if>>Email</option>
					<option value="Phone" <c:if test="${'Phone' == callBackFormData.bestWayToContact}">selected</c:if>>Phone</option>
				</form:select>
			</div>
			<div id="contactPreferences"  <c:if test="${callBackFormData.bestWayToContact != 'Phone'}">style="display:none;"</c:if>>
				<div>
					<label for="contactDays">Best Day to Contact</label>
					<div>
						<form:checkbox path="contactDays" value="Monday"/>&nbsp;Monday&nbsp;&nbsp;
						<form:checkbox path="contactDays" value="Tuesday"/>&nbsp;Tuesday&nbsp;&nbsp;
						<form:checkbox path="contactDays" value="Wednesday"/>&nbsp;Wednesday&nbsp;&nbsp;
						<form:checkbox path="contactDays" value="Thursday"/>&nbsp;Thursday&nbsp;&nbsp;
						<form:checkbox path="contactDays" value="Friday"/>&nbsp;Friday&nbsp;&nbsp;
					</div>
				</div>
				<div>
					<label for="contactTimeOfDays">Best Time to Contact</label>
					<div>
						<form:checkbox path="contactTimeOfDays" value="AM" />&nbsp;AM&nbsp;&nbsp;
						<form:checkbox path="contactTimeOfDays" value="PM" />&nbsp;PM&nbsp;&nbsp;
						<form:select id="contactTimeZone" name="contactTimeZone" path="contactTimeZone" class="form-control width270">
							<option value="">Please Select Your Time Zone</option>
							<option value="PT" <c:if test="${'PT' == callBackFormData.contactTimeZone}">selected</c:if>>Pacific Time</option>
							<option value="MT" <c:if test="${'MT' == callBackFormData.contactTimeZone}">selected</c:if>>Mountain Time</option>
							<option value="CT" <c:if test="${'CT' == callBackFormData.contactTimeZone}">selected</c:if>>Central Time</option>
							<option value="ET" <c:if test="${'ET' == callBackFormData.contactTimeZone}">selected</c:if>>Eastern Time</option>
						</form:select>
					</div>
				</div>
			</div>

		</div>
		</div>

		<div class="profileBtn topMargn_25 btmMrgn_30">
			<button id="submit" class="btn btn-fmDefault" type="submit">Submit</button>
		</div>
		
	</form:form>
</section>



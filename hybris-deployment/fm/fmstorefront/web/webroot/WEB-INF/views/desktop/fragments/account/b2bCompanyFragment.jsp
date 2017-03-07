<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form:form class="registrationB2b" method="POST"
							commandName="fmdata" id="registrationB2b" name="registrationB2b"
							enctype="multipart/form-data">
<div class="form-group  regFormFieldGroup form-b2bRegbody" id="companyDiv" >

					
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyName">Company Name<span
			class="fm_fntRed">*</span></label>
		<form:input type="text" id="companyName" name="company" disabled="true"
			placeholder="Company" class="form-control width375"
			 value="${unitdetails.locName}" path="companyName"/>
	</div>
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyAddress">Company Address<span
			class="fm_fntRed">*</span></label>
		<form:input id="companyAddress" type="text" name="Address line" disabled="true"
			placeholder="address1" class="form-control width375"
			  value="${unitdetails.addresses[0].line1}" path="companyaddressline1"/>
		<form:input id="companyAddress2" type="text" name="Address line 2"
			placeholder="" class="form-control width375 topMargn_10"
			  value="${unitdetails.addresses[0].line2}" disabled="true" path="companyaddressline2"/>
	</div>
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyCity">Company City<span
			class="fm_fntRed">*</span></label>
		<form:input id="companyCity" type="text" name="city" disabled="true"
			placeholder="city" class="form-control width375"   value="${unitdetails.addresses[0].town}" path="companycity"/>
	</div>

	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyZip">Company ZIP / Postal Code<span
			class="fm_fntRed">*</span></label>
		<form:input type="text" id="companyZip" name="companyZip"
			placeholder="zip code" class="form-control width375"
			 value="${unitdetails.addresses[0].postalcode}" disabled="true" path="companyzipCode"/>
	</div>
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="Ccountry">Company Country<span
			class="fm_fntRed">*</span></label>
		<form:select id="Ccountry" name="Ccountry" placeholder="country"
			class="form-control width165" disabled="true" path="companycountry">
			<option value="${unitdetails.addresses[0].country.isocode}">${unitdetails.addresses[0].country.name}</option>
	<%-- 		<c:forEach items="${countryData}" var="iso">
				<option value="${iso.isocode}">${iso.name}</option>
			</c:forEach> --%>
		</form:select>

	</div>
	
	
		<div class="form-group  regFormFieldGroup" id="unitState">
		<label class="" for="companyState">Company State / Province<span
			class="fm_fntRed">*</span></label>
		<form:select id="companyState" type="text" name="state" disabled="true"
			placeholder="state" class="form-control width165" path="companystate">
			<option value="${unitdetails.addresses[0].region.isocode}">${unitdetails.addresses[0].region.name}</option>
			<c:forEach items="${regionsdatas}" var="reg">
				<c:forEach items="${reg}" var="val">
					<c:if test="${fn:contains(val.isocode,'US-')}">
						<option value="${val.isocode}">${val.name}</option>
					</c:if>
				</c:forEach>
			</c:forEach>
		</form:select>
	</div>
	<div class="form-group  regFormFieldGroup clearfix">
		<div class="pull-left">
			<label class="" for="companyPhone">Company Phone Number<span
				class="fm_fntRed">*</span>
			</label>
			<form:input type="text" id="companyPhone" name="companyFax"
				placeholder="(555)555-5555" class="form-control width165"
				value="${unitdetails.addresses[0].phone1}" disabled="true" path="companyPhoneNumber"/>
		</div>
		<div class="pull-left lftMrgn_20">
			<label class="" for="companyFax">Company Fax</label>
			<form:input type="text" id="companyFax" name="companyFax"
				placeholder="(555)555-5555" class="form-control width165"
				value="${unitdetails.addresses[0].fax}" disabled="true" path="companyFax"/>
		</div>
	</div>
	<%-- <div class="form-group  regFormFieldGroup ">
		<label class="" for="companyWeb">Company Website</label>
		<input type="text" id="companyWeb" name="companyWeb"
			placeholder="Ex. www.companywebsite.com"
			class="form-control width375" value="${unitdetails.addresses[0].url}" disabled="disabled"/>
	</div>
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyAssociation">Association<span
			class="fm_fntRed">*</span></label> <select id="companyAssociation"
			type="text" name="companyAssociation" placeholder=""
			class="form-control width275" disabled="disabled">
			<option value="${unitdetails.association}">${unitdetails.association}</option>

		</select>
	</div> --%>

</div> 
</form:form>
<%--  For account code error--%>
 <script>
 $(document).ready(
			function() {
 afterGetUnits();
			});
 </script>
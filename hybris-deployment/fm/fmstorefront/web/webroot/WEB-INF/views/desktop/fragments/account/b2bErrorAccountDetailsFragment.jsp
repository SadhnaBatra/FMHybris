<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- 	<form:form class="registrationB2b" 
							commandName="fmdata" id="registrationB2b" name="registrationB2b"
							 > --%>
							 
<!-- <div class="lftPad_20 pull-left" id="label-change">
												 
													<p>please enter the correct account code</p>
													<p> Call customer service at XXX.XXX.XXXX</p>
											</div>	 -->						 
							 
<div id="companyDiv" class="form-group  regFormFieldGroup form-b2bRegbody ">
<!--<div id="globalMessages" class="fm_fntRed globalMessagesSpace">
		<common:globalMessages />-->
	</div>
				
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyName">Company Name<span
			class="fm_fntRed">*</span></label>
		<input type="text" id="companyName" name="company" disabled="disabled"
			placeholder="Company" class="form-control width375" 
			 />
	</div>
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyAddress">Company Address<span
			class="fm_fntRed">*</span></label>
		<input id="companyAddress" type="text" name="Address line" disabled="disabled"
			placeholder="Address" class="form-control width375" 
			  value=""/>
		<input id="companyAddress2" type="text" name="Address line 2"
			placeholder="" class="form-control width375 topMargn_10" 
			  value="" disabled="disabled"/>
	</div>
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyCity">Company City<span
			class="fm_fntRed">*</span></label>
		<input id="companyCity" type="text" name="city" disabled="disabled"
			placeholder="City" class="form-control width375"  value=""/>
	</div>

	<div class="form-group  regFormFieldGroup ">
		<label class="" for="companyZip">Company ZIP / Postal Code<span
			class="fm_fntRed">*</span></label>
		<input type="text" id="companyZip" name="companyZip"
			placeholder="ZIP Code" class="form-control width375" 
			 disabled="disabled"/>
	</div>
	<div class="form-group  regFormFieldGroup ">
		<label class="" for="Ccountry">Company Country<span
			class="fm_fntRed">*</span></label>
		<select id="Ccountry" type="text" name="Ccountry" placeholder="country" disabled="disabled"
			class="form-control width165" >
			<option value="${unitdetails.addresses[0].country.isocode}">${unitdetails.addresses[0].country.name}</option>
			<c:forEach items="${countryData}" var="iso">
				<option value="${iso.isocode}">${iso.name}</option>
			</c:forEach>
			</select>
	</div>			
		<div class="form-group  regFormFieldGroup" id="unitState">
		<label class="" for="companyState">Company State / Province<span
			class="fm_fntRed">*</span></label>
		<select id="companyState" type="text" name="state" disabled="disabled"
			placeholder="state" class="form-control width165" >
			<option value="${unitdetails.addresses[0].region.isocode}">${unitdetails.addresses[0].region.name}</option>
			<c:forEach items="${regionsdatas}" var="reg">
				<c:forEach items="${reg}" var="val">
					<c:if test="${fn:contains(val.isocode,'US-')}">
						<option value="${val.isocode}">${val.name}</option>
					</c:if>
				</c:forEach>
			</c:forEach>
													
		</select>

	</div>
	<div class="form-group  regFormFieldGroup clearfix">
		<div class="pull-left">
			<label class="" for="companyPhone">Company Phone Number<span
				class="fm_fntRed">*</span>
			</label>
			<input type="text" id="companyPhone" name="companyFax"
				placeholder="(555)555-5555" class="form-control width165" 
				value="" disabled="disabled"/>
		</div>
		<div class="pull-left lftMrgn_20">
			<label class="" for="companyFax">Company Fax</label>
			<input type="text" id="companyFax" name="companyFax"
				placeholder="(555)555-5555" class="form-control width165" 
				value="" disabled="disabled"/>
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

<%-- </form:form> 
 --%>
<%--  For account code error--%>
 <script>
 $(document).ready(
			function() {
 afterGetUnits();
			});
 </script>
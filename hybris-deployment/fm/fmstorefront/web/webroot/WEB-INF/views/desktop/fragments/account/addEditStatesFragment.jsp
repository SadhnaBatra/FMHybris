<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<form:form method="post" commandName="addressForm"
			class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetails">
<div class="form-group  regFormFieldGroup " id="addEditState">
		<label class="" for="ZipCode">State/Province<span
			class="fm_fntRed">*</span></label>
		<%-- 	<form:input type="text" path="region" /> --%>

		<form:select path="region" type="text" id="ZipCode" name="ZipCode"
			placeholder="" class="form-control width270">
			<!-- 	<option value="Default">Select</option> -->
			<%-- 		<option value="${addressForm.region}">${addressForm.region}</option> --%>
			<c:forEach items="${regionsdatas}" var="reg">
				<option value="${reg.isocode}">${reg.name}</option>
			</c:forEach>
		</form:select>
	</div>


</form:form>


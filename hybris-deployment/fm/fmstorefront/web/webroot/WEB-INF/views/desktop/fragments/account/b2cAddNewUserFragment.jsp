<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form:form  method="post"
				commandName="fmRegistrationForm">

					<div class="" id="parentDiv">

						<label class="">Parent Business Unit<span
							class="fm_fntRed">*</span>
						</label>

						 <form:select path="parentB2BUnit" id="parentUnit"
							name="parentUnit" class="form-control"
							disabled="${not empty param.unit and not empty param.role}"
							skipBlankMessageKey="form.select.empty">
							<option value="${parentB2CUnit}">${parentB2CUnit}</option>
							
						</form:select>

					</div>
					
</form:form>
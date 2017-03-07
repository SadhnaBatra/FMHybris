<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!-- <form:form class="registrationB2b" method="POST" commandName="fmdata"
	id="registrationB2b" name="registrationB2b" > 

<div class=" regFrm">
	<div class="form-group  regFormFieldGroup" id="unitState">-->

		 <label class="" for="companystate">Company State / Province<span
			class="required fm_fntRed" id="companyStateSpan">*</span></label> 
		<form:select id="companyState" type="text" name="companystate"
			placeholder="state" class="form-control width165" path="companystate" Required="true">
			<option value="" selected="selected">Select</option>
			<c:forEach items="${regionsdatas}" var="reg">
				<option value="${reg.isocode}">${reg.name}</option>
			</c:forEach>
		</form:select>
<!--	</div>

 </form:form> -->


<div class="errorMsg fm_fntRed" style="display: none;">Please select a Company State</div>



<script>

 $(document).ready(function () {

									
							$('.regFrm input,select').blur(function(){
								var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
								if($(this).val() == '' && $hasReqField.hasClass('required')){
									$(this).addClass('inputError');
									$(this).parent().find('.errorMsg').show();
								} else if($(this).hasClass('inputError')){
									$(this).removeClass('inputError');
									$(this).parent().find('.errorMsg').hide();
								}
							});
						}); 
 </script>
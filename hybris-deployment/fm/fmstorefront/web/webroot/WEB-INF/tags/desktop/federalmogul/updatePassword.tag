<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
  <div class="manageUserPanel rightHandPanel clearfix">
    <h2 class="text-uppercase"><spring:theme code="text.account.profile.changePassword" text="Update Password"/></h2>
    <p class="reqField topMargn_15"> 
      <span class="required fm_fntRed"><spring:theme code="required" text="*"/></span> <spring:theme code="user.requiredFields" text="Required Fields"/> 
    </p>
    <div id="globalMessages">
			<common:globalMessages />
		</div>
    <div class="regFrm">
      <form:form action="update-password" method="post" commandName="updatePasswordForm" autocomplete="off" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetails">
      <div class="addressBookFillDetailsPanelFirst">
        <div class="form-group regFormFieldGroup">
          <label for="CurrentPassword" class=""><spring:theme code="text.UpdatePassword.current" text="Current Password"/><span class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>
          <form:input type="password" path="currentPassword" mandatory="true" required="required"  value="" name="CurrentPassword" class="form-control width270" id="CurrentPassword"/>
          <div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="text.UpdatePassword.current.alert" text="Please enter your current password"/></div>
        </div>
        <div class="form-group regFormFieldGroup">
          <label for="newPassword" class=""><spring:theme code="text.UpdatePassword.new" text="New Password"/><span class="tip" data-original-title="<spring:theme code="text.Password.Validation.Error" text="Password validation constraints"/>" data-toggle="tooltip" data-placement="right" title=""><span class="required fm_fntRed"><spring:theme code="required" text="*"/></span><span class="fa fa-question-circle"></span></span></label>
          <div class="clearfix"> 
            <form:input type="password" path="newPassword" required="required"  inputCSS="text password strength" mandatory="true" class="form-control width270" id="newPassword" onkeyup="javascript:getpassword()"/>
	          <div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="text.UpdatePassword.new.alert" text="Please enter the new password"/></div>
          </div>
        </div>
        <div class="form-group regFormFieldGroup">
          <label for="currentNewPassword" class=""> <spring:theme code="text.UpdatePassword.confirm" text="Confirm Password"/><span class="required fm_fntRed"><spring:theme code="required" text="*"/></span> </label>
          <form:input type="password" path="checkNewPassword" required="required"  inputCSS="text password" mandatory="true" class="form-control width270" id="currentNewPassword"/>
          <div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="text.UpdatePassword.confirm.alert" text="Please confirm the password"/></div>
        </div>
      </div>
      <button class="btn btn-sm btn-fmDefault text-uppercase registrationBtns" type="submit"><spring:theme code="text.UpdatePassword.submit" text="Change Password"/></button>
    </form:form>
    </div>
  </div>
</div>

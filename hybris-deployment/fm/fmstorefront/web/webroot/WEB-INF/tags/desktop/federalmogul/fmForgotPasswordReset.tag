
<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>

<spring:url value="/password-reset/fmResetPasswordPost"
	var="passwordSaveUrl">
</spring:url>

<section class="customBgBlock">
	<div class="container">
		<div class="row">
			<div class="resetPasswordPanel">
				<div class="col-lg-6 col-lg-offset-3 col-md-4 col-md-offset-2 col-sm-6 col-xs-12">
					<!-- Reset Password -->
					<div class="panel panel-default resetPanel">
						<div class="panel-body">
							<h3 class="resetPwdTitle">
								<span class="fa fa-user fm_fntRed"></span><span
									class="text-uppercase"> RESET YOUR PASSWORD</span>
							</h3>
                            <form:form method="post" commandName="updatePwdForm" id="checkYourMail" class="" role="form">
                                <div style="display: none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                                <div class="hintEmail">Email Address: ${encodedEmailId}</div>
                                <p class="pwdSuggestion">Strong passwords include numbers, letters and punctuation marks.</p>
                                <div id="globalMessages" class="fm_fntRed">
                                    <common:globalMessages />
                                </div> 
                                <div class="form-group">
                                    <label for="setnewpwd">New Password:
                                    <span class="tip" data-original-title="<spring:theme code="text.Password.Validation.Error" text="Password validation constraints"/>" data-toggle="tooltip" data-placement="right" title=""><span class="fa fa-question-circle"></span>
                                    </label>
                                    <div class="input-group col-md-12 col-sm-12 col-xs-12">
                                        <span class="required"></span>
                                        <form:input type="password" path="pwd" value="" inputCSS="text password" mandatory="true" required="required" name="pwd" class="form-control width288" id="setnewpwd" onkeypress="javascript:getpassword_pwdreset()" />
                                    </div>
                                    <span class="errorMsg fm_fntRed" style="display:none;">Please enter your New Password.</span>
                                </div>
                                <div class="form-group">
                                    <label for="CurrentPassword">Confirm Password:</label>
                                    <div class="input-group col-md-12 col-sm-12 col-xs-12">
                                        <span class="required"></span>
                                        <form:input type="password" path="checkPwd" required="required" inputCSS="text password" mandatory="true" value="" name="checkPwd" class="form-control width288" id="CurrentPassword" />
                                    </div>
                                    <span class="errorMsg fm_fntRed" style="display:none;">Please confirm your New Password.</span>
                                </div>
                                <div class="col-xs-4 resetPassCol">
                                    <button id="btn-login" class="btn btn-sm btn-fmDefault">Continue</button>
                                </div>
                            </form:form>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
</section>


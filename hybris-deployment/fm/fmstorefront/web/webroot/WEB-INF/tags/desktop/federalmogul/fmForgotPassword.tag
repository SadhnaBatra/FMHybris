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


<!-- MAIN CONTAINER-->

<section class="customBgBlock">
	<div class="container">
		<div class="row">
			<div class="resetPasswordPanel">
				<div
					class="col-lg-6 col-lg-offset-3 col-md-4 col-md-offset-2 col-sm-6 col-xs-12">
					
					<div id="resetPanel" class="panel panel-default resetPanel">
						<div class="panel-body">
							<h3 class="resetPwdTitle">
								<span class="fa fa-user fm_fntRed"></span> <span
									class="text-uppercase"> Password Reset</span>
							</h3>

							<br>
							<span id="globalMessages" class="fm_fntRed globalMessagesSpace">
								<common:globalMessages />
							</span>

							<%-- <form:form action="${saveUrl}" method="post"
								commandName="fmRegistrationForm" id="signinform" class="" role="form">
								</form:form> --%>

							<%-- <form:form id="signinform" class="" role="form"
								action="/password-reset" commandName="form"> --%>
							<form:form method="post" commandName="forgottenPwdForm" class=""
								role="form">

								<div style="display: none" id="login-alert"
									class="alert alert-danger col-sm-12"></div>
								<p class="infoMessage">To reset your password, enter the
									email address associated to your account</p>
								<div class="form-group clearfix">
								<div>
									<div class="input-group ">

										<span class="input-group-addon required"><i
											class="fa fa-user"></i></span>

										<form:input id="login-username" type="email"
											class="form-control" name="username" value=""
											placeholder="Email Address" path="email" />



									</div>
									
									<div class="errorMsg fm_fntRed" style="display: none;">Please enter your Email Address</div>
								</div>
									<%-- <formElement:formInputBox idKey="forgottenPwd.email"
											labelKey="forgottenPwd.email" path="email" inputCSS="text"
											mandatory="true" /> --%>





								<%--	<div id="globalMessages" class="fm_fntRed globalMessagesSpace">
										<common:globalMessages />
									</div> --%>

								</div>
								<div id="loginErrorMsg" class="alert alert-error hide">E-mail
									address is not valid</div>
								<div class="controls clearfix">
									<div class="col-sm-4 col-xs-12 resetPassCol passwordResetBtn">

										<ycommerce:testId code="User_Save_button">


											<button id="btn-login" class="btn btn-sm btn-fmDefault"
												type="submit">Continue</button>
										</ycommerce:testId>
									</div>


									<%-- <c:url value="/password-reset/fmSecreteQuestion"
											var="secretQuestion" />
										<ycommerce:testId code="myFMAccount_addressBook_navLink">
											<a href="secretQuestion" class="cantAccessEmail">I cannot
												access my email</a>
										</ycommerce:testId> --%>


									<%-- 	<div class="col-sm-8 col-xs-12 resetPassCol">
										<spring:url value="/password-reset/fmSecreteQuestion"
											var="secretQuestion">
										</spring:url>

										<a href="${secretQuestion}" class="cantAccessEmail">I
											cannot access my email</a>

									</div> --%>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>



		</div>
	</div>
</section>





<!-- InstanceEndEditable  END-->
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

<!-- MAIN CONTAINER-->

<section class="customBgBlock">
	<div class="container">
		<div class="row">
			<div class="resetPasswordPanel">
				<div
					class="col-lg-6 col-lg-offset-3 col-md-4 col-md-offset-2 col-sm-6 col-xs-12">
					<spring:theme code="text.company.user.resetPassword" text="Reset Password" />
					<div id="resetPanel" class="panel panel-default resetPanel">
						<div class="panel-body">
							<h3 class="resetPwdTitle">
								<span class="fa fa-user fm_fntRed"></span> <span
									class="text-uppercase"> <spring:theme code="text.error.user.passwordreset" text="Password Reset" /></span>
							</h3>

							<%-- <form:form action="${saveUrl}" method="post"
								commandName="fmRegistrationForm" id="signinform" class="" role="form">
								</form:form> --%>

							<%-- <form:form id="signinform" class="" role="form"
								action="/fmlogin/pw/request" commandName="form"> --%>
							<form:form method="post" commandName="forgottenPwdForm" class=""
								role="form">

								<div style="display: none" id="login-alert"
									class="alert alert-danger col-sm-12"></div>
								<p class="infoMessage"><spring:theme code="text.error.user.passwordreset.alert" text="To reset your password, enter the
									email address associated to your account" /></p>
								<div class="form-group ">
									<div class="input-group ">
										<span class="input-group-addon"><i class="fa fa-user"></i></span>


										<form:input id="login-username" type="email" class="form-control"
											name="username" value="" placeholder="E-mail Address" path="email"/>


										<%-- <formElement:formInputBox idKey="forgottenPwd.email"
											labelKey="forgottenPwd.email" path="email" inputCSS="text"
											mandatory="true" /> --%>

									</div>
								</div>
								<div id="loginErrorMsg" class="alert alert-error hide"><spring:theme code="text.error.user.passwordreset.email.alert" text="E-mail address is not valid" /></div>
								<div class="controls clearfix">
									<div class="col-sm-4 col-xs-12 resetPassCol">

										<ycommerce:testId code="User_Save_button">
										
										
											<button id="btn-login" class="btn btn-sm btn-fmDefault" type="submit">
												<spring:theme code="text.error.user.passwordreset.submit" text="Continue" /></button>
										</ycommerce:testId>
									</div>


									<%-- <c:url value="/fmlogin/pw/fmSecreteQuestion"
											var="secretQuestion" />
										<ycommerce:testId code="myFMAccount_addressBook_navLink">
											<a href="secretQuestion" class="cantAccessEmail">I cannot
												access my email</a>
										</ycommerce:testId> --%>


								<%-- 	<div class="col-sm-8 col-xs-12 resetPassCol">
										<spring:url value="/fmlogin/pw/fmSecreteQuestion"
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
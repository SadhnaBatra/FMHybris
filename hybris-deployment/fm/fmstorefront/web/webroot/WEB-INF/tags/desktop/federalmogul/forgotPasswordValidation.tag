<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>

<!-- MAIN CONTAINER-->
<!-- InstanceBeginEditable name="Page Content Section" -->
<section class="customBgBlock">
	<div class="container">
		<div class="row">
			<div class="resetPasswordPanel">
				<div
					class="col-lg-6 col-lg-offset-3 col-md-4 col-md-offset-2 col-sm-6 col-xs-12">
					<!-- Reset Password -->
					<div class="panel panel-default resetPanel">
						<div class="panel-body">
							<h3 class="resetPwdTitle">
								<span class="fa fa-user fm_fntRed"></span> <span
									class="text-uppercase"> Check your email</span>
							</h3>
							<form id="checkYourMail" class="" role="form">
								<div style="display: none" id="login-alert"
									class="alert alert-danger col-sm-12"></div>
								<p class="checkYourMailMessage">We have sent an email to
									${userEmailID}</p>
								<p>Click the link in the email to reset your password.</p>
								<p class="checkYourJunkMailMessage">If you do not see the
									email, check your junk mail or spam folders</p>
								<div id="loginErrorMsg" class="alert alert-error hide">E-mail
									address is not valid</div>
								<div class="controls clearfix">
									<div class=" ">
										<spring:url value="/password-reset" var="encodedUrl" />
										<a href="${encodedUrl}" class="didntReceiveEmail">I did
											not receive an email</a>
									</div>
								</div>
						
								
							</form>
						</div>
					</div>
				</div>
			</div>


		</div>
	</div>
</section>
<!-- InstanceEndEditable -->

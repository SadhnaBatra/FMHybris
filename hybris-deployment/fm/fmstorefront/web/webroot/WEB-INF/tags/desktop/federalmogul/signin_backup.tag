<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%-- <%@ attribute name="actionNameKey" required="true" type="java.lang.String" %> --%>



<!-- SIGN IN -->
<div id="loginbox" class="panel panel-default loginBoxPanel">
<c:url value="/fmlogin/dologin" var="submitUser" />
	<div class="panel-body">
		<h3 class="panel-title">
			<span class="fa fa-user"></span> SIGN IN
		</h3>
			<form:form  id="loginform" class="form-horizontal" action="${submitUser}"
					method="get" commandName="loginForm">
			<div style="display: none" id="login-alert"
				class="alert alert-danger col-sm-12"></div>
					<c:if test="${loginError}">
									
						<div id="globalMessages">
						<common:globalMessages />
					</div>
						
					</c:if>
			<div class="input-group ">
				<span class="input-group-addon"><i class="fa fa-user"></i></span> <input
					id="login-username" type="text" class="form-control"
					name="username" value="" placeholder="username or email">
			</div>
			<div class="input-group">
				<span class="input-group-addon"><i class="fa fa-lock"></i></span> <input
					id="login-password" type="password" class="form-control"
					name="password" placeholder="password">
			</div>
			<div class="controls clearfix">
				<a href="resetPassword.html" class="pull-left forgotPassword">Forgot
					password?</a> <a id="btn-login" href="#"
					class="btn  btn-sm btn-fmDefault pull-right">Login </a>
			</div>
			<div class="form-group regButtonHolder">
				<div class=" control">
					<button type="button"
						onClick="$('#loginbox').slideToggle(); $('#signupbox').slideToggle()"
						class="btn  btn-sm btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 regBtnOnSign btn-md">
						<span class="btn-label"><i class="fa fa-plus"></i></span><span
							class="pull-left regBtnTxt text-uppercase"><b>Register Now</b></span>
					</button>
				</div>
			</div>
		</form:form>
	</div>
</div>

<!-- RESISTER PANEL -->
<div class="panel panel-info signupBoxPanel clearfix" id="signupbox"
	style="display: none;">
	<div class="form-group sininButtonHolder">
		<div class=" control">
			<button type="button"
				onclick="$('#signupbox').slideToggle(); $('#loginbox').slideToggle()"
				class="btn   btn-sm btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 regBtnOnReg btn-md">
				<span class="btn-label backToSignin"><i class="fa fa-minus"></i></span><span
					class="pull-left regBtnTxt text-uppercase">Back To Sign In</span>
			</button>
		</div>
	</div>
	<div class="panel-body">
	<c:url value="/fmcustomer/registrationform" var="submitAction" />
				<!-- <form id="signupform" class="form-horizontal" role="form" action="/fmcustomer/registrationform">   -->
				<form id="signupform" class="form-horizontal" role="form"
					action="${submitAction}" method="post">


					<h3>Why Register?</h3>
					<h5>Enjoy all these benefits:</h5>
					<ul class="whyRegPoints">
						<li>Quickly upload your own order</li>
						<li>Get exclusive deals and cupons</li>
						<li>Be the first to know of new parts</li>
						<li>Keep up with industry news</li>
					</ul>


					<button type="submit" id="btn-signup"
						class="btn  btn-sm btn-fmDefault btnRegNow text-uppercase">Register
						Now</button>
				</form>

	</div>
</div>
<script>
	function RegistrationQuery() {

		document.forms["signupform"].submit();

	}
</script>
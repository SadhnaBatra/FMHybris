<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>

<div class="col-lg-3">
	<div class="signupAndRegisterPanel">
		<!-- SIGN IN -->
		<div id="loginbox" class="panel panel-default loginBoxPanel">


			<div class="panel-body">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-user"></span> <strong>SIGN
						IN </strong>
				</h3>
				<form id="loginform" class="form-horizontal" role="form">
					<div style="display: none" id="login-alert"
						class="alert alert-danger col-sm-12"></div>

					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-user"></i></span> <input id="login-username"
							type="text" class="form-control" name="username" value=""
							placeholder="username or email">
					</div>
					<div class="input-group">
						<span class="input-group-addon"><i
							class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
							type="password" class="form-control" name="password"
							placeholder="password">
					</div>



					<div class="controls clearfix">
						<a href="#" class="pull-left">Forgot password?</a> <a
							id="btn-login" href="#" class="btn btn-fmDefault pull-right">Login
						</a>
					</div>
					<div class="form-group regButtonHolder">
						<div class=" control">
							<button type="button"
								onClick="$('#loginbox').hide(); $('#signupbox').show()"
								class="btn btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 regBtn btn-md">
								<span class="btn-label"><i
									class="glyphicon glyphicon-plus"></i></span><span
									class="pull-left regBtnTxt">RESISTER NOW</span>
							</button>


						</div>
					</div>
				</form>
			</div>

		</div>

		<!-- RESISTER PANEL -->
		<div class="panel panel-info" id="signupbox" style="display: none;">

			<div class="form-group sininButtonHolder">
				<div class=" control">
					<button type="button"
						onclick="$('#signupbox').hide(); $('#loginbox').show()"
						class="btn btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 regBtn btn-md">
						<span class="btn-label"><i
							class="glyphicon glyphicon-minus"></i></span><span
							class="pull-left regBtnTxt">BACK TO SIGN IN</span>
					</button>


				</div>
			</div>



			<div class="panel-body">
				<form id="signupform" class="form-horizontal" role="form">
					<h3>Why Register?</h3>
					<h4>Enjoy all these benefits:</h4>
					<ul class="whyRegPoints">
						 <li>Learn about  promotions and offers</li>
                  				<li>Access to training and technical information</li>
                  				<li>Keep up with industry news and trends</li>
                  				<li>Be the first to know about new parts and more!</li>
					</ul>
					<button id="btn-signup" type="button"
						class="btn btn-fmDefault btnRegNow">REGISTER NOW</button>
				</form>
			</div>
		</div>

	</div>
</div>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="actionNameKey" required="true"
              type="java.lang.String" %>
<%@ attribute name="action" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement"
           tagdir="/WEB-INF/tags/desktop/formElement" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>


<body onload="checkPlaceHolderLabel()">

<c:if test="${not empty message}">
	<span class="errors"><spring:theme code="${message}"/></span>
</c:if>

<section class="customBgBlock">
    <div class="container">
        <div class="row">
            <div class="signupAndRegisterPanel">
                <div class="col-lg-4 col-lg-offset-2 col-md-4 col-md-offset-2 col-sm-6 col-xs-12">

                    <!-- SIGN IN -->
                    <div id="signinbox" class="panel panel-default loginBoxPanel">
                        <div class="panel-body">
                            <h3 class="panel-title">
                            	<span class="fa fa-user"></span>
                            	<span class="text-uppercase"><spring:theme code="header.link.login" text="Sign In"/></span>
                            </h3>

                            <form:form action="${action}" method="post" commandName="loginForm" id="loginForm" target="_parent">

                                <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                                <div class="form-group signupPageComp">
                                    <br>

                                    <c:if test="${loginError}">
                                        <div id="globalMessages" class="fm_fntRed globalMessagesSpace">
                                            <common:globalMessages/>
                                        </div>
                                    </c:if>

                                    <div id="tokenLoginErrors" class="fm_fntRed globalMessagesSpace" style="display:none">
                                        <div class="shoppingCartsubHead fm_fntRed lftMrgn_37">
                                            <spring:theme code="login.failed" text="Sign In Failed: Please try again"/>
                                        </div>
                                    </div>

                                    <div>
                                        <span id="email_ie9"></span>
                                        <div class="input-group ">
                                            <span class="input-group-addon required"><i class="fa fa-user"></i></span>

                                            <form:input id="j_username" type="email" class="form-control"
                                                        name="j_username" path="j_username"
                                                        placeholder="Email Address" required="required"/>
                                        </div>
 
                                        <span class="errorMsg fm_fntRed lftMrgn_37" style="display:none;">
                                            <spring:theme code="login.error.emailAddress" text="Please enter your Email Address"/>
                                        </span>
                                    </div>
                                </div>

                                <div class="form-group signupPageComp">
                                    <div>
                                        <span id="pwd_ie9"></span>
                                        <div class="input-group">
                                        	<span class="input-group-addon required"><i class="fa fa-lock"></i></span>
                                            <input id="j_password" type="password" class="form-control"
                                                   name="j_password" placeholder="Password" required="required">
                                        </div>
                                        <span class="errorMsg fm_fntRed lftMrgn_37" style="display:none;">
                                        	<spring:theme code="login.error.password" text="Please enter your Password."/>
                                        </span>
                                    </div>
                                </div>

                                <div id="loginErrorMsg" class="alert alert-error hide">
                                    <spring:theme code="login.error.wrongCredentials" text="Wrong username or password"/>
                                </div>
                                                  
                                <div class="controls clearfix">
                                    <button id="btn-login" class="btn btn-sm btn-fmDefault pull-left" type="submit">
                                            <spring:theme code="login.button.sigin" text="Sign In"/>
                                    </button>

                                    <a href="/fmstorefront/federalmogul/en/USD/password-reset"
                                       class="pull-right forgotPassword text-capitalize">
                                       <spring:theme code="login.link.forgotPassword" text="Forgot password?"/>
                                    </a>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                    <!-- Register -->
                    <federalmogul:registrationPanel/>
                </div>
            </div>
        </div>
    </div>
</section>

<c:if test="${expressCheckoutAllowed}">
	<div class="expressCheckoutLogin">
	    <div class="headline">
	        <spring:theme text="Express Checkout"
	                      code="text.expresscheckout.header"/>
	    </div>
	
	    <div class="description">
	        <spring:theme text="Benefit from a faster checkout by:"
	                      code="text.expresscheckout.title"/>
	    </div>
	
	    <ul>
	        <li><spring:theme
	                text="setting a default Delivery Address in your account"
	                code="text.expresscheckout.line1"/></li>
	        <li><spring:theme
	                text="setting a default Payment Details in your account"
	                code="text.expresscheckout.line2"/></li>
	        <li><spring:theme text="a default shipping method is used"
	                          code="text.expresscheckout.line3"/></li>
	    </ul>
	
	    <div class="expressCheckoutCheckbox clearfix">
	        <label for="expressCheckoutCheckbox">
	        	<input id="expressCheckoutCheckbox" name="expressCheckoutEnabled"
	                	type="checkbox" class="form left doExpressCheckout"/> 
	            <spring:theme text="I would like to Express checkout"
	                			code="cart.expresscheckout.checkbox"/>
	        </label>
	    </div>
	</div>
</c:if>

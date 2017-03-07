<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ attribute name="displayIFrame" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="formElement"
           tagdir="/WEB-INF/tags/desktop/formElement" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags" %>


<body onload="checkPlaceHolderLabel()">

<c:if test="${empty displayIFrame}">
	<c:set var="displayIFrame" value="${false}" />
</c:if>

<!-- SIGN IN -->
<div id="loginbox" class="panel panel-default loginBoxPanel">

    <spring:url value="/fmlogin/dologin" var="submitUser"/>
    
    		<c:if test="${displayIFrame}">
    			<spring:url value="/fmlogin/dologin/iframe" var="submitUser"/>	
       		</c:if>

    <div class="panel-body">
        <c:choose>
            <c:when test="${loginError}">
                <h3 class="panel-title btmMrgn_5">
                    <span class="fa fa-user"></span> <spring:theme code="anonymousHome.sigin" text="SIGN IN"/>
                </h3>
                <div class="fm_fntRed globalMessagesSpace">
                    <c:if test="${not empty accErrorMsgs}">
                        <c:forEach items="${accErrorMsgs}" var="msg">
                            <div class="alert negative">
                                <spring:theme code="${fn:escapeXml(msg.code)}"
                                    arguments="${fn:escapeXml(msg.attributes)}"/>
                            </div>
                        </c:forEach>
                    </c:if>
                </div>

            </c:when>
            <c:otherwise>
                <h3 class="panel-title btmMrgn_20">
                    <span class="fa fa-user"></span> <spring:theme code="anonymousHome.sigin" text="SIGN IN"/>
                </h3>

            </c:otherwise>
        </c:choose>
        <div id="tokenLoginErrors" class="fm_fntRed globalMessagesSpace" style="display:none">
            <div class="alert negative">
                <spring:theme code="login.failed" text="Sign In Failed: Please try again"/>
            </div>
        </div>

        <form:form id="loginform" class="form-horizontal" action="${submitUser}"
                   method="post" commandName="loginForm" target="_parent">

            <div style="display: none" id="login-alert"
                 class="alert alert-danger col-sm-12">
            </div>


            <div>
                <span id="email_ie9"></span>
                <div class="input-group signInEmailPassTextBox">
                    <span class="input-group-addon required"><i class="fa fa-user"></i></span>
                    <form:input id="j_username" type="email" class="form-control"
                                name="j_username" path="j_username"
                                placeholder="Email Address" required="required"/>


                </div>
                <span class="errorMsg fm_fntRed lftMrgn" style="display:none;"><spring:theme
                        code="login.error.emailAddress" text="Please enter your Email Address"/></span>
            </div>

            <div>
                <span id="pwd_ie9" class="signinLabel"></span>
                <div class="input-group signInEmailPassTextBox">
                    <span class="input-group-addon required"><i class="fa fa-lock"></i></span>
                    <input id="j_password" type="password" class="form-control"
                           name="j_password" value="" placeholder="Password" required="required"/>
                </div>
                <span class="errorMsg fm_fntRed lftMrgn" style="display:none;">Please enter your Password</span>
            </div>

            <div class="clearfix topMargn_20">
                <button class="btn btn-sm btn-fmDefault pull-left" type="submit" id="loginSubmit">
                        <spring:theme code="login.button.submit" text="Submit "/>
                </button>
 
                <spring:url value="/password-reset" var="encodedUrl"/>
                <a href="${encodedUrl}"
				   <c:if test="${displayIFrame}">target="_parent"</c:if>
				   class="pull-right forgotPassword"><spring:theme code="login.link.forgotPassword" text="Forgot password?"/></a>
            </div>

        </form:form>
    </div>
    <div class="form-group regButtonHolder">
        <div class="control">
            <button type="button"
                    onClick="$('#loginbox').slideToggle(); $('#signupbox').slideToggle()"
                    class="btn btn-sm btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 regBtnOnSign btn-md">
                <span class="btn-label"><i class="fa fa-plus"></i></span><span
                    class="pull-left regBtnTxt text-uppercase"><spring:theme code="registration.registernow" text="Register now"/></span>
            </button>
        </div>
    </div>
</div>


<!-- REGISTER PANEL -->
<div class="panel panel-info signupBoxPanel clearfix" id="signupbox"
     style="display: none;">
    <div class="form-group sign-in-button">
        <div class=" control">
            <button type="button"
                    onclick="$('#signupbox').slideToggle(); $('#loginbox').slideToggle()"
                    class="btn   btn-sm btn-labeled btn-fmDefault col-lg-12 col-xs-12 col-md-12  col-sm-12 regBtnOnReg btn-md">
                <span class="btn-label backToSignin"><i class="fa fa-minus"></i></span><span
                    class="pull-left regBtnTxt text-uppercase"><spring:theme code="login.button.backtosignin" text="Back To Sign In "/></span>
            </button>
        </div>
    </div>

    <div class="panel-body">
        <c:url value="/registration" var="submitAction"/>

        <form id="signupform" class="form-horizontal" role="form"
			  <c:if test="${displayIFrame}">target="_parent"</c:if>
              action="${submitAction}" method="post">

            <c:set var="fmComponentName" value="registrationloginblock" scope="session"/>
            <cms:pageSlot position="anonymousLoginAndRegister" var="feature">
                <cms:component component="${feature}"/>
            </cms:pageSlot>

            <button type="submit" id="btn-signup"
                    class="btn  btn-sm btn-fmDefault btnRegNow text-uppercase">
                    <spring:theme code="registration.registernow" text="Register now"/></button>
        </form>
    </div>
</div>


<script>
    function RegistrationQuery() {

        document.forms["signupform"].submit();

    }
</script>

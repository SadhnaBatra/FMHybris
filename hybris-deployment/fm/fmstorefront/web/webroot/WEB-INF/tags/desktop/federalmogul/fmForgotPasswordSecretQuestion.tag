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

<spring:url value="/fmlogin/pw/change" var="submitColor">
	 <spring:param name="token" value="siva@fm.com" />
</spring:url>

<!-- MAIN CONTAINER-->
<!-- InstanceBeginEditable name="Page Content Section" -->

<section class="customBgBlock">
	<div class="container">
		<div class="row">
			<div class="resetPasswordPanel">
				<div
					class="col-lg-6 col-lg-offset-3 col-md-4 col-md-offset-2 col-sm-6 col-xs-12">
					<!-- Reset Password -->
					<div id="resetPanel" class="panel panel-default resetPanel">
						<div class="panel-body">
							<h3 class="resetPwdTitle">
								<span class="fa fa-user fm_fntRed"></span> <span
									class="text-uppercase"> Answer your secret question</span>
							</h3>
							<form:form action="${submitColor}" method="get"
								commandName="forgottenPwdForm" id="" class="" role="form">
								<div style="display: none" id="login-alert"
									class="alert alert-danger col-sm-12"></div>
								<p class="resetQuesMsg">To reset your password, answer the
									secret question on associated with your account.</p>
								<p class="resetQuest">What is your favourite color?</p>
								<div class="form-group row">
									<div class="col-md-7  col-sm-12 col-xs-12">

										<form:input type="text" id="confirmnewpwd" name="color"
											path="color" class="form-control" value="" />
									</div>
								</div>
								<div class="controls secretQuestCtrl clearfix">
									<div class="col-sm-3 col-xs-12 resetPassCol">
										<button id="" class="btn btn-sm btn-fmDefault ">
											Continue</button>
									</div>
									<div class="col-sm-9 col-xs-12 resetPassCol">

										<spring:url value="/fmlogin/pw/request" var="encodedUrl" />
										<a href="${encodedUrl}" class="cantAccessEmail">I cannot
											remember my answer</a>

									</div>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>


		</div>
	</div>
</section>





<%-- <section class="customBgBlock">
  <div class="container">
    <div class="row">
      <div class="resetPasswordPanel">
        <div class="col-lg-6 col-lg-offset-3 col-md-4 col-md-offset-2 col-sm-6 col-xs-12"> 
          <!-- Reset Password -->
          <div id="resetPanel"  class="panel panel-default resetPanel" >
            <div class="panel-body" >
              <h3 class="resetPwdTitle" ><span class="fa fa-user fm_fntRed"></span> <span class="text-uppercase"> Answer your secret question</span></h3>
              <form id="" class="" role="form" action="resetYourPassword.html">
                <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                <p class="resetQuesMsg">To reset your password, answer the secret question on associated with your account.</p>
                <p class="resetQuest">What is your favourite color?</p>
                <div class="form-group row">
                  <div  class="col-md-7  col-sm-12 col-xs-12" >
                    <input id="confirmnewpwd" type="text" class="form-control" name="username" value="">
                  </div>
                </div>
                <div class="controls secretQuestCtrl clearfix">
                  <div class="col-sm-3 col-xs-12 resetPassCol">
                    <button id="" class="btn btn-sm btn-fmDefault " > Continue </button>
                  </div>
                  <div class="col-sm-9 col-xs-12 resetPassCol"> <a href="resetPassword.html" class="cantAccessEmail">I cannot remember my answer</a></div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
      
      
    </div>
  </div>
</section> --%>
<!-- InstanceEndEditable -->

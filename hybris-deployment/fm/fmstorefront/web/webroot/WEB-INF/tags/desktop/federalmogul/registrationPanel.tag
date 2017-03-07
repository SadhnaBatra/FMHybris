<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<div id="registerbox" class="panel panel-default loginBoxPanel registerBoxPanel" >
            <div class="panel-body" >
              
              <form id="registerform" class="registerForm" role="form" action="registration" method="post">
         <c:set var="fmComponentName" value="registrationloginblock" scope="session" />
					<cms:pageSlot position="TopContent" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
                <div class="controls clearfix">
                  <button id="btn-register" class="btn btn-sm btn-fmDefault" type="submit"><spring:theme code="registration.registernow.registeraccount" text="Register for an account" />  </button>
                </div>
              </form>
            </div>
          </div>
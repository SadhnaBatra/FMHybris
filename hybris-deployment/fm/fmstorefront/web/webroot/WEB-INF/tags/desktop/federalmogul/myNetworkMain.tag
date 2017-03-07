<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
 
 <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
        <div class="panel panel-default manageAccountLinks orgMngDetailBlock clearfix" >
          <div class="panel-body">
            <div class="orgMngBlock">
              <h3 class="text-uppercase"><spring:theme code="text.company.manage.units" text=" Manage Business Units" /></h3>
              <ul class="orgMngBoldBody">
                <li><a href="#"> <spring:theme code="text.company.createNewUnits" text="Create New Units " /><span class="linkarow fa fa-angle-right "></span></a></li>
                <li><a href="#"><spring:theme code="text.company.editOrDisableUnits" text="Edit or Disable Units " />  <span class="linkarow fa fa-angle-right "></span></a></li>
              </ul>
            </div>
            <div class="orgMngBlock">
              <h3 class="text-uppercase"><spring:theme code="text.company.manageUser" text="Manage Users" /> </h3>
              <ul class="orgMngBoldBody">
                <li><a href="#"><spring:theme code="text.company.addNewUser" text="Add New User" /> <span class="linkarow fa fa-angle-right "></span></a></li>
                <li><a href="#"><spring:theme code="text.company.editOrDisableUsers" text="Edit or Disable Users " /> <span class="linkarow fa fa-angle-right "></span></a></li>
              </ul>
            </div>
             <div class="orgMngBlock">
              <h3 class="text-uppercase"><spring:theme code="text.company.manage.user.groups" text="Manage User Groups " /> </h3>
              <ul class="orgMngBoldBody">
                <li><a href="#"><spring:theme code="text.company.addNewUsergroup" text="Add New User Group " /><span class="linkarow fa fa-angle-right "></span></a></li>
                <li><a href="#"><spring:theme code="text.company.editOrDisableUsersgroup" text="Edit or Disable Users Group" /><span class="linkarow fa fa-angle-right "></span></a></li>
              </ul>
            </div>
           <!-- <div class="orgMngBlock">
              <h3 class="text-uppercase">Manage Order Permissions</h3>
              <ul class="orgMngBoldBody">
                <li><a href="#">Add New Permission <span class="linkarow fa fa-angle-right "></span></a></li>
                <li><a href="#">Edit or Disable Permissions <span class="linkarow fa fa-angle-right "></span></a></li>
              </ul>
            </div> -->
          </div>
        </div>
        <!-- Ends: Manage Account Right hand side panel --> 
      </div>
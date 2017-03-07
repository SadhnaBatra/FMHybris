

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="internal" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="fms" tagdir="/WEB-INF/tags/desktop/federalmogul"%>

<!-- MAIN CONTAINER-->
<!-- InstanceBeginEditable name="Page Content Section" -->
<section class="accountDetailPage pageContet" >
  <div class="container manageAccount">
    <div class="row"> 
      <!-- Starts: Manage Account Left hand side panel -->
      <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"> 
        <!--- Order in Progress PANEL -->
        <div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body orgMangPanel">
            <h3 class="text-uppercase">Learning Center</h3>
            <cms:slot  var="feature" position="CompanySection">
            <ul class="">
              <li>
		<c:url value="${feature.url}" var="encodedUrl" />
		<a href="${encodedUrl}" ${feature.uid eq 'LCOverviewLink' ? 'class="selected"' : ''} title="${feature.linkName}" ${feature.target == null || feature.target == 'SAMEWINDOW' ? '' : 'target="_blank"'}>${feature.linkName}</a>
 		</li>

<!--               <li><a href="#">Training Options </a></li> -->
<!--               <li><a href="#">Courses <span class="linkarow fa fa-angle-right "></span></a></li> -->
<!--               <li><a href="#">Tech Tips<span class="linkarow fa fa-angle-right "></span></a></li> -->
<!--               <li><a href="#">Diagnostic Center <span class="linkarow fa fa-angle-right "></span></a></li> -->
<!--               <li><a href="#">Video Gallery <span class="linkarow fa fa-angle-right "></span></a></li> -->
            </ul>
            </cms:slot>
          </div>
        </div>
        <div class="panel panel-default messagingPanel clearfix" > </div>
        <div class="panel panel-default messagingPanel clearfix" > </div>
      </div>
      <!-- Ends: Manage Account Left hand side panel --> 
      
      <!-- Starts: Manage Account Right hand side panel -->
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 learningCenter">
      <div class="clearfix">
      <div class="col-xs-12 customBgBlock ">
      	<c:set var="fmComponentName" value="FMBannerSection"
								scope="session" />
      <cms:slot var="feature" position="FMBannerSection">
		
      <cms:component component="${feature}" />
      </cms:slot>
      </div>
      </div>
      
<cms:slot  var="feature" position="InternalContentSection">

         <c:set var="fmComponentName" value="${feature.uid}"
								scope="session" />
      
	<div class="clearfix">
      <div class="col-xs-12 bgwhite topMargn_20 fm-padding-20">
        
        <cms:component component="${feature}" /> 
     
      </div>
      </div>
 </cms:slot>


      </div>
      <!-- Ends: Manage Account Right hand side panel --> 
    </div>
  </div>
</section>
<!-- InstanceEndEditable -->

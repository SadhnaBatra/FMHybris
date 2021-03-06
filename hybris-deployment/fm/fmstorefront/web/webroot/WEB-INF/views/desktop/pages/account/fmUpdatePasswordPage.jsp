<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">

	<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
	  <div class="container">
	    <div class="row">
	      <ul class="breadcrumb">
	        <breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	      </ul>
	    </div>
	  </div>
	</section>
	
	<section class="accountDetailPage pageContet">
		<div class="container manageAccount">
			<div class="row">
				<!-- Starts: Manage Account Left hand side panel 
				nav:myCompanyNav selected="organizationManagement
				-->
				<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12" ${pwdMeetsLatestStandards ? 'style="display:block"' : 'style="display:none"'}>				
					<federalmogul:myAccount />
				</div>
				
				<federalmogul:updatePassword />
				
			</div>
		</div>
	</section>

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>

	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />

</template:page>

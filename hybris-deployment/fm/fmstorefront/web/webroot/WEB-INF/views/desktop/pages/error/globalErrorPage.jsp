<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">
<section class="customBgBlock">
  <div class="container">
    <div class="row">
      <div class="resetPasswordPanel">
        <div class="col-lg-8 col-lg-offset-2 col-md-3 col-md-offset-2 col-sm-4 col-xs-12">
          <div  class="panel panel-default resetPanel pageNotFound" >
            <div class="panel-body" >
              <h3 class="resetPwdTitle"><span class="text-uppercase">Page not found</span></h3>
              <p class="topMargn_15 btmMrgn_14">The page you are looking for has not been found.</p>

			<p>Please <a href="" onclick="window.history.go(-1);return false;" class="addNewAddLink">go back</a> to the last page or try our site search.  </p>
            </div>
          </div>
        </div>
      </div>	
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
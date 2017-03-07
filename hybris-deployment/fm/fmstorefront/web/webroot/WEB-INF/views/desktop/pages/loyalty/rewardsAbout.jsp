<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="user" tagdir="/WEB-INF/tags/desktop/user" %>
<%@ taglib prefix="checkout" tagdir="/WEB-INF/tags/desktop/checkout" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<template:page pageTitle="${pageTitle}">

<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
				</ul>
			</div>
		</div>
	</section>
<!-- InstanceEndEditable -->


<!-- MAIN CONTAINER-->
<!-- InstanceBeginEditable name="Page Content Section" -->
<section class="rewardsAboutPage contentPage"> 
  
  <!-- Lookup Rewards Section -->
  
  <section class="accountDetailPage pageContet" >
    <div class="container">
      <div class="row">
        <div class="col-lg-12 btmMrgn_30">
    	<c:set var="fmComponentName" value="rewardbanner" scope="session" />
         
  			<cms:pageSlot position="FMBannerSection" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalLanding contentRHS">
  <div class="internalPage rightHandPanel clearfix">
  <c:set var="fmComponentName" value="program" scope="session" />
         
  			<cms:pageSlot position="FMProgramSection" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
  
  </div>
          
        </div>
        <!-- Starts: Manage Account Left hand side panel -->
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 contentLHS"> 
          <!--- Order in Progress PANEL -->
         
          <div class="panel panel-default clearfix" >
           <c:set var="fmComponentName" value="benefits" scope="session" />
         
  			<cms:pageSlot position="FMProgramBenefitsSection" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
        </div>
        </div>
      </div>
      
      
      
      
      <div class="row">
        <div class="col-lg-12 col-xs-12 internalLanding contentRHS">
          <div class="internalPage rightHandPanel clearfix">
  

          <c:set var="fmComponentName" value="learnpara" scope="session" />
         
  			<cms:pageSlot position="FMPointsSection" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
            <!--.container--> 
            
          </div>
          
        </div>
      </div>
      
      
      <div class="row"> </div>
    </div>
  </section>
 
<!-- POPUP -->
<div class="modal fade shipToModel" id="enrollNow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog newAddressPopup">
    <button data-dismiss="modal" class="close" type="button"><span class="fa fa-close" aria-hidden="true"></span><span class="sr-only">Close</span></button>
    <div class="modal-content">
      <div class="modal-body">
        <div id="new"  >
          <form role="form">
            <div class="reviewFirstPanelMargin ">
              <ul class="list-group checkboxGroup">
                <li class="list-group-item">
                  <label for="member">
                    <input type="checkbox"  id="loyalty"/>
                    &nbsp;Loyalty</label>
                </li></ul>
              <div class="form-group">
                <label for="lmsLoginID" >LMS/Success Factors log-in ID </label>
                <input id="lmsLoginID" type="text" class="form-control" value=""/>
              </div>
            </div>
            <div class="">
              <button class="btn btn-fmDefault" type="button" onClick="$('#shipTonewAddress').show();"  data-dismiss="modal" >Submit</button>
              <button class="btn btn-fmDefault" type="button" data-dismiss="modal" >Cancel</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div> 
	</section>
	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<!-- <c:set var="fmComponentName" value="brandStripB2B" scope="session" />-->
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />

	</template:page>

<!-- InstanceEndEditable -->



<!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
<script src="js/jquery-1.11.1.min.js"></script>
<script src="js/holder.js"></script> 
<!-- Include all compiled plugins (below), or include individual files as needed --> 
<script src="js/bootstrap.min.js"></script> 
<script src="js/bootstrap-slider.js"></script> 
<script src="js/bootstrap-toggle.min.js"></script> 
<script src="js/easyResponsiveTabs.js"></script>
<script src="js/jquery.slimscroll.js"></script>
<script src="js/bootstrap-datepicker.js"></script>
<script src="js/bootstrap-maxlength.js"></script> 
<script src="js/custom.js"></script> 
<!-- InstanceBeginEditable name="Page Related Script" --> 
<script src="js/jquery.bxslider.js"></script> 
<script type="text/javascript">
$(document).ready(function() {
	
  $('.slider4').bxSlider({
    slideWidth: 302,
    minSlides: 2,
    maxSlides: 4,
    moveSlides: 1,
    slideMargin: 30,
	nextText: '<span class="fa fa-chevron-right"></span>',
	prevText: '<span class="fa fa-chevron-left"></span>'
  });
});
</script> 



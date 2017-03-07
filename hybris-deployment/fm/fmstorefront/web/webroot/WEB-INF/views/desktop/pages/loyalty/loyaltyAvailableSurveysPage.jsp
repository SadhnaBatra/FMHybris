<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>




<template:page pageTitle="${pageTitle}">


 <!-- InstanceBeginEditable name="Breadcrumb" -->
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
  
  <section class="wayToPoints accountDetailPage pageContet" >
    <div class="container champion">
      <div class=" clearfix bgwhite">
        <div class="col-lg-9 lftPad_0">
    	<c:set var="fmComponentName" value="waytolearn" scope="session" />
         
  			<cms:pageSlot position="FMSearchGridBannerSection" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
        </div>
        <div class="col-lg-3 rewardsPanel">
        
            <h1 class="panel-title"><span class="fa fa-user"></span><span class=" text-uppercase">&nbsp;Garage rewards</span></h1>
            <div class="rewardsPoints"><i class="fa fa-wrench"></i><strong><fmt:formatNumber type="number" value="${TotalPoints}" /><sub>pts</sub></strong></div>
            <div class="text-center">
                      <a class="text-capitalize addNewAddLink" href="loyalty/history">View Points History <span class="linkarow fa fa-angle-right "></span></a></div> 
          </div>
      </div>
      
      <div class="row">
        <div class="col-lg-12 col-xs-12 internalLanding">
          <div class="internalPage rightHandPanel clearfix">
  
<%-- 
          <c:set var="fmComponentName" value="learnpara" scope="session" />
         
  			<cms:pageSlot position="FMproductListGridSection" var="feature">
  			
				<cms:component component="${feature}" />
			</cms:pageSlot> --%>
            <!--.container--> 
            
            
            <div class="clearfix">
            <h2 class="text-capitalize DINWebBold">Available Surveys to Earn Points</h2>
            <div class="table-responsive userTable rewardsEarn">
            <table border="0" class="table ordeDetailsTable">
            <thead>
            <tr>
            <th class="col-lg-4 col-md-4 col-sm-4 col-xs-4 ">Activity</th>
            <th class="col-lg-5 col-md-5 col-sm-5 col-xs-5 ">Points</th>
            <th class="col-lg-5 col-md-5 col-sm-5 col-xs-5 ">&nbsp;</th>
            </tr>
            </thead>
             <tbody>
             <tr>
             <td>Brand Preference Survey (15 - 20 minutes)</td>
             <td>250<br /></td>
             <c:choose>
             <c:when  test="${fn:contains(surevyStats,'Brand')}">
              <td>Completed</td>
             </c:when>
             <c:otherwise>
             <td><a href="/fmstorefront/loyalty/en/USD/Survey/BrandSurvey" class=" fm_fnt_Blue">Take Survey<span class="linkarow fa fa-angle-right "></span></a></td>
             </c:otherwise>
             </c:choose>
             </tr>
             <tr>
             <td>Interests &amp; Hobbies Survey (10 - 15 minutes)</td>
              <td>250</td>
                <c:choose>
             <c:when  test="${fn:contains(surevyStats,'IntersetAndHobbies')}">
              <td>Completed</td>
             </c:when>
             <c:otherwise>
              <td><a href="/fmstorefront/loyalty/en/USD/Survey/InterestsandHobbies" class=" fm_fnt_Blue">Take Survey<span class="linkarow fa fa-angle-right "></span></a></td>
              </c:otherwise>
              </c:choose>
              </tr>
              <tr>
              <td>Parts Buying Survey  (15 - 20 minutes)</td>
              <td>250</td>
                <c:choose>
             <c:when  test="${fn:contains(surevyStats,'Parts')}">
              <td>Completed</td>
             </c:when>
             <c:otherwise>
              <td><a href="/fmstorefront/loyalty/en/USD/Survey/PartsBuyingSurvey" class=" fm_fnt_Blue">Take Survey<span class="linkarow fa fa-angle-right "></span></a></td>
            </c:otherwise>
            </c:choose> 
              </tr>
              </tbody>
              </table>
              </div>
              </div>
          </div>
          
        </div>
      </div>
      
      
   
      <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 internalLanding">
         <cms:pageSlot position="FMProductGridBannerSection" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
          
        </div>
      </div>
    </div>
  </section>
  

<!-- InstanceEndEditable -->
	
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
	</section>
	</template:page>

 

	
	


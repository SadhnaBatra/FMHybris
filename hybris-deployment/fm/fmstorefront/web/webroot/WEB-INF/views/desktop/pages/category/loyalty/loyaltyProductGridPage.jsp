<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product/loyalty" %>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav/loyalty" %>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<template:page pageTitle="Loyalty Product Grid">
	
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
	
	

                  

<!-- MAIN CONTAINER-->
<!-- InstanceBeginEditable name="Page Content Section" -->
<section class="myReward productListPage" >
  <div class="container">
    <div class="row">
      <aside class="col-lg-3 col-md-3 ">
        <div class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs" id="accordion">
          <div class="panel panel-default searchby">
            <div class="panel-body">
              <h4><span class=" fa fa-search"></span>Refine Results By</h4>
            </div>
          </div>
          <!-- accordian starts -->
         
           <nav:loyaltysearchfacetNavRefinements pageData="${searchPageData}" categoryCode="${fn:trim(categoryCode)}" rootCategories ="${rootCategoriesList}" />          
          
        </div>
      </aside>
      <section class="col-lg-9">
        <div class=" clearfix bgwhite">
          <div class="col-lg-8 prodBanner ban"> 
          <c:set var="fmComponentName" value="banner" scope="session" />
         
  			<cms:pageSlot position="FMSearchGridBannerSection" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
			</div>
        <div class="col-lg-4 rewardsPanel">
            <h3 class="panel-title"><span class="fa fa-user"></span> <span class=" text-uppercase">&nbsp;Garage rewards</span></h3>
            <div class="rewardsPoints"><i class="fa fa-wrench"></i><strong><fmt:formatNumber type="number" value="${TotalPoints}" /><sub>pts</sub></strong></div>
            <div class="text-center">
              <form role="form" class="form-horizontal rewardsPanelForm" id="rewardsform">
		<c:url value="/loyalty/earn" var="loyalEarnUrl" />
                <div class="controls clearfix"> <a class="btn  btn-sm btn-fmDefault pull-rightn text-uppercase" href="${contextPath}/loyalty/${currentLanguage.isocode }/${currentCurrency.isocode }/loyalty/earn" id="btn-login">Earn More Points</a></div>
              </form>
		

              <a class="text-capitalize addNewAddLink" href="${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/loyalty/history?clear=true&site=federalmogul">View Points History <span class="linkarow fa fa-angle-right "></span></a></div>
          </div>
        </div>
        <section class="prodList clearfix">
          <div class=" col-lg-12 topMargn_10">
            <p>Check back often for new gear.</p>
          </div>
          <section class="prodFilter col-lg-12 topMargn_10">
          	<nav:loyaltypagination top="true" supportShowPaged="${isShowPageAllowed}"
											supportShowAll="${isShowAllAllowed}"
											searchPageData="${searchPageData}"
											searchUrl="${searchPageData.currentQuery.url}"
											numberPagesShown="${numberPagesShown}" />
            
          </section>
          <section class="prodList col-lg-12"> 
			<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
				<c:if test="${(status.index)%3 == 0}">
					<div class="productlistblock clearfix">
				</c:if>
				   <product:loyaltyproductListerGridItem product="${product}"/>
					
				<c:if test="${(status.index+1)%3 == 0}">
					</div>
				</c:if>
				
					
			</c:forEach>
			
				
		</section>
        <section class="prodFilter col-lg-12">
            <nav:loyaltypagination top="false" supportShowPaged="${isShowPageAllowed}"
											supportShowAll="${isShowAllAllowed}"
											searchPageData="${searchPageData}"
											searchUrl="${searchPageData.currentQuery.url}"
											numberPagesShown="${numberPagesShown}" />
          </section>
        </section>
      </section>
    </div>
  </div>

<!-- InstanceEndEditable -->


	<!-- Brandstrip -->

	<div class="well well-sm well-brandstrip clearfix topMargn_35">
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
	

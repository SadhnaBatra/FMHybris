<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<template:page pageTitle="${pageTitle}">
	

	<div class="visible-lg visible-md row">
		<!-- InstanceBeginEditable name="Breadcrumb" -->
		<div class="breadcrumbPanel clearfix">
			<div class="col-lg-10 col-lg-offset-1">
				<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
			</div>
		</div>
	</div>
	<div id="globalMessages">
		<common:globalMessages/>
	</div>

	<div class="row">
		<div class="productListPage clearfix pageContet">
			<div class="col-lg-10 col-lg-offset-1">
				<div class="col-lg-3 col-md-3 ">
			
					<!-- <nav:facetNavAppliedFilters pageData="${searchPageData}"/> -->
				 
					<div id="accordion" class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs">
						<nav:facetNavRefinements pageData="${searchPageData}"/>
					</div>
			
				</div>
				<div class="col-lg-9">
					<div class="prodBanner">
						<c:set var="fmComponentName" value="productGridBanner" scope="session" />
						
							<cms:pageSlot position="FMProductGridBannerSection" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
	
					</div>
					<section class="prodList clearfix">
						<div class="search-result-title col-lg-12">
							<h4>
								You searched for "<span class="search-result-text">${searchPageData.categoryCode}</span>"
							</h4>
						</div>
						<section class="prodFilter col-lg-12">
							<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}"
											supportShowAll="${isShowAllAllowed}"
											searchPageData="${searchPageData}"
											searchUrl="${searchPageData.currentQuery.url}"
											numberPagesShown="${numberPagesShown}" />
						</section>
						<section class="prodList col-lg-12">
	
							<c:set var="count" value="0" />
							<c:forEach  items="${searchPageData.results}" var="product"
										varStatus="status">
								
	
								<c:if test="${(status.index)%3 == 0}">
									<div class="productlistblock clearfix">
								</c:if>
	
								
									<div
										class="<c:if test="${(status.index+1)%3 == 1}">  col-sm-4 col-md-4 </c:if>
													<c:if test="${(status.index+1)%3 == 2 || (status.index+1)%3 == 0 }">  col-xs-18 col-sm-4 col-md-4 </c:if> ">
										 <product:productListerGridItem product="${product}" /> 
									</div>
	
								<c:if
									test="${(status.index+1)%3 == 0 && (status.index+1)%3 == 0 }">
								</div>
								</c:if>  
							 		
							</c:forEach>

						</section>
						<section class="prodFilter col-lg-12">
							<nav:pagination top="true"
								supportShowPaged="${isShowPageAllowed}"
								supportShowAll="${isShowAllAllowed}"
								searchPageData="${searchPageData}"
								searchUrl="${searchPageData.currentQuery.url}"
								numberPagesShown="${numberPagesShown}" />
						</section>
					
					</section>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="well well-sm well-brandstrip clearfix">
				<ul class="nav ">
					<c:set var="fmComponentName" value="brandStrip" scope="session" />
	
					<cms:pageSlot position="FMBrandstrip" var="feature">
						<cms:component component="${feature}" />
					</cms:pageSlot>
					<!-- 
	        		no-repeat; height:60px; width: 178px; display:block;
	        <li class=""><a href="#" ></a></li>
	        <li class=""><a href="#" class="wangerLight"></a></li>
	        <li class=""><a href="#" class="champion"></a></li>
	        <li class=""><a href="#" class="felPro"></a></li>
	        <li class=""><a href="#" class="sealderPower"></a></li>
	        <li class=""><a href="#" class="fp"></a></li>
	        <li class="" ><a href="#" class="speedPro"></a></li> -->
					<!--<li class=""><a href="#" class="anco"></a></li>
	            <li class=""><a href="#" class="moog"></a></li>
	            <li class=""><a href="#" class="abex"></a></li>-->
				</ul>
			</div>
		</div>




	<%-- <cms:pageSlot position="Section1" var="feature">
		<cms:component component="${feature}" element="div" class="span-24 section1 cms_disp-img_slot"/>
	</cms:pageSlot>

	<div class="span-241">
		<div class="col-lg-3 col-md-3 span-61 ">
			
			<nav:facetNavAppliedFilters pageData="${searchPageData}"/>
			 
				<div id="accordion" class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs">
					<nav:facetNavRefinements pageData="${searchPageData}"/>
				</div>
			

			<cms:pageSlot position="Section4" var="feature">
				<cms:component component="${feature}" element="div" class="span-4 section4 cms_disp-img_slot"/>
			</cms:pageSlot>
		</div>
	
		<div class="span-18 last">
		
			<cms:pageSlot position="Section2" var="feature">
				<cms:component component="${feature}" element="div" class="section2 cms_disp-img_slot"/>
			</cms:pageSlot>
		
			
			<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}" searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}" numberPagesShown="${numberPagesShown}"/>

			<cms:pageSlot position="Section3" var="feature" element="div" class="span-20 last">
				<cms:component component="${feature}" element="div" class="span-5 section3 small_detail"/>
			</cms:pageSlot>

			<div class="productGrid">
				<c:forEach items="${searchPageData.results}" var="product" varStatus="status">
					<div class="span-6 ${(status.index+1)%3 == 0 ? ' last' : ''}${(status.index)%3 == 0 ? ' first clear' : ''}">
						<product:productListerGridItem product="${product}" />
					</div>
				</c:forEach>
			</div>
			
				<nav:pagination top="false"  supportShowPaged="${isShowPageAllowed}" supportShowAll="${isShowAllAllowed}"  searchPageData="${searchPageData}" searchUrl="${searchPageData.currentQuery.url}"  numberPagesShown="${numberPagesShown}"/>
		
		</div>
	</div> --%>
	
	
	

</template:page>


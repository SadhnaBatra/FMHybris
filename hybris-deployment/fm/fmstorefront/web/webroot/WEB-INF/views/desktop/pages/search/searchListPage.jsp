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




<template:page pageTitle="${pageTitle}">
	<jsp:attribute name="pageScripts">
		<script type="text/javascript" src="${commonResourcePath}/js/acc.productlisting.js"></script>
	</jsp:attribute>
	
	<jsp:body>
	<!-- InstanceBeginEditable name="Breadcrumb" -->
		<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
		  <div class="container">
		    <div class="row">
		      <breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
		    </div>
		  </div>
		</section>
	<!-- InstanceEndEditable --> 
	
	<div id="quick_fade"></div>
        <div id="quick_modal">
            <img id="loader" src="/fmstorefront/_ui/desktop/common/images/spinner.gif" />
        </div>

<section class="productListPage productCategoryPage pageContet" >
  <div class="container">
    <div class="row">
     <aside class="col-lg-3 col-md-3 col-sm-12 col-xs-12 ">
        <div class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs" id="accordion">
        	<div class="panel panel-default searchby">
	            <div class="panel-body">
	              <h4>
					<span class=" fa fa-search"></span> <spring:theme code="SearchresultsPage.refineResults.title"/></h4>
	            </div>
          </div>
           <%-- <nav:facetNavAppliedFilters pageData="${searchPageData}"/>  --%> 
          <nav:facetNavRefinements pageData="${searchPageData}"/>
        </div>
      </aside>
       <section class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
        <section class="prodList productCategory clearfix">
          <div class="search-result-title col-lg-12">
            <h2>${searchPageData.categoryCode}</h2>
          </div>
          <section class="rhsSearchby col-lg-12">
            <p><spring:theme code="SearchresultsPage.refineResults.description"/></p><br/>
            <div class="row">
              <div class="col-lg-6 col-md-6 col-xs-12">
                <div class="row">
                 
                    <nav:ymmFitment pageData="${searchPageData}"/> 
                 	
                </div>
              </div>
              
              <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                <div class="row">
                	<form class="rhsProdCat">
                			<nav:categoryRefinementDropdowns pageData="${searchPageData}" categoryCode="" />
                	</form>	
               </div> 
              </div>
              
            </div>
          </section>
          <section class="prodFilter col-lg-12">
          	<div class="visible-lg visible-md visible-sm clearfix">
		          	<nav:pagination top="true" supportShowPaged="${isShowPageAllowed}"
													supportShowAll="${isShowAllAllowed}"
													searchPageData="${searchPageData}"
													searchUrl="${searchPageData.currentQuery.url}"
													numberPagesShown="${numberPagesShown}" />
			</div>		
           
          </section>
          <section class="prodList col-lg-12"> 
          	<c:forEach  items="${searchPageData.results}" var="product"
										varStatus="status">
             	<product:productListerGridItem product="${product}" /> 
             </c:forEach>
          </section>
          
          <!-- pagination -->
          <section class="prodFilter col-lg-12">
            <nav:pagination top="true" supportShowPaged="${isShowPageAllowed}"
											supportShowAll="${isShowAllAllowed}"
											searchPageData="${searchPageData}"
											searchUrl="${searchPageData.currentQuery.url}"
											numberPagesShown="${numberPagesShown}" />
            
          </section>
        </section>
      </section>
    </div>
  </div>
</section>
	<div class="clearfix">
			<div class="well well-sm well-brandstrip clearfix">
				<ul class="nav ">
					<c:set var="fmComponentName" value="brandStrip" scope="session" />
	
					<cms:pageSlot position="FMBrandstrip" var="feature">
						<cms:component component="${feature}" />
					</cms:pageSlot>
					
				</ul>
			</div>
		</div>
	
	
	
	</jsp:body>
	
</template:page>	
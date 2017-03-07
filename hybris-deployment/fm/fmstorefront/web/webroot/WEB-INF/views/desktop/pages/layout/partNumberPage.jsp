<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<template:page pageTitle="${pageTitle}">
<c:set var="whereToBuyURI"><spring:eval expression="@propertyConfigurer.getProperty('wheretobuyurl')" /></c:set>
	<c:if test="${!loginError}">
	<div id="globalMessages">
		<common:globalMessages/>
	</div>

	</c:if>
	
<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
  <div class="container">
    <div class="row">
      <ul class="breadcrumb">
        <breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
      </ul>
    </div>
  </div>
</section>
	
	<section class="productDetailPage pageContet registrationContent">
    <div class="container">
      <div class="row">
	<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 contentLHS"> 
        <!--- Order in Progress PANEL -->
        <div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body orgMangPanel">
            <h3 class="text-uppercase">Parts Finder</h3>
            <ul class="">
              <!-- <li><a href="#">Vehicle <span class="linkarow fa fa-angle-right"></span></a></li>
              <li><a href="#">License Plate <span class="linkarow fa fa-angle-right"></span></a></li>
              <li><a href="#">VIN <span class="linkarow fa fa-angle-right"></span></a></li>
              <li><a href="#">Competitor <span class="linkarow fa fa-angle-right"></span></a></li> -->
              
               <li>  <spring:url value="/catalog/part-Number-search" var="partUrl" />
               
				<a href="${partUrl}" ${partsFinderLink eq 'PartNumberSearch' ? 'class="selected"' : 'class=""'}>Federal-Mogul Part Search <span
						${partsFinderLink eq 'PartNumberSearch' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a></li>
				<li><spring:url value="/catalog/partsFinderVehicleLookup" var="vehicleurl" /> <a
					href="${vehicleurl}">Vehicle Lookup<span class="linkarow fa fa-angle-right"></span></a></li>
				<li>
				<spring:url value="/catalog/partsFinderLicensePlateLookup" var="licenseurl" />
				<a href="${licenseurl}">License Plate Lookup<span
						class="linkarow fa fa-angle-right"></span></a></li>
				<li>
		<spring:url value="/catalog/partsFinderLicenseVINLookup" var="vinurl" />
				<a href="${vinurl}">VIN Lookup <span
						class="linkarow fa fa-angle-right"></span></a></li>
						
				<li>
				
				<spring:url value="/catalog/competitor-interchange" var="competitorurl" />
				<a href="${competitorurl}">Competitor Interchange <span
						class="linkarow fa fa-angle-right"></span></a></li>
              <li><a href="${whereToBuyURI}">Where to Buy <span class="linkarow fa fa-angle-right"></span></a></li>
            </ul>
          </div>
        </div>
      </div>
     
      
      
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 bgwhite contentRHS regFrm"> 
     		  
        		<form:form class="ymmForm" method="POST" id="partInterchangeForm" commandName="partInterchangeForm"  action="${request.contextPath}/catalog/part-Number-search" enctype="multipart/form-data">
                <h3 class="text-uppercase  registrationHeading">Federal-Mogul Part Search</h3>
                <div class="clearfix topMargn_35">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 form-group lftPad_0 regFormFieldGroup"> 
                  <input id="externalPart" class="form-control" required="required" type="text" placeholder="Part Number" name="partNumber" value="${partNumber}">                 
                </div>               
                 <div class="col-lg-8 col-md-8  col-sm-8  col-xs-12  topMargn_2 form-group"> <button type="submit" onclick="return submit();" class="btn  btn-sm btn-fmDefault">Look it Up</button> 
                 </div>
                        </div>    
              </form:form>
              
            
            
            
       
          <!--<p class="reqField"> <span class="fm_fntRed">*</span> Required Fields </p>-->
                               
          <c:choose>
		<c:when test="${not empty fmPartModel}">
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 table-responsive userTable">
           
            <table class="table tablesorter" id="myTable">
              <thead>
                <tr class="text-capitalize">
                  <th class="col-lg-2">Brand</th>
                  <th class="col-lg-2">Part Number</th>
                  <th class="col-lg-3">Part Description</th>

               
                </tr>
              </thead>
							
									<c:forEach items="${fmPartModel}" var="parts">
										<c:forEach items="${parts.corporate}" var="corpList">
											<c:set var="corpName" value="${corpList.corpname}" />
										</c:forEach>
										<c:forEach items="${parts.supercategories}" var="category">
											<c:set var="categorycode" value="${category.code}" />
										</c:forEach>
										<c:if test="${corpName ne 'dummy' }">
											<tbody>
												<tr>
													<td>${corpName}</td>
													<c:url var="productURL" value="p/${parts.code}" />
													<td><a class="fm-blue-text DIMbold" href="${productURL}?categoryCode=${categorycode}">${parts.partNumber}</a></td>
													<td>${parts.name}</td>
												</tr>
											</tbody>
										</c:if>
									</c:forEach>

								

						</table>
          </div>    
          </c:when>
								<c:otherwise>
									<tbody>
										<tr>
										   <c:if test="${not empty partNumber}">
											<td colspan="3" align="center">No Results Available.</td>
										  </c:if>	
										</tr>
									</tbody>
								</c:otherwise>
							</c:choose>
                  </div>   
      </div>
    </div>
  </section>
	
	

    
    
      
      
 
		

	<!-- Brandstrip -->

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>



</template:page>

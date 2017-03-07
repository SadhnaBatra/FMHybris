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
				<a href="${competitorurl}" ${partsFinderLink eq 'PartInterchange' ? 'class="selected"' : 'class=""'}>Competitor Interchange <span
						${partsFinderLink eq 'PartInterchange' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a></li>
              <li><a href="${wheretoBuyUrl}">Where to Buy <span class="linkarow fa fa-angle-right"></span></a></li>
			<spring:url value="/catalog/part-Number-search" var="partUrl" />
              <li><a href="${partUrl}">FM Part Search <span class="linkarow fa fa-angle-right"></span></a></li>
            </ul>
          </div>
        </div>
      </div>
     
      
      
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 bgwhite contentRHS regFrm"> 
     		  
        		<form:form class="ymmForm" method="POST" id="partInterchangeForm" commandName="partInterchangeForm"  action="${request.contextPath}/catalog/competitor-interchange" enctype="multipart/form-data">
                <h3 class="text-uppercase  registrationHeading">competitive interchange listings</h3>
                <div class="clearfix topMargn_35">
                <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 form-group lftPad_0"> 
                  <input id="externalPart" class="form-control" type="text" placeholder="Part Interchange#" name="externalPart">                 
                </div>               
                 <div class="col-lg-8 col-md-8  col-sm-8  col-xs-12  topMargn_2 form-group"> <button type="submit" onclick="return submit();" class="btn  btn-sm btn-fmDefault">Look it Up</button> 
                 </div>
                        </div>    
              </form:form>
              
            
            
            
       
          <!--<p class="reqField"> <span class="fm_fntRed">*</span> Required Fields </p>-->
            <c:forEach items="${partInterchangeFromModel}" var="part">  
            <c:if test="${part ne null }">
          		<c:set var="PartInterchange" value="true" />
            </c:if>
          </c:forEach>
          <c:if test="${PartInterchange eq true }">
          <form class="registrationB2b" id="qtyPartInterchangeForm" action="<c:url value="/orderupload/addPI"/>" method="post">
          
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 table-responsive userTable">
           
            <table class="table tablesorter" id="myTable">
              <thead>
                <tr class="text-capitalize">
                  <th class="col-lg-2">Manufacturers</th>
                  <th class="col-lg-2">Product Type</th>
                  <th class="col-lg-3">Manufacturer's Part Number</th>
                  <th class="col-lg-2">FM Brand</th>
                  <th class="col-lg-2">FM Part No.</th>
                  <c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer' or customerType eq 'b2cCustomer' or customerType eq 'CSRCustomer' or customerType eq 'FMAdmin'}">
                  <th class="col-lg-2">Quantity</th>
                  </c:if>
                </tr>
              </thead>
              
              <c:forEach items="${partInterchangeFromModel}" var="parts">  
                  <input type="hidden" name="piPartNumbers" value="${parts.fmcorpCode}${parts.fmRawPartNumber}"/> 
              <tbody>
                <tr>
                  <td>${parts.corpName}</td>
					<td>${parts.partTypeName}</td>
					<td>${parts.partNumber}</td>
					<td>${parts.fmcorpName}</td>
					<c:choose>
					<c:when test="${parts.fmcorpCode eq 'FELPF' or parts.fmcorpCode eq 'BTFUE' or parts.fmcorpCode eq 'TREDF' or parts.fmcorpCode eq 'YANKF' or 
					parts.fmcorpCode eq 'ZANXF' or parts.fmcorpCode eq 'NATHF' or parts.fmcorpCode eq 'NATLF' or parts.fmcorpCode eq 'SIGNF' or parts.fmcorpCode eq 'SLDPF' 
					or parts.fmcorpCode eq 'VILMF' or parts.fmcorpCode eq 'AEFM' or parts.fmcorpCode eq 'GMSPF' or parts.fmcorpCode eq 'MERCF' or parts.fmcorpCode eq 'AUBF' or parts.fmcorpCode eq 'CARTF' 
					or parts.fmcorpCode eq 'CHFLF' or parts.fmcorpCode eq 'FORDF' or parts.fmcorpCode eq 'MCRDF' or parts.fmcorpCode eq 'STERF' or parts.fmcorpCode eq 'BRIGF' or parts.fmcorpCode eq 'CSPF'
					or parts.fmcorpCode eq 'DANAF' or parts.fmcorpCode eq 'FELPR' or parts.fmcorpCode eq 'FM' or parts.fmcorpCode eq 'GMF' or parts.fmcorpCode eq 'INTCF' or parts.fmcorpCode eq 'BELDF' 
					or parts.fmcorpCode eq 'FPDF' or parts.fmcorpCode eq 'KOHLF' or parts.fmcorpCode eq 'ANCOF' or parts.fmcorpCode eq 'CCPF' or parts.fmcorpCode eq 'DITZF' or parts.fmcorpCode eq 'FELPD'
					or parts.fmcorpCode eq 'MCFM' or parts.fmcorpCode eq 'ONANF' or parts.fmcorpCode eq 'PKGFM' or parts.fmcorpCode eq 'SWITF' or parts.fmcorpCode eq 'TECUF' or parts.fmcorpCode eq 'TRWF' 
					or parts.fmcorpCode eq 'WGLF' or parts.fmcorpCode eq 'AFLOW' or parts.fmcorpCode eq 'SPDPF' or parts.fmcorpCode eq 'BCA' or parts.fmcorpCode eq 'CHRYF' or parts.fmcorpCode eq 'ENPRO'
					or parts.fmcorpCode eq 'FELPF' or parts.fmcorpCode eq 'GLYCD' or parts.fmcorpCode eq 'PAYNF' or parts.fmcorpCode eq 'PKGMG' or parts.fmcorpCode eq 'RDYF' or parts.fmcorpCode eq 'RFUEL' 
					or parts.fmcorpCode eq 'TRUFL' }">					
					<td><a class="fm-blue-text DIMbold" href="p/${parts.partFlagCode}${parts.fmRawPartNumber}?categoryCode=${parts.partTypeCode}">${parts.fmPartNumber}</a></td>		
					</c:when>
					<c:otherwise>
					<td><a class="fm-blue-text DIMbold" href="p/${parts.fmcorpCode}${parts.fmRawPartNumber}?categoryCode=${parts.partTypeCode}">${parts.fmPartNumber}</a></td>		
					</c:otherwise>	
					</c:choose>			
					<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer' or customerType eq 'b2cCustomer' or customerType eq 'CSRCustomer' or customerType eq 'FMAdmin'}">		
					<td><input type="text" maxlength="5" size="1" id="piQtys" name="piQtys" class="form-control width58 prodQtyInput" value="0" width="30%"></td>
					</c:if>
                </tr>                      
                </tbody>
                </c:forEach>
            </table>
          </div>
          <div class="row topMargn_25">
            <div class="col-md-5">
            <br><!-- <a href="javascript:history.back()"><span class="btn btn-sm btn-fmDefault text-uppercase registrationBtns">Back </span></a>   -->
		<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer' or customerType eq 'b2cCustomer' or customerType eq 'CSRCustomer' or customerType eq 'FMAdmin'}">
         		<button type="submit" class="btn btn-fmDefault">ADD TO CART</button>    
		</c:if>    
            </div>
          </div>         
          </form>
         </c:if>
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

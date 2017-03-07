<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <c:set var="fmComponentName" value="brand2" scope="session" />
        <c:set var="count" value="1" />
<section class="accountDetailPage pageContet" >
  <div class="container champion">
    <div class="row"> 
      <!-- Starts: Manage Account Left hand side panel -->
      <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"> 
        <!--- Order in Progress PANEL -->
        <div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body">      
             <cms:slot  var="feature" position="FerodoCompanySection">
             <c:choose>
             
      <c:when test="${feature.uid=='FMBrandlandingImage13'}">
      
        <div class="championLeftImagePanelNew"> 
     	 <cms:component component="${feature}" />
        </div>
     
         
      </c:when>
    
    
          
       <c:when test="${feature.uid=='FerodoUrl'}">
        
      <div class="championLeftImagePanel"> 
        <c:if test="${feature.url!=null}">
     	<a class="fa fa-sign-out orderPdf"><cms:component component="${feature}" /> </a>
      </c:if>
 		</div>
          
      </c:when>
   <c:when test="${feature.uid != 'FerodoUrl' && feature.uid !='FMBrandlandingImage13'}">
    	<c:set var="fmComponentName" value="ancoUrl" scope="session" />
      	
 			<c:if test="${count == 1 }" >
          		<h4 class="text-uppercase">Browse Parts</h4>
          	</c:if>
          	<c:set var="count" value="${count + 1 }" />
          <ul class="">
              <li> <a> <cms:component component="${feature}" /> <span class="linkarow fa fa-angle-right "></span></a></li>
                                   
            </ul>
   	</c:when>
          
      
            
             </c:choose>
               </cms:slot>
             
          </div>
        </div>
      </div>
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalLanding">
        <div class="">  
       <!--  <div class="internalPage rightHandPanel clearfix">  -->
           <c:set var="fmComponentName" value="brand" scope="session" />
         <cms:slot  var="feature" position="FerodoInternalContentSection">
             <cms:component component="${feature}" /> 
              </cms:slot>          
            
          <!-- </div>  -->
          
            </div>
          </div>
          
        </div>
      </div>
    
</section>

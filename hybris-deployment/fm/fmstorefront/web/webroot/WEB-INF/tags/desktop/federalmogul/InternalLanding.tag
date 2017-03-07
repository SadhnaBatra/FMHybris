

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<section class="accountDetailPage pageContet" >
  <div class="container companyOverview">
    <div class="row"> 
    				 
      <!-- Starts: Manage Account Left hand side panel -->
      <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"> 
        <!--- Order in Progress PANEL -->
        <div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body">
            <h4 class="text-uppercase" >Company</h4>
            
		
		
            <ul class="">
            <c:forEach var="component" items="${slots.CompanySection.cmsComponents}" varStatus="status">
		
   			<li>
   			<%-- <a onclick="showhide('${component.content}','${component.uid}');" id="${component.uid}" > ${component.name}  <span class="linkarow fa fa-angle-right " ></span> </a> --%>
   			<a href="/fmstorefront/AboutUs/company/${component.name}" > ${component.name}  <span class="linkarow fa fa-angle-right " ></span> </a>
   			</li >
		    </c:forEach> 
            </ul>
       
          </div>
        </div>
      </div>
      
     
      
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalLanding">
      	
        <div class="internalPage rightHandPanel clearfix" >
       <div id=defaultshow >
        <c:forEach begin="0" end="${slots.CompanySection.cmsComponents.size()}" var="component" items="${slots.CompanySection.cmsComponents}">
 
        <c:if test="${component.name eq componentUid}">
        	<p>${component.content}</p></c:if>
 
     
       </c:forEach>
 
       <c:if test="${ componentUid eq null }">
       	<c:set var="component" value="${slots.CompanySection.cmsComponents.get(0)}">
       	</c:set>
        	<p>${component.content}</p>
 
  		</c:if>
            </div>
       </div>
  </div>
        </div>
      </div>
    
   

</section>
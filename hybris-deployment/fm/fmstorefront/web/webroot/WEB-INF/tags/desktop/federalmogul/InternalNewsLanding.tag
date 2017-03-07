

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
            <h4 class="text-uppercase" >News</h4>
            
		
		
            <ul >
            <c:forEach var="component" items="${slots.CompanySection2.cmsComponents}" varStatus="status">
		
   			<li >
   			<c:url value="/about-us/news/${component.uid}?complnkname=${component.name}" var="aboutusURL" />
   			<c:choose>
   			<c:when test="${component.uid eq componentUid}">
   			
   				<a href="${ aboutusURL}" class="selected">${component.name}<span class="linkarow fa fa-angle-right "></span></a>
   			</c:when>
			<c:when test="${ (componentUid eq null) and (component.uid eq 'news') }">
   			
   				<a href="${ aboutusURL}" class="selected">${component.name}</a>
   			</c:when>
   			<c:otherwise>
   				<a href="${ aboutusURL}">${component.name}<span class="linkarow fa fa-angle-right "></span></a>
   			</c:otherwise>
   			
   			</c:choose>
    			
   			</li >
		    </c:forEach> 
            </ul>
       
          </div>
        </div>
      </div>
      
     
      
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalLanding">
      	
        <div class="internalPage rightHandPanel clearfix" >
        <div id=defaultshow >
        <c:forEach begin="0" end="${slots.CompanySection2.cmsComponents.size()}" var="component" items="${slots.CompanySection2.cmsComponents}">

        <c:if test="${component.uid eq componentUid}">
        	<p>${component.content}</p></c:if>

     
       </c:forEach>
       <c:if test="${ componentUid eq null }">
       	<c:set var="component" value="${slots.CompanySection2.cmsComponents.get(0)}">
       	</c:set>
        	<p>${component.content}</p>


  		</c:if>
        </div>      
       </div>
  </div>
        </div>
      </div>
    
   

</section>
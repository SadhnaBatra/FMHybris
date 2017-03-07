

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
            <h4 class="text-uppercase" class="selected">Learning Center</h4>
            
		
		
            <ul >
            <li>
            	<c:url value="/lc/learningCenter" var="encodedUrl" /> 
   				<a href="${encodedUrl}"> Overview&nbsp;<span class="linkarow fa fa-angle-right "> </span></a>
   			</li>
   			
            <c:forEach var="component" items="${slots.LearningCenterLinksSectionVG.cmsComponents}" varStatus="status">
		
   			<li >
   				<c:url value="/lc/video-gallery/${component.uid}?complnkname=${component.name}" var="learningcenterURL" />
   				<c:choose>
   					<c:when test="${component.uid eq componentUid}">
   						<a href="${ learningcenterURL}" class="selected"> ${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   					</c:when>
   			
					<c:when test="${ (componentUid eq null) and (component.uid eq 'videogallery') }">
   						<a href="${ learningcenterURL}" class="selected"> ${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   					</c:when>
   			
   					<c:when test="${ (componentUid eq null) and (component.uid eq 'training-options') }">
   						<c:set var="component" value="${slots.LearningCenterLinksSectionVG.cmsComponents.get(0)}"></c:set>       	
   						<a href="${component.content}" target="_blank">${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   					</c:when>
   			
   					<c:when test="${ (componentUid eq null) and (component.uid eq 'courses') }">
   						<c:set var="component" value="${slots.LearningCenterLinksSectionVG.cmsComponents.get(1)}"></c:set>       	
   						<a href="${component.content}" target="_blank">${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   					</c:when>
   				
   					<c:when test="${ (componentUid eq null) and (component.uid eq 'techtips') }">
   						<c:set var="component" value="${slots.LearningCenterLinksSectionVG.cmsComponents.get(2)}"></c:set>        	
   						<a href="${component.content}" target="_blank">${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   					</c:when>
   			
   					<c:otherwise>
   						<c:choose>
   							<c:when test="${ component.uid eq 'training-options' }">
   								<c:set var="component" value="${slots.LearningCenterLinksSectionVG.cmsComponents.get(0)}"></c:set>
   								<a href="${component.content}" target="_blank">${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   							</c:when>
   							
   							<c:when test="${ component.uid eq 'courses' }">
   								<c:set var="component" value="${slots.LearningCenterLinksSectionVG.cmsComponents.get(1)}"></c:set>
   								<a href="${component.content}" target="_blank">${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   							</c:when>
   							
   							<c:when test="${ component.uid eq 'techtips' }">
   								<c:set var="component" value="${slots.LearningCenterLinksSectionVG.cmsComponents.get(2)}"></c:set>
   								<a href="${component.content}" target="_blank">${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   							</c:when>
   							
   							<c:otherwise>
   								<a href="${ learningcenterURL}"> ${component.name}  <span class="linkarow fa fa-angle-right "></span> </a>
   							</c:otherwise>
   						</c:choose>   				
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
        <c:forEach begin="0" end="${slots.LearningCenterLinksSectionVG.cmsComponents.size()}" var="component" items="${slots.LearningCenterLinksSectionVG.cmsComponents}">

        <c:if test="${component.uid eq componentUid}">
        	<p>${component.content}</p></c:if>

  
       </c:forEach>
       <c:if test="${ componentUid eq null }">
       	<c:set var="component" value="${slots.LearningCenterLinksSectionVG.cmsComponents.get(6)}">
       	</c:set>
        	<p>${component.content}</p>


  		</c:if>
        </div>      
       </div>
  </div>
        </div>
      </div>
    
   

</section>
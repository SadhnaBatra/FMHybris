

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>

<section class="accountDetailPage pageContet" >
  <div class="container companyOverview">
    <div class="row"> 
    				 
      <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 contentLHS"> 
           <!--- Order in Progress PANEL -->
        <div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body">
          <h3 class="text-uppercase">Customer Service</h3>
              <div class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs" id="accordion"> 
                <!-- accordian starts -->
                <div class="panel panel-default">
                  <div class="acc-heading1">
                  </div>              
                <div class="panel panel-default">
                <c:forEach var="component" items="${slots.SupportCustomerSection.cmsComponents}" varStatus="status">	
             
                  <div class="acc-heading1">            
                   <c:url value="/support/customer-service/${component.uid}?complnkname=${component.name}" var="aboutusURL" />  
                   
                   <c:set var="selMenu" value="${component.uid == componentUid ? 'true' : 'false' }" />
		    		<c:set var="comParagraphCount" value="0" />
                    <c:forEach var="compParagraph" items="${component.fmParagraphComp}" varStatus="statusList">
                    	<c:if test="${compParagraph.uid ==  componentUid}">
                    		<c:set var="selMenu" value="true" />
                    	</c:if>
                    	<c:set var="comParagraphCount" value="${comParagraphCount +1 }" />
                    </c:forEach>
                   <c:choose>
                    <c:when test="${comParagraphCount > 2 }">
                    	    <h4 class="panel-title"> <a href="${ aboutusURL}" ${selMenu == 'true' ? 'class="selected toggle"' : 'class="toggle collapsed"'} id="dropdown-detail-1" data-toggle="detail-${status.count}"> <span class=" ">${component.name}</span><span class="linkarow fa fa-angle-right "></span><span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="Brands"></span> </a> </h4>  
                    </c:when>
                    <c:otherwise>
						<c:choose>
				           <c:when test="${componentUid eq null and  component.name eq 'Customer Service Link'}" >
								<h4 class="panel-title"><a href="${ aboutusURL1}" class="selected toggle"> <span class="text-capitalize">${component.name}</span></a></h4>
				
							   </c:when>
								<c:otherwise>
                    				<h4 class="panel-title"><a href="${ aboutusURL}" ${selMenu == 'true' ? 'class="selected "' : 'class=""'} id="dropdown-detail-1" ><span class=" ">${component.name}</span><span class="linkarow fa fa-angle-right "></span><span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="Brands"></span></a> </h4>                    				
								</c:otherwise>
						</c:choose>                    
                    </c:otherwise>
                    </c:choose>
                   
                    <%-- <h4 class="panel-title"> <a href="${ aboutusURL}" ${selMenu == 'true' ? 'class="toggle"' : 'class="toggle collapsed"'} id="dropdown-detail-1" data-toggle="detail-${status.count}"> <span class=" ">${component.name}</span><span class="linkarow fa fa-angle-right "></span><span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="Brands"></span> </a> </h4> --%>
                 <c:if test="${comParagraphCount > 1 }">
                  <div id="detail-${status.count}" ${selMenu == 'true' ? 'style="display:block"' : 'style="display:none"'} >
                                        <div class="panel-body acc-body">
                      <form class="form-horizontal" role="form">
                        <div class="form-group btmMrgn_0">
                          <ul class="filter-option">
                           
				   			  <c:forEach var="componentList" items="${component.fmParagraphComp}" varStatus="statusList">
                            <c:url value="/support/customer-service/${componentList.uid}?complnkname=${componentList.name}" var="aboutusURLNew" />
                          <c:choose>
				   			<c:when test="${componentList.uid eq componentUid}">				   			
				   				<li><a href="${ aboutusURLNew}" class="selected"><span class="fa fa-minus subCatMinus"></span>&nbsp;<span>${componentList.name} </span></a> </li>
				   			</c:when>	
				   			
				   			<c:when test="${componentList.name ne component.name}">				   			
				   				<li><a href="${ aboutusURLNew}" class="selected"><span class="fa fa-minus subCatMinus"></span>&nbsp;<span>${componentList.name} </span></a> </li>
				   			</c:when>				   			
		
				   			<c:otherwise>
				   				<!-- <li><a href="${ aboutusURLNew}"><span class="fa fa-minus subCatMinus"></span>&nbsp;<span>${componentList.name} </span></a> </li> -->
				   			</c:otherwise>
				   			
				   			</c:choose>
                        
                         
                              </c:forEach> 
                          </ul>
                        </div>
                      </form>
                    </div>
                  </div>
                  </c:if>
                  </div>
                  </c:forEach> 
		   
                </div>
	              </div>
	             </div> 
          
          </div>
        </div>
         
        
        
        
      </div>
     <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalLanding">
      	
        <div class="internalPage rightHandPanel clearfix" >
        <div id=defaultshow >
        <c:forEach begin="0" end="${slots.SupportCustomerSection.cmsComponents.size()}" var="component" items="${slots.SupportCustomerSection.cmsComponents}">
         <c:forEach begin="0" end="${component.fmParagraphComp.size()}" var="componentList" items="${component.fmParagraphComp}">

        <c:if test="${componentList.uid eq componentUid}">
        	<p>${componentList.content}</p></c:if>
	</c:forEach>
      <c:if test="${component.uid eq componentUid}">
        	<p>${component.content}</p></c:if>
       </c:forEach>
       <c:if test="${ componentUid eq null }">
       	<c:set var="component" value="${slots.SupportCustomerSection.cmsComponents.get(0)}">
       	</c:set>
        	<p>${component.content}</p>


  		</c:if>
        </div>      
       </div>
  </div>
</div>
</div>
</section>
 

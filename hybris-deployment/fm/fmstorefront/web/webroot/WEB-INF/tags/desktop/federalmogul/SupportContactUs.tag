<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>

<section class="accountDetailPage pageContet" >
  <div class="container companyOverview">
    <div class="row">                
      <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 contentLHS"> 
        <div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body">
            <h3 class="text-uppercase">Contact Us</h3>
            <div class="contactAddressTitle">
              <b>World Headquarters</b>
            </div>
            <div class="contactAddress">
              <p>27300 W 11 Mile Rd</p>
              <p>Southfield, MI 48034</p>
            </div>
            <div class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs" id="accordion"> 
              <div class="panel panel-default">
                <c:forEach var="component" items="${slots.SupportLinkSection.cmsComponents}" varStatus="status">  
             
                  <div>            
                    <c:url value="/support/contact-us/${component.uid}?complnkname=${component.name}" var="aboutusURL" />
                    <c:url value="/leadGeneration/contact-us" var="encodedUrl" />  
                    <c:set var="selMenu" value="${component.uid == componentUid ? 'true' : 'false' }" />
                    <c:set var="comParagraphCount" value="0" />
                    <c:forEach var="compParagraph" items="${component.fmParagraphComp}" varStatus="statusList">
                      <c:if test="${compParagraph.uid ==  componentUid}">
                          <c:set var="selMenu" value="true" />
                      </c:if>
                      <c:set var="comParagraphCount" value="${comParagraphCount +1 }" />
                    </c:forEach>
                   
                    <c:choose>
                      <c:when test="${comParagraphCount >= 1 }">
                           <h4 class="panel-title"> <a href="${ aboutusURL}" ${selMenu == 'true' ? 'class="selected toggle"' : 'class="toggle collapsed"'} id="dropdown-detail-1" data-toggle="detail-${status.count}"> <span class="text-capitalize">${component.name}</span><span ${selMenu == 'false' ? 'class="linkarow fa fa-angle-right"' : 'class=""'}></span><span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="Brands"></span> </a> </h4>
                      </c:when>
                      <c:otherwise>
                        <c:if test="${component.name eq 'General Inquiry'}" >
                              <h4 class="panel-title"><a href="${ encodedUrl}" ${selMenu == 'true' ? 'class="selected "' : 'class=""'}  ><span class="text-capitalize">2${component.name}</span><span></span></a> 
                          </h4>
                          </c:if>
                        <c:choose>
                          <c:when test="${componentUid eq null and  component.name eq 'Overview'}" >
                            <h4 class="panel-title"><a href="${ aboutusURL}"  class="selected toggle" ><span class="text-capitalize">${component.name}</span><span></span></a> 
                            </h4>
                          </c:when>
                          <c:otherwise>
                            <h4 class="panel-title"><a href="${ aboutusURL}" ${selMenu == 'true' ? 'class="selected "' : 'class=""'} id="dropdown-detail-1" ><span class=" ">${component.name}</span><span ${selMenu == 'false' ? 'class="linkarow fa fa-angle-right"' : 'class=""'}></span><span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="Brands"></span></a> 
                            </h4>
                          </c:otherwise>
                        </c:choose>                     
                      </c:otherwise>
                    </c:choose>
                    <%-- Removed in F20-344; not currently in use on this page
                    <div id="detail-${status.count}" ${selMenu == 'true' and comParagraphCount >= 1 ? 'style="display:block"' : 'style="display:none"'} >
                      <div class="panel-body acc-body">
                        <form class="form-horizontal" role="form">
                          <div class="form-group btmMrgn_0">
                            <ul class="filter-option">
                              <c:forEach var="componentList" items="${component.fmParagraphComp}" varStatus="statusList">
                                <c:url value="/support/contact-us/${componentList.uid}?complnkname=${componentList.name}" var="aboutusURLNew" />
                              </c:forEach> 
                            </ul>
                          </div>
                        </form>
                      </div>
                    </div> --%>
                  
                  </div>
                </c:forEach> 
         
              </div>
            </div> 
          
          </div>
        </div>
        
      </div>
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 internalLanding">    
        <div class="internalPage rightHandPanel clearfix" >
          <div id="defaultshow">
            <c:forEach begin="0" end="${slots.SupportLinkSection.cmsComponents.size()}" var="component" items="${slots.SupportLinkSection.cmsComponents}">
              <c:forEach begin="0" end="${component.fmParagraphComp.size()}" var="componentList" items="${component.fmParagraphComp}">
                <c:if test="${componentList.uid eq componentUid}">
                 <p>${componentList.content}</p>
                </c:if>
              </c:forEach>
              <c:choose>
                <c:when
                    test="${component.uid eq componentUid and  componentUid eq 'Locations'}">
                        <federalmogul:fmBranchLocations/>
                </c:when>
                <c:otherwise>
                    <c:if test="${component.uid eq componentUid}">
                        <p>${component.content}</p>
                    </c:if>
                </c:otherwise>
              </c:choose>
            </c:forEach>
         
            <c:if test="${componentUid eq 'supportgeneralInquiry'}">
              <federalmogul:leadGenerationCallBack /> 
            </c:if>
             
            <c:if test="${ componentUid eq null }">
              <c:set var="component" value="${slots.SupportLinkSection.cmsComponents.get(0)}"></c:set>
              <p>${component.content}</p>
            </c:if>
          </div>      
        </div>
      </div>
    </div>
  </div>
</section>
 

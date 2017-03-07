<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body orgMangPanel">
            <h3 class="text-uppercase">contact</h3>
            <div class="contactAddressTitle"><b>World Headquarters</b></div>
            <div class="contactAddress">
              <p>27300 W 11 Mile Rd</p>
              <p>Southfield, MI 48034</p>
            </div>
            <ul class="">

			 	<br/>
			  <!-- Balaji---Start Order Lead Generation Call back -->
			  <li><c:url value="/leadGeneration/contact-us" var="encodedUrl" /> 
                     <a href="${encodedUrl}" class="${selectedLink eq 'ReturnRequest' ? 'selected' : ''}"> 
                                Contact Us<span class="linkarow fa fa-angle-right "></span> 
                     </a></li>
			  <!-- 
			  <li><c:url value="/leadGeneration/leadGeneration-partEnqueries" var="encodedUrl" /> 
                      <a href="${encodedUrl}" class="${selectedLink eq 'ReturnHistory' ? 'selected' : ''}"> 
                                Part Enqueries<span class="linkarow fa fa-angle-right "></span> 
                      </a></li>
                      
               <li><c:url value="/leadGeneration/leadGeneration-mediaContacts" var="encodedUrl" /> 
               		<a href="${encodedUrl}" class="${selectedLink eq 'ReturnHistory' ? 'selected' : ''}"> 
                         Media Contacts<span class="linkarow fa fa-angle-right "></span> 
               </a></li>
               
               <li><c:url value="/leadGeneration/leadGeneration-investors" var="encodedUrl" /> 
               		<a href="${encodedUrl}" class="${selectedLink eq 'ReturnHistory' ? 'selected' : ''}"> 
                         Investors<span class="linkarow fa fa-angle-right "></span> 
               </a></li>
               
               <li><c:url value="/leadGeneration/leadGeneration-suppliers" var="encodedUrl" /> 
               		<a href="${encodedUrl}" class="${selectedLink eq 'ReturnHistory' ? 'selected' : ''}"> 
                         Suppliers<span class="linkarow fa fa-angle-right "></span> 
               </a></li>
               
               <li><c:url value="/leadGeneration/leadGeneration-otherLocations" var="encodedUrl" /> 
               		<a href="${encodedUrl}" class="${selectedLink eq 'ReturnHistory' ? 'selected' : ''}"> 
                         Other Locations<span class="linkarow fa fa-angle-right "></span> 
               </a></li>
			  -->
              
              <!-- Balaji---End Order Lead Generation Call back -->      
			
             
            </ul>
          </div>
        </div>
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%-- <%@ attribute name="returnOrderData" required="true" type="com.federalmogul.facades.order.data.FMReturnOrderData" %> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
	<c:if test="${fn:escapeXml(returnOrder.returnCode) eq '000' }">
	  	 <div class="manageUserPanel rightHandPanel clearfix">
	        <h2 class="text-uppercase">Return Request Confirmation</h2>
	        <div class="clearfix btmMrgn_30">
	        <div class="alert alert-success col-lg-12 col-md-12 col-sm-12 col-xs-12">
	          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 uploadOrderGreenPanel"> <span class="fa fa-check-circle uploadOrderCheck pull-left"></span>
	             	
	             <div class="pull-left">
	              <p class="uploadOrderBold">Order return has been processed and is pending for approval. </p>
	              <p class="uploadOrderBold">Return confirmation no: ${fn:escapeXml(returnOrder.returnId)} </p>
	            </div> 
	            
	          </div>
	        </div>
	        </div>
	      </div>
	  </c:if> 
	  
	<c:if test="${fn:escapeXml(returnOrder.returnCode) eq '001' }">     
	     <div class="manageUserPanel rightHandPanel clearfix">
	       <h2 class="text-uppercase">Return Request Unsuccessful</h2>
	        <div class="pull-left">
	            <p class="uploadOrderBold">Return Order not processed. Please check with FM customer care </p>
	       </div>
	         
	     </div>
	 </c:if>  
      
  </div>
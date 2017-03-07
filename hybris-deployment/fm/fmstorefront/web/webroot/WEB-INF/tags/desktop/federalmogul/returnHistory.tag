<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<section id="returnHistory" class="accountDetailPage pageContet" >
 <!-- Starts: Manage Account Right hand side panel -->
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
        <div class="manageUserPanel rightHandPanel clearfix">
          <h2 class="text-uppercase">Return History</h2>
          <div class="table-responsive col-lg-12 userTable returnHistory">
            <table class="table tablesorter" id="myTable">
              <thead>
                <tr>
                  <th class="">Return Confirmation #</th>
                  <th class="">Date of Return </th>
                  <th class="">Confirmation #</th>
                  <th class="">Sales Order #</th>
                  <th class="">Status</th>
                </tr>
              </thead>
              <tbody>
              <c:if test="${not empty ReturnOrderHeaderStatus }"  >   
	              <c:forEach items="${ReturnOrderHeaderStatus}" var="order" varStatus="status">
		          	<c:forEach items="${order.orderUnitList}" var="orderUnit" >
		          		<c:forEach items="${orderUnit.shippingUnitList}" var="shippingUnit" >
			                <tr>
			                  <td class="linkCol">
			                  	<c:url value="/my-fmaccount/return-details" var="encodedUrl" />
			                  <a href="${encodedUrl}">${orderUnit.orderNumber}</a></td>
			                  <td><fmt:formatDate value="${orderUnit.originalOrderDate}" pattern="MM/dd/yyyy"/></td>
			                  <td>${orderUnit.orderNumber}</td>
			                  <td class="text-uppercase">${orderUnit.orderNumber}</td>
			                  <td class="text-capitalize">${shippingUnit.packingStatus}</td>
			                </tr>
			            </c:forEach>
			         </c:forEach>
			       </c:forEach>
               </c:if> 
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <!-- Ends: Manage Account Right hand side panel --> 
    
</section>
<!-- InstanceEndEditable --> 

  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   <td colspan="6">
	   <table class="table tablesorter" >
		   <thead>
		     <tr class="text-capitalize">
		       <th class="width175">Update User ID # </th>
		       <th class="width175">Action Timestamp </th>
		       <th class="width175">Status </th>
		       <th class="width175">Order ID </th>
		     </tr>
		   </thead>
	      <tbody>
	       	<c:if test="${not empty UploadOrderHistoryData}">
			<c:forEach items="${UploadOrderHistoryData}" var="history" varStatus="status">
	          <tr class="">
	            <td>${history.user}</td>
	            <td><fmt:formatDate value="${history.updatedTime}" pattern="MM/dd/yyyy"/></td>
	            <td>${history.status}</td>
	            <td>${history.orderNumber}</td>
	          </tr>
	        </c:forEach>
	       	</c:if>
	      </tbody>
	    </table>
    </td>

		                
 
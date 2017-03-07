  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  	                  
<td colspan="6"><table class="table tablesorter" >
    <thead>
      <tr class="text-capitalize">
        <th>Part #</th>
        <th>Part Quantity</th>
        <th>Product Flag</th>
        <th>Description</th>
        <th>Remove</th>
      </tr>
    </thead>
    <tbody>
    <c:if test="${not empty uploadOrderEntryData}">
    	<c:forEach items="${uploadOrderEntryData}" var="entry" varStatus="status">
	      <tr  class="noBorder">
	        <td colspan="3" class=""><span class="fm_fntRed">Status:</span> ${entry.partResolutionMsg}</td>
	        <td></td>
	        <td></td>
	      </tr>
	      <tr class="">
	        <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="${entry.partNumber}" placeholder="12998881"></td>
	        <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="${entry.quantity}" placeholder="1"></td>
	        <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="AMG"></td>
	        <td></td>
	        <td><a href="#" class="fa fa-close uploadOrderClose uploadOrderAction"></a></td>
	      </tr>
	   </c:forEach>
     </c:if>
    </tbody>
  </table>
  <div class="">
    <button class="btn btn-default btn-fmDefault uploadOrderBtn" type="button">Save</button>
    <button class="btn btn-default btn-fmDefault uploadOrderBtn" type="button">Cancel</button>
  </div></td>
		                     
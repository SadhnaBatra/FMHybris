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
    	<c:if test="${entry.partResolutionMsg != 'Part Resloved'}" >
	    	<c:set var="uploadorder" value="${entry.entryNumber}" />
		      <tr  class="noBorder">
		        <td colspan="3" class=""><span class="fm_fntRed">Status:</span> ${entry.partResolutionMsg}</td>
		        <td></td>
		        <td></td>
		      </tr>
		      <tr class="">
		        <td><input id="part_${status.index }" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" onchange="javascript:updateUploadOrderEntry('${status.index }','${entry.entryNumber}');" value="${entry.partNumber}" placeholder=""></td>
		        <td><input id="qty_${status.index }" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" onchange="javascript:updateUploadOrderEntry('${status.index }','${entry.entryNumber}');" value="${entry.quantity}" placeholder="1"></td>
		        <td><input id="productFlag_${status.index }" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" onchange="javascript:updateUploadOrderEntry('${status.index }','${entry.entryNumber}');" value="${entry.productFlag}" placeholder="1"></td>
		        <td>${entry.productDescription}</td>
		        <td><a href="javascript:deleteUploadOrderEntry('${entry.entryNumber}')" class="fa fa-close uploadOrderClose uploadOrderAction"></a></td>
		      </tr>
		  </c:if>
	   </c:forEach>
     </c:if>
    </tbody>
  </table>
  <div class="">
    <button onclick="javascript:saveUploadOrder('${uploadOrderCode}')" class="btn btn-default btn-fmDefault" type="button">Save</button>
    <button onclick="javascript:closeUploadOrder('${uploadOrderCode}')" class="btn btn-default btn-fmDefault uploadOrderBtn" type="button">Cancel</button>
  </div></td>
		                     
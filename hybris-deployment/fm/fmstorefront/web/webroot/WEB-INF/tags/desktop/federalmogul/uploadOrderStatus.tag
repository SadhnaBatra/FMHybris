  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
      <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  <div class="container manageAccount">
    <div class="row"> 
      <!-- Starts: Manage Account Right hand side panel -->
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
      <!--<div class=""><h2 class="text-uppercase">Address Details</h2></div> -->
        <div id="manageAccountTab" class="productDetailPageTab manageAccountTab horizontalTab">
          <ul class="resp-tabs-list pull-left">
            
            <li id="uploadorder" >Upload Order Status</li>
          </ul>
           
              <spring:url value="/my-fmaccount/order-history" var="ordersearchurl"/>
          <div class="resp-tabs-container">

             <div class="uploadOrderStatus" id="uploadOrderStatus">
              <form class="uploadOrderStatus">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="pruchaseOrder_uo" class="form-control" value="" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder_uo" class="form-control" type="text" placeholder="Confirmation Order #">
                  </div>
 		  <!-- <div class="form-group col-sm-3">
                    <input id="AccountsCode_uo" class="form-control" type="text" placeholder="Accounts #">
                  </div> -->

                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-6" type="text" class="date-picker form-control" placeholder="Start Date" />
                        <label for="date-picker-6" class="input-group-addon btn"><span id="startDate" class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-7" type="text" class="date-picker form-control" placeholder="End Date" />
                        <label for="date-picker-7" class="input-group-addon btn"><span id="endDate" class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <select class="form-control" id="uploadOrrderStatus">
                      <option>Requiring Attention</option>
                      <option>Hold</option>
                      <option>Submitted</option>
                      <option>ALL</option>
                    </select>
                  </div>
                </div>
                 
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right">
                      <button type="button" onClick="javascript:OrderSearch();" id="uploadOrderSearch_uo" class="btn btn-default btn-fmDefault">Search</button>
                    </div>
                  </div>
                </div>
               
              </form>
              
              <c:if test="${not empty uploadOrderData}">
	              <div class="uploadOrderStatus uploadOrderTabTable">
	                <div class="table-responsive userTable">
	                  <table class="table tablesorter" id="myTable">
	                    <thead>
	                      <tr class="text-capitalize">
	                        <th class="">PO # </th>
	                        <th class="">Account Code</th>
	                        <th class="">User ID </th>
	                        <th class="">Status </th>
	                        <th class="">Updated </th>
	                        <th class="">Action </th>
	                      </tr>
	                    </thead>
	                    <tbody>
	                    <c:forEach items="${uploadOrderData}" var="order" varStatus="status">
		                      <tr class="orderDetails${status.index}">
		                        <td>${order.PONumber}</td>
		                        <td>${order.soldToAccount.uid}</td>
		                        <td>${order.userFirstName}</td>
		                        <td>
						<c:choose>
							<c:when test="${order.status eq 'fileParsed'}">
								<spring:theme
									code="text.account.uploadOrderStatus.fileParsed"
									text="File Parsed" />
							</c:when>
							<c:when test="${order.status eq 'FileParseError'}">
								<spring:theme
									code="text.account.uploadOrderStatus.FileParseError"
									text="File Parse Error" />
							</c:when>
							<c:when test="${order.status eq 'PartResolutionError'}">
								<spring:theme
									code="text.account.uploadOrderStatus.PartResolutionError"
									text="Part Resolution Error" />
							</c:when>
							<c:when test="${order.status eq 'OrderError'}">
								<spring:theme
									code="text.account.uploadOrderStatus.OrderError"
									text="Order Error" />
							</c:when>
							<c:when test="${order.status eq 'InProgress'}">
								<spring:theme
									code="text.account.uploadOrderStatus.InProgress"
									text="In Progress" />
							</c:when>
							<c:when test="${order.status eq 'PartResolutionIssue'}">
								<spring:theme
									code="text.account.uploadOrderStatus.PartResolutionIssue"
									text="Part Resolution Issue" />
							</c:when>
							<c:when test="${order.status eq 'PartsResolved'}">
								<spring:theme
									code="text.account.uploadOrderStatus.PartsResolved"
									text="Parts Resolved" />
							</c:when>
							<c:when test="${order.status eq 'SystemError'}">
								<spring:theme
									code="text.account.uploadOrderStatus.SystemError"
									text="System Error" />
							</c:when>
							<c:otherwise>
								${order.status}
							</c:otherwise>
						</c:choose>
					</td>
		                        <td><fmt:formatDate value="${order.updatedTime }" pattern="MM/dd/yyyy"/></td>
		                        <td class="uploadOrderAction">
		                        	<c:if test="${order.status != 'Submitted'}" >
		                        		<span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Edit">
		                        		<a title="Modify the Order" onClick="$('#EditOrderTableRow_${order.code}').toggle();$('.upOrderPencil-${order.code}').toggleClass('selected'); $('#EditOrderHistory_${order.code}').hide();$('.upOrderClock-${order.code}').removeClass('selected');javascript:editUploadOrderEntry('${order.code}');" class="fa fa-pencil upOrderPencil-${order.code}"></a></span> 
		                        	</c:if>
		                        	<span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Download">
		                        		<a title="Download" href="${order.uploadfilemedia.downloadURL }" class="fa fa-download upOrderDownload-${order.code}"></a></span> 
		                       		<span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="History">
		                        		<a title="Order History" onClick="$('#EditOrderHistory_${order.code}').toggle();$('.upOrderClock-${order.code}').toggleClass('selected');$('#EditOrderTableRow_${order.code}').hide();$('.upOrderPencil-${order.code}').removeClass('selected');javascript:showUploadOrderHistory('${order.code}');" class="fa fa-clock-o upOrderClock-${order.code}"></a></span> 
		                        	<c:if test="${order.status != 'Submitted'}" >
		                        	 <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Delete">
		                        		<a title="Cancel the Order"  href="javascript:deleteUploadOrder('${order.code}')" class="fa fa-trash delete upOrderDelete-${order.code}"></a></span>
		                        	</c:if>
		                       </td>
		                      </tr>
		                      <tr class="EditOrderTableRow" id="EditOrderTableRow_${order.code}" style="display:none">
		                      </tr>
		                      <tr class="EditOrderHistory" id="EditOrderHistory_${order.code}"style="display:none">
		                       </tr>
		                   
						</c:forEach>
	                    </tbody>
	                  </table>
	                  <!-- table two --> 
	                </div>
	              </div>
	           </c:if>
            </div> 
          </div>
        </div>
      </div>

    </div>
  </div>
  
  
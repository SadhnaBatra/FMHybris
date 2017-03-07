  <%@page import="java.util.Map"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
   
 
   <div class="orderStatusBlock" id="orderStatusHeader">
              <form class="orderStatusForm">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="pruchaseOrder_no" class="form-control" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder_no" class="form-control" type="text" placeholder="Confirmation Order #">
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-1" type="text" onkeyup="return formateDate('date-picker-1');" class="date-picker form-control" placeholder="Start Date" value="${startDate}"/>
                        <label for="date-picker-1" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-2" type="text" onkeyup="return formateDate('date-picker-2');" class="date-picker form-control" placeholder="End Date" value="${endDate}" />
                        <label for="date-picker-2" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <select class="form-control" id="orderStatus">
                     <c:choose>
			<c:when test="${order_status eq  'IN_PROCESS' }">
				<option value="ALL">All</option>
				<option value="IN_PROCESS" selected="selected">In Progress</option>
				<option value="COMPLETE">Shipped</option>
			</c:when>
			<c:when test="${order_status eq  'COMPLETE' }">
				<option value="ALL">All</option>
				<option value="IN_PROCESS">In Progress</option>
				<option value="COMPLETE" selected="selected">Shipped</option>
			</c:when>
			<c:otherwise>
				<option value="ALL" selected="selected">All</option>
				<option value="IN_PROCESS">In Progress</option>
				<option value="COMPLETE">Shipped</option>
			</c:otherwise>
		     </c:choose>   
                 </select>
                  </div>
                </div>
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right">
                      <button type="button" class="btn btn-default btn-fmDefault" onClick="javascript:searchOrders();">Search</button>
                     <!-- <button type="button" class="btn btn-default btn-fmDefault" onClick="javascript:resetOrders();">Reset</button> -->
                    </div>
                  </div>
                </div>
              </form>
             <%-- <c:if test="${not empty OrderHeaderStatus  }"  > --%>  
             <c:if test="${fn:length(orderStatusResult) gt 0}">
		<div class="prodFilter  visible-lg visible-md visible-sm clearfix">
                <div class="col-lg-2 col-md-3 col-xs-2 col-lg-offset-7">
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block" for="display">View</label>
                      <div class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
                         <select class="form-control" id="orderStatusdisplayup" onchange="javascript:displayOrdersAs('up')">
                          <option value="20"  ${itemsCount eq '20' ? 'selected' : '' }>20</option>
                          <option value="40"  ${itemsCount eq '40' ? 'selected' : '' }>40</option>
                          <option value="80"  ${itemsCount eq '80' ? 'selected' : '' }>80</option>
                          <option value="120" ${itemsCount eq '120' ? 'selected' : '' }>120</option>
                        </select>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="col-lg-3 col-md-3 col-xs-4 text-right PaginationNav">
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline" for="page">Page</label>
                      <input type="text" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45" value="${page}"   />
                      <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline"> of ${noOfpages eq 0 ? 1 : noOfpages} </label>
                      <c:if test="${page gt 1}">
                      	<button type="button" class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline" 
                      	onclick="javascript:hasPrev(${page},${itemsCount},${begin},${end})"></button>
                      </c:if>	
                      <c:if test="${page lt noOfpages}">
                      	<button type="button" class="fa fa-angle-right pagination-next-page " onclick="javascript:hasNext(${page},${itemsCount},${begin},${end})"></button>
                      </c:if>	
                    </div>
                  </form>
                </div>
              </div> 
		<c:url value="/order-status/exportToExcel" var="exportExcelurl" />
 		<c:url value="/order-status/exportToPdf"   var="exportPDFurl" />	
		 <div class="row">
			<div class="form-group col-sm-12">
				<div class="pull-right exportButton orderHistoryExport">
					<label class="text-uppercase">Export</label> 
					<a  target="_blank" href="${exportExcelurl}" class="btn fa fa-file-excel-o orderExcel"></a> 
					<a  target="_blank" href="${exportPDFurl}" class="btn fa fa-file-pdf-o orderPdf"></a>
				</div>
			</div>
		</div>  


              <div class="orderStatusTable">
                <div class="table-responsive userTable">
                  <table class="table tablesorter" id="myTable">
                    <thead>
                      <tr>
                        <spring:url value="/my-fmaccount/sortOrderHistory" var="sortUrl"></spring:url>
                        <th><a href="${sortUrl}?sortBy=PO" > PO #<span class="fa fa-sort pull-right"></span></a></th>
                        <th>Confirmation #</th>
                        <th><a href="${sortUrl}?sortBy=OrderNumber" >Order #<span class="fa fa-sort pull-right"></span></a></th>
                        <th><a href="${sortUrl}?sortBy=PackingSlip" >Packing Slip #<span class="fa fa-sort pull-right"></span></a></th>
                        <th><a href="${sortUrl}?sortBy=ShippingDC" >Shipping DC<span class="fa fa-sort pull-right"></span></a></th>
                        <th><a href="${sortUrl}?sortBy=ShippingDate" >Ship Date<span class="fa fa-sort pull-right"></span></a></th>
                        <th>Tracking #</th>
                        <!-- <th>Source</th> -->
                      </tr>
                    </thead>
                    <tbody>
                   
                   <%--  <c:forEach items="${OrderHeaderStatus}" var="order" varStatus="status">
	                      <c:forEach items="${order.orderUnitList}" var="orderUnit" >
	                      	<c:forEach items="${orderUnit.shippingUnitList}" var="shippingUnit" >
	                      			<div id="ShippedBO_${status.index }" data-object="${order}"></div>
		                          <tr class="orderDateStatusAction">
			                        <td colspan="4"><span class="orderDate">Order Date:</span> <fmt:formatDate value="${orderUnit.originalOrderDate}" pattern="MM/dd/yyyy"/> <span class="divider">|</span> <span class="orderStatus">Status:</span>${shippingUnit.packingStatus}</td>
						<spring:url value="/my-fmaccount/exportToExcel/${order.orderNumber}"
									var="exportExcel" />
						<spring:url value="/my-fmaccount/exportToPdf/${order.orderNumber}"
									var="exportPdf" />
						<spring:url value="/my-fmaccount/sendOrderHistoryEmail/${order.orderNumber}"
									var="sendOrderHistoryEmail" />			                        
						<td colspan="4" class="text-right"><span class="orderActionLabel">Action</span> <a href="${exportExcel}" class="fa fa-file-excel-o orderExcel"></a> <a href="${sendOrderHistoryEmail}" class="fa fa-envelope-o orderMail"></a> <a href="${exportPdf}" class="fa fa-file-pdf-o orderPdf"></a> <!--<a href="createOrderReturn.html" class="fa fa-undo fm_fntBlack" title="Return Order"></a> --></td>
			                      </tr>
			                      <tr class="orderInfoBlock">
			                        <td>${order.customerPurchaseOrderNum}</td>
			                        <td>${order.masterOrderNumber}</td>
			                        <td><spring:url value="/my-fmaccount/order-details/${orderUnit.orderNumber}"
												var="encodedUrl" />
											<a href="${encodedUrl}"  class="orderNo">${orderUnit.orderNumber}</a></td>
			                        <td>
											<a href="${encodedUrl}"  class="orderNo">${shippingUnit.packingSlip}</a></td>
			                        <td>${shippingUnit.shipFrom.code }</td>
			                        <td><fmt:formatDate value="${shippingUnit.shipDate}" pattern="MM/dd/yyyy"/></td>
			                        <td><a href="#">${shippingUnit.trackingNumber}</a></td>
			                        <td>${orderUnit.orderSourceKey}</td>
			                      </tr>
		                      </c:forEach>
	                      </c:forEach>
                      </c:forEach> --%>
			
				<c:forEach items="${orderStatusResult}" var="orderStatus" varStatus="Status" begin="${begin}" end="${end-1}">
	                      
	                      			<div id="ShippedBO_${status.index }" data-object="${orderStatus}"></div>
		                          <tr class="orderDateStatusAction">
			                        <td colspan="4"><a href="${sortUrl}?sortBy=OrderDate"><span class="orderDate">Order Date:</span></a> <fmt:formatDate value="${orderStatus.orderDate}" pattern="MM/dd/yyyy"/> <span class="divider">|</span><a href="${sortUrl}?sortBy=OrderStatus"> <span class="orderStatus">Status: &nbsp; </span></a>
							<c:choose>
			                        		<c:when test="${orderStatus.status eq 'IN_PROCESS' }">
			                        			In Progress
				                        	</c:when>
				                        	<c:otherwise>
				                        		${orderStatus.status}
				                        	</c:otherwise>
			        	                </c:choose>	
					<span class="divider">|</span> <span class="orderActionLabel">Source:</span>  
						<c:if test="${orderStatus.orderShippingUnit.orderSourceKey eq 'WEB' }">
			                    <c:set var="orderSourceKey" value="ECON" />
			                    	 ${orderSourceKey}			                        		
			                  </c:if>
			                   <c:if test="${orderStatus.orderShippingUnit.orderSourceKey ne 'WEB' }">
			                   		${orderStatus.orderShippingUnit.orderSourceKey}
			                   </c:if>

						</td> 

								<c:choose>
									<c:when test="${not empty orderStatus.packingSlipNumber}">
										<spring:url
											value="/my-fmaccount/exportToExcel/${orderStatus.orderNumber}/${orderStatus.packingSlipNumber}"
											var="exportExcel" />
										<spring:url
											value="/my-fmaccount/exportToPdf/${orderStatus.orderNumber}/${orderStatus.packingSlipNumber}"
											var="exportPdf" />
									</c:when>
									<c:otherwise>
										<spring:url
											value="/my-fmaccount/exportToExcel/${orderStatus.orderNumber}/''"
											var="exportExcel" />
										<spring:url
											value="/my-fmaccount/exportToPdf/${orderStatus.orderNumber}/''"
											var="exportPdf" />

									</c:otherwise>
								</c:choose>
								<spring:url value="/my-fmaccount/sendOrderHistoryEmail/${orderStatus.orderNumber}"
									var="sendOrderHistoryEmail" />			                        
						<td colspan="4" class="text-right"><span class="orderActionLabel">Action</span> <a href="${exportExcel}" class="fa fa-file-excel-o orderExcel"></a> <a href="${sendOrderHistoryEmail}" class="fa fa-envelope-o orderMail"></a> <a href="${exportPdf}" target="_Blank" class="fa fa-file-pdf-o orderPdf"></a> <!--<a href="createOrderReturn.html" class="fa fa-undo fm_fntBlack" title="Return Order"></a> --></td>
			                      </tr>
			                      <tr class="orderInfoBlock">
			                        <td>${orderStatus.purchaseOrderNumber}</td>
			                       <td>${orderStatus.shippedOrder.shippedOrderBO.masterOrderNumber}</td> 
			                        <td><spring:url value="/my-fmaccount/order-details/${orderStatus.orderNumber}"
												var="encodedUrl" />
											<a href="${encodedUrl}"  class="orderNo">${orderStatus.orderNumber}</a></td>
			                        <td>
			                        		<spring:url value="/my-fmaccount/order-tracking/${orderStatus.orderNumber}/${orderStatus.packingSlipNumber }"
											var="encodedUrl" />
											<a href="${encodedUrl}"  class="orderNo">${orderStatus.packingSlipNumber}</a></td>
			                       <td>${orderStatus.orderShippingUnit.shippingUnitBO.shipFrom.name}</td>
			                        <td><fmt:formatDate value="${orderStatus.shipDate}" pattern="MM/dd/yyyy"/></td>
			                        <td>
			                        
			                        <c:if test="${not empty orderStatus.orderShippingUnit.trackingNumber }"  > 
			                            <c:if test="${not empty orderStatus.orderShippingUnit.trackingURL }"  > 
				                        	<spring:url value="${orderStatus.orderShippingUnit.trackingURL}" var="encodedUrl" />
				                       		 <a href="${encodedUrl}" class="orderNo" target="_blank">${orderStatus.orderShippingUnit.trackingNumber}</a>
										</c:if>
										<c:if test="${empty orderStatus.orderShippingUnit.trackingURL }"  > 
											${orderStatus.orderShippingUnit.trackingNumber}
										</c:if>
			                          </c:if>

			                        </td>
			                       <!-- <td>${orderStatus.orderShippingUnit.orderSourceKey}</td> -->
			                      </tr>
		                </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="visible-lg visible-md visible-sm clearfix topMargn_15">
                <div class="col-lg-2 col-md-3 col-xs-2 col-lg-offset-7">
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block" for="display">View</label>
                      <div class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
                         <select class="form-control" id="orderStatusdisplaydown" onchange="javascript:displayOrdersAs('down')">
                          <option value="20"  ${itemsCount eq '20' ? 'selected' : '' }>20</option>
                          <option value="40"  ${itemsCount eq '40' ? 'selected' : '' }>40</option>
                          <option value="80"  ${itemsCount eq '80' ? 'selected' : '' }>80</option>
                          <option value="120" ${itemsCount eq '120' ? 'selected' : '' }>120</option>
                        </select>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="col-lg-3 col-md-3 col-xs-4 text-right PaginationNav">
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline" for="page">Page</label>
                      <input type="text" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45" value="${page}"   />
                      <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline"> of ${noOfpages eq 0 ? 1 : noOfpages} </label>
                      <c:if test="${page gt 1}">
                      	<button type="button" class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline" 
                      	onclick="javascript:hasPrev(${page},${itemsCount},${begin},${end})"></button>
                      </c:if>	
                      <c:if test="${page lt noOfpages}">
                      	<button type="button" class="fa fa-angle-right pagination-next-page " onclick="javascript:hasNext(${page},${itemsCount},${begin},${end})"></button>
                      </c:if>	
                    </div>
                  </form>
                </div>
              </div> 
               </c:if>
              
             
            </div>
           
          
             
  <script>
/*Table sorter */

$(document).ready(function() 
    { 
       		//$(".orderStatusTable #myTable").tablesorter();
		$(".invoiceTabTable #myTable").tablesorter();
		$(".backOrderTabTable #myTable").tablesorter();
    } 
);
$('.date-picker').datepicker().on('changeDate', function(ev){                 
    $('.date-picker').datepicker('hide');
})
</script> 

  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
   
    <c:url value="/backOrder/backOrder-header-export-xls"
							var="excelsheeturl" />
 <c:url value="/backOrder/backOrder-header-export-pdf"
							var="pdfurl" />	
 <c:url value="/backOrder/backOrder-header-mail"
							var="mailurl" />
  <form class="orderStatusForm">
                <div class="row">
                  <div class="form-group col-sm-4">
                    <input id="backOrderpo_no" class="form-control" type="text" placeholder="Order #">
                  </div>
                  <div class="form-group col-sm-4">
                    <input id="backOrderpart_no" class="form-control" type="text" placeholder="Part #">
                  </div>
                  <div class="form-group col-sm-4">
                    <button type="button" class="btn btn-default btn-fmDefault" onclick="javascript:searchBackOrder();">Search</button>
                    <button type="button" class="btn btn-default btn-fmDefault reset" onclick="javascript:resetBackOrder();">Reset</button>
                  </div>
                </div>
		 <div class="row">
		<c:if test="${ not empty BackOrders or not empty backOrderSearchValues}" >
                	  <div class="form-group col-sm-12">
						<div class="pull-right exportButton">
                      <label class="text-uppercase">Export</label>				
						 <span data-original-title="Send me the information by email" data-toggle="tooltip" data-placement="bottom" title="Download Excel" class="tip"> 
 					<a class="fa fa-file-excel-o orderExcel" href="${excelsheeturl}"/> </span>					
					<span data-original-title="Send me the information by email" data-toggle="tooltip" data-placement="bottom" title="Send Mail" class="tip">
					<a href="#" data-toggle="modal" data-target="#sendBackOrderReportMail" class="fa fa-envelope-o orderMail"/> </span>
						<span data-original-title="Send me the information by email" data-toggle="tooltip" data-placement="bottom" title="Download PDF" class="tip">
						<a class="fa fa-file-pdf-o orderPdf" href="${pdfurl}" target="_blank"/> </span>
							</div>
					</div>
					</c:if>
					</div>
              </form>
 	<c:if test="${ not empty BackOrders or not empty backOrderSearchValues}" >
              <div class="backOrderTabTable">
                <div class="table-responsive userTable">
                  <table class="table tablesorter" id="myTable">
                    <thead>
                      <tr class="text-capitalize">
                        <th  class="header" onclick="javascript:backOrderSortBy('PartNumber')"><a href="#"  >part number</a></th>
                        <th>Deliver to</th>
                        <th  class="header" onclick="javascript:backOrderSortBy('OrderNumber')"><a href="#"  >order #</a></th>
                        <th  class="header"  onclick="javascript:backOrderSortBy('PO')"><a href="#" >PO#</a></th>
                        <th class="shippingDC">shipping DC</th>
                        <th  class="header" onclick="javascript:backOrderSortBy('OrderDate')"><a href="#"  >order date</a> </th>
                        <th>orignal qty </th>
                        <th>back order qty</th>
                        <!-- <th>action</th> -->
                      </tr>
                    </thead>
                    <tbody>
                    
	                   <%--  <c:forEach items="${backOrderSearchValues}" var="order" varStatus="status">
	                    	<c:forEach items="${order.backOrderUnits}" var="backOrderUnits" varStatus="status">
	                    		<c:forEach items="${backOrderUnits.backOrderedItems}" var="entry" varStatus="status">
				                      <tr>
				                        <td>${entry.partNumber}</td>
				                        <td>${order.shipToAccount.accountCode }</td>
				                        <td>
							     <spring:url value="/my-fmaccount/order-details/${backOrderUnits.orderNumber}"
												var="encodedUrl" />

								<a href="${encodedUrl}" class="orderNo">${backOrderUnits.orderNumber}</a></td>
				                        <td>${order.customerPurchaseOrderNumber}</td>
				                        <td>${backOrderUnits.distributionCenter.code}</td>
				                        <td><fmt:formatDate value="${backOrderUnits.orderDate }" pattern="MM/dd/yyyy"/></td>
				                        <td>${entry.originalOrderQty}</td>
				                        <td>${entry.backOrderQty}</td>
				                      <!--  <td><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td> -->
				                      </tr>
	                      		</c:forEach>
	                       </c:forEach>
	                  	</c:forEach> --%>
						<c:forEach items="${backOrderSearchValues}" var="backOrder"
							varStatus="status">
	
							<tr>
								<td>${backOrder.partNumber}</td>
								<td>${backOrder.shipToAccount.accountCode }</td>
								<td>
								     <spring:url value="/backOrder/backorder-details?backOrderNumber=${backOrder.orderNumber}"
												var="encodedUrl" />

								     <a href="${encodedUrl}" class="orderNo">${backOrder.orderNumber}</a></td>
								<td>${backOrder.customerPONumber}</td>
								<td>${backOrder.distCntr.name}</td>
								<td><fmt:formatDate value="${backOrder.orderDate }"
										pattern="MM/dd/yyyy" /></td>
								<td>${backOrder.originalOrderQty}</td>
								<td>${backOrder.backOrderQty}</td>
								<!--  <td><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td> -->
							</tr>
						</c:forEach>
                     
                    </tbody>
                  </table>
                </div>
              </div>
	</c:if>
				<c:url
					value="/backOrder/backOrder-header-mail" var="encodedUrl" />
              <div class="modal fade" id="sendBackOrderReportMail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog backOrderWrapper">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close pull-right" data-dismiss="modal"><span aria-hidden="true" class="fa fa-close "></span><span class="sr-only">Close</span></button>
          <h3 class=""> <span class="fa fa fa-envelope fm_fntRed"></span> <span class="text-uppercase DINWebBold ">SEND REPORT VIA EMAIL</span></h3>
        </div>
        <div class="modal-body">
          <form:form class="" method="POST" commandName="emailForm" modelAttribute="emailForm" id="email" action="${encodedUrl} ">
            <div class="form-group regFormFieldGroup"> 
              <!--<label for="year" >Year</label> -->
              <form:input type="email" id="primaryEmail" required="required" name="primaryEmail" path="primaryEmail" placeholder="Email Address" class="form-control backOrderInput"/>
              <div class="errorMsg fm_fntRed" style="display: none;">Please enter your email address.</div>
            </div>
            <div class="form-group"> 
              <!--<label for="year" >Year</label> -->
              <form:input type="email" id="secondaryEmail"  name="secondaryEmail" path="secondaryEmail" placeholder="Second Email Address" class="form-control backOrderInput"/>
            </div>
            <div class="form-group">  <button class="btn btn-sm btn-fmDefault text-uppercase sendReportButton" type="submit">Send</button> </div>
          </form:form>
        </div>
      </div>
    </div>
  </div>

 
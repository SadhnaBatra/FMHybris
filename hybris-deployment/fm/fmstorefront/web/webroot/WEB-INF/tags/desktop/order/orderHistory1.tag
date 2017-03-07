  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
  
  <div class="container manageAccount">
    <div class="row"> 
      <!-- Starts: Manage Account Right hand side panel -->
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 "> 
        <!--<div class=""><h2 class="text-uppercase">Address Details</h2></div> -->
        <div id="manageAccountTab" class="productDetailPageTab manageAccountTab horizontalTab">
          <ul class="resp-tabs-list pull-left">
            <li>Order Status</li>
            <li id="invoices">Invoices</li>
            <li id="backOrders">Back Orders</li>
            <li id="uploadorder">Uploaded Order Status</li>
          </ul>
          <div class="resp-tabs-container">
            <div class="orderStatusBlock">
              <form class="orderStatusForm">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="pruchaseOrder_os" class="form-control" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder_os" class="form-control" type="text" placeholder="Confirmation Order #">
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-2" type="text" class="date-picker form-control" placeholder="Start Date" />
                        <label for="date-picker-2" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-3" type="text" class="date-picker form-control" placeholder="End Date" />
                        <label for="date-picker-3" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <select class="form-control">
                      <option>Status</option>
                    </select>
                  </div>
                </div>
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right">
                      <button type="button" class="btn btn-default btn-fmDefault">Search</button>
                      <button type="button" class="btn btn-default btn-fmDefault">Reset</button>
                    </div>
                  </div>
                </div>
              </form>
              <div class="orderStatusTable">
                <div class="table-responsive userTable">
                  <table class="table tablesorter" id="myTable">
                    <thead>
                      <tr class="text-capitalize">
                        <th>PO #</th>
                        <th>Confirmation #</th>
                        <th>Order #</th>
                        <th>Order Date #</th>
                        <th>Status </th>
                        <th>Source</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${orderDataList}" var="orderData">                   
    					<tr>
        				<td> ${orderData.purchaseOrderNumber}</td>
                        <td>${orderData.code}</td>
                        <td>
                        <spring:url value="/my-fmaccount/return-order-details"
									var="encodedUrl" />
								<a href="${encodedUrl}"  class="orderNo">${orderData.code}</a></td>
                       <!--  <a href="/my-fmaccount/return-order-details" class="orderNo">60WAJ0347</a> -->
                       <fmt:parseDate value="${task.startDate}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/>
						<fmt:formatDate value="${myDate}" var="startFormat" pattern="yyyy-MM-dd"/>
                        <td>${orderData.created}</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
  					  </tr>
					</c:forEach>
                    </tbody> 
                    <!-- Edited by sree - for CSR static HTML - start -->
                    <tbody>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Pending</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Completed</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Completed</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Pending</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Completed</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Completed</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Completed</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>Pending</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>01/27/2015</td>
                        <td>In Progress</td>
                        <td>Web</td>
                        <td colspan="4" class="text-right"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                    </tbody>
                    <!-- Edited by sree - for CSR static HTML - end -->
                    
                    
                  </table>
                </div>
              </div>
              <div class="visible-lg visible-md visible-sm clearfix topMargn_20">
                <div class="col-lg-2 col-md-3 col-xs-2 col-lg-offset-7">
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label class="control-label visible-lg-inline-block visible-md-inline-block visible-sm-inline-block" for="display">View</label>
                      <div class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
                        <select class="form-control" id="display">
                          <option>20</option>
                          <option>40</option>
                          <option>80</option>
                          <option>120</option>
                        </select>
                      </div>
                    </div>
                  </form>
                </div>
                <div class="col-lg-3 col-md-3 col-xs-4 text-right PaginationNav">
                  <form class="form-horizontal">
                    <div class="form-group">
                      <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline" for="page">Page</label>
                      <input type="text" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45" value="1"   />
                      <label class="control-label visible-lg-inline visible-md-inline visible-sm-inline"> of 32</label>
                      <button type="button" class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"></button>
                      <button type="button" class="fa fa-angle-right pagination-next-page "></button>
                    </div>
                  </form>
                </div>
              </div>
              <div class="visible-xs ">
                <div class="col-lg-4 col-md-4 col-xs-2">
                  <button value="Fiter" class="fa fa-gear"></button>
                </div>
                <div class="col-lg-4 col-md-4 col-xs-3">
                  <select class="visible-lg-inline visible-md-inline visible-sm-inline visible-xs-inline" >
                    <option>Popular</option>
                    <option>High Price</option>
                    <option>Med Price</option>
                    <option>Low Price</option>
                  </select>
                </div>
                <div class="col-lg-4 col-md-4 col-xs-7 text-right">
                  <button type="button" class="fa fa-chevron-left"></button>
                  Page 2 of 5
                  <button type="button" class="fa fa-chevron-right"></button>
                </div>
              </div>
            </div>
            <div class="invoiceBlock" id="invoiceBlock">
              
            </div>
            <div class="backOrdersBlock" id="backOrdersBlock">
              
            </div>
             <div class="uploadOrderStatus" id="uploadOrderStatus">
              
            </div> 
          </div>
        </div>
      </div>
      <!-- Ends: Manage Account Right hand side panel --> 
    </div>
  </div>
  <!-- Starts: Send Back Order Report Mail Popup -->
  <div class="modal fade" id="sendBackOrderReportMail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog backOrderWrapper">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close pull-right" data-dismiss="modal"><span aria-hidden="true" class="fa fa-close "></span><span class="sr-only">Close</span></button>
          <h3 class=""> <span class="fa fa fa-envelope fm_fntRed"></span> <span class="text-uppercase DINWebBold ">SEND REPORT VIA EMAIL</span></h3>
        </div>
        <div class="modal-body">
          <form class="">
            <div class="form-group"> 
              <!--<label for="year" >Year</label> -->
              <input type="email" name="emailid" placeholder="Email Address" class="form-control backOrderInput">
            </div>
            <div class="form-group"> 
              <!--<label for="year" >Year</label> -->
              <input type="email" name="secEmailid" placeholder="Second Email Address" class="form-control backOrderInput">
            </div>
            <div class="form-group"> <a class="btn btn-sm btn-fmDefault text-uppercase sendReportButton" type="submit">Send</a> </div>
          </form>
        </div>
      </div>
    </div>
  </div>
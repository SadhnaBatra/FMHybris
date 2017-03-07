  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
  
  <div class="container manageAccount">
    <div class="row"> 
      <!-- Starts: Manage Account Left hand side panel -->
      <!-- <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"> 
        - Order in Progress PANEL
        <div class="panel panel-default manageAccountLinks clearfix" >
          <div class="panel-body">
            <h4 class="text-uppercase">My account</h4>
            <ul class="">
              <li><a href="profilePage.html">Profile <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="addressBook_veryNewPage_view4All.html">Address book <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="payment_details.html">Payment details <span class="linkarow fa fa-angle-right "></span></a></li>
                           <li><a href="#">My network <span class="linkarow fa fa-angle-right "></span></a></li> 
                  <li><a href="#">Pending orders<span class="pendingOrderNo">[10]</span> <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a class="selected" href="orderHistory.html">Order History</a></li>
              <li><a href="returnHistory.html">Return History <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="manageUser.html">Users <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="taxExemption.html">Tax Document <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="loyaltyHistory.html">Loyalty History <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="#">My quotes <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="#">My replenishment orders <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="#">Order approval dashboard<span class="orderApprovalDashboardNo">[3]</span> <span class="linkarow fa fa-angle-right "></span></a></li>
              <li><a href="#">User approval dashboard<span class="orderApprovalDashboardNo">[3]</span> <span class="linkarow fa fa-angle-right "></span></a></li>
            </ul>
          </div>
        </div>
        <div class="panel panel-default messagingPanel clearfix" >
          <div class="lms_intro_hover " >
            <p ><img src="images/insight-img4.png" alt="Insights" class="img-responsive"/> </p>
            <div class="caption">
              <div class="blur"></div>
              <div class="caption-text">
                <h5 >Year Over Year Wiper Blade Sales Shows Increase</h5>
                <div class="downloadLink"><a href="#"> Download [232 Kb] <span class="fa fa-angle-right "></span></a></div>
                <p class="lms_desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
                <div class="lms_btm_link"> <span class="pull-left  text-capitalize"><a href="#"> Read More <span class="fa fa-angle-right "></span></a></span> <span class="pull-right  text-capitalize"><a href="#"><span class="fa fa-share-alt"> </span></a></span></div>
              </div>
            </div>
          </div>
        </div>
        <div class="panel panel-default messagingPanel clearfix" >
          <div class="lms_intro_hover " >
            <p > <img src="images/insight-img3.png" alt="Insights" class="img-responsive"/> </p>
            <div class="caption">
              <div class="blur"></div>
              <div class="caption-text">
                <h5 ><span class="fa fa-youtube-play"></span> VIDEO: Vehicles - Change in Miles Driven</h5>
                <div class="downloadLink"><a href="#"> Download [358 Kb] <span class="fa fa-angle-right "></span></a></div>
                <p class="lms_desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. </p>
                <div class="lms_btm_link"> <span class="pull-left  text-capitalize"><a href="#"> Play <span class="fa fa-angle-right "></span></a></span> <span class="pull-right  text-capitalize"><a href="#"><span class="fa fa-share-alt"> </span></a></span></div>
              </div>
            </div>
          </div>
        </div>
      </div> -->
      <!-- Ends: Manage Account Left hand side panel --> 
      
      <!-- Starts: Manage Account Right hand side panel -->
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 "> 
        <!--<div class=""><h2 class="text-uppercase">Address Details</h2></div> -->
        <div id="manageAccountTab" class="productDetailPageTab manageAccountTab horizontalTab">
          <ul class="resp-tabs-list pull-left">
            <li>Order Status</li>
            <li>Invoices</li>
            <li>Back Orders</li>
            <li>Uploaded Order Status</li>
          </ul>
          <div class="resp-tabs-container">
            <div class="orderStatusBlock">
              <form class="orderStatusForm">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="pruchaseOrder" class="form-control" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder" class="form-control" type="text" placeholder="Confirmation Order #">
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
                      <tr>
                        <td>6526526521</td>
                        <td>H14102914222070</td>
                        <td><spring:url value="/my-fmaccount/order-details"
									var="encodedUrl" />
								<a href="${encodedUrl}"  class="orderNo">60WAJ0347</a></td>
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
            <div class="invoiceBlock">
              <form class="orderStatusForm">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="pruchaseOrder" class="form-control" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder" class="form-control" type="text" placeholder="Confirmation Order #">
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-4" type="text" class="date-picker form-control" placeholder="Start Date" />
                        <label for="date-picker-4" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-5" type="text" class="date-picker form-control" placeholder="End Date" />
                        <label for="date-picker-5" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <select class="form-control">
                      <option>STATUS...</option>
                    </select>
                  </div>
                </div>
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right">
                      <button type="button" class="btn btn-default btn-fmDefault search">Search</button>
                      <button type="button" class="btn btn-default btn-fmDefault reset">Reset</button>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right exportButton">
                      <label class="text-uppercase">Export</label>
                      <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel">
                      <button type="button" class="btn fa fa-file-excel-o orderExcel"></button>
                      </span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf">
                      <button type="button" class="btn fa fa-file-pdf-o orderPdf"></button>
                      </span> </div>
                  </div>
                </div>
              </form>
              <div class="invoiceTabTable">
                <div class="table-responsive userTable">
                  <table class="table tablesorter" id="myTable">
                    <thead>
                      <tr class="text-capitalize">
                        <th>Invoice/credit memo</th>
                        <th>Invoice#</th>
                        <th>Invoice to </th>
                        <th>Deliver to</th>
                        <th>Invoice Date </th>
                        <th>Invoice Amount </th>
                        <th>PO#</th>
                        <th>Status</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>Credit Memo</td>
                        <td><a href="#">2967830</a></td>
                        <td>30683</td>
                        <td>30683</td>
                        <td>10/10/2014</td>
                        <td>$ (262.79)</td>
                        <td>2056207A07MZ</td>
                        <td>Open</td>
                      </tr>
                      <tr>
                        <td>Invoice</td>
                        <td><a href="#">2967830</a></td>
                        <td>11423</td>
                        <td>26780</td>
                        <td>12/10/2013</td>
                        <td>$ (3,337.90)</td>
                        <td>2056210A07MZ</td>
                        <td>Open</td>
                      </tr>
                      <tr>
                        <td>Credit Memo</td>
                        <td><a href="#">2967830</a></td>
                        <td>30683</td>
                        <td>30683</td>
                        <td>10/10/2014</td>
                        <td>$ (262.79)</td>
                        <td>2056207A07MZ</td>
                        <td>Open</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <div class="visible-lg visible-md visible-sm invoiceBlockPaging clearfix">
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
            <div class="backOrdersBlock">
              <form class="orderStatusForm">
                <div class="row">
                  <div class="form-group col-sm-4">
                    <input id="pruchaseOrder" class="form-control" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-4">
                    <input id="confirmationOrder" class="form-control" type="text" placeholder="Part #">
                  </div>
                  <div class="form-group col-sm-4">
                    <button type="button" class="btn btn-default btn-fmDefault">Search</button>
                    <button type="button" class="btn btn-default btn-fmDefault reset">Reset</button>
                  </div>
                </div>
              </form>
              <div class="backOrderTabTable">
                <div class="table-responsive userTable">
                  <table class="table tablesorter" id="myTable">
                    <thead>
                      <tr class="text-capitalize">
                        <th>part number</th>
                        <th>Deliver to</th>
                        <th>order #</th>
                        <th>PO#</th>
                        <th class="shippingDC">shipping DC</th>
                        <th>order date </th>
                        <th>orignal qty </th>
                        <th>back order qty</th>
                        <th>action</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>27101562-7</td>
                        <td>11432</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>652</td>
                        <td>Indianapolis Consumer</td>
                        <td>12/28/2014</td>
                        <td>23</td>
                        <td>4</td>
                        <td><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>27101562-7</td>
                        <td>11432</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>652</td>
                        <td>Indianapolis Consumer</td>
                        <td>12/28/2014</td>
                        <td>23</td>
                        <td>4</td>
                        <td><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                      <tr>
                        <td>27101562-7</td>
                        <td>11432</td>
                        <td><a href="orderDetails.html" class="orderNo">60WAJ0347</a></td>
                        <td>652</td>
                        <td>Indianapolis Consumer</td>
                        <td>12/28/2014</td>
                        <td>23</td>
                        <td>4</td>
                        <td><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="uploadOrderStatus">
              <form class="uploadOrderStatus">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="pruchaseOrder" class="form-control" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="confirmationOrder" class="form-control" type="text" placeholder="Confirmation Order #">
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-6" type="text" class="date-picker form-control" placeholder="Start Date" />
                        <label for="date-picker-6" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-7" type="text" class="date-picker form-control" placeholder="End Date" />
                        <label for="date-picker-7" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <select class="form-control">
                      <option>STATUS...</option>
                    </select>
                  </div>
                </div>
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right">
                      <button type="button" class="btn btn-default btn-fmDefault">Search</button>
                    </div>
                  </div>
                </div>
              </form>
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
                      <tr class="">
                        <td>27101562-7</td>
                        <td>10245</td>
                        <td>HEIKEG15</td>
                        <td>Requiring Attention</td>
                        <td>11/27/2013 9:09:32 AM</td>
                        <td class="uploadOrderAction"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Edit"><a onClick="$('.EditOrderTableRow').toggle();$('.upOrderPencil-1').toggleClass('selected'); $('.EditOrderHistory').hide();$('.upOrderClock-1').removeClass('selected');" class="fa fa-pencil upOrderPencil-1"></a></span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Download"><a href="#" class="fa fa-download upOrderDownload-1"></a></span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="History"><a onClick="$('.EditOrderHistory').toggle();$('.upOrderClock-1').toggleClass('selected');$('.EditOrderTableRow').hide();$('.upOrderPencil-1').removeClass('selected'); " class="fa fa-clock-o upOrderClock-1"></a></span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Search"><a class="fa fa-search upOrderSearch-1"></a></span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Delete"><a href="#" class="fa fa-trash delete upOrderDelete-1"></a></span></td>
                      </tr>
                      <tr class="EditOrderTableRow" style="display:none">
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
                              <tr  class="noBorder">
                                <td colspan="3" class=""><span class="fm_fntRed">Status:</span> Cannot be Ordered - Contact and F-M Representative</td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr class="">
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="12998881"></td>
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="1"></td>
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="AMG"></td>
                                <td></td>
                                <td><a href="#" class="fa fa-close uploadOrderClose uploadOrderAction"></a></td>
                              </tr>
                              <tr class="noBorder">
                                <td colspan="3" class="border"><span class="fm_fntRed">Status:</span>Part Not Found</td>
                                <td></td>
                                <td></td>
                              </tr>
                              <tr class="">
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="12998881"></td>
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="1"></td>
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="AMG"></td>
                                <td>Universal Joint</td>
                                <td><a href="#" class="fa fa-close uploadOrderClose uploadOrderAction"></a></td>
                              </tr>
                              <tr class="noBorder">
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="12998881"></td>
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="2"></td>
                                <td><input class="form-control visible-lg-inline visible-md-inline visible-sm-inline width115" type="text" value="" placeholder="N"></td>
                                <td></td>
                                <td><a href="#" class="fa fa-close uploadOrderClose uploadOrderAction"></a></td>
                              </tr>
                            </tbody>
                          </table>
                          <div class="">
                            <button class="btn btn-default btn-fmDefault uploadOrderBtn" type="button">Save</button>
                            <button class="btn btn-default btn-fmDefault uploadOrderBtn" type="button">Cancel</button>
                          </div></td>
                      </tr>
                      <tr class="EditOrderHistory" style="display:none">
                        <td colspan="6"><table class="table tablesorter" >
                            <thead>
                              <tr class="text-capitalize">
                                <th class="width175">Update User ID # </th>
                                <th class="width175">Action Timestamp </th>
                                <th class="width175">Status </th>
                                <th class="width175">Order ID </th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr class="">
                                <td>27101562-7</td>
                                <td>Sept 10, 2013 9:09:32 AM</td>
                                <td>Part Resolution Issue</td>
                                <td>454</td>
                              </tr>
                              <tr class="">
                                <td>27101562-7</td>
                                <td>Sept 10, 2013 8:32:06 AM</td>
                                <td>Part Resolution Issue</td>
                                <td>454</td>
                              </tr>
                              <tr class="">
                                <td>27101562-7</td>
                                <td>Sept 10, 2013 8:27:03 AM</td>
                                <td>Part Resolution Issue</td>
                                <td>454</td>
                              </tr>
                              <tr class="">
                                <td>27101562-7</td>
                                <td>Sept 10, 2013 8:27:03 AM</td>
                                <td>Part Resolution Issue</td>
                                <td>454</td>
                              </tr>
                            </tbody>
                          </table>
                      </tr>
                      <tr class="EditOrderDetails" style="display:none">
                        <td colspan="6"><table class="table tablesorter" >
                            <thead>
                              <tr class="text-capitalize">
                                <th>Update User ID # </th>
                                <th>Update Timestamp </th>
                                <th>Part # </th>
                                <th>Part Quantity</th>
                                <th>Product Flag </th>
                                <th class="width115">Status </th>
                                <th>Description </th>
                              </tr>
                            </thead>
                            <tbody>
                              <tr class="">
                                <td>27101562-7</td>
                                <td>Sept 10, 2013 9:09:32 AM</td>
                                <td>AMGK5142</td>
                                <td>100000</td>
                                <td>AMG</td>
                                <td>Resolved User Action (Modifiable)</td>
                                <td class="text-center">454</td>
                              </tr>
                              <tr class="">
                                <td colspan="3" class=""><span class="fm_fntRed">Status:</span>Part Not Found</td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                              </tr>
                            </tbody>
                          </table>
                      </tr>
                      <tr>
                        <td>27101562-7</td>
                        <td>11432</td>
                        <td>8668676</td>
                        <td>Cancelled</td>
                        <td>10/27/2013 9:09:32 AM</td>
                        <td class="uploadOrderAction"><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Download"><a href="#" class="fa fa-download upOrderDownload-2 uploadAction"></a></span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="History"><a  class="fa fa-clock-o upOrderClock02"></a></span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Search"><a class="fa fa-search upOrderSearch-2"></a></span></td>
                      </tr>
                    </tbody>
                  </table>
                  <!-- table two --> 
                </div>
              </div>
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
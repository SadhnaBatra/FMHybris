  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
  
	<!-- <div class="uploadOrderStatus"> -->
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
          <!--   </div> -->
 
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<template:page pageTitle="${pageTitle}">
	<%-- <c:out value="<%=request.getContextPath()%>"/> --%>
	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
				</ul>
			</div>
		</div>
	</section>
	
<!-- MAIN CONTAINER--> 
<!-- InstanceBeginEditable name="Page Content Section" -->
<section class="accountDetailPage pageContet" >
  <div class="container manageAccount">
    <div class="row"> 
      <!-- Starts: Manage Account Left hand side panel -->
      <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12"> 
        <!--- Order in Progress PANEL -->
					<federalmogul:myAccount />
					<!--  <div class="panel panel-default messagingPanel clearfix" >
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
        </div>-->
      </div> 
      <!-- Ends: Manage Account Left hand side panel --> 
      
      <!-- Starts: Manage Account Right hand side panel -->
      <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 "> 
        <!--<div class=""><h2 class="text-uppercase">Address Details</h2></div> -->
        <div id="manageAccountTab" class="productDetailPageTab manageAccountTab horizontalTab">
          <ul class="resp-tabs-list pull-left">
            <li>Activity History</li>
            <li>Redemption History</li>
          
          </ul>
          <div class="resp-tabs-container">
            <div class="orderStatusBlock">
             <!-- <form class="orderStatusForm">
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
              </form> -->
								<div class="orderStatusTable">
									<div class=" clearfix">
										<div
											class="visible-lg visible-md visible-sm clearfix topMargn_20">
											<div class="col-lg-2 col-md-3 col-xs-2 col-lg-offset-6">
												<form class="form-horizontal loyaltyPagination">
													
												</form>
											</div>
										
										</div>
										<div class="visible-xs ">
											<div class="col-lg-4 col-md-4 col-xs-2">
												<button value="Fiter" class="fa fa-gear"></button>
											</div>
											<div class="col-lg-4 col-md-4 col-xs-3">
												<select
													class="visible-lg-inline visible-md-inline visible-sm-inline visible-xs-inline">
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
									<div class="table-responsive userTable">
										<table class="table tablesorter" id="myTable">
											<thead>
												<tr class="text-capitalize">
													<!-- <th class="col-md-1 text-center">Sr. #</th>
													<th class="col-md-1 text-center">Activity ID</th>
													<th class="col-md-2 text-center">Order ID</th> -->
													<th class="col-md-2 text-center">Date</th>
													<th class="col-md-4 text-center">Activity</th>
													<th class="col-md-2 text-center">Points Earned</th>

												</tr>
											</thead>
									<c:set var="counti" value="0"/>
											<tbody> 
												<c:forEach items="${ActivityResult}" var="list" varStatus="i">
												
													<c:forEach items="${list.itemsDetail}" var="pointsList">
														<c:set var="counti" value="${counti+1}"/>	
													<tr>
													<%-- 	<td class="text-right">${counti}</td>
														<td class="text-center">${list.activityId}</td>
														<td>${pointsList.orderId}</td> --%>
														<td class="text-center">${list.postingDate}</td>
														<td class="text-center">${pointsList.productActivity}</td>
														<td class="text-center"> <fmt:formatNumber type="number" value="${pointsList.points}" /></td>
														
													</tr>
														</c:forEach>

												</c:forEach>
												
											</tbody>
										</table>
									</div>
								</div>
								<div class="table-responsive orderDetailSubTotal userTable">
									<table class="table tablesorter" id="myTable">
                <tbody>
                  <tr class="reviewPlaceOrderTableTwo">
                    <td class="col-lg-6 col-md-6 col-sm-6"></td>
                    <td class="col-lg-5 col-md-5 col-sm-5"><table class="orderSummaryTable">
                        <tbody>
                         
                          <tr class="estTotal">
                            <td class="text-left text-capitalize">Total Points Earned</td>
                            <td class="text-right shoppingCartTotal"><fmt:formatNumber type="number" value="${totalActivityPoints}" /></td>
                          </tr>
                        </tbody>
                      </table></td>
                  </tr>
                </tbody>
              </table>
								</div>
								<div class=" clearfix">
									<div
										class="visible-lg visible-md visible-sm clearfix topMargn_10">
									
									</div>
									<div class="visible-xs ">
										<div class="col-lg-4 col-md-4 col-xs-2">
											<button value="Fiter" class="fa fa-gear"></button>
										</div>
										<div class="col-lg-4 col-md-4 col-xs-3">
											<select
												class="visible-lg-inline visible-md-inline visible-sm-inline visible-xs-inline">
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
								<%--<div class="topMargn_10 ">

									<button value="redeem" class="btn btn-fmDefault">Redeem</button>
									<button value="cancel" class="btn btn-fmDefault">Cancel</button>
								</div>--%>
							</div>
							<div class="invoiceBlock">
							
								<div class="orderStatusTable">
									<div class=" clearfix">
										
										<div class="visible-xs ">
											<div class="col-lg-4 col-md-4 col-xs-2">
												<button value="Fiter" class="fa fa-gear"></button>
											</div>
											<div class="col-lg-4 col-md-4 col-xs-3">
												<select
													class="visible-lg-inline visible-md-inline visible-sm-inline visible-xs-inline">
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
									<div class="table-responsive userTable">
										<table class="table tablesorter" id="myTable">
											<thead>
												<tr class="text-capitalize">
													<!-- <th class="col-md-1 text-center">Sr. #</th>
													<th class="col-md-1 text-center">Activity ID</th>
													<th class="col-md-1 text-center">Order ID</th> -->
													<th class="col-md-1 text-center">Date</th>
													<th class="col-md-1 text-center">Product</th>
													<th class="col-md-3 text-center">Product Description</th>
													<th class="col-md-1 text-center">Status</th>
													<th class="col-md-2 text-center">Tracking Number</th>
													<th class="col-md-3 text-center">Points Redeemed</th>
												</tr>
											</thead>
												<c:set var="count" value="0"/>
											<tbody>
												<c:forEach items="${RedemptionResult}" var="list" varStatus="j">
											
													<c:forEach items="${list.itemsDetail}" var="listDetails" >
													<c:set var="count" value="${count+1}"/>
														<tr>
														<%-- <td class="text-right">${count}</td>
														<td class="text-center">${list.activityId}</td>
														<td>${listDetails.orderId}</td> --%>
														<td class="text-center">${list.postingDate}</td>								
														<td>${listDetails.productId}</td>
														<td >${listDetails.productActivity}</td>
														<td>${listDetails.status}</td>
														<td><c:set var="trackURL"
																value="${fn:toLowerCase(list.trackingURL)}" />
															<c:set var="trackNumberSubStr"
																value="${fn:substringAfter(trackURL, 'tracknumbers=')}" />
															<c:set var="trackingNumber"
																value="${fn:substringBefore(trackNumberSubStr, '&')}" />
															<a href="${trackURL}" class="orderNo" target="_blank"> ${trackingNumber}</a>
														</td>
														<td class="text-right"> 
														<c:set var="redeemedPoints" value="${fn:replace(listDetails.points,'-', '')}" />
														<fmt:formatNumber type="number" value="${redeemedPoints}" /></td>

													</tr>
												</c:forEach>
												</c:forEach>
											
											</tbody>
										</table>
									</div>
								</div>
								<div class="table-responsive orderDetailSubTotal userTable">
									<table class="table tablesorter" id="myTable">
										<tbody>
											<tr class="reviewPlaceOrderTableTwo">
												<td class="col-lg-6 col-md-6 col-sm-6"></td>
												<td class="col-lg-5 col-md-5 col-sm-5"><table
														class="orderSummaryTable">
														<tbody>

															<tr class="estTotal">
																<td class="text-left text-capitalize">Total points
																	redeemed</td>
																<td class="text-right shoppingCartTotal">
																	<c:set var="redeemedTotalPoints" value="${fn:replace(totalredemptionPoints,'-', '')}" />
																	<fmt:formatNumber type="number" value="${redeemedTotalPoints}" /></td>
															</tr>
														</tbody>
													</table></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class=" clearfix">
									
								
									</div>
									<div class="visible-xs ">
										<div class="col-lg-4 col-md-4 col-xs-2">
											<button value="Fiter" class="fa fa-gear"></button>

										</div>
										<div class="col-lg-4 col-md-4 col-xs-3">
											<select
												class="visible-lg-inline visible-md-inline visible-sm-inline visible-xs-inline">
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

							</div>


						</div>
					</div>
				</div>
				<!-- Ends: Manage Account Right hand side panel -->
			</div>
		</div>
		<!-- Starts: Send Back Order Report Mail Popup -->
		<div class="modal fade" id="sendBackOrderReportMail" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog backOrderWrapper">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close pull-right"
							data-dismiss="modal">
							<span aria-hidden="true" class="fa fa-close "></span><span
								class="sr-only">Close</span>
						</button>
						<h3 class="">
							<span class="fa fa fa-envelope fm_fntRed"></span> <span
								class="text-uppercase DINWebBold ">SEND REPORT VIA EMAIL</span>
						</h3>
					</div>
					<div class="modal-body">
						<form class="">
							<div class="form-group">
								<!--<label for="year" >Year</label> -->
								<input type="email" name="emailid" placeholder="Email Address"
									class="form-control backOrderInput">
							</div>
							<div class="form-group">
								<!--<label for="year" >Year</label> -->
								<input type="email" name="secEmailid"
									placeholder="Second Email Address"
									class="form-control backOrderInput">
							</div>
							<div class="form-group">
								<a
									class="btn btn-sm btn-fmDefault text-uppercase sendReportButton"
									type="submit">Send</a>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<!-- Ends: Send Back Order Report Mail Popup -->
	</section>
	<!-- InstanceEndEditable -->



		<!-- Brandstrip -->

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<!-- <c:set var="fmComponentName" value="brandStripB2B" scope="session" />-->
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
        
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
	</template:page>
	

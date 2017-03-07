<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

     <c:forEach items="${OrderStatusDetail.orderUnitList}" var="orderUnit" >
     	<c:if test="${orderUnit.orderNumber eq orderNumber}">
	     	<c:set var="orderNumber" value="${orderUnit.orderNumber }" />
	     	<c:set var="orderComments" value="${orderUnit.commentsList }" />
	     	<fmt:formatDate value="${orderUnit.originalOrderDate}" pattern="MM/dd/yyyy" var="orderDate" /> 
	     	<c:forEach items="${orderUnit.shippingUnitList}" var="shippingUnit" >
	     		<c:set var="orderStatus" value="${shippingUnit.packingStatus}" />
	     	</c:forEach>
     	</c:if>
     </c:forEach>
    
      <!-- Starts: Manage Account Right hand side panel -->

     <c:if test="${not empty OrderStatusDetail}"  > 
     
      <!-- Starts: Manage Account Right hand side panel -->

		

          <h2 class="text-uppercase">Order details</h2>
          <div class="orderDetailSubTitle text-capitalize fm_bgRed clearfix">
            <div class="col-md-4 lftPad_10"><span class="ODlabel">Order #:</span> ${orderNumber }</div>
            <div class="col-md-4 lftPad_10"><span class="ODlabel">Status:</span>
             <c:choose>
			      <c:when test="${orderStatus eq 'IN_PROCESS' }">
			                     In Progress
			                 </c:when>
			          <c:when test="${orderStatus eq 'Partial_shipment' || orderStatus eq 'PARTIAL_SHIPMENT'}">
			                     Partial Shipment
			                 </c:when>
			                <c:otherwise>
			                    ${orderStatus}
			                 </c:otherwise>
			 </c:choose>
            </div>            <div class="col-md-4"><span class="ODlabel">Created on:</span> ${orderDate } </div>
          </div>
          
          <div class="row topMargn_25">
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Invoice Details</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                  <p class="reviewPlaceOrderBold  text-capitalize">Sold To</p> 
                  <p class="text-uppercase">${OrderStatusDetail.billToAccount.accountName}</p>
                  <p>${OrderStatusDetail.billToAccount.address.addrLine1}</p> 
                  <p>${OrderStatusDetail.billToAccount.address.addrLine2}</p>
                  <p>${OrderStatusDetail.billToAccount.address.city}, ${OrderStatusDetail.billToAccount.address.stateOrProv} ${OrderStatusDetail.billToAccount.address.zipOrPostalCode}</p>
                  <p>${OrderStatusDetail.billToAccount.address.phoneNum}</p>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
                  <p class="reviewPlaceOrderBold text-capitalize">customer code</p>
                  <p>${OrderStatusDetail.billToAccount.accountCode}/${OrderStatusDetail.billToAccount.sapAccount.sapAccountCode}</p>
                </div>
		<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
                  <p class="reviewPlaceOrderBold text-capitalize">Created by</p>
 	 	                  <p class="text-capitalize">${OrderStatusDetail.orderedBy }</p>
                </div>
              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">shipping details</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                  <p class="reviewPlaceOrderBold text-capitalize">Ship To</p> 
	                <p class="text-uppercase">${OrderStatusDetail.shipToAccount.accountName}</p>
	                <p>${OrderStatusDetail.shipToAccount.address.addrLine1}</p> 
	                  <p>${OrderStatusDetail.shipToAccount.address.addrLine2}</p>
	                  <p>${OrderStatusDetail.shipToAccount.address.city}, ${OrderStatusDetail.shipToAccount.address.stateOrProv} ${OrderStatusDetail.shipToAccount.address.zipOrPostalCode}</p>
	                  <p>${OrderStatusDetail.shipToAccount.address.phoneNum}</p>
	              </div>
	                <div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
                  <p class="reviewPlaceOrderBold text-capitalize">customer code</p>
		<c:set var="shipCode" value="${OrderStatusDetail.billToAccount.accountCode}/${OrderStatusDetail.billToAccount.sapAccount.sapAccountCode}" />
                  <p> ${empty shipToAccountCode ? shipCode :  shipToAccountCode }</p>
                </div>
		<div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
                  <p class="reviewPlaceOrderBold text-capitalize">Order Type</p>
 	 	                  <p class="text-capitalize">${OrderStatusDetail.orderType}</p>
                </div>

              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">payment details</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                  <p class="reviewPlaceOrderBold  text-capitalize">Billing address</p>
                  <p class="text-uppercase">${OrderStatusDetail.billToAccount.accountName}</p>
                  <p>${OrderStatusDetail.billToAccount.address.addrLine1}</p> 
                  <p>${OrderStatusDetail.billToAccount.address.addrLine2}</p>
                  <p>${OrderStatusDetail.billToAccount.address.city}, ${OrderStatusDetail.billToAccount.address.stateOrProv} ${OrderStatusDetail.billToAccount.address.zipOrPostalCode}</p>
                  <p>${OrderStatusDetail.billToAccount.address.phoneNum}</p>
                </div>
                <c:if test="${not empty OrderStatusDetail.billToAccount.creditCard}" >
	                <div class="reviewFirstPanelMargin  lftPad_10">
	                  <p class="reviewPlaceOrderBold text-capitalize">card payment</p>
	                  <p class=" text-capitalize">OrderStatusDetail.billToAccount.creditCard </p>
	                </div>
                </c:if>
                <div class="reviewFirstPanelMargin lftPad_10">
                  <p class="reviewPlaceOrderBold text-capitalize">P.O. Number</p>
                  <p class=" text-capitalize">${OrderStatusDetail.customerPurchaseOrderNum}</p>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10 btmMrgn_30">
                  <p class="reviewPlaceOrderBold text-capitalize">Order Instruction / Customer Note</p>
                  <p class=" text-capitalize">
                  <c:forEach items="${orderComments}" var="comments" >
						${comments} <br />                 
                  </c:forEach>
                  </p>
                  
                </div>
              </div>
            </div>
             <c:forEach items="${OrderStatusDetail.orderUnitList}" var="orderUnit" >
             <c:if test="${orderUnit.orderNumber eq orderNumber}">
     			<c:forEach items="${orderUnit.shippingUnitList}" var="shippingUnit" >
     				<c:if test="${not empty shippingUnit.shippedItemsList }" >
		          	<div class="row">
		            <div class="reviewPlaceOrderFirstPanel userTable">
		              <div class="shipmentMethodSubHeadingFirst">
		                <div class="panel  panel-primary panel-frm ">
		                  <div class="panel-heading clickable">
		                  <spring:url value="/my-fmaccount/return-request/${orderNumber}"
											var="encodedUrl" />
		                    <h3 class="panel-title text-uppercase">Shipping From : 
 					${shippingUnit.shipFrom.name}

				<span class="text-capitalize"></span></h3>
		                    <span class="pull-right "><i class="fa fa-minus"></i></span> </div>
		                  <div class="panel-body">
		                  
		                    <div class="form-group chooseShipMethod clearfix">
		                      <div class="chooseShipMethod">
		                        <div class="clearfix">
					   <div class="col-md-4">
		                            <label class="shipMethod ">Status</label>
		                            <c:choose>
			                        	<c:when test="${shippingUnit.packingStatus eq 'IN_PROCESS' }">
			                        		<div>In Progress</div>

			                        	</c:when>
			                        	<c:when test="${shippingUnit.packingStatus eq 'Partial_shipment' || shippingUnit.packingStatus eq 'PARTIAL_SHIPMENT'}">
			                  			  	 <div>Partial Shipment</div>
			                        	</c:when>
			                        	<c:otherwise>
			                        		<div>${shippingUnit.packingStatus}</div>
			                        	</c:otherwise>
			                        </c:choose>
		                          </div>
		                            <div class="col-md-4">
		                            <label class="shipmentPlace ">Shipped From</label>
		                            <div> ${shippingUnit.shipFrom.name}

						</div>
		                          </div>
		                          <div class="col-md-4">
		                            <label class="shipmentPlace ">Shipped Date </label>
		                           	 <div>
											<c:choose>
		                            			<c:when test="${not empty shippingUnit.shipDate}">
		                            				<fmt:formatDate value="${shippingUnit.shipDate}" pattern="MM/dd/yyyy" /> 
			                            		</c:when>
			                            			<c:otherwise>
		        	                    			TBD
		                	            		</c:otherwise>
		                        	    	</c:choose>						 
						</div>
		                          </div>
		                        </div>

		                        
		                        </div>
		                        <div class="clearfix">
		                           <div class="col-md-4">
		                            <label class="carrier ">Carrier Code</label>
		                            <div>${shippingUnit.carrierName} </div>
		                          </div>
		                          <div class="col-md-4">
		                            <label class="shipMethod ">Packing Slip Number</label>
		                            <div>
		                            <c:choose>
			                        	<c:when test="${shippingUnit.packingSlip eq 'NO_PACKING_SLIP' }">
			                        		<div>No Packing Slip</div>

			                        	</c:when>
			                        	<c:otherwise>
			                        		<div>${shippingUnit.packingSlip}</div>
			                        	</c:otherwise>
			                        </c:choose>

					  </div>
		                          </div>
		                          <div class="col-md-4">
		                            <label class="shipMethod ">Tracking Number</label>
		                            <div>
		                            <c:if test="${not empty shippingUnit.trackingNumber }"  > 
			                         
			                        <!-- Balaji dynamic code to add tracking Url based on trackingUrl -->
			                        <c:if test="${not empty shippingUnit.trackingURL }"  > 
			                        	<spring:url value="${shippingUnit.trackingURL}" var="encodedUrl" />
			                        	<a href="${encodedUrl}${shippingUnit.trackingNumber}" class="orderNo" target="_blank">${shippingUnit.trackingNumber}</a>
						</c:if>

			                        <c:if test="${empty shippingUnit.trackingURL }"  > 
			                        	${shippingUnit.trackingNumber}
						</c:if>

									
			                          </c:if>
			                        <c:if test="${empty shippingUnit.trackingNumber}"  > 
			                        	TBD
						</c:if>
		                            </div>
		                          </div>
		                        </div>
		                      </div>
		                      
		                     <c:set var="totalpieces" value="0" />
		                      <c:set var="piecesShipped" value="0" />
		                      <c:set var="linesShipped" value="0" />
		                      <c:forEach items="${shippingUnit.shippedItemsList}" var="shippeditem">
		                      	<c:set var="totalpieces" value="${totalpieces + shippeditem.orderedQuantity}" />	
		                      	<c:set var="piecesShipped" value="${piecesShipped + shippeditem.shippedQuantity}" />	    
		                      	<c:if test="${shippeditem.orderedQuantity eq shippeditem.shippedQuantity}" >
		                      	     <c:set var="linesShipped" value="${linesShipped + 1}" />
		                      	</c:if>             
		                      </c:forEach>

	       			  <div class="table-responsive userTable btmMrgn_25">
					<table class="table tablesorter ordeDetailsTable">
						<thead>
							<tr class="">
							<th class=" text-capitalize">Total # Lines:${totallines}</th>
							<th class="  text-capitalize"># Lines Shipped:${linesShipped}</th>
							<th class=" text-capitalize">Total Pieces:${totalpieces}</th>
							<th class="  text-capitalize"># Pieces Shipped:${piecesShipped}</th>
							</tr>
						</thead>
					</table>
				  </div>	

		                    
		                   
		                    <div class="table-responsive userTable"><!-- id="shipmentTable3"-->
		                      <table class="table tablesorter ordeDetailsTable">
		                        <thead>
		                          <tr class="">
		                            <th class="shipmentMethodTr width55p text-capitalize">Line number</th>
		                            <th class="shipmentMethodTr width55p text-capitalize">Item details</th>
		                            <th class="shipmentMethodTr text-center text-capitalize">Ordered Quantity</th>
		                            <th class="shipmentMethodTr text-center text-capitalize">Assigned Quantity </th>
		                            <th class="shipmentMethodTr text-center text-capitalize">Shipped  Quantity</th>
		                            <th class="shipmentMethodTr text-center text-capitalize">Backordered Quantity</th>
		                          </tr>
		                        </thead>
		                        <tbody>
		                        <c:forEach items="${shippingUnit.shippedItemsList}" var="item" >
		                          <tr>
		                            <td>
		                            	<div class="prodDetail">${item.lineNumber}</div>
		                            </td>
		                            <td><div class="prodDetail">
		                                <h5>${item.description }</h5>
		                                <p>Part No: 		                                
		                                <c:choose>
			                        	<c:when test="${not empty item.displayPartNumber }">
			               					 ${item.displayPartNumber}
			                        	</c:when>
			                        	<c:otherwise>
			                        		 ${item.partNumber}
			                        	</c:otherwise>
			                        </c:choose>
		                                </p>
		                              </div></td>
		                            <td class="text-center">${item.orderedQuantity}</td>
		                              <td class="text-center">${item.assignedQuantity}</td>
		                              <td class="text-center">${item.shippedQuantity }</td>
		                            
		                            <td class="text-center">${item.backorderedQuantity}</td>
		                          </tr>
		                          </c:forEach>
		                        </tbody>
		                      </table>
		                    </div>
							</div>
		                  </div>
		                </div>
		            </div>
		          </div>
		       </c:if>
         </c:forEach>
         </c:if>
    </c:forEach>
     
    </c:if>
   
    <!-- Ends: Manage Account Right hand side panel --> 
  
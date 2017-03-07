
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="order" tagdir="/WEB-INF/tags/desktop/order"%>
<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer'}">
	<div class="clearfix bgwhite setup-content" id="step-1">
</c:if>
	<c:if test="${customerType eq 'b2cCustomer'}">
	<div class="clearfix bgwhite setup-content" id="step-2">
</c:if>
	${dc }
      <div class="row">
        <div class="shipmentMethodB2C">
          <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12 shipmentMethodPanelFirst">
          
            <h2 class="text-uppercase"> delivery Method</h2>
            <!--<h4 class="text-uppercase"><span class="myCartLabel">My cart</span> <span class="myCartId">ID: 123456789</span></h4>-->
           	 <form>
              <div class="shipmentMethodSubHeadingFirst">
              <div class="panel  panel-primary panel-frm  panel-frm-filled">
                <div class="panel-heading clickable  panel-collapsed">
                	<h3 class="panel-title text-uppercase">Shipment 1</h3>
                	<span class="pull-right "><i class="fa fa-minus"></i></span>
                </div>
                <div class="panel-body" style="">
                	<div class="chooseShipMethod clearfix">
                      <div class="col-md-4"><span class="shipmentPrice ">Esitmated Shipping Price:</span> FREE </div>
                      <div class="col-md-4"><span class="shipmentPlace ">Shipped From:</span> Skokie, IL </div> 
                      <div class="col-md-4"><label class="shipmentPlace ">Estimated Shipping Date</label><div>02/26/2015 </div></div>
               		</div>
               
	                <div class="form-group chooseShipMethod clearfix">
	                  <div class="col-md-4">
	                    <label class="text-capitalize" for="chooseCarrier">Choose carrier<!--<span class="fm_fntRed">*</span>--></label>
	                    <select id="chooseCarrier"  class="form-control">
	                       <c:forEach items="${deliveryModes}" var="deliverymethod">
	                      	<option>${deliverymethod.code}</option>
	                      </c:forEach>
	                    </select>
	                  </div>
	                  <div class="col-md-4 ">
	                    <label class="text-capitalize" for="chooseShipMethod">Choose shipping method<!--<span class="fm_fntRed" >*</span>--></label>
	                    <select id="chooseShipMethod"  class="form-control">
	                      <option>Ground</option>
	                      <option>Priority Overnight</option>
	                      <option>First Overnight</option>
	                      <option>Standard Overnight</option>
	                      <option>2Day</option>
	                      <option>3Day</option>
	                      <option>Sat Overnight</option>
	                      <option>2Day Saturday</option>
	                      <option>Standard Shipping</option>
	                      <option>Pick Up</option>
	                    </select>
	                  </div>
	                  <div class="col-md-4">
                        <label for="carrierAccCode" class="text-capitalize">Carrier Account Code<!--<span class="fm_fntRed">*</span>--></label>
                        <input type="text" value="23141123" class="width175 form-control">
                      </div>
	                </div>                    
                     
                           
	                <div class="table-responsive userTable"><!-- id="shipmentTable"-->
	                  <table class="table tablesorter shipmentTable">
	                    <thead>
	                      <tr class="">
	                        <th class="shipmentMethodTr width55p text-capitalize" colspan="2">Item details</th>
	                        <th class="shipmentMethodTr text-center text-capitalize">Weight</th>
	                        <th class="shipmentMethodTr text-right text-capitalize">Price</th>
	                        <th class="shipmentMethodTr text-center text-capitalize">Quantity</th>
	                        <th class="shipmentMethodTr text-center text-capitalize">Total</th>
	                      </tr>
	                    </thead>
	                    <tbody>
	                    <c:forEach items="${cartData.entries}" var="entry">
	                    <c:url value="${entry.product.url}" var="productUrl"/>
	                      <tr>                     
						
	                        <td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
	                        <td><div class="prodDetail">
	                            <h5>${entry.product.name}</h5>
	                            <p>Part No: ${entry.product.partNumber}</p>
	                            <p>QTY:  ${entry.quantity}</p>
	                          </div></td>
	                        <td class="text-center">2 lbs</td>
	                        <td class="text-right">$${entry.product.price.value }</td>
	                        <td class="text-center">${entry.quantity}</td>
	                        <td class="text-right">$${entry.totalPrice.value }</td>
							
	                      </tr>
	                      </c:forEach>
	                      <tr class="tableBottomTitle">
	                        <th class="shipmentMethodTr width55p text-capitalize" colspan="2"></th>
	                        <th class="shipmentMethodTr text-center text-capitalize">Weight</th>
	                        <th class="shipmentMethodTr text-right text-capitalize"></th>
	                        <th class="shipmentMethodTr text-center text-capitalize">Quantity</th>
	                        <th class="shipmentMethodTr text-center text-capitalize">Total</th>
	                      </tr>                      
	                      <tr>
	                        <td ></td>
	                        <td ></td>
	                        <td class="text-center">2 lbs</td>
	                        <td class="text-right"> </td>
	                        <td class="text-center">${cartData.totalUnitCount}</td>
	                        <td class="text-right">$${cartData.subTotal.value }</td>
	                        
	                      </tr>
	                     
	                    </tbody>
	                  </table>
	                </div>
                </div>
              </div>
              </div>
            </form>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 shipmentMethodPanelSecond">
            	<div class="orderSummary">
              		<h3 class="text-uppercase">order summary</h3>
          			<order:orderhistory/>
          		 </div>
            <h5 class="">Shipping Information</h5>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed elit felis, scelerisque a lorem quis, varius lobortis arcu. Phasellus ultricies erat nunc, non tristique sem placerat eu. Cras et magna ornare, ornare justo sodales, faucibus sapien. Maecenas fringilla tellus sit amet malesuada viverra. Praesent condimentum mi a dui porttitor scelerisque. Sed at arcu in sem vestibulum finibus. Nulla a feugiat augue. </p>
          </div>
          <div class="shipmentMethodBtn pull-right ">
             <c:url value="https://testsecureacceptance.cybersource.com/pay" var="paymentFormUrl" />
              <form:form id="hostOrderPostForm" name="hostOrderPostForm" commandName="hopPaymentDetailsForm" class="create_update_payment_form" action="${paymentFormUrl}" method="POST">
 						<c:forEach items="${hopPaymentDetailsForm.unSignedParams}" var="entry" varStatus="status">
								<input type="hidden" id="${entry.key}" name="${entry.key}" value="${entry.value}"/>
						</c:forEach>
    					 <c:forEach items="${hopPaymentDetailsForm.parameters}" var="entry" varStatus="status">
								<input type="hidden" id="${entry.key}" name="${entry.key}" value="${entry.value}"/>
						</c:forEach>
           </form:form>
           <c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2cCustomer'}">
				<button id="activate-step-2" type="submit" onClick='submitPaymentForm()' class="btn btn-sm btn-fmDefault pull-right text-uppercase">Continue to Payment Details</button>
			</c:if>
           	<c:if test="${customerType eq 'b2BCustomer'}">
				<c:url value="/checkout/multi/paymentDetails" var="encodedUrl"/>
            	<a href="${encodedUrl }"><button id="activate-step-2" type="button" class="btn btn-sm btn-fmDefault pull-right text-uppercase">Continue to Payment Details</button></a>             
            </c:if>
            <c:if test="${customerType eq 'b2cCustomer'}">
            	<c:url value="/checkout/multi" var="encodedUrl"/>
            	<a href="${encodedUrl }" class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right">Return to billing &amp; Shipping Address</a>           
            </c:if>
             <c:if	test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer'}">
            	<c:url value="/cart" var="encodedUrl"/>
            	<a href="${encodedUrl}" ><button class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right"><!--<i class="fa fa-angle-left"></i> -->Return to cart</button></a>           
            </c:if>
          </div>
     </div>      
     </div>  
     </div> 

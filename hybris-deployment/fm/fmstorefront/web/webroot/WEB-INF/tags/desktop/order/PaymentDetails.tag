<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
	<div id="step-2" class="clearfix bgwhite setup-content" style="display: block;">
      <div class="row">
        <div class="shipmentMethodB2C paymentb2c">
          <div class="col-lg-8 col-md-8 col-sm-8 col-xs-12 shipmentMethodPanelFirst">
            <h2 class="text-uppercase"><spring:theme code="checkout.page.paymentdetails.purchaseOrderDetails"/> </h2>
            <!-- <p>Enter your payments details below</p>
            <h4 class="text-capitalize">account payment</h4> -->
            <div class="row">
              <div class="col-sm-6">
                <form class="">
                  <div class="form-group  regFormFieldGroup "> 
                    <label for="poNumber" class=""><spring:theme code="checkout.page.deliverymethod.poNumber"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                    <input type="text" class="form-control width270" placeholder="" name="p_poNumber" id="poNumber" >
                     <span id="err_poNumber"class="poerror"><spring:theme code="checkout.page.deliverymethod.fieldRequired"/></span>
                  </div>
                  <div class="form-group  regFormFieldGroup ">
                    <label for="orderNote" class=""><spring:theme code="checkout.page.deliverymethod.orderNote"/></label>
                    <textarea class="form-control width270 height200" name="p_orderNote" id="orderNote" maxlength="60" minheight="140" ></textarea>
 			<span class="char-count" ></span>
                  </div>
                </form>
              </div>
            </div>
          </div>
          <%-- <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 shipmentMethodPanelSecond">
            <h4 class="text-uppercase ">Order related terms or information</h4>
            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed elit felis, scelerisque a lorem quis, varius lobortis arcu. Phasellus ultricies erat nunc, non tristique sem placerat eu. Cras et magna ornare, ornare justo sodales, faucibus sapien. Maecenas fringilla tellus sit amet malesuada viverra. Praesent condimentum mi a dui porttitor scelerisque. Sed at arcu in sem vestibulum finibus. Nulla a feugiat augue. </p>
          </div> --%>
          <div class="shipmentMethodBtn pull-right">
          		<c:url value="/checkout/multi/reviewPlaceOrder" var="encodedUrl"/>
          		<form id="paymentDetailsForm" class="" action="${encodedUrl}" method="post">
					<c:set var="buttonType"><spring:theme code="checkout.page.paymentDetails.Submit"/></c:set>
					<input type="hidden" id="poNumber" name="poNumber" value="">
					<input type="hidden" name="orderInstruction" id="h_orderIns" value=""/>
				</form>
				<button type="${buttonType}"  onclick="submitB2BPaymentForm()" class="btn btn-sm btn-fmDefault pull-right text-uppercase"><spring:theme code="checkout.page.deliverymethod.continueToReview"/> &amp; <spring:theme code="checkout.page.deliverymethod.placeorder"/></button>
				<c:if test="${ cartData.fmordertype == 'pickup' }" >
					<c:url value="/cart" var="encodedUrl"/>
            		<a href="${encodedUrl }" class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right"> <spring:theme code="checkout.page.return.cart"/></a>
				</c:if>
				<c:if test="${ cartData.fmordertype != 'pickup' }" >
					<c:url value="/checkout/multi/deliverymethod" var="encodedUrl"/>
            		<a href="${encodedUrl }" class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right"> <spring:theme code="checkout.page.paymentdetails.returnTO.Deliverymethod"/></a> 
            	</c:if> 
            </div>
        </div>
      </div>
    </div>
          

  
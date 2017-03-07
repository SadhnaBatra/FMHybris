<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


 <c:forEach items="${ReturnOrder.orderUnitList}" var="orderUnit" >
     	<c:set var="orderNumber" value="${orderUnit.orderNumber }" />
     	<fmt:formatDate value="${orderUnit.originalOrderDate}" pattern="MM/dd/yyyy" var="orderDate" /> 
   <c:forEach items="${orderUnit.shippingUnitList}" var="shippingUnit" >
     	<c:set var="orderStatus" value="${shippingUnit.packingStatus}" />
     </c:forEach>
     </c:forEach>
<c:url value="/my-fmaccount/return-request-confirmation" var="submitAction" />
		<form:form class="topMargn_25" method="POST" commandName="returnRequestFormData"
			action="${submitAction}" enctype="multipart/form-data">     
<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
    <div class="manageUserPanel rightHandPanel clearfix">
      <h2 class="text-uppercase">Return Request</h2>
      <div class="chooseShipMethod clearfix">
        <div class="col-md-4">
          <label class="shipmentPrice ">Confirmation Number</label>
          <div>${orderNumber}</div>
        </div>
         <!--<div class="col-md-4">
          <label class="shipmentPrice ">Invoice Number</label>
          <div>2312431</div>
        </div>
       <div class="col-md-4">
                          <label class="shipmentPlace ">Purchase Order Number</label> 
                          <div>Skokie, IL </div></div> -->
        
        <div class="col-md-4">
          <label class="shipmentPlace ">Sales Order Number</label>
          <div>${orderNumber}</div>
        </div>
      </div>
      <div class="chooseShipMethod clearfix">
        <div class="col-md-4">
          <label class="shipmentPrice ">Return Reason <span class="fm_fntRed">*</span></label>
          <div>
           <form:select name="returnReason" path="returnReason" id="returnReason" class="form-control width270">
                  <option value=" ">-Select Return Reason-</option>
				<c:forEach items="${fMReasonForReturn}" var="returnReason">
				   <option value="${returnReason}" selected="selected" >${returnReason}</option>
				</c:forEach>
			 </form:select>
          </div>
        </div>
        <div class="col-md-4">
          <label class="shipmentPrice" for="returnDescriptionform">Return Description</label>
          <div>
             <form:textarea cols="50" rows="4" class="form-control width448" name="returnDescriptionform" path="returnDescriptionform" 
                  id="returnDescriptionform" maxlength="500" type="text" ></form:textarea>
          </div>
        </div>
      </div>
      <div class="table-responsive userTable topMargn_25"><!-- id="shipmentTable"-->
        <table class="table tablesorter shipmentTable">
          <thead>
            <tr class="">
              <th class="shipmentMethodTr text-capitalize"><!--<input type="checkbox" id="fullfillOption1">--></th>
              <th class="shipmentMethodTr text-capitalize" colspan="2">Item Description</th>
              <th class="shipmentMethodTr text-center text-capitalize">Ordered Qty.</th>
              <th class="shipmentMethodTr text-center text-capitalize">Return Qty.</th>
              <!-- <th class="shipmentMethodTr text-center text-capitalize">Reason for return</th>
             <th class="shipmentMethodTr text-center text-capitalize">Original Price</th> --> 
            </tr>
          </thead>
          <tbody>
           <c:forEach items="${ReturnOrder.orderUnitList}" var="orderUnit" >
     			<c:forEach items="${orderUnit.shippingUnitList}" var="shippingUnit" >
     				<c:if test="${not empty shippingUnit.shippedItemsList }" >
     				<c:forEach items="${shippingUnit.shippedItemsList}" var="item" >
		            <tr>
		              <td><input type="checkbox" id="fullfillOption2" checked></td>
		              <td><!-- <div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div> --></td>
		              <td><div class="prodDetail">
		                  <h4>${item.description }</h4>
		                  <p>Part No: ${item.displayPartNumber}</p>
		                </div></td>
		              <td class="text-center">${item.orderedQuantity}</td>
		              <td class="text-right"><input type="text" class="form-control width75 text-right" value="1"/></td>
		              <!--   <td class="text-center"><select id="display" class="form-control">
		                  <option>Select</option>
		                </select></td>
		            <td class="text-right">49.26</td>--> 
		            </tr>
		            </c:forEach>
           			</c:if>
           	</c:forEach>
           	</c:forEach>
           	<c:if test="${(orderNumber) eq '999999' }">
           	 <tr>
		              <td><input type="checkbox" id="fullfillOption2" checked></td>
		              <td><!-- <div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div> --></td>
		              <td><div class="prodDetail">
		                  <h4>Bearing NNT111</h4>
		                  <p>Part No: NNT201CC</p>
		                </div></td>
		              <td class="text-center">2</td>
		              <td class="text-right"><input type="text" class="form-control width75 text-right" value="1"/></td> 
		            </tr>
		            <tr>
		              <td><input type="checkbox" id="fullfillOption2" checked></td>
		               <td><!-- <div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div> --></td>
		              <td><div class="prodDetail">
		                  <h4>Bearing NNT112344</h4>
		                  <p>Part No: NNT202CC</p>
		                </div></td>
		              <td class="text-center">2</td>
		              <td class="text-right"><input type="text" class="form-control width75 text-right" value="1"/></td>
		              <!--   <td class="text-center"><select id="display" class="form-control">
		                  <option>Select</option>
		                </select></td>
		            <td class="text-right">49.26</td>--> 
		     </tr>
		     </c:if>
          </tbody>
        </table>
      </div>
      <div class="topMargn_10"> <a href="#"> Please read our return policy. </a> </div>
      <div class="profileBtn topMargn_25 btmMrgn_30"><!-- <a href="profile_password.html"></a> --> 
        <%-- <c:url value="/my-fmaccount/return-request-confirmation" var="encodedUrl" />
        <a href="${encodedUrl}">
        <button id="update_password" class="btn btn-fmDefault">submit</button> --%>
        <button class="btn btn-sm btn-fmDefault text-uppercase registrationBtns"
						type="submit">Submit</button>
        </a><a href="orderHistory.html">
        <button class="btn btn-fmDefault ">Cancel</button>
        </a> </div>
    </div>
  </div>
</form:form>
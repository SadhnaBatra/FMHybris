<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/desktop/grid" %>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart" %>
		<table id="myTable" class="table tablesorter">
	        <thead>
	          <tr>
	            <th class="col-md-5" colspan="3"><spring:theme code="basket.page.title"/></th>
	            <!-- <th class="col-md-3">Delivery Method</th> -->
	            <th class="col-md-1 text-center"><spring:theme code="basket.page.itemPrice"/></th>
	            <th class="col-md-1 text-center">Price Type</th>
	            <th class="col-md-2 text-center"><spring:theme code="basket.page.quantity"/></th>
	            <th class="col-md-1 text-center"><spring:theme code="basket.page.total"/></th>
	          </tr>
	        </thead>
	        <tbody>
	      	<c:forEach items="${cartData.entries}" var="entry">
	      		<c:set var="categoryCodeList" value="${fn:split(entry.product.url, '/')}" />
				<c:if test="${not empty categoryCodeList[0]}">
					<c:set var="categoryCode" value="${categoryCodeList[0]}"></c:set>
				</c:if>	      	
	      	
			<c:url value="${entry.product.url}" var="productUrl"/>
			<tr>
				<td class="col-md-1  col-xs-2 imgCol">
					<a href="${productUrl}?categoryCode=${categoryCode}"><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></a>
				</td>
				<td class="col-md-8  col-xs-8" colspan="2">
					<div class="shoppingCartsubHead">
						<ycommerce:testId code="cart_product_name">
						<a href="${productUrl}?categoryCode=${categoryCode}"><h4 class="fm_fntRed miniCartProdName">${entry.product.name}</h4></a>
						</ycommerce:testId>
						<c:set var="entryStock" value="${entry.product.stock.stockLevelStatus.code}"/>
						<div class="">
                          <span><span class="">Part No:</span>${entry.product.code} </span>
                          <!-- <span class="lftMrgn_5 rghtMrgn_5">|</span>
                          <span><span class="shoppingCartBold">Warranty:</span> <span class="addNewAddLink "> Lifetime Replacement</span></span> -->
                        </div>
                        
                        <c:if test="${entry.errorMessage != null}" >
                       		<c:set var="errMSG" value="${fn:split(entry.errorMessage , '~')}" />
	                        
	                        <div class="errorMSG">
	                        	<span class="" style="color:red">${errMSG[0]}</span>
	                        	<c:if test="${not empty errMSG[1]}">
	                        		</br>
	                        		<span class="" style="color:red">${errMSG[1]}</span>
	                        	</c:if>
	                        </div>
                        </c:if>
 						</div>
					</td>
					
					<td class="col-md-1 ">
						<div class="shoppingCartRate">
							<c:choose>
								<c:when test="${entry.product.multidimensional and (entry.product.priceRange.minPrice.value ne entry.product.priceRange.maxPrice.value)}" >
									<format:price priceData="${entry.product.priceRange.minPrice}" displayFreeForZero="true"/>
									-
									<format:price priceData="${entry.product.priceRange.maxPrice}" displayFreeForZero="true"/>
								</c:when>
								<c:otherwise>
									<format:price priceData="${entry.basePrice}" displayFreeForZero="true"/>
								</c:otherwise>
							</c:choose>
						</div>
					</td>
					<td class="col-md-1 ">
						<c:set var="priceType" value="N/A"/>
						<c:choose>
						<c:when test="${entry.priceType eq 'WD1'}">
							<c:set var="priceType" value="D"/>
						</c:when>
						<c:when test="${entry.priceType eq 'JBR'}">
							<c:set var="priceType" value="J"/>
						</c:when>
						<c:otherwise>
							<c:set var="priceType" value=""/>
						</c:otherwise>
						</c:choose>
						<div class="shoppingCartRate text-center">${priceType}</div>
					</td>
					<td class="col-md-2 text-center">
						<div class="clearfix" >
						<div class="pull-left">
							<c:url value="/cart/update" var="cartUpdateFormAction" />
							<form:form id="updateCartForm${entry.entryNumber}" action="${cartUpdateFormAction}" method="post" commandName="updateQuantityForm${entry.entryNumber}">
								<input type="hidden" name="entryNumber" value="${entry.entryNumber}"/>
								<input type="hidden" name="productCode" value="${entry.product.code}"/>
								<input type="hidden" name="initialQuantity" value="${entry.quantity}"/>
								
									<c:choose>
										<c:when test="${not entry.product.multidimensional}" >
											<ycommerce:testId code="cart_product_quantity">
												<form:label cssClass="skip" path="quantity" for="quantity${entry.entryNumber}"><spring:theme code="basket.page.quantity"/></form:label>
												<c:if test="${entry.updateable}" >
													<form:input disabled="${not entry.updateable}" type="text" maxlength="5" size="1" id="quantity${entry.entryNumber}" class="form-control width58 " path="quantity" onkeypress="return isNumber(event);"/>
												</c:if>
											</ycommerce:testId>
											<c:if test="${entry.updateable}" >
												<ycommerce:testId code="cart_product_updateQuantity">
													<div class="addNewAddLink shoppingCartQuantityLink shoppingCartQuantityLinkMargin pull-left lftMrgn_5">
														<a href="#" id="QuantityProduct_${entry.entryNumber}" class=" addNewAddLink updateQuantityProduct"><span class="shoppingCartDisplay"><spring:theme code="basket.page.update"/></span></a>
													</div>
												</ycommerce:testId> 
											</c:if>
										</c:when>
										<c:otherwise>
											<span class="qty">
												<c:out value="${entry.quantity}" />
											</span>
											<input type="hidden" name="quantity" value="0"/>								
											<ycommerce:testId code="cart_product_updateQuantity">
												<a href="#" id="QuantityProduct_${entry.product.code}" class="updateQuantityProduct"><spring:theme code="basket.page.updateMultiD"/></a>
											</ycommerce:testId>
										</c:otherwise>
									</c:choose>
								
							</form:form>
							<c:if test="${entry.updateable}" >
								<ycommerce:testId code="cart_product_removeProduct">
									<div class="addNewAddLink shoppingCartQuantityLink shoppingCartQuantityLinkMargin pull-left lftMrgn_5">
										<a href="#" id="RemoveProduct_${entry.entryNumber}" class="addNewAddLink submitRemoveProduct">Remove</a>
									</div>
								</ycommerce:testId>
							</c:if>
							</div>
						</div>
					</td>

					<td class="col-md-1 text-right">
						<div class="shoppingCartRate text-right">
							<ycommerce:testId code="cart_totalProductPrice_label">
								<format:price priceData="${entry.totalPrice}" displayFreeForZero="true"/>
							</ycommerce:testId>
						</div>
					</td>				 
				</tr>
				 
				<c:if test="${entry.product.multidimensional}" >
					<tr><th colspan="5">
						<c:forEach items="${entry.entries}" var="currentEntry" varStatus="stat">
							<c:set var="subEntries" value="${stat.first ? '' : subEntries}${currentEntry.product.code}:${currentEntry.quantity},"/>
						</c:forEach>

						<div style="display:none" id="grid_${entry.product.code}" data-sub-entries="${subEntries}"> </div>
					</th></tr>		
				</c:if>
				 
			</c:forEach>
			<tr class="orderSubTotalRow">
			<c:if test="${not empty freeFrieghtAmt}">
			<td class="freeFreightCol" colspan="4">
                  			<div class="checkoutSubTitle ">Free Freight Requirement</div> 
                  		<div class="table-responsive userTable">
           					 <table class="table tablesorter" id="myTable">
             					 <thead>
                					<tr class="text-capitalize" >
                 					 <th>Federal Mogul Minimum Purchase</th>
                 					 <th>Additional Amount Required</th>
               						 </tr>
              					</thead>
             				 <tbody>
                                 <tr class="freeFrieghtTotal">                				              						    
               	  					 <td>
                 	 					<div><fmt:formatNumber value="${freeFrieghtAmt}" type="currency" currencySymbol="$"/></div>                 		
                 	 				</td>
                   					<td>
                   					<c:set var="totalePrice" value="${cartData.subTotal.value}" />
                					  <c:if test="${freightPrice < freeFrieghtAmt}">
                   						<!--  <div>${freeFrieghtAmt-totalePrice}</div> -->
                   						<fmt:formatNumber value="${freeFrieghtAmt-freightPrice}" type="currency" currencySymbol="$"/>
                   					</c:if>
                   					 <c:if test="${freightPrice > freeFrieghtAmt}">
                   						<div>Free</div>
                   					</c:if>
                  					</td>                                  					
                 				 </tr>
             				 </tbody>
            				</table>
          			</div>   
          			 
              </td>                          
              </c:if>
				<td colspan="2" class="text-right">
					<p class=""> <span class="shoppingSubTotal"> Subtotal</span></p></td>
					<td><p  class="shoppingCartTotal fm_fntRed"><ycommerce:testId code="Order_Totals_Subtotal">
				<format:price priceData="${cartData.subTotal}"/>
			</ycommerce:testId></p>
				</td>
				
			</tr>
		</tbody>
	</table>
	
	<product:productOrderFormJQueryTemplates />

<div class="modal fade in" id="findPopupLocation" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-Dialog billingShippingPopUpB2B findPopupLocation"
		style="margin-top: 20.5px; width: 92%;">
		<button data-dismiss="modal" class="close" type="button">
			<span class="fa fa-close" aria-hidden="true"></span><span
				class="sr-only">Close</span>
		</button>
		<div class="modal-content col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="modal-header">
				<h2 class="text-uppercase">find pickup location</h2>
			</div>
			<div class="modal-body">
				<div class="form-group  regFormFieldGroup ">
					<label class="" for="billZipPostalCode">Enter Zip Code or
						City, State<span class="fm_fntRed">*</span>
					</label>
				</div>
				<div class="form-group regFormFieldGroup row">
					<div class="col-md-3">
						<input type="text" id="billZipPostalCode" name="billZipPostalCode"
							placeholder="" class="form-control width270" />
					</div>
					<div class="col-md-2">
						<button class="btn btn-fmDefault shoppingCartCheckOut">find
							stores</button>
					</div>
					<!-- <div class="col-md-7">
						<span class="shoppingCartBold">2 stores found within 100
							miles</span>
					</div> -->
				</div>
				<ul class="list-group checkboxGroup">
					<li class="list-group-item"></br><!-- <label for="savetomyaddress1">
							<input type="checkbox" id="savetomyaddress1" /> &nbsp;Only show
							stores with product availability
					</label>-->
					</li>
				</ul> 
				<!-- <div class="table-responsive userTable">
					<table id="myTable" class="table tablesorter">
						<thead>
							<tr>
								<th class="text-uppercase">store locations</th>
								<th class="text-uppercase">distance</th>
								<th class="text-uppercase">store availability</th>
								<th class="text-uppercase">buy online, pick up in store</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-capitalize"><p class="shoppingCartBold">Farmington
										technical support center</p>
									<p>2381 orchard lake rd,</p>
									<p>farmington, m148336</p>
									<p class="shoppingCartBold">
										<span class="fm-blue-text">Maps &amp; Directions</span><span
											class="lftMrgn_5 rghtMrgn_5">|</span><span
											class="fm-blue-text">Hours</span>
									</p></td>
								<td class="text-capitalize">66 miles</td>
								 <td class="text-capitalize">in stock</td>
								<td class=""><button
										class="btn btn-fmDefault shoppingCartCheckOut">pick
										up in store</button></td>
							</tr>
							<tr>
								<td class="text-capitalize"><p class="shoppingCartBold">novi
										technical support center</p>
									<p>43131 orchard lake rd,</p>
									<p>farmington, m148336</p>
									<p class="shoppingCartBold">
										<span class="fm-blue-text">Maps &amp; Directions</span><span
											class="lftMrgn_5 rghtMrgn_5">|</span><span
											class="fm-blue-text">Hours</span>
									</p></td>
								<td class="text-capitalize">90 miles</td>
								<td class="text-capitalize">limited stock</td>
								<td class=""><button
										class="btn btn-fmDefault shoppingCartCheckOut">pick
										up in store</button></td>
							</tr>
						</tbody>
					</table>
				</div> -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
 function showhide(id, id1) {
    var e = document.getElementById(id);
    var f = document.getElementById(id1);
    e.style.display = (e.style.display == 'block') ? 'none' : 'block';
    f.style.display = (f.style.display == 'block') ? 'none' : 'block';
    
 }
/*  function hide(id, id1) {
	    var e = document.getElementById(id);
	    var f = document.getElementById(id1);
	    e.style.display = (e.style.display == 'block') ? 'none' : 'block';
	    f.style.display = (f.style.display == 'block') ? 'none' : 'block';
	    
	 } */
 </script>
	
<!-- </div> -->


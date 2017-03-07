<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="cartData" required="true"
	type="de.hybris.platform.commercefacades.order.data.CartData"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>
<%@ taglib prefix="grid" tagdir="/WEB-INF/tags/desktop/grid"%>
<%@ taglib prefix="cart" tagdir="/WEB-INF/tags/desktop/cart"%>

	
	
		<c:forEach items="${cartData.entries}" var="entry">
		   <c:set var="categoryCodeList"
				value="${fn:split(entry.product.url, '/')}" />
			<c:if test="${not empty categoryCodeList[0]}">
				<c:set var="categoryCode" value="${categoryCodeList[0]}"></c:set>
			</c:if>
			<c:url value="${entry.product.url}" var="productUrl" />
			<c:set var="string1" value="${productUrl}" />
			<c:set var="productlink"  value="${fn:replace(string1, '/p/', '/lp/')}" />
			
			<c:forEach items="${entry.entries}" var="varientEntry" varStatus="stat">
				<tr>
		  				<td class="col-md-1  col-xs-2 imgCol">
		  				  <a href="${productlink}"><product:productPrimaryImage product="${entry.product}" format="thumbnail" /></a>
		  				</td>
		  				<td class="col-md-8  col-xs-8" colspan="2">
							<div class="shoppingCartsubHead">
								<ycommerce:testId code="cart_product_name">
									<c:url value="${productlink}" var="productUrl" />
		
									<c:set var="productCode"
										value="${fn:substringAfter(productUrl, 'lp/')}" />
		
									<h4 class="fm_fntRed miniCartProdName"> 
										<a href="${productlink}">${varientEntry.product.name}</a> 
									</h4>
								</ycommerce:testId>
								<c:set var="entryStock" value="${entry.product.stock.stockLevelStatus.code}" />
								<div class="">
									<span><span class="">Product Code:</span>${varientEntry.product.code}</span>
							    </div>
								<c:if test="${entry.errorMessage != null}">
									<c:set var="errMSG" value="${fn:split(entry.errorMessage , '~')}" />
		
									<div class="errorMSG">
										<span class="" style="color: red">${errMSG[0]}</span>
										<c:if test="${not empty errMSG[1] && errMSG[0] != errMSG[1]}">
											</br>
											<span class="" style="color: red">${errMSG[1]}</span>
										</c:if>
									</div>
								</c:if>
							</div>
						</td>
		  				<td class="col-md-1 ">
							<div class="shoppingCartRate text-center">${entry.product.loyaltyPoints} </div>
						</td>
						<td class="col-md-2 text-center">
							<div class="clearfix">
								 <div class="pull-left">
									<c:url value="/loyaltycart/update" var="cartUpdateFormAction" />
										<form:form id="updateCartForm${varientEntry.entryNumber}"
											action="${cartUpdateFormAction}" method="post"
											commandName="updateQuantityForm${entry.entryNumber}">
											<input type="hidden" name="entryNumber"
												value="${varientEntry.entryNumber}" />
											<input type="hidden" name="productCode"
												value="${varientEntry.product.code}" />
											<input type="hidden" name="initialQuantity"
												value="${varientEntry.quantity}" />
										<ycommerce:testId code="cart_product_quantity">
											<form:label cssClass="skip" path="quantity" for="quantity${varientEntry.entryNumber}"><spring:theme code="basket.page.quantity"/></form:label>
											<c:if test="${entry.updateable}" >
												<form:input disabled="${not entry.updateable}" type="text" maxlength="5" size="1" id="quantity${varientEntry.entryNumber}" class="form-control width58 " path="quantity" 
												 value="${varientEntry.quantity}" onkeypress="return isNumber(event);"/>
											</c:if>
										</ycommerce:testId>
										<c:if test="${entry.updateable}" >
											<ycommerce:testId code="cart_product_updateQuantity">
												<div class="addNewAddLink shoppingCartQuantityLink shoppingCartQuantityLinkMargin pull-left lftMrgn_5">
													<a href="#" id="QuantityProduct_${varientEntry.entryNumber}" class=" addNewAddLink updateQuantityProduct"><span class="shoppingCartDisplay"><spring:theme code="basket.page.update"/></span></a>
												</div>
											</ycommerce:testId> 
										</c:if>
									</form:form>	
									<c:if test="${entry.updateable}">
										<ycommerce:testId code="cart_product_removeProduct">
											<div
												class="addNewAddLink shoppingCartQuantityLink shoppingCartQuantityLinkMargin pull-left lftMrgn_5">
												<a href="#" id="RemoveProduct_${varientEntry.entryNumber}" class="addNewAddLink submitRemoveProduct">Remove</a>
											</div>
										</ycommerce:testId>
									</c:if>
								</div> 
							</div>
						</td>
						<td class="col-md-1 text-right">
							<div class="shoppingCartRate text-right">
								<c:set var="subtotal" value="${entry.product.loyaltyPoints * entry.quantity}" />
								<ycommerce:testId code="cart_totalProductPrice_label">
										${entry.product.loyaltyPoints * varientEntry.quantity}
								</ycommerce:testId>
							</div>
					  </td>
		  			</tr>
			</c:forEach>
		</c:forEach>

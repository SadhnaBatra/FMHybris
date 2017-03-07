<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData" %>
<%@ attribute name="isOrderForm" required="false" type="java.lang.Boolean" %>
<%@ attribute name="table" required="false" type="java.lang.Boolean" %>
<%@ attribute name="categoryCode" required="false" type="java.lang.String" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product"%>

<c:set var="whereToBuyURI"><spring:eval expression="@propertyConfigurer.getProperty('wheretobuyurl')" /></c:set>

<div class="clearfix">
	<div class="prodFilter prodDetailFilter col-lg-12 col-xs-12">
			
				<c:if test="${user.uid != 'anonymous'}">
				<sec:authorize ifNotGranted="ROLE_FMB2T">
					<%-- <div itemtype="http://schema.org/Offer" itemscope="" itemprop="offers" class="col-sm-6">
  						<div itemprop="price" class="mainPrice"><format:fromPrice priceData="${product.price}" /> </div>
  					<div class="msrPrice"> MSRP </div>
					</div> > --%>
				 <div class="col-sm-12 hideAddedtoCart lftPad_0 topMargn_10  rgtPad_0">
				<c:if test="${not empty partResolutionMessage }">
					<c:if test="${partResolutionMessage ne 'success'}">
						<c:if test="${fn:contains(partResolutionMessage, '@')}">
							<c:set var="errorMSG"
								value="${fn:split(partResolutionMessage , '@')}" />
							<c:set var="errMSG" value="${fn:split(errorMSG[0] , '~')}" />
						</c:if>
						<c:if test="${!fn:contains(partResolutionMessage, '@')}">
							<c:set var="errMSG"
								value="${fn:split(partResolutionMessage , '~')}" />
						</c:if>
						<div class="clearfix col-lg-5  lftPad_0 rgtPad_0 errorMSG">
							<span class="productDetailPageResolutionmsg" style="color:#c41230">${errMSG[0]}</span>
							<c:if test="${not empty errMSG[1]}">
								</br>
								<span class="" style="color: red">${errMSG[1]}</span>
							</c:if>
						</div>
					</c:if>
				</c:if>



				<form class="form-horizontal">
						
						<div class="col-lg-4">
	
							<label for="qty" class="control-label DINWebBold prodQtyLabel ">Quantity</label>							
								<input type="text" maxlength="5" size="1" id="qtyInput" name="qtyInput" class="form-control width58 prodQtyInput pull-right" value="1" width="30%">
							<!-- <div
								class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
								<select class="form-control" id="qty">
									<option>1</option>
									<option>2</option>
									<option>3</option>
									<option>4</option>
								</select>
							</div> -->
						</div>
					</form>
				
				<c:set var="credit" value="true"/>
				<c:set var="order" value="true"/>
				<c:if test="${creditBlock != null && creditBlock =='X' }" >
					<c:set var="credit" value="false"/>
				</c:if>
				<c:if test="${orderBlock != null && orderBlock == 'X' }" >
					<c:set var="order" value="false"/>
				</c:if>

				<form id="addToCartForm" class="add_to_cart_form" action="<c:url value="/cart/add"/>" method="post">
					<c:set var="buttonType">submit</c:set>
					<input type="hidden" maxlength="5" size="1" id="qty" name="qty" class="qty" value="1">
					<input type="hidden" name="productCodePost" value="${product.code}"/>
					<div class="col-lg-3">
					<c:if test="${order == 'true'}">
						<sec:authorize ifAnyGranted="ROLE_FMVIEWONLYGROUP,FMBUVOR">
						<button type="${buttonType}" class="btn btn-fmDefault prodDetAddtoCart "onclick="javascript:ACC.track.trackAddToCart('${product.code}',$('#qty').val(),'${product.name}','${product.price.value}');"  disabled="disabled">ADD TO CART</button>
						</sec:authorize>
						
						 <sec:authorize ifNotGranted="ROLE_FMVIEWONLYGROUP,FMBUVOR">
						<button type="${buttonType}" class="btn btn-fmDefault prodDetAddtoCart" onclick="javascript:ACC.track.trackAddToCart('${product.code}',$('#qty').val(),'${product.name}','${product.price.formattedValue}');"${isValidPart =='false' ? 'disabled="disabled"' :'' } >ADD TO CART</button>
						</sec:authorize>
					</c:if>
					</div>
				</form>
</div>
</sec:authorize>
				</c:if>
				

				<c:if test="${user.uid == 'anonymous'}">
					<!--Please sign in to place orders <ycommerce:testId code="header_Login_link"><a href="<c:url value='/sign-in'/>"><spring:theme code="header.link.login"/></a></ycommerce:testId> -->

					<a href="${whereToBuyURI}#hybrisProductCode=${product.brands.corpcode}" class="btn btn-fmDefault pull-right" style="display: block">where to buy</a>
				</c:if>
					 <sec:authorize ifAnyGranted="ROLE_FMB2T">
					<!--Please sign in to place orders <ycommerce:testId code="header_Login_link"><a href="<c:url value='/sign-in'/>"><spring:theme code="header.link.login"/></a></ycommerce:testId> -->

					<a href="${whereToBuyURI}#hybrisProductCode=${product.brands.corpcode}" class="btn btn-fmDefault pull-right" style="display: block">where to buy</a>
				</sec:authorize>
</div> 
		</div>


 <div class="fitmentCheck clearfix">
 			<c:if test="${not empty years}">
              <div class="pull-left"> <i class="fa fa-check-circle fmFitsChecked"></i> <span class="fitLabel">Fits: </span>${years}&nbsp ${make}&nbsp ${model} </div>
              <div class="pull-right">
                <div class="checkAnotnerLnk"> <a href="javascript:history.back()">Check fit for another vehicle <span class="fa fa-angle-right"></span></a> </div>
              </div>
              </c:if>
              <c:if test="${empty years and not empty categoryCode}">
              <div class="pull-right">
                <div class="checkAnotnerLnk"> <a href="<%= request.getContextPath() %>/c/${categoryCode}">Check vehicle fitment <span class="fa fa-angle-right"></span></a> </div>
              </div>
              </c:if>
            </div>
              <div class="clearfix productDescription  topMargn_25">
						${product.description}
					</div>
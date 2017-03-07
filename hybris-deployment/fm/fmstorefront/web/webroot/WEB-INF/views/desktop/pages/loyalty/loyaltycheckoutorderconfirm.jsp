<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<template:page pageTitle="${pageTitle}">
	<%-- <c:out value="<%=request.getContextPath()%>"/> --%>
	
	


<!-- MAIN CONTAINER-->
<!-- InstanceBeginEditable name="Page Content Section" -->

<section class="customBgBlock checkoutPage">
  <div class="container">
    <div class="row">
      <div class="col-xs-12 checkoutSteps">
        <ul class="nav nav-pills nav-justified thumbnail setup-panel">
          <li class="disabled"><a href="#step-1">Shipping Address</a><span class="chevron"></span></li>
          <li class="disabled"><a href="#step-2">Review & Place Order</a><span class="chevron"></span></li>
          <li class="active"><a href="#step-3">Order Confirmation</a></li>
        </ul>
      </div>
    </div>
  </div>
</section>
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
<section class="productDetailPage pageContet">
  <div class="container">
  
  <div class="clearfix bgwhite setup-content" id="step-3">
      <div class="col-lg-12 ">
        <ul class="printShareSaveLink pull-right">
<c:url value="/loyalty/checkout/loyaltyorderConfirmationExport" var="encodedUrl" />
          <li><a target="_BLANK" href="${encodedUrl}"><span class="fa fa-print"></span> Print</a></li>
          <li><a target="_BLANK" href="${encodedUrl}"><span class="fa fa-file-pdf-o orderPdf"></span> Save PDF</a></li>
        </ul>
      </div>
      <div class="confirmationB2C">
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <h2 class="text-uppercase">thank you for order</h2>
            <p>A copy of your order details has been sent to <strong>${CurrentCustomerData.email}</strong></p>
           <!-- <p>Would like to refer your friend?<strong>email@website.com</strong></p> -->
            <div class="ConfirmationHeadingRedPanel lftPad_10 clearfix"><span><span class="shipmentMethodBold">Order Confirmation #:</span> ${crmordercode}</span><span class="pull-right"><span class="shipmentMethodBold">Order Status</span>: Open</span></div>
            <div class="row">
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Shipping Details</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                  <p class="reviewPlaceOrderBold  text-capitalize">Shipping address</span></p>
                  <p class="text-uppercase">${shipToUnit.name}</p>
                  <p>${shipToAddress.line1}</p>
                  <p>${shipToAddress.line2}</p>
                  <p>${shipToAddress.phone},&nbsp;${shipToAddress.town},&nbsp;${shipToAddress.region.isocode}&nbsp;${shipToAddress.postalCode}&nbsp;${shipToAddress.country.isocode}</p>
                </div>
               
              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Redeem Points</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                <p class="reviewPlaceOrderBold text-capitalize">Points Redeemed</p>
                <p> <fmt:formatNumber type="number" value="${totalReedemPoints}" /></p>
              </div>
                
              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Available Points</div>
                </div>
                <div class="reviewFirstPanelMargin  lftPad_10">
                  <p class="reviewPlaceOrderBold text-capitalize">Remaining Points</span></p>
                  <p>  <fmt:formatNumber type="number" value="${TotalPoints - totalReedemPoints}" /></p>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="reviewPlaceOrderFirstPanel userTable">
                <div class="shipmentMethodSubHeadingFirst">
                  <div class="panel  panel-primary panel-frm">
                    <div class="panel-heading clickable">
                      <h3 class="panel-title text-uppercase">Shipment 1</h3>
                      <span class="pull-right "><i class="fa fa-minus"></i></span> </div>
                    <div class="panel-body rgtPad_0">
                      <div class="table-responsive userTable topMargn_20">
                        <table class="table tablesorter shipmentTable">
                          <thead>
                            <tr class="">
                              <th class="shipmentMethodTr text-capitalize col-md-6" colspan="2">Item details</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Points</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Quantity</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Total Points</th>
                            </tr>
                          </thead>
                          <tbody>
                          <c:forEach items="${cartData.entries}" var="entry">
                          
                          	<c:if test="${entry.entries ne null}">
                           <c:forEach items="${entry.entries}" var="variantentry">
                            <tr>
                             <td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
                              <td class="text-left col-md-5"> <div class="shoppingCartsubHead">
                                	<h4 class="fm_fntRed miniCartProdName"> 
                                		${variantentry.product.name}
                                	</h4>
                                	<div class="">
										<span><span class="">Product Code:</span>${variantentry.product.code}</span>
									</div>
                                </div>
                               </td>
                              <td class="text-center col-md-2"> <fmt:formatNumber type="number" value="${entry.product.loyaltyPoints}" /></td>
                              <td class="text-center col-md-2">${variantentry.quantity}</td>
                              <td class="text-center col-md-2"><fmt:formatNumber type="number" value="${entry.product.loyaltyPoints * variantentry.quantity}" /></td>
                            </tr>
                            </c:forEach>
                            </c:if>
                         
                           
                           	<c:if test="${entry.entries eq null}">
                           	<tr>
                             <td><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></td>
                              <td class="text-left col-md-5"> <div class="shoppingCartsubHead">
                                	<h4 class="fm_fntRed miniCartProdName"> 
                                		${entry.product.name}
                                	</h4>
                                	<div class="">
										<span><span class="">Product Code:</span>${entry.product.code}</span>
									</div>
                                 </div>
                               </td>
                              <td class="text-center col-md-2"> <fmt:formatNumber type="number" value="${entry.product.loyaltyPoints}" /></td>
                              <td class="text-center col-md-2">${entry.quantity}</td>
                              <td class="text-center col-md-2"> <fmt:formatNumber type="number" value="${entry.product.loyaltyPoints * entry.quantity}" /></td>
                            </tr>
                           
                           </c:if>
                           </c:forEach>
                            <tr>
                              <td colspan="2"></td>
                              <td colspan="2" class="text-right"><span class="shoppingSubTotal"> Total Redeemed Points</span></td>
                              <td><p  class="shoppingCartTotal text-center"> <fmt:formatNumber type="number" value="${totalReedemPoints}" /></p></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  
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
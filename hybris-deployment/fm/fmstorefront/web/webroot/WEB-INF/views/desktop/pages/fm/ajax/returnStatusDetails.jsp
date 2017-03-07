  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
   
  <div class="container invoiceContent">
    <div class="clearfix bgwhite col-lg-12">
      <div class="topMargn_10 pull-right"> <a class="t" href="#"><i class="fa fa-file-pdf-o orderPdf"></i> <span class="invoiceDownload text-capitalize rghtMrgn_5">Download</span></a> <a class="fa  fa-print orderPdf"></a> <span class="invoicePrint text-capitalize">Print</span> </div>
      <div class="row registerRow">
        <div class="">
          <div class="col-lg-5 col-md-5 col-sm-5 col-xs-12"><img itemprop="logo" src="images/FM_invoice.jpg" class="img-responsive" alt=""></div>
          <div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">
            <h3 class="text-uppercase">Invoice</h3>
          </div>
        </div>
      </div>
      <div class="clearfix">
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 rgtPad_0 invoiceContentFirstPanel">
          <div class="subTitle text-uppercase">
            <div class="clearfix">Billing address <!--:10013109<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
          </div>
          <div class="reviewFirstPanelMargin lftPad_10">
            <p class="text-uppercase">${InvoiceDetails.invoiceToAccount.address.addrName }</p>
            <p>${InvoiceDetails.invoiceToAccount.address.addrLine1 }</p>
            <p>${InvoiceDetails.invoiceToAccount.address.addrLine2 }</p>
            <p>${InvoiceDetails.invoiceToAccount.address.city }, ${InvoiceDetails.invoiceToAccount.address.stateOrProv } ${InvoiceDetails.invoiceToAccount.address.zipOrPostalCode }</p>
            <p>${InvoiceDetails.invoiceToAccount.address.country.countryName}</p>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 rgtPad_0 invoiceContentFirstPanel">
          <div class="subTitle text-uppercase">
            <div class="clearfix">Ship To<!--: 10014976<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
          </div>
          <div class="reviewFirstPanelMargin lftPad_10">
           <p class="text-uppercase">${InvoiceDetails.deliverToAccount.address.addrName }</p>
             <p>${InvoiceDetails.deliverToAccount.address.addrLine1 }</p>
            <p>${InvoiceDetails.deliverToAccount.address.addrLine2 }</p>
            <p>${InvoiceDetails.deliverToAccount.address.city }, ${InvoiceDetails.deliverToAccount.address.stateOrProv } ${InvoiceDetails.deliverToAccount.address.zipOrPostalCode }</p>
            <p>${InvoiceDetails.deliverToAccount.address.country.countryName}</p>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 rgtPad_0 invoiceContentFirstPanel">
          <div class="subTitle text-uppercase">
            <div class="clearfix">Shipping<!--<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
          </div>
          <div class="reviewFirstPanelMargin lftPad_10">
            <table>
              <tbody>
                <tr>
                  <td class="invoiceBold">Packing Slip:</td>
                  <td>${InvoiceDetails.packingSlip}</td>
                </tr>
                <tr>
                  <td class="invoiceBold">Shipping Date:</td>
                  <td><fmt:formatDate value="${InvoiceDetails.shippingDate}" pattern="MM/dd/yyyy"/></td>
                </tr>
                <tr>
                  <td class="invoiceBold">Shipped Via:</td>
                  <td>${InvoiceDetails.shippedVia}</td>
                </tr>
                <tr>
                  <td class="invoiceBold">Tracking #:</td>
                  <td><a href="#">${InvoiceDetails.trackingNumber}</a></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 rgtPad_0 invoiceContentFirstPanel">
          <div class="subTitle text-uppercase">
            <div class="clearfix">Invoice <!--<a href="#" class="pull-right fm_fnt_Blue text-capitalize">Edit</a>--></div>
          </div>
          <div class="reviewFirstPanelMargin lftPad_10">
            <table>
              <tbody>
                <tr>
                  <td class="invoiceBold">Invoice Date:</td>
                  <td><fmt:formatDate value="${InvoiceDetails.invoiceDate}" pattern="MM/dd/yyyy"/></td>
                </tr>
                <tr>
                  <td class="invoiceBold">Confirmation  #:</td>
                  <td>${InvoiceDetails.confirmationNumer}</td>
                </tr>
                <tr>
                  <td class="invoiceBold">Payment Method:</td>
                  <td>${InvoiceDetails.paymentMethod}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
          <h4 class="text-uppercase">Order Details</h4>
          <div class="table-responsive userTable">
            <table class="table tablesorter" id="myTable">
              <thead>
                <tr class="text-capitalize">
                  <th>Ordered</th>
                  <th>Shipped</th>
                  <th>Unit</th>
                  <th>Part Number</th>
                  <th>Product Description</th>
                  <th class="text-right">Unit Price</th>
                  <th class="text-right">Total</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${InvoiceDetails.orderDetails}" var="invDetails" varStatus="status">
                <tr>
                  <td class="invoiceBold">${invDetails.orderedQuantity}</td>
                  <td class="invoiceBold">${invDetails.shippedQuantity}</td>
                  <td class="invoiceBold">${invDetails.unit}</td>
                  <td class="invoiceBold">${invDetails.partNumber}</td>
                  <td class="invoiceBold">${invDetails.description}</td>
                  <td class="text-right">$${invDetails.unitPrice}</td>
                  <td class="invoiceBold text-right">$${invDetails.totalPrice}</td>
                </tr>
             </c:forEach>
              
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 invoiceSectionThree">
          <div class="checkoutSubTitle text-uppercase col-lg-9 col-md-9 col-sm-8 col-xs-12 invoiceComment"><!-- Comment:<span class="text-uppwercase">Nick/Emer/Grnd</span> --></div>
          <div class="col-lg-3 col-md-3 col-sm-4 col-xs-12">
            <table class="orderSummaryTable">
              <tbody>
                <tr>
                  <td class="text-left text-capitalize">subtotal</td>
                  <td class="text-right">$${InvoiceDetails.subTotal}</td>
                </tr>
                <%-- <tr class="fm_fntRed">
                  <td class="text-left text-capitalize">savings</td>
                  <td class="text-right">${InvoiceDetails.orderDetails}</td>
                </tr> --%>
                <tr>
                  <td class="text-left text-capitalize">Delivery Charges</td>
                  <td class="text-right">$${InvoiceDetails.deliveryCharges}</td>
                </tr>
                <tr>
                  <td class="text-left text-capitalize">Taxes</td>
                  <td class="text-right">$${InvoiceDetails.tax}</td>
                </tr>
                <tr class="estTotal">
                  <td class="text-left text-capitalize">Total</td>
                  <td class="text-right">$${InvoiceDetails.total}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
              
              
            

              
 
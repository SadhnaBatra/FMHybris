  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  <%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:url value="/invoice/fm-AppXtender/invoicePDF" var="AppXtenderUrl" />
  <c:url value="/invoice/invoice-header-export-excel"
							var="excelsheeturl" />
 <c:url value="/invoice/invoice-header-export-pdf"
							var="pdfurl" />	
 <c:url value="/invoice/invoicePDF" var="invoiceURL"/>
  <form class="orderStatusForm" method="POST">
                <div class="row">
                  <div class="form-group col-sm-3">
                    <input id="invoicePruchaseOrder_iv" class="form-control" type="text" placeholder="Purchase Order #">
                  </div>
                  <div class="form-group col-sm-3">
                    <input id="invoiceConfirmationOrder_iv" class="form-control" type="text" placeholder="Invoice #">
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-4" type="text" class="date-picker form-control" placeholder="Start Date" value="${sdate}"/>
                        <label for="date-picker-4" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                  <div class="form-group col-sm-2">
                    <div class="controls">
                      <div class="input-group">
                        <input id="date-picker-5" type="text" class="date-picker form-control" placeholder="End Date" value="${edate}"/>
                        <label for="date-picker-5" class="input-group-addon btn"><span class="glyphicon glyphicon-calendar"></span> </label>
                      </div>
                    </div>
                  </div>
                 <!-- <div class="form-group col-sm-2">
                    <select class="form-control" id="invoiceStatus11">
                      <option value="ALL">ALL</option>
                     <option value="IN_PROCESS">INPROGRESS</option>
                     <option value="SHIPPED">COMPLETE</option>
                    </select>
                  </div> -->
 		  <div class="form-group col-sm-2">
                    <select class="form-control" id="invoiceStatus">
                      <option value="ALL">ALL</option>
                     <option value="CREDITMEMO">Credit Memo</option>
                     <option value="INVOICE">Invoice</option>
                    </select>
                  </div>
                </div>
                <div class="row">
                  <div class="form-group col-sm-12">
                    <div class="pull-right">
                      <button type="button" class="btn btn-default btn-fmDefault search"  id="invoiceSearch" onclick="javascript:searchInvoice();">Search</button>
                      <button type="button" class="btn btn-default btn-fmDefault reset" id="invoiceReset"  onclick="javascript:resetInvoice();">Reset</button>
                    </div>
                  </div>
                </div>
                <div class="row">
        <!--          
	<div class="form-group col-sm-12">
                    <div class="pull-right exportButton">
                      <label class="text-uppercase">Export</label>
                      <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel">
                      <button type="button" class="btn fa fa-file-excel-o orderExcel"></button>
                      </span> <span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf">
                      <button type="button" class="btn fa fa-file-pdf-o orderPdf"></button>
                      </span> </div>
                  </div>  -->
			    <c:if test="${ not empty InvoiceHeader or not empty invoiceSearchValues}" >
					<div class="form-group col-sm-12">
					<div class="pull-right exportButton">
			                      <label class="text-uppercase">Export</label>
			 			<a class="btn fa fa-file-excel-o orderExcel" href="${excelsheeturl}"/> 
						<a class="btn fa fa-file-pdf-o orderPdf" href="${pdfurl}" target="_blank"/>
					</div>
					</div>
				</c:if>			                 
                </div>
              </form>
	      <c:if test="${ not empty InvoiceHeader or not empty invoiceSearchValues}" >
              <div class="invoiceTabTable">
                <div class="table-responsive userTable">
                  <table class="table tablesorter" id="myTable">
                    <thead>
                      <tr class="text-capitalize">
                        <th><a href="#" onclick="javascript:invoiceSortBy('InvoiceType')" >Invoice/credit memo </a></th>
                        <th><a href="#" onclick="javascript:invoiceSortBy('InvoiceNumber')" >Invoice#</a></th>
                        <th>Invoice to </th>
                        <th>Deliver to</th>
                        <th><a href="#" onclick="javascript:invoiceSortBy('InvoiceDate')" >Invoice Date </a></th>
                        <th>Invoice Amount </th>
                        <th><a href="#" onclick="javascript:invoiceSortBy('PO')" >PO#</a></th>
                        <!--  <th>Status</th> -->
                      </tr>
                    </thead>
                    <tbody>
                    
                      
	                    <c:forEach items="${invoiceSearchValues}" var="invoice" varStatus="status">
	                      <tr>
	                      
	                        			<td>${invoice.invoiceType.description}</td>
								<td><c:set var="b2BinvoiceDate" value="" /> 
									<%-- <c:choose>
									<c:when test="${userGroup eq 'FMB2BB'}">
										<a
											href="http://sfldmims498.federalmogul.com:8888/?appCode=INSF&invNum=${invoice.invoiceNumber }"
											onclick="window.open(this.href,'targetWindow','toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=SomeSize,height=SomeSize');return false;">
											${invoice.invoiceNumber }</a>
									</c:when>
									<c:otherwise>
										<a
											href="javascript:showInvoiceDetail('${invoice.invoiceNumber }')">${invoice.invoiceNumber }</a>
									</c:otherwise>
								</c:choose>
									
										<a
											href="http://sfldmims498.federalmogul.com:8888/?appCode=INSF&invNum=${invoice.invoiceNumber }"
											onclick="window.open(this.href,'targetWindow','toolbar=yes,location=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=SomeSize,height=SomeSize');return false;">
											${invoice.invoiceNumber }</a>  --%>
									<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR">
											
									<form target="_blank" action="${invoiceURL}" method="POST" id="${invoice.invoiceNumber }">
										<input type="hidden" name="invoiceNo" id="invoiceNo" value="${invoice.invoiceNumber }">
										<input type="hidden" name="invoiceDate" id="invoiceDate" value="<fmt:formatDate value="${invoice.invoiceDate}" pattern="yyyyMMdd"/>">
										<a href="#" onclick="document.getElementById('${invoice.invoiceNumber }').submit();">${invoice.invoiceNumber }</a>
									</form>
									
									<%-- <a href="${invoiceURL}?invoiceNo=${invoice.invoiceNumber }&invoiceDate=<fmt:formatDate value="${invoice.invoiceDate}" pattern="yyyyMMdd"/>"
											target="_blank" >${invoice.invoiceNumber }</a> --%>
									</sec:authorize>
									<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMBUVOR">
										<a
											href="javascript:showInvoiceDetail('${invoice.invoiceNumber }')">${invoice.invoiceNumber }</a>
									</sec:authorize>
								</td>
							
							<td>${invoice.billToAccount.accountCode}</td>
	                        <td>${invoice.shipToAccount.accountCode}</td>
	                        <td><fmt:formatDate value="${invoice.invoiceDate }" pattern="MM/dd/yyyy"/></td>
	                         <c:choose>
	                        <c:when test="${fn:contains(invoice.invoiceAmount.displayPrice, '-')}">
	                       	 <c:set var="displaySplitPrice" value="${fn:split(invoice.invoiceAmount.displayPrice, '-')}" />
								<c:if test="${not empty displaySplitPrice[0]}">
								<c:set var="displayPrice" value="${displaySplitPrice[0]}"></c:set>
								</c:if>
								  <td>$ (<fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${displayPrice}" />)</td>   
	                        </c:when>
	                        <c:otherwise> 
	                        	<td>$ <fmt:formatNumber minFractionDigits="2" maxFractionDigits="2" value="${invoice.invoiceAmount.displayPrice}" /></td>
	                        </c:otherwise>
	                        </c:choose>
	                         <td> ${invoice.customerPurchaseOrderNumber} </td>
	              
	                        <!-- <td><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to excel"><a href="#" class="fa fa-file-excel-o orderExcel"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Send me the information by email"> <a href="#" class="fa fa-envelope-o orderMail"></a></span><span class="tip" title="" data-placement="bottom" data-toggle="tooltip" data-original-title="Export to pdf"> <a href="#" class="fa fa-file-pdf-o orderPdf"></a></span></td> -->
	                      </tr>
                    	</c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              </c:if>

<script type='text/javascript'>
/*Table sorter */

$(document).ready(function() {
        $(".orderStatusTable #myTable").tablesorter({headers: {0: { sorter: false}, 1: { sorter: false},2: { sorter: false},3: { sorter: false}, 6: { sorter: false}, 7: { sorter: false}}});
		$(".invoiceTabTable #myTable").tablesorter();
		$(".backOrderTabTable #myTable").tablesorter();
    }
);
$('.date-picker').datepicker();
</script>
 

	<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>

	<c:if test="${empty displayIFrame}">
		<c:set var="displayIFrame" value="${false}" />
	</c:if>

	<div id="quick_fade"></div>
        <div id="quick_modal">
            <img id="loader" src="/fmstorefront/_ui/desktop/common/images/spinner.gif" />
        </div>
        <!--- Quick Order PANEL -->
        <div class="col-lg-4 b2b-home-pane">
          <div class="panel panel-default quickOrderPanel quickOrderPanelPartNumber">
            <div class="panel-body">
              <h2 class="panel-title text-uppercase quickOrderTitle"><span class="fa fa-bolt"></span> <span class="text-uppercase"><spring:theme code="B2BHomepage.quickOrder.title"/></span></h2>
              <p><spring:theme code="B2BHomepage.enterPart"/></p>
              <div class="row">
                <form class="quickOrderForm">
                  <div class="form-group">
                    <div class="col-xs-8">
            
                      <label for="partNumber" ><spring:theme code="B2BHomepage.partNumber"/></label>
                      <input id="hiddenPartFlag_0" class="hiddenPartFlag form-control" type="hidden" value="">
                      <input id="hiddenPartNumber_0" class="hiddenPartNumber form-control" type="hidden" value="">
                      <input id="partNumber_0" class="partNumber form-control" type="text" onchange="addtocart(this, ${displayIFrame});" placeholder="<spring:theme code="B2BHomepage.quickOrder.PartNo" text="Enter Part Number"/>">
                    </div>
                    <div class="col-xs-4 quantityColumn">
                      <label for="qty" ><spring:theme code="B2BHomepage.quantity"/></label>
                      <input id="hiddenQty_0" class="hiddenQty form-control" type="hidden" value="0">
                      <input id="qty_0" class="qty form-control" type="text" placeholder="0" onchange="addtocart(this, ${displayIFrame});" maxlength="5">
                    </div>
                    
                  </div>
                    <div class="form-group">
                  	<div id="div_0" class="col-xs-12">
                  		<div id="error_0" class="poerror"></div>
                  	</div>
                  	<div id="multi_0" class="col-xs-8 btmMrgn_14" style="display:none">
						<select id="multiMatch_0" class="form-control" style="width: 100%;" onchange="javascript:onMultiSelect(this)">
							
						</select> 
					</div>
                  </div>
                  <div class="form-group">
                    <div class="col-xs-8">
                      <input id="hiddenPartNumber_1" class="hiddenPartNumber form-control" type="hidden" value="">
                      <input id="hiddenPartFlag_1" class="hiddenPartNumber form-control" type="hidden" value="">
                      <input id="partNumber_1" class="partNumber form-control" type="text" onchange="addtocart(this, ${displayIFrame});" placeholder="Enter Part Number">
                    </div>
                    <div class="col-xs-4 quantityColumn">
                      <input id="hiddenQty_1" class="hiddenQty form-control" type="hidden" value="0">
                      <input id="qty_1" class="qty form-control" type="text" placeholder="0" onchange="addtocart(this, ${displayIFrame});"  maxlength="5">
                    </div>
                    
                  </div>
                     <div class="form-group">
                  	<div id="div_1" class="col-xs-12">
                  		<div id="error_1" class="poerror"></div>
                  	</div>
                  	<div id="multi_1" class="col-xs-8 btmMrgn_14" style="display:none">
						<select id="multiMatch_1" class="form-control" style="width: 100%;" onchange="javascript:onMultiSelect(this)">
							
						</select> 
					</div>
                  </div>
                  <div class="form-group">
                    <div class="col-xs-8">
                      <input id="hiddenPartNumber_2" class="hiddenPartNumber form-control" type="hidden" value="">
                      <input id="hiddenPartFlag_2" class="hiddenPartNumber form-control" type="hidden" value="">
                      <input id="partNumber_2" class="partNumber form-control" type="text" onchange="addtocart(this, ${displayIFrame});" placeholder="Enter Part Number">
                    </div>
                    <div class="col-xs-4 quantityColumn">
                       <input id="hiddenQty_2" class="hiddenQty form-control" type="hidden" value="0">
                      <input id="qty_2" class="qty form-control" type="text" placeholder="0" onchange="addtocart(this, ${displayIFrame});"  maxlength="5">
                    </div>
                  </div>
                  <div class="form-group">
                  	<div id="div_2" class="col-xs-12">
                  		<div id="error_2" class="poerror"></div>
                  	</div>
                  	<div id="multi_2" class="col-xs-8 btmMrgn_14" style="display:none">
						<select id="multiMatch_2" class="form-control" style="width: 100%;" onchange="javascript:onMultiSelect(this)">
							
						</select> 
					</div>
                  </div>
                  <div class="col-xs-12">
			<c:set var="credit" value="true"/>
			<c:set var="order" value="true"/>
					<c:if test="${creditBlock != null && creditBlock =='X' }" >
						<c:set var="credit" value="false"/>
					</c:if>
					<c:if test="${orderBlock != null && orderBlock == 'X' }" >
						<c:set var="order" value="false"/>
					</c:if>
			

                  <c:url value="/orderupload/quick-order" var="quickOrderUrl"/>
                    <div class="col-lg-5 reviewLnk rgtPad_0 lftPad_0">
						<a <c:if test="${displayIFrame}">target="_parent"</c:if> href="${quickOrderUrl}"> <span class="fa fa-plus"> </span> <spring:theme code="B2BHomepage.addMoreParts"/></a>

		  <br><span class="fa fa-table"></span><a class="" data-toggle="modal" href="#brandprefix"> Brand Prefix</a>
		  <!-- commenting this link as part of new requirement -->
		  <!--   <br><a title="Main DC Inventory" id="inventory" class="inventoryRow" href="#" onclick="CheckHomeInventory(this,'false');"><span class="fa fa-exchange"></span> Main DC Inventory </a>  -->		  
				 <!-- <br><a title="Local DC Inventory/Pick Up" id="tscinventory" class="tscinventoryRow" href="#" onclick="CheckHomeInventory(this,'true');"><span class="fa fa-exchange"></span> Local DC Inventory/Pick Up </a> -->
		  </div>
				<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="brandprefix" class="modal fade shipToModel in fixedModalHeight" style="display: none;">
				<federalmogul:brandprefix pageType="quickOrder"/>				
				</div>
						<%--Inventory POPUP check --%>
				<div id="inventoryPopup" class="inventoryPopup">    
				 
				</div>
                    <c:url value="/cart" var="cartUrl"/>
		    		<c:if test="${order == 'true'}">
                    	<div class="pull-right"> <a href="${cartUrl }" onclick="return ValidateForm();" id="QuickOrderButton0"  class="btn  btn-sm btn-fmDefault disabled" <c:if test="${displayIFrame}">target="_parent"</c:if>>Proceed to Checkout</a></div>
		   			</c:if>
		   			   <br></br>
		   			   <div class="pull-right"> <a href="#" onclick="CheckHomeInventory(this,'false');" id="inventory0"  class="inventoryRow btn  btn-sm btn-fmDefault">Check Inventory</a></div>
		   			   
		   			<!-- <div class="reviewLnk rgtPad_0 lftPad_0">
		   				<br>
		   				<br>
					 	<a title="Local DC Inventory/Pick Up" id="tscinventory" class="tscinventoryRow" href="#" onclick="CheckHomeInventory(this,'true');"><span class="fa fa-exchange"></span> Local DC Inventory/Pick Up </a>
					</div> -->
                  </div>
        <!-- Removing local DC inventory link -->          
		<!-- <div class="col-xs-12 reviewLnk">
			<a title="Local DC Inventory/Pick Up" id="tscinventory" class="tscinventoryRow" href="#" onclick="CheckHomeInventory(this,'true');"><span class="fa fa-exchange"></span> Local DC Inventory </a>

		</div> -->
                </form>
              </div>
            </div>
          </div>
        </div>

 <script type="text/javascript">
function ValidateForm() {
     	var partNumber1 = document.getElementById("partNumber_0").value;
     	var partNumber2 = document.getElementById("partNumber_1").value;
     	var partNumber3 = document.getElementById("partNumber_2").value;
    	var qty1 = document.getElementById("qty_0").value;
    	var qty2 = document.getElementById("qty_1").value;
    	var qty3 = document.getElementById("qty_2").value;
     	if((partNumber1 == null || partNumber1 =="") && (partNumber2 == null || partNumber2 =="") && (partNumber3 == null || partNumber3 ==""))
     		{
     		alert("Please enter Part Number and Quantity");
     		return false;
     		}
     	else if((qty1 ==null || qty1 =="") && (qty2 ==null || qty2 =="") && (qty3 ==null || qty3 =="")){
     		alert("Please enter Part Number and Quantity");
     		return false;
     	}
	else{
     		
     		return true;
     	}

}
</script>

<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>

<c:if test="${empty displayIFrame}">
	<c:set var="displayIFrame" value="${false}" />
</c:if>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">

	<script type="text/javascript">
		function validateFile(){
			var quickfilename = quickload.quickOrderFile.value;
			if(quickfilename.length ==  0 ){
				alert("Please choose a file to upload");
				return false;
			} else {
				quickload.submit();		
				return true;
			}	
		}	
	</script>

        <div class="quickOrderPagePanel rightHandPanel clearfix">
          <h2 class="text-uppercase"><span class="fa fa-bolt fm_fntRed"></span> Quick Order</h2>
            <section class="col-lg-12 clearfix">
          <div class="row quickInvoice checkAnotnerLnk">
          <spring:theme code="text.quickOrder.headline"/><a href="/fmstorefront/federalmogul/en/USD/">Upload Order</a> functionality.
            	<!-- <div class="well well-small">
                <p class="	btmMrgn_14">Note: Please go ahead and upload a file or make an entries in following table.</p>
                <c:url value="orderupload/upload-order"  var="quickURL"/>
                <form:form method="POST" id="quickload" commandName="quickOrderUpload"  action="${request.contextPath}/orderupload/upload-order-file" enctype="multipart/form-data"> 
                    <div class="">
                    <label for="uploadFile">Choose a File</label>
                    <div class="clearfix">
                    <div class="input-group  pull-left width375">
                    
                      <input type="text" readonly="" class="form-control  ">
                      <span class="input-group-btn text-right"> <span class="btn btn-primary btn-file orderUploadButton"> <span class="fa fa-arrow-circle-o-up "></span>
                       <form:input id="quickOrderFile" type="file" class="" path="quickOrderFile" onchange="ValidateFileExtension(this);"/>
                      </span> </span> </div>
                      <div class="pull-left lftMrgn_20 topMargn_2"> 
                      	<button type="submit" onclick="return validateFile();" class="btn  btn-sm btn-fmDefault">Import Parts</button>
                      	<div class="reviewLnk "> <a href="/medias/quickorder-textfile.txt?context=bWFzdGVyfHJvb3R8NDR8dGV4dC9wbGFpbnxoMjIvaDE5Lzg4NjYwODU3MzIzODIudHh0fGE4NWVlNTM4MWIyNzliODVlNzZmNTIxYTZmMDhkNjk2N2MzM2UyMzg1MzJlN2ViNDY3MmY5ZjE3M2IzYjg5MDU&attachment=true">Sample file format &nbsp; <span class="fa fa-chevron-right"> </span></a>
                		</div>
                      </div>
                      
                      </div>
                  </div>
                </form:form>
               </div> --> 
	       </div>
            <div class="col-lg-12 reviewLnk lftMrgn_28 text-right"><span class="fa fa-table"></span> <a data-toggle="modal" href="#brandprefix">Brand Prefix</a></div> 
			<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="brandprefix" class="modal fade shipToModel in fixedModalHeight " style="display: none;">
				<federalmogul:brandprefix pageType="quickOrder"/>
			</div>
			 
			 <%--Inventory POPUP check --%>
				<div id="inventoryPopup" class="inventoryPopup">
					   
				      
				</div>
			           
            <div class="row bottomForm">
              <div class="visible-lg visible-md visible-sm ">
                <div class="col-lg-5 col-md-7 col-xs-6">
                  <form class="form-horizontal clearfix">
                    <div class="form-group pull-left">
                      <label for="page" class="control-label visible-lg-inline visible-md-inline visible-sm-inline">Add</label>
                      <input type="text" value="1" id="addrowtoptext" maxlength="3" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45 addpageinput">
                       
                    </div>
                    <div class="pull-left lftMrgn_20">
                    <button type="button" onclick="javascript:addTopRows();" class="btn btn-fmDefault ">more rows</button>
			<a href="${request.contextPath}/my-fmaccount/reset-order" class="btn btn-fmDefault ">clear cart</a>
                   </div>
                  </form>
                </div>
                
                <div class="col-lg-7 col-md-3 col-xs-4 text-right btnPanel">
                  <div class="">
                  <c:url value="/cart" var="cartUrl"/>
                     <a href="#" onclick="CheckHomeInventory(this,'false');" id="inventory0"  class="inventoryRow btn  btn-sm btn-fmDefault"><!-- <span class="fa fa-exchange"></span> -->Check Inventory</a>
                     <a href="${cartUrl }" onclick="return ValidateForm();" id="QuickOrderButton0" class="btn  btn-sm btn-fmDefault ${not empty  cartData.entries ? '' : 'disabled'}">Proceed To Checkout</a> 
                   
                    <!--<button class="btn btn-fmDefault visible-lg-inline-block lftMrgn_5">Checkout</button> -->
                  </div>
                </div>
              </div>
              <div class="visible-xs ">
                <div class="col-lg-4 col-md-4 col-xs-2">
                  <button class="fa fa-gear" value="Fiter"></button>
                </div>
                <div class="col-lg-4 col-md-4 col-xs-3">
                  <select id="pageNumber" class="form-control">
                    <option> View </option>
                    <option>9</option>
                    <option>18</option>
                    <option>27</option>
                    <option>36</option>
                    <option>45</option>
                    <option>54</option>
                    <option>64</option>
                    <option>72</option>
                    <option>81</option>
                  </select>
                </div>
                <div class="col-lg-4 col-md-4 col-xs-7 text-right">
                  <button class="fa fa-chevron-left" type="button"></button>
                  Page 2 of 5
                  <button class="fa fa-chevron-right" type="button"></button>
                </div>
              </div>
            </div>
          </section>
          <div class="table-responsive col-lg-12 userTable">
            <table class="table" id="quickOrderTable">
              <thead>
                <tr>
                  <th class="col-lg-4">Items</th>
                  <th class="col-lg-1">Quantity</th>
                  <th class="col-lg-4">Description</th>
               	  <th class="text-right col-lg-2">Unit Price</th>
                  <th class="text-right col-lg-1">Actions</th>
                </tr>
              </thead>
              <tbody>
              
              <c:if test="${not empty  cartData.entries }" >
              	<c:forEach items="${cartData.entries}" var="entry">
	                <tr id="tr_${entry.entryNumber}">
	                  <%-- <td class="nameCol itemsCol">
	                  	<div class="">
	                  	<div class="checkStatus ${entry.errorMessage == null ? 'show' : 'hide'}">
		                  	<span class="fa fa-check-circle fa-2x"></span>
		                </div>
		                <div class="exclamationStatus ${entry.errorMessage != null ? 'show' : 'hide'}">
		                  	<span class="fa fa-exclamation-circle fa-2x"></span>
		                </div>
		               <div class="warningStatus ${entry.errorMessage != null ? 'hide' : 'hide'}">
		                   	<span class="fa fa-warning fa-2x"></span>
	                   	</div>
	                   	<input type="text" value="${entry.product.code}" id="partNumber_${entry.entryNumber }" placeholder="Enter Part Number" class="partnumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165 ">
	                    </div>
	                 </td> --%>
	                 <td class="text-left">
	                 <input id="hiddenPartNumber_${entry.entryNumber }" class="hiddenPartNumber form-control" type="hidden" value="${entry.product.partNumber}">
	                 <input id="hiddenPartFlag_${entry.entryNumber }" class="hiddenPartFlag form-control" type="hidden" value="${entry.product.partFlag}">
	                 <input type="text" value="${entry.product.partNumber}" onchange="addtocart(this, ${displayIFrame});" id="partNumber_${entry.entryNumber }" placeholder="Enter Part Number" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165 "></td>
	                 <td class="text-center">
	                 <input id="hiddenQty_${entry.entryNumber}" class="hiddenQty form-control" type="hidden" value="${entry.quantity}">
	                 <input type="text" id="qty_${entry.entryNumber}" value="${entry.quantity}" onchange="addtocart(this, ${displayIFrame});" class="qty form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td>
	                 <td class="descCol">${entry.product.name} </td>
	                 <td class="text-right"><h4 class="DINWebBold"><format:price priceData="${entry.basePrice}" displayFreeForZero="true"/></h4></td>
	                 <td class="text-right"><div><a id="delete_${entry.entryNumber}" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td>
	                </tr>
	                <c:if test="${entry.errorMessage != null}" >
		                <tr id="err_${entry.entryNumber}">
		                	<td colSpan="5">
		                		<c:if test="${fn:contains(entry.errorMessage, '@')}" >
		                			<c:set var="errorMSG" value="${fn:split(entry.errorMessage , '@')}" />
		                			<c:set var="errMSG" value="${fn:split(errorMSG[0] , '~')}" />
		                		</c:if>
		                		<c:if test="${!fn:contains(entry.errorMessage, '@')}" >
		                			<c:set var="errMSG" value="${fn:split(entry.errorMessage , '~')}" />
		                		</c:if>
		                		<div class="errorMSG">
		                     		<span class="" style="color:red">${errMSG[0]}</span>
		                        		<c:if test="${not empty errMSG[1] && errMSG[0] != errMSG[1]}">
		                        			</br>
		                        			<span class="" style="color:red">${errMSG[1]}</span>
		                        		</c:if>
		                        </div>
		                        <c:if test="${entry.multiSelect != null}" > 
			                        <div id="multi_${entry.entryNumber}" class="col-lg-5" >
				                        <select id="multiMatch_${entry.entryNumber}" class="form-control" style="width: 100%;" onchange="javascript:onMultiSelect(this)">
											<option value="choose">Choose</option>
											<c:forEach items="${entry.multiSelect}" var="map" >
												<c:set value="${map.value}" var="item"/>
												<c:set var="optVal" value="${item.partNumber}~${item.productFlag}~${item.brandState }"/>
												<option value="${optVal}">${item.description }</option>
											</c:forEach>
										</select> 
			          				</div>
			          			 </c:if> 
	                        </td>
		                </tr>
	                 </c:if>
	                 <c:if test="${entry.errorMessage == null}" >
	                 	<tr >
                			<td colSpan="5" class="quicOrderPoerrorTd ">
                				<div id="err_${entry.entryNumber}" style="display:none" class="topMargn_20 ">
                  					<div id="error_${entry.entryNumber}" class="poerror quicOrderPoerror_show"></div>
                  				</div>
                  				<div id="multi_${entry.entryNumber}"  style="display: none;">
				                        <select id="multiMatch_${entry.entryNumber}" class="form-control" style="width: 100%;" onchange="javascript:onMultiSelect(this)">
											<option value="choose">Choose</option>
											<c:forEach items="${entry.multiSelect}" var="map" >
												<c:set value="${map.value}" var="item"/>
												<c:set var="optVal" value="${item.partNumber}~${item.productFlag}~${item.brandState }"/>
												<option value="${optVal}">${item.description }</option>
											</c:forEach>
										</select> 
			          				</div>
                   	 		</td>
		           		</tr>
	                 </c:if>
                	</c:forEach> 
                	
                	<!-- Added for FAL 41 - begin  -->
						<c:set var="cartDataLength" value="${cartData.entries.size()}" />
						<c:if test="${cartDataLength < 5}">
							<c:forEach var="i" begin="${cartDataLength}" end="4">
								<tr id="tr_${i}">
									<td class="nameCol itemsCol">
										<div class="">
											<div class="checkStatus hide">
												<span class="fa fa-check-circle fa-2x"></span>
											</div>
											<div class="exclamationStatus hide">
												<span class="fa fa-exclamation-circle fa-2x"></span>
											</div>
											<div class="warningStatus hide">
												<span class="fa fa-warning fa-2x"></span>
											</div>
											<input id="hiddenPartNumber_${i}"
												class="hiddenPartNumber form-control" type="hidden" value="">
											<input id="hiddenPartFlag_${i}"
												class="hiddenPartFlag form-control" type="hidden" value="">
											<input type="text" id="partNumber_${i}"
												placeholder="Enter Part Number" onchange="addtocart(this, ${displayIFrame});"
												class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165">
											<div class="searchStatus hide">
												<span class="fa fa-check-circle"></span>
											</div>
											<!-- <div style="display: none;" id="err_1" class="topMargn_20">
	                  <div class="" id="div_1">                  	
	               	 <div class="poerror_show topMargn_25" id="error_1"></div>	                
 					<div style="display: none;" id="multi_1" class="topMargn_10">
						<select id="multiMatch_1" class="form-control width165" style="display: none;" onchange="javascript:onMultiSelect(this)"></select> 
					</div>
					</div>
	                </div> -->
									</td>
									<td class="text-center"><input id="hiddenQty_${i}"
										class="hiddenQty form-control" type="hidden" value="0">
										<input type="text" id="qty_${i}" onchange="addtocart(this, ${displayIFrame});"
										placeholder="0"
										class="qty form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td>
									<td class="descCol"><div id="desc_${i}"></div></td>
									<td class="text-right"><h4 class="DINWebBold">
											<div id="price_${i}"></div>
										</h4></td>
										
									<!-- We don't need check inventory per line item -->	
									 <td class="text-right"><div>
											<a id="delete_${i}" class="deleteRow" href="#"
												onclick="RemoveProduct(this);"><span
												class="fa fa-trash-o fa-fw fm_fntRed"></span></a>
										</div></td> 
								</tr>
								<tr>
									<td colspan="5" class="quicOrderPoerrorTd"><div
											style="display: none;" id="err_${i}" class="topMargn_20 ">
											<div class="" id="div_${i}">
												<div class="poerror_show topMargn_25 quicOrderPoerror_show"
													id="error_${i}"></div>
												<div style="display: none;" id="multi_${i}"
													class="topMargn_10">
													<select id="multiMatch_${i}" class="form-control width165"
														style="display: none;"
														onchange="javascript:onMultiSelect(this)"></select>
												</div>
											</div>
										</div></td>
								</tr>
							</c:forEach>
						</c:if>
					<!-- Added for FAL 41 - end  -->
					</c:if>
                <c:if test="${empty cartData.entries}" >
                	<tr id="tr_0">
	                  <td class="nameCol itemsCol">
	                  	<div class="">
	                  	<div class="checkStatus hide">
		                  	<span class="fa fa-check-circle fa-2x"></span>
		                </div>
		                <div class="exclamationStatus hide">
		                  	<span class="fa fa-exclamation-circle fa-2x"></span>
		                </div>
		               <div class="warningStatus hide">
		                   	<span class="fa fa-warning fa-2x"></span>
	                   	</div>
	                   	<input id="hiddenPartNumber_0" class="hiddenPartNumber form-control" type="hidden" value="">
	                   	  <input id="hiddenPartFlag_0" class="hiddenPartFlag form-control" type="hidden" value="">
	                    <input type="text" id="partNumber_0" placeholder="Enter Part Number" onchange="addtocart(this, ${displayIFrame});" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165">
	                    <div class="searchStatus hide"><span class="fa fa-check-circle"></span></div>
	                    
	                      <!-- <div style="display: none;" id="err_5" class="topMargn_20">
	                  <div class="" id="div_5">                  	
	               	 <div class="poerror_show topMargn_25" id="error_5"></div>	                
 					<div style="display: none;" id="multi_5" class="topMargn_10">
						<select id="multiMatch_5" class="form-control width165" style="display: none;" onchange="javascript:onMultiSelect(this)"></select> 
					</div>
					</div>
	                </div> -->
	                 </td>
	                 <td class="text-center">
	                 <input id="hiddenQty_0" class="hiddenQty form-control" type="hidden" value="0">
	                 <input type="text" id="qty_0" onchange="addtocart(this, ${displayIFrame});" placeholder="0"  class="qty form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td>
	                 <td class="descCol"><div id="desc_0"></div> </td>
	                 <td class="text-right"><h4 class="DINWebBold"><div id="price_0"></div></h4></td>
	                 <td class="text-right"><div><a title="Check Inventory" id="inventory_0" class="inventoryRow deleteRow" href="#" onclick="CheckInventory(this);"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a><a id="delete_0" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td>
	                </tr>
	                	<tr>
							<td colspan="5" class="quicOrderPoerrorTd"><div
									style="display: none;" id="err_0"
									class="topMargn_20 ">
									<div class="" id="div_0">
										<div class="poerror_show topMargn_25 quicOrderPoerror_show" id="error_0"></div>
										<div style="display: none;" id="multi_0" class="topMargn_10">
											<select id="multiMatch_0" class="form-control width165"
												style="display: none;"
												onchange="javascript:onMultiSelect(this)"></select>
										</div>
									</div>
								</div></td>
						</tr>
	                <tr id="tr_1">
	                  <td class="nameCol itemsCol">
	                  	<div class="">
	                  	<div class="checkStatus hide">
		                  	<span class="fa fa-check-circle fa-2x"></span>
		                </div>
		                <div class="exclamationStatus hide">
		                  	<span class="fa fa-exclamation-circle fa-2x"></span>
		                </div>
		               <div class="warningStatus hide">
		                   	<span class="fa fa-warning fa-2x"></span>
	                   	</div>
	                   	<input id="hiddenPartNumber_1" class="hiddenPartNumber form-control" type="hidden" value="">
	                    <input id="hiddenPartFlag_1" class="hiddenPartFlag form-control" type="hidden" value="">
	                    <input type="text" id="partNumber_1" placeholder="Enter Part Number" onchange="addtocart(this, ${displayIFrame});" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165">
	                    <div class="searchStatus hide"><span class="fa fa-check-circle"></span></div>
	                    <!-- <div style="display: none;" id="err_1" class="topMargn_20">
	                  <div class="" id="div_1">                  	
	               	 <div class="poerror_show topMargn_25" id="error_1"></div>	                
 					<div style="display: none;" id="multi_1" class="topMargn_10">
						<select id="multiMatch_1" class="form-control width165" style="display: none;" onchange="javascript:onMultiSelect(this)"></select> 
					</div>
					</div>
	                </div> -->
	                 </td>
	                 <td class="text-center">
	                 <input id="hiddenQty_1" class="hiddenQty form-control" type="hidden" value="0">
	                 <input type="text" id="qty_1" onchange="addtocart(this, ${displayIFrame});" placeholder="0"  class="qty form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td>
	                 <td class="descCol"><div id="desc_1"></div> </td>
	                 <td class="text-right"><h4 class="DINWebBold"><div id="price_1"></div></h4></td>
	                 <td class="text-right"><div><a title="Check Inventory" id="inventory_1" class="inventoryRow deleteRow" href="#" onclick="CheckInventory(this);"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a><a id="delete_1" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td>
	                </tr>
						<tr>
							<td colspan="5" class="quicOrderPoerrorTd"><div
									style="display: none;" id="err_1"
									class="topMargn_20 ">
									<div class="" id="div_1">
										<div class="poerror_show topMargn_25 quicOrderPoerror_show" id="error_1"></div>
										<div style="display: none;" id="multi_1" class="topMargn_10">
											<select id="multiMatch_1" class="form-control width165"
												style="display: none;"
												onchange="javascript:onMultiSelect(this)"></select>
										</div>
									</div>
								</div></td>
						</tr>
						
	                <tr id="tr_2">
	                  <td class="nameCol itemsCol">
	                  	<div class="">
	                  	<div class="checkStatus hide">
		                  	<span class="fa fa-check-circle fa-2x"></span>
		                </div>
		                <div class="exclamationStatus hide">
		                  	<span class="fa fa-exclamation-circle fa-2x"></span>
		                </div>
		               <div class="warningStatus hide">
		                   	<span class="fa fa-warning fa-2x"></span>
	                   	</div>
	                   	<input id="hiddenPartNumber_2" class="hiddenPartNumber form-control" type="hidden" value="">
	                   	  <input id="hiddenPartFlag_2" class="hiddenPartFlag form-control" type="hidden" value="">
	                    <input type="text" id="partNumber_2" placeholder="Enter Part Number" onchange="addtocart(this, ${displayIFrame});" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165">
	                    <div class="searchStatus hide"><span class="fa fa-check-circle"></span></div>
	                     <!-- <div style="display: none;" id="err_2" class="topMargn_20">
	                  <div class="" id="div_2">                  	
	               	 <div class="poerror_show topMargn_25" id="error_2"></div>	                
 					<div style="display: none;" id="multi_2" class="topMargn_10">
						<select id="multiMatch_2" class="form-control width165" style="display: none;" onchange="javascript:onMultiSelect(this)"></select> 
					</div>
					</div>
	                </div> -->
	                 </td>
	                 <td class="text-center">
	                 <input id="hiddenQty_2" class="hiddenQty form-control" type="hidden" value="0">
	                 <input type="text" id="qty_2" onchange="addtocart(this, ${displayIFrame});" placeholder="0"  class="qty form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td>
	                 <td class="descCol"><div id="desc_2"></div> </td>
	                 <td class="text-right"><h4 class="DINWebBold"><div id="price_2"></div></h4></td>
	                 <td class="text-right"><div><a title="Check Inventory" id="inventory_2" class="inventoryRow deleteRow" href="#" onclick="CheckInventory(this);"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a><a id="delete_2" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td>
	                </tr>
	                <tr>
							<td colspan="5" class="quicOrderPoerrorTd"><div
									style="display: none;" id="err_2"
									class="topMargn_20 ">
									<div class="" id="div_2">
										<div class="poerror_show topMargn_25 quicOrderPoerror_show" id="error_2"></div>
										<div style="display: none;" id="multi_2" class="topMargn_10">
											<select id="multiMatch_2" class="form-control width165"
												style="display: none;"
												onchange="javascript:onMultiSelect(this)"></select>
										</div>
									</div>
								</div></td>
						</tr>
	            
	               <tr id="tr_3">
	                  <td class="nameCol itemsCol">
	                  	<div class="">
	                  	<div class="checkStatus hide">
		                  	<span class="fa fa-check-circle fa-2x"></span>
		                </div>
		                <div class="exclamationStatus hide">
		                  	<span class="fa fa-exclamation-circle fa-2x"></span>
		                </div>
		               <div class="warningStatus hide">
		                   	<span class="fa fa-warning fa-2x"></span>
	                   	</div>
	                   	<input id="hiddenPartNumber_3" class="hiddenPartNumber form-control" type="hidden" value="">
	                   	  <input id="hiddenPartFlag_3" class="hiddenPartFlag form-control" type="hidden" value="">
	                    <input type="text" id="partNumber_3" placeholder="Enter Part Number" onchange="addtocart(this, ${displayIFrame});" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165">
	                    <div class="searchStatus hide"><span class="fa fa-check-circle"></span></div>
	                    
	                 </td>
	                 <td class="text-center">
	                 <input id="hiddenQty_3" class="hiddenQty form-control" type="hidden" value="0">
	                 <input type="text" id="qty_3" onchange="addtocart(this, ${displayIFrame});" placeholder="0"  class="qty form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td>
	                 <td class="descCol"><div id="desc_3"></div> </td>
	                 <td class="text-right"><h4 class="DINWebBold"><div id="price_3"></div></h4></td>
	                 <td class="text-right"><div><a title="Check Inventory" id="inventory_3" class="inventoryRow deleteRow" href="#" onclick="CheckInventory(this);"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a><a id="delete_3" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td>
	                </tr>
	                <tr>
							<td colspan="5" class="quicOrderPoerrorTd"><div
									style="display: none;" id="err_3"
									class="topMargn_20 ">
									<div class="" id="div_3">
										<div class="poerror_show topMargn_25 quicOrderPoerror_show" id="error_3"></div>
										<div style="display: none;" id="multi_3" class="topMargn_10">
											<select id="multiMatch_3" class="form-control width165"
												style="display: none;"
												onchange="javascript:onMultiSelect(this)"></select>
										</div>
									</div>
								</div></td>
						</tr>
	                
	               <tr id="tr_4">
	                  <td class="nameCol itemsCol">
	                  	<div class="">
	                  	<div class="checkStatus hide">
		                  	<span class="fa fa-check-circle fa-2x"></span>
		                </div>
		                <div class="exclamationStatus hide">
		                  	<span class="fa fa-exclamation-circle fa-2x"></span>
		                </div>
		               <div class="warningStatus hide">
		                   	<span class="fa fa-warning fa-2x"></span>
	                   	</div>
	                   	<input id="hiddenPartNumber_4" class="hiddenPartNumber form-control" type="hidden" value="">
	                   	  <input id="hiddenPartFlag_4" class="hiddenPartFlag form-control" type="hidden" value="">
	                    <input type="text" id="partNumber_4" placeholder="Enter Part Number" onchange="addtocart(this, ${displayIFrame});" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165">
	                    <div class="searchStatus hide"><span class="fa fa-check-circle"></span></div>
	                    
	                 </td>
	                 <td class="text-center">
	                 <input id="hiddenQty_4" class="hiddenQty form-control" type="hidden" value="0">
	                 <input type="text" id="qty_4" onchange="addtocart(this, ${displayIFrame});" placeholder="0"  class="qty form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td>
	                 <td class="descCol"><div id="desc_4"></div> </td>
	                 <td class="text-right"><h4 class="DINWebBold"><div id="price_4"></div></h4></td>
	                 <td class="text-right"><div><a title="Check Inventory" id="inventory_4" class="inventoryRow deleteRow" href="#" onclick="CheckInventory(this);"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a><a id="delete_4" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td>
	                </tr>
	                <tr>
							<td colspan="5" class="quicOrderPoerrorTd"><div
									style="display: none;" id="err_4"
									class="topMargn_20 ">
									<div class="" id="div_4">
										<div class="poerror_show topMargn_25 quicOrderPoerror_show" id="error_4"></div>
										<div style="display: none;" id="multi_4" class="topMargn_10">
											<select id="multiMatch_4" class="form-control width165"
												style="display: none;"
												onchange="javascript:onMultiSelect(this)"></select>
										</div>
									</div>
								</div></td>
						</tr>
                </c:if>
               
              </tbody>
            </table>
          </div>
          <section class="col-lg-12 clearfix">
            <div class="row bottomForm">
              <div class="visible-lg visible-md visible-sm ">
                <div class="col-lg-5 col-md-7 col-xs-6">
                  <form class="form-horizontal clearfix">
                    <div class="form-group pull-left">
                      <label for="page" class="control-label visible-lg-inline visible-md-inline visible-sm-inline">Add</label>
                      <input type="text" value="1" id="addrowbuttomtext" maxlength="3" class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45 addpageinput">
                       
                    </div>
                    <div class="pull-left lftMrgn_20">
                     <button type="button" onclick="javascript:addButtomRows();" class="btn btn-fmDefault  ">more rows</button>
			<a href="${request.contextPath}/my-fmaccount/reset-order" class="btn btn-fmDefault ">clear cart</a>
                   </div>
                  </form>
                </div>
               
                <div class="col-lg-7 col-md-3 col-xs-4 text-right btnPanel">
                  <div class="">
                    <c:url value="/cart" var="cartUrl"/>
                    <a href="#" onclick="CheckHomeInventory(this,'false');" id="inventory1"  class="inventoryRow btn  btn-sm btn-fmDefault">Check Inventory</a>
                  	<a href="${cartUrl }" onclick="return ValidateForm();" id="QuickOrderButton1" class="btn  btn-sm btn-fmDefault ${not empty  cartData.entries ? '' : 'disabled'}">Proceed To Checkout</a>
                   <!-- <button class="btn btn-fmDefault visible-lg-inline-block lftMrgn_5">Checkout</button>-->
                  </div>
                </div>
              </div>
              <div class="visible-xs ">
                <div class="col-lg-4 col-md-4 col-xs-2">
                  <button class="fa fa-gear" value="Fiter"></button>
                </div>
                <div class="col-lg-4 col-md-4 col-xs-3">
                  <select id="pageNumber" class="form-control">
                    <option> View </option>
                    <option>9</option>
                    <option>18</option>
                    <option>27</option>
                    <option>36</option>
                    <option>45</option>
                    <option>54</option>
                    <option>64</option>
                    <option>72</option>
                    <option>81</option>
                  </select>
                </div>
                <div class="col-lg-4 col-md-4 col-xs-7 text-right">
                  <button class="fa fa-chevron-left" type="button"></button>
                  Page 2 of 5
                  <button class="fa fa-chevron-right" type="button"></button>
                </div>
              </div>
            </div>

		<div class="panel  panel-primary panel-frm">
				<div class="panel-heading clickable panel-collapsed">
					<h3 class="panel-title text-uppercase">
						Bulk Upload<span class="text-capitalize"></span>
					</h3>
					<span class="pull-right shipmentMethodPanelSpan" style="padding-top: 0px"><i
						class="fa fa-plus"></i></span>
				</div>
				<div class="panel-body" style="display: none;">

					<div class="well well-small">
						<p class="	btmMrgn_14">Upload a text file of part numbers to expedite entry.</p>
						<c:url value="orderupload/upload-order" var="quickURL" />
						<form:form method="POST" id="quickload"
							commandName="quickOrderUpload"
							action="${request.contextPath}/orderupload/upload-order-file"
							enctype="multipart/form-data">
							<div class="">
								<label for="uploadFile">Choose a File</label>
								<div class="clearfix">
									<div class="input-group  pull-left width375">

										<input type="text" readonly="" class="form-control  ">
										<span class="input-group-btn text-right"> <span
											class="btn btn-primary btn-file orderUploadButton"> <span
												class="fa fa-arrow-circle-o-up "></span> <form:input
													id="quickOrderFile" type="file" class=""
													path="quickOrderFile"
													onchange="ValidateFileExtension(this);" />
										</span>
										</span>
									</div>
									<div class="pull-left lftMrgn_20 topMargn_2">
										<button type="submit" onclick="return validateFile();"
											class="btn  btn-sm btn-fmDefault">Import Parts</button>
										<div class="reviewLnk ">
											<a
												href="/medias/quickorder-textfile.txt?context=bWFzdGVyfHJvb3R8NDR8dGV4dC9wbGFpbnxoMWQvaDFiLzg4OTk1Nzg3ODk5MTgudHh0fGVmOTQ2ZDM5ZGYwY2YzYzRjZGJmNWViOTJkNjY5MWZjZWMxNDk1N2QzMWVmNTMxMjVkZjEyZjEwMjE1YWU3ZjQ&attachment=true" target="_blank">Sample
												file format &nbsp; <span class="fa fa-chevron-right">
											</span>
											</a>
										</div>
									</div>

								</div>
							</div>
						</form:form>
					</div>
				</div>
			</div>
          </section>
        </div>
      </div>



<script>
function ValidateForm() {



 var flag = false;

var table = document.getElementById("quickOrderTable");
//alert(document.getElementById("quickOrderTable").rows.length);

for (var i = 0, row; row = table.rows[i]; i++) {
	try{
		partNumber = $("#partNumber_" + i).val();
		errorText = $('#error_' + i).text();
		styleClassIndex = $("#partNumber_" + i)[0].style.border.indexOf("green");
	
		//alert(partNumber +" - "+errorText+" - "+styleClassIndex);
		
		if ((!(partNumber  == null || partNumber  == "") && (errorText  == ''))
				|| styleClassIndex >= 0){
			flag = true;
			break;
		}
	}catch(err){}
}

	if(flag){
		
		return true;
	}else{
		alert("Please enter correct Part Number and Quantity");
		return false;
	}


}



</script>  
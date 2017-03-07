<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template"  tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="spring"    uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms"       uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt"       uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"        uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="theme"     tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="format"    tagdir="/WEB-INF/tags/shared/format" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="common"    tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="product"   tagdir="/WEB-INF/tags/desktop/product" %>
<%@ taglib prefix="grid"      tagdir="/WEB-INF/tags/desktop/grid" %>
<%@ taglib prefix="cart"      tagdir="/WEB-INF/tags/desktop/cart" %>

<%@ attribute name="cartData" required="true" type="de.hybris.platform.commercefacades.order.data.CartData" %>

<c:if test="${cartData.fmordertype == 'Emergency'}">
	<table id="myTable" class="table tablesorter">
		<thead>
			<tr>
				<th class="text-capitalize" colspan="2">Items</th>
				<th class="text-capitalize">Request Qty</th>
				<th class="text-capitalize"><span class="text-right"><input type="checkbox" class="confirmCheckbox" id="fm-chk-confirm-all" name="confirmCheckbox" >&nbsp;&nbsp;Confirm All</span></th>
			</tr>
		</thead>
 
		<tbody>
			<c:forEach items="${cartData.entries}" var="entry">
				<c:url value="${entry.product.url}" var="productUrl"/>
				<c:set value="${ycommerce:productImage(entry.product, format)}" var="primaryImage"/>
				<c:set value="${primaryImage.url}" var="primaryImageurl"/>

				<tr>
					<td class="text-uppercase">
						<div class="gatpimgCol"><product:productPrimaryImage product="${entry.product}" format="thumbnail"/></div>
	                </td>
	                <td>
	                	<div class="">
							<h5 class="billingShippingPopUpName">${entry.product.name}</h5>
							<p class="billingShippingPsize">Part No: ${entry.product.partNumber} </p>
							<!--<p class="billingShippingPsize"><span class="billingShippingPopUpBold">Warranty:</span><span class="addNewAddLink "> Lifetime Replacement</span></p>--> 
						</div>
					</td>
					<td class="text-uppercase">${entry.quantity}</td>
					<td class="">
						<c:if test="${not empty entry.distrubtionCenter}">
							<table class="reviewOrderNestedTable">
								<thead>
									<tr class="text-capitalize">
										<th>Shipping location</th>
										<th>available quantity</th>
										<!-- <th>available date</th> -->
										<th>Confirm</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="dccount" value="0" />

									<c:forEach items="${entry.distrubtionCenter}" var="dc">
										<c:if test="${dc.gatpProposed}">
											<tr>
												<td>
													<c:choose>
														<c:when test="${dc.cutOffTime == 'Approaching'}" >
															<span title="Cutoff time is approaching" id="cutoffClockyellow" class="fa fa-clock-o fa-2x" ></span>
														</c:when>
														<c:when test="${dc.cutOffTime == 'Passed'}" >
															<span title="Cutoff time has passed" id="cutoffClockred" class="fa fa-clock-o fa-2x" ></span>
														</c:when>
														<c:otherwise>
															<span title="Cutoff time is distant"  id="cutoffClockgreen" class="fa fa-clock-o fa-2x" ></span>
														</c:otherwise>
													</c:choose>
													${dc.distrubtionCenterDataName}
												</td>
												<td class="text-center">${dc.availableQTY}</td>
												<!-- <td><fmt:formatDate value="${dc.availableDate }" pattern="MM/dd/yyyy"/></td> -->
												<td class="text-center">
													<input type="checkbox" class="dcCheckbox" id="${entry.entryNumber}" name="dcCheckbox" value="${dc.code}" 
															onclick="disableAltDcSelectBox(this, 'altDcs-${entry.entryNumber}');onSelectDC(this);">		
												</td>
											</tr>
											<c:set var="dccount" value="${dccount + 1}" />
										</c:if>
									</c:forEach>
								</tbody>
							</table>
							<br>
							<h5 class="billingShippingPopUpName">Alternate Locations:</h5>
							<table>
	                    		<tr>
	                    			<td>
										<%-- Get a count of non-GATP Proposed DCs: --%>
										<c:set var="nonGatpDcCount" value="0" />

										<c:forEach items="${entry.distrubtionCenter}" var="nonGatpDc">
											<c:if test="${not nonGatpDc.gatpProposed}">
												<c:set var="nonGatpDcCount" value="${nonGatpDcCount + 1}" />
											</c:if>
										</c:forEach>

										<%-- Check if there are any non-GATP Proposed DCs: --%>
										<c:choose>
											<c:when test="${nonGatpDcCount gt 0}">
												<c:set var="altDcCount" value="${dccount}" />

												<select id="altDcs-${entry.entryNumber}" class="form-control" style="width: 100%; !important" name="altDcSelectBox" 
														onchange="clearGatpDcCheckboxes(this.value, ${entry.entryNumber});onSelectDC(this);">
		                  							<option value="Select">Select</option>
													<c:forEach items="${entry.distrubtionCenter}" var="nonGatpDc">
														<c:if test="${not nonGatpDc.gatpProposed}">
															<option value="available_${nonGatpDc.code}" class="text-capitalize">ship from ${nonGatpDc.distrubtionCenterDataName} &nbsp;&nbsp;  (${nonGatpDc.availableQTY}+) &nbsp;&nbsp;${nonGatpDc.distance} miles</option>
															
															<c:set var="altDcCount" value="${altDcCount + 1}" />
														</c:if>
													</c:forEach>
												</select>
											</c:when>
											<c:otherwise>
												None
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</c:if>
						<c:if test="${entry.errorMessage != null}">
							<c:set var="errMSG" value="${fn:split(entry.errorMessage, '~')}" />
							<div class="errorMSG">
								<span class="" style="color:red">${errMSG[0]}</span>
								<c:if test="${not empty errMSG[1]}">
									</br>
									<span class="fm_fntRed">${errMSG[1]}</span>
								</c:if>
							</div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>

<c:if test="${cartData.fmordertype == 'Stock' or cartData.fmordertype == 'Regular'}">
	<table id="myTable" class="table tablesorter">
		<thead>
			<tr>
				<th class="text-capitalize" colspan="2">Items</th>
				<th class="text-capitalize">Request Qty</th>
				<th class="text-capitalize">Confirmation</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${cartData.entries}" var="entry">
				<c:url value="${entry.product.url}" var="productUrl"/>
				<c:set value="${ycommerce:productImage(entry.product, format)}" var="primaryImage"/>
				<c:set value="${primaryImage.url}" var="primaryImageurl"/>

				<tr>
					<td class="text-uppercase">
						<div class="gatpimgCol"><product:productPrimaryImage product="${entry.product}" format="thumbnail"/> </div>
					</td>
					<td>
						<div class="">
							<h5 class="billingShippingPopUpName shipmentConfirmationH5 ">${entry.product.name}</h5>
							<p class="billingShippingPsize">Part No: ${entry.product.partNumber} </p>
							<!--<p class="billingShippingPsize"><span class="billingShippingPopUpBold">Warranty:</span><span class="addNewAddLink "> Lifetime Replacement</span></p>--> 
						</div>
					</td>
					<td class="text-uppercase">${entry.quantity}</td>
					<td class="">
						<c:if test="${not empty entry.distrubtionCenter}">
							<c:set var="count" value="0"  />
							<c:forEach items="${entry.distrubtionCenter}" var="dc">
								<c:set var="count" value="${count+1 }"  />
							</c:forEach>

							<c:choose>
								<c:when test="${count == 1 }">
									<c:set var="dccount" value="0"  />
									<c:forEach items="${entry.distrubtionCenter}" var="dc">
										<c:if test="${'partial' != dc.backorderFlag}">
											<span class ="s-fm-dc-ship-info" id="s-fm-dc-ship-info-${entry.entryNumber}"><p> <b>Shipping location :</b> <c:set var="string1" value="${dc.distrubtionCenterDataName }"/>
											<c:set var="string2" value="${fn:replace(string1,',',', ')}" />
											${string2} </p><p> <b>Available Quantity :</b> ${dc.availableQTY } </p></span>
											<c:if test="${'nothing' == dc.backorderFlag }">
												</br>
			                      	 			<span class="fm_fntRed"><b>No inventory available. Only back order all available.</b></span>
											</c:if>
										</c:if>
										<c:if test="${'partial' == dc.backorderFlag}">
											<span class ="s-fm-dc-ship-info" style="display:none" id="s-fm-dc-ship-info-${entry.entryNumber}"> <p><b>Shipping location :</b> <c:set var="string1" value="${dc.distrubtionCenterDataName }"/>
											<c:set var="string2" value="${fn:replace(string1,',',', ')}" />
											${string2} </p><p> <b>Available Quantity :</b> ${dc.availableQTY }</p> </span>
											<select class="form-control" id="sel-fm-stock-partial-${entry.entryNumber}-${dccount}" onchange="javascript:onPartialSelect(this,'${entry.entryNumber}');" style="width: 50%; !important">
												<option value="partial_choose">Select</option>
												<c:if test="${dc.availableQTY > 0}">
													<option value="available_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">ship only ${dc.availableQTY }</option>
												</c:if>
												<option value="partial_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">ship ${dc.availableQTY } and Backorder ${dc.backorderQTY } </option>
												<option value="nothing_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize" >Backorder all ${dc.backorderQTYAll }</option>
											</select>
										</c:if>
										<c:set var="dccount" value="${dccount+1 }"  />
									</c:forEach> 
								</c:when>
								<c:otherwise>
									<span class ="s-fm-dc-ship-info" style="display:none" id="s-fm-dc-ship-info-${entry.entryNumber}"></span>
									<c:set var="isPartialPresent" value="false"/>
									<select class="form-control" id="sel-fm-stock-dc-${entry.entryNumber}" onchange="javascript:onSelectDC(this);" style="width: 75%; !important" name="stockDcSelectBox">
										<c:set var="dccount" value="0"  />
										<option value="dc_choose">Select</option>
										<c:forEach items="${entry.distrubtionCenter}" var="dc">
											<c:choose>
												<c:when test="${dc.backorderFlag == 'available' }">
													<option value="available_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">ship from ${dc.distrubtionCenterDataName } (${dc.availableQTY } available) ${dc.distance} Miles </option>
												</c:when>
												<c:when test="${'nothing' == dc.backorderFlag }">
													<option value="nothing_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">ship from ${dc.distrubtionCenterDataName } (${dc.availableQTY } available ${dc.distance} Miles)</option>
												</c:when>
												<c:otherwise>
													<option value="partial_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">ship from ${dc.distrubtionCenterDataName } (${dc.availableQTY } available ${dc.distance} Miles)</option>
													<c:set var="isPartialPresent" value="true"/>
												</c:otherwise>
											</c:choose>
											<c:set var="dccount" value="${dccount+1 }"  />
										</c:forEach>
									</select>
									<c:if test="${isPartialPresent == 'true' }">
										<c:set var="dccount" value="0"  />
										<c:forEach items="${entry.distrubtionCenter}" var="dc">
											<select class="form-control" id="sel-fm-stock-partial-${entry.entryNumber}-${dccount}" onchange="javascript:onPartialSelect(this,'${entry.entryNumber}');" style="display:none;width: 50%; !important">
												<option value="partial_choose">Select</option>
												<c:if test="${dc.availableQTY > 0}">
													<option value="available_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">ship only ${dc.availableQTY }</option>
												</c:if>
												<option value="partial_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">ship ${dc.availableQTY } and Backorder ${dc.backorderQTY } </option>
												<option value="nothing_DS-${cartData.code}-${entry.entryNumber}-${dccount}" class="text-capitalize">Backorder all ${dc.backorderQTYAll }</option>
											</select>
											<c:set var="dccount" value="${dccount+1 }"  />
										</c:forEach>
									</c:if>
								</c:otherwise>
							</c:choose>
					      	<%-- <c:if test="${count != 1 }">
			                  <table id="dc_${entry.entryNumber}" class="reviewOrderNestedTable">
			                      <tbody>
			                      
			                      	<c:set var="dccount" value="0"  />
			                      	 <c:forEach items="${entry.distrubtionCenter}" var="dc">
			                      	 	<tr>
			                      	 		<td id="dc_${entry.entryNumber}_${dccount}" colspan="5">
			                      	 		<c:choose>
			                      	 			<c:when test="${dc.cutOffTime == 'Approaching'}" >
			                      	 				<span title="Cutoff time is approaching" id="cutoffClockyellow" class="fa fa-clock-o fa-2x" ></span>
			                      	 			</c:when>
			                      	 			<c:when test="${dc.cutOffTime == 'Passed'}" >
			                      	 				<span title="Cutoff time has passed" id="cutoffClockred" class="fa fa-clock-o fa-2x" ></span>
			                      	 			</c:when>
			                      	 			<c:otherwise>
			                      	 				<span title="Cutoff time is distant"  id="cutoffClockgreen" class="fa fa-clock-o fa-2x" ></span>
			                      	 			</c:otherwise>
			                      	 		</c:choose>  
			                      	 			<b>Shipping location :</b> <c:set var="string1" value="${dc.distrubtionCenterDataName }"/>
											<c:set var="string2" value="${fn:replace(string1,',',', ')}" />
			                      	 		${string2} , <b>Available QTY :</b> ${dc.availableQTY }<span class="text-center"> <input type="radio" class="dcRadio" id="dcradio_${entry.entryNumber}-${dccount}" name="dcradio" value="DS-${cartData.code}-${entry.entryNumber}-${dccount}" /></span>
			                      	 		</td>
			                      	 		</tr>
			                      	 		<tr>
			                      	 		<td>
			                      	 		<table id="backorder_${entry.entryNumber}-${dccount}" class="reviewOrderNestedTable">
			                      				<thead>
				                        			<tr class="text-capitalize">
				                          			<th colspan="3">Description</th>
				                          			 <th colspan="2">Confirm</th>					
			                        			</tr>
			                      				</thead>
			                      				<tbody>
					                      				<c:choose>
						                      				<c:when test="${dc.backorderFlag == 'available' }">
						                      					<tr>
						                      						<td colspan="3" class="text-center">Order ${dc.availableQTY }</td>
						                      	 	 				<td  colspan="2" class="text-center" ><input name="available" type="checkbox" class="dcCheckbox_${entry.entryNumber}-${dccount}" id="available_${entry.entryNumber}-${dccount}" value="DS-${cartData.code}-${entry.entryNumber}-${dccount}" disabled ></td>
						                      	 	 			</tr>
						                      				</c:when>
						                      				<c:when test="${'nothing' == dc.backorderFlag }">
						                      					<tr>
						                      						<td colspan="3" class="text-center">Backorder All ${dc.backorderQTYAll }</td>
						                      	 	 				<td  colspan="2" class="text-center" ><input name="nothing" type="checkbox" class="dcCheckbox_${entry.entryNumber}-${dccount}" id="nothing_${entry.entryNumber}-${dccount}"  value="DS-${cartData.code}-${entry.entryNumber}-${dccount}" disabled></td>
						                      	 	 			</tr>
						                      				</c:when>
						                      				<c:otherwise>
						                      					<tr>
						                      						<td colspan="3" class="text-center">Order ${dc.availableQTY }</td>
					                      	 	 					<td  colspan="2" class="text-center" ><input name="available" type="checkbox" class="dcCheckbox_${entry.entryNumber}-${dccount}" id="available_${entry.entryNumber}-${dccount}"  value="DS-${cartData.code}-${entry.entryNumber}-${dccount}" disabled></td>
					                      	 	 				</tr>
					                      	 	 				<tr>
						                      						<td colspan="3" class="text-center">BackOrder ${dc.backorderQTY }</td>
					                      	 	 					<td  colspan="2" class="text-center" ><input name="partial" type="checkbox" class="dcCheckbox_${entry.entryNumber}-${dccount}" id="partial_${entry.entryNumber}-${dccount}"  value="DS-${cartData.code}-${entry.entryNumber}-${dccount}" disabled></td>
					                      	 	 				</tr>
					                      	 	 				<tr>
						                      						<td colspan="3" class="text-center">Backorder All ${dc.backorderQTYAll }</td>
					                      	 	 					<td  colspan="2" class="text-center" ><input name="nothing" type="checkbox" class="dcCheckbox_${entry.entryNumber}-${dccount}" id="nothing_${entry.entryNumber}-${dccount}"  value="DS-${cartData.code}-${entry.entryNumber}-${dccount}" disabled></td>
					                      	 	 				</tr>
					                          				</c:otherwise>
					                      				</c:choose>
			                          			</tbody>
			                          		</table>
			                          		</td>
			                          		</tr>
			                          		 <tr></tr>
			                          	<c:set var="dccount" value="${dccount+1 }"  />
			                      	 </c:forEach>
			                      	
			                      </tbody>
			                    </table>
		                    </c:if> --%>
						</c:if>
						<c:if test="${entry.errorMessage != null && empty entry.distrubtionCenter}" >
							<c:set var="errMSG" value="${fn:split(entry.errorMessage , '~')}" />
							<div class="errorMSG">
								<span class="" style="color:red">${errMSG[0]}</span>
								<c:if test="${not empty errMSG[1]}">
									</br>
									<span class="fm_fntRed">${errMSG[1]}</span>
								</c:if>
							</div>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>
            
<product:productOrderFormJQueryTemplates />

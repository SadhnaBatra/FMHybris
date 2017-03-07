<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="errorNoResults" required="true"
	type="java.lang.String"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>


<div class="manageUserPanel rightHandPanel clearfix">
	<div class="modal-header">
		<h2 class="text-uppercase"><spring:theme code="cart.page.store.pickupLocation" text="Select your pickup location"/></h2>
	</div>
	<!-- <div class="form-group panel-body regFormFieldGroup ">
		<label class="" for="billZipPostalCode">Enter Zip Code or
			City, State<span class="fm_fntRed">*</span>
		</label>
	</div>  -->
	
		
		
		<div class="addressBookFillDetailsPanelFirst">

			<div class="abc">
			<!-- <form:form method="POST" id="storeFinderForm" commandName="storeFinderForm"  action="${request.contextPath}/cart-store-finder/inputZipCodeSearchPost" enctype="multipart/form-data">
		
				<div class="form-group regFormFieldGroup row">
					<div class="col-md-3">
						
					<form:input id="q1" path="q" class="form-control width270" type="text" placeholder="Enter Zip Code or City, State" required="required"/>
					</div>
					<div class="col-md-2">
						<button type="submit"
							class="btn btn-fmDefault shoppingCartCheckOut lftMrgn_40" >find Location</button>
						
					</div>
					<div class="col-md-2">
					 <c:url value="/cart" var="cartURL"/>
					 <a href="${cartURL }" id="activate-step-2" ><button type="button" class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right" >Back To Cart</button></a>
					</div>
				</div>
				</form:form> --!>
				<ul class="list-group checkboxGroup">
					<li class="list-group-item"></br><!-- <label for="savetomyaddress1">
							<input type="checkbox" id="savetomyaddress1" /> &nbsp;Only show
							stores with product availability
					</label></li>
				</ul> -->
				<div class="table-responsive userTable">
					<table id="myTable" class="table tablesorter">
						<thead>
							<tr>
								<th class="text-uppercase">Location</th>
								<th class="text-uppercase">Distance (in Miles)</th>
								<th class="text-uppercase">Stock availability</th> 
								<th class="text-uppercase">Pickup from</th>
							</tr>
						</thead>
						<tbody id="xyz">
						<c:if test="${not empty storeList }" >
							<c:forEach items="${storeList}" var="map">
								<c:set value="${map.key}" var="pos"/>
								<c:set value="${map.value}" var="miles"/>
								<c:set var="ctr1" value="0" scope="page"/>
								<tr>
									<td class="text-capitalize">
										<p class="shoppingCartBold">${pos.TSCLocId}</p>
										<p>${pos.addressLine1} </p>
										<c:if test="${not empty pos.addressLine2 }" >
											<p>${pos.addressLine2} </p>
										</c:if>
										<p>${pos.town} </p>
										<p>${pos.state} &nbsp; ${pos.zipcode}</p>
										<p>${pos.country}</p>
									</td>
									<td >${miles}</td>
									<td ><a title="Check Inventory" id="inventory_pickup" class="inventoryRow deleteRow" href="#" onclick="CheckPickupInventory('${pos.code}','true');"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a></td>
									<td > <c:url value="/inventory/pickup/${pos.code}/false" var="checkoutURL"/><a href="${checkoutURL }" ><button type="button" data-dismiss="modal" id="abc" class="btn btn-fmDefault shoppingCartCheckOut">Pick Up Here!</button></a></td>
								</tr>
							<c:set var="ctr1" value="${ctr1+1}"/> 	
							</c:forEach>	
					 	</c:if>
 						<c:if test="${empty storeList }" >
 							<tr>
 								<td colspan="4" align="center"><spring:theme code="cart.page.store.no.pickup.locations"/></td>
 							</tr>
 						</c:if>
 							
						</tbody>
					</table>
				</div>
			</div>
			<div class="col-md-13">
					 <c:url value="/cart" var="cartURL"/>
					 <a href="${cartURL }" id="activate-step-2" ><button type="button" class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right" ><spring:theme code="cart.page.store.pickup.backto.cart"/></button></a>
					</div>
		</div>
		
</div>



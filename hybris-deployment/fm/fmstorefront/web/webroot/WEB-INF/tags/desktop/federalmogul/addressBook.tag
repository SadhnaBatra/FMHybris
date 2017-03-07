<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel clearfix">
		<h2 class="text-uppercase">
			<spring:theme code="text.account.addressBook" text="Address Book" />
		</h2>

		<c:choose>
			<c:when test="${not empty addressData}">

				<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMB2SB">



					<div class="topMargn_25 addNewUserBtnHolder">
						<a class="control-label searchResultText" href="add-address"><strong><span
								class="fa fa-plus-circle fm_fntRed"></span> Add New Address</strong></a>
					</div>
				</sec:authorize>



				<sec:authorize ifAnyGranted="ROLE_FMB2SB">
					<c:if
						test="${currentdata.fmunit.prospectId != currentdata.fmunit.uid}">

						<div class="topMargn_25 addNewUserBtnHolder">
							<a class="control-label searchResultText" href="add-address"><strong><span
									class="fa fa-plus-circle fm_fntRed"></span> Add New Address</strong></a>
						</div>
					</c:if>


				</sec:authorize>

				<%--<p class="checkoutSubTitle text-uppercase">
					<spring:theme code="text.account.addressBook.manageYourAddresses"
						text="Manage your address book" />
				</p>--%>

				<div class="address">
					<div class="row">



						<c:forEach items="${addressData}" var="address">
							<div class="col-md-4">

								<sec:authorize ifNotGranted="ROLE_FMB2BB">
									<ul class="list-group checkboxGroup">
										<li class="list-group-item"><label for="savetomyaddress1">
												<%-- <c:if test="${address.defaultAddress}">
                      	<input type="radio" value="${address.id}" id="makeAddressDefault" name="default" checked="checked"/><spring:theme code="text.default" text="Default"/>
                      	</c:if>
                      	<c:if test="${not address.defaultAddress}">
                      	<input type="radio" value="${address.id}" id="makeAddressDefault" name="default" onclick="sendValue()"  /><spring:theme code="text.setDefault" text="Set as default"/>
                      	</c:if> --%> <c:if
													test='${fn:length(addressData) >1 }'>
													<c:choose>
														<c:when test="${address.defaultAddress}">
															<input type="radio" value="${address.id}"
																id="makeAddressDefault" name="default" checked="checked" />
															<spring:theme code="text.default" text="Default" />
														</c:when>
														<c:otherwise>
															<input type="radio" value="${address.id}"
																id="makeAddressDefault" name="default"
																onclick="sendValue()" />
															<spring:theme code="text.setDefault"
																text="Set as default" />
														</c:otherwise>
													</c:choose>
												</c:if>




										</label></li>
									</ul>
								</sec:authorize>


								<sec:authorize ifAnyGranted="ROLE_FMB2BB">



									<c:choose>

										<c:when test="${address.defaultAddress}">
											<ul class="list-group checkboxGroup">
												<li class="list-group-item"><label
													for="savetomyaddress1"> <input type="radio"
														checked="checked" /> <spring:theme code="text.default"
															text="Default" />
												</label></li>
											</ul>
										</c:when>
										<c:otherwise></c:otherwise>
									</c:choose>




								</sec:authorize>
								<div class="addressBlock">
									<ycommerce:testId code="addressBook_address_label">
										<ul>
											${fn:escapeXml(address.companyName)}</br>
											${fn:escapeXml(address.line1)}</br>
											${fn:escapeXml(address.town)},&nbsp;${fn:escapeXml(address.region.isocodeShort)}&nbsp;
										    ${fn:escapeXml(address.postalCode)}&nbsp;
								             ${fn:escapeXml(address.country.isocode)}


										</ul>
									</ycommerce:testId>
								</div>
								<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMB2SB">

									<div class="editRemoveLink">
										<%-- <ycommerce:testId code="addressBook_editAddress_button"> --%>
										<a class="fm_fntBlack" href="edit-address/${address.id}">
											<span class="fa fa-pencil"></span> <spring:theme
												code="text.edit" text="Edit" />
										</a>
										<%-- </ycommerce:testId>
	 	
	 	<ycommerce:testId code="addressBook_removeAddress_button"> --%>
										<span class="divider">|</span> <a class="fm_fntRed"
											href="remove-address/${address.id}"> <span
											class="fa fa-close"></span> <spring:theme code="text.remove"
												text="Remove" />
										</a>
										<%--  </ycommerce:testId> --%>

									</div>
								</sec:authorize>





								<sec:authorize ifAnyGranted="ROLE_FMB2SB">
									<c:if
										test="${currentdata.fmunit.prospectId != currentdata.fmunit.uid}">
										<div class="editRemoveLink">
											<%-- <ycommerce:testId code="addressBook_editAddress_button"> --%>
											<a class="fm_fntBlack" href="edit-address/${address.id}">
												<span class="fa fa-pencil"></span> <spring:theme
													code="text.edit" text="Edit" />
											</a>
											<%-- </ycommerce:testId>
	 	
	 	<ycommerce:testId code="addressBook_removeAddress_button"> --%>
											<span class="divider">|</span> <a class="fm_fntRed"
												href="remove-address/${address.id}"> <span
												class="fa fa-close"></span> <spring:theme code="text.remove"
													text="Remove" />
											</a>
											<%--  </ycommerce:testId> --%>

										</div>
									</c:if>
								</sec:authorize>








							</div>
						</c:forEach>

					</div>
				</div>
				<!-- </div> -->
				<%-- start of removal <div style="display:none">
						<div id="popup_confirm_address_removal_${address.id}" class="address-removal-confirm-popup">
							<div class="addressItem">
								<ul>
									<li>${fn:escapeXml(address.title)}&nbsp;${fn:escapeXml(address.firstName)}&nbsp;${fn:escapeXml(address.lastName)}</li>
									<li>${fn:escapeXml(address.line1)}</li>
									<li>${fn:escapeXml(address.line2)}</li>
									<li>${fn:escapeXml(address.town)}</li>
									<li>${fn:escapeXml(address.region.name)}</li>
									<li>${fn:escapeXml(address.postalCode)}</li>
									<li>${fn:escapeXml(address.country.name)}</li>
								</ul>
							</div>
							<spring:theme code="text.adress.remove.confirmation" text="Are you sure you would like to delete this address?"/></a>

							<div class="buttons clearfix">
								<a class="button" data-address-id="${address.id}" href="remove-address/${address.id}">
									<spring:theme code="text.yes" text="Yes"/>
								</a>
								<a class="button closeColorBox" data-address-id="${address.id}">
									<spring:theme code="text.no" text="No"/></a>
							</div>
							
						</div>
					</div>  --- end--%>
				<%-- </c:forEach> --%>
			</c:when>

			<c:otherwise>
				<sec:authorize ifNotGranted="ROLE_FMB2BB">
					<p class="text-uppercase">
						<spring:theme code="text.account.addressBook.manageYourAddresses"
							text="Manage your address book" />
					</p>
					<p class="emptyMessage">
						<strong> <spring:theme
								code="text.account.addressBook.noSavedAddresses" />
						</strong>
					</p>
					<div class="">
						<a href="add-address"><button
								class="btn btn-fmDefault addressBookADD">
								<spring:theme code="text.account.addressBook.addAddress"
									text="Add new address" />
							</button></a>
					</div>
				</sec:authorize>


				<sec:authorize ifAnyGranted="ROLE_FMB2BB">
					<p class="text-uppercase">
						<spring:theme code="text.account.addressBook.manageYourAddresses"
							text="Manage your address book" />
					</p>
					<p class="emptyMessage">
						<strong> <spring:theme
								code="text.account.addressBook.noSavedAddresses" />
						</strong>
					</p>
				</sec:authorize>
			</c:otherwise>
		</c:choose>
	</div>
</div>


<script type="text/javascript">
	function sendValue() {
		/* var r = confirm("Do you want to make this address to default");
		if (r == true) {
			var xyz = $(':radio[name=default]:checked').val();
			//alert("hello !! radio button value :: "+ xyz )	
			$.ajax({
				type:"POST",
				url:"http://localhost:9001/fmstorefront/federalmogul/en/USD/my-fmaccount/set-default-address",
				data:"addressid="+xyz,
				success:function(response){
					//alert(response);	
				},
				error:function(e){
					//alert("Error"+e);
				}
			}); 
		}
		else {
		    alert("You pressed Cancel!");
		} */
		//alert("Do you want to make this address to default")
		var xyz = $(':radio[name=default]:checked').val();
		alert("Address " + xyz + " is set to default, Refresh the page")

		var currentPath = window.location.href;
		 var pathName = "";
		// alert(currentPath);
		try{
	     if(currentPath.indexOf("/USD") != -1){
	  	  // alert(currentPath.lastIndexOf("/USD/"))
	            pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
	          //  alert("fdsfds"+pathName);
	     }else if(currentPath.indexOf("?site") != -1){
	            pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/USD/";
	     }else{
	            pathName = window.location.href+"federalmogul/en/USD/";
	     }
		}catch(e){
			       alert(e);
		}	

		$
				.ajax({
					type : "POST",
					url : pathName +"my-fmaccount/set-default-address",
					data : "addressid=" + xyz,
					success : function(response) {
						//alert(response);	
					},
					error : function(e) {
						//alert("Error"+e);
					}
				});
	}
</script>
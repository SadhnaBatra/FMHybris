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

	<c:set var="hasPreviousPage"
		value="${searchPageData.pagination.currentPage > 0}" />
	<c:set var="hasNextPage"
		value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}" />
		
		<spring:url value="/my-fmaccount/adminAddress-book"
					var="encodedUrl">
		</spring:url>

<input type="hidden" id="noOfRecords" value="${ProductCountPerPage }"/>		

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel clearfix">
		<h2 class="text-uppercase">Address book</h2>
		<section class="tableFilter col-lg-12 clearfix">
			<div class="visible-lg visible-md visible-sm ">
				<div class="col-lg-7 col-md-7 col-xs-6 addNewUserBtnHolder">

					<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMB2SB">
						<!-- admin-add-address -->
						<a class="control-label searchResultText" href=""><strong><span
							class="fa fa-plus-circle fm_fntRed"></span> Add New Address</strong></a>
							</sec:authorize>

					<sec:authorize ifAnyGranted="ROLE_FMB2SB">
						<c:if
							test="${currentdata.fmunit.prospectId != currentdata.fmunit.uid}">
							<a class="control-label searchResultText"
								href="admin-add-address"><strong><span
									class="fa fa-plus-circle fm_fntRed"></span> Add New Address</strong></a>

						</c:if>
					</sec:authorize>
				</div>

				<div class="col-lg-2 col-md-3 col-xs-2">
					<form class="form-horizontal">
						<div class="form-group clearfix">
							<div
								class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block pull-left">
								<label class="control-label" for="display">View &nbsp;</label>
							</div>
							<div
								class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
								<input type="hidden" id="url" name="url" value="${encodedUrl }" />
 								<select class="form-control" id="display" onchange="javascript:goToPageProductCount(this);">
 								<c:forEach begin="10" end="20" var="pageNumber" step="5">
 									<c:choose>
 										<c:when test="${ProductCountPerPage eq pageNumber }">
 											<option selected="selected">${pageNumber }</option>
 										</c:when>
 										<c:otherwise>
 										<option>${pageNumber }</option>
 										</c:otherwise>
 									</c:choose>
 								</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="col-lg-3 col-md-3 col-xs-4 text-right PaginationNav">
					<form class="form-horizontal">
						<div class="form-group">
							<label
								class="control-label visible-lg-inline visible-md-inline visible-sm-inline"
								for="page">Page</label> <input type="text"
								class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45"
								value="${searchPageData.pagination.currentPage+1}" disabled="disabled" /> <label
								class="control-label visible-lg-inline visible-md-inline visible-sm-inline">
								of  ${searchPageData.pagination.numberOfPages}</label>
								<spring:url value="${searchUrl}" var="pageNumberUrl" htmlEscape="true">
									<spring:param name="noOfRecords" value="${noOfRecords}"/>
								</spring:url>
								
								<c:if test="${hasPreviousPage}" >	
							
							<spring:url value="${searchUrl}" var="previousPageUrl"
								htmlEscape="true">
								<spring:param name="page"
									value="${searchPageData.pagination.currentPage - 1}" />
							</spring:url>
							<button type="button"
								class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"
								value="${previousPageUrl}" onclick="javascript:goToPageNumber(this);"></button>
							</c:if>
							<c:if test="${hasNextPage}" >
							
							<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
								<spring:param name="page"
									value="${searchPageData.pagination.currentPage + 1}" />
							</spring:url>
							<button type="button"
								class="fa fa-angle-right pagination-next-page " value= "${nextPageUrl}" onclick="javascript:goToPageNumber(this);"></button>
							</c:if>	
							
						</div>
					</form>
				</div>
			</div>
			
		</section>
		<div class="table-responsive col-lg-12 userTable">
			<table class="table tablesorter" id="myTable">
				<thead>
					<tr>
						<th class="maName">Name</th>
						<th class="maAdd">Address</th>
						<sec:authorize ifNotGranted="ROLE_FMB2SB">
						<sec:authorize ifAnyGranted="ROLE_FMB2BB">
						<th class="maActions">Actions</th>
						</sec:authorize>
						</sec:authorize>
					</tr>
				</thead>
				<tbody>
					<!-- 					<tr>
						<td><input type="radio" name="default" /></td>
						<td class="nameCol"><a href="#">Adam Anisley</a></td>

						<td>1124 Golfview Drive</td>



						<td class="linkCol"><a
							href="addressBook_addAddress_view4All.html"
							class="fa fa-pencil upOrderPencil-1 lftMrgn_2 rghtMrgn_2"></a><a
							class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a><a
							href="#"
							class="fa fa-trash delete upOrderDelete-1 lftMrgn_2 rghtMrgn_2"></a></td>
					</tr>
					<tr class="inactiveData">
						<td><input type="radio" name="default" /></td>


						<td class="nameCol">Francois Marceaux</td>


						<td>1124 Golfview Drive</td>

						<td class="linkCol"><a
							href="addressBook_addAddress_view4All.html"
							class="fa fa-pencil upOrderPencil-1 lftMrgn_2 rghtMrgn_2"></a><a
							class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a><a
							href="#"
							class="fa fa-trash delete upOrderDelete-1 lftMrgn_2 rghtMrgn_2"></a></td>
					</tr> -->

					<!-- For current user START -->

					<%-- 
					<c:forEach items="${currentAdminAddressData}" var="address">
					---${address.id} current--
						<tr>
							<!-- <input type="radio" name="default" /> -->

							<td><c:choose>
									<c:when test="${address.defaultAddress}">
										<input type="radio" value="${address.id}"
											id="makeAddressDefault" name="default" checked="checked" />
									</c:when>
									<c:otherwise>
										<input type="radio" value="${address.id}"
											id="makeAddressDefault" name="default" onclick="sendValue()" />
									</c:otherwise>
								</c:choose></td>

							<td class="nameCol"><ycommerce:testId
									code="admin_address_username_label">
									<p>${address.firstName}&nbsp;${address.lastName}</p>
								</ycommerce:testId></td>
							<td><ycommerce:testId code="addressBook_address_label">
											
											${fn:escapeXml(address.line1)}&nbsp;
											${fn:escapeXml(address.line2)}&nbsp;
											${fn:escapeXml(address.town)}
										
									</ycommerce:testId></td>

							<td class="linkCol"><a
								href="admin-edit-address/${address.id}"
								class="fa fa-pencil upOrderPencil-1 lftMrgn_2 rghtMrgn_2"></a> <a
								class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a> <a
								href="removeAdmin-address/${address.id}"
								class="fa fa-trash delete upOrderDelete-1 lftMrgn_2 rghtMrgn_2"></a>

							</td>
						</tr>
					</c:forEach> --%>

					<!-- For current user END -->

					<!-- For all users in same unit START -->
					<sec:authorize ifNotGranted="ROLE_FMB2SB">
					<c:forEach items="${unitAddressMap}" var="unitAddress">
						<c:forEach items="${unitAddress.value}" var="address">
							<tr>
								<%-- ---${address.id} other-- --%>

								<sec:authorize ifNotGranted="ROLE_FMB2BB">

							<%--		<td><c:choose>

											<c:when test="${address.defaultAddress}">
												<input type="radio" value="${address.id}"
													id="makeAddressDefault" name="default" checked="checked" />
											</c:when>
											<c:otherwise>
												<input type="radio" value="${address.id}"
													id="makeAddressDefault" name="default"
													onclick="sendValue()" />
											</c:otherwise>
										</c:choose></td> --%>
								</sec:authorize>



								<sec:authorize ifAnyGranted="ROLE_FMB2BB">

									<td><c:choose>

											<c:when test="${address.defaultAddress}">
												<input type="radio" checked="checked" />
											</c:when>
											<c:otherwise>

											</c:otherwise>
										</c:choose></td>
								</sec:authorize>


								<td class="nameCol"><ycommerce:testId
										code="admin_address_username_label">
											<p>${unitAddress.key.name}&nbsp;</p>
									</ycommerce:testId></td>

								<td><ycommerce:testId code="addressBook_address_label">
											
											${fn:escapeXml(address.line1)}&nbsp;
											${fn:escapeXml(address.line2)}&nbsp;
											${fn:escapeXml(address.town)}
										
									</ycommerce:testId></td>

								<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMB2SB">
									<td class="linkCol"><a
										href="admin-edit-address/${address.id}"
										class="fa fa-pencil upOrderPencil-1 lftMrgn_2 rghtMrgn_2"></a>
										<a class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a>
										<a href="removeAdmin-address/${address.id}"
										class="fa fa-trash delete upOrderDelete-1 lftMrgn_2 rghtMrgn_2"></a>

									</td>
								</sec:authorize>


								<sec:authorize ifAnyGranted="ROLE_FMB2SB">
									<c:if
										test="${currentdata.fmunit.prospectId != currentdata.fmunit.uid}">
										<td class="linkCol"><a
											href="admin-edit-address/${address.id}"
											class="fa fa-pencil upOrderPencil-1 lftMrgn_2 rghtMrgn_2"></a>
											<a class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a>
											<a href="removeAdmin-address/${address.id}"
											class="fa fa-trash delete upOrderDelete-1 lftMrgn_2 rghtMrgn_2"></a>

										</td>
									</c:if>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_FMB2BB">
									<td class="linkCol"><a
										class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a>
									</td>
								</sec:authorize>							
						</c:forEach>
					</c:forEach>
					
					</sec:authorize>
					
					
					<!-- Changes done for B2b admin -  sreedevi start-->
					<sec:authorize ifAnyGranted="ROLE_FMB2SB">					
					
					<c:forEach items="${unitAddress}" var="address"> 
							
							<tr>
								<%-- ---${address.id} other-- --%>

								<sec:authorize ifNotGranted="ROLE_FMB2BB">

							<%--		<td><c:choose>

											<c:when test="${address.defaultAddress}">
												<input type="radio" value="${address.id}"
													id="makeAddressDefault" name="default" checked="checked" />
											</c:when>
											<c:otherwise>
												<input type="radio" value="${address.id}"
													id="makeAddressDefault" name="default"
													onclick="sendValue()" />
											</c:otherwise>
										</c:choose></td> --%>
								</sec:authorize>
								
								<sec:authorize ifAnyGranted="ROLE_FMB2BB">

									<td><c:choose>
											<c:when test="${address.defaultAddress}">
												<input type="radio" checked="checked" />
											</c:when>
											<c:otherwise>

											</c:otherwise>
										</c:choose></td>
								</sec:authorize>
								
								<td class="nameCol"><ycommerce:testId
										code="admin_address_username_label">
											<p>${unitDetails.name}&nbsp;</p>
									</ycommerce:testId></td>

								<td><ycommerce:testId code="addressBook_address_label">
											
											${fn:escapeXml(address.line1)}&nbsp;
											${fn:escapeXml(address.line2)}&nbsp;
											${fn:escapeXml(address.town)}
										
									</ycommerce:testId></td>

								<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMB2SB">
									<td class="linkCol"><a
										href="admin-edit-address/${address.id}"
										class="fa fa-pencil upOrderPencil-1 lftMrgn_2 rghtMrgn_2"></a>
										<a class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a>
										<a href="removeAdmin-address/${address.id}"
										class="fa fa-trash delete upOrderDelete-1 lftMrgn_2 rghtMrgn_2"></a>

									</td>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_FMB2SB">
									<c:if
										test="${currentdata.fmunit.prospectId != currentdata.fmunit.uid}">
										<td class="linkCol"><a
											href="admin-edit-address/${address.id}"
											class="fa fa-pencil upOrderPencil-1 lftMrgn_2 rghtMrgn_2"></a>
											<a class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a>
											<a href="removeAdmin-address/${address.id}"
											class="fa fa-trash delete upOrderDelete-1 lftMrgn_2 rghtMrgn_2"></a>

										</td>
									</c:if>
								</sec:authorize>
								<sec:authorize ifAnyGranted="ROLE_FMB2BB">
									<td class="linkCol"><a
										class="fa fa-search upOrderSearch-1 lftMrgn_2 rghtMrgn_2"></a>
									</td>
								</sec:authorize>							
						</c:forEach>
				
					</sec:authorize>
					<!-- Changes done for B2b admin -  sreedevi end-->
					
					
					
					
					<!-- For all users in same unit END -->
				</tbody>
			</table>
		</div>
<section class="tableFilter col-lg-12 clearfix">
			<div class="visible-lg visible-md visible-sm ">
				<div class="col-lg-7 col-md-7 col-xs-6 addNewUserBtnHolder">


					<sec:authorize ifNotGranted="ROLE_FMB2BB,ROLE_FMB2SB">

						<a class="control-label searchResultText" href=""><strong><span
								class="fa fa-plus-circle fm_fntRed"></span> Add New Address</strong></a>
					</sec:authorize>


					<sec:authorize ifAnyGranted="ROLE_FMB2SB">
						<c:if
							test="${currentdata.fmunit.prospectId != currentdata.fmunit.uid}">
							<a class="control-label searchResultText"
								href="admin-add-address"><strong><span
									class="fa fa-plus-circle fm_fntRed"></span> Add New Address</strong></a>

						</c:if>
					</sec:authorize>


				</div>


				<div class="col-lg-2 col-md-3 col-xs-2">
					<form class="form-horizontal">
						<div class="form-group clearfix">
							<div
								class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block pull-left">
								<label class="control-label" for="display">View &nbsp;</label>
							</div>
							<div
								class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
								<input type="hidden" id="url" name="url" value="${encodedUrl }" />
 								<select class="form-control" id="display" onchange="javascript:goToPageProductCount(this);">
 								<c:forEach begin="10" end="20" var="pageNumber" step="5">
 									<c:choose>
 										<c:when test="${ProductCountPerPage eq pageNumber }">
 											<option selected="selected">${pageNumber }</option>
 										</c:when>
 										<c:otherwise>
 										<option>${pageNumber }</option>
 										</c:otherwise>
 									</c:choose>
 								</c:forEach>
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="col-lg-3 col-md-3 col-xs-4 text-right PaginationNav">
					<form class="form-horizontal">
						<div class="form-group">
							<label
								class="control-label visible-lg-inline visible-md-inline visible-sm-inline"
								for="page">Page</label> <input type="text"
								class="form-control visible-lg-inline visible-md-inline visible-sm-inline width45"
								value="${searchPageData.pagination.currentPage+1}" disabled="disabled" /> <label
								class="control-label visible-lg-inline visible-md-inline visible-sm-inline">
								of  ${searchPageData.pagination.numberOfPages}</label>
								<spring:url value="${searchUrl}" var="pageNumberUrl" htmlEscape="true">
									<spring:param name="noOfRecords" value="${noOfRecords}"/>
								</spring:url>
								
							<c:if test="${hasPreviousPage}" >	
							
							<spring:url value="${searchUrl}" var="previousPageUrl"
								htmlEscape="true">
								<spring:param name="page"
									value="${searchPageData.pagination.currentPage - 1}" />
							</spring:url>
							<button type="button"
								class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"
								value="${previousPageUrl}" onclick="javascript:goToPageNumber(this);"></button>
							</c:if>
							<c:if test="${hasNextPage}" >
							
							<spring:url value="${searchUrl}" var="nextPageUrl" htmlEscape="true">
								<spring:param name="page"
									value="${searchPageData.pagination.currentPage + 1}" />
							</spring:url>
							<button type="button"
								class="fa fa-angle-right pagination-next-page " value= "${nextPageUrl}" onclick="javascript:goToPageNumber(this);"></button>
							</c:if>	
							
						</div>
					</form>
				</div>
			</div>
			
		</section>
 </div>
</div> 


<script type="text/javascript">
	function sendValue() {

		var xyz = $(':radio[name=default]:checked').val();
		alert("You are setting this address as your default address.")

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
					url : pathName+ "my-fmaccount/set-default-address",
					data : "addressid=" + xyz,
					success : function(response) {
						//alert(response);	
					},
					error : function(e) {
						//alert("Error"+e);
					}
				});
	}
	
	function goToPageNumber(url){
		var newUrl = url.value;
		
		var productsCount = document.getElementById("noOfRecords").value;
		document.location.href = url.value+"&noOfRecords="+productsCount;
	}
	function goToPageProductCount(count){
		var url = document.getElementById("url").value;
		document.location.href = url+"?noOfRecords="+count.value;
		 
	}
	
</script>
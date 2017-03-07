<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="company" tagdir="/WEB-INF/tags/desktop/company"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<!-- 
/my-network/organization-management/manage-users/edit
 -->
<spring:url value="/my-account/manage-users/create" var="manageUsersUrl" />

<spring:url value="/my-company/organization-management/manage-users"
	var="manageUserRecordsUrl" />

<spring:url
	value="/my-company/organization-management/manage-users/csrAccountSearch"
	var="csrAccountSearchUrl" />

<c:set var="hasPreviousPage"
	value="${searchPageData.pagination.currentPage > 0}" />
<c:set var="hasNextPage"
	value="${(searchPageData.pagination.currentPage + 1) < searchPageData.pagination.numberOfPages}" />



<%-- <spring:url
	value="/my-company/organization-management/manage-users/edit-approver/"
	var="editUserUrl">
	<spring:param name="user" value="${customerData.uid}" />
	<spring:param name="approver" value="${user.uid}" />
</spring:url> --%>
<spring:url
	value="/my-company/organization-management/manage-users/disable"
	var="disableUserUrl">
	<spring:param name="user" value="${user.uid}" />
</spring:url>




<!-- Starts: Manage Account Right hand side panel -->
<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<c:if test="${not empty searchPageData.results}">
		<div class="manageUserPanel rightHandPanel clearfix">
			<h2 class="text-capitalize"><spring:theme code="text.company.manageUser" text="Manage Users" /> </h2>
			<div id="globalMessages">
				<common:globalMessages />
			</div>
			<section class="tableFilter col-lg-12 col-xs-12 clearfix">
				<div class="visible-lg visible-md visible-sm ">
					<div class="col-lg-3 col-md-3 col-xs-5 addNewUserBtnHolder">
						<form class="navbar-form smallB2b-Navform" role="search"
							name="search_form" method="get" action="#">
							<div class="input-group">
								<div class="input-group-btn">
									<button class="btn btn-default" type="submit"
										value="${manageUserRecordsUrl}">
										<i class="fa fa-search"></i>
									</button>
								</div>
								<input type="text" class="form-control"
									placeholder="Customer Name Search" name="searchBy"
									id="searchBy" value="${searchBy}">
							</div>
						</form>




						<sec:authorize ifAnyGranted="ROLE_FMCSR">
							<form class="navbar-form smallB2b-Navform" role="search"
								name="search_form" method="get" action="${csrAccountSearchUrl}">
								<div class="input-group">
									<div class="input-group-btn">
										<button class="btn btn-default" type="submit"
											value="${csrAccountSearchUrl}">
											<i class="fa fa-search"></i>
										</button>
									</div>
									<input type="text" class="form-control"
										placeholder="Account Search" name="searchByAccount"
										id="searchByAccount" value="${searchByAccount}">
								</div>
							</form>

						</sec:authorize>
					</div>



					<c:if test="${not empty searchBy}">
						<c:url
							value="/my-company/organization-management/manage-users/downloadExcel/${searchBy}"
							var="excelsheeturl" />
				
							<c:set var="namesearch" value="${searchBy}" />
                                                          <c:set var="namecheck" value="true" />


						<c:url
							value="/my-company/organization-management/manage-users/downloadPdf/${searchBy}"
							var="pdfurl" />

					</c:if>

					<c:if test="${empty searchBy}">

						<c:set var="searchBy" scope="request" value="all" />

						<c:url
							value="/my-company/organization-management/manage-users/downloadExcel/${searchBy}"
							var="excelsheeturl" />

						<c:url
							value="/my-company/organization-management/manage-users/downloadPdf/${searchBy}"
							var="pdfurl" />

					</c:if>


					<c:if test="${not empty searchByAccount}">
						<c:url
							value="/my-company/organization-management/manage-users/downloadExcelByAccount/${searchByAccount}"
							var="excelsheeturl" />
					
							<c:set var="namesearch" value="${searchByAccount}" />
							<c:set var="namecheck" value="false" />

						<c:url
							value="/my-company/organization-management/manage-users/downloadPdfByAccount/${searchByAccount}"
							var="pdfurl" />

					</c:if>





					<sec:authorize ifAnyGranted="ROLE_FMCSR">
						<div
							class="col-lg-9 col-md-9 col-xs-7 text-right  topMargn_76 learningCenterBold">
							<span><a class="fa fa-file-excel-o orderExcel"
								href="${excelsheeturl}"></a>&nbsp;Export as Excel</span> &nbsp; <span><a
								class="fa fa-file-pdf-o orderPdf" href="${pdfurl}"
								target="_blank"></a>&nbsp;Export as Pdf</span>
						</div>
					</sec:authorize>


				</div>
			</section>

			<section class="tableFilter col-lg-12  col-xs-12 clearfix">
				<div class="visible-lg visible-md visible-sm ">
					<div class="col-lg-7 col-md-7 col-xs-6 addNewUserBtnHolder">
						<ycommerce:testId code="User_AddUser_button">
							<a href="${manageUsersUrl}"
								class="control-label searchResultText"><strong><span
									class="fa fa-plus-circle fm_fntRed"></span> <spring:theme code="text.company.addNewUser" text="Add New User" /></strong></a>
						</ycommerce:testId>
					</div>



					<%-- 	<input type="hidden" id="searchByValue" value="${searchBy }" /> --%>


					<%-- <c:if test="${searchBy  != null}"> --%>





					<input type="hidden" id="noOfRecords"
						value="${ProductCountPerPage }" /> <input type="hidden"
						id="sortCode" value="${sortCode}" />
					<div class="col-lg-2 col-md-3 col-xs-2">
						<form class="form-horizontal">
							<div class="form-group clearfix">
								<div
									class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block pull-left">
									<label class="control-label" for="display">View &nbsp;</label>
								</div>
								<div
									class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
									<input type="hidden" id="url" name="url"
										value="${manageUserRecordsUrl }" /> <select
										class="form-control" id="display"
										onchange="javascript:goToPageProductCount(this);">
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
									value="${searchPageData.pagination.currentPage+1}"
									disabled="disabled" /> <label
									class="control-label visible-lg-inline visible-md-inline visible-sm-inline">
									of ${searchPageData.pagination.numberOfPages}</label>
								<spring:url value="${searchUrl}" var="pageNumberUrl"
									htmlEscape="true">
									<spring:param name="noOfRecords" value="${noOfRecords}" />
								</spring:url>

								<c:if test="${hasPreviousPage}">

									<spring:url value="${searchUrl}" var="previousPageUrl"
										htmlEscape="true">
										<spring:param name="page"
											value="${searchPageData.pagination.currentPage - 1}" />
									</spring:url>
									<button type="button"
										class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"
										value="${previousPageUrl}"
										onclick="javascript:goToPageNumber(this);"></button>
								</c:if>
								<c:if test="${hasNextPage}">

									<spring:url value="${searchUrl}" var="nextPageUrl"
										htmlEscape="true">
										<spring:param name="page"
											value="${searchPageData.pagination.currentPage + 1}" />
									</spring:url>
									<button type="button"
										class="fa fa-angle-right pagination-next-page "
										value="${nextPageUrl}"
										onclick="javascript:goToPageNumber(this);"></button>
								</c:if>


							</div>
						</form>
					</div>
				</div>

			</section>

			<div class="table-responsive col-lg-12  col-xs-12 userTable">
				<table class="table tablesorter" id="myTable">
					<thead>
						<tr>
							<th class="muName">Name <a
								href="${manageUserRecordsUrl}?sort=ASC"> &#9650; </a> <a
								href="${manageUserRecordsUrl}?sort=DESC"> &#9660; </a>
							</th>
							<th class="muRole">Role</th>
							<th class="muCompany">Company</th>
							<th class="muStatus">Status</th>
							<th class="muActions">Actions</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${searchPageData.results}" var="user"
							begin="${begin}" end="${end}">
							<tr>
									<!-- for edit button disable for technicians -->
									<c:set var="isTechnician" value="no" />
									<c:forEach items="${user.roles}" var="role">
											<c:if test="${role == 'FMB2T'}">
											<c:set var="isTechnician" value="yes" />
											</c:if>
									</c:forEach>
								
								<sec:authorize ifNotGranted="ROLE_FMCSR">



									<c:set var="isAdminn" value="" />



									<%-- 	<c:forEach items="${user.roles}" var="role">
									<c:if test="${role eq 'b2badmingroup'}">
										<c:set var="isAdminn" value="abc" />
									</c:if>
								</c:forEach> --%>


									<c:if test="${empty isAdminn}">



										<spring:url
											value="/my-company/organization-management/manage-users/details/"
											var="viewUserUrl">
											<spring:param name="user" value="${user.uid}" />
										</spring:url>
										<spring:url
											value="/my-company/organization-management/manage-units/details/"
											var="viewUnitUrl">
											<spring:param name="unit" value="${user.unit.uid}" />
										</spring:url>
										<td headers="header1"><ycommerce:testId
												code="my-company_username_label">
												<p>${user.firstName}&nbsp;${user.lastName}</p>
											</ycommerce:testId></td>
										<td headers="header2"><ycommerce:testId
												code="my-company_user_roles_label">
												<c:forEach items="${user.roles}" var="role">
													<p>
														<%-- <spring:theme code="b2busergroup.${role.name}" />  --%>

															<c:choose>
															<c:when test="${role == 'FMB2BB'}">Customer</c:when>
															<c:when test="${role == 'FMNoInvoiceGroup'}">Limited Access</c:when>
															<c:when test="${role == 'FMViewOnlyGroup'}">View Only</c:when>
															<c:when test="${role == 'FMCSR'}">CSR</c:when>
															<c:when test="${role == 'FMBUVOR'}">Sales Rep</c:when>
															<c:when test="${role == 'FMB2T'}">Technician</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>	
													</p>
												</c:forEach>
											</ycommerce:testId></td>
										<td headers="header3"><ycommerce:testId
												code="my-company_user_unit_label">
												<p>
													${user.unit.name}
													<%-- <a href="${viewUnitUrl}">${user.unit.name}</a> --%>
												</p>
											</ycommerce:testId></td>
										<td headers="header4"><ycommerce:testId
												code="costCenter_status_label">
												<p>
													<spring:theme
														code="text.company.status.isLoginDisabled.${user.isLoginDisabled}" />
												</p>
											</ycommerce:testId></td>




										<td class="linkCol classinline"><c:choose>
												<c:when test="${user.isLoginDisabled}">

													<spring:url
														value="/my-company/organization-management/manage-users/enable"
														var="enableUserUrl">
														<spring:param name="user" value="${user.uid}" />
													</spring:url>
														<spring:url value="/my-account/manage-users/editCustomer"
															var="editUserUrl">
															</spring:url>
													<form:form action="${enableUserUrl}" method="post">
														<button class="btn btn-link">Enable <span class="manageuserdivider">|</span></button>
														
											<input type="hidden" id="searchname" name="namesearch" value="${namesearch}" />
												<input type="hidden" id="searchaccount" name="acountsearch" value="${namecheck}" />
													</form:form>
													<c:if test="${empty isAdminn && isTechnician ne 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
													<input type="hidden" id="user" name="user" value="$${user.uid}" />

													<button class="btn btn-link"><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
																	</form:form>
														</c:if>
														<c:if test="${empty isAdminn && isTechnician eq 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
														<input type="hidden" id="user" name="user" value="${user.uid}" />

													<button class="btn btn-link" disabled><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
																	</form:form>
														</c:if>


												</c:when>


												<c:otherwise>

														<spring:url value="/my-account/manage-users/editCustomer"
														var="editUserUrl">
														
													</spring:url>

													<%-- <c:forEach items="${user.roles}" var="role">
													<c:if test="${role eq 'b2badmingroup'}">
														<c:set var="isAdminn" value="abc" />
													</c:if>
												</c:forEach> --%>


													<c:if test="${empty isAdminn && isTechnician ne 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
														
													<button class="btn btn-link"><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
												<input type="hidden" id="user" name="user" value="${user.uid}" />
																	</form:form>
														</c:if>
														<c:if test="${empty isAdminn && isTechnician eq 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
													<button class="btn btn-link" disabled><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
													<input type="hidden" id="user" name="user" value="${user.uid}" />
																	</form:form>
														</c:if>
													
												</c:otherwise>

											</c:choose> <%-- 			<c:otherwise>
										<c:choose>
											<c:when test="${user.unit.active}">

												<spring:url
													value="/my-company/organization-management/manage-users/enable"
													var="enableUserUrl">
													<spring:param name="user" value="${user.uid}" />
												</spring:url>


												<form:form action="${enableUserUrl}">
													<button class="btn btn-link">Enable</button>
													<span class="manageuserdivider">|</span>
													 <spring:url
									value="/my-account/manage-users/edit"
									var="editUserUrl">
		
									<spring:param name="user" value="${user.uid}" />
								</spring:url> <a href="${editUserUrl}"><spring:theme
										code="text.company.manageUser.button.edit" text="Edit" /></a>
												</form:form>


											</c:when>
											<c:otherwise>
												<a><spring:theme
														code="text.company.manageusers.button.enableuser"
														text="Enable User" /></a>
											</c:otherwise>
										</c:choose>


									</c:otherwise> --%> <!-- <a href="#">Enable</a>  --> <%-- <spring:url
									value="/my-account/manage-users/edit"
									var="editUserUrl">
		
									<spring:param name="user" value="${user.uid}" />
								</spring:url> <a href="${editUserUrl}"><spring:theme
										code="text.company.manageUser.button.edit" text="Edit" /></a> --%>





										</td>

									</c:if>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_FMCSR">


									<c:set var="isCsr" value="" />

								

									<c:forEach items="${user.roles}" var="role">
										<c:if test="${role eq 'FMCSR' or role eq 'FMBUVOR'}">
											<c:set var="isCsr" value="abc" />
										</c:if>
									</c:forEach>

							
								

										<spring:url
											value="/my-company/organization-management/manage-users/details/"
											var="viewUserUrl">
											<spring:param name="user" value="${user.uid}" />
										</spring:url>
										<spring:url
											value="/my-company/organization-management/manage-units/details/"
											var="viewUnitUrl">
											<spring:param name="unit" value="${user.unit.uid}" />
										</spring:url>
										<td headers="header1"><ycommerce:testId
												code="my-company_username_label">
												<p>${user.firstName}&nbsp;${user.lastName}</p>
											</ycommerce:testId></td>
										<td headers="header2"><ycommerce:testId
												code="my-company_user_roles_label">
												<c:forEach items="${user.roles}" var="role">
													<p>
														<%-- <spring:theme code="b2busergroup.${role.name}" />  --%>

														<c:choose>
															<c:when test="${role == 'FMB2BB'}">Customer</c:when>
															<c:when test="${role == 'FMNoInvoiceGroup'}">Limited Access</c:when>
															<c:when test="${role == 'FMViewOnlyGroup'}">View Only</c:when>
															<c:when test="${role == 'FMCSR'}">CSR</c:when>
															<c:when test="${role == 'FMBUVOR'}">Sales Rep</c:when>
															<c:when test="${role == 'FMB2T'}">Technician</c:when>
															<c:otherwise></c:otherwise>
														</c:choose>														</p>
												</c:forEach>
											</ycommerce:testId></td>
										<td headers="header3"><ycommerce:testId
												code="my-company_user_unit_label">
												<p>
													${user.unit.name}
													<%-- <a href="${viewUnitUrl}">${user.unit.name}</a> --%>
												</p>
											</ycommerce:testId></td>
										<td headers="header4"><ycommerce:testId
												code="costCenter_status_label">
												<p>
													<spring:theme
														code="text.company.status.isLoginDisabled.${user.isLoginDisabled}" />
												</p>
											</ycommerce:testId></td>




										<td class="linkCol classinline"><c:choose>
												<c:when test="${user.isLoginDisabled}">

													<spring:url
														value="/my-company/organization-management/manage-users/enable"
														var="enableUserUrl">
														<spring:param name="user" value="${user.uid}" />
													</spring:url>
														<spring:url value="/my-account/manage-users/editCustomer"
															var="editUserUrl">
															
														</spring:url>
													<form:form action="${enableUserUrl}" method="post">
														<button class="btn btn-link">Enable <span class="manageuserdivider">|</span></button>
												<input type="hidden" id="searchname" name="namesearch" value="${namesearch}" />
												<input type="hidden" id="searchaccount" name="acountsearch" value="${namecheck}" />
													</form:form>
													<c:if test="${empty isAdminn && isTechnician ne 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
													<button class="btn btn-link"><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
													<input type="hidden" id="user" name="user" value="${user.uid}" />
																	</form:form>
														</c:if>
														<c:if test="${empty isAdminn && isTechnician eq 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
													<button class="btn btn-link" disabled><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
													<input type="hidden" id="user" name="user" value="${user.uid}" />
																	</form:form>
														</c:if>
												</c:when>


												<c:otherwise>

													<spring:url value="/my-account/manage-users/editCustomer"
														var="editUserUrl"></spring:url>

													<%-- <c:forEach items="${user.roles}" var="role">
													<c:if test="${role eq 'b2badmingroup'}">
														<c:set var="isAdminn" value="abc" />
													</c:if>
												</c:forEach> --%>


													<c:if test="${empty isAdminn && isTechnician ne 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
													<button class="btn btn-link"><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
											<input type="hidden" id="user" name="user" value="${user.uid}" />
																	</form:form>
														</c:if>
														<c:if test="${empty isAdminn && isTechnician eq 'yes' }">
														<form:form action="${editUserUrl}" method="POST">
													<button class="btn btn-link" disabled><spring:theme code="text.company.manageUser.button.edit" text="Edit" /></button>
											<input type="hidden" id="user" name="user" value="${user.uid}" />
																	</form:form>
														</c:if>
												</c:otherwise>

											</c:choose> <%-- 			<c:otherwise>
										<c:choose>
											<c:when test="${user.unit.active}">

												<spring:url
													value="/my-company/organization-management/manage-users/enable"
													var="enableUserUrl">
													<spring:param name="user" value="${user.uid}" />
												</spring:url>


												<form:form action="${enableUserUrl}">
													<button class="btn btn-link">Enable <span class="manageuserdivider">|</span></button>
													
													 <spring:url
									value="/my-account/manage-users/edit"
									var="editUserUrl">
		
									<spring:param name="user" value="${user.uid}" />
								</spring:url> <a href="${editUserUrl}"><spring:theme
										code="text.company.manageUser.button.edit" text="Edit" /></a>



									<input type="hidden" id="searchname" name="namesearch" value="${namesearch}" />
									<input type="hidden" id="searchaccount" name="acountsearch" value="${namecheck}" />
												</form:form>


											</c:when>
											<c:otherwise>
												<a><spring:theme
														code="text.company.manageusers.button.enableuser"
														text="Enable User" /></a>
											</c:otherwise>
										</c:choose>


									</c:otherwise> --%> <!-- <a href="#">Enable</a>  --> <%-- <spring:url
									value="/my-account/manage-users/edit"
									var="editUserUrl">
		
									<spring:param name="user" value="${user.uid}" />
								</spring:url> <a href="${editUserUrl}"><spring:theme
										code="text.company.manageUser.button.edit" text="Edit" /></a> --%>

										</td>
									
								</sec:authorize>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div>&nbsp;</div>
			<div class="visible-lg visible-md visible-sm ">


				<div class="col-lg-7 col-md-7 col-xs-6 addNewUserBtnHolder">
					<ycommerce:testId code="User_AddUser_button">
						<a href="${manageUsersUrl}" class="control-label searchResultText"><strong><span
								class="fa fa-plus-circle fm_fntRed"></span> <spring:theme code="text.company.addNewUser" text="Add New User" /></strong></a>
					</ycommerce:testId>
				</div>



				<input type="hidden" id="noOfRecords"
					value="${ProductCountPerPage }" /> <input type="hidden"
					id="sortCode" value="${sortCode}" />
				<div class="col-lg-2 col-md-3 col-xs-2">
					<form class="form-horizontal">
						<div class="form-group clearfix">
							<div
								class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block pull-left">
								<label class="control-label" for="display">View &nbsp;</label>
							</div>
							<div
								class="visible-lg-inline-block visible-md-inline-block visible-sm-inline-block">
								<input type="hidden" id="url" name="url"
									value="${manageUserRecordsUrl }" /> <select
									class="form-control" id="display"
									onchange="javascript:goToPageProductCount(this);">
									<c:forEach begin="10" end="20" var="pageNumber" step="5">
										<c:choose>
											<c:when test="${ProductCountPerPage eq pageNumber}">
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
								value="${searchPageData.pagination.currentPage+1}"
								disabled="disabled" /> <label
								class="control-label visible-lg-inline visible-md-inline visible-sm-inline">
								of ${searchPageData.pagination.numberOfPages}</label>
							<spring:url value="${searchUrl}" var="pageNumberUrl"
								htmlEscape="true">
								<spring:param name="noOfRecords" value="${noOfRecords}" />
							</spring:url>

							<c:if test="${hasPreviousPage}">

								<spring:url value="${searchUrl}" var="previousPageUrl"
									htmlEscape="true">
									<spring:param name="page"
										value="${searchPageData.pagination.currentPage - 1}" />
								</spring:url>
								<button type="button"
									class="fa fa-angle-left pagination-prev-page visible-lg-inline visible-md-inline visible-sm-inline"
									value="${previousPageUrl}"
									onclick="javascript:goToPageNumber(this);"></button>
							</c:if>
							<c:if test="${hasNextPage}">

								<spring:url value="${searchUrl}" var="nextPageUrl"
									htmlEscape="true">
									<spring:param name="page"
										value="${searchPageData.pagination.currentPage + 1}" />
								</spring:url>
								<button type="button"
									class="fa fa-angle-right pagination-next-page "
									value="${nextPageUrl}"
									onclick="javascript:goToPageNumber(this);"></button>
							</c:if>


						</div>
					</form>
				</div>
			</div>

		</div>
	</c:if>
	<c:if test="${empty searchPageData.results}">
		<p>
			<spring:theme code="text.company.manageUser.noUser"
				text="You have no users" />
		</p>
	</c:if>
</div>


<script>
	function goToPageNumber(url) {
		var newUrl = url.value;
		var searchBy = document.getElementById("searchBy").value;
		var searchByAccount = document.getElementById("searchByAccount").value;
		var productsCount = document.getElementById("noOfRecords").value;
		var sortCode = document.getElementById("sortCode").value;
		
		var requestQuery = "&noOfRecords="+ productsCount + "&sort=" + sortCode;
		
		if (searchBy != null && searchBy != "") {
			requestQuery =  requestQuery +  "&searchBy="+ searchBy;
		} 
		if(searchByAccount != null && searchByAccount != ""){
			requestQuery =  requestQuery + "&searchByAccount="+searchByAccount;
		}
		//	alert("requestQuery : "+requestQuery)
		document.location.href = url.value + requestQuery;
		
	}
	function goToPageProductCount(count) {
		var url = document.getElementById("url").value;
		document.location.href = url +"?page="+${searchPageData.pagination.currentPage}+"&noOfRecords=" + count.value;
	}
</script>

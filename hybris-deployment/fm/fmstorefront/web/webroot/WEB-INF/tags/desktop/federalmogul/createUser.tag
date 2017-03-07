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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>

<c:if test="${empty saveUrl}">
	<c:choose>
		<c:when test="${not empty fmRegistrationForm.uid}">
			<spring:url value="/my-account/manage-users/edit" var="saveUrl">
				<spring:param name="user" value="${fmRegistrationForm.uid}" />
			</spring:url>
		</c:when>
		<c:otherwise>
			<spring:url value="/my-account/manage-users/create" var="saveUrl" />
		</c:otherwise>
	</c:choose>
</c:if>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel manageUserSection rightHandPanel clearfix">
		<div class="clearfix">
			<div class="">
				<h2 class="text-uppercase"><spring:theme code="text.company.organizationManagement.createuser" text=" Create User" /></h2>
			</div>
			
			<div id="globalMessages">
				<common:globalMessages />
			</div>
			
		</div>

		<section class="tableFilter ">
			<p class="reqField">
				<span class="required fm_fntRed"><spring:theme code="required" text="*"/></span><spring:theme code="user.requiredFields" text="Required Fields" />
			</p>
			<c:choose>
				<c:when test="${not empty fmRegistrationForm.uid}">
					<spring:theme code="text.company.${action}.edituser.title"
						text="${action}" arguments="${fmRegistrationForm.parentB2BUnit}" />
				</c:when>
				<c:otherwise>
					<spring:theme code="text.company.${action}.adduser.title"
						text="${action}" arguments="${param.unit}" />
				</c:otherwise>
			</c:choose>


			<c:choose>
				<c:when test="${not empty fmRegistrationForm.uid}">
					<spring:theme code="" text="" arguments="${fmRegistrationForm.uid}" />
				</c:when>
				<c:otherwise>
					<spring:theme code="" text="" />
				</c:otherwise>
			</c:choose>


			<%-- <c:url value="/fmcustomer/createcustomer" var="submitAction" /> --%>

			<form:form action="${saveUrl}" method="post"
				commandName="fmRegistrationForm">
				<div class="createuserpage">
					<div class="manageUserForm clearfix">


						<%-- <div class=" ">
						<label class="">Title<span class="fm_fntRed">*</span></label>

						<form:select idKey="user.title" labelKey="user.title"
							path="titleCode" mandatory="true" skipBlank="false"
							skipBlankMessageKey="form.select.empty"
							class="form-control width220">
							<c:forEach items="${titleData}" var="titleData">
								<option value="${titleData.code}">${titleData.name}</option>
							</c:forEach>
						</form:select>
					</div> --%>

						<sec:authorize ifNotGranted="ROLE_FMB2SB">
							
								<div class="form-group  regFormFieldGroup">
									<label class="" for="whatdescribes">What Best Describes
										You?<span class="required fm_fntRed"><spring:theme code="required" text="*"/></span>
									</label>
									<form:select path="usertypedesc" id="whatdescribes" name="cars"
										class="form-control width165" onchange="setParentUnits()"
										 Required="true">
										<option value="" selected="selected">-Select-</option>
										<c:forEach items="${fmusertype}" var="usertype">

													<c:choose>
														<c:when test="${usertype eq sessionusertype}">
															<c:if
																test="${usertype.code eq 'WarehouseDistributorLightVehicle'}">
																<option value="${usertype}" selected="selected">Warehouse
																	Distributor Light Vehicle</option>
															</c:if>
															<c:if
																test="${usertype.code eq 'WarehouseDistributorCommercialVehicle'}">
																<option value="${usertype}" selected="selected">Warehouse
																	Distributor Commercial Vehicle</option>
															</c:if>
															<c:if test="${usertype.code eq 'Retailer'}">
																<option value="${usertype}" selected="selected">Retailer</option>
															</c:if>
															<c:if test="${usertype.code eq 'JobberPartStore'}">
																<option value="RETAILER" selected="selected">Jobber
																</option>
															</c:if>
															<c:if test="${usertype.code eq 'CSR'}">
																<option value="CSR">CSR
																</option>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:if
																test="${usertype.code eq 'WarehouseDistributorLightVehicle'}">
																<option value="${usertype}">Warehouse
																	Distributor Light Vehicle</option>
															</c:if>
															<c:if
																test="${usertype.code eq 'WarehouseDistributorCommercialVehicle'}">
																<option value="${usertype}">Warehouse
																	Distributor Commercial Vehicle</option>
															</c:if>
															<c:if test="${usertype.code eq 'Retailer'}">
																<option value="${usertype}">Retailer</option>
															</c:if>
															<c:if test="${usertype.code eq 'JobberPartStore'}">
																<option value="RETAILER">Jobber</option>
															</c:if>
															<c:if test="${usertype.code eq 'CSR'}">
																<option value="CSR">CSR
																</option>
															</c:if>
														</c:otherwise>
													</c:choose>
										</c:forEach>
									</form:select>

									<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="text.company.createuser.type" text="Please Select a user type" /></div>
								</div>
							
						</sec:authorize>
						<formElement:formSelectBox idKey="user.title"
							labelKey="user.title" path="titleCode" mandatory="true" selectCSSClass="form-control width220"
							skipBlank="true" skipBlankMessageKey="form.select.empty"
							items="${titleData}" />
						<div class="form-group  regFormFieldGroup ">
							<label class="" for="firstName"><spring:theme code="user.firstName" text="First Name" /> <span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>
							<form:input type="text" id="firstName" name="firstName"
								class="form-control width375" path="firstName" Required="true" />
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.firstName.error" text="Please
								enter First Name" /></div>
						</div>

						<!-- 	<div class="">
						<label class="">First Name<span class="fm_fntRed">*</span></label>
						<input type="text" name="userid" placeholder=""
							class="form-control width375">

					</div> -->


						<div class="form-group  regFormFieldGroup ">
							<label class="" for="lastName"><spring:theme code="user.lastName" text="Last Name" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>
							<form:input type="text" id="lastName" name="lastName"
								placeholder="" class="form-control width375" path="lastName"
								Required="true" />
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.lastName.error" text="Please
								enter Last Name" /></div>
						</div>



						<div class="">


							<label class="" for="pass"><spring:theme code="user.password" text="Password" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>

							<form:input type="password" labelKey="profile.currentPassword"
								path="password" inputCSS="text password" mandatory="true"
								value="" name="CurrentPassword" class="form-control width175"
								id="CurrentPassword" Required="true" />

							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.password.error" text="Please
								enter Password" /></div>
							<%-- <form:input id="pass" type="password" name="psw" placeholder=""
							path="password" class="form-control width175" /> --%>
						</div>

						<div class="">
							<label class=""><spring:theme code="user.status" text="Status" /><span class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label></br>
							<form:checkbox path="isLoginDisabled" data-toggle="toggle"
								data-on="Disabled" data-off="Enabled" data-onstyle="danger"
								data-offstyle="success" />
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.status.error" text="Please
								select the Status" /></div>
						</div>
						<div class="">

							<label class="" for="workContNumber"><spring:theme code="user.workContactNumber" text="Work Contact Number" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>
							<form:input type="tel" id="workContactNo" name="workContactNo"
								placeholder="(555)555-5555" class="form-control width175"
								path="workContactNo" Required="true" />
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.workContactNumber.error" text="Please
								enter Work Contact Number" /></div>
						</div>
						<div class="">

							<label class="" for="phoneno"><spring:theme code="user.personalContactNumber" text="Personal Contact Number" /></label>
							<form:input type="tel" id="phoneno" name="phoneno"
								placeholder="(555)555-5555" class="form-control width175"
								path="phoneno" />
						</div>



						<div class="">

							<label class="" for="email"><spring:theme code="user.emailAddress" text="Email Address" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label> ${email}
							<form:input type="email" id="email" name="email" placeholder=""
								class="form-control width375" path="email" Required="true" onblur="validateCSREmail()"/>
							<div class="fm_fntRed" id="errorEmail"></div>
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.emailAddress.error" text="Please
								enter Email Address" /></div>
						</div>

						<sec:authorize ifNotGranted="ROLE_FMB2SB,ROLE_FMB2BB">

							<div class="" id="parentDiv">

								<label class=""><spring:theme code="user.parentBusinessUnit" text="Parent Business Unit" /><span
									class="required fm_fntRed"><spring:theme code="required" text="*"/></span>
								</label>
								<form:input type="text" id="parentUnit" name="parentUnit"
									placeholder="" class="form-control width375"
									path="parentB2BUnit" onBlur="setunitsCreatePage()"/>
								<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.parentBusinessUnit.error" text="Please
									enter Parent Business Unit" /></div>
								<!-- 	<span id="userAccountIdsList"> </span> -->

								<!-- 		<div id="livesearch" class="livesearch"></div> -->
								<%-- <form:select path="parentB2BUnit" id="parentUnit"
								name="parentUnit" class="form-control"
								disabled="${not empty param.unit and not empty param.role}"
								skipBlankMessageKey="form.select.empty">

								<c:forEach items="${fmB2BUnitList}" var="fmB2BUnitListVar">

									<option value="${fmB2BUnitListVar.key}"
										${fmB2BUnitListVar.key == selectedUnit ? 'selected' : ''}>${fmB2BUnitListVar.key}</option>

								</c:forEach>
							</form:select> --%>


								<%--formElement:formSelectBox idKey="parentUnit"
							skipBlank="false" labelKey="Parent Business Unit"
							path="parentB2BUnit" mandatory="true" items="${b2bUnits}"
							disabled="${not empty param.unit and not empty param.role}"
							skipBlankMessageKey="form.select.empty" /> --%>


							</div>

						</sec:authorize>


						<sec:authorize ifAnyGranted="ROLE_FMB2SB,ROLE_FMB2BB">

							<div class="">

								<label class=""><spring:theme code="user.parentBusinessUnit" text="Parent Business Unit" /><span
									class="required fm_fntRed"><spring:theme code="required" text="*"/></span>
								</label>

								<form:select path="parentB2BUnit" id="parentUnit"
									name="parentUnit" class="form-control"
									disabled="${not empty param.unit and not empty param.role}"
									skipBlankMessageKey="form.select.empty" Required="true">
									<%-- <option value="${parentUnit}">${parentUnit}</option> --%>
								
								<c:forEach items="${parentUnit}" var="fmB2BUnitListVar">
									<c:choose>
										<c:when test="${fmB2BUnitListVar.uid eq fmRegistrationForm.parentB2BUnit}">
											<option value="${fmB2BUnitListVar.uid}" selected="selected">${fmB2BUnitListVar.uid}</option>					
										</c:when>
										<c:otherwise>
											<option value="${fmB2BUnitListVar.uid}">${fmB2BUnitListVar.uid}</option>						
										</c:otherwise>
									</c:choose>		
								</c:forEach>
								
								</form:select>
								<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.parentBusinessUnit.error" text="Please
									enter Parent Business Unit" /></div>
							</div>
						</sec:authorize>




						<%-- 		<form:checkboxes idKey="text.company.user.roles" class="" values="${roles}"
						labelKey="text.company.user.roles" path="roles" mandatory="false"
						items="${roles}"
						disabled="${not empty param.unit and not empty param.role}"
						typeIdentifier="String" element="span class='checkbox'"/>  --%>

						<div class="create-fmroles">

							<%-- <formElement:formCheckboxes idKey="text.company.user.roles"
							labelKey="text.company.user.roles" path="roles" mandatory="false"
							items="${roles}"
							disabled="${not empty param.unit and not empty param.role}"
							typeIdentifier="String" /> --%>
							<label class="" for="fmRole"><spring:theme code="user.accessRequired" text="Access Required" /><span
								class="required fm_fntRed">*</span></label><br />

							<!--<c:choose>
								<c:when test="${selectedFMRole eq 'FMAdminGroup'}">
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="Customer Admin" value="FMAdminGroup" checked="checked" />
									<br />
								</c:when>
								<c:otherwise>
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="Customer Admin" value="FMAdminGroup" />
									<br />
								</c:otherwise>
							</c:choose>-->

							<c:choose>
								<c:when test="${selectedFMRole eq 'FMFullAccessGroup'}">
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="Full Access" value="FMFullAccessGroup"
										checked="checked" />
									<br />
								</c:when>
								<c:otherwise>
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="Full Access" value="FMFullAccessGroup" />
									<br />
								</c:otherwise>

							</c:choose>

							<c:choose>
								<c:when test="${selectedFMRole eq 'FMNoInvoiceGroup'}">
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="Limited Access - Can Place Orders / Cannot View Invoices"
										value="FMNoInvoiceGroup" checked="checked" />
									<br />
								</c:when>
								<c:otherwise>
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="Limited Access - Can Place Orders / Cannot View Invoices"
										value="FMNoInvoiceGroup" />
									<br />
								</c:otherwise>
							</c:choose>

							<c:choose>
								<c:when test="${selectedFMRole eq 'FMViewOnlyGroup'}">
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="View Only - Cannot Place Orders / View Invoices"
										value="FMViewOnlyGroup" checked="checked" />
									<br />
								</c:when>
								<c:otherwise>
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="View Only - Cannot Place Orders / View Invoices"
										value="FMViewOnlyGroup" />
									<br />
								</c:otherwise>
							</c:choose>




							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.accessRequired.error" text="Please
								select a Access" /></div>

						</div>
						<%-- <div class="internalusersRole" style="display:none">
								<label class="" for="fmRole"><spring:theme code="user.accessRequired" text="Access Required" /><span
								class="required fm_fntRed">*</span></label><br />
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
										label="CSR" value="FMCSR" checked="checked" />
									<br />
								<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.accessRequired.error" text="Please
								select a Access" /></div>
						</div>

						<div class="susRole" style="display:none">
								<label class="" for="fmRole"><spring:theme code="user.accessRequired" text="Access Required" /><span
								class="required fm_fntRed">*</span></label><br />
									<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOR" checked="checked" /> 
							<br />
								<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.accessRequired.error" text="Please
								select a Access" /></div>
						</div> --%>
						
						<div class="panel  panel-primary panel-frm panel-frm-filled "
							style="display: none">
							<div class="form-group regFormFieldGroup form-alerts">
								<ul class="checkboxGroup">
									<li class="list-group-item"><label for="rewards">
											<form:checkbox id="rewards" path="loyaltySignup" />
											&nbsp;Enroll in Federal-Mogul Motorparts Rewards
									</label></li>
									<li class="list-group-item"><label for="techAcademy">
											<form:checkbox id="techAcademy"
												path="techAcademySubscription" />&nbsp; I am interested in
											learning more about Federal-Mogul's Technical Academy
									</label></li>
									<li class="list-group-item"><label for="alerts"> <form:checkbox
												id="alerts" path="newProductAlerts" />&nbsp; Send me new
											product alerts
									</label></li>
									<li class="list-group-item"><label for="promo"> <form:checkbox
												id="promo" path="promotionInfoSubscription" />&nbsp; Send
											me information about new promotions
									</label></li>
									<li class="list-group-item"><label for="subscribe">
											<form:checkbox id="subscribe" path="newsLetterSubscription" />
											&nbsp;Subscribe to Federal-Mogul Motorparts Newsletters
									</label></li>
								</ul>
							</div>


						</div>


						<sec:authorize ifAnyGranted="ROLE_FMB2SB">

							<div class="form-group regFormFieldGroup">
								<ul class="checkboxGroup">
									<li class="list-group-item"><label for="rewards">
											<form:checkbox id="rewards" path="loyaltySignup" />
											&nbsp;Enroll in Federal-Mogul Motorparts Rewards
									</label></li>
									<li class="list-group-item"><label for="techAcademy">
											<form:checkbox id="techAcademy"
												path="techAcademySubscription" />&nbsp; I am interested in
											learning more about Federal-Mogul's Technical Academy
									</label></li>
									<li class="list-group-item"><label for="alerts"> <form:checkbox
												id="alerts" path="newProductAlerts" />&nbsp; Send me new
											product alerts
									</label></li>
									<li class="list-group-item"><label for="promo"> <form:checkbox
												id="promo" path="promotionInfoSubscription" />&nbsp; Send
											me information about new promotions
									</label></li>
									<li class="list-group-item"><label for="subscribe">
											<form:checkbox id="subscribe" path="newsLetterSubscription" />
											&nbsp;Subscribe to Federal-Mogul Motorparts Newsletters
									</label></li>
								</ul>
							</div>
						</sec:authorize>


					</div>
				</div>
				<ycommerce:testId code="User_Save_button">
					<button type="submit" class="btn btn-sm btn-fmDefault topMargn_25">
<spring:theme code="user.submit" text="Submit" /></button>
				</ycommerce:testId>



				<!-- <button class="btn btn-sm btn-fmDefault topMargn_25" type="submit">Submit</button> -->
			</form:form>
		</section>

	</div>
</div>
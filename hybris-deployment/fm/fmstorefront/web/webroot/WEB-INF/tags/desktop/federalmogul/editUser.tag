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
<%@ taglib prefix="formElement"
	tagdir="/WEB-INF/tags/desktop/formElement"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:if test="${empty saveUrl}">
	<c:choose>
		<c:when test="${not empty fmRegistrationForm.uid}">
			<spring:url value="/my-account/manage-users/edit" var="saveUrl">
				<spring:param name="user" value="${fmRegistrationForm.uid}" />
<%-- ${fmRegistrationForm.uid} --%>
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
				<h2 class="text-uppercase"><spring:theme code="text.Edituser" text="Update User"/> </h2>
			</div>
			 <div id="globalMessages">
				<common:globalMessages />
			</div>
		</div>
		<section class="tableFilter ">
			<p class="reqField">
				<span class="fm_fntRed"><spring:theme code="required" text="*"/></span><spring:theme code="user.requiredFields" text="Required Fields"/> 
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



			<%-- <form action="${saveUrl}" method="post" class="manageUserForm" commandName="b2BCustomerForm"> --%>

			<form:form action="${saveUrl}" method="post"
				commandName="fmRegistrationForm" novalidate="novalidate">
				<div class="edituserpage">
					<div class="manageUserForm clearfix">

						



						<formElement:formSelectBox idKey="user.title"
							labelKey="user.title" path="titleCode" mandatory="false"
							skipBlank="false" skipBlankMessageKey="form.select.empty" 
							items="${titleData}" selectCSSClass="form-control width220"/>


						<form:input type="hidden" name="uid" path="uid" id="uid" />


						<%-- <formElement:formInputBox idKey="user.firstName"
						labelKey="user.firstName" path="firstName" inputCSS="text"
						mandatory="true" />
					<formElement:formInputBox idKey="user.lastName"
						labelKey="user.lastName" path="lastName" inputCSS="text"
						mandatory="true" /> --%>



						<div class="form-group  regFormFieldGroup ">
							<label class="" for="firstName"><spring:theme code="user.firstName" text="First Name" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>
							<form:input type="text" id="firstName" name="firstName"
								class="form-control width375" path="firstName" Required="true" />
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.firstName.error" text="Please
								enter First Name" /></div>
						</div>



						<div class="form-group  regFormFieldGroup ">
							<label class="" for="lastName"><spring:theme code="user.lastName" text="Last Name" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>
							<form:input type="text" id="lastName" name="lastName"
								placeholder="" class="form-control width375" path="lastName" Required="true"/>
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.lastName.error" text="Please
								enter Last Name" /></div>
						</div>

						<div class="">


							<label class="" for="CurrentPassword"><spring:theme code="user.password" text="Password" /></label>

							<form:input type="password" labelKey="profile.currentPassword"
								path="password" inputCSS="text password" mandatory="true"
								value="" name="CurrentPassword" class="form-control width175"
								id="CurrentPassword" Required="true"/>
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.password.error" text="Please
								enter Password" /></div>

							<%-- <formElement:formInputBox idKey="user.password"
							labelKey="Password" path="password" inputCSS="text password"
							mandatory="true" /> --%>

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

							<label class="" for="workContactNo">Work Contact Number</label>
							<form:input type="tel" id="workContactNo" name="workContactNo"
								placeholder="(555)555-5555" class="form-control width175"
								path="workContactNo" Required="false"/>
							
						</div>
						<div class="">

							<label class="" for="phoneno"><spring:theme code="user.personalContactNumber" text="Personal Contact Number" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label>
							<form:input type="tel" id="phoneno" name="phoneno"
								placeholder="(555)555-5555" class="form-control width175"
								path="phoneno" Required="true"/>
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.personalContactNumber.error" text="Please enter Personal Contact Number" /></div>
						</div>
						<div class="">

							<label class="" for="email"><spring:theme code="user.emailAddress" text="Email Address" /><span
								class="required fm_fntRed"><spring:theme code="required" text="*"/></span></label> ${email}
							<form:input type="email" id="email" name="email" placeholder=""
								class="form-control width375" path="email" Required="true"/>
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.emailAddress.error" text="Please
								enter Email Address" /></div>
						</div>
						<div class="">
								<label class="">Alternative Account
								</label>
							<form:input type="text" id="AlternativeAccount" name="AlternativeAccount"
								class="form-control width5000"
								path="AlternativeAccountId" Required="false"/>
							</div>

						<sec:authorize ifNotGranted="ROLE_FMB2SB,ROLE_FMB2BB">


							<div class="">

								<label class=""><spring:theme code="user.parentBusinessUnit" text="Parent Business Unit" /><span
									class="required fm_fntRed"><spring:theme code="required" text="*"/></span>
								</label>
								<%-- <form:input type="text" id="parentUnit" name="parentUnit" placeholder=""
							class="form-control width375" path="parentB2BUnit" onBlur="searchUnits()"/> --%>
								<form:input type="text" id="parentUnit" name="parentUnit"
									placeholder="" class="form-control width375"
									path="parentB2BUnit" Required="true"/>
								<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.parentBusinessUnit.error" text="Please
									enter Parent Business Unit" /></div>
								<!-- 	<span id="userAccountIdsList"> </span> -->

								<!-- 			<div id="livesearch" class="livesearch"></div> -->

								<%--  <form:select path="parentB2BUnit" id="parentUnit"
							name="parentUnit" class="form-control"
							disabled="${not empty param.unit and not empty param.role}"
							skipBlankMessageKey="form.select.empty">
										<option value="${parentUnit}">${parentUnit}</option>
									 <option value="${fmRegistrationForm.parentB2BUnit}">${fmRegistrationForm.parentB2BUnit}</option> 
							
							<c:forEach items="${fmB2BUnitList}" var="fmB2BUnitListVar">
							
								<option value="${fmB2BUnitListVar.key}">${fmB2BUnitListVar.key}</option>
							</c:forEach>
						</form:select> --%>

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
									<%-- 	<option value="${parentUnit}">${parentUnit}</option> --%>
									<option value="${fmRegistrationForm.parentB2BUnit}">${fmRegistrationForm.parentB2BUnit}</option>
									<%-- 	
							<c:forEach items="${fmB2BUnitList}" var="fmB2BUnitListVar">
								<option value="${fmB2BUnitListVar.key}">${fmB2BUnitListVar.key}</option>
							</c:forEach> --%>
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

						<div>


							<%-- <formElement:formCheckboxes idKey="text.company.user.roles"
						labelKey="text.company.user.roles" path="roles" mandatory="false"
						items="${roles}"
						disabled="${not empty param.unit and not empty param.role}"
						typeIdentifier="String" /> --%>

							<label class="" for="fmRole"><spring:theme code="user.accessRequired" text="Access Required" /><span
								class="fm_fntRed"><spring:theme code="required" text="*"/></span></label><br />



							<c:if test="${fmRole eq 'FMAdminGroup'}">
								
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Full Access" value="FMFullAccessGroup" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Limited Access - Can Place Orders / Cannot View Invoices"
									value="FMNoInvoiceGroup" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="View Only - Cannot Place Orders / View Invoices"
									value="FMViewOnlyGroup" />
								<br />
								<%--	<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOGroup" />
							<br /> --%>



							</c:if>

							<c:if test="${fmRole eq 'FMFullAccessGroup'}">
								
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Full Access" value="FMFullAccessGroup" checked="checked" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Limited Access - Can Place Orders / Cannot View Invoices"
									value="FMNoInvoiceGroup" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="View Only - Cannot Place Orders / View Invoices"
									value="FMViewOnlyGroup" />
								<br />
								<%-- <form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOGroup" />
							<br />  --%>

							</c:if>

							<c:if test="${fmRole eq 'FMNoInvoiceGroup'}">
								
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Full Access" value="FMFullAccessGroup" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Limited Access - Can Place Orders / Cannot View Invoices"
									value="FMNoInvoiceGroup" checked="checked" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="View Only - Cannot Place Orders / View Invoices"
									value="FMViewOnlyGroup" />
								<br />
								<%-- <form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOGroup" /> 
							<br /> --%>
							</c:if>

							<c:if test="${fmRole eq 'FMViewOnlyGroup'}">
								
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Full Access" value="FMFullAccessGroup" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="Limited Access - Can Place Orders / Cannot View Invoices"
									value="FMNoInvoiceGroup" />
								<br />
								<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
									label="View Only - Cannot Place Orders / View Invoices"
									value="FMViewOnlyGroup" checked="checked" />
								<%-- <form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOGroup" />
							<br />  --%>
							</c:if>



				<c:if test="${fmRole eq 'FMBUVOR'}">
							<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="CSR" value="FMCSR" />
							<br />
							 <form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOR" checked="checked" /> 
							<br />
						</c:if>
<c:if test="${fmRole eq 'FMCSR'}">
							<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="CSR" value="FMCSR" checked="checked" />
							<br />
							 <form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOR"/> 
							<br />
						</c:if>
						
								
						<c:if test="${fmRole eq 'FMBUVORSUS'}">
							 <form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="Business User view only role - Do everything a CSR does except place an order"
								value="FMBUVOR" checked="checked" /> 
							<br />
						</c:if>
						<c:if test="${fmRole eq 'FMCSRINTERNAL'}">
							<form:radiobutton path="fmRole" id="fmRole" name="fmRole"
								label="CSR" value="FMCSR" checked="checked" />
							<br />
						</c:if>
							<div class="errorMsg fm_fntRed" style="display: none;"><spring:theme code="user.accessRequired.error" text="Please
								select a Access" /></div>
						</div>

						<%-- 	 <c:forEach items="${roles}" var="roles">

				<form:radiobutton path="roles" id="roles" name="roles" label="${roles.name}" value=""/></br>
				</c:forEach> --%>


						<c:if
							test="${fmRegistrationForm.usertypedesc.code eq 'JobberPartStore' or fmRegistrationForm.usertypedesc.code eq 'ShopOwnerTechnician'}">

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


						</c:if>
					</div>
				</div>

				<ycommerce:testId code="User_Save_button">
					<button type="submit" class="btn btn-sm btn-fmDefault topMargn_25"><spring:theme code="user.submit" text="Submit" /></button>
				</ycommerce:testId>
				<!-- <button class="btn btn-sm btn-fmDefault topMargn_25" type="submit">Submit</button> -->
			</form:form>

		</section>
	</div>


</div>
</div>

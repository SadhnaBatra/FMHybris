<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<div class="panel panel-default manageAccountLinks clearfix">
	<div class="panel-body orgMangPanel">
		<h3 class="text-uppercase"><spring:theme
							code="header.link.account" text="My Account" /></h3>
		<ul class="">
			<li class="${selected eq 'profile' ? 'nav_selected' : ''}"><c:url
					value="/my-fmaccount/profile" var="encodedUrl" /> <ycommerce:testId
					code="myFMAccount_profile_navLink">
					<c:if test="${clickedlink eq 'clickedProfile'}">
					<a href="${encodedUrl}"  class="selected"><spring:theme
							code="text.account.profile" text="Profile" /><span
						class=""></span></a>
						</c:if>
						<c:if test="${clickedlink ne 'clickedProfile'}">
					<a href="${encodedUrl}" ><spring:theme
							code="text.account.profile" text="Profile" /><span
						class="linkarow fa fa-angle-right "></span></a>
						</c:if>
				</ycommerce:testId></li>


			<sec:authorize
				ifNotGranted="ROLE_FMTAXADMINGROUP,ROLE_B2BADMINGROUP,ROLE_FMADMINGROUP">
				<li class="${selected eq 'address-book' ? 'nav_selected' : ''}">
					<c:url value="/my-fmaccount/address-book" var="encodedUrl" /> <ycommerce:testId
						code="myFMAccount_addressBook_navLink">

<c:if test="${clickedlink eq 'ClickedAddressbook'}">

						<a href="${encodedUrl}"  class="selected"><spring:theme
								code="text.account.addressBook" text="Address Book" /><span
							class=""></span></a>
</c:if>
<c:if test="${clickedlink ne 'ClickedAddressbook'}">

						<a href="${encodedUrl}"><spring:theme
								code="text.account.addressBook" text="Address Book" /><span
							class="linkarow fa fa-angle-right "></span></a>
</c:if>

					</ycommerce:testId>
				</li>
			</sec:authorize>
			<sec:authorize ifAnyGranted="ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP">
				<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMB2SB">
					<sec:authorize ifNotGranted="ROLE_FMCSR">
						<li class="${selected eq 'address-book' ? 'nav_selected' : ''}">
							<spring:url value="/my-fmaccount/adminAddress-book"
								var="encodedUrl">

								<%-- <spring:param name="userID" value="${user.uid}" /> --%>
							</spring:url> <spring:url value="/my-fmaccount/address-book"
								var="addressBookUrl"></spring:url> <ycommerce:testId
								code="myFMAccount_adminAddressBook_navLink">
								<c:if test="${clickedlink eq 'ClickedAddressbook'}">
								<a href="${addressBookUrl}" class="selected"><spring:theme
										code="text.account.addressBook" text="Admin Address Book" /><span
									class="selected"></span></a>
									</c:if>
									
									<c:if test="${clickedlink ne 'ClickedAddressbook'}">
								<a href="${addressBookUrl}"><spring:theme
										code="text.account.addressBook" text="Admin Address Book" /><span
									class="linkarow fa fa-angle-right "></span></a>
									</c:if>
							<spring:url value="/my-fmaccount/order-history" var="encodedUrl" />
							<li>
							<c:if test="${clickedlink eq 'clickedOrderHistory'}">
							<a href="${encodedUrl}" class="selected"><spring:theme code="text.account.orderhistory" text="Order History" />  <span
									class=""></span></a>
									</c:if>
									
									<c:if test="${clickedlink ne 'clickedOrderHistory'}">
							<a href="${encodedUrl}"><spring:theme code="text.account.orderhistory" text="Order History" />  <span
									class="linkarow fa fa-angle-right "></span></a>
									</c:if>
									</li>
							<spring:url value="/orderupload/quick-order" var="encodedUrl" />
							<li>
							<c:if test="${clickedlink eq 'ClickedQuickOrder'}">
							<a href="${encodedUrl}" class="selected">
<spring:theme code="text.account.quickOrder" text="Quick Order" /> <span
									class="selected"></span></a>
									</c:if>
									
										<c:if test="${clickedlink ne 'ClickedQuickOrder'}">
							<a href="${encodedUrl}">
<spring:theme code="text.account.quickOrder" text="Quick Order" /> <span
									class="linkarow fa fa-angle-right "></span></a>
									</c:if>
									
									</li>
									<spring:url value="/uploadOrder/uploadorderstatus" var="encodedUrl" />
									<li><c:if test="${clickedlink eq 'ClickedUploadOrder'}">
								<a href="${encodedUrl}" class="selected"><spring:theme code="text.account.uploadOrderStatus" text="Upload Order Status" /> <span
						class="selected"></span></a></c:if>

							<c:if test="${clickedlink ne 'ClickedUploadOrder'}">
							<a href="${encodedUrl}"><spring:theme code="text.account.uploadOrderStatus" text="Upload Order Status" /> <span
						class="linkarow fa fa-angle-right "></span></a></c:if>
</li>

							</ycommerce:testId>
						</li>
					</sec:authorize>
				</sec:authorize>
			</sec:authorize>






			<sec:authorize ifNotGranted="ROLE_FMTAXADMINGROUP,ROLE_FMB2T">
				<sec:authorize
					ifAnyGranted="ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP,ROLE_FMCSR,ROLE_FMBUVOR">
					<sec:authorize
						ifAnyGranted="ROLE_FMB2BB,ROLE_FMB2SB,ROLE_FMCSR,ROLE_FMBUVOR">
						<c:if test="${csrAccountEmulation eq 'true'}">
							<spring:url value="/my-fmaccount/order-history" var="encodedUrl" />
							<li><c:if test="${clickedlink eq 'clickedOrderHistory'}">
<a href="${encodedUrl}" class="selected"><spring:theme code="text.account.orderhistory" text="Order History" /> <span
						class=""></span></a></c:if>
<c:if test="${clickedlink ne 'clickedOrderHistory'}">
<a href="${encodedUrl}"><spring:theme code="text.account.orderhistory" text="Order History" /> <span
						class="linkarow fa fa-angle-right "></span></a></c:if></li>
							<spring:url value="/orderupload/quick-order" var="encodedUrl" />
							<li><c:if test="${clickedlink eq 'ClickedQuickOrder'}">
<a href="${encodedUrl}" class="selected">
<spring:theme code="text.account.quickOrder" text="Quick Order" /><span
						class="selected"></span></a></c:if>
<c:if test="${clickedlink ne 'ClickedQuickOrder'}">
<a href="${encodedUrl}">
<spring:theme code="text.account.quickOrder" text="Quick Order" /><span
						class="linkarow fa fa-angle-right "></span></a></c:if>
</li>
				<spring:url value="/uploadOrder/uploadorderstatus" var="encodedUrl" />
				<li><c:if test="${clickedlink eq 'ClickedUploadOrder'}">
<a href="${encodedUrl}" class="selected"><spring:theme code="text.account.uploadOrderStatus" text="Upload Order Status" /> <span
						class=""></span></a></c:if>

<c:if test="${clickedlink ne 'ClickedUploadOrder'}">
<a href="${encodedUrl}"><spring:theme code="text.account.uploadOrderStatus" text="Upload Order Status" /> <span
						class="linkarow fa fa-angle-right "></span></a></c:if>
</li>
						</c:if>
					</sec:authorize>
				</sec:authorize>
			</sec:authorize>


			<sec:authorize ifNotGranted="ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP,ROLE_FMB2T">
				<spring:url value="/my-fmaccount/order-history" var="encodedUrl" />
				<li>

<c:if test="${clickedlink eq 'clickedOrderHistory'}">
<a href="${encodedUrl}" class="selected"><spring:theme code="text.account.orderhistory" text="Order History" /> <span
						class=""></span></a></c:if>
<c:if test="${clickedlink ne 'clickedOrderHistory'}">
<a href="${encodedUrl}"><spring:theme code="text.account.orderhistory" text="Order History" /> <span
						class="linkarow fa fa-angle-right "></span></a></c:if>


</li>
				<spring:url value="/orderupload/quick-order" var="encodedUrl" />
				<li>

<c:if test="${clickedlink eq 'ClickedQuickOrder'}">
<a href="${encodedUrl}" class="selected">
<spring:theme code="text.account.quickOrder" text="Quick Order" /><span
						class=""></span></a></c:if>
<c:if test="${clickedlink ne 'ClickedQuickOrder'}">
<a href="${encodedUrl}">
<spring:theme code="text.account.quickOrder" text="Quick Order" /><span
						class="linkarow fa fa-angle-right "></span></a></c:if>

</li>
				<spring:url value="/uploadOrder/uploadorderstatus" var="encodedUrl" />
				<li>

<c:if test="${clickedlink eq 'ClickedUploadOrder'}">
<a href="${encodedUrl}" class="selected"><spring:theme code="text.account.uploadOrderStatus" text="Upload Order Status" /> <span
						class=""></span></a></c:if>

<c:if test="${clickedlink ne 'ClickedUploadOrder'}">
<a href="${encodedUrl}"><spring:theme code="text.account.uploadOrderStatus" text="Upload Order Status" /> <span
						class="linkarow fa fa-angle-right "></span></a></c:if>



</li>
			</sec:authorize>

			<!-- Balaji -- Start Order Return -->
			<%-- <li><c:url value="/my-fmaccount/return-request" var="encodedUrl" /> 
                     <a href="${encodedUrl}" class="${selectedLink eq 'ReturnRequest' ? 'selected' : ''}"> 
                                Return <span class="linkarow fa fa-angle-right "></span> 
                     </a></li> --%>
			<%-- <sec:authorize
				ifNotGranted="ROLE_FMTAXADMINGROUP,ROLE_B2BADMINGROUP,ROLE_FMCSR">
				<li><c:url value="/my-fmaccount/return-history"
						var="encodedUrl" /> <a href="${encodedUrl}"
					class="${selectedLink eq 'ReturnHistory' ? 'selected' : ''}">
						Return History<span class="linkarow fa fa-angle-right "></span>
				</a></li>
			</sec:authorize>

			<sec:authorize ifAnyGranted="ROLE_FMCSR">
				<li><c:url value="/my-fmaccount/return-history"
						var="encodedUrl" /> <a href="${encodedUrl}"
					class="${selectedLink eq 'ReturnHistory' ? 'selected' : ''}">
						Return History<span class="linkarow fa fa-angle-right "></span>
				</a></li>
			</sec:authorize> --%>

			<!-- Balaji -- End Order Return -->
			<!-- <li><a href="#">Returns <span class="linkarow fa fa-angle-right "></span></a></li> -->

			<sec:authorize
				ifAnyGranted="ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP,ROLE_FMCSR,ROLE_FMBUVOR">
				<spring:url value="/my-company/organization-management/manage-users"
					var="encodedUrl" />
				<sec:authorize ifNotGranted="ROLE_FMBUVOR">
					<li>						
						<a href="${encodedUrl}" ${clickedlink == 'manageusers' ? 'class="selected "' : 'class=""'}><spring:theme code="text.company.manageUsers" text="Manage Users" /><span
							${clickedlink == 'manageusers' ? 'class=""' : 'class="linkarow fa fa-angle-right"'}></span></a>	
					</li>
				</sec:authorize>
			</sec:authorize>
		
				 <sec:authorize
						ifAnyGranted="ROLE_FMCSR,ROLE_FMBUVOR">
						<li>
							<spring:url value="/csr-emulation/application-usage" var="encodedUrl" />
							<c:if test="${clickedlink eq 'ClickedApplicationUsageReport' }" >
							<a href="${encodedUrl}" class="selected"><spring:theme code="text.account.applicationUsageReport" text="Application Usage Report" /><span
									class=" "></span></a>
								</c:if>
								<c:if test="${clickedlink ne'ClickedApplicationUsageReport' }" >
								<a href="${encodedUrl}"><spring:theme code="text.account.applicationUsageReport" text="Application Usage Report" /><span
									class="linkarow fa fa-angle-right  "></span></a>
								</c:if>
								
								
							</li>
						
					</sec:authorize> 
			


			<%-- <sec:authorize
				ifAnyGranted="ROLE_FMJOBBERGROUP,ROLE_FMINSTALLERGROUP">
				<c:url value="/my-fmaccount/taxexemptionpage" var="taxurl" />
				<li><a href="${taxurl}"><spring:theme code="text.account.taxdocument" text="Tax Document" /><span
						class="linkarow fa fa-angle-right "></span></a></li>
			</sec:authorize> 

			<sec:authorize ifAnyGranted="ROLE_FMTAXADMINGROUP">
				<c:url value="/my-fmaccount/taxexemption-approval" var="taxurl" />
				<li><a href="${taxurl}"><spring:theme code="text.account.taxdocument" text="Tax Document" /><span
						class="linkarow fa fa-angle-right "></span></a></li>
			</sec:authorize>

			 	<sec:authorize ifAnyGranted="ROLE_FMCSR">
				<li><a href="#">Loyalty History <span
						class="linkarow fa fa-angle-right "></span></a></li>
			</sec:authorize> --%>
			<sec:authorize ifAnyGranted="ROLE_FMB2T">
				<c:if test="${loyaltystatus eq true}">
					<li><a class="${clickedlink eq 'clickedLoyaltyHistory' ? 'selected' : '' }" href="${contextPath}/${currentsiteUID}/${currentLanguage.isocode}/${currentCurrency.isocode}/loyalty/history">
							<spring:theme code="text.account.loyaltyHistory" text="loyalty History" /> <span
							class="linkarow fa fa-angle-right "></span></a></li>
				</c:if>
			</sec:authorize>
		</ul>
	</div>
</div>
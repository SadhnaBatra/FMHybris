<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="hideHeaderLinks" required="false"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header" %>

<c:set var="whereToBuyURI"><spring:eval expression="@propertyConfigurer.getProperty('wheretobuyurl')" /></c:set>
<c:set var="careersURI"><spring:eval expression="@propertyConfigurer.getProperty('careersURL')" /></c:set>
<c:set var="originalEquipmentURI"><spring:eval expression="@propertyConfigurer.getProperty('originalEquipmentURL')" /></c:set>
<c:set var="mediaURI"><spring:eval expression="@propertyConfigurer.getProperty('mediaURL')" /></c:set>
<c:set var="suppliersURI"><spring:eval expression="@propertyConfigurer.getProperty('suppliersURL')" /></c:set>
<c:set var="investorsURI"><spring:eval expression="@propertyConfigurer.getProperty('investorsURI')" /></c:set>


<%-- Test if the UiExperience is currently overriden and we should show the UiExperience prompt --%>
<c:if
	test="${uiExperienceOverride and not sessionScope.hideUiExperienceLevelOverridePrompt}">
	<c:url value="/_s/ui-experience?level="
		var="clearUiExperienceLevelOverrideUrl" />
	<c:url value="/_s/ui-experience-level-prompt?hide=true"
		var="stayOnDesktopStoreUrl" />
	<div class="backToMobileStore">
		<a href="${clearUiExperienceLevelOverrideUrl}"><span
			class="greyDot">&lt;</span> <spring:theme
				code="text.swithToMobileStore" /></a> <span class="greyDot closeDot"><a
			href="${stayOnDesktopStoreUrl}">x</a></span>
	</div>
</c:if>


<%-- Test if the UiExperience is currently overriden and we should show the UiExperience prompt --%>

<c:if
	test="${uiExperienceOverride and not sessionScope.hideUiExperienceLevelOverridePrompt}">
	<c:url value="/_s/ui-experience?level="
		var="clearUiExperienceLevelOverrideUrl" />
	<c:url value="/_s/ui-experience-level-prompt?hide=true"
		var="stayOnDesktopStoreUrl" />
	<div class="backToMobileStore">
		<a href="${clearUiExperienceLevelOverrideUrl}"><span
			class="greyDot">&lt;</span> <spring:theme
				code="text.swithToMobileStore" /></a> <span class="greyDot closeDot"><a
			href="${stayOnDesktopStoreUrl}">x</a></span>
	</div>
</c:if>

<!-- Anonymous Page -->
<c:if
	test="${customerType ne 'b2bCustomer' and customerType ne 'b2BCustomer' and customerType ne 'b2cCustomer' and customerType ne 'CSRCustomer' and customerType ne 'FMAdmin'}">
	<div class="clearfix">
		<header class="headerRow">
			<div class="container">
				<div class="row">
					<header:language/>
					<div class="col-lg-8  clearfix visible-lg visible-md visible-sm">			   
					   <div class="clearfix ">
							<div class="extreemTopRightContent">
								<ul class="quicklinks pull-right ">
								    <li><a href="${careersURI}"><spring:theme code="eyebrow.nav.careers"/></a></li>
									<li><a href="${originalEquipmentURI}"><spring:theme code="eyebrow.nav.original.equipment"/></a></li>
									<li><a href="${mediaURI}"><spring:theme code="eyebrow.nav.media"/></a></li>
									<li><a href="${suppliersURI}"><spring:theme code="eyebrow.nav.suppliers"/></a></li>
									<li><a href="${investorsURI}"><spring:theme code="eyebrow.nav.investors"/></a></li>
									<li><a href="http://www.fmheavydutyparts.com/" target="_blank"><spring:theme code="eyebrow.nav.heavyduty"/></a></li>
									<li><span class="fa  fa-user"></span>  <a href="<c:url value='/sign-in'/>" class=""><spring:theme code="eyebrow.nav.signin"/></a></li>
								</ul>
							</div>
						</div>
					   			   
						<div class="clearfix ">
							<div class="extreemTopRightContent">
								<ul class="quicklinks pull-right">
								    <li><a href="http://www.partsmatter.com" target="_blank"><spring:theme code="header.parts.matter"/></a></li>
									<li><a href="<c:url value="/support/contact-us/supportgeneralInquiry?complnkname=Contact Form"/>"><span class="glyphicon glyphicon-envelope"></span>
										<spring:theme code="eyebrow.nav.contactus"/></a></li>
									<sec:authorize ifNotGranted="ROLE_FMB2BB">								   
									  	<li><span class="fa  fa-map-marker"></span><a href="${whereToBuyURI}"><spring:theme code="eyebrow.nav.wheretobuy"/></a></li>
									</sec:authorize>						
								</ul>
							</div>
						</div> 
					</div>
				</div>
			</div>
		</header>
	</div>
</c:if>

<c:if
	test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer'}">
	<header class="headerRow">
		<div class="container">
			<div class="row">
				<header:language/>
				<div class="col-lg-8  col-md-9 col-xs-2 clearfix ">
					<div class="clearfix ">
						<div class="extreemTopRightContent">
							<!-- show quick links on other device -->
							<ul class="quicklinks pull-right visible-lg visible-md">
                 				<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
									<li class="DINWebBold"><ycommerce:testId
											code="header_LoggedUser">
											<spring:theme code="header.welcome"
												arguments="${user.firstName} ${user.lastName}"
												htmlEscape="true" />
										</ycommerce:testId></li>
								</sec:authorize>

								<c:if test="${!fn:contains(siteName,'Loyalty')  }" >
								<li><ycommerce:testId code="header_myAccount">
										<a href="${contextPath}/federalmogul/en/USD/my-fmaccount/profile"><span class="fa fa-user"></span> <spring:theme
												code="header.link.account" /></a>
									</ycommerce:testId></li>
								</c:if>
								<c:if test="${fn:contains(siteName,'Loyalty')  }" >
									<li><ycommerce:testId code="header_myAccount">
										<a href="${contextPath}/federalmogul/en/USD/loyalty/history?clear=true&site=federalmogul"><span class="fa fa-user"></span> <spring:theme
												code="header.link.account" /></a>
									</ycommerce:testId></li>
								</c:if>	 
								<!-- Fix as part of FAL-20 remove pipe symbol after signout if no shopping cart  -->
								 <c:if test="${!fn:contains(siteName,'Loyalty')  }">
									<sec:authorize ifNotGranted="ROLE_FMB2T">
										<cms:pageSlot position="MiniCart" var="cart" limit="7">
											<cms:component component="${cart}" element="li"
												class="miniCart" />
										</cms:pageSlot>
									</sec:authorize>
								</c:if>
								<c:if test="${fn:contains(siteName,'Loyalty')  }">
										<cms:pageSlot position="MiniCart" var="cart" limit="7">
											<cms:component component="${cart}" element="li"
												class="miniCart" />
										</cms:pageSlot>
								</c:if> 
								
								<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
									<li class="text-capitalize"><ycommerce:testId
											code="header_signOut">
											<a onclick="return hybrisApiLogout('${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/logout');"><spring:theme
													code="header.link.logout" /></a>
										</ycommerce:testId></li>
								</sec:authorize>

								<sec:authorize ifAnyGranted="ROLE_FMCSR,ROLE_FMBUVOR">
									<c:if test="${csrAccountEmulation eq 'true'}">
										<li class="text-capitalize"><ycommerce:testId
												code="header_signOut">
												<a href="<c:url value='/csr-emulation/end-emulate'/>">End
													Emulate (${accountId}${accountNm}) </a>
											</ycommerce:testId></li>
									</c:if>
								</sec:authorize>

								<c:if test="${fn:contains(siteName,'Loyalty')  }">
									<li><a
										href="${contextPath}/loyalty/${currentLanguage.isocode}/${currentCurrency.isocode}/loyaltycart"
										class="text-capitalize"><span class="fa fa-shopping-cart"></span>&nbsp;Shopping
											Cart<span class="cartDigit">${totalItems}</span></a>
									</li>
								</c:if>
							</ul>
						</div>
					</div>
					<div class="clearfix ">
							<div class="extreemTopRightContent">
								<ul class="quicklinks pull-right ">
								    <li><a href="http://www.partsmatter.com" target="_blank"><spring:theme code="header.parts.matter"/></a></li>
									<sec:authorize ifAnyGranted="ROLE_FMB2BB">
									  <li><a target="_blank" href="https://webchat.fmmotorparts.com/system/templates/chat/sunburst/chat.html?subActivity=Chat&entryPointId=1001&templateName=sunburst&languageCode=en&countryCode=US&ver=v11" onclick="window.open(this.href, 'mywin','location=1,left=20,top=20,width=425,height=600,toolbar=1,resizable=0'); return false;"><span class="fa fa-comment"></span> Chat</a></li>
									</sec:authorize>
									<li><a href="<c:url value="/support/contact-us/supportgeneralInquiry?complnkname=Contact Form"/>"><span class="glyphicon glyphicon-envelope"></span>
										<spring:theme code="eyebrow.nav.contactus"/></a></li>					   
									  	<li><span class="fa  fa-map-marker"></span><a href="${whereToBuyURI}"><spring:theme code="eyebrow.nav.wheretobuy"/></a></li>						
								</ul>
							</div>
					</div>
				</div>
			</div>
		</div>
	</header>
</c:if>

<!--B2C Home page  -->
<c:if test="${customerType eq 'b2cCustomer' || customerType eq 'FMAdmin'}">
	<header class="headerRow" itemscope
		itemtype="http://schema.org/Organization">
		<div class="container">
			<div class="row">
				<header:language/>
				<div class="col-lg-8  col-md-9 col-xs-2 clearfix ">
					<div class="clearfix ">
						<div class="extreemTopRightContent">
							<!-- show quick links on other device -->
							<ul class="quicklinks pull-right visible-lg visible-md">
								<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
									<li class="DINWebBold"><ycommerce:testId
											code="header_LoggedUser">
											<spring:theme code="header.welcome"
												arguments="${user.firstName} ${user.lastName}"
												htmlEscape="true" />
										</ycommerce:testId></li>
								</sec:authorize>
								<c:if test="${!fn:contains(siteName,'Loyalty')  }" >
								<li><ycommerce:testId code="header_myAccount">
										<a href="${contextPath}/federalmogul/en/USD/my-fmaccount/profile"><span class="fa fa-user"></span> <spring:theme
												code="header.link.account" /></a>
									</ycommerce:testId></li>
								</c:if>
								
								<c:if test="${fn:contains(siteName,'Loyalty')  }" >
									<li><ycommerce:testId code="header_myAccount">
										<a href="${contextPath}/federalmogul/en/USD/loyalty/history?clear=true&site=federalmogul"><span class="fa fa-user"></span> <spring:theme
												code="header.link.account" /></a>
									</ycommerce:testId></li>
								</c:if>					
								<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
									<li class="text-capitalize"><ycommerce:testId
											code="header_signOut">
											<a onclick="return hybrisApiLogout('${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/logout');"><spring:theme
													code="header.link.logout" /></a>
										</ycommerce:testId></li>
								</sec:authorize>

								<sec:authorize ifNotGranted="ROLE_CUSTOMERGROUP">
									<li>
										<a href="<c:url value='/cart'/>" class="text-capitalize">
											<span class="fa fa-shopping-cart"></span>Shopping Cart<span class="cartDigit">${totalItems}</span>
										</a>
									</li>
								</sec:authorize>
							</ul>
							<!-- show quick links on mobile -->
							<ul class="quicklinks pull-right visible-xs ">
								<li><a href="#" class=""><span class="fa fa-map-marker"></span></a></li>
								<!--  <li><a href="#" class="">Register</a></li>  -->
								<li><a href="#" class=""><span class="fa fa-user"></span></a></li>
								<li><a href="#" class=""><span
										class="fa fa-shopping-cart"></span></a></li>
							</ul>
						</div>
					</div>
					<div class="clearfix ">
							<div class="extreemTopRightContent">
								<ul class="quicklinks pull-right ">
								    <li><a href="http://www.partsmatter.com" target="_blank"><spring:theme code="header.parts.matter"/></a></li>
									<li><a href="<c:url value="/support/contact-us/supportgeneralInquiry?complnkname=Contact Form"/>"><span class="glyphicon glyphicon-envelope"></span>
										<spring:theme code="eyebrow.nav.contactus"/></a></li>					   
									  	<li><span class="fa  fa-map-marker"></span><a href="${whereToBuyURI}"><spring:theme code="eyebrow.nav.wheretobuy"/></a></li>						
								</ul>
							</div>
					</div>
				</div>
			</div>
		</div>
	</header>
</c:if>

<c:if test="${customerType eq 'CSRCustomer'}">
	<header class="headerRow" itemscope
		itemtype="http://schema.org/Organization">
		<div class="container">
			<div class="row">
				<header:language/>
				<div class="col-lg-8  col-md-9 col-xs-2 clearfix ">
					<div class="clearfix ">
						<div class="extreemTopRightContent">
							<!-- show quick links on other device -->
							<ul class="quicklinks pull-right visible-lg visible-md">                            
								<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
									<li class="DINWebBold"><ycommerce:testId
											code="header_LoggedUser">
											<spring:theme code="header.welcome"
												arguments="${user.firstName},${user.lastName}"
												htmlEscape="true" />
										</ycommerce:testId></li>
								</sec:authorize>
								<c:if test="${!fn:contains(siteName,'Loyalty')  }" >
								<li><ycommerce:testId code="header_myAccount">
										<a href="${contextPath}/federalmogul/en/USD/my-fmaccount/profile"><span class="fa fa-user"></span> <spring:theme
												code="header.link.account" /></a>
									</ycommerce:testId></li>
								</c:if>
								<c:if test="${fn:contains(siteName,'Loyalty')  }" >
									<li><ycommerce:testId code="header_myAccount">
										<a href="${contextPath}/federalmogul/en/USD/loyalty/history?clear=true&site=federalmogul"><span class="fa fa-user"></span> <spring:theme
												code="header.link.account" /></a>
									</ycommerce:testId></li>
								</c:if>	
								<c:if test="${csrAccountEmulation eq 'true'}">
								  <sec:authorize ifNotGranted="ROLE_FMBUVOR">
								    <cms:pageSlot position="MiniCart" var="cart" limit="7">
									<cms:component component="${cart}" element="li" class="miniCart" />
								    </cms:pageSlot>
								  </sec:authorize>	
								</c:if>
								
								<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
									<li class="text-capitalize"><ycommerce:testId
											code="header_signOut">
											<a onclick="return hybrisApiLogout('${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/logout');"><spring:theme
													code="header.link.logout" /></a>
										</ycommerce:testId></li>
								</sec:authorize>
								<c:if test="${csrAccountEmulation eq 'true'}">
                                    <sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
										<li class="text-capitalize"><ycommerce:testId
												code="header_signOut">
												<a href="<c:url value='/csr-emulation/end-emulate'/>">End
													Emulate (${accountId}${accountNm})</a>
								    		</ycommerce:testId></li>
								    </sec:authorize>
								</c:if>
							</ul>
							<!-- show quick links on mobile -->
							<ul class="quicklinks pull-right visible-xs ">
								<li><a href="#" class=""><span class="fa fa-map-marker"></span></a></li>
								<!--  <li><a href="#" class="">Register</a></li>  -->
								<li><a href="#" class=""><span class="fa fa-user"></span></a></li>
								<li><a href="#" class=""><span
										class="fa fa-shopping-cart"></span></a></li>
							</ul>
						</div>
					</div>
					<div class="clearfix ">
							<div class="extreemTopRightContent">
								<ul class="quicklinks pull-right ">
								    <li><a href="http://www.partsmatter.com" target="_blank"><spring:theme code="header.parts.matter"/></a></li>
									<li><a href="<c:url value="/support/contact-us/supportgeneralInquiry?complnkname=Contact Form"/>"><span class="glyphicon glyphicon-envelope"></span>
										<spring:theme code="eyebrow.nav.contactus"/></a></li>					   
									  	<li><span class="fa  fa-map-marker"></span><a href="${whereToBuyURI}"><spring:theme code="eyebrow.nav.wheretobuy"/></a></li>						
								</ul>
							</div>
					</div> 
				</div>
			</div>
		</div>
	</header>
</c:if>	
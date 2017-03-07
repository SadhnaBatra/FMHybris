<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="hideHeaderLinks" required="false" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>



<%-- Test if the UiExperience is currently overriden and we should show the UiExperience prompt --%>
<c:if test="${uiExperienceOverride and not sessionScope.hideUiExperienceLevelOverridePrompt}">
	<c:url value="/_s/ui-experience?level=" var="clearUiExperienceLevelOverrideUrl"/>
	<c:url value="/_s/ui-experience-level-prompt?hide=true" var="stayOnDesktopStoreUrl"/>
	<div class="backToMobileStore">
		<a href="${clearUiExperienceLevelOverrideUrl}"><span class="greyDot">&lt;</span><spring:theme code="text.swithToMobileStore" /></a>
		<span class="greyDot closeDot"><a href="${stayOnDesktopStoreUrl}">x</a></span>
	</div>
</c:if>

<%--  <div id="header" class="clearfix">
	<div class="headerContent ">
		<ul class="nav clearfix">
						<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
							<li class="logged_in"><ycommerce:testId code="header_LoggedUser"><spring:theme code="header.welcome" arguments="${user.firstName},${user.lastName}" htmlEscape="true"/></ycommerce:testId></li>
						</sec:authorize>
						<sec:authorize ifNotGranted="ROLE_CUSTOMERGROUP">
							<li><ycommerce:testId code="header_Login_link"><a href="<c:url value='/login'/>"><spring:theme code="header.link.login"/></a></ycommerce:testId></li>
						</sec:authorize>
						<li><ycommerce:testId code="header_myAccount"><a href="<c:url value='/my-account'/>"><spring:theme code="header.link.account"/></a></ycommerce:testId></li>
						<sec:authorize ifAnyGranted="ROLE_B2BADMINGROUP">
							<li><ycommerce:testId code="header_myCompany"><a href="<c:url value='/my-company/organization-management'/>"><spring:theme code="header.link.company"/></a></ycommerce:testId></li>
						</sec:authorize>
						<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
							<li><ycommerce:testId code="header_signOut"><a href="<c:url value='/logout'/>"><spring:theme code="header.link.logout"/></a></ycommerce:testId></li>
						</sec:authorize>
						<li><a href="<c:url value="/store-finder"/>"><spring:theme code="general.find.a.store" /></a></li>

			<cms:pageSlot position="MiniCart" var="cart" limit="1">
				<cms:component component="${cart}" element="li" class="miniCart" />
			</cms:pageSlot>
		</ul>
	</div>

	<div class="headerContent secondRow">
		<cms:pageSlot position="SearchBox" var="component">
			<cms:component component="${component}"/>
		</cms:pageSlot>

		<ul class="language">
			<li><header:languageSelector languages="${languages}" currentLanguage="${currentLanguage}" /></li>
			<li><header:currencySelector currencies="${currencies}" currentCurrency="${currentCurrency}" /></li>
		</ul>
	</div>

	<cms:pageSlot position="SiteLogo" var="logo" limit="1">
		<cms:component component="${logo}" class="siteLogo"  element="div"/>
	</cms:pageSlot>
</div> --%>

<div class="row">
    <header class="headerRow">
      <div class="col-lg-10 col-lg-offset-1">
        <div class="row">
          <div class="col-lg-4 " >
            <div class="dropdown">
              <button class="btn btn-default dropdown-toggle languageDrpDwn" type="button" id="dropdownMenu1" data-toggle="dropdown"> <span class="glyphicon glyphicon-flag"></span>USA [ENGLISH] <span class="caret"></span> </button>
              <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                <li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-flag"></span>USA [ENGLISH]</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-flag"></span>USA [ENGLISH]</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-flag"></span>USA [ENGLISH]</a></li>
                <li role="presentation"><a role="menuitem" tabindex="-1" href="#"><span class="glyphicon glyphicon-flag"></span>USA [ENGLISH]</a></li>
              </ul>
            </div>
            <cms:pageSlot position="SiteLogo" var="logo" limit="1">
		<cms:component component="${logo}" class="siteLogo"  element="div"/>
	</cms:pageSlot> </div>
          <div class="col-lg-8  clearfix visible-lg visible-md visible-sm" >
            <div class="clearfix ">
              <div class="extreemTopRightContent">
                <ul class="quicklinks pull-right ">
                  <li><a href="#" class=""><span class="glyphicon glyphicon-map-marker"></span> Where to buy?</a></li>
                  <!--  <li><a href="#" class="">Register</a></li>  -->
                  <li><a href="#" class="">Sign In</a></li>
                  <li><a href="#" class=""><span class="glyphicon glyphicon-shopping-cart
"></span> My Cart&nbsp;&nbsp;<span class="cartDigit">0</span></a></li>
                </ul>
              </div>
            </div>
            <div class="clearfix ">
              <div class="extreemTopRightContent ">
                <div class="contactOrPromotionMsg  pull-right"><a href="#"><span class="glyphicon glyphicon-envelope"></span> Email</a> | <a href="#"><span class="glyphicon glyphicon-comment"></span> Chat</a></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    
<cms:pageSlot position="SearchBox" var="component">
			
<cms:component component="${component}"/>
		
</cms:pageSlot>
</header>
  </div>
 


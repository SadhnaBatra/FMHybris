<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="header" tagdir="/WEB-INF/tags/desktop/common/header"%>

<c:url value="/search/" var="searchUrl"/>
<c:url value="/search/autocomplete/${component.uid}" var="autocompleteUrl"/>

<nav role="navigation" class="navbar navbar-inverse fm-navbar ">
	<div class="container">
		<div class="row">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div class="collapse navbar-collapse"
				 id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav col-sm-9 col-md-9 ">

					<li class="dropdown mobile-menu-item">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
							<img class="" alt="United States" src="/fmstorefront/_ui/desktop/common/images/us.png">United States<b class="caret"></b>
						</a>

						<ul class="dropdown-menu mega-menu">
							<li class="mega-menu-column">
								<ul>
									<li role="presentation"><a role="menuitem" tabindex="-1" href="http://www.fmmotorparts.com/"><img class="" alt="United States" src="/fmstorefront/_ui/desktop/common/images/us.png">United States</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.eu/"><img class="" alt="International" src="/fmstorefront/_ui/desktop/common/images/en_EU.png">International</a></li>
									<li><a target="_blank" href="http://www.federalmogulmp.com/zh-CN"><img class="" alt="China" src="/fmstorefront/_ui/desktop/common/images/china.png">China</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.de/"><img class="" alt="Deutschland" src="/fmstorefront/_ui/desktop/common/images/germany.png">Deutschland</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.fr/"><img class="" alt="France" src="/fmstorefront/_ui/desktop/common/images/france.png">France</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.it/"><img class="" alt="Italy" src="/fmstorefront/_ui/desktop/common/images/italy.png">Italy</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.ru/"><img class="" alt="Russia" src="/fmstorefront/_ui/desktop/common/images/russia.png">Russia</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.co.za/"><img class="" alt="South Africa" src="/fmstorefront/_ui/desktop/common/images/en_ZA.png">South Africa</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.es/"><img class="" alt="Spain" src="/fmstorefront/_ui/desktop/common/images/spain.png">Spain</a></li>
									<li><a target="_blank" href="http://www.fmmotorparts.co.uk/"><img class="" alt="United Kingdom" src="/fmstorefront/_ui/desktop/common/images/uk.png">United Kingdom</a></li>

								</ul>
							</li>
						</ul>

					</li>
					<sec:authorize ifNotGranted="ROLE_CUSTOMERGROUP">
						<li class="dropdown mobile-menu-item">
							<a href="<c:url value='/sign-in'/>">Sign In</a>
						</li>
					</sec:authorize>

					<cms:pageSlot position="NavigationBar" var="component">
						<cms:component component="${component}"/>
					</cms:pageSlot>
				
					<sec:authorize ifNotGranted="ROLE_FMB2BB">
						<li class="mobile-menu-item">
							<a href="${whereToBuyURI}"> Where to Buy?</a>
						</li>
					</sec:authorize>

					<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
						<c:if test="${!fn:contains(siteName,'Loyalty')  }" >
							<li class="mobile-menu-item">
								<ycommerce:testId code="header_myAccount">
									<a href="${contextPath}/federalmogul/en/USD/my-fmaccount/profile">
										<spring:theme code="header.link.account" /></a>
								</ycommerce:testId>
							</li>
						</c:if>
						<c:if test="${fn:contains(siteName,'Loyalty')  }" >
							<li class="mobile-menu-item">
								<ycommerce:testId code="header_myAccount">
									<a href="${contextPath}/federalmogul/en/USD/loyalty/history?clear=true&site=federalmogul">
										<spring:theme code="header.link.account" />
									</a>
								</ycommerce:testId>
							</li>
						</c:if>
					</sec:authorize>

					<c:if test="${customerType eq 'b2bCustomer' or customerType eq 'b2BCustomer'}">
						<li class="mobile-menu-item">
							<a href="<c:url value='/cart'/>" class="text-capitalize">
								Shopping Cart<span class="cartDigit">${totalItems}</span>
							</a>
						</li>
					</c:if>

					<sec:authorize ifAnyGranted="ROLE_CUSTOMERGROUP">
						<li class="mobile-menu-item">
							<a onclick="return hybrisApiLogout('${contextPath}/federalmogul/${currentLanguage.isocode}/${currentCurrency.isocode}/logout');">
								<spring:theme code="header.link.logout" />
							</a>
						</li>
					</sec:authorize>

				</ul>
				<div class="mobile-menu-item">
					<div class="mobile-search-form">
					    <form action="/search-results.html">
			    	    	<input type="text" name="q" placeholder="Site Search" class="form-control">
							<button class="btn btn-default" type="submit">
								<i class="fa fa-search"></i>
							</button>
				        </form>
					</div>
				</div>
				<div class="col-sm-2 col-md-2 searchBar smallB2b-SearhBar">
					<%--<c:if test="${currentsiteUID ne 'loyalty'}"> --%>
						<form enctype="multipart/form-data" method="POST"
							  action="/fmstorefront/federalmogul/en/USD/catalog/part-Number-search"
							  class="ymmForm navbar-form smallB2b-Navform" id="partInterchangeForm">
							<div class="input-group">
								<div class="input-group-btn">
									<button class="btn btn-default" type="submit">
										<i class="fa fa-search"></i>
									</button>
								</div>
								<input type="text" name="partNumber" placeholder="Part # Search" class="form-control"
									   required="required" id="externalPart">
							</div>
						</form>
					<%--</c:if>--%>
				</div>
				<div class="col-sm-1 col-md-1 search-link-item navbar-right">
					<a class="site-search-trigger">
				        <span class="fa-search search-icon"></span>
				        <span class="fa-close search-icon hide"></span>
			    	</a>
				</div>
			</div>
			<div class="site-sub-nav-search-form">
		        <form action="/search-results.html">
		            <div class="search-label col-sm-1 col-md-1">
		                <span class="fa-search"></span>
		            </div>
		            <div class="search-input-container col-sm-10 col-md-10">
		                <input type="text" name="q" placeholder="Search">
		            </div>
		            <button class="button-secondary button-search col-sm-1 col-md-1">
		                Search
		            </button>
		        </form>
		    </div>
		</div>
	</div>
</nav>


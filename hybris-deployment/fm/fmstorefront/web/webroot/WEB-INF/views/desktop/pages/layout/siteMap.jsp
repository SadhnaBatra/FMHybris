<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<template:page pageTitle="${pageTitle}">


	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
				</ul>
			</div>
		</div>
	</section>
	<%-- <ul>

	<c:forEach var="mapval" items="${catalogMap}">

							<li>
					<a href="${request.contextPath}${mapval.value}">${mapval.key}
					</a>
					</li>
				</c:forEach>
	
			<c:forEach var="footerCatg" items="${links}">

									<li><a href="${request.contextPath}${footerCatg.url}">${footerCatg.linkName}
									</a></li>
									

								</c:forEach>
								</ul>		--%>

	<section class="accountDetailPage pageContet">
		<div class="container manageAccount">
			<div class="row">
				<!-- Starts: Manage Account Left hand side panel 
				nav:myCompanyNav selected="organizationManagement
				-->
				<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">

					<div class="panel panel-default manageAccountLinks clearfix">
						<div class="panel-body orgMangPanel">
							<h3 class="text-uppercase"><spring:theme code="text.SiteMap.SITEMAP"/></h3>
							<ul class="">
								<li class=""><a class="selected" href="#"><spring:theme code="text.SiteMap.Overview"/><span
										class=""></span></a></li>







								<!-- Balaji -- Start Order Return -->
								<!-- Balaji -- End Order Return -->
								<!-- <li><a href="#">Returns <span class="linkarow fa fa-angle-right "></span></a></li> -->


							</ul>
						</div>
					</div>
					<!-- Announcement panel -->
				</div>

				<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
					<div class="manageUserPanel rightHandPanel clearfix siteMapUlLI">
						<div id="globalMessages"></div>
						<h2 class="text-uppercase siteMapHeading"><spring:theme code="text.SiteMap.subhead"/></h2>

						<div class="col-lg-12 col-xs-12 lftPad_0">
							<h4><spring:theme code="text.SiteMap.Parts"/></h4>
							<ul class="lftMrgn_20">
								<c:forEach var="partsFinder" items="${partsFindeNavMap}">
									<li><a href="${request.contextPath}${partsFinder.value}">${partsFinder.key}
									</a></li>
								</c:forEach>
							</ul>

							<h4><spring:theme code="text.SiteMap.Catalog"/></h4>
							<ul class="lftMrgn_20">
								<c:forEach var="mapval" items="${catalogMap}">
									<li><a href="${request.contextPath}${mapval.value}">${mapval.key}
									</a></li>
								</c:forEach>
							</ul>
							<h4><spring:theme code="text.SiteMap.LearningCenter"/></h4>
							<ul class="lftMrgn_20">
								<c:forEach var="learningCenter" items="${learningCenterMap}">
									<li><a
										href="${request.contextPath}${learningCenter.value}">${learningCenter.key}
									</a></li>
								</c:forEach>
							</ul>
							<h4><spring:theme code="text.SiteMap.Support"/></h4>
							<ul class="lftMrgn_20">
								<c:forEach var="supportHeader" items="${supportHeaderMap}">
								<c:choose>
									<c:when test="${supportHeader.key == 'Supplier Collaboration'}"><li><a href="${supportHeader.value}">${supportHeader.key}
									</a></li></c:when>
									<c:otherwise>
									<li><a href="${request.contextPath}${supportHeader.value}">${supportHeader.key}
									</a></li></c:otherwise>
									</c:choose>
								</c:forEach>
							</ul>
							<h4><spring:theme code="text.SiteMap.AboutUs"/></h4>
							<ul class="lftMrgn_20">
								<c:forEach var="aboutUsLinks" items="${aboutUslinksMap}">
									<li><a href="${request.contextPath}${aboutUsLinks.value}">${aboutUsLinks.key}
									</a></li>
									</a></li>
								</c:forEach>
							</ul>
					<h4><spring:theme code="text.SiteMap.Oelinks"/></h4>
							<ul class="lftMrgn_20">
								<c:forEach var="equipmentLinks" items="${originalEqupmentMap}">
									<li><a href="${equipmentLinks.value}">${equipmentLinks.key}
									</a></li>
									
								</c:forEach>
							</ul>

						</div>

					</div>
				</div>
			</div>
		</div>
	</section>
	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />




</template:page>






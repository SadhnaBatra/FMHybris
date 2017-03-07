<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="federalmogul"
	tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>

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


	<div id="globalMessages">
		<common:globalMessages />
	</div>

	<section class="accountDetailPage pageContet">
		<div class="container manageAccount">
			<div class="row">
				
				<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 contentLHS">
					<div class="panel panel-default manageAccountLinks clearfix">
						<div class="panel-body">
							<h3 class="text-uppercase">Help Center</h3>
							<div
								class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs"
								id="accordion">
								<div class="panel panel-default">
									<div class="panel panel-default">
										<div class="">
											<h4 class="panel-title">
												<c:url value="/online-tools/fmHelpCenter" var="encodedUrl" />
												<a href="${encodedUrl }" class="selected ">Help Center</a>												</a>
											</h4>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
							<c:set var="fmComponentName" value="helpCenter" scope="session" />
							<cms:pageSlot position="OnlineToolsLinksBottom" var="feature">
								<cms:component component="${feature}" />
							</cms:pageSlot>
							
						
				</div>


				<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
					<c:if test="${selectedLinkName eq 'HelpCenter'}">
						<federalmogul:fmHelpCenterContent />
					</c:if>
				</div>

			</div>
		</div>

	</section>

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
</template:page>




<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="false" rtexprvalue="true" %>

<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<template:master pageTitle="Style Guide - ${pageTitle}">

	<jsp:attribute name="pageCss">
		<link rel="stylesheet" href="${themeResourcePath}/dist/styleguide.min.css" type="text/css" />
	</jsp:attribute>
	<jsp:attribute name="pageScripts">
		<script type="text/javascript" src="${themeResourcePath}/js/acc.styleguide.js"></script>
	</jsp:attribute>

	<jsp:body>
		<nav role="navigation" class="navbar navbar-inverse fm-navbar">
			<div class="container">
				<div class="row">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
							<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
						</button>
					</div>
					<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
						<ul class="nav navbar-nav col-sm-10 col-md-10 ">
							<!-- <li class="dropdown">
								<a href="style-guide/base" class="dropdown-toggle" data-toggle="dropdown">Base<b class="caret"></b></a>
								<ul class="dropdown-menu mega-menu">
									<li class="mega-menu-column">
										<ul>
											<li><a href="http://www.fmmotorparts.com/fmstorefront/c/Brakes">Brakes</a></li>
										</ul>
									</li>
								</ul>
							</li> -->
							<li><a href="/fmstorefront/federalmogul/en/USD/style-guide/base">Base</a></li>
							<li><a href="/fmstorefront/federalmogul/en/USD/style-guide/layout">Layout</a></li>
							<li><a href="/fmstorefront/federalmogul/en/USD/style-guide/components">Components</a></li>
							<li><a href="/fmstorefront/federalmogul/en/USD/style-guide/plugins">Plugins</a></li>
						</ul>
					</div>
				</div>
			</div>
		</nav>
		<div id="content" class="clearfix">
			<section class="productDetailPage pageContet">
				<section class="productDetailContent">
					<jsp:doBody/>
				</section>
			</section>
		</div>
	</jsp:body>
	
</template:master>

<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>


<div class="panel panel-default manageAccountLinks clearfix">
	<div class="panel-body">
<!--fix for  FAL-84 , header added by 279688-->
<h3 class="text-uppercase"><spring:theme code="text.onlinetools" text="Online Tools"/></h3>
		<div class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs"
			id="accordion">
			<!-- accordian starts -->
			<div class="panel panel-default">
				<div class="panel panel-default">
					<div class="acc-heading1">
						<h4 class="panel-title">
							<c:url value="/online-tools/overview" var="encodedUrl" />

					<c:if test="${clickedLink eq 'Overview'}">
								<a href="${encodedUrl }" class="selected"> <span class=" ">Overview</span><span
									class=""></span>
								</a>
							</c:if>
							<c:if test="${clickedLink ne 'Overview'}">
								<a href="${encodedUrl }"> <span class=" ">Overview</span><span
									class="linkarow fa fa-angle-right "></span>
							</a>
							</c:if>

						</h4>
					</div>
				</div>
				
				<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP">
				<sec:authorize ifNotGranted="ROLE_FMB2T">
				<div class="panel panel-default" style="margin-top: 11px;">
					<div class="acc-heading1">
						<h4 class="panel-title">
							<a href="#"  class="${not empty dirname ? 'toggle' : 'toggle collapsed'}" id="dropdown-detail-1"
								data-toggle="detail"> <span class=" ">File Downloads</span><span class="linkarow fa fa-angle-right "></span><span
								class="tip" title="" data-placement="right"
								data-toggle="tooltip" data-original-title="Brands"></span>
							</a>
						</h4>
					</div>
					<div id="detail" ${not empty dirname ? 'style="display: block; margin-bottom:-24px;"' : 'style="display: none; margin-bottom:-24px;" '}>
						<div class="panel-body acc-body">
							<form class="form-horizontal" role="form">
								<div class="form-group">
									<ul class="filter-option">
										<c:forEach items="${onlineToolFoldersList}"
											var="folderNameLink" varStatus="status">
											<c:url
												value="/online-tools/FileDownloads?dirname=${folderNameLink}"
												var="encodedUrl" />
												
											<li>&nbsp;&nbsp;<a href="${encodedUrl }" class="${dirname eq folderNameLink ? 'selected ' : ''}">${folderNameLink}</a>
											 <c:if test="${dirname ne folderNameLink}">
											 	<span class="linkarow fa fa-angle-right "></span>
											 </c:if>
											
											
										</c:forEach>
										
									</ul>
								</div>
							</form>
						</div>

					</div>
				</div>
				
				<div class="panel panel-default">
					<div class="">
						<h4 class="panel-title">
							<c:url value="/online-tools/Motorparts-Monitor" var="encodedUrl" />
							<a href="${encodedUrl }" class="${clickedLink eq 'MotorpartsMonitor' ? 'selected' : '' }">Motorparts Monitor<span
								class="linkarow fa fa-angle-right "></span>
								</a> 
								<federalmogul:dashboardLinks />
							
						</h4>
					</div>
				</div>  
				</sec:authorize>
				</sec:authorize>
				
				<div class="panel panel-default" style="margin-top: -3px;">
					<div class="acc-heading1">
						<h4 class="panel-title">
							<ul>
								<c:url value="/online-tools/research" var="encodedUrl" />
								<c:choose>
									<c:when test="${selectedLinkName eq 'Research'}">
										<li><a href="${encodedUrl }" class="selected ">Research</a>
										</li>
									</c:when>
									<c:otherwise>
										<li><a href="${encodedUrl }" class=" ">Research</a>
										<span class="linkarow fa fa-angle-right "></span></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</h4>
					</div>
				</div>

				<div class="panel panel-default" style="margin-top: -8px;">
					<div class="acc-heading1">
						<h4 class="panel-title">
							<ul>
								<c:url value="/online-tools/market-your-shop" var="encodedUrl" />
								<c:choose>
									<c:when test="${selectedLinkName eq 'Market Your Shop'}">
										<li><a href="${encodedUrl }" class="selected ">Market Your Shop</a>
										</li>
									</c:when>
									<c:otherwise>
										<li><a href="${encodedUrl }" class=" ">Market Your Shop</a>
										<span class="linkarow fa fa-angle-right "></span></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</h4>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>

<div class="panel panel-default manageAccountLinks clearfix">
	<div class="panel-body orgMangPanel">
		<ul class="">
			<sec:authorize ifAnyGranted="ROLE_FMB2BB,ROLE_FMCSR,ROLE_FMADMINGROUP,ROLE_B2BADMINGROUP">
			<sec:authorize ifNotGranted="ROLE_FMB2T">
			<li><a target="_blank" href="http://fdm.epiinc.com/IFA/FDM/cgi-bin/login100?clkey=fdm&qqid01=262&qqid02=${nabsAccCode}"><span class="linkarow fa fa-external-link "></span>Order
					Sales Materials 
			</a></li>
			</sec:authorize>
			</sec:authorize>
			<li><a target="_blank" href="http://am-na-assets.idamcloud.com/"><span class="linkarow fa fa-external-link "></span>Download Digital
					Assets 
			</a></li>
			
		</ul>
	</div>
</div>




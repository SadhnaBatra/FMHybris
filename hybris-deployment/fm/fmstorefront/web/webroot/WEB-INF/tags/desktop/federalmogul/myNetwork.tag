<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>


<div class="panel panel-default manageAccountLinks clearfix">
	<div class="panel-body orgMangPanel">
		<h3 class="text-uppercase"><spring:theme
							code="text.company.myNetwork" text="My Network" /> </h3>
		<ul class="">

			<spring:url value="/my-company/organization-management/manage-users"
				var="encodedUrl" />
			<li><a href="${encodedUrl}"><spring:theme
						code="text.company.manageUsers" text="Manage Users" /></a></li>

			<li class='${selected eq 'units' ? 'active' : ''}'><c:url
					value="/my-company/organization-management/manage-units/"
					var="encodedUrl" /> <ycommerce:testId
					code="myCompany_units_navLink">
					<a href="${encodedUrl}"><spring:theme
							code="text.company.manage.units" text="Manage Business Units" /></a>
				</ycommerce:testId></li>
			<li><a href="#"><spring:theme
							code="text.company.manage.user.groups" text="Manage User Groups" /> <span
					class="linkarow fa fa-angle-right "></span></a></li>
			<!-- <li><a href="#">Manage Order Permissions <span class="linkarow fa fa-angle-right "></span></a></li> -->
		</ul>
	</div>
</div>
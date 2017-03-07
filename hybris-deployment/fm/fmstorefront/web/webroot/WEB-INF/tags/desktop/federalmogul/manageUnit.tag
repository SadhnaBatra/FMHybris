<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="company" tagdir="/WEB-INF/tags/desktop/company" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>

<spring:url
	value="/my-company/organization-management/manage-units/add-address"
	var="addUnitAddressUrl">
	<spring:param name="unit" value="${unit.uid}" />
</spring:url>
<spring:url value="/my-company/organization-management/manage-units/create" var="createUnitUrl">
	<spring:param name="unit" value=""/>
</spring:url>

	<script type="text/javascript"> // set vars
		/*<![CDATA[*/
		
		var unittree = true;
		
		/*]]>*/
	</script>

<!-- Starts: Manage Account Right hand side panel -->
<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel UnitedAutoSupply rightHandPanel clearfix">
		<h2 class="text-capitalize"><spring:theme code="text.company.manage.units.label" text="Manage Units"/></h2>
		<p>Lorem Ipsum is simply dummy text of the printing and
			typesetting industry. Lorem Ipsum has been the industry's standard
			dummy text ever since the 1500s, when an unknown printer took a
			galley of type and scrambled it to make a type specimen book. Lorem
			Ipsum has been the industry's standard dummy text ever since the
			1500s, when an unknown printer took a galley of type and scrambled it
			to make a type specimen book.</p>

		
		<div class="form-group">
		<ycommerce:testId code="Unit_CreateNewUnit_button">
				<a href="${createUnitUrl}" class="btn btn-default btn-fmDefault"><spring:theme code="text.company.manage.units.newUnitButton" text="Create New Unit"/></a>
			</ycommerce:testId>
			</div>
		
		<div class="container">
			<div class="row">
				<div id="left" class="span3">
					<ul id="menu-group-1" class="nav menu">




						
							<company:fmUnitTree node="${rootNode}" />
						
						
						
<!-- 
						 <li class="item-8 deeper parent"><a class="" href="#"> <span
								data-toggle="collapse" data-parent="#menu-group-1"
								href="#sub-item-8" class=""><i class="fa  fa-plus-circle"></i></span>
								<span class="lbl">FM Orginization</span>
						</a>
							<ul class="children nav-child unstyled small collapse"
								id="sub-item-8">
								<li class="item-9 deeper parent"><a class="" href="#">
										<span data-toggle="collapse" data-parent="#menu-group-1"
										href="#sub-item-9" class=""><i
											class="fa  fa-plus-circle"></i></span> <span class="lbl">United
											Auto Supply</span>
								</a>
									<ul class="children nav-child unstyled small collapse"
										id="sub-item-9">
										<li class="item-10"><a class="" href="#"> <span
												class="sign"><i class="icon-play"></i></span> <span
												class="lbl">Orielly AutoPart 2236</span>
										</a></li>
										<li class="item-11"><a class="" href="#"> <span
												class="sign"><i class="icon-play"></i></span> <span
												class="lbl">Generall Auto Parts</span>
										</a></li>
									</ul></li>
								<li class="item-12 deeper parent"><a class="" href="#">
										<span data-toggle="collapse" data-parent="#menu-group-1"
										href="#sub-item-12" class=""><i
											class="fa  fa-plus-circle"></i></span> <span class="lbl">Global
											Automative Imports INC</span>
								</a>
									<ul class="children nav-child unstyled small collapse"
										id="sub-item-12">
									</ul></li>
							</ul></li> -->
							
							
							
					</ul>
				</div>
			</div>
		</div>

	</div>
</div>

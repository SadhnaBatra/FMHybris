<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common" %>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb" %>

<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>

<template:page pageTitle="${pageTitle}">

	<div id="breadcrumb" class="breadcrumb">
		<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}"/>
	</div>

	<div id="globalMessages">
		<common:globalMessages/>
	</div>
	<%-- <nav:accountNav selected="profile" />
	<nav:accountNav selected="ManageAccountPage" /> --%>
	
	
	<federalmogul:myAccount />
	
	
	
		<div class="column accountContentPane clearfix">
			<div class="headline"><spring:theme code="text.account.profile" text="Profile"/></div>
			
				<table class="account-profile-data">
					<tr>
						<td><spring:theme code="profile.title" text="Title"/>: </td>
						<td>${fn:escapeXml(title.name)}</td>
					</tr>
					<tr>
						<td><spring:theme code="profile.firstName" text="First name"/>: </td>
						<td>${fn:escapeXml(customerData.firstName)}</td>
					</tr>
					<tr>
						<td><spring:theme code="profile.lastName" text="Last name"/>: </td>
						<td>${fn:escapeXml(fmCustomerData.lastName)}</td>
					</tr>
					<tr>
						<td><spring:theme code="profile.email" text="E-mail"/>: </td>
						<td>${fn:escapeXml(fmCustomerData.email)}</td>
					</tr>
 				</table>
				
				<a class="button" href="update-password"><spring:theme code="text.account.profile.changePassword" text="Change password"/></a>
				<a class="button" href="update-profile"><spring:theme code="text.account.profile.updatePersonalDetails" text="Update personal details"/></a>
		</div>
	

</template:page>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<div class="col-lg-4 col-md-3 col-xs-6">
	<div class="dropdown visible-lg visible-md">
		<button class="btn lang-logo btn-default dropdown-toggle languageDrpDwn"
			type="button" id="dropdownMenu1" data-toggle="dropdown">
			<img class="" alt="United	States"
				src="${contextPath}/_ui/desktop/common/images/us.png">United
			States (English)<span class="caret"></span>
		</button>
		<ul class="dropdown-menu lang-logo" role="menu" aria-labelledby="dropdownMenu1">

			<spring:theme code="country.us" var="countryUS" />
			<li role="presentation"><a role="menuitem" tabindex="-1"
				href="http://www.fmmotorparts.com/"><img class="" alt="${countryUS}"
					src="${contextPath}/_ui/desktop/common/images/us.png">${countryUS}</a></li>

			<spring:theme code="country.international" var="countryIntl" />
			<li><a target="_Blank" href="http://www.fmmotorparts.eu/"><img
					class="" alt="${countryIntl}"
					src="${contextPath}/_ui/desktop/common/images/en_EU.png">${countryIntl}</a></li>

			<spring:theme code="country.china" var="countryChina" />
			<li><a target="_Blank" href="http://www.federalmogulmp.com/zh-CN/corp/Pages/landing/default.aspx"><img
					class="" alt="${countryChina}"
					src="${contextPath}/_ui/desktop/common/images/china.png">${countryChina}</a></li>

			<spring:theme code="country.germany" var="countryGermany" />
			<li><a target="_Blank" href="http://www.fmmotorparts.de/"><img
					class="" alt="${countryGermany}"
					src="${contextPath}/_ui/desktop/common/images/germany.png">${countryGermany}</a></li>

			<spring:theme code="country.france" var="countryFrance" />
			<li><a target="_Blank" href="http://www.fmmotorparts.fr/"><img
					class="" alt="${countryFrance}"
					src="${contextPath}/_ui/desktop/common/images/france.png">${countryFrance}</a></li>

			<spring:theme code="country.india" var="countryIndia" />
			<li><a target="_Blank" href="http://www.fmecat.in/"><img
					class="" alt="${countryIndia}"
					src="${contextPath}/_ui/desktop/common/images/india.png">${countryIndia}</a></li>

			<spring:theme code="country.italy" var="countryItaly" />
			<li><a target="_Blank" href="http://www.fmmotorparts.it/"><img
					class="" alt="${countryItaly}"
					src="${contextPath}/_ui/desktop/common/images/italy.png">${countryItaly}</a></li>

			<spring:theme code="country.mexico" var="countryMexico" />
			<li><a target="_Blank" href="http://www.federalmogulmexico.com/"><img
					class="" alt="${countryMexico}"
					src="${contextPath}/_ui/desktop/common/images/mexico.png">${countryMexico}</a></li>

			<spring:theme code="country.russia" var="countryRussia" />
			<li><a target="_Blank" href="http://www.fmmotorparts.ru/"><img
					class="" alt="${countryRussia}"
					src="${contextPath}/_ui/desktop/common/images/russia.png">${countryRussia}</a></li>

			<spring:theme code="country.south.africa" var="countrySA" />
			<li><a target="_Blank" href="http://www.fmmotorparts.co.za/"><img
					class="" alt="${countrySA}"
					src="${contextPath}/_ui/desktop/common/images/en_ZA.png">${countrySA}</a></li>

			<spring:theme code="country.spain" var="countrySpain" />
			<li><a target="_Blank" href="http://www.fmmotorparts.es/"><img
					class="" alt="${countrySpain}"
					src="${contextPath}/_ui/desktop/common/images/spain.png">${countrySpain}</a></li>

			<spring:theme code="country.united.kingdom" var="countryUK" />
			<li><a target="_Blank" href="http://www.fmmotorparts.co.uk/"><img
					class="" alt="${countryUK}"
					src="${contextPath}/_ui/desktop/common/images/uk.png">${countryUK}</a></li>
		</ul>
	</div>
	<cms:pageSlot position="SiteLogo" var="logo" limit="1">
		<cms:component component="${logo}" class="siteLogo" element="div" />
	</cms:pageSlot>
</div>



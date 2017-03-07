<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link type="text/css" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/redmond/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1/jquery-ui.min.js"></script>

<div class="col-lg-4 col-xs-12 padZero">
	<h3>${component.bannerName}</h3>
	<p>${component.bannerDescription}</p>
	${component.buttonName}
</div>

<div class=" col-sm-4 col-md-4 col-xs-12 topMargn_20">
	<div class="lms_intro_hover">
		<p class="col-xs-lms"> <iframe  src="${component.bannerImage}" allowfullscreen="true" ></iframe> </p>
		<div class="">
			<div class="blur"></div>
			<div class="caption-text">
				<h5>${component.bannerLink}</h5>
			</div>
		</div>
	</div>
</div>

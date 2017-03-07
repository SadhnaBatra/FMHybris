<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<c:url value="/search/" var="searchUrl"/>
<c:url value="/search/autocomplete/${component.uid}" var="autocompleteUrl"/>






<%-- <div class="col-sm-2 col-md-2 navbar-right searchBar smallB2b-SearhBar">
	<form class="navbar-form smallB2b-Navform" role="search" name="search_form" method="get"
		action="${searchUrl}">
		<div class="input-group">
			<div class="input-group-btn">
				<button class="btn btn-default" type="submit">
					<i class="fa fa-search"></i>
				</button>
			</div>
			<input type="text" class="form-control" placeholder="Site Search"
				name="q">
		</div>
	</form>
</div> --%>

<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>
<%@ attribute name="categoryCode" required="true" type="java.lang.String" %>
<%@ attribute name="rootCategories" required="true" type="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav/loyalty" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>



<div class="panel panel-default">
	<div class="panel-heading acc-heading">
		<h4 class="panel-title">
			<a class="toggle" id="dropdown-detail-1" data-toggle="detail-1">
				<span class="acc-title">Gear Type</span> <span class="tip" title=""
				data-placement="right" data-toggle="tooltip"
				data-original-title="Gear Type"></span>
			</a>
		</h4>
	</div>
	<div id="detail-1">
		<div class="panel-body acc-body">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<ul class="filter-option">
					   <c:forEach items="${pageData.facets}" var="facetData" varStatus="status" >
							<c:if test="${facetData.name eq 'category' }">
								<c:if test="${not empty facetData.values}">
									<c:forEach items="${facetData.values}" var="facetValue">
										<li>
										<c:if test="${facetData.multiSelect}">
											<form action="#" method="get">
												<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
												<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
												<label class="facet_block-label">
													<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="$(this).closest('form').submit()"/>
														${facetValue.name}
													<!-- <span class="facetValueCount"> <spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>  -->
												</label>
											</form>
										</c:if>
										<c:if test="${not facetData.multiSelect}">
											<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
											<a href="${facetValueQueryUrl}">${facetValue.name}</a>
											<!--  <span class="facetValueCount"> <spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span> -->
										</c:if>
										</li>
									</c:forEach>
								</c:if>
								</c:if>
						</c:forEach>
					</ul>
				</div>
			</form>
		</div>
	</div></div>
<div class="panel panel-default">
	<div class="panel-heading acc-heading">
		<h4 class="panel-title">
			<a class="toggle" id="dropdown-detail-2" data-toggle="detail-2">
				<span class="acc-title">Brand</span> <span class="tip" title=""
				data-placement="right" data-toggle="tooltip"
				data-original-title="Brand"></span>
			</a>
		</h4>
	</div>
	<div id="detail-2">
		<div class="panel-body acc-body">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<ul class="filter-option">
					   <c:forEach items="${pageData.facets}" var="facetData" varStatus="status" >
							<c:if test="${facetData.name eq 'Manufacturer Name' }">
								<c:if test="${not empty facetData.values}">
									<c:forEach items="${facetData.values}" var="facetValue">
										<li>
										<c:if test="${facetData.multiSelect}">
											<form action="#" method="get">
												<input type="hidden" name="q" value="${facetValue.query.query.value}"/>
												<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
												<label class="facet_block-label">
													<input type="checkbox" ${facetValue.selected ? 'checked="checked"' : ''} onchange="$(this).closest('form').submit()"/>
														${facetValue.name}
													<!-- <span class="facetValueCount"> <spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span>  -->
												</label>
											</form>
										</c:if>
										<c:if test="${not facetData.multiSelect}">
											<c:url value="${facetValue.query.url}" var="facetValueQueryUrl"/>
											<a href="${facetValueQueryUrl}">${facetValue.name}</a>
											<!--  <span class="facetValueCount"> <spring:theme code="search.nav.facetValueCount" arguments="${facetValue.count}"/></span> -->
										</c:if>
										</li>
									</c:forEach>
								</c:if>
								</c:if>
						</c:forEach>
					</ul>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
function QueryChange(obj){
	
	var pathName = '';
	var win_url = window.location.href;
	try {
		if (win_url.indexOf("/USD") != -1) {
			pathName = win_url
					.substring(0, win_url.lastIndexOf("/USD") + 5);
		} else if (win_url.indexOf("?site") != -1) {
			pathName = win_url.substring(0,
					win_url.lastIndexOf("/?site") + 1)
					+ win_url.substring(win_url.lastIndexOf("site=") + 5,
							win_url.length) + "/en/USD/";

		}else if (win_url.indexOf("/lsearch") != -1) {
			pathName = win_url
			.substring(0, win_url.lastIndexOf("/lsearch") + 1);
		} else {
alert(" :: "+window.location.href);
			//pathName = window.location.href+"federalmogul/loyalty/en/USD/";
			pathName = window.location.href+"loyalty/en/USD/";
		}

	} catch (e) {
		alert(e);
	}
	
	var categoryQuery ="";
	$("input:checkbox[name=categories]:checked").each(function () {
	    if (this.checked) {
		
	        categoryQuery = categoryQuery + "category:"+$(this).val()+":";
	       // alert("Final Cat ## "+categoryQuery);
	    }
	});
	
	
	$("input:checkbox[name=manufacturer]:checked").each(function () {
	    if (this.checked) {
	    	categoryQuery = $(this).val();
	       // alert("Final Brand  ## "+categoryQuery);
	    }
	});
	
	//alert("Final "+categoryQuery);
	
	if(categoryQuery == null || categoryQuery == ""){
		
				
		categoryQuery = "category:Hats and Gloves:category:Electronics:category:Shirts:"+
				"category:Decals:category:Jackets and Hoodies:category:Accessories";
	}
	
	//var queryVal= pathName + "lsearch?q=:name-asc:category:"+obj.value+ "&text=#";
	var queryVal= pathName + "lsearch?q=:name-asc:"+categoryQuery+"&text=#";
	
	//alert("Final Query :: "+queryVal)
	
	location.href = queryVal;
}

</script>

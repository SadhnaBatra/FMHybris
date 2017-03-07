
<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/desktop/nav" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<c:set var="displayFitmentYear"	value="false" />
<c:set var="displayFitmentMake"	value="false" />
<c:set var="displayFitmentModel" value="false" />
<c:set var="displayVehiclesegment" value="false" />

<div id="fade"></div>
        <div id="modal">
            <img id="loader" src="/fmstorefront/_ui/desktop/common/images/spinner.gif" />
        </div>
<c:if test="${not empty pageData.breadcrumbs}">
	<c:forEach items="${pageData.breadcrumbs}" var="breadcrumb">
		<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'year' }" >
        		<c:set var="displayFitmentYear"	value="${fn:substringAfter(breadcrumb.facetValueName,'|')}" />
       		</c:if>
        	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'make' }" >
        		<c:set var="displayFitmentMake"	value="${fn:substringAfter(breadcrumb.facetValueName,'|')}" />
        	</c:if>
        	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'model'}" >	
        		<c:set var="displayFitmentModel" value="${fn:substringAfter(breadcrumb.facetValueName,'|')}" />
        	</c:if>
			<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'vehiclesegment'}" >	
        		<c:set var="displayVehiclesegment" value="${fn:substringAfter(breadcrumb.facetValueName,'|')}" />
        	</c:if>

  </c:forEach>
</c:if>

<div class="form-group col-sm-12 subTitleHolder"> <span class="subTitle">Your Vehicle</span> </div>
<c:if test="${isYMMApplied}">			
	<c:forEach items="${pageData.facets}" var="facet">
		<c:if test="${not empty facet.values && facet.name == 'year' || facet.name =='make' || facet.name =='model' || facet.name =='vehiclesegment'}">
			<form id="normalYMMForm" action="#" method="get">
		        <input type="hidden" id="ymmquery" name="q" value=""/>
				<input type="hidden" name="text" value="${pageData.freeTextSearch}"/>
				<div class="form-group col-sm-3">
					<select class="form-control" name="${facet.name}" id="${facet.name}">
						<%--<c:forEach items="${facet.values}" var="facetValue">
							<c:set var="facetValueName" value="${fn:substringAfter(facetValue.name,'|')}"/>
							<c:choose>
								<c:when test="${(facet.name eq 'year' && facetValueName eq displayFitmentYear)}">
									<option selected="selected" value="${facetValueName}">${facetValueName}</option> 
								</c:when>
								<c:when test="${(facet.name eq 'make' && facetValueName eq displayFitmentMake)}">
									<option selected="selected" value="${facetValueName}">${facetValueName}</option> 
								</c:when>
								<c:when test="${(facet.name eq 'model' && facetValueName eq displayFitmentModel)}">
									<option selected="selected" value="${facetValueName}">${facetValueName}</option> 
								</c:when>
								<c:otherwise>
									<option value="${facetValueName}">${facetValueName}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach> --%>
						
						
						<c:choose>
								<c:when test="${facet.name eq 'year'}">
									<option selected="selected" value="${displayFitmentYear}">${displayFitmentYear}</option> 
								</c:when>
								<c:when test="${facet.name eq 'make'}">
									<option selected="selected" value="${displayFitmentMake}">${displayFitmentMake}</option> 
								</c:when>
								<c:when test="${facet.name eq 'model' }">
									<option selected="selected" value="${displayFitmentModel}">${displayFitmentModel}</option> 
								</c:when>
								<c:when test="${facet.name eq 'vehiclesegment' }">
									<option selected="selected" value="${displayVehiclesegment}">${displayVehiclesegment}</option>
									<option value="Commercial,Industrial Ag.">Commercial, Industrial, Ag.</option>
									<option value="Performance">Performance</option>
									<option value="Marine">Marine</option>
									<option value="Powersport">Powersport</option>
									<option value="Small Engine">Small Engine</option> 
								</c:when>
								
							</c:choose>
						
					</select>
				</div>
			</form>
		</c:if>
	</c:forEach>
</c:if>

<c:if test="${!isYMMApplied}">
	    	<form id="normalYMMForm" action="#" method="get">
               	<input type="hidden" id="ymmquery" name="q" value=""/>
			 	<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>
			 		<div class="form-group col-sm-3"> 
                      <!--<label for="vehicle" >Vehicle</label> -->
                      <select id="vehiclesegment" class="form-control">
                    		<option value="vehicleSegment">Vehicle Type</option>
					<option value="Passenger Car Light Truck">Passenger Car Light Truck</option>
					<option value="Commercial,Industrial Ag.">Commercial, Industrial, Ag.</option>
					<option value="Performance">Performance</option>
					<option value="Marine">Marine</option>
					<option value="Powersport">Powersport</option>
					<option value="Small Engine">Small Engine</option>
                    	</select>
                    </div> <br>
                 	<div class="form-group col-sm-3">
                 		<select id="year" class="form-control">
                    		<option>Year</option>
                    	</select>	
                   </div>
                   <div class="form-group col-sm-3">
                 		<select id="make" class="form-control" disabled>
                    			<option>Make</option>
                   	 	</select>
                   </div>
                  <div class="form-group col-sm-3">
                 		<select id="model" class="form-control" disabled>
                    			<option>Model</option>
                   	 	</select>	
                   </div>
           
				    <!--   <a href="http://localhost:9001/fmstorefront/federalmogul/en/USD/search?q=%3Aname-desc%3Ayear%3A2000%3Amake%3AFord%3Amodel%3AMustang&text=#" class="btn btn-fmDefault">Look it Up</a> -->
                   <!--   <div id="lookitup" ><a id="ymmSearch" href="javascript:SearchQuery()" class="btn btn-fmDefault" >Look it Up</a></div> -->
               </form> 	 	 	
	 	</c:if>






 <script>

 function QueryChange(obj)
 {
	var queryVal=":name-desc:"+obj.name+":"+$('#'+obj.id+' option:selected').text();
	$('#queryymm'+obj.id).attr('value', queryVal);
	var query= $('#queryymm'+obj.id).val();
	$(obj).closest('form').submit()
 }
 
var usedNames = {};
$("select[name='company'] > option").each(function () {
    if(usedNames[this.text]) {
        $(this).remove();
    } else {
        usedNames[this.text] = this.value;
    }
});

 function SearchQuery(){
	 var sel_vehicleSegment= $('#vehiclesegment option:selected').text();
	 var sel_year= $('#year option:selected').text();
	 var sel_make= $('#make option:selected').text();
	 var sel_model= $('#model option:selected').text();	 
	 var ymmCode=sel_year.trim()+sel_make.trim()+sel_model.trim()+"|";
	 var queryVal= pathName + "search?q=:name-desc:vehiclesegment:"+ymmCode+sel_vehicleSegment.trim()+":year:"+ymmCode+sel_year.trim()+":make:"+ymmCode+sel_make.trim()+":model:"+ymmCode+sel_model.trim()+ "&text=#";
		
	 //var queryVal=":name-desc:vehiclesegment:"+sel_vehicleSegment.trim()+":year:"+sel_year.trim()+":make:"+sel_make.trim()+":model:"+sel_model.trim();
	 $('#ymmquery').attr('value', queryVal);
	 var query= $('#ymmquery').val();
	 $('#normalYMMForm').closest('form').submit()
 }

 </script>

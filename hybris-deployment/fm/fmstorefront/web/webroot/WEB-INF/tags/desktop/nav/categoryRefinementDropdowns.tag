<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%-- <%@ attribute name="facetData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetData" %>
 --%>
 <%@ attribute name="pageData" required="true" type="de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData" %>
<%@ attribute name="categoryCode" required="true" type="java.lang.String" %>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="appliedCategory" value="false" />
<c:set var="appliedSubCategory"	value="false" />
<c:set var="appliedCategoryVal" value="false" />
<c:set var="appliedSubCategoryVal"	value="false" />
<c:set var="breadsubCategory" value="" />
<c:set var="breadcategoryCode" value="" />
<c:set var="displayFitmentYear" value="" />
<c:set var="subCategoryAll" value="" />
<c:if test="${not empty pageData.breadcrumbs}">

	<c:forEach items="${pageData.breadcrumbs}" var="breadcrumb">


	  	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'category' }" >
         	<c:set var="appliedCategory" value="true" />
         	<c:set var="appliedCategoryVal" value="${breadcrumb.facetValueName}" />
         </c:if>
         <c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'Sub Category' }" >
         	<c:set var="appliedSubCategory"	value="true" />
         	<c:set var="appliedSubCategoryVal" value="${breadcrumb.facetValueName}" />
         </c:if>
     </c:forEach>
</c:if>

<c:if test="${not empty categoryCode}">
	<c:forEach items="${breadcrumbs}" var="breadcrumb" varStatus="status">
		<c:if test="${!fn:contains(breadcrumb.name,'-')}">
			<c:if test="${status.index eq 0 }">
				<c:set var="breadcategoryCode" value="${breadcrumb.name}" />
			</c:if>
			<c:if test="${status.index eq 1 }">
				<c:set var="breadsubCategory" value="${breadcrumb.name}" />
			</c:if>
		</c:if>
	</c:forEach>
</c:if>

<c:if test="${not empty searchPageData.breadcrumbs}">

	<c:forEach items="${searchPageData.breadcrumbs}" var="breadcrumb">
	
		<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'year' }" >
        		<c:set var="displayFitmentYear"	value="${breadcrumb.facetValueName}" />
       		</c:if>
        	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'make' }" >
        		<c:set var="displayFitmentMake"	value="${breadcrumb.facetValueName}" />
        	</c:if>
        	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'model'}" >	
        		<c:set var="displayFitmentModel" value="${breadcrumb.facetValueName}" />
        	</c:if>
        	<c:if test="${not empty breadcrumb.facetName && breadcrumb.facetName == 'vehiclesegment'}" >	
        		<c:set var="displayVehiclesegment" value="${breadcrumb.facetValueName}" />
        	</c:if>

  </c:forEach>
</c:if>


<c:set var="category" value="category" />
<c:set var="subCategory" value="Sub Category" />
<c:set var="categoryAll" value=":name-asc:vehiclesegment:${displayVehiclesegment}:year:${displayFitmentYear}:make:${displayFitmentMake}:model:${displayFitmentModel}" />
<c:set var="categoryNotAll" value="" />
<c:if test="${not empty displayFitmentYear && displayFitmentYear ne ''}">
<c:set var="subCategoryAll" value=":name-asc:vehiclesegment:${displayVehiclesegment}:year:${displayFitmentYear}:make:${displayFitmentMake}:model:${displayFitmentModel}" />
</c:if>


	
	<c:forEach items="${pageData.facets}" var="facetData" varStatus="status" >

		<c:if test="${not empty facetData.values}">
			<c:if test="${status.index eq 0 }">
				<div class="form-group col-sm-12 subTitleHolder">
					<span class="subTitle">Product Category</span>
				</div>
			</c:if>
			<c:if test="${(facetData.name eq 'category' ) || (facetData.name eq 'Sub Category' )}">
				
				<ycommerce:testId code="facetNav_facet${facetData.name}_links">
					<div class="form-group col-sm-6">

					<form id="${facetData.code }Form" action="#" method="get">	
					<input type="hidden" id="q${facetData.code }" name="q" value=""/>	
					<input type="hidden" name="text" value="${searchPageData.freeTextSearch}"/>			
						<select class="form-control" id="${facetData.code}" onchange="javascript:QueryChange(this)">
						<c:if test="${empty breadcategoryCode || empty breadsubCategory  }" >		
							<c:if test="${((facetData.name eq 'category' && !appliedCategory ) && empty pageData.categoryCode)}" >						
								<option class="text-capitalize">Category</option>
							</c:if>
							<c:if test="${(facetData.name eq 'category' && empty pageData.categoryCode && not empty displayFitmentYear)}" >						
								<option value="${categoryAll}">All</option>
							</c:if>
							
							
							<c:if test="${(facetData.name eq 'Sub Category' && !appliedSubCategory)}" >						
								<option label="${facetData.name}">${facetData.name}</option>								
		
							</c:if>
							

							<c:forEach items="${facetData.values}" var="facetValue">	
								<c:if test="${facetData.multiSelect}">
									<c:choose>
										<c:when test="${categoryCode eq facetValue.name}">
											<option selected="selected"  value="${facetValue.query.query.value}">${facetValue.name}</option>
										</c:when>
										<c:otherwise>
											<c:choose>
										<c:when test="${(facetData.name eq 'category' && appliedCategory && facetValue.name eq appliedCategoryVal)}">
											<option selected="selected" value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:when>
										<c:when test="${(facetData.name eq 'Sub Category' && appliedSubCategory && facetValue.name eq appliedSubCategoryVal)}">
											<option selected="selected" value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:when>
										<c:otherwise>
											 <option value="${facetValue.query.query.value}">${facetValue.name}</option> 
												</c:otherwise>
											</c:choose> 
										</c:otherwise>
									</c:choose>														
									
								</c:if>
								<c:if test="${not facetData.multiSelect}">
									<c:when test="${(facetData.name eq 'category' && appliedCategory && facetValue.name eq appliedCategoryVal)}">
											<option selected="selected" value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:when>
										<c:when test="${(facetData.name eq 'Sub Category' && appliedSubCategory && facetValue.name eq appliedSubCategoryVal)}">
											<option selected="selected" value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:when>
										<c:otherwise>
											<option value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:otherwise>
								</c:if>

							</c:forEach>
							</c:if>
						   <c:if test="${not empty breadcategoryCode && facetData.name eq 'category' }" >	
						    	<option selected="selected" value="${categoryCode}">${breadcategoryCode }</option>
						    </c:if>	
						    <c:if test="${not empty breadsubCategory && facetData.name eq 'Sub Category' }" >	
						    	<option selected="selected" value="${categoryCode}">${breadsubCategory 	}</option>
						    </c:if>							      
 						    						
						</select>
						</form>
					</div>
				</ycommerce:testId>
			</c:if>
				<%-- <c:if test="${(facetData.name eq 'category' && appliedCategory ) }">
					<div class="form-group col-sm-6">	
						<select class="form-control"  id="${facetData.code}" onchange="javascript:QueryChange(this)" >
			            	<option selected="selected" >${appliedCategoryVal}</option>
			            </select>
			        </div>
				</c:if>
				<c:if test="${(facetData.name eq 'Sub Category' && appliedSubCategory)}">
					<div class="form-group col-sm-6">	
						<select class="form-control"  id="${facetData.code}" onchange="javascript:QueryChange(this)" >
			            	<option selected="selected" >${appliedSubCategoryVal}</option>
			            </select>
			          </div>
				</c:if> --%>
		</c:if>
	</c:forEach>

<div style="display:none">

<c:forEach items="${pageData.facets}" var="facetData" varStatus="status" >

		<c:if test="${not empty facetData.values}">
			<c:if test="${status.index eq 0 }">
				<div class="form-group col-sm-12 subTitleHolder">
					<span class="subTitle">Product Category</span>
				</div>
			</c:if>
			<c:if test="${facetData.name eq 'Sub Category' }">
				<ycommerce:testId code="facetNav_facet${facetData.name}_links">
					<div class="form-group col-sm-6">	
					<form id="${facetData.code }Form" action="#" method="get">	
					<input type="hidden" id="q${facetData.code }" name="q" value=""/>				
						<select class="form-control" id="dummy${facetData.code}" >
							<c:forEach items="${facetData.values}" var="facetValue">	
								<c:if test="${facetData.multiSelect}">
									<c:choose>
										<c:when test="${(facetData.name eq 'Sub Category' && appliedSubCategory && facetValue.name eq appliedSubCategoryVal)}">
											<option selected="selected" value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${categoryCode eq facetValue.name }">
													<option selected="selected"  value="${facetValue.query.query.value}">${facetValue.name}</option>
												</c:when>
												<c:otherwise>
													<option value="${facetValue.query.query.value}">${facetValue.name}</option>
												</c:otherwise>
											</c:choose> 
										</c:otherwise>
									</c:choose>														
									
								</c:if>
								<c:if test="${not facetData.multiSelect}">
										<c:when test="${(facetData.name eq 'Sub Category' && appliedSubCategory && facetValue.name eq appliedSubCategoryVal)}">
											<option selected="selected" value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:when>
										<c:otherwise>
											<option value="${facetValue.query.query.value}">${facetValue.name}</option> 
										</c:otherwise>
								</c:if>

							</c:forEach>
							
						</select>
						</form>
					</div>
				</ycommerce:testId>
			</c:if>
				<%-- <c:if test="${(facetData.name eq 'category' && appliedCategory ) }">
					<div class="form-group col-sm-6">	
						<select class="form-control"  id="${facetData.code}" onchange="javascript:QueryChange(this)" >
			            	<option selected="selected" >${appliedCategoryVal}</option>
			            </select>
			        </div>
				</c:if>
				<c:if test="${(facetData.name eq 'Sub Category' && appliedSubCategory)}">
					<div class="form-group col-sm-6">	
						<select class="form-control"  id="${facetData.code}" onchange="javascript:QueryChange(this)" >
			            	<option selected="selected" >${appliedSubCategoryVal}</option>
			            </select>
			          </div>
				</c:if> --%>
		</c:if>
	</c:forEach>

</div>
<script type="text/javascript">
/*
 function QueryChange(obj)
 {
	var queryVal=$('#'+obj.id+' option:selected').val();
	$('#q'+obj.id).attr('value', queryVal);
	var query= $('#q'+obj.id).val();	
	$(obj).closest('form').submit()
 }*/
function QueryChange(obj)
{
       
       var queryVal=$('#'+obj.id+' option:selected').val();       
       var subCategoryVal = "${subCategoryAll}";
 	var appliedCategory = "${appliedCategory}";
       var appliedCategoryVal = "${appliedCategoryVal}";
   
       if(obj.id == 'category'){
              
              var  categoryFisrtOccurence = queryVal.indexOf("category");
              var categoryLastOccurence = queryVal.lastIndexOf("category");
              
              if(categoryFisrtOccurence != categoryLastOccurence){
                     
                     var baseQuery=queryVal.substring(0,categoryFisrtOccurence);
                     var remainingCategories =queryVal.substring(categoryFisrtOccurence);
                     var requiredCategory  = queryVal.substring(categoryLastOccurence);
                     queryVal=baseQuery+requiredCategory;
              }
       }
       else if(obj.id == 'subcategory' && queryVal !='subCategoryAll' ){
    	   
    	   		var ele = document.getElementById("category"); 
    	   		var selectedCategory = ele.options[ele.selectedIndex].value; 
			
			if(selectedCategory == "Gaskets Sealing Systems")
     			{
     		   		selectedCategory = "Gaskets & Sealing Systems";
     		 	}
              
              var subCategoryFisrtOccurence = queryVal.indexOf("subcategory");
              var subCategoryLastOccurence = queryVal.lastIndexOf("subcategory");
              
              if(subCategoryFisrtOccurence != subCategoryLastOccurence){
                     
                     var baseQuery=queryVal.substring(0,subCategoryFisrtOccurence);
                     var remainingSubCategories =queryVal.substring(subCategoryFisrtOccurence);
                     var requiredSubCategory  = queryVal.substring(subCategoryLastOccurence);
                     
                           queryVal=baseQuery+requiredSubCategory;
                     
               }              
              
              if(queryVal.indexOf(":category:") < 0){ 

            	  subcategoryIndex = queryVal.indexOf(":subcategory:") 
            	  categoryAppenQueryVal = queryVal.substring(0, subcategoryIndex) + ":category:"+selectedCategory + queryVal.substring(subcategoryIndex, queryVal.length); 


            	  queryVal = categoryAppenQueryVal; 

        	}
       }else{    		   	   
    	   if(appliedCategory){ 
    		   var subCategoryAll = queryVal.indexOf("subCategoryAll");
        	   var baseQuery=queryVal.substring(0,subCategoryAll);
        	   queryVal = baseQuery+subCategoryVal;
        	   queryVal = queryVal + ":category:"+appliedCategoryVal;

       		}else{
    	   		var subCategoryAll = queryVal.indexOf("subCategoryAll");
    	   		var baseQuery=queryVal.substring(0,subCategoryAll);
    	   		queryVal = baseQuery+subCategoryVal;
       		}
       }

       
       $('#q'+obj.id).attr('value', queryVal);
       var query= $('#q'+obj.id).val();  
	openUploadOrderModal();
       $(obj).closest('form').submit()   
 }

</script>
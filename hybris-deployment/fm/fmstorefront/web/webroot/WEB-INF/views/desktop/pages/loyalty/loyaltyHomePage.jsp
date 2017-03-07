<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<template:page pageTitle="${pageTitle}">
	<%-- <c:out value="<%=request.getContextPath()%>"/> --%>
	<section class="breadcrumbPanel visible-lg visible-md visible-sm"
		itemscope itemtype="http://schema.org/Product">
		<div class="container">
			<div class="row">
				<ul class="breadcrumb">
					<breadcrumb:breadcrumb breadcrumbs="${breadcrumbs}" />
				</ul>
			</div>
		</div>
	</section>
	
	<!-- MAIN CONTAINER--> 
<!-- InstanceBeginEditable name="Page Content Section" -->
<section class="productListPage productCategoryPage pageContet" >
  <div class="container">
    <div class="row">
      <aside class="col-lg-3 col-md-3 col-sm-12 col-xs-12 ">
        <div class="panel-group sidebar visible-lg visible-md visible-sm productlist-lhs" id="accordion">
          <div class="panel panel-default searchby">
            <div class="panel-body">
              <h4><span class=" fa fa-search"></span> Search By:</h4>
            </div>
          </div>
          <!-- accordian starts -->
          <div class="panel panel-default">
            <div class="panel-heading acc-heading">
              <h4 class="panel-title"> <a class="toggle collapsed" id="dropdown-detail-1" data-toggle="detail-1" > <span class="acc-title">T-Shirt</span> <span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="Brands"><span class="fa fa-question-circle"></span></span> </a> </h4>
            </div>
            <div id="detail-1" class="">
              <div class="panel-body acc-body">
                <form class="form-horizontal" role="form">
                <div class="chooseColor">
                <label class="text-label subTitle">Choose Color</label>
                
                  <div class="checkbox">
                  
                    <ul class="filter-option">
                      <li>
                        <input id="check1" type="checkbox" value="">
                        <label for="check1" class="red" >Red</label>
                      </li>
                      <li>
                        <input id="check2" type="checkbox" name="check" value="check1">
                        <label for="check2" class="black" >Black</label>
                      </li>
                      <li>
                        <input id="check3" type="checkbox" value="">
                        <label for="check3" class="white" >White</label>
                      </li>
                      <li>
                        <input id="check4" type="checkbox" value="">
                        <label for="check4" class="" >Gray</label>
                      </li>
                      <li>
                        <input id="check5" type="checkbox" value="">
                        <label for="check5" class="blue" >Blue</label>
                      </li>
                      
                    </ul>
                  </div>
                 </div>
                 
                 
                 <div class="chooseSize"> 
                 <label class="text-label subTitle">Choose Size</label>
                  <div class="">
                    <ul class="filter-option">
                      <li>
                        <input type="checkbox" value="">
                        <label>Small</label>
                      </li>
                      <li>
                        <input type="checkbox" value="">
                        <label>Medium</label>
                      </li>
                      <li>
                        <input type="checkbox" value="">
                        <label>Large</label>
                      </li>
                      <li>
                        <input type="checkbox" value="">
                        <label>X-Large</label>
                      </li>
                      <li>
                        <input type="checkbox" value="">
                        <label>XX-Large</label>
                      </li>
                      <li>
                        <input type="checkbox" value="">
                        <label>XXX-Large</label>
                      </li>
                      
                    </ul>
                  </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading acc-heading">
              <h4 class="panel-title"> <a class="toggle collapsed" id="dropdown-detail-2" data-toggle="detail-2" > <span class="acc-title">Cap</span> <span class="tip" title="" data-placement="right" data-toggle="tooltip" data-original-title="More Vehicle"><span class="fa fa-question-circle"></span></span> </a></h4>
            </div>
            <div id="detail-2">
              <div class="panel-body acc-body">
                
                <form class="form-horizontal" role="form">
                <div class="chooseColor">
                <label class="text-label subTitle">Choose Color</label>
                
                  <div class="checkbox">
                  
                    <ul class="filter-option">
                      <li>
                        <input id="check6" type="checkbox" value="">
                        <label for="check6" class="red" >Red</label>
                      </li>
                      <li>
                        <input id="check7" type="checkbox" value="">
                        <label for="check7" class="blue" >Blue</label>
                      </li>
                      
                    </ul>
                  </div>
                 </div>
                 
                 
                 <div class="chooseSize"> 
                 <label class="text-label subTitle">Choose Size</label>
                  <div class="">
                    <ul class="filter-option">
                      <li>
                        <input type="checkbox" value="">
                        <label>Small</label>
                      </li>
                      <li>
                        <input type="checkbox" value="">
                        <label>Medium</label>
                      </li>
                      <li>
                        <input type="checkbox" value="">
                        <label>Large</label>
                      </li>
                      
                    </ul>
                  </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
          
        </div>
      </aside>
      <section class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
        <section class="prodList productCategory clearfix">
          <!--<div class="search-result-title col-lg-12">
            <h2>Drive Shaft Components</h2>
          </div>-->
          
          <section class="prodList col-lg-12 "> 
            
            <div class="table-responsive col-lg-12 userTable topMargn_25">
          <table id="myTable" class="table tablesorter">
              <thead>
                <tr>
                  <th class="col-md-6" colspan="2">Items</th>
                  <th class="col-md-1">Points</th>
                  
                  <th class="col-md-1">Total Points</th>
			<th class="col-md-4">Quantity</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td class="col-md-1  col-xs-1 imgCol"><div > <img class="width85" alt="" src="/fmstorefront/_ui/desktop/common/images/t-shirt.jpg"> </div></td>
                  <td class="col-md-5  col-xs-5 "><div class="shoppingCartsubHead">
				
			<c:url value="/loyalty/productDetail" var="productDetail"/>		
			 <h4 class="miniCartProdName"><a href="${productDetail}">T-Shirt</a></h4>

                         <span class=""><span class="shoppingCartBold">Size:</span> Small <span class="lftMrgn_5 rghtMrgn_5">|</span><span class="shoppingCartBold">Color:</span>  Red</span> </div></td>
                  
                  <td class="col-md-2"><div class="shoppingCartRate text-right">30</div></td>
                
                  <td class="col-md-2"><div class="shoppingCartRate text-right">60</div></td>
  			<td class="col-md-2">
                       <div class="col-md-4"> <input type="text" value="2" class="form-control width58 "></div>
		<!--<c:url value="/loyalty/checkout" var="submitAction" />
            <div class="col-lg-8"> <a href=${submitAction}><button class="btn btn-fmDefault pull-right addCart">Add to cart</button></a>  </div>-->

                     </td>
                </tr>
                <tr>
                  <td class="col-md-1  col-xs-1 imgCol"><div > <img class="width85" alt="" src="/fmstorefront/_ui/desktop/common/images/t-shirt.jpg"> </div></td>
                  <td class="col-md-5  col-xs-5 "><div class="shoppingCartsubHead">
                     <c:url value="/loyalty/productDetail" var="productDetail"/>		
			 <h4 class="miniCartProdName"><a href="${productDetail}">T-Shirt</a></h4>
                      <span class=""><span class="shoppingCartBold">Size:</span> Small <span class="lftMrgn_5 rghtMrgn_5">|</span><span class="shoppingCartBold">Color:</span>  Blue</span> </div></td>
                  
                  <td class="col-md-2"><div class="shoppingCartRate text-right">30</div></td>
                
                  <td class="col-md-2"><div class="shoppingCartRate text-right">30</div></td>
 			 <td class="col-md-2">
                       <div class="col-md-4">  <input type="text" value="1" class="form-control width58 "></div>

			<!--<c:url value="/loyalty/checkout" var="submitAction" />
            <div class="col-lg-8"> <a href=${submitAction}><button class="btn btn-fmDefault pull-right addCart">Add to cart</button></a>  </div>-->


                     </td>
                </tr>
                <tr>
                  <td class="col-md-1  col-xs-1 imgCol"><div > <img class="width85" alt="" src="/fmstorefront/_ui/desktop/common/images/t-shirt.jpg"> </div></td>
                  <td class="col-md-5  col-xs-5 "><div class="shoppingCartsubHead">
                     <c:url value="/loyalty/productDetail" var="productDetail"/>		
			 <h4 class="miniCartProdName"><a href="${productDetail}">T-Shirt</a></h4>
                      <span class=""><span class="shoppingCartBold">Size:</span> Small <span class="lftMrgn_5 rghtMrgn_5">|</span><span class="shoppingCartBold">Color:</span>  White</span> </div></td>
                  
                  <td class="col-md-2"><div class="shoppingCartRate text-right">30</div></td>
                 
                  <td class="col-md-2"><div class="shoppingCartRate text-right">60</div></td>
			 <td class="col-md-2">
                        <div class="col-md-4"> <input type="text" value="2" class="form-control width58 "></div>

		<!--<c:url value="/loyalty/checkout" var="submitAction" />
            <div class="col-lg-8"> <a href=${submitAction}><button class="btn btn-fmDefault pull-right addCart">Add to cart</button></a>  </div>-->


                     </td>
                </tr>
                <tr>
                  <td class="col-md-1  col-xs-1 imgCol"><div > <img class="width85" alt="" src="/fmstorefront/_ui/desktop/common/images/t-shirt.jpg"> </div></td>
                  <td class="col-md-5  col-xs-5 "><div class="shoppingCartsubHead">
                   <c:url value="/loyalty/productDetail" var="productDetail"/>		
			 <h4 class="miniCartProdName"><a href="${productDetail}">T-Shirt</a></h4>
                      <span class=""><span class="shoppingCartBold">Size:</span> Small <span class="lftMrgn_5 rghtMrgn_5">|</span><span class="shoppingCartBold">Color:</span>  Gray</span> </div></td>
                  
                  <td class="col-md-2"><div class="shoppingCartRate text-right">30</div></td>
               
                  <td class="col-md-2"><div class="shoppingCartRate text-right">30</div></td>
  			 <td class="col-md-2">
                        <div class="col-md-4"> <input type="text" value="1" class="form-control width58 "></div>

		<!--<c:url value="/loyalty/checkout" var="submitAction" />
            <div class="col-lg-8"> <a href=${submitAction}><button class="btn btn-fmDefault pull-right addCart">Add to cart</button></a>  </div>-->


                     </td>
                </tr>
                <tr>
                  <td colspan="2">
                    </td>
                  <td colspan="2" class="text-right"><span class="shoppingSubTotal"> Total Reedemed Points</span></td>
                  <td><p  class="shoppingCartTotal text-right">180</p></td>
                </tr>
              </tbody>
            </table>
         <!-- <div class="row shoppingCartFotter">
          	<c:url value="/loyalty/checkout" var="submitAction" />
            <div class="col-lg-12"> <a href=${submitAction}><button class="btn btn-fmDefault pull-right shoppingCartCheckOut">Checkout</button></a>  </div>
          </div> -->
        </div>
            
          </section>
          
          <!-- pagination -->
          
        </section>
      </section>
    </div>
  </div>
</section>
	
		<!-- Brandstrip -->

	<div class="well well-sm well-brandstrip clearfix">
		<ul class="nav ">
			<!-- <c:set var="fmComponentName" value="brandStripB2B" scope="session" />-->
			<c:set var="fmComponentName" value="brandStrip" scope="session" />

			<cms:pageSlot position="FMBrandstrip" var="feature">
				<cms:component component="${feature}" />
			</cms:pageSlot>
		</ul>
	</div>
	<c:set var="fmComponentName" value="footerImageLinks" scope="session" />
	</template:page>
	
	<script type="text/javascript">

$(document).ready(function() {
    $('[id^=detail-]').hide();
    $('.toggle').click(function() {
		$(this).toggleClass('collapsed');
        $input = $( this );
        $target = $('#'+$input.attr('data-toggle'));
        $target.slideToggle();
    });
});
</script>
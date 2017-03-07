<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="breadcrumb"
	tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>

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

<section class="customBgBlock checkoutPage">
  <div class="container">
    <div class="row">
      <div class="col-xs-12 checkoutSteps">
        <ul class="nav nav-pills nav-justified thumbnail setup-panel">
          <li class="active"><a href="#step-1">Shipping Address</a><span class="chevron"></span></li>
          <li class="disabled"><a href="#step-2">Review & Place Order</a><span class="chevron"></span></li>
          <li class="disabled"><a href="#step-3">Order Confirmation</a></li>
        </ul>
      </div>
    </div>
  </div>
</section>
<section class="breadcrumbPanel visible-lg visible-md visible-sm" itemscope itemtype="http://schema.org/Product">
  <div class="container">
    <div class="row">
      <ul class="breadcrumb">
        <li><a href="homepageB2b_small.html"><span class="fa fa-home"></span> Home</a> <span class="fa fa-angle-right"></span></li>
        <li><a href="#" class="text-capitalize" itemprop="url">shopping cart</a> <span class="fa fa-angle-right " ></span></li>
        <li><a href="#" class="selected text-capitalize" itemprop="url">checkout</a></li>
      </ul>
    </div>
  </div>
</section>
<section class="productDetailPage pageContet">
  <div class="container">
    <div class="clearfix bgwhite setup-content" id="step-1">
      <div class="col-xs-12 chekoutBillingShippingAddress">
        <div class="clearfix">
          <div class="col-sm-6 chekoutShipping chekoutShippingWithNoBorder">
            <h2 class="text-uppercase ">Ship to</h2>
            <p class="reqField"> <span class="fm_fntRed">*</span> Required Fields </p>
            <div class="">
              <form class="CheckoutBillTo">
                <div class="form-group  regFormFieldGroup  topMargn_10">
                  <label class="" for="shipfirstName">First Name<span class="fm_fntRed">*</span></label>
                  <input type="text" id="shipfirstName" name="shipfirstName" placeholder="" class="form-control width270"/>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shiplasttName">Last Name<span class="fm_fntRed">*</span></label>
                  <input type="text" id="shiplasttName" name="shiplasttName" placeholder="" class="form-control width270"/>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipAddress">Address<span class="fm_fntRed">*</span></label>
                  <input id="shipAddress" type="text" name="Address line" placeholder="" class="form-control width270">
                  <input id="shipAddress2" type="text" name="Address line 2" placeholder="" class="form-control width270 topMargn_10">
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipCity">City<span class="fm_fntRed">*</span></label>
                  <input id="shipCity" type="text" name="shipcity" placeholder="" class="form-control width270"/>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipstateProvince">State / Province<span class="fm_fntRed">*</span></label>
                  <select id="shipstateProvince" name="shipstateProvince" class="form-control width270">
                    <option >Select</option>
                  </select>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipZipPostalCode"><span class="text-uppercase">Zip</span> / Postal Code<span class="fm_fntRed">*</span></label>
                  <input type="text" id="shipZipPostalCode" name="shipZipPostalCode" placeholder="" class="form-control width270"/>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipcountry">Country<span class="fm_fntRed">*</span></label>
                  <select id="shipcountry" name="shipcountry" class="form-control width270">
                    <option > United States</option>
                  </select>
                </div>
                <ul class="list-group checkboxGroup">
                  <li class="list-group-item">
                    <label for="savetomyaddress2">
                     <!-- <input type="checkbox"  id="savetomyaddress2" onClick="$('.billcontactNickname2').toggle();"/>
                      &nbsp;Save to my address book -->
			</label>
                  </li>
                </ul>
                <div class="form-group  regFormFieldGroup  billcontactNickname2" style="display:none">
                  <label class="" for="shipcontactNickname">Contact Nickname</label>
                  <input type="text" id="shipcontactNickname" name="contactNickname" placeholder="" class="form-control width270"/>
                </div>
              </form>
            </div>
          </div>
          
        </div>
        <div class="row">
          <div class="col-sm-5 col-sm-offset-7">
            <button id="activate-step-2" class="btn btn-sm btn-fmDefault pull-right text-uppercase">Continue to Review &amp; Place ORder</button></div>
        </div>
      </div>
    </div>
    <div class="clearfix bgwhite setup-content" id="step-2">
      <div class="row">
        <div class="reviewPlaceOrderB2C">
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <h2 class="text-uppercase">review &amp; place order</h2>
            <div class="row">
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Shipping Details</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                  <p class="reviewPlaceOrderBold  text-capitalize">Shipping address</span></p>
                  <p class="text-uppercase">UNI-SELECT USA INC</p>
                  <p>8080 E 33<sup>rd</sup> Street</p>
                  <p>INDIANAPOLIS, IN 46226-6530</p>
                  <p>(000) 000-0000</p>
                </div>
               
              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Reedem Points</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                <p class="reviewPlaceOrderBold text-capitalize">Points Reedemed</p>
                <p>180</p>
              </div>
                
              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Available Points</div>
                </div>
                <div class="reviewFirstPanelMargin  lftPad_10">
                  <p class="reviewPlaceOrderBold text-capitalize">Remaining Points</span></p>
                  <p>20</p>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="reviewPlaceOrderFirstPanel userTable">
                <div class="shipmentMethodSubHeadingFirst">
                  <div class="panel  panel-primary panel-frm">
                    <div class="panel-heading clickable">
                      <h3 class="panel-title text-uppercase">Shipment 1</h3>
                      <span class="pull-right "><i class="fa fa-minus"></i></span> </div>
                    <div class="panel-body rgtPad_0">
                      <div class="table-responsive userTable topMargn_20">
                        <table class="table tablesorter shipmentTable">
                          <thead>
                            <tr class="">
                              <th class="shipmentMethodTr text-capitalize col-md-6" colspan="2">Item details</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Points</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Quantity</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Total Points</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: Red</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">2</td>
                              <td class="text-right col-md-2">60</td>
                            </tr>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: Blue</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">1</td>
                              <td class="text-right col-md-2">30</td>
                            </tr>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: White</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">2</td>
                              <td class="text-right col-md-2">60</td>
                            </tr>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: Gray</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">1</td>
                              <td class="text-right col-md-2">30</td>
                            </tr>
                            <tr>
			      <td colspan="3" class="freeFreightCol"></td>
                              <td colspan="2"></td>
                              <td colspan="2" class="text-right"><span class="shoppingSubTotal"> Total Reedemed Points</span></td>
                              <td><p  class="shoppingCartTotal text-right">180</p></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
        <div class="reviewPlaceOrderBtn col-lg-12">
          <button id="activate-step-3" class="btn btn-sm btn-fmDefault text-uppercase pull-right rghtMrgn_20">Place order</button>
          <button id="prev-step-1" class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right"><!--<i class="fa fa-angle-left"></i>--> Return to Shipping Address</button>
        </div>
      </div>
    </div>
    <div class="clearfix bgwhite setup-content" id="step-3">
      <div class="col-lg-12 ">
        <ul class="printShareSaveLink pull-right">
          <li><a href="#"><span class="fa fa-print"></span> Print</a></li>
          <li><a href="#"><span class="fa fa-file-pdf-o orderPdf"></span> Save PDF</a></li>
        </ul>
      </div>
      <div class="confirmationB2C">
        <div class="row">
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <h2 class="text-uppercase">thank you for reedeming the points</h2>
            <p>A copy of your order details has been sent to <strong>email@gmail.com</strong></p>
            <p>Would like to refer your friend?<strong>email@website.com</strong></p>
            <div class="ConfirmationHeadingRedPanel lftPad_10 clearfix"><span><span class="shipmentMethodBold">Order Confirmation #:</span> H349829223</span><span class="pull-right"><span class="shipmentMethodBold">Order Status</span>: In Progress</span></div>
            <div class="row">
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Shipping Details</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                  <p class="reviewPlaceOrderBold  text-capitalize">Shipping address</span></p>
                  <p class="text-uppercase">UNI-SELECT USA INC</p>
                  <p>8080 E 33<sup>rd</sup> Street</p>
                  <p>INDIANAPOLIS, IN 46226-6530</p>
                  <p>(000) 000-0000</p>
                </div>
               
              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 rgtPad_0 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Reedem Points</div>
                </div>
                <div class="reviewFirstPanelMargin lftPad_10">
                <p class="reviewPlaceOrderBold text-capitalize">Points Reedemed</p>
                <p>180</p>
              </div>
                
              </div>
              <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 lftPad_1">
                <div class="subTitle text-uppercase">
                  <div class="clearfix">Available Points</div>
                </div>
                <div class="reviewFirstPanelMargin  lftPad_10">
                  <p class="reviewPlaceOrderBold text-capitalize">Remaining Points</span></p>
                  <p>20</p>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="reviewPlaceOrderFirstPanel userTable">
                <div class="shipmentMethodSubHeadingFirst">
                  <div class="panel  panel-primary panel-frm">
                    <div class="panel-heading clickable">
                      <h3 class="panel-title text-uppercase">Shipment 1</h3>
                      <span class="pull-right "><i class="fa fa-minus"></i></span> </div>
                    <div class="panel-body rgtPad_0">
                      <div class="table-responsive userTable topMargn_20">
                        <table class="table tablesorter shipmentTable">
                          <thead>
                            <tr class="">
                              <th class="shipmentMethodTr text-capitalize col-md-6" colspan="2">Item details</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Points</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Quantity</th>
                              <th class="shipmentMethodTr text-center text-capitalize col-md-2">Total Points</th>
                            </tr>
                          </thead>
                          <tbody>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: Red</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">2</td>
                              <td class="text-right col-md-2">60</td>
                            </tr>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: Blue</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">1</td>
                              <td class="text-right col-md-2">30</td>
                            </tr>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: White</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">2</td>
                              <td class="text-right col-md-2">60</td>
                            </tr>
                            <tr>
                              <td class="col-md-1"><div class="img"> <img alt="Image" src="images/t-shirt.jpg" class="img-responsive"> </div></td>
                              <td class="text-left col-md-5"><div class="prodDetail">
                                  <h5>T-Shirt</h5>
                                  <p>Color: Gray</p>
                                  <p>Size: Small</p>                                  
                                </div></td>
                              <td class="text-right col-md-2">30</td>
                              <td class="text-center col-md-2">1</td>
                              <td class="text-right col-md-2">30</td>
                            </tr>
                            <tr>
                              <td colspan="2"></td>
                              <td colspan="2" class="text-right"><span class="shoppingSubTotal"> Total Reedemed Points</span></td>
                              <td><p  class="shoppingCartTotal text-right">180</p></td>
                            </tr>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
                
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
<!-- InstanceEndEditable -->


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
    
    var navListItems = $('ul.setup-panel li a'),
        allWells = $('.setup-content');

    allWells.hide();

    navListItems.click(function(e)
    {
        e.preventDefault();
        var $target = $($(this).attr('href')),
            $item = $(this).closest('li');
			$item1 = $(this).closest('li .active');
        if ($('ul.setup-panel').find('.active') ) {
				$('ul.setup-panel').find('.active').addClass('complete').removeClass('active');
        }
        if (!$item.hasClass('disabled')) {
            navListItems.closest('li').removeClass('active');
            $item.addClass('active');
            allWells.hide();
            $target.show();
        }
		
    });
    
    $('ul.setup-panel li.active a').trigger('click');
    $('ul.setup-panel li:first-child').addClass('active');
     // show step 2 page //
    $('#activate-step-2').on('click', function(e) {
        $('ul.setup-panel li:eq(0)').addClass('complete');
        $('ul.setup-panel li:eq(1)').removeClass('disabled');
        $('ul.setup-panel li a[href="#step-2"]').trigger('click');
    })   
      // show step 3 page //
    $('#activate-step-3').on('click', function(e) {
        $('ul.setup-panel li:eq(1)').addClass('complete');
        $('ul.setup-panel li:eq(2)').removeClass('disabled');
        $('ul.setup-panel li a[href="#step-3"]').trigger('click');
    })  
	
	     
      // show prev step 1 page //
    $('#prev-step-1').on('click', function(e) {
        $('ul.setup-panel li:eq(0)').addClass('active');
        $('ul.setup-panel li:eq(1)').removeClass('complete');
        $('ul.setup-panel li a[href="#step-1"]').trigger('click');
    })       
      // show prev step 2 page //
    $('#prev-step-2').on('click', function(e) {
        $('ul.setup-panel li:eq(1)').addClass('active');
        $('ul.setup-panel li:eq(2)').removeClass('complete');
        $('ul.setup-panel li a[href="#step-2"]').trigger('click');
    })       
      // show prev step 3 page //
    $('#prev-step-3').on('click', function(e) {
        $('ul.setup-panel li:eq(2)').addClass('active');
        $('ul.setup-panel li:eq(3)').removeClass('complete');
        $('ul.setup-panel li a[href="#step-3"]').trigger('click');
    })   
});

</script>
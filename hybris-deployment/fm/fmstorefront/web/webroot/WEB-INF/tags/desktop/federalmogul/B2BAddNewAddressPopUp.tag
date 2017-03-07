<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<div class="modal fade shipToModel" id="shipToNewAddressPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog newAddressPopup">
	    <!-- For Close icon (top right corner): -->
		<button data-dismiss="modal" class="close" type="button" onclick="javascript:clearNewAddress();"><span class="fa fa-close" aria-hidden="true"></span><span class="sr-only">Close</span></button>
	    <!-- For Close icon (top right corner) - End -->

		<div class="modal-content">
	    	<!-- For Title: -->
			<div class="modal-header">
				<h2 class="text-uppercase DINWebBold"><spring:theme code="B2BHomepage.addShipToAddress.Title"/></h2>
			</div>
	    	<!-- For Title - End -->

			<div class="modal-body">
				<div id="new">
					<div class="reviewFirstPanelMargin">
						<form:form action="/my-fmaccount/add-newaddress-session" method="post" commandName="addressForm" id="myFMAddressForm">  
          
							 <!-- For Company Name: -->
							<div class="clearfix">
								<div class="form-group regFormFieldGroup">
									<label for="firstLastName"><spring:theme code="B2BHomepage.addShipToAddress.companyName"/></label><span class="required fm_fntRed">*</span>
									<span class="required fm_fntRed"></span>
									<form:input path="firstName" id="firstLastName" type="text" class="form-control" name="fname" required="required" onblur="checkHide()" maxlength="35"/>
									<div class="errorMsg fm_fntRed" style="display:none;"><spring:theme code="B2BHomepage.addShipToAddress.companyName.error"/></div>
									<span class="errorMsg fm_fntRed" id="errorcname"> </span>
								</div>
							</div>
							<!-- For Company Name - End-->
	              
	              			<!-- For Address Line 1: -->
							<div class="form-group regFormFieldGroup">
								<label class="" for="AddressLine1"><spring:theme code="B2BHomepage.addShipToAddress.Address"/> <span class="required fm_fntRed" >*</span></label>          
								<form:input path="line1" id="Addressline1" type="texLine1t" class="form-control" name="addr" required="required" onblur="checkHide()" maxlength="30"/>
								<div class="errorMsg fm_fntRed" style="display:none;"><spring:theme code="B2BHomepage.addShipToAddress.Address.error"/></div>
								<span class="errorMsg fm_fntRed" id="erroraddress" > </span>
							</div>              
	              			<!-- For Address Line 1 - End-->
	
	              			<!-- For Address Line 2: -->
							<div class="form-group">
								<form:input path="line2" id="Addressline2" type="text" class="form-control" maxlength="30"/>
							</div>
	              			<!-- For Address Line 2 - End -->
	                
							<div class="clearfix">
	              				<!-- For City: -->
								<div class="form-group pull-left">
									<label for="city"><spring:theme code="B2BHomepage.addShipToAddress.City"/></label>
	                
	 								<span class="required fm_fntRed">*</span>
									<form:input path="townCity" id="city" type="text" class="form-control width268" name="city" required="required" onblur="checkHide()" maxlength="20"/> 
									<div class="errorMsg fm_fntRed" style="display:none;"><spring:theme code="B2BHomepage.addShipToAddress.City.error"/></div>
									<span class="errorMsg fm_fntRed" id="errorcity"> </span>
								</div>
	              				<!-- For City - End -->

	              				<!-- For Country: -->
								<div class="form-group pull-left lftMrgn_20">
									<label for="country" ><spring:theme code="B2BHomepage.addShipToAddress.country"/></label>
									<form:input path="countryIso" type="text" id="coutntry" class="form-control width268" disabled="true" value="${country.name}"/>
								</div>
	              				<!-- For Country - End -->
							</div>
	
							<div class="clearfix">
	              				<!-- For State/Province: -->
								<div class="form-group pull-left" >
									<label for="prov"><spring:theme code="B2BHomepage.addShipToAddress.stateProvince"/><span class="required fm_fntRed" >*</span></label>
									<form:select path="region" id="prov" type="text" class="form-control width268"  onblur="checkHide()">
										<option value=" " selected="selected">Select</option>
										<c:forEach items="${regionData}" var="region">		
											<option value="${region.isocode}">${region.name}</option>
										</c:forEach>
									</form:select>  
									<!-- <span style="color:red;" id="errorstate" > </span>   -->   
									<div class="errorMsg fm_fntRed" style="display:none;"><spring:theme code="B2BHomepage.addShipToAddress.stateProvince.error"/></div> 
									<span class="errorMsg fm_fntRed" id="errorstate" > </span>    
								</div>
	              				<!-- For State/Province - End -->
	
	              				<!-- For Zip/Postal Code: -->
								<div class="form-group pull-left lftMrgn_20">
									<label for="zip"><spring:theme code="B2BHomepage.addShipToAddress.zipPostalCode"/></label>
	               
									<span class="required fm_fntRed" >*</span>
	 								<form:input path="postcode" type="text" id="zip" class="form-control width175" name="postalcode" required="required" onblur="checkHide()" maxlength="7"/>
									<div class="errorMsg fm_fntRed" style="display:none;"><spring:theme code="B2BHomepage.addShipToAddress.zipPostalCode.error"/></div>
									<!--  <span style="color:red;" id="errorzip" > </span> -->
									<span class="errorMsg fm_fntRed" id="errorzip" > </span>
								</div>
	              				<!-- For Zip/Postal Code - End -->
							</div>
	
	              			<!-- For Contact Phone #: -->
							<div class="form-group">
								<label for="contactNumber" ><spring:theme code="B2BHomepage.addShipToAddress.contactNumber"/></label>
	              
								<!-- <span class="required fm_fntRed" ></span> -->
								<form:input path="contactNo" id="contactNumber" type="text" class="form-control width175" name="cnum" required="required" onblur="validate()" placeholder="(555)555-5555" maxlength="13"/>
								<!-- <div class="errorMsg fm_fntRed" style="display:none;">Please enter Contact Number</div>
								<span style="color:red;" id="errorcnum" > </span> -->
							</div>
	              			<!-- For Contact Phone # - End -->
	            
	              			<!-- For Submit and Cancel buttons: -->
							<div class="">
								<!--    <button class="btn btn-fmDefault" type="button" onClick="$('#shipTonewAddress').show();"  onclick="javascript:addNewAddressToSession();"data-dismiss="modal" >Submit</button> -->
								<button class="btn btn-fmDefault" id="submitNewAddress" type="button" onclick="javascript:addNewAddressToSession();" ><spring:theme code="B2BHomepage.addShipToAddress.Submit"/></button>
								<button class="btn btn-fmDefault cancel" type="button" data-dismiss="modal" onclick="javascript:clearNewAddress();"><spring:theme code="B2BHomepage.addShipToAddress.Cancel"/></button>
	 						</div>
	              			<!-- For Submit and Cancel buttons - End -->

						</form:form>
					</div>
				</div>
			</div>
    	</div>
	</div>
</div>
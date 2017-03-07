<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
        <div class="manageUserPanel rightHandPanel viewUnit clearfix">
        <div class="row">
        	<div class="col-md-4">
          <h2 class="text-uppercase"><spring:theme code="text.editAddress.Details" text="Address Details"/> </h2>
            </div>
        	<div class="col-md-8">
          <a class="returnToUser" href="myNetworkBUviewunit.html"><span class="fa fa-angle-left fm_fntRed"></span><spring:theme code="text.back" text="Back"/></a>
            </div>
        </div>
           <p class="reqField"> <span class="fm_fntRed"><spring:theme code="required" text="*"/> </span> <spring:theme code="user.requiredFields" text="Required Fields"/>  </p>
           <p><spring:theme code="text.editAddressDetails.form" text="Please use this forms to add/edit an address"/></p>
          <form class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetails">
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetailsPanelFirst">
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Title"><spring:theme code="address.title" text="Title"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <select id="Title"  name="Title" class="form-control width270">
                    <option value="volvo">Please select</option>
                  </select>
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="firstname"><spring:theme code="address.firstName" text="First Name"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <input type="text" id="firstnam" name="firstnam" placeholder="" value="Name" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="surname"><spring:theme code="address.surname" text="Surname"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <input type="text" id="surname" name="surname" placeholder="" value="Surname" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Addressline1"><spring:theme code="address.line1" text="Address line 1"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <input id="Addressline1" type="text" name="Addressline1" placeholder="" value="1000 Bagby Street" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Addressline2"><spring:theme code="address.line2" text="Address line 2"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <input id="Addressline2" type="text" name="Addressline2" placeholder="" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Town/City"><spring:theme code="address.townCity" text="Town/City"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <input type="text" id="Town/City" name="Town/City" placeholder="" value="Houston" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="ZipCode"><spring:theme code="address.zipcode" text="Zip Code"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <input type="text" id="ZipCode" name="ZipCode" placeholder="" value="Texas" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Country"><spring:theme code="address.country" text="Country"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <select id="Country"  name="Country" class="form-control width270">
                    <option value="">Please select the country</option>
                    <option value="" selected>United States</option>
                  </select>
                </div>
                </div>
                <div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetailsPanelFirst">
                <div class="clearfix">
                    <button class="btn btn-fmDefault btn-fm-Grey"><spring:theme code="text.editAddressDetails.cancel" text="cancel"/></button>
                    <button class="btn btn-fmDefault"><spring:theme code="text.editAddressDetails.update" text="Update address"/></button>
                </div>
                </div>
              </form>
        </div>
      </div>
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
          <h2 class="text-uppercase">Address Details </h2>
            </div>
        	<div class="col-md-8">
          <a class="returnToUser" href="myNetworkBUviewunit.html"><span class="fa fa-angle-left fm_fntRed"></span> Back</a>
            </div>
        </div>
           <p class="reqField"> <span class="fm_fntRed">*</span> Required Fields </p>
           <p>Please use this forms to add/adit an address</p>
          <form class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetails">
          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetailsPanelFirst">
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Title">Title<span class="fm_fntRed">*</span></label>
                  <select id="Title"  name="Title" class="form-control width270">
                    <option value="volvo">Please select</option>
                  </select>
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="firstname">First Name<span class="fm_fntRed">*</span></label>
                  <input type="text" id="firstnam" name="firstnam" placeholder="" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="surname">Surname<span class="fm_fntRed">*</span></label>
                  <input type="text" id="surname" name="surname" placeholder="" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Addressline1">Address line 1<span class="fm_fntRed">*</span></label>
                  <input id="Addressline1" type="text" name="Addressline1" placeholder="" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Addressline2">Address line 2<span class="fm_fntRed">*</span></label>
                  <input id="Addressline2" type="text" name="Addressline2" placeholder="" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Town/City">Town/City<span class="fm_fntRed">*</span></label>
                  <input type="text" id="Town/City" name="Town/City" placeholder="" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="ZipCode">Zip Code<span class="fm_fntRed">*</span></label>
                  <input type="text" id="ZipCode" name="ZipCode" placeholder="" class="form-control width270">
                </div>
                <div class="form-group  regFormFieldGroup">
                  <label class="" for="Country">Country<span class="fm_fntRed">*</span></label>
                  <select id="Country"  name="Country" class="form-control width270">
                    <option value="volvo">Please select the country</option>
                  </select>
                </div>
                </div>
                <div  class="col-lg-12 col-md-12 col-sm-12 col-xs-12 addressBookFillDetailsPanelFirst">
                <div class="clearfix">
                    <button class="btn btn-fmDefault btn-fm-Grey">cancel</button>
                    <button class="btn btn-fmDefault">Save address</button>
                </div>
                </div>
              </form>
        </div>
      </div>
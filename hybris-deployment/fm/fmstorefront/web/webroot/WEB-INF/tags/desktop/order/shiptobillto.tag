<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formElement" tagdir="/WEB-INF/tags/desktop/form" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>

<%@ taglib prefix="theme"uri ="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="fn"uri ="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="common"uri ="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="ycommerce"uri ="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="breadcrumb" tagdir="/WEB-INF/tags/desktop/nav/breadcrumb"%>
 --%>
<div id="step-1" class="clearfix bgwhite setup-content" style="display: block;">
<div class="col-xs-12 chekoutBillingShippingAddress">
        <div class="clearfix">
          <div class="col-sm-6 chekoutShipping chekoutShippingWithNoBorder">
            <h2 class="text-uppercase "><spring:theme code="checkout.page.ShipTo"/></h2>
            <p class="reqField"> <span class="fm_fntRed"><spring:theme code="required" text="*"/></span><spring:theme code="checkout.page.ShipTo.Required.Fields"/></p>
            <div class="">
             <%--  <form class="AddressForm"> --%>
              <form:form method="POST" commandName="shipToAddress" modelAttribute="shipToAddress" id="abcd" action="deliverymethod">
               
                <div class="form-group  regFormFieldGroup  topMargn_10">
                  <label class=""   for="shipfirstName"><spring:theme code="checkout.page.ShipTo.firstName"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <form:input type="text" id="shipfirstName" path="firstName"  value="${shipToAddress.firstName}" name="shipfirstName" placeholder="" class="form-control width270"></form:input>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shiplasttName"><spring:theme code="checkout.page.ShipTo.lastName"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <form:input type="text" id="shiplasttName" path="lastName" value="${shipToAddress.lastName}" name="shiplasttName" placeholder="" class="form-control width270"></form:input>
                </div>
                
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipAddress"><spring:theme code="checkout.page.ShipTo.Address"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <form:input id="shipAddress" type="text" path="line1" name="Address line" value="${shipToAddress.line1}" placeholder="" class="form-control width270"></form:input>
                  <form:input id="shipAddress2" type="text" path="line2" name="Address line 2" value="${shipToAddress.line2}" placeholder="" class="form-control width270 topMargn_10"></form:input>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipCity"><spring:theme code="checkout.page.ShipTo.City"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <form:input id="shipCity" type="text" path="town" value="${shipToAddress.town}" name="shipcity" placeholder="" class="form-control width270"></form:input>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipstateProvince"><spring:theme code="checkout.page.ShipTo.statePorvince"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <select id="shipstateProvince"   name="shipstateProvince"  class="form-control width270">
                    <option >${shipToAddress.town}</option>
                  </select>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipZipPostalCode"><span class="text-uppercase"><spring:theme code="checkout.page.ShipTo.zip"/></span> / <spring:theme code="checkout.page.ShipTo.postalCode"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <form:input type="text" id="shipZipPostalCode" path="postalCode" value="${shipToAddress.postalCode}" name="shipZipPostalCode" placeholder="" class="form-control width270"></form:input>
                </div>
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipcountry"><spring:theme code="checkout.page.ShipTo.Country"/><span class="fm_fntRed"><spring:theme code="required" text="*"/></span></label>
                  <select id="shipcountry"  path="name"  name="shipcountry" class="form-control width270">
                    <option >${shipToAddress.region.name}</option>
                  </select>
                </div>
                
                 <ul class="list-group checkboxGroup">
                  <li class="list-group-item">
                    <label for="savetomyaddress2">
                      <input type="checkbox"  id="savetomyaddress2"/>
                      &nbsp;<spring:theme code="checkout.page.ShipTo.addressSave"/></label>
                  </li>
                </ul>
               
                <div class="form-group  regFormFieldGroup ">
                  <label class="" for="shipcontactNickname"><spring:theme code="checkout.page.ShipTo.contact.Nickname"/></label>
                  <input type="text" id="shipcontactNickname"  name="contactNickname" placeholder="" class="form-control width270"></input>
                </div>
                
                
               <!--  <button type="submit" class="btn btn-sm btn-fmDefault pull-right text-uppercase">continue to delivery method</button> -->
              </form:form>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-sm-offset-7">
            <!-- <button id="activate-step-2" onclick="return submit();" type="submit" class="btn btn-sm btn-fmDefault pull-right text-uppercase">continue to delivery method</button> -->
            <button id="activate-step-2" type="submit" onclick="return submit();" class="btn btn-sm btn-fmDefault pull-right text-uppercase"><spring:theme code="checkout.page.delivery.method.continue"/></button>
          <%--  <c:url value="/checkout/multi/deliveryMethod" var="encodedUrl"/>
            <a href="${encodedUrl }" class="btn btn-sm btn-fmDefault pull-right text-uppercase">continue to delivery method</a>  --%>
            <a href="shoppingCart_B2C.html">
            <button class="btn btn-sm btn-fmDefault btn-fm-Grey text-uppercase pull-right"><!--<i class="fa fa-angle-left"></i>--> <spring:theme code="checkout.page.return.cart"/></button>
            </a> </div>
        </div>
      </div>
    </div>  
     <script type="text/javascript">
function submit(){
	document.getElementById("abcd").submit();
}
</script>
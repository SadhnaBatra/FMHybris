<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/desktop/common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form:form class="registrationB2b" method="POST"
           commandName="fmdata" id="registrationB2b" name="registrationB2b"
           enctype="multipart/form-data">

    <div class="form-group  regFormFieldGroup form-b2bRegbody" id="companyDiv">

        <div class="form-group  regFormFieldGroup ">
            <label class="" for="companyName">Company Name</label>
            <form:input type="text" id="companyName" name="company" class="form-control width375" path="companyName" disabled="true" value="${unitdetails.locName}"  />
        </div>

        <div class="form-group  regFormFieldGroup ">
            <label class="" for="companyAddress">Company Address</label>
            <form:input id="companyAddress" type="text" name="Address line" class="form-control width375" path="companyaddressline1" disabled="true" value="${unitdetails.addresses[0].line1}" />
        </div>

        <div class="form-group  regFormFieldGroup ">
            <form:input id="companyAddress2" type="text" name="Address line 2" class="form-control width375 topMargn_10" path="companyaddressline2" disabled="true" value="${unitdetails.addresses[0].line2}" />
        </div>

        <div class="form-group  regFormFieldGroup ">
            <label class="" for="companyCity">Company City</label>
            <form:input id="companyCity" type="text" name="city" class="form-control width375" path="companycity" disabled="true" value="${unitdetails.addresses[0].town}" />
        </div>

        <div class="form-group  regFormFieldGroup" id="unitState">
            <label class="" for="companyState">
                Company State / Province
            </label>
            <form:input id="companyState" type="text" name="state" class="form-control width220" path="companystate" disabled="true" value="${unitdetails.addresses[0].region.name}" />
        </div>

        <div class="form-group  regFormFieldGroup ">
            <label class="" for="companyZip">Company ZIP / Postal Code</label>
            <form:input type="text" id="companyZip" name="companyZip" class="form-control width375" path="companyzipCode" disabled="true" value="${unitdetails.addresses[0].postalcode}" />
        </div>


        <div class="form-group  regFormFieldGroup">
            <label class="" for="Ccountry">Company Country</label>
            <form:input id="Ccountry" name="Ccountry" class="form-control width165" path="companycountry" disabled="true" value="${unitdetails.addresses[0].country.name}" />
        </div>

        <div class="form-group  regFormFieldGroup clearfix">

            <div class="pull-left">
                <label class="" for="companyPhone">Company Phone Number</label>
                <form:input type="tel" id="companyPhone" name="companyFax" maxlength="15" class="form-control width165" path="companyPhoneNumber" disabled="true" value="${unitdetails.addresses[0].phone1}" />
            </div>

            <div class="pull-left lftMrgn_20">
                <label class="" for="companyFax">Company Fax</label>
                <form:input type="tel" id="companyFax" name="companyFax" maxlength="15" class="form-control width165" path="companyFax" disabled="true" value="${unitdetails.addresses[0].fax}" />
            </div>

        </div>

        <div class="form-group" id="registration-incorrect-company-info-msg">
            If your company information presented above is not correct, please contact customer service at 1-800-334-3210.
        </div>

    </div>

</form:form>

<%--  For account code error--%>
<script>
    $(document).ready(
        function() {
            afterGetUnits();
        });
</script>
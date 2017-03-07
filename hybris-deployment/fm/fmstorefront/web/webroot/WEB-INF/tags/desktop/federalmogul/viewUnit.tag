<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags" %>

<spring:url value="/my-company/organization-management/manage-units/add-address" var="addUnitAddressUrl">
	<spring:param name="unit" value="${unit.uid}"/>
</spring:url>

<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 manageUnitViewUniteAutoSupply">
        <div class="manageUserPanel rightHandPanel viewUnit clearfix">
          <h2 class="text-uppercase">view unit: united auto supply</h2>
          <div class="row">
            <div class="col-md-12">
             <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.</p>
             <form class="">
                 <div class="form-group  regFormFieldGroup ">
                  <label class="" for="buUnitID">Business Unit Id:</label>
                  <input type="text" id="buUnitID" name="buUnitID" value="United Auto Supply" disabled  class="form-control width270">
                 </div>  
                 <div class="form-group  regFormFieldGroup ">
                  <label class="" for="lastName">Last Name:</label>
                  <input type="text" id="lastName" name="lastName" value="United Auto Supply" disabled class="form-control width270">
                 </div>    
                 <div class="form-group  regFormFieldGroup ">
                  <label class="" for="parentBU">Parent Business Unit:</label>
                  <input type="text" id="parentBU" name="parentBU" value="FM Organization"disabled class="form-control width270">
                 </div>    
                 <div class="form-group  regFormFieldGroup ">
                  <label class="" for="approvalProcess">Approval Process:</label>
                  <input type="text" id="approvalProcess" name="approvalProcess" value="" disabled class="form-control width270">
                 </div>  
                  <div class="form-group">
                    <button type="button" class="btn btn-default btn-fmDefault">Edit </button>
                    <button type="button" class="btn btn-default btn-fmDefault lftMrgn_20">disable</button>
                  </div>
              </form>
            </div>
          </div>
          
          <div class="checkoutSubTitle text-uppercase"> Account managers</div>
          <p>The account mangers are powertools store contact for the unit.</p>
          <h5 class="text-uppercase">name</h5>
          <div><div class="checkoutSubTitle text-uppercase">Addresses</div>
          <div class="clearfix"><p class="">The addresses will be available to the customer ordering for a cost center related to its unit.</p>
                <button type="button" class="btn btn-default btn-fmDefault search">create</button></div>
          <div class="table-responsive userTable">
            <table class="table tablesorter" id="myTable">
              <thead>
                <tr class="text-capitalize">
                  <th>address line 1</th>
                  <th>address line 2</th>
                  <th>town / city</th>
                  <th>postal code</th>
                  <th>country </th>
                  <th>actions</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1000 Bagby Street</td>
                  <td></td>
                  <td>Houston</td>
                  <td>Texas</td>
                  <td>United States</td>
                   <td class="linkCol"><a href="#">Edit</a><span class="divider">|</span><a href="#">Remove</a></td>
                </tr>
                <tr>
                  <td>1000 Bagby Street</td>
                  <td></td>
                  <td>Houston</td>
                  <td>Texas</td>
                  <td>United States</td>
                   <td class="linkCol"><a href="#">Edit</a><span class="divider">|</span><a href="#">Remove</a></td>
                </tr>
                <tr>
                  <td>1000 Bagby Street</td>
                  <td></td>
                  <td>Houston</td>
                  <td>Texas</td>
                  <td>United States</td>
                   <td class="linkCol"><a href="#">Edit</a><span class="divider">|</span><a href="#">Remove</a></td>
                </tr>
              </tbody>
            </table>
          </div>
          </div>
          <div><div class="checkoutSubTitle text-uppercase">Cost Center</div>
          <div class="clearfix"><p class="">The addresses will be available to the customer ordering for a cost center related to its unit.</p>
                <button type="button" class="btn btn-default btn-fmDefault search">create</button></div>
          <div class="table-responsive userTable">
            <table id="myTable" class="table tablesorter">
                    <thead>
                      <tr class="">
                        <th class="shipmentMethodTr">ID</th>
                        <th class="shipmentMethodTr">Name</th>
                        <th class="shipmentMethodTr">Currency</th>
                        <th class="shipmentMethodTr">Action</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr class="">
                        <td class="">ID</td>
                        <td class="">Name</td>
                        <td class="">Currency</td>
                        <td class="">Action</td>
                      </tr>
                      </tbody>
                    </table>
          </div>
          </div>
          <div><div class="checkoutSubTitle text-uppercase">child units</div>
          <div class="clearfix"><p class="">The addresses will be available to the customer ordering for a cost center related to its unit.</p>
                <button type="button" class="btn btn-default btn-fmDefault search">create</button></div>
          <div class="table-responsive userTable">
            <table id="myTable" class="table tablesorter">
                    <thead>
                      <tr class="">
                        <th class="shipmentMethodTr">ID</th>
                        <th class="shipmentMethodTr">Name</th>
                        <th class="shipmentMethodTr">Actions</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr class="">
                        <td class="">ID</td>
                        <td class="">Name</td>
                        <td class="">Action</td>
                      </tr>
                      </tbody>
                    </table>
          </div>
          </div>
          <div><div class="checkoutSubTitle text-uppercase">approvers</div>
          <div class="clearfix"><p class="">The addresses will be available to the customer ordering for a cost center related to its unit.</p>
                <button type="button" class="btn btn-default btn-fmDefault search">Edit</button>
                <button type="button" class="btn btn-default btn-fmDefault search">Create Units</button></div>
          <div class="table-responsive userTable">
            <table id="myTable" class="table tablesorter">
                    <thead>
                      <tr class="">
                        <th class="shipmentMethodTr">Name</th>
                        <th class="shipmentMethodTr">Email</th>
                        <th class="shipmentMethodTr">Role</th>
                        <th class="shipmentMethodTr">Actions</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr class="">
                        <td class="">Name</td>
                        <td class="">Email</td>
                        <td class="">Role</td>
                        <td class="">Action</td>
                      </tr>
                      </tbody>
                    </table>
          </div>
          </div>
          <div><div class="checkoutSubTitle text-uppercase">administrators</div>
          <div class="clearfix"><p class="">The addresses will be available to the customer ordering for a cost center related to its unit.</p>
                <button type="button" class="btn btn-default btn-fmDefault search">Edit</button>
                <button type="button" class="btn btn-default btn-fmDefault search">Create Units</button></div>
          <div class="table-responsive userTable">
            <table id="myTable" class="table tablesorter">
                    <thead>
                      <tr class="">
                        <th class="shipmentMethodTr">Name</th>
                        <th class="shipmentMethodTr">Email</th>
                        <th class="shipmentMethodTr">Role</th>
                        <th class="shipmentMethodTr">Actions</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr class="">
                        <td class="">Name</td>
                        <td class="">Email</td>
                        <td class="">Role</td>
                        <td class="">Action</td>
                      </tr>
                      </tbody>
                    </table>
          </div>
          </div>
          <div><div class="checkoutSubTitle text-uppercase">managers</div>
          <div class="clearfix"><p class="">The addresses will be available to the customer ordering for a cost center related to its unit.</p>
                <button type="button" class="btn btn-default btn-fmDefault search">Edit</button>
                <button type="button" class="btn btn-default btn-fmDefault search">Create Units</button></div>
          <div class="table-responsive userTable">
            <table id="myTable" class="table tablesorter">
                    <thead>
                      <tr class="">
                        <th class="shipmentMethodTr">Name</th>
                        <th class="shipmentMethodTr">Email</th>
                        <th class="shipmentMethodTr">Role</th>
                        <th class="shipmentMethodTr">Actions</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr class="">
                        <td class="">Name</td>
                        <td class="">Email</td>
                        <td class="">Role</td>
                        <td class="">Action</td>
                      </tr>
                      </tbody>
                    </table>
          </div>
          </div>
          <div><div class="checkoutSubTitle text-uppercase">customers</div>
          <div class="clearfix"><p class="">The addresses will be available to the customer ordering for a cost center related to its unit.</p>
                <button type="button" class="btn btn-default btn-fmDefault search">Edit</button>
                <button type="button" class="btn btn-default btn-fmDefault search">Create Units</button></div>
          <div class="table-responsive userTable">
            <table id="myTable" class="table tablesorter">
                    <thead>
                      <tr class="">
                        <th class="shipmentMethodTr">Name</th>
                        <th class="shipmentMethodTr">Email</th>
                        <th class="shipmentMethodTr">Role</th>
                        <th class="shipmentMethodTr">Actions</th>
                      </tr>
                      </thead>
                      <tbody>
                      <tr class="">
                        <td class="">Name</td>
                        <td class="">Email</td>
                        <td class="">Role</td>
                        <td class="">Action</td>
                      </tr>
                      </tbody>
                    </table>
          </div>
          </div>
        </div>
      </div>
<!doctype html>
<html>
<section class="accountDetailPage pageContet" >
  <div class="container manageAccount">
  <div class="row">
  <!-- Starts: Manage Account Right hand side panel -->
  <div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
    <div class="manageUserPanel rightHandPanel clearfix">
      <h2 class="text-uppercase">Return Request</h2>
      <div class="chooseShipMethod clearfix">
        <div class="col-md-4">
          <label class="shipmentPrice ">Confirmation Number</label>
          <div>H123456</div>
        </div>
        <div class="col-md-4">
          <label class="shipmentPrice ">Invoice Number</label>
          <div>2312431</div>
        </div>
        <!--<div class="col-md-4">
                          <label class="shipmentPlace ">Purchase Order Number</label> 
                          <div>Skokie, IL </div></div> -->
        
        <div class="col-md-4">
          <label class="shipmentPlace ">Sales Order Number</label>
          <div><a href="3">ECCSONO12345 </a></div>
        </div>
      </div>
      <div class="chooseShipMethod clearfix">
        <div class="col-md-4">
          <label class="shipmentPrice ">Return Reason <span class="fm_fntRed">*</span></label>
          <div>
            <select class="form-control">
              <option>Damaged</option>
              <option>Shipping discrepancy</option>
            </select>
          </div>
        </div>
        <div class="col-md-4">
          <label class="shipmentPrice ">Return Description</label>
          <div>
            <textarea class="form-control">
                       
                           </textarea>
          </div>
        </div>
      </div>
      <div class="table-responsive userTable topMargn_25"><!-- id="shipmentTable"-->
        <table class="table tablesorter shipmentTable">
          <thead>
            <tr class="">
              <th class="shipmentMethodTr text-capitalize"><!--<input type="checkbox" id="fullfillOption1">--></th>
              <th class="shipmentMethodTr text-capitalize" colspan="2">Item Description</th>
              <th class="shipmentMethodTr text-center text-capitalize">Ordered Qty.</th>
              <th class="shipmentMethodTr text-center text-capitalize">Return Qty.</th>
              <!-- <th class="shipmentMethodTr text-center text-capitalize">Reason for return</th>
             <th class="shipmentMethodTr text-center text-capitalize">Original Price</th> --> 
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><input type="checkbox" id="fullfillOption2" checked></td>
              <td><div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div></td>
              <td><div class="prodDetail">
                  <h4>Wagner® ThermoQuiet® Brake Pads</h4>
                  <p>Part No: 4LW550</p>
                </div></td>
              <td class="text-center">3</td>
              <td class="text-right"><input type="text" class="form-control width75 text-right" value="3"/></td>
              <!--   <td class="text-center"><select id="display" class="form-control">
                  <option>Select</option>
                </select></td>
            <td class="text-right">49.26</td>--> 
            </tr>
            <tr>
              <td><input type="checkbox" id="fullfillOption3" checked></td>
              <td><div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div></td>
              <td><div class="prodDetail">
                  <h4>Wagner® ThermoQuiet® Brake Pads</h4>
                  <p>Part No: 4LW550</p>
                </div></td>
              <td class="text-center">40</td>
              <td class="text-right"><input type="text" class="form-control width75 text-right" value="10"/></td>
              <!-- <td class="text-center"><select id="display" class="form-control">
                  <option>Select</option>
                </select></td>
              <td class="text-right">520</td>--> 
            </tr>
            <tr>
              <td><input type="checkbox" id="fullfillOption4" checked></td>
              <td><div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div></td>
              <td><div class="prodDetail">
                  <h4>Wagner® ThermoQuiet® Brake Pads</h4>
                  <p>Part No: 4LW550</p>
                </div></td>
              <td class="text-center">40</td>
              <td class="text-right"><input type="text" class="form-control width75 text-right" value="10"/></td>
              <!-- <td class="text-center"><select id="display" class="form-control">
                  <option>Return code 3</option>
                </select></td>
                <td class="text-right">520</td>--> 
            </tr>
            <tr>
              <td><input type="checkbox" id="fullfillOption5" checked></td>
              <td><div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div></td>
              <td><div class="prodDetail">
                  <h5>Wagner® ThermoQuiet® Brake Pads</h5>
                  <p>Part No: 4LW550</p>
                </div></td>
              <td class="text-center">40</td>
              <td class="text-right"><input type="text" class="form-control width75 text-right" value="10"/></td>
              <!-- <td class="text-center"><select id="display" class="form-control">
                  <option>Return code 3</option>
                </select></td>
            <td class="text-right">520</td>--> 
            </tr>
            <tr>
              <td><input type="checkbox" id="fullfillOption6" checked></td>
              <td><div class="img"> <img alt="Image" src="images/product-alt-1.jpg" class="img-responsive"> </div></td>
              <td><div class="prodDetail">
                  <h4>Wagner® ThermoQuiet® Brake Pads</h4>
                  <p>Part No: 4LW550</p>
                </div></td>
              <td class="text-center">40</td>
              <td class="text-right"><input type="text" class="form-control width75 text-right " value="10"/></td>
              <!--<td class="text-center"><select id="display" class="form-control">
                  <option>Select</option>
                </select></td>
             <td class="text-right">520</td>--> 
            </tr>
          </tbody>
        </table>
      </div>
      <div class="topMargn_10"> <a href="#"> Please read our return policy. </a> </div>
      <div class="profileBtn topMargn_25 btmMrgn_30"><!-- <a href="profile_password.html"></a> --> 
        <a href="fmReturnRequestConfirmationPage.jsp">
        <button id="update_password" class="btn btn-fmDefault">submit </button>
        </a> <a href="fmReturnRequestConfirmationPage.jsp">
                <button class="btn btn-fmDefault ">Cancel</button>
        </a> </div>
        <div>
        
         <li class='${selected eq 'returnrequetconfiguration' ? 'nav_selected' : ''}'>
              		<c:url value="/my-fmaccount/returnrequest" var="encodedUrl" />
					<ycommerce:testId code="myFMAccount_returnrequestconfirmation_navLink">
						<a href="${encodedUrl}"><spring:theme code="text.account.returnrequestconfirmation" text="Return Request Confirmation"/></a>
					</ycommerce:testId>
              </li>
              </div>
    </div>
  </div>  
</section>
</body>
</html>

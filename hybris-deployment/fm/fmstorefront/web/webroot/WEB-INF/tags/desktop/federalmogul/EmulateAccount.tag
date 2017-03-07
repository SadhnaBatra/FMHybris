<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul" %>

<div id="quick_fade"></div>
        <div id="quick_modal">
            <img id="loader" src="/fmstorefront/_ui/desktop/common/images/spinner.gif" />
        </div>
<div class="insightPanel col-lg-12">
          <div class="panel-heading clearfix">
            <h3 class="panel-title pull-left"><span class="text-uppercase">Emulate Account</span></h3>
            <div data-toggle="buttons-radio" class="lftMrgn_20 pull-left" >
            <div class="ui-group-buttons" > <a href="#accountNum" id="emNumBtn" class="btn btn-default <c:if test="${not empty accounts }"> active</c:if>" onClick="$('.tab-block').hide();$('#accountNum').show(); $('#accSerBtn').removeClass('active');$('#orderUploadStatus').hide();">By Number </a>
              <div class="or"></div>
              <a href="#accountSearch" id="accSerBtn" class=" btn btn-default <c:if test='${not empty unitAddressData }'> active</c:if>"  onClick="$('.tab-block').hide();$('#accountSearch').show();$('#emNumBtn').removeClass('active');$('#orderUploadStatus').hide();">By Name/Location</a>

             
                
                <div class="or"></div>
               <a href="#accountCode" id="accSerBtnByNabs" class=" btn btn-default <c:if test='${not empty accountsNabs }'> active</c:if>"  onClick="$('.tab-block').hide();$('#accountCode').show();$('#orderUploadStatus').hide();$('#emNumBtn').removeClass('active');$('#accSerBtn').removeClass('active');$('#csrOrderUpload').removeClass('active');">Nabs Account Code</a> </div>
             
          </div>
<a href="#orderUploadStatus" id="csrOrderUpload" class=" btn btn-default  <c:if test='${not empty unitAddressData }'> active</c:if>"  onClick="$('.tab-block').hide();$('#orderUploadStatus').show();$('#emNumBtn').removeClass('active');$('#accSerBtn').removeClass('active');">Upload Order Status</a>
          </div>
          <!-- begin tabs going in wide content -->
          
          
        
          <div class="tab-content">



           
            <div class="tab-block" id="accountNum" 
            style="<c:choose>
		          	<c:when test="${accountNumberFlag}">display:block;</c:when>
		          	<c:otherwise>display: none;</c:otherwise>
          		  </c:choose> " >
              <div class="backOrdersBlock">
              <c:url value="/csr-emulation/get-accounts" var="getaccounts" />
                <form:form class="orderStatusForm" action="${getaccounts}" method="post" commandName="fmaccountSearchForm" >
                  <div class="row">
                    <div class="form-group col-sm-2">
                    <!--   <input id="accountNumber" class="form-control" type="text" placeholder="Account #"> -->
                      <form:input path="accountNumber" id="accountNumber" class="form-control" type="text" placeholder="Account #"/>
                    </div>
                    <div class="form-group col-sm-2">
                      <button type="submit" onClick="$('#accountNumberResult').show();" class="btn btn-default btn-fmDefault" >Search</button>
                    </div>
                  </div>
                </form:form>

	<div id="accountNumberResult1" class="backOrderTabTable">
					<div class="table-responsive userTable">
						<table class="table tablesorter" id="myTable">
						<c:if test="${csrAccountList != null }">
							<thead>
								<tr class="text-capitalize">
									<th>Account number</th>
									  <th>NABS Account Code</th>
									<th>Time</th>
								</tr>
							</thead>
						</c:if>

							<tbody>
								<c:forEach items="${csrAccountList}" var="account_unit" end="9">
									<tr class="">
										<spring:url
											value="/csr-emulation/start-emulate/${account_unit.accountnum}"
											var="encodedUrl" />
										<td><a class="orderNo" href="${encodedUrl}">${account_unit.accountnum}</a></td>
										<td><a class="orderNo" href="${encodedUrl}">${account_unit.nabsAccountCode}</a></td>
										<td>${account_unit.date}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
             <!--    <div id="accountNumberResult" class="backOrderTabTable" style="display:none" > -->
            <div id="accountNumberResult" class="backOrderTabTable" style="<c:if test="${empty accounts}">display:none</c:if>"  >
   
                  <div class="table-responsive userTable">
                    <table class="table tablesorter" id="myTable">
                      <thead>
                        <tr class="text-capitalize">
                          <th>Account number</th>
                          <th>NABS Account Code</th>
                          <th>Time </th>
                        </tr>
                      </thead>
                      <tbody>       
                     
                	   <c:if test="${test_condition eq 'allAccounts' }">                    	
                      	 <c:forEach items="${accounts}" var ="account_unit">
                       	  <tr class="">
                       	  <spring:url value="/csr-emulation/start-emulate/${account_unit.uid}" var="returnOrder" />
                       	  			
                       			 <td><a class="orderNo" href="${returnOrder}">${account_unit.uid}</a></td>  
                       			<td><a class="orderNo" href="${returnOrder}">${account_unit.nabsAccountCode}</a>
                         		 <td>${account_unit.creationTime}</td>
                          </tr>
                        </c:forEach>
                       </c:if>

						<c:if test="${test_condition eq 'UniqueAccount' }">								
						  <tr class="">
						   <spring:url value="/csr-emulation/start-emulate/${accounts.uid}" var="returnOrder" />
							
								<td><a class="orderNo" href="${returnOrder}">${accounts.uid}</a></td>
								<td><a class="orderNo" href="${returnOrder}">${accounts.nabsAccountCode}</a>
								 <td>${accounts.creationTime}</td>
						  </tr>
						</c:if>			
						</tbody>
                     </table>
                  </div>
                </div>
              </div>
            </div>
            <!--/.tab-pane -->
               
            <div class="tab-block" id="accountSearch" style="<c:if test="${addressFlag}">display: block;</c:if>
            												 <c:if test="${empty addressFlag}">display:none;</c:if> " >
              <div class="uploadOrderStatus">
              <c:url value="/csr-emulation/get-account-address" var="getaccountaddress" />
                <form:form class="orderStatusForm" action="${getaccountaddress}" method="post" commandName="fmaccountSearchForm" >
                  <div class="row accountSearch" id="accountSearchForm" style="">
                    <div class="form-group col-sm-2">
                     <!--  <input id="name" class="form-control" type="text" placeholder="Name"> -->
                      <form:input path="companyName"  id="name" class="form-control" type="text" placeholder="Company Name" />
                    </div>
                    <div class="form-group col-sm-2">
                      <!-- <input id="address" class="form-control" type="text" placeholder="Address"> -->
                      <form:input path="line1" id="address" class="form-control" type="text" placeholder="Address"/>
                    </div>
                    <div class="form-group col-sm-2">
                     <!--  <input id="city" class="form-control" type="text" placeholder="City"> -->
                      <form:input path="townCity" id="city" class="form-control" type="text" placeholder="City"/>
                    </div>
                    <div class="form-group col-sm-2">
                   <!--    <input id="stateOrProvince" class="form-control" type="text" placeholder="State or Province"> -->
                      <form:input path="region" id="stateOrProvince" class="form-control" type="text" placeholder="State or Province"/>
                    </div>
                    <div class="form-group col-sm-2">
                      <!-- <input id="postalOrZip" class="form-control" type="text" placeholder="Zip Or Postal"> -->
                      <form:input path="postcode" id="postalOrZip" class="form-control" type="text" placeholder="Zip Or Postal" />
                    </div>
                    <div class="form-group col-sm-2">
                      <button type="submit" class="btn btn-default btn-fmDefault" onClick="$('.userSearchTable').hide();$('.accountSearchTable').show();">Search</button>
                    </div>
                  </div>
                </form:form>
                <div class="accountSearchTable" style="<c:if test="${empty unitAddressData}">display:none</c:if>">
                  <div class="table-responsive userTable">
                    <table class="table tablesorter" id="myTable">
                      <thead>
                        <tr class="text-capitalize">
                        <tr class="text-capitalize">
                          <th>Account code</th>
                          <th>Company Name </th>
                          <th>Address </th>
                          <th>City </th>
                          <th>State/Prov </th>
                          <th>Zip/Postal </th>
                          <th>Comments </th>
                        </tr>
                      </thead>
                      <tbody>
                      
                     <c:forEach items="${unitAddressData}" var="unit">
                       
			<spring:url value="/csr-emulation/start-emulate/${unit.uid}" var="returnOrder" />
                      	
                      	<c:forEach items="${unit.addresses}" var="address">
                      	 <tr>
                          <td class="text-left"><a class="orderNo" href="${returnOrder}">${unit.uid}</a></td>
                          <td class="text-left">${address.companyName}</td>
                          <td class="text-left">${address.line1}</td>
                          <td class="text-left">${address.town}</td>
                          <td class="text-left">${address.region.name}</td>
                          <td class="text-left">${address.postalCode}</td>
                          <td class="text-left"></td>
                           </tr>
                          </c:forEach>
                        
                         
                        </c:forEach>
                       
                        <!-- <tr>
                          <td class="text-left"><a class="orderNo" href="orderHistory_B2B.html">00549</a></td>
                          <td class="text-left">Autozone Lexington</td>
                          <td class="text-left">77 RUS STREET</td>
                          <td class="text-left">Lexington</td>
                          <td class="text-left">TN</td>
                          <td class="text-left">38531-0001</td>
                          <td class="text-left"></td>
                        </tr> -->
                      </tbody>
                    </table>
                    <!-- table two --> 
                  </div>
                </div>
              </div>
            </div>
            
            <!--For NABS ACcount code  -->
            
           
            
               <c:url value="/csr-emulation/get-accountsByNabsCode" var="getaccountsByNabs" />
            <div class="tab-block" id="accountCode"
            style="<c:choose>
		          	<c:when test="${accountNumberFlagnabs}">display:block;</c:when>
		          	<c:otherwise>display: none;</c:otherwise>
          		  </c:choose> " >
              <div class="backOrdersBlock">
                <form:form class="orderStatusForm" action="${getaccountsByNabs}" method="post" commandName="fmaccountSearchForm" >
                  <div class="row">
                    <div class="form-group col-sm-2">
                   
                
                      <form:input id="accountNumberCode" class="form-control" type="text" placeholder="Nabs Account #" path="nabsAccount"/>
                    </div>
                    <div class="form-group col-sm-2">
                      <button type="submit" class="btn btn-default btn-fmDefault" onClick="$('#accountNumberResultCode').show();">Search</button>
                    </div>
                  </div>
                </form:form>
          
                  <div id="accountNumberResultCode" class="backOrderTabTable" style="<c:if test="${empty accountsNabs}">display:none</c:if>"  >
   
                  <div class="table-responsive userTable">
                    <table class="table tablesorter" id="myTable">
                      <thead>
                        <tr class="text-capitalize">
                        <th>NABS Account Code</th>
                          <th>Account number</th>
                           <th>Time </th>
                        </tr>
                      </thead>
                      <tbody>       
                     
                	   <c:if test="${test_condition_Nabs eq 'allAccounts' }">                    	
                      	 <c:forEach items="${accountsNabs}" var ="account_unit">
                       	  <tr class="">
                       	   <spring:url value="/csr-emulation/start-emulate/${account_unit.uid}" var="returnOrder" /> 
                       	  			<td><a class="orderNo" href="${returnOrder}">${account_unit.nabsAccountCode}</a>
                       				 <td><a class="orderNo" href="${returnOrder}">${account_unit.uid}</a></td>  
                       			
                         		 <td>${account_unit.creationTime}</td>
                          </tr>
                        </c:forEach>
                       </c:if>

						<c:if test="${test_condition_Nabs eq 'UniqueAccount' }">								
						  <tr class="">
						   <spring:url value="/csr-emulation/start-emulate/${accountsNabs.uid}" var="returnOrder" />
							<td><a class="orderNo" href="${returnOrder}">${accountsNabs.nabsAccountCode}</a>
								<td><a class="orderNo" href="${returnOrder}">${accountsNabs.uid}</a></td>
								
								 <td>${accountsNabs.creationTime}</td>
						  </tr>
						</c:if>			
						</tbody>
                     </table>
                  </div>
                </div>
              </div>
            </div>
            
            
            
            
            
            
            
            
            <!--For NABS ACcount code end  -->
            
            
             <div class="tab-block" id="orderUploadStatus" style="<c:if test="${addressFlag}">display: block;</c:if>
            												 <c:if test="${empty addressFlag}">display:none;</c:if> " >
            <div id="uploadOrderStatus" class="uploadOrderStatus">
              
            </div>
         </div> 


            <!--/.tab-pane --> 
          </div>
          <!--/.tab-content --> 
          
        </div>
        
        
            
            
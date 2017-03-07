<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:url value="/my-fmaccount/taxexemption" var="submitAction" />
<c:url value="/my-fmaccount/taxexemption-approval" var="sortAction" />
				
<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
        <div class="manageUserPanel rightHandPanel clearfix">
                <h2 class="text-uppercase">Tax Exemption Approval</h2>
					<form class="navbar-form smallB2b-Navform" role="search"
						name="search_form" method="get" action="#">
						<div class="input-group">
							<div class="input-group-btn">
								<button class="btn btn-default" type="submit" value="${submitAction}" >
									<i class="fa fa-search"></i>
								</button>
							</div>
							<input type="text" class="form-control" placeholder="Tax Document Search"
								name="taxDocName" id="taxDocName" value="${searchTaxDocument}">
						</div>
					</form>
				

                
                
                <form class="topMargn_25" method="POST" 
                        action="${submitAction}" enctype="multipart/form-data">
                        
                        <div class="table-responsive userTable topMargn_10">
                                <table id="regPlaceOrderTable"
                                        class="table tablesorter regPlaceOrderTable">
                                        <thead>
                                                <tr>
                                                        <th>Serial No</th> 
                                                        <th>User Name </th>
                                                        <th>Approver Name </th>
                                                        <th>State</th>
                                                        <th>Tax Document 
                                                         <a href="${sortAction}?sortOption=ASC" > &#9650; </a> 
                                                         <a href="${sortAction}?sortOption=DESC" > &#9660; </a> 
                                                        	
                                                        </th>
                                                        <th>Validate</th> 
                                                                                                               
                                                        
                                                </tr>
                                        </thead>
                                        <tbody>
                                                     <c:forEach  items="${ allUnitsTaxDocuments}"  var="TaxDocument" varStatus="status">
                                                     <tr>
				    										<%-- <c:if test="${TaxDocument.validate ne 'Approved' }"> --%>
                                                             <td><div class="form-group" >${status.index+1}</div></td>
															 <td><div class="form-group" style="width:14.5rem !important">${TaxDocument.uploadedBy.name}  </div></td>
															 <td><div class="form-group" style="width:12.5rem !important">${TaxDocument.approvedBy}  </div></td>
                                                             <td><div class="form-group" style="width:10.5rem !important">${TaxDocument.state.name}</div></td>
                                                             <td><div class="form-group ">
                                                                             <div class="input-group" style="width:18.5rem !important" >
                                                                                     <c:url value="/my-fmaccount/taxfiledownload/${TaxDocument.pk}" var="downloadTaxDocument" />
                                                                                     <a href="${TaxDocument.downloadURL }" >${TaxDocument.docname} </a> 
                                                                                      
                                                                             </div>
                                                                     </div></td>
                                                             <td>
                                                                     <div class="form-group">
                                                                     
                                                                     <input type="hidden" id="taxDocPK" name="taxDocPK" value="${TaxDocument.pk} " />
                                                                      <select id="taxDocApproval${status.index}" onchange="javascript:updateApproval(this,${TaxDocument.pk});" class="form-control"  style="width:144px !important">     
                                                                       <c:forEach items="${validateEnum}" var="validateEnumType">
                                                                     				<c:choose>
																					<c:when test="${validateEnumType eq TaxDocument.validate}">
																						
																						<c:if test="${validateEnumType eq 'NOTREVIEWED'  }">
																							<option value="${validateEnumType}" selected="selected">NOT REVIEWED</option>
																						</c:if>
																						<c:if test="${validateEnumType ne 'NOTREVIEWED' }">
																							<option value="${validateEnumType}" selected="selected">${validateEnumType}</option>
																						</c:if>
																					</c:when>
																					<c:otherwise>
																						<c:if test="${validateEnumType eq 'NOTREVIEWED' }">
																							<option value="${validateEnumType}">NOT REVIEWED</option>
																						</c:if>	
																						<c:if test="${validateEnumType ne 'NOTREVIEWED' }">
																							<option value="${validateEnumType}">${validateEnumType}</option>
																						</c:if>
																					</c:otherwise>
																				</c:choose>
																		</c:forEach>
																  </select> 
                                                                             
                                                                             </div>
                                                                     </td>
                                                                     <%-- <td>	
                                                                     	<div class="form-group">

								<c:if test="${taxexemptionUpdateStatus && updatedtaxDocPK eq TaxDocument.pk}">Successfully Updated</c:if>
								<c:if test="${not taxexemptionUpdateStatus && updatedtaxDocPK eq TaxDocument.pk}">Error While Updating</c:if>
							</div>
						</td> --%>
				     <%-- </c:if> --%>

                                                        </tr>
                                                     </c:forEach>
                                                        
                                                
                                        </tbody>
                                </table>
                        </div>
						
						
						

                        <!-- <div class="clearfix btnHolder">
                                <div class=" ">
                                        <button id="addmorePOIbutton" onclick="insRow()" class="btn btn-sm btn-fmDefault text-uppercase registrationBtns" type="submit">Add Row</button>
                                        <button
                                                class="btn btn-sm btn-fmDefault text-uppercase registrationBtns "
                                                type="submit">Submit</button>
                                </div>
                        </div> -->
                </form>
                <%-- <div class="table-responsive userTable topMargn_10">
                        <table id="regPlaceOrderTable"
                                class="table tablesorter regPlaceOrderTable">
                                <thead>
                                        <tr>
                                                <th class="hide">serial no</th>
                                                <th>State</th>
                                                <th>tax document</th>
                                                <th>Validate</th>
                                                <th>Delete</th> 
                                        </tr>
                                </thead>
                                <tbody>
                                        <c:forEach items="${existingtaxdocs}" var="taxdocs">
                                                <tr>
        <c:url value="/my-fmaccount/filedownload/${taxdocs.taxdocname}" var="submitAction" />
        <c:url value="/my-fmaccount/filedelete/${taxdocs.taxdocname}" var="deletedoc" />
                                                        <td class="hide">1</td>
                                                        <td><div class="form-group">
                                                                        <input id="selState" class="form-control width165"
                                                                                value="${taxdocs.state.isocode}" disabled="disabled">
                                                                
                                                                </div></td>
                                                        <td><div class="form-group ">
                                                                        <div class="input-group width375">
                                                                                <input id="uploadTaxDoc" type="text"
                                                                                        class="form-control   orderUploadInput " readonly
                                                                                        value="${taxdocs.taxdocname}" > <span
                                                                                        class="input-group-btn text-right"><span
                                                                                        class="input-group-btn text-right"> <input type="file"
                                                                                        class="" value="${taxdocs.taxdoc}">
                                                                                </span> </span> <a class="fm_fntBlack"
                                                                                        href="${submitAction}" >${taxdocs.taxdocname}</a>
                                                                        </div>
                                                                </div></td>
                                                        <td><div class="form-group">
                                                                        <input id="action" class="form-control width165"
                                                                                value="${taxdocs.validate}" disabled="disabled" />

                                                                </div></td>
                                                        <td class="text-center"><a  id="delPOIbutton"
                                                                class="fa fa-trash"  href="${deletedoc}"></a></td>

                                                </tr>
                                        </c:forEach>
                                </tbody>
                        </table>
                </div> --%>
        </div>
</div>



<script type="text/javascript">


var Url1 ="";

function updateApproval(objValue,taxDocPK){
        

var taxDocPKValue = taxDocPK;
var taxDocApproval= objValue.value;
ajaxUrl="USD/my-fmaccount/";
currentPath= window.location.href;
pathName= "";

                try{
                        if(ACC.myfmaccount.currentPath.indexOf("/USD") != -1){
                                pathName = ACC.myfmaccount.currentPath.substring(0, ACC.myfmaccount.currentPath.lastIndexOf("/USD") + 1);
                        }else if(ACC.myfmaccount.currentPath.indexOf("?site") != -1){
                                pathName = ACC.myfmaccount.currentPath.substring(0, ACC.myfmaccount.currentPath.lastIndexOf("/?site") + 1)+ACC.myfmaccount.currentPath.substring(ACC.myfmaccount.currentPath.lastIndexOf("site=")+5,ACC.myfmaccount.currentPath.length)+"/en/";
                        }else{
                                pathName = window.location.href;
                        }
                }catch(e){
                        alert(e);
                }

                       // alert("taxDocIdValue :: "+taxDocPKValue);
//alert("pathName :: "+pathName);
//alert("currentPath :: "+currentPath);
//alert("taxDocApproval :: "+taxDocApproval);
                        Url1  = ACC.myfmaccount.pathName + ajaxUrl+"update-taxexemption-approval/"+taxDocApproval+"/"+taxDocPKValue;
                        //alert("Url1 :: "+Url1)
 			if(confirm("Do you want to Change this Tax Document Status!")){
                      		postAJAX(ACC.myfmaccount.taxDocApproval, taxDocApproval,"");
			}else{
				return false;
			}


}


function postAJAX(reqType , selectField,defaultopt){
        //alert("reqType  :: "+reqType ); 
                $.ajax({
                 url: Url1,             
                 async: false,
                         cache: false,  
                 success: function (xmlDoc) {
                
                var respDoc = $(xmlDoc).find(reqType).text();
                   
                 var respSize = $(xmlDoc).find(reqType+ACC.myfmaccount.conSize).text();

               // alert("hiiiiiiii url ::" + Url1  );
                          
                    try{        
                        ACC.myfmaccount.options = defaultopt;
                        if(respSize>0)
                                        { 
                                                ACC.myfmaccount.options += ACC.myfmaccount.conOptionStart+$(xmlDoc).find(reqType+ACC.myfmaccount.conValue+int).text()+'">'+$(xmlDoc).find(reqType+ACC.myfmaccount.conValue+int).text()+ ACC.myfmaccount.conOptionEnd;
                                                        
                                        }else{
                                                selectField.append(ACC.myfmaccount.options); 
                                        }
                                                
                                        
                                        
                                }
                                catch(err){} 
                }
             });
        }
</script>



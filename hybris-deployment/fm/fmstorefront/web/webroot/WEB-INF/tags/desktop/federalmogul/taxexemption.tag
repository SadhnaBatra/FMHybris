<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel clearfix">
		<h2 class="text-uppercase">Tax exemption</h2>

		<c:url value="/my-fmaccount/taxexemption" var="submitAction" />
		<form:form class="topMargn_25" method="POST" commandName="taxdata"
			action="${submitAction}" enctype="multipart/form-data">
			<div class="row">
				<div class="form-group   col-md-4">
					<label class="" for="TaxID">Tax ID / Canada Business Number<span class="fm_fntRed">*</span></label>
					<form:input type="text" id="TaxID" name="TaxID" placeholder=""
						class="form-control width175" value="${currentdata.fmunit.taxID}"
						path="taxID" />
				</div>
				<%-- <div class="form-group  col-md-4">
					<label class="" for="taxDoc">Tax Document<span
						class="fm_fntRed">*</span></label> <input type="text" id="taxDoc"
						name="taxDoc" placeholder="" class="form-control width270"
						value="${currentdata.fmunit.taxDocumentList[0].docname}">
				</div> --%>
			</div>

			<div class="form-group  clearfix">

				<label for="subscribe"> <input type="checkbox"
					id="subscribe" /> &nbsp;Register me for placing order
				</label>

			</div>
			<div class="addNewUserBtnHolder">
				<a class="control-label searchResultText " id="addmorePOIbutton"
					onclick="insRow()"><strong><span
						class="fa fa-plus-circle fm_fntRed"></span> Add New Document</strong></a>
			</div>
			<div class="table-responsive userTable topMargn_10">
				<table id="regPlaceOrderTable"
					class="table tablesorter regPlaceOrderTable">
					<thead>
						<tr>
							<th class="hide">serial no</th>
							<th>State</th>
					<th>Upload US State Sales Tax Exemption / Canada PST exemption document</th>
						<!-- 	 <th>Validate</th> -->
							<th>Delete</th> 
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="hide">1</td>
							<td><div class="form-group">
									<form:select id="selState" class="form-control width165"
										path="state">
										<c:forEach items="${regionsdatas}" var="reg">
											<c:forEach items="${reg}" var="val">
												<c:if test="${fn:contains(val.isocode,'US-')}">
													<option value="${val.isocode}">${val.name}</option>
												</c:if>
											</c:forEach>
										</c:forEach>
									</form:select>
								</div></td>
							<td><div class="form-group ">
									<div class="input-group width375">
										<input id="uploadTaxDoc" type="text"
											class="form-control   orderUploadInput " readonly> <span
											class="input-group-btn text-right"> <span
											class="btn btn-primary btn-file orderUploadButton"> <span
												class="fa fa-arrow-circle-o-up "></span> <form:input
													type="file" class="" path="taxDocument" />
										</span>
										</span>
									</div>
								</div></td>
							<%-- <td><div class="form-group">
									<form:select id="action" class="form-control width165"
										path="taxvalidate" disabled="disabled">
										<option value="select">Select</option>
										<option>Validated</option>
										<option>Rejected</option>
									</form:select>
								</div></td> --%>
							<td class="text-center"><a href="#" id="delPOIbutton"
								class="fa fa-trash" onclick="deleteRow(this)" ></a></td>
						</tr>
					</tbody>
				</table>
			</div>


			<div class="clearfix btnHolder">
				<div class=" ">
					<!--<button id="addmorePOIbutton" onclick="insRow()" class="btn btn-sm btn-fmDefault text-uppercase registrationBtns" type="submit">Add Row</button> -->
					<button
						class="btn btn-sm btn-fmDefault text-uppercase registrationBtns "
						type="submit">Submit</button>
				</div>
			</div>
		</form:form>
		<div class="table-responsive userTable topMargn_10">
			<table id="regPlaceOrderTable"
				class="table tablesorter regPlaceOrderTable">
				<thead>
					<tr>
						<th>serial no</th>
						<th>State</th>
						<th>tax document</th>
						<th>Validate</th>
						<th>Delete</th> 
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${existingtaxdocs}" var="taxdocs" varStatus="status">
						<tr>
							<%-- <c:url value="/my-fmaccount/filedownload/${taxdocs.taxdocname}" var="submitAction" />
							<c:url value="/my-fmaccount/filedelete/${taxdocs.taxdocname}" var="deletedoc" /> --%>
							<td >1</td>
							<td>
								<div class="form-group">
										${taxdocs.state.isocode}
								</div>
							</td>
							<td>
								<div class="form-group ">
									<div class="input-group width375">
										 ${taxdocs.docname}
									</div>
								</div>
							</td>
							<td>
								<div class="form-group">
									${taxdocs.validate}
								</div>
							</td>
							
							<td class="text-center">
								<c:url value="/my-fmaccount/filedelete/${taxdocs.docname}" var="deletedoc" />
								<a  id="delPOIbutton"
									class="fa fa-trash"  href="${deletedoc}"></a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
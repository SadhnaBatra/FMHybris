<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<input type="hidden" name="loggedUserType" id="loggedUserType"
	value="${ifType}">
<input type="hidden" name="loggedAccountID" id="loggedAccountID"
	value="${accountId}">

<div class="col-lg-4 b2b-home-pane">
	<div class="panel panel-default quickOrderPanel">
		<div class="panel-body accDetBody">
			<h2 class="panel-title text-uppercase quickOrderTitle">
				<span class="fa fa-book"></span> <span class="text-uppercase"><spring:theme
						code="B2BHomepage.accountDetails.title" /></span>
			</h2>
			<c:choose>
				<c:when test="${not empty fn:escapeXml(User_SoldTo_Full_Address)}">
					<c:set var="addressType" value="change" />
				</c:when>
				<c:otherwise>
					<c:set var="addressType" value="default" />
				</c:otherwise>
			</c:choose>
			<div class="soldToForm">
				<form role="form">
					<%-- <c:forEach items="${SoldToAccount}" var="SoldTo">
							<c:set value="${SoldTo.key.uid}" var="defaultSoldTo" />
						</c:forEach> --%>
					<div class=""> 
						<c:if test="${insideMulitpleSoldTo eq 'multiple' }">
							<div class="clearfix lftPad_20 rgtPad_20">

								<div class="pull-left">
									<label class="radio "> <input type="radio" class=""
										name="optionsRadios" id="opt3" value="opt3"
										data-toggle="radio"
										${addressType == 'default' ? 'checked="checked"' : ''}
										onClick="$('#changeAddressSoldTo').hide();$('#existsoldto').hide();$('#defaultAccSoldTo').show();javascript:changeSoldToDefault('${SoldToAccount.uid}') " />
										<spring:theme code="B2BHomepage.defaultAccount" />
									</label>
								</div>
								<div class="pull-right">
									<label class="radio "> <input type="radio" class=""
										name="optionsRadios" id="opt4" value="opt4"
										onClick="$('#defaultAccSoldTo').hide();$('#existsoldto').show(); $('#changeSold').show(); $('#changeSoldbtn').show();" />
										<spring:theme code="B2BHomepage.alternateAccount" />
									</label>
								</div>
							</div>
						</c:if>
						<div class="tab-content topMargn_5">
							<div id="defaultAccSoldTo" class="tab-pane active option opt3">
								<div class="reviewFirstPanelMargin">
									<p class="reviewPlaceOrderBold text-capitalize">
										<spring:theme code="B2BHomepage.soldTo" />
									</p>
									<span id="oldSlodTo"
										${addressType == 'default' ? 'style="display:block"' : 'style="display:none"'}>
										<p>${SoldToAccount.unitAddress[0].companyName}<br>
											${SoldToAccount.unitAddress[0].line1}<br>
											${SoldToAccount.unitAddress[0].town},&nbsp;${SoldToAccount.unitAddress[0].region.isocodeShort}&nbsp;${SoldToAccount.unitAddress[0].postalCode}&nbsp;${SoldToAccount.unitAddress[0].country.isocode}<br>
											${SoldToAccount.uid}/${SoldToAccount.nabsAccountCode}
										</p>
									</span> <span id="newSoldTo"
										${addressType == 'change' ? 'style="display:block"' : 'style="display:none"'}>
										${User_SoldTo_Full_Address} </span>
								</div>
							</div>
							<input type="hidden"
								value="${addressType == 'change' ? 'newSoldTo' : 'oldSlodTo'}"
								name="SoldToAddressType" id="SoldToAddressType" />
							<div id="existsoldto"
								class="tab-pane topMargn_10 btmMrgn_25 option opt4">
								<label class="text-capitalize" for="changeShip"><spring:theme
										code="B2BHomepage.changeSoldTo" /></label>
								<div class="input-group  width285 btmMrgn_3 changeShipTo">
									<input type="search" id="changeSold" class="form-control">
									<span class="input-group-btn" id="changeSoldbtn">
										<button type="button" class="btn btn-default"
											onClick="javascript:getAllAddress('getAll*','searchSoldTo');">
											<span class="fa fa-search"></span>
										</button>
									</span>
								</div>
								<div id="livesearchSoldTo"></div>
							</div>
						</div>
					</div>
				</form>
				<p class="reviewPlaceOrderBold">
					<spring:theme code="B2BHomepage.reviewplaceorder.description" />
					<abbr title="Freight On Board"><spring:theme
							code="B2BHomepage.reviewplaceorder.description1" /></abbr>
					<spring:theme code="B2BHomepage.reviewplaceorder.description2" />
				</p>
			</div>
			<div class="shipToForm">
				<div class="reviewFirstPanelMargin">
					<div class="tab-content topMargn_10">
						<div id="shipTodefaultAcc" class="tab-pane active">
							<div class="reviewFirstPanelMargin ">
								<p class="reviewPlaceOrderBold text-capitalize">
									<spring:theme code="B2BHomepage.shipTo" />
								</p>
								<c:choose>
									<c:when
										test="${not empty fn:escapeXml(User_ShipTo_Full_Address)}">
										<c:set var="addressType" value="change" />
									</c:when>
									<c:otherwise>
										<c:set var="addressType" value="default" />
									</c:otherwise>
								</c:choose>
								<span id="oldShipTo"
									${addressType == 'default' ? 'style="display:block"' : 'style="display:none"'}>
									<p>
										${ShipToAccount.unitAddress[0].companyName}<br>
										${ShipToAccount.unitAddress[0].line1}<br>
										${ShipToAccount.unitAddress[0].town},&nbsp;${ShipToAccount.unitAddress[0].region.isocodeShort}&nbsp;${ShipToAccount.unitAddress[0].postalCode}&nbsp;${ShipToAccount.unitAddress[0].country.isocode}<br>
										${ShipToAccount.uid} /${ShipToAccount.nabsAccountCode}
									</p>
								</span> <span id="newShipTo"
									${addressType == 'change' ? 'style="display:block"' : 'style="display:none"'}>
									${User_ShipTo_Full_Address} </span>
							</div>
						</div>
						<!-- sreedevi - added addr search box   -->
						<div id="shipTo" class="tab-pane topMargn_10 ">
							<form role="form">
								<label class="text-capitalize " for="changeShip"><spring:theme
										code="B2BHomepage.changeShipTo" /></label>
								<div class="input-group  width285 btmMrgn_3 changeShipTo">
									<input type="search" id="changeShip" class="form-control">
									<span class="input-group-btn" id="changeShipbtn">
										<button type="button" class="btn btn-default"
											onClick="javascript:getAllAddress('getAll*','searchShipTo');">
											<span class="fa fa-search"></span>
										</button>
									</span>
								</div>
								<div id="livesearch"></div>
							</form>
						</div>
						<span id="shipTonewAddress1"></span>
					</div>
					<div class="form-group ">
						<c:choose>
						<c:when test="${addressType ne 'change'}">
					
						<select class="form-control shipToSelect" id="shipToSelect"
							onchange="javascript:hideAndShow();">
							<option ${addressType == 'default' ? 'selected="selected"' : ''}
								value="default"
								onclick="javascript:changeDefault('${ShipToAccount.uid}'); ">Ship
								to - Default Address</option>
							<c:if test="${fn:startsWith(ShipToAccount.uid, '001')}">
								<option onselect="javascript:hideAndShow();"
									onclick="javascript:hideAndShow();" value="chooseExist">Ship
									to - Other Existing Address</option>
							</c:if>
							<c:if test="${manualShipToFlagEnable eq 'Yes'}">
								<option value="new" data-target="#shipToNewAddressPopup"
									data-toggle="modal"
									onClick="$('.shipToForm .tab-pane').hide();">Ship to -
									New Address</option>
							</c:if>
						</select>
						</c:when>
						<c:otherwise>
						<select class="form-control shipToSelect" id="shipToSelect"
							onchange="javascript:hideAndShow();">
							<option value="default"
								onclick="javascript:changeDefault('${ShipToAccount.uid}'); ">Ship
								to - Default Address</option>
								<option onselect="javascript:hideAndShow();"
									onclick="javascript:hideAndShow();" value="chooseExist" selected>Ship
									to - Other Existing Address</option>
							<c:if test="${manualShipToFlagEnable eq 'Yes'}">
								<option value="new" data-target="#shipToNewAddressPopup"
									data-toggle="modal"
									onClick="$('.shipToForm .tab-pane').hide();">Ship to -
									New Address</option>
							</c:if>
						</select>
						</c:otherwise>
						</c:choose>					
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

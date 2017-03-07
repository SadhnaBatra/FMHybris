<%@ tag body-content="empty" trimDirectiveWhitespaces="true" %>
<%@ attribute name="selected" required="false" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme" %>

<div class="col-lg-4">
	<div class="panel panel-default quickOrderPanel">
		<div class="panel-body accDetBody">
			<h2 class="panel-title text-uppercase quickOrderTitle">
				<span class="fa fa-book"></span> <span class="text-uppercase">Account
					Details</span>
			</h2>
			<div class="soldToForm topMargn_10">
				<div class="reviewFirstPanelMargin ">
					<p class="reviewPlaceOrderBold text-capitalize">Sold to</p>
					<p class="text-uppercase">${unitDetails.name}</p>                         
                    <p>${address[0].line1}&nbsp;, &nbsp;${address[0].region.isocode}</p>
				</div>
			</div>
			<div class="shipToForm">
				<div class="reviewFirstPanelMargin"><b>SHIP TO NOT CREATED</b>
					<!--<select class="form-control width270 shipForm">
						<option selected value="default">Change Ship To Default
							Account</option>
						 <option value="12345">12345 - MOM and POP MI</option>
						<option value="67890">67890 - MOM and POP IL</option> -->
					</select>

					<!-- <div class="tab-content topMargn_10">
						<div id="defaultAcc" class="tab-pane active">
							<div class="reviewFirstPanelMargin ">
								<p class="reviewPlaceOrderBold text-capitalize">Ship to</p>
								<p class="text-uppercase">Jamestome Auto Parts</p>
								<p>46 E Main St</p>
								<p>Jamestone, IN 46147-0000</p>
							</div>
						</div>

						<div id="changeAddress1" class="tab-pane topMargn_10">
							<div class="reviewFirstPanelMargin ">
								<p class="reviewPlaceOrderBold text-capitalize">Ship to</p>
								<p class="text-uppercase">UNI-SELECT USA INC</p>
								<p>12345 - MOM and POP MI</p>
								<p>Jamestone, IN 46147-0000</p>
							</div>
						</div>

						<div id="changeAddress2" class="tab-pane topMargn_10">
							<div class="reviewFirstPanelMargin ">
								<p class="reviewPlaceOrderBold text-capitalize">Ship to</p>
								<p class="text-uppercase">UNI-SELECT USA INC</p>
								<p>67890 - MOM and POP IL</p>
								<p>Jamestone, IN 46147-0000</p>
							</div>
						</div>
					</div> -->
				</div>
			</div>
		</div>
	</div>
</div>


<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="selected" required="false" type="java.lang.String"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags"%>
<%@ taglib prefix="theme" tagdir="/WEB-INF/tags/shared/theme"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="format" tagdir="/WEB-INF/tags/shared/format"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="clearfix" id="BrandQuestionsBlock" style="display: none;">

  <div id="PartsBuySurveyError1" style="display: none;" class="col-lg-12 surveyError">Please indicate the percentage for all types of parts.</div>
	<div id="PartsBuySurveyError2" style="display: none;" class="col-lg-12 surveyError">Please indicate the percentage for all types of parts.</div>
  <div id="PartsBuySurveyQ2Error" style="display: none;" class="col-lg-12 surveyError">Please make sure you put a 1, 2 and 3 for your top 3 reasons.</div>
  <div id="PartsBuySurveyQ3to14Error" style="display: none;" class="col-lg-12 surveyError">Please select one supplier for Preferred and a different one for Secondary.</div>  
  <div id="PartsBuySurveyQ2Error2" style="display: none;" class="col-lg-12 surveyError">Cannot have duplicate answers.</div>
  <div id="PartsSurveyOtherTxtError" style="display: none;" class="col-lg-12 surveyError">If other is selected, an answer must be typed into the corresponding box.</div>

	<form:form method="POST" id="partsBuyingQuestionsForm" commandName="partsBuyingQuestionsForm"  action="${request.contextPath}/Survey/updatePartsBuySurvey">
		<div class="clearfix topMargn_5" id="Question1">
			<div class="">
				<p>1. Think about where your shop purchases parts.  For the parts listed below, please indicate the percentage that is purchased from a Retailer, WD, Jobber and Car Dealer.  The Total column must add up to 100%. 
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Type Of Part</th>
							<th>Retailer</th>
							<th>WD</th>
							<th>Jobber</th>
							<th>Car Dealer</th>
							<th>Total</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">Batteries</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bt"  onchange="javascript:calculateTotal('bt','bt','Q1Batteries',2000)" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bt"   onchange="javascript:calculateTotal('bt','bt','Q1Batteries',2000)" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bt"  onchange="javascript:calculateTotal('bt','bt','Q1Batteries',2000)" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bt"   onchange="javascript:calculateTotal('bt','bt','Q1Batteries',2000)" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total" id="bt"  placeholder="%" readonly>
                            	<form:hidden path="Q1Batteries" id="Q1Batteries" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Brakes</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bk" onchange="javascript:calculateTotal('bk','bk','Q1Brakes',2001);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bk" onchange="javascript:calculateTotal('bk','bk','Q1Brakes',2001);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bk" onchange="javascript:calculateTotal('bk','bk','Q1Brakes',2001);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight"  name="bk" onchange="javascript:calculateTotal('bk','bk','Q1Brakes',2001);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total" id="bk"   placeholder="%"  readonly>
                            	<form:hidden path="Q1Brakes" id="Q1Brakes" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Drive Line</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="dl" onchange="javascript:calculateTotal('dl','dl','Q1DriveLine',2002);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="dl" onchange="javascript:calculateTotal('dl','dl','Q1DriveLine',2002);"placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="dl" onchange="javascript:calculateTotal('dl','dl','Q1DriveLine',2002);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="dl" onchange="javascript:calculateTotal('dl','dl','Q1DriveLine',2002);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total" id="dl"    placeholder="%" readonly>
                            	<form:hidden path="Q1DriveLine" id="Q1DriveLine" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Engine</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="eg" onchange="javascript:calculateTotal('eg','eg','Q1Engine',2003);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="eg" onchange="javascript:calculateTotal('eg','eg','Q1Engine',2003);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="eg" onchange="javascript:calculateTotal('eg','eg','Q1Engine',2003);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="eg" onchange="javascript:calculateTotal('eg','eg','Q1Engine',2003);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight"  name="total" id="eg"  placeholder="%" readonly>
                            	<form:hidden path="Q1Engine" id="Q1Engine" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Filters</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="ft" onchange="javascript:calculateTotal('ft','ft','Q1Filters',2004);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="ft" onchange="javascript:calculateTotal('ft','ft','Q1Filters',2004);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="ft" onchange="javascript:calculateTotal('ft','ft','Q1Filters',2004);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="ft" onchange="javascript:calculateTotal('ft','ft','Q1Filters',2004);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total"  id="ft" placeholder="%" readonly>
                            	<form:hidden path="Q1Filters" id="Q1Filters" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Gaskets</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="gt" onchange="javascript:calculateTotal('gt','gt','Q1Gaskets',2005);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="gt" onchange="javascript:calculateTotal('gt','gt','Q1Gaskets',2005);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="gt" onchange="javascript:calculateTotal('gt','gt','Q1Gaskets',2005);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="gt" onchange="javascript:calculateTotal('gt','gt','Q1Gaskets',2005);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total"  id="gt" placeholder="%" readonly>
                            	<form:hidden path="Q1Gaskets" id="Q1Gaskets" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Lighting</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="lt" onchange="javascript:calculateTotal('lt','lt','Q1Lighting',2006);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="lt" onchange="javascript:calculateTotal('lt','lt','Q1Lighting',2006);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="lt" onchange="javascript:calculateTotal('lt','lt','Q1Lighting',2006);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="lt" onchange="javascript:calculateTotal('lt','lt','Q1Lighting',2006);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total" id="lt"  placeholder="%" readonly>
                            	<form:hidden path="Q1Lighting" id="Q1Lighting" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Steering</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="st" onchange="javascript:calculateTotal('st','st','Q1Steering',2007);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="st" onchange="javascript:calculateTotal('st','st','Q1Steering',2007);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="st" onchange="javascript:calculateTotal('st','st','Q1Steering',2007);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="st" onchange="javascript:calculateTotal('st','st','Q1Steering',2007);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total" id="st"  placeholder="%" readonly>
                            	<form:hidden path="Q1Steering" id="Q1Steering" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Suspension</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="sn" onchange="javascript:calculateTotal('sn','sn','Q1Suspension',2008);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="sn" onchange="javascript:calculateTotal('sn','sn','Q1Suspension',2008);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="sn" onchange="javascript:calculateTotal('sn','sn','Q1Suspension',2008);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="sn" onchange="javascript:calculateTotal('sn','sn','Q1Suspension',2008);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight" name="total"  id="sn" placeholder="%" readonly>
                            	<form:hidden path="Q1Suspension" id="Q1Suspension" />
                            </td>
						</tr>
						<tr>
							<td class="col-lg-6">Wipers</td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="wi" onchange="javascript:calculateTotal('wi','wi','Q1Wipers',2009);" placeholder="%"></td>
							<td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="wi" onchange="javascript:calculateTotal('wi','wi','Q1Wipers',2009);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="wi" onchange="javascript:calculateTotal('wi','wi','Q1Wipers',2009);" placeholder="%"></td>
                            <td class="text-center"><input type="text" class="form-control widthHeight surveyTextRight" name="wi" onchange="javascript:calculateTotal('wi','wi','Q1Wipers',2009);" placeholder="%"></td>
                            <td class="text-center surveyTotal">
                            	<input type="text" class="form-control widthHeight surveyTextRight"   name="total"  id="wi" placeholder="%" readonly>
                            	<form:hidden path="Q1Wipers" id="Q1Wipers" />
                            </td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_0" id="Question1_Back"
						onclick="javascript:moveBack(this,['']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_2" id="Question1_Next"
						onclick="javascript:movePartsBuySurvey(this,['Supplier Selection Criteria']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question1 Ending -->
		<div class="clearfix topMargn_5" id="Question2" style="display: none;">
			<div class="">
				<p>2. Please rank the top 3 reasons you select OE parts, with 1 as the most important.  Type a 1, 2 or 3 in the box.
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Rank</th>
							<th>Reason for selecting OE parts</th>
							</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center"><input type="text" value="" name="2010" id="rank1"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q2BetterFit" id="2010" />
							</td>
							<td>Better Fit</td>
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2011" id="rank2"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q2BetterPerformance" id="2011" />
							</td>
								<td>Better Performance</td>
							
						</tr>
						<tr>
						
							<td class="text-center"><input type="text" value="" name="2012" id="rank3"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q2BetterPrice" id="2012" />
							</td>
								<td>Better Price</td>
							
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2013" id="rank4"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q2CustomerReq"  id="2013"/>
							</td>
								<td>Customer Request</td>
							
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2014" id="rank5"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q2FavoriteBrand" id="2014" />
							</td>
								<td>Favorite Brand doesn't make specific part</td>
							
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2015" id="rank6" 
							    class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q2PartsForVehicle" id="2015" />
							</td>
							<td>Normal supplier doesn't have parts for vehicle</td>
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2016" id="rank7"
								 class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q2Other" id="02016" />
							</td>
							<td>Other (Please Specify)</td>
						</tr>
						
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_1" id="Question1_Back"
						onclick="javascript:moveBack(this,['Supplier Preference']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_3" id="Question1_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Steering']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question2 Ending -->
		<div class="clearfix topMargn_5" id="Question3" style="display: none;">
			<div class="">
				<p>3. Please select your top two suppliers for <b>Steering Products</b>.
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q3Primary" value="2017" id="Q3adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Secondary" id="Q3adv" value="2018"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Primary" id="Q3Altrom" value="2021" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Secondary" id="Q3Altrom" value="2022" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Autozone" value="2023" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Autozone" value="2024" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Primary" id="Q3Auto" value="2025" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Secondary" id="Q3Auto" value="2026" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3APPlus" value="2059" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3APPlus" value="2060" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3APlus" value="2061" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3APlus" value="2062" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Best" value="2051" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Best" value="2052" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3cand" value="2027" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3cand" value="2028" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Primary" id="Q3Car" value="2029" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Secondary" id="Q3Car" value="2030" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Fed" value="2031" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Fed"  value="2032" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Fisher" value="2057" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Fisher" value="2058" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Ind" value="2049" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Ind" value="2050" /></td>
								<td>Independent jobber</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Lord" value="2045" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Lord" value="2046" /></td>
								<td>Lordco</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Modern" value="2047" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Modern" value="2048" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Primary" id="Q3NAPA" value="2033" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q3Secondary" id="Q3NAPA" value="2034" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3New" value="2035" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3New" value="2036" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3OReilly" value="2037" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3OReilly" value="2038" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" value="2019" id="Q3parts"/></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" value="2020" id="Q3parts" /></td>
								<td>Parts plus</td>
						</tr>
					
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3PartsSource" value="2039" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3PartsSource" value="2040" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Pep" value="2041" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Pep" value="2042" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Prof" value="2043" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Prof" value="2044" /></td>
									<td>Professional's choice</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Pro" value="2053" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Pro" value="2054" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3TruStar" value="2055" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3TruStar"  value="2056" /></td>
								<td>TruStar</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Worldpac" value="2063" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Worldpac" value="2064" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Other1" value="2065" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Other1" value="2066" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q3Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q3Primary" id="Q3Other2" value="2067" /></td>
							<td class="text-center"><form:radiobutton path="Q3Secondary" id="Q3Other2" value="2068" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q3Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_2" id="Question3_Back"
						onclick="javascript:moveBack(this,['Supplier Selection Criteria']);">&lt; Back</button>
						<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_4" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Suspension']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question3 Ending -->
		
		<div class="clearfix topMargn_5" id="Question4" style="display: none;">
			<div class="">
				<p>4. Please select your top two suppliers for <b>Suspension Products</b>.
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q4Primary" value="2069" id="Q4adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Secondary" id="Q4adv" value="2070"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Primary" id="Q4Altrom" value="2073" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Secondary" id="Q4Altrom" value="2074" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Autozone" value="2075" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Autozone" value="2076" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Primary" id="Q4Auto" value="2077" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Secondary" id="Q4Auto" value="2078" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4APPlus" value="2111" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4APPlus" value="2112" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4APlus" value="2113" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4APlus" value="2114" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Best" value="2103" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Best" value="2104" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4cand" value="2079" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4cand" value="2080" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Primary" id="Q4Car" value="2081" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Secondary" id="Q4Car" value="2082" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Fed" value="2083" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Fed"  value="2084" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Fisher" value="2109" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Fisher" value="2110" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Ind" value="2101" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Ind" value="2102" /></td>
								<td>Independent jobber</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Lord" value="2097" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Lord" value="2098" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Modern" value="2099" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Modern" value="2100" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Primary" id="Q4NAPA" value="2085" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q4Secondary" id="Q4NAPA" value="2086" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4New" value="2087" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4New" value="2088" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4OReilly" value="2089" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4OReilly" value="2090" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" value="2071" id="Q4parts"/></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" value="2072" id="Q4parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4PartsSource" value="2091" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4PartsSource" value="2092" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Pep" value="2093" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Pep" value="2094" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Prof" value="2095" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Prof" value="2096" /></td>
									<td>Professional's choice</td>
						</tr>
					
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Pro" value="2105" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Pro" value="2106" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4TruStar" value="2107" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4TruStar"  value="2108" /></td>
								<td>TruStar</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Worldpac" value="2115" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Worldpac" value="2116" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Other1" value="2117" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Other1" value="2118" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q4Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q4Primary" id="Q4Other2" value="2119" /></td>
							<td class="text-center"><form:radiobutton path="Q4Secondary" id="Q4Other2" value="2120" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q4Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_3" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Steering']);">&lt; Back</button>
						<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_5" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','U-Joints']);">Next &gt;</button>
				</div>
			</div>
		</div>

		<!-- Question4 Ending -->
		<div class="clearfix topMargn_5" id="Question5" style="display: none;">
			<div class="">
				<p>5. Please select your top two suppliers for <b>U-Joints</b>.
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q5Primary" value="2121" id="Q5adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Secondary" id="Q5adv" value="2122"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Primary" id="Q5Altrom" value="2125" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Secondary" id="Q5Altrom" value="2126" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Autozone" value="2127" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Autozone" value="2128" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Primary" id="Q5Auto" value="2129" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Secondary" id="Q5Auto" value="2130" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5APPlus" value="2163" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5APPlus" value="2164" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5APlus" value="2165" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5APlus" value="2166" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Best" value="2155" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Best" value="2156" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5cand" value="2131" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5cand" value="2132" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Primary" id="Q5Car" value="2133" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Secondary" id="Q5Car" value="2134" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Fed" value="2135" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Fed"  value="2136" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Fisher" value="2161" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Fisher" value="2162" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Ind" value="2153" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Ind" value="2154" /></td>
								<td>Independent jobber</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Lord" value="2149" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Lord" value="2150" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Modern" value="2151" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Modern" value="2152" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Primary" id="Q5NAPA" value="2137" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q5Secondary" id="Q5NAPA" value="2138" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5New" value="2139" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5New" value="2140" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5OReilly" value="2141" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5OReilly" value="2142" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" value="2123" id="Q5parts"/></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" value="2124" id="Q5parts" /></td>
								<td>Parts plus</td>
						</tr>
					
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5PartsSource" value="2143" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5PartsSource" value="2144" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Pep" value="2145" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Pep" value="2146" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Prof" value="2147" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Prof" value="2148" /></td>
									<td>Professional's choice</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Pro" value="2157" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Pro" value="2158" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5TruStar" value="2159" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5TruStar"  value="2160" /></td>
								<td>TruStar</td>
						</tr>
					
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Worldpac" value="2167" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Worldpac" value="2168" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Other1" value="2169" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Other1" value="2170" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q5Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q5Primary" id="Q5Other2" value="2171" /></td>
							<td class="text-center"><form:radiobutton path="Q5Secondary" id="Q5Other2" value="2172" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q5Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_4" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Suspension']);">&lt; Back</button>
						<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_6" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Hub Assemblies']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question5 Ending -->
				<div class="clearfix topMargn_5" id="Question6" style="display: none;">
			<div class="">
				<p>6. Please select your top two suppliers for <b>Hub Assemblies</b>.
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q6Primary" value="2173" id="Q6adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Secondary" id="Q6adv" value="2174"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Primary" id="Q6Altrom" value="2177" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Secondary" id="Q6Altrom" value="2178" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Autozone" value="2179" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Autozone" value="2180" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Primary" id="Q6Auto" value="2181" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Secondary" id="Q6Auto" value="2182" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6APPlus" value="2215" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6APPlus" value="2216" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6APlus" value="2217" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6APlus" value="2218" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Best" value="2207" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Best" value="2208" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6cand" value="2183" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6cand" value="2184" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Primary" id="Q6Car" value="2185" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Secondary" id="Q6Car" value="2186" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Fed" value="2187" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Fed"  value="2188" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Fisher" value="2213" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Fisher" value="2214" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Ind" value="2205" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Ind" value="2206" /></td>
								<td>Independent jobber</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Lord" value="2201" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Lord" value="2202" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Modern" value="2203" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Modern" value="2204" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Primary" id="Q6NAPA" value="2189" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q6Secondary" id="Q6NAPA" value="2190" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6New" value="2191" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6New" value="2192" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6OReilly" value="2193" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6OReilly" value="2194" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" value="2175" id="Q6parts"/></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" value="2176" id="Q6parts" /></td>
								<td>Parts plus</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6PartsSource" value="2195" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6PartsSource" value="2196" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Pep" value="2197" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Pep" value="2198" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Prof" value="2199" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Prof" value="2200" /></td>
									<td>Professional's choice</td>
						</tr>
					
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Pro" value="2209" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Pro" value="2210" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6TruStar" value="2211" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6TruStar"  value="2212" /></td>
								<td>TruStar</td>
						</tr>
				
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Worldpac" value="2219" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Worldpac" value="2220" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Other1" value="2221" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Other1" value="2222" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q6Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q6Primary" id="Q6Other2" value="2223" /></td>
							<td class="text-center"><form:radiobutton path="Q6Secondary" id="Q6Other2" value="2224" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q6Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_5" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','U-Joints']);">&lt; Back</button>				
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_7" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Wheel Bearings']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question6 Ending -->
				<div class="clearfix topMargn_5" id="Question7" style="display: none;">
			<div class="">
				<p>7. Please select your top two suppliers for <b>Wheel Bearings</b>.
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q7Primary" value="2225" id="Q7adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Secondary" id="Q7adv" value="2226"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Primary" id="Q7Altrom" value="2229" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Secondary" id="Q7Altrom" value="2230" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Autozone" value="2231" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Autozone" value="2232" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Primary" id="Q7Auto" value="2233" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Secondary" id="Q7Auto" value="2234" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7APPlus" value="2267" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7APPlus" value="2268" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7APlus" value="2269" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7APlus" value="2270" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Best" value="2259" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Best" value="2260" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7cand" value="2235" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7cand" value="2236" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Primary" id="Q7Car" value="2237" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Secondary" id="Q7Car" value="2238" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Fed" value="2239" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Fed"  value="2240" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Fisher" value="2265" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Fisher" value="2266" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Ind" value="2257" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Ind" value="2258" /></td>
								<td>Independent jobber</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Lord" value="2253" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Lord" value="2254" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Modern" value="2255" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Modern" value="2256" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Primary" id="Q7NAPA" value="2241" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q7Secondary" id="Q7NAPA" value="2242" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7New" value="2243" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7New" value="2244" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7OReilly" value="2245" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7OReilly" value="2246" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" value="2227" id="Q7parts"/></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" value="2228" id="Q7parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7PartsSource" value="2247" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7PartsSource" value="2248" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Pep" value="2249" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Pep" value="2250" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Prof" value="2251" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Prof" value="2252" /></td>
									<td>Professional's choice</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Pro" value="2261" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Pro" value="2262" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7TruStar" value="2263" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7TruStar"  value="2264" /></td>
								<td>TruStar</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Worldpac" value="2271" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Worldpac" value="2272" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Other1" value="2273" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Other1" value="2274" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q7Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q7Primary" id="Q7Other2" value="2275" /></td>
							<td class="text-center"><form:radiobutton path="Q7Secondary" id="Q7Other2" value="2276" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q7Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_6" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Hub Assemblies']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_8" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Brakes']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question7 Ending -->
				<div class="clearfix topMargn_5" id="Question8" style="display: none;">
			<div class="">
				<p>8. Please select your top two suppliers for <b>Brakes</b>.
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q8Primary" value="2277" id="Q8adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Secondary" id="Q8adv" value="2278"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Primary" id="Q8Altrom" value="2281" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Secondary" id="Q8Altrom" value="2282" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Autozone" value="2283" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Autozone" value="2284" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Primary" id="Q8Auto" value="2285" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Secondary" id="Q8Auto" value="2286" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8APPlus" value="2319" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8APPlus" value="2320" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8APlus" value="2321" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8APlus" value="2322" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Best" value="2311" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Best" value="2312" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8cand" value="2287" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8cand" value="2288" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Primary" id="Q8Car" value="2289" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Secondary" id="Q8Car" value="2290" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Fed" value="2291" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Fed"  value="2292" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Fisher" value="2317" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Fisher" value="2318" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Ind" value="2309" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Ind" value="2310" /></td>
								<td>Independent jobber</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Lord" value="2305" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Lord" value="2306" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Modern" value="2307" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Modern" value="2308" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Primary" id="Q8NAPA" value="2293" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q8Secondary" id="Q8NAPA" value="2294" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8New" value="2295" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8New" value="2296" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8OReilly" value="2297" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8OReilly" value="2298" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" value="2279" id="Q8parts"/></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" value="2280" id="Q8parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8PartsSource" value="2299" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8PartsSource" value="2300" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Pep" value="2301" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Pep" value="2302" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Prof" value="2303" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Prof" value="2304" /></td>
									<td>Professional's choice</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Pro" value="2313" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Pro" value="2314" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8TruStar" value="2315" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8TruStar"  value="2316" /></td>
								<td>TruStar</td>
						</tr>
						
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Worldpac" value="2323" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Worldpac" value="2324" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Other1" value="2325" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Other1" value="2326" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q8Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q8Primary" id="Q8Other2" value="2327" /></td>
							<td class="text-center"><form:radiobutton path="Q8Secondary" id="Q8Other2" value="2328" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q8Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_7" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Wheel Bearings']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_9" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Gaskets']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question8 Ending -->
						<div class="clearfix topMargn_5" id="Question9" style="display: none;">
			<div class="">
				<p>9. Please select your top two suppliers for <b>Gaskets</b>.
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q9Primary" value="2329" id="Q9adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Secondary" id="Q9adv" value="2330"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Primary" id="Q9Altrom" value="2333" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Secondary" id="Q9Altrom" value="2334" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Autozone" value="2335" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Autozone" value="2336" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Primary" id="Q9Auto" value="2337" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Secondary" id="Q9Auto" value="2338" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9APPlus" value="2371" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9APPlus" value="2372" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9APlus" value="2373" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9APlus" value="2374" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Best" value="2363" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Best" value="2364" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9cand" value="2339" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9cand" value="2340" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Primary" id="Q9Car" value="2341" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Secondary" id="Q9Car" value="2342" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Fed" value="2343" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Fed"  value="2344" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Fisher" value="2369" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Fisher" value="2370" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Ind" value="2361" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Ind" value="2362" /></td>
								<td>Independent jobber</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Lord" value="2357" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Lord" value="2358" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Modern" value="2359" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Modern" value="2360" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Primary" id="Q9NAPA" value="2345" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q9Secondary" id="Q9NAPA" value="2346" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9New" value="2347" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9New" value="2348" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9OReilly" value="2349" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9OReilly" value="2350" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" value="2331" id="Q9parts"/></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" value="2332" id="Q9parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9PartsSource" value="2351" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9PartsSource" value="2352" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Pep" value="2353" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Pep" value="2354" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Prof" value="2355" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Prof" value="2356" /></td>
									<td>Professional's choice</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Pro" value="2365" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Pro" value="2366" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9TruStar" value="2367" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9TruStar"  value="2368" /></td>
								<td>TruStar</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Worldpac" value="2375" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Worldpac" value="2376" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Other1" value="2377" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Other1" value="2378" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q9Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q9Primary" id="Q9Other2" value="2379" /></td>
							<td class="text-center"><form:radiobutton path="Q9Secondary" id="Q9Other2" value="2380" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q9Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_8" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Brakes']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_10" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Wipers']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question9 Ending -->
						<div class="clearfix topMargn_5" id="Question10" style="display: none;">
			<div class="">
				<p>10. Please select your top two suppliers for <b>Wiper Blades</b>.
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q10Primary" value="2381" id="Q10adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Secondary" id="Q10adv" value="2382"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Primary" id="Q10Altrom" value="2385" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Secondary" id="Q10Altrom" value="2386" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Autozone" value="2387" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Autozone" value="2388" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Primary" id="Q10Auto" value="2389" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Secondary" id="Q10Auto" value="2390" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10APPlus" value="2423" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10APPlus" value="2424" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q1APlus" value="2425" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q1APlus" value="2426" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Best" value="2415" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Best" value="2416" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10cand" value="2391" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10cand" value="2392" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Primary" id="Q10Car" value="2393" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Secondary" id="Q10Car" value="2394" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Fed" value="2395" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Fed"  value="2396" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Fisher" value="2421" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Fisher" value="2422" /></td>
								<td>Fisher</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Ind" value="2413" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Ind" value="2414" /></td>
								<td>Independent jobber</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Lord" value="2409" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Lord" value="2410" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Modern" value="2411" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Modern" value="2412" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Primary" id="Q10NAPA" value="2397" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q10Secondary" id="Q10NAPA" value="2398" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10New" value="2399" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10New" value="2400" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10OReilly" value="2401" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10OReilly" value="2402" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" value="2383" id="Q10parts"/></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" value="2384" id="Q10parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10PartsSource" value="2403" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10PartsSource" value="2404" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Pep" value="2405" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Pep" value="2406" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Prof" value="2407" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Prof" value="2408" /></td>
									<td>Professional's choice</td>
						</tr>
						
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Pro" value="2417" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Pro" value="2418" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10TruStar" value="2419" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10TruStar"  value="2420" /></td>
								<td>TruStar</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Worldpac" value="2427" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Worldpac" value="2428" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Other1" value="2429" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Other1" value="2430" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q10Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q10Primary" id="Q10Other2" value="2431" /></td>
							<td class="text-center"><form:radiobutton path="Q10Secondary" id="Q10Other2" value="2432" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q10Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_9" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Gaskets']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_11" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Engine']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question10 Ending -->
						<div class="clearfix topMargn_5" id="Question11" style="display: none;">
			<div class="">
				<p>11. Please select your top two suppliers for <b>Engine Components</b>
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q11Primary" value="2433" id="Q11adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Secondary" id="Q11adv" value="2434"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Primary" id="Q11Altrom" value="2437" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Secondary" id="Q11Altrom" value="2438" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Autozone" value="2439" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Autozone" value="2440" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Primary" id="Q11Auto" value="2441" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Secondary" id="Q11Auto" value="2442" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11APPlus" value="2475" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11APPlus" value="2476" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11APlus" value="2477" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11APlus" value="2478" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Best" value="2467" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Best" value="2468" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11cand" value="2443" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11cand" value="2444" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Primary" id="Q11Car" value="2445" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Secondary" id="Q11Car" value="2446" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Fed" value="2447" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Fed"  value="2448" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Fisher" value="2473" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Fisher" value="2474" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Ind" value="2465" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Ind" value="2466" /></td>
								<td>Independent jobber</td>
						</tr>
						
							<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Lord" value="2461" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Lord" value="2462" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Modern" value="2463" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Modern" value="2464" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Primary" id="Q11NAPA" value="2449" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q11Secondary" id="Q11NAPA" value="2450" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11New" value="2451" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11New" value="2452" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11OReilly" value="2453" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11OReilly" value="2454" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" value="2435" id="Q11parts"/></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" value="2436" id="Q11parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11PartsSource" value="2455" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11PartsSource" value="2456" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Pep" value="2457" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Pep" value="2458" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Prof" value="2459" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Prof" value="2460" /></td>
									<td>Professional's choice</td>
						</tr>
					
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Pro" value="2469" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Pro" value="2470" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11TruStar" value="2471" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11TruStar"  value="2472" /></td>
								<td>TruStar</td>
						</tr>
						
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Worldpac" value="2479" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Worldpac" value="2480" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Other1" value="2481" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Other1" value="2482" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q11Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q11Primary" id="Q11Other2" value="2483" /></td>
							<td class="text-center"><form:radiobutton path="Q11Secondary" id="Q11Other2" value="2484" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q11Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_10" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Wipers']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_12" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Filters']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question11 Ending -->
						<div class="clearfix topMargn_5" id="Question12" style="display: none;">
			<div class="">
				<p>12. Please select your top two suppliers for <b>Filters</b>.
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q12Primary" value="2485" id="Q12adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Secondary" id="Q12adv" value="2486"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Primary" id="Q12Altrom" value="2489" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Secondary" id="Q12Altrom" value="2490" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Autozone" value="2491" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Autozone" value="2492" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Primary" id="Q12Auto" value="2493" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Secondary" id="Q12Auto" value="2494" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12APPlus" value="2527" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12APPlus" value="2528" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12APlus" value="2529" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12APlus" value="2530" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12cand" value="2495" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12cand" value="2496" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Primary" id="Q12Car" value="2497" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Secondary" id="Q12Car" value="2498" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Fed" value="2499" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Fed"  value="2500" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Fisher" value="2525" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Fisher" value="2526" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Ind" value="2517" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Ind" value="2518" /></td>
								<td>Independent jobber</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Best" value="2519" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Best" value="2520" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Lord" value="2513" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Lord" value="2514" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Modern" value="2515" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Modern" value="2516" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Primary" id="Q12NAPA" value="2501" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q12Secondary" id="Q12NAPA" value="2502" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12New" value="2503" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12New" value="2504" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12OReilly" value="2505" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12OReilly" value="2506" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" value="2487" id="Q12parts"/></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" value="2488" id="Q12parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12PartsSource" value="2507" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12PartsSource" value="2508" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Pep" value="2509" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Pep" value="2510" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Prof" value="2511" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Prof" value="2512" /></td>
									<td>Professional's choice</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Pro" value="2521" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Pro" value="2522" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12TruStar" value="2523" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12TruStar"  value="2524" /></td>
								<td>TruStar</td>
						</tr>
						
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Worldpac" value="2531" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Worldpac" value="2532" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Other1" value="2533" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Other1" value="2534" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q12Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q12Primary" id="Q12Other2" value="2535" /></td>
							<td class="text-center"><form:radiobutton path="Q12Secondary" id="Q12Other2" value="2536" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q12Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_11" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Engine']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_13" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Batteries']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question12 Ending -->
						<div class="clearfix topMargn_5" id="Question13" style="display: none;">
			<div class="">
				<p>13. Please select your top two suppliers for <b>Batteries</b>.
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q13Primary" value="2537" id="Q13adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Secondary" id="Q13adv" value="2538"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Primary" id="Q13Altrom" value="2541" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Secondary" id="Q13Altrom" value="2542" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Autozone" value="2543" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Autozone" value="2544" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Primary" id="Q13Auto" value="2545" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Secondary" id="Q13Auto" value="2546" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13APPlus" value="2579" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13APPlus" value="2580" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13APlus" value="2581" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13APlus" value="2582" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Best" value="2571" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Best" value="2572" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13cand" value="2547" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13cand" value="2548" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Primary" id="Q13Car" value="2549" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Secondary" id="Q13Car" value="2550" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Fed" value="2551" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Fed"  value="2552" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Fisher" value="2577" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Fisher" value="2578" /></td>
								<td>Fisher</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Ind" value="2569" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Ind" value="2570" /></td>
								<td>Independent jobber</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Lord" value="2565" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Lord" value="2566" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Modern" value="2567" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Modern" value="2568" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Primary" id="Q13NAPA" value="2553" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q13Secondary" id="Q13NAPA" value="2554" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13New" value="2555" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13New" value="2556" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13OReilly" value="2557" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13OReilly" value="2558" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" value="2539" id="Q13parts"/></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" value="2540" id="Q13parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13PartsSource" value="2559" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13PartsSource" value="2560" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Pep" value="2561" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Pep" value="2562" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Prof" value="2563" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Prof" value="2564" /></td>
									<td>Professional's choice</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Pro" value="2573" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Pro" value="2574" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13TruStar" value="2575" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13TruStar"  value="2576" /></td>
								<td>TruStar</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Worldpac" value="2583" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Worldpac" value="2584" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Other1" value="2585" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Other1" value="2586" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q13Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q13Primary" id="Q13Other2" value="2587" /></td>
							<td class="text-center"><form:radiobutton path="Q13Secondary" id="Q13Other2" value="2588" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q13Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_12" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Filters']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_14" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Part Buying Allocation','Automotive Replacement Lighting']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question13 Ending -->
						<div class="clearfix topMargn_5" id="Question14" style="display: none;">
			<div class="">
				<p>14. Please select your top two suppliers for <b>Automotive Replacement Lighting</b>.
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Primary</th>
							<th>Secondary</th>
							<th>Suppliers</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							
							<td class="text-center col-lg-3">
							<form:radiobutton path="Q14Primary" value="2589" id="Q14adv" />
							</td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Secondary" id="Q14adv" value="2590"/></td>
								<td class="col-lg-6">Advance Auto parts</td>
						</tr>
						
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Primary" id="Q14Altrom" value="2593" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Secondary" id="Q14Altrom" value="2594" /></td>
								<td class="col-lg-6">Altrom</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Autozone" value="2595" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Autozone" value="2596" /></td>
									<td>Autozone</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Primary" id="Q14Auto" value="2597" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Secondary" id="Q14Auto" value="2598" /></td>
								<td class="col-lg-6">Auto value/Bumper to bumper</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14APPlus" value="2631" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14APPlus" value="2632" /></td>
								<td>Auto parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14APlus" value="2633" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14APlus" value="2634" /></td>
								<td>Auto plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Best" value="2623" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Best" value="2624" /></td>
								<td>Bestbuy distributors</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14cand" value="2599" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14cand" value="2600" /></td>
								<td>Canadian Tire</td>
						</tr>
						<tr>
						
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Primary" id="Q14Car" value="2601" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Secondary" id="Q14Car" value="2602" /></td>
									<td class="col-lg-6">CARQUEST</td>
						</tr>
						<tr>
					
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Fed" value="2603" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Fed"  value="2604" /></td>
										<td>Federated</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Fisher" value="2629" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Fisher" value="2630" /></td>
								<td>Fisher</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Ind" value="2621" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Ind" value="2622" /></td>
								<td>Independent jobber</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Lord" value="2617" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Lord" value="2618" /></td>
								<td>Lordco</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Modern" value="2619" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Modern" value="2620" /></td>
								<td>Modern sales Co-op</td>
						</tr>
						<tr>
							
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Primary" id="Q14NAPA" value="2605" /></td>
							<td class="text-center col-lg-3"><form:radiobutton path="Q14Secondary" id="Q14NAPA" value="2606" /></td>
								<td class="col-lg-6">NAPA</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14New" value="2607" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14New" value="2608" /></td>
								<td>New car dealership parts departments</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14OReilly" value="2609" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14OReilly" value="2610" /></td>
								<td>O'Reilly</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" value="2591" id="Q14parts"/></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" value="2592" id="Q14parts" /></td>
								<td>Parts plus</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14PartsSource" value="2611" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14PartsSource" value="2612" /></td>
								<td>PartsSource</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Pep" value="2613" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Pep" value="2614" /></td>
								<td>Pep Boys</td>
						</tr>
						<tr>
						
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Prof" value="2615" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Prof" value="2616" /></td>
									<td>Professional's choice</td>
						</tr>
						
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Pro" value="2625" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Pro" value="2626" /></td>
								<td>Pronto</td>
						</tr>
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14TruStar" value="2627" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14TruStar"  value="2628" /></td>
								<td>TruStar</td>
						</tr>
						
						<tr>
							
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Worldpac" value="2635" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Worldpac" value="2636" /></td>
								<td>Worldpac</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Other1" value="2637" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Other1" value="2638" /></td>
							<td>Other 1(Please Specify)
								<input type="text" value="" id="Q14Other1txt" class="form-control widthHeight surveyPartsBuying ">
							</td>
						</tr>
						<tr>
							<td class="text-center"><form:radiobutton path="Q14Primary" id="Q14Other2" value="2639" /></td>
							<td class="text-center"><form:radiobutton path="Q14Secondary" id="Q14Other2" value="2640" /></td>
							<td>Other 2(Please Specify)
								<input type="text" value="" id="Q14Other2txt" class="form-control widthHeight surveyPartsBuying ">
						    </td>
								
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_13" id="Question3_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Batteries']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_15" id="Question3_Next"
						onclick="javascript:movePartsBuySurvey(this,['Supplier Selection Criteria']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question14 Ending -->
		<div class="clearfix topMargn_5" id="Question15" style="display: none;">
			<div class="">
				<p>15. Rank your top three selection criteria for purchasing from your primary supplier in order of importance with 1 being most important. 
Type a 1, 2 or 3 in the box.
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Rank</th>
							<th>Selection criteria for purchasing</th>
							</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center"><input type="text" value="" name="2641" id="Q15rank1"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q15BrandsOffered" id="2641" />
							</td>
							<td>Brands offered</td>
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2642" id="Q15rank2"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q15PartAvailability" id="2642" />
							</td>
								<td>Part availabilty</td>
							
						</tr>
						<tr>
						
							<td class="text-center"><input type="text" value="" name="2643" id="Q15rank3"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q15Price" id="2643" />
							</td>
								<td>Price</td>
							
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2644" id="Q15rank4"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q15Proximity" id="2644" />
							</td>
								<td>Proximity</td>
							
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2645" id="Q15rank5"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q15Service" id="2645" />
							</td>
								<td>Service</td>
							
						</tr>
						<tr>
							
							<td class="text-center"><input type="text" value="" name="2646" id="Q15rank6"
								class="form-control widthHeight surveyTextLeft" maxlength="1">
								<form:hidden path="Q15Warranty" id="2646" />
							</td>
							<td>Warranty/Return Policy</td>
						</tr>		
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_14" id="Question1_Back"
						onclick="javascript:moveBack(this,['Part Buying Allocation','Automotive Replacement Lighting']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="submit" value="Ques_16" onclick="return movePartsBuySurvey(this,'');" id="partsBuySubmit"
						>Next &gt;</button>
				</div>
			</div>
		</div> 
		<!-- Question15 Ending -->
			</form:form>
</div>
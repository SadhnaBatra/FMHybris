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


<div class="clearfix" id="BrandQuestionsBlock"  style="display: none;">
 
  <div id="BrandSurveyError1" style="display: none;" class="col-lg-12 surveyError">A choice must be made for both 1st and 2nd choice.</div>
  <div id="BrandSurveyError2" style="display: none;" class="col-lg-12 surveyError">Cannot select same brand as 1st and 2nd choice.</div>
  <div id="BrandSurveyQ19andQ20Error" style="display: none;" class="col-lg-12 surveyError">Each the slider is not moved.</div>
  
	<form:form method="POST"  commandName="brandQuestionform"  id="brandQuestionform" action="${request.contextPath}/Survey/updateBrandSurvey"> 
		<div class="clearfix topMargn_5" id="Question1">
			<div class="">
				<p>1. What are the top two brands of <b>Control Arms</b> you install?
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="ACDelco_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0007"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="ACDelco_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0008"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="BeckArnley_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0467"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="BeckArnley_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0468"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="CARQUEST_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0005"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="CARQUEST_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0006"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Centric</td>
								<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="Centric_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0021"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="Centric_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0022"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Dorman</td>
								<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="Dorman_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0011"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="Dorman_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0012"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Driveworks</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="Driveworks_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0023"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="Driveworks_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0024"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="Duralast_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0019"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="Duralast_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0020"/></td>
						</tr>
						 <tr>
							<td class="col-lg-6">Master Pro</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="Master_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0009"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="Master_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0010"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">MOOG</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="MOOG_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0025"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="MOOG_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0026"/></td>
						</tr> 
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="NAPA_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0003"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="NAPA_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0004"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Parts Master</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="Parts_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0015"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="Parts_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0016"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Raybestos</td>
							<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="Raybestos_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0001" /></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="Raybestos_Q1" onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0002"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">TRW</td>
								<td class="text-center"><form:radiobutton path="firstQuestionPrimary" id="TRW_Q1"  onclick="javascript:removeTextBoxValue('firstQuestionOtherPrimary');" value="0013"/></td>
							<td class="text-center"><form:radiobutton path="firstQuestionSecondary" id="TRW_Q1"  onclick="javascript:removeTextBoxValue('firstQuestionOtherSecondary');" value="0014"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
									<td class="text-center"><form:input type="text"  path="firstQuestionOtherPrimary" id="firstQuestionOtherPrimary" onclick="javascript:removeRadioSelection('firstQuestionPrimary');" class="form-control widthHeight "/></td>
									<td class="text-center"><form:input type="text" path="firstQuestionOtherSecondary" id="firstQuestionOtherSecondary" onclick="javascript:removeRadioSelection('firstQuestionSecondary');" class="form-control widthHeight "/></td>
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
						onclick="javascript:moveBrandSurvey(this,'firstQuestionPrimary','firstQuestionSecondary','firstQuestionOtherPrimary','firstQuestionOtherSecondary',['Question 2']);">Next &gt;</button>
				</div>
				
			</div>
		</div>
		<!-- Question1 Ending -->
		<div class="clearfix topMargn_5" id="Question2"  style="display: none;">
			<div class="">
				<p>2. What are the top two brands of <b>Tie Rods</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary"  id="ACDelco_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0035"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="ACDelco_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0036"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary"  id="BeckArnley_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0469"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="BeckArnley_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0470"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="CARQUEST_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0033"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="CARQUEST_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0034"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Centric</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Centric_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0049"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="Centric_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0050"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Dorman</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Dorman_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0039"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="Dorman_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0040"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Driveworks</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Driveworks_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0051"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary"  id="Driveworks_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0052"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Duralast_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0047"/></td>
								<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="Duralast_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');"  value="0048"/></td>
						</tr>
						 <tr>
							<td class="col-lg-6">Master Pro</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Master_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0037"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary"  id="Master_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');"  value="0038"/></td>
						</tr>
						<%-- 
						<tr>
							<td class="col-lg-6">Mevotech</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Mevotech_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0045"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="Mevotech_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0046"/></td>
						</tr>
						--%>
						<tr>
							<td class="col-lg-6">MOOG</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="MOOG_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0053"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="MOOG_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0054"/></td>
						</tr> 
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="NAPA_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0031"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary"  id="NAPA_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0032"/></td>
							
						</tr>
						<tr>
							<td class="col-lg-6">Parts Master</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Parts_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0043"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="Parts_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0044"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Raybestos</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="Raybestos_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0029"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="Raybestos_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0030"/></td>
						</tr>						
						<tr>
							<td class="col-lg-6">TRW</td>
							<td class="text-center"><form:radiobutton path="secondQuestionPrimary" id="TRW_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherPrimary');" value="0041"/></td>
							<td class="text-center"><form:radiobutton path="secondQuestionSecondary" id="TRW_Q2" onclick="javascript:removeTextBoxValue('secondQuestionOtherSecondary');" value="0042"/></td>
						</tr>					
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="secondQuestionOtherPrimary" id="q2_others1" onclick="javascript:removeRadioSelection('secondQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="secondQuestionOtherSecondary" id="q2_others2" onclick="javascript:removeRadioSelection('secondQuestionSecondary');" class="form-control widthHeight " /></td>
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
						onclick="javascript:moveBack(this,['Question 1']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_3" id="Question1_Next"
						onclick="javascript:moveBrandSurvey(this,'secondQuestionPrimary','secondQuestionSecondary','secondQuestionOtherPrimary','secondQuestionOtherSecondary',['Question 3']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question2 Ending -->
		<div class="clearfix topMargn_5" id="Question3"  style="display: none;">
			<div class="">
				<p>3. What are the top two brands of <b>Ball Joints</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="ACDelco_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0063"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="ACDelco_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0064"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="BeckArnley_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0471"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="BeckArnley_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0472"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="CARQUEST_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0061"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="CARQUEST_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0062"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Centric</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Centric_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0077"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Centric_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0078"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Dorman</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Dorman_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0067"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Dorman_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0068"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Driveworks</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Driveworks_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0079"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Driveworks_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0080"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Duralast_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');"  value="0075"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Duralast_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');"  value="0076"/></td>
						</tr>
				 		<tr>
							<td class="col-lg-6">Master Pro</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Master_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0065"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Master_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');"  value="0066"/></td>
						</tr>
						<%-- 
						<tr>
							<td class="col-lg-6">Mevotech</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Mevotech_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0073"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Mevotech_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0074"/></td>
						</tr>
						--%>
						<tr>
							<td class="col-lg-6">MOOG</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="MOOG_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0081"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="MOOG_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');"  value="0082"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="NAPA_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0059"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="NAPA_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0060"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Parts Master</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Parts_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0071"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Parts_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0072"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Raybestos</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="Raybestos_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0057"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="Raybestos_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0058"/></td>
						</tr>						
						<tr>
							<td class="col-lg-6">TRW</td>
							<td class="text-center"><form:radiobutton path="thirdQuestionPrimary" id="TRW_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherPrimary');" value="0069"/></td>
							<td class="text-center"><form:radiobutton path="thirdQuestionSecondary" id="TRW_Q3" onclick="javascript:removeTextBoxValue('thirdQuestionOtherSecondary');" value="0070"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="thirdQuestionOtherPrimary" id="q3_others1" onclick="javascript:removeRadioSelection('thirdQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="thirdQuestionOtherSecondary" id="q3_others2" onclick="javascript:removeRadioSelection('thirdQuestionSecondary');" class="form-control widthHeight "/></td>
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
						onclick="javascript:moveBack(this,['Question 2']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_4" id="Question3_Next"
						onclick="javascript:moveBrandSurvey(this,'thirdQuestionPrimary','thirdQuestionSecondary','thirdQuestionOtherPrimary','thirdQuestionOtherSecondary',['Question 4']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question3 Ending -->
		<div class="clearfix topMargn_5" id="Question4"  style="display: none;">
			<div class="">
				<p>4. What are the top two brands of <b>Sway Bar Links</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="ACDelco_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0091"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="ACDelco_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0092"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="BeckArnley_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0473"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="BeckArnley_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0474"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="CARQUEST_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0089"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="CARQUEST_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0090"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Centric</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="Centric_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0105"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Centric_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');"  value="0106"/></td>
						</tr>
							<tr>
							<td class="col-lg-6">Dorman</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="Dorman_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0095"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Dorman_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0096"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Driveworks</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="Driveworks_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0107"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Driveworks_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0108"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="Duralast_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0103"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Duralast_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0104"/></td>
						</tr>
						 <tr>
							<td class="col-lg-6">Master Pro</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary"  id="Master_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0093"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Master_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0094"/></td>
						</tr>
						<%-- 
						<tr>
							<td class="col-lg-6">Mevotech</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="Mevotech_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0101"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Mevotech_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');"  value="0102"/></td>
						</tr>
						--%>
						<tr>
							<td class="col-lg-6">MOOG</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="MOOG_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0109"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="MOOG_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');"  value="0110"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Parts Master</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="Parts_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0099"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Parts_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');"  value="0100"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="NAPA_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0087"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="NAPA_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0088"/></td>
							
						</tr>
						<tr>
							<td class="col-lg-6">Raybestos</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary" id="Raybestos_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0085"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="Raybestos_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0086"/></td>
						</tr>					
						<tr>
							<td class="col-lg-6">TRW</td>
							<td class="text-center"><form:radiobutton path="fourthQuestionPrimary"  id="TRW_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherPrimary');" value="0097"/></td>
							<td class="text-center"><form:radiobutton path="fourthQuestionSecondary" id="TRW_Q4" onclick="javascript:removeTextBoxValue('fourthQuestionOtherSecondary');" value="0098"/></td>
						</tr>			 
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="fourthQuestionOtherPrimary"  id="q4_others1" onclick="javascript:removeRadioSelection('fourthQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="fourthQuestionOtherSecondary"  id="q4_others2" onclick="javascript:removeRadioSelection('fourthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_3" id="Question4_Back"
						onclick="javascript:moveBack(this,['Question 3']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_5" id="Question4_Next"
						onclick="javascript:moveBrandSurvey(this,'fourthQuestionPrimary','fourthQuestionSecondary','fourthQuestionOtherPrimary','fourthQuestionOtherSecondary',['Question 5']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question4 Ending -->
		<div class="clearfix topMargn_5" id="Question5" style="display: none;">
			<div class="">
				<p>5. What are the top two brands of <b>U-Joints</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<%--
						<tr>
							<td class="col-lg-6">AutoXtra</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="AutoXtra_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" value="0113"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="AutoXtra_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0114"/></td>
						</tr>
						<tr>
							<td>Certificate</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="Certificate_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');"  value="0115"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="Certificate_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0116"/></td>
						</tr>
						<tr>
							<td>MOOG</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="MOOG_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');"	value="0117"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="MOOG_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0118"/></td>
						</tr>
						<tr>
							<td>MotorCraft</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="MotorCraft_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0119"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary"  id="MotorCraft_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0120"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">National</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="National_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');"	value="0121"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="National_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0122"/></td>
						</tr>
 						<tr>
							<td>OEM</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="OEM_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0123"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="OEM_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0124"/></td>
						</tr>
 						<tr>
							<td class="col-lg-6">SKF</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="SKF_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0125"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="SKF_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0126"/></td>
						</tr>
						<tr>
							<td>Timken</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="Timken_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0127"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="Timken_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0128"/></td>
						</tr>
						 --%>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="ACDelco_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" value="0475"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="ACDelco_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0476"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Dana-Spicer</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="DanaSpicer_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" value="0113"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="DanaSpicer_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0114"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">GMB</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="GMB_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');"  value="0115"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="GMB_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0116"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">MOOG</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="MOOG_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');"	value="0117"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="MOOG_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0118"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NEAPCO</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="NEAPCO_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0119"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary"  id="NEAPCO_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0120"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Precision</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="Precision_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');"	value="0121"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="Precision_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0122"/></td>
						</tr>
 						<tr>
							<td class="col-lg-6">Qualis</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="Qualis_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0123"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="Qualis_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0124"/></td>
						</tr>
 						<tr>
							<td class="col-lg-6">SKF</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary"  id="SKF_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0125"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="SKF_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0126"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Wanxiang</td>
							<td class="text-center"><form:radiobutton path="fifthQuestionPrimary" id="Wanxiang_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherPrimary');" 	value="0127"/></td>
							<td class="text-center"><form:radiobutton path="fifthQuestionSecondary" id="Wanxiang_Q5" onclick="javascript:removeTextBoxValue('fifthQuestionOtherSecondary');" value="0128"/></td>
						</tr>
 						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="fifthQuestionOtherPrimary"  id="q5_others1" onclick="javascript:removeRadioSelection('fifthQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="fifthQuestionOtherSecondary" id="q5_others2" onclick="javascript:removeRadioSelection('fifthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_4" id="Question5_Back"
						onclick="javascript:moveBack(this,['Question 4']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_6" id="Question5_Next"
						onclick="javascript:moveBrandSurvey(this,'fifthQuestionPrimary','fifthQuestionSecondary','fifthQuestionOtherPrimary','fifthQuestionOtherSecondary',['Question 6']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question5 Ending -->
		<div class="clearfix topMargn_5" id="Question6" style="display: none;">
			<div class="">
				<p>6. What are the top two brands of <b>Hub Assemblies</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">AutoXtra</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="AutoXtra_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0131"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="AutoXtra_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0132"/></td>
						</tr>
						<%--
						 <tr>
							<td>Certificate</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="Certificate_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0133"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="Certificate_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0134"/></td>
						</tr>
						 --%>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="BeckArnley_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0481"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="BeckArnley_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0482"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="CARQUEST_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0483"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="CARQUEST_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0484"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Certified</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="Certified_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0485"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="Certified_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0486"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Driveworks</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="Driveworks_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0487"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="Driveworks_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0488"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="Duralast_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0489"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="Duralast_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0490"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">MasterPro</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="MasterPro_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0491"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="MasterPro_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0492"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">MOOG</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="MOOG_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  	value="0135"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="MOOG_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0136"/></td>
						</tr>
						<%-- 
						<tr>
							<td>MotorCraft</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="MotorCraft_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  value="0137"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="MotorCraft_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0138"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">National</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="National_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  	value="0139"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="National_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"   value="0140"/></td>
						</tr>
						 --%>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="NAPA_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  	value="0493"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="NAPA_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"   value="0494"/></td>
						</tr> 
						<tr>
							<td class="col-lg-6">OEM</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="OEM_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  	value="0141"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="OEM_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"   value="0142"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Parts Master</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="PartsMaster_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  	value="0495"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="PartsMaster_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"   value="0496"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Precision</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="Precision_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  	value="0497"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="Precision_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"   value="0498"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">SKF</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary" id="SKF_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');"  	value="0143"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="SKF_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0144"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Timken</td>
							<td class="text-center"><form:radiobutton path="sixthQuestionPrimary"  id="Timken_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherPrimary');" 	value="0145"/></td>
							<td class="text-center"><form:radiobutton path="sixthQuestionSecondary" id="Timken_Q6" onclick="javascript:removeTextBoxValue('sixthQuestionOtherSecondary');"  value="0146"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="sixthQuestionOtherPrimary" id="q6_others1" onclick="javascript:removeRadioSelection('sixthQuestionPrimary');" class="form-control widthHeight " /></td>
							<td class="text-center"><form:input type="text" path="sixthQuestionOtherSecondary" id="q6_others2" onclick="javascript:removeRadioSelection('sixthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_5" id="Question6_Back"
						onclick="javascript:moveBack(this,['Question 5']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_7" id="Question6_Next"
						onclick="javascript:moveBrandSurvey(this,'sixthQuestionPrimary','sixthQuestionSecondary','sixthQuestionOtherPrimary','sixthQuestionOtherSecondary',['Question 7']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question6 Ending -->
		<div class="clearfix topMargn_5" id="Question7" style="display: none;">
			<div class="">
				<p>7. What are the top two brands of <b>Wheel Bearings</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="Arnley_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0151"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="Arnley_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"   value="0152"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="CARQUEST_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0153"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="CARQUEST_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0154"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Driveworks</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="Driveworks_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0163"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="Driveworks_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0164"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="Duralast_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0159"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="Duralast_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0160"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">MasterPro</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="MasterPro_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0161"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="MasterPro_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0162"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">MOOG</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="MOOG_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0499"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="MOOG_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0500"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="NAPA_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0155"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="NAPA_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0156"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">National</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary" id="National_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"   value="0149"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="National_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0150"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">SKF</td>
							<td class="text-center"><form:radiobutton path="seventhQuestionPrimary"  id="SKF_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherPrimary');"  value="0157"/></td>
							<td class="text-center"><form:radiobutton path="seventhQuestionSecondary" id="SKF_Q7" onclick="javascript:removeTextBoxValue('seventhQuestionOtherSecondary');"  value="0158"/></td>
						</tr>					
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="seventhQuestionOtherPrimary"  id="q7_others1" onclick="javascript:removeRadioSelection('seventhQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="seventhQuestionOtherSecondary" id="q7_others2" onclick="javascript:removeRadioSelection('seventhQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_6" id="Question7_Back"
						onclick="javascript:moveBack(this,['Question 6']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_8" id="Question7_Next"
						onclick="javascript:moveBrandSurvey(this,'seventhQuestionPrimary','seventhQuestionSecondary','seventhQuestionOtherPrimary','seventhQuestionOtherSecondary',['Question 8']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question7 Ending -->
		<div class="clearfix topMargn_5" id="Question8" style="display: none;">
			<div class="">
				<p>8. What are the top two brands of <b>Wiper Blades</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary" id="ACDelco_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"   value="0179"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="ACDelco_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0180"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">ANCO</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary"  id="ANCO_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"  value="0183"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="ANCO_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0184"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Bosch</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary"  id="Bosch_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"  value="0177"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="Bosch_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0178"/></td>	
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary"  id="Duralast_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"  value="0173"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="Duralast_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0174"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Michelin</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary" id="Michelin_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"   value="0171"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="Michelin_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0172"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary"  id="NAPA_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"  value="0175"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary"  id="NAPA_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0176"/></td>						
						</tr>
						<tr>
							<td class="col-lg-6">Rain-X</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary"  id="Rain_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"  value="0169"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="Rain_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0170"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Trico</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary" id="Trico_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"   value="0167"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="Trico_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0168"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Valeo</td>
							<td class="text-center"><form:radiobutton path="eightQuestionPrimary" id="Valeo_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherPrimary');"   value="0181"/></td>
							<td class="text-center"><form:radiobutton path="eightQuestionSecondary" id="Valeo_Q8" onclick="javascript:removeTextBoxValue('eightQuestionOtherSecondary');"  value="0182"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="eightQuestionOtherPrimary" id="q8_others1" onclick="javascript:removeRadioSelection('eightQuestionPrimary');" class="form-control widthHeight " /></td>
							<td class="text-center"><form:input type="text" path="eightQuestionOtherSecondary" id="q8_others2" onclick="javascript:removeRadioSelection('eightQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_7" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 7']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_9" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'eightQuestionPrimary','eightQuestionSecondary','eightQuestionOtherPrimary','eightQuestionOtherSecondary',['Question 9']);">Next &gt;</button>
				</div>
			</div>
		</div><!-- Question8 Ending -->
		<div class="clearfix topMargn_5" id="Question9" style="display: none;">
			<div class="">
			    
				<p><b>The next 2 questions are about  BRAKES:</b><br>
				9. What are the top two brands of <b>Brake Pads</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary"  id="ACDelco_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"  value="0191"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="ACDelco_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0192"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Akebono</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary"  id="Akebono_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0207"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Akebono_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0208"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary" id="BeckArnley_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0501"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="BeckArnley_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0502"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Bendix</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary" id="Bendix_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0195"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Bendix_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0196"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Brake Best</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary" id="Break_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0193"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Break_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"   value="0194"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary" id="CARQUEST_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0201"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="CARQUEST_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0202"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Centric</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary"  id="Centric_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"  value="0205"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Centric_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"   value="0206"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary" id="Duralast_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0199"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Duralast_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"   value="0200"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Motorcraft</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary"  id="Motorcraft_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"  value="0203"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Motorcraft_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"   value="0204"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary" id="NAPA_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0187"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="NAPA_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"   value="0188"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Raybestos</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary"  id="Raybestos_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"  value="0197"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Raybestos_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0198"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Wagner</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary"  id="Wagner_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"  value="0209"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Wagner_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0210"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Wearever</td>
							<td class="text-center"><form:radiobutton path="ninthQuestionPrimary" id="Wearever_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherPrimary');"   value="0189"/></td>
							<td class="text-center"><form:radiobutton path="ninthQuestionSecondary" id="Wearever_Q9" onclick="javascript:removeTextBoxValue('ninthQuestionOtherSecondary');"  value="0190"/></td>
						</tr>						
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="ninthQuestionOtherPrimary" id="q9_others1" onclick="javascript:removeRadioSelection('ninthQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="ninthQuestionOtherSecondary" id="q9_others2" onclick="javascript:removeRadioSelection('ninthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_8" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 8']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_10" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'ninthQuestionPrimary','ninthQuestionSecondary','ninthQuestionOtherPrimary','ninthQuestionOtherSecondary',['Question 10']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question9 -->
		<div class="clearfix topMargn_5" id="Question10" style="display: none;">
			<div class="">
			    
				<p>
				10. What are the top two brands of <b>Brake Rotors</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary" id="ACDelco_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0217"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="ACDelco_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');"  value="0218"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Akebono</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"   id="Akebono_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0233"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary"  id="Akebono_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0234"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="BeckArnley_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0503"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="BeckArnley_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0504"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Bendix</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="Bendix_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0221"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Bendix_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0222"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Brake Best</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="Break_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0219"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Break_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0220"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="CARQUEST_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0227"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="CARQUEST_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');"  value="0228"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Centric</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="Centric_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0231"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Centric_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');"  value="0232"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary" id="Duralast_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0225"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Duralast_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0226"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Motorcraft</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="Motorcraft_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');"  value="0229"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Motorcraft_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');"  value="0230"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="NAPA_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');" value="0213"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="NAPA_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0214"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Raybestos</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="Raybestos_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');" value="0223"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Raybestos_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0224"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Wagner</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="Wagner_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');" value="0235"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Wagner_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0236"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Wearever</td>
							<td class="text-center"><form:radiobutton path="tenthQuestionPrimary"  id="Wearever_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherPrimary');" value="0215"/></td>
							<td class="text-center"><form:radiobutton path="tenthQuestionSecondary" id="Wearever_Q10" onclick="javascript:removeTextBoxValue('tenthQuestionOtherSecondary');" value="0216"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="tenthQuestionOtherPrimary" id="q10_others1"  onclick="javascript:removeRadioSelection('tenthQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="tenthQuestionOtherSecondary" id="q10_others2"  onclick="javascript:removeRadioSelection('tenthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_9" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 9']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_11" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'tenthQuestionPrimary','tenthQuestionSecondary','tenthQuestionOtherPrimary','tenthQuestionOtherSecondary',['Question 11']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question10 -->
		<div class="clearfix topMargn_5" id="Question11" style="display: none;">
			<div class="">
			    
				<p>
				11. What are the top two brands of <b>Gaskets</b> you install?</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary" id="ACDelco_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');"  value="0241"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="ACDelco_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');" value="0242"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Beck/Arnley</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary"  id="Beck_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');" value="0249"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="Beck_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');"  value="0250"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary"  id="CARQUEST_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');" value="0505"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="CARQUEST_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');"  value="0506"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Dorman</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary"  id="Dorman_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');" value="0243"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="Dorman_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');" value="0244"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Fel-Pro</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary" id="Fel_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');"  value="0253"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="Fel_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');"  value="0254"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">ITM</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary" id="ITM_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');"  value="0251"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="ITM_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');" value="0252"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Magnum</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary" id="Magnum_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');"  value="0247"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="Magnum_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');" value="0248"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary" id="NAPA_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');"  value="0239"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="NAPA_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');" value="0240"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Victor Reinz</td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionPrimary" id="Victor_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherPrimary');"  value="0245"/></td>
							<td class="text-center"><form:radiobutton path="elevanthQuestionSecondary" id="Victor_Q11" onclick="javascript:removeTextBoxValue('elevanthQuestionOtherSecondary');" value="0246"/></td>
						</tr>						
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="elevanthQuestionOtherPrimary" id="q11_others1" onclick="javascript:removeRadioSelection('elevanthQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="elevanthQuestionOtherSecondary" id="q11_others2" onclick="javascript:removeRadioSelection('elevanthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_10" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 10']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_12" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'elevanthQuestionPrimary','elevanthQuestionSecondary','elevanthQuestionOtherPrimary','elevanthQuestionOtherSecondary',['Question 12']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question11 -->
		<div class="clearfix topMargn_5" id="Question12" style="display: none;">
			<div class="">
			    
				<p>
				<b>The next 4 questions are about  Engine Components:</b><br>
				   12. What are the top two brands of <b>Pistons</b> you install?
				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<%-- <tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"   id="ACDelco_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');"	value="0257"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="ACDelco_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0258"/></td>
						</tr>
						<tr>
							<td>Akebono</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="Akebono_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" 	value="0259"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Akebono_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0260"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Bendix</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary" id="Bendix_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');"  value="0261"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary"  id="Bendix_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');"	value="0262"/></td>
						</tr>
						<tr>
							<td>Bosch</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary" id="Bosch_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');"  value="0263"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Bosch_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0264"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Carquest</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="Carquest_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0265"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Carquest_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0266"/></td>
						</tr>
						<tr>
							<td>Centric</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary" id="Centric_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');"  value="0267"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Centric_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0268"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Monroe</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary" id="Monroe_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');"  value="0269"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Monroe_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');"  value="0270"/></td>
						</tr>
						<tr>
							<td>NAPA</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="NAPA_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0271"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="NAPA_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0272"/></td>
						</tr>
						<tr>
							<td>OEM</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="OEM_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0273"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="OEM_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0274"/></td>
						</tr>
						<tr>
							<td>ProMax</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="ProMax_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0275"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="ProMax_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0276"/></td>
						</tr>
						<tr>
							<td>Raybestos</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="Raybestos_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0277"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Raybestos_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0278"/></td>
						</tr>
						<tr>
							<td>Wagner</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="Wagner_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0279"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Wagner_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0280"/></td>
						</tr> --%>
						
						<tr>
							<td class="col-lg-6">Mahle</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="Mahle_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0507"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="Mahle_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0508"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">NPR</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="NPR_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0509"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="NPR_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0510"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">Sealed Power/Sealed-Pro</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="SealedPower_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0511"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="SealedPower_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0512"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">Silv-o-lite</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="SilvOlite_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0513"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="SilvOlite_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0514"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">Top Line</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="TopLine_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0515"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="TopLine_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0516"/></td>
						</tr>
						
						<tr>
							<td class="col-lg-6">United Engine</td>
							<td class="text-center"><form:radiobutton path="twelthQuestionPrimary"  id="UnitedEngine_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherPrimary');" value="0517"/></td>
							<td class="text-center"><form:radiobutton path="tewlthQuestionSecondary" id="UnitedEngine_Q12" onclick="javascript:removeTextBoxValue('twelthQuestionOtherSecondary');" value="0518"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="twelthQuestionOtherPrimary" id="q12_others1" onclick="javascript:removeRadioSelection('twelthQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="twelthQuestionOtherSecondary" id="q12_others2" onclick="javascript:removeRadioSelection('tewlthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_11" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 11']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_13" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'twelthQuestionPrimary','tewlthQuestionSecondary','twelthQuestionOtherPrimary','twelthQuestionOtherSecondary',['Question 13']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question12 -->
		
		<div class="clearfix topMargn_5" id="Question13" style="display: none;">
			<div class="">
			    
				<p>
				
				   13. What are the top two brands of <b>Piston Rings</b> you install?

				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<%-- <tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary"  id="ACDelco_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');" value="0283"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="ACDelco_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');"  value="0284"/></td>
						</tr>
						<tr>
							<td>Akebono</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary"  id="Akebono_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');" value="0285"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Akebono_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0286"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Bendix</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary"  id="Bendix_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');" value="0287"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Bendix_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0288"/></td>
						</tr>
						<tr>
							<td>Bosch</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="Bosch_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0289"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Bosch_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0290"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Carquest</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary"  id="Carquest_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');" value="0291"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Carquest_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0292"/></td>
						</tr>
						<tr>
							<td>Centric</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="Centric_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0293"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Centric_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');"  value="0294"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Monroe</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="Monroe_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0295"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Monroe_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0296"/></td>
						</tr>
						<tr>
							<td>NAPA</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary"  id="NAPA_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');" value="0297"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="NAPA_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0298"/></td>
						</tr>
						<tr>
							<td>OEM</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="OEM_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0299"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="OEM_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0300"/></td>
						</tr>
						<tr>
							<td>ProMax</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="ProMax_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0301"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="ProMax_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');"  value="0302"/></td>
						</tr>
						<tr>
							<td>Raybestos</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary"  id="Raybestos_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');" value="0303"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Raybestos_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0304"/></td>
						</tr>
						<tr>
							<td>Wagner</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="Wagner_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0305"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Wagner_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0306"/></td>
						</tr> --%>
						
						<tr>
							<td class="col-lg-6">Grant Piston Rings</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="GrantPistonRings_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0519"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="GrantPistonRings_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0520"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Hastings</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="Hastings_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0521"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Hastings_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0522"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Mahle</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="Mahle_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0523"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="Mahle_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0524"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NPR</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="NPR_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0525"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="NPR_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0526"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Sealed Power/Speed-Pro</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="SealedPower_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0527"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="SealedPower_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0528"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Total Seal</td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionPrimary" id="TotalSeal_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherPrimary');"  value="0529"/></td>
							<td class="text-center"><form:radiobutton path="thirteenthQuestionSecondary" id="TotalSeal_Q13" onclick="javascript:removeTextBoxValue('thirteenthQuestionOtherSecondary');" value="0530"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="thirteenthQuestionOtherPrimary" id="q13_others1" onclick="javascript:removeRadioSelection('thirteenthQuestionPrimary');" class="form-control widthHeight "/></td>
							<td class="text-center"><form:input type="text" path="thirteenthQuestionOtherSecondary" id="q13_others2" onclick="javascript:removeRadioSelection('thirteenthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_12" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 12']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_14" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'thirteenthQuestionPrimary','thirteenthQuestionSecondary','thirteenthQuestionOtherPrimary','thirteenthQuestionOtherSecondary',['Question 14']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question13 -->
		
		<div class="clearfix topMargn_5" id="Question14" style="display: none;">
			<div class="">
			    
				<p>
			14. What are the top two brands of <b>Engine Bearings</b> you install?

				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<%-- <tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="ACDelco_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0309"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="ACDelco_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0310"/></td>
						</tr>
						<tr>
							<td>Akebono</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary" id="Akebono_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');"  value="0311"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Akebono_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0312"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Bendix</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="Bendix_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0313"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Bendix_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0314"/></td>
						</tr>
						<tr>
							<td>Bosch</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="Bosch_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0315"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Bosch_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0316"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Carquest</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="Carquest_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0317"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Carquest_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0318"/></td>
						</tr>
						<tr>
							<td>Centric</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="Centric_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0319"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Centric_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0320"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Monroe</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="Monroe_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0321"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Monroe_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0322"/></td>
						</tr>
						<tr>
							<td>NAPA</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="NAPA_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0323"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="NAPA_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');"  value="0324"/></td>
						</tr>
						<tr>
							<td>OEM</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="OEM_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0325"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary"  id="OEM_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0326"/></td>
						</tr>
						<tr>
							<td>ProMax</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="ProMax_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');"  value="0327"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="ProMax_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0328"/></td>
						</tr>
						<tr>
							<td>Raybestos</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="Raybestos_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0329"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Raybestos_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0330"/></td>
						</tr>
						<tr>
							<td>Wagner</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="Wagner_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0331"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="Wagner_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0332"/></td>
						</tr> --%>
						<tr>
							<td class="col-lg-6">ACL</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="ACL_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0531"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="ACL_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0532"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">King</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="King_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0533"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="King_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0534"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Mahle/Clevite</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="MahleClevite_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0535"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="MahleClevite_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0536"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Sealed Power/Speed-Pro</td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionPrimary"  id="SealedPower_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherPrimary');" value="0537"/></td>
							<td class="text-center"><form:radiobutton path="fourteenthQuestionSecondary" id="SealedPower_Q14" onclick="javascript:removeTextBoxValue('fourteenthQuestionOtherSecondary');" value="0538"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
						<td class="text-center"><form:input type="text" path="fourteenthQuestionOtherPrimary" id="q14_others1" onclick="javascript:removeRadioSelection('fourteenthQuestionPrimary');" class="form-control widthHeight "/></td>
						<td class="text-center"><form:input type="text" path="fourteenthQuestionOtherSecondary" id="q14_others2" onclick="javascript:removeRadioSelection('fourteenthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_13" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 13']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_15" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'fourteenthQuestionPrimary','fourteenthQuestionSecondary','fourteenthQuestionOtherPrimary','fourteenthQuestionOtherSecondary',['Question 15']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question14 -->
			<div class="clearfix topMargn_5" id="Question15" style="display: none;">
			<div class="">
			    
				<p>
			15. What are the top two brands of  <b>ValveTrain Components</b> you install?
				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<%--
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary" id="ACDelco_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');"  value="0335"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="ACDelco_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0336"/></td>
						</tr>
						<tr>
							<td>Akebono</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary" id="Akebono_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');"  value="0337"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Akebono_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0338"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Bendix</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary" id="Bendix_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');"  value="0339"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Bendix_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0340"/></td>
						</tr>
						<tr>
							<td>Bosch</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="Bosch_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0341"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Bosch_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0342"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Carquest</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="Carquest_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0343"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Carquest_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0344"/></td>
						</tr>
						<tr>
							<td>Centric</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="Centric_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0345"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Centric_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0346"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Monroe</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="Monroe_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0347"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Monroe_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0348"/></td>
						</tr>
						<tr>
							<td>NAPA</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="NAPA_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0349"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="NAPA_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0350"/></td>
						</tr>
						<tr>
							<td>OEM</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="OEM_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0351"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="OEM_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0352"/></td>
						</tr>
						<tr>
							<td>ProMax</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="ProMax_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0353"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="ProMax_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0354"/></td>
						</tr>
						<tr>
							<td>Raybestos</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="Raybestos_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0355"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Raybestos_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0356"/></td>
						</tr>
						<tr>
							<td>Wagner</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary" id="Wagner_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');"  value="0357"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Wagner_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0358"/></td>
						</tr> --%>
						
						<tr>
							<td class="col-lg-6">Comp Cams</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary" id="CompCams_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');"  value="0539"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="CompCams_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0540"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Elgin</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary" id="Elgin_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');"  value="0541"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Elgin_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0542"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Melling</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary" id="Melling_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');"  value="0543"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Melling_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0544"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Pioneer</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="Pioneer_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0545"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Pioneer_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0546"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Qualcast</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="Qualcast_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0547"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="Qualcast_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0548"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">SBI</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="SBI_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0549"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="SBI_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0550"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Sealed Power / Speed-Pro</td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionPrimary"  id="SealedPower_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherPrimary');" value="0551"/></td>
							<td class="text-center"><form:radiobutton path="fifteenthQuestionSecondary" id="SealedPower_Q15" onclick="javascript:removeTextBoxValue('fifteenthQuestionOtherSecondary');" value="0552"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="fifteenthQuestionOtherPrimary" id="q15_others1" onclick="javascript:removeRadioSelection('fifteenthQuestionPrimary');" class="form-control widthHeight "/> </td>
						<td class="text-center"><form:input type="text"    path="fifteenthQuestionOtherSecondary" id="q15_others2" onclick="javascript:removeRadioSelection('fifteenthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_14" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 14']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_16" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'fifteenthQuestionPrimary','fifteenthQuestionSecondary','fifteenthQuestionOtherPrimary','fifteenthQuestionOtherSecondary',['Question 16']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question15 -->
			<div class="clearfix topMargn_5" id="Question16" style="display: none;">
			<div class="">
			    
				<p>
			16. What are the top two brands of <b>Filters</b> you install?
				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="ACDelco_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');" value="0373"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="ACDelco_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');"  value="0374"/></td>
						</tr>
						<tr>
							<td>Champion</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="Champion_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');"  value="0379"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="Champion_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');" value="0380"/></td>
						</tr>
						<tr>
							<td>Fram</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="Fram_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');"  value="0363"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="Fram_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');"  value="0364"/></td>
						</tr>
						<%-- 
						<tr>
							<td class="col-lg-6">K&P</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="KP_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');" value="0369"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="KP_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');" value="0370"/></td>
						</tr>
						 --%>
						<tr>
							<td class="col-lg-6">K&N</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="KN_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');" value="0369"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="KN_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');" value="0370"/></td>
						</tr> 
						<tr>
							<td>Mopar</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary" id="Mopar_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');"  value="0377"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="Mopar_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');"  value="0378"/></td>
						</tr>
						<tr>
							<td>Motorcraft</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="Motorcraft_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');" value="0375"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="Motorcraft_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');"  value="0376"/></td>
						</tr>
						<tr>
							<td>NAPA</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="NAPA_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');"  value="0367"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="NAPA_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');"  value="0368"/></td>
						</tr>
						<tr>
							<td>ProMax</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="ProMax_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');" value="0553"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="ProMax_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');" value="0554"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Purolator</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary" id="Purolator_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');"  value="0365"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="Purolator_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');" value="0366"/></td>
						</tr>
						<tr>
							<td>Royal Purple</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary" id="Royal_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');"  value="0371"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="Royal_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');"  value="0372"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Wix</td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionPrimary"  id="Wix_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherPrimary');" value="0361"/></td>
							<td class="text-center"><form:radiobutton path="sixteenthQuestionSecondary" id="Wix_Q16" onclick="javascript:removeTextBoxValue('sixteenthQuestionOtherSecondary');" value="0362"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="sixteenthQuestionOtherPrimary" id="q16_others1" onclick="javascript:removeRadioSelection('sixteenthQuestionPrimary');" class="form-control widthHeight "/></td>
						<td class="text-center"><form:input type="text"    path="sixteenthQuestionOtherSecondary" id="q16_others2" onclick="javascript:removeRadioSelection('sixteenthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_15" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 15']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_17" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'sixteenthQuestionPrimary','sixteenthQuestionSecondary','sixteenthQuestionOtherPrimary','sixteenthQuestionOtherSecondary',['Question 17']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question16 -->
			<div class="clearfix topMargn_5" id="Question17" style="display: none;">
			<div class="">
			    
				<p>
			17. What are the top two brands of <b>Batteries</b> you install?
				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">ACDelco</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary" id="ACDelco_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');"  value="0387"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="ACDelco_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');" value="0388"/></td>
						</tr>
						<tr>
							<td>Autocraft</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary" id="Autocraft_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');"  value="0389"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Autocraft_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');"  value="0390"/></td>
						</tr>
						<tr>
							<td>Bosch</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary"  id="Bosch_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');" value="0397"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Bosch_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');" value="0398"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">CARQUEST</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary"  id="CARQUEST_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');"  value="0395"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="CARQUEST_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');"  value="0396"/></td>
						</tr>
						<tr>
							<td>Champion</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary"  id="Champion_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');"  value="0403"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Champion_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');" value="0404"/></td>
						</tr>
						<tr>
							<td>Deka</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary"  id="Deka_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');" value="0401"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Deka_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');"  value="0402"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Duralast</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary"  id="Duralast_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');" value="0391"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Duralast_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');" value="0392"/></td>
						</tr>
						<tr>
							<td>Exide</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary" id="Exide_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');"  value="0399"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Exide_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');"  value="0400"/></td>
						</tr>
						<tr>
							<td>Interstate</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary"  id="Interstate_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');" value="0385"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Interstate_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');" value="0386"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">NAPA</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary" id="NAPA_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');"  value="0383"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="NAPA_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');" value="0384"/></td>
						</tr>						
						<tr>
							<td>Super Start</td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionPrimary"  id="Super_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherPrimary');" value="0393"/></td>
							<td class="text-center"><form:radiobutton path="seventeenthQuestionSecondary" id="Super_Q17" onclick="javascript:removeTextBoxValue('seventeenthQuestionOtherSecondary');" value="0394"/></td>
						</tr>
						<tr>
							<td>Other (Please Specify)</td>
							<td class="text-center"><form:input type="text" path="seventeenthQuestionOtherPrimary" id="q17_others1" onclick="javascript:removeRadioSelection('seventeenthQuestionPrimary');" class="form-control widthHeight "/></td>
						<td class="text-center"><form:input type="text"    path="seventeenthQuestionOtherSecondary" id="q17_others2" onclick="javascript:removeRadioSelection('seventeenthQuestionSecondary');" class="form-control widthHeight "/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_16" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 16']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_18" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'seventeenthQuestionPrimary','seventeenthQuestionSecondary','seventeenthQuestionOtherPrimary','seventeenthQuestionOtherSecondary',['Question 18']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question17 -->
			<div class="clearfix topMargn_5" id="Question18" style="display: none;">
			<div class="">
			    
				<p>
		18. What are the top two brands of <b>Automotive Replacement Lighting</b> you install?
				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Brands</th>
							<th>1st Choice</th>
							<th>2nd Choice</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-lg-6">Eiko</td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionPrimary"  id="Eiko_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherPrimary');" value="0411"/></td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionSecondary" id="Eiko_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherSecondary');" value="0412"/></td>
						</tr>
						<tr>
							<td>GE</td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionPrimary"  id="GE_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherPrimary');" value="0409"/></td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionSecondary" id="GE_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherSecondary');" value="0410"/></td>
						</tr>
						<tr>
							<td>Hella</td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionPrimary" id="Hella_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherPrimary');"  value="0413"/></td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionSecondary" id="Hella_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherSecondary');" value="0414"/></td>
						</tr>
						<tr>
							<td>NAPA</td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionPrimary" id="NAPA_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherPrimary');"  value="0417"/></td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionSecondary" id="NAPA_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherSecondary');" value="0418"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Philips</td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionPrimary" id="Philips_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherPrimary');"  value="0407"/></td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionSecondary" id="Philips_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherSecondary');" value="0408"/></td>
						</tr>
						<tr>
							<td class="col-lg-6">Sylvania</td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionPrimary"  id="Sylvania_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherPrimary');" value="0415"/></td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionSecondary" id="Sylvania_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherSecondary');" value="0416"/></td>
						</tr>
						<tr>
							<td>Wagner</td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionPrimary" id="Wagner_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherPrimary');"  value="0419"/></td>
							<td class="text-center"><form:radiobutton path="eighteenthQuestionSecondary" id="Wagner_Q18" onclick="javascript:removeTextBoxValue('eighteenthQuestionOtherSecondary');" value="0420"/></td>
						</tr>						
						<tr>
							<td>Other (Please Specify)</td>
						<td class="text-center"><form:input type="text"    path="eighteenthQuestionOtherPrimary" id="q18_others1" onclick="javascript:removeRadioSelection('eighteenthQuestionPrimary');" class="form-control widthHeight"/></td>
						<td class="text-center"><form:input type="text"    path="eighteenthQuestionOtherSecondary" id="q18_others2" onclick="javascript:removeRadioSelection('eighteenthQuestionSecondary');" class="form-control widthHeight"/></td>
						</tr>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_17" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 17']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_19" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'eighteenthQuestionPrimary','eighteenthQuestionSecondary','eighteenthQuestionOtherPrimary','eighteenthQuestionOtherSecondary',['Question 19']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question18 -->

<div class="clearfix topMargn_5" id="Question19" style="display: none;">
			<div class="">
			    
				<p>
			19. There are  a variety of reasons why you do not use your 1st choice brand: customer opinion, part availability, price and type of vehicle. 

			Let's say you had 10 jobs, how many times out of 10 do you use your 1st choice brand?

				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn surveyHobbiesInt">
				<table border="0" class="table ordeDetailsTable">
										<thead>
						<tr>
							<th>Type of part used</th>
							<th>Move the slider to indicate how often you use your 1st choice</th>
							
						</tr>
					</thead>
					<tbody>
					<tr>
							<td class="col-lg-6">Ball Joints</td>
							<td class="text-center">
							<form:hidden  path="question19ballJoints" id="0555" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Brake pads</td>
							<td class="text-center">
								<form:hidden  path="question19Brakepads" id="0556" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Control Arms</td>
							<td class="text-center">
								<form:hidden  path="question19controlArms" id="0557" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Hub Assemblies</td>
							<td class="text-center">
								<form:hidden  path="question19hubAssemblie" id="0558" class="single-slider" style="display: none;" />
							</td>
						</tr>
						
						<tr>
							<td class="col-lg-6">Sway Bar Links</td>
							<td class="text-center">
								<form:hidden  path="question19swaybarlinkr" id="0559" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Tie Rods</td>
							<td class="text-center">
								<form:hidden  path="question19TieRods" id="0560" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">U- Joints</td>
							<td class="text-center">
								<form:hidden  path="question19UJoints" id="0561" class="single-slider" style="display: none;" />
							</td>				
							</tr>
							<tr>
							<td class="col-lg-6">Wheel Bearings </td>
							<td class="text-center">
								<form:hidden  path="question19Bearingseals" id="0562" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Wipers</td>
							<td class="text-center">
								<form:hidden  path="question19Wipers" id="0563" class="single-slider" style="display: none;" />
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
						type="button" value="Ques_18" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 18']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_20" id="Question8_Next"
						onclick="javascript:moveBrandSurvey(this,'','','','',['Question 20']);">Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question19 -->
			<div class="clearfix topMargn_5" id="Question20" style="display: none;">
			<div class="">
			    
				<p>
			20. There are  a variety of reasons why you do not use your 1st choice brand: customer opinion, part availability, price and type of vehicle. 

			Let's say you had 10 jobs, how many times out of 10 do you use your 1st choice brand?


				</p>
			</div>
			<div class="col-lg-3"></div>
			<div class="col-lg-6 table-responsive userTable rewardsEarn surveyHobbiesInt">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Type of part used</th>
							<th>Move the slider to indicate hw often you use your 1st choice</th>
							
						</tr>
					</thead>
					<tbody>
					<tr>
							<td class="col-lg-6">Automotive Replacement Lighting</td>
							<td class="text-center">
							<form:hidden  path="question20AlignmentParts" id="0564" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Batteries</td>
							<td class="text-center">
							<form:hidden  path="question19Batteries" id="0565" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Brake Rotors</td>
							<td class="text-center">
								<form:hidden  path="question19Rotors" id="0566" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Engine Bearings</td>
							<td class="text-center">
							<form:hidden  path="question20Batteries" id="0567" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Filters</td>
							<td class="text-center">
								<form:hidden  path="question20filters" id="0568" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Gaskets</td>
							<td class="text-center">
								<form:hidden  path="question20gaskets" id="0569" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Pistons</td>
							<td class="text-center">
								<form:hidden  path="question20hubAssemblie" id="0570" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Pistion Rings</td>
							<td class="text-center">
								<form:hidden  path="question20idlerarms" id="0571" class="single-slider" style="display: none;" />
							</td>
						</tr>
						<tr>
							<td class="col-lg-6">Valvetrain Components</td>
							<td class="text-center">
								<form:hidden  path="question20UJoints" id="0572" class="single-slider" style="display: none;" />
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
						type="button" value="Ques_19" id="Question8_Back"
						onclick="javascript:moveBack(this,['Question 19']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="submit" value="Ques_21" onclick="return moveBrandSurvey(this,'','','','','');"  id="brandSubmit"
						>Next &gt;</button>
				</div>
			</div>
		</div><!--  End Question20 -->
		<input type="hidden" id="BRAND_SURVEY_FORM" value="" />
	
	
	
</form:form>
</div>

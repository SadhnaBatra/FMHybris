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



   <span class="col-lg-12 surveyError" id="errormsgSurvey" > </span>
<!--  <div id="IntandHobbiesSurveyQ1Error" style="display: none;" class="col-lg-12 surveyError">Please select atleast one.</div> -->
<form:form method="POST" name="InterestAndHobbiesQuestionsForm" id="InterestAndHobbiesQuestionsForm" commandName="interestAndHobbiesQuestionsForm" class="" action="${request.contextPath}/Survey/updateIntersetAndHobbiesSurvey">
		<div class="clearfix topMargn_5" id="Question1">
			<div class="">
				<p>1. What is your age?
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select One</th>
							</tr>
					</thead>
					<tbody>
						<tr>
							<td><span class="text-center"><form:radiobutton 
								name="firstQAgeoption1" path="firstQAge" class="" value="1000"/></span>  13-17</td>
							
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton 
								path="firstQAge" name="firstQAgeoption2" value="1001"/></span> 18-24</td>
							
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="firstQAgeoption3" path="firstQAge" class="" value="1002"/></span>  25-34</td>
							
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="firstQAge" name="firstQAgeoption4" value="1003"/></span>  35-44</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="firstQAgeoption5" path="firstQAge" class="" value="1004"/></span>  45-54</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="firstQAge" name="firstQAgeoption6" value="1005"/></span>  55-64</td>
								</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="firstQAgeoption7" path="firstQAge" class="" value="1006"/></span>  65 or older</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="firstQAge" name="firstQAgeoption8" value="1007"/></span>  Prefer not to answer</td>
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
						onclick="javascript:moveBackInterestSurvey(this,[' ']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_2" id="Question1_Next"
						onclick="javascript:moveIntandHobbiesSurvey(this,'firstQAge',['Birthday']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question1 Ending -->
		<div class="clearfix topMargn_5" id="Question2" style="display: none;">
			<div class="">
				<p>2. What month is your birthday?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select One</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="secondQBirthMonthJan" path="secondQBirthMonth" class="" value="1008"/></span>	Jan</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="secondQBirthMonth" name="secondQBirthMonthFeb" value="1009"/></span>	Feb</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="secondQBirthMonthMar" path="secondQBirthMonth" class="" value="1010"/></span>	Mar</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="secondQBirthMonth" name="secondQBirthMonthApr" value="1011"/></span>	Apr</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="secondQBirthMonthMay" path="secondQBirthMonth" class="" value="1012"/></span>	May</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="secondQBirthMonth" name="secondQBirthMonthJun" value="1013"/></span>	Jun</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="secondQBirthMonthJul" path="secondQBirthMonth" class="" value="1014"/></span>	Jul</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="secondQBirthMonth" name="secondQBirthMonthAug" value="1015"/></span>	Aug</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="secondQBirthMonthSep" path="secondQBirthMonth" class="" value="1016"/></span>	Sep</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="secondQBirthMonth" name="secondQBirthMonthOct" value="1017"/></span>	Oct</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="secondQBirthMonth" name="secondQBirthMonthNov" value="1018"/></span>	Nov</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="secondQBirthMonth" name="secondQBirthMonthDec" value="1019"/></span>	Dec</td>
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
					onclick="javascript:moveBackInterestSurvey(this,['Age']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_3" id="Question1_Next"
						onclick="javascript:moveIntandHobbiesSurvey(this,'secondQBirthMonth',['Gender']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question2 Ending -->
		<div class="clearfix topMargn_5" id="Question3" style="display: none;">
			<div class="">
				<p>3. What is your gender?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select One</th>
							</tr>
					</thead>
					<tbody>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="thirdQGenderMale" path="thirdQGender" class="" value="1020"/></span>	Male</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="thirdQGender" name="thirdQGenderFemale" value="1021"/></span>	Female</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="thirdQGenderPrefer not to answer" path="thirdQGender" class="" value="1022"/></span>	Prefer not to answer</td>
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
					onclick="javascript:moveBackInterestSurvey(this,['Birthday']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_4" id="Question3_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'thirdQGender',['Ethnicity']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question3 Ending -->
		<div class="clearfix topMargn_5" id="Question4" style="display: none;">
			<div class="">
				<p>4. What is your ethnicity?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select One</th>
							</tr>
					</thead>
					<tbody>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="fourthQEthnicity" name="fourthQEthnicityAfrican American" value="1024"/></span>	African American</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fourthQEthnicityAmerican Indian" path="fourthQEthnicity" class="" value="1027"/></span>	American Indian</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="fourthQEthnicity" name="fourthQEthnicityAsian" value="1026"/></span>	Asian</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fourthQEthnicityCaucasian" path="fourthQEthnicity" class="" value="1025"/></span>	Caucasian</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fourthQEthnicityHispanic" path="fourthQEthnicity" class="" value="1023"/></span>	Hispanic</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="fourthQEthnicity" name="fourthQEthnicityPacific Islander" value="1028"/></span>	Pacific Islander</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fourthQEthnicityPrefer not to answer" path="fourthQEthnicity" class="" value="1029"/></span>	Prefer not to answer</td>
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
					onclick="javascript:moveBackInterestSurvey(this,['Gender']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_5" id="Question4_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'fourthQEthnicity',['Professional tenure']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question4 Ending -->
		<div class="clearfix topMargn_5" id="Question5" style="display: none;">
			<div class="">
				<p>5. How long have you been a professional technician?</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select One</th>
							</tr>
					</thead>
					<tbody>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fifthQTechExperienceLess than 1 Year" path="fifthQTechExperience" class="" value="1030"/></span>	Less than 1 Year</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="fifthQTechExperience" name="fifthQTechExperience1-2 years" value="1031"/></span>	1-2 years</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fifthQTechExperience3-5 years" path="fifthQTechExperience" class="" value="1032"/></span>	3-5 years</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="fifthQTechExperience" name="fifthQTechExperience6-10 years" value="1033"/></span>	6-10 years</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fifthQTechExperience11-20 years" path="fifthQTechExperience" class="" value="1034"/></span>	11-20 years</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								path="fifthQTechExperience" name="fifthQTechExperience21-30 years" value="1035"/></span>	21-30 years</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:radiobutton
								name="fifthQTechExperienceOver 30 years" path="fifthQTechExperience" class="" value="1036"/></span>	Over 30 years</td>
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
					onclick="javascript:moveBackInterestSurvey(this,['Ethnicity']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_6" id="Question5_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'fifthQTechExperience',['Hobbies']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question5 Ending -->
		<div class="clearfix topMargn_5" id="Question6" style="display: none;">
			<div class="">
				<p>6.What types of activities do you do on a regular basis?
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesAttending Professional Sporting Events" path="sixthQRegularActivities" class="" value="1037"/></span>	Attend Professional Sporting Events</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesCar Shows" value="1038"/></span>	Car Shows</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesCooking" path="sixthQRegularActivities" class="" value="1039"/></span>	Cooking</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesDancing" value="1040"/></span>	Dancing</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesGardening" path="sixthQRegularActivities" class="" value="1041"/></span>	Gardening</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesGoing to movies" value="1042"/></span>	Going to movies</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesHunting/Fishing" path="sixthQRegularActivities" class="" value="1043"/></span>	Hunting/Fishing</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesMotorsports events" value="1044"/></span>	Motorsports events</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox 
								path="sixthQRegularActivities" name="sixthQRegularActivitiesOutdoor activities(biking,boating,camping,hiking,etc.)" value="1045"/></span>	Outdoor activities (biking,boating,camping,hiking,etc.)</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesPhotography" path="sixthQRegularActivities" class="" value="1046"/></span>	Photography</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesPlaying Sports" value="1047"/></span>	Playing Sports</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesPlaying musical Instrument(guitor,piano,drums,etc.)" path="sixthQRegularActivities" class="" value="1048"/></span>	Playing musical Instrument (guitar,piano,drums,etc.)</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesReading" value="1049"/></span>	Reading </td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox  
								path="sixthQRegularActivities" name="sixthQRegularActivitiesRecreational Vehicles(motorcyclr,AVT,jet ski,etc.)" value="1050"/></span>	Recreational Vehicles (motorcycle,AVT,jet ski,etc.)</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesRestoring vehicles,Working on cars" path="sixthQRegularActivities" class="" value="1051"/></span>	Restoring vehicles,Working on cars</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesShooting range" value="1052"/></span>	Shooting range</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="sixthQRegularActivitiesSocial media" path="sixthQRegularActivities" class="" value="1053"/></span>	Social media</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesTravelling" value="1054"/></span>	Traveling</td>
							</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities"  name="sixthQRegularActivitiesVideo gaming" value="1055"/></span>	Video gaming</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								path="sixthQRegularActivities" name="sixthQRegularActivitiesWorking out/Exercise" value="1056"/></span>	Working out/Exercise</td>
							</tr>
						<tr>
							<td><span class="text-center"><input type="text" class="form-control surveySelect"	
								  name="test" id="1057"/>
								<form:hidden path="sixthQRegularActivitiesOther"  id="Q6RA" /></span>	Others (Please specify)</td>
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
					onclick="javascript:moveBackInterestSurvey(this,['Professional tenure']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_7" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'sixthQRegularActivities',['Motorsports Interest']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question6 Ending -->
		<div class="clearfix topMargn_5" id="Question7" style="display: none;">
			<div class="">
				<p>7. How much time do you spend watching/following these Motorsports?
</p>
			</div>
			<div class="clearfix"><div class="col-lg-12">
			<div class="col-lg-offset-3 col-lg-6 text-center">
			<span class="surveyLabelSliderOne">Not at all</span>
			<span class="surveyLabelSliderTwo"> Occasionally</span>
			<span class="surveyLabelSliderThree"> All the Time</span>
			</div>
			</div>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn surveyHobbiesInt">
				<table border="0" class="table ordeDetailsTable">
					<thead>
					</thead>
					<tbody>
					<tr> <td>Drag Racing</td>
					 <td><form:hidden path="seventhQMotorSportsTimeDragRacing"  id="1060" class="single-slider" name="seventhQueMotorSportsTimeDragRacing" style="display: none;" /></td> </tr>
					  <%-- <form:hidden path="seventhQMotorSportsTimeDragRacing" id="seventhQMotorSportsTimeDragRacingHidden"/> --%>
					<tr><td>Drifting</td>
					 <td><form:hidden path="seventhQMotorSportsTimeDrifting"  id="1063" class="single-slider" style="display: none;" name="seventhQueMotorSportsTimeDrifting" /></td></tr> 
					  <%-- <form:hidden path="seventhQMotorSportsTimeDrifting" id="seventhQMotorSportsTimeDriftingHidden"/> --%>
					<tr><td>Formula 1</td>
					 <td><form:hidden path="seventhQMotorSportsTimeFormula1"  id="1059" name="seventhQueMotorSportsTimeFormula1" class="single-slider" style="display: none;" /></td></tr>
					 <%-- <form:hidden path="seventhQMotorSportsTimeFormula1" id="seventhQMotorSportsTimeFormula1Hidden"/> --%> 
					 <tr> <td>Motocross</td>
					 <td><form:hidden path="seventhQMotorSportsTimeMotocross"  id="1061" class="single-slider" name="seventhQueMotorSportsTimeMotocross" style="display: none;" /></td> </tr>
					  <%-- <form:hidden path="seventhQMotorSportsTimeMotocross" id="seventhQMotorSportsTimeMotocrossHidden"/> --%> 
					<tr>
					 <td>NASCAR</td>
					 <td><form:hidden path="seventhQMotorSportsTimeNascar" id="1058" name="seventhQueMotorSportsTimeNascar" class="single-slider" style="display: none;" /></td></tr>
					<%-- <form:hidden path="seventhQMotorSportsTimeNascar" id="seventhQMotorSportsTimeNascarHidden"/> --%>
					 <tr><td>Rally</td>
					 <td><form:hidden path="seventhQMotorSportsTimeRally"  id="1064" class="single-slider" style="display: none;" name="seventhQueMotorSportsTimeRally" /></td> 
					 <%--  <form:hidden path="seventhQMotorSportsTimeRally" id="seventhQMotorSportsTimeRallyHidden"/> --%>
					</tr>
					 <tr><td>Supercross</td>
					 <td><form:hidden path="seventhQMotorSportsTimeSupercross"  id="1062" class="single-slider" style="display: none;" name="seventhQueMotorSportsTimeSupercross" /></td></tr> 
					  <%-- <form:hidden path="seventhQMotorSportsTimeSupercross" id="seventhQMotorSportsTimeSupercrossHidden"/> --%> 
						</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_6" id="Question7_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Hobbies']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_8" id="Question7_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'seventhQMotorSportsTimeSupercross',['Professional Sports Interest']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question7 Ending -->
		<div class="clearfix topMargn_5" id="Question8" style="display: none;">
			<div class="">
				<p>8. How much time do you spend watching/following these professional sports?
				</p>
			</div>
			<div class="clearfix"><div class="col-lg-12">
			<div class="col-lg-offset-3 col-lg-6 text-center">
			<span class="surveyLabelSliderOne">Not at all</span>
			<span class="surveyLabelSliderTwo"> Occasionally</span>
			<span class="surveyLabelSliderThree"> All the Time</span>
			</div>
			</div>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn surveyHobbiesInt">
				<table border="0" class="table ordeDetailsTable">
					<thead>
					</thead>
					<tbody>
					<tr> <td>Baseball</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeBaseball"  id="1067" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeBaseball" /></td> </tr>
					 <%--  <form:hidden  path="eightQProfessionalSportsTimeBaseball" id="eightQProfessionalSportsTimeBaseballHidden"/> --%>
					 <tr><td>Basketball</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeBasketball"  id="1066" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeBasketball" /></td></tr> 
					  	  <%-- <form:hidden  path="eightQProfessionalSportsTimeBasketball" id="eightQProfessionalSportsTimeBasketballHidden"/> --%>
					<tr><td>Extreme Sports</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeExtremeSports" id="1074" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeExtremeSports"/></td> 
					 <%-- <form:hidden  path="eightQProfessionalSportsTimeExtremeSports" id="eightQProfessionalSportsTimeExtremeSportsHidden"/> --%>  
					</tr>
					<tr><td>Fantasy Sports</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeFantasySports" id="1073" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeFantasySports"/></td> 
					 <%-- <form:hidden  path="eightQProfessionalSportsTimeFantasySports" id="eightQProfessionalSportsTimeFantasySportsHidden"/> --%> 
					</tr>
						<tr>
					 <td>Football</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeFootball"  id="1065" class="single-slider" style="display: none;" name ="eightQueProfessionalSportsTimeFootball" /></td></tr>
					 	  <%-- <form:hidden  path="eightQProfessionalSportsTimeFootball" id="eightQProfessionalSportsTimeFootballHidden"/> --%>
					<tr> <td>Hockey</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeHockey"  id="1068" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeHockey"/></td> </tr>
					<%--  <form:hidden  path="eightQProfessionalSportsTimeHockey" id="eightQProfessionalSportsTimeHockeyHidden"/> --%> 
					 <tr><td>MMA</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeMMA" id="1069" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeMMA"/></td></tr> 
					  <%-- <form:hidden  path="eightQProfessionalSportsTimeMMA" id="eightQProfessionalSportsTimeMMAHidden"/> --%> 
					 <tr><td>Soccer</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeSoccer" id="1070" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeSoccer" /></td></tr> 
					 <%-- <form:hidden  path="eightQProfessionalSportsTimeSoccer" id="eightQProfessionalSportsTimeSoccerHidden"/> --%>
					 <tr><td>Tennis</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeTennis" id="1071" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeTennis" /></td> 
					 <%-- <form:hidden  path="eightQProfessionalSportsTimeTennis" id="eightQProfessionalSportsTimeTennisHidden"/> --%>
					</tr>
					 <tr><td>WWE</td>
					 <td><form:hidden  path="eightQProfessionalSportsTimeWWE" id="1072" class="single-slider" style="display: none;" name="eightQueProfessionalSportsTimeWWE" /></td> 
					<%-- <form:hidden  path="eightQProfessionalSportsTimeWWE" id="eightQProfessionalSportsTimeWWEHidden"/> --%>
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
					onclick="javascript:moveBackInterestSurvey(this,['Motorsports Interest']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_9" id="Question8_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'eightQProfessionalSportsTimeExtremeSports',['Social Media']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question8 Ending -->
		<div class="clearfix topMargn_5" id="Question9" style="display: none;">
			<div class="">
				<p>9. What social media channels do you use?
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="ninthQSocialMediaBlogs & Forums" path="ninthQSocialMedia" class="" value="1075"/></span>	Blogs & Forums</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="ninthQSocialMedia" name="ninthQSocialMediaFacebook" value="1076"/></span>	Facebook</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="ninthQSocialMediaFlickr" path="ninthQSocialMedia" class="" value="1077"/></span>	Flickr</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="ninthQSocialMedia" name="ninthQSocialMediaGoogle+" value="1078"/></span>	Google+</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="ninthQSocialMediaInstagram" path="ninthQSocialMedia" class="" value="1079"/></span>	Instagram</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="ninthQSocialMedia" name="ninthQSocialMediaLinkedIn" value="1080"/></span>	LinkedIn</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="ninthQSocialMediaPinterest" path="ninthQSocialMedia" class="" value="1081"/></span>	Pinterest</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="ninthQSocialMedia" name="ninthQSocialMediaSnapchat" value="1082"/></span>	Snapchat</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox 
								path="ninthQSocialMedia" name="ninthQSocialMediaTumblr" value="1083"/></span>	Tumblr</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="ninthQSocialMediaTwitter" path="ninthQSocialMedia" class="" value="1084"/></span>	Twitter</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="ninthQSocialMedia" name="ninthQSocialMediaVine" value="1085"/></span>	Vine</td>
							</tr>
						</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_8" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Professional Sports Interest']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_10" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'ninthQSocialMedia',['Music Preference']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question9 Ending -->
				<div class="clearfix topMargn_5" id="Question10" style="display: none;">
			<div class="">
				<p>10. What do you typically listen to on the radio?
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="tenthQTypicalRadioAlternative" path="tenthQTypicalRadio" class="" value="1086"/></span>	Alternative</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioBluegrass" value="1087"/></span>	Bluegrass</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="tenthQTypicalRadioChristian/Gospel" path="tenthQTypicalRadio" class="" value="1088"/></span>	Christian/Gospel </td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioClassic Rock" value="1089"/></span>	Classic Rock</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="tenthQTypicalRadioClassical" path="tenthQTypicalRadio" class="" value="1090"/></span>	Classical</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioCountry" value="1091"/></span>	Country</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="tenthQTypicalRadioDance/Electronic" path="tenthQTypicalRadio" class="" value="1092"/></span>	Dance/Electronic</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioHeavy Metal" value="1093"/></span>	Heavy Metal</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioHip Hop" value="1094"/></span>	Hip Hop</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="tenthQTypicalRadioJazz/Blues" path="tenthQTypicalRadio" class="" value="1095"/></span>	Jazz/Blues</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioNews" value="1096"/></span>	News</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioPop" value="1097"/></span>	Pop</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioRap" value="1098"/></span>	Rap</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioR&B" value="1099"/></span>	R&B</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioRock" value="1100"/></span>	Rock</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioSports Talk" value="1101"/></span>	Sports Talk</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioTalk Radio" value="1102"/></span>	Talk Radio</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="tenthQTypicalRadio" name="tenthQTypicalRadioDon't Listen to Radio" value="1103"/></span>	Don't Listen to Radio </td>
							</tr>
						</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_9" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Social Media']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_11" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'tenthQTypicalRadio',['Internet Usage']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question10 Ending -->
				<div class="clearfix topMargn_5" id="Question11" style="display: none;">
			<div class="">
				<p>11. What do you use the internet for?
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="eleventhQInternetUsageCommunication(Email,Skype,Yahoo Messenger,etc.)" path="eleventhQInternetUsage" class="" value="1104"/></span>	Communication (Email,Skype,Yahoo Messenger,etc.)</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="eleventhQInternetUsage" name="eleventhQInternetUsageEntertainment(watch videos,download music/movies,etc.)" value="1105"/></span>	Entertainment (watch videos,download music/movies,etc.)</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="eleventhQInternetUsageGaming" path="eleventhQInternetUsage" class="" value="1106"/></span>	Gaming</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="eleventhQInternetUsage" name="eleventhQInternetUsageInformation/Trouble shooting" value="1107"/></span>	"How to" Information/Trouble shooting</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="eleventhQInternetUsageNews(local,national,worldwide)" path="eleventhQInternetUsage" class="" value="1108"/></span>	News (local,national,worldwide)</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="eleventhQInternetUsage" name="eleventhQInternetUsageOnline Education" value="1109"/></span>	Online Education</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="eleventhQInternetUsageParts look-up" path="eleventhQInternetUsage" class="" value="1110"/></span>	Parts look-up</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="eleventhQInternetUsage" name="eleventhQInternetUsageResearch" value="1111"/></span>	Research</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="eleventhQInternetUsage" name="eleventhQInternetUsageShopping" value="1112"/></span>	Shopping Online</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="eleventhQInternetUsageSocial" path="eleventhQInternetUsage" class="" value="1113"/></span>	Social Networking (Facebook,Instagram,Twitter,LinkedIn...etc)</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="eleventhQInternetUsage" name="eleventhQInternetUsageSports" value="1114"/></span>	Sports Updates</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="eleventhQInternetUsage" name="eleventhQInternetUsageTechnical" value="1115"/></span>	Technical Training</td>
							</tr>
						</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_10" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Music Preference']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_12" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'eleventhQInternetUsage',['Learning Preference']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question11 Ending -->
				<div class="clearfix topMargn_5" id="Question12" style="display: none;">
			<div class="">
				<p>12. What is your preferred method for learning new things for your job?
</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
					<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="twelthQPreferredLearningMethodHand-on" path="twelthQPreferredLearningMethod"  class="" value="1119"/></span>	Hand-on practice</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								 path="twelthQPreferredLearningMethod" name="twelthQPreferredLearningMethodListening" class=""  value="1117"/></span>	Listening</td>
							</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="twelthQPreferredLearningMethodReading"  path="twelthQPreferredLearningMethod" class="" value="1116"/></span>	Reading</td>
							</tr>
						
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="twelthQPreferredLearningMethodWatching" path="twelthQPreferredLearningMethod"  class="" value="1118"/></span>	Watching</td>
						</tr>
						
							</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_11" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Internet Usage']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_13" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'twelthQPreferredLearningMethod',['Knowledge Preference']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question12 Ending -->
				<div class="clearfix topMargn_5" id="Question13" style="display: none;">
			<div class="">
				<p>13. How do you stay up to speed on new technology in your industry?
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUptechnician" class="" value="1123"/></span>	Ask another technician</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox 
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpAttendtradeshows" class="" value="1128"/></span>	Attend tradeshows,conventions</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpAttendtraining" class=""  value="1127"/></span>		Attend training or clinics</td>
						</tr>
							<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpChatrooms" class="" value="1122"/></span>	Industry forums/Chat rooms</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpblogs" class="" value="1120"/></span>	Read blogs</td>
						</tr>
					
					
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpreadreviews" class="" value="1124"/></span>	Read product reviews</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpreadmagazines" class="" value="1125"/></span>	Read trade magazines</td>
						</tr>
							<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpResearch" class="" value="1121"/></span>	Research manufacturer websites</td>
						</tr>
					
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								path="thirteenthQNewTechSpeedUp" name="thirteenthQNewTechSpeedUpreadnewsletter" class="" value="1126"/></span>	Subscribe and read industry newsletter</td>

						</tr>
						
					
							</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_12" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Learning Preference']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_14" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'thirteenthQNewTechSpeedUp',['Email Usage']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question13 Ending -->
						<div class="clearfix topMargn_5" id="Question14" style="display: none;">
			<div class="">
				<p>14. How often do you check your email?
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select One</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
							<span class="text-center"><form:radiobutton
								name="fourtheenthQEmailCheckCoupleday"  path="fourtheenthQEmailCheck"  class="" value="1129"/></span>	Couple times a day</td>
						</tr>
					
						<tr>
							<td>
							<span class="text-center"><form:radiobutton
								name="fourtheenthQEmailCheckCoupleweek"  path="fourtheenthQEmailCheck"  class=""  value="1131"/></span>	Couple times a week</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:radiobutton
								name="fourtheenthQEmailCheckCouplemonth"  path="fourtheenthQEmailCheck"  class="" value="1132"/></span>	Couple times a month</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:radiobutton
								name="fourtheenthQEmailCheckCoupleyear"  path="fourtheenthQEmailCheck"  class="" value="1133"/></span>	Couple times a year</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:radiobutton
								name="fourtheenthQEmailCheckrarely"  path="fourtheenthQEmailCheck"  class="" value="1134"/></span>	I rarely check my mail</td>
						</tr>
						
						<tr>
							<td>
							<span class="text-center"><form:radiobutton
								name="fourtheenthQEmailCheckOnceday"  path="fourtheenthQEmailCheck"  class="" value="1130"/></span>	Once a day</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:radiobutton
								name="fourtheenthQEmailCheckdont"  path="fourtheenthQEmailCheck"  class="" value="1135"/></span>	I don't have email</td>
						</tr>
								</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_13" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Knowledge Preference']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_15" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'fourtheenthQEmailCheck',['Job Likes']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question14 Ending -->
						<div class="clearfix topMargn_5" id="Question15" style="display: none;">
			<div class="">
				<p>15.  What do you like best about your job?

				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
					<tr>
							<td><span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobDoingdifferent" path="fifteenthQBestAboutJob" class="" value="1138"/></span>	Doing something different everyday</td>
								</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobEnjoycars" path="fifteenthQBestAboutJob" class="" value="1139"/></span>	Enjoy working on cars</td>
						</tr>
						<tr>
							<td><span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobFinancial" path="fifteenthQBestAboutJob" class="" value="1140"/></span>	Financial compensation, hours, vacation, benefits</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobenvironment" path="fifteenthQBestAboutJob" class="" value="1142"/></span>	Great work environment and like the people I work with</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobHelping" path="fifteenthQBestAboutJob" class="" value="1136"/></span>	Helping people</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobopportunities" path="fifteenthQBestAboutJob" class="" value="1141"/></span>	Lots of opportunities in the industry</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobSolving" path="fifteenthQBestAboutJob" class="" value="1137"/></span>	Solving mechanical problems</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="fifteenthQBestAboutJobStable" path="fifteenthQBestAboutJob" class="" value="1143"/></span>	Stable career</td>
						</tr>
							</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_14" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Email Usage']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_16" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'fifteenthQBestAboutJob',['Job Dislikes']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question15 Ending -->
						<div class="clearfix topMargn_5" id="Question16" style="display: none;">
			<div class="">
				<p>16. What would you consider to be the least enjoyable aspects of your job? 
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1144"/></span>	Dealing with customers and compliants</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1145"/></span>	Evolving complexity of the vehicle systems</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1146"/></span>	Lack of technical information</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1147"/></span>	Little opportunity to get promoted or a raise</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1148"/></span>	Long Hours</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1149"/></span>	No job security</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1150"/></span>	Personality conflicts in the shop</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q16ChkBox" path="sixteenthQLeastAboutJob"  class="" value="1151"/></span>	Physically demanding at times</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox value="1152"
								 path="sixteenthQLeastAboutJob" id="Q16SingleChk" class="" /></span>   Can't think of anything that is not enjoyable</td>
						</tr>
							</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_15" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Job Likes']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_17" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'sixteenthQLeastAboutJob',['Career Characteristics']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question16 Ending -->
						<div class="clearfix topMargn_5" id="Question17" style="display: none;">
			<div class="">
				<p>17. Please rank the characteristics below as they relate to being successful as a professional technician:
				</p>
			</div>
			<div class="clearfix"><div class="col-lg-12">
			<div class="col-lg-offset-3 col-lg-6 text-center">
			<span class="surveyLabelSliderOne">Not Important</span>
			<span class="surveyLabelSliderTwo">Somewhat Important</span>
			<span class="surveyLabelSliderThree">Very Important</span>
			</div>
			</div>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn surveyHobbiesInt">
				<table border="0" class="table ordeDetailsTable">
					<thead>
					</thead>
					<tbody>
					 <tr><td>Business savvy/sense to run a shop</td>
					 <td><form:hidden  path="seventeethQProfTechCharRankBusinesssavvysensetorunashop"  id="1157" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankBusinesssavvysensetorunashop" /></td></tr>
					 <%-- <form:hidden  path="seventeethQProfTechCharRankBusinesssavvysensetorunashop" id="seventeethQProfTechCharRankBusinesssavvysensetorunashopHidden"/> --%>
					<tr> <td>Connecting with technicians online</td>
					 <td><form:hidden  path="seventeethQProfTechCharRankConnectingwithtechniciansonline"  id="1155" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankConnectingwithtechniciansonline" /></td> </tr>
					<%-- <form:hidden  path="seventeethQProfTechCharRankConnectingwithtechniciansonline" id="seventeethQProfTechCharRankConnectingwithtechniciansonlineHidden"/> --%>
					<tr>
					 <td>Continuing education</td>
					  <td><form:hidden  path="seventeethQProfTechCharRankContinuingeducation"  id="1153" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankContinuingeducation" /></td></tr>
					  <%-- <form:hidden  path="seventeethQProfTechCharRankContinuingeducation" id="seventeethQProfTechCharRankContinuingeducationHidden"/> --%>
					  <tr><td>Natural mechanical ability</td>
					 <td><form:hidden  path="seventeethQProfTechCharRankNaturalmechanicalability"  id="1159" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankNaturalmechanicalability" /></td>
					 <%-- <form:hidden  path="seventeethQProfTechCharRankNaturalmechanicalability" id="seventeethQProfTechCharRankNaturalmechanicalabilityHidden"/> --%>
					</tr>
					<tr> <td>Networking with technicians</td>
					 <td><form:hidden  path="seventeethQProfTechCharRankNetworkingwithtechnicians"  id="1156" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankNetworkingwithtechnicians" /></td> </tr>
					<%--  <form:hidden  path="seventeethQProfTechCharRankNetworkingwithtechnicians" id="seventeethQProfTechCharRankNetworkingwithtechniciansHidden"/> --%>
					 <tr><td>Passion for cars and trucks</td>
					 <td><form:hidden  path="seventeethQProfTechCharRankPassionforcarsandtrucks"  id="1154" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankPassionforcarsandtrucks" /></td></tr> 
					<%-- <form:hidden  path="seventeethQProfTechCharRankPassionforcarsandtrucks" id="seventeethQProfTechCharRankPassionforcarsandtrucksHidden"/> --%> 
					 <tr><td>Staying knowledgeable in new technologies</td>
					 <td><form:hidden  path="seventeethQProfTechCharRankStayingknowledgeableinnewtechnologies" id="1158" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankStayingknowledgeableinnewtechnologies" /></td></tr>
					<%--  <form:hidden  path="seventeethQProfTechCharRankStayingknowledgeableinnewtechnologies" id="seventeethQProfTechCharRankStayingknowledgeableinnewtechnologiesHidden"/> --%> 
					<tr><td>Strong work ethic</td>
					 <td><form:hidden  path="seventeethQProfTechCharRankStrongworkethic" id="1160" class="single-slider" style="display: none;" name="seventeethQueProfTechCharRankStrongworkethic" /></td>
					 <%-- <form:hidden  path="seventeethQProfTechCharRankStrongworkethic" id="seventeethQProfTechCharRankStrongworkethicHidden"/> --%>
					</tr>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_16" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Job Dislikes']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_18" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'seventeethQProfTechCharRankPassionforcarsandtrucks',['Electronic Devices']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question17 Ending -->
						<div class="clearfix topMargn_5" id="Question18" style="display: none;">
			<div class="">
				<p>18. What devices to you own?
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class=""  value="1170"/></span>	Camera: Digital or SLR</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1171"/></span>	Go Pro or action camera</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1166"/></span>	HD TV</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1164"/></span>	ipad</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1168"/></span>	Kindle fire or similar</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1161"/></span>	Laptop/Computer</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class=""  value="1167"/></span>	Nintendo Wii</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1169"/></span>	Printer: Injet or laser</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1163"/></span>	Smartphone</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1162"/></span>	Tablet</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								id="Q18ChkBox" path="eighteenthQDevicesOwn"  class="" value="1165"/></span>	Xbox 360</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox value="1172"
								id="Q18SingleChk" path="eighteenthQDevicesOwn"  class="" /></span>	None of the above</td>
						</tr>
							</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_17" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Career Characteristics']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="button" value="Ques_19" id="Question6_Next"
					onclick="javascript:moveIntandHobbiesSurvey(this,'eighteenthQDevicesOwn',['Professional Certificates']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question18 Ending -->
						<div class="clearfix topMargn_5" id="Question19" style="display: none;">
			<div class="">
				<p>19. Do you have any professional certifications?
				</p>
			</div>
			<div class="col-lg-3"></div>

			<div class="col-lg-6 table-responsive userTable rewardsEarn">
				<table border="0" class="table ordeDetailsTable">
					<thead>
						<tr>
							<th>Select all that apply</th>
							
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="nineteethQProfCertificatesASE" id="Q19ChkBox" path="nineteethQProfCertificates"  class=""  value="1173"/></span>	ASE Certified</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="nineteethQProfCertificatesNATEF"  id="Q19ChkBox" path="nineteethQProfCertificates"  class="" value="1174"/></span>	NATEF Certified</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="nineteethQProfCertificatesOther"  id="Q19ChkBox" path="nineteethQProfCertificates"  class="" value="1175"/></span>	Other</td>
						</tr>
						<tr>
							<td>
							<span class="text-center"><form:checkbox
								name="nineteethQProfCertificatesNone"   id="Q19NoneChk" path="nineteethQProfCertificates"  class="" value="1177"/></span>	None</td>
						</tr>
						</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
			<div class="clearfix">
				<div class="col-lg-12">
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-left topMargn_20"
						type="button" value="Ques_18" id="Question6_Back"
					onclick="javascript:moveBackInterestSurvey(this,['Electronic Devices']);">&lt; Back</button>
					<button
						class="btn btn-sm btn-fmDefault text-uppercase pull-right topMargn_20"
						type="submit" value="Ques_20" id="InterestAndHobbiesSubmit"
					onclick="return moveIntandHobbiesSurvey(this,'nineteethQProfCertificates',[' ']);">Next &gt;</button>
				</div>
			</div>
		</div>
		<!-- Question19 Ending -->
</form:form>
</div>


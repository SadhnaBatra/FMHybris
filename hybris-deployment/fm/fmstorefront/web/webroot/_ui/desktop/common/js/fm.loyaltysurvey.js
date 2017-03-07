ACC.loyaltysurvey = {

              currentPath: window.location.href,
              pathName: "",
              currentCurrency: "USD",
              ajaxUrl : "USD/Survey/",
              $BeginBrandSurvey: $('#BrandSurveyBegin_btn'),
              $BeginPartsBuySurvey: $('#PartsBuySurveyBegin_btn'),
              $BeginIntAndHobbiesSurvey: $('#IntandHobbiesSurveyBegin_btn'),
             
                           
               options: "",
               url: "",
               setCurrentPath: function() {
                      try{
                             if(ACC.loyaltysurvey.currentPath.indexOf("/USD") != -1){
                                    ACC.loyaltysurvey.pathName = ACC.loyaltysurvey.currentPath.substring(0, ACC.loyaltysurvey.currentPath.lastIndexOf("/USD") + 1);
                             }else if(ACC.loyaltysurvey.currentPath.indexOf("?site") != -1){

                                    ACC.loyaltysurvey.pathName = ACC.loyaltysurvey.currentPath.substring(0, ACC.loyaltysurvey.currentPath.lastIndexOf("/?site") + 1)+ACC.loyaltysurvey.currentPath.substring(ACC.loyaltysurvey.currentPath.lastIndexOf("site=")+5,ACC.loyaltysurvey.currentPath.length)+"/en/";
                             }else{


                                    ACC.loyaltysurvey.pathName = window.location.href+"loyalty/en/";
                             }
                      }catch(e){
                             alert(e);
                      }
               },
               bindAll: function() {
                   with (ACC.loyaltysurvey) {
                          bindToBeginBrandSurvey($BeginBrandSurvey);
                          bindToBeginPartsBuySurvey($BeginPartsBuySurvey);
                          bindToBeginIntAndHobbiesSurvey($BeginIntAndHobbiesSurvey);
                   }
            },
            
            bindToBeginBrandSurvey: function(BrandSurveyBegin_btn) {
            	BrandSurveyBegin_btn.click(function(event) {
            		
            		var breadCurumbArray = this.value.split(",");
            		chnageBreadCrumb(breadCurumbArray);
            		
            		$("#BrandQuestionsBlock").show();
            		$("#BrandSurveyBegin").hide();
            		
        		});
         },
         
         bindToBeginPartsBuySurvey: function(PartsBuySurveyBegin_btn) {
        	 PartsBuySurveyBegin_btn.click(function(event) {
        		 var breadCurumbArray = this.value.split(",");
         		chnageBreadCrumb(breadCurumbArray);
         		$("#BrandQuestionsBlock").show();
         		$("#BrandSurveyBegin").hide();
         		
     		});
      },
      
      bindToBeginIntAndHobbiesSurvey: function(IntandHobbiesSurveyBegin_btn) {
    	  IntandHobbiesSurveyBegin_btn.click(function(event) {
    		  var breadCurumbArray = this.value.split(",");
      		chnageBreadCrumb(breadCurumbArray);
      		$("#BrandQuestionsBlock").show();
      		$("#BrandSurveyBegin").hide();
      		
  		});
   },
         
        
                           
 
}; 

function removeTextBoxValue(otTextName){
	//$("#"+otTextName).val('');
	$('input:text[name='+otTextName+']').val('');
}

function removeRadioSelection(radioName){
	$("input:radio[name='"+radioName+"']").prop('checked', false);
}

function chnageBreadCrumb(breadCurumbArray){
	/* var breadcrumbVal = "";
	  for (var i = 0; i < breadCurumbArray.length; i++){
	      if(breadCurumbArray[i] != "" && breadCurumbArray[i] != null ){
	    	  breadcrumbVal = breadcrumbVal + '<span class="fa fa-angle-right "></span>&nbsp;'+breadCurumbArray[i]+"&nbsp;";
	    	  //'&nbsp;<span class="fa fa-angle-right "></span>&nbsp;' +"<li>"+ breadCurumbArray[i] +"</li>";
	      }
	  }*/
	 document.getElementById("surveyBreadCrumb").innerHTML = '<span class="fa fa-angle-right "></span>&nbsp;' + 'Question '+ breadCurumbArray;
}


function moveBrandSurvey(obj, primary, secondary, otPrimary, otSecondary,breadCurumbArray) {
	
	$(window).scrollTop(90);
	var btnValue = obj.value;
	var questionNo = btnValue.substr(btnValue.lastIndexOf("_") + 1,btnValue.length);
	var currQuesNo = questionNo -1;

	if(breadCurumbArray=''){
		$('input:submit').attr("disabled",true);
	}
	//var otPrimaryVal = $('input:text[name='+otPrimary+']').val();//$("#" + otPrimary).val();
	//var otSecondaryVal = $('input:text[name='+otSecondary+']').val();//$("#" + otSecondary).val();
	if(otPrimary!=''){
	var otPrimaryVal = $('input:text[name='+otPrimary+']').val();
	}//$("#" + otPrimary).val();
	if(otSecondary!=''){
	var otSecondaryVal = $('input:text[name='+otSecondary+']').val();//$("#" + otSecondary).val();
	}
	

	var primaryCheck = $("input:radio[name='" + primary + "']").is(":checked");
	var secondaryCheck = $("input:radio[name='" + secondary + "']").is(":checked");
	
	var primaryID= $("input[name='" + primary + "']:checked").attr('id');
	var secondaryID= $("input[name='" + secondary + "']:checked").attr('id');

	
	if(currQuesNo == 19){
		var validationFlag = false;
		for(i=555; i<=563;i++){
			//alert("i val "+i+" input val "+$("#0" + i).val());
			var sliderInitValue = "0"+i+":0";
			var sliderNaNValue =  "0"+i+":NaN";
			//alert("sliderInitValue "+sliderInitValue);
			if($("#0" + i).val() != sliderInitValue && $("#0" + i).val() != sliderNaNValue){
				validationFlag = true;
				break;
			}
		}
		if(validationFlag){
			$("#BrandSurveyQ19andQ20Error").hide();
			moveToNextORBack(questionNo);
			chnageBreadCrumb(questionNo);
			return true;
		}else{
			$("#BrandSurveyQ19andQ20Error").show();
			return false;
		}
	}else if(currQuesNo == 20){

		var validationFlag = false;
		for(i=564 ; i<=572;i++){
			//alert("i val "+i+" input val "+$("#0" + i).val());
			var sliderInitValue = "0"+i+":0";
			var sliderNaNValue =  "0"+i+":NaN";
			//alert("sliderInitValue "+sliderInitValue);
			if($("#0" + i).val() != sliderInitValue && $("#0" + i).val() != sliderNaNValue){
				validationFlag = true;
				break;
			}
		}
		if(validationFlag){
			$("#BrandSurveyQ19andQ20Error").hide();
			return true;
		}else{
			$("#BrandSurveyQ19andQ20Error").show();
			return false;
		}
	}else if((primaryID == secondaryID) && !(jQuery.type(primaryID) === "undefined" || jQuery.type(secondaryID) === "undefined") ){		
		$("#BrandSurveyError1").hide();
		$("#BrandSurveyError2").show();
		return false;
	}else if((otPrimaryVal != "" && otSecondaryVal != "")
			|| (primaryID == secondaryID
					&& !jQuery.type(primaryID) === "undefined" 
					&& !jQuery.type(secondaryID) === "undefined")) {
		$("#BrandSurveyError1").hide();
		$("#BrandSurveyError2").hide();
		moveToNextORBack(questionNo);
		chnageBreadCrumb(questionNo);
	}else if ((primaryCheck || otPrimaryVal != "") && (secondaryCheck || otSecondaryVal != "") && (primaryID != secondaryID)) {
		$("#BrandSurveyError1").hide();
		$("#BrandSurveyError2").hide();
		moveToNextORBack(questionNo);
		chnageBreadCrumb(questionNo);
	} else {
		$("#BrandSurveyError1").show();
		$("#BrandSurveyError2").hide();
		return false;
	}
	
}
function moveBack(obj,breadCurumbArray){
	var btnValue = obj.value;
	var questionNo = btnValue.substr(btnValue.lastIndexOf("_") + 1,
			btnValue.length);

	$("#BrandSurveyError2").hide();
	$("#BrandSurveyError1").hide();
	$("#PartsBuySurveyError1").hide();
	$("#PartsBuySurveyQ2Error").hide();
	$("#PartsBuySurveyQ3to14Error").hide();
	$("#PartsBuySurveyQ2Error2").hide();
	$("#BrandSurveyQ19andQ20Error").hide()
	$("#PartsSurveyOtherTxtError").hide();
	chnageBreadCrumb(questionNo);
	moveToNextORBack(questionNo);
}

function moveBackInterestSurvey(obj,breadCurumbArray){
	var btnValue = obj.value;
	var questionNo = btnValue.substr(btnValue.lastIndexOf("_") + 1,
			btnValue.length);
	document.getElementById("errormsgSurvey").innerHTML=" ";	
	chnageBreadCrumb(questionNo);
	moveToNextORBack(questionNo);
}
function moveIntandHobbiesSurvey(obj,radioGroupName,breadCurumbArray){
	

	$(window).scrollTop(90);
	var btnValue = obj.value;
	var questionNo = btnValue.substr(btnValue.lastIndexOf("_") + 1,
			btnValue.length);
	var currQuesNo = questionNo -1;
	
	if(radioGroupName != "" && radioGroupName != null){
		
		if(currQuesNo < 6 || currQuesNo == 14)
	    {
		if ($("input:radio[name='" + radioGroupName + "']").is(":checked")){
			moveToNextORBack(questionNo);
			document.getElementById("errormsgSurvey").innerHTML=" ";
			chnageBreadCrumb(questionNo);
			return true;
		}else{
			if(currQuesNo == 1)
				{
			document.getElementById("errormsgSurvey").innerHTML="Please select an age range.";	
				}
			if(currQuesNo == 2)
			{
		document.getElementById("errormsgSurvey").innerHTML="Please select a month.";	
			}
			if(currQuesNo == 3)
			{
		document.getElementById("errormsgSurvey").innerHTML="Please select a gender.";	
			}
			if(currQuesNo == 4)
			{
		document.getElementById("errormsgSurvey").innerHTML="Please select ethnicity.";	
			}
			
			if(currQuesNo == 5)
			{
		document.getElementById("errormsgSurvey").innerHTML="Please select a range.";	
			}
			
			if(currQuesNo == 14)
			{
		document.getElementById("errormsgSurvey").innerHTML="Please select at least one.";	
			}
			
			
			return false;
		 }
		}
		else if( currQuesNo == 9 || currQuesNo == 10 || currQuesNo == 11 || currQuesNo == 12 || currQuesNo == 13 || currQuesNo == 15 || currQuesNo == 16 || currQuesNo == 18 || currQuesNo == 19)
			 {
		var length = $("input[name='" + radioGroupName + "']:checked").length;
		//alert("length"+length);
		
			if($("input[name='" + radioGroupName + "']:checked").length > 0)
			{
				document.getElementById("errormsgSurvey").innerHTML=" ";
				var count=0;
				var valCheck1;
		
				if(currQuesNo == 16 || currQuesNo == 18 || currQuesNo == 19)				
{
					
					if(currQuesNo == 16)
						{
						valCheck1='1152';
						}
					else if(currQuesNo == 18)
						{
						valCheck1='1172';
						}
				else if(currQuesNo == 19)
					{
					valCheck1='1177';
					}
					
				$("input[name='" + radioGroupName + "']:checked").each(function() {
					  
					   if(this.value == valCheck1)
						   {
					count++;
						   }
					});
				
				if(count>0)
					{
					$("input[name='" + radioGroupName + "']:checked").each(function() {
						
						   if(this.value != valCheck1)
							   {
							   $(this).prop('checked', false);
							   }
						});
					}
			
				}
				
				moveToNextORBack(questionNo);
				chnageBreadCrumb(questionNo);
				
				return true;
			}else{
				
				if(currQuesNo == 9)
				{
			document.getElementById("errormsgSurvey").innerHTML="Please select at least one channel.";	
				}
				if(currQuesNo == 10 || currQuesNo == 11 || currQuesNo == 12 || currQuesNo == 13 || currQuesNo == 15 || currQuesNo == 16 || currQuesNo == 18 ||currQuesNo == 19)
				{
			document.getElementById("errormsgSurvey").innerHTML="Please select at least one.";	
				}

				return false;
			 }
			}
		else if(currQuesNo == 7)
			{
			
			var idVal =$('input:hidden[name=seventhQMotorSportsTimeNascar]').val();
	
			var idVal1 =$('input:hidden[name=seventhQMotorSportsTimeFormula1]').val();
	
			var idVal2 =$('input:hidden[name=seventhQMotorSportsTimeDragRacing]').val();
		
			
			var idVal3 =$('input:hidden[name=seventhQMotorSportsTimeMotocross]').val();

			var idVal4 =$('input:hidden[name=seventhQMotorSportsTimeSupercross]').val();

			var idVal5 =$('input:hidden[name="seventhQMotorSportsTimeDrifting"]').val();

			var idVal6 =$('input:hidden[name="seventhQMotorSportsTimeRally"]').val();
	
			if(idVal.indexOf(":0") > 0 && idVal1.indexOf(":0") > 0 && idVal2.indexOf(":0") > 0 && idVal3.indexOf(":0") > 0 && idVal4.indexOf(":0") > 0 && idVal5.indexOf(":0") > 0 && idVal6.indexOf(":0") > 0)
				{
				document.getElementById("errormsgSurvey").innerHTML="Please move the slider to indicate your level of interest for each sport.";
				return false;
				}
			else
				{
				moveToNextORBack(questionNo);
				document.getElementById("errormsgSurvey").innerHTML=" ";
				chnageBreadCrumb(questionNo);
				return true;
				}
			
			}
		
		else if(currQuesNo == 8)
		{
		
		var idVal =$('input:hidden[name=eightQProfessionalSportsTimeFootball]').val();
	
		var idVal1 =$('input:hidden[name=eightQProfessionalSportsTimeBasketball]').val();
	
		
		var idVal2 =$('input:hidden[name=eightQProfessionalSportsTimeBaseball]').val();
	
		var idVal3 =$('input:hidden[name=eightQProfessionalSportsTimeHockey]').val();

		var idVal4 =$('input:hidden[name=eightQProfessionalSportsTimeMMA]').val();
	
		var idVal5 =$('input:hidden[name="eightQProfessionalSportsTimeSoccer"]').val();

		var idVal6 =$('input:hidden[name="eightQProfessionalSportsTimeTennis"]').val();

		var idVal7 =$('input:hidden[name="eightQProfessionalSportsTimeWWE"]').val();

		var idVal8 =$('input:hidden[name="eightQProfessionalSportsTimeFantasySports"]').val();

		var idVal9 =$('input:hidden[name="eightQProfessionalSportsTimeExtremeSports"]').val();

		
		if(idVal.indexOf(":0") > 0 && idVal1.indexOf(":0") > 0 && idVal2.indexOf(":0") > 0 && idVal3.indexOf(":0") > 0 && idVal4.indexOf(":0") > 0 && idVal5.indexOf(":0") > 0 && idVal6.indexOf(":0") > 0 && idVal7.indexOf(":0") > 0 && idVal8.indexOf(":0") > 0 && idVal9.indexOf(":0") > 0)
		{
		document.getElementById("errormsgSurvey").innerHTML="Please move the slider to indicate your level of interest for each sport.";
		return false;
		}
	else
		{
		moveToNextORBack(questionNo);
		document.getElementById("errormsgSurvey").innerHTML=" ";
		chnageBreadCrumb(questionNo);
		return true;
		}
		
		}
		else if(currQuesNo == 17)
			{

			
			var idVal =$('input:hidden[name=seventeethQProfTechCharRankContinuingeducation]').val();
			

			var idVal1 =$('input:hidden[name=seventeethQProfTechCharRankPassionforcarsandtrucks]').val();

			var idVal2 =$('input:hidden[name=seventeethQProfTechCharRankConnectingwithtechniciansonline]').val();

			var idVal3 =$('input:hidden[name=seventeethQProfTechCharRankNetworkingwithtechnicians]').val();

			var idVal4 =$('input:hidden[name=seventeethQProfTechCharRankBusinesssavvysensetorunashop]').val();

			var idVal5 =$('input:hidden[name="seventeethQProfTechCharRankStayingknowledgeableinnewtechnologies"]').val();

			var idVal6 =$('input:hidden[name="seventeethQProfTechCharRankNaturalmechanicalability"]').val();

			var idVal7 =$('input:hidden[name="seventeethQProfTechCharRankStrongworkethic"]').val();

			
			if(idVal.indexOf(":0") > 0 && idVal1.indexOf(":0") > 0 && idVal2.indexOf(":0") > 0 && idVal3.indexOf(":0") > 0 && idVal4.indexOf(":0") > 0 && idVal5.indexOf(":0") > 0 && idVal6.indexOf(":0") > 0 && idVal7.indexOf(":0") > 0)
			{
			document.getElementById("errormsgSurvey").innerHTML="Please move the slider to rank each characteristic.";
			return false;
			}
		else
			{
			moveToNextORBack(questionNo);
			document.getElementById("errormsgSurvey").innerHTML=" ";
			chnageBreadCrumb(questionNo);
			return true;
			}
			
			}
		else if(currQuesNo == 6)
		{
			var length = $("input[name='" + radioGroupName + "']:checked").length;
			
				if($("input[name='" + radioGroupName + "']:checked").length > 0)
				{
		
					moveToNextORBack(questionNo);
					document.getElementById("errormsgSurvey").innerHTML=" ";
					chnageBreadCrumb(questionNo);
					return true;
				}
				else if($('input:text[name=test]').val() != "")
				{

					var idVal =$('input:text[name=test]').attr('id');
					alert("idval"+idVal);
					$('[id=Q6RA]').val(idVal);
					document.getElementById("errormsgSurvey").innerHTML=" ";	
					moveToNextORBack(questionNo);
					chnageBreadCrumb(questionNo);
					return true;
				}else{
					document.getElementById("errormsgSurvey").innerHTML="Please select at least one activity.";	
					return false;
				}
		}
		
		}
	
	
}
/*function disableFormSubmit()
{
	$('form#InterestAndHobbiesQuestionsForm').submit(function(e){
		   e.preventDefault();
		   //alert('prevent submit');             
		});	
}*/
function movePartsBuySurvey(obj,breadCurumbArray) {
	$(window).scrollTop(90);
	var btnValue = obj.value;
	var questionNo = btnValue.substr(btnValue.lastIndexOf("_") + 1,
			btnValue.length);
	var currQuesNo = questionNo -1;
	
	
	
	var totalcheck = false;
	var NanCheck = false;
	var rankCount =0;
	var total=true;
if(breadCurumbArray=''){
		$('input:submit').attr("disabled",true);
	}
	
	if(currQuesNo == 1){
		var boxValueCount =0
		$('[name^="total"]').each(function() {

			if (!isNaN(this.value) && this.value.length != 0) {
				if (this.value == 100) {
				var currID = this.id;
				boxValueCount++;
				totalcheck =true;
				} else {
					totalcheck = false;
                     total=false;
				}
			} else {
				NanCheck = true;
			}
		});

		if (totalcheck && total && (boxValueCount==10)) {
			chnageBreadCrumb(questionNo);
			moveToNextORBack(questionNo);
			return true;
		} else if (NanCheck || !totalcheck ||(!boxValueCount==10)) {
			$("#PartsBuySurveyError1").show();
			return false;
		}else if (NanCheck || !(totalcheck&&total) || (!boxValueCount==10)) {

			$("#PartsBuySurveyError1").show();
			return false;
		}

	}else if(currQuesNo == 2){
		for (i = 1; i <= 7; i++) {
			if($("#rank"+i).val() != "" && $("#rank"+i).val() != null){
				rankCount++;
			}
		}
		if(rankCount == 3){
			$("#PartsBuySurveyQ2Error").hide();
			chnageBreadCrumb(questionNo);
			moveToNextORBack(questionNo);
			return true;
		}else{
			$("#PartsBuySurveyQ2Error").show();
			return false;
		}
	}else if(currQuesNo >= 3 && currQuesNo <= 14){
		
		var priRadioName = "Q"+currQuesNo+"Primary";
		var secRadioName = "Q"+currQuesNo+"Secondary";
		if ($('[name="' + priRadioName + '"]').is(':checked')
				&& $('[name="' + secRadioName + '"]').is(':checked')) {
			var priID = $('input[name='+priRadioName+']:checked').attr('id');
			var secID = $('input[name='+secRadioName+']:checked').attr('id');
			
			if(!(priID === secID)){
				if(priID.indexOf("Other") > -1 && ( $("#"+priID+"txt").val() == "" || $("#"+priID+"txt").val() == null) ){

					$("#PartsSurveyOtherTxtError").show();
					return false;
				}else if(secID.indexOf("Other") > -1 && ($("#"+secID+"txt").val() == "" || $("#"+secID+"txt").val() == null)){
					$("#PartsSurveyOtherTxtError").show();
					return false;
				}

				$("#PartsBuySurveyQ3to14Error").hide();
				$("#PartsSurveyOtherTxtError").hide();
				chnageBreadCrumb(questionNo);
				moveToNextORBack(questionNo);
				return true;
			}else{
				$("#PartsBuySurveyQ3to14Error").show();
				return false;
			}
			
		}else{
			$("#PartsBuySurveyQ3to14Error").show();
			return false;
		}

	}else if(currQuesNo == 15){
		rankCount =0 ;	
		for (i = 1; i <= 6; i++) {
			if($("#Q15rank"+i).val() != "" && $("#Q15rank"+i).val() != null){
				rankCount++;
			}
		}
		if(rankCount == 3){
			$("#PartsBuySurveyQ2Error").hide();
			//moveToNextORBack(questionNo);
			return true;
		}else{
			$("#PartsBuySurveyQ2Error").show();
			return false;
		}
	}
}

function moveToNextORBack(questionNo){
	$("#PartsBuySurveyError1").hide();
	if (questionNo == 0) {
		$("#BrandQuestionsBlock").hide();
		$("#BrandSurveyBegin").show();
	} else {
		
		for (i = 1; i <= 20; i++) {
			$("#Question" + i).hide();
		}
	}
	$("#Question" + questionNo).show();
	/*$(window).scrollTop(80);*/
}

function calculateTotal(groupName,totalDispName,pathName,id){

	var tot = 0;
	var pathValString =id;	
	$('[name^="'+groupName+'"]').each(function(){

		if(!isNaN(this.value)) {
			if(this.value.length!=0){
			 tot += parseFloat(this.value);
			 pathValString +=":"+parseFloat(this.value);
			}
			if(this.value.length==0){
				tot += parseFloat(0);
				 pathValString +=":"+" ";
			}
        }
    });
	
	$('[id='+totalDispName+']').val(tot);
	$('[id='+pathName+']').val(pathValString);
	
}
/*function moveInterestAndHobbies(obj) {
	var btnValue = obj.value;
	var questionNo = btnValue.substr(btnValue.lastIndexOf("_") + 1,
			btnValue.length);
	if (questionNo == 0) {
		$("#BrandQuestionsBlock").hide();
		$("#BrandSurveyBegin").show();
	}else {
		
		for (i = 1; i <= 20; i++) {
			$("#Question" + i).hide();
		}
		}
		$("#Question"+questionNo).show();
		var selected = $("#radioDiv input[type='radio']:checked");
	
	}*/

function submitSurveyForm(form_Name) {
	var formElements = window.document.getElementById(form_Name).elements;
	var formElement;
	var brandSurvey = "";
	ACC.loyaltysurvey.Url = ACC.loyaltysurvey.pathName + ACC.loyaltysurvey.ajaxUrl+"updateBrandSurvey"
	for (var i = 0, j = 0; i < formElements.length; i++) {
		formElement = formElements.item(i);
		if (formElement.type === "radio") {
			if (formElements[i].checked) {
				var radioOptionVal = formElements[i].value;
				brandSurvey = brandSurvey + radioOptionVal + ":"
			}
		}
		if (formElement.type === "text"
				&& (formElements[i].value != null && formElements[i].value != "")) {
			
			var OtherText_id = formElements[i].id;
			brandSurvey = brandSurvey + OtherText_id + ":"
		}
	}
	
	document.getElementById("BRAND_SURVEY_FORM").value = brandSurvey;
	
	window.location.href=ACC.loyaltysurvey.Url+"?brandSurvey="+brandSurvey;
	
}



$('#rank1, #rank2, #rank3, #rank4, #rank5, #rank6, #rank7').keypress(function(event){
	var keycode = (event.keyCode ? event.keyCode : event.which);
	if(keycode == 49 || keycode == 50 || keycode == 51){
		var value = keycode == 49 ? 1 : (keycode == 50 ? 2 : 3);
		var currID = this.id;
		var duplicateFlag = false;
		for (i = 1; i <= 7; i++) {
			if($("#rank"+i).val() == value){
				duplicateFlag = true;
			}
		}
		if(!duplicateFlag){
			var name = $("input#"+currID).attr("name");
			$('[id='+name+']').val(name+":"+value);
			$("#PartsBuySurveyQ2Error2").hide();
			return true;
		}else{
			$("#PartsBuySurveyQ2Error2").show();
			return false;
		}
	}else if(keycode == 8 || keycode == 46){
		return true;
	}else{
		return false;
	}
});

$('#Q15rank1, #Q15rank2, #Q15rank3, #Q15rank4, #Q15rank5, #Q15rank6').keypress(function(event){
	var keycode = (event.keyCode ? event.keyCode : event.which);
	if(keycode == 49 || keycode == 50 || keycode == 51){
		var value = keycode == 49 ? 1 : (keycode == 50 ? 2 : 3);
		var currID = this.id;
		var duplicateFlag = false;
		for (i = 1; i <= 7; i++) {
			if($("#Q15rank"+i).val() == value){
				duplicateFlag = true;
			}
		}
		if(!duplicateFlag){
			var name = $("input#"+currID).attr("name");
			$('[id='+name+']').val(name+":"+value);
			$("#PartsBuySurveyQ2Error2").hide();
			return true;
		}else{
			$("#PartsBuySurveyQ2Error2").show();
			return false;
		}
		
	}else if(keycode == 8 || keycode == 46){
		return true;
	}else{
		return false;
	}
});

function blockSpecialChar(e) {
            var k = e.keyCode;
            return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8   || (k >= 48 && k <= 57));
        }

$('#Q16SingleChk').click(function(){
    if (this.checked) {
    	//alert("I'm coming");
    	$("input[id='Q16ChkBox']:checked").each(function(){ 
    		//alert("I'came");//loop through each checkbox
            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
        });         
    }
}) 
$('#Q18SingleChk').click(function(){
    if (this.checked) {
    	//alert("I'm coming");
    	$("input[id='Q18ChkBox']:checked").each(function(){ 
    		//alert("I'came");//loop through each checkbox
            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
        });         
    }
}) 

$('#Q19NoneChk').click(function(){
    if (this.checked) {
    	//alert("I'm coming");
    	$("input[id='Q19ChkBox']:checked").each(function(){ 
    		//alert("I'came");//loop through each checkbox
            this.checked = false; //deselect all checkboxes with class "checkbox1"                       
        });         
    }
}) 

$(document).ready(function() {
       ACC.loyaltysurvey.setCurrentPath();
       ACC.loyaltysurvey.bindAll(); 
	
    });

$('#1057,#firstQuestionOtherPrimary,#firstQuestionOtherSecondary,#q2_others1,#q2_others2,#q3_others1,#q3_others2,#q4_others1,#q4_others2,#q5_others1,#q5_others2,#q6_others1,#q6_others2,#q7_others1,#q7_others2,#q8_others1,#q8_others2,#q9_others1,#q9_others2,#q10_others1,#q10_others2,#q11_others1,#q11_others2,#q12_others1,#q12_others2,#q13_others1,#q13_others2,#q14_others1,#q14_others2,#q15_others1,#q15_others2,#q16_others1,#q16_others2,#q17_others1,#q17_others2,#q18_others1,#q18_others2,#Q3Other1txt,#Q3Other2txt,#Q4Other1txt,#Q4Other2txt,#Q5Other1txt,#Q5Other2txt,#Q6Other1txt,#Q6Other2txt,#Q7Other1txt,#Q7Other2txt,#Q8Other1txt,#Q8Other2txt,#Q9Other1txt,#Q9Other2txt,#Q10Other1txt,#Q10Other2txt,#Q11Other1txt,#Q11Other2txt,#Q12Other1txt,#Q12Other2txt,#Q13Other1txt,#Q13Other2txt,#Q14Other1txt,#Q14Other2txt').keypress(function(event){
	//alert("keypress");
	var keyCode = (event.keyCode ? event.keyCode : event.which);
	//alert(keyCode);
	var alphaNumericCheck = ((keyCode >= 48 && keyCode <= 57)
					|| (keyCode >= 65 && keyCode <= 90)
					|| (keyCode >= 97 && keyCode <= 122) );
				
	if(alphaNumericCheck || keyCode == 8 || keyCode == 32){
		return true;		
	}else{
		return false;
	}
}); 

$("#brandQuestionform").submit(function( event ) {
    $("#brandSubmit").prop("disabled", true).addClass("disabled");
 });

$("#partsBuyingQuestionsForm").submit(function( event ) {
    $("#partsBuySubmit").prop("disabled", true).addClass("disabled");
 });

$("#InterestAndHobbiesQuestionsForm").submit(function( event ) {
    $("#InterestAndHobbiesSubmit").prop("disabled", true).addClass("disabled");
 });
function submitPromocode()
{

	
	//alert("in change");
		
		var promCode = $('#promCode').val();
		//alert("promCode" + promCode );
	if(promCode!= null  || promCode != "")
		{
		//$("#promoCodeBtn").addAttr('disabled');
 		$("#promoCodeBtn").prop("disabled",true);
		var currentPath = window.location.href;
		//alert("current path"+currentPath);
		 var pathName = "";
		//alert(currentPath);
		 try{
				if(currentPath.indexOf("/USD") != -1){
					pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
					//alert("pathname1"+pathName);
				}else if(currentPath.indexOf("?site") != -1){
					
					pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/";
					//alert("pathname2"+pathName);
				}else{
					pathName = window.location.href+"loyalty/en/USD";
					//alert("pathname3"+pathName);
				}
			}catch(e){
				alert(e);
			}
		$.ajax({
			type : "POST",
			url : pathName+ "loyalty/promocode",
			data : "selectedPromo=" + promCode,
			success : function(response) {
				$('#promoCodeBlock').replaceWith(response);
			},
			error : function(e) {
				
			}
		});
		}
	

}
function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};
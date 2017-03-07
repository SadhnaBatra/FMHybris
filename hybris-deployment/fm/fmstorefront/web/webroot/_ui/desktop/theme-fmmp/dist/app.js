ACC.useraddress = {

              currentPath: window.location.href,
              pathName: "",
              currentCurrency: "USD",
              ajaxUrl: "USD/my-fmaccount/",

                           $SelectShipToAddress: $('#selectshipto'),
                           $SelectSoldToAddress: $('#selectsoldto'),
                           $SearchShipToAddress: $('#changeShip'),
                           $liveAutoSearchResults: $('#livesearch'),
                           $SearchSoldToAddress: $('#changeSold'),
                           $liveSoldToAutoSearchResults: $('#livesearchSoldTo'),
                           
                           options: "",
                           url: "",
                           setCurrentPath: function() {
                                  try{
                       			if (ACC.useraddress.currentPath.indexOf("iframe") != -1) {
                        				ACC.useraddress.currentPath = ACC.useraddress.currentPath.replace("iframe","");
                        			}

                                         if(ACC.useraddress.currentPath.indexOf("/USD") != -1){
                                                ACC.useraddress.pathName = ACC.useraddress.currentPath.substring(0, ACC.useraddress.currentPath.lastIndexOf("/USD") + 1);
                                         }else if(ACC.useraddress.currentPath.indexOf("?site") != -1){
                                                ACC.useraddress.pathName = ACC.useraddress.currentPath.substring(0, ACC.useraddress.currentPath.lastIndexOf("/?site") + 1)+ACC.useraddress.currentPath.substring(ACC.useraddress.currentPath.lastIndexOf("site=")+5,ACC.useraddress.currentPath.length)+"/en/";
                                         }else{
                                                ACC.useraddress.pathName = window.location.href+"federalmogul/en/";
                                         }
                                  }catch(e){
                                         alert(e);
                                  }
                           },
                           
                           bindAll: function() {
                                  with (ACC.useraddress) {
                                        
                                         bindToChangeShipTo($SelectShipToAddress);
                                         bindToChangeSoldTo($SelectSoldToAddress);
                                         bindToSearchShipToAddress($SearchShipToAddress);
                                         bindToSearchSoldToAddress($SearchSoldToAddress);
                                  }
                           },
                           bindToChangeShipTo: function(shipTo) {
                                  shipTo.change(function(event) {                                      
                                         var shipTo =ACC.useraddress.$SelectShipToAddress.val();
                                         var shipToSoldToFlag="shipTo" ;
                                         
                                         if(shipTo != null && shipTo.indexOf(":") > -1){
                                                
                                                accountId = shipTo.substring(0, shipTo.indexOf(":"));
                                                addressId = shipTo.substring(shipTo.indexOf(":")+1);
                                                ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"change-shipto-soldto-session/"+accountId+"/"+shipToSoldToFlag+"/dummy";                             
                                                ACC.useraddress.postAJAX(ACC.useraddress.Url);
                                               
                                         }                                        
                                  });
                           },
                           
                           bindToChangeSoldTo: function(soldTo) {
                                  soldTo.change(function(event) {                                      
                                         var soldTo =ACC.useraddress.$SelectSoldToAddress.val();
                                         var shipToSoldToFlag="soldTo";
                                       
                                         
                                         if(soldTo != null && soldTo.indexOf(":") > -1){
                                                
                                                accountId = soldTo.substring(0, soldTo.indexOf(":"));
                                                addressId = soldTo.substring(soldTo.indexOf(":")+1);
                                                
                                                ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"change-shipto-soldto-session/"+accountId+"/"+shipToSoldToFlag+"/dummy";                      
                                                ACC.useraddress.postAJAX(ACC.useraddress.Url);
                                         }                                        

                                  });
                           },
                           
                           bindToSearchShipToAddress: function(shipToAddrSearch) {
                                  shipToAddrSearch.keyup(function(event) {  
                                	  var searchShipToAddrText =ACC.useraddress.$SearchShipToAddress.val();
                                	  	setTimeout(function(){ 
                                	  		 if(ACC.useraddress.$SearchShipToAddress.val() == searchShipToAddrText ){
                                	  			 if(searchShipToAddrText == ''){
                                	  				searchShipToAddrText="getAll*";
                                	  			 }
		                                	     var shipToSoldToFlag="shipTo";   
		                                         var type="searchShipTo";
		                                         var userType =  $('#loggedUserType').val();
		                                         var accountId =  $('#loggedAccountID').val();
							 var SoldToAddressType = $('#SoldToAddressType').val();
		                                 		 if('' != SoldToAddressType){
		                                 			var  soldToAcc = $('#'+SoldToAddressType).html();
									var accountcode1= soldToAcc.substring(soldToAcc.indexOf("/",soldToAcc.lastIndexOf("<br>"))-10);
                                                                        accountcode = accountcode1.split("/");
									accountId = $.trim(accountcode[0]);
		                                 			//var accountcode= soldToAcc.substring(soldToAcc.indexOf("/")-10,soldToAcc.indexOf("/"));
		                                 			//accountId = $.trim(accountcode);
		                                 		 }
		                                         ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"userAutoAddressSearch/"+searchShipToAddrText+"/"+shipToSoldToFlag+"?loggedUserType="+userType+"&loggedAccountID="+accountId;                         
		                                         ACC.useraddress.postAJAX(ACC.useraddress.Url, ACC.useraddress.$liveAutoSearchResults,type);    
                                	  		 }
                                	  	}, 1000); 
                                  	});
                           },
                           
                           bindToSearchSoldToAddress: function(soldToAddrSearch) {
                        	   	soldToAddrSearch.keyup(function(event) { 
                        			var searchSoldToAddrText =ACC.useraddress.$SearchSoldToAddress.val();
                        	   		setTimeout(function(){
	                        	   		 if(ACC.useraddress.$SearchSoldToAddress.val() == searchSoldToAddrText){
		                        	   		var shipToSoldToFlag="soldTo";      
		                        	   		var type="searchSoldTo";
		                        	   		var userType =  $('#loggedUserType').val();
		                        	        var accountId =  $('#loggedAccountID').val();
		                        	        ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"userAutoAddressSearch/"+searchSoldToAddrText+"/"+shipToSoldToFlag+"?loggedUserType="+userType+"&loggedAccountID="+accountId;                       
		                        	        ACC.useraddress.postAJAX(ACC.useraddress.Url, ACC.useraddress.$liveSoldToAutoSearchResults,type); 
	                        	   		 }
                        	   		}, 1000);
                            });
                           },
                           
       postAJAX:function(Url){
              //openOrderInvoiceModal();
              //ACC.uploadorder.pathName
              $.ajax({
               url: Url,           
               async: false,
                      cache: false,
                success: function (result) {
                     // div.html(result);
                     // closeOrderInvoiceModal();
                           //callback();
                     
                      
                }
            });
       },
       
       postAJAX:function(Url,div,type){
              //openOrderInvoiceModal();
              //ACC.uploadorder.pathName
              //alert("Hey I'm Called !!!!!!!!!!!!!!!!!" + div);
              //alert("URL :: "+Url);
		openLoader();
              $.ajax({
               url: Url,           
               async: true,
                      cache: false,
                      dataType:'html',
                      type: 'GET',
                success: function (data) {
                     // div.html(result);
                     // closeOrderInvoiceModal();
                           //callback();
                      //alert("############ success ###################");
                      $('#autoSearchList'+type).show();
                     div.html(data);
		    closeLoader();
                }
            });
       },
}; 

$(document).ready(function() {
       ACC.useraddress.setCurrentPath();
       ACC.useraddress.bindAll(); 
	closeLoader();
    });

function hideList(selectedValue,type,accountId,nabsAccountcode){
	var listId = 'autoSearchList'+type ;
	var data ='<p>'+selectedValue+'</p>';
	if(nabsAccountcode == ''){
		nabsAccountcode="dummy";
	}
	
       $('#'+listId).hide();
       if(type == 'searchSoldTo'){
    	   $('#changeSold').hide();
    	   $('#changeSoldbtn').hide();
    	   $('#oldSlodTo').hide();
           $('#newSoldTo').show();
           $('#existsoldto').hide();
           $('#newSoldTo').html(data);
           $('#defaultAccSoldTo').show();
	       var shipToSoldToFlag="soldTo";
	       ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"change-shipto-soldto-session/"+accountId+"/"+shipToSoldToFlag+"/"+nabsAccountcode;                      
	       $.ajax({
               url:  ACC.useraddress.Url,           
               async: false,
               cache: false,
               success: function (result) {
                     $('#SoldToAddressType').val('newSoldTo') ;
                }
            });
       }
       
       if(type == 'searchShipTo'){
    	  $('#changeShip').hide();  
    	  $('#changeShipbtn').hide();
          $('#oldShipTo').hide();
          $('#newShipTo').show();
          $('#shipTo').hide();
          $('#newShipTo').html(data);
          $('#shipTodefaultAcc').show();
           var shipToSoldToFlag="shipTo" ;
           ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"change-shipto-soldto-session/"+accountId+"/"+shipToSoldToFlag+"/"+nabsAccountcode;                             
           $.ajax({
               url:  ACC.useraddress.Url,           
               async: false,
               cache: false,
               success: function (result) {
                      
                }
            });
           
           
       }
         
}

function  updateUserNewAddressToSession(userNewAddress,shipToSoldToFlag){
	
	
	ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"user-new-Address/"+userNewAddress+"/"+shipToSoldToFlag;  

        ACC.useraddress.postAJAX(ACC.useraddress.Url);
}

function getAllAddress(inputString,type){

	if (type == "searchSoldTo")
	{
		
		var shipToSoldToFlag="soldTo"; 
		var userType =  $('#loggedUserType').val();
		var accountId =  $('#loggedAccountID').val();
		ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"userAutoAddressSearch/"+inputString+"/"+shipToSoldToFlag+"?loggedUserType="+userType+"&loggedAccountID="+accountId;                       
		ACC.useraddress.postAJAX(ACC.useraddress.Url, ACC.useraddress.$liveSoldToAutoSearchResults,type);                             
	}
	if (type == "searchShipTo")
	{
		var shipToSoldToFlag="shipTo";
		var userType =  $('#loggedUserType').val();
		var accountId =  $('#loggedAccountID').val();
		var SoldToAddressType = $('#SoldToAddressType').val();
		if('' != SoldToAddressType){
			var  soldToAcc = $('#'+SoldToAddressType).html();

			var accountcode1= soldToAcc.substring(soldToAcc.indexOf("/",soldToAcc.lastIndexOf("<br>"))-10);
                        accountcode = accountcode1.split("/");
			accountId = $.trim(accountcode[0]);
			//var accountcode= soldToAcc.substring(soldToAcc.indexOf("/")-10,soldToAcc.indexOf("/"));
			//accountId = $.trim(accountcode);
		}

		ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"userAutoAddressSearch/"+inputString+"/"+shipToSoldToFlag+"?loggedUserType="+userType+"&loggedAccountID="+accountId;                         
		ACC.useraddress.postAJAX(ACC.useraddress.Url, ACC.useraddress.$liveAutoSearchResults,type);                             
	}
}

function openLoader(){

	 
$("#floatingBarsG4").show();
}

function closeLoader(){
	
	$("#floatingBarsG4").hide();
}


function changeDefault(accId){
	ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"change-shipto-soldto-session/"+accId+"/defaultShipTo/dummy";
	$.ajax({
        url:  ACC.useraddress.Url,           
        async: false,
        cache: false,
        type: 'GET',
        success: function (xmlDoc) {
        	var respDoc = $(xmlDoc).find("shipToSessionAddresschanged").text().replace(/^\s+|\s+$/g, '');
        	if(respDoc.indexOf("Success") != -1 ){
        		 $(".shipToForm .tab-pane").hide();
                 $("#shipTodefaultAcc").show();
                 $("#newShipTo").hide();
                 $('#oldShipTo').show();
        	}
         }
     });
}

function changeSoldToDefault(accId){
	ACC.useraddress.Url = ACC.useraddress.pathName + ACC.useraddress.ajaxUrl +"change-shipto-soldto-session/"+accId+"/defaultSoldTo/dummy";
	$.ajax({
        url:  ACC.useraddress.Url,           
        async: false,
        cache: false,
        type: 'GET',
        success: function (xmlDoc) {
        	var respDoc = $(xmlDoc).find("shipToSessionAddresschanged").text().replace(/^\s+|\s+$/g, '');
        	if(respDoc.indexOf("Success") != -1 ){
        		 $(".soldToForm .tab-pane").hide();
        		 $('#defaultAccSoldTo').show();
                 $("#soldTodefaultAcc").show();
                 $('#newSoldTo').hide();
                 $('#oldSlodTo').show();
		 $('#SoldToAddressType').val('oldSlodTo');
        	}
         }
     });
}

function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};;

ACC.userlocation = {

	bindAll: function ()
	{
		ACC.userlocation.bindUserLocationSearchButtonClick();
		ACC.userlocation.bindUserLocationEnterPress();
		ACC.userlocation.bindAutoLocationSearchButtonClick();
	},

	bindUserLocationEnterPress: function ()
	{
		$('#user_location_query').keypress(function (e)
		{
			var code = null;
			code = (e.keyCode ? e.keyCode : e.which);
			if (code == 13)
			{
				$.ajax({
					url: searchUserLocationUrl,
					type: 'POST',
					data: {q: $('#user_location_query').attr("value")},
					success: function (data)
					{
						location.reload();
					}
				});
				return false;
			}
			;
		});
	},

	bindUserLocationSearchButtonClick: function ()
	{
		$('#user_location_query_button').click(function (e)
		{
			$.ajax({
				url: searchUserLocationUrl,
				type: 'POST',
				data: {q: $('#user_location_query').attr("value")},
				success: function (data)
				{
					location.reload();
				}
			});
			return false;
		});
	},

	bindAutoLocationSearchButtonClick: function ()
	{
		$(document).on("click", "#findStoresNearMeAjax", function (e)
		{
			e.preventDefault();
			try
			{
				var gps = navigator.geolocation;
				gps.getCurrentPosition(ACC.userlocation.positionSuccessStoresNearMe, function (error)
				{
					console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
				});
			}
			catch (error)
			{
				console.log("An error occurred... ");
			}
		});
	},

	positionSuccessStoresNearMe: function (position)
	{
		if (typeof autoUserLocationUrl !== 'undefined')
		{
			$.ajax({
				url: autoUserLocationUrl,
				type: 'POST',
				data: {latitude: position.coords.latitude, longitude: position.coords.longitude},
				success: function (data)
				{
					location.reload();
				}
			});
		}

		return false;
	}

};

$(document).ready(function ()
{
	ACC.userlocation.bindAll();
});
;

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
function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};;

ACC.track = {
	trackAddToCart: function (productCode,quantity,name,price)
	{
		window.mediator.publish('trackAddToCart',{
			productCode: productCode,
			quantity: quantity,
			productName:name,
                        productPrice:price
		});
	},
	trackRemoveFromCart: function(productCode, initialCartQuantity,name,price)
	{
		window.mediator.publish('trackRemoveFromCart',{
			productCode: productCode,
			initialCartQuantity: initialCartQuantity,
                        productName:name,
                        productPrice:price
		});
	},

	trackUpdateCart: function(productCode, initialCartQuantity, newCartQuantity,name,price)
	{
		window.mediator.publish('trackUpdateCart',{
			productCode: productCode,
			initialCartQuantity: initialCartQuantity,
			newCartQuantity: newCartQuantity,
			productName:name,
                        productPrice:price

		});
	}
	

};
;

ACC.ymmsearch = {
	currentPath: window.location.href,
	pathName: "",
	currentCurrency: "USD",
	ajaxUrl: "USD/ymmsearch/",
	$page:           $("#page"),
	$globalMessages: $("#globalMessages"),
	$vehicleSegmentSelect: $('#vehiclesegment'),
	$yearSelect: $('#year'),
	$makeSelect:  $('#make'),
	$modelSelect: $('#model'),
	$modelhomepageSelect: $('#modelhome'),
	$categorySelect: $('#category :selected'),
	$subCategorySelect: $('#subcategory'),
	options: "",
	url: "",
	VehicleResultsSegments: "VehicleSegmentResults",
	Years: "Years",
	Make: "Make",
	Models: "Models",
	SubCategory: "SubCategoryResults",
	segementOption: '<option value="vehicleSegment">Vehicle Type</option>',
	yearsOption: '<option value="year">Year</option>',
	makeOption: '<option value="make">Make</option>',
	modelsOption: '<option value="model">Model</option>',
	modelshomeOption: '<option value="modelhome">Model</option>',
	subCategoryOption: '<option value="subCategorySelect">Sub Category</option>',
	subCategoryAllOption: '<option value="subCategoryAll">All</option>',
	conOptionStart: '<option value="',
	conOptionEnd: ' </option>',
	conValue: 'value',
	conSize: 'Size',
	setCurrentPath: function() {
		try{
			if (ACC.ymmsearch.currentPath.indexOf("iframe") != -1) {
				ACC.ymmsearch.currentPath = ACC.ymmsearch.currentPath.replace("iframe","");
			}

			if(ACC.ymmsearch.currentPath.indexOf("/USD") != -1){
				ACC.ymmsearch.pathName = ACC.ymmsearch.currentPath.substring(0, ACC.ymmsearch.currentPath.lastIndexOf("/USD") + 1);
			}else if(ACC.ymmsearch.currentPath.indexOf("?site") != -1){
				ACC.ymmsearch.pathName = ACC.ymmsearch.currentPath.substring(0, ACC.ymmsearch.currentPath.lastIndexOf("/?site") + 1)+ACC.ymmsearch.currentPath.substring(ACC.ymmsearch.currentPath.lastIndexOf("site=")+5,ACC.ymmsearch.currentPath.length)+"/en/";
			}else{
				ACC.ymmsearch.pathName = ACC.ymmsearch.currentPath+"federalmogul/en/";
			}
		}catch(e){
			alert(e);
		}
	},
	
	bindAll: function() {
		with (ACC.ymmsearch) {
			bindToAddVehicleSegment($vehicleSegmentSelect);
			bindToChangeYear($yearSelect);
			bindToChangeMake($makeSelect);
			bindToChangeModel($modelSelect);
			bindToChangeSubcategory($subCategorySelect);
		}
	},

	bindToAddVehicleSegment: function(vehicleSegmentSelect) {
		
		vehicleSegmentSelect.change(function(event) {
			ACC.ymmsearch.$makeSelect.empty();
			ACC.ymmsearch.$makeSelect.attr("disabled", "true");
			ACC.ymmsearch.$modelSelect.empty();
			ACC.ymmsearch.$modelSelect.attr("disabled", "true");
			ACC.ymmsearch.$modelhomepageSelect.empty();
			ACC.ymmsearch.$modelhomepageSelect.attr("disabled", "true");
			$('#ymmSearch').addClass('disabled');
			ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"autoyear/"+this.value;

			if(this.value == "Commercial,Industrial Ag."){
				ACC.ymmsearch.$yearSelect.empty();
				ACC.ymmsearch.$yearSelect.attr("disabled", "true");
				window.parent.location = "http://fme-cat.com/commercialApplication.aspx";
			}else if(this.value == "Marine"){
				ACC.ymmsearch.$yearSelect.empty();
				ACC.ymmsearch.$yearSelect.attr("disabled", "true");
				window.parent.location = "http://fme-cat.com/MarineApplication.aspx#marine";
			}else if(this.value == "Powersport"){
				ACC.ymmsearch.$yearSelect.empty();
				ACC.ymmsearch.$yearSelect.attr("disabled", "true");
				window.parent.location = "http://fme-cat.com/PowerSportApplication.aspx#powersport";
			}else if(this.value == "Performance"){
				ACC.ymmsearch.$yearSelect.empty();
				ACC.ymmsearch.$yearSelect.attr("disabled", "true");
				window.parent.location = "http://fme-cat.com/PerfApplication.aspx#performance";
			} else if(this.value == "Small Engine"){
				ACC.ymmsearch.$yearSelect.empty();
				ACC.ymmsearch.$yearSelect.attr("disabled", "true");
				window.parent.location = "http://fme-cat.com/SmallEngineApplication.aspx#smallengine";
			}else{

				ACC.ymmsearch.postAJAX(ACC.ymmsearch.Years, ACC.ymmsearch.$yearSelect,ACC.ymmsearch.yearsOption,null);
				ACC.ymmsearch.$yearSelect.removeAttr('disabled');
			}
			
		});
	},
	bindToChangeYear: function(yearSelect) {
		yearSelect.change(function(event) {
			ACC.ymmsearch.$makeSelect.empty();
			ACC.ymmsearch.$makeSelect.attr("disabled", "true");
			ACC.ymmsearch.$modelSelect.empty();
			ACC.ymmsearch.$modelSelect.attr("disabled", "true");
			ACC.ymmsearch.$modelhomepageSelect.empty();
			ACC.ymmsearch.$modelhomepageSelect.attr("disabled", "true");
			$('#ymmSearch').addClass('disabled');
			var vehicleSegment=ACC.ymmsearch.$vehicleSegmentSelect.val();
			ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"automake/"+this.value+"/"+vehicleSegment;
			ACC.ymmsearch.postAJAX(ACC.ymmsearch.Make, ACC.ymmsearch.$makeSelect,ACC.ymmsearch.makeOption,null);
		});
		yearSelect.focus(function(event) {
			ACC.ymmsearch.$yearSelect.empty();
			var vehicleSegment=ACC.ymmsearch.$vehicleSegmentSelect.val();
			
			ACC.ymmsearch.$makeSelect.empty();
			ACC.ymmsearch.$makeSelect.attr("disabled", "true");
			ACC.ymmsearch.$modelSelect.empty();
			ACC.ymmsearch.$modelSelect.attr("disabled", "true");
			ACC.ymmsearch.$modelhomepageSelect.empty();
			ACC.ymmsearch.$modelhomepageSelect.attr("disabled", "true");
			$('#ymmSearch').addClass('disabled');
			
			ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"autoyear/"+vehicleSegment;
			ACC.ymmsearch.postAJAX(ACC.ymmsearch.Years, ACC.ymmsearch.$yearSelect,ACC.ymmsearch.yearsOption,null);
			
			ACC.ymmsearch.$yearSelect.removeAttr('disabled');
			ACC.ymmsearch.$makeSelect.empty();
			ACC.ymmsearch.$makeSelect.removeAttr('disabled');
		});
		
		
	},
	bindToChangeMake: function(makeSelect) {
		makeSelect.change(function(event) {
			ACC.ymmsearch.$modelSelect.empty();
			ACC.ymmsearch.$modelSelect.attr("disabled", "true");	
			ACC.ymmsearch.$modelhomepageSelect.empty();
			ACC.ymmsearch.$modelhomepageSelect.attr("disabled", "true");
			$('#ymmSearch').addClass('disabled');
			var modelsOptions;
			var modelSelectNew;
			var homeModelVal = ACC.ymmsearch.$modelhomepageSelect.val();
			var modelVal = ACC.ymmsearch.$modelSelect.val();
			var modelHome = (homeModelVal == null && modelVal === undefined)? true : false;
			if(modelHome){
				modelSelectNew = ACC.ymmsearch.$modelhomepageSelect;
				modelsOptions = ACC.ymmsearch.modelshomeOption;
			}else{
				modelSelectNew = ACC.ymmsearch.$modelSelect;
				modelsOptions = ACC.ymmsearch.modelsOption;				
			}
			var vehicleSegment=ACC.ymmsearch.$vehicleSegmentSelect.val();
			var years=ACC.ymmsearch.$yearSelect.val();
			ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"automodel/"+years+"/"+this.value+"/"+vehicleSegment;
			ACC.ymmsearch.postAJAX(ACC.ymmsearch.Models, modelSelectNew,modelsOptions,null);
		});
		
		makeSelect.focus(function(event) {
			ACC.ymmsearch.$makeSelect.empty();

			ACC.ymmsearch.$modelhomepageSelect.empty();
			ACC.ymmsearch.$modelhomepageSelect.attr("disabled", "true");
			$('#ymmSearch').addClass('disabled');
			var vehicleSegment=ACC.ymmsearch.$vehicleSegmentSelect.val();
			var years=ACC.ymmsearch.$yearSelect.val();
			ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"automake/"+years+"/"+vehicleSegment;
			ACC.ymmsearch.postAJAX(ACC.ymmsearch.Make, ACC.ymmsearch.$makeSelect,ACC.ymmsearch.makeOption,null);

			ACC.ymmsearch.$modelSelect.removeAttr('disabled');
			ACC.ymmsearch.$modelhomepageSelect.removeAttr('disabled');
			
		});
	},
	bindToChangeSubcategory: function(subcategory) {
		
		var isIE = false ;		
		var trident = !!navigator.userAgent.match(/Trident\/7.0/);
		var net = !!navigator.userAgent.match(/.NET4.0E/);
		var IE11 = trident && net
		var IEold = ( navigator.userAgent.match(/MSIE/i) ? true : false );
		var category=ACC.ymmsearch.$categorySelect.text();
		
		if(IE11 || IEold){
			isIE = true;
		}
		if(isIE & category != null && category!='Category' && category!='undefined' && category!=""){
			closeModal();
			var subCat =  ACC.ymmsearch.$subCategorySelect.find("option:selected").text();
			ACC.ymmsearch.$subCategorySelect.empty();
			var category=ACC.ymmsearch.$categorySelect.text();
      			ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"autoSubCategory/"+category;
			ACC.ymmsearch.postAJAX(ACC.ymmsearch.SubCategory, ACC.ymmsearch.$subCategorySelect,ACC.ymmsearch.subCategoryOption,ACC.ymmsearch.subCategoryAllOption);
			$("#subcategory > option").each(function () {
		        	if($.trim(this.text) == $.trim(subCat)) {
	        			$(this).attr('selected', 'selected');
			        } 
			});
			closeModal();
		}
		subcategory.focus(function(event) {
			closeModal();
			ACC.ymmsearch.$subCategorySelect.empty();
			var category=ACC.ymmsearch.$categorySelect.text();
			ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"autoSubCategory/"+category;
			
			ACC.ymmsearch.postAJAX(ACC.ymmsearch.SubCategory, ACC.ymmsearch.$subCategorySelect,ACC.ymmsearch.subCategoryOption,ACC.ymmsearch.subCategoryAllOption);
		});
	}, 
	bindToChangeModel: function(modelSelect) {
	

		var onClickedEve = false;
		var homeModelVal = ACC.ymmsearch.$modelhomepageSelect.val();
		var modelVal = ACC.ymmsearch.$modelSelect.val();
		var modelsOptions;
		var modelSelectNew;
		var modelHome = (homeModelVal == 'Model' && modelVal === undefined)? true : false;

		if(modelHome){
			modelSelectNew = ACC.ymmsearch.$modelhomepageSelect;
			modelsOptions = ACC.ymmsearch.modelshomeOption;
		}else{
			modelSelectNew = ACC.ymmsearch.$modelSelect;
			modelsOptions = ACC.ymmsearch.modelsOption;				
		}

		modelSelectNew.focus(function(event) {
				ACC.ymmsearch.$modelSelect.empty();
				ACC.ymmsearch.$modelhomepageSelect.empty();
				var vehicleSegment=ACC.ymmsearch.$vehicleSegmentSelect.val();
				var years=ACC.ymmsearch.$yearSelect.val();
				var make =ACC.ymmsearch.$makeSelect.val();
				ACC.ymmsearch.Url = ACC.ymmsearch.pathName + ACC.ymmsearch.ajaxUrl+"automodel/"+years+"/"+make+"/"+vehicleSegment;
				ACC.ymmsearch.postAJAX(ACC.ymmsearch.Models, modelSelectNew,modelsOptions,null);
		});

		modelSelectNew.change(function(event) {
			var sel_vehicleSegment= ACC.ymmsearch.$vehicleSegmentSelect.val();
			var sel_year= ACC.ymmsearch.$yearSelect.val();
			var sel_make= ACC.ymmsearch.$makeSelect.val();
			var sel_model= modelSelectNew.val(); 
			var pathName = '';
			var win_url = window.location.href;
			try {
				if (win_url.indexOf("/USD") != -1) {
					pathName = win_url
							.substring(0, win_url.lastIndexOf("/USD") + 5);
				} else if (win_url.indexOf("?site") != -1) {
					pathName = win_url.substring(0,
							win_url.lastIndexOf("/?site") + 1)
							+ win_url.substring(win_url.lastIndexOf("site=") + 5,
									win_url.length) + "/en/USD/";

				} else {
					pathName = window.location.href;
				}

			} catch (e) {
				alert(e);
			}
			if(!modelHome){
				var categoryVal =$('#category').val();
				var categoryText =  $('#category').find("option:selected").text();
				var category ;
				
				if(categoryText == "Gaskets & Sealing Systems"){
					categoryText = "Gaskets %26 Sealing Systems";
				}
				if(categoryText == "Filters & Chemicals"){
					categoryText = "Filters %26 Chemicals";
				}
				if(categoryText != "Category" && categoryText !="All"){
					category = "category:" + categoryText.trim()+"&text=#";
				}else{
					category ="&text=#"
				}
			}
			var ymmCode=sel_year.trim()+sel_make.trim()+sel_model.trim().replace('&','')+"|";
			var sele_model = sel_model.trim().replace('&','');
			var queryVal= pathName + "search?q=:name-asc:vehiclesegment:"+ymmCode+sel_vehicleSegment.trim()+":year:"+ymmCode+sel_year.trim()+":make:"+ymmCode+sel_make.trim()+":model:"+ymmCode+sele_model.trim()+":"+category;

			if(!modelHome){
				openUploadOrderModal();
			 	location.href = queryVal;
		 	}
		 
		});
	},
	postAJAX:function(reqType , selectField,defaultopt, defaultoptAll){
		openModal();
		$.ajax({
	    	 url: ACC.ymmsearch.Url, 		
	    	 async: false,
		  	 cache: false,	
	         success: function (xmlDoc) {
		
	       	 var respDoc = $(xmlDoc).find(reqType).text();
	       	 var sel_year= ACC.ymmsearch.$yearSelect.val();
	       	 var respSize = $(xmlDoc).find(reqType+ACC.ymmsearch.conSize).text();
			  
	            try{	
	            	ACC.ymmsearch.options = defaultopt;
	            	if(reqType == 'SubCategoryResults' && defaultoptAll !=null && respSize > 1 && sel_year !='Year'){
	            		ACC.ymmsearch.options = defaultopt + defaultoptAll;
	            	}
	            	
	            	if(respSize>0)
					{ 
						for ( var int = 0; int < respSize; int++) {
						
							if(reqType == 'SubCategoryResults'){							ACC.ymmsearch.options += setSubCategory($(xmlDoc).find(reqType+ACC.ymmsearch.conValue+int).text());
							}else{
								ACC.ymmsearch.options += ACC.ymmsearch.conOptionStart+$(xmlDoc).find(reqType+ACC.ymmsearch.conValue+int).text()+'">'+$(xmlDoc).find(reqType+ACC.ymmsearch.conValue+int).text()+ ACC.ymmsearch.conOptionEnd;
							} 
						}
						selectField.append(ACC.ymmsearch.options); 
						selectField.removeAttr('disabled');
						closeModal();
					}else 
					{
						closeModal();
						alert("For the Selected "+reqType+ " not Found");
						
					}
				}
				catch(err){}
	        }
	     });
	},
};

$(document).ready(function() {
	
	ACC.ymmsearch.setCurrentPath();
	ACC.ymmsearch.bindAll();
	
	$(".description").each(function (i) {
		var b=  $(this).find("b").html();
		$(this).html(b);
		$(this).find("ul").remove();		  
	});

	var text_max = $('textarea#orderNote').attr('maxlength');
	$('.char-count').html('<label>'+text_max + '</label> characters remaining');
 	 $('#orderNote').keyup(function() {
		    	 
		         var text_length = $('#orderNote').val().length;
		         var text_remaining = text_max - text_length;

		         $('.char-count').html('<label>'+text_remaining + '</label> characters remaining');
		     });
	
	$('#ymmSearch').addClass('disabled')
	
	var d = new Date();
	var n = d.getFullYear(); 
	
	$('#facetnameid').on('change', function() {
		alert("test");
	    var x=$(this).val();
	    alert(x);
	});
	
	$('#modelhome').on('change', function() {
		if($(this).val() != "modelhome"){
			$('#ymmSearch').removeClass('disabled');
		}else{
			$('#ymmSearch').addClass('disabled')
		}
		
		$('#categoryDropdowns').show();
		
	});
	$("body").on("keyup", "input[name=qtyInput]", function(event) {
		var input = $(event.target);
	  	var value = input.val();
	  	var qty_css = 'input[name=qty]';
			while(input.parent()[0] != document) {
				input = input.parent();
				if(input.find(qty_css).length > 0) {
					input.find(qty_css).val(value);
					return;
				}
			}
	});
	$("body").on("keyup", "input[name=p_poNumber]", function(event) {
		var input = $(event.target);
	  	var value = input.val();
	  	var input=$(this);
	    var is_name=input.val();
	    if(is_name){input.removeClass("invalid").addClass("valid");}
	    else{input.removeClass("valid").addClass("invalid");}
	  	
	  	var qty_css = 'input[name=poNumber]';
			while(input.parent()[0] != document) {
				input = input.parent();
				if(input.find(qty_css).length > 0) {
					input.find(qty_css).val(value);
					return;
				}
			}
	});
	$("body").on("keyup", "textarea[name=p_orderNote]", function(event) {
		var input = $(event.target);
	  	var value = input.val();
	  	var qty_css = 'input[name=orderInstruction]';
			while(input.parent()[0] != document) {
				input = input.parent();
				if(input.find(qty_css).length > 0) {
					input.find(qty_css).val(value);
					return;
				}
			}
	});
 
	$('input:checkbox[name=dcCheckbox]').on('click',function() 
	{    
	    if($(this).is(':checked')){
	    	var entrynumber = $(this).attr('id');
	    	//$("#fm-gatp-button").attr("disabled", "false");
	    	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+$(this).val()+"/"+entrynumber+"/available";
	    	$.ajax({
		    	 url: ajaxURL, 		
		    	 async: false,
			  	 cache: false,	
		         success: function (xmlDoc) {
		        	 return true;
		         }
		     });
	    }else{
	    	var entrynumber = $(this).attr('id');
	    	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/removedc/"+$(this).val()+"/"+entrynumber;
	    	$.ajax({
		    	 url: ajaxURL, 		
		    	 async: false,
			  	 cache: false,	
		         success: function (xmlDoc) {
		        	 return true;
		         }
		     });
	    }
	});

	$('input:checkbox[name=confirmCheckbox]').on('click',function() 
	{    
	    if ($(this).is(':checked')) {
	    	$('input:checkbox[name=dcCheckbox]').each(function () {
		    	var entrynumber = $(this).attr('id');
		    	$(this).prop('checked', true);
		    	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+$(this).val()+"/"+entrynumber+"/available";
		    	$.ajax({
			    	 url: ajaxURL, 		
			    	 async: false,
				  	 cache: false,	
			         success: function (xmlDoc) {
			        	 return true;
			         }
			     });
	    	});
	    	
	    	// Disable all Alternate DC Select boxes and reset their values to default.
	   		$('select[name="altDcSelectBox"]').prop('disabled', true);
	   		$('select[name="altDcSelectBox"]').val("Select");
	    } else {
	    	$('input:checkbox[name=dcCheckbox]').each(function () {
		    	var entrynumber = $(this).attr('id');
		    	$(this).prop('checked', false);
		    	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/removedc/"+$(this).val()+"/"+entrynumber;
		    	$.ajax({
			    	 url: ajaxURL, 		
			    	 async: false,
				  	 cache: false,	
			         success: function (xmlDoc) {
			        	 return true;
			         }
			     });
	    	});
	    	
	    	// Enable all Alternate DC Select boxes.
	    	$('select[name="altDcSelectBox"]').prop('disabled', false);
	    }
	});
	$('input:checkbox[name=available]').on('click',function() 
	{    
		if($(this).is(':checked')){
			var sequences = $(this).attr('id').split("_");
			var entrynumber = sequences[1].split("-")[0];
			$("#nothing_"+sequences[1]).attr("disabled", "true");
			//$("#fm-gatp-button").attr("disabled", "false");
			var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+$(this).val()+"/"+entrynumber+"/available";
			$.ajax({
				url: ajaxURL, 		
				async: false,
				cache: false,	
				success: function (xmlDoc) {
					return true;
				}
			});
		}else{
			$("#nothing_"+sequences[1]).attr("disabled", "false");
		}
	});
	$('input:checkbox[name=nothing]').on('click',function() 
	{    
		if($(this).is(':checked')){
			var sequences = $(this).attr('id').split("_");
			var entrynumber = sequences[1].split("-")[0];
			$("#partial_"+sequences[1]).attr("disabled", "true");
			$("#available_"+sequences[1]).attr("disabled", "true");
			//$("#fm-gatp-button").attr("disabled", "false");
			var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+$(this).val()+"/"+entrynumber+"/nothing";
			$.ajax({
				url: ajaxURL, 		
				async: false,
				cache: false,	
				success: function (xmlDoc) {
					return true;
				}
			});
		}else{
			$("#partial_"+sequences[1]).attr("disabled", "false");
			$("#available_"+sequences[1]).attr("disabled", "false");
		}
	});
	$('input:checkbox[name=partial]').on('click',function() 
	{    
		if($(this).is(':checked')){
			var sequences = $(this).attr('id').split("_");
			var entrynumber = sequences[1].split("-")[0];
			$("#nothing_"+sequences[1]).attr("disabled", "true");
			//$("#fm-gatp-button").attr("disabled", "false");
			var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+$(this).val()+"/"+entrynumber+"/partial";
			$.ajax({
				url: ajaxURL, 		
				async: false,
				cache: false,	
				success: function (xmlDoc) {
					return true;
				}
			});
		}else{
			$("#nothing_"+sequences[1]).attr("disabled", "false");
		}
	});

	$('input:radio[name=dcradio]').on('click',function() 
	{    
		if($(this).is(':checked')){
			var id = $(this).attr('id');
			var ids = id.split("_");
			var entrynumber = ids[1].split("-");
			var tableId =$("#backorder_"+ids[1]) ;
				tableId.each(function (i) {
				$(this).find(".dcCheckbox_"+ids[1]).removeAttr('disabled');
			});
			$("#dc_"+entrynumber[0]+' input:radio[name=dcradio]').each(function (i) {
				var a = $(this).attr('id');
				if(a != id ){
					$(this).attr("disabled", "true");
				}
			});
	    }
	});

	$('#fm-gatp-a-Emergency').on('click',function(e)
	{
		//var isDCRadioSelected = false;
		var isDCCheckBoxSelected = false;
		var isAltDCSelectBoxSelected = false;
		var altDcSelectOptionValue = "";

		//$('input:radio[name=dcradio]').each(function () {
			//if($(this).is(':checked')){
				//isDCRadioSelected = true;
			//}
	    //});
		$('input:checkbox[name=available]').each(function () {
			if($(this).is(':checked')){
				isDCCheckBoxSelected = true;
			}
		});
		$('input:checkbox[name=partial]').each(function () {
			if($(this).is(':checked')){
				isDCCheckBoxSelected = true;
			}
		});
		$('input:checkbox[name=nothing]').each(function () {
			if($(this).is(':checked')){
				isDCCheckBoxSelected = true;
			}
		});
		$('input:checkbox[name=dcCheckbox]').each(function () {
			if($(this).is(':checked')){
				isDCCheckBoxSelected = true;
			}
		});
		$('select[name="altDcSelectBox"]').each(function() {
			altDcSelectOptionValue = $(this).children(":selected").val();
			if (altDcSelectOptionValue != 'Select') {
				isAltDCSelectBoxSelected = true;
			}
		});
		if (!isDCCheckBoxSelected && !isAltDCSelectBoxSelected) {
			e.preventDefault();
			alert("Please confirm the shipment.");
		}
	});

	$('#fm-gatp-a-Stock').on('click',function(e)
	{
		var isDCSelected = false;
		var stockDcSelectOptionValue = "";
		$('select[name="stockDcSelectBox"]').each(function (i) {
			stockDcSelectOptionValue = $(this).children(":selected").val();
			if (stockDcSelectOptionValue != 'dc_choose') {
				isDCSelected = true;
			}
		});

		if (!isDCSelected) {
			$('.s-fm-dc-ship-info').each(function (i) {
				var b=  $(this).html();
				if(b != ''){
					isDCSelected = true;
				}
			});
		}

		if (!isDCSelected) {
			e.preventDefault();
			alert("Please confirm the shipment");
		}
	});

	$('#btn-fm-rp-placeorder').on('click',function(e)
	{
		$(this).attr("disabled", "true");
		$('.btn-fm-rp-cart').attr("disabled", "true");
	});

	$('.a-fm-cart').on('click',function(e)
	{
		var orderType = $('#selectOrderType').val();
		if(orderType == "ordertype"){
			e.preventDefault();
			//alert("Please select the order type to continue");
			$("#selectOrderType").css('border', '2px solid red');
			$("#ordertyp_error").css('display', 'block');
			//Added as part if FAL-68 to focus on selectOrderType by saikumar
			$("html, body").animate({ scrollTop: 0 }, "slow");
			//closeModal();
		}
	});
	$('#selectOrderType').on('change', function() {

		$("#ordertyp_error").css('display', 'none');
		$("#selectOrderType").css('border', '1px solid #ccc');


		var orderTypecode = $(this).val();
		if(orderTypecode != "ordertype"){
			openModal();
		if(orderTypecode == "Stock"){
			document.getElementById('futureDate').style.display = 'block';
			$("#freefrieghtdiv").css('display', 'block');
		}else{
			document.getElementById('futureDate').style.display = 'none';
			$("#freefrieghtdiv").css('display', 'none');
		}
		if(orderTypecode == "pickup"){
			$("#tscpickuptop").css('display', 'block');
			$("#gatpflowtop").css('display', 'none');
			$("#tscpickupbottom").css('display', 'block');
			$("#gatpflowbottom").css('display', 'none');
			$("#freefrieghtdiv").css('display', 'none');

		}else{
			$("#tscpickuptop").css('display', 'none');
			$("#gatpflowtop").css('display', 'block');
			$("#tscpickupbottom").css('display', 'none');
			$("#gatpflowbottom").css('display', 'block');
		}
			
		var ajaxURL = ACC.ymmsearch.pathName + "USD/cart/ordertype/"+orderTypecode;
			$.ajax({
				url: ajaxURL, 		
				async: false,
				cache: false,	
				success: function (xmlDoc) {
					closeModal();
					return true;
				},
				error: function (err) {
					closeModal();
				}
			});
		}
	});

	$('#carrierAccountChange').on('change', function() {
		var carrierAccountCode = $(this).val();
		if(carrierAccountCode != null){
		var ajaxURL = ACC.ymmsearch.pathName + "USD/checkout/multi/carrierAccCode/"+carrierAccountCode;
			$.ajax({				
				url: ajaxURL, 		
				async: false,
				cache: false,	
				success: function (xmlDoc) {
					return true;
				}
			});
		}
	});
});
function removeDuplicates(obj){
	var usedNames = {};
	var id = obj.id;
	$("select[id='"+id+"'] > option").each(function () {
	    if(usedNames[this.text]) {
	        $(this).remove();
	    } else {
	        usedNames[this.text] = this.value;
	    }
	});
}
function selectCarrier(obj,id){
	var usedNames = {};
	var select_id = obj.value+"_"+id;
	document.getElementById('carrierAccCode').style.display = 'none';
	if(obj.value == 'FM'){
		document.getElementById('FM_'+id).style.display = 'block';
		document.getElementById('UPS_'+id).style.display = 'none';
		document.getElementById('Fedex_'+id).style.display = 'none';
	}else if(obj.value == 'UPS'){
		document.getElementById('FM_'+id).style.display = 'none';
		document.getElementById('UPS_'+id).style.display = 'block';
		document.getElementById('Fedex_'+id).style.display = 'none';
	}else{
		document.getElementById('FM_'+id).style.display = 'none';
		document.getElementById('UPS_'+id).style.display = 'none';
		document.getElementById('Fedex_'+id).style.display = 'block';
	}
}

function openModal() {
    document.getElementById('modal').style.display = 'block';
    document.getElementById('fade').style.display = 'block';
}

function closeModal() {
	document.getElementById('modal').style.display = 'none';
	document.getElementById('fade').style.display = 'none';
}


function submitPaymentForm(){
	 $("#hostOrderPostForm").submit();
}
function submitB2BPaymentForm() {
	var error_free = true;
	// Validate PO #.
	var element = $('input[name=p_poNumber]');
	var valid = element.hasClass("valid");
    var error_element = $("span", element.parent());
    if (!valid) {
    	error_element.removeClass("poerror").addClass("poerror_show"); 
    	error_free = false;
    } else {
    	error_element.removeClass("poerror_show").addClass("poerror");
    }
    
    if (valid) {
    	element.removeClass("invalid").addClass("valid");
    } else {
    	element.removeClass("valid").addClass("invalid");
    }

	var isCarrierSelected = false;
    var isShipMethodSelected = false;

    // Validate Carrier.
    $('select[name=carrier]').each(function (a) {
   		if ($(this).val() !="Carrier") {
			isCarrierSelected = true;
		} else {
			var id = $(this).attr('id').split("_")[1];
			var custPickupChecked = $('#CustPkupChkbx_' + id).is(':checked');
			if (!custPickupChecked) {
				isCarrierSelected = false;
				$("#chooseCarrier_error_"+id).css('display', 'block');
				$(this).css('border', '2px solid red');
			} else {
				isCarrierSelected = true;
			}
		}
    });
	
   	// Validate Shipping Method.
   	$('select[name=shippingmethod]').each(function (b) {
		if ($(this).val() != "SM") {
			isShipMethodSelected = true;
		} else {
			var id = $(this).attr('id').split("_")[1];
			var custPickupChecked = $('#CustPkupChkbx_' + id).is(':checked');
			if (!custPickupChecked) {
				isShipMethodSelected = false;
				$("#shippingmethod_error_" + id).css('display', 'block');
				$(this).css('border', '2px solid red');
			} else {
				isShipMethodSelected = true;
			}
		}
    });

    if (!error_free) {
    	element.focus();
    	event.preventDefault();
    } else {
    	if (!isCarrierSelected) {
    		event.preventDefault();
    	} else if (!isShipMethodSelected) {
    		event.preventDefault();
    	} else {
    		$("#paymentDetailsForm").submit();
    	}
    }
}

function popupresult() {
	var zipCode = $('#q').val();
	var ajaxURL = ACC.ymmsearch.pathName + "USD/store-finder/inputZipCodeSearchPost/"+zipCode;
	$.ajax({
			type: "POST",
			url : ajaxURL,success : function(xmlDoc) {
				var respDoc = $(xmlDoc).find("storeLocator").html();
				$("#xyz").html(respDoc);
				$("#xyz").find("script").each(function(i) {
					var s=  eval($(this).text());
				});               
					
			},
			error : function(e) {
			alert("Error = " + e);
			}
	});	
}

function validateVIN(){
	var vinValue = $("#vin").val();
	if((vinValue != null && vinValue != "") && vinValue.length >= 17){
		$("#vin").removeClass('inputError');
		$("#vinInput").hide();
		return  true;
	}else{
 		$("#vin").addClass('inputError');
		$("#vinInput").show();
		$("#vinNoData").val('');
		$("#vinNoData").hide();

		
		return false;
	}
}

function validateLicensePlate(){
	
	var licensePlateValue = $("#descFile").val();
	if(licensePlateValue != null && licensePlateValue != ""){
		licensePlateValue = licensePlateValue.trim();
		var licensePlateLength = licensePlateValue.split(" ").length;

		if(licensePlateLength > 2){

			$("#descFile").addClass('inputError');
			$("#licensePlateInput").show();
			$("#noDataLicensePlate").val('');
			$("#noDataLicensePlate").hide();
			return false; 
		}else{
			$("#descFile").removeClass('inputError');
			$("#licensePlateInput").hide();
			
			return true;
		}
		
	}



}
function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};

function setSubCategory(m) {

    var k = document.getElementById("dummysubcategory");
	
    for ( var h = 0; h < k.options.length; h++) {
    	if(k.options.length == 1){
    		 if ($.trim(k.options[h].text) == $.trim(m)) { 				
 				
                 return '<option value="'+k.options[h].value+'" selected>'+k.options[h].text+'</option>';                    }
    	}else{
		
                    if ($.trim(k.options[h].text) == $.trim(m)) {
				
				
                                   return '<option value="'+k.options[h].value+'">'+k.options[h].text+'</option>';                    }
    	}
    }
};
;

ACC.loyaltyproductdetails = {

              currentPath: window.location.href,
              pathName: "",
              currentCurrency: "USD",
             
              $SelectColor: $('#apparelColor'),
              $SelectSize: $('#apparelSize'),
                           
               options: "",
               url: "",
               setCurrentPath: function() {
                      try{
                             if(ACC.loyaltyproductdetails.currentPath.indexOf("/USD") != -1){
                                    ACC.loyaltyproductdetails.pathName = ACC.loyaltyproductdetails.currentPath.substring(0, ACC.loyaltyproductdetails.currentPath.lastIndexOf("/USD") + 1);
                             }else if(ACC.loyaltyproductdetails.currentPath.indexOf("?site") != -1){
                                    ACC.loyaltyproductdetails.pathName = ACC.loyaltyproductdetails.currentPath.substring(0, ACC.loyaltyproductdetails.currentPath.lastIndexOf("/?site") + 1)+ACC.loyaltyproductdetails.currentPath.substring(ACC.loyaltyproductdetails.currentPath.lastIndexOf("site=")+5,ACC.loyaltyproductdetails.currentPath.length)+"/en/";
                             }else{
                                    ACC.loyaltyproductdetails.pathName = window.location.href+"loyalty/en/";
                             }
                      }catch(e){
                             alert(e);
                      }
               },
               bindAll: function() {
                   with (ACC.loyaltyproductdetails) {
                          bindToChangeColor($SelectColor);
                          bindToChangeSize($SelectSize);
                   }
            },
            
            bindToChangeColor: function(apparelColor) {
            	apparelColor.change(function(event) {
            		//alert("**** bindToChangeColor **** ");
            		var url = "";
        			var selectedIndex = 0;
        			$("#apparelColor option:selected").each(function () {
        				url = $(this).attr('value');
        				selectedIndex = $(this).attr("index");
        			});
        			if (selectedIndex != 0) {
        				//alert("URL :: "+url+" selectedIndex :: "+selectedIndex);
        				window.location.href=url+"?variantSelected=true";
        	    }});
         },
         
         bindToChangeSize: function(apparelSize) {
        	 apparelSize.change(function(event) {
        		// alert("**** bindToChangeSize **** ");
         		var url = "";
     			var selectedIndex = 0;
     			$("#apparelSize option:selected").each(function () {
				if($(this).attr('value') != 'select'){
     					url = $(this).attr('value');
     					selectedIndex = $(this).attr("index");  
   				}else{
     					$('.prodDetailAddToCart').attr("disabled", "true");
     				}
     				
     			});
     			if (selectedIndex != 0) {
     				//alert("URL :: "+url+" selectedIndex :: "+selectedIndex);
     				window.location.href=url+"?variantSelected=true";
     	  }});
      },
               
                           
 
}; 

function ViewAllCategories(){
	
	var pathName = '';
	var win_url = window.location.href;
	
	try {
		if (win_url.indexOf("/USD") != -1) {
			pathName = win_url
			.substring(0, win_url.lastIndexOf("/USD") + 4);
			
		
		} else if (win_url.indexOf("?site") != -1) {
			pathName = win_url.substring(0,
					win_url.lastIndexOf("/?site") + 1)
					+ win_url.substring(win_url.lastIndexOf("site=") + 5,
							win_url.length) + "/en/USD/";

		}
		else {
			
			pathName = window.location.href+"loyalty/en/USD/";
		}

	} catch (e) {
		alert(e);
	}


	/* var categoryQuery = "category:Hats and Gloves:category:Electronics:category:Shirts:"+
				"category:Decals:category:Jackets and Hoodies:category:Accessories";	*/	
	var queryVal;
	if (pathName.indexOf("/USD/") != -1){
	 queryVal= pathName + "lsearch?q=:name-asc:"+"&text=#";
	}else{
 		queryVal= pathName + "/lsearch?q=:name-asc:"+"&text=#";
	}
	

	//alert("Final Query :: "+queryVal)

	window.location.href = queryVal;

}

function validateNumber(evt,obj) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
 
    
    if ((charCode > 31 && (charCode < 48 || charCode > 57) ) || charCode == 13) {
	
        return false;
    }
    if(charCode ==  48 && obj.value <= 0){
		 return false;
    }

    return true;
}

function validateReferalForm(formCount) {
	//alert(" formCount :: " + formCount);
	var flag = 0;
	for (var i = 0; i < formCount; i++) {
		if ($("#firstName" + i).val() != "" || $("#lastName" + i).val() != "") {
			//alert("fname :: " + $("#firstName" + i).val() + " lname :: "+ $("#lastName" + i).val());
			if ($("#email" + i).val() == "" || $("#email" + i).val() == null) {
				$("#email" + i).addClass('inputError');
				flag++;
			} else {
				//alert("email:: " + $("#email" + i).val());
				$("#email" + i).removeClass('inputError');
			}
		}
	}
	//alert("Final Flag :: " + flag);
	if (flag == 0) {
		//alert("Good to send");
		return true;
	} else {
		//alert("Empty Fields are there check !!!!");
		return false;
	}
}

$(document).ready(function() {
       ACC.loyaltyproductdetails.setCurrentPath();
       ACC.loyaltyproductdetails.bindAll(); 
	
    });


function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};;

ACC.storefinder = {
	map:"",
	
	bindAll: function ()
	{
		
		this.drawMap();
		this.getStoreMarkersImages();
		this.bindFindStoresNearMe();
	},
	
	drawMap: function(){
		
		if($('#map_canvas').length!=0)
		{
			var count = 0;
			var $e=$('#map_canvas')
			
			var centerPoint = new google.maps.LatLng($e.data("latitude"), $e.data("longitude"));
			var mapOptions = {
				zoom: 13,
				zoomControl: true,
				panControl: true,
				streetViewControl: false,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
				center: centerPoint

			}
			
			ACC.storefinder.map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
			
			if($e.data("southLatitude"))
			{


				this.setMapBouns();
				
				this.getStorePosition();
			}
			else{
				
				store =$e.data('stores');
				
				this.addStore(store.id,store.latitude,store.longitude, store.name, count+=1)
			}
		}
	},
	
	setMapBouns: function(){
		
		var $e=$('#map_canvas');
		var swBounds=new google.maps.LatLng($e.data("southLatitude"), $e.data("westLongitude"));
		var neBounds=new google.maps.LatLng($e.data("northLatitude"), $e.data("eastLongitude"));
		var bounds = new google.maps.LatLngBounds(swBounds, neBounds);
				ACC.storefinder.map.fitBounds(bounds);
		

				
	},
	
	
	
	getStorePosition: function(){
	
		var $e=$('#map_canvas');
		var count = 0;

			stores = $e.data('stores');	
		
			jQuery.each( stores, function( k, v ) {
				//alert(v.id)
			ACC.storefinder.addStore( v.id,v.latitude,v.longitude,v.name, count+=1 );
		});

	},
	
	addStore: function(i,latitude,longitude, name, count)
	
	{

			
		
		var marker = new google.maps.Marker({
			position: new google.maps.LatLng(latitude, longitude),
			map: ACC.storefinder.map,
			title: name,
                        icon:'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld='+ (count) +'|FF776B|000000'
			//icon: "http://maps.google.com/mapfiles/marker" + numTitle + ".png"
					});

		

		var infowindow = new google.maps.InfoWindow({
			content: name,
			disableAutoPan: true
		});
		google.maps.event.addListener(marker, 'click', function ()
		{
			infowindow.open(ACC.storefinder.map, marker);
		});
		
	

	},

	getStoreMarkersImages: function(){
	var count = 0;
	
		if($('.storeResultList').length!=0)
		{
			$(".storeMarker").each(function(i){
				

				$(this).attr("src",'//chart.googleapis.com/chart?chst=d_map_pin_letter&chld='+ (count) +'|FF776B|000000')
				count +=1;
			})
		}

		
	},
	
	
	bindFindStoresNearMe: function(){
		$(document).on("click", "#findStoresNearMe", function(e){
			e.preventDefault();
			var gps = navigator.geolocation;
			
			if (gps)
			{
				gps.getCurrentPosition(ACC.storefinder.positionSuccessStoresNearMe, function (error)
				{
					console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
				});
			}
		});
	},

	positionSuccessStoresNearMe: function (position)
	{
		$("#latitude").val(position.coords.latitude);
		$("#longitude").val(position.coords.longitude);
		$("#nearMeStorefinderForm").submit();
		return false;
	}
};

 
$(document).ready(function ()
{
	ACC.storefinder.bindAll();

});
;

ACC.uploadorder = {
	currentPath: window.location.href,
	pathName: "",
	currentCurrency: "USD",
	ajaxUrl: "USD/uploadOrder/",
	ajaxBOUrl: "USD/backOrder/",
	ajaxIOUrl: "USD/invoice/",
	ajaxOHUrl: "USD/order-status/",
	url: "",
	currentPath: window.location.href,
	pathName: "",
	$divUploadOrderStatus: $('#uploadOrderStatus'),
	$divBackOrders: $('#backOrdersBlock'),
	$divInvoiceHeader: $('#invoiceBlock'),
	$divOrderHeader: $('#orderStatusHeader'),
	$tabUploadorder: $('#uploadorder'),
	$tabBackOrders: $('#backOrders'),
	$tabInvoiceHeader: $('#invoices'),
	$tabOrderStatus: $('#orderStatus'),
	$uploadOrderFile: $('#uploadOrderFile'),
	$csrOrderUpload: $('#csrOrderUpload'),
	$formID: $('#upload'),
	$buttonUploadOrderSearch: $('#uploadOrderSearch_uo'),
	setCurrentPath: function() {
		try {
			if (ACC.uploadorder.currentPath.indexOf("iframe") != -1) {
				ACC.uploadorder.currentPath = ACC.uploadorder.currentPath.replace("iframe","");
			}

			if (ACC.uploadorder.currentPath.indexOf("/USD") != -1) {
				ACC.uploadorder.pathName = ACC.uploadorder.currentPath.substring(0, ACC.uploadorder.currentPath.lastIndexOf("/USD") + 1);
			} else if (ACC.uploadorder.currentPath.indexOf("?site") != -1) {
				ACC.uploadorder.pathName = ACC.uploadorder.currentPath.substring(0, ACC.uploadorder.currentPath.lastIndexOf("/?site") + 1)+ACC.uploadorder.currentPath.substring(ACC.uploadorder.currentPath.lastIndexOf("site=")+5,ACC.uploadorder.currentPath.length)+"/en/";
			} else {
				ACC.uploadorder.pathName = ACC.uploadorder.currentPath+"federalmogul/en/";
			}
		} catch (e){
			alert(e);
		}
	},

	bindAll: function() {
		with (ACC.uploadorder) {
			bindToUploadOrder($tabUploadorder);
			bindToBackOrders($tabBackOrders);
			bindToInvoiceHeader($tabInvoiceHeader);
			bindToOrderStatusHeader($tabOrderStatus);
			bindToCSRUploadOrder($csrOrderUpload);
		}
	},

	bindToUploadOrder: function(uploadorder) {
		uploadorder.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"orderstatus";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
		});
	},
	bindToCSRUploadOrder: function(csruploadorder) {
		csruploadorder.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"csrorderstatus";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
		});
	},
	bindToBackOrders: function(backorder) {
		backorder.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxBOUrl+"back-order";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divBackOrders);
		});
	},
	bindToInvoiceHeader: function(invoiceHeader) {
		invoiceHeader.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"invoice-header";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divInvoiceHeader);
		});
	},
	bindToOrderStatusHeader: function(orderHeader) {
		orderHeader.click(function(event) {
			ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"order-header";
			ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);
		});
	},
	postAJAX:function(div){
		openUploadOrderModal();
		//ACC.uploadorder.pathName
		$.ajax({
			url: ACC.uploadorder.Url,
			async: true,
			cache: false,
			type: 'POST',
			success: function (result) {
				closeUploadOrderModal();
				div.html(result);
				setTimeout(function () {
					ACC.uploadorder.bootstrapFn();
				}, 0);
			},
			error: function (err) {
				closeUploadOrderModal();
			}
		});
	},
};

var bootstrapFn = function() {
	ACC.uploadorder.setCurrentPath();
	ACC.uploadorder.bindAll();

	var nowTemp = new Date();
	var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate()+8, 0, 0, 0, 0);
	$('#date-picker-6, #date-picker-7').datepicker({
		flat: true,
		autoclose: true
	});

	$('#datepicker').datepicker({
		onRender: function(date) {
			return date.valueOf() < now.valueOf() ? 'disabled' : '';
		},
		flat: true,
		format:'mm-dd-yyyy',
		setValue: now,
		autoclose: true,
	}).on('changeDate', function(ev){
		$(this).datepicker('hide');
		var deliveryDate = $(this).val();
		if (deliveryDate != ''){
			var ajaxURL = ACC.uploadorder.pathName + "USD/cart/futuredelivery/"+deliveryDate;
			$.ajax({
				url: ajaxURL,
				async: true,
				cache: false,
				success: function (xmlDoc) {

				}
			});
		}
	});

	$('#datepicker').on('keypress',function(evt){
		evt.preventDefault();
	});
	$('#datepicker').change(
		function(event) {
			var rgexpDate = /^((0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01])[- /.](19|20)?[0-9]{2})*$/;
			var deliveryDate = $(this).val();

			var dd = now.getDate();
			var mm = now.getMonth()+1;

			dd = dd < 10 ? '0' + dd : dd;

			mm = mm < 10 ? '0' + mm : mm;

			var formatedDate = mm  + "-"+ dd  + "-" + now.getFullYear();

			var isValidDate = rgexpDate.test(deliveryDate);
			if (!isValidDate) {
				$('#datepicker').val(formatedDate);
			}

			deliveryDate = $(this).val();

			if (deliveryDate != '') {
				var ajaxURL = ACC.uploadorder.pathName + "USD/cart/futuredelivery/"+deliveryDate;
				$.ajax({
					url: ajaxURL,
					async: true,
					cache: false,
					success: function (xmlDoc) {

					}
				});
			}
		}
	);
};

ACC.uploadorder.bootstrapFn = bootstrapFn;

$(document).ready(bootstrapFn);

function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;

	if ((charCode > 31 && (charCode < 48 || charCode > 57) ) || charCode == 13) {
		return false;
	}
	return true;
}


function openUploadOrderModal() {
	document.getElementById('quick_modal').style.display = 'block';
	document.getElementById('quick_fade').style.display = 'block';
}

function closeUploadOrderModal() {
	document.getElementById('quick_modal').style.display = 'none';
	document.getElementById('quick_fade').style.display = 'none';
}

function editUploadOrderEntry(code){
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderEntry/"+code;
	var trOrderEntry = $("#EditOrderTableRow_"+code);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderEntry.html(result);
		}
	});
}

function closeUploadOrder(code){
	document.getElementById('EditOrderTableRow_'+code).style.display = 'none';
}

function saveUploadOrder(code){
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"uploadOrderSave/"+code;
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
}
function deleteUploadOrderEntry(entryno){
	var code = entryno.split("_") ;
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderEntryDelete/"+code[0]+"/"+entryno;
	var trOrderEntry = $("#EditOrderTableRow_"+code[0]);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderEntry.html(result);
		}
	});
}

function updateUploadOrderEntry(index,entryno){
	var code = entryno.split("_") ;
	var part = $('#part_'+index).val();
	var qty = $('#qty_'+index).val();
	var flag = $('#productFlag_'+index).val();
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderEntryEdit/"+code[0]+"/"+entryno+"/"+part+"/"+qty+"/"+flag;
	var trOrderEntry = $("#EditOrderTableRow_"+code[0]);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderEntry.html(result);
		}
	});
}

function showUploadOrderHistory(code){
	var trurl = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl +"uploadOrderHistory/"+code;
	var trOrderHistory = $("#EditOrderHistory_"+code);
	$.ajax({
		url: trurl,
		async: true,
		cache: false,
		type: 'POST',
		success: function (result) {
			trOrderHistory.html(result);
		}
	});
}

function deleteUploadOrder(code) {
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"uploadOrderDelete/"+code;
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
}

function OrderSearch() {
	var ponumber = $('#pruchaseOrder_uo').val();
	var confirmationOrder = $('#confirmationOrder_uo').val();
	var sdate = $('#date-picker-6').val();
	var edate = $('#date-picker-7').val();
	var status = $('#uploadOrrderStatus').val();
	var urlString ="?ponumber="+ponumber+"&sapordernumber="+confirmationOrder+"&sdate="+sdate+"&edate="+edate+"&status="+status;
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxUrl+"search"+urlString;
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divUploadOrderStatus);
}

function categoryAddToCart (partnumber, name, price){
	// /category/{partNumber}
	var errMSG = $("#error_"+partnumber);
	var qty = $("#qty_"+partnumber).val();
	var isQtyNumber = true;
	ACC.track.trackAddToCart(partnumber,qty,name,price);

	if (isNaN(qty)){
		errMSG.html("Please enter the proper quantity");
		errMSG.removeClass("poerror").addClass("poerror_show");
		$("#error_"+partnumber).css('display', 'block');
		isQtyNumber = false;
	}
	if (partnumber != '' && qty != '' && isQtyNumber && qty >=1){
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/category/"+partnumber+"/"+qty;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			contentType: 'text/xml',
			success: function (xmlDoc) {
				var respDoc = $(xmlDoc).find("msg").text() ;
				if (respDoc != null && respDoc.length > 1){
					if (respDoc.indexOf("multiple") != -1){
						var message = respDoc.split("@");
						respDoc = message[0];
					}
					if (respDoc.indexOf("~") != -1){
						var errMSGS = respDoc.split("~");
						errMSG.html(errMSGS[0]+"</br>"+errMSGS[1]);
					} else {
						errMSG.html(respDoc);
					}
					$("#err_"+partnumber).css('display', 'block');
					errMSG.removeClass("poerror").addClass("poerror_show");
					if ((respDoc.indexOf("multiple") != -1) || (respDoc.indexOf("superseded") != -1) || (respDoc.indexOf("matching") != -1)||((respDoc.indexOf("order quantity") != -1) && !(respDoc.indexOf("~") != -1))){
						location.href = ACC.ymmsearch.pathName +"USD/cart";
						ACC.minicart.refreshMiniCartCount();
					} else {
						var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/remove/"+partnumber;
						$.ajax({
							url: ajaxURL,
							async: true,
							cache: false,
							type: 'GET',
							success: function (xmlDoc) {
								var respDoc = $(xmlDoc).find("removePart").text().replace(/^\s+|\s+$/g, '');
								if (respDoc.indexOf("Sucess") != -1 ){
									ACC.minicart.refreshMiniCartCount();
								}
							}
						});
					}
				} else {
					location.href = ACC.ymmsearch.pathName +"USD/cart";
					ACC.minicart.refreshMiniCartCount();
				}
			},
			error: function (err) {
				errMSG.html("This part cannot be ordered. For additional information, please contact FMMP Customer Service at 800-334-3210.");
				errMSG.removeClass("poerror").addClass("poerror_show");
				$("#err_"+partnumber).css('display', 'block');
			}
		});
	}
}

function addtocart(obj, refreshParent){
	refreshParent = refreshParent || false;
	var sequenceId =obj.id.split("_") ;
	var id = "partNumber_"+sequenceId[1];
	var partnumber =$("#partNumber_"+sequenceId[1]).val();
	var hPartnumber =$("#hiddenPartNumber_"+sequenceId[1]).val();
	var qty = $("#qty_"+sequenceId[1]).val();
	var hQty = $("#hiddenQty_"+sequenceId[1]).val();
	var hPartFlag=$("#hiddenPartFlag_"+sequenceId[1]).val();
	var errMSG = $("#error_"+sequenceId[1]);
	var div = $("#div_"+sequenceId[1]);
	var isQtyNumber = true;
	if (isNaN(qty)) {
		errMSG.html("Please enter the proper quantity");
		errMSG.removeClass("poerror").addClass("poerror_show");
		$("#err_"+sequenceId[1]).css('display', 'block');
		isQtyNumber = false;
	}
	if (partnumber != '' && qty != '' && isQtyNumber && qty >=1) {
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		if (hPartnumber === '') {
			hPartnumber = null;
		}
		if (hPartFlag === '') {
			hPartFlag = null;
		}
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/add/"+partnumber+"/"+qty+"/"+hPartnumber+"/"+hQty+"/"+hPartFlag;
		if (ajaxURL.indexOf("iframe") != -1) {
			ajaxURL = ajaxURL
		      .replace("iframe","");
		}
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			contentType: 'text/xml',
			success: function (xmlDoc) {
				//var respDoc = $(xmlDoc).find("msg").html();
				var respDoc = $(xmlDoc).find("msg").text() ;
				//var rawPartNumber = $(xmlDoc).find("rawPartNumber").html().trim();
				var rawPartNumber = $(xmlDoc).find("rawPartNumber").text().replace(/^\s+|\s+$/g, '') ;
				var desc = $(xmlDoc).find("itemDescription").text() ;
				var itemPrice = $(xmlDoc).find("itemPrice").text() ;
				var partFlag = $(xmlDoc).find("partFlag").text().replace(/^\s+|\s+$/g, '');
				if (rawPartNumber != ''){
					$("#partNumber_"+sequenceId[1]).val(rawPartNumber);
					$("#hiddenPartNumber_"+sequenceId[1]).val(rawPartNumber);
				} else {
					$("#hiddenPartNumber_"+sequenceId[1]).val(partnumber);
				}
				if (desc != ''){
					$("#desc_"+sequenceId[1]).html(desc);
				}
				if (itemPrice !=''){
					$("#price_"+sequenceId[1]).html(itemPrice);
				} else {
					$("#price_"+sequenceId[1]).html("N/A");
				}
				$("#hiddenQty_"+sequenceId[1]).val(qty);
				$("#hiddenPartFlag_"+sequenceId[1]).val(partFlag);
				if (respDoc != null && respDoc.length > 1){
					if (respDoc.indexOf("multiple") != -1){
						var message = respDoc.split("@");
						respDoc = message[0];
						if (message[1].indexOf("~") != -1){
							var qtymsg = message[1].split("~");
							$("#qty_"+sequenceId[1]).val(qtymsg[0]);
							$("#hiddenQty_"+sequenceId[1]).val(qtymsg[0]);
							respDoc = respDoc + "~"+qtymsg[1];
						} else {
							$("#qty_"+sequenceId[1]).val(message[1]);
							$("#hiddenQty_"+sequenceId[1]).val(message[1]);
						}
					}
					if (respDoc.indexOf("matching") != -1){
						//var multiSel = $(xmlDoc).find("multiselect").children("div") ;
						var multiSize =$(xmlDoc).find("multipartsize").text();
						$("#multiMatch_"+sequenceId[1]).empty();
						var defaultopt = '<option value="choose">Choose</option>';
						if (multiSize>0)
						{
							for ( var int = 0; int < multiSize; int++) {
								var option = $(xmlDoc).find("multiopt"+int).text().split("|");
								defaultopt += '<option value="'+option[0]+'">'+option[1]+ ' </option>';
							}
							$("#multiMatch_"+sequenceId[1]).append(defaultopt);
							$("#multi_"+sequenceId[1]).css('display', 'block');
							$("#multiMatch_"+sequenceId[1]).css('display', 'block');
						}
					} else {
						$("#multi_"+sequenceId[1]).css('display', 'none');
						$("#multiMatch_"+sequenceId[1]).css('display', 'none');
					}
					if (respDoc.indexOf("~") != -1){
						var errMSGS = respDoc.split("~");
						if (errMSGS[0] == errMSGS[1]){
							errMSG.html(errMSGS[0]);
						} else {
							errMSG.html(errMSGS[0]+"</br>"+errMSGS[1]);
						}
					} else {
						errMSG.html(respDoc);
					}
					$("#err_"+sequenceId[1]).css('display', 'block');
					//div.after(multiSel);
					errMSG.removeClass("poerror").addClass("poerror_show");
					if ((respDoc.indexOf("multiple") != -1) || (respDoc.indexOf("superseded") != -1) || (respDoc.indexOf("vintage") != -1)||(respDoc.indexOf("Part not returnable") != -1)|| (respDoc.indexOf("matching") != -1)||((respDoc.indexOf("order quantity") != -1) && !(respDoc.indexOf("~") != -1))){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
						$("#QuickOrderButton0").removeClass('disabled');
						$("#QuickOrderButton1").removeClass('disabled');
						ACC.minicart.refreshMiniCartCount();
					} else {
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
						//Added as part of FAL-58 Fix that is disabled the proceed to check out button if none of the part is resolved correct.
						for (i=0 ;i<=sequenceId[1];i++){
							var partNumber = '';
							var errorText = '';
							var styleClassIndex=-1;
							try {
								partNumber = $("#partNumber_" + i).val();
								errorText = $('#error_' + i).text();
								styleClassIndex = $("#partNumber_" + i)[0].style.border.indexOf("green");
							} catch (err){
								var partNumber = '';
								var errorText = '';
								var styleClassIndex=-1;
							}


							if ((!(partNumber  == null || partNumber  == "") && (errorText  == ''))
								|| styleClassIndex >= 0){
								$("#QuickOrderButton0").removeClass('disabled');
								$("#QuickOrderButton1").removeClass('disabled');
								break;
							} else {
								$("#QuickOrderButton0").addClass('disabled');
								$("#QuickOrderButton1").addClass('disabled');

							}
						}
					}
					if (respDoc.indexOf("discontinued") != -1){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
						//Added as part of FAL-58 Fix that is disabled the proceed to check out button if none of the part is resolved correct.
						for (i=0 ;i<=sequenceId[1];i++){
							var partNumber = '';
							var errorText = '';
							var styleClassIndex=-1;
							try {
								partNumber = $("#partNumber_" + i).val();
								errorText = $('#error_' + i).text();
								styleClassIndex = $("#partNumber_" + i)[0].style.border.indexOf("green");
							} catch (err){
								var partNumber = '';
								var errorText = '';
								var styleClassIndex=-1;
							}


							if ((!(partNumber  == null || partNumber  == "") && (errorText  == ''))
								|| styleClassIndex >= 0){
								$("#QuickOrderButton0").removeClass('disabled');
								$("#QuickOrderButton1").removeClass('disabled');
								break;
							} else {
								$("#QuickOrderButton0").addClass('disabled');
								$("#QuickOrderButton1").addClass('disabled');
							}
						}
					}

				} else {
					$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
					ACC.minicart.refreshMiniCartCount();
					errMSG.removeClass("poerror_show").addClass("poerror");
					//errMSG.css('display', 'none');
					//$("#err_"+sequenceId[1]).css('display', 'none');
					$("#QuickOrderButton0").removeClass('disabled');
					$("#QuickOrderButton1").removeClass('disabled');

				}
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';

				// Send a nav refresh message to the parent window (should be triggered if we're in an iframe) so that cart on AEM nav can update...
				if (refreshParent) {
					window.parent.postMessage("hybrisCartRefresh" + new Date().getTime(), '*');
				}

			},
			error: function (err) {
				$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
				errMSG.html("This part cannot be ordered. For additional information, please contact FMMP Customer Service at 800-334-3210.");
				errMSG.removeClass("poerror").addClass("poerror_show");
				$("#err_"+sequenceId[1]).css('display', 'block');
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';

				//Added as part of FAL-58 Fix that is disabled the proceed to check out button if none of the part is resolved correct.

				for (i=0 ;i<=sequenceId[1];i++){

					var partNumber = '';
					var errorText = '';
					var styleClassIndex=-1;
					try {
						partNumber = $("#partNumber_" + i).val();
						errorText = $('#error_' + i).text();
						styleClassIndex = $("#partNumber_" + i)[0].style.border.indexOf("green");
					} catch (err){
						var partNumber = '';
						var errorText = '';
						var styleClassIndex=-1;
					}


					if ((!(partNumber  == null || partNumber  == "") && (errorText  == ''))
						|| styleClassIndex >= 0){
						$("#QuickOrderButton0").removeClass('disabled');
						$("#QuickOrderButton1").removeClass('disabled');
						break;
					} else {
						$("#QuickOrderButton0").addClass('disabled');
						$("#QuickOrderButton1").addClass('disabled');

					}
				}
				//alert(err.message);
			}
		});
	} else {
		if (partnumber == '' && hPartnumber != '' ){
			var dPartNumber = hPartnumber;
			if (hPartFlag != ''){
				dPartNumber = hPartFlag.trim()+hPartnumber;
			}
			var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/remove/"+dPartNumber;
			$.ajax({
				url: ajaxURL,
				async: true,
				cache: false,
				type: 'GET',
				success: function (xmlDoc) {
					var respDoc = $(xmlDoc).find("removePart").text().replace(/^\s+|\s+$/g, '');
					if (respDoc.indexOf("Sucess") != -1 ){
						ACC.minicart.refreshMiniCartCount();
						errMSG.removeClass("poerror_show").addClass("poerror");
						$("#partNumber_"+sequenceId[1]).css('border', '1px solid #ccc');
						$("#partNumber_"+sequenceId[1]).val("");
						$("#qty_"+sequenceId[1]).val("");
						$("#hiddenQty_"+sequenceId[1]).val("0");
						$("#hiddenPartNumber_"+sequenceId[1]).val("");
						$("#hiddenPartFlag_"+sequenceId[1]).val("");

						// Send a nav refresh message to the parent window (should be triggered if we're in an iframe) so that cart on AEM nav can update...
						if (refreshParent) {
							window.parent.postMessage("hybrisCartRefresh" + new Date().getTime(), '*');
						}
					}
				}
			});
		}
	}
}

function onMultiSelect(obj){
	var partDetails = obj.value;
	var partDetails =obj.value.split("~") ;
	var id ;
	var partnumber =partDetails[0];
	var productFlag =partDetails[1];
	var brandState = partDetails[2];
	$(".partNumber").each(function () {
		if ($(this).val() == partnumber ){
			id = $(this).attr('id');
		}
	});
	if (brandState != ''){
		brandState='a';
	}
	var sequenceId =obj.id.split("_") ;
	var errMSG = $("#error_"+sequenceId[1]);
	var div = $("#div_"+sequenceId[1]);
	var qty =$("#qty_"+sequenceId[1]).val();
	var isQtyNumber = true;
	if (isNaN(qty)){
		errMSG.html("Please enter the proper quantity");
		errMSG.removeClass("poerror").addClass("poerror_show");
		$("#err_"+sequenceId[1]).css('display', 'block');
		isQtyNumber = false;
	}
	if (partnumber != '' && qty != '' && isQtyNumber && qty >=1){
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/addmultimatch/"+partnumber+"/"+qty+"/"+productFlag+"/"+brandState;
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				//var respDoc = $(xmlDoc).find("msg").html();
				var respDoc = $(xmlDoc).find("msg").text() ;
				//var rawPartNumber = $(xmlDoc).find("rawPartNumber").html().trim();
				var rawPartNumber = $(xmlDoc).find("rawPartNumber").text().replace(/^\s+|\s+$/g, '') ;
				var desc = $(xmlDoc).find("itemDescription").text() ;
				var itemPrice = $(xmlDoc).find("itemPrice").text() ;
				var partFlag = $(xmlDoc).find("partFlag").text().replace(/^\s+|\s+$/g, '');
				if (rawPartNumber != ''){
					$("#partNumber_"+sequenceId[1]).val(rawPartNumber);
					$("#hiddenPartNumber_"+sequenceId[1]).val(rawPartNumber);
				} else {
					$("#hiddenPartNumber_"+sequenceId[1]).val(partnumber);
				}
				$("#hiddenPartFlag_"+sequenceId[1]).val(partFlag);
				if (desc != ''){
					$("#desc_"+sequenceId[1]).html(desc);
				}
				if (itemPrice !=''){
					$("#price_"+sequenceId[1]).html(itemPrice);
				} else {
					$("#price_"+sequenceId[1]).html("N/A");
				}
				$("#hiddenQty_"+sequenceId[1]).val(qty);
				if (respDoc != null && respDoc.length > 1){
					if (respDoc.indexOf("multiple") != -1){
						var message = respDoc.split("@");
						respDoc = message[0];
						if (message[1].indexOf("~") != -1){
							var qtymsg = message[1].split("~");
							$("#qty_"+sequenceId[1]).val(qtymsg[0]);
							$("#hiddenQty_"+sequenceId[1]).val(qtymsg[0]);
							respDoc = respDoc + "~"+qtymsg[1];
						} else {
							$("#qty_"+sequenceId[1]).val(message[1]);
							$("#hiddenQty_"+sequenceId[1]).val(message[1]);
						}
					}
					if (respDoc.indexOf("matching") != -1){
						//var multiSel = $(xmlDoc).find("multiselect").children("div") ;
						var multiSize =$(xmlDoc).find("multipartsize").text();
						var defaultopt = '<option value="choose">Choose</option>';
						if (multiSize>0)
						{
							for ( var int = 0; int < multiSize; int++) {
								var option = $(xmlDoc).find("multiopt"+int).text().split("|");
								defaultopt += '<option value="'+option[0]+'">'+option[1]+ ' </option>';
							}
							$("#multiMatch_"+sequenceId[1]).append(defaultopt);
							$("#multi_"+sequenceId[1]).css('display', 'block');
						}
					} else {
						$("#multi_"+sequenceId[1]).css('display', 'none');
						$("#multiMatch_"+sequenceId[1]).css('display', 'none');
					}
					if (respDoc.indexOf("~") != -1){
						var errMSGS = respDoc.split("~");
						if (errMSGS[0] == errMSGS[1]){
							errMSG.html(errMSGS[0]);
						} else {
							errMSG.html(errMSGS[0]+"</br>"+errMSGS[1]);
						}
					} else {
						errMSG.html(respDoc);
					}
					//div.after(multiSel);
					errMSG.removeClass("poerror").addClass("poerror_show");
					if ((respDoc.indexOf("multiple") != -1) || (respDoc.indexOf("superseded") != -1) ||(respDoc.indexOf("vintage") != -1) || (respDoc.indexOf("matching") != -1)||((respDoc.indexOf("order quantity") != -1) && !(respDoc.indexOf("~") != -1))){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
						ACC.minicart.refreshMiniCartCount();
					} else {
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
					}
					if (respDoc.indexOf("discontinued") != -1){
						$("#partNumber_"+sequenceId[1]).css('border', '2px solid red');
					}
				} else {
					$("#partNumber_"+sequenceId[1]).css('border', '2px solid green');
					errMSG.removeClass("poerror_show").addClass("poerror");
					errMSG.css('display', 'none');
					ACC.minicart.refreshMiniCartCount();
				}
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';
				$(obj).css('display', 'none');
			}
		});
	}
}


function showInvoiceDetail(ioNumber){
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"invoice-details/"+ioNumber;
	ACC.uploadorder.postAJAX($('#orderHistory'));
}

var _validFileExtensions = [".txt", ".xls",".xlsx"];

function ValidateFileExtension(oInput) {
	if (oInput.type == "file") {
		var sFileName = oInput.value;
		if (sFileName.length > 0) {
			var blnValid = false;
			for (var j = 0; j < _validFileExtensions.length; j++) {
				var sCurExtension = _validFileExtensions[j];
				if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
					blnValid = true;
					break;
				}
			}
			if (!blnValid) {
				alert("Sorry, " + sFileName + " is invalid, allowed extensions are: " + _validFileExtensions.join(", "));
				oInput.value = "";
				return false;
			}
		}
	}
	return true;
}

function addRows(count){
	//var count =$('#addrowtext').val();
	var lastInuptEle = $(".table tr:last");
	var len = $(".table tr:last ").index() + 1;
	var addcount= parseInt(len)+1;
	for (var j = 0; j <count; j++) {
		var myRow = '<tr id="tr_'+parseInt(addcount+j)+'"> <td class="nameCol itemsCol"><div class=""><input id="hiddenPartNumber_'+parseInt(addcount+j)+'" class="hiddenPartNumber form-control" type="hidden" value=""><input id="hiddenPartFlag_'+parseInt(addcount+j)+'" class="hiddenPartFlag form-control" type="hidden" value=""><input type="text" onchange="addtocart(this, true);" id="partNumber_'+parseInt(addcount+j)+'" placeholder="Enter Part Number" class="partNumber form-control visible-lg-inline visible-md-inline visible-sm-inline width165"></div><div class="searchStatus hide"><span class="fa fa-check-circle"></span></div></td><td class="text-center"><input id="hiddenQty_'+parseInt(addcount+j)+'" class="hiddenQty form-control" type="hidden" value="0"><input type="text" id="qty_'+parseInt(addcount+j)+'" onchange="addtocart(this, true);" placeholder="0"  class="form-control visible-lg-inline visible-md-inline visible-sm-inline width60"></td> <td class="descCol"><div id="desc_'+parseInt(addcount+j)+'"></div></td> <td class="text-right"><h4 class="DINWebBold"><div id="price_'+parseInt(addcount+j)+'"></div></h4></td> <td class="text-right"><div><a id="inventory_'+parseInt(addcount+j)+'" class="inventoryRow deleteRow" href="#" onclick="CheckInventory(this);"><span class="fa fa-exchange fa-fw fm_fntRed"></span></a><a id="delete_'+parseInt(addcount+j)+'" class="deleteRow" href="#" onclick="RemoveProduct(this);"><span class="fa fa-trash-o fa-fw fm_fntRed"></span></a></div></td></tr>'+'<tr><td colspan="5" class="quicOrderPoerrorTd"><div style="display: none;" id="err_'+parseInt(addcount+j)+'" class="topMargn_20 "><div class="" id="div_'+parseInt(addcount+j)+'"><div class="poerror_show topMargn_25 quicOrderPoerror_show" id="error_'+parseInt(addcount+j)+'"></div><div style="display: none;" id="multi_'+parseInt(addcount+j)+'" class="topMargn_10"><select id="multiMatch_'+parseInt(addcount+j)+'" class="form-control width165" style="display: none;"	onchange="javascript:onMultiSelect(this)"></select></div></div></div></td></tr>';
		$(".table tr:last").after(myRow);
	}
}

function addTopRows(){
	var count =$('#addrowtoptext').val();
	addRows(count);
}

function addButtomRows(){
	var count =$('#addrowbuttomtext').val();
	addRows(count);
}

function RemoveProduct(obj){
	$('#quickload').trigger('reset');
	$('#quickOrderFile').val("");

	var input = $("#quickOrderFile");
	input.replaceWith(input.val('').clone(true));

	var sequenceId =obj.id.split("_") ;
	var id = "partNumber_"+sequenceId[1];
	var partnumber =$("#partNumber_"+sequenceId[1]).val();
	var errMSG = $("#error_"+sequenceId[1]);
	var qty =  $("#qty_"+sequenceId[1]);
	var div = $("#div_"+sequenceId[1]);
	if (partnumber != ''  ){
		$("#partNumber_"+sequenceId[1]).val("");
		$("#qty_"+sequenceId[1]).val("");
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/orderupload/remove/"+partnumber;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				var respDoc = $(xmlDoc).find("removePart").text().replace(/^\s+|\s+$/g, '');
				document.getElementById('quick_modal').style.display = 'none';
				document.getElementById('quick_fade').style.display = 'none';
				if (respDoc.indexOf("Sucess") != -1 ){
					ACC.minicart.refreshMiniCartCount();
					$("#tr_"+sequenceId[1]).remove();
					$("#err_"+sequenceId[1]).remove();
					//top.location.href=top.location.href;
				}
			}
		});

	}
}

function CheckInventory(obj){
	var sequenceId =obj.id.split("_") ;
	var id = "partNumber_"+sequenceId[1];
	var partnumber =$("#partNumber_"+sequenceId[1]).val();
	var partFlag=$("#hiddenPartFlag_"+sequenceId[1]).val().replace(/^\s+|\s+$/g, '');
	var qty =  $("#qty_"+sequenceId[1]).val();
	if (partnumber != ''  ){
		if (partFlag == ''){
			partFlag ='NA';
		}
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/inventory/quick/"+partnumber+"/"+qty+"/"+partFlag.trim();
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				$('#inventoryPopup').html(xmlDoc);
				$('#inventoryPopup').css('display', 'block');
				document.getElementById('quick_modal').style.display = 'none';
			}
		});
	}
}

function CheckPickupInventory(storeId,chkInvOnly){
	if (storeId != ''  ){
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/inventory/pickup/"+storeId+"/"+chkInvOnly+"/pickup";
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				if (chkInvOnly =='true'){
					$('#inventoryPopup').html(xmlDoc);
					$('#inventoryPopup').css('display', 'block');
					document.getElementById('quick_modal').style.display = 'none';
				} else {
					setTimeout(function() {
						location.href = ACC.ymmsearch.pathName+"USD/cart/checkout";
					}, 200);
				}
			}
		});
	}
}

function submitTSCPaymentForm(){
	var error_free=true;
	var element=$('input[name=p_poNumber]');
	var valid=element.hasClass("valid");
	var error_element=$("span", element.parent());
	if (!valid){error_element.removeClass("poerror").addClass("poerror_show"); error_free=false;}
	else {error_element.removeClass("poerror_show").addClass("poerror");}
	if (valid){element.removeClass("invalid").addClass("valid");}
	else {element.removeClass("valid").addClass("invalid");}

	if (!error_free){
		element.focus();
		event.preventDefault();
	} else {
		$("#paymentDetailsForm").submit();
	}

}

function setstores(storeId) {
	if (storeId != null ) {
		var ajaxURL = ACC.ymmsearch.pathName + "USD/cart-store-finder/setStore/"+storeId;
		$.ajax({
			url : ajaxURL,
			success : function(xmlDoc) {
				CheckPickupInventory(storeId,'false');
			},
			error : function(e) {
				alert("Error" + e);
			}
		});
	}
}

function CheckHomeInventory(obj,isTSCPickup){
	var partnumberList = '' ;
	for ( var int = 0; int < 3; int++) {
		var partnumber =$("#partNumber_"+int).val();
		var partFlag=$("#hiddenPartFlag_"+int).val().replace(/^\s+|\s+$/g, '');
		var qty =  $("#qty_"+int).val();
		if (partFlag == ''){
			partFlag ='NA';
		}
		if (partnumber != '' && qty !=''){
			if (int == 0){
				partnumberList = partnumber.trim()+"|"+qty.trim()+"|"+partFlag.trim()+"~";
			} else {
				partnumberList += partnumber.trim()+"|"+qty.trim()+"|"+partFlag.trim()+"~";
			}
		}
	}

	if (partnumberList != ''  ){
		document.getElementById('quick_modal').style.display = 'block';
		document.getElementById('quick_fade').style.display = 'block';
		var ajaxURL = ACC.uploadorder.pathName + "USD/inventory/home/"+partnumberList+"?TSCPickup="+isTSCPickup;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			type: 'GET',
			success: function (xmlDoc) {
				$('#inventoryPopup').html(xmlDoc);
				$('#inventoryPopup').css('display', 'block');
				document.getElementById('quick_modal').style.display = 'none';
			}
		});
	}
}
function inventoryClose(){
	$('#inventoryPopup').css('display', 'none');
	document.getElementById('quick_fade').style.display = 'none';
}

$('.inventoryClose').click( function() {
	$('#inventoryPopup').css('display', 'none');
	document.getElementById('quick_fade').style.display = 'none';
});

$('#quick_fade').click(function() {
	$('#inventoryPopup').css('display', 'none');
	$('#brandPrefix').css('display', 'none');
	document.getElementById('quick_modal').style.display = 'none';
	document.getElementById('quick_fade').style.display = 'none';
});


function displayCarrier(obj,ind,dc,country){
	document.getElementById('carrierAccCode_'+ind).style.display = 'none';
	var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/carrier/"+dc;
	$.ajax({
		url: ajaxURL,
		async: true,
		cache: false,
		type: 'POST',
		success: function (xmlDoc) {
			$(obj).empty();
					//var respDoc = $(xmlDoc).find("carrier").html();
			var carrierSize =$(xmlDoc).find("carrierSize").text();
			var defaultCarrierCollect=$(xmlDoc).find("defaultCarrierCollect").text().replace(/^\s+|\s+$/g, '');
			var defaultCACarrierCollect=$(xmlDoc).find("defaultCACarrierCollect").text().replace(/^\s+|\s+$/g, '');
			var defaultopt = '<option value="Carrier">Please Select Carrier</option>';
			if (carrierSize>0)
			{
				for ( var int = 0; int < carrierSize; int++) {
					var option = $(xmlDoc).find("carrieropt"+int).text().split("-");
					if (option[1] != "FM" && option[1] != "OTH") {
						if ((country =="US") || (country =="CA" && option[1] == defaultCACarrierCollect ) ){
							defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
						}

					}
				}
			}
			$(obj).append(defaultopt);
		}
	});
}


function displayStockCarrier(obj,ind,dc){
	document.getElementById('carrierAccCode_'+ind).style.display = 'none';
	var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/carrier/"+dc;
	$.ajax({
		url: ajaxURL,
		async: true,
		cache: false,
		type: 'POST',
		success: function (xmlDoc) {
			$(obj).empty();
					//var respDoc = $(xmlDoc).find("carrier").html();
			var carrierSize =$(xmlDoc).find("carrierSize").text();
			var defaultopt = '<option value="Carrier">Please Select Carrier</option>';
			if (carrierSize>0)
			{
				for ( var int = 0; int < carrierSize; int++) {
					var option = $(xmlDoc).find("carrieropt"+int).text().split("-");
					if (option[1] == "OTH" || option[1] == "FM"){
						defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
					}
				}
			}
			$(obj).append(defaultopt);
		}
	});
}

function displayShippingMethod(obj,id,dc,country){
	var usedNames = {};
	var smSelect = $('#sm_'+id);
	$("#chooseCarrier_error_"+id).css('display', 'none');
	$("#chooseCarrier_"+id).css('border', '1px solid #ccc');
	var cc = $('#chooseCarrier_'+id).val();
	var ccName = $('#chooseCarrier_'+id).find("option:selected").text();
	var isCarrierCollect = false;

	if (ccName.indexOf("Collect") != -1){
		isCarrierCollect = true;
	}

	if (cc != null){
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shipmethod/"+dc+"/"+cc+"/"+country;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			success: function (xmlDoc) {
				smSelect.empty();
				var smSize =$(xmlDoc).find("smSize").text();
				var defaultopt = '<option value="SM">Please Select Shipping Method</option>';
				if (smSize>0)
				{
					for ( var int = 0; int < smSize; int++) {
						var option = $(xmlDoc).find("smopt"+int).text().split("-");
						if (isCarrierCollect && option[0].indexOf("Collect") != -1){
							defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
						} else if (!isCarrierCollect && option[0].indexOf("Collect") == -1){
							defaultopt += '<option value="'+option[1].replace(/^\s+|\s+$/g, '')+'">'+option[0].replace(/^\s+|\s+$/g, '')+ ' </option>';
						}
					}
				}
				smSelect.append(defaultopt);
				removeDuplicates(id);
				smSelect.removeAttr('disabled');
			}
		});
	}
}

function removeDuplicates(id){
	var usedNames = {};
	$("select[id=sm_"+id+"] > option").each(function () {
		if (usedNames[this.text]) {
			$(this).remove();
		} else {
			usedNames[this.text] = this.value;
		}
	});
}

function saveShippingMethod(obj,id,dc) {

	$("#shippingmethod_error_"+id).css('display', 'none');
	$("#sm_"+id).css('border', '1px solid #ccc');
	var sm = obj.value;
	var cc = $('#chooseCarrier_'+id).val();
	var ccName= $('#chooseCarrier_'+id).find("option:selected").text();
	var ccAccount =$('#carrierAccountChange_'+id).val();
	var ccacc =  $(obj).find("option:selected").text();

	if (ccAccount == null || ccAccount == '') {
		ccAccount ='NA';
	} else {
		if (ccAccount == 'NA') {
			ccAccount ='NA';
		}
	}

	if (cc != null && sm != null) {
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shippingmethod/"+dc+"/"+cc+"/"+sm+"/"+ccAccount+"/"+ccName+"/"+ccacc;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			contentType: 'text/xml',
			success: function (xmlDoc) {
				var loggedInUserType = $(xmlDoc).find("loggedInUserType").text();
				var dcCode = $(xmlDoc).find("dcCode").text();
				var totalItemPriceForDC = $(xmlDoc).find("totalItemPriceForDC").text();
				var totalFreightCostForDC = $(xmlDoc).find("totalFreightCostForDC").text();
				var totalDCValue = $(xmlDoc).find("totalDCValue").text();
				var totalItemPriceForAllDCs = $(xmlDoc).find("totalItemPriceForAllDCs").text();
				var totalFreightCostForAllDCs = $(xmlDoc).find("totalFreightCostForAllDCs").text();
				var totalOrderValue = $(xmlDoc).find("totalOrderValue").text();

				if (totalFreightCostForDC > 0) {
					$("#shipCostForDC_" + dc).html("$" + totalFreightCostForDC);
				} else {
					$("#shipCostForDC_" + dc).html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>N/A</h5>");
				} else {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>$" + totalDCValue + "</h5>");
				}

				if (totalFreightCostForAllDCs > 0) {
					$("#shipCostSummary").html("$" + totalFreightCostForAllDCs);
				} else {
					$("#shipCostSummary").html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#estTotalSummary").html("N/A");
				} else {
					$("#estTotalSummary").html("$" + totalOrderValue + "**");
				}
			}
		});
	}

	if (ccacc.indexOf("Collect") != -1) {
		document.getElementById('carrierAccCode_'+id).style.display = 'block';
	} else {
		document.getElementById('carrierAccCode_'+id).style.display = 'none';
	}

}

function saveShippingMethodOnPkupChkboxClick(custPickupCheckbox, id, dc) {
	var carrierCd    = '';
	var carrierName  = '';
	var shipMthdCd   = '';
	var shipMthdName = '';

	if (custPickupCheckbox.checked) {
		carrierCd    = 'OTH';
		carrierName  = 'Other';
		shipMthdCd   = 'PKUP';
		shipMthdName = 'Pickup';
	} else {
		carrierCd    = 'UPS';
		carrierName  = 'UPS';
		shipMthdCd   = 'GRD';
		shipMthdName = 'Ground';
	}

	var carrierAcctCd = $('#carrierAccountChange_' +  id).val();

	if (carrierAcctCd == null || carrierAcctCd == '') {
		carrierAcctCd ='NA';
	} else {
		if (carrierAcctCd == 'NA') {
			carrierAcctCd ='NA';
		}
	}

	if (carrierCd != null && shipMthdCd != null) {
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shippingmethod/" + dc + "/" + carrierCd + "/" + shipMthdCd + "/" + carrierAcctCd + "/" + carrierName + "/" + shipMthdName;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			success: function (xmlDoc) {
				var loggedInUserType = $(xmlDoc).find("loggedInUserType").text();
				var dcCode = $(xmlDoc).find("dcCode").text();
				var totalItemPriceForDC = $(xmlDoc).find("totalItemPriceForDC").text();
				var totalFreightCostForDC = $(xmlDoc).find("totalFreightCostForDC").text();
				var totalDCValue = $(xmlDoc).find("totalDCValue").text();
				var totalItemPriceForAllDCs = $(xmlDoc).find("totalItemPriceForAllDCs").text();
				var totalFreightCostForAllDCs = $(xmlDoc).find("totalFreightCostForAllDCs").text();
				var totalOrderValue = $(xmlDoc).find("totalOrderValue").text();

				if (totalFreightCostForDC > 0) {
					$("#shipCostForDC_" + dc).html("$" + totalFreightCostForDC);
				} else {
					$("#shipCostForDC_" + dc).html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>N/A</h5>");
				} else {
					$("#subtotalWithShipCostForDC_" + dc).html("<h5>$" + totalDCValue + "</h5>");
				}


				if (totalFreightCostForAllDCs > 0) {
					$("#shipCostSummary").html("$" + totalFreightCostForAllDCs);
				} else {
					$("#shipCostSummary").html("N/A");
				}

				if (loggedInUserType == "ShipTo") {
					$("#estTotalSummary").html("N/A");
				} else {
					$("#estTotalSummary").html("$" + totalOrderValue + "**");
				}
			}
		});
	}
}

function saveCarrierAccCode(obj,id,dc){

	var cc = $('#chooseCarrier_'+id).val();
	var ccName= $('#chooseCarrier_'+id).find("option:selected").text();
	var ccAccount =obj.value;
	var sm =  $('#sm_'+id).val();
	var ccacc =  $('#sm_'+id).find("option:selected").text();
	if (ccAccount == null || ccAccount == ''){
		ccAccount ='dummy';
	} else {
		if (ccAccount == 'NA'){
			ccAccount ='dummy';
		}
	}
	if (cc != null && sm != null){
		var ajaxURL = ACC.uploadorder.pathName + "USD/checkout/multi/shippingmethod/"+dc+"/"+cc+"/"+sm+"/"+ccAccount+"/"+ccName+"/"+ccacc;
		$.ajax({
			url: ajaxURL,
			async: true,
			cache: false,
			success: function (xmlDoc) {

			}
		});
	}
}

$('.qty').on('keypress',function(evt){
	var charCode = (evt.which) ? evt.which : event.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)){
		evt.preventDefault();     // Prevent character input
	}
});


$('.a-fm-deliverytag').on('click',function(e)
{
	var isCarrierSelected = false;
	var isShipMethodSelected = false;
	$('select[name=carrier]').each(function (a) {
		if ($(this).val() !="Carrier"){
			isCarrierSelected = true;
		} else {
			isCarrierSelected = false;
		}
	});

	$('select[name=shippingmethod]').each(function (b) {
		if ($(this).val() !="SM"){
			isShipMethodSelected = true;
		} else {
			isShipMethodSelected = false;
		}
	});

	if (!isCarrierSelected){
		e.preventDefault();
		alert("Select the carrier to continue ");
	}
	else if (!isShipMethodSelected){
		e.preventDefault();
		alert("Select the shipping method to continue ");
	}
});


function onSelectDC(obj)
{
	//nothing_DS-${cartData.code}-${entry.entryNumber}-${dccount}
	var optSelected = obj.value.split("_") ;
	var backorderPolicy = optSelected[0];
	var availDC = optSelected[1];
	if (backorderPolicy != "dc"){
		var dcDetails = availDC.split("-");
		var entrynumber = dcDetails[2];
		var dcCode= dcDetails[3];
		var dcInfo = $(obj).find("option:selected").text().split("ship from")[1];
		var dcName= dcInfo.substring(0, dcInfo.lastIndexOf("("));
		var quantity = dcInfo.split("(")[1].split("available")[0];
		var res = dcName.replace(',',', ');
		var info= '<p><b>Shipping location :</b>'+ res +' ,</p><p> <b>Available Quantity :</b> '+quantity +'</p>';
		var backOrderInfo = "</br><span class='fm_fntRed'><b>No inventory available. Only back order all available.</b></span>";
		if (backorderPolicy  == "nothing"){
			info += backOrderInfo;
		}
		//$('#s-fm-dc-ship-info-'+entrynumber).html(info);
		//$(obj).css('display', 'none');
		if (backorderPolicy  != "partial"){
			//$('#s-fm-dc-ship-info-'+entrynumber).css('display', 'block');
			addDistrubutionCenter(availDC,entrynumber,backorderPolicy);
		} else {
			//$("#sel-fm-stock-partial-"+entrynumber+"-"+dcCode).css('display', 'block');
		}
	}
}

function onPartialSelect(obj,entrynumber){
	var optSelected =obj.value.split("_") ;
	var backorderPolicy = optSelected[0];
	var availDC = optSelected[1];
	$(obj).css('display', 'none');
	$('#s-fm-dc-ship-info-'+entrynumber).css('display', 'block');
	addDistrubutionCenter(availDC,entrynumber,backorderPolicy);
}

function addDistrubutionCenter(availDC,entrynumber,backorderPolicy){
	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+availDC+"/"+entrynumber+"/"+backorderPolicy;
	$.ajax({
		url: ajaxURL,
		async: true,
		cache: false,
		success: function (xmlDoc) {

		}
	});
}

function searchOrders(){

	var PO_Number = $('#pruchaseOrder_no').val();
	var confirma_Number = $('#confirmationOrder_no').val();
	var start_Date = $('#date-picker-1').val();
	var end_Date = $('#date-picker-2').val();
	var Status= $("select[id='orderStatus'] option:selected").val();



	var urlString ="?ponumber="+encodeURIComponent(PO_Number)+"&invoice="+encodeURIComponent(confirma_Number)+"&sdate="+start_Date+"&edate="+end_Date+"&status="+Status
			//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"order-header"+urlString;
			//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);


}

function resetOrders(){


	$('#pruchaseOrder_no').val("");
	$('#confirmationOrder_no').val("");
	$('#date-picker-1').val("");
	$('#date-picker-2').val("");
	$('#orderStatus').prop('selectedIndex',0);


}

function searchInvoice(){

	var PO_Number = $('#invoicePruchaseOrder_iv').val();
	var confirma_Number = $('#invoiceConfirmationOrder_iv').val();
	var start_Date = $('#date-picker-4').val();
	var end_Date = $('#date-picker-5').val();
	var Status= $('#invoiceStatus').val();

	var urlString ="?ponumber="+encodeURIComponent(PO_Number)+"&invoice="+encodeURIComponent(confirma_Number)+"&sdate="+start_Date+"&edate="+end_Date+"&status="+Status
			//alert("urlString :: "+urlString);
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"invoice-header"+urlString;
			//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divInvoiceHeader);
}

function resetInvoice(){
	$('#invoicePruchaseOrder_iv').val("");
	$('#invoiceConfirmationOrder_iv').val("");
	$('#date-picker-4').val("");
	$('#date-picker-5').val("");
	$('#invoiceStatus').prop('selectedIndex',0);
}

function searchBackOrder(){

	var PO_Number = $('#backOrderpo_no').val();
	var part_Number = $('#backOrderpart_no').val();

	var urlString ="?ponumber="+PO_Number+"&partnumber="+part_Number
			//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxBOUrl+"back-order"+urlString;
			//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divBackOrders);
}

function resetBackOrder(){

	$('#backOrderpo_no').val("");
	$('#backOrderpart_no').val("");
}

function invoiceSortBy(sortOption){
	var urlString ="?sortBy="+sortOption;
	//alert("urlString :: "+urlString);
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxIOUrl+"sortInvoice-Header"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divInvoiceHeader);
}

function backOrderSortBy(sortOption){
	var urlString ="?sortBy="+sortOption;
	//alert("urlString :: "+urlString);
	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxBOUrl+"sort-Back-Order"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divBackOrders);

}

function displayOrdersAs(pos){

	var itemsCount = 20;

	if (pos == 'up'){
		itemsCount  = $( "#orderStatusdisplayup option:selected" ).val();
	} else {
		itemsCount = $( "#orderStatusdisplaydown option:selected" ).val();
	}

	var urlString ="?itemsCount="+itemsCount
	//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"orderHistory-Pagination"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);

}

function hasNext(currentPage,itemsCount,begin,end){


	var urlString ="?itemsCount="+itemsCount+"&begin="+begin+"&end="+end+"&page="+currentPage+"&moveFlag=1";
	//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"orderHistory-Pagination"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);

}

function hasPrev(currentPage,itemsCount,begin,end){


	var urlString ="?itemsCount="+itemsCount+"&begin="+begin+"&end="+end+"&page="+currentPage+"&moveFlag=-1";
	//alert("urlString :: "+urlString);

	ACC.uploadorder.Url = ACC.uploadorder.pathName + ACC.uploadorder.ajaxOHUrl+"orderHistory-Pagination"+urlString;
	//alert("ACC.uploadorder.Url :: "+ACC.uploadorder.Url);
	ACC.uploadorder.postAJAX(ACC.uploadorder.$divOrderHeader);

}

function formateDate(id){

	if (id == 'date-picker-1'){
	 // $('#date-picker-1').attr("placeholder", "");
		$('#date-picker-1').val("");
		return false;
	} else {
		//$('#date-picker-2').attr("placeholder", "");
		$('#date-picker-2').val("");
		return false;
	}
}

function disableAltDcSelectBox(gatpDc, altDcToDisable) {
	if (gatpDc.checked) {
		document.getElementById(altDcToDisable).disabled = true;
		document.getElementById(altDcToDisable).value = "Select";
	} else {
		document.getElementById(altDcToDisable).disabled = false;
	}
}

function clearGatpDcCheckboxes(nonGatpSel, entryNumberToDisable) {
	var altDcValue = $('select#' + nonGatpSel + ' :selected').text();

	if (nonGatpSel != "Select") {
		document.getElementById(entryNumberToDisable).checked = false;
	}
}

function addGatpDc(entryNumber) {
	var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+$(this).val()+"/"+entryNumber+"/available";
	$.ajax({
		url: ajaxURL,
		async: false,
		cache: false,
		success: function (xmlDoc) {
			return true;
		}
	});
}

function removeGatpDc(altDcSelectTxt, gatpCheckboxId) {
	if (gatpCheckboxId.checked && altDcSelectTxt != "Select") {
		var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/removedc/"+$(this).val()+"/"+gatpCheckboxId;
		$.ajax({
			url: ajaxURL,
			async: false,
			cache: false,
			success: function (xmlDoc) {
				return true;
			}
		});
	}
}

function addAltDc(selectedAltDc, entryNumber) {
	if (selectedAltDc.indexOf("-") > -1) {
		var arr = selectedAltDc.split("-");
		var dcCode = "";
		if (arr.length > 0) {
			dcCode = arr[1];
		}
		var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/adddc/"+dcCode+"/"+entryNumber+"/available";
		$.ajax({
			url: ajaxURL,
			async: false,
			cache: false,
			success: function (xmlDoc) {
				return true;
			}
		});
	}
}

function removeAltDc(gatpCheckboxId, altDcSelect, altDcEntryNumber) {
	if (altDcSelect.value != "Select" && gatpCheckboxId.checked) {
		var ajaxURL = ACC.ymmsearch.pathName + "USD/gatp/removedc/"+$(this).val()+"/"+altDcEntryNumber;
		$.ajax({
			url: ajaxURL,
			async: false,
			cache: false,
			success: function (xmlDoc) {
				return true;
			}
		});
	}
}

function enableOrDisableCarrierShipMthdSelectBoxes(custPickupCheckbox, id) {
	if (custPickupCheckbox.checked) {
		document.getElementById('chooseCarrier_' + id).disabled = true;
		document.getElementById('chooseCarrier_' + id).value = "Carrier";
		document.getElementById('sm_' + id).disabled = true;
		document.getElementById('sm_' + id).value = "SM";
	} else {
		document.getElementById('chooseCarrier_' + id).disabled = false;
		document.getElementById('chooseCarrier_' + id).value = "UPS";
		document.getElementById('sm_' + id).disabled = false;
		document.getElementById('sm_' + id).value = "GRD";
	}
}

function validatePO(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if ((charCode > 47 && charCode < 58)
			|| (charCode > 64 && charCode < 91)
			|| (charCode > 96 && charCode < 123) || charCode == 0
			|| charCode == 95 || charCode == 45 || charCode == 32
			|| charCode == 8 || charCode == 9) {
		return true;
	}
	return false;
}

function GetXmlHttpObject(){var a=null;try {a=new XMLHttpRequest()} catch (b){try {a=new ActiveXObject("Msxml2.XMLHTTP")} catch (b){try {a=new ActiveXObject("Microsoft.XMLHTTP")} catch (b){alert("Your browser broke!");return false}}} return a};
;

ACC.locations = {

	currentPath : window.location.href,
	pathName : "",
	currentCurrency : "USD",
	ajaxUrl : "USD/support/",
	$Selectfmzone : $('#fmzone'),
	$SelectfmzoneDiv : $('#fmZoneDiv'),
	$Selectfmcountry : $('#fmcountry'),
	$SelectfmcountryDiv : $('#fmCountriesDiv'),
	$SelectfmState : $('#fmState'),
	$SelectfmStateDiv : $('#fmStatesDiv'),
	
	$Selectfmresultcountry : $('#fmResultcountry'),
	$SelectfmresultState : $('#fmResultState'),
	options : "",
	url : "",
	setCurrentPath : function() {
		try {
			if (ACC.locations.currentPath.indexOf("/USD") != -1) {
				ACC.locations.pathName = ACC.locations.currentPath.substring(0,
						ACC.locations.currentPath.lastIndexOf("/USD") + 1);
			} else if (ACC.locations.currentPath.indexOf("?site") != -1) {
				ACC.locations.pathName = ACC.locations.currentPath.substring(0,
						ACC.locations.currentPath.lastIndexOf("/?site") + 1)
						+ ACC.locations.currentPath
								.substring(ACC.locations.currentPath
										.lastIndexOf("site=") + 5,
										ACC.locations.currentPath.length)
						+ "/en/";
			} else {
				ACC.locations.pathName = window.location.href
						+ "federalmogul/en/";
			}
		} catch (e) {
			alert(e);
		}
	},

	bindAll : function() {
		with (ACC.locations) {

			bindToChangefmZone($Selectfmzone);
			bindToChangefmcountry($Selectfmcountry);
			bindToChangefmState($SelectfmState);
			

		}
	},

	bindToChangefmZone : function(zone) {
		zone.change(function(event) {
			var selectedZone = ACC.locations.$Selectfmzone.val();
			ACC.locations.Url = ACC.locations.pathName + ACC.locations.ajaxUrl
					+ "getCountries/" + selectedZone;

			$.ajax({
				url : ACC.locations.Url,
				type : "GET",
				success : function(xmlDoc) {
					var respCountryDoc = $(xmlDoc).find("fmcountries").text();
					var defaulCountrytopt = '<option value="" selected="selected">Select Country</option>';
					
					if(respCountryDoc != null || respCountryDoc != ""){
						var option = respCountryDoc.split("|"); 
						for (var i = 0; i < option.length; i++) {
							var index = option[i].indexOf(":");
							var optVal = option[i].substring(0,index)
							var optTxt = option[i].substring(index+1)
							if( (optVal != null || optVal != "") && (optTxt != null || optTxt != "") ){
								defaulCountrytopt += '<option value="'+optVal+'">'+optTxt+ ' </option>';
							}
						}
						
					}
					$("#fmcountry").html(defaulCountrytopt);
					
					var respStateDoc = $(xmlDoc).find("fmstates").text();
					var defaulStateopt = '<option value="" selected="selected">Select State</option>';
					
					if(respStateDoc != null || respStateDoc != ""){
						var option = respCountryDoc.split("|"); 
						for (var i = 0; i < option.length; i++) {
							var index = option[i].indexOf(":");
							var optVal = option[i].substring(0,index)
							var optTxt = option[i].substring(index+1)
							if( (optVal != null || optVal != "") && (optTxt != null || optTxt != "") ){
								defaulCountrytopt += '<option value="'+optVal+'">'+optTxt+ ' </option>';
							}
						}
						
					}
					$("#fmState").html(defaulStateopt);
					
					var respLocationsDoc = $(xmlDoc).find("fmlocations").html();
					$("#fmlocations").html(respLocationsDoc);
				},
				error : function(e) {
					
				}

			});

		});
	},

	bindToChangefmcountry : function(country) {
		country.change(function(event) {
			var selectedCountry = ACC.locations.$Selectfmcountry.val();
			var selectedZone = ACC.locations.$Selectfmzone.val();
			ACC.locations.Url = ACC.locations.pathName + ACC.locations.ajaxUrl
					+ "getStates/"+selectedZone+"/" + selectedCountry;
			
			$.ajax({
				type : "GET",
				url : ACC.locations.Url,
				success : function(xmlDoc) {
					
					var respStateDoc = $(xmlDoc).find("fmstates").text();
					respStateDoc = respStateDoc.trim();
					var defaulStateopt = '<option value="" selected="selected">Select State</option>';
					
					if(respStateDoc != null && respStateDoc != '' ){
						
						var option = respStateDoc.split("|"); 
						for (var i = 0; i < option.length; i++) {
							var index = option[i].indexOf(":");
							var optVal = option[i].substring(0,index)
							var optTxt = option[i].substring(index+1)
							if( (optVal != null || optVal != "") && (optTxt != null || optTxt != "") ){
								defaulStateopt += '<option value="'+optVal+'">'+optTxt+ ' </option>';
							}
							
						}
					
					
					if(defaulStateopt != null && defaulStateopt != ""){
						// defaulStateopt +='<option value="" selected="selected">Select State</option>';
							$("#fmState").removeAttr("disabled");
						
					}else{
						$("#fmState").prop('disabled', 'disabled');
					}
					$("#fmState").html(defaulStateopt);
					
				}else{
					$("#fmlocations").html(xmlDoc);
					defaulStateopt +='<option value="" selected="selected">Select State</option>';
					$("#fmState").html(defaulStateopt);
					$("#fmState").prop('disabled', 'disabled');
				}	
				},
				error : function(xhr, err) {
				}
			});

		});
	},
	

	bindToChangefmState : function(state) {
		state.change(function(event) {
			var selectedCountry = ACC.locations.$Selectfmcountry.val();
			var selectedState = ACC.locations.$SelectfmState.val();
			ACC.locations.Url = ACC.locations.pathName + ACC.locations.ajaxUrl
			+ "getLocations/"+selectedCountry+"/" + selectedState;
			
			
			
			$.ajax({
				type : "GET",
				url : ACC.locations.Url,
				success : function(xmlDoc) {
					
					$("#fmlocations").html(xmlDoc);
				},
				error : function(xhr, err) {
				}
			});

		});
	},
};

$(document).ready(function() {
	ACC.locations.setCurrentPath();
	ACC.locations.bindAll();

});


function getStates(obj){
	var selectedCountry = obj.value;
	var selectedZone = ACC.locations.$Selectfmzone.val();
	ACC.locations.Url = ACC.locations.pathName + ACC.locations.ajaxUrl
			+ "getStates/"+selectedZone+"/" + selectedCountry;
	
	$.ajax({
		type : "GET",
		url : ACC.locations.Url,
		success : function(xmlDoc) {
			var respDoc = $(xmlDoc).find("fmstates").text();
			$("#fmStatesDiv").html(respDoc);
		},
		error : function(xhr, err) {
		}
	});
}

function getLocations(obj){
	var selectedCountry = $( "#fmcountry" ).val();
	var selectedState = obj.value;
	ACC.locations.Url = ACC.locations.pathName + ACC.locations.ajaxUrl
	+ "getLocations/"+selectedCountry+"/" + selectedState;
	
	$.ajax({
		type : "GET",
		url : ACC.locations.Url,
		success : function(xmlDoc) {
			var respDoc = $(xmlDoc).find("fmlocations").text();
			$("#fmlocations").html(respDoc);
		},
		error : function(xhr, err) {
		}
	});
}


function GetXmlHttpObject() {
	var a = null;
	try {
		a = new XMLHttpRequest()
	} catch (b) {
		try {
			a = new ActiveXObject("Msxml2.XMLHTTP")
		} catch (b) {
			try {
				a = new ActiveXObject("Microsoft.XMLHTTP")
			} catch (b) {
				alert("Your browser broke!");
				return false
			}
		}
	}
	return a
};;

ACC.cartremoveitem = {

	bindAll: function ()
	{
		this.bindCartRemoveProduct();
	},

	bindCartRemoveProduct: function ()
	{
		$('.submitRemoveProduct').on("click", function ()
		{		
			var entryNum = $(this).attr('id').split("_")[1];
		
			var $form = $('#updateCartForm' + entryNum);
			var initialCartQuantity = $form.find('input[name=initialQuantity]');
			var cartQuantity = $form.find('input[name=quantity]');
			var productCode = $form.find('input[name=productCode]').val(); 
			var productName = $form.find('input[name=productName]').val();
			var productPrice = $form.find('input[name=productPrice]').val(); 

			document.getElementById('quick_modal').style.display = 'block';
		    document.getElementById('quick_fade').style.display = 'block';
			ACC.track.trackRemoveFromCart(productCode, initialCartQuantity.val(),productName,productPrice);
			
			cartQuantity.val(0);
			initialCartQuantity.val(0);
			var method = $form.attr("method") ? $form.attr("method").toUpperCase() : "GET";

			$.ajax({
				url: $form.attr("action"),
				data: $form.serialize(),
				type: method,
				success: function(data) 
				{
					location.reload();
					document.getElementById('quick_modal').style.display = 'none';
					document.getElementById('quick_fade').style.display = 'none';
				},
				error: function() 
				{
					document.getElementById('quick_modal').style.display = 'none';
					document.getElementById('quick_fade').style.display = 'none';
					alert("Failed to remove quantity. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				}
			});
		});
		
		$('.updateQuantityProduct').on("click", function (event)
		{
			var entryNum = $(this).attr('id').split("_")[1];
			
			var $form = $('#updateCartForm' + entryNum);
			var initialCartQuantity = $form.find('input[name=initialQuantity]').val();
			var newCartQuantity = $form.find('input[name=quantity]').val();
			var productCode = $form.find('input[name=productCode]').val(); 
			var productName = $form.find('input[name=productName]').val();
            var productPrice = $form.find('input[name=productPrice]').val(); 		

            if (initialCartQuantity != newCartQuantity)
			{ 
				document.getElementById('quick_modal').style.display = 'block';
			    document.getElementById('quick_fade').style.display = 'block';
				ACC.track.trackUpdateCart(productCode, initialCartQuantity, newCartQuantity,productName,productPrice);

				var method = $form.attr("method") ? $form.attr("method").toUpperCase() : "GET";
				$.ajax({
					url: $form.attr("action"),
					data: $form.serialize(),
					type: method,
					success: function(data) 
					{
						location.reload();
						document.getElementById('quick_modal').style.display = 'none';
						document.getElementById('quick_fade').style.display = 'none';
					},
					error: function() 
					{
						document.getElementById('quick_modal').style.display = 'none';
						document.getElementById('quick_fade').style.display = 'none';
						alert("Failed to update quantity. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
					}
				});
			}
		});
	},
	
	getProductQuantity: function(gridContainer, mapData) 
	{
		var skus          = jQuery.map(gridContainer.find("input[type='hidden'].sku"), function(o) {return o.value});
		var quantities    = jQuery.map(gridContainer.find("input[type='textbox'].sku-quantity"), function(o) {return o});
		
		var totalPrice = 0.0;
		var totalQuantity = 0.0;
	
		$.each(skus, function(index, skuId) 
		{ 
			var quantity = mapData[skuId];
			if (quantity != undefined)
			{
				quantities[index].value = quantity;
				totalQuantity += parseFloat(quantity);
				
				var indexPattern = "[0-9]+";
				var currentIndex = parseInt(quantities[index].id.match(indexPattern));
				
				var currentPrice = $("input[id='productPrice["+currentIndex+"]']").val();
				totalPrice += parseFloat(currentPrice) * parseInt(quantity);
			}
		});
		
		var subTotalValue = Currency.formatMoney(Number(totalPrice).toFixed(2), Currency.money_format[ACC.common.currentCurrency]);
		var avgPriceValue = 0.0;
		if (totalQuantity > 0)
		{
			avgPriceValue = Currency.formatMoney(Number(totalPrice/totalQuantity).toFixed(2), Currency.money_format[ACC.common.currentCurrency]);
		}

		gridContainer.parent().find('#quantity').html(totalQuantity);
		gridContainer.parent().find("#avgPrice").html(avgPriceValue)
		gridContainer.parent().find("#subtotal").html(subTotalValue);
		
		var $inputQuantityValue = gridContainer.parent().find('#quantityValue');
		var $inputAvgPriceValue = gridContainer.parent().find('#avgPriceValue');
		var $inputSubtotalValue = gridContainer.parent().find('#subtotalValue');

		$inputQuantityValue.val(totalQuantity);
		$inputAvgPriceValue.val(Number(totalPrice/totalQuantity).toFixed(2));
		$inputSubtotalValue.val(Number(totalPrice).toFixed(2));
	}, 
	
	coreTableActions: function(productCode, mapCodeQuantity)  
	{	
        var skuQuantityClass = '.sku-quantity';

		var quantityBefore = 0;
		var quantityAfter = 0;

		var grid = $('#grid_' + productCode);
		
		grid.on('click', skuQuantityClass, function(event) {
            $(this).select();
        });

        grid.on('focusin', skuQuantityClass, function(event) {
            quantityBefore = jQuery.trim(this.value);
            if (quantityBefore == "") {
                quantityBefore = 0;
                this.value = 0;
            }
        });

        grid.on('focusout', skuQuantityClass, function(event) {
            var indexPattern           = "[0-9]+";
            var currentIndex           = parseInt($(this).attr("id").match(indexPattern));
            var $gridGroup             = $(this).parents('.orderForm_grid_group');
            var $closestQuantityValue  = $gridGroup.find('#quantityValue');
            var $closestAvgPriceValue  = $gridGroup.find('#avgPriceValue');
            var $closestSubtotalValue  = $gridGroup.find('#subtotalValue');
            
            var currentQuantityValue   = $closestQuantityValue.val();
            var currentSubtotalValue   = $closestSubtotalValue.val();

            var currentPrice = $("input[id='productPrice["+currentIndex+"]']").val();
            var variantCode = $("input[id='cartEntries["+currentIndex+"].sku']").val();

            quantityAfter = jQuery.trim(this.value);

            if (isNaN(jQuery.trim(this.value))) {
                this.value = 0;
            }

            if (quantityAfter == "") {
                quantityAfter = 0;
                this.value = 0;
            }

            if (quantityBefore == 0) {
                $closestQuantityValue.val(parseInt(currentQuantityValue) + parseInt(quantityAfter));
                $closestSubtotalValue.val(parseFloat(currentSubtotalValue) + parseFloat(currentPrice) * parseInt(quantityAfter));
            } else {
                $closestQuantityValue.val(parseInt(currentQuantityValue) + (parseInt(quantityAfter) - parseInt(quantityBefore)));
                $closestSubtotalValue.val(parseFloat(currentSubtotalValue) + parseFloat(currentPrice) * (parseInt(quantityAfter) - parseInt(quantityBefore)));
            }

            if (parseInt($closestQuantityValue.val()) > 0) {
                $closestAvgPriceValue.val(parseFloat($closestSubtotalValue.val()) / parseInt($closestQuantityValue.val()));
            } else {
                $closestAvgPriceValue.val(0);
            }

            $closestQuantityValue.parent().find('#quantity').html($closestQuantityValue.val());
            $closestAvgPriceValue.parent().find('#avgPrice').html(ACC.productorderform.formatTotalsCurrency($closestAvgPriceValue.val()));
            $closestSubtotalValue.parent().find('#subtotal').html(ACC.productorderform.formatTotalsCurrency($closestSubtotalValue.val()));
            
            if (quantityBefore != quantityAfter)
            {
            	var method = "POST";

            	$.ajax({
            		url: ACC.config.contextPath + '/cart/update',
            		data: {productCode: variantCode, quantity: quantityAfter, entryNumber: -1},
					type: method,
					success: function(data) 
					{
						ACC.cartremoveitem.refreshCartData(data, -1, productCode, null);
						mapCodeQuantity[variantCode] = quantityAfter;
					},
					error: function(xht, textStatus, ex) 
					{
						alert("Failed to get variant matrix. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
					}
				});
            }
        });
	},
	
	refreshCartData: function(cartData, entryNum, productCode, quantity) 
	{
		// if cart is empty, we need to reload the whole page
		if (cartData.entries.length == 0)
		{
			location.reload();
		}
		else
		{
			var form;	
			var removeItem = false;
		
			if (entryNum == -1) // grouped item
			{
				var editLink = $('#QuantityProduct_' + productCode);
				form = editLink.parent();
			
				var quantity = 0;
				var entryPrice = 0;
				for (var i = 0; i < cartData.entries.length; i++)
				{
					var entry = cartData.entries[i];
					if (entry.product.code == productCode)
					{			
						quantity = entry.quantity;
						entryPrice = entry.totalPrice;
						break;
					}
				}

				if (quantity == 0)
				{
					removeItem = true;
					form.parent().parent().remove();
				}
				else
				{
					form.find(".qty").html(quantity);
					form.parent().parent().find(".total").html(entryPrice.formattedValue);
				}
			
			}
			else //ungrouped item
			{
				form = $('#updateCartForm' + entryNum);
		
				if (quantity == 0)
				{
					removeItem = true;
					form.parent().parent().remove();
				}
				else
				{
					for (var i = 0; i < cartData.entries.length; i++)
					{
						var entry = cartData.entries[i];
						if (entry.entryNumber == entryNum)
						{				
							form.find('input[name=quantity]').val(entry.quantity);
							form.parent().parent().find(".total").html(entry.totalPrice.formattedValue);
						}
					}
				}
			}
			
			// remove item, need to update other items' entry numbers
			if (removeItem === true)
			{
				$('.cartItem').each(function(index)
				{
					form = $(this).find('.quantity').children().first();
					var productCode = form.find('input[name=productCode]').val(); 

					for (var i = 0; i < cartData.entries.length; i++)
					{
						var entry = cartData.entries[i];
						if (entry.product.code == productCode)
						{				
							form.find('input[name=entryNumber]').val(entry.entryNumber);
							break;
						}
					}
				});
			}
			
			// refresh mini cart 	
			ACC.minicart.refreshMiniCartCount();
		
			$('#orderTotals').next().remove();
			$('#orderTotals').remove();
			$("#ajaxCart").html($("#cartTotalsTemplate").tmpl({data: cartData}));		
		}
	}
}


$(document).ready(function ()
{
	ACC.cartremoveitem.bindAll();
});
;

ACC.paginationsort = {

	downUpKeysPressed: false,

	bindAll: function ()
	{
		this.bindPaginaSort();
	},
	bindPaginaSort: function ()
	{
		with (ACC.paginationsort)
		{
			bindSortForm($('#sort_form1'));
			bindSortForm($('#sort_form2'));
		}
	},
	bindSortForm: function (sortForm)
	{
		/*if ($.browser.msie)
		{
			this.sortFormIEFix($(sortForm).children('select'), $(sortForm).children('select').val());
		}*/

		sortForm.change(function ()
		{
			/*if (!$.browser.msie)
			{
				this.submit();
			}
			else
			{*/
				if (!ACC.paginationsort.downUpPressed)
				{
					this.submit();
				}
				ACC.paginationsort.downUpPressed = false;
			//}
		});
	},
	sortFormIEFix: function (sortOptions, selectedOption)
	{
		sortOptions.keydown(function (e)
		{
			// Pressed up or down keys
			if (e.keyCode === 38 || e.keyCode === 40)
			{
				ACC.paginationsort.downUpPressed = true;
			}
			// Pressed enter
			else if (e.keyCode === 13 && selectedOption !== $(this).val())
			{
				$(this).parent().submit();
			}
			// Any other key
			else
			{
				ACC.paginationsort.downUpPressed = false;
			}
		});
	}
};

$(document).ready(function ()
{
	ACC.paginationsort.bindAll();
});
;

ACC.minicart = {
	
	$layer:$('#miniCartLayer'),

	bindMiniCart: function ()
	{
		$(document).on('mouseenter', '.miniCart', this.showMiniCart);
		$(document).on('mouseleave', '.miniCart', this.hideMiniCart);
	},
	
	showMiniCart: function ()
	{

		if(ACC.minicart.$layer.data("hover"))
		{
			return;
		}
		
		if(ACC.minicart.$layer.data("needRefresh") != false){
			ACC.minicart.getMiniCartData(function(){
				ACC.minicart.$layer.fadeIn(function(){
					ACC.minicart.$layer.data("hover", true);
					ACC.minicart.$layer.data("needRefresh", false);
				});
			})
		}
		
		ACC.minicart.$layer.fadeIn(function(){
			ACC.minicart.$layer.data("hover", true);
		});
	},
	
	hideMiniCart: function ()
	{
		ACC.minicart.$layer.fadeOut(function(){
			ACC.minicart.$layer.data("hover", false);
		});
	},
	
	getMiniCartData : function(callback)
	{
		$.ajax({
			url: ACC.minicart.$layer.attr("data-rolloverPopupUrl"),
			cache: false,
			type: 'POST',
			success: function (result)
			{
				ACC.minicart.$layer.html(result);
				callback();
			}
		});	
	},

	refreshMiniCartCount : function()
	{
		$.ajax({
			dataType: "json",
			url: ACC.minicart.$layer.attr("data-refreshMiniCartUrl") + Math.floor(Math.random() * 101) * (new Date().getTime()),
			success: function (data)
			{
				
				$(".miniCart .count").html("("+data.miniCartCount+")");
				$(".miniCart .price").html(data.miniCartPrice);
				ACC.minicart.$layer.data("needRefresh", true);
			}
		});
	}
};

$(document).ready(function ()
{
	ACC.minicart.bindMiniCart();
});

;

var actionUrl= "";
$(document).ready(function(){

	$.support.placeholder = false;
	test = document.createElement('input');
	if ('placeholder' in test) $.support.placeholder = true;

	$('.slider4').bxSlider({
		slideWidth: 500,
		minSlides: 1,
		maxSlides: 2,
		moveSlides: 1,
		slideMargin: 30,
		nextText: '<span class="fa fa-chevron-right"></span>',
		prevText: '<span class="fa fa-chevron-left"></span>'
	});

	/* For Loyalty Product Carousel */
	$('.loyaltyslider4').bxSlider({
		slideWidth: 202,
		minSlides: 2,
		maxSlides: 3,
		moveSlides: 1,
		slideMargin: 30,
		nextText: '<span class="fa fa-chevron-right"></span>',
		prevText: '<span class="fa fa-chevron-left"></span>'
	});

	$('.single-slider').jRange({
		from: 0,
		to: 10,
		step: 1,
		scale: [0,5 ,10],
		format: '%s',
		width: 300,
		showLabels: true
	});

	//anonymous
	$('#loginbox input').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().parent().find('.errorMsg').hide();
		}
	});

	//anonymous user -sign in
	$('#loginForm input').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().parent().find('.errorMsg').show();

		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().parent().find('.errorMsg').hide();

		}
	});

	//ProductDetailsPage
	$("#prodMainImg").elevateZoom({ gallery: 'prdDetail_thumbnail', cursor: 'pointer', galleryActiveClass: "active",
		zoomWindowWidth: 300,
		zoomWindowHeight: 300,
		zoomWindowWidth: 526,
		zoomWindowHeight: 500,
		zoomWindowPosition: 1,
		easing: true,
		zoomWindowFadeIn: 500,
		zoomWindowFadeOut: 500,
		lensFadeIn: 500,
		lensFadeOut: 500,
		zoomWindowOffety: -2,
		zoomWindowOffetx: 40
	});
	$("#prodMainImg").bind("click", function(e) {
		var ez = $('#prodMainImg').data('elevateZoom');
		ez.closeAll();
		return false;
	});
	$('#prodMainImg').on('click', function(event){
		$('#myModal').modal('toggle');
	});

	var reviewFlag = '${reviewFlag}';

	if (reviewFlag == 'FromReview')
	{
		$(window).scrollTop($("#tabsection").offset().top);
		$("ul.resp-tabs-list > li").removeClass("resp-tab-active");
		$(".resp-tabs-container > div").removeClass("resp-tab-content-active");
		$('#tab2').addClass("resp-tab-active");
		$("#reviewTab").addClass("resp-tab-content-active");
		$("#prodSpec").hide();
	}

//	For Online tools file downloads
	$(".fileDownloadTable #myTable").tablesorter();
	$('.toggle').click(function() {
		$(this).toggleClass('collapsed');
		$input = $( this );
		$target = $('#'+$input.attr('data-toggle'));
		$target.slideToggle();
	});
//	B2BHomePage

//	LeadGenerationCallBack
	$('.leadGen input,select,textarea').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' || $(this).val()==" " && $hasReqField.hasClass('required')){
			//if(!$(this).val()  && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().find('.errorMsg').hide();
		}
	});


	/* max char count for textarea */
	var text_max = $('textarea#callBackDescription').attr(
	'maxlength');
	$('.char-count').html(
			text_max
			+ ' characters remaining');

	$('#callBackDescription')
	.keyup(
			function() {
				var text_length = $(
				'#callBackDescription')
				.val().length;
				var text_remaining = text_max
				- text_length;

				$('.char-count')
				.html(
						text_remaining
						+ ' characters remaining');
			});

//	fmOrderHistoryPage
	$(".invoiceTabTable #myTable").tablesorter();
	$(".backOrderTabTable #myTable").tablesorter();

//	fmOrderHistoryPage-uploadOrderStatus-fmCartPage
//	ApplicationUsageReport
	$('.date-picker').datepicker();

//	Update Password/fmUpdatePasswordPage
	$('.regFrm input').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().find('.errorMsg').hide();
		}
	});

//	for create user page
	$('.createuserpage input,select').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' || $("#whatdescribes").val()==" "  && $hasReqField.hasClass('required')){
			//if(!$(this).val()  && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().find('.errorMsg').hide();
		}
	});

	/*var options = {
		onLoad: function () {
			$('#messages').text('Start typing password');
		},
		onKeyUp: function (evt) {
			$(evt.target).pwstrength("outputErrorList");
		}
};*/
	$('#newPassword').pwstrength(options);
	$('.password-verdict, .progress').hide();

//	for EditUserPage
	$('.edituserpage input,select').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			//if(!$(this).val()  && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().find('.errorMsg').hide();
		}
	});


//	fmUpdateProfilePage
	$('.regFrm input').blur(function(){
		var $hasReqField = $(this).parent().find('span');
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().find('.errorMsg').hide();
		}
	});

//	for forgot password view page
	$('.resetPanel input').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');

			$(this).parent().parent().find('.errorMsg').show();

		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().parent().find('.errorMsg').hide();
		}
	});


	//for loyalty checkout shipping page
	$('.chekoutBillingShippingAddress input,select').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().find('.errorMsg').hide();
		}
	});

//	quickOrderPage
	$("#brandprefix").hide();
//	for forgot password reset page
	$('#checkYourMail input').blur(function() {
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')) {
			$(this).addClass('inputError');
			$(this).parent().parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')) {
			$(this).removeClass('inputError');
			$(this).parent().parent().find('.errorMsg').hide();
		}
	});
	$('.progress').hide();
	var options = {
		onLoad: function () {
			$('#messages').text('Start typing password');
		},
		onKeyUp: function (evt) {
			$(evt.target).pwstrength("outputErrorList");
		}
	};
	$('#setnewpwd').pwstrength(options);
	$('.password-verdict, .progress').hide();

	$('.regFrm input,select').blur(function(){
		var $hasReqField = $(this).parent().find('span'); //var to if input parent is having span and store it
		if ($(this).val() == '' && $hasReqField.hasClass('required')){
			$(this).addClass('inputError');
			$(this).parent().find('.errorMsg').show();
		} else if ($(this).hasClass('inputError')){
			$(this).removeClass('inputError');
			$(this).parent().find('.errorMsg').hide();
		}
	});

//	B2B HomePage
	$('#upload_order').on('click', function(e) {
		$(".alertContainer").addClass("show");
	});
	$('.close').on('click', function(e) {
		$(".alertContainer").removeClass("show");
	});

	$("#shipToSelect").change(function(){
		$("#shipToSelect option:selected").each(function(){
			if ($(this).attr("value")=="new"){
				$(".shipToForm .tab-pane").hide();
				$("body").addClass("modal-open");
				$("#shipToNewAddressPopup").addClass("in");
				$("#shipToNewAddressPopup").attr("aria-hidden", "false");
				$("#shipToNewAddressPopup").show();
				$('body').append('<div class="modal-backdrop fade in"></div>');
				$(".brandprefix").removeClass("in");
				$("#upload-brandprefix").removeClass("in");
			}
			else if ($(this).attr("value")=="chooseExist"){
				$(".shipToForm .tab-pane").hide();
				$("#shipTo").show();
			}
			else if ($(this).attr("value")=="default"){
				$(".shipToForm .tab-pane").hide();
				$("#shipTodefaultAcc").show();
				$("#newShipTo").hide();
				$("#oldShipTo").show();
			}
		});
	});

	$("#soldToSelect").change(function(){
		$( "#soldToSelect option:selected").each(function(){
			if ($(this).attr("value")=="default"){
				$(".soldToForm .tab-pane").hide();
				$("#soldTodefaultAcc").show();
			}
			if ($(this).attr("value")=="chooseExist"){
				$(".soldToForm .tab-pane").hide();
				$("#soldTo").show();
			}
		});
	});

	$(".close, .cancel" ).bind( "click", function() {
		$("body").removeClass("modal-open");
		$(".modal").removeClass("in");
		$(".modal").attr("aria-hidden", "true");
		$(".modal").hide();
		$(".modal-backdrop").remove();
	});

	$("[class^=categories]").click(function(e){
		e.stopPropagation();
		var text=$(event.target).attr('class');
		var temp=text.split("-");
		var classCat=temp[0];
		var partNoArr=temp[1].split(" ");
		var partNo=partNoArr[0];

		 $("[class^=categories]").addClass('hide');
		 $("[class^=categories]").parent('.form-control').addClass('hide');

		 $('.'+classCat+'-'+partNo).siblings('.category').addClass('hide');
		 $('.'+classCat+'-'+partNo).addClass('show');
		 $('.'+classCat+'-'+partNo).parent('form-control-'+partNo).addClass('show');
		 $('.'+classCat+'-'+partNo).siblings('.category-'+partNo).removeClass('hide').addClass('show').toggle()
	});

	$("[class^=category]").click(function(e){
	  e.stopPropagation();
	  var text=$(event.target).attr('class');
	  $('.'+text).removeClass('show');
	  var temp=text.split("-");
	  var classCat=temp[0];
	  var partNoArr=temp[1].split(" ");
	  var partNo=partNoArr[0];
	  $('.'+classCat+'-'+partNo).addClass('hide').removeClass('show').slideToggle();
	  $("[class^=categories]").addClass('show').removeClass('hide');
	  $('.categories-'+partNo+' span').html($(this).text());
	  $('.'+classCat+'-'+partNo).siblings('.category').removeClass('hide');
	});

//	SignupPage
	getUnits();

	function getQueryStringValue(key) {
		var keyValues = location.search.substr(1).split('&');
		var i;

		for (i = 0; i < keyValues.length; ++i) {
			var pair = keyValues[i].split('=');

			if (pair[0] === key) {
				return decodeURIComponent(pair[1]);
			}
		}
	}

	// pre populate input values based on configured query strings
	$('[data-querystring-key]').each(function () {
		var key = $(this).attr('data-querystring-key');
		$(this).val(getQueryStringValue(key));
	});

	// Site Search in Nav Bar
	$(".search-link-item a").click(function(event) {
		$(".search-icon").toggleClass("hide");
		$(".site-sub-nav-search-form").toggleClass("active");
	});

});



function createCookie(name,value,days) {
	document.cookie = name +
	'=; expires=Thu, 01-Jan-70 00:00:01 GMT;';
}

function getpassword() {
	$('.password-verdict, .progress').show();
}

function getpassword_reg() {
	$('.password-verdict, .progress').show();
}

//for forgot password reset page
function getpassword_pwdreset() {
	$('.password-verdict, .progress').show();
}

function addNewAddressToSession(){
	if ((myFMAddressForm.firstLastName.value != "")
		&& (myFMAddressForm.Addressline1.value != "")
		&& (myFMAddressForm.city.value != "")
		&& (myFMAddressForm.zip.value != "")) {
		var count=0;
		if (document.getElementById("coutntry").value == 'United States' ) {
			var zipcode = "^\\d{5}(-\\d{4})?$";
			if (document.getElementById("zip").value.search(zipcode) == -1) {
				document.getElementById("errorzip").innerHTML="Invalid ZIP/Postal Code ";
			} else {
				document.getElementById("errorzip").innerHTML=" ";
				count++;
			}
		} else if (document.getElementById("coutntry").value == 'Canada' ) {
			var zipcode = "(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Za-z]{1} \\d{1}[A-Za-z]{1}\\d{1}$)";
			if (document.getElementById("zip").value.search(zipcode) == -1) {
				document.getElementById("errorzip").innerHTML="Invalid ZIP/Postal Code ";
			} else {
				document.getElementById("errorzip").innerHTML=" ";
				count++;
			}
		}

		if (count >= 1) {
			ajaxUrl="USD/my-fmaccount/";
			currentPath= window.location.href;
			var pathName= "";
			var userNewAddress = "";

			try {
				if (currentPath.indexOf("/USD") != -1) {
					pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 1);
				} else if (currentPath.indexOf("?site") != -1){
					pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)
						+ currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)
						+ "/en/";
				} else {
					pathName = window.location.href;
				}
			} catch (e){}

			actionUrl  = pathName + ajaxUrl+"/add-address-tosession";

			$.ajax({
				url:actionUrl,
				type:'POST',
				mimeType:"multipart/form-data",
				data: $("#myFMAddressForm").serialize(),
				success: function(xmlDoc) {
					$("#shipTodefaultAcc").hide();
					var respDoc = $(xmlDoc).find("addNewShipToaddressSession").html();
					userNewAddress = respDoc.substr(respDoc.indexOf("NewAddress:")+11);
					respDoc = respDoc.substr(0,respDoc.indexOf("NewAddress:"));
					$("#shipTonewAddress1").html(respDoc);
				},
				error: function(xmlDoc) {}
			});

			$("body").removeClass("modal-open");
			$(".modal").removeClass("in");
			$(".modal").attr("aria-hidden", "true");
			$(".modal").hide();
			$(".modal-backdrop").remove();
		}
	} else {
		if (myFMAddressForm.firstLastName.value === "") {
			document.getElementById("errorcname").innerHTML = "Please enter Company Name";
		} else {
			document.getElementById("errorcname").innerHTML = " ";
		}

		if (myFMAddressForm.Addressline1.value === "") {
			document.getElementById("erroraddress").innerHTML = "Please enter Contact Address";
		} else {
			document.getElementById("erroraddress").innerHTML = " ";
		}

		if (myFMAddressForm.city.value === "") {
			document.getElementById("errorcity").innerHTML = "Please enter city";
		} else {
			document.getElementById("errorcity").innerHTML = " ";
		}

		if (myFMAddressForm.zip.value === "") {
			document.getElementById("errorzip").innerHTML = "Please enter Postal Code";
		} else {
			document.getElementById("errorzip").innerHTML = " ";
		}

		if (myFMAddressForm.prov.value === ' ') {
			document.getElementById("errorstate").innerHTML = "Please select state";
		} else {
			document.getElementById("errorstate").innerHTML = " ";
		}
	}

}

function validate() {

	if (myFMAddressForm.zip.value !== "") {
		if (document.getElementById("coutntry").value === 'United States' ) {
			var zipcode = "^\\d{5}(-\\d{4})?$";
			if (document.getElementById("zip").value.search(zipcode) === -1) {
				document.getElementById("errorzip").innerHTML = "Invalid ZIP/Postal Code ";
			} else {
				document.getElementById("errorzip").innerHTML = " ";
			}
		} else if (document.getElementById("coutntry").value === 'Canada' ) {
			var zipcode = "(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Za-z]{1} \\d{1}[A-Za-z]{1}\\d{1}$)";
			if (document.getElementById("zip").value.search(zipcode) === -1) {
				document.getElementById("errorzip").innerHTML = "Invalid ZIP/Postal Code ";
			} else {
				document.getElementById("errorzip").innerHTML = " ";
			}
		}
	}
}

function hideAndShow(){
	var e = document.getElementById("shipToSelect");
	var strUser = e.options[e.selectedIndex].value;

	if (strUser === "chooseExist"){
		$('#shipTodefaultAcc').hide();
		$('#floatingBarsG4').hide();
		$('#shipTo').show();
		$('#changeShip').show();
		$('#changeShipbtn').show();
	}
}

function clearNewAddress() {
	$(".shipToForm .tab-pane").hide();
	$("#shipTodefaultAcc").show();
	$("#newShipTo").hide();
	$('#oldShipTo').show();
}

function checkHide(){
	if (myFMAddressForm.firstLastName.value !== "") {
		document.getElementById("errorcname").innerHTML = " ";
	}

	if (myFMAddressForm.Addressline1.value !== "") {
		document.getElementById("erroraddress").innerHTML = " ";
	}

	if (myFMAddressForm.city.value !== "") {
		document.getElementById("errorcity").innerHTML = " ";
	}

	if (myFMAddressForm.zip.value !== "") {
		document.getElementById("errorzip").innerHTML = " ";
	}

	if (myFMAddressForm.prov.value !== ' ') {
		document.getElementById("errorstate").innerHTML = " ";
	}
}

function goToReview() {
	$("ul.resp-tabs-list > li").removeClass("resp-tab-active");
	$(".resp-tabs-container > div").removeClass("resp-tab-content-active");
	$('#tab1').removeClass("resp-tab-content-active");
	$('#tab3').removeClass("resp-tab-content-active");
	$('#tab4').removeClass("resp-tab-content-active");
	$('#tab5').removeClass("resp-tab-content-active");

	$('#tab2').addClass("resp-tab-active");
	$("#reviewTab").addClass("resp-tab-content-active");
	$("#prodSpec").hide();
	$("#FAQTab").hide();
	$("#AlsoFitsTab").hide();
	$("#TechTipsTag").hide();
}

function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}

function validateuser() {
	var userexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
	if (document.getElementById("userid").value.search(userexp) === -1) {
		document.getElementById("userid1").innerHTML = "Invalid user";
	} else {
		document.getElementById("userid1").innerHTML = "";
	}
}

function validateNewShipForm() {
	var count=0;
	if (newAddress.shipZipPostalCode.value !== "") {
		if (window.document.getElementById("shipcountry").value === 'US') {
			var zipcode = "^\\d{5}(-\\d{4})?$";
			if (window.document.getElementById("shipZipPostalCode").value.search(zipcode) === -1) {
				window.document.getElementById("errorzip").innerHTML = "Invalid ZIP/Postal Code ";
				count++;
			} else {
				window.document.getElementById("errorzip").innerHTML = " ";
			}
		} else if (window.document.getElementById("shipcountry").value === 'CA') {
			var zipcode = "(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Za-z]{1} \\d{1}[A-Za-z]{1}\\d{1}$)";
			if (window.document.getElementById("shipZipPostalCode").value.search(zipcode) === -1) {
				window.document.getElementById("errorzip").innerHTML = "Invalid ZIP/Postal Code ";
				count++;
			} else {
				window.document.getElementById("errorzip").innerHTML = " ";
			}
		}
	}

	if (newAddress.phone.value !== "") {
		var phonenum = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
		if (document.getElementById("phone").value.search(phonenum) === -1) {
			document.getElementById("errorcnum").innerHTML = "Please verify that you've entered a 10-digit phone number ";
			count++;
		} else {
			document.getElementById("errorcnum").innerHTML = " ";
		}
	}

	if (count > 0) {
		document.getElementById('continueToReview').disabled = true;
	} else {
		document.getElementById('continueToReview').disabled = false;
	}
}

function changeText(a) {
	switch (a) {
	case 1: {
		document.getElementById("displayDesc").innerHTML="<label style='font: bold;' >I hate it</label>";
		break;
	}
	case 2: {
		document.getElementById("displayDesc").innerHTML="<label style='font: bold;' >I don't like it</label>";
		break;
	}
	case 3: {
		document.getElementById("displayDesc").innerHTML="<label style='font: bold;' >It's okay</label>";
		break;
	}
	case 4: {
		document.getElementById("displayDesc").innerHTML="<label style='font: bold;' >I like it</label>";
		break;
	}
	case 5: {
		document.getElementById("displayDesc").innerHTML="<label style='font: bold;' >I love it</label>";
		break;
	}
	}
}

function changeEmpty() {
	document.getElementById("displayDesc").innerHTML="<label style='font: bold;' ></label> ";
}

function checkPlaceHolderLabel() {
	if (!$.support.placeholder) {
		document.getElementById("email_ie9").innerHTML = '<label class="" for="j_username">Email Address</label>';
		document.getElementById("pwd_ie9").innerHTML = '<label class="" for="j_password">Password</label>';
	}
}
;

ACC.common = {
	currentPath: window.location.pathname,
	currentCurrency: "USD",
	$page:           $("#page"),
	$globalMessages: $("#globalMessages"),

	setCurrentCurrency: function() {
		ACC.common.currentCurrency = ACC.common.$page.data("currencyIsoCode");
	},

	refreshScreenReaderBuffer: function() {
	    // changes a value in a hidden form field in order
	    // to trigger a buffer update in a screen reader
	    $('#accesibility_refreshScreenReaderBufferField').attr('value', new Date().getTime());
	},

	bindAll: function() {
		ACC.common.bindToUiCarouselLink();
		ACC.common.bindShowProcessingMessageToSubmitButton();
	},

	bindToUiCarouselLink: function ()
	{
		try{
		$("ul.carousel > li a.popup").colorbox({
			onComplete: function ()
			{
				ACC.common.refreshScreenReaderBuffer();
				$.colorbox.resize()
				ACC.product.initQuickviewLightbox();
			},

			onClosed: function ()
			{
				ACC.common.refreshScreenReaderBuffer();
			}
		});
}catch(e){}
	},
	
	processingMessage: $("<img src='" + ACC.config.commonResourcePath + "/images/spinner.gif'/>"),

	bindShowProcessingMessageToSubmitButton : function () {

		$(':submit.show_processing_message').each(function(){
			$(this).on("click",ACC.common.showProcessingMessageAndBlockForm);
		});
	},
	
	showProcessingMessageAndBlockForm: function ()
	{
		$("#checkoutContentPanel").block({ message: ACC.common.processingMessage });  
	},

	blockFormAndShowProcessingMessage: function (submitButton)
	{
		var form = submitButton.parents('form:first');
		form.block({ message: ACC.common.processingMessage });  
	},

	showSpinner: function(message) {
		$('.spinner-wrapper').show();
		$('#spinnerMessage').html(message);
	},
	
	showSpinnerById: function(id) {
		$('#'+id).show();
	},

	hideSpinner: function() {
		$('.spinner-wrapper').hide();
		$('#spinnerMessage').html("");
	},
	
	hideSpinnerById: function(id) {
		$('#'+id).hide();
	}

};

$(document).ready(function() {
	ACC.common.setCurrentCurrency();
	ACC.common.bindAll();
	ACC.common.bindShowProcessingMessageToSubmitButton();
});



/* Extend jquery with a postJSON method */
jQuery.extend({
	postJSON: function (url, data, callback)
	{
		return jQuery.post(url, data, callback, "json");
	}
});

// add a CSRF request token to POST ajax request if its not available
$.ajaxPrefilter(function (options, originalOptions, jqXHR)
{
	// Modify options, control originalOptions, store jqXHR, etc
	if (options.type === "post" || options.type === "POST")
	{
		var noData = (typeof options.data === "undefined");
		if (noData || options.data.indexOf("CSRFToken") === -1)
		{
			options.data = (!noData ? options.data + "&" : "") + "CSRFToken=" + ACC.config.CSRFToken;
		}
	}
});

function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};
;

ACC.cms = {

	bindAll: function()
	{
		this.bindNavigationBarMenu();
	},

	bindNavigationBarMenu: function()
	{
		$('li.La ul ul a').each(function ()
		{
			$(this).focus(function ()
			{
				$(this).addClass('focused');
				var menuParent = $(this).closest('ul').parent().closest('ul');
				$(menuParent).addClass('dropdown-visible');
			});

			$(this).blur(function ()
			{
				$(this).removeClass('focused');
				var menuParent = $(this).closest('ul').parent().closest('ul');
				if (!$('.focused', menuParent).length)
				{
					$(menuParent).removeClass('dropdown-visible');
				}
			});
		});
	}
};

$(document).ready(function()
{
	ACC.cms.bindAll();
});
;

ACC.product = {
	
	initQuickviewLightbox:function(){
		this.enableAddToCartButton();
	},
	
	
	enableAddToCartButton: function ()
	{
		$('#addToCartButton').removeAttr("disabled");
	},

	/**
	 * This is now disabled. Originally it was called in the initQuickviewLightbox and in the onReady callback.
	 * FM now wants to have the cart redirect to the cart page, so we are no longer wrapping the POST and displaying
	 * a pop-up. This code is being kept in place in case they decide to go back to the cart pop-up in the near future.
	 */
	bindToAddToCartForm: function ()
	{
		var addToCartForm = $('.add_to_cart_form');
		try{
		addToCartForm.ajaxForm({success: ACC.product.displayAddToCartPopup});
		}catch(e){}
	},

	displayAddToCartPopup: function (cartResult, statusText, xhr, formElement)
	{
		
		ACC.common.$globalMessages.html(cartResult.cartGlobalMessagesHtml);
		
		$('#addToCartLayer').remove();
		
		if (typeof ACC.minicart.refreshMiniCartCount == 'function')
		{
			ACC.minicart.refreshMiniCartCount();
		}
		
		$("#header").append(cartResult.addToCartLayer);
		

		$('#addToCartLayer').fadeIn(function(){
			$.colorbox.close();
			if (typeof timeoutId != 'undefined')
			{
				clearTimeout(timeoutId);
			}
			timeoutId = setTimeout(function ()
			{
				$('#addToCartLayer').fadeOut(function(){
			 	   $('#addToCartLayer').remove();
					
				});
			}, 5000);
			
		});
		
		var productCode = $('[name=productCodePost]', formElement).val();
		var quantityField = $('[name=qty]', formElement).val();

		var quantity = 1;
		if (quantityField != undefined)
		{
			quantity = quantityField;
		}

		ACC.track.trackAddToCart(productCode, quantity, cartResult.cartData);
		
		// if it is orderForm, disable add to cart button in the end
		if($('#orderFormAddToCart').length > 0) {
			$('#addToCartBtn').attr('disabled','disabled');
		}
	}

};

$(document).ready(function ()
{
	with(ACC.product)
	{
		enableAddToCartButton();
	}
});

;

ACC.refinements = {

	bindAll: function ()
	{
		this.bindRefinementCategoryToggles();
		this.bindMoreLessToggles();
		this.bindMoreStoresToggles()
	},

	bindRefinementCategoryToggles: function ()
	{
		$('a.refinementToggle').each(function ()
		{
			$(this).attr('title', $(this).data('hideFacetText'));

			$(this).on("click", function ()
			{
				var content = $(this).parents('.facet').find('div.facetValues');
				$(this).attr('title', $(content).is(':visible') ? $(this).data('showFacetText') : $(this).data('hideFacetText'));
				$(this).toggleClass("close");
				$(content).slideToggle();
				return false;
			});

			$(this).next().click(function ()
			{
				$(this).prev().click();
			});
		});
	},
	
	bindMoreLessToggles: function ()
	{
		$("a.moreFacetValues").click(function(e){
			e.preventDefault();
				
			var eParent = $(this).parents(".facetValues");
			eParent.find(".topFacetValues").hide();
			eParent.find(".allFacetValues").show();
		})
		
		$("a.lessFacetValues").click(function(e){
			e.preventDefault();
				
			var eParent = $(this).parents(".facetValues");
			eParent.find(".topFacetValues").show();
			eParent.find(".allFacetValues").hide();
		})
	},
	
	bindMoreStoresToggles: function ()
	{
		$("a.moreStoresFacetValues").click(function(e){
			e.preventDefault();
				
			$(this).parents('div.allFacetValues').find('li.hidden').slice(0, 5).removeClass('hidden').first().find('input:[type=checkbox]').focus();
		})
		
	}
};

$(document).ready(function ()
{
	ACC.refinements.bindAll();
});
;

ACC.carousel = {

	addthis_config: {
		ui_click: true
	},

	bindAll: function ()
	{
		this.bindJCarousel();
	},
	bindJCarousel: function ()
	{
		try{
		jQuery('.yCmsContentSlot.span-4 .jcarousel-skin').jcarousel({
			vertical: true
		});
		
		jQuery('.yCmsContentSlot.span-24 > .scroller .jcarousel-skin').jcarousel({

		});

		$('#homepage_slider').waitForImages(function ()
		{
			$(this).slideView({toolTip: true, ttOpacity: 0.6, autoPlay: true, autoPlayTime: 8000});
		});
		}catch(e){}
	}
};

$(document).ready(function ()
{
	ACC.carousel.bindAll();
});

;

ACC.autocomplete = {

	bindAll: function ()
	{
		this.bindSearchAutocomplete();
	},

	bindSearchAutocomplete: function ()
	{
		var $search = $("#search");
		var option  = $search.data("options");
		var cache   = {};

		if (option)
		{
            $search.autocomplete({
                minLength: option.minCharactersBeforeRequest,
                delay:     option.waitTimeBeforeRequest,
                appendTo:  ".siteSearch",
                source:    function(request, response) {

                    var term = request.term.toLowerCase();

                    if (term in cache) {
                        return response(cache[term]);
                    }

                    $.postJSON(option.autocompleteUrl, {term: request.term}, function(data) {
                        var autoSearchData = [];
                        if(data.suggestions != null){
                            $.each(data.suggestions, function (i, obj)
                            {
                                autoSearchData.push(
                                        {value: obj.term,
                                            url: ACC.config.contextPath + "/search?text=" + obj.term,
                                            type: "autoSuggestion"});
                            });
                        }
                        if(data.products != null){
                            $.each(data.products, function (i, obj)
                            {
                                autoSearchData.push(
                                        {value: obj.name,
                                            code: obj.code,
                                            desc: obj.description,
                                            manufacturer: obj.manufacturer,
                                            url: ACC.config.contextPath + obj.url,
                                            price: obj.price.formattedValue,
                                            type: "productResult",
                                            image: obj.images[0].url});
                            });
                        }
                        cache[term] = autoSearchData;
                        return response(autoSearchData);
                    });
                },
                focus: function (event, ui)
                {
                    return false;
                },
                select: function (event, ui)
                {
                    window.location.href = ui.item.url;
                }
            }).data("autocomplete")._renderItem = function (ul, item)
            {
                if (item.type == "autoSuggestion")
                {
                    renderHtml = "<a href='?q=" + item.value + "' class='clearfix'>" + item.value + "</a>";
                    return $("<li class='suggestions'>")
                            .data("item.autocomplete", item)
                            .append(renderHtml)
                            .appendTo(ul);
                }
                if (item.type == "productResult")
                {
                    var renderHtml = "<a href='" + ACC.config.contextPath + item.url + "' class='product clearfix'>";
                    if (option.displayProductImages &&  item.image != null)
                    {
                        renderHtml += "<span class='thumb'><img src='" + item.image + "' /></span><span class='desc clearfix'>";
                    }
                    renderHtml += "<span class='title'>" + item.value +
                            "</span><span class='price'>" + item.price + "</span></span>" +
                            "</a>";
                    return $("<li class='product'>").data("item.autocomplete", item).append(renderHtml).appendTo(ul);
                }
            };
		}
	}
};

$(document).ready(function ()
{
	ACC.autocomplete.bindAll();
});;

ACC.pwdstrength = {

	bindAll: function ()
	{
		this.bindPStrength();
	},

	bindPStrength: function ()
	{
		try{
			$('.strength').pstrength({ verdicts: [ACC.pwdStrengthVeryWeak,
				ACC.pwdStrengthWeak,
				ACC.pwdStrengthMedium,
				ACC.pwdStrengthStrong,
				ACC.pwdStrengthVeryStrong],
				tooShort: ACC.pwdStrengthTooShortPwd,
				minCharText: ACC.pwdStrengthMinCharText });
		}catch(e){}
	}

};

$(document).ready(function ()
{
	ACC.pwdstrength.bindAll();
});
;

ACC.password = {

	bindAll: function ()
	{
		$(":password").bind("cut copy paste", function (e)
		{
			e.preventDefault();
		});
	}
};

$(document).ready(function ()
{
	ACC.password.bindAll();
});
;

ACC.langcurrency = {

	bindAll: function ()
	{
		this.bindLangCurrencySelector();
	},

	bindLangCurrencySelector: function ()
	{
		$('#lang-selector').change(function ()
		{
			$('#lang-form').submit();
		})

		$('#currency-selector').change(function ()
		{
			$('#currency-form').submit();
		})
	}
};

$(document).ready(function ()
{
	ACC.langcurrency.bindAll();
});
;

ACC.checkout = {
	spinner: $("<img id='taxesEstimateSpinner' src='" + ACC.config.commonResourcePath + "/images/spinner.gif' />"),

	bindAll: function ()
	{
		this.bindCheckO();
	},

	bindCheckO: function ()
	{
		var cartEntriesError = false;

		// Alternative checkout flows options
		$('.doFlowSelectedChange').change(function ()
		{
			if ('multistep-pci' == $('#selectAltCheckoutFlow').attr('value'))
			{
				$('#selectPciOption').css('display', '');
			}
			else
			{
				$('#selectPciOption').css('display', 'none');

			}
		});

		// Alternative checkout flows version of the doCheckout method to handle the checkout buttons on the cart page.
		$('.doCheckoutBut').click(function ()
		{
			var checkoutUrl = $(this).data("checkoutUrl");
			
			cartEntriesError = ACC.pickupinstore.validatePickupinStoreCartEntires();
			if (!cartEntriesError)
			{
				var expressCheckoutObject = $('.doExpressCheckout');
				if(expressCheckoutObject.is(":checked"))
				{
					window.location = expressCheckoutObject.data("expressCheckoutUrl");
				}
				else
				{
					var flow = $('#selectAltCheckoutFlow').attr('value');
					if ('' == flow)
					{
						// No alternate flow specified, fallback to default behaviour
						window.location = checkoutUrl;
					}
					else
					{
						// Fix multistep-pci flow
						if ('multistep-pci' == flow)
						{
						flow = 'multistep';
						}
						var pci = $('#selectPciOption').attr('value');

						// Build up the redirect URL
						var redirectUrl = checkoutUrl + '/select-flow?flow=' + flow + '&pci=' + pci;
						window.location = redirectUrl;
					}
				}
			}
			return false;
		});

		$('#estimateTaxesButton').click(function ()
		{
			$('#zipCodewrapperDiv').removeClass("form_field_error");
			$('#countryWrapperDiv').removeClass("form_field_error");

			var countryIso = $('#countryIso').val();
			if (countryIso === "")
			{
				$('#countryWrapperDiv').addClass("form_field_error");
			}
			var zipCode = $('#zipCode').val();
			if (zipCode === "")
			{
				$('#zipCodewrapperDiv').addClass("form_field_error");
			}

			if (zipCode !== "" && countryIso !== "")
			{
				$("#order_totals_container").append(ACC.checkout.spinner);
				$.postJSON("cart/estimate", {zipCode: zipCode, isocode: countryIso  }, function (estimatedCartData)
				{
					$("#estimatedTotalTax").text(estimatedCartData.totalTax.formattedValue)
					$("#estimatedTotalPrice").text(estimatedCartData.totalPrice.formattedValue)
					$(".estimatedTotals").show();
					$(".realTotals").hide();
					$("#taxesEstimateSpinner").remove();

				});
			}
		});
	}

};

$(document).ready(function ()
{
	ACC.checkout.bindAll();
});
;

ACC.approval = {

	submitApprovalDecision: function (desictionCode)
	{
		$('#approverSelectedDecision').attr("value", desictionCode);
		$("#approvalDecisionForm").submit();
	},

	bindToApproverDecisionButton: function ()
	{
		$('.approverDecisionButton').click(function()
		{
			ACC.approval.submitApprovalDecision($(this).data("decision"));

		});
	}
};

$(document).ready(function ()
{
	ACC.approval.bindToApproverDecisionButton();
});
;

ACC.quote = {

	bindAll: function()
	{
		this.bindToCancelQuoteButtonClick();
		this.bindToNegotiateQuoteButtonClick();
		this.bindToCancelClick();
		this.bindToAddAdditionalComment();
	},

	showRequestRequoteForm: function()
	{
		ACC.quote.displayNegotiateQuoteDiv();
		$("#cancelQuoteButton").attr('disabled', true);
	},

	displayNegotiateQuoteDiv: function()
	{
		$('#negotiate-quote-div').show();
		return false;
	},

	bindToCancelQuoteButtonClick: function()
	{
		$('#cancelQuoteButton').click(function() {
			ACC.quote.displayNegotiateQuoteDiv();
			$('#negotiate-quote-div-label-cancel').show();
			$("#cancelQuoteButton").addClass('pressed');
			$("#negotiateQuoteButton").attr('disabled', true);
			$("#acceptQuoteButton").attr('disabled', true);
			$("#addAdditionalComment").attr('disabled', true);
			$('#selectedQuoteDecision').val(this.name);
			return false;
		});
	},

	updateNogotiatiateQuoteDivLabel: function(label)
	{
		$('#negotiate-quote-div-label').html(label)
	},

	bindToNegotiateQuoteButtonClick: function()
	{
		$('#negotiateQuoteButton').click(function() {
			$("#negotiateQuote").val(true);
			$("#negotiateQuoteButton").addClass('pressed');
			$("#cancelQuoteButton").attr('disabled', true);
			$("#acceptQuoteButton").attr('disabled', true);
			$("#addAdditionalComment").attr('disabled', true);
			$('#selectedQuoteDecision').val(this.name);
			ACC.quote.displayNegotiateQuoteDiv();
			$('#negotiate-quote-div-label-add-comment').show();

			return false;
		});
	},

	submitQuoteDecision: function(selectedQuoteDecision)
	{
		$('#selectedQuoteDecision').attr("value", selectedQuoteDecision);
		$("#quoteOrderDecisionForm").submit();
	},

	bindToCancelClick: function()
	{
		$('#cancelComment').click(function() {
			$("#negotiateQuote").val(false);
			$('#negotiate-quote-div, #negotiate-quote-div h2 > div').hide()
			$("#cancelQuoteButton").removeClass('pressed');
			$("#negotiateQuoteButton").removeClass('pressed');
			$("#acceptQuoteButton").removeClass('pressed');
			$("#addAdditionalComment").removeClass('pressed');

			$("#negotiateQuoteButton").removeAttr("disabled");
			$("#cancelQuoteButton").removeAttr("disabled");
			$("#acceptQuoteButton").removeAttr("disabled");
			$("#addAdditionalComment").removeAttr("disabled");
			$('#selectedQuoteDecision').val("");
			return false;
		});
	},

	bindToAddAdditionalComment: function()
	{
		$('#addAdditionalComment').click(function() {
			$("#addAdditionalComment").addClass('pressed');
			$("#negotiateQuoteButton").attr('disabled', true);
			$("#cancelQuoteButton").attr('disabled', true);
			$("#acceptQuoteButton").attr('disabled', true);
			$('#selectedQuoteDecision').val(this.name);
			ACC.quote.displayNegotiateQuoteDiv();
			$('#negotiate-quote-div-label-add-comment').show();
			return false;
		});		
	}
	
	
};

$(document).ready(function()
{
	
	if(typeof quoteActive != 'undefined' && quoteActive)
	{
		ACC.quote.bindAll();
	}
	
});










;

ACC.negotiatequote = {

	bindAll: function()
	{
		
		if(typeof cartDataquoteAllowed != 'undefined')
		{
			$("#requestQuoteButton").attr('quoteAllowed',cartDataquoteAllowed);
			this.bindToCancelQuoteClick();
			this.updateRequestQuoteButton();
			this.bindToProceedButtonClick();
			this.bindToRequestQuoteButtonClick();
			this.bindToQuoteDescriptionValidation();
		}
		
	},
	
	displayNegotiateQuoteDiv: function() 
	{
		$("#requestQuoteButton").addClass('pressed');
		$('#negotiate-quote-div').show();
		$(".place-order").attr('disabled', true);
		$("#scheduleReplenishmentButton").attr('disabled', true);
		return false;	
	},
	
	bindToCancelQuoteClick: function()
	{
		$('#cancel-quote-negotiation').click(function()
		{
			ACC.negotiatequote.cancelQuoteNegotiationEvent();
		});
	},
	
	cancelQuoteNegotiationEvent: function()
	{
		$("#requestQuoteButton").removeClass('pressed');
		$('#negotiate-quote-div').hide();
		$('#quoteRequestDescription').value = "";
		$("#negotiateQuote").val(false);
		ACC.placeorder.updatePlaceOrderButton();
		ACC.negotiatequote.updateRequestQuoteButton();
		ACC.replenishment.updateScheduleReplenishmentButton();
	},

	bindToProceedButtonClick: function()
	{
		$('#negotiateQuoteButton').click(function()
		{
			$("#negotiateQuote").val(true);
			ACC.placeorder.placeOrderWithSecurityCode();
			return false;
		});
	},
	
	bindToRequestQuoteButtonClick: function()
	{
		$('#requestQuoteButton').click(function()
		{
			$('#requestQuoteButton').addClass("pressed");;
			ACC.negotiatequote.displayNegotiateQuoteDiv();
			return false;
		});
	},
	
	bindToQuoteDescriptionValidation: function()
	{
		$('#quoteRequestDescription[maxlength]').keyup(function(){  

	        var limit = parseInt($(this).attr('maxlength'));  
	        var text = $(this).val();  
	        var chars = text.length;  
	  
	        if(chars > limit){  
	            var new_text = text.substr(0, limit);  
	            $(this).val(new_text);  
	        }  
	    }); 
	},
		
	updateRequestQuoteButton: function()
	{
		var paymentType = $("#checkout_summary_paymentType_div").hasClass("complete");
		var deliveryAddress = $("#checkout_summary_deliveryaddress_div").hasClass("complete");
		var deliveryMode = $("#checkout_summary_deliverymode_div").hasClass("complete");
		var costCenter = $('#checkout_summary_costcenter_div').hasClass("complete");
		var paymentDetails = $("#checkout_summary_payment_div").hasClass("complete")
		var selectedPaymentType = $("input:radio[name='PaymentType']:checked").val() != 'CARD';
		var costCenterSelected = $("#CostCenter option:selected")[0].value != '';
		var quoteAllowed = 'true' == $("#requestQuoteButton").attr("quoteAllowed");
		
		if (quoteAllowed && paymentType && deliveryAddress && deliveryMode && (costCenter || paymentDetails) && selectedPaymentType
			&& costCenterSelected)
		{
			$("#requestQuoteButton").removeAttr('disabled');
		}
		else
		{
			$("#requestQuoteButton").attr('disabled', true);
		}

		if ($("#requestQuoteButton").hasClass("pressed"))
		{
			$(".place-order").attr('disabled', true);
			$("#scheduleReplenishmentButton").attr('disabled', true);
		}
	}
	
		
	

};

$(document).ready(function()
{
	if (typeof negotiateQuote !== 'undefined' && negotiateQuote)
	{
		ACC.negotiatequote.displayNegotiateQuoteDiv();

		// Used in Account Quote Management page
		$('#negotiate-quote-div-label-add-comment').show();
	}

	ACC.negotiatequote.bindAll();
});








;

ACC.paymentmethod = {

	bindAll: function ()
	{
		this.bindEditPaymentMethodButton();
		this.bindSecurityCodeWhatIs();
		
		$(document).on("change", "#cardType", ACC.paymentmethod.displayStartDateIssueNum);

	},

	bindSecurityCodeWhatIs: function ()
	{
		if (typeof securityWhatText != 'undefined')
		{
			$('.security_code_what').bt(securityWhatText, {
				trigger: 'click',
				positions: 'bottom',
				fill: '#efefef',
				cssStyles: {
					fontSize: '11px'
				}
			});
		}
	},

	bindEditPaymentMethodButton: function ()
	{
		$('div.checkout_summary_flow_b .change_payment_method_button').click(function ()
		{
			var paymentId = $(this).attr('data-payment_id');
			var options = {
				url: getPaymentDetailsFormUrl,
				data: {paymentId: paymentId, createUpdateStatus: ''},
				target: '#popup_checkout_add_edit_payment_method',
				type: 'POST',
				cache: false,
				success: function (data)
				{
					ACC.paymentmethod.bindCreateUpdatePaymentDetailsForm();

					// Show the payment method popup
					$.colorbox({inline: true, href: "#popup_checkout_add_edit_payment_method", height: false, overlayClose: false});
					$(document).on('cbox_complete', function ()
					{
						$.colorbox.resize();
					});

				},
				error: function (xht, textStatus, ex)
				{
					alert("Failed to get payment details. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				}
			};

			$(this).ajaxSubmit(options);

			return false;
		});
	},

	bindUseSavedCardButton: function ()
	{
		$('button.use_saved_card_button').click(function ()
		{
			$.ajax({
				url: getSavedCardsUrl,
				type: 'POST',
				cache: false,
				dataType: 'json',
				success: function (data)
				{
					// Fill the available saved cards
					$('#saved_cards_tbody').html($('#savedCardsTemplate').tmpl({savedCards: data}));
					ACC.paymentmethod.bindUseThisSavedCardButton();
					ACC.paymentmethod.bindEnterNewPaymentButton();

					// Show the saved cards popup
					$.colorbox({inline: true, href: "#popup_checkout_saved_payment_method", height: false, innerHeight: "530px"});
				},
				error: function (xht, textStatus, ex)
				{
					alert("Failed to get saved cards. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				}
			});

			return false;
		});
	},

	bindEnterNewPaymentButton: function ()
	{
		$('button.enter_new_payment_button').click(function ()
		{
			// Show the payment method popup
			$.colorbox({inline: true, href: "#popup_checkout_add_edit_payment_method", height: false, innerHeight: "930px"});
			return false;
		});
	},

	bindCreateUpdatePaymentDetailsForm: function ()
	{
		ACC.paymentmethod.bindUseSavedCardButton();

		$('.create_update_payment_form').each(function ()
		{
			var options = {
				type: 'POST',
				beforeSubmit: function ()
				{
					$('#popup_checkout_add_edit_payment_method').block({ message: "<img src='" + spinnerUrl + "' />" });
					$.colorbox.toggleLoadingOverlay();
				},
				success: function (data)
				{
					$('#popup_checkout_add_edit_payment_method').html(data);
					var status = $('.create_update_payment_id').attr('status');
					if (status != null && "success" == status.toLowerCase())
					{
						ACC.refresh.getCheckoutCartDataAndRefreshPage();
						parent.$.colorbox.close();
					}
					else
					{
						ACC.paymentmethod.bindCreateUpdatePaymentDetailsForm();
						parent.$.colorbox.resize()
						
					}
				},
				error: function (xht, textStatus, ex)
				{
					alert("Failed to create/update payment details. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					$('#popup_checkout_add_edit_payment_method').unblock();
					$.colorbox.toggleLoadingOverlay();
					ACC.paymentmethod.displayStartDateIssueNum()
					
				}
			};

			$(this).ajaxForm(options);

		});
	},

	bindUseThisSavedCardButton: function ()
	{
		$('.use_this_saved_card_button').click(function ()
		{
			var paymentId = $(this).attr('data-payment_id');
			$.postJSON(setPaymentDetailsUrl, {paymentId: paymentId}, ACC.paymentmethod.handleSelectSavedCardSuccess);
			return false;
		});
	},

	handleSelectSavedCardSuccess: function (data)
	{
		if (data != null)
		{
			ACC.refresh.refreshPage(data);
			parent.$.colorbox.close();
		}
		else
		{
			alert("Failed to set payment details");
		}
	},

	refreshPaymentDetailsSection: function (data)
	{
		$('#checkout_summary_payment_div').replaceWith($('#paymentSummaryTemplate').tmpl(data));
		//bind edit payment details button
		ACC.paymentmethod.bindEditPaymentMethodButton();
		ACC.paymentmethod.bindSecurityCodeWhatIs();
	},
	
	displayStartDateIssueNum: function()
	{
		
		var cardType = $("#cardType").val();
    	            	
    	if (cardType == 'maestro' || cardType == 'switch')
        {
            $('#startDate, #issueNum').show("fast",'linear', function() {
				parent.$.colorbox.resize()
			});
        }
        else
        {
            $('#startDate, #issueNum').hide('fast','linear', function() {
				parent.$.colorbox.resize()
			});
            
	    }
		
						
    }

};

$(document).ready(function ()
{
	ACC.paymentmethod.bindAll();
	

});







;

ACC.placeorder = {

	bindAll: function ()
	{
		this.bindPlaceOrder();
		this.updatePlaceOrderButton();
		this.bindSecurityCodeWhatIs();
	},

	bindPlaceOrder: function ()
	{
		$(".placeOrderWithSecurityCode").on("click", function ()
		{
			ACC.common.blockFormAndShowProcessingMessage($(this));
			$(".securityCodeClass").val($("#SecurityCode").val());
			$("#placeOrderForm1").submit();
		});
	},

	updatePlaceOrderButton: function ()
	{
		
		$(".place-order").removeAttr("disabled");
		// need rewrite /  class changes
	},

	bindSecurityCodeWhatIs: function ()
	{
		try{
		$('.security_code_what').bt($("#checkout_summary_payment_div").data("securityWhatText"),
				{
					trigger: 'click',
					positions: 'bottom',
					fill: '#efefef',
					cssStyles: {
						fontSize: '11px'
					}
				});
		}catch(e){}
	}
};

$(document).ready(function ()
{
	ACC.placeorder.bindAll();
});


;

ACC.address = {

	spinner: $("<img src='" + ACC.config.commonResourcePath + "/images/spinner.gif' />"),
	addressID: '',

	setupDeliveryAddressPopupForm: function (data)
	{
		// Fill the available delivery addresses
		$('#summaryDeliveryAddressBook').html($('#deliveryAddressesTemplate').tmpl({addresses: data}));
		// Handle selection of address
		$('#summaryDeliveryAddressBook button.use_address').click(ACC.address.handleSelectExistingAddressClick);
		// Handle edit address
		$('#summaryDeliveryAddressBook button.edit').click(ACC.address.handleEditAddressClick);
		// Handle set default address
		$('#summaryDeliveryAddressBook button.default').click(ACC.address.handleDefaultAddressClick);
	},

	emptyAddressForm: function ()
	{
		var options = {
			url: getDeliveryAddressFormUrl,
			data: {addressId: ACC.address.addressID, createUpdateStatus: ''},
			type: 'POST',
			success: function (data)
			{
				$('#summaryDeliveryAddressFormContainer').html(data);
				ACC.address.bindCreateUpdateAddressForm();
			}
		};

		$.ajax(options);
	},

	handleSelectExistingAddressClick: function ()
	{
		console.log("handleSelectExistingAddressClick")
		var addressId = $(this).attr('data-address');
		$.postJSON(setDeliveryAddressUrl, {addressId: addressId}, ACC.address.handleSelectExitingAddressSuccess);
		return false;
	},

	handleEditAddressClick: function ()
	{

		$('#summaryDeliveryAddressFormContainer').show();
		$('#summaryOverlayViewAddressBook').show();
		$('#summaryDeliveryAddressBook').hide();

		var addressId = $(this).attr('data-address');
		var options = {
			url: getDeliveryAddressFormUrl,
			data: {addressId: addressId, createUpdateStatus: ''},
			target: '#summaryDeliveryAddressFormContainer',
			type: 'POST',
			success: function (data)
			{
				ACC.address.bindCreateUpdateAddressForm();
				$.colorbox.resize();
			},
			error: function (xht, textStatus, ex)
			{
				alert("Failed to update cart. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
			}
		};

		$(this).ajaxSubmit(options);
		return false;
	},

	handleDefaultAddressClick: function ()
	{
		var addressId = $(this).attr('data-address');
		var options = {
			url: setDefaultAddressUrl,
			data: {addressId: addressId},
			type: 'POST',
			success: function (data)
			{
				ACC.address.setupDeliveryAddressPopupForm(data);
			},
			error: function (xht, textStatus, ex)
			{
				alert("Failed to update address book. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
			}
		};

		$(this).ajaxSubmit(options);
		return false;
	},

	handleSelectExitingAddressSuccess: function (data)
	{
		if (data != null)
		{
			ACC.refresh.refreshPage(data);
			parent.$.colorbox.close();
		}
		else
		{
			alert("Failed to set delivery address");
		}
	},

	bindCreateUpdateAddressForm: function ()
	{
		$('.create_update_address_form').each(function ()
		{
			var options = {
				type: 'POST',
				beforeSubmit: function ()
				{
					$('#checkout_delivery_address').block({ message: ACC.address.spinner });
				},
				success: function (data)
				{
					$('#summaryDeliveryAddressFormContainer').html(data);
					var status = $('.create_update_address_id').attr('status');
					if (status != null && "success" === status.toLowerCase())
					{
						ACC.refresh.getCheckoutCartDataAndRefreshPage();
						parent.$.colorbox.close();
					}
					else
					{
						ACC.address.bindCreateUpdateAddressForm();
						$.colorbox.resize();
					}
				},
				error: function (xht, textStatus, ex)
				{
					alert("Failed to update cart. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					$('#checkout_delivery_address').unblock();
				}
			};

			$(this).ajaxForm(options);
		});
	},

	refreshDeliveryAddressSection: function (data)
	{
		$('.summaryDeliveryAddress').replaceWith($('#deliveryAddressSummaryTemplate').tmpl(data));

	},

	bindSuggestedDeliveryAddresses: function ()
	{
		var status = $('.add_edit_delivery_address_id').attr('status');
		if (status != null && "hasSuggestedAddresses" == status)
		{
			ACC.address.showSuggestedAddressesPopup();
		}
	},

	showSuggestedAddressesPopup: function ()
	{
		$.colorbox({
			inline: true,
			height: false,
			width: 525,
			href: "#popup_suggested_delivery_addresses",
			overlayClose: false,
			onComplete: function ()
			{
				$(this).colorbox.resize();
			}
		});
	},

	bindCountrySpecificAddressForms: function ()
	{
		$('#countrySelector :input').on("change", function ()
		{
			var options = {
				'addressCode': '',
				'countryIsoCode': $(this).val()
			};
			ACC.address.displayCountrySpecificAddressForm(options, ACC.address.showAddressFormButtonPanel);
		})

	},

	showAddressFormButtonPanel: function ()
	{
		if ($('#countrySelector :input').val() !== '')
		{
			$('#addressform_button_panel').show();
		}
	},

	showAddressBook: function ()
	{
		$(document).on("click", "#viewAddressBook", function ()
		{
			var data = $("#savedAddressListHolder").html();
			$.colorbox({

				height: false,
				html: data,
				onComplete: function ()
				{

					$(this).colorbox.resize();
				}
			});

		})
	},

	showRemoveAddressConfirmation: function ()
	{
		$(document).on("click", ".removeAddressButton", function (e)
		{

			e.preventDefault();

			var addressId = $(this).data("addressId");
			$.colorbox({
				inline: true,
				height: false,
				href: "#popup_confirm_address_removal_" + addressId,
				onComplete: function ()
				{

					$(this).colorbox.resize();
				}
			});

		})
	},

	bindToColorboxClose: function ()
	{
		$(document).on("click", ".closeColorBox", function ()
		{
			$.colorbox.close();
		})
	},


	displayCountrySpecificAddressForm: function (options, callback)
	{
		$.ajax({
			url: ACC.config.contextPath + '/my-account/addressform',
			async: true,
			data: options,
			dataType: "html",
			beforeSend: function ()
			{
				$("#i18nAddressForm").html(ACC.address.spinner);
			}
		}).done(function (data)
				{
					$("#i18nAddressForm").html($(data).html());
					if (typeof callback == 'function')
					{
						callback.call();
					}
				});
	},

	bindToChangeAddressButton: function ()
	{
		$(document).on("click", '.summaryDeliveryAddress .editButton', ACC.address.handleChangeAddressButtonClick);
	},
	bindViewAddressBook: function ()
	{
		$(document).on("click", '#summaryOverlayViewAddressBook', function ()
		{
			$('#summaryDeliveryAddressFormContainer').hide();
			$('#summaryOverlayViewAddressBook').hide();
			$('#summaryDeliveryAddressBook').show();
			$.colorbox.resize();
		});
	}
}

/* Extend jquery with a postJSON method */
jQuery.extend({
	postJSON: function (url, data, callback)
	{
		return jQuery.post(url, data, callback, "json");
	}
});

// Address Verification
$(document).ready(function ()
{
	// with (ACC.address)
// 	{
// 		bindToChangeAddressButton();
// 		bindCreateUpdateAddressForm();
// 		bindSuggestedDeliveryAddresses();
// 		bindCountrySpecificAddressForms();
// 		showAddressFormButtonPanel();
// 		showAddressBook();
// 		bindViewAddressBook();
//		showRemoveAddressConfirmation();
// 		bindToColorboxClose();
// 	}

	ACC.address.showRemoveAddressConfirmation();
	ACC.address.bindToColorboxClose();
});


;

ACC.refresh = {
	refreshCartTotals: function (checkoutCartData)
	{
		$('#ajaxCart').html($('#cartTotalsTemplate').tmpl(checkoutCartData));
	},

	refreshPage: function (checkoutCartData)
	{
		//update delivery address, delivery method and payment sections, and cart totals section
		
	
	},
	getCheckoutCartDataAndRefreshPage: function ()
	{
		$.postJSON(getCheckoutCartUrl, function (checkoutCartData)
		{
			ACC.refresh.refreshPage(checkoutCartData);
		});
	}
}

$.blockUI.defaults.overlayCSS = {};
$.blockUI.defaults.css = {};
;

ACC.stars = {

	bindStarsWrapperRadioButtons: function(radioButtons)
	{
		var length = radioButtons.length;
		radioButtons.change(function() {
			for (var btnNo = 1; btnNo <= length; btnNo++)
			{
				var ratingId = '#rating' + btnNo;

				if (btnNo <= $(this).val())
				{
					$(ratingId).prev().removeClass('no_star');
				}
				else
				{
					$(ratingId).prev().addClass('no_star');
				}

				$(ratingId).prev().removeClass('selected');
			}
			$(this).prev().addClass('selected');
		});
	},

	bindStarsWrapperRadioButtonsFirstTimeFocus: function(radioButtons)
	{
		radioButtons.one('focus', function() {
			$(this).trigger('change');
		});
	},

	initialize: function()
	{
		this.bindStarsWrapperRadioButtons($('#stars-wrapper input'));
		this.bindStarsWrapperRadioButtonsFirstTimeFocus($('#stars-wrapper input'));
	}
}

$(document).ready(function() {
	ACC.stars.initialize();
});;

ACC.forgotpassword = {
	$forgottenPwdForm:   $('#forgottenPwdForm'),
	$forgotPasswordLink: $('.password-forgotten'),


	bindForgotPasswordLink: function(link) {
		link.click(function() {
			$.get(link.data('url')).done(function(data) {
				$.colorbox({
					html: data,
					height: '250px',
					overlayClose: false,
					onOpen: function() {
						$('#validEmail').remove();
					},
					onComplete: function() {
						ACC.forgotpassword.$forgottenPwdForm.ajaxForm({
							success: function(data)
							{
								if ($(data).closest('#validEmail').length)
								{
									if ($('#validEmail').length === 0)
									{
										ACC.common.$globalMessages.append(data);
									}
									$.colorbox.close();
								}
								else
								{
									ACC.forgotpassword.$forgottenPwdForm
										.find('.form_field-elements')
										.html($(data)
										.find('#forgottenPwdForm .form_field-elements'));
									$.colorbox.resize();
								}
							}
						});
						ACC.common.refreshScreenReaderBuffer();
					},
					onClosed: function() {
						ACC.common.refreshScreenReaderBuffer();
					}
				});
			});
		});
	},

	bindAll: function() {
		ACC.forgotpassword.bindForgotPasswordLink(ACC.forgotpassword.$forgotPasswordLink);
	}
};

$(document).ready(function() {
	ACC.forgotpassword.bindAll();
});;

ACC.productDetail = {

	
	initPageEvents: function ()
	{
		try{
		
		$('.productImageGallery .jcarousel-skin').jcarousel({
			vertical: true
		});
		}catch(e){}
		
		$(document).on("click","#imageLink, .productImageZoomLink",function(e){
			e.preventDefault();
			
			$.colorbox({
				href:$(this).attr("href"),
				height:555,
				onComplete: function() {
				    ACC.common.refreshScreenReaderBuffer();
					
					$('#colorbox .productImageGallery .jcarousel-skin').jcarousel({
						vertical: true
					});
					
				},
				onClosed: function() {
					ACC.common.refreshScreenReaderBuffer();
				}
			});
		});
		
		
		
		$(".productImageGallery img").click(function(e) {
			$(".productImagePrimary img").attr("src", $(this).attr("data-primaryimagesrc"));
			$("#zoomLink, #imageLink").attr("href",$("#zoomLink").attr("data-href")+ "?galleryPosition="+$(this).attr("data-galleryposition"));
			$(".productImageGallery .thumb").removeClass("active");
			$(this).parent(".thumb").addClass("active");
		});


		$(document).on("click","#colorbox .productImageGallery img",function(e) {
			$("#colorbox  .productImagePrimary img").attr("src", $(this).attr("data-zoomurl"));
			$("#colorbox .productImageGallery .thumb").removeClass("active");
			$(this).parent(".thumb").addClass("active");
		});
		
		
		
		$("body").on("keyup", "input[name=qtyInput]", function(event) {
  			var input = $(event.target);
		  	var value = input.val();
		  	var qty_css = 'input[name=qty]';
  			while(input.parent()[0] != document) {
 				input = input.parent();
 				if(input.find(qty_css).length > 0) {
  					input.find(qty_css).val(value);
  					return;
 				}
  			}
		});
		
	


		$("#Size").change(function () {
			var url = "";
			var selectedIndex = 0;
			$("#Size option:selected").each(function () {
				url = $(this).attr('value');
				selectedIndex = $(this).attr("index");
			});
			if (selectedIndex != 0) {
				window.location.href=url;
			}
		});

		$("#variant").change(function () {
			var url = "";
			var selectedIndex = 0;
		
			$("#variant option:selected").each(function () {
				url = $(this).attr('value');
				selectedIndex = $(this).attr("index");
			});
			if (selectedIndex != 0) {
				window.location.href=url;
			}
		});


		$(".selectPriority").change(function () {
			var url = "";
			var selectedIndex = 0;

			url = $(this).attr('value');
			selectedIndex = $(this).attr("index");

			if (selectedIndex != 0) {
				window.location.href=url;
			}
		});




	}


};

$(document).ready(function ()
{

	with(ACC.productDetail)
	{
		initPageEvents();
	}
});

;

ACC.productTabs = {

	bindAll: function ()
	{
		if($('#productTabs').length>0){
	
			// only load review one at init 
			ACC.productTabs.showReviewsAction("reviews");
		
			 ACC.productTabs.productTabs = $('#productTabs').accessibleTabs({
		        wrapperClass: 'content',
		        currentClass: 'current',
		        tabhead: '.tabHead',
		        tabbody: '.tabBody',
		        fx:'show',
		        fxspeed: null,
		        currentInfoText: 'current tab: ',
		        currentInfoPosition: 'prepend',
		        currentInfoClass: 'current-info',
				autoAnchor:true
		    });


			$(document).on("click", '#write_review_action_main, #write_review_action', function(e){
				e.preventDefault();
				ACC.productTabs.scrollToReviewTab('#write_reviews')
				$('#reviewForm input[name=headline]').focus();
			});
		
			$('#based_on_reviews, #read_reviews_action').bind("click", function(e) {
				e.preventDefault();
				ACC.productTabs.scrollToReviewTab('#reviews')
			});
		
		
			$(document).on("click", '#show_all_reviews_top_action, #show_all_reviews_bottom_action', function(e){
				e.preventDefault();
				ACC.productTabs.showReviewsAction("allreviews");
				$(this).hide();
			});
		
		}

	},
	
	scrollToReviewTab: function (pane)
	{
		$.scrollTo('#productTabs', 300, {axis: 'y'});
		ACC.productTabs.productTabs.showAccessibleTabSelector('#tab-reviews');
		$('#write_reviews,#reviews').hide();
		$(pane).show();
	},
	
	showReviewsAction: function (s)
	{
		$.get($("#reviews").data(s), function (result){
			$('#reviews').html(result);
		});
	}
};

$(document).ready(function ()
{
	ACC.productTabs.bindAll();

	// prevent space filling for min character constraint
	$('#review textarea#comment').on('keyup', function (e) {
		$(this).val($(this).val().replace(/\s+(?=\s)/g,''));
	});
});

;

ACC.productorderform = {

	$addToCartOrderForm:       $("#AddToCartOrderForm"),
	$omsErrorMessageContainer: $("#globalMessages"),
	$skuQuantityInputs:        $(".sku-quantity"),

	// Templates
	$futureTooltipTemplate:      $("#future-tooltip-template"),
	$futureTooltipErrorTemplate: $("#future-tooltip-error-template"),
	$omsErrorMessageTemplate:    $("#oms-error-message-template"),
  
	coreTableActions: function()  {
		
        var skuQuantityClass = '.sku-quantity';

		var quantityBefore = 0;
		var quantityAfter = 0;

		ACC.productorderform.$addToCartOrderForm.on('click', skuQuantityClass, function(event) {
            $(this).select();
        });

        ACC.productorderform.$addToCartOrderForm.on('focusin', skuQuantityClass, function(event) {
            quantityBefore = jQuery.trim(this.value);
            if (quantityBefore == "") {
                quantityBefore = 0;
                this.value = 0;
            }
        });

        ACC.productorderform.$addToCartOrderForm.on('focusout', skuQuantityClass, function(event) {
            var indexPattern           = "[0-9]+";
            var currentIndex           = parseInt($(this).attr("id").match(indexPattern));
            var $gridGroup             = $(this).parents('.orderForm_grid_group');
            var $closestQuantityValue  = $gridGroup.find('#quantityValue');
            var $closestAvgPriceValue  = $gridGroup.find('#avgPriceValue');
            var $closestSubtotalValue  = $gridGroup.find('#subtotalValue');
            var $currentTotalItems     = $('#total-items-count');
            var currentTotalItemsValue = $currentTotalItems.html();
            var currentTotalPrice      = $('#total-price-value').val();
            var currentQuantityValue   = $closestQuantityValue.val();
            var currentSubtotalValue   = $closestSubtotalValue.val();

            var totalPrice = 0;
            var currentPrice = $("input[id='productPrice["+currentIndex+"]']").val();

            quantityAfter = jQuery.trim(this.value);

            if (isNaN(jQuery.trim(this.value))) {
                this.value = 0;
            }

            if (quantityAfter == "") {
                quantityAfter = 0;
                this.value = 0;
            }

            if (quantityBefore == 0) {
                $closestQuantityValue.val(parseInt(currentQuantityValue) + parseInt(quantityAfter));
                $closestSubtotalValue.val(parseFloat(currentSubtotalValue) + parseFloat(currentPrice) * parseInt(quantityAfter));

                $currentTotalItems.html(parseInt(currentTotalItemsValue) + parseInt(quantityAfter));
                totalPrice = parseFloat(currentTotalPrice) + parseFloat(currentPrice) * parseInt(quantityAfter);
            } else {
                $closestQuantityValue.val(parseInt(currentQuantityValue) + (parseInt(quantityAfter) - parseInt(quantityBefore)));
                $closestSubtotalValue.val(parseFloat(currentSubtotalValue) + parseFloat(currentPrice) * (parseInt(quantityAfter) - parseInt(quantityBefore)));

                $currentTotalItems.html(parseInt(currentTotalItemsValue) + (parseInt(quantityAfter) - parseInt(quantityBefore)));
                totalPrice = parseFloat(currentTotalPrice) + parseFloat(currentPrice) * (parseInt(quantityAfter) - parseInt(quantityBefore));
            }
            
            // if there are no items to add, disable addToCartBtn, otherwise, enable it
            if ($('#total-items-count').text() == 0) {
                $('#addToCartBtn').attr('disabled','disabled');
            } else {
            	$('#addToCartBtn').removeAttr('disabled');
            }

            $('#total-price').html(ACC.productorderform.formatTotalsCurrency(totalPrice));
            $('#total-price-value').val(totalPrice);

            if (parseInt($closestQuantityValue.val()) > 0) {
                $closestAvgPriceValue.val(parseFloat($closestSubtotalValue.val()) / parseInt($closestQuantityValue.val()));
            } else {
                $closestAvgPriceValue.val(0);
            }

            $closestQuantityValue.parent().find('#quantity').html($closestQuantityValue.val());
            $closestAvgPriceValue.parent().find('#avgPrice').html(ACC.productorderform.formatTotalsCurrency($closestAvgPriceValue.val()));
            $closestSubtotalValue.parent().find('#subtotal').html(ACC.productorderform.formatTotalsCurrency($closestSubtotalValue.val()));

        });

	},

	bindUpdateFutureStockButton: function(updateFutureStockButton) {
		try{
		updateFutureStockButton.live("click", function(event) {
			event.preventDefault();

			var $gridContainer = $(this).parent().parent().find(".product-grid-container");
			var $skus          = jQuery.map($gridContainer.find("input[type='hidden'].sku"), function(o) {return o.value});
			var skusId         = $(this).data('skusId');
			var futureStockUrl = $(this).data('skusFutureStockUrl');
			var postData       = {skus: $skus, productCode: skusId};

			ACC.common.showSpinnerById(skusId);
			
			$.ajax({
				url:         futureStockUrl,
				type:        'POST',
				data:        postData,
				traditional: true,
				dataType:    'json',
				success:     function(data) { ACC.productorderform.updateFuture($gridContainer, $skus, data, skusId)},
				error:       function(xht, textStatus, ex) {
					ACC.common.hideSpinnerById(skusId);
					alert("Failed to get delivery modes. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				}
			});
		});
		}catch(e){}
	},

	bindExpandGridButton: function(expandGridButton) {
		expandGridButton.click(function(event) {
			event.preventDefault();

			$.colorbox({
				html:      ACC.productorderform.$addToCartOrderForm.clone(true),
				scroll:    true,
				width:     "98%",
				height:    "98%",
				onCleanup: function() { ACC.productorderform.syncGrid("#cboxContent", "form#AddToCartOrderForm") }
			});
		});
	},

	updateFuture: function(gridContainer, skus, freshData, callerId) {
		// clear prior error messages
		ACC.productorderform.$omsErrorMessageContainer.find("div").remove();

		if (freshData !== null && typeof freshData['basket.page.viewFuture.unavailable'] !== 'undefined') { 
			// future stock service is not available
			$.tmpl(ACC.productorderform.$omsErrorMessageTemplate, {
				errorMessage:  freshData['basket.page.viewFuture.unavailable']
			}).appendTo(ACC.productorderform.$omsErrorMessageContainer);
		}
		else {
			$.each(skus, function(index, skuId) {
				var stocks = freshData[skuId];

				var cell               = gridContainer.find("[data-sku-id='" + skuId + "']");
				var isCurrentlyInStock = cell[0].attributes['class'].nodeValue.indexOf("in-stock") != -1;
				var futureStockPresent = typeof stocks !== 'undefined' && stocks !== null && stocks[0] !== null && typeof stocks[0] !== 'undefined';

				cell.children(".future_tooltip, .out-of-stock, .future-stock").remove(); // remove previous tool tips

				if (futureStockPresent) {
					// we have stock for this product
					if (!isCurrentlyInStock) { cell.addClass("future-stock"); }

					// render template and append to cell
					$.tmpl(ACC.productorderform.$futureTooltipTemplate, {
						deliverMessage: ACC.productorderform.$addToCartOrderForm.data("gridFutureTooltipHeadingDelivery"),
						qtyMessage:     ACC.productorderform.$addToCartOrderForm.data("gridFutureTooltipHeadingQty"),
						formattedDate:  stocks[0].formattedDate,
						availabilities: stocks
					}).appendTo(cell);

				} else {
					// no future stock for this product
					if (!isCurrentlyInStock) {
						cell[0].attributes['class'].nodeValue = "td_stock out-of-stock";
					}
				}
			});
		}
		ACC.common.hideSpinnerById(callerId);
	},

	syncGrid: function(sourceContainer, targetContainer) {
		var $allSkus = $(sourceContainer + " .sku-quantity");

		$.each($allSkus, function(index, sku) {
			var selectorSuffix     = " input[name='" + sku.name + "'].sku-quantity";
			var $skuQuantitySource = $(sourceContainer + selectorSuffix);
			var $skuQuantityTarget = $(targetContainer + selectorSuffix);

			$skuQuantityTarget.val(sku.value);

			ACC.productorderform.syncTotalsBySku($skuQuantitySource, $skuQuantityTarget);
		});
	},

	toJSON: function(gridForm, skipZeroQuantity) {
		var skus          = gridForm.find("input.sku").map(function(index, element) {return element.value}),
			skuQuantities = gridForm.find("input.sku-quantity").map(function(index, element) {return parseInt(element.value)}),
			skusAsJSON      = [];

		for (var i = 0; i < skus.length; i++) {
			if (!(skipZeroQuantity && skuQuantities[i] === 0)) {
				skusAsJSON.push({"sku": skus[i], "quantity": skuQuantities[i]});
			}
		}

		return JSON.stringify({"cartEntries": skusAsJSON});
	},

	syncTotalsBySku: function(skuQuantitySource, skuQuantityTarget) {

		var $sourceQuantityValue = $(skuQuantitySource).closest('.orderForm_grid_group').find('#quantityValue');
		var $sourceAvgPriceValue = $(skuQuantitySource).closest('.orderForm_grid_group').find('#avgPriceValue');
		var $sourceSubtotalValue = $(skuQuantitySource).closest('.orderForm_grid_group').find('#subtotalValue');

		var $targetQuantityValue = $(skuQuantityTarget).closest('.orderForm_grid_group').find('#quantityValue');
		var $targetAvgPriceValue = $(skuQuantityTarget).closest('.orderForm_grid_group').find('#avgPriceValue');
		var $targetSubtotalValue = $(skuQuantityTarget).closest('.orderForm_grid_group').find('#subtotalValue');

		$targetQuantityValue.parent().find('#quantity').html($sourceQuantityValue.val());
		$targetAvgPriceValue.parent().find('#avgPrice').html(Number($sourceAvgPriceValue.val()).toFixed(2));
		$targetSubtotalValue.parent().find('#subtotal').html(Number($sourceSubtotalValue.val()).toFixed(2));

		$targetQuantityValue.val($sourceQuantityValue.val());
		$targetAvgPriceValue.val($sourceAvgPriceValue.val());
		$targetSubtotalValue.val($sourceSubtotalValue.val());
	},
	
	formatTotalsCurrency: function(amount)  {
		return Currency.formatMoney(Number(amount).toFixed(2), Currency.money_format[ACC.common.currentCurrency]);
	},
	
	cleanValues: function() {
		if ($(".orderForm_grid_group").length !== 0) {
			var formattedTotal = ACC.productorderform.formatTotalsCurrency('0.00');

			ACC.common.$page.find('#avgPrice, #subtotal, #total-price').html(formattedTotal);
			ACC.common.$page.find('#quantity, #total-items-count').html(0);

			ACC.common.$page.find('#quantityValue, #avgPriceValue, #subtotalValue, #total-price-value').val(0);
			ACC.productorderform.$skuQuantityInputs.val(0);
		}
	},

	bindAll: function() {
		ACC.productorderform.coreTableActions();
		ACC.productorderform.bindUpdateFutureStockButton($(".update_future_stock_button"));
		ACC.productorderform.bindExpandGridButton($(".js-expand-grid-button"));
	}
};

$(document).ready(function() {
	ACC.productorderform.bindAll();
	ACC.productorderform.cleanValues();
});;

ACC.skiplinks = {

	bindAll: function()
	{
		this.bindLinks();
	},

	bindLinks: function()
	{
		$("a[href^='#']").not("a[href='#']").click(function()
		{
			var target = $(this).attr("href");
			$(target).attr("tabIndex", -1).focus();
		});
	}
};

$(document).ready(function ()
{
	try{
	if ($.browser.webkit)
	{
		ACC.skiplinks.bindAll();
	}
	}catch(e){}
});
;

/*price range*/

$("#ex2").bootstrapSlider({});
/*responsive tabs */
 $(document).ready(function () {
        $('#horizontalTab, .horizontalTab').easyResponsiveTabs({
            type: 'default', //Types: default, vertical, accordion           
            width: 'auto', //auto or any width like 600px
            fit: true,   // 100% fit in a container
            closed: 'accordion', // Start closed if in accordion view
            activate: function(event) { // Callback function if tab is switched
                var $tab = $(this);
                var $info = $('#tabInfo');
                var $name = $('span', $info);

                $name.text($tab.text());

                $info.show();
            }
        });
    });	
/*Responsive Footer*/

$(function(){
	var winIsSmall;
	$(window).on('load resize', footerAccordion );

	function footerAccordion() {
		winIsSmall = window.innerWidth < 769;
		$('.col-xs-12 .mobslider').toggle(!winIsSmall);
	}

	$('.col-xs-12').find('h5').click(function () {
        /*
         * this code will loop through all of the h5s within the containing div
         * to find the actual one selected and then select its sibling-ul.
         * this is because the actual jsp code does not split up the various h5/ul
         * pairs in footer when setting footer links under one another.
         *
         * I have already bashed my head against the wall in punishment for this crazy code.
         */
		if (winIsSmall) {
			var h5 = this;
			var parentDiv = $(h5).parents(".link-container")[0];
			var children = $(parentDiv).children("h5, ul");
			var ulIx = -1;
			$.each(children, function(i,o) {
				if (o == h5) {
					ulIx = i+1;
					return false;
				}
			});

            if (ulIx >= 0) {
                var ul = children[ulIx];
                $(h5).toggleClass('expandFooterLink');
                $(ul).stop().toggleClass('expandFooterLink').slideToggle();
            }
		}
	});

});
/* display tooltip */
$('.tip').tooltip();

/* add slim scroll to div */



 $(function(){

      $('#productDescPanel').slimScroll({
          height: '115px',
          width: '96%'
      });
	  $('#myClassPointsPanel').slimScroll({
          height: '100px',
          width: '96%'
      });
});


 /*for custom input type file*/
 $(document).on('change', '.btn-file :file', function() {
  var input = $(this),
      numFiles = input.get(0).files ? input.get(0).files.length : 1,
      label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
  input.trigger('fileselect', [numFiles, label]);
});

$(document).ready( function() {
	$('.btn-file :file').on('fileselect', function(event, numFiles, label) {
		var input = $(this).parents('.input-group').find(':text'),
		log = numFiles > 1 ? numFiles + ' files selected' : label;

		if( input.length ) {
			input.val(log);
		} else {
			if( log ) alert(log);
		}
	});

	$('select#whatdescribes').on('change', function() {
		// Show Registration Details section and retrieve the registration type...
		$('div.panel-frm-filled').show();
		var optVal= $(this).val();

		// Set the email type on the form based on what the registration type is...
		var formEmail=$('select#whatdescribes :selected').text();
		$("input[type=hidden][name=technicianType]").val(formEmail);

		// Here we disable and reset company address information. This is always disabled and will be auto-filled based on the account code / employee id selected...
		resetDisabledCompanyFields();
		getUnits(true);
		validateAccount();

		// Here we reset all the type-customizable fields so that they are hidden and then show them based on optVal...
		displayFieldsForUserType(optVal);
	});
/*social share plugin*/

  $("#share").click(function(){
    $("#twitbtn,#fbbtn,#gglbtn,#stmblbtn,#lnkdbtn").slideToggle("slow");
  });



/* store locator google map slide*/


  $(".searchStLocator, .searchStLoc").click(function(){
    $("#map").slideDown(2000);
	$('#defaultmap').hide();
    $(".facloseFont").show();
  });
  
  $("#closestloc").click(function(){
    $("#map").slideUp(2000);
	$('#defaultmap').show();
    $(".facloseFont").hide();
  });
});

/*stop auto scroll for carousel*/

$('.carousel').carousel({
pause: false,
interval: 10000
});

/*Show modal popup vertically center*/

function centerModal() {
    $(this).css('display', 'block');
    var $dialog = $(this).find(".modal-dialog");
    var offset = ($(window).height() - $dialog.height()) / 2;
    // Center modal vertically in window
    $dialog.css("margin-top", offset);
}

$('.modal').on('show.bs.modal', centerModal);
$(window).on("resize", function () {
    $('.modal:visible').each(centerModal);
});


/*Expanding and Collapsing Panel or Div (E.g. In Registration page) */
$(document).on('click', '.panel div.clickable', function (e) {

    var $this = $(this);
    if (!$this.hasClass('panel-collapsed')) {
        	
	$this.parents('.panel').find('.panel-body').slideDown();
        $this.addClass('panel-collapsed');
        $this.find('i').removeClass('fa-plus').addClass('fa-minus');
		$this.parent('.panel').addClass('panel-frm-filled');
		
    } else {
        $this.parents('.panel').find('.panel-body').slideUp();
        $this.removeClass('panel-collapsed');
        $this.find('i').removeClass('fa-minus').addClass('fa-plus');
		$this.parent('.panel').removeClass('panel-frm-filled');
		
    }

});
$(document).ready(function () {

    $('.panel-heading span.clickable').click();
	$('.panel div.clickable').click();
	
	var nabscode = $('#nabscode').val();
	$('.fm_btn_olt-overview').attr('href','http://fdm.epiinc.com/IFA/FDM/cgi-bin/login100?clkey=fdm&qqid01=262&qqid02='+nabscode);
	
});

function hideAstricForDisabledBoxes(){
	
	$('#companyPhoneSpan').hide();
	$('#companyZipSpan').hide();
	$('#companyStateSpan').hide();
	$('#CcountrySpan').hide();
	$('#companyCitySpan').hide();
	$('#companyAddressSpan').hide();
	$('#companyNameSpan').hide();	
	
}

function showAstricForDisabledBoxes(){
	
	$('#companyPhoneSpan').show();
	$('#companyZipSpan').show();
	$('#companyStateSpan').show();
	$('#CcountrySpan').show();
	$('#companyCitySpan').show();
	$('#companyAddressSpan').show();
	$('#companyNameSpan').show();	
}

$('#registrationB2b').submit(function(){
	try{
	  $("#regsubmit").prop("disabled", true).addClass("disabled");

	    $(this).find('input:text').each(function(){
	          $(this).val($.trim($(this).val()));
	    });
	    $(this).find('input:tel').each(function(){
	        $(this).val($.trim($(this).val()));
	  });
	}catch(e){
	}
});

function toggleGarageRewardPromoCode(obj) {
	if (obj.checked) {
		$('#form-ifGarageGuruChecked').show();
	} else {
		$('#form-ifGarageGuruChecked').hide();
	}
}

function setHomeAddressToCompanyAddress(obj) {

	var checkedFlag = obj.checked;
	
	if (checkedFlag) {
		var acccode = $('#accCode').val();
		
		var currentPath = window.location.href;
	
		var pathName = "";
		try {
			if (currentPath.indexOf("/USD") != -1) {
				pathName = currentPath.substring(0, currentPath
						.lastIndexOf("/USD") + 5);
			} else if (currentPath.indexOf("?site") != -1) {
				pathName = currentPath.substring(0, currentPath
						.lastIndexOf("/?site") + 1)
						+ currentPath.substring(currentPath
								.lastIndexOf("site=") + 5,
								currentPath.length) + "/en/USD/";
			} else {
				pathName = window.location.href + "federalmogul/en/USD/";
			}
		} catch (e) {
			alert(e);
		}
	
		if (acccode.value != null || acccode != "") {
			var ajaxURL = pathName + "my-fmaccount/getFMUnitDetailsForUpdateProfile";
			$.ajax({
				type : "POST",
				url : ajaxURL,
				contentType: 'text/xml',
				data : "acctNbr=" + acccode,
				success : function(xmlDoc) {
			  		 var companyName = $(xmlDoc).find("companyName").text().trim();
			  		 var companyAddressLine1 = $(xmlDoc).find("companyAddressLine1").text().trim();
			  		 var companyAddressLine2 = $(xmlDoc).find("companyAddressLine2").text().trim();
			  		 var companyCity = $(xmlDoc).find("companyCity").text().trim();
			  		 var companyStateOrProvIsoCode = $(xmlDoc).find("companyStateOrProvIsoCode").text().trim();
			  		 var companyCountryIsoCode = $(xmlDoc).find("companyCountryIsoCode").text().trim();
			  		 var companyZipOrPostalCode = $(xmlDoc).find("companyZipOrPostalCode").text().trim();

			  		 $("#contactAddress").val(companyAddressLine1);
		  			 $("#contactAddress2").val(companyAddressLine2);
		  			 $("#city").val(companyCity);
		  			 $("state").val(companyStateOrProvIsoCode).change();
		  			 $("pcountry").val(companyCountryIsoCode).change();
		  			 $("#zip").val(companyZipOrPostalCode);
				},
				error : function(e) {
				}
			});
		}
	}
}

function GarageRewardMemberQuestion(obj){
	toggleGarageRewardPromoCode(obj);
	if (obj.checked) {
		$('#form-addresssamecheckbox').show();
		$('#tech-rewards-questions').show();
		enableHomeaddress();
	}else{
		$('#form-addresssamecheckbox').hide();
		$('#tech-rewards-questions').hide();
		disableHomeaddress();
	}
}

function onAboutShop(obj){
	if($(obj).val() == 'Reg003'){
		$('.shopBanner').show();
	}else{
		$('.shopBanner').hide();
	}
}

function disableHomeaddress(){
	
	//disable the fileds
	$('.form-address1').hide();
	$('.form-address2').hide();
	$('.form-country').hide();
	$('.form-state').hide();
	$('.form-zipcode').hide();
	$('.form-phonenumber').hide();
	$('.form-city').hide();
	// remove the required filed options.
	$('#contactAddress').prop('required', false);
	$('#city').prop('required', false);
	$('#pcountry').prop('required', false);
	$('#state').prop('required', false);
	$('#zip').prop('required', false);
	$('#phone').prop('required', false);
}
function enableHomeaddress(){
	
	//enable the fileds
	$('.form-address1').show();
	$('.form-address2').show();
	$('.form-country').show();
	$('.form-state').show();
	$('.form-zipcode').show();
	$('.form-phonenumber').show();
	$('.form-city').show();
	
	// add the required filed options.
	$('#contactAddress').prop('required', true);
	$('#city').prop('required', true);
	$('#pcountry').prop('required', true);
	$('#state').prop('required', true);
	$('#zip').prop('required', true);
	$('#phone').prop('required', true);
}
;

var FM_ACCOUNTCODE_INTERNAL_USERS = "16427113582";

String.prototype.startsWith = function (str)
{
	return this.indexOf(str) == 0;
}

function validateRegistration(obj) {
	var value = obj.value;
	var ide = obj.id;

	if (obj.id == "email") {
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
			return true;
		} else {
			alert("You have entered an invalid email address!");
			return false;
		}
	}
	if (obj.id == "setnewpwd") {
		if (value.length < 6) {
			alert("minimum characters should be 6");
			return false;
		} else {
			return true;
		}
	}
	if (obj.id == "confPassword") {
		if (value == document.registrationB2b.setnewpwd.value) {
			return true;
		} else {
			alert("password mismatch");
			return false;
		}
	}
	if (obj.id == "city" || obj.id == "companyCity") {
		var letters = /^[A-Za-z]+$/;
		if (value.match(letters)) {
			return true;
		} else {
			alert("enter only characters");
			return false;
		}
	}
	if (obj.id == "zip" || obj.id == "companyZip") {
		var regZipcode = /^([0-9]){5}(([ ]|[-])?([0-9]){4})?$/;
		if (value.match(regZipcode)) {
			return true;
		} else {
			alert("enter the correct zip code");
			return false;
		}
	}
	if (obj.id == "phone" || obj.id == "companyPhone") {

		var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
		if (value.match(phoneno)) {
			return true;
		} else {
			alert("enter valid phone number");
		}
	}
}

function validateCountry(ucountry) {
	if (ucountry.value == "Default") {
		ucountry.focus();
		return false;
	} else {
		return true;
	}
}

function validateState(ustate) {
	if (ustate.value == "Default") {
		return false;
	} else {
		return true;
	}
}

function addresssame(val) {
	if (val.checked) {
		document.getElementById('contactAddress').value = document.getElementById('companyAddress').value;
		document.getElementById('contactAddress2').value = document.getElementById('companyAddress2').value;
		document.getElementById('city').value = document.getElementById('companyCity').value;
		document.getElementById('state').value = document.getElementById('companyState').value;
		document.getElementById('zip').value = document.getElementById('companyZip').value;
		document.getElementById('pcountry').value = document.getElementById('Ccountry').value;
		document.getElementById('phone').value = document.getElementById('companyPhone').value;

		return true;
	} else {
		document.getElementById('contactAddress').value = " ";
		document.getElementById('contactAddress2').value = " ";
		document.getElementById('city').value = " ";
		document.getElementById('state').value = " ";
		document.getElementById('zip').value = " ";
		document.getElementById('pcountry').value = " ";
		document.getElementById('phone').value = " "
			
		var temp="Default"; 
	    $("#state").val(temp);
	    $("#pcountry").val(temp);
	}

}

function showLms(val)
{
	if(val.checked) {
		$('.forLoyaltyRewards').show();
	} else {
		$('.forLoyaltyRewards').hide();
	}
}

function getUnits(readOnly) {
	try {
		readOnly = readOnly || false;
		var acccode = $('#accNo').val();

		var selectedTest = $('select#whatdescribes :selected').text();

		// Basic validation for FM employees...
		if (selectedTest.toLowerCase().startsWith('F-M Employee'.toLowerCase())) {
			var errorId = "errorEmployeeId";
			acccode = $('#employeeId').val();
			if (acccode.toLowerCase().startsWith('SUS'.toLowerCase()) || acccode.toLowerCase().startsWith('SCA'.toLowerCase())) {
				document.getElementById(errorId).innerHTML = "";
			} else {
				document.getElementById(errorId).innerHTML = "Please enter a valid Employee ID";
				return false;
			}
		}

		if (!selectedTest.startsWith('Repair') && !selectedTest.startsWith('Technician') && !selectedTest.startsWith('Student') && !selectedTest.startsWith('Consumer')) {

			// Here the account code is hacked in for FM emails...
			var email = $('#email').val();
			var substring = "@federalmogul.com";
			if (acccode === null || acccode === "") {
				if (email.indexOf(substring) > -1) {
					acccode = FM_ACCOUNTCODE_INTERNAL_USERS;
					$('.form-roles').hide();
				}

			}

			var currentPath = window.location.href;

			var pathName = "";
			try {
				if (currentPath.indexOf("/USD") != -1) {
					pathName = currentPath.substring(0, currentPath
							.lastIndexOf("/USD") + 5);
				} else if (currentPath.indexOf("?site") != -1) {
					pathName = currentPath.substring(0, currentPath
							.lastIndexOf("/?site") + 1)
							+ currentPath.substring(currentPath
									.lastIndexOf("site=") + 5,
									currentPath.length) + "/en/USD/";
				} else {
					pathName = window.location.href + "federalmogul/en/USD/";
				}
			} catch (e) {
				alert(e);
			}
			if (acccode.value != null || acccode != "") {
				$.ajax({
					type : "POST",
					url : pathName + "/registration/getFMUnitDetails",
					data : "accvalue=" + acccode + "&readOnly=" + readOnly,
					success : function(response) {
						$('#companyDiv').html(response);
					},
					error : function(e) {
					}
				});
			}

		}
	} catch (err) {
	}

}
function afterGetUnits()
{
	var compName = $('#companyName').val();

	if(compName === null || compName === "") {
		document.getElementById("errorAccCode").innerHTML = "Please enter a valid Account Code";
	} else {
		document.getElementById("errorAccCode").innerHTML = " ";
	}
}

function searchUnits() 
{
	var currentPath = window.location.href;
	var pathName = "";
	try{
		if(currentPath.indexOf("/USD") != -1){
    	   pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
		}else if(currentPath.indexOf("?site") != -1){
    	   pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/USD/";
		}else{
    	   pathName = window.location.href+"federalmogul/en/USD/";
		}
	}catch(e){
		alert(e);
	}
	var acccode = $('#parentUnit').val();
	
	if(acccode.value !== null  || acccode !== "") {
		$.ajax({
			type : "POST",
			url : pathName+"my-account/manage-users/searchParentUnits",
			data : "accvalue=" + acccode,
			async: false,
	        cache: false,
	        dataType:'html',
			success : function(response) {
				$('#livesearch').show();
				$('#livesearch').html(response);
			},
			error : function(e) {
			}
		});
	}
}

function hideUnitsList(obj){
	var selectedAccountId = obj.value;
	
	document.getElementById('parentUnit').value = selectedAccountId;
	$('#livesearch').hide();
}

function setParentUnits() 
{
	var whatdescribes = $('#whatdescribes').val();
	var optVal = $('select#whatdescribes :selected').text();

	if (optVal.toLowerCase().startsWith('CSR'.toLowerCase())) {
		$('.create-fmroles').hide();
		$('.form-alerts').hide();
		document.getElementById('parentUnit').value = FM_ACCOUNTCODE_INTERNAL_USERS;
		document.getElementById('parentUnit').disabled = true;
	} 

	if (optVal == 'JOBBERPARTSTORE' || optVal == 'SHOPOWNERTECHNICIAN' || optVal == 'REPAIRSHOPOWNER' || optVal == 'TECHNICIAN') {
		$('.form-alerts').show();
		$('.create-fmroles').hide();
	} else {
		if(!(optVal.toLowerCase().startsWith('CSR'.toLowerCase()))){	
			$('.form-alerts').hide();
			$('.create-fmroles').show();
			document.getElementById('parentUnit').disabled = false;
			document.getElementById('parentUnit').value = "";
		}
	}

	if (whatdescribes == "CONSUMERDIFM"  || whatdescribes == "CONSUMERDIY" || whatdescribes == "CONSUMER") {
		var currentPath = window.location.href;
		var pathName = "";
		try{
			if(currentPath.indexOf("/USD") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
			}else if(currentPath.indexOf("?site") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/USD/";
			}else{
				pathName = window.location.href+"federalmogul/en/USD/";
			}
		}catch(e){
			alert(e);
		}
	
		$.ajax({
			type : "POST",
			url : pathName+"my-account/manage-users/create/setParentUnit",
			data : "whatdescribes=" + whatdescribes,
			success : function(response) {
				$('#parentDiv').html(response);
			},
			error : function(e) {
				alert("Error" + e);
			}
		});
	}
}

function getRegions() 
{
	var country = $('#pcountry').val();
	if(country.value != null  || country != "") {
		var currentPath = window.location.href;
		var pathName = "";
		try{
			if(currentPath.indexOf("/USD") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
			}else if(currentPath.indexOf("?site") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/USD/";
			}else{
				pathName = window.location.href+"federalmogul/en/USD/";
			}
		}catch(e){
			alert(e);
		}
	
		$.ajax({
			type : "POST",
			url : pathName+ "registration/getStates",
			data : "selectedCountry=" + country,
			success : function(response) {
				$('#userState').html(response);
			},
			error : function(e) {
			}
		});
	}
}

function getRegionsForaddedit() 
{
	var country = $('#Country').val();
	
	if(country.value != null  || country != "") {
		var currentPath = window.location.href;
		var pathName = "";
		try{
			if(currentPath.indexOf("/USD") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
			}else if(currentPath.indexOf("?site") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/USD/";
			}else{
				pathName = window.location.href+"federalmogul/en/USD/";
			}
		}catch(e){
			alert(e);
		}
	
		$.ajax({
			type : "POST",
			url : pathName+"my-fmaccount/getStatesForAddEdit",
			data : "selectedCountry=" + country,
			success : function(response) {
				$('#addEditState').html(response);
			},
			error : function(e) {
			}
		});
	}
}

function getCompanyStates() 
{
	var country = $('#Ccountry').val();
	if(country.value != null  || country != "") {
		var currentPath = window.location.href;
		var pathName = "";
		try{
			if(currentPath.indexOf("/USD") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/USD") + 5);
			}else if(currentPath.indexOf("?site") != -1){
				pathName = currentPath.substring(0, currentPath.lastIndexOf("/?site") + 1)+currentPath.substring(currentPath.lastIndexOf("site=")+5,currentPath.length)+"/en/USD/";
			}else{
				pathName = window.location.href+"federalmogul/en/USD/";
			}
		}catch(e){
			alert(e);
		}	
	
		$.ajax({
			type : "POST",
			url : pathName+ "registration/getCompanyStates",
			data : "selectedCountry=" + country,
			success : function(response) {
				$('#unitState').html(response);
			},
			error : function(e) {
			}
		});
	}
}

function retainRegistrationValues() 
{
	$('div.panel-frm-filled').show();

	var optVal = $('#sessionusertype').val();

	if (optVal == 'CONSUMERDIY' || optVal == 'CONSUMERDIFM' || optVal == 'CONSUMER') {
		$('.form-accCode').hide();
		$('.form-taxid').hide();
		$('.form-B2Cbody').hide();
		$('.form-B2Bbody').hide();
		$('.form-secretque').hide();
		$('.form-secretans').hide();
		$('.form-sameaddress').hide();
		$('.form-city').show();
		$('.form-state').show();
		$('.form-zipcode').show();
		$('.form-regdetailsheader').show();
		$('.form-b2bSub').hide();
		$('.form-b2cSub').show();
		$('.forLoyaltyRewards').hide();
		$('#tech-rewards-questions').hide();
		$('.forReferBy').hide();

		document.registrationB2b.email.focus();
		
		//form-B2Bbody
		document.getElementById("taxid").required = false;
		document.getElementById("accNo").required = false;
		document.getElementById("companyName").required = false;
		document.getElementById("companyAddress").required = false;
		document.getElementById("companyCity").required = false;
		document.getElementById("Ccountry").required = false;
		document.getElementById("companyState").required = false;
		document.getElementById("companyZip").required = false;
		document.getElementById("companyPhone").required = false;

	} else if (optVal == 'JOBBERPARTSTORE') {
		$('.form-accCode').hide();
		$('.form-B2Cbody').hide();
		$('.form-reg').hide();
		$('.form-IsUser').hide();
		$('.form-regdetailsheader').show();
		$('.form-taxid').show();
		$('.form-taxdoc').show();
		$('.form-B2Bbody').show();
		$('.form-city').show();
		$('.form-state').show();
		$('.form-zipcode').show();
		$('.form-b2bSub').show();
		$('.form-b2cSub').hide();

		showAstricForDisabledBoxes();

		$('#companyAddress').prop('disabled', false);
		$('#companyAddress2').prop('disabled', false);
		$('#companyCity').prop('disabled', false);
		$('#companyState').prop('disabled', false);
		$('#companyZip').prop('disabled', false);
		$('#Ccountry').prop('disabled', false);
		$('#companyPhone').prop('disabled', false);
		$('#companyFax').prop('disabled', false);
		$('#companyName').prop('disabled', false);
		$('.forLoyaltyRewards').hide();
		$('#tech-rewards-questions').hide();
		$('.forReferBy').hide();

		document.registrationB2b.taxid.focus();
		document.getElementById("accNo").required = false;

		$('#form-addresssamecheckbox').show();

	} else if (optVal == 'SHOPOWNERTECHNICIAN' || optVal == 'REPAIRSHOPOWNER' || optVal == 'TECHNICIAN') {
		$('.form-accCode').hide();
		$('.form-B2Cbody').hide();
		$('.form-reg').hide();
		$('.form-IsUser').hide();
		$('.form-regdetailsheader').show();
		$('.form-taxid').hide();
		$('.form-taxdoc').show();
		$('.form-B2Bbody').show();
		$('.form-city').show();
		$('.form-state').show();
		$('.form-zipcode').show();
		$('.form-b2bSub').show();
		$('.form-b2cSub').hide();

		showAstricForDisabledBoxes();
		
		$('#companyAddress').prop('disabled', false);
		$('#companyAddress2').prop('disabled', false);
		$('#companyCity').prop('disabled', false);
		$('#companyState').prop('disabled', false);
		$('#companyZip').prop('disabled', false);
		$('#Ccountry').prop('disabled', false);
		$('#companyPhone').prop('disabled', false);
		$('#companyFax').prop('disabled', false);
		$('#companyName').prop('disabled', false);
		$('.forLoyaltyRewards').hide();
		$('#tech-rewards-questions').hide();
		$('.forReferBy').hide();

		disableHomeaddress();
		document.registrationB2b.taxid.focus();
		document.getElementById("accNo").required = false;

	} else if (optVal == 'WAREHOUSEDISTRIBUTORLIGHTVEHICLE' || optVal == 'WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE' || optVal == 'RETAILER' || optVal == 'SALESREP') {
		if (optVal == 'SALESREP') {
			$('#accCodeToolTip').attr('data-original-title', "Account code must start with SUS/SCA");
			$('.form-reg').hide();
		} else {
			$('#accCodeToolTip').attr('data-original-title', "Do not have an Account Code? Check with Customer Care");
			$('.form-reg').show();
		}

		$('.form-taxid').hide();
		$('.form-taxdoc').hide();

		$('.form-IsUser').show();
		$('.form-B2Bbody').show();
		$('.form-B2Cbody').hide();
		$('.form-b2bSub').hide();
		$('.form-b2cSub').hide();
		$('.form-secretque').show();
		$('.form-secretans').show();
		$('.form-sameaddress').show();
		$('.form-city').show();
		$('.form-state').show();
		$('.form-zipcode').show();
		$('.forLoyaltyRewards').hide();
		$('#tech-rewards-questions').hide();
		$('.forReferBy').hide();
		$('.form-regdetailsheader').show();

		hideAstricForDisabledBoxes();
		document.registrationB2b.accNo.focus();
		document.getElementById("taxid").required = false;

		$('#form-addresssamecheckbox').show();
	}

	showLmsonload();
	
	var isGarageRewardMember = $('#GarageRewardMember');
	if (isGarageRewardMember.is(':checked') && (optVal == 'SHOPOWNERTECHNICIAN' || optVal == 'REPAIRSHOPOWNER' || optVal == 'TECHNICIAN')) {
		$('#tech-rewards-questions').show();
		$('#form-ifGarageGuruChecked').show();
		$('#form-addresssamecheckbox').show();	

		enableHomeaddress();
	} else {
		$('#tech-rewards-questions').hide();
		$('#form-ifGarageGuruChecked').hide();
		$('#form-addresssamecheckbox').hide();	
	}
	
	var abtShop = $('#aboutShop').val();
	if (abtShop == 'Member of a banner program') {
		$('.shopBanner').show();
	} else {
		$('.shopBanner').hide();
	}
	
	var isGarage = $('input:radio[name=loyaltySignup]:checked').val();
	if (isGarage == 'Yes' ) {
		$('.forLoyaltyRewards').show();
	}else{
		$('.forLoyaltyRewards').hide();
	}
}

function addeditUser()
{
	var optVal=$('select#whatdescribes :selected').text();

	if (optVal.toLowerCase().startsWith('CSR'.toLowerCase())) {
		
		$('.create-fmroles').hide();
		$('.form-alerts').hide();
		document.getElementById('parentUnit').value = FM_ACCOUNTCODE_INTERNAL_USERS;
		document.getElementById('parentUnit').disabled = true;
	} 

	if (optVal == 'JOBBERPARTSTORE' || optVal=='SHOPOWNERTECHNICIAN')
	{
		$('.form-alerts').show();
		$('.create-fmroles').hide();
	} else {
		if(!(optVal.toLowerCase().startsWith('CSR'.toLowerCase()))) {
			$('.form-alerts').hide();
			$('.create-fmroles').show();
		}
	}
}

function hideAstricForDisabledBoxes()
{
	$('#companyPhoneSpan').hide();
	$('#companyZipSpan').hide();
	$('#companyStateSpan').hide();
	$('#CcountrySpan').hide();
	$('#companyCitySpan').hide();
	$('#companyAddressSpan').hide();
	$('#companyNameSpan').hide();	
}

function showAstricForDisabledBoxes()
{
	$('#companyPhoneSpan').show();
	$('#companyZipSpan').show();
	$('#companyStateSpan').show();
	$('#CcountrySpan').show();
	$('#companyCitySpan').show();
	$('#companyAddressSpan').show();
	$('#companyNameSpan').show();	
}

function getCountry() 
{
	var state = $('#state').val();
	if (state.startsWith('US')) {
		document.getElementById('pcountry').value = 'US';
	} else if(state.startsWith('CA')) {
		document.getElementById('pcountry').value = 'CA';
	}
}

function getCompanyCountry() 
{
	var state = $('#companyState').val();
	if (state.startsWith('US')) {
		document.getElementById('Ccountry').value = 'US';
	} else if (state.startsWith('CA')) {
		document.getElementById('Ccountry').value = 'CA';
	}
}

function showLmsonload()
{
	var status = $('#rewards');
	if (status.is(':checked')) {
		$('.forLoyaltyRewards').show();
	} else {
		$('.forLoyaltyRewards').hide();
	}
}

function checkIsLoyaltyopted()
{
	$('#form-ifGarageGuruChecked').hide();
	$('#form-addresssamecheckbox').hide();
	$('#tech-rewards-questions').hide();

	disableHomeaddress();
}

function setunitsCreatePage()
{
	var email = $('#email').val();
	var acc = 	$('#parentUnit').val();
	var substring="@federalmogul.com";

	$('.create-fmroles').show();
	if (acc === null || acc === "" || acc === FM_ACCOUNTCODE_INTERNAL_USERS) {
		if (email.indexOf(substring) > -1) {
			var acccode = FM_ACCOUNTCODE_INTERNAL_USERS;
			$('#parentUnit').val(acccode);
			$('.create-fmroles').hide();
		}	
	} else {
		if(acc.toLowerCase().startsWith('SUS'.toLowerCase()) || acc.toLowerCase().startsWith('SCA'.toLowerCase())) {
			$('.create-fmroles').hide();
		}
	}
}

function validateConfirmEmail() {
	var email = $('#email').val();
	var confirmEmail = $('#confirmEmail').val();
	if (email && confirmEmail) {
		if (email.trim().toLowerCase() !== confirmEmail.trim().toLowerCase()) {
			document.getElementById("errorConfirmEmail").innerHTML = "Email addresses do not match.";
			return false;
		}
	}

	document.getElementById("errorConfirmEmail").innerHTML = "";
}

function validateEmail()
{
	var email = $('#email').val();
	var domainstring = "@federalmogul.com";
	if (email.length > 0) {
		if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
			var optVal = $('select#whatdescribes :selected').text();

			if (optVal.toLowerCase().startsWith('F-M Employee'.toLowerCase())) {
				if (!(email.indexOf(domainstring) > -1)) {
					document.getElementById("errorEmail").innerHTML = "Please enter  valid Email Address";
					email.focus();
					return false;

				}else{
					document.getElementById("errorEmail").innerHTML = "";
					return true;
				}
			} else {
				document.getElementById("errorEmail").innerHTML = "";
				return true;
			}
		} else {
			document.getElementById("errorEmail").innerHTML = "Please enter  valid Email Address";
			email.focus();
			return false;
		}
	}
}

function validatePassword()
{
	var pwd=$('#setnewpwd').val();
	if (pwd.length > 0) {
		if(/^(?=.*?[0-9])(?=.*[A-Z]).{8,}$/.test(pwd)){
			document.getElementById("errorPwd").innerHTML = "";	
		} else {
			document.getElementById("errorPwd").innerHTML = "Please enter  valid Password";
		}
	}
}

function validateAccount() 
{
	var selectedTest = $('select#whatdescribes :selected').text();

	var codeId = selectedTest.toLowerCase().startsWith('F-M Employee'.toLowerCase()) ? "#employeeId" : "#accNo";
	var errorId = selectedTest.toLowerCase().startsWith('F-M Employee'.toLowerCase()) ? "errorEmployeeId" : "errorAccCode";

	var accountNo = $(codeId).val();
	if (accountNo.length > 0) {
		var optVal = $('select#whatdescribes :selected').text();
		if (optVal.toLowerCase().startsWith('F-M Employee'.toLowerCase())) {
			if(!acccode.toLowerCase().startsWith('SUS'.toLowerCase()) || !acccode.toLowerCase().startsWith('SCA'.toLowerCase())){
				document.getElementById(errorId).innerHTML = "Please enter a valid Employee ID";	
			}else{
				document.getElementById(errorId).innerHTML = "";
			}
		}
	} else {
		document.getElementById(errorId).innerHTML = "";
	}
}

function validateConfPassword() 
{
	var pwd=$('#setnewpwd').val();
	var cpwd=$('#confPassword').val();
    if (pwd != cpwd) {
    	document.getElementById("errorCpwd").innerHTML = "Your Passwords do not match.";
        return false;
    }
    document.getElementById("errorCpwd").innerHTML = "";
    return true;
}

function validateCSREmail() 
{
	var email = $('#email').val();
	var domainstring = "@federalmogul.com";
	if (email.length > 0) {
		var optVal = $('select#whatdescribes :selected').text();
		if (optVal.toLowerCase().startsWith('CSR'.toLowerCase())) {
			if (!(email.indexOf(domainstring) > -1)) {
				document.getElementById("errorEmail").innerHTML = "Please enter  valid Email Address";
				email.focus();
				return false;
			} else {
				document.getElementById("errorEmail").innerHTML = "";
				return true;
			}
		} else {
			document.getElementById("errorEmail").innerHTML = "";
		}
	}
}

function resetDisabledCompanyFields() 
{
	document.getElementById("companyAddress").value="";
	document.getElementById("companyName").value="";
	document.getElementById("companyAddress2").value="";
	document.getElementById("companyState").value="";
	document.getElementById("companyZip").value="";
	document.getElementById("Ccountry").value="";
	document.getElementById("companyPhone").value="";
	document.getElementById("companyFax").value="";
	document.getElementById("companyCity").value="";
}

function displayFieldsForUserType(optVal) 
{
	// Hide all user-specific fields...
	$('.form-accCode').hide();					// Account number
	$('.form-employeeId').hide();				// Employee ID
	$('.form-b2bSub').hide();					// Email opt-in
	$('.form-b2bRegbody').hide();				// Company information
	$('.form-roles').hide();					// Required access radio group
	$('#registration-garage-rewards').hide();	// Garage Rewards opt in/out and related promo code
	$('.form-address1').hide();					// Address1
	$('.form-address2').hide();					// Address2
	$('.form-city').hide();						// City
	$('.form-country').hide();					// Country
	$('.form-state').hide();					// State/Province
	$('.form-zipcode').hide();					// Zip/postal code
	$('#registration-referred-by').hide();		// Referred by email

	switch (optVal) {
		case "WAREHOUSEDISTRIBUTORLIGHTVEHICLE":
			$('.form-accCode').show();
			$('.form-b2bSub').show();
			$('.form-b2bRegbody').show();
			$('.form-roles').show();
			break;
		case "WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE":
			$('.form-accCode').show();
			$('.form-b2bSub').show();
			$('.form-b2bRegbody').show();
			$('.form-roles').show();
			break;
		case "RETAILER":
			$('.form-accCode').show();
			$('.form-b2bSub').show();
			$('.form-b2bRegbody').show();
			$('.form-roles').show();
			break;
		case "JOBBERPARTSTORE":
			$('.form-accCode').show();
			$('.form-b2bSub').show();
			$('.form-b2bRegbody').show();
			$('.form-roles').show();
			break;
		case "SALESREP":
			$('.form-employeeId').show();
			$('.form-b2bRegbody').show();
			break;
		case "SHOPOWNERTECHNICIAN":
		case "REPAIRSHOPOWNER":
		case "TECHNICIAN":
		case "STUDENT":
			$('.form-b2bSub').show();
			$('#registration-garage-rewards').show();
			$('.form-address1').show();
			$('.form-address2').show();
			$('.form-city').show();
			$('.form-country').show();
			$('.form-state').show();
			$('.form-zipcode').show();
			$('#registration-referred-by').show();
			break;
		case "CONSUMER":
			$('.form-b2bSub').show();
			$('.form-country').show();
			$('.form-zipcode').show();
			break;
		default:
			$('#registration-account-details').hide();
			break;
	}
}
;

ACC.myfmaccount = {
	currentPath: window.location.href,
	pathName: "",
	currentCurrency: "USD",
	ajaxUrl: "USD/my-fmaccount/",
	$page:           $("#page"),
	$globalMessages: $("#globalMessages"),
	$taxDocApprovalSelect: $('#taxDocApproval'),
	options: "",
	url: "",
	
	
	conOptionStart: '<option value="',
	conOptionEnd: ' </option>',
	conValue: 'value',
	conSize: 'Size',
	setCurrentPath: function() {
		try{
			if(ACC.myfmaccount.currentPath.indexOf("/USD") != -1){
				ACC.myfmaccount.pathName = ACC.myfmaccount.currentPath.substring(0, ACC.myfmaccount.currentPath.lastIndexOf("/USD") + 1);
			}else if(ACC.myfmaccount.currentPath.indexOf("?site") != -1){
				ACC.myfmaccount.pathName = ACC.myfmaccount.currentPath.substring(0, ACC.myfmaccount.currentPath.lastIndexOf("/?site") + 1)+ACC.myfmaccount.currentPath.substring(ACC.myfmaccount.currentPath.lastIndexOf("site=")+5,ACC.myfmaccount.currentPath.length)+"/en/";
			}else{
				ACC.myfmaccount.pathName = window.location.href;
			}
		}catch(e){
			alert(e);
		}
	},
	
	bindAll: function() {
		with (ACC.myfmaccount) {
			bindToAddTaxDocApproval($taxDocApprovalSelect);
		}
	},

	bindToAddTaxDocApproval: function(taxDocApprovalSelect) {
		taxDocApprovalSelect.change(function(event) {
			
			//alert("Helooooooooooooo IM in myfmaccount js :########: "+this.value);
			var taxDocPKValue = $('#taxDocPK').val();
			//alert("taxDocIdValue :: "+taxDocPKValue);
			ACC.myfmaccount.Url = ACC.myfmaccount.pathName + ACC.myfmaccount.ajaxUrl+"update-taxexemption-approval/"+this.value+"/"+taxDocPKValue;

			ACC.myfmaccount.postAJAX(ACC.myfmaccount.taxDocApproval, ACC.myfmaccount.$taxDocApprovalSelect,"");
			
			
		});
	},
	
	postAJAX:function(reqType , selectField,defaultopt){
		alert("reqType :: "+reqType);
		$.ajax({
	    	 url: ACC.myfmaccount.Url, 		
	    	 async: false,
		  	 cache: false,	
	         success: function (xmlDoc) {
		
	       	 var respDoc = $(xmlDoc).find(reqType).text();
		   
	       	 var respSize = $(xmlDoc).find(reqType+ACC.myfmaccount.conSize).text();
			  
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
	},
};

$(document).ready(function() {
	ACC.myfmaccount.setCurrentPath();
	ACC.myfmaccount.bindAll();
	
	$(".description").each(function (i) {
		var b=  $(this).find("b").html();
		$(this).html(b);
		$(this).find("ul").remove();		  
	});
	
	
	
	
	
 
	
});

function openModal() {
	try{
    		document.getElementById('modal').style.display = 'block';
   		 document.getElementById('fade').style.display = 'block';
	}catch(e){
	}

}

function closeModal() {
	try{
		document.getElementById('modal').style.display = 'none';
		document.getElementById('fade').style.display = 'none';
	}catch(e){
	}
}


function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};


;

ACC.mycompany = {

	bindAll: function()
	{
		this.mycompanyInit()
		this.selectDeselectBudgetInit()
		this.selectDeselectUserInit()
		this.selectDeselectLinkInit()
		try{
		$("#unittree").treeview();
		}catch(e){}
		
	},

	mycompanyInit: function()
	{
		if($("#budgetStartDate").length>0 && $("#budgetEndDate").length>0){
			$("#budgetStartDate").datepicker({dateFormat: budgetStartEnd});
			$("#budgetEndDate").datepicker({dateFormat: budgetStartEnd});
			$("#budgetStartDate").datepicker( "option", "appendText",  budgetStartEndHint);
			$("#budgetEndDate").datepicker( "option", "appendText",  budgetStartEndHint);

			$('#editB2bBudgetform').validate({
				rules:{
					startDate:{
						required: true,
						dpDate: true
					},
					endDate:{
						required: true,
						dpDate: true
					}
				}
			});
		}
	
	},

	selectDeselectBudgetInit: function()
	{
		ACC.mycompany.bindToSelectBudget();
		ACC.mycompany.bindToDeselectBudget();
	},

	bindToSelectBudget: function()
	{
		$(document).on('click','.selectBudget',function(){
			$.postJSON(this.getAttribute('url'),{}, ACC.mycompany.selectionCallback);
			return false;
		});
		
	},

	bindToDeselectBudget: function()
	{
		$(document).on('click','.deselectBudget',function(){
			var url = this.getAttribute('url');
			ACC.mycompany.bindConfirmDeselectButton(url);
			ACC.mycompany.bindCancelDeselectButton();

			$.colorbox({
				inline: true,
				height: false,
				width: 300,
				href: "#deselect_budget_warning",
				overlayClose: false,
				onComplete: function ()
				{
					$(this).colorbox.resize();
				},
				onClosed: function()
				{
					$('#confirm_deselect').off('click');
				}
			});
		});
	},

	bindConfirmDeselectButton: function(url)
	{
		$(document).on('click','#confirm_deselect',function(){
			$.postJSON(url,{}, ACC.mycompany.deselectionCallback);
			$.colorbox.close();
		});
	},

	bindCancelDeselectButton: function()
	{
		$(document).on('click','#cancel_deselect',function(){
			$.colorbox.close();
		});
	},

	selectionCallback: function(budget)
	{
		$('#row-' + budget.normalizedCode).addClass("selected");
		$('#span-' + budget.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(budget));
	},

	deselectionCallback: function(budget)
	{
		$('#row-' + budget.normalizedCode).removeClass("selected");
		$('#span-' + budget.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(budget));
	},
	

	selectDeselectUserInit: function()
	{
		ACC.mycompany.bindToSelectUser();
		ACC.mycompany.bindToDeselectUser();
	},

	bindToSelectUser: function()
	{
		$(document).on('click','.selectUser',function(){
			$.postJSON(this.getAttribute('url'), {}, ACC.mycompany.selectionUserCallback);
			return false;
		});
	},

	bindToDeselectUser: function()
	{
		$(document).on('click','.deselectUser',function(){
			$.postJSON(this.getAttribute('url'), {}, ACC.mycompany.deselectionUserCallback);
			return false;
		});
	},

	selectionUserCallback: function(user)
	{
		if( typeof user.normalizedUid != 'undefined')
		{
			$('#row-' + user.normalizedUid).addClass("selected");
			$('#selection-' + user.normalizedUid).html($('#enableDisableLinksTemplate').tmpl(user));
			$('#roles-' + user.normalizedUid).html($('#userRolesTemplate').tmpl(user));
		}else{
			$('#row-' + user.normalizedCode).addClass("selected");
			$('#selection-' + user.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(user));
			$('#roles-' + user.normalizedCode).html($('#userRolesTemplate').tmpl(user));			
		}
	},

	deselectionUserCallback: function(user)
	{
		if( typeof user.normalizedUid != 'undefined')
		{
			$('#row-' + user.normalizedUid).removeClass("selected");
			$('#selection-' + user.normalizedUid).html($('#enableDisableLinksTemplate').tmpl(user));
			$('#roles-' + user.normalizedUid).html($('#userRolesTemplate').tmpl(user));
		}else{
			$('#row-' + user.normalizedCode).removeClass("selected");
			$('#selection-' + user.normalizedCode).html($('#enableDisableLinksTemplate').tmpl(user));
			$('#roles-' + user.normalizedCode).html($('#userRolesTemplate').tmpl(user));
		}
	},

	selectDeselectLinkInit: function()
	{
		ACC.mycompany.bindToSelectLink();
		ACC.mycompany.bindToDeselectLink();	
	},

	bindToSelectLink: function()
	{
		$(document).on('click','.selectionLink',function(){
			$.postJSON(this.getAttribute('url'), {}, ACC.mycompany.selectionCallback);
			return false;
		});
	},

	bindToDeselectLink: function()
	{
		$(document).on('click','.deselectionLink',function(){
			$.postJSON(this.getAttribute('url'), {}, ACC.mycompany.deselectionCallback);
			return false;
		});
	},


};

$(document).ready(function()
{
	ACC.mycompany.bindAll();
});



;

ACC.futurelink = {
	bindAll: function() {
		this.bindFutureStockLink();
	},
	
	bindFutureStockLink: function() {
		$(document).on("click",".futureStockLink", function(e) {
			e.preventDefault();
			$.colorbox({
				href:       $(this).attr("href"),
				width:      440,
				height:     250,
				onComplete: function() {
					$.colorbox.resize();
				}
			});
		})
	}

};

$(document).ready(function() {
	ACC.futurelink.bindAll();
});
;

var ACC = ACC || {}; // make sure ACC is available

ACC.search = {
	/*
	* Register selectors for later re-use.
	*/
	$selectedProductIdsContainer: $("#js-selected-product-ids"),
	$productIdsInput:             $("#js-product-ids"),
	$addProductIdsButton:         $("#js-add-product-ids"),
	$enableProductIdsCheckBox:    $("#js-enable-product-ids"),
	$productIdTagBoxTemplate:     $("#product-id-tag-box-template"),
	$removeProductIdButton:       $(".js-remove-product-id"),
	$advSearchButton:             $(".adv_search_button"),
	$skusHidden:                  $("#skus"),
	$searchCurrentLabel:          $(".searchInput label"),
	$createOrderFormButton:		  $("#js-create-order-form-button"),
	$createOrderFormCheckbox:	  $(".js-checkbox-sku-id, .js-checkbox-base-product"),
	$baseProductCheckBox:		  $(".js-checkbox-base-product"),
	$skuIDCheckbox:				  $(".js-checkbox-sku-id"),

	/*
	* Register all the event handlers.
	*/
	bindAll: function() {
		with (ACC.search) {
			bindToAddProductIds($addProductIdsButton);
			bindToToggleProductIds($enableProductIdsCheckBox);
			bindToRemoveProductId($removeProductIdButton);
			bindToAdvSearchButton($advSearchButton);
			bindToCreateOrderFormButton($createOrderFormButton);
			bindToBaseProductCheckBox($baseProductCheckBox);
			bindToSkuIDCheckBox($skuIDCheckbox);
		}
	},

	/*
	* Event handlers
	*/
	bindToAdvSearchButton: function(advSearchButton) {
		advSearchButton.click(function(event) {
			ACC.search.stripoutInvalidChars();

			if (ACC.search.$enableProductIdsCheckBox.attr("checked") === "checked") {
				if (ACC.search.$productIdsInput.val() != "") {
					ACC.search.$addProductIdsButton.click();
				}
				ACC.search.$productIdsInput.val(ACC.search.$skusHidden.val());
				ACC.search.$skusHidden.val("");
			}
		});
	},
	
	bindToCreateOrderFormButton: function(createOrderFormButton) {
		createOrderFormButton.click(function(event) {
			ACC.search.stripoutInvalidChars();

			ACC.search.$productIdsInput.val(ACC.search.$skusHidden.val());
			$("#isCreateOrderForm").val(true);
			
			var skusIds = [];
			ACC.search.$createOrderFormCheckbox.each(function() {
				
				if (this.checked) {
					skusIds.push(this.value);
				} 
				
			});

			ACC.search.$productIdsInput.val(skusIds.join(","));	
			ACC.search.$selectedProductIdsContainer.empty();
			ACC.search.$skusHidden.val("");
			ACC.search.$enableProductIdsCheckBox.attr("checked",false);
			// hide the add button
			ACC.search.$addProductIdsButton.hide();
			
			ACC.search.$advSearchButton.click();

		});
	},
	
	bindToBaseProductCheckBox: function(baseProductCheckBox){
		baseProductCheckBox.click(function(event) {
			var baseProduct = this;
			$(ACC.search.$skuIDCheckbox).each(function (){
				if ($(this).attr("base-product-code") && $(this).attr("base-product-code") === baseProduct.value){
					$(this).attr("checked",baseProduct.checked);
				}				
			})
		});
	},

	bindToSkuIDCheckBox: function(skuIDCheckbox){
		skuIDCheckbox.click(function(event) {
			var skuID = this;
			var baseProduct = $(skuID).attr("base-product-code");

			var hasChecked = false;
			$(ACC.search.$skuIDCheckbox).each(function (){
				if (baseProduct === $(this).attr("base-product-code") && $(this).attr("checked")){
					hasChecked = true;
				}				
			})
			
			$(".js-checkbox-base-product").each(function (){
				if (baseProduct === this.value){
					$(this).attr("checked",hasChecked);
				}				
			})	
			
		});
	},	
	// Add product id event handler
	bindToAddProductIds: function(addProductIdsButton) {
		addProductIdsButton.click(function(event) {
			if (ACC.search.$enableProductIdsCheckBox.attr("checked") === "checked") {
				event.preventDefault();
				
				// TODO: allow the delimiter being configurable!
				ACC.search.stripoutInvalidChars();
				var productIds = ACC.search.$productIdsInput.val().split(",");
	
				var presentProductIds = ACC.search.$skusHidden;
				
				//merge new ids with the present ones
				if(presentProductIds.val() != undefined && presentProductIds.val() !== ""){
					var persistentIds = presentProductIds.val().split(",");

					for(var i = 0; i < persistentIds.length; i++){
						productIds.push(persistentIds[i]);
					}
				}
				
				//  clean product ids
				productIds = $.map(productIds, function(productId, index){ return ($.trim(productId)); });
				productIds = $.unique(productIds);
				
				// create the tags by using a template
				$.each(productIds, function(index, productId) {
					var $existingProductId = ACC.search.$selectedProductIdsContainer.find("#product-id-" + productId + "-tag");
	
					if (productId !== '' && $existingProductId.length === 0) {
						// Render the product id tag boxes using the template
						ACC.search.$productIdTagBoxTemplate
							.tmpl({productId: productId, index:index})
							.appendTo(ACC.search.$selectedProductIdsContainer);
					}
	
				});
				
				//now write all ids back to one hidden field
				presentProductIds.val(productIds.join(","));
	
				// clear the input field
				ACC.search.$productIdsInput.val('');
				ACC.search.$productIdsInput.focus();
			}
		});
	},

	// Add product ids search enable checkbox handler
	bindToToggleProductIds: function(addProductIdsCheckBox) {
		addProductIdsCheckBox.on("change", function(event) {
			var checked = $(this).attr("checked") === "checked";

			ACC.search.replaceSearchLabel(checked);
			
			if (checked) {
				// show the add product ids button
				ACC.search.$addProductIdsButton.show();
			}
			else {
				// move the added product id tags back into the input
				var joinedProductIds = $.map(
							ACC.search.$selectedProductIdsContainer.find(".product-id-tag-box span"),
							function(index) { return $(index).text() })
						.join(", ");

				if (joinedProductIds !== "") {
					ACC.search.$productIdsInput.val(joinedProductIds);
				}
				// clear product id tags
				ACC.search.$selectedProductIdsContainer.empty();
				ACC.search.$skusHidden.val("");
				// hide the add button
				ACC.search.$addProductIdsButton.hide();
			}
		});

	},

	// Add remove product id event handler
	bindToRemoveProductId: function(removeProductIdButton) {
		removeProductIdButton.live("click", function(event) {
			event.preventDefault();
			var valueToRemove = $('#'.concat($(this).parent().attr("id").concat(' span'))).html();
			ACC.search.cleanupInvalidCommas(valueToRemove);
			$(this).parent().remove();
		});
	},
	
	replaceSearchLabel: function(productIdsChecked) {
		var currentLabel = ACC.search.$searchCurrentLabel.html();
		var searchByIdsLabel = $("#searchByIdsLabel").val();
		var searchByKeywordLabel = $("#searchByKeywordLabel").val();

		if (productIdsChecked) {
			currentLabel = currentLabel.replace(searchByKeywordLabel, searchByIdsLabel);
		} else {
			currentLabel = currentLabel.replace(searchByIdsLabel, searchByKeywordLabel);
		}
		ACC.search.$searchCurrentLabel.html(currentLabel);
	},
	
	stripoutInvalidChars: function() {
		ACC.search.$productIdsInput.val(ACC.search.$productIdsInput.val().replace(/[^a-z0-9 ,.\-_]/ig, ''));
	},
	
	cleanupInvalidCommas: function(valueToRemove) {
		ACC.search.$skusHidden.val(ACC.search.$skusHidden.val().replace(new RegExp("\\b"+valueToRemove+"\\b","gi"), ""));
		ACC.search.$skusHidden.val(ACC.search.$skusHidden.val().replace(new RegExp("\\,,\\b","gi"), ","));
		ACC.search.$skusHidden.val(ACC.search.$skusHidden.val().replace(/(,$)|(^,)/g, ''));
	}
};

$(document).ready(function() {
	// Bind all the event handlers
	ACC.search.bindAll();
	
	var productIdsChecked = (ACC.search.$enableProductIdsCheckBox.attr("checked") === "checked") ;
	if (productIdsChecked) {
		ACC.search.replaceSearchLabel(productIdsChecked);
		ACC.search.$addProductIdsButton.show();
		ACC.search.$addProductIdsButton.click();
	}
	
});
;

ACC.checkoutB2B = {
	cartData:"",
	PaymentType: function ()
	{


		if($(".summaryPaymentType").length>0){
			var $element = $(".summaryPaymentType")
			var config = $element.data()


			$(".summaryPaymentType input[name='PaymentType']").on("change",function(){
				$.postJSON(config.setPaymentTypeUrl, {paymentType: $(this).val()}, function(data){	
					ACC.checkoutB2B.refresh(data);	
					$(".summarySection").removeClass("complete")
					$(".summaryPaymentType").addClass("complete")
					$(".summaryCostCenter .constCenterSelect").val("")

				});
				var globalMessage = document.getElementById("globalMessages");
				while (globalMessage.firstChild) {
					globalMessage.removeChild(globalMessage.firstChild);
				}
			})



			$("#PurchaseOrderNumber").on("blur",function(){
				
					$.postJSON(config.setPurchaseOrderNumberUrl, {purchaseOrderNumber: $(this).val()}, function(data){
						ACC.checkoutB2B.refresh(data);		
					});
				
			})

		}
	
	},
		
	costCenter: function ()
	{
		if($(".summaryCostCenter").length>0){
			var config = $(".summaryCostCenter").data()
			$(".summaryCostCenter .constCenterSelect").on("change",function(){
				if($(this).val()!=""){
					$.postJSON(config.setCostCenterUrl, {costCenterId: $(this).val()}, function(data){
						ACC.checkoutB2B.refresh(data);
						$(".summaryCostCenter").addClass("complete")
					});
				}else{
					$(".summaryCostCenter").removeClass("complete")
				}
			})
		}

	},
	createUpdatePaymentForm: function ()
	{

		$(document).on("change", "#cardType", ACC.checkoutB2B.displayStartDateIssueNum);

		if($('#differentAddress:checked').length<1){
			$("#newBillingAddressFields :input").attr('disabled', 'disabled');
		}
		$('.create_update_payment_form').each(function ()
		{

			ACC.checkoutB2B.displayStartDateIssueNum()

			var options = {
				type: 'POST',
				beforeSubmit: function ()
				{
					
				},
				success: function (data)
				{
					$('#summaryPaymentOverlayEnterNew').html(data);
					ACC.checkoutB2B.createUpdatePaymentForm();
					if ($('#paymentDetailsForm').hasClass('Success'))
					{
						ACC.checkoutB2B.refresh();
						
						parent.$.colorbox.close();
					}
					else
					{
						
						$.colorbox.resize();
					}
				},
				error: function (xht, textStatus, ex)
				{
					alert("Failed to create/update payment details. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					
	
				}
			};

			$(this).ajaxForm(options);

		});
	},
	payment: function ()
	{
		
		$(document).ready(function(){
			$("#securityCode").val("");
		});
		
		$(document).on("focusout",'.summaryPayment #SecurityCodePayment', function (e){
			$("#securityCode").val($(this).val())
		});
		
		
		
		
		$(document).on("click",'.summaryPayment .security_code_what', function (e){
			$('.security_code_what').bt($(".summaryPayment").data('security-what-text'), {
				trigger: 'click',
				positions: 'bottom',
				fill: '#efefef',
				cssStyles: {
					fontSize: '11px'
				}
			});
		});
		

		$(document).on("click",'#summaryOverlayViewSavedPaymentMethods', function (){
			$("#summaryPaymentOverlay").show();
			$("#summaryPaymentOverlayEnterNew").remove();
			$.colorbox.resize();
		});
		
		
		$(document).on("click",'#summaryPaymentOverlay .useCard', function (e){
			e.preventDefault();
			var paymentId = $(this).data('payment');
			$.postJSON(setPaymentDetailsUrl, {paymentId: paymentId}, function(data){
				ACC.checkoutB2B.refresh(data);
				$.colorbox.close();
			});
		});
		
		
		
		
		
		$(document).on("click",'#summaryPaymentOverlay .enterNewPayment', function (){
			$("#summaryPaymentOverlay").hide();
			$("#summaryPaymentOverlay").after($("#summaryPaymentOverlayEnterNew").clone());
			ACC.checkoutB2B.createUpdatePaymentForm();	
			$.colorbox.resize();
		});
		
		
		$(document).on("click",'.summaryPayment .editButton', function (){

			$("#summaryPaymentOverlayContainer").html("");
			$("#summaryPaymentOverlayContainer").append($("#summaryPaymentOverlayEnterNew").clone()).append($("#summaryPaymentOverlay").clone());



					$.colorbox({
						inline: true,
						href: "#summaryPaymentOverlayContainer",
						height: false,
						overlayClose: false,
						onComplete: function ()
						{
							ACC.common.refreshScreenReaderBuffer();
								$.colorbox.resize();
								ACC.checkoutB2B.createUpdatePaymentForm();	

								$.ajax({
									url: $(".summaryPayment").data("getSavedCardsUrl"),
									type: 'POST',
									cache: false,
									dataType: 'json',
									success: function (data)
									{
										$('#summaryPaymentOverlay').html($('#savedCardsTemplate').tmpl({savedCards: data})).hide();
										
									},
									error: function (xht, textStatus, ex)
									{
										alert("Failed to get saved cards. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
									}
								});
			
						},
						onClosed: function ()
						{
							ACC.common.refreshScreenReaderBuffer();
						}
					});

		});
		
		
		
		$(document).on("change",'#differentAddress', function (){
			var newAddress = $('#differentAddress').attr("checked");
			if(newAddress)
			{
				$("#newBillingAddressFields :input").removeAttr('disabled');
			}
			else
			{
				$("#newBillingAddressFields :input").attr('disabled', 'disabled');
			}
		});
		
	},
	deliveryAddress: function (section)
	{
			
		$(document).on("click",'#summaryDeliveryAddressOverlay #summaryOverlayViewAddressForm', function (){
			$("#summaryDeliveryAddressForm").show();
			$("#summaryDeliveryAddressBook").hide();
			$.colorbox.resize();
		});
		
		$(document).on("click",'#summaryDeliveryAddressOverlay #summaryOverlayViewAddressBook', function (){
			$("#summaryDeliveryAddressBook").show();
			$("#summaryDeliveryAddressForm").hide();
			$.colorbox.resize();
		});		
		
		$(document).on("click",'.summaryDeliveryAddress .editButton', function (){



			if(ACC.checkoutB2B.cartData.paymentType.code == "ACCOUNT"){

				$.postJSON($(".summaryDeliveryAddress").data('get'), function(data){
					section= ".summaryDeliveryAddress";
					$("#summaryDeliveryAddressBook").html($(section+' .colorboxTemplate').tmpl({addresses: data,editable: false}));
					$("#summaryDeliveryAddressBook").show();
					$("#summaryDeliveryAddressForm").hide();


					$.colorbox({
						inline: true,
						href: "#summaryDeliveryAddressOverlay",
						height: false,
						overlayClose: false,
						onComplete: function ()
						{
							ACC.common.refreshScreenReaderBuffer();
								$.colorbox.resize();
						},
						onClosed: function ()
						{

							ACC.common.refreshScreenReaderBuffer();
						}
					});


				});

				


				return;
			}



			$("#summaryDeliveryAddressBook").hide();
			var changeFormUrl= $(".summaryDeliveryAddress").data('get-form')+"?createUpdateStatus=1&addressId="+$(this).data("address");
			$.get(changeFormUrl, function(data){
				$("#summaryDeliveryAddressForm").remove();
				$("#summaryDeliveryAddressOverlay").prepend(data);
				
			
				$.postJSON($(".summaryDeliveryAddress").data('get'), function(data){
					section= ".summaryDeliveryAddress";
					$("#summaryDeliveryAddressBook").html($(section+' .colorboxTemplate').tmpl({addresses: data,editable: true}));
				});
			
				
				ACC.checkoutB2B.createUpdateAddressForm();
				
				$.colorbox({
					inline: true,
					href: "#summaryDeliveryAddressOverlay",
					height: false,
					overlayClose: false,
					onComplete: function ()
					{
						ACC.common.refreshScreenReaderBuffer();
							$.colorbox.resize();
					},
					onClosed: function ()
					{

						ACC.common.refreshScreenReaderBuffer();
					}
				});
			});
		});
		
		
		$(document).on("click",'#summaryDeliveryAddressOverlay .useAddress', function (e){
			e.preventDefault();
			var addressId = $(this).data('address');
			$.postJSON($('.summaryDeliveryAddress').data("set"), {addressId: addressId}, function(data){
				ACC.checkoutB2B.refresh(data);
				$.colorbox.close();
			});
			
		});
		
		
		$(document).on("click",'#summaryDeliveryAddressOverlay #addressForm button', function (){
			var saveAddressChecked = $(this).prop('checked');
			$('#defaultAddress').prop('disabled', !saveAddressChecked);
			if (!saveAddressChecked) {
				$('#defaultAddress').prop('checked', false);
			}
		});




		$(document).on("click",'#summaryDeliveryAddressBook .addressEntry .edit', function (e){
			e.preventDefault();

			var changeFormUrl= $(".summaryDeliveryAddress").data('get-form')+"?createUpdateStatus=1&addressId="+$(this).data("address");
			$.get(changeFormUrl, function(data){

				$("#summaryDeliveryAddressForm").remove();
				$("#summaryDeliveryAddressOverlay").prepend(data);
				$("#summaryDeliveryAddressBook").hide();
				$("#summaryDeliveryAddressForm").show();
				$.colorbox.resize();

				ACC.checkoutB2B.createUpdateAddressForm();

			});
		});


		$(document).on("click",'#summaryDeliveryAddressOverlay button.default', function (e){
			e.preventDefault();
			var addressId = $(this).data('address');
			$.postJSON($('.summaryDeliveryAddress').data("setDefault"), {addressId: addressId}, function(data){
				section= ".summaryDeliveryAddress";
				$("#summaryDeliveryAddressBook").html($(section+' .colorboxTemplate').tmpl({addresses: data}));
			});
			
		});



		
	},
	createUpdateAddressForm: function ()
	{
		$('.create_update_address_form').each(function ()
		{
			var options = {
				type: 'POST',
				beforeSubmit: function ()
				{
					
				},
				success: function (data)
				{
					
					$('#summaryDeliveryAddressForm').html(data);
					ACC.checkoutB2B.createUpdateAddressForm();
					if ("Success" == $(".create_update_address_id").data('status'))
					{
						ACC.checkoutB2B.refresh();
						
						parent.$.colorbox.close();
					}
					else
					{
						
						$.colorbox.resize();
					}
				},
				error: function (xht, textStatus, ex)
				{
					alert("Failed to update cart. Error details [" + xht + ", " + textStatus + ", " + ex + "]");
				},
				complete: function ()
				{
					
				}
			};

			$(this).ajaxForm(options);
		});
	},
	deliveryMode: function ()
	{
		
		$(document).on("click",'#summaryDeliveryModeOverlay .saveButton', function (e){
			e.preventDefault();
			var setDeliveryModeUrl = $(".summaryDeliveryMode").data("set");
			var selectedCode = $('input:radio[name=delivery]:checked').val();
			if(selectedCode)
			{
				$.postJSON(setDeliveryModeUrl, {modeCode: selectedCode}, function(data){
					ACC.checkoutB2B.refresh(data);
				});
			}
			$.colorbox.close();
		});
	
	
		$(document).on("click",'.summaryDeliveryMode .editButton', function (){
			section= ".summaryDeliveryMode";
			$.postJSON($(section).data('get'), function(data){
				if (ACC.checkoutB2B.cartData.deliveryMode != null)
					$("#summaryDeliveryModeOverlay").html($(section+' .colorboxTemplate').tmpl({data: data, selectedCode: ACC.checkoutB2B.cartData.deliveryMode.code}));
				else
					$("#summaryDeliveryModeOverlay").html($(section+' .colorboxTemplate').tmpl({data: data, selectedCode: 'standard-net'}));
			});
			
	
			$.colorbox({
				inline: true,
				href: "#summaryDeliveryModeOverlay",
				height: false,
				overlayClose: false,
				onComplete: function ()
				{
					ACC.common.refreshScreenReaderBuffer();
						$.colorbox.resize();
				},
				onClosed: function ()
				{
					ACC.common.refreshScreenReaderBuffer();
				}
			});
	
		});
	},
	displaySection: function (section)
	{
		var cartData = ACC.checkoutB2B.cartData;
		
		if($(".summaryPaymentType input[name=PaymentType]:checked").length < 1){
			$("#PaymentTypeSelection_"+cartData.paymentType.code).attr("checked",true);
		}

		if(cartData.paymentType.code=="ACCOUNT"){
			$(".summaryCostCenter").show();
			$(".summaryPayment").hide();
			$("#deliverySectionNum").text("3");
		}else{
			$(".summaryCostCenter").hide();
			$(".summaryPayment").show();
			$("#deliverySectionNum").text("2");
			
		}
	},
	refreshSection: function (section)
	{
		var cartData = ACC.checkoutB2B.cartData;
		$(section+" .contentSection").html($(section+' .sectionTemplate').tmpl({data: cartData}));
		ACC.checkoutB2B.displaySection();
	},
	refresh: function(data){
		if(data==undefined)
		{
			$.postJSON($("#checkoutContentPanel").data("checkoutCartUrl"), function(data){
				ACC.checkoutB2B.refreshSections(data);
			});
		}
		else
		{
			ACC.checkoutB2B.refreshSections(data);
		}
	},
	refreshSections: function(data){
		ACC.checkoutB2B.cartData=data;
		ACC.checkoutB2B.refreshCart(data);
		// ACC.checkoutB2B.refreshSection(".summaryPaymentType");
		// ACC.checkoutB2B.refreshSection(".summaryCostCenter");
		ACC.checkoutB2B.refreshSection(".summaryPayment");
		ACC.checkoutB2B.refreshSection(".summaryDeliveryAddress");
		ACC.checkoutB2B.refreshSection(".summaryDeliveryMode");

		if (data.deliveryMode == null || data.deliveryAddress == null || (data.paymentType.code == "CARD" && data.paymentInfo == null)) {
			$(".placeOrderButton").attr("disabled", "disabled")
			$(".scheduleReplenishmentButton").attr("disabled", "disabled")
			$(".requestQuoteButton").attr("disabled", "disabled")
		} else {
			if ($('#replenishmentSchedule').is(':visible')) {
				$(".placeOrderButton").attr("disabled", "disabled")
				$(".requestQuoteButton").attr("disabled", "disabled")
				$(".scheduleReplenishmentButton").removeAttr("disabled")
				
			} else if ($('#negotiateQuote').is(':visible')) {
				$(".placeOrderButton").attr("disabled", "disabled")
				$(".scheduleReplenishmentButton").attr("disabled", "disabled")
				$(".requestQuoteButton").removeAttr("disabled")
				
			} else {
				$(".placeOrderButton").removeAttr("disabled")
				$(".scheduleReplenishmentButton").removeAttr("disabled")

				if (data.paymentType.code == "CARD" || !data.quoteAllowed) {
					$(".requestQuoteButton").attr("disabled", "disabled")
				} else {
					$(".requestQuoteButton").removeAttr("disabled")
				}
			}
		}

	},
	refreshCart: function(data){
		$("#ajaxCart").html($("#cartTotalsTemplate").tmpl({data: data}))
		$("#ajaxCartItems").html($("#cartItemsTemplate").tmpl({data: data}))		
	},
	
	
	scheduleReplenishment: function(data){
			 
			$(document).on("click",'#placeOrder .scheduleReplenishmentButton', function (e){
				e.preventDefault();

				if (ACC.checkoutB2B.cartData.quoteAllowed)
					$("#placeOrder .requestQuoteButton").attr('disabled', 'disabled');
				$(".placeOrderButton").attr('disabled', 'disabled');
				$("#placeOrder .scheduleReplenishmentButton").addClass('pressed');
				$('#replenishmentSchedule').show();
				ACC.checkoutB2B.replenishmentInit();
			});

			$(document).on("click",'#replenishmentSchedule #placeReplenishmentOrder', function (e){
				e.preventDefault();
				$(".replenishmentOrderClass").val(true);
				var securityCode = $("#SecurityCodePayment").val();
				$(".securityCodeClass").val(securityCode);
				$("#placeOrderForm1").submit();
			});

			$(document).on("click",'#replenishmentSchedule #cancelReplenishmentOrder', function (e){
				e.preventDefault();
				if (ACC.checkoutB2B.cartData.quoteAllowed && ACC.checkoutB2B.cartData.paymentType.code != "CARD")
					$("#placeOrder .requestQuoteButton").removeAttr('disabled');
				$(".placeOrderButton").removeAttr('disabled');
				$("#placeOrder .scheduleReplenishmentButton").removeClass('pressed');
				$('#replenishmentSchedule').hide();
				$(".replenishmentOrderClass").val(false);
			});

			$(document).on("click",'#replenishmentSchedule .replenishmentfrequencyD, #replenishmentSchedule .replenishmentfrequencyW, #replenishmentSchedule .replenishmentfrequencyM', function (e){
				$('.scheduleform').removeClass('selected');
				$(this).parents('.scheduleform').addClass('selected');
			});	

			var placeOrderFormReplenishmentOrder = $('#replenishmentSchedule').data("placeOrderFormReplenishmentOrder")

			if(placeOrderFormReplenishmentOrder){
				$('#placeOrder .scheduleReplenishmentButton').click()
			}
		
	},

	replenishmentInit: function ()
	{
		var dateForDatePicker = $('#replenishmentSchedule').data("dateForDatePicker");
		var placeOrderFormReplenishmentRecurrence = $('#replenishmentSchedule').data("placeOrderFormReplenishmentRecurrence");
		var placeOrderFormNDays = $('#replenishmentSchedule').data("placeOrderFormNDays");
		var placeOrderFormNthDayOfMonth = $('#replenishmentSchedule').data("placeOrderFormNthDayOfMonth");
		var placeOrderFormNegotiateQuote = $('#replenishmentSchedule').data("placeOrderFormNegotiateQuote");
		var placeOrderFormReplenishmentOrder = $('#replenishmentSchedule').data("placeOrderFormReplenishmentOrder");
		
		$("input:radio[name='replenishmentRecurrence'][value=" + placeOrderFormReplenishmentRecurrence + "]").attr('checked', true);
		$("input:radio[name='replenishmentRecurrence'][value=" + placeOrderFormReplenishmentRecurrence + "]").parents('.scheduleform').addClass('selected');;
		$("#nDays option[value=" + placeOrderFormNDays + "]").attr('selected', 'selected');
		$("#daysoFWeek option[value=" + placeOrderFormNthDayOfMonth + "]").attr('selected', 'selected');
		$("#replenishmentStartDate").datepicker({dateFormat: dateForDatePicker});
		$("#replenishmentStartDate").datepicker("option", "appendText", dateForDatePicker);	
	},
	
	
	negotiateQuote: function(data){
		
			$(document).on("click",'#placeOrder .requestQuoteButton', function (e){
				e.preventDefault();
				$("#placeOrder .scheduleReplenishmentButton").attr('disabled', 'disabled');
				$(".placeOrderButton").attr('disabled', 'disabled');
				$("#placeOrder .requestQuoteButton").addClass('pressed');
				$('#negotiateQuote').show();
				
			});
			
			$(document).on("click",'#negotiateQuote #placeNegotiateQuote', function (e){
				e.preventDefault();
				$(".negotiateQuoteClass").val(true);
				var securityCode = $("#SecurityCodePayment").val();
				$(".securityCodeClass").val(securityCode);
				$("#placeOrderForm1").submit();
			});

			$(document).on("click",'#negotiateQuote #cancelNegotiateQuote', function (e){
				e.preventDefault();
				$("#placeOrder .scheduleReplenishmentButton").removeAttr('disabled');
				$(".placeOrderButton").removeAttr('disabled');
				$("#placeOrder .requestQuoteButton").removeClass('pressed');
				$('#negotiateQuote').hide();
				$(".negotiateQuoteClass").val(false);
			});


			var placeOrderFormNegotiateQuote = $('#replenishmentSchedule').data("placeOrderFormNegotiateQuote");

			if(placeOrderFormNegotiateQuote){
				$('#placeOrder .requestQuoteButton').click()
			}
	},
	
	
	placeOrder: function(data){
		
			$(document).on("click",'.placeOrderButton', function (e){
				e.preventDefault();
				var securityCode = $("#SecurityCodePayment").val();
				$(".securityCodeClass").val(securityCode);
				$("#placeOrderForm1").submit();
			});
			
			
			$(document).on("click",'#Terms1,#Terms2', function (e){
				if($(this).attr('checked')){
					$('#Terms1,#Terms2').attr('checked','checked')
				}else{
					$('#Terms1,#Terms2').removeAttr('checked')
				}
			});

			
	},

	displayStartDateIssueNum: function()
	{
		
		var cardType = $("#cardType").val();
    	            	
    	if (cardType == 'maestro' || cardType == 'switch')
        {
            $('#startDate, #issueNum').show("fast",'linear', function() {
				parent.$.colorbox.resize()
			});
        }
        else
        {
            $('#startDate, #issueNum').hide('fast','linear', function() {
				parent.$.colorbox.resize()
			});
            
	    }
		
						
    },

    bindTermsAndConditionsLink: function() {
    	$(document).on("click",'.termsAndConditionsLink', function(e) {
			e.preventDefault();
			$.colorbox({
				href: getTermsAndConditionsUrl,
				onComplete: function() {
					ACC.common.refreshScreenReaderBuffer();
				},
				onClosed: function() {
					ACC.common.refreshScreenReaderBuffer();
				}
			});
		});
	}
	
	
	
	
	
	
	
	
	
	

};

$(document).ready(function ()
{
	
	if($("body").hasClass("page-cartPage"))
	{
		$('.checkoutButton').click(function (){
			var checkoutUrl = $(this).data("checkoutUrl");
			window.location = checkoutUrl;
		});
	}
	
	
	if($("body").hasClass("page-singleStepCheckoutSummaryPage"))
	{
		with(ACC.checkoutB2B){
			refresh();
			PaymentType();
			costCenter();
			payment();
			deliveryAddress();
			deliveryMode();
			scheduleReplenishment();
			negotiateQuote();
			placeOrder();
			bindTermsAndConditionsLink();
		}
	}
});

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

function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};
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


function GetXmlHttpObject(){var a=null;try{a=new XMLHttpRequest()}catch(b){try{a=new ActiveXObject("Msxml2.XMLHTTP")}catch(b){try{a=new ActiveXObject("Microsoft.XMLHTTP")}catch(b){alert("Your browser broke!");return false}}}return a};
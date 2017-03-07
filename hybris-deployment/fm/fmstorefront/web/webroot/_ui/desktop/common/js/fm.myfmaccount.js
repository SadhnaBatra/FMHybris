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



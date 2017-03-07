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
};
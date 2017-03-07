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

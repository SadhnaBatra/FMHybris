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

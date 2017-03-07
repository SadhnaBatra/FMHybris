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

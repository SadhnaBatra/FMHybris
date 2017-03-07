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

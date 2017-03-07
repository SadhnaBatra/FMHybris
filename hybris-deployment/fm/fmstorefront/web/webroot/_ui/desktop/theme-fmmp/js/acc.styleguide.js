var ACC = ACC || {}; // make sure ACC is available

ACC.styleguide = {
	bindAll: function() {
		// Accessible Tabs
		$("#productTabs", "#style-guide-plugins").accessibleTabs({
			tabhead:'h2',
			fx:"fadeIn"
		});

		// BeautyTips
		$("#BeautyTips", "#style-guide-plugins").bt();
		$("#BeautyTipsCustom").bt({
			shrinkToFit: true,
			trigger: 'click',
			positions: ['bottom']
		});

		// Bootstrap
		// Defined in the HTML Source
		
		// Bootstrap Datepicker
		$(".date-picker", "#style-guide-plugins").datepicker();

		// Bootstrap Maxlength
		$('input[maxlength]', "#style-guide-plugins").maxlength();
		
		// Bootstrap Slider
		$("#ex2", "#style-guide-plugins").bootstrapSlider();

		// Bootstrap Toggle
		// Defined in the HTML Source data attributes
		
		// BXSlider
		$("#BXSlider", "#style-guide-plugins").bxSlider({
			slideWidth: 500,
			minSlides: 1,
			maxSlides: 2,
			moveSlides: 1,
			slideMargin: 30,
			nextText: '<span class="fa fa-chevron-right"></span>',
			prevText: '<span class="fa fa-chevron-left"></span>'
		});

		// Colorbox
		$(".inline", "#style-guide-plugins").colorbox({inline:true, width:"50%"});

		// Easy Responsive Tab
		$("#easyResponsiveTabs", "#style-guide-plugins").easyResponsiveTabs();
		
		// Elevate Zoom
		$("#zoom_01", "#style-guide-plugins").elevateZoom();

		// jQuery Slim Scroll
		$("#slimScroll", "#style-guide-plugins").slimScroll({
			allowPageScroll: true,
			height: '200px'
		});

		// jQuery Treeview
		$("#jQueryTreeview", "#style-guide-plugins").treeview();
	
		// jQuery UI Stars
		$("#stars-wrapper", "#style-guide-plugins").stars();

		// jQuery Validate
		$("#validationForm", "#style-guide-plugins").validate({
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

		// jRange
		$(".single-slider", "#style-guide-plugins").jRange({
			from: 0,
			to: 10,
			step: 1,
			scale: [0, 5 ,10],
			format: '%s',
			width: 300,
			showLabels: true
		});

		// TableSorter
		$("#myTable").tablesorter(); 
	}
};

$(document).ready(function() {
	// Bind all the event handlers
	ACC.styleguide.bindAll();
});

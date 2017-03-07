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


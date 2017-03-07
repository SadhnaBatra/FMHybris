ACC.storefinder = {
	map:"",
	
	bindAll: function ()
	{
		
		this.drawMap();
		this.getStoreMarkersImages();
		this.bindFindStoresNearMe();
	},
	
	drawMap: function(){
		
		if($('#map_canvas').length!=0)
		{
			var count = 0;
			var $e=$('#map_canvas')
			
			var centerPoint = new google.maps.LatLng($e.data("latitude"), $e.data("longitude"));
			var mapOptions = {
				zoom: 13,
				zoomControl: true,
				panControl: true,
				streetViewControl: false,
				mapTypeId: google.maps.MapTypeId.ROADMAP,
				center: centerPoint

			}
			
			ACC.storefinder.map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
			
			if($e.data("southLatitude"))
			{


				this.setMapBouns();
				
				this.getStorePosition();
			}
			else{
				
				store =$e.data('stores');
				
				this.addStore(store.id,store.latitude,store.longitude, store.name, count+=1)
			}
		}
	},
	
	setMapBouns: function(){
		
		var $e=$('#map_canvas');
		var swBounds=new google.maps.LatLng($e.data("southLatitude"), $e.data("westLongitude"));
		var neBounds=new google.maps.LatLng($e.data("northLatitude"), $e.data("eastLongitude"));
		var bounds = new google.maps.LatLngBounds(swBounds, neBounds);
				ACC.storefinder.map.fitBounds(bounds);
		

				
	},
	
	
	
	getStorePosition: function(){
	
		var $e=$('#map_canvas');
		var count = 0;

			stores = $e.data('stores');	
		
			jQuery.each( stores, function( k, v ) {
				//alert(v.id)
			ACC.storefinder.addStore( v.id,v.latitude,v.longitude,v.name, count+=1 );
		});

	},
	
	addStore: function(i,latitude,longitude, name, count)
	
	{

			
		
		var marker = new google.maps.Marker({
			position: new google.maps.LatLng(latitude, longitude),
			map: ACC.storefinder.map,
			title: name,
                        icon:'https://chart.googleapis.com/chart?chst=d_map_pin_letter&chld='+ (count) +'|FF776B|000000'
			//icon: "http://maps.google.com/mapfiles/marker" + numTitle + ".png"
					});

		

		var infowindow = new google.maps.InfoWindow({
			content: name,
			disableAutoPan: true
		});
		google.maps.event.addListener(marker, 'click', function ()
		{
			infowindow.open(ACC.storefinder.map, marker);
		});
		
	

	},

	getStoreMarkersImages: function(){
	var count = 0;
	
		if($('.storeResultList').length!=0)
		{
			$(".storeMarker").each(function(i){
				

				$(this).attr("src",'//chart.googleapis.com/chart?chst=d_map_pin_letter&chld='+ (count) +'|FF776B|000000')
				count +=1;
			})
		}

		
	},
	
	
	bindFindStoresNearMe: function(){
		$(document).on("click", "#findStoresNearMe", function(e){
			e.preventDefault();
			var gps = navigator.geolocation;
			
			if (gps)
			{
				gps.getCurrentPosition(ACC.storefinder.positionSuccessStoresNearMe, function (error)
				{
					console.log("An error occurred... The error code and message are: " + error.code + "/" + error.message);
				});
			}
		});
	},

	positionSuccessStoresNearMe: function (position)
	{
		$("#latitude").val(position.coords.latitude);
		$("#longitude").val(position.coords.longitude);
		$("#nearMeStorefinderForm").submit();
		return false;
	}
};

 
$(document).ready(function ()
{
	ACC.storefinder.bindAll();

});

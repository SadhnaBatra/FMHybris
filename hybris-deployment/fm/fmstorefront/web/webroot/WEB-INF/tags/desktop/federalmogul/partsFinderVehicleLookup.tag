
<div id="fade"></div>
<div id="modal">
	<img id="loader"
		src="/fmstorefront/_ui/desktop/common/images/spinner.gif" />
</div>

<!-- InstanceEndEditable -->
<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12 ">
	<div class="manageUserPanel rightHandPanel viewUnit clearfix">
		<h2 class="text-uppercase">Vehicle Lookup</h2>
		<div class="row">
			<div class="col-md-12">
				<!-- <p>Lorem Ipsum is simply dummy text of the printing</p> -->
			</div>

		</div>
		<form class="" action="#" method="get" id="normalYMMForm">
			<!--   <div class="clearfix">    -->

			<input type="hidden" id="ymmquery" name="q" value="" /> <input
				type="hidden" name="text" value="#" />
			<div class="form-group col-lg-3 col-md-3 col-sm-3 lftPad_0">
				<select class="form-control" id="vehiclesegment">
					<option value="vehiclesegment">Vehicle Type</option>
					<option value="Passenger Car Light Truck">Passenger Car Light Truck</option>
				</select>
			</div>
			<div class="form-group col-lg-2 col-md-2 col-sm-2 lftPad_0 width95">
				<select class="form-control" id="year">
					<option value="year">Year</option>
				</select>
			</div>
			<div class="form-group col-lg-2 col-md-2 col-sm-2 lftPad_0">
				<select class="form-control" id="make">
					<option>Make</option>
				</select>
			</div>
			<div class="form-group col-lg-3 col-md-3 col-sm-3 lftPad_0">
				<select class="form-control" id="modelhome">
					<option>Model</option>
				</select>
			</div>
			<div class="form-group col-lg-2 col-md-2 col-sm-2 ">
				<a class="btn  btn-sm btn-fmDefault" id="ymmSearch"
					href="javascript:SearchQuery()">Look It Up</a>
			</div>

		</form>
	</div>
	<!-- Ends: Manage Account Right hand side panel -->
</div>

<script>
	function SearchQuery() {
		
		var sel_vehicleSegment = $('#vehiclesegment option:selected').text();
		var sel_year = $('#year option:selected').text();
		var sel_make = $('#make option:selected').text();
		var sel_model = $('#modelhome option:selected').text();
		
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
		if(sel_model == 'modelhome' ||  sel_model == ''){
			$('#ymmSearch').addClass('disabled');
			alert ("Please select Model");
			return;
		}
		var ymmCode=sel_year.trim()+sel_make.trim()+sel_model.trim()+"|";
		
		var queryVal= pathName + "search?q=:name-asc:vehiclesegment:"+ymmCode+sel_vehicleSegment.trim()+":year:"+ymmCode+sel_year.trim()+":make:"+ymmCode+sel_make.trim()+":model:"+ymmCode+sel_model.trim()+ "&text=#";
		
		
		//var queryVal = pathName + "search?q=:name-desc:vehiclesegment:"
				//+ sel_vehicleSegment.trim() + ":year:" + sel_year.trim()
				//+ ":make:" + sel_make.trim() + ":model:" + sel_model.trim()
				//+ "&text=#";
		$("#ymmSearch").prop("href", queryVal);
		location.href = queryVal;
	}
</script>
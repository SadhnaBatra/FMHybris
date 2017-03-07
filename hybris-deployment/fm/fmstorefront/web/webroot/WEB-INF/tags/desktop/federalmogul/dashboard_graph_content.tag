<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="federalmogul" tagdir="/WEB-INF/tags/desktop/federalmogul"%>
<script type="text/javascript" src="/fmstorefront/_ui/desktop/common/js/amcharts.js"></script>
<script type="text/javascript" src="/fmstorefront/_ui/desktop/common/js/serial.js"></script>
<script type="text/javascript" src="/fmstorefront/_ui/desktop/common/js/jquery-1.2.3.min.js"></script>

<div class="container companyOverview">
	<div class="row">
		<div class="col-lg-9 col-md-9 col-sm-9 col-xs-12">
			<div class="manageUserPanel rightHandPanel clearfix">
				<div class="clearfix">
					<h2 class="col-lg-8 col-md-8 col-sm-8 col-xs-12 text-uppercase">
					Motorparts Monitor</h2>
					<div
						class="col-lg-4 col-md-4 col-sm-4 col-xs-12 topMargn_15  onlineToolPrintMonitor">
						<a onclick="printDiv('mygraph')" class="text-capitalize DINWebBold fm-blue-text " href="#">
						<span class="fa fa-print"></span> print your monitor report</a>
						
					</div>
				</div>
				<div class="clearfix topMargn_15">
					<h3	class="col-lg-9 col-md-9 col-xs-12 fm_fntRed text-uppercase onlineToolGaskets">
						${prodDesc}</h3>
					<div class="form-group col-lg-3 col-md-3 col-xs-12 dummy topMargn_12">
						<div id="legenddiv" class="legenddiv2 "></div>
						<div id="legenddiv2" class="legenddiv2 "></div>
						<select id="changegraph" class="form-control shipToSelect unit  width105 ">
							<c:choose>
								<c:when test="${flag eq 'units'}">
									<option value="units">Units</option>
								</c:when>

								<c:when test="${flag eq 'dollars'}">
									<option value="dollars">Dollars</option>
								</c:when>

								<c:otherwise>
									<option value="units">Units</option>
									<option value="dollars">Dollars</option>
								</c:otherwise>
							</c:choose>
						</select>
						<!-- <a class="fm_fntRed" href="#"><span class=
          "fa fa-bar-chart-o"></span></a> <a class="fm_fntGrey" href=
          "#"><span class="fa fa-line-chart"></span></a> -->
					</div>
				</div>

				<div class="row">
					<div class="col-lg-12 col-md-12 col-xs-12 motorPartMonitorGreyPanel">
						Point-of-Sale Data. Year-Over-Year Performance.</div>
					<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12 motorPartMonitorGreyPanelTwo col-lg-offset-4"
						id="sales_yoy" style="display: none">
						<h6 class="text-center">Last 3/6/12 Months vs. Prior year</h6>
						<p class="text-center motorPartMonitorGreyPanelPara">
							<span class="rghtMrgn_32">${yoyValues.salechange_3months}%</span><span
								class="rghtMrgn_32">${yoyValues.salechange_6months}%</span>${yoyValues.salechange_12months}%</p>
					</div>

					<div class="col-lg-5 motorPartMonitorGreyPanelTwo col-lg-offset-4"
						id="unit_yoy">
						<h6 class="text-center">Last 3/6/12 Months vs. Prior year</h6>
						<p class="text-center motorPartMonitorGreyPanelPara">
							<span class="rghtMrgn_32">${yoyValues.unitchange_3months}%</span><span
								class="rghtMrgn_32">${yoyValues.unitchange_6months}%</span>${yoyValues.unitchange_12months}%</p>
					</div>

					<div id="mygraph" class="mygraph col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div id="chartdiv" class="display" style="width: 100%; height: 400px; background-color: #FFFFFF;"></div>
						<div id="chart2div" class="display"	style="width: 100%; height: 400px; background-color: #FFFFFF;"></div>
						<div id="chart3div"	style="width: 100%; height: 400px; background-color: #FFFFFF;"></div>
					</div>
					<div id="descunits"	class="topMargn_25 col-lg-12 col-md-12 col-xs-12">
						<federalmogul:cpl1unitsdescription />
					</div>
					<div id="descdollars" class="topMargn_25 col-lg-12 col-md-12 col-xs-12" style="display: none">
						<federalmogul:cpl1dollarsdescription />
					</div>
					
					<div class="pull-right rgtPad_25 topMargn_15"><p>For any queries,contact     <a class=" DINWebBold fm-blue-text" href="mailto:MotorpartsMonitor@FederalMogul.com">MotorpartsMonitor@FederalMogul.com</a></p>
					</div>
				<div>
			</div>
		</div>
	</div>
</div>

<script>
	var graphCount = '${graphCount}';

	$(document).ready(function() {
		$('.display').hide();
		//$('#sales_yoy').hide();
		//$('#unit_yoy').show();

		if (graphCount == "isTwo") {
			$('#chart2div').show();
			$('#chart3div').hide();

		} else {
			$('#chart2div').hide();
		}
		$('#changegraph').change(function() {
			if ($(this).val() == 'units') {
				$('#chart2div').show();
				$('#chartdiv').hide();
				$('#sales_yoy').hide();
				$('#unit_yoy').show();
				$('#descunits').show();
				$('#descdollars').hide();
			} else {
				$('#chartdiv').show();
				$('#chart2div').hide();
				$('#unit_yoy').hide();
				$('#sales_yoy').show();
				$('#descunits').hide();
				$('#descdollars').show();
			}
		});
	});

	//GraphData		 
	var x = '${xaxis}';
	var xstr = x.replace("[", "");
	var newxstr = xstr.replace("]", "");
	var xaxis = new Array();
	xaxis = newxstr.split(",");

	//sales-dollars
	var y1 = '${yaxis1}';
	var ystr1 = y1.replace("[", "");
	var newystr1 = ystr1.replace("]", "");
	var yaxis1 = new Array();
	yaxis1 = newystr1.split(",");

	//units
	var y2 = '${yaxis2}';
	var ystr2 = y2.replace("[", "");
	var newystr2 = ystr2.replace("]", "");
	var yaxis2 = new Array();
	yaxis2 = newystr2.split(",");

	var chartData1 = [];
	var len = xaxis.length;
	for (var i = 0; i < len; i++) {
		chartData1.push({
			"Monthyear" : xaxis[i].trim(),
			"Dollars" : yaxis1[i]
		});
	}

	var chartData2 = [];
	var len = xaxis.length;
	for (var i = 0; i < len; i++) {
		chartData2.push({
			"Monthyear" : xaxis[i].trim(),
			"Units" : yaxis2[i]
		});
	}

	//added new
	var chartData = [];
	var len = xaxis.length;
	var flag = '${flag}';

	if (flag == "dollars") {
		//alert("dollars data provider");
		for (var i = 0; i < len; i++) {
			chartData.push({
				"Monthyear" : xaxis[i].trim(),
				"Dollars" : yaxis1[i]

			});
		}
	}
	if (flag == "units") {
		//alert("units data provider");
		for (var i = 0; i < len; i++) {
			chartData.push({
				"Monthyear" : xaxis[i].trim(),
				"Units" : yaxis2[i]
			});
		}
	}

	//Legend select
	function handleLegendClick(graph) {
		var chart = graph.chart;
		for (var i = 0; i < chart.graphs.length; i++) {
			if (graph.id == chart.graphs[i].id)
				chart.showGraph(chart.graphs[i]);
			else
				chart.hideGraph(chart.graphs[i]);
		}
		return false;
	}

	//draw chart
	if (graphCount == "isTwo") {
		//div -1
		var chart = new AmCharts.AmSerialChart();
		chart.dataProvider = chartData1;
		chart.categoryField = "Monthyear";
		chart.pathToImages = "http://cdn.amcharts.com/lib/3/images/";

		var legend = new AmCharts.AmLegend();
		legend.position = "top";
		legend.markerSize = 14;
		legend.useGraphSettings = true;
		legend.horizontalGap = 10;
		legend.align = "right";
		legend.spacing = -50;
		legend.marginRight = -40;
		legend.clickMarker = handleLegendClick;
		legend.clickLabel = handleLegendClick;
		chart.addLegend(legend, "legenddiv2");

		//Bar graph 
		var graph = new AmCharts.AmGraph();
		graph.valueField = "Dollars";
		graph.type = "column";
		graph.fillAlphas = 1;
		//graph.title = "Bar Graph";
		graph.lineAlpha = 0;
		graph.labelText = "[[value]]";
		graph.fillColors = "#535353";
		graph.negativeFillColors = "#AD102A";

		//Line graph 
		var graph1 = new AmCharts.AmGraph();
		graph1.valueField = "Dollars";
		graph1.type = "line";
		//graph1.balloonText= "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b></span>";
		graph1.bullet = "round";
		graph1.bulletBorderAlpha = 0;
		graph1.useLineColorForBulletBorder = true;
		graph1.fillAlphas = 0;
		graph1.lineThickness = 2;
		graph1.lineAlpha = 1;
		graph1.bulletSize = 7;
		graph1.labelText = "[[value]]";
		//graph1.title = "Line Graph";
		graph1.lineColor = "#535353";
		graph1.negativeLineColor = "#AD102A";
		graph1.hidden = true;
		chart.addGraph(graph);
		chart.addGraph(graph1);

		var categoryAxis = chart.categoryAxis;
		categoryAxis.title = "Year-Month";
		categoryAxis.labelOffset = 15;
		categoryAxis.labelRotation = 90;

		var valueaxis = new AmCharts.ValueAxis();
		valueaxis.title = "Dollars";
		valueaxis.unit = "%";
		valueaxis.showFirstLabel = false;
		chart.addValueAxis(valueaxis);

		var chartScrollbar = new AmCharts.ChartScrollbar();
		//chartScrollbar.dragIcon = "";
		chartScrollbar.dragIconHeight = 0;
		chartScrollbar.scrollbarHeight = 20;
		chartScrollbar.resizeEnabled = false;
		chartScrollbar.selectedBackgroundColor = "#0000";
		chartScrollbar.oppositeAxis = false;
		chart.addChartScrollbar(chartScrollbar);

		var chartCursor = new AmCharts.ChartCursor();
		chart.addChartCursor(chartCursor);
		chartCursor.enabled = false;

		chart.addListener("init", zoomChart);
		chart.write('chartdiv');

		//div - 2
		var chart2 = new AmCharts.AmSerialChart();
		chart2.dataProvider = chartData2;
		chart2.categoryField = "Monthyear";
		chart2.pathToImages = "http://cdn.amcharts.com/lib/3/images/";

		var legend1 = new AmCharts.AmLegend();
		legend1.position = "top";
		legend1.markerSize = 14;
		legend1.useGraphSettings = true;
		legend1.horizontalGap = 10;
		legend1.align = "right";
		legend1.spacing = -50;
		legend1.marginRight = -40;
		legend1.clickMarker = handleLegendClick;
		legend1.clickLabel = handleLegendClick;
		chart2.addLegend(legend1, "legenddiv2");

		var graph2 = new AmCharts.AmGraph();
		graph2.valueField = "Units";
		graph2.type = "column";
		graph2.fillAlphas = 1;
		//graph2.title = "Bar Graph";
		graph2.lineAlpha = 0;
		graph2.fillColors = "#535353";
		graph2.labelText = "[[value]]";
		graph2.negativeFillColors = "#AD102A";

		var graph3 = new AmCharts.AmGraph();
		graph3.valueField = "Units";
		graph3.type = "line";
		graph3.balloonText = "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b></span>";
		graph3.bullet = "round";
		graph3.bulletBorderAlpha = 0;
		graph3.useLineColorForBulletBorder = true;
		graph3.fillAlphas = 0;
		graph3.lineThickness = 2;
		graph3.lineAlpha = 1;
		graph3.bulletSize = 7;
		graph3.labelText = "[[value]]";
		//graph3.title = "Line Graph";
		graph3.lineColor = "#535353";
		graph3.negativeLineColor = "#AD102A";
		graph3.hidden = true;
		chart2.addGraph(graph2);
		chart2.addGraph(graph3);

		var categoryAxis1 = chart2.categoryAxis;
		categoryAxis1.title = "Year-Month";
		categoryAxis1.labelOffset = 15;
		categoryAxis1.labelRotation = 90;

		var valueaxis1 = new AmCharts.ValueAxis();
		valueaxis1.title = "Units";
		valueaxis1.unit = "%";
		valueaxis1.showFirstLabel = false;
		chart2.addValueAxis(valueaxis1);

		var chartScrollbar1 = new AmCharts.ChartScrollbar();
		//chartScrollbar1.dragIcon = "";
		chartScrollbar1.dragIconHeight = 0;
		chartScrollbar1.scrollbarHeight = 20;
		chartScrollbar1.resizeEnabled = false;
		chartScrollbar1.selectedBackgroundColor = "#0000";
		chartScrollbar1.oppositeAxis = false;
		chart2.addChartScrollbar(chartScrollbar1);

		var chartCursor1 = new AmCharts.ChartCursor();
		chart2.addChartCursor(chartCursor1);
		chartCursor1.enabled = false;

		chart2.addListener("init", zoomChart1);

		chart2.write("chart2div");
	}

	else if (graphCount == "isOne") {

		//dollar graph
		if (flag == "dollars") {

			var chart = new AmCharts.AmSerialChart();
			chart.dataProvider = chartData;
			chart.categoryField = "Monthyear";
			chart.pathToImages = "http://cdn.amcharts.com/lib/3/images/";
			//chart.addTitle("Point of Sale Data. Year-Over-Year Performance.",16,"red",true);

			var legend = new AmCharts.AmLegend();
			legend.position = "top";
			legend.markerSize = 14;
			legend.useGraphSettings = true;
			legend.horizontalGap = 10;
			legend.align = "right";
			legend.spacing = -50;
			legend.marginRight = -40;
			legend.reversedOrder = true;
			legend.clickMarker = handleLegendClick;
			legend.clickLabel = handleLegendClick;
			chart.addLegend(legend, "legenddiv");

			var graph = new AmCharts.AmGraph();
			graph.valueField = "Dollars";
			graph.type = "column";
			graph.labelText = "[[value]]";

			//graph.title = "Bar Graph";
			graph.lineAlpha = 0;
			graph.fillColors = "#535353";
			graph.negativeFillColors = "#AD102A";
			graph.fillAlphas = 1;

			var graph1 = new AmCharts.AmGraph();
			graph1.valueField = "Dollars";
			graph1.type = "line";
			graph1.labelText = "[[value]]";
			//graph1.balloonText= "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b></span>";
			graph1.bullet = "round";
			graph1.bulletBorderAlpha = 0;
			graph1.useLineColorForBulletBorder = true;
			graph1.fillAlphas = 0;
			graph1.lineThickness = 2;
			graph1.lineAlpha = 1;
			graph1.bulletSize = 7;
			//graph1.title = "Line Graph";
			graph1.lineColor = "#535353";
			graph1.negativeLineColor = "#AD102A";
			graph1.hidden = true;
			chart.addGraph(graph1);
			chart.addGraph(graph);

			var categoryAxis2 = chart.categoryAxis;
			categoryAxis2.title = "Year-Month";
			categoryAxis2.labelOffset = 19;
			categoryAxis2.labelRotation = 90;
			categoryAxis2.inside = false;
			categoryAxis2.gridAlpha = 0.20;
			categoryAxis2.autoWrap = true;
			categoryAxis2.tickLength = 0;

			var valueaxis2 = new AmCharts.ValueAxis();
			valueaxis2.title = "Dollars";
			valueaxis2.unit = "%";
			valueaxis2.showFirstLabel = false;
			chart.addValueAxis(valueaxis2);

			var chartScrollbar = new AmCharts.ChartScrollbar();
			//chartScrollbar.dragIcon = "";
			chartScrollbar.dragIconHeight = 0;
			chartScrollbar.scrollbarHeight = 20;
			chartScrollbar.resizeEnabled = false;
			chartScrollbar.selectedBackgroundColor = "#0000";
			chartScrollbar.oppositeAxis = false;
			chart.addChartScrollbar(chartScrollbar);

			var chartCursor = new AmCharts.ChartCursor();
			chart.addChartCursor(chartCursor);
			chartCursor.enabled = false;

			chart.addListener("init", zoomChart2);

			chart.write('chart3div');
		}
		//units graph
		else if (flag == "units") {

			var chart = new AmCharts.AmSerialChart();
			chart.dataProvider = chartData;
			chart.categoryField = "Monthyear";
			chart.pathToImages = "http://cdn.amcharts.com/lib/3/images/";
			//chart.addTitle("Point of Sale Data. Year-Over-Year Performance.",16,"red",true);

			var legend = new AmCharts.AmLegend();
			legend.position = "top";
			legend.markerSize = 14;
			legend.useGraphSettings = true;
			legend.horizontalGap = 10;
			legend.align = "right";
			legend.spacing = -50;
			legend.marginRight = -40;
			legend.reversedOrder = true;
			legend.clickMarker = handleLegendClick;
			legend.clickLabel = handleLegendClick;
			chart.addLegend(legend, "legenddiv");

			var graph = new AmCharts.AmGraph();
			graph.valueField = "Units";
			graph.type = "column";
			//graph.title = "Bar Graph";
			graph.lineAlpha = 0;
			graph.labelText = "[[value]]";
			graph.fillColors = "#535353";
			graph.negativeFillColors = "#AD102A";
			graph.fillAlphas = 1;

			var graph1 = new AmCharts.AmGraph();
			graph1.valueField = "Units";
			graph1.type = "line";
			graph1.balloonText = "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b></span>";
			graph1.bullet = "round";
			graph1.labelText = "[[value]]";
			graph1.bulletBorderAlpha = 0;
			graph1.useLineColorForBulletBorder = true;
			graph1.fillAlphas = 0;
			graph1.lineThickness = 2;
			graph1.lineAlpha = 1;
			graph1.bulletSize = 7;
			//graph1.title = "Line Graph";
			graph1.lineColor = "#535353";
			graph1.negativeLineColor = "#AD102A";
			graph1.hidden = true;
			chart.addGraph(graph1);
			chart.addGraph(graph);

			var categoryAxis2 = chart.categoryAxis;
			categoryAxis2.title = "Year-Month";
			categoryAxis2.labelOffset = 19;
			categoryAxis2.labelRotation = 90;
			categoryAxis2.inside = false;
			categoryAxis2.gridAlpha = 0.20;
			categoryAxis2.autoWrap = true;
			categoryAxis2.tickLength = 0;

			var valueaxis2 = new AmCharts.ValueAxis();
			valueaxis2.title = "Units";
			valueaxis2.unit = "%";
			valueaxis2.showFirstLabel = false;
			chart.addValueAxis(valueaxis2);

			var chartScrollbar = new AmCharts.ChartScrollbar();
			//chartScrollbar.dragIcon = "";
			chartScrollbar.dragIconHeight = 0;
			chartScrollbar.scrollbarHeight = 20;
			chartScrollbar.resizeEnabled = false;
			chartScrollbar.selectedBackgroundColor = "#0000";
			chartScrollbar.oppositeAxis = false;
			chart.addChartScrollbar(chartScrollbar);

			var chartCursor = new AmCharts.ChartCursor();
			chart.addChartCursor(chartCursor);
			chartCursor.enabled = false;

			chart.addListener("init", zoomChart2);

			chart.write('chart3div');
		}

	}
	//zoom
	function zoomChart() {
		//var index2 = '${zoomIndex2}';
		//var one = "Aug 2014";
		//var two = "Jul 2015";
		//var index1 = chartData.length - 14;
		//chart.zoomToCategoryValues(one,two);
		chart.zoomToIndexes(chartData1.length - 12, chartData1.length);
		//chart.zoomToIndexes(chartData.length - 12, chartData.length);
		//chart.zoomToIndexes(5, 16);
		//chart.zoomToIndexes(index1,index2);

	}

	function zoomChart1() {
		//var index2 = '${zoomIndex2}';
		//var one = "Aug 2014";
		//var two = "Jul 2015";
		//var index1 = chartData.length - 14;
		//chart2.zoomToCategoryValues(one,two);
		//chart2.zoomToCategoryValues("201401", " 201406");
		chart2.zoomToIndexes(chartData2.length - 12, chartData2.length);
		//chart2.zoomToIndexes(5, 16);
		//chart2.zoomToIndexes(index1,index2);

	}
	function zoomChart2() {
		//chart.zoomToCategoryValues("201401", "201404");
		chart.zoomToIndexes(chartData.length - 12, chartData.length);
		//chart.zoomToIndexes(5, 16);

	}
	//print	
	function printDiv(divName) {
		var printContents = document.getElementById(divName).innerHTML;
		var originalContents = document.body.innerHTML;
		document.body.innerHTML = printContents;
		window.print();
		document.body.innerHTML = originalContents;
	}
</script>

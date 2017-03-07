<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/desktop/template" %>

<template:styleGuide pageTitle="Plugins">

	<jsp:body>

		<div class="container bgwhite" id="style-guide-plugins">
			<div class="row">
				<div class="col-sm-6 col-sm-push-3">
					<h1><i class="fa fa-cogs" aria-hidden="true"></i> Plugin Styles</h1>
					<h3><blockquote><i>This section contains all Javascript Plugins to verify their styles and functionality</blockquote></h3>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Accessible Tabs - 
						<small>
							<a href="https://github.com/ginader/Accessible-Tabs" target="_blank">Source</a> | 
							<a href="http://blog.ginader.de/dev/jquery/accessible-tabs/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<div class="tabs" id="productTabs">
							<h2>some dummy text</h2>
							<div class="tabbody">
								<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nullam malesuada   suscipit pede. Nullam ipsum lacus, varius vel, nonummy in, consequat ut, neque.   </p>
								<h3>Lorem ipsum</h3>
								<p>Nullam malesuada suscipit pede. Nullam ipsum lacus, varius vel, nonummy in, consequat ut, neque.   Vivamus viverra. Duis dolor arcu, lacinia sit amet, sollicitudin sed, aliquet   vel, quam. Pellentesque molestie laoreet tortor. Aenean quam. Pellentesque magna   metus, venenatis sit amet, congue nec, dictum in, est. Aliquam nibh. </p>
							</div>
							<h2>some more dummy text</h2>
							<div class="tabbody">
								<p>Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. </p>
								<p>Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque</p>
							</div>
							<h2>anything else</h2>
							<div class="tabbody">
								<p>Here could be your content</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						BeautyTips - 
						<small>
							<a href="https://assets.lullabot.com/bt/bt-latest/jquery.bt.js" target="_blank">Source</a> | 
							<a href="https://assets.lullabot.com/bt/bt-latest/DEMO/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<a id="BeautyTips" class="target" title="The content of this tooltip is provided by the 'title' attribute of the target element.">Default example on hover</a><br />
						<a id="BeautyTipsCustom" class="target" title="The content of this tooltip is provided by the 'title' attribute of the target element.">Opens to the bottom on click</a>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Bootstrap - 
						<small>
							<a href="https://github.com/twbs/bootstrap" target="_blank">Source</a> | 
							<a href="http://getbootstrap.com/javascript" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<h2>Carousel</h2>
						<div id="thumbnail-preview-indicators" class="carousel slide" data-ride="carousel">
							<div class="carousel-inner">
								<div class="item slides active">
									<div class="slide-0">
										<img class="img-responsive" src="https://www.fmmotorparts.com/medias/?context=bWFzdGVyfGltYWdlc3wyNzIxMzZ8aW1hZ2UvanBlZ3xpbWFnZXMvaDRkL2hlYS85MTAxOTY5MDk2NzM0LmpwZ3xmMTM3N2Q2NGNhZGU2NjhmYTM4MGE3ZDYwYjUxNTM4MmFmYzVkYWY1ZTMzNDIwYzJjMmVkYzRkNTA4NWY1YmM5">
									</div>
								</div>
								<div class="item slides ">
									<div class="slide-1">
										<img class="img-responsive" src="https://www.fmmotorparts.com/medias/?context=bWFzdGVyfGltYWdlc3wyMzU2MjB8aW1hZ2UvanBlZ3xpbWFnZXMvaDQyL2gzNy85MTAxOTY5MjYwNTc0LmpwZ3wxYzMxNWY5ZDJkOGU2NDgzYmMwMWEyYzU4ZjkyMDBlZWJiMWY5NWFmN2YwOTYwOWQ3OTFhM2ZlYzE0ZTk5ZDRh">
									</div>
								</div>
							</div>
							<!-- Indicators -->
							<ol class="carousel-indicators">
								<li data-target="#thumbnail-preview-indicators" data-slide-to="0" class="active">
									<div class="thumbnail" style="padding:0;" >
										<img class="img-responsive" src="https://www.fmmotorparts.com/medias/?context=bWFzdGVyfGltYWdlc3wzNzA2fGltYWdlL2pwZWd8aW1hZ2VzL2hiNy9oZTUvOTEwMTk2Nzk4MjYyMi5qcGd8NDZlMTVmN2Y2ZWRkMzJlMWUyZjIzYWQ3NzRiYWVlNDcwZDgyODY3NzQzNGM1Y2Y1NTNkM2QxM2RkZDZlYjBlZQ" style="width:68px; width:6.8rem; height:68px; height:6.8rem; !important;">
									</div>
								</li>
								<li data-target="#thumbnail-preview-indicators" data-slide-to="1" class="">
									<div class="thumbnail" style="padding:0;" >
										<img class="img-responsive" src="https://www.fmmotorparts.com/medias/?context=bWFzdGVyfGltYWdlc3wzMjg0fGltYWdlL2pwZWd8aW1hZ2VzL2hkYS9oMmUvOTEwMTk2ODMxMDMwMi5qcGd8NTA5MWJlMGZlOTQ0M2UzMDE1YTc1ZWYzMTYyYTBiMTZhMWQxNTFhM2JlZjRkYjdhMTYwZWE2MTU5YWE1YmRjOA" style="width:68px; width:6.8rem; height:68px; height:6.8rem; !important;">
									</div>
								</li>
							</ol>
						</div>

						<hr />
						<h2>Modal</h2>
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#basicModal">Launch basic modal</button> 
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#productDetailModal">Launch product detail modal</button>

						<div class="modal fade" id="basicModal" tabindex="-1" role="dialog" aria-labelledby="basicModalLabel">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true" class="fa fa-close"></span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="basicModalLabel">Modal title</h4>
									</div>
									<div class="modal-body">
										...
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
										<button type="button" class="btn btn-primary">Save changes</button>
									</div>
								</div>
							</div>
						</div>
						<div class="modal fade" id="productDetailModal" tabindex="-1" role="dialog" aria-labelledby="productDetailModalLabel">
							<div class="modal-dialog prodDetailDialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true" class="fa fa-close"></span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="productDetailModalLabel">Modal title</h4>
									</div>
									<div class="modal-body">
										...
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
										<button type="button" class="btn btn-primary">Save changes</button>
									</div>
								</div>
							</div>
						</div>

						<h2>Tabs</h2>
						<div id="tabsection" class="col-lg-12">
							<div id="horizontalTab" class="productDetailPageTab">
								<ul class="resp-tabs-list pull-left">
									<li id="tab1">Specifications</li>
									<li id="tab2">Reviews</li>
									<li id="tab4">Also Fits</li>
								</ul>
								<div class="resp-tabs-container">
									<div id="prodSpec">
										<h3>Part Specifications</h3>
										<div class="productFeatureClasses">
											<div class="headline">FILTER SPECIFICATIONS</div>
											<table>
												<tbody>
													<tr>
														<td class="attrib">Filter Type</td>
														<td>Spin On</td>
													</tr>
													<tr>
														<td class="attrib">Outer Diameter Top (mm)</td>
														<td>94.8</td>
													</tr>
													<tr>
														<td class="attrib">Inner Diameter Bottom (Inches)</td>
														<td>3.591</td>
													</tr>
													<tr>
														<td class="attrib">Inner Diameter Bottom (mm)</td>
														<td>91.2</td>
													</tr>
													<tr>
														<td class="attrib">Thread Pitch</td>
														<td>13/16-16</td>
													</tr>
													<tr>
														<td class="attrib">Pressure Relief</td>
														<td>NO</td>
													</tr>
													<tr>
														<td class="attrib">Anti-Drain Back Valve</td>
														<td>YES</td>
													</tr>
													<tr>
														<td class="attrib">Height (Inches)</td>
														<td>5.335</td>
													</tr>
													<tr>
														<td class="attrib">Height (mm)</td>
														<td>135.5</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div id="reviewTab">
										<div class="reviewsDetails">
											<a href="https://www.fmmotorparts.com/fmstorefront/federalmogul/en/USD/sign-in" class=" fm_fnt_Blue"> SignIn/Register&nbsp;to write a Review<span class="linkarow fa fa-angle-right ">&nbsp;</span></a>
										</div>
									</div>
									<div id="AlsoFitsTab">
										<div class="alsoFitsScroll" style="display:block">  
											<h3>Also Fits</h3>
											<div class="userTable">
												<table class="table">
													<thead>
														<tr>
															<th>Make</th>
															<th>Model</th>
															<th>Year Range</th>
															<th>Position</th>
															<th>Drive Wheel</th>
															<th>Veh. Qty.</th>
															<th>Engine Base</th>
															<th>Engine VIN</th>
															<th>Engine Desg</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>911</td>
															<td>1992-1991</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.3L H6 (3294 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>968</td>
															<td>1995-1992</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.0L L4 (2983 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>911</td>
															<td>1994-1993</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.6L H6 (3606 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>944</td>
															<td>1988-1983</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>2.5L L4 (2475 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>944</td>
															<td>1991-1990</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.0L L4 (2983 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>924</td>
															<td>1988-1987</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>2.5L L4 (2475 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Bootstrap Slider - 
						<small>
							<a href="https://github.com/seiyria/bootstrap-slider" target="_blank">Source</a> | 
							<a href="http://seiyria.com/bootstrap-slider/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<input id="ex2" type="text" class="span2" value="" data-slider-min="10" data-slider-max="1000" data-slider-step="5" data-slider-value="[150,350]" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Bootstrap Datepicker - 
						<small>
							<a href="https://github.com/uxsolutions/bootstrap-datepicker" target="_blank">Source</a> | 
							<a href="https://uxsolutions.github.io/bootstrap-datepicker" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<div class="row">
							<form class="form-inline">
								<div class="form-group col-sm-2">
									<div class="controls">
										<div class="input-group">
											<input type="text" path="startDate" required="required"  id="date-picker-8" placeholder="Start Date" class="date-picker form-control"/>
											<label for="date-picker-8" class="input-group-addon btn">
												<span id="startDate" class="glyphicon glyphicon-calendar"></span>
											</label>
										</div>
									</div>
								</div>
								<div class="form-group col-sm-2">
									<div class="controls">
										<div class="input-group">
											<input type="text" path="endDate" required="required"  id="date-picker-8" placeholder="End Date" class="date-picker form-control"/>
											<label for="date-picker-8" class="input-group-addon btn">
												<span id="endDate" class="glyphicon glyphicon-calendar"></span>
											</label>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Bootstrap Maxlength - 
						<small>
							<a href="https://github.com/mimo84/bootstrap-maxlength" target="_blank">Source</a> | 
							<a href="http://mimo84.github.io/bootstrap-maxlength/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<p>Input below has a max length of 25, notification starts showing at 15</p>
						<input type="text" class="form-control" maxlength="25" name="defaultconfig" id="defaultconfig">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Bootstrap Toggle - 
						<small>
							<a href="https://github.com/minhur/bootstrap-toggle" target="_blank">Source</a> | 
							<a href="http://www.bootstraptoggle.com/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<div class="checkbox">
							<label>
								<input type="checkbox" data-toggle="toggle"> Option one is enabled
							</label>
						</div>
						<div class="checkbox disabled">
							<label>
								<input type="checkbox" disabled data-toggle="toggle"> Option two is disabled
							</label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						BXSlider - 
						<small>
							<a href="https://github.com/stevenwanderski/bxslider-4" target="_blank">Source</a> | 
							<a href="http://bxslider.com/examples" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<div class="quality">
							<div class="qualityCarousel">
								<div class="bx-wrapper">
									<div id="BXSlider" style="width: 415%; position: relative; transition-duration: 0s; transform: translate3d(-1060px, 0px, 0px);">
										<div class="slide bx-clone" style="float: left; list-style: none; position: relative; width: 500px; margin-right: 30px;">

											<div class="well well-qualityCarousel qualityImg1 clearfix ">
												<div class="col-sm-5 col-lg-5 col-md-5 col-xs-5 rgtPad_0">
													<div class=" ">
														<h3 class="qualityContentTitle topMargn_8">
															<span>Quality parts. Trusted brands.</span>
														</h3>
														<p class="qualityContent">See what sets our parts apart.</p>
														<a href="#">
															<button class="btn btn-sm btn-fmDefault text-uppercase">LEARN MORE</button>
														</a>
													</div>
												</div>
												<div class="holder col-sm-7 col-lg-7 col-md-7 col-xs-7">
													<img class="img-responsive fm-announce-banner" src="https://www.fmmotorparts.com/medias/fm-homepage-promotile.jpg?context=bWFzdGVyfHJvb3R8NzM2OXxpbWFnZS9qcGVnfGgzOS9oYTcvODg3NjE1MTczNDMwMi5qcGd8NzI0NGRmZjJlMzVjYWRiMGI5ZTUwZTlkYjlkMGEwNTQ1N2U3YjU2ZTEyMWFkODRmMmQ1ODcyMTI0MWYwZWZiNg" alt="" title="">
												</div>
											</div>
										</div>
										<div class="slide bx-clone" style="float: left; list-style: none; position: relative; width: 500px; margin-right: 30px;">

											<div class="well well-qualityCarousel qualityImg1 clearfix ">
												<a target="_blank" href="http://moogproblemsolver.com"><img class="img-responsive fm-announce-banner" src="https://www.fmmotorparts.com/medias/moog-nascar-promo.gif?context=bWFzdGVyfHJvb3R8MTI3NzR8aW1hZ2UvZ2lmfGgzMi9oMjAvODg3NjE1MTQ3MjE1OC5naWZ8NTlmMzBlODFiYjk3OTUxMWViZWZkZmZlN2FlNjdjNjMzMTAzYTkzNDI0NmNjZTkxNmRhYWNmZDgyMjM4NDIzNw" alt="" title=""> </a>
											</div>
										</div>

										<div class="slide" style="float: left; list-style: none; position: relative; width: 500px; margin-right: 30px;">
											<div class="well well-qualityCarousel qualityImg1 clearfix ">
												<div class="col-sm-5 col-lg-5 col-md-5 col-xs-5 rgtPad_0">
													<div class=" ">
														<h3 class="qualityContentTitle topMargn_8">
															<span>Quality parts. Trusted brands.</span>
														</h3>
														<p class="qualityContent">See what sets our parts apart.</p>
														<a href="#">
															<button class="btn btn-sm btn-fmDefault text-uppercase">LEARN MORE</button>
														</a>
													</div>
												</div>
												<div class="holder col-sm-7 col-lg-7 col-md-7 col-xs-7">
													<img class="img-responsive fm-announce-banner" src="https://www.fmmotorparts.com/medias/fm-homepage-promotile.jpg?context=bWFzdGVyfHJvb3R8NzM2OXxpbWFnZS9qcGVnfGgzOS9oYTcvODg3NjE1MTczNDMwMi5qcGd8NzI0NGRmZjJlMzVjYWRiMGI5ZTUwZTlkYjlkMGEwNTQ1N2U3YjU2ZTEyMWFkODRmMmQ1ODcyMTI0MWYwZWZiNg" alt="" title="">
												</div>
											</div>
										</div>
										<div class="slide" style="float: left; list-style: none; position: relative; width: 500px; margin-right: 30px;">
											<div class="well well-qualityCarousel qualityImg1 clearfix ">
												<a target="_blank" href="http://moogproblemsolver.com"><img class="img-responsive fm-announce-banner" src="https://www.fmmotorparts.com/medias/moog-nascar-promo.gif?context=bWFzdGVyfHJvb3R8MTI3NzR8aW1hZ2UvZ2lmfGgzMi9oMjAvODg3NjE1MTQ3MjE1OC5naWZ8NTlmMzBlODFiYjk3OTUxMWViZWZkZmZlN2FlNjdjNjMzMTAzYTkzNDI0NmNjZTkxNmRhYWNmZDgyMjM4NDIzNw" alt="" title=""> </a>
											</div>
										</div>
										<div class="slide bx-clone" style="float: left; list-style: none; position: relative; width: 500px; margin-right: 30px;">

											<div class="well well-qualityCarousel qualityImg1 clearfix ">
												<div class="col-sm-5 col-lg-5 col-md-5 col-xs-5 rgtPad_0">
													<div class=" ">
														<h3 class="qualityContentTitle topMargn_8">
															<span>Quality parts. Trusted brands.</span>
														</h3>
														<p class="qualityContent">See what sets our parts apart.</p>
														<a href="#">
															<button class="btn btn-sm btn-fmDefault text-uppercase">LEARN MORE</button>
														</a>
													</div>
												</div>
												<div class="holder col-sm-7 col-lg-7 col-md-7 col-xs-7">
													<img class="img-responsive fm-announce-banner" src="https://www.fmmotorparts.com/medias/fm-homepage-promotile.jpg?context=bWFzdGVyfHJvb3R8NzM2OXxpbWFnZS9qcGVnfGgzOS9oYTcvODg3NjE1MTczNDMwMi5qcGd8NzI0NGRmZjJlMzVjYWRiMGI5ZTUwZTlkYjlkMGEwNTQ1N2U3YjU2ZTEyMWFkODRmMmQ1ODcyMTI0MWYwZWZiNg" alt="" title="">
												</div>
											</div>
										</div>
										<div class="slide bx-clone" style="float: left; list-style: none; position: relative; width: 500px; margin-right: 30px;">
											<div class="well well-qualityCarousel qualityImg1 clearfix ">
												<a target="_blank" href="http://moogproblemsolver.com"><img class="img-responsive fm-announce-banner" src="https://www.fmmotorparts.com/medias/moog-nascar-promo.gif?context=bWFzdGVyfHJvb3R8MTI3NzR8aW1hZ2UvZ2lmfGgzMi9oMjAvODg3NjE1MTQ3MjE1OC5naWZ8NTlmMzBlODFiYjk3OTUxMWViZWZkZmZlN2FlNjdjNjMzMTAzYTkzNDI0NmNjZTkxNmRhYWNmZDgyMjM4NDIzNw" alt="" title=""> </a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">Colorbox - 
						<small>
							<a href="https://github.com/jackmoore/colorbox" target="_blank">Source</a> | 
							<a href="http://www.jacklmoore.com/colorbox/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<a class='inline' href="#inline_content">Launch demo modal</a>
						<!-- This contains the hidden content for inline calls -->
						<div style='display:none'>
							<div id='inline_content' style='padding:10px; background:#fff;'>
								<p><strong>This content comes from a hidden element on this page.</strong></p>
								<p>The inline option preserves bound JavaScript events and changes, and it puts the content back where it came from when it is closed.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Easy Responsive Tabs - 
						<small>
							<a href="https://github.com/samsono/Easy-Responsive-Tabs-to-Accordion/" target="_blank">Source</a> | 
							<a href="https://webthemez.com/demo/easy-responsive-tabs/Index.html#parentHorizontalTab1" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<div id="easyResponsiveTabs" class="productDetailPageTab">
								<ul class="resp-tabs-list pull-left">
									<li id="tab1">Specifications</li>
									<li id="tab2">Reviews</li>
									<li id="tab4">Also Fits</li>
								</ul>
								<div class="resp-tabs-container">
									<div id="prodSpec">
										<h3>Part Specifications</h3>
										<div class="productFeatureClasses">
											<div class="headline">FILTER SPECIFICATIONS</div>
											<table>
												<tbody>
													<tr>
														<td class="attrib">Filter Type</td>
														<td>Spin On</td>
													</tr>
													<tr>
														<td class="attrib">Outer Diameter Top (mm)</td>
														<td>94.8</td>
													</tr>
													<tr>
														<td class="attrib">Inner Diameter Bottom (Inches)</td>
														<td>3.591</td>
													</tr>
													<tr>
														<td class="attrib">Inner Diameter Bottom (mm)</td>
														<td>91.2</td>
													</tr>
													<tr>
														<td class="attrib">Thread Pitch</td>
														<td>13/16-16</td>
													</tr>
													<tr>
														<td class="attrib">Pressure Relief</td>
														<td>NO</td>
													</tr>
													<tr>
														<td class="attrib">Anti-Drain Back Valve</td>
														<td>YES</td>
													</tr>
													<tr>
														<td class="attrib">Height (Inches)</td>
														<td>5.335</td>
													</tr>
													<tr>
														<td class="attrib">Height (mm)</td>
														<td>135.5</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div id="reviewTab">
										<div class="reviewsDetails">
											<a href="http://www.fmmotorparts.com/fmstorefront/federalmogul/en/USD/sign-in" class=" fm_fnt_Blue"> SignIn/Register&nbsp;to write a Review<span class="linkarow fa fa-angle-right ">&nbsp;</span></a>
										</div>
									</div>
									<div id="AlsoFitsTab">
										<div class="alsoFitsScroll" style="display:block">  
											<h3>Also Fits</h3>
											<div class="userTable">
												<table class="table">
													<thead>
														<tr>
															<th>Make</th>
															<th>Model</th>
															<th>Year Range</th>
															<th>Position</th>
															<th>Drive Wheel</th>
															<th>Veh. Qty.</th>
															<th>Engine Base</th>
															<th>Engine VIN</th>
															<th>Engine Desg</th>
														</tr>
													</thead>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>911</td>
															<td>1992-1991</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.3L H6 (3294 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>968</td>
															<td>1995-1992</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.0L L4 (2983 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>911</td>
															<td>1994-1993</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.6L H6 (3606 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>944</td>
															<td>1988-1983</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>2.5L L4 (2475 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>944</td>
															<td>1991-1990</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>3.0L L4 (2983 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
													<tbody>
														<tr>
															<td>Porsche</td>
															<td>924</td>
															<td>1988-1987</td>
															<td></td>
															<td></td>
															<td>1</td>
															<td>2.5L L4 (2475 CC)</td>
															<td></td>
															<td></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</div>
							</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Elevate Zoom - 
						<small>
							<a href="https://github.com/elevateweb/elevatezoom" target="_blank">Source</a> | 
							<a href="http://www.elevateweb.co.uk/image-zoom" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<img id="prodMainImg" src="https://www.fmmotorparts.com/medias/?context=bWFzdGVyfGltYWdlc3wyNzIxMzZ8aW1hZ2UvanBlZ3xpbWFnZXMvaDRkL2hlYS85MTAxOTY5MDk2NzM0LmpwZ3xmMTM3N2Q2NGNhZGU2NjhmYTM4MGE3ZDYwYjUxNTM4MmFmYzVkYWY1ZTMzNDIwYzJjMmVkYzRkNTA4NWY1YmM5" data-zoom-image="https://www.fmmotorparts.com/medias/?context=bWFzdGVyfGltYWdlc3wyNzIxMzZ8aW1hZ2UvanBlZ3xpbWFnZXMvaDRkL2hlYS85MTAxOTY5MDk2NzM0LmpwZ3xmMTM3N2Q2NGNhZGU2NjhmYTM4MGE3ZDYwYjUxNTM4MmFmYzVkYWY1ZTMzNDIwYzJjMmVkYzRkNTA4NWY1YmM5" class="img-responsive" style="max-width: 200px;" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						Holder.js - 
						<small>
							<a href="https://github.com/imsky/holder" target="_blank">Source</a> | 
							<a href="http://holderjs.com/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<img data-src="holder.js/300x200">
						<img data-src="holder.js/200x200">
						<img data-src="holder.js/350x200">
						<img data-src="holder.js/150x200">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						jQuery Slim Scroll - 
						<small>
							<a href="https://github.com/rochal/jQuery-slimScroll" target="_blank">Source</a> | 
							<a href="http://rocha.la/jQuery-slimScroll" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<div id="slimScroll" style="overflow: hidden; width: auto; height: 180px;">
							<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed diam sem, imperdiet at mollis vestibulum, bibendum id purus. Aliquam molestie, leo sed molestie condimentum, massa enim lobortis massa, in vulputate diam lorem quis justo. Nullam nec dignissim mi. In non varius nibh. Proin et eros nisi, eu vulputate libero. Suspendisse potenti. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Duis ultricies augue id risus dapibus blandit.</p>
							<p>Integer malesuada molestie dolor sit amet viverra. Mauris nec urna lorem. Integer commodo feugiat ligula eget fermentum. In in tellus a risus convallis pellentesque. Cras non faucibus est. Morbi sagittis risus mollis nisl mollis ac mattis mi volutpat. Vivamus ac rutrum elit. Suspendisse semper orci vitae sapien sollicitudin mattis.</p>
							<p>Ad culpa ipsa, commodi vitae id veniam distinctio, laboriosam iure quidem amet qui ducimus mollitia, eligendi praesentium libero vero.</p>
							<p>Voluptates accusantium error explicabo repudiandae architecto a dolor aperiam expedita quas culpa facilis natus earum aliquam minus, nostrum debitis ex ut placeat.</p>
							<p>Eius quasi sapiente dolor debitis autem ipsam id adipisci ratione, modi ipsum veritatis? Eveniet, impedit nihil. Velit sequi eligendi deleniti ea laboriosam.</p>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Deleniti velit quasi optio amet error corporis commodi eum, debitis qui quaerat! Aliquam odit et, fugiat enim ipsum atque dolores, laudantium debitis!</p>
							<p>Pariatur laboriosam, quasi earum repellat consequuntur adipisci possimus, recusandae quibusdam ipsum nisi, beatae distinctio minima accusamus! Mollitia cum aliquid quia, odit debitis delectus nulla illo quas nihil similique, architecto ducimus.</p>
							<p>Reprehenderit obcaecati beatae non soluta sed suscipit dolores a voluptates similique hic delectus, quidem doloribus cupiditate itaque autem exercitationem error asperiores dignissimos, fugit, omnis nulla iure dolore voluptatem, ut. Nulla.</p>
							<p>Corporis nihil quod quia nesciunt odio, dignissimos est delectus dolorum error? Voluptas vel atque nobis totam, autem sequi, dolorum porro error. Neque dolorum aliquam voluptatem, mollitia consectetur fugiat vel architecto.</p>
							<p>Obcaecati nemo veritatis expedita eos nesciunt atque, possimus culpa alias magnam aperiam! Iusto quisquam nihil, nostrum et consequatur exercitationem vero, natus magnam cupiditate ratione quis. Magni fugiat veritatis, qui reiciendis.</p>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						jQuery Treeview - 
						<small>
							<a href="https://github.com/jzaefferer/jquery-treeview" target="_blank">Source</a> | 
							<a href="http://jquery.bassistance.de/treeview/demo/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<div class="treeview">
							<ul id="jQueryTreeview">
								<li><a href="#">Item 1.0</a>
									<ul>
										<li><a href="#.0">Item 1.0.0</a></li>
									</ul>
								</li>
								<li><a href="#">Item 1.1</a></li>
								<li><a href="#">Item 1.2</a>
									<ul>
										<li><a href="#">Item 1.2.0</a>
										<ul>
											<li><a href="#">Item 1.2.0.0</a></li>
											<li><a href="#">Item 1.2.0.1</a></li>
											<li><a href="#">Item 1.2.0.2</a></li>
										</ul>
									</li>
										<li><a href="#">Item 1.2.1</a>
										<ul>
											<li><a href="#">Item 1.2.1.0</a></li>
										</ul>
									</li>
										<li><a href="#">Item 1.2.2</a>
										<ul>
											<li><a href="#">Item 1.2.2.0</a></li>
											<li><a href="#">Item 1.2.2.1</a></li>
											<li><a href="#">Item 1.2.2.2</a></li>
										</ul>
									</li>
									</ul>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						jQuery UI Stars
					</h1>
					<div class="well well-lg">
						<div id="stars-wrapper" class="controls clearfix">
							<input type="radio" name="rating" value="1" />
					        <input type="radio" name="rating" value="2" />
					        <input type="radio" name="rating" value="3" checked="checked" />
					        <input type="radio" name="rating" value="4" />
					        <input type="radio" name="rating" value="5" />
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						jQuery Validation - 
						<small>
							<a href="https://github.com/jzaefferer/jquery-validation" target="_blank">Source</a> | 
							<a href="http://jqueryvalidation.org/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<p>Validation on Submit</p>
						<div class="row">
							<form id="validationForm" class="form-inline">
								<div class="form-group col-sm-2">
									<div class="controls">
										<div class="input-group">
											<input type="text" name="startDate" required="required" placeholder="Start Date" class="form-control"/>
										</div>
									</div>
								</div>
								<div class="form-group col-sm-2">
									<div class="controls">
										<div class="input-group">
											<input type="text" name="endDate" required="required" placeholder="End Date" class="form-control"/>
										</div>
									</div>
								</div>
								<div class="col-sm-3">
									<button type="submit" class="btn"">Submit</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">jRange - 
						<small>
							<a href="https://github.com/nitinhayaran/jRange" target="_blank">Source</a> | 
							<a href="http://nitinhayaran.github.io/jRange/demo/" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<input type="hidden" class="single-slider" value="0.0" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">
						TableSorter - 
						<small>
							<a href="_SOURCE_" target="_blank">Source</a> | 
							<a href="_DEMO_" target="_blank">Demo</a>
						</small>
					</h1>
					<div class="well well-lg">
						<table id="myTable" class="table tablesorter"> 
							<thead> 
								<tr> 
								    <th>Last Name</th> 
								    <th>First Name</th> 
								    <th>Email</th> 
								    <th>Due</th> 
								    <th>Web Site</th> 
								</tr> 
								</thead> 
								<tbody> 
								<tr> 
								    <td>Smith</td> 
								    <td>John</td> 
								    <td>jsmith@gmail.com</td> 
								    <td>$50.00</td> 
								    <td>http://www.jsmith.com</td> 
								</tr> 
								<tr> 
								    <td>Bach</td> 
								    <td>Frank</td> 
								    <td>fbach@yahoo.com</td> 
								    <td>$50.00</td> 
								    <td>http://www.frank.com</td> 
								</tr> 
								<tr> 
								    <td>Doe</td> 
								    <td>Jason</td> 
								    <td>jdoe@hotmail.com</td> 
								    <td>$100.00</td> 
								    <td>http://www.jdoe.com</td> 
								</tr> 
								<tr> 
								    <td>Conway</td> 
								    <td>Tim</td> 
								    <td>tconway@earthlink.net</td> 
								    <td>$50.00</td> 
								    <td>http://www.timconway.com</td> 
								</tr> 
							</tbody> 
						</table> 
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<h1 class="style-guide-headline">Others - <small>Additional plugins in use that do not have a visual representation</small></h1>
					<div class="well well-lg">
						<h2>bgiframe - 
							<small><a href="https://github.com/brandonaaron/bgiframe" target="_blank">Source</a></small>
						</h2>
						<h2>blockUI - 
							<small><a href="http://jquery.malsup.com/block/" target="_blank">Source</a></small>
						</h2>
						<h2>Currencies - 
							<small><a href="https://github.com/carolineschnapp/currencies" target="_blank">Source</a></small>
						</h2>
						<h2>jquery Form - 
							<small><a href="https://github.com/malsup/form" target="_blank">Source</a></small>
						</h2>
						<h2>jquery.easing - 
							<small><a href="https://github.com/gdsmith/jquery.easing" target="_blank">Source</a></small>
						</h2>
						<h2>jquery.pstrength - 
							<small>**Two versions in use, both found in <code>fm/fmstorefront/web/webroot/_ui/desktop/vendor/</code></small>
						</h2>
						<h2>jquery.scrollTo - 
							<small><a href="https://github.com/flesler/jquery.scrollTo" target="_blank">Source</a></small>
						</h2>
						<h2>jquery-plugin-query-object - 
							<small><a href="https://github.com/alrusdi/jquery-plugin-query-object" target="_blank">Source</a></small>
						</h2>
						<h2>jquery-tmpl - 
							<small><a href="https://github.com/BorisMoore/jquery-tmpl" target="_blank">Source</a></small>
						</h2>
						<h2>waitForImages - 
							<small><a href="https://github.com/alexanderdickson/waitForImages" target="_blank">Source</a></small>
						</h2>
						<h2>Waypoints - 
							<small><a href="http://imakewebthings.com/waypoints/" target="_blank">Source</a></small>
						</h2>

					</div>
				</div>
			</div>
		</div>

	</jsp:body>

</template:styleGuide>
module.exports = function(grunt) {
	var basePath = "fm/fmstorefront/web/webroot/_ui/desktop";
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),

		concat: {
			options: {
				separator: ';\n\n'
			},
			vendor: {
				src: [
					// Vendor Includes
					basePath + '/bower_components/bootstrap/dist/js/bootstrap.js',
					basePath + '/bower_components/jquery-plugin-query-object/jquery.query-object.js',
					basePath + '/bower_components/jquery-waypoints/waypoints.js',
					basePath + '/bower_components/jquery-ui/jquery-ui.js',
					basePath + '/bower_components/jquery-tmpl/jquery.tmpl.js',
					basePath + '/bower_components/blockUI/jquery.blockUI.js',
					basePath + '/bower_components/jquery-colorbox/jquery.colorbox.js',
					basePath + '/bower_components/jquery.bt/index.js',
					basePath + '/vendor/jquery.ui.stars/jquery.ui.stars-3.0.1.min.js',
					basePath + '/bower_components/waitForImages/dist/jquery.waitforimages.js',
					basePath + '/bower_components/jquery.scrollTo/jquery.scrollTo.js',
					basePath + '/bower_components/form/jquery.form.js',
					basePath + '/bower_components/bgiframe/jquery.bgiframe.js',
					basePath + '/vendor/jquery.pstrength/jquery.pstrength.custom-1.2.0.js',
					basePath + '/vendor/jquery.pstrength/password_strength_error.js',
					basePath + '/bower_components/jQuery-slimScroll/jquery.slimscroll.js',
					basePath + '/bower_components/elevatezoom/jquery.elevatezoom.js',
					basePath + '/bower_components/tablesorter/jquery.tablesorter.js',
					basePath + '/bower_components/bxslider-4/jquery.bxslider.js',
					basePath + '/bower_components/jquery.easing/jquery.easing.js',
					basePath + '/bower_components/jRange/jquery.range.js',
					basePath + '/bower_components/Accessible-Tabs/js/jquery.tabs.js',
					basePath + '/bower_components/currencies/jquery.currencies.js',
					basePath + '/bower_components/jquery-validation/jquery.validate.js',
					basePath + '/bower_components/jquery-treeview/jquery.treeview.js',
					basePath + '/vendor/bootstrap-slider/compat.js',
					basePath + '/vendor/bootstrap-slider/bootstrap-slider.js',
					basePath + '/bower_components/Easy-Responsive-Tabs-to-Accordion/js/easyResponsiveTabs.js',
					basePath + '/bower_components/holderjs/holder.js',
					basePath + '/bower_components/bootstrap-toggle/js/bootstrap-toggle.js',
					basePath + '/bower_components/bootstrap-datepicker/dist/js/bootstrap-datepicker.js',
					basePath + '/bower_components/bootstrap-maxlength/bootstrap-maxlength.js',
					basePath + '/bower_components/iframe-resizer/js/iframeResizer.contentWindow.js',
				],
				dest: basePath + '/theme-fmmp/dist/vendor.js'
			},
			header: {
				src: [
					basePath + '/bower_components/jquery/dist/jquery.js',
					basePath + '/bower_components/jquery-migrate-1.4.1/index.js',
					basePath + '/bower_components/js-cookie/src/js.cookie.js',
					basePath + '/common/js/js.hybris.oauth.js',
					'fm/fmstorefront/web/webroot/_ui/shared/js/analyticsmediator.js'
				],
				dest: basePath + '/theme-fmmp/dist/header.js'
			},
			base: {
				src: [
					// Environment Variables
					basePath + '/common/js/fm.useraddress.js',
					basePath + '/common/js/acc.userlocation.js',
					basePath + '/common/js/fm.loyaltysurvey.js',
					basePath + '/common/js/acc.track.js',
					basePath + '/common/js/fm.ymmsearch.js',
					basePath + '/common/js/fm.loyaltyproductdetails.js',
					basePath + '/common/js/acc.storefinder.js',
					basePath + '/common/js/fm.uploadorder.js',
					basePath + '/common/js/fm.locations.js',
					basePath + '/common/js/acc.cartremoveitem.js',
					basePath + '/common/js/acc.paginationsort.js',
					basePath + '/common/js/acc.minicart.js',
					basePath + '/common/js/fm.common.js',
					basePath + '/common/js/acc.common.js',
					basePath + '/common/js/acc.cms.js',
					basePath + '/common/js/acc.product.js',
					basePath + '/common/js/acc.refinements.js',
					basePath + '/common/js/acc.carousel.js',
					basePath + '/common/js/acc.autocomplete.js',
					basePath + '/common/js/acc.pstrength.js',
					basePath + '/common/js/acc.password.js',
					basePath + '/common/js/acc.minicart.js',
					basePath + '/common/js/acc.userlocation.js',
					basePath + '/common/js/acc.langcurrencyselector.js',
					basePath + '/common/js/acc.paginationsort.js',
					basePath + '/common/js/acc.checkout.js',
					basePath + '/common/js/acc.approval.js',
					basePath + '/common/js/acc.quote.js',
					basePath + '/common/js/acc.negotiatequote.js',
					basePath + '/common/js/acc.paymentmethod.js',
					basePath + '/common/js/acc.placeorder.js',
					basePath + '/common/js/acc.address.js',
					basePath + '/common/js/acc.refresh.js',
					basePath + '/common/js/acc.stars.js',
					basePath + '/common/js/acc.forgotpassword.js',
					basePath + '/common/js/acc.productDetail.js',
					basePath + '/common/js/acc.producttabs.js',
					basePath + '/common/js/acc.productorderform.js',
					basePath + '/common/js/acc.skiplinks.js',
					basePath + '/common/js/custom.js',
					basePath + '/common/js/registrationvalidation.js',
					basePath + '/common/js/fm.myfmaccount.js',
					basePath + '/theme-fmmp/js/acc.mycompany.js',
					basePath + '/theme-fmmp/js/acc.futurelink.js',
					basePath + '/theme-fmmp/js/acc.search.js',
					basePath + '/theme-fmmp/js/acc.checkoutB2B.js',
				],
				dest: basePath + '/theme-fmmp/dist/app.js'
			}
		},
		uglify: {
			options: {
				mangle: false,
				sourceMap: true
			},
			app: {
				files: {
					'fm/fmstorefront/web/webroot/_ui/desktop/theme-fmmp/dist/app.min.js': [basePath + '/theme-fmmp/dist/vendor.js', basePath + '/theme-fmmp/dist/app.js']
				}
			},
			header: {
				files: {
					'fm/fmstorefront/web/webroot/_ui/desktop/theme-fmmp/dist/header.min.js': [basePath + '/theme-fmmp/dist/header.js']
				}
			}
		},
		cssmin: {
			options: {
				keepSpecialComments: 0,
				roundingPrecision: -1,
				sourceMap: true
			},
			vendor: {
				src: [
					basePath + '/bower_components/bootstrap/dist/css/bootstrap.css',
					basePath + '/bower_components/bootstrap-datepicker/dist/css/bootstrap-datepicker.css',
					basePath + '/bower_components/bootstrap-toggle/css/bootstrap-toggle.min.css',
					basePath + '/bower_components/bxslider-4/jquery.bxslider.css',
					basePath + '/bower_components/font-awesome/css/font-awesome.css',
					basePath + '/bower_components/jquery-colorbox/example1/colorbox.css',
					basePath + '/bower_components/jquery-ui/themes/base/jquery.ui.autocomplete.css',
					basePath + '/bower_components/jquery-treeview/jquery.treeview.css',
					basePath + '/bower_components/jRange/jquery.range.css',
					basePath + '/bower_components/seiyria-bootstrap-slider/dist/css/bootstrap-slider.css',
					basePath + '/vendor/jquery.ui.stars/jquery.ui.stars-3.0.1.css'
				],
				dest: basePath + '/theme-fmmp/dist/vendor.min.css'
			}
		},
		less: {
			prod: {
				options: {
					compress: true,
					dumpLineNumbers: 'comments',
					plugins : [ new (require('less-plugin-autoprefix'))({browsers : [ "last 4 versions" ]}) ],
					relativeUrls: true,
					sourceMap: true,
					sourceMapBasepath: 'fm/fmstorefront/web/webroot/_ui/',
					sourceMapRootpath: '/fmstorefront/_ui/'
				},
				files: {
					'fm/fmstorefront/web/webroot/_ui/desktop/theme-fmmp/dist/styles-hybris.min.css': [basePath + '/theme-fmmp/less/styles-hybris.less']
				}
			},
			styleguide: {
				options: {
					compress: true,
					plugins : [ new (require('less-plugin-autoprefix'))({browsers : [ "last 4 versions" ]}) ],
					relativeUrls: true,
					sourceMap: true,
					sourceMapBasepath: 'fm/fmstorefront/web/webroot/_ui/',
					sourceMapRootpath: '/fmstorefront/_ui/'
				},
				files: {
					'fm/fmstorefront/web/webroot/_ui/desktop/theme-fmmp/dist/styleguide.min.css': [basePath + '/theme-fmmp/less/styleguide.less']
				}
			}
		},
		watch: {
			grunt: {
				files: ['Gruntfile.js'],
				tasks: 'build'
			},
			js: {
				files: [basePath + '/common/js/**/*.js', basePath + '/theme-fmmp/js/**/*.js'],
				tasks: ['concat:base','uglify:app']
			},
			less: {
				files: [basePath + '/theme-fmmp/less/**/*'],
				tasks: ['less']
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	grunt.loadNpmTasks('grunt-contrib-less');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-watch');

	grunt.registerTask('build', ['less', 'cssmin', 'concat', 'uglify']);
	grunt.registerTask('default', ['build', 'watch']);
};

/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.federalmogul.storefront.controllers;

import de.hybris.platform.acceleratorcms.model.components.CategoryFeatureComponentModel;
import de.hybris.platform.acceleratorcms.model.components.MiniCartComponentModel;
import de.hybris.platform.acceleratorcms.model.components.NavigationBarComponentModel;
import de.hybris.platform.acceleratorcms.model.components.ProductFeatureComponentModel;
import de.hybris.platform.acceleratorcms.model.components.ProductReferencesComponentModel;
import de.hybris.platform.acceleratorcms.model.components.PurchasedCategorySuggestionComponentModel;
import de.hybris.platform.cms2.model.contents.components.CMSLinkComponentModel;
import de.hybris.platform.cms2lib.model.components.ProductCarouselComponentModel;


/**
 * Class with constants for controllers.
 */
public interface ControllerConstants
{
	/**
	 * Class with action name constants
	 */
	interface Actions
	{
		interface Cms
		{
			String _Prefix = "/view/";
			String _Suffix = "Controller";

			/**
			 * Default CMS component controller
			 */
			String DefaultCMSComponent = _Prefix + "DefaultCMSComponentController";

			/**
			 * CMS components that have specific handlers
			 */
			String PurchasedCategorySuggestionComponent = _Prefix + PurchasedCategorySuggestionComponentModel._TYPECODE + _Suffix;
			String ProductReferencesComponent = _Prefix + ProductReferencesComponentModel._TYPECODE + _Suffix;
			String ProductCarouselComponent = _Prefix + ProductCarouselComponentModel._TYPECODE + _Suffix;
			String MiniCartComponent = _Prefix + MiniCartComponentModel._TYPECODE + _Suffix;
			String ProductFeatureComponent = _Prefix + ProductFeatureComponentModel._TYPECODE + _Suffix;
			String CategoryFeatureComponent = _Prefix + CategoryFeatureComponentModel._TYPECODE + _Suffix;
			String NavigationBarComponent = _Prefix + NavigationBarComponentModel._TYPECODE + _Suffix;
			String CMSLinkComponent = _Prefix + CMSLinkComponentModel._TYPECODE + _Suffix;
		}
	}

	/**
	 * Class with view name constants
	 */
	interface Views
	{
		interface Cms
		{
			String ComponentPrefix = "cms/";
		}

		interface Pages
		{
			interface Account
			{
				String AccountLoginPage = "pages/account/accountLoginPage";
				String AccountHomePage = "pages/account/accountHomePage";
				String AccountOrderHistoryPage = "pages/account/accountOrderHistoryPage";
				String AccountOrderPage = "pages/account/accountOrderPage";
				String AccountProfilePage = "pages/account/accountProfilePage";
				String AccountProfileEditPage = "pages/account/accountProfileEditPage";
				String AccountProfileEmailEditPage = "pages/account/accountProfileEmailEditPage";
				String AccountChangePasswordPage = "pages/account/accountChangePasswordPage";
				String AccountAddressBookPage = "pages/account/accountAddressBookPage";
				String AccountEditAddressPage = "pages/account/accountEditAddressPage";
				String AccountPaymentInfoPage = "pages/account/accountPaymentInfoPage";
				String AccountMyQuotesPage = "pages/account/accountMyQuotesPage";
				String AccountReplenishmentSchedule = "pages/account/accountReplenishmentSchedule";
				String AccountReplenishmentScheduleDetails = "pages/account/accountReplenishmentScheduleDetails";
				String AccountOrderApprovalDashboardPage = "pages/account/accountOrderApprovalDashboardPage";
				String AccountOrderApprovalDetailsPage = "pages/account/accountOrderApprovalDetailsPage";
				String AccountQuoteDetailPage = "pages/account/accountQuoteDetailPage";
				String AccountCancelActionConfirmationPage = "pages/account/accountCancelActionConfirmationPage";
				String AccountBToSmallB = "pages/layout/b2bHomePage";
				String AccountBToBigB = "pages/layout/b2BLandingHomePage";
				String AccountBToC = "pages/layout/b2cHomePage";
				String RegistrationSuccessPage = "pages/account/registrationSuccessPage";
				String SignUpPage = "pages/account/signUpPage";
				String FMAccountHomePage = "pages/account/fmManageAccountPage";
				String FMAccountProfilePage = "pages/account/fmAccountProfilePage";
				String FMUpdateProfilePage = "pages/account/fmUpdateProfilePage";
				String FMUpdatePasswordPage = "pages/account/fmUpdatePasswordPage";
				String FMAccountAddressBookPage = "pages/account/fmAddressBookPage";
				String FMAccountEditAddressPage = "pages/account/fmAddEditAddressPage";
				String FMAccountAddressBookListPage = "pages/account/fmAddressBookListView";
				String FmTaxExemptionPage = "pages/account/fmTaxExemptionPage";
				String FMAccountAdminAddressBookPage = "pages/account/fmAdminAddressBookPage";
				String FMAccountAdminAddEditAddressPage = "pages/account/fmAdminAddEditAddressPage";
				String FmTaxExemptionApprovalPage = "pages/account/fmtaxexemptionapprovalpage";
				String AccountCSR = "pages/layout/fmCSRHomePage";
				String FmOrderHistoryPage = "pages/account/fmOrderHistoryPage";
				String fmOrderDetailsPage = "pages/account/fmOrderDetailsPage";
				//<!-- Balaji---Start Order Return --> 
				String FMAccountReturnHistoryPage = "pages/account/fmReturnHistoryPage";
				String FMAccountReturnDetailsPage = "pages/account/fmReturnDetailsPage";
				String FMAccountReturnRequestPage = "pages/account/fmReturnRequestPage";
				String FMAccountReturnRequestConfirmationPage = "pages/account/fmReturnRequestConfirmationPage";
				//<!-- Balaji---End Order Return -->
				//<!-- Balaji---Start Lead Generation Call back --> 
				String FMLeadGenerationCallBack = "pages/account/fmLeadGenerationCallBackPage";
				String FMLeadGenerationCallBackRequest = "pages/account/fmLeadGenerationCallBackRequestPage";
				//<!-- Balaji---End Lead Generation Call back -->
				String FMOrderHistoryPage = "pages/account/fmOrderHistoryPage1";
				String FMOrderDetailsPage = "pages/account/fmOrderDetailsPage";
				String FMOnlineToolsPage = "pages/layout/fmOnlineToolsPage";
				String FMHelpCenterPage = "pages/layout/fmHelpCenterPage";
				//<!-- Mahaveer ---upload order -->
				String FMUploadOrder = "pages/fm/ajax/uploadorder";
				String FMUploadOrderEntry = "pages/fm/ajax/uploadOrderEntry";
				String FMUploadOrderHistory = "pages/fm/ajax/uploadOrderHistory";
				String FMQuickOrderPage = "pages/account/quickOrderPage";
				String FMBackOrder = "pages/fm/ajax/backOrder";
				String FMBackOrderEntry = "pages/fm/ajax/backOrderEntry";
				String FMInvoiceHeader = "pages/fm/ajax/invoiceHeader";
				String FMInvoiceDetail = "pages/fm/ajax/invoiceDetail";
				String SITEMAP = "pages/layout/siteMap";


				String FMOrderStatusHeader = "pages/fm/ajax/orderStatusHeader";
				String FMOrderStatusDetails = "pages/fm/ajax/orderStatusDetails";

				String FMReturnStatusHeader = "pages/fm/ajax/returnStatusHeader";
				String FMReturnStatusDetails = "pages/fm/ajax/returnStatusDetails";
				String FMAutoAddressSearch = "pages/fm/ajax/autoAddressSearch";
				String PartInterchange = "pages/layout/partInterchangePage";
				String PartNumber = "pages/layout/partNumberPage";

				String FMGlobalErrorPage = "pages/error/globalErrorPage";
				String PartsFinderLicensePlateLookup = "pages/layout/partsFinderLicensePlateLookup";
				String PartsFinderLicenseVINLookup = "pages/layout/partsFinderLicenseVINLookup";
				String PartsFinderVehicleLookup = "pages/layout/partsFinderVehicleLookup";
				String UploadOrderStatus = "pages/account/uploadOrderStatus";
				String ApplicationUsageReport = "pages/account/applicationUsageReport";

				String LoyaltyHomePage = "pages/loyalty/loyaltyHomePage";
				String LoyaltyCheckoutPage = "pages/loyalty/loyaltyCheckoutPage";
				String loyaltycheckoutShippingPage = "pages/loyalty/loyaltycheckoutShippingPage";
				String loyaltycheckoutreviewplace = "pages/loyalty/loyaltycheckoutreviewplace";
				String loyaltycheckoutorderconfirm = "pages/loyalty/loyaltycheckoutorderconfirm";
				String LoyaltyHistoryPage = "pages/loyalty/loyaltyHistoryPage";
				String RewardProductDetailPage = "pages/loyalty/rewardsProductDetailPage";
				String LoyaltySearchResultPage = "pages/loyalty/loyaltySearchResultPage";
	                        String loyaltyWayToEarnPoints = "pages/loyalty/rewardsWaysToEarnPoints";
	                        String loyaltySupportTopicFAQs = "pages/loyalty/loyaltyFAQsPage";
                                String Rewardsaboutpage = "pages/account/rewardsAbout";
				String AccountBToT = "pages/layout/b2TLandingHomePage";
                                String loyaltyReferaFriendpage = "pages/loyalty/loyaltyRewardsReferFriendPage";
				String LoyaltyAvailableSurveysPage = "pages/loyalty/loyaltyAvailableSurveysPage";
                                String FMSITEMAPXML = "pages/layout/fmsiteMapxml";
				String LoyaltySurveyPage = "pages/loyalty/loyaltySurveyPage";
				String LoyaltySurveyCompletedPage = "pages/loyalty/loyaltySurveyCompletedPage";

			}

			interface Checkout
			{
				String CheckoutLoginPage = "pages/checkout/checkoutLoginPage";
				String CheckoutConfirmationPage = "pages/checkout/checkoutConfirmationPage";
				String QuoteCheckoutConfirmationPage = "pages/checkout/quoteCheckoutConfirmationPage";
				String CheckoutReplenishmentConfirmationPage = "pages/checkout/checkoutReplenishmentConfirmationPage";
			}

			interface SingleStepCheckout
			{
				String CheckoutSummaryPage = "pages/checkout/single/checkoutSummaryPage";
			}

			interface MultiStepCheckout
			{
				String checkoutShiptoAddress = "pages/checkout/multi/checkoutShiptoAddress";
				String checkoutMultiDeliveryMethod = "pages/checkout/multi/checkoutMultiDeliveryMethod";
				String checkoutMultiPaymentDetails = "pages/checkout/multi/checkoutMultiPaymentDetails";
				String checkoutMultiReviewPlaceOrder = "pages/checkout/multi/checkoutMultiReviewPlaceOrder";
				String checkoutMultiOrderConfirmation = "pages/checkout/multi/checkoutMultiOrderConfirmation";
				String CheckoutSampleLandingPage = "pages/checkout/multi/checkoutSampleLandingPage";
			}

			interface Password
			{
				String PasswordResetChangePage = "pages/password/passwordResetChangePage";
				String FMForgotPasswordPage = "pages/password/fmForgotPassword";
				String FMForgotPasswordResetPage = "pages/password/fmForgotPasswordResetPage";
				String FMForgotPasswordValidationPage = "pages/password/fmForgotPasswordValidationPage";
				String FMForgotPasswordSecretQuestionPage = "pages/password/fmForgotPasswordSecretQuestionPage";
			}

			interface Error
			{
				String ErrorNotFoundPage = "pages/error/errorNotFoundPage";
			}

			interface Cart
			{
				String CartPage = "pages/cart/cartPage";
				String FMCartPage = "pages/cart/fmCartPage";
				String FMLoyaltyCartPage = "pages/cart/loyalty/fmLoyaltyCartPage";
				String StoreFinderSearchPageForCart = "pages/cart/fmcartPageStoreFinder";
			}

			interface StoreFinder
			{
				String StoreFinderSearchPage = "pages/storeFinder/storeFinderSearchPage";
				String StoreFinderDetailsPage = "pages/storeFinder/storeFinderDetailsPage";
			}

			interface Misc
			{
				String MiscRobotsPage = "pages/misc/miscRobotsPage";
			}

			interface MyCompany
			{
				String MyCompanyLoginPage = "pages/company/myCompanyLoginPage";
				String MyCompanyHomePage = "pages/company/myCompanyHomePage";
				String MyNetworkHomePage = "pages/company/fmNetworkPage";
				String MyNetworkManageUsersPage = "pages/company/fmManageUserPage";
				String MyNetworkManageUserAddFormPage = "pages/company/fmCreateUserPage";
				String MyNetworkManageUserEditFormPage = "pages/company/fmEditUserPage";
				String MyNetworkManageUnitAddAddressPage = "pages/company/fmCreateAddressPage";
				String MyNetworkManageUnitEditAddressPage = "pages/company/fmEditAddressPage";
				String MyNetworkManageUnitDetailsPage = "pages/company/fmViewUnitPage";
				String MyNetworkManageUnitsPage = "pages/company/fmManageUnitPage";


				String MyCompanyManageUnitsPage = "pages/company/myCompanyManageUnitsPage";
				String MyCompanyManageUnitEditPage = "pages/company/myCompanyManageUnitEditPage";
				String MyCompanyManageUnitDetailsPage = "pages/company/myCompanyManageUnitDetailsPage";
				String MyCompanyManageUnitCreatePage = "pages/company/myCompanyManageUnitCreatePage";
				String MyCompanyManageBudgetsPage = "pages/company/myCompanyManageBudgetsPage";
				String MyCompanyManageBudgetsViewPage = "pages/company/myCompanyManageBudgetsViewPage";
				String MyCompanyManageBudgetsEditPage = "pages/company/myCompanyManageBudgetsEditPage";
				String MyCompanyManageBudgetsAddPage = "pages/company/myCompanyManageBudgetsAddPage";
				String MyCompanyManageCostCentersPage = "pages/company/myCompanyManageCostCentersPage";
				String MyCompanyCostCenterViewPage = "pages/company/myCompanyCostCenterViewPage";
				String MyCompanyCostCenterEditPage = "pages/company/myCompanyCostCenterEditPage";
				String MyCompanyAddCostCenterPage = "pages/company/myCompanyAddCostCenterPage";
				String MyCompanyManagePermissionsPage = "pages/company/myCompanyManagePermissionsPage";
				String MyCompanyManageUnitUserListPage = "pages/company/myCompanyManageUnitUserListPage";
				String MyCompanyManageUnitApproverListPage = "pages/company/myCompanyManageUnitApproversListPage";
				String MyCompanyManageUserDetailPage = "pages/company/myCompanyManageUserDetailPage";
				String MyCompanyManageUserAddEditFormPage = "pages/company/myCompanyManageUserAddEditFormPage";
				String MyCompanyManageUsersPage = "pages/company/myCompanyManageUsersPage";
				String MyCompanyManageUserDisbaleConfirmPage = "pages/company/myCompanyManageUserDisableConfirmPage";
				String MyCompanyManageUnitDisablePage = "pages/company/myCompanyManageUnitDisablePage";
				String MyCompanySelectBudgetPage = "pages/company/myCompanySelectBudgetsPage";
				String MyCompanyCostCenterDisableConfirm = "pages/company/myCompanyDisableCostCenterConfirmPage";
				String MyCompanyManageUnitAddAddressPage = "pages/company/myCompanyManageUnitAddAddressPage";
				String MyCompanyManageUserPermissionsPage = "pages/company/myCompanyManageUserPermissionsPage";
				String MyCompanyManageUserResetPasswordPage = "pages/company/myCompanyManageUserPassword";
				String MyCompanyBudgetDisableConfirm = "pages/company/myCompanyDisableBudgetConfirmPage";
				String MyCompanyManageUserGroupsPage = "pages/company/myCompanyManageUserGroupsPage";
				String MyCompanyManageUsergroupViewPage = "pages/company/myCompanyManageUsergroupViewPage";
				String MyCompanyManageUsergroupEditPage = "pages/company/myCompanyManageUsergroupEditPage";
				String MyCompanyManageUsergroupCreatePage = "pages/company/myCompanyManageUsergroupCreatePage";
				String MyCompanyManageUsergroupDisableConfirmationPage = "pages/company/myCompanyManageUsergroupDisableConfirmationPage";
				String MyCompanyManagePermissionDisablePage = "pages/company/myCompanyManagePermissionDisablePage";
				String MyCompanyManagePermissionsViewPage = "pages/company/myCompanyManagePermissionsViewPage";
				String MyCompanyManagePermissionsEditPage = "pages/company/myCompanyManagePermissionsEditPage";
				String MyCompanyManagePermissionTypeSelectPage = "pages/company/myCompanyManagePermissionTypeSelectPage";
				String MyCompanyManagePermissionAddPage = "pages/company/myCompanyManagePermissionAddPage";
				String MyCompanyManageUserCustomersPage = "pages/company/myCompanyManageUserCustomersPage";
				String MyCompanyManageUserGroupPermissionsPage = "pages/company/myCompanyManageUserGroupPermissionsPage";
				String MyCompanyManageUserGroupMembersPage = "pages/company/myCompanyManageUserGroupMembersPage";
				String MyCompanyRemoveDisableConfirmationPage = "pages/company/myCompanyRemoveDisableConfirmationPage";
				String MyCompanyManageUserB2BUserGroupsPage = "pages/company/myCompanyManageUserB2BUserGroupsPage";
				String MyCompanyManageUsergroupRemoveConfirmationPage = "pages/company/myCompanyManageUsergroupRemoveConfirmationPage";


			}

			interface AboutUs
			{
				String CompanyLanding = "pages/company/fmCompanyLandingPage";
				String NewsLanding = "pages/company/fmNewsLandingPage";
				String CareersLanding = "pages/company/fmCareersLandingPage";
				String InvestorsLanding = "pages/company/fmInvestorsLandingPage";
				String SuppliersLanding = "pages/company/fmSuppliersLandingPage";
				String PrivacyLegalLanding = "pages/company/fmPrivacyLegalLandingPage";
				String ExecutiveLanding = "pages/company/fmExecutiveLandingPage";
				String MediaLanding = "pages/company/fmMediaPage";
				//String SupportPage = "pages/company/fmSupportPage";
			}

			interface Support
			{
				String ContactUsPage = "pages/company/fmContactUsPage";
				String TechnicalLinePage = "pages/company/fmTechnicalLinePage";
				String CustomerServicePage = "pages/company/fmCustomerServicePage";
			}



			interface Brand
			{
				String BrandLanding = "pages/category/fmBrandLandingPage";
				String AbexLanding = "pages/category/fmAbexLandingPage";
				String AncoLanding = "pages/category/fmAncoLandingPage";
				String FelproLanding = "pages/category/fmFelproLandingPage";
				String FPDLanding = "pages/category/fmFPDLandingPage";
				String MoogLanding = "pages/category/fmMoogLandingPage";
				String SealedLanding = "pages/category/fmSealedPRLandingPage";
				String SpeedproLanding = "pages/category/fmSpeedproLandingPage";
				String WagnerBPLanding = "pages/category/fmWagnerBPLandingPage";
				String WagnerLightingLanding = "pages/category/fmWagnerLightingLandingPage";
				String NationalLanding = "pages/category/fmNationalLandingPage";
				String FerodoLanding = "pages/category/fmFerodoLandingPage";
				String BeckArnleyLanding = "pages/category/fmBeckArnleyLandingPage";
			}

			interface Learning
			{
				String LearningCenter = "pages/category/fmLearningCenterPage";
				String TrainingOptions = "pages/learningCenter/fmTrainingOptionsPage";
				String Courses = "pages/learningCenter/fmCoursesPage";
				String TechTips = "pages/learningCenter/fmTechTipsPage";
				String VideoGallery = "pages/learningCenter/fmVideoGalleryPage";
				String DiagnosticCenter = "pages/learningCenter/fmDiagnosticCenterPage";
				String Bulletins = "pages/learningCenter/fmBulletinsPage";
				String Catalogs = "pages/learningCenter/fmCatalogsPage";
				String SpecLookup = "pages/learningCenter/fmSpecLookUpPage";
				String Calculators = "pages/learningCenter/fmCalculatorsPage";
			}


			interface Product
			{
				String OrderForm = "pages/product/productOrderFormPage";
			}

			interface StyleGuide
			{
				String Base = "pages/styleGuide/base";
				String Components = "pages/styleGuide/components";
				String Home = "pages/styleGuide/home";
				String Layout = "pages/styleGuide/layout";
				String Plugins = "pages/styleGuide/plugins";
			}

		}

		interface Fragments
		{
			interface Cart
			{
				//String AddToCartPopup = "fragments/cart/addToCartPopup";
				String LoyaltyAddToCartPopup = "redirect:/loyaltycart";
				String AddToCartPopup = "redirect:/cart";
				String MiniCartPanel = "fragments/cart/miniCartPanel";
				String MiniCartErrorPanel = "fragments/cart/miniCartErrorPanel";
				String CartPopup = "fragments/cart/cartPopup";
				String ExpandGridInCart = "fragments/cart/expandGridInCart";
			}

			interface Gatp
			{

				String GatpPopup = "fragments/cart/gatpPopup";

			}

			interface Checkout
			{
				String TermsAndConditionsPopup = "fragments/checkout/termsAndConditionsPopup";
			}

			interface SingleStepCheckout
			{
				String DeliveryAddressFormPopup = "fragments/checkout/single/deliveryAddressFormPopup";
				String PaymentDetailsFormPopup = "fragments/checkout/single/paymentDetailsFormPopup";
			}

			interface Password
			{
				String PasswordResetRequestPopup = "fragments/password/passwordResetRequestPopup";
				String ForgotPasswordValidationMessage = "fragments/password/forgotPasswordValidationMessage";
			}

			interface Product
			{
				String FutureStockPopup = "fragments/product/futureStockPopup";
				String QuickViewPopup = "fragments/product/quickViewPopup";
				String ZoomImagesPopup = "fragments/product/zoomImagesPopup";
				String ReviewsTab = "fragments/product/reviewsTab";
				String ProductLister = "fragments/product/productLister";
			}
		}
	}
}

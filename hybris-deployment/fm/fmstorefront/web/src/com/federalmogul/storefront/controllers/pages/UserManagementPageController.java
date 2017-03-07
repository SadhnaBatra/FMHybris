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
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2bacceleratorfacades.company.data.UserData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BPermissionData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BSelectionData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUserGroupData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.event.FMB2BBRegistrationApprovalProcessEvent;
import com.federalmogul.core.model.FMB2BBRegistrationApprovalProcessModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.B2BCustomerForm;
import com.federalmogul.storefront.forms.B2BPermissionForm;
import com.federalmogul.storefront.forms.CustomerResetPasswordForm;


/**
 * Controller defines routes to manage Users within My Company section.
 */
@Controller
@Scope("tenant")
@RequestMapping("/my-company/organization-management/manage-users")
public class UserManagementPageController extends MyCompanyPageController
{
	private static final Logger LOG = Logger.getLogger(UserManagementPageController.class);



	@Autowired
	private UserService userService;

	/*
	 * @Autowired private CustomerAccountService customerAccountService;
	 */

	@Autowired
	private EventService eventService;

	//@Autowired
	//private FMCustomerServiceImpl fmCustomerServiceImpl;

	@Autowired
	private FMNetworkFacade fmNetworkFacade;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;


	private static final String PDF_EXCEL_SEARCHBY = "{searchBy:.*}";

	private static final String PDF_EXCEL_SEARCHBY_ACCOUNT = "{searchByAccount:.*}";

	@Autowired
	private BaseSiteService baseSiteService;

	@Autowired
	private CommonI18NService commonI18NService;

	@Autowired
	private BaseStoreService baseStoreService;

	@Autowired
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;


	@RequestMapping(method = RequestMethod.GET)
	@RequireHardLogIn
	public String manageUsers(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "noOfRecords", defaultValue = "9") final int noOfRecords,
			@RequestParam(value = "sort", defaultValue = "ASC") final String sortCode,
			@RequestParam(value = "searchBy", defaultValue = "") final String searchBy, final Model model)
			throws CMSItemNotFoundException
	{
		// Handle paged search results
		LOG.info("noOfRecords :: " + noOfRecords);
		LOG.info("sortCode :: " + sortCode);
		LOG.info("searchBy :: " + searchBy);

		int begin = 0;
		int end = 0;
		final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);

		LOG.info("SortCode from  pageableData ::: " + pageableData.getSort());
		SearchPageData<CustomerData> searchPageData = null;

		//	final CustomerData customerData = customerFacade.getCurrentCustomer();
		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();

		if (searchBy != null && !searchBy.isEmpty())
		{
			LOG.info("searchBy :: ###### 1111 ");

			//searchPageData = b2bCommerceUserFacade.getPagedCustomersByName(pageableData, searchBy);
			searchPageData = fmNetworkFacade.getAllFMUsersByName(pageableData, searchBy);
			if (searchPageData != null)
			{
				populateModel(model, searchPageData, showMode);
			}
			LOG.info("Executed in IF :: ");
		}
		else
		{
			LOG.info("ELSE :: ###### 2222 ");
			if (fmCustomerData.getUserTypeDescription() != null)
			{
				if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
						|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN)
						|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.RETAILER)
						|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE)
						|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE)

				)
				{
					LOG.info("INSIDEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE OIIIIIIIIIIIFFFFFFFFFFF");
					LOG.info("UNIT" + fmCustomerData.getFmunit().getUid());
					searchPageData = fmNetworkFacade.getUsersforB2bAdmin(pageableData, fmCustomerData.getFmunit().getUid());
					//final List<CustomerData> CustomerList = (List<CustomerData>) fmCustomerData.getUnit().getCustomers();
					/*
					 * LOG.info("SIZE B4" + customerData.getUnit().getCustomers().size()); final List<CustomerData>
					 * CustomerList = (List<CustomerData>) customerData.getUnit().getCustomers(); LOG.info("SIZE" +
					 * customerData.getUnit().getCustomers().size()); final Iterator i = CustomerList.iterator(); while
					 * (i.hasNext()) { final CustomerData customer = (CustomerData) i.next(); LOG.info("CUSTOMERS" +
					 * customer.getUid()); CustomerList.add(customer);
					 * 
					 * } searchPageData.setResults(CustomerList);
					 */

					//searchPageData = fmNetworkFacade.getAllFMUsers(pageableData);
					//searchPageData.setResults(results);
					//	fmNetworkFacade.getShiptosForAdminiSession();

				}
				else
				{
					LOG.info("INSIDEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE ELSEEEEEEEEEEEEEEEEEEEEEEEEEEE");
					searchPageData = fmNetworkFacade.getAllFMUsers(pageableData);

				}
			}
			else
			{
				LOG.info("INSIDEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE ELSEEEEEEEEEEEEEEEEEEEEEEEEEEE");
				searchPageData = fmNetworkFacade.getAllFMUsers(pageableData);
			}
			if (searchPageData != null)
			{
				populateModel(model, searchPageData, showMode);
			}
			LOG.info("Executed in else :: ");
		}


		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/profile", "My Account", null));
		breadcrumbs.add(new Breadcrumb("#", "Manage Users", null));

		model.addAttribute("breadcrumbs", breadcrumbs);
		/*
		 * final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.getBreadcrumbs(null); breadcrumbs.add(new
		 * Breadcrumb("/my-company/organization-management/", getMessageSource().getMessage(
		 * "text.company.organizationManagement", null, getI18nService().getCurrentLocale()), null)); breadcrumbs.add(new
		 * Breadcrumb("/my-company/organization-management/manage-users", getMessageSource().getMessage(
		 * "text.company.manageUsers", null, getI18nService().getCurrentLocale()), null));
		 */

		model.addAttribute("ProductCountPerPage", noOfRecords);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("searchBy", searchBy);
		model.addAttribute("clickedlink", "manageusers");

		if (page == 0)
		{
			begin = 0;
			end = noOfRecords;
		}
		else
		{
			begin = ((page + 1) * noOfRecords) - noOfRecords;
			end = (page + 1) * noOfRecords;
		}

		LOG.info("Begin :: " + begin + " End :: " + end);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		model.addAttribute("sortCode", sortCode);
		return ControllerConstants.Views.Pages.MyCompany.MyNetworkManageUsersPage;
	}





	@RequestMapping(value = "/csrAccountSearch", method = RequestMethod.GET)
	public String csrAccountSearch(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "noOfRecords", defaultValue = "9") final int noOfRecords,
			@RequestParam(value = "sort", defaultValue = "ASC") final String sortCode,
			@RequestParam(value = "searchByAccount", defaultValue = "") String searchByAccount, final Model model)
			throws CMSItemNotFoundException
	{
		// Handle paged search results
		LOG.info("noOfRecords :: " + noOfRecords);
		LOG.info("sortCode :: " + sortCode);
		LOG.info("searchByAccount :: " + searchByAccount);
		final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);

		LOG.info("SortCode from  pageableData ::: " + pageableData.getSort());
		SearchPageData<CustomerData> searchPageData = null;



		if (searchByAccount != null && !searchByAccount.isEmpty())
		{

			LOG.info("searchBy :: ###### 1111 ");

			//searchPageData = b2bCommerceUserFacade.getPagedCustomersByName(pageableData, searchBy);
			LOG.info("searchby account" + searchByAccount);

			if (searchByAccount.length() < 10)
			{
				searchByAccount = ("0000000000" + searchByAccount).substring(searchByAccount.length());
				LOG.info("!!!!!!!!!!!!!accountId after formatting::" + searchByAccount);
			}
			searchPageData = fmNetworkFacade.getUsersforB2bAdmin(pageableData, searchByAccount);
			if (searchPageData != null)
			{
				populateModel(model, searchPageData, showMode);
			}
		}

		int begin = 0;
		int end = 0;
		if (page == 0)
		{
			begin = 0;
			end = noOfRecords;
		}
		else
		{
			begin = ((page + 1) * noOfRecords) - noOfRecords;
			end = (page + 1) * noOfRecords;
		}

		LOG.info("Begin :: " + begin + " End :: " + end);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);


		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users", getMessageSource().getMessage(
				"text.company.manageUsers", null, getI18nService().getCurrentLocale()), null));
		/*
		 * final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.getBreadcrumbs(null); breadcrumbs.add(new
		 * Breadcrumb("/my-company/organization-management/", getMessageSource().getMessage(
		 * "text.company.organizationManagement", null, getI18nService().getCurrentLocale()), null)); breadcrumbs.add(new
		 * Breadcrumb("/my-company/organization-management/manage-users", getMessageSource().getMessage(
		 * "text.company.manageUsers", null, getI18nService().getCurrentLocale()), null));
		 */
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("ProductCountPerPage", noOfRecords);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("searchByAccount", searchByAccount);
		return ControllerConstants.Views.Pages.MyCompany.MyNetworkManageUsersPage;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/downloadExcel/" + PDF_EXCEL_SEARCHBY, method = RequestMethod.GET)
	public ModelAndView downloadExcel(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "noOfRecords", defaultValue = "9") final int noOfRecords,
			@RequestParam(value = "sort", defaultValue = "ASC") final String sortCode,
			@PathVariable("searchBy") final String searchBy) throws CMSItemNotFoundException
	{
		final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);
		LOG.info("SERACHBY" + searchBy);
		LOG.info("SortCode from  pageableData ::: " + pageableData.getSort());
		SearchPageData<CustomerData> searchPageData = null;

		//if (searchBy != null && !searchBy.equals("all") && !searchBy.isEmpty())
		if (null != searchBy && !"all".equals(searchBy) && !searchBy.isEmpty())
		{
			LOG.info("searchBy :: ###### 1111 ");

			//searchPageData = b2bCommerceUserFacade.getPagedCustomersByName(pageableData, searchBy);
			searchPageData = fmNetworkFacade.getAllFMUsersByName(pageableData, searchBy);

			//	populateModel(model, searchPageData, showMode);
			LOG.info("Executed in IF :: ");
		}
		else if ("all".equals(searchBy))
		{
			searchPageData = fmNetworkFacade.getAllFMUsers(pageableData);
			LOG.info("Executed in ELSE :: ");
		}
		// create some sample data
		final List<CustomerData> customersData = searchPageData.getResults();
		/*
		 * listBooks.add(new Book("Effective Java", "Joshua Bloch", "0321356683", "May 28, 2008", 38.11F));
		 * listBooks.add(new Book("Head First Java", "Kathy Sierra & Bert Bates", "0596009208", "February 9, 2005",
		 * 30.80F)); listBooks.add(new Book("Java Generics and Collections", "Philip Wadler", "0596527756",
		 * "Oct 24, 2006", 29.52F)); listBooks.add(new Book("Thinking in Java", "Bruce Eckel", "0596527756",
		 * "February 20, 2006", 43.97F)); listBooks.add(new Book("Spring in Action", "Craig Walls", "1935182358",
		 * "June 29, 2011", 31.98F));
		 */
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("excelView", "customersList", customersData);
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/downloadPdf/" + PDF_EXCEL_SEARCHBY, method = RequestMethod.GET)
	public ModelAndView downloadPdf(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "noOfRecords", defaultValue = "9") final int noOfRecords,
			@RequestParam(value = "sort", defaultValue = "ASC") final String sortCode,
			@PathVariable(value = "searchBy") final String searchBy) throws CMSItemNotFoundException
	{
		final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);
		LOG.info("SERACHBY" + searchBy);
		LOG.info("SortCode from  pageableData ::: " + pageableData.getSort());
		SearchPageData<CustomerData> searchPageData = null;

		if (null != searchBy && !"all".equals(searchBy) && !searchBy.isEmpty())
		//if (searchBy != null && !searchBy.equals("all") && !searchBy.isEmpty())	
		{
			LOG.info("searchBy :: ###### 1111 ");

			//searchPageData = b2bCommerceUserFacade.getPagedCustomersByName(pageableData, searchBy);
			searchPageData = fmNetworkFacade.getAllFMUsersByName(pageableData, searchBy);

			//	populateModel(model, searchPageData, showMode);
			LOG.info("Executed in IF :: ");
		}
		else if ("all".equals(searchBy))
		{
			searchPageData = fmNetworkFacade.getAllFMUsers(pageableData);
		}
		// create some sample data
		final List<CustomerData> customersData = searchPageData.getResults();
		/*
		 * listBooks.add(new Book("Effective Java", "Joshua Bloch", "0321356683", "May 28, 2008", 38.11F));
		 * listBooks.add(new Book("Head First Java", "Kathy Sierra & Bert Bates", "0596009208", "February 9, 2005",
		 * 30.80F)); listBooks.add(new Book("Java Generics and Collections", "Philip Wadler", "0596527756",
		 * "Oct 24, 2006", 29.52F)); listBooks.add(new Book("Thinking in Java", "Bruce Eckel", "0596527756",
		 * "February 20, 2006", 43.97F)); listBooks.add(new Book("Spring in Action", "Craig Walls", "1935182358",
		 * "June 29, 2011", 31.98F));
		 */
		// return a view which will be resolved by an excel view resolver
		return new ModelAndView("pdfView", "customersList", customersData);
	}




	@SuppressWarnings("null")
	@RequestMapping(value = "/downloadExcelByAccount/" + PDF_EXCEL_SEARCHBY_ACCOUNT, method = RequestMethod.GET)
	public ModelAndView downloadExcelByAccount(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "noOfRecords", defaultValue = "9") final int noOfRecords,
			@RequestParam(value = "sort", defaultValue = "ASC") final String sortCode,
			@PathVariable("searchByAccount") final String searchByAccount) throws CMSItemNotFoundException
	{
		final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);
		LOG.info("searchByAccount" + searchByAccount);
		LOG.info("SortCode from  pageableData ::: " + pageableData.getSort());
		SearchPageData<CustomerData> searchPageData = null;

		if (searchByAccount != null && !searchByAccount.isEmpty())
		{
			LOG.info("searchBy :: ###### 1111 ");
			searchPageData = fmNetworkFacade.getUsersforB2bAdmin(pageableData, searchByAccount);
			LOG.info("Executed in IF :: ");
		}


		final List<CustomerData> customersData = searchPageData.getResults();

		return new ModelAndView("excelView", "customersList", customersData);
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/downloadPdfByAccount/" + PDF_EXCEL_SEARCHBY_ACCOUNT, method = RequestMethod.GET)
	public ModelAndView downloadPdfByAccount(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "noOfRecords", defaultValue = "9") final int noOfRecords,
			@RequestParam(value = "sort", defaultValue = "ASC") final String sortCode,
			@PathVariable(value = "searchByAccount") final String searchByAccount) throws CMSItemNotFoundException
	{
		final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);
		LOG.info("searchByAccount" + searchByAccount);
		LOG.info("SortCode from  pageableData ::: " + pageableData.getSort());
		SearchPageData<CustomerData> searchPageData = null;

		if (searchByAccount != null && !searchByAccount.isEmpty())
		{
			LOG.info("searchByAccount :: ###### 1111 ");


			searchPageData = fmNetworkFacade.getUsersforB2bAdmin(pageableData, searchByAccount);

			LOG.info("Executed in IF :: ");
		}

		final List<CustomerData> customersData = searchPageData.getResults();
		return new ModelAndView("pdfView", "customersList", customersData);
	}


	@Override
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	@RequireHardLogIn
	public String manageUserDetail(@RequestParam("user") final String user, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		model.addAttribute("action", "manageUsers");
		return super.manageUserDetail(user, model, request);
	}

	@Override
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editUser(@RequestParam("user") final String user, final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("action", "manageUsers");



		LOG.info("in edit get user>>>>" + user);
		return super.editUser(user, model);
	}

	@RequestMapping(value = "/edit-approver", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editUsersApprover(@RequestParam("user") final String user, @RequestParam("approver") final String approver,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{



		LOG.info("in edit editusersapprover user>>>>" + user);

		model.addAttribute("cancelUrl", String.format("%s/my-company/organization-management/manage-users/details?user=%s",
				request.getContextPath(), urlEncode(user)));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-approver?user=%s&approver=%s",
						request.getContextPath(), urlEncode(user), urlEncode(approver)));
		final String editUserUrl = super.editUser(approver, model);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-units/edit-approver?user=%s&approver=%s", urlEncode(user),
				urlEncode(approver)), getMessageSource().getMessage("text.company.manageusers.edit", new Object[]
		{ approver }, "Edit {0} User", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		return editUserUrl;
	}

	@RequestMapping(value = "/edit-approver", method = RequestMethod.POST)
	@RequireHardLogIn
	public String editUsersApprover(@RequestParam("user") final String user, @RequestParam("approver") final String approver,
			@Valid final B2BCustomerForm b2BCustomerForm, final BindingResult bindingResult, final Model model,
			final HttpServletRequest request, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		model.addAttribute("cancelUrl", String.format("%s/my-company/organization-management/manage-users/details?user=%s",
				request.getContextPath(), urlEncode(user)));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-approver?user=%s&approver=%s",
						request.getContextPath(), urlEncode(user), urlEncode(approver)));

		final String editUserUrl = super.editUser(user, b2BCustomerForm, bindingResult, model, redirectModel);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-units/edit-approver?user=%s&approver=%s", urlEncode(user),
				urlEncode(approver)), getMessageSource().getMessage("text.company.manageusers.edit", new Object[]
		{ approver }, "Edit {0} User", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		if (bindingResult.hasErrors() || GlobalMessages.hasErrorMessage(model))
		{
			return editUserUrl;
		}
		else
		{
			return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
		}
	}

	@RequestMapping(value = "/approvers/remove", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String removeApproverFromCustomer(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		b2bCommerceUserFacade.removeApproverFromCustomer(user, approver);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.approver.removed");
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
	}

	@Override
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@RequireHardLogIn
	public String editUser(@RequestParam("user") final String user, @Valid final B2BCustomerForm b2BCustomerForm,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{




		LOG.info("in edit post user>>>>" + user);
		return super.editUser(user, b2BCustomerForm, bindingResult, model, redirectModel);
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	@RequireHardLogIn
	public String createUser(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("action", "manageUsers");
		return super.createUser(model);
	}

	@Override
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@RequireHardLogIn
	public String createUser(@Valid final B2BCustomerForm b2BCustomerForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		model.addAttribute("action", "manageUsers");
		return super.createUser(b2BCustomerForm, bindingResult, model, redirectModel);
	}

	@RequestMapping(value = "/disable", method = RequestMethod.GET)
	@RequireHardLogIn
	public String disableUserConfirmation(@RequestParam("user") final String user, final Model model)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUnitsDetailsBreadcrumbs(user);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/disable?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manage.units.disable.breadcrumb", new Object[]
		{ user }, "Disable {0} Customer", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		LOG.info("before user>>>>" + user);

		final CustomerData customerData = companyB2BCommerceFacade.getCustomerDataForUid(user);
		LOG.info("after user>>>>" + user);
		LOG.info("customerdata>>>>" + customerData);

		model.addAttribute("customerData", customerData);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserDisbaleConfirmPage;
	}

	@RequestMapping(value = "/disable", method = RequestMethod.POST)
	@RequireHardLogIn
	public String disableUser(@RequestParam("user") final String user, final Model model, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException, DuplicateUidException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUnitsDetailsBreadcrumbs(user);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/disable?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manageusers.disable.breadcrumb", new Object[]
		{ user }, "Disable {0}  Customer ", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		b2bCommerceUserFacade.disableCustomer(user);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.disable");

		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
	}

	@RequestMapping(value = "/enable", method = RequestMethod.POST)
	@RequireHardLogIn
	public String enableUser(@RequestParam("user") final String user,
			@RequestParam(value = "namesearch", required = false) final String searchname,
			@RequestParam(value = "acountsearch", required = false) final String accountsearch,
			final RedirectAttributes redirectModel, final HttpServletRequest request, final Model model)
			throws CMSItemNotFoundException, DuplicateUidException	{
		
		LOG.debug("searchBy" + request.getParameter("namesearch"));
		LOG.debug("searchByaccount" + request.getParameter("acountsearch"));
		final UserModel currentUserModel = userService.getCurrentUser();

		//getSessionService().setAttribute(SessionContext.USER, userService.getAdminUser());


		fmNetworkFacade.enableCustomer(user);

		//	b2bCommerceUserFacade.enableCustomer(user);
		//getSessionService().setAttribute(SessionContext.USER, userService.getAdminUser());

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.enable");


		//getSessionService().setAttribute(SessionContext.USER, userService.getAdminUser());



		final FMCustomerModel fmCustomerModel = (FMCustomerModel) userService.getUserForUID(user);

		//getSessionService().setAttribute(SessionContext.USER, currentUserModel);
		/************* email publishing *********/
		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users", getMessageSource().getMessage(
				"text.company.manageUsers", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		try
		{





			final FMB2BBRegistrationApprovalProcessModel fmB2BBRegistrationApprovalProcessModel = new FMB2BBRegistrationApprovalProcessModel();

			fmB2BBRegistrationApprovalProcessModel.setEmailId(fmCustomerModel.getUid());
			fmB2BBRegistrationApprovalProcessModel.setEndMessage("Hi");
			fmB2BBRegistrationApprovalProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
			fmB2BBRegistrationApprovalProcessModel.setStore(getBaseStoreService().getCurrentBaseStore());
			fmB2BBRegistrationApprovalProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());


			fmB2BBRegistrationApprovalProcessModel.setCustomer(fmCustomerModel);


			final FMB2BBRegistrationApprovalProcessEvent fmB2BBRegistrationApprovalProcessEvent = new FMB2BBRegistrationApprovalProcessEvent(
					fmB2BBRegistrationApprovalProcessModel);

			eventService.publishEvent(initializeEvent(fmB2BBRegistrationApprovalProcessEvent, fmCustomerModel));



			/************* end of email publishing ************/
		}
		catch (final Exception ex)
		{
			LOG.error("Exception ...." + ex.getMessage());
		}

		if ((searchname == null) || (accountsearch == null))
		{
			return String.format(REDIRECT_TO_MANAGE_USER, urlEncode(user));
		}
		else if (searchname != "" && "true".equalsIgnoreCase(accountsearch))

		{
			LOG.debug("request for by name" + REDIRECT_TO_MANAGE_USER + ("?searchBy=") + (searchname) + ("#").toString());
			return (REDIRECT_TO_MANAGE_USER + ("?searchBy=") + (searchname) + ("#")).toString();
		}
		else if (searchname != "" && "false".equalsIgnoreCase(accountsearch))
		{
			LOG.debug("request for account" + REDIRECT_TO_MANAGE_USER + ("/csrAccountSearch") + ("?searchByAccount=") + (searchname));
			return (REDIRECT_TO_MANAGE_USER + ("/csrAccountSearch") + ("?searchByAccount=") + (searchname)).toString();

		}
		return String.format(REDIRECT_TO_MANAGE_USER, urlEncode(user));
	}
	@RequestMapping(value = "/resetpassword", method = RequestMethod.GET)
	@RequireHardLogIn
	public String updatePassword(@RequestParam("user") final String user, final Model model) throws CMSItemNotFoundException
	{
		if (!model.containsAttribute("customerResetPasswordForm"))
		{
			final CustomerResetPasswordForm customerResetPasswordForm = new CustomerResetPasswordForm();
			customerResetPasswordForm.setUid(user);
			model.addAttribute("customerResetPasswordForm", customerResetPasswordForm);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/restpassword?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manageusers.restpassword.breadcrumb", new Object[]
		{ user }, "Reset Password {0}  User ", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserResetPasswordPage;
	}

	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updatePassword(@RequestParam("user") final String user,
			@Valid final CustomerResetPasswordForm customerResetPasswordForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute(customerResetPasswordForm);
			GlobalMessages.addErrorMessage(model, "form.global.error");
			return updatePassword(customerResetPasswordForm.getUid(), model);
		}

		if (customerResetPasswordForm.getNewPassword().equals(customerResetPasswordForm.getCheckNewPassword()))
		{

			b2bCommerceUserFacade.resetCustomerPassword(customerResetPasswordForm.getUid(),
					customerResetPasswordForm.getNewPassword());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.password.updated");
		}
		else
		{
			model.addAttribute(customerResetPasswordForm);
			bindingResult.rejectValue("checkNewPassword", "validation.checkPwd.equals", new Object[] {},
					"validation.checkPwd.equals");
			GlobalMessages.addErrorMessage(model, "form.global.error");
			return updatePassword(customerResetPasswordForm.getUid(), model);
		}

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder
				.createManageUnitsDetailsBreadcrumbs(customerResetPasswordForm.getUid());
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/restpassword?user=%s",
				urlEncode(customerResetPasswordForm.getUid())), getMessageSource().getMessage(
				"text.company.manageusers.restpassword.breadcrumb", new Object[]
				{ customerResetPasswordForm.getUid() }, "Reset Password {0}  Customer ", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(customerResetPasswordForm.getUid()));
	}

	@RequestMapping(value = "/approvers", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getPagedApproversForCustomer(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", defaultValue = UserModel.NAME) final String sortCode,
			@RequestParam("user") final String user, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/approvers?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manageUsers.approvers.breadcrumb", new Object[]
		{ user }, "Customer {0} Approvers", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		final B2BUnitData unit = companyB2BCommerceFacade.getUnitForUid(user);
		model.addAttribute("unit", unit);

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, 5, sortCode, showMode);
		final SearchPageData<UserData> searchPageData = b2bCommerceUserFacade.getPagedApproversForCustomer(pageableData, user);
		populateModel(model, searchPageData, showMode);
		model.addAttribute("action", "approvers");
		model.addAttribute("baseUrl", "/my-company/organization-management/manage-users");
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserCustomersPage;
	}

	@ResponseBody
	@RequestMapping(value = "/approvers/select", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData selectApproverForCustomer(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver) throws CMSItemNotFoundException
	{
		return populateDisplayNamesForRoles(b2bCommerceUserFacade.addApproverForCustomer(user, approver));
	}

	@ResponseBody
	@RequestMapping(value = "/approvers/deselect", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData deselectApproverForCustomer(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver) throws CMSItemNotFoundException
	{
		return populateDisplayNamesForRoles(b2bCommerceUserFacade.removeApproverFromCustomer(user, approver));
	}

	@RequestMapping(value = "/permissions", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getPagedPermissionsForCustomer(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", defaultValue = UserModel.NAME) final String sortCode,
			@RequestParam("user") final String user, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/permissions?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manage.units.permissions.breadcrumb", new Object[]
		{ user }, "Customer {0} Permissions", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, 5, sortCode, showMode);
		final SearchPageData<B2BPermissionData> searchPageData = b2bCommerceUserFacade.getPagedPermissionsForCustomer(pageableData,
				user);
		populateModel(model, searchPageData, showMode);
		model.addAttribute("action", "permissions");
		model.addAttribute("baseUrl", "/my-company/organization-management/manage-users");
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserPermissionsPage;
	}

	@ResponseBody
	@RequestMapping(value = "/permissions/select", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData selectPermissionForCustomer(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission) throws CMSItemNotFoundException
	{
		return b2bCommerceUserFacade.addPermissionToCustomer(user, permission);
	}

	@ResponseBody
	@RequestMapping(value = "/permissions/deselect", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData deselectPermissionForCustomer(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission) throws CMSItemNotFoundException
	{
		return b2bCommerceUserFacade.removePermissionFromCustomer(user, permission);
	}

	@RequestMapping(value = "/permissions/remove", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String removeCustomersPermission(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		b2bCommerceUserFacade.removePermissionFromCustomer(user, permission);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.permission.removed");
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
	}

	@RequestMapping(value = "/permissions/confirm/remove", method =
	{ RequestMethod.GET })
	@RequireHardLogIn
	public String confirmRemovePermissionFromUser(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.company.users.remove.permission.confirmation",
				new Object[]
				{ permission }, "Remove Permission {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s, %s", permission, user));
		model.addAttribute("page", "users");
		model.addAttribute("role", "permission");
		model.addAttribute("disableUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/permissions/remove/?user=%s&permission=%s", urlEncode(user),
				urlEncode(permission)));
		model.addAttribute("cancelUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/details?user=%s", urlEncode(user)));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyRemoveDisableConfirmationPage;
	}

	@RequestMapping(value = "/approvers/confirm/remove", method =
	{ RequestMethod.GET })
	@RequireHardLogIn
	public String confirmRemoveApproverFromUser(@RequestParam("user") final String user,
			@RequestParam("approver") final String approver, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(
				String.format("text.company.users.remove.%s.confirmation", B2BConstants.B2BAPPROVERGROUP), new Object[]
				{ approver }, "Remove B2B Approver {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s, %s", approver, user));
		model.addAttribute("page", "users");
		model.addAttribute("role", B2BConstants.B2BAPPROVERGROUP);
		model.addAttribute("disableUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/approvers/remove/?user=%s&approver=%s", urlEncode(user),
				urlEncode(approver)));
		model.addAttribute("cancelUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/details?user=%s", urlEncode(user)));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyRemoveDisableConfirmationPage;
	}

	@RequestMapping(value = "/usergroups", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getPagedB2BUserGroupsForCustomer(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", defaultValue = UserModel.NAME) final String sortCode,
			@RequestParam("user") final String user, final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format("/my-company/organization-management/manage-users/usergroups?user=%s",
				urlEncode(user)), getMessageSource().getMessage("text.company.manageUsers.usergroups.breadcrumb", new Object[]
		{ user }, "Customer {0} User Groups", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		// Handle paged search results
		final PageableData pageableData = createPageableData(page, 5, sortCode, showMode);
		final SearchPageData<B2BUserGroupData> searchPageData = b2bCommerceUserFacade.getPagedB2BUserGroupsForCustomer(
				pageableData, user);
		populateModel(model, searchPageData, showMode);
		model.addAttribute("action", "usergroups");
		model.addAttribute("baseUrl", "/my-company/organization-management/manage-users");
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserB2BUserGroupsPage;
	}

	@ResponseBody
	@RequestMapping(value = "/usergroups/select", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData selectB2BUserGroupForCustomer(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup) throws CMSItemNotFoundException
	{
		return b2bCommerceUserFacade.addB2BUserGroupToCustomer(user, usergroup);
	}

	@ResponseBody
	@RequestMapping(value = "/usergroups/deselect", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public B2BSelectionData deselectB2BUserGroupForCustomer(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup) throws CMSItemNotFoundException
	{
		return b2bCommerceUserFacade.deselectB2BUserGroupFromCustomer(user, usergroup);
	}

	@RequestMapping(value = "/usergroups/confirm/remove", method =
	{ RequestMethod.GET })
	@RequireHardLogIn
	public String confirmRemoveUserGroupFromUser(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.company.users.remove.usergroup.confirmation",
				new Object[]
				{ usergroup }, "Remove User group {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("arguments", String.format("%s, %s", user, usergroup));
		model.addAttribute("page", "users");
		model.addAttribute("role", "usergroup");
		model.addAttribute("disableUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/usergroups/remove/?user=%s&usergroup=%s", urlEncode(user),
				urlEncode(usergroup)));
		model.addAttribute("cancelUrl", String.format(request.getContextPath()
				+ "/my-company/organization-management/manage-users/details?user=%s", urlEncode(user)));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyRemoveDisableConfirmationPage;
	}

	@RequestMapping(value = "/usergroups/remove", method =
	{ RequestMethod.GET, RequestMethod.POST })
	@RequireHardLogIn
	public String removeCustomersUserGroup(@RequestParam("user") final String user,
			@RequestParam("usergroup") final String usergroup, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		try
		{
			b2bCommerceUserFacade.removeB2BUserGroupFromCustomerGroups(user, usergroup);
			GlobalMessages
					.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.usergroup.removed");
		}
		catch (final UnknownIdentifierException e)
		{
			if (LOG.isDebugEnabled())
			{
				LOG.debug("can not remove b2b user '" + user + "' from group '" + usergroup + "' due to, " + e.getMessage(), e);
			}
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "usergroup.notfound");
		}
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
	}

	@RequestMapping(value = "/edit-permission", method = RequestMethod.GET)
	@RequireHardLogIn
	public String editUsersPermission(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException
	{
		model.addAttribute("cancelUrl", String.format("%s/my-company/organization-management/manage-users/details?user=%s",
				request.getContextPath(), urlEncode(user)));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s",
						request.getContextPath(), urlEncode(user), urlEncode(permission)));

		final String editPermissionUrl = editPermission(permission, model);

		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s", urlEncode(user),
				urlEncode(permission)), getMessageSource().getMessage("text.company.manageusers.permission.edit", new Object[]
		{ permission }, "Edit Permission {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		return editPermissionUrl;
	}

	@RequestMapping(value = "/edit-permission", method = RequestMethod.POST)
	@RequireHardLogIn
	public String editUsersPermission(@RequestParam("user") final String user,
			@RequestParam("permission") final String permission, @Valid final B2BPermissionForm b2BPermissionForm,
			final BindingResult bindingResult, final Model model, final HttpServletRequest request,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException, ParseException
	{
		model.addAttribute("cancelUrl", String.format("%s/my-company/organization-management/manage-users/details?user=%s",
				request.getContextPath(), urlEncode(user)));
		model.addAttribute(
				"saveUrl",
				String.format("%s/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s",
						request.getContextPath(), urlEncode(user), urlEncode(permission)));

		final String editPermissionUrl = editPermission(b2BPermissionForm, bindingResult, model, redirectModel);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		breadcrumbs.add(new Breadcrumb(String.format(
				"/my-company/organization-management/manage-users/edit-permission?user=%s&permission=%s", urlEncode(user),
				urlEncode(permission)), getMessageSource().getMessage("text.company.manageusers.permission.edit", new Object[]
		{ permission }, "Edit Permission {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		if (bindingResult.hasErrors())
		{
			return editPermissionUrl;
		}
		else
		{
			return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(user));
		}
	}

	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event, final CustomerModel customerModel)
	{
		event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
		event.setSite(getBaseSiteService().getCurrentBaseSite());
		event.setCustomer(customerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}
}

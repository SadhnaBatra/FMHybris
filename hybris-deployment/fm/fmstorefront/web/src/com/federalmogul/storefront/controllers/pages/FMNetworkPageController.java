/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerPartnerFunctionModel;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.account.FMUserFacade;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.FMRegistrationForm;



/**
 * @author su244261
 * 
 */
@Controller
@Scope("tenant")
public class FMNetworkPageController extends MyCompanyPageController
{
	@Resource(name = "fmNetworkFacade")
	protected FMNetworkFacade fmNetworkFacade;


	@Resource(name = "fmCustomerFacade")
	protected FMCustomerFacade fmCustomerFacade;

	@Resource
	protected FMUserFacade fmUserFacade;

	@Resource
	protected UserService userService;
	@Resource
	protected SessionService sessionService;
	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;
	@Autowired
	private ModelService modelService;

	private static final Logger LOG = Logger.getLogger(FMNetworkPageController.class);

	



	@Override
	@RequestMapping(value = "/my-network", method = RequestMethod.GET)
	@RequireHardLogIn
	public String myCompany(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MY_COMPANY_CMS_PAGE));
		model.addAttribute("breadcrumbs", myCompanyBreadcrumbBuilder.getBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyNetworkHomePage;
	}

	@Override
	@RequestMapping(value = "/my-network/organization-management", method = RequestMethod.GET)
	@RequireHardLogIn
	public String organizationManagement(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		model.addAttribute("breadcrumbs", myCompanyBreadcrumbBuilder.getBreadcrumbs("text.company.organizationManagement"));
		model.addAttribute("unitUid", companyB2BCommerceFacade.getParentUnit().getUid());
		model.addAttribute("userUid", customerFacade.getCurrentCustomer().getUid());
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.MyCompany.MyNetworkHomePage;
	}






	@Override
	@RequestMapping(value = "/my-account/manage-users/create", method = RequestMethod.GET)
	@RequireHardLogIn
	protected String createUser(final Model model) throws CMSItemNotFoundException
	{
              	LOG.info("in mycompanypagecon>>createUser>> start");
		boolean createAllow = false;
		final UserModel currentUserModel = userService.getCurrentUser();
		final List<String> groupUID = new ArrayList<String>();
		final Set<PrincipalGroupModel> groupss = currentUserModel.getGroups();
		for (final PrincipalGroupModel groupModel : groupss)
		{
			final String groupId = groupModel.getUid();
			groupUID.add(groupId);
		}
		if (groupUID.contains("FMCSR"))
		{
			createAllow = true;
		}
		if (!createAllow)
		{
			return REDIRECT_PREFIX + ROOT;
		}
		LOG.info("************Create user GET START**************");
		if (!model.containsAttribute("fmRegistrationForm"))
		{
			final FMRegistrationForm fmCustomerForm = new FMRegistrationForm();
			
			fmCustomerForm.setRoles(Collections.singletonList("b2bcustomergroup"));
			model.addAttribute("fmRegistrationForm", fmCustomerForm);
		}
		model.addAttribute("titleData", getUserFacade().getTitles());

		LOG.info("###----11111" + companyB2BCommerceFacade.getUserGroups());
		if (fmCustomerFacade.getCurrentFMCustomer().getUnit() != null)
		{
			model.addAttribute("parentUnit",
					fmNetworkFacade.getAdminUserUnits(fmCustomerFacade.getCurrentFMCustomer().getUnit().getUid()));
			LOG.info("creat user ship tos size "
					+ fmNetworkFacade.getAdminUserUnits(fmCustomerFacade.getCurrentFMCustomer().getUnit().getUid()).size());
		}
		
		model.addAttribute("roles", populateRolesCheckBoxes(companyB2BCommerceFacade.getUserGroups()));
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/profile", "My Account", null));
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users", "Manage Users", null));
		breadcrumbs.add(new Breadcrumb("#", "Create User", null));


		model.addAttribute("breadcrumbs", breadcrumbs);


		model.addAttribute("fmusertype", Fmusertype.values());
		model.addAttribute("metaRobots", "no-index,no-follow");
		LOG.info("************Create user GET END**************");

		
		return ControllerConstants.Views.Pages.MyCompany.MyNetworkManageUserAddFormPage;
	}


	@RequestMapping(value = "/my-account/manage-users/create/setParentUnit", method = RequestMethod.POST)
	String setParentUnit(@RequestParam(value = "whatdescribes") final String whatdescribes, final Model model)
	{
		LOG.info("Inside Parent Unit Check Deepa:" + whatdescribes);
		if ("CONSUMERDIY".equals(whatdescribes) || "CONSUMERDIFM".equals(whatdescribes))
		{
			model.addAttribute("fmRegistrationForm", new FMRegistrationForm());
			LOG.info("Inside Parent Unit IF LOOP Check Deepa:" + whatdescribes);
			LOG.info("###########################Account Number:::::::: " + Config.getParameter("b2CCustGlobalAccount"));

			model.addAttribute("parentB2CUnit", Config.getParameter("b2CCustGlobalAccount"));
		}

		return "fragments/account/b2cAddNewUserFragment";

	}



	@RequestMapping(value = "/my-account/manage-users/create", method = RequestMethod.POST)
	@RequireHardLogIn
	protected String createUser(final FMRegistrationForm fmRegistrationForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException, UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException
	{


		
		if ((fmRegistrationForm.getUsertypedesc().equals(Fmusertype.CSR))) {
			LOG.info("in csr case" + fmRegistrationForm.getUsertypedesc());
			fmRegistrationForm.setParentB2BUnit("16427113582");
				
			}


		if (!validateform(fmRegistrationForm, model))
		{
			model.addAttribute("titleData", getUserFacade().getTitles());
			if (fmCustomerFacade.getCurrentFMCustomer().getUnit() != null)
			{
				model.addAttribute("parentUnit", fmCustomerFacade.getCurrentFMCustomer().getUnit().getUid());
			}
			
			model.addAttribute("fmusertype", Fmusertype.values());
			model.addAttribute("metaRobots", "no-index,no-follow");
			fmRegistrationForm.setUsertypedesc(fmRegistrationForm.getUsertypedesc());
			fmRegistrationForm.setTitleCode(fmRegistrationForm.getTitleCode());
			fmRegistrationForm.setFirstName(fmRegistrationForm.getFirstName());
			fmRegistrationForm.setLastName(fmRegistrationForm.getLastName());
			fmRegistrationForm.setPassword(fmRegistrationForm.getPassword());
			fmRegistrationForm.setIsLoginDisabled(fmRegistrationForm.getIsLoginDisabled());
			fmRegistrationForm.setWorkContactNo(fmRegistrationForm.getWorkContactNo());
			fmRegistrationForm.setPhoneno(fmRegistrationForm.getPhoneno());
			fmRegistrationForm.setEmail(fmRegistrationForm.getEmail());
			fmRegistrationForm.setParentB2BUnit(fmRegistrationForm.getParentB2BUnit());
			fmRegistrationForm.setFmRole(fmRegistrationForm.getFmRole());
			if (fmRegistrationForm.getUsertypedesc().equals(Fmusertype.JOBBERPARTSTORE)
					|| fmRegistrationForm.getUsertypedesc().equals(Fmusertype.SHOPOWNERTECHNICIAN))
			{
				fmRegistrationForm.setNewProductAlerts(fmRegistrationForm.getNewProductAlerts());
				fmRegistrationForm.setNewsLetterSubscription(fmRegistrationForm.getNewsLetterSubscription());
				fmRegistrationForm.setLoyaltySignup(fmRegistrationForm.getLoyaltySignup());
				fmRegistrationForm.setTechAcademySubscription(fmRegistrationForm.getTechAcademySubscription());
				fmRegistrationForm.setPromotionInfoSubscription(fmRegistrationForm.getPromotionInfoSubscription());
			}

			model.addAttribute("fmRegistrationForm", fmRegistrationForm);
			model.addAttribute("selectedUnit", fmRegistrationForm.getParentB2BUnit());
			model.addAttribute("selectedFMRole", fmRegistrationForm.getFmRole());

			final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
			breadcrumbs.add(new Breadcrumb("/my-fmaccount/profile", "My Account", null));
			breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users", "Manage Users", null));
			breadcrumbs.add(new Breadcrumb("#", "Create User", null));


			model.addAttribute("breadcrumbs", breadcrumbs);



			storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
			return ControllerConstants.Views.Pages.MyCompany.MyNetworkManageUserAddFormPage;


		}


		
		String parentUnitEditForm = fmRegistrationForm.getParentB2BUnit();
		
			if (parentUnitEditForm.length() != 10)
		{

			int acccount =0;
			final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
			final String[] acccodes = codes.split(",");
			for (int i = 0; i < acccodes.length; i++)
			{
				
				if (parentUnitEditForm.contains(acccodes[i]))
				{
					acccount++;
				}
			}
			if(acccount==0){
			final int accnumberSize = 10 - parentUnitEditForm.length();
			for (int i = 0; i < accnumberSize; i++)
			{
				parentUnitEditForm = "0" + parentUnitEditForm;
				LOG.info("inside getfmunits::::::::::::::::::::" + parentUnitEditForm);
			}
		}

		}


		final FMCustomerData adminData = fmCustomerFacade.getCurrentFMCustomer();
		LOG.info("++++++++++++++ADMINdATA+++++++++++++++" + adminData.getUserTypeDescription());
		final FMCustomerData fmCustomerData = new FMCustomerData();

		fmCustomerData.setUid(fmRegistrationForm.getUid());
		fmCustomerData.setTitleCode(fmRegistrationForm.getTitleCode());
		fmCustomerData.setFirstName(fmRegistrationForm.getFirstName());
		fmCustomerData.setLastName(fmRegistrationForm.getLastName());
		fmCustomerData.setEmail(fmRegistrationForm.getEmail());
		fmCustomerData.setDisplayUid(fmRegistrationForm.getEmail());
		fmCustomerData.setUnit(fmNetworkFacade.getUnitForUID(parentUnitEditForm));
		fmCustomerData.setLoyaltySignup(fmRegistrationForm.getLoyaltySignup());
		fmCustomerData.setTechAcademySubscription(fmRegistrationForm.getTechAcademySubscription());
		fmCustomerData.setNewProductAlerts(fmRegistrationForm.getNewProductAlerts());
		fmCustomerData.setPromotionInfoSubscription(fmRegistrationForm.getPromotionInfoSubscription());
		fmCustomerData.setNewsLetterSubscription(fmRegistrationForm.getNewsLetterSubscription());


		if (null == fmRegistrationForm.getFmRole() || fmRegistrationForm.getFmRole().isEmpty())
		{
		LOG.info("INSIDE MAIN IF ROLE");
		LOG.info("EMAIL INSIDE " + fmRegistrationForm.getEmail());
			
		if (fmRegistrationForm.getParentB2BUnit() != null || !fmRegistrationForm.getParentB2BUnit().isEmpty())
			{
					LOG.info("INSIDE GETPARENT NOT NULL" + fmRegistrationForm.getParentB2BUnit());
				final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++)
				{

					if (fmRegistrationForm.getParentB2BUnit().contains(acccodes[i]))
					{
						LOG.info("inside sus");
						final String myRole = "FMBUVOR";
						final Collection<String> fmRoles = new ArrayList<String>();
						fmRoles.add(myRole);
						fmCustomerData.setRoles(fmRoles);
						break;
					}
				}


				if (!fmRegistrationForm.getEmail().isEmpty() && fmRegistrationForm.getEmail().contains("@federalmogul.com")
						&& fmRegistrationForm.getParentB2BUnit().equals(Config.getParameter("federalmogulaccountCode_Internalusers")))
				{
					LOG.info("inside @federal");
					final String myRole = "FMCSR";
					final Collection<String> fmRoles = new ArrayList<String>();
					fmRoles.add(myRole);
					fmCustomerData.setRoles(fmRoles);
				}
			}
		}
		else
		{
			LOG.info("inside else main");
			final String myRole = fmRegistrationForm.getFmRole();
			final Collection<String> fmRoles = new ArrayList<String>();
			fmRoles.add(myRole);
			fmCustomerData.setRoles(fmRoles);
		}
		fmCustomerData.setWorkContactNo(fmRegistrationForm.getWorkContactNo());
		
		fmCustomerData.setPassword(fmRegistrationForm.getPassword());
		fmCustomerData.setIsLoginDisabled(fmRegistrationForm.getIsLoginDisabled());
		if (fmRegistrationForm.getUsertypedesc() != null)
		{
			fmCustomerData.setUserTypeDescription(fmRegistrationForm.getUsertypedesc());
			LOG.info("from data iside not null" + fmCustomerData.getUserTypeDescription());
		}
		else
		{
			if (adminData != null)
			{
				fmCustomerData.setUserTypeDescription(fmCustomerFacade.getCurrentFMCustomer().getUserTypeDescription());
				LOG.info("from data------------" + fmCustomerData.getUserTypeDescription());
			}
		}

		AddressData addressData;

		final FMB2bAddressData fmB2bAddressData = fmCustomerData.getB2baddress();
		if (fmB2bAddressData != null)
		{

			LOG.info("******Create user POST::::address data null********" + fmB2bAddressData.getPostalCode());
		}

		LOG.info("");

		if (fmCustomerData.getDefaultShippingAddress() != null)
		{
			addressData = fmCustomerData.getDefaultShippingAddress();

			LOG.info("******Create user POST::::SHIPMENT ADDRESS is not  null******** phone no from form"
					+ fmRegistrationForm.getPhoneno());

			addressData.setPhone(fmRegistrationForm.getPhoneno().trim().replaceAll("[\\-\\s\\(\\)\\.]", ""));
			

		}
		else
		{
			addressData = new AddressData();
			LOG.info("******Create user POST::::ELSE********" + fmRegistrationForm.getPhoneno());
			addressData.setPhone(fmRegistrationForm.getPhoneno().trim().replaceAll("[\\-\\s\\(\\)\\.]", ""));
			fmCustomerData.setDefaultShippingAddress(addressData);
		}

		model.addAttribute(fmRegistrationForm);
		model.addAttribute("titleData", getUserFacade().getTitles());
		model.addAttribute("roles", populateRolesCheckBoxes(companyB2BCommerceFacade.getUserGroups()));

		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/profile", "My Account", null));
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users", "Manage Users", null));
		breadcrumbs.add(new Breadcrumb("#", "Create User", null));

		model.addAttribute("breadcrumbs", breadcrumbs);

		try
		{
			
			fmNetworkFacade.updateFMUser(fmCustomerData, addressData,true);
			fmCustomerData.setUid(fmRegistrationForm.getEmail().toLowerCase());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.added");
		}
		catch (final DuplicateUidException e)
		{
			bindingResult.rejectValue("email", "text.manageuser.error.email.exists.title");
			GlobalMessages.addErrorMessage(model, "form.global.error");
			model.addAttribute("fmRegistrationForm", fmRegistrationForm);
			return editCustomer(fmRegistrationForm.getUid(), model);

		}

		LOG.info("many>>>>>>>>>>>>>>>>>> argument>>>createuser end");
		return String.format(REDIRECT_TO_MANAGE_USER, urlEncode(fmCustomerData.getUid()));


	}



	@RequestMapping(value = "/my-account/manage-users/searchParentUnits", method = RequestMethod.POST)
	String searchParentUnits(@RequestParam(value = "accvalue") final String accNo, final Model model)
	{
		LOG.info("id is +++++++++++++++++++++++++++++++++++++hhh" + accNo);
		final List<String> unitUids = fmNetworkFacade.getAllFMAccountsByID(accNo);
		model.addAttribute("unitsSearchList", unitUids);

		return "fragments/account/accountSearchFragment";

	}

	@RequestMapping(value = "/my-account/manage-users/edit", method = RequestMethod.POST)
	@RequireHardLogIn
	protected String editUser(final String user, final FMRegistrationForm fmRegistrationForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException,
			UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		LOG.info("in mycompanypagecon>>edituser>>five args start>>");
		
		String parentUnitEditForm = fmRegistrationForm.getParentB2BUnit();
		if (parentUnitEditForm.length() != 10)
		{
			
				int acccount =0;
				final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++)
				{
					
					if (parentUnitEditForm.contains(acccodes[i]))
					{
						acccount++;
					}
				}
				if(acccount==0){
				final int accnumberSize = 10 - parentUnitEditForm.length();
				for (int i = 0; i < accnumberSize; i++)
				{
					parentUnitEditForm = "0" + parentUnitEditForm;
					LOG.info("inside getfmunits::::::::::::::::::::" + parentUnitEditForm);
				}
			}
		}

		if (!validateEditForm(fmRegistrationForm, model))
		{
			model.addAttribute(fmRegistrationForm);
			return editCustomer(fmRegistrationForm.getUid(), model);
		}

		if (fmCustomerFacade.getCurrentFMCustomer().getUid().equals(fmRegistrationForm.getUid()))
		{
			LOG.info("inside edit user post if condition >>>>comparing uid condition>>>STRART");
			final Collection<String> roles = fmRegistrationForm.getRoles() != null ? fmRegistrationForm.getRoles()
					: new ArrayList<String>();
			if (!roles.contains(B2BConstants.B2BADMINGROUP))
			{
				GlobalMessages.addErrorMessage(model, "form.b2bcustomer.adminrole.error");
				roles.add(B2BConstants.B2BADMINGROUP);
				fmRegistrationForm.setRoles(roles);
				model.addAttribute(fmRegistrationForm);
				return editCustomer(fmRegistrationForm.getUid(), model);
			}
			else
			{
				// A session user can't modify their own parent unit.
				LOG.info("inside edit user post ELSE condition >>>>comparing uid condition");

				final B2BUnitData parentUnit = companyB2BCommerceFacade.getParentUnit();

				if (!parentUnit.getUid().equals(parentUnitEditForm))
				{
					GlobalMessages.addErrorMessage(model, "form.b2bcustomer.parentunit.error");
					fmRegistrationForm.setParentB2BUnit(parentUnit.getUid());
					model.addAttribute(fmRegistrationForm);
					return editCustomer(fmRegistrationForm.getUid(), model);
				}
			}
			LOG.info("inside edit user if condition >>>>comparing uid condition>>>end");
		}

			LOG.info("unit id in edit user"+parentUnitEditForm);


		final FMCustomerData fmCustomerData = fmNetworkFacade.getFMCustomerDataForUid(user);

		fmCustomerData.setUid(fmRegistrationForm.getUid());
		fmCustomerData.setTitleCode(null != fmRegistrationForm.getTitleCode() ? fmRegistrationForm.getTitleCode() : fmCustomerData
				.getTitleCode());
		fmCustomerData.setFirstName(fmRegistrationForm.getFirstName());
		fmCustomerData.setLastName(fmRegistrationForm.getLastName());
		fmCustomerData.setEmail(fmRegistrationForm.getEmail());
		fmCustomerData.setDisplayUid(fmRegistrationForm.getEmail());

		final UserModel currentUserModel = userService.getCurrentUser();
		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

		fmCustomerData.setUnit(companyB2BCommerceFacade.getUnitForUid(parentUnitEditForm));

		sessionService.setAttribute(SessionContext.USER, currentUserModel);

		final String myRole = fmRegistrationForm.getFmRole();
		final Collection<String> fmRoles = new ArrayList<String>();
		fmRoles.add(myRole);
		fmCustomerData.setRoles(fmRoles);
		
		if (fmRegistrationForm.getWorkContactNo() == null)
		{
			fmCustomerData.setWorkContactNo(fmRegistrationForm.getPhoneno().trim());
		}
		else
		{
			fmCustomerData.setWorkContactNo(fmRegistrationForm.getWorkContactNo().trim());
		}

		LOG.info("----------------------------Value of toggle button---------------------------" + fmRegistrationForm.isActive());

		
		LOG.info("INSIDE CONTROLLER LOGIN STATUS :" + fmRegistrationForm.getIsLoginDisabled());
		fmCustomerData.setIsLoginDisabled(fmRegistrationForm.getIsLoginDisabled());
		boolean passwordUpdate=false;
		if (!(fmRegistrationForm.getPassword().isEmpty())){
		fmCustomerData.setPassword(fmRegistrationForm.getPassword());
		passwordUpdate=true;
}

		fmCustomerData.setLoyaltySignup(fmRegistrationForm.getLoyaltySignup());
		fmCustomerData.setTechAcademySubscription(fmRegistrationForm.getTechAcademySubscription());
		fmCustomerData.setNewProductAlerts(fmRegistrationForm.getNewProductAlerts());
		fmCustomerData.setPromotionInfoSubscription(fmRegistrationForm.getPromotionInfoSubscription());
		fmCustomerData.setNewsLetterSubscription(fmRegistrationForm.getNewsLetterSubscription());


		

		AddressData addressData;

		final FMB2bAddressData fmB2bAddressData = fmCustomerData.getB2baddress();
		if (fmB2bAddressData != null)
		{

			LOG.info("Customer Address PostalCode ### 444" + fmB2bAddressData.getPostalCode());
		}

		LOG.info("");

		if (fmCustomerData.getDefaultShippingAddress() != null)
		{
			addressData = fmCustomerData.getDefaultShippingAddress();

			LOG.info("******222222222222222222..........11111 if phone no from form" + fmRegistrationForm.getPhoneno());

			addressData.setPhone(fmRegistrationForm.getPhoneno());


		}
		else
		{
			addressData = new AddressData();
			LOG.info("******222222222222222222...........222222 else phone no from form" + fmRegistrationForm.getPhoneno());
			addressData.setPhone(fmRegistrationForm.getPhoneno());
			addressData.setDefaultAddress(true);
			fmCustomerData.setDefaultShippingAddress(addressData);
		}


		LOG.info("Password  in edit-post method !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + fmRegistrationForm.getPassword());
		LOG.info("Roles  in edit-post method !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + fmRegistrationForm.getRoles());
		LOG.info("email in edit-post method !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + fmRegistrationForm.getEmail());

		LOG.info("parentb2bunit name in edit-post method !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + parentUnitEditForm);
		LOG.info("first name in edit-post method !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + fmRegistrationForm.getFirstName());



		model.addAttribute(fmRegistrationForm);
		model.addAttribute("titleData", getUserFacade().getTitles());
		model.addAttribute("roles", populateRolesCheckBoxes(companyB2BCommerceFacade.getUserGroups()));

		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));


		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/profile", "My Account", null));
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users", "Manage Users", null));
		breadcrumbs.add(new Breadcrumb("#", "Edit User", null));

		model.addAttribute("breadcrumbs", breadcrumbs);

		try
		{

			fmNetworkFacade.updateFMUser(fmCustomerData, addressData,passwordUpdate);
			if (fmRegistrationForm.getAlternativeAccountId() != null && !fmRegistrationForm.getAlternativeAccountId().isEmpty())
			{
				if (fmRegistrationForm.getAlternativeAccountId().contains(","))
				{
					final String[] alternativeaccounts = fmRegistrationForm.getAlternativeAccountId().split(",");
					for (int i = 0; i < alternativeaccounts.length; i++)
					{
						final String alternativeAccount = alternativeaccounts[i];
						createAlternativeAccount(alternativeAccount, parentUnitEditForm);

					}
				}
				else
				{
					final String alternativeAccount = fmRegistrationForm.getAlternativeAccountId();
					createAlternativeAccount(alternativeAccount, parentUnitEditForm);
				}

			}
			
			fmCustomerData.setUid(fmRegistrationForm.getEmail().toLowerCase());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.edited");
		}
		catch (final DuplicateUidException e)
		{
			bindingResult.rejectValue("email", "text.manageuser.error.email.exists.title");
			GlobalMessages.addErrorMessage(model, "form.global.error");
			model.addAttribute("fmRegistrationForm", fmRegistrationForm);
			return editCustomer(fmRegistrationForm.getUid(), model);

		}
		LOG.info("in mycompanypagecon>>edituser>>five args end>>");
		//return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(fmCustomerData.getUid()));
		return String.format(REDIRECT_TO_MANAGE_USER, urlEncode(fmCustomerData.getUid()));
	}

	/**
	 * @param fmRegistrationForm
	 * @param model
	 * @return
	 */
	private boolean validateform(final FMRegistrationForm fmregistrationform, final Model model)
	{


		if (fmregistrationform.getTitleCode() == null || fmregistrationform.getFirstName().isEmpty())

		{
			GlobalMessages.addErrorMessage(model, "Please select the title");
			LOG.info("Inside vaildate form :: invalid title");
		}



		if (fmregistrationform.getFirstName() == null || fmregistrationform.getFirstName().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter the Firstname");
			LOG.info("Inside vaildate form :: invalid firstname");

		}
		if (fmregistrationform.getLastName() == null || fmregistrationform.getLastName().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter the Lastname");
			LOG.info("Inside vaildate form :: invalid lastname");

		}

		if (fmregistrationform.getPassword() == null || fmregistrationform.getPassword().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter the password");
			LOG.info("Inside vaildate form :: invalid password");

		}
		
		else
		{
			final String pwd = "^(?=.*?[0-9])(?=.*[A-Z]).{8,}$";

			
			if (!fmregistrationform.getPassword().matches(pwd))
			{
				GlobalMessages.addErrorMessage(model, "text.Password.Validation.Error");
				LOG.info("Inside vaildate form :: invalid password");

			}
		}


		if (fmregistrationform.getWorkContactNo() != null && !fmregistrationform.getWorkContactNo().isEmpty())
		{
			
			if (!(fmregistrationform.getWorkContactNo().matches("\\d{3}-\\d{3}-\\d{4}")
					|| fmregistrationform.getWorkContactNo().matches("\\(\\d{3}\\) \\d{3}-\\d{4}")
					|| fmregistrationform.getWorkContactNo().matches("\\d{10}")
					|| fmregistrationform.getWorkContactNo().matches("\\d{3}.\\d{3}.\\d{4}") || fmregistrationform.getWorkContactNo()
					.matches("\\d{2}-\\d{1}.\\d{1}-\\d{1} \\d{3} \\d{2}")))

			{
				GlobalMessages.addErrorMessage(model,
						"phone number should have the format: 111-111-1111 or (111) 111-1111 or 10digits");
				LOG.info("Inside vaildate form :: invalid  work contact number");
			}
		}

		

		if (fmregistrationform.getEmail() == null || fmregistrationform.getEmail().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter email");
			LOG.info("Inside vaildate form :: invalid email");
		}
		else
		{
			final boolean checkUid = fmCustomerFacade.checkUidExists(fmregistrationform.getEmail());
			if (checkUid)
			{
				GlobalMessages.addErrorMessage(model, "EmailId Already exists!! Register with a different email Id");

			}
		}

				
		String accNo = fmregistrationform.getParentB2BUnit();
		LOG.info("Inside vaildate form for unit:: invalid email" + fmregistrationform.getUsertypedesc());
		if ((fmregistrationform.getUsertypedesc().equals(Fmusertype.CSR))) {
		LOG.info("in csr case" + fmregistrationform.getUsertypedesc());

			accNo = "16427113582";
		}
		LOG.info("in csr case" + accNo);


		if (fmregistrationform.getParentB2BUnit() == null || fmregistrationform.getParentB2BUnit().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter parentB2BUnit");
			LOG.info("Inside vaildate form :: invalid email");
		}
		else
		{
			if (accNo.length() != 10)
			{
				
				int acccount =0;
				final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++)
				{
					
					if (accNo.contains(acccodes[i]))
					{
						acccount++;
					}
				}
				if(acccount==0){
				final int accnumberSize = 10 - accNo.length();
				for (int i = 0; i < accnumberSize; i++)
				{
					accNo = "0" + accNo;
					LOG.info("inside validate form of network page::::::::::::::::::::" + accNo);
				}
			}
			}

			final B2BUnitModel unitB2B = companyB2BCommerceService.getUnitForUid(accNo);
			LOG.info("account number inside else part of valacccode:++++++++++++++++++++++++++++++++++" + accNo);
			if (null == unitB2B)
			{
				
				LOG.info("Inside vaildate form :: invalid account code");
				try
				{
					throw new IOException("Please Enter a valid account code");
				}
				catch (final IOException e)
				{
					LOG.error("IOException ::" + e.getMessage());
					GlobalMessages.addErrorMessage(model, e.getMessage());
				}
			}

		}



		if (GlobalMessages.hasErrorMessage(model))
		{
			LOG.info("Inside vaildate form :: returning failure");
			return false;
		}
		else
		{
			LOG.info("Inside vaildate form :: returning success");
			return true;
		}
	}


	/**
	 * @param fmRegistrationForm
	 * @param model
	 * @return
	 */
	private boolean validateEditForm(final FMRegistrationForm fmregistrationform, final Model model)
	{

		if (fmregistrationform.getFirstName() == null || fmregistrationform.getFirstName().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter the Firstname");
			LOG.info("Inside vaildate form :: invalid firstname");

		}
		if (fmregistrationform.getLastName() == null || fmregistrationform.getLastName().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter the Lastname");
			LOG.info("Inside vaildate form :: invalid lastname");

		}

		if (fmregistrationform.getPassword() == null || fmregistrationform.getPassword().isEmpty())
		{
			
			LOG.info("Inside vaildate form :: invalid password");

		}
		
		else
		{
			final String pwd = "^(?=.*?[0-9])(?=.*[A-Z]).{8,}$";

			
			if (!fmregistrationform.getPassword().matches(pwd))
			{
				GlobalMessages.addErrorMessage(model, "text.Password.Validation.Error");
				LOG.info("Inside vaildate form :: invalid password");

			}
		}


		if (fmregistrationform.getWorkContactNo() == null || fmregistrationform.getWorkContactNo().isEmpty())
		{
	
		LOG.info("Inside vaildate form :: invalid email");
		}

		else
		{

		if (!(fmregistrationform.getWorkContactNo().matches("\\d{3}-\\d{3}-\\d{4}")
				|| fmregistrationform.getWorkContactNo().matches("\\(\\d{3}\\) \\d{3}-\\d{4}")
				|| fmregistrationform.getWorkContactNo().matches("\\d{10}")
				|| fmregistrationform.getWorkContactNo().matches("\\d{3}.\\d{3}.\\d{4}")
				|| fmregistrationform.getWorkContactNo().matches("[(]\\d{3}[)]\\d{3}-\\d{4}") || fmregistrationform
				.getWorkContactNo().matches("\\d{2}-\\d{1}.\\d{1}-\\d{1} \\d{3} \\d{2}")))
		{
			GlobalMessages.addErrorMessage(model, "work contact number should have the format: 111-111-1111 or (111) 111-1111");
			LOG.info("Inside vaildate form :: invalid  work contact number");
		}
		}

		
		if (!(fmregistrationform.getPhoneno().matches("\\d{3}-\\d{3}-\\d{4}")
				|| fmregistrationform.getPhoneno().matches("\\(\\d{3}\\) \\d{3}-\\d{4}")
				|| fmregistrationform.getPhoneno().matches("\\d{10}")
				|| fmregistrationform.getPhoneno().matches("\\d{3}.\\d{3}.\\d{4}") || fmregistrationform.getPhoneno().matches(
				"\\d{2}-\\d{1}.\\d{1}-\\d{1} \\d{3} \\d{2}")))

		{
			GlobalMessages.addErrorMessage(model, "phone number should have the format: 111-111-1111 or (111) 111-1111");
			LOG.info("Inside vaildate form :: invalid  phone number");
		}
		



		if (fmregistrationform.getEmail() == null || fmregistrationform.getEmail().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter email");
			LOG.info("Inside vaildate form :: invalid email");
		}
		String accNo = fmregistrationform.getParentB2BUnit();
		if (fmregistrationform.getParentB2BUnit() == null || fmregistrationform.getParentB2BUnit().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter parentB2BUnit");
			LOG.info("Inside vaildate form :: invalid email");
		}
		else
		{
			if (accNo.length() != 10)
			{
				
				int acccount =0;
				final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++)
				{
					
					if (accNo.contains(acccodes[i]))
					{
						acccount++;
					}
				}
				if(acccount==0){
				final int accnumberSize = 10 - accNo.length();
				for (int i = 0; i < accnumberSize; i++)
				{
					accNo = "0" + accNo;
					LOG.info("inside getfmunits::::::::::::::::::::" + accNo);
				}
			}
			}

			final B2BUnitModel unitB2B = companyB2BCommerceService.getUnitForUid(accNo);
			LOG.info("account number inside else part of valacccode:++++++++++++++++++++++++++++++++++" + accNo);
			if (null == unitB2B)
			{
				//GlobalMessages.addErrorMessage(model, "Please enter the valid account code");
				LOG.info("Inside vaildate form :: invalid account code");
				try
				{
					throw new IOException("Please Enter a valid account code");
				}
				catch (final IOException e)
				{
					LOG.error("IOException ::" + e.getMessage());
					GlobalMessages.addErrorMessage(model, e.getMessage());
				}
			}

		}		

if (GlobalMessages.hasErrorMessage(model))
		{
			LOG.info("Inside vaildate form :: returning failure");
			return false;
		}
		else
		{
			LOG.info("Inside vaildate form :: returning success");
			return true;
		}
	}

	public static class SelectOption
	{
		private final String code;
		private final String name;

		public SelectOption(final String code, final String name)
		{
			this.code = code;
			this.name = name;
		}

		public String getCode()
		{
			return code;
		}

		public String getName()
		{
			return name;
		}
	}




	
@RequestMapping(value = "/my-account/manage-users/editCustomer", method = RequestMethod.POST)
	@RequireHardLogIn
	public String editCustomer(@RequestParam(value = "user", required = true) final String user, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("in editCustomer");
		
		boolean isallow = false;
		LOG.info("in mycompanypagecon>>edituser>>2 args start");
		final FMRegistrationForm fmCustomerForm = new FMRegistrationForm();
		final UserModel currentUserModel = userService.getCurrentUser();
		final List<String> groupUID = new ArrayList<String>();
		final Set<PrincipalGroupModel> groupss = currentUserModel.getGroups();
		for (final PrincipalGroupModel groupModel : groupss)
		{
			final String groupId = groupModel.getUid();
			groupUID.add(groupId);
		}
		if (groupUID.contains("FMCSR"))
		{
			isallow = true;
		}
		if (!isallow)
		{
			return REDIRECT_PREFIX + ROOT;
		}

		if (!model.containsAttribute("fmRegistrationForm"))
		{
			LOG.info("inside edit user if condition >>>>contians b2bcustomerform>>>STRART");
			final FMCustomerData fmCustomerData = fmNetworkFacade.getFMCustomerDataForUid(user);
			fmCustomerForm.setUid(fmCustomerData.getUid());
			fmCustomerForm.setTitleCode(fmCustomerData.getTitleCode());
			LOG.info("First name in controller :: " + fmCustomerData.getFirstName());
			fmCustomerForm.setFirstName(fmCustomerData.getFirstName());
			//fmCustomerForm.setFirstName(fmCustomerData.getName());
			fmCustomerForm.setLastName(fmCustomerData.getLastName());
			fmCustomerForm.setEmail(fmCustomerData.getEmail());
			//fmCustomerForm.setEmail(fmCustomerData.getDisplayUid());
			fmCustomerForm.setParentB2BUnit(fmCustomerData.getFmunit().getUid());
			//fmCustomerForm.setParentB2BUnit(b2bCommerceUserFacade.getParentUnitForCustomer(fmCustomerData.getUid()).getUid());
			//fmCustomerForm.setActive(fmCustomerData.isActive());
			fmCustomerForm.setIsLoginDisabled(fmCustomerData.getIsLoginDisabled());
			fmCustomerForm.setApproverGroups(fmCustomerData.getApproverGroups());
			fmCustomerForm.setApprovers(fmCustomerData.getApprovers());
			fmCustomerForm.setNewProductAlerts(fmCustomerData.getNewProductAlerts());
			fmCustomerForm.setNewsLetterSubscription(fmCustomerData.getNewsLetterSubscription());
			fmCustomerForm.setLoyaltySignup(fmCustomerData.getLoyaltySignup());
			fmCustomerForm.setTechAcademySubscription(fmCustomerData.getTechAcademySubscription());
			fmCustomerForm.setPromotionInfoSubscription(fmCustomerData.getPromotionInfoSubscription());
			LOG.info("%%%%%%%%%%%%%%%%%AAAAAAAAAA getfmCustomerDataroles:::" + fmCustomerData.getRoles());
			if (fmCustomerData.getRoles().contains("FMAdminGroup"))
			{
				LOG.info("%%%%%%%%%%%%%%%%%BBBBBBBBB ::::::::::inside FMAdminGroup");
				fmCustomerForm.setFmRole("FMAdminGroup");
				model.addAttribute("fmRole", "FMAdminGroup");
			}
			else if (fmCustomerData.getRoles().contains("FMFullAccessGroup"))
			{
				LOG.info("%%%%%%%%%%%%%%%%%cccccccc::::::::::inside FMFullAccessGroup");
				fmCustomerForm.setFmRole("FMFullAccessGroup");
				model.addAttribute("fmRole", "FMFullAccessGroup");
			}
			else if (fmCustomerData.getRoles().contains("FMNoInvoiceGroup"))
			{
				LOG.info("%%%%%%%%%%%%%%%%%DDDDDDDDDD::::::::::inside FMNoInvoiceGroup");
				fmCustomerForm.setFmRole("FMNoInvoiceGroup");
				model.addAttribute("fmRole", "FMNoInvoiceGroup");
			}
			else if (fmCustomerData.getRoles().contains("FMViewOnlyGroup"))
			{
				LOG.info("%%%%%%%%%%%%%%%%%EEEEEEEEE::::::::::inside FMViewOnlyGroup");
				fmCustomerForm.setFmRole("FMViewOnlyGroup");
				model.addAttribute("fmRole", "FMViewOnlyGroup");
			}

			else if (fmCustomerData.getRoles().contains("FMBUVOR"))
			{
				LOG.info("%%%%%%%%%%%%%%%%%EEEEEEEEE::::::::::inside FMBUVOGroup");
				fmCustomerForm.setFmRole("FMBUVOR");
				model.addAttribute("fmRole", "FMBUVOR");
			}
			else if (fmCustomerData.getRoles().contains("FMCSR"))
			{
				LOG.info("%%%%%%%%%%%%%%%%%EEEEEEEEE::::::::::inside FMCSR");
				fmCustomerForm.setFmRole("FMCSR");
				model.addAttribute("fmRole", "FMCSR");
			}



			if (fmCustomerData.getFmunit() != null)
			{


				final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++)
				{

					if (fmCustomerData.getFmunit().getUid().contains(acccodes[i]))
					{
						fmCustomerForm.setFmRole("FMBUVOR");
						model.addAttribute("fmRole", "FMBUVORSUS");
						break;
					}
				}

			}
			

			fmCustomerForm.setActive(fmCustomerData.isActive());
			if (fmCustomerData.getWorkContactNo() != null)
			{
				fmCustomerForm.setWorkContactNo(fmCustomerData.getWorkContactNo().trim());
			}
			//fmCustomerForm.setPassword(fmCustomerData.getPassword());
			fmCustomerForm.setUsertypedesc(fmCustomerData.getUserTypeDescription());
			//LOG.info("xcccccccccccccccccccccccccxxxxxxxxxxxx:::::::::::::::::::::::::::" + fmCustomerData.getUserTypeDescription());

			LOG.info("last name::" + fmCustomerData.getLastName() + "firstName::" + fmCustomerData.getFirstName() + "name:"
					+ fmCustomerData.getName() + "work contact:::" + fmCustomerData.getWorkContactNo() + "contact No::"
					+ fmCustomerData.getContactNumber());





			LOG.info("########### 1111111customer roles::::" + fmCustomerData.getRoles());



			if (fmCustomerData.getDefaultShippingAddress() != null)
			{
				if (fmCustomerData.getDefaultShippingAddress().getPhone() != null)
				{
					if (!fmCustomerData.getDefaultShippingAddress().getPhone().isEmpty())
					{
						fmCustomerForm.setPhoneno(fmCustomerData.getDefaultShippingAddress().getPhone().trim());
						LOG.info("*******************1111111111 get phone:::::" + fmCustomerData.getDefaultShippingAddress().getPhone());
					}
				}
			}



			model.addAttribute("fmRegistrationForm", fmCustomerForm);
		}
		
		model.addAttribute("titleData", getUserFacade().getTitles());
		model.addAttribute("roles", populateRolesCheckBoxes(companyB2BCommerceFacade.getUserGroups()));
		model.addAttribute("fmRegistrationForm", fmCustomerForm);
		storeCmsPageInModel(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE));



		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/profile", "My Account", null));
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users", "Manage Users", null));
		breadcrumbs.add(new Breadcrumb("#", "Edit User", null));

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.MyCompany.MyNetworkManageUserEditFormPage;
	}

public void createAlternativeAccount(final String alternativeaccount, final String parentAccount)
{
	final FMCustomerAccountModel sourceAccModel = (FMCustomerAccountModel) companyB2BCommerceService
			.getUnitForUid(parentAccount);
	final List<FMCustomerAccountModel> sourceList = new ArrayList<FMCustomerAccountModel>();
	final FMCustomerAccountModel targetAccModel = (FMCustomerAccountModel) companyB2BCommerceService
			.getUnitForUid(alternativeaccount);
	final List<FMCustomerAccountModel> targetList = new ArrayList<FMCustomerAccountModel>();
	if (sourceAccModel != null)
	{
		sourceList.add(sourceAccModel);
	}
	if (targetAccModel != null)
	{

		targetList.add(targetAccModel);
		final FMCustomerPartnerFunctionModel partnerModel = new FMCustomerPartnerFunctionModel();
		partnerModel.setCode(alternativeaccount + parentAccount);
		partnerModel.setFmCustSourceAccountCode(sourceList);
		partnerModel.setFMTargetCustomerAccount(targetAccModel);
		//partnerModel.setFmCustSourceAccountCode(sourceList);
		partnerModel.setFMSourceCustomerAccount(sourceAccModel);
		if (alternativeaccount.startsWith("002"))
		{
			partnerModel.setPartnerFMAccountType("ShipTo");
		}
		if (alternativeaccount.startsWith("001"))
		{
			partnerModel.setPartnerFMAccountType("sold-to");
		}

		modelService.save(partnerModel);

	}

}




}

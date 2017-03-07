package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.security.SecureToken;
import de.hybris.platform.commerceservices.security.SecureTokenService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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

import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.ForgottenPwdForm;
import com.federalmogul.storefront.forms.UpdatePwdForm;


/**
 * @author su244261
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/password-reset")
public class FMForgotPasswordPageController extends AbstractPageController
{
	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	private static final Logger LOG = Logger.getLogger(FMForgotPasswordPageController.class);

	private static final String FMFORGOT_PASSWORD_CMS_PAGE = "FMForgotPasswordPage";

	private static final String FMFORGOT_PASSWORD_VALIDATION_CMS_PAGE = "FMForgotPasswordValidationPage";

	private static final String FMFORGOT_PASSWORD_RESET_CMS_PAGE = "FMForgotPasswordResetPage";

	private static final String REDIRECT_LOGIN = "redirect:/";

	@Autowired
	SecureTokenService secureTokenService;

	@Resource(name = "fmNetworkFacade")
	protected FMNetworkFacade fmNetworkFacade;


	/*
	 * @Resource(name = "fmNetworkService") private FMNetworkService fmNetworkService;
	 */

	@Resource
	private FMCustomerFacade fmCustomerFacade;


	@RequestMapping(method = RequestMethod.GET)
	public String getFMForgotPassword(final Model model) throws CMSItemNotFoundException
	{


		model.addAttribute(new ForgottenPwdForm());
		model.addAttribute("metaRobots", "no-index,no-follow");

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_CMS_PAGE));

		return ControllerConstants.Views.Pages.Password.FMForgotPasswordPage;



	}

	private boolean validateEmailForm(final ForgottenPwdForm form, final Model model)
	{

		boolean validateEmail = true;
		if (form.getEmail() == null || form.getEmail().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter your email address");


		}

		if (GlobalMessages.hasErrorMessage(model))
		{
			validateEmail = false;
			//return false;
		}
		return validateEmail;
	}

	private boolean validatePasswordForm(final UpdatePwdForm updatePwdForm, final Model model)
	{


		final String pwd = "^(?=.*?[0-9])(?=.*[A-Z]).{8,}$";
		if (updatePwdForm.getPwd() == null || updatePwdForm.getPwd().isEmpty() || updatePwdForm.getCheckPwd() == null
				|| updatePwdForm.getCheckPwd().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Password can not be empty");


		}
		else if (!updatePwdForm.getPwd().equals(updatePwdForm.getCheckPwd()))
		{
			GlobalMessages.addErrorMessage(model, "Password should be same");


		}

		else if (!updatePwdForm.getPwd().matches(pwd) || !updatePwdForm.getCheckPwd().matches(pwd))
		{
			GlobalMessages.addErrorMessage(model, "text.Password.Validation.Error");

			LOG.info("inside if part of fmupdate passworddddddddddddddddddddddddddddddddddd");
		}

		boolean validateError = true;
		if (GlobalMessages.hasErrorMessage(model))
		{
			validateError = false;
			//return false;
		}
		return validateError;
	}


	@RequestMapping(method = RequestMethod.POST)
	public String getFMForgotPassword(@Valid final ForgottenPwdForm form, final BindingResult bindingResult, final Model model)
			throws CMSItemNotFoundException
	{
		if (!validateEmailForm(form, model))
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_CMS_PAGE));
			model.addAttribute(new ForgottenPwdForm());
			//	return ControllerConstants.Views.Pages.Account.SignUpPage;
			return ControllerConstants.Views.Pages.Password.FMForgotPasswordPage;
		}




		/*
		 * if (bindingResult.hasErrors()) {
		 * LOG.info("$$$$$$$$$44444444444444 In request POSTTT Binding has error $$$$$$"); return
		 * ControllerConstants.Views.Pages.Password.FMForgotPasswordPage; } else {
		 */

		try
		{

			customerFacade.forgottenPassword(form.getEmail());
		}
		catch (final UnknownIdentifierException unknownIdentifierException)
		{
			/*
			 * isExist = false; model.addAttribute("isExist", isExist); model.addAttribute(new ForgottenPwdForm());
			 */
			model.addAttribute(new ForgottenPwdForm());
			storeCmsPageInModel(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_CMS_PAGE));
			// fix given for changing customer support number by 279688
			GlobalMessages.addErrorMessage(model, "text.Password.Reset.Error");
			return ControllerConstants.Views.Pages.Password.FMForgotPasswordPage;
		}

		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("userEmailID", form.getEmail());

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_VALIDATION_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_VALIDATION_CMS_PAGE));

		return ControllerConstants.Views.Pages.Password.FMForgotPasswordValidationPage;
	}





	@RequestMapping(value = "/change", method = RequestMethod.GET)
	public String getChangePassword(@RequestParam(required = false) final String token, final Model model)
			throws CMSItemNotFoundException
	{
		//commented till the smtp configured 
		/*
		 * if (StringUtils.isBlank(token)) { return REDIRECT_HOME; }
		 */


		final UpdatePwdForm form = new UpdatePwdForm();
		form.setToken(token);
		final SecureToken decryptedToken = getSecureTokenService().decryptData(token);
		final String decryptedID = decryptedToken.getData();
		LOG.info("!!!!!!!!!!!!!!!!!decryptedID ID::::" + decryptedID);
		String userDomain = null;
		String userName = null;
		String encodedEmailId = null;
		final StringTokenizer strToken = new StringTokenizer(decryptedID, "@");

		userName = strToken.nextElement().toString();
		userDomain = strToken.nextElement().toString();

		final StringBuffer encodedUserName = new StringBuffer();
		encodedUserName.append(userName.charAt(0));
		for (int i = 1; i < userName.length() - 1; i++)
		{
			encodedUserName.append('*');
		}
		encodedUserName.append(userName.charAt(userName.length() - 1));
		encodedEmailId = encodedUserName.toString().concat("@").concat(userDomain);

		model.addAttribute("encodedEmailId", encodedEmailId);

		model.addAttribute(form);

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_RESET_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_RESET_CMS_PAGE));

		return ControllerConstants.Views.Pages.Password.FMForgotPasswordResetPage;
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public String changePassword(@Valid final UpdatePwdForm form, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (!validatePasswordForm(form, model))
		{
			final String token = form.getToken();
			final SecureToken decryptedToken = getSecureTokenService().decryptData(token);
			final String decryptedID = decryptedToken.getData();
			LOG.info("!!!!!!!!!!!!!!!!!decryptedID ID::::" + decryptedID);
			String userDomain = null;
			String userName = null;
			String encodedEmailId = null;
			final StringTokenizer strToken = new StringTokenizer(decryptedID, "@");

			userName = strToken.nextElement().toString();
			userDomain = strToken.nextElement().toString();

			final StringBuffer encodedUserName = new StringBuffer();
			encodedUserName.append(userName.charAt(0));
			for (int i = 1; i < userName.length() - 1; i++)
			{
				encodedUserName.append('*');
			}
			encodedUserName.append(userName.charAt(userName.length() - 1));
			encodedEmailId = encodedUserName.toString().concat("@").concat(userDomain);

			model.addAttribute("encodedEmailId", encodedEmailId);


			storeCmsPageInModel(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_RESET_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMFORGOT_PASSWORD_RESET_CMS_PAGE));
			model.addAttribute(new ForgottenPwdForm());
			//	return ControllerConstants.Views.Pages.Account.SignUpPage;
			return ControllerConstants.Views.Pages.Password.FMForgotPasswordResetPage;
		}




		//binding errors supressed 
		/*
		 * if (bindingResult.hasErrors()) { prepareErrorMessage(model, FMFORGOT_PASSWORD_RESET_CMS_PAGE); return
		 * ControllerConstants.Views.Pages.Password.FMForgotPasswordResetPage; }
		 */
		if (!StringUtils.isBlank(form.getToken()))
		{

			try
			{
				fmCustomerFacade.fmUpdatePassword(form.getToken(), form.getPwd());
				//getCustomerFacade().updatePassword(form.getToken(), form.getPwd());
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
						"account.confirmation.password.updated");
			}
			catch (final TokenInvalidatedException e)
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug("update passwoed failed due to, " + e.getMessage(), e);
				}
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "updatePwd.token.invalidated");
			}
			catch (final RuntimeException e)
			{
				if (LOG.isDebugEnabled())
				{
					LOG.debug("update passwoed failed due to, " + e.getMessage(), e);
				}
				GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, "updatePwd.token.invalid");
			}
		}

		return REDIRECT_LOGIN;
	}

	protected void prepareErrorMessage(final Model model, final String page) throws CMSItemNotFoundException
	{
		GlobalMessages.addErrorMessage(model, "form.global.error");
		storeCmsPageInModel(model, getContentPageForLabelOrId(page));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(page));
	}

	protected SecureTokenService getSecureTokenService()
	{
		return secureTokenService;
	}




}

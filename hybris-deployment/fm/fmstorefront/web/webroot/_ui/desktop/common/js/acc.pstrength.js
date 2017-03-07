ACC.pwdstrength = {

	bindAll: function ()
	{
		this.bindPStrength();
	},

	bindPStrength: function ()
	{
		try{
			$('.strength').pstrength({ verdicts: [ACC.pwdStrengthVeryWeak,
				ACC.pwdStrengthWeak,
				ACC.pwdStrengthMedium,
				ACC.pwdStrengthStrong,
				ACC.pwdStrengthVeryStrong],
				tooShort: ACC.pwdStrengthTooShortPwd,
				minCharText: ACC.pwdStrengthMinCharText });
		}catch(e){}
	}

};

$(document).ready(function ()
{
	ACC.pwdstrength.bindAll();
});

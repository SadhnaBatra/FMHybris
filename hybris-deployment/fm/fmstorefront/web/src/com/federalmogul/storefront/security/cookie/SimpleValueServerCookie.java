package com.federalmogul.storefront.security.cookie;

import java.text.FieldPosition;
import java.util.Date;


/**
 * This code is mostly copied from com.federalmogul.storefront.security.cookie, with custom appendCookieValue and maybeQuote methods. Essentially, this class should be used
 * when the rules for surrounding the cookie value in quotes does not apply.
 */
@SuppressWarnings("PMD")
public abstract class SimpleValueServerCookie {

	public static void appendCookieValue(final StringBuffer headerBuf, final int version, final String name, final String value,
			final String path, final String domain, final String comment, final int maxAge, final boolean isSecure,
			final boolean isHttpOnly) {
		final StringBuffer buf = new StringBuffer();
		// Servlet implementation checks name
		buf.append(name);
		buf.append("=");
		// Servlet implementation does not check anything else

		/*
		 * The spec allows some latitude on when to send the version attribute with a Set-Cookie header. To be nice to
		 * clients, we'll make sure the version attribute is first. That means checking the various things that can cause
		 * us to switch to a v1 cookie first.
		 * 
		 * Note that by checking for tokens we will also throw an exception if a control character is encountered.
		 */
		// Start by using the version we were asked for
		int newVersion = version;

		// If it is v0, check if we need to switch
		if (newVersion == 0
				&& (!ServerCookie.CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 && ServerCookie.CookieSupport.isHttpToken(value) || ServerCookie.CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0
						&& ServerCookie.CookieSupport.isV0Token(value))) {
			// HTTP token in value - need to use v1
			newVersion = 1;
		}

		if (newVersion == 0 && comment != null) {
			// Using a comment makes it a v1 cookie
			newVersion = 1;
		}

		if (newVersion == 0
				&& (!ServerCookie.CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 && ServerCookie.CookieSupport.isHttpToken(path) || ServerCookie.CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0
						&& ServerCookie.CookieSupport.isV0Token(path))) {
			// HTTP token in path - need to use v1
			newVersion = 1;
		}

		if (newVersion == 0
				&& (!ServerCookie.CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 && ServerCookie.CookieSupport.isHttpToken(domain) || ServerCookie.CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0
						&& ServerCookie.CookieSupport.isV0Token(domain))) {
			// HTTP token in domain - need to use v1
			newVersion = 1;
		}

		// Now build the cookie header
		// Value
		maybeQuote(buf, value);
		// Add version 1 specific information
		if (newVersion == 1) {
			// Version=1 ... required
			buf.append("; Version=1");

			// Comment=comment
			if (comment != null)
			{
				buf.append("; Comment=");
				ServerCookie.maybeQuote(buf, comment);
			}
		}

		// Add domain information, if present
		if (domain != null) {
			buf.append("; Domain=");
			ServerCookie.maybeQuote(buf, domain);
		}

		// Max-Age=secs ... or use old "Expires" format
		if (maxAge >= 0) {
			if (newVersion > 0) {
				buf.append("; Max-Age=");
				buf.append(maxAge);
			}
			// IE6, IE7 and possibly other browsers don't understand Max-Age.
			// They do understand Expires, even with V1 cookies!
			if (newVersion == 0 || ServerCookie.CookieSupport.ALWAYS_ADD_EXPIRES) {
				// Wdy, DD-Mon-YY HH:MM:SS GMT ( Expires Netscape format )
				buf.append("; Expires=");
				// To expire immediately we need to set the time in past
				if (maxAge == 0) {
					buf.append(ServerCookie.ANCIENTDATE);
				}
				else {
					ServerCookie.OLD_COOKIE_FORMAT.get().format(new Date(System.currentTimeMillis() + maxAge * 1000L), buf, new FieldPosition(0));
				}
			}
		}

		// Path=path
		if (path != null) {
			buf.append("; Path=");
			ServerCookie.maybeQuote(buf, path);
		}

		// Secure
		if (isSecure) {
			buf.append("; Secure");
		}

		// HttpOnly
		if (isHttpOnly) {
			buf.append("; HttpOnly");
		}
		headerBuf.append(buf);
	}

	/**
	 * Never quote values
	 * 
	 * @param buf
	 * @param value
	 */
	protected static void maybeQuote(final StringBuffer buf, final String value) {
		buf.append(value);
	}
}

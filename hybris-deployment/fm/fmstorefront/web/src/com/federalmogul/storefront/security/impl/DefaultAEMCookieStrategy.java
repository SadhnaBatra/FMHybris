package com.federalmogul.storefront.security.impl;

import com.federalmogul.storefront.security.AEMCookieStrategy;
import com.federalmogul.storefront.security.HybrisAuthToken;
import com.federalmogul.storefront.security.cookie.EnhancedCookieGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * Created by derek on 1/18/17.
 */
public class DefaultAEMCookieStrategy implements AEMCookieStrategy {

	private static final Logger LOG = Logger.getLogger(DefaultAEMCookieStrategy.class);

	private String webServiceProtocol;
	private String webServicePort;
	private String webServiceUri;
	private String clientId;
	private String clientSecret;
	private String grantType;
	private List<String> looselyVerifiedHostnames;
	private EnhancedCookieGenerator aemCookieGenerator;

	private boolean isHostnameExceptionInitialized = false;

	@Override
	public void setCookie(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Trying to authenticate user " + username + " to auth token web service");

		try {
			String authToken = getAuthToken(username, password, request);
			aemCookieGenerator.addCookieWithValueAsIs(response, authToken);
		} catch (Throwable t) {
			LOG.error("Could not retrieve an oauth token from the Hybris web service and set the HybrisAuthToken cookie. AEM and any related integration functions may not work correctly.", t);
		}
	}

	protected String getAuthToken(String username, String password, HttpServletRequest request) {
		String url = buildTokenUrl(username, password, request);

		RestTemplate rt = new RestTemplate();
		String unformattedToken = rt.getForObject(url, String.class);

		Gson gson = new GsonBuilder().create();
		HybrisAuthToken authToken = gson.fromJson(unformattedToken, HybrisAuthToken.class);
		String formattedToken = gson.toJson(authToken).replaceAll("\"", "%22").replaceAll(",", "%2C");

		return formattedToken;
	}

	protected String buildTokenUrl(String username, String password, HttpServletRequest request) {
		String host = request.getServerName();
		if (!isHostnameExceptionInitialized) {
			setHostnameSSLException(host);
		}

		String url = new StringBuilder(webServiceProtocol).append("://").append(host).append(":").append(webServicePort).append(webServiceUri).toString();
		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(url)
			.queryParam("client_id", clientId)
			.queryParam("client_secret", clientSecret)
			.queryParam("grant_type", grantType)
			.queryParam("username", username)
			.queryParam("password", password);

		return ucb.build(true).toUriString();
	}

	protected synchronized void setHostnameSSLException(String hostname) {
		if (StringUtils.isNotBlank(hostname) && !isHostnameExceptionInitialized) {
			LOG.info("Using '" + hostname + "' as the representative hostname to determine if we need to globally disable SSL certificate validation.");

			for (String looselyVerifiedHostname : looselyVerifiedHostnames) {
				if (looselyVerifiedHostname.toLowerCase().equals(hostname.toLowerCase())) {
					LOG.warn("A loosely verified hostname match was found for '" + hostname + "', will intentionally and globally disable SSL certificate validation");
					try {
						disableSSLCertificateValidation();
					} catch (NoSuchAlgorithmException e) {
						LOG.warn("Could not disable SSL certificate validation, AEM sessions that talk to Hybris may not function as expected.", e);
					} catch (KeyManagementException e) {
						LOG.warn("Could not disable SSL certificate validation, AEM sessions that talk to Hybris may not function as expected.", e);
					}

					break;
				}
			}

			isHostnameExceptionInitialized = true;
		}
	}

	protected static void disableSSLCertificateValidation() throws NoSuchAlgorithmException, KeyManagementException {

		TrustManager[] gullibleTrustManager = new TrustManager[]{
			new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers(){
					return null;
				}
				@Override
				public void checkClientTrusted( X509Certificate[] certs, String authType ){}
				@Override
				public void checkServerTrusted( X509Certificate[] certs, String authType ){}
			}
		};
		final SSLContext sc = SSLContext.getInstance("SSL");
		sc.init( null, gullibleTrustManager, null);
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		HostnameVerifier easyGoingVerifier = new HostnameVerifier() {
			@Override
			public boolean verify(String s, SSLSession sslSession) {
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(easyGoingVerifier);
	}

	@Override
	public void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		if (!request.isSecure()) {
			LOG.warn("Cannot remove secure AEM cookie from an insecure request.");
		}
		else {
			aemCookieGenerator.removeCookie(response);
		}
	}

	public String getWebServiceProtocol() {
		return webServiceProtocol;
	}

	@Required
	public void setWebServiceProtocol(String webServiceProtocol) {
		this.webServiceProtocol = webServiceProtocol;
	}

	public String getWebServicePort() {
		return webServicePort;
	}

	@Required
	public void setWebServicePort(String webServicePort) {
		this.webServicePort = webServicePort;
	}

	public String getWebServiceUri() {
		return webServiceUri;
	}

	@Required
	public void setWebServiceUri(String webServiceUri) {
		this.webServiceUri = webServiceUri;
	}

	public String getClientId() {
		return clientId;
	}

	@Required
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	@Required
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getGrantType() {
		return grantType;
	}

	@Required
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public List<String> getLooselyVerifiedHostnames() {
		return looselyVerifiedHostnames;
	}

	@Required
	public void setLooselyVerifiedHostnames(List<String> looselyVerifiedHostnames) {
		this.looselyVerifiedHostnames = looselyVerifiedHostnames;
	}

	public EnhancedCookieGenerator getAemCookieGenerator() {
		return aemCookieGenerator;
	}

	@Required
	public void setAemCookieGenerator(EnhancedCookieGenerator aemCookieGenerator) {
		this.aemCookieGenerator = aemCookieGenerator;
	}
}

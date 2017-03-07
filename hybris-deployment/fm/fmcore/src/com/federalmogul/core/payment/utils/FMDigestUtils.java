/**
 * 
 */
package com.federalmogul.core.payment.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;


/**
 * @author SU276498
 * 
 */
public class FMDigestUtils
{
	/**
	 * Utility method used for encrypting data used to secure communication with the CyberSource Hosted Order Page
	 * server.
	 * 
	 * @param customValues
	 *           - a String representation of all the data that requires securing.
	 * @param key
	 *           - a security key provided by CyberSource used to ensure each transaction is protected during it's
	 *           transmission across the Internet.
	 * @return - an encrypted String that is deemed secure for communication with CyberSource
	 * @throws java.security.InvalidKeyException
	 *            if the given key is inappropriate for initializing this MAC.
	 * @throws java.security.NoSuchAlgorithmException
	 *            when attempting to get a Message Authentication Code algorithm.
	 */


	public static String getPublicDigest(final String customValues, final String key) throws NoSuchAlgorithmException,
			InvalidKeyException
	{

		//final Base64 encoder = new Base64();
		//final Mac sha1Mac = Mac.getInstance("HmacSHA256");
		//final SecretKeySpec publicKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		//sha1Mac.init(publicKeySpec);

		//final byte[] publicBytes = sha1Mac.doFinal(customValues.getBytes());
		//final String publicDigest = new String(encoder.encode(publicBytes));

		//return publicDigest.replaceAll("\n", "");

		//LOG.info("Data:" + customValues);


		final Mac sha1Mac = Mac.getInstance("HmacSHA256");
		final SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		sha1Mac.init(secretKeySpec);

		final byte[] rawHmac = sha1Mac.doFinal(customValues.getBytes());
		final BASE64Encoder encoder = new BASE64Encoder();

		//LOG.info("Signature: " + encoder.encodeBuffer(rawHmac).trim());

		return encoder.encodeBuffer(rawHmac).trim();



	}
}

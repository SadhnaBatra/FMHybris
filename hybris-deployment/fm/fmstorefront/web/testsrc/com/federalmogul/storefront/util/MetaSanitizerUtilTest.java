/**
 * 
 */
package com.federalmogul.storefront.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class MetaSanitizerUtilTest
{

	@Test
	public void canSanitizeKeywords()
	{
		final String html = "<p>one <a href='http://example.com/two'><b>three</b></a> link.</p> <p>two</p> <p>'three'</p> <p>\"three\"</p>";
		final String expected = "one,three,link.,two,'three'";
		final String actual = MetaSanitizerUtil.sanitizeKeywords(html);

		assertEquals(expected, actual);
	}

}

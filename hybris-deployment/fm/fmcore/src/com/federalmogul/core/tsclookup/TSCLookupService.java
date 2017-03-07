/**
 * 
 */
package com.federalmogul.core.tsclookup;

import java.io.IOException;
import java.util.Map;

import com.federalmogul.core.model.TSCLocationModel;


/**
 * @author anapande
 * 
 */
public interface TSCLookupService
{

	public Map<TSCLocationModel, Integer> getTSCLocationByZipCode(final String zipCode) throws IOException;

	TSCLocationModel getStore(final String code);

}

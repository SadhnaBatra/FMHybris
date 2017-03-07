package com.falcon.strategies.impl;


import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import com.falcon.strategies.OrderCodeIdentificationStrategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Required;

/**
 * Default implementation of {@link com.falcon.strategies.OrderCodeIdentificationStrategy}.
 */
public class DefaultOrderCodeIdentificationStrategy implements OrderCodeIdentificationStrategy{
    private boolean failIfNotFound;
    private String idPattern;

    /**
     * Checks if given string is GUID
     *
     * @param potentialId
     *          - string to check
     * @return result
     */
    @Override
    public boolean isID(final String potentialId) {
        validateParameterNotNull(potentialId, "identifier must not be null");
        if(potentialId == null || potentialId.isEmpty())
        {
            return false;
        }

        final Pattern pattern = Pattern.compile(this.idPattern);
        final Matcher matcher = pattern.matcher(potentialId);
        if (matcher.find())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isFailIfNotFound()
    {

        return failIfNotFound;
    }

    @Required
    public void setFailIfNotFound(boolean failIfNotFound)
    {
        this.failIfNotFound = failIfNotFound;
    }

    @Required
    public void setIdPattern(String idPattern) {
        this.idPattern = idPattern;
    }
}

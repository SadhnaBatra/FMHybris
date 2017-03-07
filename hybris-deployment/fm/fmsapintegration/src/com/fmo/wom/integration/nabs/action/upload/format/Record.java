
package com.fmo.wom.integration.nabs.action.upload.format;

import java.text.SimpleDateFormat;
import java.util.List;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.OrderBO;

public abstract class Record {
    
    private String code;
    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
    
    public abstract List<String> getFormattedStringList(int transactionId, OrderBO theOrder);
    
    protected final String getPaddedCodeValue() {
        return StringUtil.stringPad(getCode(), ' ', 2);
    }

    protected final void setCode(String code) {
        this.code = code;
    }
    
    protected final String getCode() {
        return code;
    }
    
    public static String getFiller(int length) {
        return StringUtil.stringPad(" ", ' ', length);
    }
}
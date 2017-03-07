package com.fmo.wom.domain.nabs;

import java.io.Serializable;

public abstract class DB2StringPK implements Serializable {

    public String stringPad(String str, char ch, int len) {
        if (str == null) {
            str = "";
        }
        StringBuffer sb = new StringBuffer(str);
        for (int i = 1; i <= (len - str.length()); i++) {
            sb.append(ch);
        }
        return (sb.toString());
    }
    
}

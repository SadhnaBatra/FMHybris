package com.fmo.wom.domain.nabs.inquiry;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fmo.wom.domain.nabs.DB2StringPK;

@Embeddable
public class WebInquiryPK extends DB2StringPK {

    private static final int FIELD_SIZE = 14;
    
    @Column (name="INQUIRY_KEY")
    private String inquiryKey;
    
    public String getInquiryKey() {
//        if (inquiryKey != null) {
//            this.inquiryKey = stringPad(inquiryKey, ' ', FIELD_SIZE);
//        }
        return inquiryKey;
    }

    public void setInquiryKey(String inquiryKey) {
        this.inquiryKey = inquiryKey;
//        if (inquiryKey != null) {
//            this.inquiryKey = stringPad(inquiryKey, ' ', FIELD_SIZE);
//        }
    }
    
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(inquiryKey);
        return builder.toHashCode();
    }
    
    public int compareTo(Object obj) {
        int returnValue = -1;
        if (obj instanceof WebInquiryPK)
        {
            final WebInquiryPK other = (WebInquiryPK)obj;
            final CompareToBuilder builder = new CompareToBuilder();
            builder.append(inquiryKey, other.inquiryKey);
            returnValue = builder.toComparison();
        }
        return returnValue;
    }
    
    @Override
    public boolean equals(Object o) {
        return compareTo(o) == 0;
    }
    
}

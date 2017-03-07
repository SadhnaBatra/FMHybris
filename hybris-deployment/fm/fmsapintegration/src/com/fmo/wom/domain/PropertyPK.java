package com.fmo.wom.domain;

import java.io.Serializable;

//import javax.persistence.*;

//Embeddable
public class PropertyPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //Column(name="PROP_CATEGORY")
    private String category;
    
    //Column(name="PROP_NAME")
    private String key;

    public PropertyPK() { }
    
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
   
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PropertyPK)) {
            return false;
        }
        PropertyPK castOther = (PropertyPK) other;
        return 
            this.category.equals(castOther.category)
            && (this.key == castOther.key);
    }
    
 
}

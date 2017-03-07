package com.fmo.wom.domain;

//import javax.persistence.*;

/*Entity
//Table (name="PROPERTY")
//NamedQueries({
	//NamedQuery(name="getCategoryList",
			query="select distinct prop.id.category from PropertyBO prop")
})
//AttributeOverrides({ 
	//AttributeOverride( name="createUserId", column = //Column(name="CREATE_GUID") ),
	//AttributeOverride( name="updateUserId", column = //Column(name="UPDATE_GUID") )
})*/

public class PropertyBO extends WOMBaseBO {
    
    //EmbeddedId 
    private PropertyPK id;
    //Column(name="PROP_VALUE")
    private String value;
    
    public PropertyBO() {
        super();
    }
    
    public PropertyPK getId() {
        return this.id;
    }

    public void setId(PropertyPK id) {
        this.id = id;
    }
    
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}

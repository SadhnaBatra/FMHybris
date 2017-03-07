package com.fmo.wom.helper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ListItems")
public class XmlListAdapter<T> {

	@XmlAnyElement(lax=true)
    private List<T> value ;

    public XmlListAdapter() {
    }

    public XmlListAdapter(List<T> value) {
        this.value = value;
    }

    public List<T> getValue() {
        if(value == null)
            value = new ArrayList<T>();
        return this.value;
    }
}

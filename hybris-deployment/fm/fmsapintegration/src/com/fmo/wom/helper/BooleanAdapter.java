package com.fmo.wom.helper;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {

	@Override
	public String marshal(Boolean arg0) throws Exception {
		return arg0?"1":"0";
	}

	@Override
	public Boolean unmarshal(String arg0) throws Exception {
		return arg0==null?null:"1".equalsIgnoreCase(arg0);	
	}

} 
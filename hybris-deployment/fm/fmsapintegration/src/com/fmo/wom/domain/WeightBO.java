/*
 * Created on Jun 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

import java.text.DecimalFormat;

/**
 * @author amarnr85
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class WeightBO {

	private UnitOfMeasureBO wtUOM;
	private double weight;
	
	public UnitOfMeasureBO getWtUOM() {
		return wtUOM;
	}
	public void setWtUOM(UnitOfMeasureBO wtUOM) {
		this.wtUOM = wtUOM;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getFormatWeight() {		
		return Round(weight,2);
	}
	
    public static double Round(double Rval, int Rpl) {
	    	double p = (double)Math.pow(10,Rpl);
	    	Rval = Rval * p;
	    	return (double)Math.round(Rval)/p;
	    }
	
	@Override
	public String toString() {
		return "WeightBO [wtUOM=" + wtUOM + ", weight=" + weight + "]";
	}
	
}

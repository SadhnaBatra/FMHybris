/*
 * Created on Jun 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

public class OversizeBO extends WOMBaseBO {

	private String size;
    private boolean metric;
    private String displayPartNumber;
    private int availableQuantity;
    private int assignedQuantity;
    private boolean selected = false;
    
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public boolean isMetric() {
        return metric;
    }
    public void setMetric(boolean metric) {
        this.metric = metric;
    }
    public String getDisplayPartNumber() {
        return displayPartNumber;
    }
    public void setDisplayPartNumber(String displayPartNumber) {
        this.displayPartNumber = displayPartNumber;
    }
    public int getAvailableQuantity() {
        return availableQuantity;
    }
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    public int getAssignedQuantity() {
        return assignedQuantity;
    }
    public void setAssignedQuantity(int assignedQuantity) {
        this.assignedQuantity = assignedQuantity;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

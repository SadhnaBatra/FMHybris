package com.fmo.wom.comparators;

import java.util.Comparator;

import com.fmo.wom.domain.InventoryBO;

public class InventoryBOConparator implements Comparator<InventoryBO>{

	@Override
	public int compare(InventoryBO o1, InventoryBO o2) {
		if(o1.getDistributionCenter().getDistance()>o2.getDistributionCenter().getDistance()){
			return 1;
		}else if(o1.getDistributionCenter().getDistance()<o2.getDistributionCenter().getDistance()){
			return -1;
		}
		return 0;
	}

	

}

package com.federalmogul.core.comparators;

import java.util.Comparator;

import com.federalmogul.facades.order.data.DistrubtionCenterData;

public class DistributionCenterDataComparator implements Comparator<DistrubtionCenterData> {

	@Override
	public int compare(DistrubtionCenterData o1, DistrubtionCenterData o2) {
		// TODO Auto-generated method stub
		if(o1.getDistance()>o2.getDistance()){
			return 1;
		}else if(o1.getDistance()<o2.getDistance()){
			return -1;
		}
		return 0;
	}

	

}

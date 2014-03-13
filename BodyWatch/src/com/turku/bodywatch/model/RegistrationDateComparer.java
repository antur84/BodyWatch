package com.turku.bodywatch.model;

import java.util.Comparator;

import com.turku.bodywatch.model.domainobjects.WeightEntry;

public class RegistrationDateComparer implements
		Comparator<WeightEntry> {

	@Override
	public int compare(WeightEntry arg0, WeightEntry arg1) {
		return arg0.getRegistrationDate().getValue().compareTo(arg1.getRegistrationDate().getValue());
	}

}

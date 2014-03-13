package com.turku.bodywatch.model.domainobjects;

public class BodyFat extends ValueObjectBase<Double> {
	private final Double bodyFat;
	
	public BodyFat(Double bodyFat){
		this.bodyFat = bodyFat;
	}

	@Override
	public Double getValue() {
		return bodyFat;
	}
}

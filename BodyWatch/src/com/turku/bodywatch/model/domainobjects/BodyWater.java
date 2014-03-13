package com.turku.bodywatch.model.domainobjects;

public class BodyWater extends ValueObjectBase<Double> {
	private final Double bodyWater;
	
	public BodyWater(Double bodyWater){
		this.bodyWater = bodyWater;
	}

	@Override
	public Double getValue() {
		return bodyWater;
	}
}

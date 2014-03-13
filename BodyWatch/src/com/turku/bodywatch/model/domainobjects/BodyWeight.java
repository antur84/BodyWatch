package com.turku.bodywatch.model.domainobjects;


public class BodyWeight extends ValueObjectBase<Double> {
	private final double bodyWeight;
	
	public BodyWeight(Double bodyWeight) throws IllegalArgumentException{
		
		if(bodyWeight <= 0){
			throw new IllegalArgumentException("bodyWeight can't be 0 or negative");
		}
		
		this.bodyWeight = bodyWeight;
	}

	@Override
	public Double getValue() {
		return bodyWeight;
	}
}

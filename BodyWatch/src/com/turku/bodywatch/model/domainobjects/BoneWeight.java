package com.turku.bodywatch.model.domainobjects;

public class BoneWeight extends ValueObjectBase<Double> {
	private final Double boneWeight;
	
	public BoneWeight(Double boneWeight){
		this.boneWeight = boneWeight;
	}

	@Override
	public Double getValue() {
		return boneWeight;
	}
}

package com.turku.bodywatch.model.domainobjects;

public class MuscleRatio extends ValueObjectBase<Double> {
	private final Double muscleRatio;
	
	public MuscleRatio(Double muscleRatio){
		this.muscleRatio = muscleRatio;
	}

	@Override
	public Double getValue() {
		return muscleRatio;
	}
}

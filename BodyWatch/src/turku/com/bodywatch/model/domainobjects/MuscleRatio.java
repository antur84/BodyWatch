package turku.com.bodywatch.model.domainobjects;

public class MuscleRatio extends ValueObjectBase<Long> {
	private final long muscleRatio;
	
	public MuscleRatio(long muscleRatio){
		this.muscleRatio = muscleRatio;
	}

	@Override
	public Long getValue() {
		return muscleRatio;
	}
}

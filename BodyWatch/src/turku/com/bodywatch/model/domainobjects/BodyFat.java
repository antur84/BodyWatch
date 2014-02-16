package turku.com.bodywatch.model.domainobjects;

public class BodyFat extends ValueObjectBase<Long> {
	private final long bodyFat;
	
	public BodyFat(long bodyFat){
		this.bodyFat = bodyFat;
	}

	@Override
	public Long getValue() {
		return bodyFat;
	}
}

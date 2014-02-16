package turku.com.bodywatch.model.domainobjects;

public class BodyWater extends ValueObjectBase<Long> {
	private final long bodyWater;
	
	public BodyWater(long bodyWater){
		this.bodyWater = bodyWater;
	}

	@Override
	public Long getValue() {
		return bodyWater;
	}
}

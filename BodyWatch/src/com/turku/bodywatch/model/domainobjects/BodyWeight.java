package turku.com.bodywatch.model.domainobjects;

public class BodyWeight extends ValueObjectBase<Long> {
	private final long bodyWeight;
	
	public BodyWeight(long bodyWeight){
		this.bodyWeight = bodyWeight;
	}

	@Override
	public Long getValue() {
		return bodyWeight;
	}
}

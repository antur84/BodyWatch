package turku.com.bodywatch.model.domainobjects;

public class BoneWeight extends ValueObjectBase<Long> {
	private final long boneWeight;
	
	public BoneWeight(long boneWeight){
		this.boneWeight = boneWeight;
	}

	@Override
	public Long getValue() {
		return boneWeight;
	}
}

package turku.com.bodywatch.model.domainobjects;

public class WeightEntry {

	private int id;
	private BodyWeight bodyWeight;
	private BodyFat bodyFat;
	private BodyWater bodyWater;
	private MuscleRatio muscleRatio;
	private BoneWeight boneWeight;

	public int getId() {
		return id;
	}

	public BodyWeight getBodyWeight() {
		return bodyWeight;
	}

	public BodyFat getBodyFat() {
		return bodyFat;
	}

	public BodyWater getBodyWater() {
		return bodyWater;
	}

	public MuscleRatio getMuscleRatio() {
		return muscleRatio;
	}

	public BoneWeight getBoneWeight() {
		return boneWeight;
	}

	public WeightEntry() {
	}

	public WeightEntry(int id, BodyWeight bodyWeight, BodyFat bodyFat,
			BodyWater bodyWater, MuscleRatio muscleRatio, BoneWeight boneWeight) {
		this(bodyWeight, bodyFat, bodyWater, muscleRatio, boneWeight);
		this.id = id;
	}

	public WeightEntry(BodyWeight bodyWeight, BodyFat bodyFat,
			BodyWater bodyWater, MuscleRatio muscleRatio, BoneWeight boneWeight) {
		this.bodyWeight = bodyWeight;
		this.bodyFat = bodyFat;
		this.bodyWater = bodyWater;
		this.muscleRatio = muscleRatio;
		this.boneWeight = boneWeight;
	}

}

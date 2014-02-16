package turku.com.bodywatch.model;

import turku.com.bodywatch.model.domainobjects.WeightEntry;

public interface IBodyWatchRepository {

	void insertWeightEntry(WeightEntry weightEntry);

	String getAllWeightEntries();
}

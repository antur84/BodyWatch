package turku.com.bodywatch.model;

import java.util.List;

import turku.com.bodywatch.model.domainobjects.WeightEntry;
import android.content.Context;

public class BodyWatchRepository implements IBodyWatchRepository {

	private BodyWatchDatabaseHelper dbHelper;
	
	public BodyWatchRepository(Context context){
		dbHelper = new BodyWatchDatabaseHelper(context);
	}
	
	@Override
	public void insertWeightEntry(WeightEntry weightEntry) {
		dbHelper.InsertWeight(weightEntry);
	}
	
	@Override
	public String getAllWeightEntries() {
		String result = "";
		List<WeightEntry> entries = dbHelper.getAllWeightEntries();
		for (WeightEntry w : entries) {
			result += w.getId();
		}
		
		return result;
	}
}

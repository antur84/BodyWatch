package com.turku.bodywatch.model;

import java.util.Collections;
import java.util.List;

import com.turku.bodywatch.model.domainobjects.WeightEntry;

import android.content.Context;

public class BodyWatchRepository implements IBodyWatchRepository {

	private BodyWatchDatabaseHelper dbHelper;
	
	public BodyWatchRepository(Context context){
		dbHelper = new BodyWatchDatabaseHelper(context);
	}
	
	@Override
	public void insertWeightEntry(WeightEntry weightEntry) {
		dbHelper.insertWeightEntry(weightEntry);
	}
	
	@Override
	public List<WeightEntry> getAllWeightEntries() {
		List<WeightEntry> entries = dbHelper.getAllWeightEntries();
		Collections.sort(entries, new RegistrationDateComparer());
		return entries;
	}

	@Override
	public void deleteWeightEntries(List<Integer> _selectedRows) {
		for (int i = 0; i < _selectedRows.size(); i++) {
			dbHelper.deleteWeightEntry(_selectedRows.get(i));
		}		
	}
}

package com.turku.bodywatch.model;

import java.util.List;

import com.turku.bodywatch.model.domainobjects.WeightEntry;

public interface IBodyWatchRepository {

	void insertWeightEntry(WeightEntry weightEntry);

	List<WeightEntry> getAllWeightEntries();

	void deleteWeightEntries(List<Integer> _selectedRows);
}
